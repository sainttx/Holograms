package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Matthew on 14/03/2015.
 */
public class CommandDelete implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandDelete(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}