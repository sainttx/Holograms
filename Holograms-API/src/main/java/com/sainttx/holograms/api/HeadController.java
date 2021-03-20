package com.sainttx.holograms.api;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface HeadController {
    /**
     * Creates a head using a base64 texture
     * @param base64 base64 texture
     * @return the ItemStack
     */
    ItemStack createBase64Head(String base64);
    /**
     * Creates a head with a set UUID
     * @param uuid the uuid
     * @return the ItemStack
     */
    ItemStack createHead(UUID uuid);

    /**
     * Gets the base 64 of the itemStack
     * @param itemStack the item stack
     * @return the base64
     */
    String getBase64(ItemStack itemStack);

    /**
     * Gets the UUID from the ItemStack
     * @param itemStack the item stack
     * @return the uuid.
     */
    UUID getUUID(ItemStack itemStack);
}
