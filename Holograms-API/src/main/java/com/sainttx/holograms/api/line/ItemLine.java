package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemLine extends AbstractLine implements ItemCarryingHologramLine {

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
        return 0.23;
    }
}
