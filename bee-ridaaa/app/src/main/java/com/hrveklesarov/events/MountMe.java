package com.hrveklesarov.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MountMe implements Listener {
    public final String HAS_SADDLE = "has_saddle";
    public final String[] BEE_NAMES = {
            "Adam Flayman",
            "Barry B. Benson",
            "Bee Larry King",
            "Buzzwell",
            "Dave",
            "Jackson",
            "Lou Loduca",
            "Sandy Shrimpkin",
            "Splitz",
            "Trudy",
    };

    @EventHandler
    public void playerTouchesBee(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (!(entity instanceof Bee)) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Bee bee = (Bee) entity;

        if (this.hasSaddle(bee)) {
            bee.addPassenger(event.getPlayer());
        } else {
            tryAddSaddle(bee, event);
        }
    }

    @EventHandler
    public void beeDies(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.BEE) {
            return;
        }
        Bee bee = (Bee) event.getEntity();

        if (!this.hasSaddle(bee)) {
            return;
        }

        ItemStack saddle = new ItemStack(Material.SADDLE);

        event.getDrops().clear();
        bee.getWorld().dropItemNaturally(bee.getLocation(), saddle);
    }

    private void tryAddSaddle(Bee bee, PlayerInteractAtEntityEvent event) {
        PlayerInventory inventory = event.getPlayer().getInventory();
        ItemStack item = inventory.getItemInMainHand();
        if (item.getType() != Material.SADDLE) {
            return;
        }

        if (this.modifyBee(bee)) {
            item.setType(Material.AIR);
            inventory.setItem(inventory.getHeldItemSlot(), item);
        }
    }

    private boolean hasSaddle(Bee bee) {
        return bee.getScoreboardTags().contains(HAS_SADDLE);
    }

    private boolean modifyBee(Bee bee) {
        boolean result = bee.addScoreboardTag(HAS_SADDLE);
        if (result) {
            bee.setCustomName(getBeeName());
        }

        return result;
    }

    private String getBeeName() {
        if (Math.random() > 0.9D) {
            Random random = new Random();
            int index = random.ints(0, BEE_NAMES.length).findFirst().getAsInt();
            return BEE_NAMES[index];
        }

        return null;
    }
}
