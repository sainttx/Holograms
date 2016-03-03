package com.sainttx.holograms.util;

import org.bukkit.Bukkit;

public class ReflectionUtil {

    /*
     * The server version string to location NMS & OBC classes
     */
    private static String versionString;

    /**
     * Gets the version string for NMS & OBC class paths
     *
     * @return The version string of OBC and NMS packages
     */
    public static String getVersion() {
        if (versionString == null) {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            versionString = name.substring(name.lastIndexOf('.') + 1) + ".";
        }

        return versionString;
    }
}