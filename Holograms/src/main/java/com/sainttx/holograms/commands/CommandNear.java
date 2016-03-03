package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandNear implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandNear(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can view nearby holograms, use /hologram list instead.");
        } else if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram near <radius>");
        } else {
            Double radius = null;

            try {
                radius = Double.parseDouble(args[1]);
            } catch (NumberFormatException ex) { /* Handled later */ }

            if (radius == null || Double.isInfinite(radius) || Double.isNaN(radius) || radius < 0) {
                sender.sendMessage(ChatColor.RED + "Please enter a valid number as your radius.");
                return true;
            }

            Player player = (Player) sender;
            HologramManager manager = plugin.getHologramManager();
            Map<Hologram, Double> nearby = new HashMap<>();
            for (Hologram hologram : manager.getActiveHolograms().values()) {
                if (hologram.getLocation().getWorld().equals(player.getWorld())) {
                    double distance = hologram.getLocation().distance(player.getLocation());
                    if (distance <= radius) {
                        nearby.put(hologram, distance);
                    }
                }
            }

            if (nearby.isEmpty()) {
                sender.sendMessage(ChatColor.RED + "There are no nearby holograms within radius " + radius);
            } else {
                sender.sendMessage(TextUtil.color("&7Holograms within a radius of &f" + radius + "&7:"));
                for (Map.Entry<Hologram, Double> near : nearby.entrySet()) {
                    Hologram holo = near.getKey();
                    sender.sendMessage(" - \"" + holo.getId() + "\" at " + TextUtil.locationAsString(holo.getLocation()) + " (dist: " + TextUtil.formatDouble(near.getValue()) + ")");
                }
            }
        }

        return true;
    }
}