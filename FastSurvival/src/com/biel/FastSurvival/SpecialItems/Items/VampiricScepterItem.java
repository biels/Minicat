package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class VampiricScepterItem extends RawLifeStealItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 23;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vampiric scepter";
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.REDSTONE_TORCH_ON;
	}
	@Override
	public Double getLifeStealMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.2D;
	}
	@Override
	public double getUndeadReducingMultiplier() {
		// TODO Auto-generated method stub
		return 0.48D;
	}
}
