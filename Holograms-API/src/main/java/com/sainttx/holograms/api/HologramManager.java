package com.sainttx.holograms.api;

import com.sainttx.holograms.api.line.UpdatingHologramLine;

import java.util.Collection;
import java.util.Map;

public interface HologramManager {

    /**
     * Saves a {@link Hologram} for persistence through server restarts.
     *
     * @param hologram the hologram
     */
    void saveHologram(Hologram hologram);

    /**
     * Permanently deletes a {@link Hologram}. This method will despawn
     * and remove the hologram as active.
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
     * Tracks an updating hologram line for execution of
     * {@link UpdatingHologramLine#update()} by the plugins internal
     * scheduler.
     *
     * @param line the line to track.
     */
    void trackLine(UpdatingHologramLine line);

    /**
     * Removes an already tracked line.
     *
     * @param line the line
     * @return <tt>true</tt> if the line was removed from the
     *      collection returned by {@link #getTrackedLines()}
     */
    boolean untrackLine(UpdatingHologramLine line);

    /**
     * Returns all currently tracked hologram lines.
     *
     * @return tracked lines
     */
    Collection<? extends UpdatingHologramLine> getTrackedLines();

    /**
     * Despawns and removes all active holograms.
     */
    void clear();
}
