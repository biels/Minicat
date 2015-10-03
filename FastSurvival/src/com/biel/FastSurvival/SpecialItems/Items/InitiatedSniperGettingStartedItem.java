package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class InitiatedSniperGettingStartedItem extends RawBowDistScalingItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 26;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Getting started - How to snipe";
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.PAPER;
	}
	@Override
	public Double getDistDmgAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.04D;
	}
}
