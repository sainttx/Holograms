package com.sainttx.holograms.nms.v1_14_R1;

import com.sainttx.holograms.api.entity.Nameable;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftChatMessage;

import javax.annotation.Nullable;

public class EntityNameable extends EntityArmorStand implements Nameable {

    private boolean lockTick;
    private HologramLine parentPiece;
    private CraftEntity bukkitEntity;

    public EntityNameable(World world, HologramLine parentPiece) {
        super(EntityTypes.ARMOR_STAND, world);
        super.collides = false;
        setInvisible(true);
        setSmall(true);
        setArms(false);
        setNoGravity(true);
        setBasePlate(true);
        setMarker(true);
        this.parentPiece = parentPiece;
    }

    public void setLockTick(boolean lock) {
        lockTick = lock;
    }

    @Override
    public String getName() {
        return CraftChatMessage.fromComponent(super.getCustomName());
    }

    @Override
    public void setName(String text) {
        super.setCustomName(CraftChatMessage.fromStringOrNull(text));
        super.setCustomNameVisible(!text.isEmpty());
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

    @Override
    public void remove() {
        this.dead = true;
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
    public void f(NBTTagCompound nbttagcompound) {

    }

    @Override
    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        return new NBTTagCompound();
    }

    @Override
    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public void setCustomName(@Nullable IChatBaseComponent ichatbasecomponent) {

    }

    @Override
    public void setCustomNameVisible(boolean visible) {

    }

    @Override
    public EnumInteractionResult a(EntityHuman entityhuman, Vec3D vec3d, EnumHand enumhand) {
        return EnumInteractionResult.FAIL;
    }

    @Override
    public boolean a_(int i, ItemStack item) {
        return false;
    }

    @Override
    public void a(AxisAlignedBB boundingBox) {

    }

    @Override
    public void tick() {
        if (!lockTick) {
            super.playStepSound();
        }
    }

    @Override
    public void a(SoundEffect soundeffect, float f, float f1) {

    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void setInvisible(boolean flag) {
        super.setInvisible(true);
    }


    @Override
    public void setSlot(EnumItemSlot enumitemslot, ItemStack itemstack) {

    }

    @Override
    public void killEntity() {

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
        if (this.bukkitEntity == null) {
            this.bukkitEntity = new CraftNameable(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }

}
