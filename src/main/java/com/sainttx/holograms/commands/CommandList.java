package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramManager;
import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.data.Hologram;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Matthew on 14/03/2015.
 */
public class CommandList implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandList(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        HologramManager manager = plugin.getHologramManager();

        sender.sendMessage(ChatColor.GRAY + "All holograms:");
        for (Hologram hologram : manager.getActiveHolograms().values()) {
            int lines = hologram.getHologramLines().size();
            sender.sendMessage(" - \"" + hologram.getName() + "\" at " + TextUtil.locationAsString(hologram.getLocation()) + " (" + lines + " lines)");
        }
        return false;
    }
}