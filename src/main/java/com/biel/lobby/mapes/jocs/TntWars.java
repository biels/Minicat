package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Utils;
import com.connorlinfoot.bountifulapi.BountifulAPI;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public class TntWars extends JocEquips {
	
	Map<UUID, Long> tntCooldown = new HashMap<UUID, Long>();
	Map<Integer, UUID> tntOwner = new HashMap<Integer, UUID>();
    
    Integer team0CoreTotal = 1;
    Integer team1CoreTotal = 1;
    
    Integer team0CoreDestruit = 0;
    Integer team1CoreDestruit = 0;
	
	public TntWars() {
		super();

	}
	@Override
	public String getGameName() {
		return "TntWars";
	}
	
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		
		equips.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		equips.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		
		return equips;
	}
	
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void customJocFinalitzat() {
		super.customJocFinalitzat();
		setBlockBreakPlace(false);
	}

	@Override
	protected void customJocIniciat() {
		super.customJocIniciat();
		setBlockBreakPlace(true);
		setGiveStartingItemsRespawn(true);
		
		// Start the things
		
		Cuboid team0Core = pMapaActual().ObtenirCuboid("RegT10", getWorld());
	    Cuboid team1Core = pMapaActual().ObtenirCuboid("RegT20", getWorld());
	    
	    this.team0CoreTotal = team0Core.getVolume();
	    this.team1CoreTotal = team1Core.getVolume();
	    
	}
	
	@Override
	public boolean giveSnowLauncherOnKill() {
		// TODO Auto-generated method stub
		return false;
	}
    
	public void setTntCooldown(Player player) {
	    long time = System.currentTimeMillis();
	    tntCooldown.put(player.getUniqueId(), time);
	}
	
	public void removeTntCooldown(Player player) {
	    tntCooldown.remove(player.getUniqueId());
	}

	public boolean hasTntCooldown(Player player) {
	  
	    if (!tntCooldown.containsKey(player.getUniqueId())) {
	        return false;
	    }
	  
	    int cooldown = 1000 * 3;
	    long now = System.currentTimeMillis();
	    long time = tntCooldown.get(player.getUniqueId());

	    if (now - time >= cooldown) {
	        tntCooldown.remove(player.getUniqueId());
	        return false;
	    }

	    return true;

	}
	
	public long getTntCooldown(Player player) {
		
		if(!hasTntCooldown(player)) {
			return 0;
		} else {
		    long now = System.currentTimeMillis();
		    long time = tntCooldown.get(player.getUniqueId());
		    long cooldown = now - time;		
		    return (cooldown);
		}
	}
	
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		
		Equip team = obtenirEquip(killed);
		
		evt.setDeathMessage(team.getChatColor() +  killed.getName() + ChatColor.GRAY + " ha mort.");		

	}
	
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);

		double distance = killed.getLocation().distance(killer.getLocation());
		
		DamageCause dc = killed.getLastDamageCause().getCause();
		
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		
		ArrayList<ItemStack> items = new ArrayList<>();
		
		Equip e = obtenirEquip(ply);
		
		items.add(new ItemStack(Material.IRON_SWORD, 1));
		items.add(new ItemStack(Material.TNT, 1));
		
		updateScoreBoard(ply);
		
		return items;
	}
	
	protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
		
		super.onPlayerInteract(evt, p);
		
		Player player = evt.getPlayer();
		
		if (player.getItemInHand().getType() == Material.TNT && evt.getAction() == Action.RIGHT_CLICK_AIR) {
						
			if(hasTntCooldown(player)) {
				
				Long cooldown = getTntCooldown(player);
				
				Double time = cooldown / 1000.0;
				
				BountifulAPI.sendActionBar(player, "");
				
				String msg = ChatColor.RED + "" + ChatColor.ITALIC + "Aquest item encara no s'ha recarregat... " + time.toString() + "s";
				BountifulAPI.sendActionBar(player, msg);
				
			} else {
				
				TNTPrimed tnt = p.getWorld().spawn(p.getEyeLocation(), TNTPrimed.class);
				
				if(player.isSneaking()) {
					// Lower speed if sneaking
					tnt.setVelocity(player.getLocation().getDirection().multiply(1.5));
				} else if(player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR) && !player.getLocation().subtract(0, 2, 0).getBlock().getType().equals(Material.AIR)) {
					// More speed if jumping
					tnt.setVelocity(player.getLocation().getDirection().multiply(2.5));
				} else {
					tnt.setVelocity(player.getLocation().getDirection().multiply(2));
				}
				
				tntOwner.put(tnt.getEntityId(), player.getUniqueId());
				
				tnt.setFuseTicks(5 * 20);
				
				setTntCooldown(player);
				
			}
			

			
		}
	}
	
	@Override
	protected synchronized void gameEvent(Event event) {
		
		super.gameEvent(event);
		
		if (event instanceof EntityEvent){
			
			if (event instanceof EntityExplodeEvent){
				
				EntityExplodeEvent evt = (EntityExplodeEvent)event;
								
				evt.setYield(0);
				
				Entity entity = ((EntityEvent)event).getEntity();
				
				if (entity.getType() == EntityType.PRIMED_TNT){
					
					// Get tnt owner
				    if (tntOwner.containsKey(entity.getEntityId())) {
				       UUID user = tntOwner.get(entity.getEntityId());
				       
				       Player owner = Bukkit.getPlayer(user);
				       Equip ownerTeam = obtenirEquip(owner);
				       Equip ownerEnemic = obtenirEquipEnemic(owner);
				       
				       // String regOwnerCore = "RegT" + (ownerTeam.getId() + 1) +"0";
				       String regEnemicCore = "RegT" + (ownerEnemic.getId() + 1) +"0";
				       
				       // Cuboid ownerCore = pMapaActual().ObtenirCuboid(regOwnerCore, getWorld());
				       Cuboid enemicCore = pMapaActual().ObtenirCuboid(regEnemicCore, getWorld());
				       
				       ArrayList<Block> rem = new ArrayList<>();
				       Integer destroyedEnemyBlocks = 0;
				       
				       for (Block blk : evt.blockList()) {
							
				    	   if (enemicCore.contains(blk)){
							
				    		   destroyedEnemyBlocks++;
				    		   blk.setType(Material.AIR);
								
				    	   } else {
				    		   rem.add(blk);
				    	   }
				       }
				       
				       if(destroyedEnemyBlocks > 0) {  
				    	   
				    	   Integer totalToDestroy = 0;
				    	   
				    	   if(ownerEnemic.getId() == 1) {
				    		   totalToDestroy = team1CoreTotal;
				    		   team1CoreDestruit = team1CoreDestruit + destroyedEnemyBlocks;
				    	   } else {
				    		   totalToDestroy = team0CoreTotal;
				    		   team0CoreDestruit = team0CoreDestruit + destroyedEnemyBlocks;
				    	   }
				    	   
				    	   
				    	   
				    	   Double destroyRatio = ((double)destroyedEnemyBlocks / totalToDestroy) * 100.F;
				    	   destroyRatio = Math.floor(destroyRatio * 100) / 100.F;
				    	   
				    	   sendGlobalMessage(getGameDisplayName() +  owner.getName() + " ha destruit el core enemic en un " + destroyRatio + "%");
				    	   sendGlobalSound(Sound.ENTITY_FIREWORK_TWINKLE, 100, 0);
				    	   
				    	   for(Player p: getPlayers()) {
				    		   
				    		   updateScoreBoard(p);
				    	   }
				    	   
				    	  
				    	   
				       }
				       
				       evt.blockList().removeAll(rem);
				       
					} else {
						
						evt.blockList().clear();
					}

					
				} else {
					evt.setCancelled(true);
				}
				
			}
			
		}
		
	}
	
	@Override
	protected void updateScoreBoard(Player ply) {
		super.updateScoreBoard(ply);
			
			ArrayList<String> list = new ArrayList<>();
			
			// list.add("  ");
			
			for (Equip e : Equips) {
				
				Integer id = e.getId();
				
				Integer total = 0;
				Integer destruit = 0;
				
				if (id == 1) {
					total = team1CoreTotal;
					destruit = team1CoreDestruit;
				} else {
					total = team0CoreTotal;
					destruit = team0CoreDestruit;		
				}	
			    				
		    	Double destroyRatio = ((double)destruit / total) * 100.F;
		    	
		    	destroyRatio = Math.floor(destroyRatio);
		    	destroyRatio = 100 - destroyRatio;
		    	
		    	if(id == 1)	list.add(e.getChatColor() + "Equip " + e.getAdjectiu() + "       " + ChatColor.GRAY + destroyRatio + "% ");
		    	else list.add(e.getChatColor() + "Equip " + e.getAdjectiu() + "   " + ChatColor.GRAY + destroyRatio + "% ");

		    	// if(id == 1)list.add("   ");
		    	// else list.add("    ");
			}
			// list.add("     ");
			list.add(ChatColor.ITALIC + "by lluiscab");
			ScoreBoardUpdater.setScoreBoard(ply, "TntWars", list, null);

	}

}
