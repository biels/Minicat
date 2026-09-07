package com.biel.lobby.minions;

import org.bukkit.Material;

/**
 * The kinds of snowman a thrown snowball can become, told apart by the block on the
 * head: the plain one keeps its pumpkin, the magma one wears a magma block where the
 * pumpkin was. Every snowball is the same item; the kind is the thrower's team's state
 * (Obsidian Defenders: the team that last felled the Guardian throws magma snowmen). A
 * kind fixes the body's numbers; what its snowballs do to an enemy is the game's rule,
 * passed in as the hit callback. Both kinds charge the ice cage, see
 * {@link SnowmanMinion#chargeCage}.
 */
public enum SnowmanKind {
	NEU("neu", null, 12, 20),
	MAGMA("magma", Material.MAGMA_BLOCK, 12, 30);

	/** Player-facing word, as in "Ninot de magma". */
	public final String label;
	/** Block shown on the head instead of the pumpkin; null keeps the pumpkin. */
	public final Material headBlock;
	public final double maxHealth;
	/** Ticks between two snowballs; fewer, heavier shots for the kind that does more than slow. */
	public final int cooldownTicks;

	SnowmanKind(String label, Material headBlock, double maxHealth, int cooldownTicks) {
		this.label = label;
		this.headBlock = headBlock;
		this.maxHealth = maxHealth;
		this.cooldownTicks = cooldownTicks;
	}
}
