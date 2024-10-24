package com.hrveklesarov;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hrveklesarov.command.TagMe;
import com.hrveklesarov.command.TagPlayer;
import com.hrveklesarov.command.UntagPlayer;
import com.hrveklesarov.events.ItemCreationListener;
import com.hrveklesarov.events.MountMe;

public class PseudoHardcore extends JavaPlugin {
    public static final String NAME = "PseudoHardcore";

    private PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled plugin: " + this.getName());
        this.createDefaultConfig();
        this.registerCommands();
        this.registerEvents();
    }

    private void createDefaultConfig() {
        this.saveDefaultConfig();
    }

    private void registerCommands() {
        this.getCommand("hc-tag").setExecutor(new TagPlayer());
        this.getCommand("hc-tagme").setExecutor(new TagMe());
        this.getCommand("hc-untag").setExecutor(new UntagPlayer());
    }

    private void registerEvents() {
        this.pluginManager.registerEvents(new ItemCreationListener(), this);
        this.pluginManager.registerEvents(new MountMe(), this);
    }
}
