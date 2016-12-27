package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocScoreRace;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SlimeWatcher;

public class PilotaSplash extends JocScoreRace {

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 8 + getPlayers().size() * 2;
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Pilota Splash";
	}
	@Override
	public void JocIniciat() {
		// TODO Auto-generated method stub
		super.JocIniciat();
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		//ply.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 4, true), true);

	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		items.add(GUtils.setItemNameAndLore(item, "Puny de pilota", "Pilotassa"));
		
		return items;
	}
	public ItemStack getMagnusLauncherItem(){
		ItemStack item = new ItemStack(Material.QUARTZ, 1);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
		return GUtils.setItemNameAndLore(item, "Efecte magnus", "Pilotassa");
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected void teletransportarTothom() {
		for (Player d : getPlayers()) {  // d gets successively each value in ar.
			teleportToRandomSpawn(d);					
		} 
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		if(evt.getCause() == DamageCause.FALL){
			evt.setCancelled(true);
			GUtils.healDamageable(p, 1.0);
			getWorld().playSound(p.getLocation(), (evt.getDamage() > 4 ? Sound.BLOCK_SLIME_HIT : Sound.BLOCK_SLIME_STEP), 1F, 1F);			
		}
	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, p);
		if(evt.getTo().getY() < getMinimumHeight()){
			teleportToRandomSpawn(p);
			Player k = getPlayerInfo(p).getLastDamager();
			if (k != null) {
				incrementScore(k);
				resetSpree(p);
				k.playSound(p.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1F, 1.5F);
				k.playEffect(p.getEyeLocation(), Effect.POTION_SWIRL, 3);
			}
		}
		//Magnus controller
		//sendGlobalMessage(Double.toString(p.getVelocity().normalize().angle(new Vector(0, -1, 0))));
		//add cd 
		if(p.getVelocity().getY() < -0.19 && p.getVelocity().normalize().angle(new Vector(0, -1, 0)) < Math.PI / 5){
			giveMagnusIfNecessary(p);
		}else{
			removeMagnusIfNecessary(p);
		}
	}
	public void giveMagnusIfNecessary(Player p){
		if(!p.getInventory().contains(getMagnusLauncherItem()))p.getInventory().addItem(getMagnusLauncherItem());
	}
	public void removeMagnusIfNecessary(Player p){
		if(p.getInventory().contains(getMagnusLauncherItem()))p.getInventory().removeItem(getMagnusLauncherItem());
	}
	@Override
	protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerInteract(evt, p);
		if(p.getItemInHand().equals(getMagnusLauncherItem())){
			p.setVelocity(p.getLocation().getDirection().multiply(1.15).add(new Vector(0, 0.25, 0)));
			p.playSound(p.getEyeLocation(), Sound.BLOCK_SAND_STEP, 1F, 0.9F);
		}
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		incrementScore(killer);
	}
	@Override
	protected void onPlayerRespawn(PlayerRespawnEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerRespawn(evt, p);
		teleportToRandomSpawn(p);
	}
	@Override
	protected void setSpree(Player ply, int value) {
		// TODO Auto-generated method stub
		super.setSpree(ply, value);
//		SlimeWatcher w = (SlimeWatcher) DisguiseAPI.getDisguise(ply).getWatcher();
//		w.setSize((value > 1 ? 3 : 2));		
	}
	@Override
	public void clearExternals() {
		// TODO Auto-generated method stub
		super.clearExternals();
//		for(Player p : getPlayers()){
//			DisguiseAPI.undisguiseToAll(p);			
//		}
	}
	@Override
	public void clearExternals(Player p) {
		// TODO Auto-generated method stub
		super.clearExternals(p);
//		DisguiseAPI.getDisguise(p).removeDisguise();
//		DisguiseAPI.undisguiseToAll(p);	
	}
}
