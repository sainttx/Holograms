package com.sainttx.holograms.parser;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.animation.TextAnimation;
import com.sainttx.holograms.api.line.AnimatedTextLine;
import com.sainttx.holograms.api.line.HologramLine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimatedTextLineParser implements AnimatedTextLine.Parser {

    private static final Pattern linePattern = Pattern.compile("((animated|animation)_text(\\(([0-9]+)\\))?:)(.+)");

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

        String delay = matcher.group(4);
        long parsedDelay = delay == null || delay.isEmpty() ? 5000 : Long.parseLong(delay);
        String items = matcher.group(5);
        String[] individualItems = items.split("\\|\\|");
        TextAnimation animation = new TextAnimation(individualItems);
        return new AnimatedTextLine(hologram, animation, parsedDelay);
    }
}
