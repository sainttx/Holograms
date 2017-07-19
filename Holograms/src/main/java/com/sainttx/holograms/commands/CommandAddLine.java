package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandAddLine implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandAddLine(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram addline <name> <text>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Couldn't find a hologram with name \"" + hologramName + "\".");
            } else {
                String text = TextUtil.implode(2, args);
                try {
                    HologramLine line = plugin.parseLine(hologram, text);
                    if (line instanceof ItemLine) {
                        sender.sendMessage(TextUtil.color("&cYou may need to relog or reload the area to properly view the item"));
                    }
                    hologram.addLine(line);
                    plugin.getHologramManager().saveHologram(hologram);
                } catch (Exception ex) {
                    sender.sendMessage(ChatColor.RED + "Error: " + ex.getMessage());
                    return true;
                }
                sender.sendMessage(TextUtil.color("&7Added line &f\"" + text + "\" &7to hologram &f\"" + hologram.getId() + "&f\"."));
            }
        }

        return true;
    }
}
