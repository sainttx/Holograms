package com.sainttx.holograms.nms;

import com.sainttx.holograms.data.HologramLine;

/**
 * Created by Matthew on 08/01/2015.
 */
public interface NMSEntityBase {

    /**
     * Returns the HologramLine associated with the entity base
     *
     * @return The hologram line
     */
    HologramLine getHologramLine();

    /**
     * Kills the base hologram
     */
    void die();

    /**
     * Sets a new custom name for the entity base
     *
     * @param text The new custom name to set
     */
    void setCustomName(String text);

    /**
     * Returns the bases custom name
     *
     * @return The custom name of the base
     */
    String getCustomName();

    /**
     * Sets the lock tick value for the entity
     *
     * @param lockTick The lock tick value
     */
    void setLockTick(boolean lockTick);
}
