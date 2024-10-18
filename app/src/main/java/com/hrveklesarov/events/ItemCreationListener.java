package com.hrveklesarov.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.hrveklesarov.config.Config;

public class ItemCreationListener implements Listener {
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

    private boolean isValidPlayer(HumanEntity targetPlayer) {
        return Config.getTaggedPlayers().stream().anyMatch(taggedPlayer -> {
            return taggedPlayer.getName().equals(targetPlayer.getName());
        });
    }
}
