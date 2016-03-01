package com.sainttx.holograms.internal;

import com.sainttx.holograms.ManagerImpl;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramLine;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Matthew on 08/01/2015.
 */
public class HologramImpl implements Hologram {

    private final String name;
    private Location location;
    private boolean persist;
    private List<HologramLine> lines = new ArrayList<>();

    public HologramImpl(String name, Location location, String... lines) {
        this(name, location, false, lines);
    }

    public HologramImpl(String name, Location location, boolean persist, String... lines) {
        Validate.notNull(name, "Hologram name cannot be null");
        Validate.notNull(location, "Hologram location cannot be null");
        this.name = name;
        this.location = location;
        this.persist = persist;

        // Create HologramLines
        if (lines != null && lines.length > 0) {
            for (String line : lines) {
                HologramLine holoLine = new HologramLineImpl(this, line);
                this.lines.add(holoLine);
            }
        }

        ManagerImpl.getInstance().addActiveHologram(this);
        saveIfPersistent();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isPersistent() {
        return persist;
    }

    @Override
    public void setPersistent(boolean persist) {
        this.persist = persist;
        saveIfPersistent();
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void remove() {
        this.removeAllLines();
        ManagerImpl.getInstance().removeActiveHologram(this);

        if (this.persist) {
            ManagerImpl.getInstance().deleteHologram(this);
        }
    }

    // Removes all lines from this hologram
    private void removeAllLines() {
        this.despawn();
        lines.clear();
    }

    // Saves this hologram only if it is persistent
    private void saveIfPersistent() {
        if (this.isPersistent()) {
            ManagerImpl.getInstance().saveHologram(this);
        }
    }

    @Override
    public Collection<HologramLine> getLines() {
        return lines;
    }

    @Override
    public void addLine(HologramLine line) {
        this.addLine(line, lines.size());
    }

    @Override
    public void addLine(HologramLine line, int index) {
        lines.add(index, line);
        this.saveIfPersistent();
    }

    @Override
    public void removeLine(HologramLine line) {
        lines.remove(line);
        line.despawn();
        this.saveIfPersistent();
    }

    @Override
    public HologramLine getLine(int index) {
        return lines.get(index);
    }

    @Override
    public void refresh() {
        this.despawn();
        if (location.getChunk().isLoaded()) {
            spawnEntities();
        }
    }

    // Forces the entities to spawn
    private void spawnEntities() {
        this.despawn();

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

    @Override
    public void despawn() {
        for (HologramLine line : getLines()) {
            line.despawn();
        }
    }

    @Override
    public void teleport(Location location) {
        if (this.location.equals(location)) {
            return;
        }

        // Move all the hologram lines
        this.location = location;
        double currentY = this.location.getY();
        boolean first = true;

        for (HologramLine line : lines) {
            if (first) {
                first = false;
            } else {
                currentY -= line.getHeight();
                currentY -= 0.02;
            }

            Location nextLocation = this.location.clone();
            nextLocation.setY(currentY);
            line.getEntity().getBukkitEntity().teleport(nextLocation);
        }

        // Save new location
        this.saveIfPersistent();
    }
}
