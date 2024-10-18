package com.hrveklesarov.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.hrveklesarov.PseudoHardcore;

public class ItemCreationListener implements Listener {
    private PseudoHardcore plugin = (PseudoHardcore) Bukkit.getPluginManager().getPlugin(PseudoHardcore.NAME);

    @EventHandler
    public void enchantItem(EnchantItemEvent event) {
        this.tryAddCurseOfVanishing(event.getItem(), event.getEnchanter());
    }

    @EventHandler
    public void prepareAnvilEvent(PrepareAnvilEvent event) {
        this.tryAddCurseOfVanishing(event.getResult(), event.getView().getPlayer());
    }

    @EventHandler
    public void prepareGrindStoneEvent(PrepareGrindstoneEvent event) {
        this.tryAddCurseOfVanishing(event.getResult(), event.getView().getPlayer());
    }

    @EventHandler
    public void prepareSmithingEvent(PrepareSmithingEvent event) {
        this.tryAddCurseOfVanishing(event.getResult(), event.getView().getPlayer());
    }

    @EventHandler
    public void prepareItemCraftEvent(PrepareItemCraftEvent event) {
        Recipe recipe = event.getRecipe();
        if (recipe == null) {
            return;
        }
        this.tryAddCurseOfVanishing(event.getInventory().getResult(), event.getView().getPlayer());
    }

    private void tryAddCurseOfVanishing(ItemStack item, HumanEntity player) {
        if (this.isValidPlayer(player) == false) {
            return;
        }
        if (item == null) {
            return;
        }
        try {
            item.addEnchantment(Enchantment.VANISHING_CURSE, 1);
        } catch (IllegalArgumentException e) {
        }
    }

    private boolean isValidPlayer(HumanEntity player) {
        List<String> taggedPlayers = (List<String>) this.plugin.getConfig().getList(PseudoHardcore.TAGGED_PLAYERS_LIST);
        if (taggedPlayers == null) {
            return false;
        }

        Bukkit.getLogger().info(String.valueOf(taggedPlayers.size()));

        Player targetPlayer = Bukkit.getPlayer(player.getName());
        if (targetPlayer == null) {
            return false;
        }

        return taggedPlayers.stream().anyMatch((String playerName) -> {
            if (playerName == null) {
                return false;
            }

            Player taggedPlayer = Bukkit.getPlayer(playerName);
            if (taggedPlayer == null) {
                return false;
            }

            return taggedPlayer.getName().equals(targetPlayer.getName());
        });
    }
}
