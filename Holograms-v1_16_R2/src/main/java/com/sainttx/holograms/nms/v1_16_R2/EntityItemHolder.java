package com.sainttx.holograms.nms.v1_16_R2;

import javax.annotation.Nullable;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;

import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.line.HologramLine;

public class EntityItemHolder extends EntityItem implements ItemHolder {

    private boolean lockTick;
    private HologramLine line;
    private org.bukkit.inventory.ItemStack item;
    private CraftEntity bukkitEntity;

    public EntityItemHolder(World world, HologramLine line) {
        super(EntityTypes.ITEM, world);
        this.line = line;
        this.noclip = true;
    }

    public void setLockTick(boolean lockTick) {
        this.lockTick = lockTick;
    }

    @Override
    public HologramLine getHologramLine() {
        return line;
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public void remove() {
        this.dead = true;
        if (isPassenger()) {
            getVehicle().dead = true;
        }
    }

    @Override
    public void setItem(org.bukkit.inventory.ItemStack item) {
        ItemStack nms = CraftItemStack.asNMSCopy(item);
        if (nms == null || nms == ItemStack.b) {
            nms = new ItemStack(Blocks.BARRIER);
        }
        this.item = item;
        super.setItemStack(nms);
    }

    @Override
    public org.bukkit.inventory.ItemStack getItem() {
        return item;
    }

    @Override
    public HologramEntity getMount() {
        return (HologramEntity) getVehicle();
    }

    @Override
    public void setMount(HologramEntity entity) {
        if (entity instanceof Entity) {
            this.startRiding((Entity) entity);
        }
    }

    // Overriden NMS methods

    @Override
    public void tick() {
        this.s();
        this.p();
        this.ticksLived = 0;
        if (!lockTick) {
            super.tick();
        }
    }

    @Override
    public void postTick() {
        if (!lockTick) {
            super.postTick();
        }
    }

    @Override
    public void entityBaseTick() {
        if (!lockTick) {
            super.entityBaseTick();
        }
    }

    @Override
    public void loadData(NBTTagCompound nbttagcompound) {

    }

    @Override
    public void saveData(NBTTagCompound nbttagcompound) {
    	
    }

    @Override
    public boolean a_(NBTTagCompound nbttagcompound) {
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    @Override
    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        return new NBTTagCompound();
    }

    @Override
    public void load(NBTTagCompound nbttagcompound) {
    	
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    @Override
    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public void die() {

    }

    @Override
    public void killEntity() {

    }

    @Override
    public void setPickupDelay(int i) {
        super.setPickupDelay(Integer.MAX_VALUE);
    }

/*  @Override TODO Cannot find this method. Maybe it was removed
    protected void burn(float i) {

    }*/

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    @Override
    public void pickup(EntityHuman entityhuman) {

    }

    @Nullable
    @Override
    public ShapeDetectorShape a(WorldServer dimensionmanager) {
        return null;
    }

    @Override
    public void setItemStack(ItemStack itemstack) {

    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null) {
            this.bukkitEntity = new CraftItemHolder(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }
}