package com.sainttx.holograms.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class HologramCommand {
    private HologramPlugin plugin;
    public HologramCommand(HologramPlugin hologramPlugin) {
this.plugin = hologramPlugin;
    }

    @CommandMethod("hologram info <name>")
    @CommandDescription("Gives information about Hologram")
    @CommandPermission("holograms.delete")
    private void commandDelete(CommandSender sender, @Argument("name") String hologramName) {
        Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

        if (hologram == null) {
            sender.sendMessage(ChatColor.RED + "Hologram " + hologramName + " does not exist");
        } else {
            plugin.getHologramManager().deleteHologram(hologram);
            sender.sendMessage(ChatColor.GREEN + "Deleted hologram " + hologram.getId());
        }

    }

    @CommandMethod("hologram info <name>")
    @CommandDescription("Gives information about Hologram")
    @CommandPermission("holograms.info")
    private void commandInfo(CommandSender sender, @Argument("name") String hologramName) {
        Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

        if (hologram == null) {
            sender.sendMessage(ChatColor.RED + "Hologram " + hologramName + " does not exist");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Hologram \"" + hologram.getId() + "\"");
            Collection<HologramLine> lines = hologram.getLines();
            sender.sendMessage(ChatColor.GRAY + "Location: " + ChatColor.WHITE + TextUtil.locationAsString(hologram.getLocation()));
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


    @CommandMethod("hologram list")
    @CommandDescription("Lists available holograms")
    @CommandPermission("holograms.list")
    private void commandList(CommandSender sender) {
        HologramManager manager = plugin.getHologramManager();

        sender.sendMessage(ChatColor.GREEN + "All holograms:");
        for (Hologram hologram : manager.getActiveHolograms().values()) {
            int lines = hologram.getLines().size();
            sender.sendMessage(" - \"" + hologram.getId() + "\" at " + TextUtil.locationAsString(hologram.getLocation()) + " (" + lines + " lines)");
        }
    }

}
