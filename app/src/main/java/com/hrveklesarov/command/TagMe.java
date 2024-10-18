package com.hrveklesarov.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hrveklesarov.config.Config;

import net.md_5.bungee.api.ChatColor;

public class TagMe implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String senderName = sender.getName();
        if (senderName == null) {
            return true;
        }
        Player player = Bukkit.getPlayer(senderName);
        boolean result = Config.tagPlayer(player);

        if (result == false) {
            sender.sendMessage(ChatColor.RED + "Already tagged");
        }

        return true;
    }
}
