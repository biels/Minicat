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
import com.biel.lobby.utilities.events.skills.types.CooldownSkill;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.AuraStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;

public class DeflectorSkill extends InherentSkill {
	private int stacks = 0;

	public DeflectorSkill(Player ply) {
		super(ply);
		if(ply == null)return;
		//PlayerInfo i = getPlayerInfo();
		//AuraStatusEffect aura = new AuraStatusEffect(getPlayer(), new AuraInfo(2.5, 8, 1, getItemStack()));
		//i.addStatusEffect(aura);
		// TODO Auto-generated constructor stub
		//getPlayerInfo().addAura(new AuraInfo("Defl", 1, 9, 1, getItemStack()));
	}
	@Override
	public boolean usingAssociatedCDEffect() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 35;
	}
	@Override
	protected boolean getPlayerSpecificEventFiltering() {
		// TODO Auto-generated method stub
		return false;
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
		String modifier3 = ChatColor.GREEN + "" + getDmgMultiplierBlk() * 100 + ChatColor.WHITE;
		String modifier2 = ChatColor.GREEN + "" + getCDSeconds() + ChatColor.WHITE;
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

		//		if(!i.hasStatusEffect(DeflectorStatusEffect.class)){	
		//			if(tryUseCD()){
		//				sendSkillMessage("Deflector seconds: " + getCDRemainigSeconds());
		//				i.addStatusEffect(new DeflectorStatusEffect(getPlayer()));
		//				//sendGlobalMessage(i.getStatusEffectsText());
		//			}
		//		}
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		if(killer == getPlayer()){
			skipCooldown();
		}
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
			boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		//H
		//sendEffectMessage("Damage handled");
		if(!getPlayer().equals(damaged))return;
		if(!tryUseCD())return;

		//Checks
		if(damager.equals(damaged))return;


		evt.setCancelled(true);
		boolean blocking = getPlayer().isBlocking();
		damager.damage(evt.getDamage() * (blocking ? getDmgMultiplierBlk() : getDmgMultiplier()), damaged);

		//Knockback
		Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
		Vector dir = rawDir.normalize().multiply(-1.35).add(new Vector(0,0.42,0));
		damager.setVelocity(dir);
		//ENDH
		getWorld().playSound(damager.getLocation(), Sound.BLOCK_METAL_HIT, 1.2F, 0.8F);
		getWorld().playEffect(damaged.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.BLUE.getDyeData());   				
		getWorld().playEffect(damager.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.RED.getDyeData());
		if(blocking)getWorld().playEffect(damager.getEyeLocation(), Effect.MAGIC_CRIT, DyeColor.RED.getDyeData());
		if(blocking)getWorld().playEffect(damaged.getEyeLocation(), Effect.MAGIC_CRIT, DyeColor.RED.getDyeData());

		//if(blocking)sendSkillMessage("Deflexió crítica!");
		//Remove after use
	}
	public double getDmgMultiplier() {
		return 1.2;
	}
	public double getDmgMultiplierBlk() {
		return 2.35;
	}
	@Override
	public void onCDAvaliable() {
		// TODO Auto-generated method stub
		super.onCDAvaliable();
		getPlayerInfo().addAura(new AuraInfo(getName(), 4, getItemStack()));
	}
	@Override
	public void onCDUse() {
		// TODO Auto-generated method stub
		super.onCDUse();
		getPlayerInfo().removeAura(getName());
	}
	//	public class DeflectorStatusEffect extends StatusEffect{
	//
	//		public DeflectorStatusEffect(Player ply) {
	//			super(ply);
	//			setType(StatusEffectType.SKILL_TRAY);
	//			setRemainingTicks(-1);
	//		}
	//
	//		@Override
	//		public String getName() {
	//			// TODO Auto-generated method stub
	//			return "Deflector";
	//		}
	//		@Override
	//		public String getDescription() {
	//			// TODO Auto-generated method stub
	//			return "El següent atac que rebis serà retornat";
	//		}
	//		
	//	}
}
