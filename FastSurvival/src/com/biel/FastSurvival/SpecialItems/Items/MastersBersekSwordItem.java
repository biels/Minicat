package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class MastersBersekSwordItem extends RawBersekDamageBoosterItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 20;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Master's bersek sword";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GOLD_SWORD;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public Double getMaxDamageAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 2.25D;
	}
}
