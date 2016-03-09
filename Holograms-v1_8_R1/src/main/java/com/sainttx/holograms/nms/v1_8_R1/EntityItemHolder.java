package com.sainttx.holograms.nms.v1_8_R1;

import com.sainttx.holograms.api.entity.HologramEntity;
import com.sainttx.holograms.api.entity.ItemHolder;
import com.sainttx.holograms.api.line.HologramLine;
import net.minecraft.server.v1_8_R1.DamageSource;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityItem;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.NBTTagString;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EntityItemHolder extends EntityItem implements ItemHolder {

    private boolean lockTick;
    private HologramLine line;
    private Entity vehicle;
    private org.bukkit.inventory.ItemStack item;

    public EntityItemHolder(World world, HologramLine line) {
        super(world);
        this.line = line;
        super.pickupDelay = Integer.MAX_VALUE;
        this.lockTick = true;
    }

    @Override
    public void s_() {
        ticksLived = 0;
        if (!lockTick) {
            super.m();
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
    public void e(NBTTagCompound nbttagcompound) {
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
        if (entity == null) {
            removeMount();
        } else if (entity instanceof Entity) {
            removeMount();
            vehicle = (Entity) entity;
            mount(vehicle);
            vehicle.passenger = this;
        }
    }

    // Removes the current mount
    private void removeMount() {
        if (vehicle != null) {
            mount(null);
            vehicle.passenger = null;
            vehicle.die();
            vehicle = null;
        }
    }
}