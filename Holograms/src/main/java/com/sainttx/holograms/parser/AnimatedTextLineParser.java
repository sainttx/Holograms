package com.sainttx.holograms.parser;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.animation.TextAnimation;
import com.sainttx.holograms.api.line.AnimatedTextLine;
import com.sainttx.holograms.api.line.HologramLine;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimatedTextLineParser implements AnimatedTextLine.Parser {

    private static final Pattern linePattern = Pattern.compile("(animation_text:)(.+)");

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
        String items = matcher.group(2);
        System.out.print(items);
        String[] individualItems = items.split("\\|\\|");
        System.out.print(Arrays.asList(individualItems));
        TextAnimation animation = new TextAnimation(individualItems);
        return new AnimatedTextLine(hologram, animation);
    }
}
