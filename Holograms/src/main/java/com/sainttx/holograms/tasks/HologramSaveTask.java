package com.sainttx.holograms.tasks;

import com.sainttx.holograms.api.HologramPlugin;

public class HologramSaveTask implements Runnable {

    private HologramPlugin plugin;

    public HologramSaveTask(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getHologramManager().getActiveHolograms().values().stream()
                .filter(holo -> holo.isPersistent() && holo.isDirty())
                .forEach(plugin.getHologramManager()::saveHologram);
    }
}
