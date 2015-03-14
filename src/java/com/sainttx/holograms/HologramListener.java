package com.sainttx.holograms;

import com.sainttx.holograms.nms.NMSEntityBase;
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
    private HologramManager instance;

    /**
     * Creates a HologramListener
     *
     * @param instance The running instance of the Holograms controller
     */
    public HologramListener(HologramManager instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        instance.onChunkLoad(event.getChunk());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        instance.onChunkUnload(event.getChunk());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled() && event.getEntity() instanceof NMSEntityBase) {
            event.setCancelled(false);
        }
    }
}
