package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class CalciumSourceItem extends SpecialItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 12;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Calcium source";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getOverallMultiplier(d) * 100 * 10) / 10D;
		return "Calcium reinforces your skeleton reducing fall damage by " + ChatColor.GREEN + d.getModifier() + ChatColor.WHITE + " so you can fall higher without taking any damage, and reducing overall taken damage by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.MILK_BUCKET;
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
		d.setModifier(1D + Utils.NombreEntre(1, 5));
	}
	public Double getOverallMultiplier(SpecialItemData d){
		return 0.025;
	}
	//Handlers
		@EventHandler
		public void onDmg(EntityDamageEvent evt) {
			if (!(evt.getEntity() instanceof LivingEntity)){return;}
			LivingEntity damaged = (LivingEntity) evt.getEntity();
			DamageCause c = evt.getCause();
			if (damaged instanceof Player){
				Player p = (Player) damaged;
				if (!hasItem(p)){return;} //CHECK!
				SpecialItemData d = getData(p); //DATA
				double m = getOverallMultiplier(d);
				double dmg = evt.getDamage();
				double reduction = dmg * m;
				double result = dmg - reduction;
				evt.setDamage(result);
				if(c == DamageCause.FALL){
					evt.setDamage(evt.getDamage() - d.getModifier());
				}
				//p.sendMessage(getName() + ": "  + "Dmg multimplier " + Double.toString(m) + ", damage reduced by " + Double.toString(reduction) + " from " + Double.toString(dmg));
				
				replaceItem(p, d); //REPLACE
			}
		}
}
