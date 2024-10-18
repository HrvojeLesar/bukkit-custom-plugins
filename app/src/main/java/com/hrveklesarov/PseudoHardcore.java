package com.hrveklesarov;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hrveklesarov.events.ItemCreationListener;

public class PseudoHardcore extends JavaPlugin {
    public static final String NAME = "PseudoHardcore";
    public static final String TAGGED_PLAYERS_LIST = "tagged_players";

    private PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled plugin: " + this.getName());
        this.createDefaultConfig();
        this.registerEvents();
    }

    private void createDefaultConfig() {
        this.saveDefaultConfig();
    }

    private void registerEvents() {
        this.pluginManager.registerEvents(new ItemCreationListener(), this);
    }
}
