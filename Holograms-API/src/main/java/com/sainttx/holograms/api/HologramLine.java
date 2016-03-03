package com.sainttx.holograms.api;

import org.bukkit.Location;

public interface HologramLine {

    /*
    TODO:
    spawn -> setLocation(Location)
    despawn -> hide()
    *new* show() -> throws IllegalStateException if location not set
    *new* getLocation()
    *new* boolean isHidden()
    remove getEntity (only used to teleport, but this will be done with setLocation)
     */

    /**
     * Spawns this line at a specific {@link Location}.
     *
     * @param location where to spawn
     */
    void spawn(Location location);

    /**
     * Despawns this line.
     */
    void despawn();

    /**
     * Returns the height of the line.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the {@link HologramEntity} being used to display this line.
     *
     * @return the entity
     */
    HologramEntity getEntity();

    /**
     * Returns the parent {@link Hologram} that contains this line.
     *
     * @return the parent hologram
     */
    Hologram getHologram();
}
