package com.sainttx.holograms.util;

import com.sainttx.holograms.api.line.ItemLine;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;

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
     * Converts a Location into a String
     *
     * @param location A location to be converted
     * @return A string representation of the Location
     */
    public static String locationAsString(Location location) {
        return location.getWorld().getName() + " (x/y/z: " + formatDouble(location.getX()) + "/" + formatDouble(location.getY())
                + "/" + formatDouble(location.getZ()) + ")";
    }

    /**
     * Returns a formatted String representation of a double, rounded to 2
     * decimal places
     *
     * @param value The double to be formatted
     * @return A formatted string of the double
     */
    public static String formatDouble(double value) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(value);
    }

    /**
     * Parses an ItemStack from a string of text.
     *
     * @param text the text
     * @return the item
     * @throws NumberFormatException    when an invalid amount or durability are provided
     * @throws IllegalArgumentException when an invalid material or enchantment is provided
     */
    public static ItemStack parseItem(String text) {
        return ItemLine.parseItem(text);
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

        for (int i = startIndex; i < toImplode.length; i++) {
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
