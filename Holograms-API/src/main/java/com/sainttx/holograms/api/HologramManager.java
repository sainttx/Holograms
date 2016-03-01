package com.sainttx.holograms.api;

import java.util.Map;

public interface HologramManager {

    /**
     * Saves a {@link Hologram} for persistence through server restarts.
     *
     * @param hologram the hologram
     */
    void saveHologram(Hologram hologram);

    /**
     * Permanently deletes a {@link Hologram}.
     *
     * @param hologram the hologram
     */
    void deleteHologram(Hologram hologram);

    /**
     * Gets a {@link Hologram} by its unique name. If the hologram does not
     * exist then <code>null</code> will be returned.
     *
     * @param name the name of the hologram
     * @return the obtained hologram
     */
    Hologram getHologram(String name);

    /**
     * Gets all currently {@link Hologram}s. The map returned is a shallow copy.
     *
     * @return all holograms
     */
    Map<String, Hologram> getActiveHolograms();

    /**
     * Registers a {@link Hologram} to the manager. If the user wishes for
     * the hologram to have persistence, then the {@link #saveHologram(Hologram)} method
     * should be called.
     * <p>
     * This method does not call the {@link Hologram#refresh()} method
     * and thus displaying the holograms entities is up to the user.
     * The hologram will be available in {@link #getActiveHolograms()} after
     * calling this method.
     *
     * @param hologram the hologram
     */
    void addActiveHologram(Hologram hologram);

    /**
     * Unregisters a {@link Hologram} from the map of active holograms.
     *
     * @param hologram the hologram
     */
    void removeActiveHologram(Hologram hologram);

    /**
     * Despawns and removes all active holograms.
     */
    void clear();
}
