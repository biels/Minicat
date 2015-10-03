package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class KnowledgeMagnifierItem extends SpecialItem {
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 5;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Knowledge magnifier";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getExpMultiplier(d) * 100 * 10) / 10D;
		return "Increases experience gain by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "(8 + 1/10 of your current level)";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GLASS;
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
		d.setValue(0D);
		d.setMaxValue(1D); //Ints to exp
	}
	public Double getExpMultiplier(SpecialItemData d){
		return ((8 + (d.getModifier() / 10D)) / 100D);
	}
	//Handlers
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent evt) {
		Player p = evt.getPlayer();
		if (!hasItem(p)){return;} //CHECK!
		SpecialItemData d = getData(p); //DATA
		//-------
		d.setModifier((double) p.getLevel());
		double addAmount = evt.getAmount() * getExpMultiplier(d);
		int round = (int) Math.round(addAmount);
		double store = addAmount - round;
		double newValue = d.getValue() + store;
		if (newValue >= d.getMaxValue()){
			round = (int) (round + d.getMaxValue());
			newValue = newValue - d.getMaxValue();
		}
		d.setValue(newValue);
		evt.setAmount(round);
		
		//-------
		replaceItem(p, d); //REPLACE
	}
}
