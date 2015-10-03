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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;

public class RawDmgDeflectorItem extends SpecialItem{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RawDmgDeflectorItem";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.STAINED_GLASS;
	}
	@Override
	public String getDescription(SpecialItemData d) {
		// TODO Auto-generated method stub
		Double damageMultiplier = getDamageMultiplier();
		String insert = "";
		if (damageMultiplier != 0){
			long percentage = Math.round(damageMultiplier * 100);
			insert = "amplified by " + ChatColor.GREEN + percentage + ChatColor.WHITE + "% ";
		}
		
		return "Returns the damage " + insert +"to the attacker every " + ChatColor.YELLOW + Double.toString(d.getMaxValue()) + ChatColor.WHITE + " hits. " + "The effect isn't applied if the player has its max health. The source of the damage can only be a living enemy. Sounds will be played when the effect is ready or applied.";
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setValue(0D);
		d.setMaxValue(100D);
	}
	public Double getDamageMultiplier(){
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
			
			//Log
			double newValue = d.getValue() + 1;
			if (newValue == d.getMaxValue()){
				p.playSound(p.getEyeLocation(), Sound.ANVIL_LAND, 0.5F, 2.2F);
			}
			if(newValue > d.getMaxValue()){
				newValue = 0;
				//Return the damage
				double returnedDamage = evt.getDamage();
				returnedDamage = returnedDamage + (evt.getDamage() * getDamageMultiplier());
				damager.damage(returnedDamage, p);
				
				evt.setCancelled(true);
				//Play loaded effect
				p.playSound(p.getEyeLocation(), Sound.IRONGOLEM_THROW, 0.5F, 4F);
			}
			d.setValue(newValue);

			replaceItem(p, d); //REPLACE
		}
		
	}
}
