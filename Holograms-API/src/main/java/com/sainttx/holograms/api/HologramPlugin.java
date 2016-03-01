package com.sainttx.holograms.api;

import org.bukkit.plugin.Plugin;

public interface HologramPlugin extends Plugin {

    /**
     * Returns the Hologram manager
     *
     * @return The hologram manager
     */
    HologramManager getHologramManager();

    /**
     * Returns the NMS controller instance
     *
     * @return The NMS Controller
     */
    NMSController getNMSController();
}
