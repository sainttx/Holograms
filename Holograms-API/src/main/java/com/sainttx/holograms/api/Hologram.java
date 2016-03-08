package com.sainttx.holograms.api;

import com.sainttx.holograms.api.line.HologramLine;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Hologram {

    private final String id;
    private Location location;
    private boolean persist;
    private boolean dirty;
    private List<HologramLine> lines = new ArrayList<>();

    @ConstructorProperties({ "id", "location" })
    public Hologram(String id, Location location) {
        this(id, location, false);
    }

    @ConstructorProperties({ "id", "location", "persist" })
    public Hologram(String id, Location location, boolean persist) {
        Validate.notNull(id, "Hologram id cannot be null");
        Validate.notNull(location, "Hologram location cannot be null");
        this.id = id;
        this.location = location;
        this.persist = persist;
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
        setDirty(true);
    }

    /**
     * Returns whether this Hologram has had any changes since it was last saved.
     *
     * @return <tt>true</tt> if the hologram has been modified
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets this Holograms dirty state. A "dirty" Hologram implies that since the last
     * time it was saved, the Hologram has had some sort of modification performed to it
     * and requires the plugin to save it to reflect any changes. If the Hologram is not
     * persistent, it will always remain dirty unless modified by a third party. This is
     * due to the plugin never saving it.
     *
     * @param dirty the dirty state
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
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
    public Collection<HologramLine> getLines() {
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
        line.show();
        reorganize();
        setDirty(true);
    }

    /**
     * Removes a {@link HologramLine} from this Hologram and de-spawns it.
     *
     * @param line the line
     * @throws IllegalArgumentException if the line is not part of this hologram
     */
    public void removeLine(HologramLine line) {
        Validate.isTrue(lines.contains(line), "Line is not a part of this hologram");
        lines.remove(line);
        if (!line.isHidden()) {
            line.hide();
        }
        reorganize();
        setDirty(true);
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

    // Reorganizes holograms after an initial index
    private void reorganize() {
        Location location = getLocation();
        double y = location.getY();

        // Spawn the first line and then start decrementing the y
        HologramLine first = getLine(0);
        first.setLocation(location);

        for (int i = 1 ; i < lines.size() ; i++) {
            HologramLine line = getLine(i);
            if (line != null && !line.isHidden()) {
                double height = line.getHeight();
                double middle = height / 2;
                y -= middle; // Spawn the line at the middle of its height
                location.setY(y);
                y -= middle; // Add space below the line so added lines don't get placed inside it
                y -= HologramLine.SPACE_BETWEEN_LINES;
                line.setLocation(location);
            }
        }
    }

    /**
     * De-spawns all of the lines in this Hologram.
     */
    public void despawn() {
        getLines().stream().filter(line -> !line.isHidden()).forEach(HologramLine::hide);
    }

    /**
     * Spawns all of the lines in this Hologram.
     */
    public void spawn() {
        getLines().stream().filter(HologramLine::isHidden).forEach(HologramLine::show);
    }

    /**
     * Teleports this Hologram to a new {@link Location}.
     *
     * @param location the location
     */
    public void teleport(Location location) {
        if (!this.location.equals(location)) {
            this.location = location.clone();
            reorganize();
            setDirty(true);
        }
    }
}
