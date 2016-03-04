package com.sainttx.holograms.api.entity;

public interface MountedEntity extends HologramEntity {

    HologramEntity getMount();

    void setMount(HologramEntity entity);
}
