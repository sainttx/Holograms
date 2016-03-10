package com.sainttx.holograms.tasks;

import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.UpdatingHologramLine;

public class HologramUpdateTask implements Runnable {

    public HologramPlugin plugin;

    public HologramUpdateTask(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        HologramManager hologramManager = plugin.getHologramManager();
        long time = System.currentTimeMillis();

        // Update all lines
        hologramManager.getTrackedLines().stream()
                .filter(line -> !line.isHidden()) // Don't update hidden lines
                .filter(line -> time > line.getLastUpdateTime() + line.getDelay()) // Allow intervals set by implementation
                .forEach(UpdatingHologramLine::update);
    }
}
