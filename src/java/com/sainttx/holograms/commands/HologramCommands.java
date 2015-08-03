package com.sainttx.holograms.commands;

import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 14/03/2015.
 */
public class HologramCommands implements CommandExecutor {

    /*
     * The hologram plugin instance
     */
    private HologramPlugin plugin;

    /*
     * A map containing all commands for the Hologram plugin
     */
    private Map<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

    /**
     * Instantiates the Hologram command controller
     *
     * @param plugin The Hologram plugin instance
     */
    public HologramCommands(HologramPlugin plugin) {
        this.plugin = plugin;
        commands.put("addline", new CommandAddLine(plugin));
        commands.put("create", new CommandCreate(plugin));
        commands.put("delete", new CommandDelete(plugin));
        commands.put("import", new CommandImport(plugin));
        commands.put("info", new CommandInfo(plugin));
        commands.put("insertline", new CommandInsertLine(plugin));
        commands.put("list", new CommandList(plugin));
        commands.put("movehere", new CommandMoveHere(plugin));
        commands.put("near", new CommandNear(plugin));
        commands.put("removeline", new CommandRemoveLine(plugin));
        commands.put("refresh", new CommandRefresh(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendMenu(sender);
        } else {
            String subCommand = args[0].toLowerCase();

            if (!sender.hasPermission("holograms." + subCommand)) {
                sender.sendMessage(ChatColor.RED + command.getPermissionMessage());
            } else {
                CommandExecutor subCommandExec = commands.get(subCommand);

                if (subCommandExec == null) {
                    sendMenu(sender);
                } else {
                    return subCommandExec.onCommand(sender, command, label, args);
                }
            }
        }

        return false;
    }

    /*
     * Sends the command menu to a command sender
     */
    private void sendMenu(CommandSender sender) {
        sender.sendMessage(TextUtil.color("&7&m----------&7[ &eHolograms Help &7]&m----------"));
        sender.sendMessage(TextUtil.color("&e/holograms addline &f<name> <text>"));
        sender.sendMessage(TextUtil.color("&e/holograms create &f<name> <text>"));
        sender.sendMessage(TextUtil.color("&e/holograms delete &f<name>"));
        sender.sendMessage(TextUtil.color("&e/holograms import &f<plugin>"));
        sender.sendMessage(TextUtil.color("&e/holograms info &f<name>"));
        sender.sendMessage(TextUtil.color("&e/holograms insertline &f<name> <index> <text>"));
        sender.sendMessage(TextUtil.color("&e/holograms list"));
        sender.sendMessage(TextUtil.color("&e/holograms movehere &f<name>"));
        sender.sendMessage(TextUtil.color("&e/holograms near &f<radius>"));
        sender.sendMessage(TextUtil.color("&e/holograms removeline &f<name> <index>"));
        sender.sendMessage(TextUtil.color("&e/holograms refresh"));
        sender.sendMessage(TextUtil.color("&7&oHolograms v" + plugin.getDescription().getVersion() + " by SainttX"));
    }
}
