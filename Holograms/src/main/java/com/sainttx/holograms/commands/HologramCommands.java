package com.sainttx.holograms.commands;

import com.sainttx.holograms.api.HologramPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;
import java.util.stream.Collectors;

public class HologramCommands implements CommandExecutor, TabExecutor {

    /*
     * The hologram plugin instance
     */
    private final HologramPlugin plugin;

    /*
     * A map containing all commands for the Hologram plugin
     */
    private final Map<String, CommandExecutor> commands = new HashMap<>();

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
        commands.put("reload", new CommandReload(plugin));
        commands.put("setline", new CommandSetLine(plugin));
        CommandTeleport teleportCommand = new CommandTeleport(plugin);
        commands.put("teleport", teleportCommand);
        commands.put("tp", teleportCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendMenu(sender);
        } else {
            String subCommand = args[0].toLowerCase();
            CommandExecutor subCommandExec = commands.get(subCommand);

            if (subCommandExec == null) {
                sendMenu(sender);
            } else if (!sender.hasPermission("holograms." + subCommand)) {
                sender.sendMessage(ChatColor.RED + command.getPermissionMessage());
            } else {
                return subCommandExec.onCommand(sender, command, label, args);
            }
        }

        return false;
    }

    /*
     * Sends the command menu to a command sender
     */
    private void sendMenu(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "------[ " + ChatColor.WHITE + "Holograms v" + plugin.getDescription().getVersion() + " - SainttX " + ChatColor.GRAY + "]------");
        sender.sendMessage(ChatColor.YELLOW + "/holograms addline " + ChatColor.WHITE + "<name> <text>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms create " + ChatColor.WHITE + "<name> <text>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms delete " + ChatColor.WHITE + "<name>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms import " + ChatColor.WHITE + "<plugin>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms info " + ChatColor.WHITE + "<name> <text>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms insertline " + ChatColor.WHITE + "<name> <index> <text>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms list");
        sender.sendMessage(ChatColor.YELLOW + "/holograms move " + ChatColor.WHITE + "<name> <world> <x> <y> <z>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms movehere " + ChatColor.WHITE + "<name>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms near " + ChatColor.WHITE + "<radius>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms removeline " + ChatColor.WHITE + "<name> <index>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms reload");
        sender.sendMessage(ChatColor.YELLOW + "/holograms setline " + ChatColor.WHITE + "<name> <index> <text>");
        sender.sendMessage(ChatColor.YELLOW + "/holograms teleport " + ChatColor.WHITE + "<name>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(commands.keySet());
        } else {
            return Collections.emptyList();
        }
    }
}
