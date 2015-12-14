package com.sainttx.holograms.api;

import org.bukkit.Location;

import java.util.Collection;

/**
 * Created by Matthew on 12/14/2015.
 */
public interface Hologram {

    /**
     * Returns the identification of this Hologram
     *
     * @return the holograms identifier
     */
    String getName();

    /**
     * Returns whether or not this Hologram is persistent throughout server restarts
     *
     * @return the state of persistence
     */
    boolean isPersistent();

    /**
     * Sets this {@link Hologram}s persistence state
     *
     * @param persistent the new state of persistence
     */
    void setIsPersistent(boolean persistent);

    /**
     * Returns the location of the hologram
     *
     * @return the holograms location
     */
    Location getLocation();

    /**
     * Permanently removes this hologram
     */
    void remove();

    /**
     * Despawns all of the lines in the hologram
     */
    void despawn();

    /**
     * Refreshes this {@link Hologram}s line(s)
     */
    void refresh();

    /**
     * Returns the lines contained by this {@link Hologram}
     *
     * @return all lines in the hologram
     */
    Collection<HologramLine> getLines();

    /**
     * Adds a new {@link HologramLine} to this {@link Hologram}
     *
     * @param line the line
     */
    void addLine(HologramLine line);

    /**
     * Adds a new {@link HologramLine} to this {@link Hologram} at a specific slot
     *
     * @param line  the line
     * @param index the slot to add the line at
     */
    void addLine(HologramLine line, int index);

    /**
     * Removes a {@link HologramLine} from this {@link Hologram}
     *
     * @param line the line
     */
    void removeLine(HologramLine line);

    /**
     * Returns a {@link HologramLine} at a specific slot
     *
     * @param index the slot
     */
    HologramLine getLine(int index);

    /**
     * Teleports this {@link Hologram} to a new {@link Location}
     *
     * @param location the location to teleport to
     */
    void teleport(Location location);
}
