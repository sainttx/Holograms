package com.sainttx.holograms;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.data.Configuration;
import com.sainttx.holograms.internal.HologramImpl;
import com.sainttx.holograms.internal.HologramLineImpl;
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
    private Map<String, HologramImpl> activeHolograms = new TreeMap<String, HologramImpl>(String.CASE_INSENSITIVE_ORDER);

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

                if (location == null) {
                    plugin.getLogger().warning("Hologram \"" + hologramName + "\" has an invalid location");
                    continue;
                }

                // Create the Hologram
                Hologram hologram = new HologramImpl(hologramName, location, false, uncoloredLines.toArray(new String[uncoloredLines.size()]));
                hologram.refresh();
                hologram.setIsPersistent(true);
            }
        }
    }

    /**
     * Saves a Hologram for persistence through server restarts
     *
     * @param hologram The Hologram to be saved
     */
    public void saveHologram(HologramImpl hologram) {
        String hologramName = hologram.getName();
        Collection<HologramLine> holoLines = hologram.getLines();
        List<String> uncoloredLines = new ArrayList<String>();

        for (HologramLine line : holoLines) {
            uncoloredLines.add(((HologramLineImpl) line).getOriginalText());
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
    public void deleteHologram(HologramImpl hologram) {
        persistingHolograms.set("holograms." + hologram.getName(), null);
        persistingHolograms.saveConfiguration();
    }

    /**
     * Returns a Hologram by its name
     *
     * @param name The name of the Hologram
     * @return The hologram
     */
    public HologramImpl getHologramByName(String name) {
        return activeHolograms.get(name);
    }

    /**
     * Returns a map of the active holograms
     *
     * @return A deep copy of the active holograms map
     */
    public Map<String, HologramImpl> getActiveHolograms() {
        Map<String, HologramImpl> ret = new TreeMap<String, HologramImpl>(String.CASE_INSENSITIVE_ORDER);
        ret.putAll(activeHolograms);
        return ret;
    }

    /**
     * Adds an active Hologram
     *
     * @param hologram The Hologram to be added
     */
    public void addHologram(HologramImpl hologram) {
        Validate.isTrue(activeHolograms.get(hologram.getName()) == null, "A Hologram with that name already exists.");
        activeHolograms.put(hologram.getName(), hologram);
    }

    /**
     * Removes an active Hologram
     *
     * @param hologram The Hologram to be removed
     */
    public void removeHologram(HologramImpl hologram) {
        activeHolograms.remove(hologram.getName());
    }

    /**
     * Handles chunk loading
     *
     * @param chunk The chunk that just loaded
     */
    public void onChunkLoad(Chunk chunk) {
        for (HologramImpl hologram : activeHolograms.values()) {
            if (hologram == null) {
                continue;
            }

            if (hologram.getLocation().getChunk().equals(chunk)) {
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
        for (HologramImpl hologram : activeHolograms.values()) {
            if (hologram == null) {
                continue;
            }

            if (hologram.getLocation().getChunk().equals(chunk)) {
                hologram.despawn();
            }
        }
    }

    /**
     * Removes all active Holograms
     */
    public void clear() {
        Map<String, HologramImpl> copyActive = getActiveHolograms();
        activeHolograms.clear();

        for (HologramImpl hologram : copyActive.values()) {
            hologram.despawn();
        }
    }
}
