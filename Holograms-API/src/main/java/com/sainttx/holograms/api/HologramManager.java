package com.sainttx.holograms.api;

import java.util.Map;

public interface HologramManager {

    /**
     * Saves a Hologram for persistence through server restarts
     *
     * @param hologram The Hologram to be saved
     */
    void saveHologram(Hologram hologram);

    /**
     * Deletes a Hologram for persistence through server restarts
     *
     * @param hologram The Hologram to be deleted
     */
    void deleteHologram(Hologram hologram);

    /**
     * Returns a Hologram by its name
     *
     * @param name The name of the Hologram
     * @return The hologram
     */
    Hologram getHologram(String name);

    /**
     * Returns a map of the currently active holograms
     *
     * @return all active holograms
     */
    Map<String, Hologram> getActiveHolograms();

    /**
     * Adds an active Hologram
     *
     * @param hologram The Hologram to be added
     */
    void addHologram(Hologram hologram);

    /**
     * Removes an active Hologram
     *
     * @param hologram The Hologram to be removed
     */
    void removeHologram(Hologram hologram);

    /**
     * Removes all active Holograms
     */
    void clear();
}
