package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.MinecraftVersion;
import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemLine extends AbstractLine implements ItemCarryingHologramLine {

    private static final Pattern linePattern = Pattern.compile("((item|icon|itemstack):)(.+)");
    private ItemStack item;
    private ItemHolder entity;

    public ItemLine(Hologram parent, String raw) {
        this(parent, raw, parseItem(raw));
    }

    public ItemLine(Hologram parent, ItemStack item) {
        this(parent, "item:" + itemstackToRaw(item), item);
    }

    ItemLine(Hologram parent, String raw, ItemStack item) {
        super(parent, raw);
        Validate.notNull(item, "Item cannot be null");
        this.item = item;
    }

    // Parses an itemstack from text
    private static ItemStack parseItem(String text) {
        Matcher matcher = linePattern.matcher(text);
        if (matcher.find()) {
            text = matcher.group(3);
        }
        String[] split = text.split(" ");
        short durability = -1;
        String data = split[0];

        if (data.contains(":")) {
            String[] datasplit = data.split(":");
            data = datasplit[0];
            durability = Short.parseShort(datasplit[1]);
        }
        Material material = Material.getMaterial(data.toUpperCase());

        // Throw exception if the material provided was wrong
        if (material == null) {
            throw new IllegalArgumentException("Invalid material " + data);
        }

        int amount;
        try {
            amount = split.length == 1 ? 1 : Integer.parseInt(split[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid amount \"" + split[1] + "\"", ex);
        }
        ItemStack item = new ItemStack(material, amount, (short) Math.max(0, durability));
        ItemMeta meta = item.getItemMeta();

        // No meta data was provided, we can return here
        if (split.length < 3) {
            return item;
        }

        // Go through all the item meta specified
        for (int i = 2 ; i < split.length ; i++) {
            String[] information = split[i].split(":");

            // Data, name, or lore has been specified
            switch (information[0].toLowerCase()) {
                case "name":
                    // Replace '_' with spaces
                    String name = information[1].replace(' ', ' ');
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                    break;
                case "lore":
                    // If lore was specified 2x for some reason, don't overwrite
                    List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                    String[] loreLines = information[1].split("\\|");

                    // Format all the lines and add them as lore
                    for (String line : loreLines) {
                        line = line.replace('_', ' '); // Replace '_' with space
                        lore.add(ChatColor.translateAlternateColorCodes('&', line));
                    }

                    meta.setLore(lore);
                    break;
                case "data":
                    short dataValue = Short.parseShort(information[1]);
                    item.setDurability(dataValue);
                default:
                    // Try parsing enchantment if it was nothing else
                    Enchantment ench = Enchantment.getByName(information[0].toUpperCase());
                    int level = Integer.parseInt(information[1]);

                    if (ench != null) {
                        meta.addEnchant(ench, level, true);
                    } else {
                        throw new IllegalArgumentException("Invalid enchantment " + information[0]);
                    }
                    break;
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    // Converts an ItemStack to raw representation
    static String itemstackToRaw(ItemStack itemstack) {
        StringBuilder sb = new StringBuilder();
        sb.append(itemstack.getType().toString()); // append type
        sb.append(':').append(itemstack.getDurability()); // append durability
        sb.append(' ').append(itemstack.getAmount()); // append amount

        if (itemstack.hasItemMeta()) {
            ItemMeta meta = itemstack.getItemMeta();

            // append name
            if (meta.hasDisplayName()) {
                sb.append(' ').append("name:").append(meta.getDisplayName().replace(' ', '_'));
            }

            // append lore
            if (meta.hasLore()) {
                sb.append(' ').append("lore:");
                Iterator<String> iterator = meta.getLore().iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next().replace(' ', '_'));
                    if (iterator.hasNext()) {
                        sb.append('|');
                    }
                }
            }

            // append enchantments
            meta.getEnchants().forEach((ench, level) -> {
                sb.append(' ').append(ench.getName()).append(':').append(level);
            });
        }

        return sb.toString();
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
