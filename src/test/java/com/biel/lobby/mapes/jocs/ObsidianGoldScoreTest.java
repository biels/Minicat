package com.biel.lobby.mapes.jocs;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import io.papermc.paper.persistence.PersistentDataContainerView;

public final class ObsidianGoldScoreTest {
    public static void main(String[] args) {
        ObsidianGoldScore score = new ObsidianGoldScore();
        score.grant(0, 12);
        score.grant(0, 5);
        score.grant(1, 12);
        require(score.total(0) == 17 && score.total(1) == 12, "direct rewards accumulate independently");

        ItemStack existingGold = new Stack(Material.GOLD_NUGGET, 12);
        require(score.collect(0, existingGold) == 0, "reward gold must not count again on pickup");
        ItemStack fresh = ObsidianGoldScore.freshLoot(new Stack(Material.GOLD_NUGGET, 64));
        ItemStack partial = fresh.clone();
        partial.setAmount(20);
        fresh.setAmount(44);
        require(score.collect(0, partial) == 20, "partial pickup credits only the collected amount");
        require(score.total(0) == 37, "uncollected leftovers do not count");
        require(score.collect(0, partial) == 0 && score.collect(1, partial) == 0, "repeated pickup or transfer never counts twice");
        require(partial.getPersistentDataContainer().isEmpty(), "credited loot loses its marker so ordinary shop payments work");
        require(score.collect(1, fresh) == 44, "leftovers can be earned by another collector");
        require(score.total(1) == 56, "other team's partial reward");

        ItemStack ingots = ObsidianGoldScore.freshLoot(new Stack(Material.GOLD_INGOT, 3));
        require(score.collect(0, ingots) == 30, "ingots are ten nuggets each");
        require(score.collect(0, new Stack(Material.GOLD_NUGGET, 30)) == 0, "converted nuggets cannot be credited twice");
        partial.setAmount(0);
        require(score.total(0) == 67, "spending or losing inventory does not reduce the score");
        require(score.collect(0, null) == 0 && score.collect(0, new Stack(Material.AIR, 0)) == 0, "empty slots are ignored");
        require(score.collect(0, ObsidianGoldScore.freshLoot(new Stack(Material.GOLD_BLOCK, 1))) == 0, "purchased special gold blocks are not currency income");
        score.grant(0, 3000000000L);
        require(score.total(0) == 3000000067L, "team score does not overflow an int");
        score.clear();
        require(score.total(0) == 0 && score.total(1) == 0, "new match resets both totals");
        System.out.println("Obsidian cumulative gold checks passed");
    }

    /** Exercise the actual PDC marker logic without requiring a running Paper item factory. */
    private static final class Stack extends ItemStack {
        private final Material material;
        private int amount;
        private final Map<NamespacedKey, Object> tags = new HashMap<>();
        private final PersistentDataContainer data = (PersistentDataContainer) Proxy.newProxyInstance(
                PersistentDataContainer.class.getClassLoader(), new Class<?>[]{PersistentDataContainer.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "set" -> { tags.put((NamespacedKey) args[0], args[2]); yield null; }
                    case "remove" -> { tags.remove(args[0]); yield null; }
                    case "has" -> tags.containsKey(args[0]);
                    case "isEmpty" -> tags.isEmpty();
                    case "get" -> tags.get(args[0]);
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        Stack(Material material, int amount) { super(); this.material = material; this.amount = amount; }
        @Override public Material getType() { return material; }
        @Override public int getAmount() { return amount; }
        @Override public void setAmount(int amount) { this.amount = amount; }
        @Override public PersistentDataContainerView getPersistentDataContainer() { return data; }
        @Override public boolean editPersistentDataContainer(Consumer<PersistentDataContainer> consumer) { consumer.accept(data); return true; }
        @Override public ItemStack clone() {
            Stack copy = new Stack(material, amount);
            copy.tags.putAll(tags);
            return copy;
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
