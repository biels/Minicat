package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class UndeadProofLifeStealerItem extends RawLifeStealItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 25;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Undead proof life stealer";
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
		return 0.5D;
	}
	@Override
	public double getUndeadReducingMultiplier() {
		// TODO Auto-generated method stub
		return 0.1D;
	}
}
