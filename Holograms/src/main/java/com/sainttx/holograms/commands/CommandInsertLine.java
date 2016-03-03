package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.TextLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collection;

/**
 * Created by Matthew on 14/03/2015.
 */
public class CommandInsertLine implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandInsertLine(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram insertline <name> <index> <text>");
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

                Collection<HologramLine> lines = hologram.getLines();
                if (index < 0 || index > lines.size()) {
                    sender.sendMessage(ChatColor.RED + "Invalid index, must be between 0 and " + lines.size() + ".");
                } else {
                    String text = TextUtil.implode(3, args);
                    HologramLine line = new TextLine(hologram, text);
                    hologram.addLine(line, index);
                    hologram.refresh();
                    sender.sendMessage(TextUtil.color("&7Inserted line &f\"" + text + "&f\" &7into hologram &f\""
                            + hologram.getId() + "\" &7at index &f" + index + "."));
                }
            }
        }

        return true;
    }
}