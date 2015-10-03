package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class DiamondCoreItem extends RawDmgReductionItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Diamond core";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.DIAMOND;
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
		d.setMaxValue(500D);
	}
	@Override
	public Double getDamageMultiplier() {
		// TODO Auto-generated method stub
		return 0.22;
	}
}
