package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.data.Configuration;
import com.sainttx.holograms.internal.HologramImpl;
import com.sainttx.holograms.util.TextUtil;
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

/**
 * Created by Matthew on 16/03/2015.
 */
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
                    sender.sendMessage(TextUtil.color("&7&oImporting holograms from HolographicDisplays..."));
                    YamlConfiguration config = Configuration.loadConfiguration(holoDatabase);
                    Random random = new Random();

                    for (String holoName : config.getKeys(false)) {
                        try {
                            Location location = convertHologramLocation(config.getString(holoName + ".location"));

                            if (location == null) {
                                sender.sendMessage(TextUtil.color("&c&oHologram \"" + holoName + "\" has no location and was skipped"));
                            } else {
                                List<String> lines = config.getStringList(holoName + ".lines");

                                if (lines == null || lines.isEmpty()) {
                                    sender.sendMessage(TextUtil.color("&c&oHologram \"" + holoName + "\" has no lines and was skipped"));
                                    continue;
                                } else if (plugin.getHologramManager().getHologramByName(holoName) != null) {
                                    int randomInt = random.nextInt(1000);
                                    sender.sendMessage(TextUtil.color("&e&oHologram \"" + holoName + "\" was renamed to \"" + (holoName + randomInt) + "\" (already existed)"));
                                    holoName = holoName + randomInt;
                                }

                                HologramImpl hologram = new HologramImpl(holoName, location, true, lines.toArray(new String[lines.size()]));
                                hologram.refresh();
                                sender.sendMessage(TextUtil.color("&a&oSuccessfully converted HolographicDisplays hologram \"" + holoName + "\"."));
                            }
                        } catch (Exception ex) {
                            sender.sendMessage(TextUtil.color("&c&oFailed to convert hologram \"" + holoName + "\", check your CONSOLE for information."));
                            plugin.getLogger().log(Level.SEVERE, "Failed to convert hologram \"" + holoName + "\"", ex);
                        }
                    }
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
