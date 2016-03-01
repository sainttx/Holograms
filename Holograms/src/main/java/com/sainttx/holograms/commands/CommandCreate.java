package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.internal.HologramImpl;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew on 14/03/2015.
 */
public class CommandCreate implements CommandExecutor {

    private HologramPlugin plugin;

    public CommandCreate(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can create holograms.");
        } else if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /hologram create <name> <text>");
        } else {
            String hologramName = args[1];
            Hologram hologram = plugin.getHologramManager().getHologramByName(hologramName);

            if (hologram != null) {
                sender.sendMessage(ChatColor.RED + "A hologram with that name already exists.");
            } else {
                Player player = (Player) sender;
                String text = TextUtil.implode(2, args);
                HologramImpl newHologram = new HologramImpl(hologramName, player.getLocation(), true, text);
                newHologram.refresh();
                sender.sendMessage(TextUtil.color("&7Created a new hologram &f\"" + hologramName + "\" &7with line &f\"" + text + "&f\"."));
            }
        }

        return true;
    }
}