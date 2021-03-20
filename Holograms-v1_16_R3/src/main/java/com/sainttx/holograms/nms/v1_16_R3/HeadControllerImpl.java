package com.sainttx.holograms.nms.v1_16_R3;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sainttx.holograms.api.BasicHeadController;
import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.exception.HologramEntitySpawnException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

public class HeadControllerImpl extends BasicHeadController {
    public HeadControllerImpl(HologramEntityController controller) {
        super(controller);
    }

    @Override
    public ItemStack createBase64Head(String base64) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(),"" ) ;
        gameProfile.getProperties().put("textures", new Property("textures",base64));
        try {
            Field  profile = itemMeta.getClass().getDeclaredField("profile");
            profile.setAccessible(true);
            profile.set(itemMeta, gameProfile);
        } catch (Exception e) {
            throw new HologramEntitySpawnException("unable to modify gameprofile",e);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getBase64(ItemStack itemStack) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        try {
            Field  profile = itemMeta.getClass().getDeclaredField("profile");
            profile.setAccessible(true);
            GameProfile profil = (GameProfile) profile.get(itemMeta);
            Collection<Property> textures = profil.getProperties().get("textures");
            return textures.stream().findFirst().get().getValue();
        } catch (Exception e) {
            return "";
        }
    }
}
