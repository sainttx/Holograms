package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import org.bukkit.Location;

public interface HologramLine {

    /**
     * Represents a parser that transforms text into a valid HologramLine
     */
    interface Parser {

        /**
         * Returns whether or not the text is a valid representation
         * of the line type this parser is parsing for.
         *
         * @param text the provided text
         * @return <tt>true</tt> if the text is valid and can be parsed
         */
        boolean canParse(String text);

        /**
         * Parses a text validated with {@link #canParse(String)} and
         * returns a HologramLine created from the text.
         *
         * @param hologram the parent hologram to use in the creation of lines
         * @param text validated text
         * @return the parsed line
         */
        HologramLine parse(Hologram hologram, String text);
    }

    /**
     * The amount of space that should be added between lines
     * when spawning them on top of each other.
     */
    double SPACE_BETWEEN_LINES = 0.02;

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
     *
     * @throws IllegalStateException if the line is already hidden
     */
    void hide();

    /**
     * Re-adds this line to the hologram for display.
     *
     * @throws IllegalStateException if the line is already being shown
     */
    boolean show();

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

    /**
     * Returns the raw representation of this line. This string is what is
     * used when saving and loading hologram lines.
     *
     * @return the text representation
     */
    String getRaw();
}
