package com.biel.lobby.minions;

import org.bukkit.Material;

/**
 * The kinds of snowman a thrown snowball can become, told apart by the block on the
 * head: the plain one keeps its pumpkin, the others wear a block display where the
 * pumpkin was. A kind fixes the body's numbers; what its snowballs do to an enemy is
 * the game's rule (Obsidian Defenders: neu slows, gel cages on the fourth hit, magma
 * burns), passed in as the hit callback.
 */
public enum SnowmanKind {
	NEU("neu", null, 8, 20),
	GEL("gel", Material.ICE, 8, 40),
	MAGMA("magma", Material.MAGMA_BLOCK, 8, 30);

	/** Player-facing word, as in "Ninot de gel". */
	public final String label;
	/** Block shown on the head instead of the pumpkin; null keeps the pumpkin. */
	public final Material headBlock;
	public final double maxHealth;
	/** Ticks between two snowballs; fewer, heavier shots for the kinds that do more than damage. */
	public final int cooldownTicks;

	SnowmanKind(String label, Material headBlock, double maxHealth, int cooldownTicks) {
		this.label = label;
		this.headBlock = headBlock;
		this.maxHealth = maxHealth;
		this.cooldownTicks = cooldownTicks;
	}
}
