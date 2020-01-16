package com.sainttx.holograms.nms.v1_9_R2;

import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EntityItemHolder extends EntityItem implements ItemHolder {

    private boolean lockTick;
    private HologramLine line;
    private Entity vehicle;
    private int mountPacketTick;
    private org.bukkit.inventory.ItemStack item;

    public EntityItemHolder(World world, HologramLine line) {
        super(world);
        this.line = line;
        super.pickupDelay = Integer.MAX_VALUE;
        super.v();
    }

    public void setLockTick(boolean lockTick) {
        this.lockTick = lockTick;
    }

    @Override
    public void d(EntityHuman entityhuman) {

    }

    @Override
    public void m() {
        super.v();
        ticksLived = 0;
        if (mountPacketTick++ > 20) {
            mountPacketTick = 0;
            mountPacket();
        }
        if (!lockTick) {
            super.m();
        }
    }

    // Sends a packet to notify nearby players that this entity is mounted
    private void mountPacket() {
        // Send packet to update passenger state
        PacketPlayOutMount packet = new PacketPlayOutMount(this.vehicle);
        world.players.stream()
                .filter(e -> e instanceof EntityPlayer)
                .map(e -> (EntityPlayer) e)
                .forEach(p -> {
                    double distanceSquared = Math.pow(p.locX - this.locX, 2) + Math.pow(p.locZ - this.locZ, 2);
                    if (distanceSquared < 1024 && p.playerConnection != null) {
                        p.playerConnection.sendPacket(packet);
                    }
                });
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
    public NBTTagCompound e(NBTTagCompound nbttagcompound) {
        return new NBTTagCompound();
    }

    @Override
    public void f(NBTTagCompound nbttagcompound) {
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
    }

    @Override
    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public void die() {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null) {
            this.bukkitEntity = new CraftItemHolder(this.world.getServer(), this);
        }
        return this.bukkitEntity;
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
        this.lockTick = false;
        super.die();
    }

    @Override
    public void setItem(org.bukkit.inventory.ItemStack item) {
        ItemStack nms = CraftItemStack.asNMSCopy(item);

        if (nms != null) {
            if (nms.getTag() == null) {
                nms.setTag(new NBTTagCompound());
            }

            NBTTagCompound display = nms.getTag().getCompound("display");
            if (!nms.getTag().hasKey("display")) {
                nms.getTag().set("display", display);
            }

            NBTTagList tagList = new NBTTagList();
            tagList.add(new NBTTagString(getRandomString()));
            display.set("Lore", tagList);
        }
        this.item = item;
        setItemStack(nms);
    }

    // Returns a random string
    private String getRandomString() {
        return Double.toString(ThreadLocalRandom.current().nextDouble());
    }

    @Override
    public org.bukkit.inventory.ItemStack getItem() {
        return item;
    }

    @Override
    public HologramEntity getMount() {
        return (HologramEntity) vehicle;
    }

    @Override
    public void setMount(HologramEntity entity) {
        if (entity instanceof Entity) {
            removeMount();
            vehicle = (Entity) entity;
            super.a(vehicle, true);
            vehicle.passengers.add(this);
        }
    }

    // Removes the current mount
    private void removeMount() {
        if (vehicle != null) {
            vehicle.passengers.remove(this);
            vehicle = null;
        }
    }
}