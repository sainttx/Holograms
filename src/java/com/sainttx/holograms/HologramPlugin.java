package com.sainttx.holograms;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Matthew on 14/03/2015.
 */
public class HologramPlugin extends JavaPlugin {

    private HologramManager manager;

    /*
    TODO
     * hologram create name text
     * hologram delete name
     * hologram list
     * hologram near [radius]
     * holo movehere name
     * edit->
     * addline name index
     * removeline name index
     * insertline name index
     * info name
     */

    @Override
    public void onEnable() {
        this.manager = new HologramManager(this);
        getServer().getPluginManager().registerEvents(new HologramListener(manager), this);
    }
}
