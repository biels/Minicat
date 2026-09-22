package com.biel.lobby.mapes.jocs.roborampage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/** Keeps consumed potion bottles out of combat hotbar slots. */
final class HotbarBottleManager {
    private static final int HOTBAR_SIZE = 9;
    private static final int STORAGE_END_EXCLUSIVE = 36;

    private HotbarBottleManager() {}

    static void moveBottleToStorage(Player player, int consumedHotbarSlot) {
        if (consumedHotbarSlot < 0 || consumedHotbarSlot >= HOTBAR_SIZE) return;
        PlayerInventory inventory = player.getInventory();
        ItemStack bottle = inventory.getItem(consumedHotbarSlot);
        if (bottle == null || bottle.getType() != Material.GLASS_BOTTLE) return;

        ItemStack remaining = bottle.clone();
        for (int slot = HOTBAR_SIZE; slot < STORAGE_END_EXCLUSIVE && remaining.getAmount() > 0; slot++) {
            ItemStack stored = inventory.getItem(slot);
            if (stored == null || !stored.isSimilar(remaining)) continue;
            int room = stored.getMaxStackSize() - stored.getAmount();
            if (room <= 0) continue;
            int moved = Math.min(room, remaining.getAmount());
            stored.setAmount(stored.getAmount() + moved);
            remaining.setAmount(remaining.getAmount() - moved);
        }
        for (int slot = HOTBAR_SIZE; slot < STORAGE_END_EXCLUSIVE && remaining.getAmount() > 0; slot++) {
            ItemStack stored = inventory.getItem(slot);
            if (stored != null && stored.getType() != Material.AIR) continue;
            inventory.setItem(slot, remaining.clone());
            remaining.setAmount(0);
        }

        // If all 27 storage slots are full, discarding an empty bottle is the only
        // outcome that cannot immediately refill the newly freed hotbar slot.
        inventory.setItem(consumedHotbarSlot, null);
    }
}
