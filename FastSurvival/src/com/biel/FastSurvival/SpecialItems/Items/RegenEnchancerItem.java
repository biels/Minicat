package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class RegenEnchancerItem extends SpecialItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 4;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Regen enchancer";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getMultiplier(d) * 100 * 10) / 10D;
		return "Increases natural health regeneration by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "%. " + "This item can be found in a wide range of strength (effect %) values.";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.SAPLING;
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
		d.setModifier((double) Utils.NombreEntre(1, 40));
	}
	public Double getMultiplier(SpecialItemData d){
		return ((8 + (d.getModifier() / 2D)) / 100D);
	}
	//Handlers
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent evt) {
		if(evt.getRegainReason() != RegainReason.SATIATED){return;}
		if (evt.getEntity() instanceof Player){
			Player p = (Player) evt.getEntity();
			if (!hasItem(p)){return;} //CHECK!
			SpecialItemData d = getData(p); //DATA
			//-------
			//d.setModifier((double) p.getLevel());
			evt.setAmount(evt.getAmount() * (1 + getMultiplier(d)));
			
			
			//-------
			replaceItem(p, d); //REPLACE
		}
	}
}
