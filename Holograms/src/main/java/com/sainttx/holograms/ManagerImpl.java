package com.sainttx.holograms;

import com.sainttx.holograms.api.CustomModelDataHelper;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.exception.HologramEntitySpawnException;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.UpdatingHologramLine;
//import com.sainttx.holograms.nms.v1_14_R1.CustomModelDataHelperImpl;
import com.sainttx.holograms.util.LocationUtil;
import org.bukkit.Location;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ManagerImpl implements HologramManager {

    private HologramPlugin plugin;
    private Configuration persistingHolograms;
    private Map<String, Hologram> activeHolograms = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Set<UpdatingHologramLine> trackedUpdatingLines = new HashSet<>();
    private CustomModelDataHelper customModelDataHelper;

    ManagerImpl(HologramPlugin plugin) {
        this.plugin = plugin;
        this.reloadConfiguration();
        //customModelDataHelper = new CustomModelDataHelperImpl();
    }

    /* Re-reads the holograms.yml file into memory */
    private void reloadConfiguration() {
        this.persistingHolograms = new Configuration(plugin, "holograms.yml");
    }

    @Override
    public void reload() {
        clear();
        reloadConfiguration();
        load();
    }

    @Override
    public CustomModelDataHelper getCustomModelDataHelper() {
        return customModelDataHelper;
    }

    /**
     * Loads all saved Holograms
     */
    void load() {
        if (persistingHolograms == null) {
            this.reloadConfiguration();
        }

        // Load all the holograms
        if (persistingHolograms.isConfigurationSection("holograms")) {
            loadHolograms:
            for (String hologramName : persistingHolograms.getConfigurationSection("holograms").getKeys(false)) {
                List<String> uncoloredLines = persistingHolograms.getStringList("holograms." + hologramName + ".lines");
                Location location = LocationUtil.stringAsLocation(persistingHolograms.getString("holograms." + hologramName + ".location"));

                if (location == null) {
                    plugin.getLogger().warning("Hologram \"" + hologramName + "\" has an invalid location");
                    continue;
                }

                // Create the Hologram
                Hologram hologram = new Hologram(hologramName, location, true);
                // Add the lines
                for (String string : uncoloredLines) {
                    HologramLine line = plugin.parseLine(hologram, string);
                    try {
                        hologram.addLine(line);
                    } catch (HologramEntitySpawnException e) {
                        plugin.getLogger().log(Level.WARNING, "Failed to spawn Hologram \"" + hologramName + "\"", e);
                        continue loadHolograms;
                    }
                }
                hologram.spawn();
                addActiveHologram(hologram);
                plugin.getLogger().info("Loaded hologram with \"" + hologram.getId() + "\" with " + hologram.getLines().size() + " lines");
            }
        } else {
            plugin.getLogger().warning("holograms.yml file had no 'holograms' section defined, no holograms loaded");
        }
    }

    @Override
    public void saveHologram(Hologram hologram) {
        String hologramName = hologram.getId();
        Collection<HologramLine> holoLines = hologram.getLines();
        List<String> uncoloredLines = holoLines.stream()
                .map(HologramLine::getRaw)
                .collect(Collectors.toList());
        persistingHolograms.set("holograms." + hologramName + ".location", LocationUtil.locationAsString(hologram.getLocation()));
        persistingHolograms.set("holograms." + hologramName + ".lines", uncoloredLines);
        persistingHolograms.saveConfiguration();
    }

    @Override
    public void deleteHologram(Hologram hologram) {
        hologram.despawn();
        removeActiveHologram(hologram);
        persistingHolograms.set("holograms." + hologram.getId(), null);
        persistingHolograms.saveConfiguration();
    }

    @Override
    public Hologram getHologram(String name) {
        return activeHolograms.get(name);
    }

    @Override
    public Map<String, Hologram> getActiveHolograms() {
        return activeHolograms;
    }

    @Override
    public void addActiveHologram(Hologram hologram) {
        activeHolograms.put(hologram.getId(), hologram);
    }

    @Override
    public void removeActiveHologram(Hologram hologram) {
        activeHolograms.remove(hologram.getId());
    }

    @Override
    public void trackLine(UpdatingHologramLine line) {
        trackedUpdatingLines.add(line);
    }

    @Override
    public boolean untrackLine(UpdatingHologramLine line) {
        return trackedUpdatingLines.remove(line);
    }

    @Override
    public Collection<? extends UpdatingHologramLine> getTrackedLines() {
        return trackedUpdatingLines;
    }

    @Override
    public void clear() {
        getActiveHolograms().values().forEach(Hologram::despawn);
        getActiveHolograms().clear();
    }
}
