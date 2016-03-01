package com.sainttx.holograms;

import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.commands.HologramCommands;
import com.sainttx.holograms.util.ReflectionUtil;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Matthew on 14/03/2015.
 */
public class HologramPlugin extends JavaPlugin implements com.sainttx.holograms.api.HologramPlugin {

    /**
     * The path to the package that contains our NMS implementations
     */
    public static final String NMS_PACKAGE_PATH = "com.sainttx.holograms.nms." + ReflectionUtil.getVersion();

    /*
     * The Hologram manager instance
     */
    private HologramManager manager;
    /*
     * The entity controller
     */
    private HologramEntityController controller;

    @Override
    public void onEnable() {
        this.manager = new ManagerImpl(this);

        if (setupController()) {
            getServer().getPluginManager().registerEvents(new HologramListener(this), this);
            getCommand("holograms").setExecutor(new HologramCommands(this));
            ((ManagerImpl) manager).load();
        }
    }

    @Override
    public void onDisable() {
        manager.clear();
        this.manager = null;
        this.controller = null;
    }

    // Sets up the entity controller
    private boolean setupController() {
        try {
            Class<?> nmsControllerClazz = Class.forName(NMS_PACKAGE_PATH + "HologramEntityControllerImpl");
            this.controller = (HologramEntityController) nmsControllerClazz.newInstance();
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
    public HologramEntityController getNMSController() {
        return controller;
    }
}
