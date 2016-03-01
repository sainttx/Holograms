package com.sainttx.holograms.nms.v1_8_R1;

import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.HologramEntity;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.logging.Level;

/**
 * Created by Matthew on 08/01/2015.
 */
public class HologramEntityControllerImpl implements HologramEntityController {

    @Override
    public EntityHologram spawnHologram(org.bukkit.World world, double x, double y, double z, HologramLine parentPiece) {
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        EntityHologram armorStand = new EntityHologram(nmsWorld, parentPiece);
        armorStand.setLocationNMS(x, y, z);
        if (!addEntityToWorld(nmsWorld, armorStand)) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to add armor stand entity to the world");
        }

        return armorStand;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public HologramEntity getHologramEntity(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        return nmsEntity instanceof HologramEntity ? (HologramEntity) nmsEntity : null;
    }
}
