package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.entity.Nameable;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class TextLine extends AbstractLine implements TextualHologramLine {

    public static class Parser implements HologramLine.Parser {

        @Override
        public boolean canParse(String text) {
            return false;
        }

        @Override
        public HologramLine parse(String text) {
            return null;
        }
    }

    private String text;
    private Nameable nameable;

    public TextLine(Hologram parent, String text) {
        super(parent);
        Validate.notNull(text, "Text cannot be null");
        this.text = text == null ? null : ChatColor.translateAlternateColorCodes('&', text);
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
        return nameable == null;
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
    public double getHeight() {
        return 0.23;
    }
}
