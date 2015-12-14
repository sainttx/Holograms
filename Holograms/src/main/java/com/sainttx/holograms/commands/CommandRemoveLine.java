package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.internal.HologramImpl;
import com.sainttx.holograms.internal.HologramLineImpl;
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
            HologramImpl hologram = plugin.getHologramManager().getHologramByName(hologramName);

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
                    hologram.refresh();
                    if (line instanceof HologramLine.Textual) {
                        sender.sendMessage(TextUtil.color("&7Removed line &f\"" + ((HologramLine.Textual) line).getText()
                                + "&f\" &7from hologram &f\"" + hologram.getName() + "\"."));
                    } else {
                        sender.sendMessage(TextUtil.color("&7Removed line at position &f\"" + index + "&f\" &7from hologram &f\""
                                + hologram.getName() + "\"."));
                    }
                }
            }
        }

        return true;
    }
}