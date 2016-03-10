package com.sainttx.holograms.parser;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.animation.ItemAnimation;
import com.sainttx.holograms.api.line.AnimatedItemLine;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.util.TextUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimatedItemLineParser implements AnimatedItemLine.Parser {

    private static final Pattern linePattern = Pattern.compile("((animated|animation)_(item|icon|itemstack)(\\(([0-9]+)\\))?:)(.+)");

    @Override
    public boolean canParse(String text) {
        return linePattern.matcher(text).matches();
    }

    @Override
    public HologramLine parse(Hologram hologram, String text) {
        Matcher matcher = linePattern.matcher(text);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid raw text provided");
        }
        String delay = matcher.group(5);
        long parsedDelay = delay == null || delay.isEmpty() ? 5000 : Long.parseLong(delay);
        String items = matcher.group(6);
        String[] individualItems = items.split("\\|\\|");
        ItemAnimation animation = new ItemAnimation();
        for (String item : individualItems) {
            animation.addSlide(TextUtil.parseItem(item));
        }
        return new AnimatedItemLine(hologram, animation, parsedDelay);
    }
}
