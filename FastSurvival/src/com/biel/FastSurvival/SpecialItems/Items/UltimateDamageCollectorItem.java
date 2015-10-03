package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class UltimateDamageCollectorItem extends RawDamageCollectorItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 8;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Ultimate damage collector";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.LAPIS_BLOCK;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setMaxValue(24D + Utils.NombreEntre(0, 10));
	}
	@Override
	public Double getDamageStoringMultiplier() {
		// TODO Auto-generated method stub
		return 0.45D;
	}
}
