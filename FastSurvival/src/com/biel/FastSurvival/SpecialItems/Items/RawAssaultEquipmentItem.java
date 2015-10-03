package com.biel.FastSurvival.SpecialItems.Items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class RawAssaultEquipmentItem extends SpecialItem{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawAssaultEquipmentItem";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		String insert = "";
		Double damageMultiplier = getDamageMultiplier(d);
		if (damageMultiplier != 0){
			Double percentage = Math.round(damageMultiplier * 100 * 10) / 10D;
			insert = "amplified by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% ";
		}

		return "Transfers your fall damage " + insert + "to the nearby enemies on the ground and knocks them up in the air. Range: " + ChatColor.GREEN + d.getModifier() + ChatColor.WHITE + " blocks. If no enemies in range when falling then no effect is applied." + "";
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setModifier(5D + Utils.NombreEntre(0, 2));
	}
	public Double getDamageMultiplier(SpecialItemData d){
		return 0D;
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
			if(c == DamageCause.FALL){
				ArrayList<LivingEntity> nearbyEnemies = Utils.getNearbyEnemies(p, d.getModifier(), false);
				Boolean someone = false;
				for(LivingEntity l : nearbyEnemies){
					l.damage(evt.getDamage() * (1 + getDamageMultiplier(d)), p);
					l.setVelocity(new Vector(0, 1, 0));
					p.getWorld().playEffect(l.getLocation().add(0.5, 0, 0.5), Effect.SMOKE, 4);
					someone = true;
				}
				if (someone){evt.setCancelled(true);}
			}
			//p.sendMessage(getName() + ": "  + "Dmg multimplier " + Double.toString(m) + ", damage reduced by " + Double.toString(reduction) + " from " + Double.toString(dmg));

			//replaceItem(p, d); //REPLACE
		}
	}
}
