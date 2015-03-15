package com.sainttx.holograms;

import com.sainttx.holograms.commands.HologramCommands;
import com.sainttx.holograms.data.Hologram;
import com.sainttx.holograms.nms.NMSController;
import com.sainttx.holograms.util.ReflectionUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by Matthew on 14/03/2015.
 */
public class HologramPlugin extends JavaPlugin {

    /**
     * The path to the package that contains our NMS implementations
     */
    public static final String NMS_PACKAGE_PATH = "com.sainttx.holograms.nms." + ReflectionUtil.getVersion();

    /*
     * The Hologram manager instance
     */
    private HologramManager manager;

    /*
     * The NMSController instance
     */
    private NMSController controller;

    @Override
    public void onEnable() {
        this.manager = new HologramManager(this);
        getCommand("holograms").setExecutor(new HologramCommands(this));
        getServer().getPluginManager().registerEvents(new HologramListener(manager), this);

        setupController();
    }

    @Override
    public void onDisable() {
        for (Map.Entry<String, Hologram> hologram : manager.getActiveHolograms().entrySet()) {
            hologram.getValue().despawnEntities();
            manager.removeHologram(hologram.getValue());
        }
    }

    /*
     * Sets up the NMS Controller instance
     */
    private void setupController() {
        try {
            Class<?> nmsControllerClazz = Class.forName(NMS_PACKAGE_PATH + "NMSControllerImpl");
            this.controller = (NMSController) nmsControllerClazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (this.controller == null) {
            getLogger().severe("The plugin couldn't create the NMS controller instance and has been disabled. This is likely" +
                    "due to no supported Hologram implementation for your CraftBukkit/Spigot version.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    /**
     * Returns the Hologram manager
     *
     * @return The hologram manager
     */
    public HologramManager getHologramManager() {
        return manager;
    }

    /**
     * Returns the NMS controller instance
     *
     * @return The NMS Controller
     */
    public NMSController getNMSController() {
        return controller;
    }
}
