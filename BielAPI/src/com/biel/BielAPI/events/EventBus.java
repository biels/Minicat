package com.biel.BielAPI.events;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;

import com.biel.BielAPI.Com;
import com.biel.BielAPI.Utils.GUtils;

public class EventBus { //Bus d'esdeveniments del joc
	ArrayList<Event> recieved = new ArrayList<Event>();
	private boolean destroyed = false;
	public EventBus() {
		Com.getPlugin().evtgest.registerEventBus(this);
		//Com.getPlugin().evtgest.unregisterInvalidBuses();
	}
	public void destroyEventBus(){
		destroyed = true;
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		destroyEventBus();
		super.finalize();
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public boolean isValid(){
		if(isDestroyed())return false;
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return MessageFormat.format("{0}[Destroyed={1}, Valid={2}]", super.toString(), isDestroyed(), isValid());
	}
	public synchronized void recieveEvent(Event evt) {
		if(!isValid())return;
		if(recieved.contains(evt))return;
		if (verifyEvent(evt)){gameEvent(evt);}
		if(GUtils.Possibilitat(4)){
			//recieved.clear();
		}
		recieved.add(evt);
	}
	protected synchronized void gameEvent(Event event){
		//BLOCK
		if (event instanceof BlockEvent){ 
			Block blk = ((BlockEvent)event).getBlock();
			Location loc = blk.getLocation();
			if (event instanceof BlockBreakEvent){
				BlockBreakEvent evt = (BlockBreakEvent)event;
				onBlockBreak(evt, blk);    	
			}
			if (event instanceof BlockPlaceEvent){
				BlockPlaceEvent evt = (BlockPlaceEvent)event;
				onBlockPlace(evt, blk);				       	
			}
			if (event instanceof FurnaceBurnEvent){
				FurnaceBurnEvent evt = (FurnaceBurnEvent)event;
				if(blk.getType() == Material.FURNACE || blk.getType() == Material.BURNING_FURNACE){
					onFurnaceBurn(evt, blk, (Furnace) blk.getState());				       						
				}
			}
			if (event instanceof FurnaceSmeltEvent){
				FurnaceSmeltEvent evt = (FurnaceSmeltEvent)event;
				if(blk.getType() == Material.FURNACE || blk.getType() == Material.BURNING_FURNACE){
					onFurnaceSmelt(evt, blk, (Furnace) blk.getState());				       						
				}
			}
		}
		//ENTITY
		if (event instanceof EntityEvent){
			Entity entity = ((EntityEvent)event).getEntity();
			Location loc = entity.getLocation();
			if (event instanceof PlayerDeathEvent){
				PlayerDeathEvent evt = (PlayerDeathEvent)event;
				Player killed = evt.getEntity();
				onPlayerDeath(evt, killed);
				if (killed.getKiller() != null){
					Player killer = killed.getKiller();
					onPlayerDeathByPlayer(evt, killed, killer);
				}
			}
			if (event instanceof ExplosionPrimeEvent){
				ExplosionPrimeEvent evt = (ExplosionPrimeEvent)event;
				onExplosionPrime(evt);
			}
			if (event instanceof EntityDamageEvent){
				EntityDamageEvent evt = (EntityDamageEvent)event;
				onEntityDamage(evt, entity);
				if (entity instanceof Player){
					onPlayerDamage(evt, (Player)entity);
				}
			}
			if (event instanceof EntityDamageByEntityEvent){
				EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent)event;
				Entity damaged =  evt.getEntity();
				Entity damager =  evt.getDamager();
				onEntityDamageByEntity(evt, damaged, damager);
				if(evt.getEntity() instanceof Player){
					Player Pdamaged = (Player) evt.getEntity();
					Player Pdamager = null;
					boolean ranged = false;
					if (evt.getDamager() instanceof Player){
						Pdamager = (Player) evt.getDamager();
					}
					if (evt.getDamager() instanceof Projectile){
						ProjectileSource shooter = ((Projectile) evt.getDamager()).getShooter();
						if (shooter instanceof Player){
							Pdamager = (Player) shooter;
							ranged = true;
						}						
					}
					if (Pdamaged != null && Pdamager != null){
						onPlayerDamageByPlayer(evt, Pdamaged, Pdamager, ranged);
					}
				}
			}
			if (event instanceof ProjectileLaunchEvent){
				ProjectileLaunchEvent evt = (ProjectileLaunchEvent)event;
				Projectile proj = evt.getEntity();
				LivingEntity shooter = (LivingEntity) proj.getShooter();
				onProjectileLaunch(evt, proj);
			}
			if (event instanceof ProjectileHitEvent){
				ProjectileHitEvent evt = (ProjectileHitEvent)event;
				Projectile proj = evt.getEntity();
				LivingEntity shooter = (LivingEntity) proj.getShooter();
				onProjectileHit(evt, proj);
				//Block Hit
				World world = entity.getWorld();

				if((shooter instanceof Player)){
					Player player = (Player)shooter;
					BlockIterator iterator = new BlockIterator(world, proj.getLocation().toVector(), proj.getVelocity().normalize(), 0, 4);
					Block hitBlock = null;

					while(iterator.hasNext()) {
						hitBlock = iterator.next();
						// hitBlock.breakNaturally();
						if(GUtils.isValidSolidBlock(hitBlock)){ break;}
					}
					if (hitBlock != null) {
						onBlockHitByProjectile(evt, hitBlock, proj);
					}

				}

			
				//----
			}
			if (event instanceof EntityExplodeEvent){
				EntityExplodeEvent evt = (EntityExplodeEvent)event;
				onEntityExplode(evt, entity);
			}
			if (event instanceof EntityDeathEvent){
				EntityDeathEvent evt = (EntityDeathEvent)event;
				onEntityDeath(evt, entity);
			}
			if (event instanceof ItemDespawnEvent){
				ItemDespawnEvent evt = (ItemDespawnEvent)event;
				onItemDespawn(evt, evt.getEntity());
			}
		}
		//PLAYER
		if (event instanceof PlayerEvent){
			Player p = ((PlayerEvent)event).getPlayer();
			if (event instanceof PlayerTeleportEvent){
				PlayerTeleportEvent evt = (PlayerTeleportEvent)event;
				onPlayerTeleport(evt, p, evt.getFrom(), evt.getTo(), evt.getCause());
			}
			if (event instanceof PlayerItemHeldEvent){
				PlayerItemHeldEvent evt = (PlayerItemHeldEvent)event;
				onPlayerItemHeld(evt, p);
			}
			if (event instanceof PlayerInteractEvent){
				PlayerInteractEvent evt = (PlayerInteractEvent)event;
				ItemStack stack = evt.getItem();
				Inventory inv = p.getInventory();
				onPlayerInteract(evt, evt.getPlayer());

			}
			
			if (event instanceof PlayerInteractEntityEvent){
				PlayerInteractEntityEvent evt = (PlayerInteractEntityEvent)event;
				onPlayerInteractEntity(evt, p);
			}
			//PlayerPickupItemEvent
			if (event instanceof PlayerPickupItemEvent){
				PlayerPickupItemEvent evt = (PlayerPickupItemEvent)event;
				onPlayerPickupItem(evt, evt.getPlayer());
			}
			if (event instanceof PlayerMoveEvent){
				PlayerMoveEvent evt = (PlayerMoveEvent)event;
				onPlayerMove(evt, evt.getPlayer());
				int pN = evt.getFrom().getWorld().getPlayers().size();
				if (pN > 0){
					if (GUtils.Possibilitat(50)){
						onPlayerMoveDistributed(evt, p);
					}
				}

			}
			if (event instanceof PlayerJoinEvent){
				PlayerJoinEvent evt = (PlayerJoinEvent)event;
				onPlayerJoin(evt, p);
			}
			if (event instanceof PlayerQuitEvent){
				PlayerQuitEvent evt = (PlayerQuitEvent)event;
				onPlayerQuit(evt, p);
			}
			if (event instanceof PlayerChangedWorldEvent){
				PlayerChangedWorldEvent evt = (PlayerChangedWorldEvent)event;
				onPlayerChangedWorld(evt, p);
			}
			if (event instanceof PlayerRespawnEvent){
				PlayerRespawnEvent evt = (PlayerRespawnEvent)event;
				onPlayerRespawn(evt, p);
			}
			if (event instanceof PlayerBucketFillEvent){
				PlayerBucketFillEvent evt = (PlayerBucketFillEvent)event;
				onPlayerBucketFill(evt, p);
			}
			if (event instanceof PlayerBucketEmptyEvent){
				PlayerBucketEmptyEvent evt = (PlayerBucketEmptyEvent)event;
				onPlayerBucketEmpty(evt, p);
			}
			if (event instanceof PlayerItemBreakEvent){
				PlayerItemBreakEvent evt = (PlayerItemBreakEvent)event;
				onPlayerItemBreak(evt, p);
			}
			if (event instanceof PlayerFishEvent){
				PlayerFishEvent evt = (PlayerFishEvent)event;
				onPlayerFish(evt, p);
			}
			if (event instanceof PlayerLevelChangeEvent){
				PlayerLevelChangeEvent evt = (PlayerLevelChangeEvent)event;
				onPlayerLevelChange(evt, p);
			}
			if (event instanceof PlayerGameModeChangeEvent){
				PlayerGameModeChangeEvent evt = (PlayerGameModeChangeEvent)event;
				onPlayerGameModeChange(evt, p);
			}
			if (event instanceof PlayerToggleFlightEvent){
				PlayerToggleFlightEvent evt = (PlayerToggleFlightEvent)event;
				onPlayerToggleFlight(evt, p);
			}
			if (event instanceof PlayerToggleFlightEvent){
				PlayerToggleFlightEvent evt = (PlayerToggleFlightEvent)event;
				onPlayerToggleFlight(evt, p);
			}
			if (event instanceof PlayerToggleSneakEvent){
				PlayerToggleSneakEvent evt = (PlayerToggleSneakEvent)event;
				onPlayerToggleSneak(evt, p);
			}
			if (event instanceof PlayerToggleSprintEvent){
				PlayerToggleSprintEvent evt = (PlayerToggleSprintEvent)event;
				onPlayerToggleSprint(evt, p);
			}
			if (event instanceof PlayerAnimationEvent){
				PlayerAnimationEvent evt = (PlayerAnimationEvent)event;
				onPlayerAnimation(evt, p);
			}
		}
		//INVENTORY
		if(event instanceof InventoryEvent){
			Inventory inv = ((InventoryEvent) event).getInventory();
			//TODO
			if(event instanceof InventoryOpenEvent){
				InventoryOpenEvent evt = (InventoryOpenEvent) event;
				onInventoryOpen(evt, inv);
			}
			if(event instanceof InventoryCloseEvent){
				InventoryCloseEvent evt = (InventoryCloseEvent) event;
				onInventoryClose(evt, inv);
			}
			if(event instanceof InventoryClickEvent){
				InventoryClickEvent evt = (InventoryClickEvent) event;
				onInventoryClick(evt, inv);
			}
		}
	}

