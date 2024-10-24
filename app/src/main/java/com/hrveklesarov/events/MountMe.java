package com.hrveklesarov.events;

import org.bukkit.Material;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MountMe implements Listener {
    public final String HAS_SADDLE = "has_saddle";

    @EventHandler
    public void playerTocuhesBee(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (!(entity instanceof Bee)) {
            return;
        }

        Bee bee = (Bee) entity;

        if (this.hasSaddle(bee)) {
            bee.addPassenger(event.getPlayer());
        } else {
            tryAddSaddle(bee, event);
        }
    }

    private void tryAddSaddle(Bee bee, PlayerInteractAtEntityEvent event) {
        PlayerInventory inventory = event.getPlayer().getInventory();
        ItemStack item = inventory.getItemInMainHand();
        if (item.getType() != Material.SADDLE) {
            return;
        }

        if (bee.addScoreboardTag(HAS_SADDLE)) {
            item.setType(Material.AIR);
            inventory.setItem(inventory.getHeldItemSlot(), item);

        }

    }

    private boolean hasSaddle(Bee bee) {
        return bee.getScoreboardTags().contains(HAS_SADDLE);
    }
}
