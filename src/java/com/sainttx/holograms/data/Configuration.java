package com.sainttx.holograms.data;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by Matthew on 21/10/2014.
 */
public class Configuration extends YamlConfiguration {

    /**
     * The name of the file that this configuration is stored in
     */
    protected String file;

    /**
     * The plugin that stores this configuration
     */
    protected JavaPlugin plugin;

    /**
     * Instantiates a new Configuration file
     *
     * @param plugin The plugin instantiating the file
     * @param file   The name of the file being created as a .YML file
     */
    public Configuration(JavaPlugin plugin, String file) {
        this.plugin = plugin;
        this.file = file;

        this.createFile();
        this.loadConfiguration();
    }

    /**
     * Creates and loads the configuration
     */
    private void createFile() {
        File file = new File(plugin.getDataFolder(), this.file);

        // Create any directories leading to this file if we have too
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            if (!file.exists()) {
                if (plugin.getResource(this.file) != null) {
                    plugin.saveResource(this.file, false);
                } else {
                    file.createNewFile();
                }
            }
            super.load(file);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Configuration " + this.file + " could not be loaded!");
            ex.printStackTrace();
        }
    }

    /**
     * Deletes the file from disk
     */
    public void deleteFile() {
        File file = new File(plugin.getDataFolder(), this.file);
        file.delete();
    }

    /**
     * Loads the configuration from disk
     */
    public void loadConfiguration() {
        try {
            File file = new File(plugin.getDataFolder(), this.file);

            if (!file.exists()) {
                this.createFile();
            } else {
                super.load(file);
            }
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Configuration " + this.file + " could not be loaded!");
            ex.printStackTrace();
        }
    }

    /**
     * Saves the configuration to file
     */
    public void saveConfiguration() {
        File file = new File(plugin.getDataFolder(), this.file);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            super.save(file);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Configuration" + this.file + " could not be saved!");
            ex.printStackTrace();
        }
    }
}
