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

public class RawDamageAmplifierItem extends SpecialItem{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawDamageAmplifierItem";
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		long percentage = Math.round(getDamageAmplifierMultiplier(d) * 100);
		return "Increases damage dealt in any way by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "The effect is halved if the player has its max health. ";
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
	}
	public Double getDamageAmplifierMultiplier(SpecialItemData d){
		return 1D;
	}
	public void updateMissingHealth(Player p, SpecialItemData d) {
		d.setModifier(1D - (p.getHealth() / p.getMaxHealth()));
	}
	public Double getMissingHealth(SpecialItemData d){
		return d.getModifier();
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
			//Extra
			updateMissingHealth(p, d);
			//--
			double dmg = evt.getDamage();
			Double damageAmplifierMultiplier = getDamageAmplifierMultiplier(d);
			if (p.getHealth() == p.getMaxHealth()){damageAmplifierMultiplier = damageAmplifierMultiplier / 2;}
			evt.setDamage(dmg * (1 + damageAmplifierMultiplier));	
			replaceItem(p, d); //REPLACE
		}
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent evt) {
		if(evt.getRegainReason() != RegainReason.SATIATED){return;}
		if (evt.getEntity() instanceof Player){
			Player p = (Player) evt.getEntity();
			if (!hasItem(p)){return;} //CHECK!
			SpecialItemData d = getData(p); //DATA
			//-------
			
			updateMissingHealth(p, d);
			
			
			//-------
			replaceItem(p, d); //REPLACE
		}
	}
}
