package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.TextualHologramLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSetLine implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandSetLine(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram setline <name> <index> <text>");
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
                    String text = TextUtil.implode(3, args);
                    HologramLine settingLine = plugin.parseLine(hologram, text);
                    if (line instanceof ItemLine) {
                        sender.sendMessage(TextUtil.color("&cYou may need to relog or reload the area to properly view the item"));
                    }
                    hologram.removeLine(line);
                    hologram.addLine(settingLine, index);
                    plugin.getServer().getScheduler().runTaskLater(plugin, hologram::reorganize, 10L);
                    plugin.getHologramManager().saveHologram(hologram);
                    sender.sendMessage(TextUtil.color("&7Updated line at index &f" + index + " &7of hologram &f\""
                        + hologram.getId() + "\""));
                }
            }
        }

        return true;
    }
}