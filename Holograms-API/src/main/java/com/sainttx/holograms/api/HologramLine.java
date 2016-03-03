package com.sainttx.holograms.api;

import org.bukkit.Location;

public interface HologramLine {

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
