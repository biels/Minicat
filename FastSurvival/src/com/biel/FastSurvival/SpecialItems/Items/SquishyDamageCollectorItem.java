package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class SquishyDamageCollectorItem extends RawDamageCollectorItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 6;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Squishy damage collector";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.STAINED_GLASS;
	}
	@Override
	public Byte getData() {
		// TODO Auto-generated method stub
		return 14; //Red Stained Glass 
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setMaxValue(8D);
	}
	@Override
	public Double getDamageStoringMultiplier() {
		// TODO Auto-generated method stub
		return 0.045D;
	}
}
