package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.StatusEffect;
import com.biel.lobby.utilities.events.skills.types.CooldownSkill;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class DeflectorSkill extends InherentSkill {
	private int stacks = 0;

	public DeflectorSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 20;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.IRON_TRAPDOOR;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Deflector";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getDmgMultiplier() * 100 + ChatColor.WHITE;
		String modifier3 = ChatColor.GREEN + "" + getDmgMultiplier() * 100 + ChatColor.WHITE;
		String modifier2 = ChatColor.GREEN + "" + 20 + ChatColor.WHITE;
		return "Retorna un atac a l'enemic cada "+ modifier2 + "s amplificant-lo un" + modifier1 + "% " + "s o un" + modifier3 + "% si el jugador bloqueja amb l'espasa. L'efecte es restaura al matar.";
	}

	private int getStacks(){
		return stacks;
	}
	private void setStacks(int value){
		stacks = value;
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		if(tryUseCD()){
			getPlayerInfo().addStatusEffect(new DeflectorStatusEffect(getPlayer()));
		}
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		if(killer == getPlayer()){
			skipCooldown();
		}
	}
	public double getDmgMultiplier() {
		return 1.2;
	}
	public double getDmgMultiplierBlk() {
		return 2.35;
	}

	public class DeflectorStatusEffect extends StatusEffect{

		public DeflectorStatusEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.BUFF);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Deflector";
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "El següent atac que rebis serà retornat";
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
				boolean ranged) {
			// TODO Auto-generated method stub
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			//H
			PlayerInfo di = getPlayerInfo(damaged);
			//di.removeStatusEffect(DeflectorStatusEffect.class);
			evt.setCancelled(true);
			boolean blocking = getPlayer().isBlocking();
			damager.damage(evt.getDamage() * (blocking ? getDmgMultiplierBlk() : getDmgMultiplier()), damaged);
			Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
			Vector dir = rawDir.normalize().multiply(-1.35).add(new Vector(0,0.42,0));
			damager.setVelocity(dir);
			//ENDH
			getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 1.2F, 0.8F);
			getWorld().playEffect(damaged.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.BLUE.getDyeData());   				
			getWorld().playEffect(damager.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.RED.getDyeData());
			if(blocking)getWorld().playEffect(damager.getEyeLocation(), Effect.MAGIC_CRIT, DyeColor.RED.getDyeData());
			if(blocking)getWorld().playEffect(damaged.getEyeLocation(), Effect.MAGIC_CRIT, DyeColor.RED.getDyeData());
			if(blocking)sendSkillMessage("Deflexió crítica!");
			//Remove after use
			expire();
		}
	}
}
