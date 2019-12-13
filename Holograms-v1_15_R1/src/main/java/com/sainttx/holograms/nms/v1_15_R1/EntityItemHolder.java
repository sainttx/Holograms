package com.sainttx.holograms.nms.v1_15_R1;

import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.line.HologramLine;
import java.lang.reflect.Field;
import net.minecraft.server.v1_15_R1.DamageSource;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.EntityItem;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.ItemStack;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import net.minecraft.server.v1_15_R1.NBTTagString;
import net.minecraft.server.v1_15_R1.PacketPlayOutMount;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EntityItemHolder extends EntityItem implements ItemHolder {

    private static final Field vehicleField;
    static {
        try {
            vehicleField = Entity.class.getDeclaredField("vehicle");
            vehicleField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean lockTick;
    private HologramLine line;
    private org.bukkit.inventory.ItemStack item;
    private CraftEntity bukkitEntity;

    public EntityItemHolder(World world, HologramLine line) {
        super(EntityTypes.ITEM, world);
        this.line = line;
        super.pickupDelay = Integer.MAX_VALUE;
        this.lockTick = true;
    }

    @Override
    public void tick() {
        ticksLived = 0;

        if (!lockTick) {
            super.tick();
        }
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
    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
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
    public CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null) {
            this.bukkitEntity = new CraftItemHolder(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }

    @Override
    public HologramLine getHologramLine() {
        return line;
    }

    @Override
    public void remove() {
        super.dead = true;
        Entity vehicle = super.getVehicle();
        if (vehicle != null) {
            vehicle.dead = true;
        }
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
            tagList.add(NBTTagString.a(getRandomString()));

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
        try {
            return (HologramEntity) vehicleField.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setMount(HologramEntity entity) {
        if (!(entity instanceof Entity)) {
            return;
        }

        Entity old = super.getVehicle();
        if (old != null) {
            old.passengers.remove(this);
        }

        Entity next = (Entity) entity;
        try {
            vehicleField.set(this, next);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        next.passengers.clear();
        next.passengers.add(this);
    }
}