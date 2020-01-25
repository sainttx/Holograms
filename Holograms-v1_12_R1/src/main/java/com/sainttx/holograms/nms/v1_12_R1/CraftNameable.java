package com.sainttx.holograms.nms.v1_12_R1;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Collection;

public class CraftNameable extends CraftArmorStand {

    public CraftNameable(CraftServer server, EntityNameable entity) {
        super(server, entity);
    }

    @Override
    public void setItemInHand(ItemStack item) {

    }

    @Override
    public void setBoots(ItemStack item) {

    }

    @Override
    public void setLeggings(ItemStack item) {

    }

    @Override
    public void setChestplate(ItemStack item) {

    }

    @Override
    public void setHelmet(ItemStack item) {

    }

    @Override
    public void setBodyPose(EulerAngle pose) {

    }

    @Override
    public void setLeftArmPose(EulerAngle pose) {

    }

    @Override
    public void setRightArmPose(EulerAngle pose) {

    }

    @Override
    public void setLeftLegPose(EulerAngle pose) {

    }

    @Override
    public void setRightLegPose(EulerAngle pose) {

    }

    @Override
    public void setHeadPose(EulerAngle pose) {

    }

    @Override
    public void setBasePlate(boolean basePlate) {

    }

    @Override
    public void setGravity(boolean gravity) {

    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public void setArms(boolean arms) {

    }

    @Override
    public void setSmall(boolean small) {

    }

    @Override
    public void setMarker(boolean marker) {

    }

    @Override
    public void setHealth(double health) {

    }

    @Override
    public void setMaxHealth(double amount) {

    }

    @Override
    public void resetMaxHealth() {

    }

    @Override
    public void setRemainingAir(int ticks) {

    }

    @Override
    public void setMaximumAir(int ticks) {

    }

    @Override
    public void damage(double amount) {

    }

    @Override
    public void damage(double amount, Entity source) {

    }

    @Override
    public void setMaximumNoDamageTicks(int ticks) {

    }

    @Override
    public void setLastDamage(double damage) {

    }

    @Override
    public void setNoDamageTicks(int ticks) {

    }

    @Override
    public boolean addPotionEffect(PotionEffect effect) {
        return false;
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        return false;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        return false;
    }

    @Override
    public void setRemoveWhenFarAway(boolean remove) {

    }

    @Override
    public void setCanPickupItems(boolean pickup) {

    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        return false;
    }

    @Override
    public boolean setLeashHolder(Entity holder) {
        return false;
    }

    @Override
    public void setGliding(boolean gliding) {

    }

    @Override
    public void setAI(boolean ai) {

    }

    @Override
    public void setCollidable(boolean collidable) {

    }

    @Override
    public void setVelocity(Vector velocity) {

    }

    @Override
    public boolean teleport(Location location) {
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
    public void setTicksLived(int value) {

    }

    @Override
    public void playEffect(EntityEffect type) {

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
    public void setPortalCooldown(int cooldown) {

    }
}