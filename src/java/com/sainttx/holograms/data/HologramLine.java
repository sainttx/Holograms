package com.sainttx.holograms.data;

import com.sainttx.holograms.nms.EntityNMSArmorStand;
import com.sainttx.holograms.nms.NMSController;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Created by Matthew on 08/01/2015.
 */
public class HologramLine {

    /**
     * Y offset used in the position of the NMS entities
     */
    public static final double OFFSET = -1.25;

    /*
     * The height of the line
     */
    public static final double height = 0.23;

    /*
     * The host Hologram of this line
     */
    private final Hologram parent;

    /*
     * The String of text that this HologramLine contains
     */
    private String text;

    /*
     * The String of text unformatted
     */
    private String originalText;

    /*
     * The Entity with the custom name
     */
    private EntityNMSArmorStand nmsNameable;

    /*
     * The Entity that the nameable is riding on
     */
    private EntityNMSArmorStand nmsRideable;

    /**
     * Creates a HologramLine with a parent Hologram and a String of text
     *
     * @param parent The Hologram hosting this line of text
     * @param text   The line of text that is held by this object
     */
    public HologramLine(Hologram parent, String text) {
        this.parent = parent;
        this.text = ChatColor.translateAlternateColorCodes('&', text);
        this.originalText = text;
    }

    /**
     * Spawns this line at a Location
     *
     * @param location The location to spawn at
     */
    public void spawn(Location location) {
        despawn();

        // Spawn the entities and set the horse and the skulls passenger
        nmsNameable = NMSController.spawnArmorStand(location.getWorld(), location.getX(), location.getY() + OFFSET, location.getZ(), this);

        // Set the text held by this object
        if (text != null && !text.isEmpty()) {
            nmsNameable.setCustomNameNMS(text);
        }

        nmsNameable.setLockTick(true);
    }

    /**
     * Kills this HologramLine
     */
    public void despawn() {
        if (nmsRideable != null) {
            nmsRideable.die();
            nmsRideable = null;
        }

        if (nmsNameable != null) {
            nmsNameable.die();
            nmsNameable = null;
        }
    }

    /**
     * Returns the String of text that this HologramLine carries
     *
     * @return The text displayed on this line
     */
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

    /**
     * Changes the text that this HologramLine carries
     *
     * @param text The new text to show
     */
    public void setText(String text) {
        this.text = text;
    }

    public final double getHeight() {
        return height;
    }

    public final Hologram getParent() {
        return parent;
    }
}
