package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class UltimateLifeStealerItem extends RawLifeStealItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 24;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Ultimate life stealer";
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.FIREBALL;
	}
	@Override
	public Double getLifeStealMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.65D;
	}
	@Override
	public double getUndeadReducingMultiplier() {
		// TODO Auto-generated method stub
		return 0.40D;
	}
}
