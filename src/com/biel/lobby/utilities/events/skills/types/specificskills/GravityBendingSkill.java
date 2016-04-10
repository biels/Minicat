package com.biel.lobby.utilities.events.skills.types.specificskills;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;

public class GravityBendingSkill extends InherentSkill {

	public GravityBendingSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GOLD_BOOTS;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Control de la gravetat";
	}
	
	public double getModifier(){
		return 12.0D;
	}
	public double getProtectionRatio(){
		return 0.12D;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getModifier() + ChatColor.WHITE;
		Double percentage = Math.round(getProtectionRatio() * 100 * 10) / 10D;
		String percent = ChatColor.GREEN + "" + percentage + ChatColor.WHITE + "%";
		return "Quadruplica el mal de caiguda que reben els enemics atacats en un interval de " + modifier1 + "s, robant tanta vida com mal extra faci";
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
			Player damaged, Player damager, boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		//Afegir marca d'estat
		if(damager == getPlayer()){			
			PlayerInfo i = getGame().getPlayerInfo(damaged);
			GravityStatusEffect e;
			if(i.hasStatusEffect(GravityStatusEffect.class)){
				e = i.getStatusEffect(GravityStatusEffect.class);
			}else{
				e = new GravityStatusEffect(damaged);
				i.addStatusEffect(e);						
			}
			e.setOwnerPlayer(damager);
			e.setRemainingTicks((int) (20 * getModifier()));
		}
	}


	public void playEffect(Player p) {
		for (int i = 0; i < 12; i++) {
			//getWorld().playEffect(p.getEyeLocation().subtract(0, 0.9, 0),
				//	Effect.SNOWBALL_BREAK, Utils.NombreEntre(0, 7));
		}
	}
	public class GravityStatusEffect extends StatusEffect{

		public GravityStatusEffect(Player ply) {
			super(ply);
			// TODO Auto-generated constructor stub
			setType(StatusEffectType.DEBUFF);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Gravetat x3";
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "Duplica el mal per caiguda";
		}
		@Override
		protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
			// TODO Auto-generated method stub
			super.onPlayerDamage(evt, p);
			if (evt.getCause() == DamageCause.FALL && p == getPlayer()) {
				double dmg = evt.getDamage() + 1;
				p.damage(dmg * 3, getOwnerPlayer());
				Utils.healDamageable(getOwnerPlayer(), dmg * 1.1);
				getWorld().playEffect(
						getOwnerPlayer().getEyeLocation().add(0, -0.8, 0),
						Effect.HAPPY_VILLAGER, 0);
				Location ploc = p.getLocation();
				int i = Utils.NombreEntre(5, 10);
				getWorld().playSound(ploc, Sound.ENTITY_PLAYER_SMALL_FALL, 3F, 1F);
				while (i >= 0) {
					getWorld().playEffect(ploc, Effect.SMOKE,
							Utils.NombreEntre(0, 8));
					i = i - 1;
				}
				sendEffectMessage("x3 mal de caiguda");
			}
		}
	}
}
