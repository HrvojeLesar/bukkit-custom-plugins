package com.hrveklesarov.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.hrveklesarov.PseudoHardcore;

public class Config {
    public static final String TAGGED_PLAYERS_LIST = "tagged_players";
    public static final PseudoHardcore PLUGIN = getPlugin();
    public static final FileConfiguration CONFIG_FILE = getConfigFile();

    private static List<Player> taggedPlayers = null;

    public static List<Player> getTaggedPlayers() {
        return Collections.unmodifiableList(getTaggedPlayersInner());
    }

    public static boolean tagPlayer(Player player) {
        if (player == null) {
            return false;
        }

        if (getTaggedPlayersInner().stream().anyMatch(p -> p.getName().equals(player.getName()))) {
            return false;
        }

        getTaggedPlayersInner().add(player);
        saveConfig();

        return true;
    }

    public static boolean untagPlayer(Player player) {
        boolean result = true;

        int size = getTaggedPlayersInner().size();
        taggedPlayers = getTaggedPlayersInner().stream().filter(p -> !p.getName().equals(player.getName()))
                .collect(Collectors.toList());
        int newSize = taggedPlayers.size();
        if (newSize == size) {
            result = false;
        }
        saveConfig();

        return result;
    }

    private static List<Player> getTaggedPlayersInner() {
        if (taggedPlayers == null) {
            taggedPlayers = CONFIG_FILE.getList(TAGGED_PLAYERS_LIST).stream()
                    .filter(obj -> obj instanceof String)
                    .map((playerName) -> Bukkit.getPlayer((String) playerName))
                    .filter(obj -> obj instanceof Player)
                    .collect(Collectors.toList());
        }

        return taggedPlayers;
    }

    private static void saveConfig() {
        CONFIG_FILE.set(TAGGED_PLAYERS_LIST,
                getTaggedPlayersInner().stream().map(player -> player.getName()).collect(Collectors.toList()));
        PLUGIN.saveConfig();
    }

    private static PseudoHardcore getPlugin() {
        return (PseudoHardcore) Bukkit.getPluginManager().getPlugin(PseudoHardcore.NAME);
    }

    private static FileConfiguration getConfigFile() {
        return PLUGIN.getConfig();
    }
}
