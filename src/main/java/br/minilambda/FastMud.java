package br.minilambda;

import org.bukkit.plugin.java.JavaPlugin;

import br.minilambda.event.DirtToMudUsingWater;

public class FastMud extends JavaPlugin {
    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new DirtToMudUsingWater(), this);
    }
}
