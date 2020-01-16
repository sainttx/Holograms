package com.sainttx.holograms;

import com.sainttx.holograms.api.Hologram;
import java.util.Collection;

import com.sainttx.holograms.api.entity.HologramEntity;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
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
            if (holo.isChunkLoaded()) {
                plugin.getServer().getScheduler().runTaskLater(plugin, holo::spawn, 10L);
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        for (Entity entity : chunk.getEntities()) {
            HologramEntity hologramEntity = plugin.getEntityController().getHologramEntity(entity);
            if (hologramEntity != null) {
                hologramEntity.remove();
            }
        }
        Collection<Hologram> holograms = plugin.getHologramManager().getActiveHolograms().values();
        for (Hologram holo : holograms) {
            Location loc = holo.getLocation();
            int chunkX = (int) Math.floor(loc.getBlockX() / 16.0D);
            int chunkZ = (int) Math.floor(loc.getBlockZ() / 16.0D);
            if (chunkX == chunk.getX() && chunkZ == chunk.getZ()) {
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemSpawn(ItemSpawnEvent event) {
        if (event.isCancelled() && plugin.getEntityController().getHologramEntity(event.getEntity()) != null) {
            event.setCancelled(false);
        }
    }
}
