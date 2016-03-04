package com.sainttx.holograms.api;

import org.bukkit.entity.Entity;

public interface HologramEntity {

    /**
     * Returns the parenting {@link HologramLine} of this base.
     *
     * @return the base line
     */
    HologramLine getHologramLine();

    /**
     * Permanently removes this entity.
     */
    void remove();

    /**
     * Gets the Bukkit entity for this hologram line.
     *
     * @return the entity
     */
    Entity getBukkitEntity();
}
