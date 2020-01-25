package com.sainttx.holograms.nms.v1_14_R1;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CraftItemHolder extends CraftItem {

    public CraftItemHolder(CraftServer server, EntityItemHolder entity) {
        super(server, entity);
    }

    @Override
    public void setItemStack(ItemStack stack) {

    }

    @Override
    public void setPickupDelay(int delay) {

    }

    @Override
    public void setTicksLived(int value) {

    }

    @Override
    public void playEffect(EntityEffect type) {

    }

    @Override
    public void setVelocity(Vector velocity) {

    }

    @Override
    public void setRotation(float yaw, float pitch) {

    }

    @Override
    public boolean teleport(Location location) {
        return false;
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        return false;
    }

    @Override
    public boolean teleport(Entity destination) {
        return false;
    }

    @Override
    public boolean teleport(Entity destination, PlayerTeleportEvent.TeleportCause cause) {
        return false;
    }

    @Override
    public void setFireTicks(int ticks) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void setPersistent(boolean persistent) {

    }

    @Override
    public void setMomentum(Vector value) {

    }

    @Override
    public boolean setPassenger(Entity passenger) {
        return false;
    }

    @Override
    public boolean addPassenger(Entity passenger) {
        return false;
    }

    @Override
    public boolean removePassenger(Entity passenger) {
        return false;
    }

    @Override
    public boolean eject() {
        return false;
    }

    @Override
    public void setFallDistance(float distance) {

    }

    @Override
    public boolean leaveVehicle() {
        return false;
    }

    @Override
    public void setCustomName(String name) {

    }

    @Override
    public void setCustomNameVisible(boolean flag) {

    }

    @Override
    public void setGlowing(boolean flag) {

    }

    @Override
    public void setInvulnerable(boolean flag) {

    }

    @Override
    public void setSilent(boolean flag) {

    }

    @Override
    public void setGravity(boolean gravity) {

    }

    @Override
    public void setPortalCooldown(int cooldown) {

    }
}
