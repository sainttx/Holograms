package com.sainttx.holograms.api;

import org.bukkit.Location;

public interface HologramLine {

    /**
     * Sets the location that this line should be displayed at.
     *
     * @param location the location
     */
    void setLocation(Location location);

    /**
     * Gets the current location where this line is displayed at.
     *
     * @return the location
     */
    Location getLocation();

    /**
     * Hides this line from being displayed by the hologram.
     */
    void hide();

    /**
     * Re-adds this line to the hologram for display.
     */
    void show();

    /**
     * Returns whether this line is hidden from being displayed by its
     * parent hologram.
     *
     * @return <tt>true</tt> if the line is hidden
     */
    boolean isHidden();

    /**
     * Returns the height of the line.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the parent {@link Hologram} that contains this line.
     *
     * @return the parent hologram
     */
    Hologram getHologram();
}
