package com.biel.lobby.utilities.events.skills.types.specificskills;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;

public class FrostArcherSkill extends InherentSkill {
	private int stacks = 0;
	
	public FrostArcherSkill(Player ply) {
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
		return Material.ICE;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Arquer de gel";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getModifier() + ChatColor.WHITE;
		return "Empresona els enemics encertats amb l'arc durant" + modifier1 + "s. Les fletxes de gel causen mal reduït" ;
	}

	private static double getModifier() {
		// TODO Auto-generated method stub
		return 4.25;
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
		if(damager != p || !getGame().areEnemies(damager, damaged))return;
		FrostArcherStatusEffect ef = getAssociatedEffect();
		//sendGlobalMessage(getName() + id +  ": " + evt.getEventName());
		
		int markTicks = 20 * 14;
		//if(ef.getRemainingTicks() > markTicks - 10){return;}
		ef.setRemainingTicks(markTicks);
		if(ef.getValue() >= ef.getMaxValue()){
			if (ranged) {
				empresonar(damaged, damager);
				ef.setValue(0);
				ef.setModal(false);
				evt.setDamage(evt.getDamage() / 3);
				ef.setRemainingTicks(0);
				ef.setModal(false);
			}			       				
		}else{
			ef.setValue(ef.getValue() + 1);
			if(ef.getValue() >= ef.getMaxValue()){
				damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				ef.setModal(true);
				ef.setModalRemainingTicks(20 * 4);
				getPlayerInfo().addAura(new AuraInfo(getName(), 3, 5, getItemStack()));
			}else{
			}
			
		}
	}

	/** The prison is the game's ({@link com.biel.lobby.mapes.Joc#encaseInIce}), shared with the gel snowmen. */
	public void empresonar(Player damaged, Player damager) {
		getGame().encaseInIce(damaged, (int) (20 * getModifier()));
		damaged.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 0.5F);
		removeDefaultNamedAura();
	}
	public FrostArcherStatusEffect getAssociatedEffect(){
		FrostArcherStatusEffect e;
		PlayerInfo i = getPlayerInfo(getPlayer());
		if(i.hasStatusEffect(FrostArcherStatusEffect.class)){
			e = i.getStatusEffect(FrostArcherStatusEffect.class);
		}else{
			e = new FrostArcherStatusEffect(getPlayer());
			e.setValue(1);
			i.addStatusEffect(e);
		}
		return e;
	}
	public class FrostArcherStatusEffect extends StatusEffect{

		public FrostArcherStatusEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.SKILL_TRAY);
			setMaxValue(6);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Gel";
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "Càrregues per poder empresonar un enemic";
		}
		
	}
}
