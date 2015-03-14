package com.sainttx.holograms.nms;

import com.sainttx.holograms.data.HologramLine;

/**
 * Created by Matthew on 08/01/2015.
 */
public interface NMSEntityBase {

    // Returns the linked CraftHologramLine, all the entities are part of a piece. Should never be null.
    public HologramLine getHologramLine();
}
