package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramEntity;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class TextLine implements TextualHologramLine {

    private static final double OFFSET = 0; // Y offset used in the position of the NMS entities
    private static final double height = 0.23; // Height of the line

    private Hologram parent;
    private String text;
    private String originalText;
    private HologramEntity nmsNameable;

    public TextLine(Hologram parent, String text) {
        this.parent = parent;
        this.text = text == null ? null : ChatColor.translateAlternateColorCodes('&', text);
        this.originalText = text;
    }

    @Override
    public void spawn(Location location) {
        despawn();

        // Spawn the entities and set the horse and the skulls passenger
        HologramPlugin plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        nmsNameable = plugin.getNMSController().spawnHologram(location.getWorld(), location.getX(), location.getY() + OFFSET, location.getZ(), this);

        // Set the text held by this object
        if (text != null && !text.isEmpty()) {
            nmsNameable.setCustomName(text);
        }

        nmsNameable.setLockTick(true);
    }

    @Override
    public HologramEntity getEntity() {
        return nmsNameable;
    }

    @Override
    public void despawn() {
        if (nmsNameable != null) {
            nmsNameable.die();
            nmsNameable = null;
        }
    }

    @Override
    public String getText() {
        return this.text;
    }

    /**
     * Returns the String of text this line contains (unformatted)
     *
     * @return The text pre-formatting that is displayed by this line
     */
    public String getOriginalText() {
        return this.originalText;
    }

    @Override
    public void setText(String text) {
        this.text = ChatColor.translateAlternateColorCodes('&', text);
        this.originalText = text;
    }

    @Override
    public final double getHeight() {
        return height;
    }

    @Override
    public final Hologram getHologram() {
        return parent;
    }
}
