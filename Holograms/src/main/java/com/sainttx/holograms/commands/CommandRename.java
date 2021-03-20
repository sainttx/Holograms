package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRename implements CommandExecutor {
    private HologramPlugin plugin;

    public CommandRename(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram rename <name> <new_name>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologram(hologramName);

            if (hologram == null) {
                sender.sendMessage(ChatColor.RED + "Hologram " + hologramName + " does not exist");
            } else {
                Hologram holo = new Hologram(args[2], hologram.getLocation(), true);
                for (HologramLine line : hologram.getLines()) {
                    holo.addLine(line);
                }
                plugin.getHologramManager().deleteHologram(hologram);
                holo.spawn();
                plugin.getHologramManager().addActiveHologram(holo);
                plugin.getHologramManager().saveHologram(holo);
                sender.sendMessage(ChatColor.GREEN + "Renamed Hologram " + hologramName + " to " + holo.getId());
            }
        }

        return true;
    }
}
