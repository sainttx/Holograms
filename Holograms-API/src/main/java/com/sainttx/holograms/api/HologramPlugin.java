package com.sainttx.holograms.api;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class HologramPlugin extends JavaPlugin {

    /**
     * Returns the current active {@link HologramManager} implementation.
     *
     * @return the manager
     */
    public abstract HologramManager getHologramManager();

    /**
     * Returns the entity controller instance for the currently active
     * version of CraftBukkit software.
     *
     * @return the entity controller
     */
    public abstract HologramEntityController getNMSController();
}
