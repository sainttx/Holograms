package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.data.Hologram;
import com.sainttx.holograms.data.HologramLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Matthew on 14/03/2015.
 */
public class CommandRemoveLine implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandRemoveLine(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram removeline <name> <index>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologramByName(hologramName);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Couldn't find a hologram with name \"" + hologramName + "\".");
            } else {
                int index;
                try {
                    index = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + "Please enter a valid integer as your index.");
                    return true;
                }

                List<HologramLine> lines = hologram.getHologramLines();
                if (index < 0 || index >= lines.size()) {
                    sender.sendMessage(ChatColor.RED + "Invalid index, must be between 0 and " + (lines.size() - 1) + ".");
                } else {
                    HologramLine removed = hologram.removeLine(index);
                    hologram.refreshAll();
                    sender.sendMessage(TextUtil.color("&7Removed line &f\"" + removed.getText() + "&f\" &7from hologram &f\""
                            + hologram.getName() + "\"."));
                }
            }
        }

        return true;
    }
}