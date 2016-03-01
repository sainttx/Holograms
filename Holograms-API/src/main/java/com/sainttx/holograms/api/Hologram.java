package com.sainttx.holograms.api;

import org.bukkit.Location;

import java.util.Collection;

/**
 * Created by Matthew on 12/14/2015.
 */
public interface Hologram {

    /**
     * Returns the unique ID name of this Hologram.
     *
     * @return the holograms name
     */
    String getName();

    /**
     * Returns the persistence state of this Hologram.
     *
     * @return <tt>true</tt> if the Hologram is persistent
     */
    boolean isPersistent();

    /**
     * Sets the persistence state of this Hologram.
     *
     * @param persist the new state
     */
    void setPersistent(boolean persist);

    /**
     * Returns the location of this Hologram.
     *
     * @return the holograms location
     */
    Location getLocation();

    /**
     * Permanently removes this Hologram.
     */
    void remove();

    /**
     * De-spawns all of the lines in this Hologram.
     */
    void despawn();

    /**
     * Refreshes this Holograms line(s).
     */
    void refresh();

    /**
     * Returns the lines contained by this Hologram.
     *
     * @return all lines in the hologram
     */
    Collection<HologramLine> getLines();

    /**
     * Adds a new {@link HologramLine} to this Hologram.
     *
     * @param line the line
     */
    void addLine(HologramLine line);

    /**
     * Inserts a new {@link HologramLine} to this Hologram at a specific index.
     *
     * @param line  the line
     * @param index the index to add the line at
     */
    void addLine(HologramLine line, int index);

    /**
     * Removes a {@link HologramLine} from this Hologram.
     *
     * @param line the line
     */
    void removeLine(HologramLine line);

    /**
     * Returns a {@link HologramLine} at a specific index.
     *
     * @param index the index
     */
    HologramLine getLine(int index);

    /**
     * Teleports this Hologram to a new {@link Location}.
     *
     * @param location the location
     */
    void teleport(Location location);
}
