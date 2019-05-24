package com.sainttx.holograms;

import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.commands.HologramCommands;
import com.sainttx.holograms.parser.AnimatedItemLineParser;
import com.sainttx.holograms.parser.AnimatedTextLineParser;
import com.sainttx.holograms.parser.ItemLineParser;
import com.sainttx.holograms.tasks.HologramSaveTask;
import com.sainttx.holograms.tasks.HologramUpdateTask;
import com.sainttx.holograms.util.ReflectionUtil;

import java.lang.reflect.Constructor;

public class HologramPlugin extends com.sainttx.holograms.api.HologramPlugin {

    /**
     * The path to the package that contains our NMS implementations
     */
    public static final String NMS_PACKAGE_PATH = "com.sainttx.holograms.nms." + ReflectionUtil.getVersion();

    private HologramManager manager;
    private HologramEntityController controller;
    private Runnable saveTask = new HologramSaveTask(this);
    private Runnable updateTask = new HologramUpdateTask(this);

    @Override
    public void onEnable() {
        this.manager = new ManagerImpl(this);

        // Register parsers
        addLineParser(new ItemLineParser());
        addLineParser(new AnimatedItemLineParser());
        addLineParser(new AnimatedTextLineParser());

        if (setupController()) {
            getServer().getPluginManager().registerEvents(new HologramListener(this), this);
            getCommand("holograms").setExecutor(new HologramCommands(this));
            getServer().getScheduler().runTaskLater(this, () -> ((ManagerImpl) manager).load(), 1L);
            // getServer().getScheduler().runTaskTimer(this, saveTask, 0L, 20L * 60L * 5L); // Save dirty holograms every 5 minutes
            getServer().getScheduler().runTaskTimer(this, updateTask, 2L, 2L); // Update holograms (TODO: Configurable interval)
        }
    }

    @Override
    public void onDisable() {
        saveTask.run();
        manager.clear();
        this.manager = null;
        this.controller = null;
    }

    // Sets up the entity controller
    private boolean setupController() {
        try {
            Class<?> nmsControllerClazz = Class.forName(NMS_PACKAGE_PATH + "HologramEntityControllerImpl");
            Constructor<?> constructor = nmsControllerClazz.getConstructor(com.sainttx.holograms.api.HologramPlugin.class);
            this.controller = (HologramEntityController) constructor.newInstance(this);
            getLogger().info("HologramEntityController set to: " + controller.getClass().getCanonicalName());
        } catch (Exception ex) {
            /* Couldn't instantiate the nmsController - Spigot/CraftBukkit version isn't supported */
            getLogger().severe("The plugin couldn't create the NMS controller instance and has been disabled. This is likely" +
                    "due to no supported Hologram implementation for your CraftBukkit/Spigot version.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    @Override
    public HologramManager getHologramManager() {
        return manager;
    }

    @Override
    public HologramEntityController getEntityController() {
        return controller;
    }
}