	////EVENTS
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
	}
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
	}
	protected void onBlockBurn(BlockBurnEvent evt, Block blk) {
	}
	protected void onBlockDamage(BlockDamageEvent evt, Block blk) {
	}
	protected void onBlockDispense(BlockDispenseEvent evt, Block blk) {
	}
	protected void onBlockFade(BlockFadeEvent evt, Block blk) {
	}
	protected void onBlockForm(BlockFormEvent evt, Block blk) {
	}
	protected void onBlockFromTo(BlockFromToEvent evt, Block blk) {
	}
	protected void onBlockGrow(BlockGrowEvent evt, Block blk) {
	}
	protected void onBlockIgnite(BlockIgniteEvent evt, Block blk) {
	}
	protected void onBlockPhysics(BlockPhysicsEvent evt, Block blk) {
	}
	protected void onEntityDamage(EntityDamageEvent evt, Entity e) {
	}
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
	}
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt, Entity damaged, Entity damager) {
	}
	//*TODO
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
	}
	protected void onEntityDamageByBlock(EntityDamageByBlockEvent evt) {
	}
	protected void onEntityCombustByBlock(EntityCombustByBlockEvent evt) {
	}
	protected void onEntityCombustByEntity(EntityCombustByEntityEvent evt) {
	}
	protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
	}
	protected void onProjectileLaunch(ProjectileLaunchEvent evt, Projectile proj) {
	}
	protected void onBlockHitByProjectile(ProjectileHitEvent evt, Block b, Projectile proj) {
	}
	protected void onEntityExplode(EntityExplodeEvent evt, Entity e) {
	}
	protected void onEntityDeath(EntityDeathEvent evt, Entity e) {
	}
	protected void onExplosionPrime(ExplosionPrimeEvent evt) {
	}
	protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
	}
	protected void onPlayerInteractEntity(PlayerInteractEntityEvent evt, Player p) {
	}
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
	}
	protected void onPlayerDropItem(PlayerDropItemEvent evt, Player p) {
	}
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
	}
	protected void onPlayerJoin(PlayerJoinEvent evt, Player p) {
	}
	protected void onPlayerQuit(PlayerQuitEvent evt, Player p) {
	}
	protected void onPlayerMoveDistributed(PlayerMoveEvent evt, Player p) {
	}
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
	}
	protected void onPlayerItemHeld(PlayerItemHeldEvent evt, Player p) {
	}
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
	}
	protected void onPlayerRespawn(PlayerRespawnEvent evt, Player p) {
		final Player player = p;
		final PlayerRespawnEvent fEvt = evt;
		Bukkit.getScheduler().runTaskLater(Com.getPlugin(), new Runnable() {
			@Override
			public void run() {
				if(player.isValid()){
					onPlayerRespawnAfterTick(fEvt, player);
				}
			}
		}, 1);
	}
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
	}
	protected void onPlayerGameModeChange(PlayerGameModeChangeEvent evt, Player p) {
	}
	protected void onPlayerFish(PlayerFishEvent evt, Player p) {
	}
	protected void onPlayerItemBreak(PlayerItemBreakEvent evt, Player p) {
	}
	protected void onPlayerLevelChange(PlayerLevelChangeEvent evt, Player p) {
	}
	protected void onPlayerBucket(PlayerBucketEvent evt, Player p) {
	}
	protected void onPlayerBucketFill(PlayerBucketFillEvent evt, Player p) {
	}
	protected void onPlayerBucketEmpty(PlayerBucketEmptyEvent evt, Player p) {
	}
	protected void onItemDespawn(ItemDespawnEvent evt, Item i) {
	}
	protected void onPlayerShearEntity(PlayerShearEntityEvent evt) {
	}
	protected void onPlayerPortal(PlayerPortalEvent evt) {
	}
	protected void onPlayerChangedWorld(PlayerChangedWorldEvent evt, Player p) {
	}
	protected void onPlayerTeleport(PlayerTeleportEvent evt, Player p, Location from, Location to, TeleportCause c) {
	}
	protected void onPlayerToggleFlight(PlayerToggleFlightEvent evt, Player p) {
	}
	protected void onPlayerToggleSneak(PlayerToggleSneakEvent evt, Player p) {
	}
	protected void onPlayerToggleSprint(PlayerToggleSprintEvent evt, Player p) {
	}
	protected void onPlayerAnimation(PlayerAnimationEvent evt, Player p) {
	}
	protected void onPlayerBedEnter(PlayerBedEnterEvent evt, Player p) {
	}
	protected void onPlayerBedLeave(PlayerBedLeaveEvent evt, Player p) {
	}
	protected void onWeatherChange(WeatherChangeEvent evt) {
	}
	protected void onPortalCreate(PortalCreateEvent evt) {
	}
	protected void onStructureGrow(StructureGrowEvent evt) {
	}
	protected void onCreatureSpawn(CreatureSpawnEvent evt) {
	}
	protected void onEnchantItem(EnchantItemEvent evt) {
	}
	protected void onPrepareItemEnchant(PrepareItemEnchantEvent evt) {
	}
	protected void onFurnaceBurn(FurnaceBurnEvent evt, Block blk, Furnace f) { //
	}
	protected void onFurnaceSmelt(FurnaceSmeltEvent evt, Block blk, Furnace f) {
	}
	protected void onInventoryClick(InventoryClickEvent evt, Inventory inv) {
	}
	protected void onInventoryClose(InventoryCloseEvent evt, Inventory inv) {
	}
	protected void onInventoryOpen(InventoryOpenEvent evt, Inventory inv) {
	}




	protected Boolean verifyEvent(Event evt){
		return true;
	}
}
