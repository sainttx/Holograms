package com.sainttx.holograms.data;

import com.sainttx.holograms.HologramManager;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 08/01/2015.
 */
public class Hologram {

    /*
     * The ID of this Hologram
     */
    private final String name;

    /*
     * The location of the Hologram
     */
    private Location location;

    /*
     * A boolean depicting whether or not this Hologram
     * stays through server restarts
     */
    private boolean persist;

    /*
     * The lines of text that this Hologram is hosting
     */
    private final List<HologramLine> lines = new ArrayList<HologramLine>();

    /**
     * Creates a Hologram at a Location
     *
     * @param location The location to spawn at
     * @param lines    Any text to display with this Hologram
     */
    public Hologram(String name, Location location, String... lines) {
        this(name, location, false, lines);
    }

    /**
     * Creates a Hologram at a Location
     *
     * @param location The location to spawn at
     * @param persist  Whether or not this Hologram should stay through server restarts
     * @param lines    Any text to display with this Hologram
     */
    public Hologram(String name, Location location, boolean persist, String... lines) {
        this.name = name;
        this.location = location;
        this.persist = persist;

        // Create HologramLines
        for (String line : lines) {
            HologramLine holoLine = new HologramLine(this, line);
            this.lines.add(holoLine);
        }

        saveIfPersistent();
        HologramManager.getInstance().addHologram(this);
    }

    /**
     * Returns the name (ID) of this Hologram
     *
     * @return The unique name of this Hologram
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns whether or not this Hologram is persistent throughout server restarts
     *
     * @return The persistence of the hologram
     */
    public boolean isPersistent() {
        return persist;
    }

    /**
     * Sets whether or not this Hologram is persistent
     *
     * @param persist the new persistence value
     */
    public void setPersistency(boolean persist) {
        this.persist = persist;
        saveIfPersistent();
    }

    /**
     * Returns whether or not this Hologram is held in a chunk
     *
     * @param chunk The chunk we are looking in
     * @return True if this Hologram is inside the provided chunk, false otherwise
     */
    public boolean isInChunk(Chunk chunk) {
        Chunk hologramChunk = location.getChunk();

        return hologramChunk.getX() == chunk.getX() && hologramChunk.getZ() == chunk.getZ();
    }

    /**
     * Return the Location of this Hologram
     *
     * @return Where this Hologram is
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Deletes this Hologram instance
     */
    public void delete() {
        this.clearLines();
        HologramManager.getInstance().removeHologram(this);

        if (this.persist) {
            HologramManager.getInstance().deleteHologram(this);
        }
    }

    /**
     * Saves this hologram if it is marked as persistent
     */
    public void saveIfPersistent() {
        if (persist) {
            HologramManager.getInstance().saveHologram(this);
        }
    }

    /**
     * Returns the lines that this Hologram is hosting
     *
     * @return A shallow copy of the list of Hologram Lines at this Hologram
     */
    public List<HologramLine> getHologramLines() {
        return lines;
    }

    /**
     * Adds a new line to this hologram
     *
     * @param line The hologram line
     */
    public void addLine(HologramLine line) {
        lines.add(line);
        saveIfPersistent();
    }

    /**
     * Adds a new String of text to this hologram
     *
     * @param text The text to add
     */
    public void addLine(String text) {
        HologramLine newLine = new HologramLine(this, text);
        addLine(newLine);
    }

    /**
     * Removes a line from the list of active lines and despawns it
     *
     * @param line The line to be removed
     */
    public void removeLine(HologramLine line) {
        lines.remove(line);
        line.despawn();
        saveIfPersistent();
    }

    /**
     * Removes a line from the list of active lines
     *
     * @param index The index of the line
     */
    public HologramLine removeLine(int index) {
        HologramLine line = lines.get(index);
        removeLine(line);
        return line;
    }

    /**
     * Inserts a line into the Hologram at a particular index
     *
     * @param line  The hologram line of text to insert
     * @param index The index where the line should be inserted
     */
    public void insertLine(HologramLine line, int index) {
        lines.add(index, line);
        saveIfPersistent();
    }

    /**
     * Inserts a line into the Hologram at a particular index
     *
     * @param text  The string of text to insert
     * @param index The index where the line should be inserted
     */
    public void insertLine(String text, int index) {
        HologramLine line = new HologramLine(this, text);
        insertLine(line, index);
    }

    /**
     * Removes all of the HologramLines from the world and clears the list
     * of active HologramLines in this Hologram
     */
    public void clearLines() {
        for (HologramLine line : lines) {
            line.despawn();
        }

        lines.clear();
    }

    /**
     * Refreshes this Holograms lines
     */
    public void refreshAll() {
        despawnEntities();
        if (location.getChunk().isLoaded()) {
            spawnEntities();
        }
    }

    /**
     * Forces the entities to spawn, without checking if the chunk is loaded.
     */
    public void spawnEntities() {
        despawnEntities();

        double currentY = this.location.getY();
        boolean first = true;

        for (HologramLine line : lines) {
            if (first) {
                first = false;
            } else {
                currentY -= line.getHeight();
                currentY -= 0.02;
            }

            Location location = this.location.clone();
            location.setY(currentY);
            line.spawn(location);
        }
    }

    /**
     * Called by the PluginHologramManager when the chunk is unloaded.
     */
    public void despawnEntities() {
        for (HologramLine piece : lines) {
            piece.despawn();
        }
    }

    /**
     * Teleports this Hologram to a new Location
     *
     * @param location The new location of this Hologram
     */
    public void teleport(Location location) {
        if (!this.location.equals(location)) {
            this.location = location;
        }
        saveIfPersistent();
    }
}
