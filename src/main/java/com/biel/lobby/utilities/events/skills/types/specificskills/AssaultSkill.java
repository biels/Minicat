package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class AssaultSkill extends InherentSkill {

	public AssaultSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Assalt";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.DIAMOND_BOOTS;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getModifier() + ChatColor.WHITE;
		Double percentage = Math.round(getProtectionRatio() * 100 * 10) / 10D;
		String percent = ChatColor.GREEN + "" + percentage + ChatColor.WHITE + "%";
		return "El mal per caiguda es transfereix als enemics propers (" + modifier1 + " blocs) amplificat en un " + percent;
	}
	private static Double getProtectionRatio() {
		// TODO Auto-generated method stub
		return 0.185D;
	}

	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		DamageCause c = evt.getCause();	
		if(p != getPlayer())return;
		if(c == DamageCause.FALL){
			ArrayList<LivingEntity> nearbyEnemies = Utils.getNearbyEnemies(p, getModifier(), false);
			Boolean someone = false;
			for(LivingEntity l : nearbyEnemies){
				if (l instanceof Player){
					if(getGame().areAllies((Player) l, getPlayer()))continue;
				}
				l.damage(evt.getDamage() * (1 + getProtectionRatio()), p);
				l.setVelocity(new Vector(0, 1.25, 0));
				p.getWorld().playEffect(l.getLocation().add(0.5, 0, 0.5), Effect.SMOKE, 4);
				getWorld().playEffect(p.getLocation().add(0.2, 0.3, 0.2), Effect.MAGIC_CRIT, 0);
				getWorld().playEffect(p.getLocation().add(0.2, 0.3, 0.2), Effect.MAGIC_CRIT, 0);
				someone = true;
			}
			if (someone){evt.setCancelled(true);}
		}

	}

	private static double getModifier() {
		// TODO Auto-generated method stub
		return 7;
	}
}
