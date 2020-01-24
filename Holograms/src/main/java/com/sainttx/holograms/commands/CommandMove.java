package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMove implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandMove(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can move holograms.");
        } else if (args.length < 6) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram move <name> <world> <x> <y> <z>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);
            World world = plugin.getServer().getWorld(args[2]);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Hologram " + hologramName + " does not exist");
            } else if (world == null) {
                sender.sendMessage(ChatColor.RED + "World " + args[2] + " does not exist");
            } else {
                try {
                    double x = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);
                    double z = Double.parseDouble(args[5]);
                    Location location = new Location(world, x, y, z);
                    hologram.despawn();
                    hologram.teleport(location);
                    hologram.spawn();
                    plugin.getHologramManager().saveHologram(hologram);
                    sender.sendMessage(ChatColor.GREEN + "Moved " + hologramName + " to "
                            + x + "/" + y + "/" + z + " in world " + world.getName());
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "One of your x/y/z coordinates was invalid");
                }
            }
        }

        return true;
    }
}