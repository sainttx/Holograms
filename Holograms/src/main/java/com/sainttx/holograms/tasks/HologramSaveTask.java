package com.sainttx.holograms.tasks;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramPlugin;

public class HologramSaveTask implements Runnable {

    private HologramPlugin plugin;

    public HologramSaveTask(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getHologramManager().getActiveHolograms().values().stream()
                .filter(Hologram::isPersistent)
                .forEach(plugin.getHologramManager()::saveHologram);
    }
}
