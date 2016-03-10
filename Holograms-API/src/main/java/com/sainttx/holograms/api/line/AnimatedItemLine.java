package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.animation.Animation;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class AnimatedItemLine extends ItemLine implements UpdatingHologramLine {

    private Animation<ItemStack> animation;
    private long lastUpdate;

    public AnimatedItemLine(Hologram parent, Animation<ItemStack> animation) {
        super(parent, "animation_item:" + animationToRaw(animation));
    }

    // Converts an animation to raw format
    private static String animationToRaw(Animation<ItemStack> animation) {
        StringBuilder sb = new StringBuilder();
        Iterator<ItemStack> iterator = animation.getSlides().iterator();
        while (iterator.hasNext()) {
            sb.append(itemstackToRaw(iterator.next()));
            if (iterator.hasNext()) {
                sb.append("\n"); // TODO: Is \n a good idea?
            }
        }
        return sb.toString();
    }

    @Override
    public void update() {
        setItem(animation.nextSlide());
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public long getDelay() {
        return 0L;
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdate;
    }
}
