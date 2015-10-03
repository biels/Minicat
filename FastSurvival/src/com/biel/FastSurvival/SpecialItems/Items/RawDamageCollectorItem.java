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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class RawDamageCollectorItem extends SpecialItem {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawDmgCollectorItem";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.STAINED_GLASS;
	}
	@Override
	public Byte getData() {
		// TODO Auto-generated method stub
		return super.getData();
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		long percentage = Math.round(getDamageStoringMultiplier() * 100);
		return "Stores " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% " + "incoming damage from all sources up to a maximum of " + ChatColor.YELLOW + Double.toString(d.getMaxValue()) + ChatColor.WHITE + " and adds it to your next attack when full. " + "The effect isn't applied if the player has its max health. Sounds will be played when the effect is ready or applied.";
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setValue(0D);
	}
	public Double getDamageStoringMultiplier(){
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
		if (damaged instanceof Player){ //store
			Player p = (Player) damaged;
			if (!hasItem(p)){return;} //CHECK!
			SpecialItemData d = getData(p); //DATA
			double m = getDamageStoringMultiplier();
			double dmg = evt.getDamage();
			double storing = dmg * m;
			//Log
			double newValue = d.getValue() + storing;
			if(newValue > d.getMaxValue()){
				newValue = d.getMaxValue();
				//Play loaded effect
				p.playSound(p.getEyeLocation(), Sound.ANVIL_LAND, 0.5F, 4F);
			}
			d.setValue(newValue);
			
			replaceItem(p, d); //REPLACE
		}
		if (damager instanceof Player){ //apply
			Player p = (Player) damager;
			if (!hasItem(p)){return;} //CHECK!
			SpecialItemData d = getData(p); //DATA
			if(d.getValue() >= d.getMaxValue()){
				if (c == DamageCause.ENTITY_ATTACK){
					double dmg = evt.getDamage();
					evt.setDamage(dmg + d.getValue());			
					d.setValue(0D);
					//Play loaded effect
					p.playSound(p.getEyeLocation(), Sound.IRONGOLEM_THROW, 0.5F, 4F);
				}
			}
			
			replaceItem(p, d); //REPLACE
		}
	}
}
