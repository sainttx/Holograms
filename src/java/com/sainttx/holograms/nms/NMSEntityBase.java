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
    public HologramLine getHologramLine();

    /**
     * Kills the base hologram
     */
    public void die();

    /**
     * Sets a new custom name for the entity base
     *
     * @param text The new custom name to set
     */
    public void setCustomName(String text);

    /**
     * Returns the bases custom name
     *
     * @return The custom name of the base
     */
    public String getCustomName();

    /**
     * Sets the lock tick value for the entity
     *
     * @param lockTick The lock tick value
     */
    public void setLockTick(boolean lockTick);
}
