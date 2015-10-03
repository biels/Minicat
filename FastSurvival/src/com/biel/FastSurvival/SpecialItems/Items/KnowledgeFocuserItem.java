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

public class KnowledgeFocuserItem extends SpecialItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Knowledge focuser";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getHealMultiplier(d) * 100 * 10) / 10D;
		return "Heal yourself " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "(2 + 1/3 of your current level)" + " of your missing health every " + ChatColor.YELLOW + d.getMaxValue() + ChatColor.WHITE + " collected experience points. The effect is doubled below 25% health.";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.BOOK;
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
		d.setMaxValue(32D);
	}
	public Double getHealMultiplier(SpecialItemData d){
		return ((2 + (d.getModifier() / 3D)) / 100D);
	}
	//Handlers
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent evt) {
		Player p = evt.getPlayer();
		if (!hasItem(p)){return;} //CHECK!
		SpecialItemData d = getData(p); //DATA
		//-------
		d.setModifier((double) p.getLevel());
		double newValue = d.getValue() + evt.getAmount();
		Double maxValue = d.getMaxValue();
		if(newValue >= maxValue){
			newValue = newValue - maxValue;
			//Do healing
			double missingHp = p.getMaxHealth() - p.getHealth();
			
			double amount = missingHp * getHealMultiplier(d);
			if (p.getHealth() < (p.getMaxHealth() / 4D)){
				amount = amount * 2;
			}
			Utils.healDamageable(p, amount);
			if (missingHp > 1){
				p.getWorld().playSound(p.getEyeLocation(), Sound.LEVEL_UP, 1.5F, 4F);
			}
			
		}
		d.setValue(newValue);
		
		//-------
		replaceItem(p, d); //REPLACE
	}
}
