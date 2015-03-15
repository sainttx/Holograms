package com.sainttx.holograms.nms.v1_8_R1;

import com.sainttx.holograms.data.HologramLine;
import com.sainttx.holograms.nms.NMSEntityBase;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;

import java.lang.reflect.Field;

/**
 * Created by Matthew on 28/01/2015.
 */
public class EntityNMSArmorStand extends EntityArmorStand implements NMSEntityBase {

    private boolean lockTick;
    private HologramLine parentPiece;

    public EntityNMSArmorStand(World world, HologramLine parentPiece) {
        super(world);
        super.a(new NullBoundingBox()); // Forces the bounding box
        setInvisible(true);
        setSmall(true);
        setArms(false);
        setGravity(true);
        setBasePlate(true);
        this.parentPiece = parentPiece;
        try {
            setPrivateField(EntityArmorStand.class, this, "bg", Integer.MAX_VALUE);
        } catch (Exception e) {
            // There's still the overridden method.
        }
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
    }

    @Override
    public boolean c(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }

    @Override
    public void e(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
    }


    @Override
    public boolean isInvulnerable(DamageSource source) {
        /*
         * The field Entity.invulnerable is private.
		 * It's only used while saving NBTTags, but since the entity would be killed
		 * on chunk unload, we prefer to override isInvulnerable().
		 */
        return true;
    }

    @Override
    public void setCustomName(String customName) {
        if (customName != null && customName.length() > 300) {
            customName = customName.substring(0, 300);
        }
        super.setCustomName(customName);
        super.setCustomNameVisible(customName != null && !customName.isEmpty());
    }

    @Override
    public String getCustomName() {
        return super.getCustomName();
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        // Locks the custom name.
    }

    @Override
    public boolean a(EntityHuman human, Vec3D vec3d) {
        // Prevent stand being equipped
        return true;
    }

    @Override
    public boolean d(int i, ItemStack item) {
        // Prevent stand being equipped
        return false;
    }

    @Override
    public void setEquipment(int i, ItemStack item) {
        // Prevent stand being equipped
    }

    @Override
    public void a(AxisAlignedBB boundingBox) {
        // Do not change it!
    }

    @Override
    public int getId() {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length > 2 && elements[2] != null && elements[2].getFileName().equals("EntityTrackerEntry.java") && elements[2].getLineNumber() > 137 && elements[2].getLineNumber() < 147) {
            // Then this method is being called when creating a new packet, we return a fake ID!
            return -1;
        }

        return super.getId();
    }

    @Override
    public void s_() {
        if (!lockTick) {
            super.s_();
        }
    }

    @Override
    public void makeSound(String sound, float f1, float f2) {
        // Remove sounds.
    }

    public void callSuperTick() {
        super.h();
    }

    @Override
    public void setLockTick(boolean lock) {
        lockTick = lock;
    }

    @Override
    public void die() {
        setLockTick(false);
        super.die();
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            this.bukkitEntity = new NMSArmorStand(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }

    public void setLocationNMS(double x, double y, double z) {
        super.setPosition(x, y, z);

        // Send a packet near to update the position.
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this);

        for (Object obj : this.world.players) {
            if (obj instanceof EntityPlayer) {
                EntityPlayer nmsPlayer = (EntityPlayer) obj;

                double distanceSquared = Math.pow(nmsPlayer.locX - this.locX, 2) + Math.pow(nmsPlayer.locZ - this.locZ, 2);
                if (distanceSquared < 8192 && nmsPlayer.playerConnection != null) {
                    nmsPlayer.playerConnection.sendPacket(teleportPacket);
                }
            }
        }
    }

    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

    public void setPrivateField(Class<?> clazz, Object handle, String fieldName, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(handle, value);
    }
}
