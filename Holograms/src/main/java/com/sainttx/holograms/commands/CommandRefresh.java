package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.internal.HologramImpl;
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
        for (HologramImpl hologram : plugin.getHologramManager().getActiveHolograms().values()) {
            hologram.refresh();
        }

        sender.sendMessage(ChatColor.GREEN + "Refreshed all holograms");
        return true;
    }
}