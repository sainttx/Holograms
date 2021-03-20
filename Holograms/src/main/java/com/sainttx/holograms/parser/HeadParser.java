package com.sainttx.holograms.parser;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.HeadLine;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;

import java.util.regex.Pattern;

public class HeadParser implements ItemLine.Parser {

    private static final Pattern linePattern = Pattern.compile("((head):)(.+)");

    @Override
    public boolean canParse(String text) {
        return linePattern.matcher(text).matches();
    }

    @Override
    public HologramLine parse(Hologram hologram, String text) {
        return new HeadLine(hologram, text);
    }
}
