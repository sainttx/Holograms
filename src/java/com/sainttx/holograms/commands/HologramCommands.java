package com.sainttx.holograms.commands;

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
     * A map containing all commands for the Hologram plugin
     */
    private Map<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

    public HologramCommands() {
        commands.put("addline", new CommandAddLine());
        commands.put("create", new CommandCreate());
        commands.put("delete", new CommandDelete());
        commands.put("info", new CommandInfo());
        commands.put("insertline", new CommandInsertLine());
        commands.put("list", new CommandList());
        commands.put("movehere", new CommandMoveHere());
        commands.put("near", new CommandNear());
        commands.put("removeline", new CommandRemoveLine());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
