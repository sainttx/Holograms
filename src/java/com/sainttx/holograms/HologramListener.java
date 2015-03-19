package com.sainttx.holograms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 * Created by Matthew on 08/01/2015.
 */
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
        plugin.getHologramManager().onChunkLoad(event.getChunk());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        plugin.getHologramManager().onChunkUnload(event.getChunk());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled() && plugin.getNMSController().getNMSEntityBase(event.getEntity()) != null) {
            event.setCancelled(false);
        }
    }
}
