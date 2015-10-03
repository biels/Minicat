package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.defaults.ClearCommand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.fusesource.jansi.Ansi.Color;

import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocLastStanding;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocObjectius.EquipObjectius;
import com.biel.lobby.mapes.JocObjectius.Objectiu;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;

public class TNTRun extends JocLastStanding {
	ArrayList<Player> tntPlayers = new ArrayList<Player>();
	ArrayList<Player> immunePlayers = new ArrayList<Player>();
	int temps = 60;
	int round = 0;
	@Override
	protected void setCustomGameRules() {
				
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void teletransportarTothom() {
		for (Player p : getPlayers()){
			p.teleport(getWorld().getSpawnLocation());
		}
		
	}

	@Override
	protected void customJocIniciat() {
		super.customJocIniciat();
		startRound();
		
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "TNTRun";
	}
	void updateEffects(Boolean hyper){
		for (Player p : getPlayers()) {
			Utils.clearEffects(p);
			if (!hyper){
				int speed = 2;
				if (hasTNT(p)){
					speed = 4;
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 800 * 20, 0, true), true); 
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800 * 20, speed, true), true);
			}else{
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800 * 20, 20, true), true);
			}
		
		}
	}
	void dispersarJugadors(){
		for (Player p : getPlayers()){
			Vector vec = Vector.getRandom();
			vec.normalize();
			vec.multiply(Utils.NombreEntre(3, 8));
			p.setVelocity(vec);
		}
	}
	Boolean hasTNT(Player ply){
		return tntPlayers.contains(ply);
	}
	Boolean isImmune(Player ply){
		return immunePlayers.contains(ply);
	}
	void giveImmunity(final Player ply, int secs){
		if (!isImmune(ply)){
			immunePlayers.add(ply);
			ply.getInventory().setHelmet(new ItemStack(Material.QUARTZ_BLOCK));
			ply.getInventory().setItem(8, new ItemStack(Material.QUARTZ_BLOCK));
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				 public void run() {
					 ply.getInventory().setHelmet(null);
						ply.getInventory().clear(8);
					 immunePlayers.remove(ply);
				 }
			}, 20 * 2);
		}
	}
	void passarTNT(Player de, Player a){
		if (isImmune(a)){return;}
		if (!hasTNT(de)){return;}
		if (hasTNT(a)){return;}
		treureTNT(de);
		posarTNT(a);
		giveImmunity(de, 2);
		updateEffects(false);
		sendGlobalMessage(ChatColor.YELLOW + de.getName() + ChatColor.WHITE + " ha posat el seu TNT a " + ChatColor.RED + a.getName());
	}
	void posarTNT(Player ply){
		ply.getInventory().setHelmet(new ItemStack(Material.TNT));
		ply.getInventory().setItem(8, new ItemStack(Material.TNT));
		ply.updateInventory();
		if(!tntPlayers.contains(ply)){
			tntPlayers.add(ply);
			ply.sendMessage("Tens un "+ ChatColor.DARK_RED + "TNT"+ ChatColor.WHITE +"!");
		}
	}
	void treureTNT(Player ply){
		ply.getInventory().setHelmet(null);
		ply.getInventory().clear(8);
		ply.updateInventory();
		if(tntPlayers.contains(ply)){
			tntPlayers.remove(ply);
		}
	}
	int getTNTAmount(){
		return (int) Math.ceil(getAlivePlayers().size() / ((double) 3));
	}
	void tntInicial(){
		@SuppressWarnings("unchecked")
		ArrayList<Player> alives = (ArrayList<Player>) getAlivePlayers().clone();
		Collections.shuffle(alives);
		int tnt = getTNTAmount();
		int done = 0;
		while (done < tnt){
			posarTNT(alives.get(0));
			done++;
		}
	}
	void explotarJugadors(){
		for (Player p : tntPlayers){
			//getWorld().createExplosion(p.getLocation(), 4F, false);
			removeIfAlive(p);
			Com.teleportPlayerToLobby(p);
		}
	}
	void startRound(){
		round = round + 1;
		temps = 60;
		sendGlobalMessage("Ronda " + Integer.toString(round) + " en 3s...");
		//dispersarJugadors();
		Utils.clearPlayers(getPlayers());
		teletransportarTothom();
		tntInicial();
		updateEffects(false);
		ProgTask();
		
	}
	void endRound(){
		explotarJugadors();
		if (getAlivePlayers().size() > 1){
			startRound();
		}
	}
	int taskId = 0;
	public void ProgTask(){
		 
		taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			 public void run() {
				//time
				 temps = temps - 1;
				 if(Utils.Possibilitat(1)){
					 if(Utils.Possibilitat(80)){
						 hipervelocitat();
					 }					 
				 }
				 updateScoreBoards();
				 if (temps <= 0){
					 plugin.getServer().getScheduler().cancelTask(taskId);
					 endRound();
				 }
			 }
		}, 20 * 2, 10);
	}
	public void hipervelocitat(){
		int secs = Utils.NombreEntre(2, 8);
		updateEffects(true);
		sendGlobalMessage(ChatColor.AQUA + "HIPERVELOCITAT! (" + Integer.toString(secs) + "s)" );
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			 public void run() {
				updateEffects(false);
			 }
		}, 20 * 2);
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		if (JocIniciat && !JocFinalitzat){
			ArrayList<String> list = new ArrayList<String>();
			ChatColor col = ChatColor.DARK_GREEN;
			if(temps <= 45){col = ChatColor.GREEN;}
			if(temps <= 25){col = ChatColor.YELLOW;}
			if(temps <= 12){col = ChatColor.RED;}
			if(temps <= 4){col = ChatColor.DARK_RED;}
			list.add(ChatColor.YELLOW + "Temps: " +  col + "" + ChatColor.BOLD + Integer.toString(temps));
			list.add(ChatColor.GREEN + "Ronda: " + ChatColor.WHITE + Integer.toString(round));
			list.add(ChatColor.BLUE + "Jugadors: " + ChatColor.WHITE + Integer.toString(getAlivePlayers().size()));
			ScoreBoardUpdater.setScoreBoard(getPlayers(), "Estadístiques", list, null);
		}
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
			Player damaged, Player damager, boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		passarTNT(damager, damaged);
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		evt.setDamage(0.2);
		if(JocIniciat){
			//sendGlobalMessage(evt.getCause().name());
			//removeIfAlive(ply);
			if(evt.getCause() == DamageCause.VOID){
				evt.setDamage(40);
			}
			if(evt.getCause() == DamageCause.BLOCK_EXPLOSION){
				removeIfAlive(p);
			}
		}
	}



	

}
