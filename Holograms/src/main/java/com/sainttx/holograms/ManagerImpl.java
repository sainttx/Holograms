package com.sainttx.holograms;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.line.TextualHologramLine;
import com.sainttx.holograms.util.LocationUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Matthew on 08/01/2015.
 */
public class ManagerImpl implements HologramManager {

    /*
     * The HologramPlugin instance
     */
    private HologramPlugin plugin;

    /*
     * The file that stores saved Hologram information
     */
    private Configuration persistingHolograms;

    /*
     * A map containing all spawned Holograms
     * key is the holograms name
     */
    private Map<String, Hologram> activeHolograms = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    ManagerImpl(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads all saved Holograms
     */
    void load() {
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
                Hologram hologram = new Hologram(hologramName, location, false, uncoloredLines.toArray(new String[uncoloredLines.size()]));
                hologram.refresh();
                hologram.setPersistent(true);
            }
        }
    }

    @Override
    public void saveHologram(Hologram hologram) {
        String hologramName = hologram.getName();
        Collection<HologramLine> holoLines = hologram.getLines();
        List<String> uncoloredLines = new ArrayList<String>();

        for (HologramLine line : holoLines) {
            if (line instanceof TextualHologramLine) {
                uncoloredLines.add(((TextualHologramLine) line).getText().replace(ChatColor.COLOR_CHAR, '&'));
            }
        }

        persistingHolograms.set("holograms." + hologramName + ".location", LocationUtil.locationAsString(hologram.getLocation()));
        persistingHolograms.set("holograms." + hologramName + ".lines", uncoloredLines);
        persistingHolograms.saveConfiguration();
    }

    @Override
    public void deleteHologram(Hologram hologram) {
        hologram.despawn();
        removeActiveHologram(hologram);
        persistingHolograms.set("holograms." + hologram.getName(), null);
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
        activeHolograms.put(hologram.getName(), hologram);
    }

    @Override
    public void removeActiveHologram(Hologram hologram) {
        activeHolograms.remove(hologram.getName());
    }

    @Override
    public void clear() {
        for (Hologram holo : getActiveHolograms().values()) {
            holo.despawn();
        }
        getActiveHolograms().clear();
    }
}
