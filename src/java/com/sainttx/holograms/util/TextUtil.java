package com.sainttx.holograms.util;

import org.bukkit.ChatColor;

/**
 * Created by Matthew on 15/03/2015.
 */
public class TextUtil {

    /**
     * Returns a color-formatted String of the input
     *
     * @param text The input string
     * @return A formatted String
     */
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Implode a String array and combine into a string separated by a space
     *
     * @param toImplode A string array to implode
     * @return An imploded string joined by spaces
     */
    public static String implode(String[] toImplode) {
        return implode(0, toImplode);
    }

    /**
     * Implodes a String array and combines into a single string seprated by a space
     *
     * @param startIndex The start index of the String array to implode
     * @param toImplode  The String array to implode
     * @return An imploded string joined by spaces
     */
    public static String implode(int startIndex, String[] toImplode) {
        return implode(startIndex, toImplode, " ");
    }

    /**
     * Implode a string array from a starting index and combine into a string separated by a space
     *
     * @param startIndex The start index of the String array to implode
     * @param toImplode  A string array to implode
     * @param spacer     The string to seperate words
     * @return An imploded string joined by the spacer
     */
    public static String implode(int startIndex, String[] toImplode, String spacer) {
        StringBuilder ret = new StringBuilder();

        for (int i = startIndex ; i < toImplode.length ; i++) {
            if (toImplode[i] != null) {
                ret.append(toImplode[i]);
            }
            if (i < toImplode.length - 1) {
                ret.append(spacer);
            }
        }

        return color(ret.toString().trim());
    }
}
