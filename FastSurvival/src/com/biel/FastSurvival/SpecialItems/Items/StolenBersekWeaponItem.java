package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class StolenBersekWeaponItem extends RawBersekDamageBoosterItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 17;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stolen bersek weapon";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GOLD_PICKAXE;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Double getMaxDamageAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.45D;
	}
}
