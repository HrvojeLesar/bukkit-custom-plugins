package com.hrveklesarov.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hrveklesarov.config.Config;

import net.md_5.bungee.api.ChatColor;

public class UntagPlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        for (String playerName : args) {
            if (playerName == null) {
                return false;
            }
            Player player = Bukkit.getPlayer(playerName);
            boolean result = Config.untagPlayer(player);

            if (result == false) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + playerName + ChatColor.RESET + ""
                        + ChatColor.RED + " not found or not tagged");
            }
        }

        return true;
    }
}
