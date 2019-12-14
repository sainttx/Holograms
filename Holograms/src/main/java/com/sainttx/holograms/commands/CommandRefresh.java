package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRefresh implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandRefresh(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        HologramManager manager = plugin.getHologramManager();

        for (Hologram hologram : manager.getActiveHolograms().values()) {
            hologram.despawn();
            hologram.spawn();
        }

        sender.sendMessage(ChatColor.GREEN + "Refreshed all holograms!");
        return true;
    }
}