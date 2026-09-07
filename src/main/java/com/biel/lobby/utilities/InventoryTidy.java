package com.biel.lobby.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Tidies an inventory the way a player would if they had a free second: it only places
 * things that have no place yet and only swaps when one item is a strict upgrade of
 * another of the same kind; it never moves anything else and never touches the
 * protected slot (the recall clock). Run at the moments an item arrives, never on a
 * timer (Biel, 2026-09-08: "preserving user setup but aiding in situations that are
 * clearly just extra clicks or the user clearly forgot something").
 *
 * The rules, in order: the best sword into the sword slot (wherever the first sword in
 * the hotbar sits); a bow into the hotbar; a diamond pickaxe into the hotbar; snowballs
 * merged and into the hotbar; consumables merged into as few stacks as possible, the
 * first stack staying where it is; armour worn when better than what is worn.
 */
public final class InventoryTidy {
	private static final int HOTBAR_SLOTS = 9;
	private static final int BOW_PREFERRED_SLOT = 1;
	private static final int PICKAXE_PREFERRED_SLOT = 2;
	private static final List<Material> MERGED = List.of(Material.GOLD_NUGGET, Material.COOKED_BEEF, Material.ARROW, Material.MAGMA_CREAM, Material.EMERALD, Material.SNOWBALL, Material.ENDER_PEARL);
	private static final Map<String, Integer> TIERS = Map.of("WOODEN", 1, "GOLDEN", 2, "LEATHER", 2, "STONE", 3, "CHAINMAIL", 3, "IRON", 4, "DIAMOND", 5, "NETHERITE", 6);

	private InventoryTidy() {
	}

	/** {@code protectedSlot}: a hotbar slot never read or written, -1 for none. */
	public static void tidy(Player player, int protectedSlot) {
		PlayerInventory inv = player.getInventory();
		bestSwordIntoSwordSlot(inv, protectedSlot);
		intoHotbar(inv, item -> item.getType() == Material.BOW, BOW_PREFERRED_SLOT, protectedSlot, true);
		intoHotbar(inv, item -> item.getType() == Material.DIAMOND_PICKAXE, PICKAXE_PREFERRED_SLOT, protectedSlot, true);
		for (Material material : MERGED) merge(inv, material, protectedSlot);
		intoHotbar(inv, item -> item.getType() == Material.SNOWBALL, -1, protectedSlot, false);
		wearBetterArmour(inv);
	}

	// ---- swords

	private static boolean isSword(ItemStack item) {
		return item != null && item.getType().name().endsWith("_SWORD");
	}

	/** Tier first, then the sum of enchantment levels. */
	private static int swordRank(ItemStack sword) {
		int tier = TIERS.getOrDefault(sword.getType().name().replace("_SWORD", ""), 0);
		int levels = 0;
		for (int level : sword.getEnchantments().values()) levels += level;
		return tier * 100 + levels;
	}

	private static void bestSwordIntoSwordSlot(PlayerInventory inv, int protectedSlot) {
		int swordSlot = -1, bestSlot = -1, bestRank = -1;
		for (int i = 0; i < inv.getSize(); i++) {
			if (i == protectedSlot || !isSword(inv.getItem(i))) continue;
			if (i < HOTBAR_SLOTS && swordSlot == -1) swordSlot = i;
			int rank = swordRank(inv.getItem(i));
			if (rank > bestRank) { bestRank = rank; bestSlot = i; }
		}
		if (bestSlot == -1) return;
		if (swordSlot == -1) {
			int free = firstFreeHotbarSlot(inv, protectedSlot);
			if (free != -1) swap(inv, bestSlot, free);
			return;
		}
		if (bestSlot != swordSlot) swap(inv, bestSlot, swordSlot);
	}

	// ---- the hotbar

	/** One item of the kind into the hotbar when none is there: the first free slot, else the preferred slot when {@code force}, pushing its item to where this one was. */
	private static void intoHotbar(PlayerInventory inv, Predicate<ItemStack> kind, int preferredSlot, int protectedSlot, boolean force) {
		for (int i = 0; i < HOTBAR_SLOTS; i++) if (i != protectedSlot && inv.getItem(i) != null && kind.test(inv.getItem(i))) return;
		int from = -1;
		for (int i = HOTBAR_SLOTS; i < inv.getSize(); i++) if (inv.getItem(i) != null && kind.test(inv.getItem(i))) { from = i; break; }
		if (from == -1) return;
		int free = firstFreeHotbarSlot(inv, protectedSlot);
		if (free != -1) swap(inv, from, free);
		else if (force && preferredSlot != protectedSlot) swap(inv, from, preferredSlot);
	}

	private static int firstFreeHotbarSlot(PlayerInventory inv, int protectedSlot) {
		for (int i = 0; i < HOTBAR_SLOTS; i++) if (i != protectedSlot && (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)) return i;
		return -1;
	}

	private static void swap(PlayerInventory inv, int a, int b) {
		ItemStack itemA = inv.getItem(a), itemB = inv.getItem(b);
		inv.setItem(a, itemB);
		inv.setItem(b, itemA);
	}

	// ---- stacks

	/** Every stack similar to the first one of the material, refilled from the first slot outward in stacks of the maximum; emptied slots cleared. */
	private static void merge(PlayerInventory inv, Material material, int protectedSlot) {
		List<Integer> slots = new ArrayList<>();
		ItemStack sample = null;
		int total = 0;
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (i == protectedSlot || item == null || item.getType() != material) continue;
			if (sample == null) sample = item.clone();
			if (!sample.isSimilar(item)) continue;
			slots.add(i);
			total += item.getAmount();
		}
		if (slots.size() < 2) return;
		int max = sample.getMaxStackSize();
		for (int slot : slots) {
			int amount = Math.min(max, total);
			total -= amount;
			if (amount == 0) { inv.setItem(slot, null); continue; }
			ItemStack stack = sample.clone();
			stack.setAmount(amount);
			inv.setItem(slot, stack);
		}
	}

	// ---- armour

	private static int armourSlot(Material material) {
		String name = material.name();
		if (name.endsWith("_BOOTS")) return 0;
		if (name.endsWith("_LEGGINGS")) return 1;
		if (name.endsWith("_CHESTPLATE")) return 2;
		if (name.endsWith("_HELMET")) return 3;
		return -1;
	}

	private static int armourRank(ItemStack piece) {
		if (piece == null || piece.getType() == Material.AIR) return -1;
		int tier = TIERS.getOrDefault(piece.getType().name().substring(0, piece.getType().name().indexOf('_')), 0);
		return tier * 100 + piece.getEnchantmentLevel(Enchantment.PROTECTION);
	}

	/** A piece in the inventory better than the worn one goes on; the worn one takes its place in the inventory. */
	private static void wearBetterArmour(PlayerInventory inv) {
		ItemStack[] worn = inv.getArmorContents();
		boolean changed = false;
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item == null) continue;
			int slot = armourSlot(item.getType());
			if (slot == -1 || armourRank(item) <= armourRank(worn[slot])) continue;
			ItemStack previous = worn[slot];
			worn[slot] = item;
			inv.setItem(i, previous == null || previous.getType() == Material.AIR ? null : previous);
			changed = true;
		}
		if (changed) inv.setArmorContents(worn);
	}
}
