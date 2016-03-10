package com.sainttx.holograms.api.line;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.animation.Animation;

import java.util.Iterator;

public class AnimatedTextLine extends TextLine implements UpdatingHologramLine {

    private Animation<String> animation;
    private long lastUpdate;

    public AnimatedTextLine(Hologram parent, Animation<String> animation) {
        super(parent, "animation_text:" + animationToRaw(animation), animation.firstSlide());
        this.animation = animation;
    }

    // Converts an animation to raw format
    private static String animationToRaw(Animation<String> animation) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = animation.getSlides().iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append("||");
            }
        }
        return sb.toString();
    }

    @Override
    public void update() {
        setText(animation.nextSlide());
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
