package com.sainttx.holograms;

import com.sainttx.holograms.api.Hologram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class HologramListener implements Listener {

    /*
     * The Holograms controller
     */
    private HologramPlugin plugin;

    /**
     * Creates a HologramListener
     *
     * @param instance The running instance of the Holograms controller
     */
    public HologramListener(HologramPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.getChunk() == null) {
            return;
        }
        for (Hologram holo : plugin.getHologramManager().getActiveHolograms().values()) {
            if (holo.getLocation().getChunk().equals(event.getChunk())) {
                holo.refresh();
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (event.getChunk() == null) {
            return;
        }
        for (Hologram holo : plugin.getHologramManager().getActiveHolograms().values()) {
            if (holo.getLocation().getChunk().equals(event.getChunk())) {
                holo.despawn();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled() && plugin.getNMSController().getHologramEntity(event.getEntity()) != null) {
            event.setCancelled(false);
        }
    }
}
