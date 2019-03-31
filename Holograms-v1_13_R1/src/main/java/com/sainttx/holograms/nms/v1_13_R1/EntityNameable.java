package com.sainttx.holograms.nms.v1_13_R1;

import com.sainttx.holograms.api.entity.Nameable;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_13_R1.AxisAlignedBB;
import net.minecraft.server.v1_13_R1.DamageSource;
import net.minecraft.server.v1_13_R1.EntityArmorStand;
import net.minecraft.server.v1_13_R1.EntityHuman;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.EnumHand;
import net.minecraft.server.v1_13_R1.EnumInteractionResult;
import net.minecraft.server.v1_13_R1.ItemStack;
import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_13_R1.SoundEffect;
import net.minecraft.server.v1_13_R1.Vec3D;
import net.minecraft.server.v1_13_R1.World;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R1.util.CraftChatMessage;

public class EntityNameable extends EntityArmorStand implements Nameable {

    private boolean lockTick;
    private HologramLine parentPiece;

    public EntityNameable(World world, HologramLine parentPiece) {
        super(world);
        super.a(new NullBoundingBox()); // Forces the bounding box
        setInvisible(true);
        setSmall(true);
        setArms(false);
        setNoGravity(true);
        setBasePlate(true);
        setMarker(true);
        this.parentPiece = parentPiece;
        setLockTick(true);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
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
    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        return new NBTTagCompound();
    }


    @Override
    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public void setName(String text) {
        super.setCustomName(CraftChatMessage.fromStringOrNull(text));
        super.setCustomNameVisible(!text.isEmpty());
    }

    @Override
    public String getName() {
        return CraftChatMessage.fromComponent(super.getCustomName());
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        // Lock custom name
    }

    @Override
    public EnumInteractionResult a(EntityHuman entityhuman, Vec3D vec3d, EnumHand enumhand) {
        // Don't allow equipping armor stand
        return EnumInteractionResult.FAIL;
    }

    @Override
    public boolean c(int i, ItemStack item) {
        // Don't allow equipping armor stand
        return false;
    }

    @Override
    public void a(AxisAlignedBB boundingBox) {

    }

    @Override
    public int getId() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length > 2 && elements[2] != null && elements[2].getFileName().equals("EntityTrackerEntry.java") && elements[2].getLineNumber() > 137 && elements[2].getLineNumber() < 147) {
            return -1;
        }

        return super.getId();
    }

    @Override
    public void tick() {
        if (!lockTick) {
            super.playStepSound();
        }
    }

    @Override
    public void a(SoundEffect soundeffect, float f, float f1) {
        // Disable sound effects
    }

    public void setLockTick(boolean lock) {
        lockTick = lock;
    }

    @Override
    public void remove() {
        die();
    }

    @Override
    public void die() {
        setLockTick(false);
        super.die();
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            this.bukkitEntity = new CraftNameable(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);

        // Send a packet near to update the position.
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this);
        this.world.players.stream()
                .filter(p -> p instanceof EntityPlayer)
                .map(p -> (EntityPlayer) p)
                .forEach(p -> {
                    double distanceSquared = Math.pow(p.locX - this.locX, 2) + Math.pow(p.locZ - this.locZ, 2);
                    if (distanceSquared < 8192 && p.playerConnection != null) {
                        p.playerConnection.sendPacket(teleportPacket);
                    }
                });
    }

    @Override
    public HologramLine getHologramLine() {
        return parentPiece;
    }

}
