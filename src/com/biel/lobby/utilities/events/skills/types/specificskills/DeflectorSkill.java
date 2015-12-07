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
		String modifier2 = ChatColor.GREEN + "" + 20 + ChatColor.WHITE;
		return "Retorna un atac a l'enemic si no ha estat atacat durant els últims "+ modifier2 + "s amplificant-lo un" + modifier1 + "%." ;
	}

	private int getStacks(){
		return stacks;
	}
	private void setStacks(int value){
		stacks = value;
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
			Player damaged, Player damager, boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		Player p = getPlayer();
		boolean blk = p.isBlocking();
		if(damaged != p)return;
		
		//sendGlobalMessage(getName() + id +  ": " + evt.getEventName());
		int markTicks = 20 * (9 + (blk ? 6 : 0));
		//if(ef.getRemainingTicks() > markTicks - 10){return;}
		
		if(tryUseCD()){
			//H
			PlayerInfo di = getPlayerInfo(damaged);
			di.removeStatusEffect(DeflectorStatusEffect.class);
			damager.damage(evt.getDamage() * getDmgMultiplier(), damaged);
			evt.setCancelled(true);
			Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
			Vector dir = rawDir.normalize().multiply(-1.35).add(new Vector(0,0.42,0));
			damager.setVelocity(dir);
			//ENDH
			getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 1.2F, 0.8F);
			getWorld().playEffect(damaged.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.BLUE.getDyeData());   				
			getWorld().playEffect(damager.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.RED.getDyeData());   				

		}

	}
	
	public double getDmgMultiplier() {
		return 2.0;
	}
	public DeflectorStatusEffect getAssociatedEffect(){
		DeflectorStatusEffect e;
		PlayerInfo i = getPlayerInfo(getPlayer());
		if(i.hasStatusEffect(DeflectorStatusEffect.class)){
			e = i.getStatusEffect(DeflectorStatusEffect.class);
		}else{
			e = new DeflectorStatusEffect(getPlayer());
			e.setValue(1);
			i.addStatusEffect(e);
		}
		return e;
	}
	public class DeflectorStatusEffect extends StatusEffect{

		public DeflectorStatusEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.SKILL_TRAY);
			setMaxValue(5);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Deflector";
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "Càrregues per poder retornar un atac";
		}

	}
}
