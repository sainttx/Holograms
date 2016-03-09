package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class CommandInfo implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandInfo(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram info <name>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Couldn't find a hologram with name \"" + hologramName + "\".");
            } else {
                sender.sendMessage(TextUtil.color("&7Hologram information for &f\"" + hologram.getId() + "\"&7:"));
                Collection<HologramLine> lines = hologram.getLines();
                sender.sendMessage(TextUtil.color("&7Location: &f" + TextUtil.locationAsString(hologram.getLocation())));
                if (lines.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "Hologram has no lines.");
                } else {
                    sender.sendMessage(ChatColor.GRAY + "Lines:");
                    for (HologramLine line : lines) {
                        sender.sendMessage(" - \"" + line.getRaw() + ChatColor.WHITE + "\"");
                    }
                }
            }
        }

        return true;
    }
}