package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemLine extends AbstractLine implements ItemCarryingHologramLine {

    public static class Parser implements HologramLine.Parser {

        private Pattern linePattern = Pattern.compile("((item|icon|itemstack):)(.+)");

        @Override
        public boolean canParse(String text) {
            return linePattern.matcher(text).matches();
        }

        @Override
        public HologramLine parse(Hologram hologram, String text) {
            Matcher matcher = linePattern.matcher(text);
            if (matcher.find()) {
                text = matcher.group(3);
            } else {
                throw new IllegalArgumentException("Invalid item provided");
            }
            String[] split = text.split(" ");
            short durability = -1;
            String data = split[0];

            if (data.contains(":")) {
                String[] datasplit = data.split(":");
                data = datasplit[0];
                durability = Short.parseShort(datasplit[1]);
            }

            Material material = data.matches("[0-9]+")
                    ? Material.getMaterial(Integer.parseInt(data))
                    : Material.getMaterial(data.toUpperCase());

            // Throw exception if the material provided was wrong
            if (material == null) {
                throw new IllegalArgumentException("Invalid material " + data);
            }

            int amount;
            try {
                amount = split.length == 1 ? 1 : Integer.parseInt(split[1]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid amount \""+ split[1] +"\"", ex);
            }
            ItemStack item = new ItemStack(material, amount, (short) Math.max(0, durability));
            ItemMeta meta = item.getItemMeta();

            // No meta data was provided, we can return here
            if (split.length < 3) {
                return new ItemLine(hologram, item);
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

            // Set the meta and return created item line
            item.setItemMeta(meta);
            return new ItemLine(hologram, item);
        }
    }

    private ItemStack item;
    private ItemHolder entity;

    public ItemLine(Hologram parent, ItemStack item) {
        super(parent);
        Validate.notNull(item, "Item cannot be null");
        this.item = item;
    }

    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
        if (!isHidden()) {
            entity.setMount(null);
            entity.getBukkitEntity().teleport(getLocation());
            entity.setMount(createMount());
        }
    }

    @Override
    public void hide() {
        if (isHidden()) {
            throw new IllegalStateException("This hologram line is already hidden");
        }
        entity.setMount(null);
        entity.remove();
        entity = null;
    }

    @Override
    public boolean show() {
        if (!isHidden()) {
            throw new IllegalStateException("This hologram line is already being displayed");
        }
        HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        entity = plugin.getEntityController().spawnItemHolder(this, getLocation());
        if (entity == null) {
            return false;
        }
        entity.setItem(item);
        entity.setMount(createMount());
        return true;
    }

    // Creates a new mount entity
    private HologramEntity createMount() {
        HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        return plugin.getEntityController().spawnNameable(this, getLocation());
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
    public void setItem(ItemStack text) {
        this.item = item.clone();
        entity.setItem(this.item);
    }

    @Override
    public double getHeight() {
        HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        switch (plugin.getEntityController().getMinecraftVersion()) {
            case V1_8_R1:
                return 0.8;
            default:
                return 0.4;
        }
    }
}
