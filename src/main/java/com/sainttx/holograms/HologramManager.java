package com.sainttx.holograms;

import com.sainttx.holograms.data.Configuration;
import com.sainttx.holograms.data.Hologram;
import com.sainttx.holograms.data.HologramLine;
import com.sainttx.holograms.util.LocationUtil;
import com.sainttx.holograms.data.Configuration;
import com.sainttx.holograms.data.Hologram;
import com.sainttx.holograms.data.HologramLine;
import com.sainttx.holograms.util.LocationUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.*;

/**
 * Created by Matthew on 08/01/2015.
 */
public class HologramManager {

    /*
     * The HologramPlugin instance
     */
    private HologramPlugin plugin;

    /*
     * The only Holograms instance - Singleton
     */
    private static HologramManager instance;

    /*
     * The file that stores saved Hologram information
     */
    private Configuration persistingHolograms;

    /*
     * A map containing all spawned Holograms
     * key is the holograms name
     */
    private Map<String, Hologram> activeHolograms = new TreeMap<String, Hologram>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Singleton
     */
    protected HologramManager(HologramPlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    /**
     * Returns the Holograms controller instance
     *
     * @return The running instance of the Holograms controller class
     */
    public static HologramManager getInstance() {
        return instance;
    }

    /**
     * Loads all saved Holograms
     */
    public void load() {
        if (persistingHolograms == null) {
            persistingHolograms = new Configuration(plugin, "holograms.yml");
        }

        // Load all the holograms
        if (persistingHolograms.isConfigurationSection("holograms")) {
            for (String hologramName : persistingHolograms.getConfigurationSection("holograms").getKeys(false)) {
                List<String> uncoloredLines = persistingHolograms.getStringList("holograms." + hologramName + ".lines");
                Location location = LocationUtil.stringAsLocation(persistingHolograms.getString("holograms." + hologramName + ".location"));

                // Create the Hologram
                Hologram hologram = new Hologram(hologramName, location, false, uncoloredLines.toArray(new String[uncoloredLines.size()]));
                hologram.refreshAll();
                hologram.setPersistency(true);
            }
        }
    }

    /**
     * Saves a Hologram for persistence through server restarts
     *
     * @param hologram The Hologram to be saved
     */
    public void saveHologram(Hologram hologram) {
        String hologramName = hologram.getName();
        List<HologramLine> holoLines = hologram.getHologramLines();
        List<String> uncoloredLines = new ArrayList<String>();

        for (HologramLine line : holoLines) {
            uncoloredLines.add(line.getOriginalText());
        }

        persistingHolograms.set("holograms." + hologramName + ".location", LocationUtil.locationAsString(hologram.getLocation()));
        persistingHolograms.set("holograms." + hologramName + ".lines", uncoloredLines);
        persistingHolograms.saveConfiguration();
    }

    /**
     * Deletes a Hologram for persistence through server restarts
     *
     * @param hologram The Hologram to be deleted
     */
    public void deleteHologram(Hologram hologram) {
        persistingHolograms.set("holograms." + hologram.getName(), null);
        persistingHolograms.saveConfiguration();
    }

    /**
     * Returns a Hologram by its name
     *
     * @param name The name of the Hologram
     * @return The hologram
     */
    public Hologram getHologramByName(String name) {
        return activeHolograms.get(name);
    }

    /**
     * Returns a map of the active holograms
     *
     * @return A deep copy of the active holograms map
     */
    public Map<String, Hologram> getActiveHolograms() {
        Map<String, Hologram> ret = new TreeMap<String, Hologram>(String.CASE_INSENSITIVE_ORDER);
        ret.putAll(activeHolograms);
        return ret;
    }

    /**
     * Adds an active Hologram
     *
     * @param hologram The Hologram to be added
     */
    public void addHologram(Hologram hologram) {
        Validate.isTrue(activeHolograms.get(hologram.getName()) == null, "A Hologram with that name already exists.");
        activeHolograms.put(hologram.getName(), hologram);
    }

    /**
     * Removes an active Hologram
     *
     * @param hologram The Hologram to be removed
     */
    public void removeHologram(Hologram hologram) {
        activeHolograms.remove(hologram.getName());
    }

    /**
     * Handles chunk loading
     *
     * @param chunk The chunk that just loaded
     */
    public void onChunkLoad(Chunk chunk) {
        for (Hologram hologram : activeHolograms.values()) {
            if (hologram.isInChunk(chunk)) {
                hologram.spawnEntities();
            }
        }
    }

    /**
     * Handles chunk unloading
     *
     * @param chunk The chunk that just unloaded
     */
    public void onChunkUnload(Chunk chunk) {
        for (Hologram hologram : activeHolograms.values()) {
            if (hologram.isInChunk(chunk)) {
                hologram.despawnEntities();
            }
        }
    }

    /**
     * Removes all active Holograms
     */
    public void clear() {
        Map<String, Hologram> copyActive = getActiveHolograms();
        activeHolograms.clear();

        for (Hologram hologram : copyActive.values()) {
            hologram.despawnEntities();
        }
    }
}
