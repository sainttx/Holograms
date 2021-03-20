package com.sainttx.holograms.api;

import com.sainttx.holograms.api.exception.UnsupportedMCVersionException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class BasicHeadController implements HeadController{
    private HologramEntityController controller;

    public BasicHeadController(HologramEntityController controller) {
        this.controller = controller;
    }

    @Override
    public ItemStack createHead(UUID uuid) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getBase64(ItemStack itemStack) {
        throw new UnsupportedMCVersionException("Base64 heads are not supported in "+controller.getMinecraftVersion().name());
    }

    @Override
    public UUID getUUID(ItemStack itemStack) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        return itemMeta.getOwningPlayer().getUniqueId();
    }

    @Override
    public ItemStack createBase64Head(String base64) {
        throw new UnsupportedMCVersionException("Base64 heads are not supported in "+controller.getMinecraftVersion().name());
    }
}
