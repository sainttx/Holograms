package com.sainttx.holograms.api;

import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.Nameable;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface HologramEntityController {

    /**
     * Spawns a new entity at a specific location for a HologramLine
     * to modify displayed text.
     *
     * @param line the parenting hologram line for the entity
     * @param location the location
     * @return the resulting entity that was spanwned
     */
    Nameable spawnNameable(HologramLine line, Location location);

    /**
     * Returns the {@link HologramEntity} of a hologram entity. If the
     * entity is not a Hologram <code>null</code> is returned.
     *
     * @param bukkitEntity the Bukkit entity
     * @return the base entity
     */
    HologramEntity getHologramEntity(Entity bukkitEntity);
}
