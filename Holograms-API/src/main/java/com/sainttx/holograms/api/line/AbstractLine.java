package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;

public abstract class AbstractLine implements HologramLine {

    private Hologram parent;
    private Location location;

    public AbstractLine(Hologram parent) {
        Validate.notNull(parent, "Parent hologram cannot be null");
        this.parent = parent;
        this.location = parent.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        this.location = location.clone();
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    @Override
    public double getHeight() {
        return 0d;
    }

    @Override
    public final Hologram getHologram() {
        return parent;
    }
}
