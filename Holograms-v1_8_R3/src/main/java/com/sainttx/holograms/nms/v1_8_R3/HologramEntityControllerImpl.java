package com.sainttx.holograms.nms.v1_8_R3;

import com.sainttx.holograms.api.HologramEntityController;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.MinecraftVersion;
import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.entity.Nameable;
import com.sainttx.holograms.api.exception.HologramEntitySpawnException;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.logging.Level;

public class HologramEntityControllerImpl implements HologramEntityController {

    private HologramPlugin plugin;

    public HologramEntityControllerImpl(HologramPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public MinecraftVersion getMinecraftVersion() {
        return MinecraftVersion.V1_8_R3;
    }

    @Override
    public EntityNameable spawnNameable(HologramLine line, Location location) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityNameable armorStand = new EntityNameable(nmsWorld, line);
        armorStand.setPosition(location.getX(), location.getY(), location.getZ());
        if (!addEntityToWorld(nmsWorld, armorStand)) {
            armorStand.remove();
            return null;
        }

        armorStand.setLockTick(true);
        return armorStand;
    }

    @Override
    public ItemHolder spawnItemHolder(HologramLine line, Location location) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityItemHolder item = new EntityItemHolder(nmsWorld, line);
        Location armorStandPosition = location.clone();
        location.setY(location.getY() + line.getHeight());
        EntityNameable armorStand = spawnNameable(line, armorStandPosition);
        item.setPosition(location.getX(), location.getY(), location.getZ());
        if (!addEntityToWorld(nmsWorld, item)) {
            item.remove();
            return null;
        }
        if (armorStand != null) {
            item.setMount(armorStand);
        }
        item.setLockTick(true);
        return item;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        // Validate the chunk is loaded
        final int chunkX = MathHelper.floor(nmsEntity.locX / 16.0);
        final int chunkZ = MathHelper.floor(nmsEntity.locZ / 16.0);

        if (!nmsWorld.chunkProviderServer.isChunkLoaded(chunkX, chunkZ)) {
            nmsEntity.dead = true;
            return false;
        }

        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public HologramEntity getHologramEntity(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        return nmsEntity instanceof HologramEntity ? (HologramEntity) nmsEntity : null;
    }
}
