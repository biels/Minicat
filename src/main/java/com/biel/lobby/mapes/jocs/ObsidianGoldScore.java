package com.biel.lobby.mapes.jocs;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

/** Generated loot is credited once when collected; direct rewards are credited when granted. */
final class ObsidianGoldScore {
    private static final NamespacedKey UNCLAIMED = new NamespacedKey("minicat", "obsidian-unclaimed-gold");
    private final long[] earned = new long[2];

    void clear() {
        java.util.Arrays.fill(earned, 0);
    }

    void grant(int team, long nuggets) {
        if (nuggets < 0) throw new IllegalArgumentException("Gold earnings cannot be negative");
        earned[team] = Math.addExact(earned[team], nuggets);
    }

    long total(int team) {
        return earned[team];
    }

    static ItemStack freshLoot(ItemStack item) {
        if (nuggetValue(item) == 0) return item;
        ItemStack marked = item.clone();
        marked.editPersistentDataContainer(data -> data.set(UNCLAIMED, PersistentDataType.BYTE, (byte) 1));
        return marked;
    }

    long collect(int team, ItemStack item) {
        long nuggets = nuggetValue(item);
        if (nuggets == 0 || !item.getPersistentDataContainer().has(UNCLAIMED, PersistentDataType.BYTE)) return 0;
        grant(team, nuggets);
        item.editPersistentDataContainer(data -> data.remove(UNCLAIMED));
        return nuggets;
    }

    private static long nuggetValue(ItemStack item) {
        if (item == null || item.getAmount() <= 0) return 0;
        if (item.getType() == Material.GOLD_NUGGET) return item.getAmount();
        if (item.getType() == Material.GOLD_INGOT) return 10L * item.getAmount();
        return 0;
    }
}
