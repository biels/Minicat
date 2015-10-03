package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

public class SteelCoreItem extends RawDmgReductionItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Steel core";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.IRON_INGOT;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Double getDamageMultiplier() {
		// TODO Auto-generated method stub
		return 0.06;
	}
}
