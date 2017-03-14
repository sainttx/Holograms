package com.sainttx.holograms.nms.v1_8_R2;

import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.MinecraftVersion;
import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.entity.Nameable;
import com.sainttx.holograms.api.exception.HologramEntitySpawnException;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.logging.Level;

public class HologramEntityControllerImpl implements HologramEntityController {

    private HologramPlugin plugin;

    public HologramEntityControllerImpl(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public MinecraftVersion getMinecraftVersion() {
        return MinecraftVersion.V1_8_R2;
    }

    @Override
    public Nameable spawnNameable(HologramLine line, Location location) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityNameable armorStand = new EntityNameable(nmsWorld, line);
        armorStand.setPosition(location.getX(), location.getY(), location.getZ());
        if (!addEntityToWorld(nmsWorld, armorStand)) {
            plugin.getLogger().log(Level.WARNING, "Failed to spawn hologram entity in world " + location.getWorld().getName()
                    + " at x:" + location.getX() + " y:" + location.getY() + " z:" + location.getZ());
            armorStand.remove();
            return null;
        }

        return armorStand;
    }

    @Override
    public ItemHolder spawnItemHolder(HologramLine line, Location location) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityItemHolder item = new EntityItemHolder(nmsWorld, line);
        item.setPosition(location.getX(), location.getY(), location.getZ());
        if (!addEntityToWorld(nmsWorld, item)) {
            plugin.getLogger().log(Level.WARNING, "Failed to spawn item entity in world " + location.getWorld().getName()
                    + " at x:" + location.getX() + " y:" + location.getY() + " z:" + location.getZ());
            item.remove();
            return null;
        }
        return item;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        net.minecraft.server.v1_8_R2.Chunk nmsChunk = nmsWorld.getChunkAtWorldCoords(nmsEntity.getChunkCoordinates());

        if (nmsChunk != null) {
            Chunk chunk = nmsChunk.bukkitChunk;

            if (chunk == null) {
                try {
                    chunk = new CraftChunk(nmsChunk);
                } catch (NullPointerException e) {
                    throw new HologramEntitySpawnException("Attempted to spawn hologram entity in invalid chunk", e);
                }
            }

            if (!chunk.isLoaded()) {
                chunk.load();
                plugin.getLogger().info("Loaded chunk (x:" + chunk.getX() + " z:" + chunk.getZ() + ") to spawn a Hologram");
            }
        }

        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public HologramEntity getHologramEntity(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        return nmsEntity instanceof HologramEntity ? (HologramEntity) nmsEntity : null;
    }
}
