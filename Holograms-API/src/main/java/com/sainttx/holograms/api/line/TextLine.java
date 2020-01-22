package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.entity.Nameable;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class TextLine extends AbstractLine implements TextualHologramLine {

    private String text;
    private Nameable nameable;

    public TextLine(Hologram parent, String text) {
        this(parent, text, text);
    }

    TextLine(Hologram parent, String raw, String text) {
        super(parent, raw);
        this.text = ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
        if (!isHidden()) {
            nameable.getBukkitEntity().teleport(getLocation());
        }
    }

    @Override
    public void hide() {
        if (!isHidden()) {
            nameable.remove();
            nameable = null;
        }
    }

    @Override
    public boolean show() {
        if (isHidden()) {
            HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
            nameable = plugin.getEntityController().spawnNameable(this, getLocation());
            nameable.setName(text);
        }
        return true;
    }

    @Override
    public boolean isHidden() {
        return nameable == null;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        setRaw(text);
        nameable.setName(text);
    }

    @Override
    public double getHeight() {
        return 0.23;
    }
}
