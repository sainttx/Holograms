package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Matthew on 8/3/2015.
 */
public class CommandRefresh implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandRefresh(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (Hologram hologram : plugin.getHologramManager().getActiveHolograms().values()) {
            hologram.refresh();
        }

        sender.sendMessage(ChatColor.GREEN + "Refreshed all holograms");
        return true;
    }
}