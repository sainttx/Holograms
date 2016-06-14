package com.sainttx.holograms.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

    /**
     * Converts a Location into a String
     *
     * @param location A location to be converted
     * @return A string representation of the Location
     */
    public static String locationAsString(Location location) {
        if(location == null) {
            return null;
        }
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY()
                + ";" + location.getZ() + ";" + location.getPitch() + ";" + location.getYaw();
    }

    /**
     * Converts a String into a Location
     *
     * @param string A String to be converted
     * @return The Location represented by the String, null if an invalid String was provided or
     * the world specified is not loaded
     */
    public static Location stringAsLocation(String string) {
        if(string == null) {
            return null;
        }
        String[] parts = string.split(";");

        if (parts.length >= 4) { // At least world, x, y, z specified
            World world = Bukkit.getWorld(parts[0]);

            if (world != null) {
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);

                Location location = new Location(world, x, y, z);
                if (parts.length == 6) { // Has a pitch & yaw
                    float pitch = Float.parseFloat(parts[4]);
                    float yaw = Float.parseFloat(parts[5]);
                    location.setYaw(yaw);
                    location.setPitch(pitch);
                }

                return location;
            }
        }

        return null;
    }
}
