package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramEntity;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class TextLine implements TextualHologramLine {

    private Hologram parent;
    private Location location;
    private String text;
    private HologramEntity nmsNameable;

    public TextLine(Hologram parent, String text) {
        this.parent = parent;
        this.location = parent.getLocation(); // TODO: Should this be the behavior?
        this.text = text == null ? null : ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location.clone();
        if (!isHidden()) {
            nmsNameable.getBukkitEntity().teleport(location); // TODO: Stuff later
        }
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    @Override
    public void hide() {
        if (isHidden()) {
            throw new IllegalStateException("This hologram line is already hidden");
        }
        nmsNameable.die();
        nmsNameable = null;
    }

    @Override
    public void show() {
        if (!isHidden()) {
            throw new IllegalStateException("This hologram line is already being displayed");
        }
        HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        nmsNameable = plugin.getNMSController().spawnHologram(location.getWorld(), location.getX(), location.getY(), location.getZ(), this);
        nmsNameable.setCustomName(text);
        // TODO: lock tick
    }

    @Override
    public boolean isHidden() {
        return nmsNameable == null; // TODO: Could set the name to null
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        nmsNameable.setCustomName(text);
    }

    @Override
    public final double getHeight() {
        return 0.23;
    }

    @Override
    public final Hologram getHologram() {
        return parent;
    }
}
