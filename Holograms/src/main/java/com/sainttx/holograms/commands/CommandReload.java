package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReload implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandReload(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.getHologramManager().reload();
        sender.sendMessage(ChatColor.GREEN + "Reloaded all holograms");
        return true;
    }
}