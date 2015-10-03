package com.biel.FastSurvival.SpecialItems.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.projectiles.ProjectileSource;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class RawBowDistScalingItem extends SpecialItem{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawDamageAmplifierItem";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		long percentage = Math.round(getDistDmgAmplifierMultiplier(d) * 100);
		return "Deals " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "increased damage for each block of distance between you and the target.";
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
	}
	public Double getDistDmgAmplifierMultiplier(SpecialItemData d){
		return 1D;
	}
	//Handlers
	@EventHandler
	public void onEntDmg(EntityDamageByEntityEvent evt) {
		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		Entity rawDamager = evt.getDamager();
		if (rawDamager instanceof Projectile){
			ProjectileSource shooter = ((Projectile) rawDamager).getShooter();
			if(shooter instanceof Entity){
				rawDamager = (Entity) shooter;
			}			
		}
		if (!(rawDamager instanceof LivingEntity)){return;}
		LivingEntity damager = (LivingEntity) rawDamager;
		DamageCause c = evt.getCause();
		if (damager instanceof Player){ //apply
			Player p = (Player) damager;
			if (!hasItem(p)){return;} //CHECK!
			SpecialItemData d = getData(p); //DATA
			double dmg = evt.getDamage();
			double distance = p.getLocation().distance(damaged.getLocation());
			Double damageAmplifierMultiplier = getDistDmgAmplifierMultiplier(d) * distance;
			evt.setDamage(dmg * (1 + damageAmplifierMultiplier));	
			replaceItem(p, d); //REPLACE
		}
	}
	
	
}
