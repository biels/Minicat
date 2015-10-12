package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.ArrayList;

import org.bukkit.ChatColor;
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
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class SwordsmanSkill extends InherentSkill {
	private int stacks = 0;

	public SwordsmanSkill(Player ply) {
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
		return Material.IRON_SWORD;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Espadatxí";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getDmgMultiplier() * 100 + ChatColor.WHITE;
		return "Cada 6 cops el mal del següent cop augmenta en un " + modifier1 + "%. incrementant també l'empenta aplicada a l'enemic. Aquest efecte nomès s'aplica als atacs cos a cos" ;
	}

	private int getStacks(){
		return stacks;
	}
	private void setStacks(int value){
		stacks = value;
	}
	private static int getMaxStacks(){
		return 6;
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
			Player damaged, Player damager, boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		Player p = getPlayer();
		if(damager != p)return;
		SwordsmanStatusEffect ef = getAssociatedEffect();
		//sendGlobalMessage(getName() + id +  ": " + evt.getEventName());
		int markTicks = 20 * 11;
		//if(ef.getRemainingTicks() > markTicks - 10){return;}
		ef.setRemainingTicks(markTicks);
		
		if(ef.getValue() >= ef.getMaxValue()){
			if (!ranged) {
				//H
				evt.setDamage(evt.getDamage() * getDmgMultiplier());
				Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
				Vector dir = rawDir.normalize().multiply(1.95).add(new Vector(0,0.42,0));
				damaged.setVelocity(dir);
				damaged.playSound(damager.getLocation(), Sound.EAT, 1, 0.3F);
				//ENDH
				copFort(damaged, damager);
				ef.setValue(0);
				ef.setModal(false);
				//evt.setDamage(evt.getDamage() / 3);
				ef.setRemainingTicks(0);
				ef.setModal(false);
			}			       				
		}else{
			ef.setValue(ef.getValue() + 1);
			if(ef.getValue() >= ef.getMaxValue()){
				damager.playSound(damager.getLocation(), Sound.ORB_PICKUP, 1, 1);
				ef.setModal(true);
				ef.setModalRemainingTicks(20 * 4);
			}else{
			}			
		}
		
	}

	public double getDmgMultiplier() {
		return 1.5;
	}

	public void copFort(Player damaged, Player damager) {

	}
	public SwordsmanStatusEffect getAssociatedEffect(){
		SwordsmanStatusEffect e;
		PlayerInfo i = getPlayerInfo(getPlayer());
		if(i.hasStatusEffect(SwordsmanStatusEffect.class)){
			e = i.getStatusEffect(SwordsmanStatusEffect.class);
		}else{
			e = new SwordsmanStatusEffect(getPlayer());
			e.setValue(1);
			i.addStatusEffect(e);
		}
		return e;
	}
	public class SwordsmanStatusEffect extends StatusEffect{

		public SwordsmanStatusEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.SKILL_TRAY);
			setMaxValue(6);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Cop fort";
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "Càrregues per poder efectuar un cop amb mal adicional";
		}

	}
}
