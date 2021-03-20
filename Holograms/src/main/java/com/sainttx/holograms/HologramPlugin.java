package com.sainttx.holograms;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.meta.SimpleCommandMeta;
import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.commands.HologramCommand;
import com.sainttx.holograms.parser.AnimatedItemLineParser;
import com.sainttx.holograms.parser.AnimatedTextLineParser;
import com.sainttx.holograms.parser.HeadParser;
import com.sainttx.holograms.parser.ItemLineParser;
import com.sainttx.holograms.tasks.HologramUpdateTask;
import com.sainttx.holograms.util.ReflectionUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.function.Function;

public class HologramPlugin extends com.sainttx.holograms.api.HologramPlugin {

    /**
     * The path to the package that contains our NMS implementations
     */
    public static final String NMS_PACKAGE_PATH = "com.sainttx.holograms.nms." + ReflectionUtil.getVersion();

    private HologramManager manager;
    private HologramEntityController controller;
    private final Runnable updateTask = new HologramUpdateTask(this);
    private Metrics metrics;
    private   BukkitCommandManager<CommandSender> bukkitCommandManager ;
private AnnotationParser<CommandSender> annotationParser;
    @Override
    public void onEnable() {
        this.manager = new ManagerImpl(this);
        metrics = new Metrics(this, 9421);
        // Register parsers
        addLineParser(new ItemLineParser());
        addLineParser(new AnimatedItemLineParser());
        addLineParser(new AnimatedTextLineParser());
        addLineParser(new HeadParser());

        if (setupController()) {
            getServer().getPluginManager().registerEvents(new HologramListener(this), this);

            getServer().getScheduler().runTaskLater(this, () -> ((ManagerImpl) manager).load(), 5L);
            getServer().getScheduler().runTaskTimer(this, updateTask, 2L, 2L);
        }
        final Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
                CommandMeta.simple()
                        // This will allow you to decorate commands with descriptions
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build();
        this.annotationParser = new AnnotationParser<>(
                /* Manager */ bukkitCommandManager,
                /* Command sender type */ CommandSender.class,
                /* Mapper for command meta instances */ commandMetaFunction
        );
        annotationParser.parse(new HologramCommand(this));
    }

    @Override
    public void onDisable() {
        manager.clear();
        this.manager = null;
        this.controller = null;
    }

    // Sets up the entity controller
    private boolean setupController() {
        try {
            Class<?> nmsControllerClazz = Class.forName(NMS_PACKAGE_PATH + "HologramEntityControllerImpl");
            Constructor<?> constructor = nmsControllerClazz.getConstructor(com.sainttx.holograms.api.HologramPlugin.class);
            this.controller = (HologramEntityController) constructor.newInstance(this);
            getLogger().info("HologramEntityController set to: " + controller.getClass().getCanonicalName());
        } catch (Exception ex) {
            /* Couldn't instantiate the nmsController - Spigot/CraftBukkit version isn't supported */
            getLogger().severe("The plugin couldn't create the NMS controller instance and has been disabled. This is likely" +
                    "due to no supported Hologram implementation for your CraftBukkit/Spigot version.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    @Override
    public HologramManager getHologramManager() {
        return manager;
    }

    @Override
    public HologramEntityController getEntityController() {
        return controller;
    }
}
