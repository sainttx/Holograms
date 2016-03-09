package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.TextualHologramLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

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

                if (index < 0 || index >= hologram.getLines().size()) {
                    sender.sendMessage(ChatColor.RED + "Invalid index, must be between 0 and " + (hologram.getLines().size() - 1) + ".");
                } else {
                    HologramLine line = hologram.getLine(index);
                    hologram.removeLine(line);
                    if (line instanceof TextualHologramLine) {
                        sender.sendMessage(TextUtil.color("&7Removed line &f\"" + ((TextualHologramLine) line).getText()
                                + "&f\" &7from hologram &f\"" + hologram.getId() + "\"."));
                    } else {
                        sender.sendMessage(TextUtil.color("&7Removed line at position &f\"" + index + "&f\" &7from hologram &f\""
                                + hologram.getId() + "\"."));
                    }

                    if (hologram.getLines().size() == 0) {
                        plugin.getHologramManager().deleteHologram(hologram);
                        sender.sendMessage(TextUtil.color("&7Hologram &f\"" + hologram.getId() + "\" &7was removed."));
                    }
                }
            }
        }

        return true;
    }
}