package com.biel.lobby.utilities.events.statuseffects;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.utilities.Utils;

public class LifeDrainStatusEffect extends StatusEffect {
	
	public LifeDrainStatusEffect(Player ply) {
		super(ply);
		setType(StatusEffectType.DEBUFF);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Wither";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Transfereix vida a l'enemic";
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		if(isNthTick(5) && !getPlayer().hasPotionEffect(PotionEffectType.WITHER)){
			getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, getRemainingTicks(), 1));
		}
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		if(evt.getCause() == DamageCause.WITHER){
			GUtils.healDamageable(getOwnerPlayer(), evt.getDamage() * 1.5);
			Vector v = Utils.CrearVector(getOwnerPlayer().getLocation(), getPlayer().getLocation()).multiply(0.5D);
			double max = 7;
			for (int i = 0; i < max; i++) {
//				getWorld().playEffect(getPlayer().getEyeLocation().add(0, -0.8, 0).add(v.clone().multiply(i/max)),
//						Effect.HEART, 0);
			}
		}
	}
}
