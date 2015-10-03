package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class BersekEssenceItem extends RawBersekDamageBoosterItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 21;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bersek essence";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.INK_SACK;
	}
	@Override
	public Byte getData() {
		// TODO Auto-generated method stub
		return 11;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Double getMaxDamageAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.35D;
	}
}
