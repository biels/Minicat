package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class SnipersAdvancedGuideItem extends RawBowDistScalingItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 27;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "How to snipe - Advanced guide";
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.PAPER;
	}
	@Override
	public Double getDistDmgAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.08D;
	}
}
