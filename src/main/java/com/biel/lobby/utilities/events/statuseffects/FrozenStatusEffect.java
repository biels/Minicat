package com.biel.lobby.utilities.events.statuseffects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Shut in ice ({@link com.biel.lobby.mapes.Joc#encaseInIce}): the player cannot break out
 * while it lasts. Shared by the frost archer skill and the ice snowmen.
 */
public class FrozenStatusEffect extends StatusEffect {
	public FrozenStatusEffect(Player ply) {
		super(ply);
		setType(StatusEffectType.DEBUFF);
		setModal(true);
	}

	@Override
	public String getName() {
		return "Congelat";
	}

	@Override
	public double getMaxValue() {
		return 6;
	}

	@Override
	public String getDescription() {
		return "Tancat en gel";
	}

	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		super.onBlockBreak(evt, blk);
		evt.setCancelled(true);
	}
}
