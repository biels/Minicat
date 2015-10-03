package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class DamageBoosterItem extends RawDamageAmplifierItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 16;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Damage booster";
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.IRON_SWORD;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
	}
	@Override
	public Double getDamageAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.16D;
	}
}
