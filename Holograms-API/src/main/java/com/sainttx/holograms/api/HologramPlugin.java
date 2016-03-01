package com.sainttx.holograms.api;

import org.bukkit.plugin.Plugin;

public interface HologramPlugin extends Plugin {

    /**
     * Returns the current active {@link HologramManager} implementation.
     *
     * @return the manager
     */
    HologramManager getHologramManager();

    /**
     * Returns the entity controller instance for the currently active
     * version of CraftBukkit software.
     *
     * @return the entity controller
     */
    NMSController getNMSController();
}
