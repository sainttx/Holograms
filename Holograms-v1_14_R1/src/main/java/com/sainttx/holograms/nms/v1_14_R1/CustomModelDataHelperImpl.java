package com.sainttx.holograms.nms.v1_14_R1;

import com.sainttx.holograms.api.CustomModelDataHelper;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomModelDataHelperImpl implements CustomModelDataHelper {

    public void setCustomModelData(ItemMeta itemMeta, int value) throws NoSuchMethodException {
        itemMeta.setCustomModelData(value);
    }

    public int getCustomModelData(ItemMeta itemMeta) throws NoSuchMethodException {
        return itemMeta.getCustomModelData();
    }
}
