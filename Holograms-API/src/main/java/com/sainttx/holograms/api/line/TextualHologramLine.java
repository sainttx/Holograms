package com.sainttx.holograms.api.line;

/**
 * Represents a {@link HologramLine} that only displays a single line of text.
 */
public interface TextualHologramLine extends HologramLine {

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
