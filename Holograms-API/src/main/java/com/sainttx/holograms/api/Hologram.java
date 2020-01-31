package com.sainttx.holograms.api;

import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.UpdatingHologramLine;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private final HologramPlugin plugin;
    private final String id;
    private Location location;
    private boolean persist;
    private boolean spawned;
    private List<HologramLine> lines = new ArrayList<>();

    @ConstructorProperties({ "id", "location" })
    public Hologram(String id, Location location) {
        this(id, location, false);
    }

    @ConstructorProperties({ "id", "location", "persist" })
    public Hologram(String id, Location location, boolean persist) {
        Validate.notNull(id, "Hologram id cannot be null");
        Validate.notNull(location, "Hologram location cannot be null");
        this.plugin = JavaPlugin.getPlugin(HologramPlugin.class);
        this.id = id;
        this.location = location;
        this.persist = persist;
    }
    
    // Internal method to save hologram if persistent state has been set
    private void save() {
        if (isPersistent()) {
            plugin.getHologramManager().saveHologram(this);
        }
    }

    /**
     * Returns the unique ID id of this Hologram.
     *
     * @return the holograms id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns whether the Hologram is currently spawned (ie. visible).
     *
     * @return true if spawned, false otherwise
     */
    public boolean isSpawned() {
        return spawned;
    }

    /**
     * Returns the persistence state of this Hologram.
     *
     * @return <tt>true</tt> if the Hologram is persistent
     */
    public boolean isPersistent() {
        return persist;
    }

    /**
     * Sets the persistence state of this Hologram.
     *
     * @param persist the new state
     */
    public void setPersistent(boolean persist) {
        this.persist = persist;
    }

    /**
     * Returns the location of this Hologram.
     *
     * @return the holograms location
     */
    public Location getLocation() {
        return this.location.clone();
    }

    /**
     * Returns the lines contained by this Hologram.
     *
     * @return all lines in the hologram
     */
    public List<HologramLine> getLines() {
        return lines;
    }

    /**
     * Adds a new {@link HologramLine} to this Hologram.
     *
     * @param line the line
     */
    public void addLine(HologramLine line) {
        addLine(line, lines.size());
    }

    /**
     * Inserts a new {@link HologramLine} to this Hologram at a specific index.
     *
     * @param line the line
     * @param index the index to add the line at
     */
    public void addLine(HologramLine line, int index) {
        lines.add(index, line);
        save();
        if (this.spawned) {
            spawn();
        }
        if (line instanceof UpdatingHologramLine) { // Track updating line
            plugin.getHologramManager().trackLine(((UpdatingHologramLine) line));
        }
    }

    /**
     * Removes a {@link HologramLine} from this Hologram and de-spawns it.
     *
     * @param line the line
     * @throws IllegalArgumentException if the line is not part of this hologram
     */
    public void removeLine(HologramLine line) {
        lines.remove(line);
        save();
        line.hide();
        if (line instanceof UpdatingHologramLine) { // Remove tracked line
            plugin.getHologramManager().untrackLine(((UpdatingHologramLine) line));
        }
    }

    /**
     * Returns a {@link HologramLine} at a specific index.
     *
     * @param index the index
     */
    public HologramLine getLine(int index) {
        if (index < 0 || index >= lines.size()) {
            return null;
        }
        return lines.get(index);
    }

    /**
     * Returns whether the Hologram is in a loaded chunk.
     *
     * @return true if chunk is loaded, false otherwise
     */
    public boolean isChunkLoaded() {
        Location location = getLocation();
        int chunkX = (int) Math.floor(location.getBlockX() / 16.0D);
        int chunkZ = (int) Math.floor(location.getBlockZ() / 16.0D);
        return location.getWorld().isChunkLoaded(chunkX, chunkZ);
    }

    // Reorganizes holograms after an initial index
    private void reorganize() {
        Location location = getLocation();
        double y = location.getY();
        for (int i = 0 ; i < lines.size() ; i++) {
            HologramLine line = getLine(i);
            if (line instanceof ItemLine) {
                y -= 0.2;
            }
            Location lineLocation = new Location(location.getWorld(), location.getX(), y, location.getZ());
            y -= line.getHeight();
            y -= HologramLine.SPACE_BETWEEN_LINES;
            line.setLocation(lineLocation);
        }
    }

    /**
     * De-spawns all of the lines in this Hologram.
     */
    public void despawn() {
        getLines().forEach(HologramLine::hide);
        this.spawned = false;
    }

    /**
     * Spawns all of the lines in this Hologram.
     */
    public void spawn() {
        if (this.isSpawned()) {
            despawn();
        }
        if (isChunkLoaded()) {
            reorganize();
            getLines().forEach(HologramLine::show);
            this.spawned = true;
        }
    }

    /**
     * Teleports this Hologram to a new {@link Location}.
     *
     * @param location the location
     */
    public void teleport(Location location) {
        if (!this.location.equals(location)) {
            this.location = location.clone();
            save();
            if (isSpawned()) {
                spawn();
            }
        }
    }
}
