package com.sainttx.holograms;

import com.sainttx.holograms.api.Hologram;
import java.util.Collection;
import org.bukkit.Chunk;
import org.bukkit.Location;
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
        Chunk chunk = event.getChunk();
        if (chunk == null || !chunk.isLoaded()) {
            return;
        }

        Collection<Hologram> holograms = plugin.getHologramManager().getActiveHolograms().values();
        for (Hologram holo : holograms) {
            Location loc = holo.getLocation();
            if (loc.getBlockX() >> 4 == chunk.getX() && loc.getBlockZ() >> 4 == chunk.getZ()) {
                holo.spawn();
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        Collection<Hologram> holograms = plugin.getHologramManager().getActiveHolograms().values();
        for (Hologram holo : holograms) {
            Location loc = holo.getLocation();
            if (loc.getBlockX() >> 4 == chunk.getX() && loc.getBlockZ() >> 4 == chunk.getZ()) {
                holo.despawn();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled() && plugin.getEntityController().getHologramEntity(event.getEntity()) != null) {
            event.setCancelled(false);
        }
    }
}
