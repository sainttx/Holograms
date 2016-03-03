package com.sainttx.holograms.api;

import org.bukkit.World;
import org.bukkit.entity.Entity;

public interface HologramEntityController {

    /**
     * Spawns a new entity at a specific location for a HologramLine
     * to modify displayed text.
     *
     * @param world the world
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @param parentPiece the parenting hologram line for the entity
     * @return the resulting entity that was spanwned
     */
    HologramEntity spawnHologram(World world, double x, double y, double z, HologramLine parentPiece);

    /**
     * Returns the {@link HologramEntity} of a hologram entity. If the
     * entity is not a Hologram <code>null</code> is returned.
     *
     * @param bukkitEntity the Bukkit entity
     * @return the base entity
     */
    HologramEntity getHologramEntity(Entity bukkitEntity);
}
