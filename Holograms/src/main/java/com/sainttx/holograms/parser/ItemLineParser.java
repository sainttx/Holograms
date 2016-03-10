package com.sainttx.holograms.parser;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;

import java.util.regex.Pattern;

public class ItemLineParser implements ItemLine.Parser {

    private static final Pattern linePattern = Pattern.compile("((item|icon|itemstack):)(.+)");

    @Override
    public boolean canParse(String text) {
        return linePattern.matcher(text).matches();
    }

    @Override
    public HologramLine parse(Hologram hologram, String text) {
        return new ItemLine(hologram, text);
    }
}
