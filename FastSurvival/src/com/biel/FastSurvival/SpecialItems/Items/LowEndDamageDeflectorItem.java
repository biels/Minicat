package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class LowEndDamageDeflectorItem extends RawDmgDeflectorItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 9;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Low-end damage deflector";
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
		d.setMaxValue(7D + Utils.NombreEntre(0, 1));
	}
	@Override
	public Double getDamageMultiplier() {
		// TODO Auto-generated method stub
		return 0D;
	}
}
