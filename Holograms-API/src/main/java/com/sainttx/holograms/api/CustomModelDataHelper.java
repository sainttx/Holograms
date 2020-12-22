package com.sainttx.holograms.api;

import org.bukkit.inventory.meta.ItemMeta;

public interface CustomModelDataHelper {

    void setCustomModelData(ItemMeta itemMeta, int value) throws NoSuchMethodException;

    int getCustomModelData(ItemMeta itemMeta) throws NoSuchMethodException;
}
