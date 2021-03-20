package com.sainttx.holograms.api;

import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.entity.Nameable;
import com.sainttx.holograms.api.line.HologramLine;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface HologramEntityController {

    /**
     * Returns the current Minecraft version implementation that this
     * controller is handling.
     *
     * @return the minecraft version
     */
    MinecraftVersion getMinecraftVersion();

    /**
     * Spawns a new entity at a specific location for a HologramLine
     * to modify displayed text.
     *
     * @param line the parenting hologram line for the entity
     * @param location the location
     * @return the resulting entity that was spawned
     */
    Nameable spawnNameable(HologramLine line, Location location);
    /**
     * Spawns a new entity at a specific location for a HologramLine
     * to hold an item as a head. No rotation happens
     *
     * @param line the parenting hologram line for the entity
     * @param location the location
     * @param itemstack the item to put on the head
     * @return the resulting entity that was spawned
     */
     Nameable spawnHeadHolder(HologramLine line, Location location, ItemStack itemstack);
    /**
     * Spawns a new entity at a specific location for a HologramLine
     * to modify displayed item.
     *
     * @param line the parenting hologram line for the entity
     * @param location the location
     * @return the resulting entity that was spawned
     * @deprecated superseded by {@link #spawnItemHolder(HologramLine, Location, ItemStack)}
     */
    @Deprecated
    ItemHolder spawnItemHolder(HologramLine line, Location location);

    /**
     * Spawns a new entity at a specific location for a HologramLine
     * to modify displayed item.
     *
     * @param line the parenting hologram line for the entity
     * @param location the location
     * @param itemstack initial item
     * @return the resulting entity that was spawned
     */
    ItemHolder spawnItemHolder(HologramLine line, Location location, ItemStack itemstack);

    /**
     * Returns the {@link HologramEntity} of a hologram entity. If the
     * entity is not a Hologram <code>null</code> is returned.
     *
     * @param bukkitEntity the Bukkit entity
     * @return the base entity
     */
    HologramEntity getHologramEntity(Entity bukkitEntity);

    /**
     * Gets the HeadController for that version
     *
     * @return
     */
    HeadController getHeadController();
}
