package com.sainttx.holograms.nms.v1_9_R1;

import com.sainttx.holograms.api.HologramLine;
import com.sainttx.holograms.api.NMSController;
import com.sainttx.holograms.api.NMSEntityBase;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityTypes;
import net.minecraft.server.v1_9_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Matthew on 08/01/2015.
 */
public class NMSControllerImpl implements NMSController {

    @Override
    public void setup() {
        try {
            registerCustomEntity(NMSEntityArmorStandExtend.class, "ArmorStand", 30);
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Could not register Slime and ArmorStand properly");
        }
    }

    private void registerCustomEntity(Class entityClass, String name, int id) throws NoSuchFieldException, IllegalAccessException {
        putInPrivateStaticMap(EntityTypes.class, "d", entityClass, name);
        putInPrivateStaticMap(EntityTypes.class, "f", entityClass, Integer.valueOf(id));
    }

    private void putInPrivateStaticMap(Class<?> clazz, String fieldName, Object key, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map map = (Map) field.get(null);
        map.put(key, value);
    }

    @Override
    public NMSEntityArmorStandExtend spawnArmorStand(org.bukkit.World world, double x, double y, double z, HologramLine parentPiece) {
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        NMSEntityArmorStandExtend armorStand = new NMSEntityArmorStandExtend(nmsWorld, parentPiece);
        armorStand.setLocationNMS(x, y, z);
        if (!addEntityToWorld(nmsWorld, armorStand)) {
            System.out.print("Could not spawn armor stand");
        }

        return armorStand;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        net.minecraft.server.v1_9_R1.Chunk nmsChunk = nmsWorld.getChunkAtWorldCoords(nmsEntity.getChunkCoordinates());

        if (nmsChunk != null) {
            Chunk chunk = nmsChunk.bukkitChunk;

            if (!chunk.isLoaded()) {
                chunk.load();
                System.out.print("Loaded chunk " + chunk + " for hologram");
            }
        } else {
            System.out.print("Chunk not found for Hologram");
        }

        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public NMSEntityBase getNMSEntityBase(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        return nmsEntity instanceof NMSEntityBase ? (NMSEntityBase) nmsEntity : null;
    }
}
