package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class RawBersekDamageBoosterItem extends RawDamageAmplifierItem{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawBersekDamageBoosterItem";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		long percentage = Math.round(getDamageAmplifierMultiplier(d) * 100 * 10) / 10;
		long maxPercentage = Math.round(getMaxDamageAmplifierMultiplier(d) * 100);
		return "Increases damage dealt in any way by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "The effect scales with missing health (as a good bersek) and is halved if the player has its max health. The maximum (on 0 hp) is "  + ChatColor.GREEN + maxPercentage + ChatColor.WHITE + "% " ;
	}
	@Override
	public Double getDamageAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return getMissingHealth(d) * getMaxDamageAmplifierMultiplier(d);
	}
	public Double getMaxDamageAmplifierMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 1D;
	}
}
