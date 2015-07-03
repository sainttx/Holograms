package com.sainttx.holograms.nms;

import com.sainttx.holograms.data.HologramLine;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by Matthew on 15/03/2015.
 */
public interface NMSController {

    /**
     * Sets up the NMS environment
     */
    void setup();

    /**
     * Spawns a new Hologram in the world
     *
     * @param world       The world to spawn the entity in
     * @param x           The x coordinate of the entity
     * @param y           The y coordinate of the entity
     * @param z           The z coordinate of the entity
     * @param parentPiece The hologram piece for the hologram
     * @return The spawned entity
     */
    NMSEntityBase spawnArmorStand(World world, double x, double y, double z, HologramLine parentPiece);

    /**
     * Returns the NMSEntityBase instance for a Bukkit entity
     *
     * @param bukkitEntity The bukkit entity
     * @return The NMSEntityBase found, defaults to null
     */
    NMSEntityBase getNMSEntityBase(Entity bukkitEntity);
}
