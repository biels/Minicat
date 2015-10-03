package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class RedstoneDamageCollectorItem extends RawDamageCollectorItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 7;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Redstone damage collector";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.REDSTONE_BLOCK;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setMaxValue(14D);
	}
	@Override
	public Double getDamageStoringMultiplier() {
		// TODO Auto-generated method stub
		return 0.25D;
	}
}
