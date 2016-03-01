package com.sainttx.holograms.api;

import org.bukkit.Location;

/**
 * Created by Matthew on 12/14/2015.
 */
public interface HologramLine {

    /**
     * Spawns this line at a specific {@link Location}.
     *
     * @param location where to spawn
     */
    void spawn(Location location);

    /**
     * Despawns this line.
     */
    void despawn();

    /**
     * Returns the height of the line.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the {@link NMSEntityBase} being used to display this line.
     *
     * @return the entity
     */
    NMSEntityBase getEntity();

    /**
     * Returns the parent {@link Hologram} that contains this line.
     *
     * @return the parent hologram
     */
    Hologram getHologram();

    /**
     * Represents a line that displays a simple string.
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
