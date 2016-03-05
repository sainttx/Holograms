package com.sainttx.holograms.nms.v1_8_R3;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CraftItemHolder extends CraftItem {
    
    public CraftItemHolder(CraftServer server, EntityItemHolder entity) {
        super(server, entity);
    }

    @Override
    public void remove() {
    }

    @Override
    public void setVelocity(Vector vel) {
    }

    @Override
    public boolean teleport(Location loc) {
        return false;
    }

    @Override
    public boolean teleport(Entity entity) {
        return false;
    }

    @Override
    public boolean teleport(Location loc, PlayerTeleportEvent.TeleportCause cause) {
        return false;
    }

    @Override
    public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause cause) {
        return false;
    }

    @Override
    public void setFireTicks(int ticks) {
    }

    @Override
    public boolean setPassenger(Entity entity) {
        return false;
    }

    @Override
    public boolean eject() {
        return false;
    }

    @Override
    public boolean leaveVehicle() {
        return false;
    }

    @Override
    public void playEffect(EntityEffect effect) {
    }

    @Override
    public void setCustomName(String name) {
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
    }

    @Override
    public void setItemStack(ItemStack stack) {
    }

    @Override
    public void setPickupDelay(int delay) {
    }
}
