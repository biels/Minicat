package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.biel.FastSurvival.MobIntelligence.KingSkeletonBossUtils;
import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class RawDmgReductionItem extends SpecialItem {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawDmgReductionItem";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		long percentage = Math.round(getDamageMultiplier() * 100);
		return "Reduces incoming damage from all sources by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "";
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setValue(0D);
	}
	public Double getDamageMultiplier(){
		return 1D;
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
			double m = getDamageMultiplier();
			double dmg = evt.getDamage();
			double reduction = dmg * m;
			double result = dmg - reduction;
			evt.setDamage(result);
			//Log
			d.setValue(d.getValue() + reduction);
			//p.sendMessage(getName() + ": "  + "Dmg multimplier " + Double.toString(m) + ", damage reduced by " + Double.toString(reduction) + " from " + Double.toString(dmg));
			
			replaceItem(p, d); //REPLACE
		}
	}
}
