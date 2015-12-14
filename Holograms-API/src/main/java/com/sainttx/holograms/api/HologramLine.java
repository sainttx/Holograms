package com.sainttx.holograms.api;

import org.bukkit.Location;

/**
 * Created by Matthew on 12/14/2015.
 */
public interface HologramLine {

    /**
     * Displays the line at a specific location
     *
     * TODO: IllegalStateException if already spawned, rename to display
     *
     * @param location the location to spawn the Hologram at
     */
    void spawn(Location location);

    /**
     * Hides this hologram line from being shown
     */
    void despawn();

    /**
     * Returns the height of the line
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the entity that is used to display this line
     *
     * @return the entity
     */
    NMSEntityBase getEntity();

    /**
     * Returns the parent hologram that contains this line
     *
     * @return the parent hologram
     */
    Hologram getHologram();

    /**
     * Represents a Hologram line that displays a string to players
     */
    interface Textual extends HologramLine {

        /**
         * Returns the text displayed by the line
         *
         * @return the text
         */
        String getText();

        /**
         * Sets the text to be displayed by the line
         *
         * @param text the new text
         */
        void setText(String text);
    }
}
