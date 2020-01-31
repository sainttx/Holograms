package com.sainttx.holograms.nms.v1_8_R2;

import com.sainttx.holograms.api.entity.Nameable;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_8_R2.AxisAlignedBB;
import net.minecraft.server.v1_8_R2.DamageSource;
import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.NBTTagCompound;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R2.Vec3D;
import net.minecraft.server.v1_8_R2.World;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;

public class EntityNameable extends EntityArmorStand implements Nameable {

    private boolean lockTick;
    private HologramLine parentPiece;
    private boolean disableFakeId;

    public EntityNameable(World world, HologramLine parentPiece) {
        super(world);
        setInvisible(true);
        setSmall(true);
        setArms(false);
        setGravity(true);
        setBasePlate(true);
        setMarker(true);
        this.parentPiece = parentPiece;
    }

    /* Since super.n(boolean) is private, we override to set marker flag */
    private void setMarker(boolean flag) {
        byte b0 = this.datawatcher.getByte(10);
        if(flag) {
            b0 = (byte)(b0 | 16);
        } else {
            b0 &= -17;
        }

        this.datawatcher.watch(10, b0);
    }

    @Override
    public void setName(String text) {
        if (text.length() > 256) {
            text = text.substring(0, 256);
        }
        super.setCustomName(text);
        super.setCustomNameVisible(!text.isEmpty());
    }

    @Override
    public String getName() {
        return super.getCustomName();
    }

    public void setLockTick(boolean lock) {
        lockTick = lock;
    }

    @Override
    public void remove() {
        this.dead = true;
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

    // Overriden NMS methods

    @Override
    public void a(NBTTagCompound nbttagcompound) {

    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {

    }

    @Override
    public boolean c(NBTTagCompound nbttagcompound) {
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    @Override
    public void e(NBTTagCompound nbttagcompound) {

    }

    @Override
    public void f(NBTTagCompound nbttagcompound) {

    }

    @Override
    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public void setCustomName(String name) {

    }

    @Override
    public void setCustomNameVisible(boolean visible) {

    }

    @Override
    public boolean a(EntityHuman human, Vec3D vec3d) {
        return true;
    }

    @Override
    public boolean d(int i, ItemStack item) {
        return false;
    }

    @Override
    public void setEquipment(int i, ItemStack item) {

    }

    @Override
    public void a(AxisAlignedBB boundingBox) {

    }

    @Override
    public void t_() {
        if (!lockTick) {
            super.t_();
        }
    }

    @Override
    public void makeSound(String sound, float f1, float f2) {

    }

    @Override
    public void setInvisible(boolean flag) {
        super.setInvisible(true);
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    @Override
    public void die() {

    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            this.bukkitEntity = new CraftNameable(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }
}
