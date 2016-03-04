package com.sainttx.holograms.api.entity;

import org.bukkit.inventory.ItemStack;

public interface ItemHolder extends HologramEntity {

    /**
     * Sets the item for this entity to display.
     *
     * @param item the new item
     */
    void setItem(ItemStack item);

    /**
     * Returns the current displayed item by this entity.
     *
     * @return the current item
     */
    ItemStack getItem();
}
