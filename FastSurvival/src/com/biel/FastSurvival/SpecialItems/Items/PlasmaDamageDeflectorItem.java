package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class PlasmaDamageDeflectorItem extends RawDmgDeflectorItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 11;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Plasma damage deflector";
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public Byte getData() {
		// TODO Auto-generated method stub
		return 11;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setMaxValue(4D + Utils.NombreEntre(0, 2));
	}
	@Override
	public Double getDamageMultiplier() {
		// TODO Auto-generated method stub
		return 0.75D;
	}
}
