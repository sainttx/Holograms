package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.HeadController;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.entity.ItemHolder;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadLine  extends AbstractLine implements ItemCarryingHologramLine{

    private static final Pattern linePattern = Pattern.compile("((head):)(.+)");
    private ItemStack item;
    private ItemHolder entity;

    public HeadLine(Hologram parent, String raw) {
        this(parent, raw, parseItem(raw));
    }

    public HeadLine(Hologram parent, ItemStack item) {
        this(parent, "head:" + itemstackToRaw(item), item);
    }

    HeadLine(Hologram parent, String raw, ItemStack item) {
        super(parent, raw);
        Validate.notNull(item, "Item cannot be null");
        this.item = item;
    }
    //head:{UUID} or head:{base64}
    // Parses an head from text
    public static ItemStack parseItem(String text) {
        Matcher matcher = linePattern.matcher(text);
        if (matcher.find()) {
            text = matcher.group(3);
        }
        String[] split = text.split(" ");
        String data = split[0];

        if (data.contains(":")) {
            String[] datasplit = data.split(":");
            data = datasplit[0];
        }
        HeadController headController = HologramPlugin.getHologramPlugin().getEntityController().getHeadController();
        if(data.length() == 36 || data.length()==40){
            return headController.createHead(UUID.fromString(data));
        }else{
            return headController.createBase64Head(data);
        }
    }

    // Converts an ItemStack to raw representation
    public static String itemstackToRaw(ItemStack itemstack) {
        HeadController headController = HologramPlugin.getHologramPlugin().getEntityController().getHeadController();
        String base64 = headController.getBase64(itemstack);
        if(!base64.isEmpty()){
            return base64;
        }else{
            return headController.getUUID(itemstack).toString();
        }
    }

    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
        if (entity != null) {
            entity.setPosition(location.getX(), location.getY(), location.getZ());
        }
    }

    @Override
    public void hide() {
        if (!isHidden()) {
            entity.setMount(null);
            entity.remove();
            entity = null;
        }
    }

    @Override
    public boolean show() {
        if (isHidden()) {
            HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
            entity = plugin.getEntityController().spawnItemHolder(this, getLocation(), item);
        }
        return true;
    }

    @Override
    public boolean isHidden() {
        return entity == null;
    }

    @Override
    public ItemStack getItem() {
        return item.clone();
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item.clone();
        entity.setItem(this.item);
    }

    @Override
    public double getHeight() {
        return 0.5;
    }

}
