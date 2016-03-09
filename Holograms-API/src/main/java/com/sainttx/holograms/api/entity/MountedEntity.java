package com.sainttx.holograms.api.entity;

public interface MountedEntity extends HologramEntity {

    /**
     * Returns the current mount that this entity is sitting on.
     *
     * @return the mount
     */
    HologramEntity getMount();

    /**
     * Mounts this item to a new entity. Passing in a null value
     * will remove any mount that this entity is currently sitting on.
     *
     * @param entity new mount
     */
    void setMount(HologramEntity entity);
}
