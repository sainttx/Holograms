package com.sainttx.holograms.api;

import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.TextLine;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class HologramPlugin extends JavaPlugin {

    // Holds all registered line parsers
    private Set<HologramLine.Parser> parsers = new HashSet<>();

    /**
     * Parses and returns a {@link HologramLine} from a textual representation.
     * This method will return a {@link TextLine} if no valid parser is found.
     *
     * @param hologram the parent hologram
     * @param text the text
     * @return the created line
     */
    public HologramLine parseLine(Hologram hologram, String text) {
        Optional<HologramLine.Parser> parser = parsers.stream()
                .filter(p -> p.canParse(text))
                .findFirst();
        return parser.isPresent() ? parser.get().parse(hologram, text) : new TextLine(hologram, text);
    }

    /**
     * Registers a new line parser for creation/parsing of hologram lines.
     *
     * @param parser the parser
     * @return true if the parser was successfully added
     */
    public boolean addLineParser(HologramLine.Parser parser) {
        return parsers.add(parser);
    }

    /**
     * Returns the current active {@link HologramManager} implementation.
     *
     * @return the manager
     */
    public abstract HologramManager getHologramManager();

    /**
     * Returns the entity controller instance for the currently active
     * version of CraftBukkit software.
     *
     * @return the entity controller
     */
    public abstract HologramEntityController getEntityController();
}
