package com.hrveklesarov;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hrveklesarov.events.MountMe;

public class BeeRidaaa extends JavaPlugin {
    public static final String NAME = "BeeRidaaa";

    private PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled plugin: " + this.getName());
        this.registerEvents();
    }

    private void registerEvents() {
        this.pluginManager.registerEvents(new MountMe(), this);
    }
}
