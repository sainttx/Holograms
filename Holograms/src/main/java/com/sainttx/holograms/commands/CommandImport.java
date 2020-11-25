package com.sainttx.holograms.commands;

import com.sainttx.holograms.Configuration;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class CommandImport implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandImport(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram import <plugin>");
        } else {
            String toImport = args[1];

            if (!toImport.equalsIgnoreCase("holographicdisplays")) {
                sender.sendMessage(ChatColor.RED + "Valid plugins to import: HolographicDisplays");
            } else {
                File holoDatabase = new File(plugin.getDataFolder().getParentFile(), "HolographicDisplays" + File.separator + "database.yml");

                if (!holoDatabase.exists()) {
                    sender.sendMessage(ChatColor.RED + "File \"../plugins/HolographicDisplays/database.yml\" couldn't be found");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "Importing HolographicDisplays holograms...");
                    YamlConfiguration config = Configuration.loadConfiguration(holoDatabase);
                    Random random = new Random();

                    for (String holoName : config.getKeys(false)) {
                        try {
                            Location location = convertHologramLocation(config.getString(holoName + ".location"));

                            if (location == null) {
                                sender.sendMessage(ChatColor.RED + "Hologram " + holoName + " has no location (skipping)");
                            } else {
                                List<String> lines = config.getStringList(holoName + ".lines");

                                if (lines == null || lines.isEmpty()) {
                                    sender.sendMessage(ChatColor.RED + "Holgoram " + holoName + " has no lines (skipping)");
                                    continue;
                                } else if (plugin.getHologramManager().getHologram(holoName) != null) {
                                    int randomInt = random.nextInt(1000);
                                    sender.sendMessage(ChatColor.GOLD + "Hologram " + holoName + " was renamed to " + (holoName + randomInt) + " (already existed)");
                                    holoName = holoName + randomInt;
                                }

                                Hologram hologram = new Hologram(holoName, location, true);
                                for (String text : lines) {
                                    try {
                                        hologram.addLine(plugin.parseLine(hologram, text));
                                    } catch (Exception ex) {
                                        plugin.getLogger().log(Level.SEVERE, "Attempted to parse invalid hologram line", ex);
                                    }
                                }
                                plugin.getHologramManager().addActiveHologram(hologram);
                                plugin.getHologramManager().saveHologram(hologram);
                                sender.sendMessage(ChatColor.YELLOW + "Converted HolographicDisplays hologram " + holoName);
                            }
                        } catch (Exception ex) {
                            sender.sendMessage(ChatColor.DARK_RED + "Failed to convert hologram " + holoName + ", see logs for details");
                            plugin.getLogger().log(Level.SEVERE, "Failed to convert hologram \"" + holoName + "\"", ex);
                        }
                    }

                    sender.sendMessage(ChatColor.GREEN + "Import complete!");
                }
            }
        }

        return true;
    }

    /*
     * Converts a HolographicDisplays location String to a Bukkit Location
     */
    private Location convertHologramLocation(String location) {
        if (location != null) {
            String[] pieces = location.split(",");
            World world = Bukkit.getWorld(pieces[0]);
            double x = Double.parseDouble(pieces[1]);
            double y = Double.parseDouble(pieces[2]);
            double z = Double.parseDouble(pieces[3]);

            return new Location(world, x, y, z);
        }

        return null;
    }
}
