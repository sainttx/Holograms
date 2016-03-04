package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.entity.Nameable;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class TextLine implements TextualHologramLine {

    private Hologram parent;
    private Location location;
    private String text;
    private Nameable nameable;

    public TextLine(Hologram parent, String text) {
        this.parent = parent;
        this.location = parent.getLocation(); // TODO: Should this be the behavior?
        this.text = text == null ? null : ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location.clone();
        if (!isHidden()) {
            nameable.getBukkitEntity().teleport(location); // TODO: Stuff later
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
        nameable.remove();
        nameable = null;
    }

    @Override
    public boolean show() {
        if (!isHidden()) {
            throw new IllegalStateException("This hologram line is already being displayed");
        }
        HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        nameable = plugin.getEntityController().spawnNameable(this, getLocation());
        if (nameable == null) {
            return false;
        }
        nameable.setName(text);
        return true;
    }

    @Override
    public boolean isHidden() {
        return nameable == null; // TODO: Could set the name to null
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        nameable.setName(text);
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
