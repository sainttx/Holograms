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
     * Sets a new custom name for the hologram line.
     *
     * @param text the new text
     */
    void setCustomName(String text);

    /**
     * Returns the current custom name/text for the hologram line.
     *
     * @return the current text.
     */
    String getCustomName();

    /**
     * Gets the Bukkit entity for this hologram line.
     *
     * @return the entity
     */
    Entity getBukkitEntity();
}
