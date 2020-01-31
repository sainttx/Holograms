package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeleport implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandTeleport(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can teleport to holograms.");
        } else if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram teleport <name>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Hologram " + hologramName + " does not exist");
            } else {
                Player player = (Player) sender;
                player.teleport(hologram.getLocation());
                sender.sendMessage(ChatColor.GREEN + "Teleported to hologram " + hologramName);
            }
        }

        return true;
    }
}