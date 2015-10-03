package com.biel.BielAPI.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.*;
import org.bukkit.event.world.*;

import com.biel.BielAPI.BielAPI;
import com.biel.BielAPI.Com;

public class GeneralListener implements Listener {

	public GeneralListener() {
		BielAPI plugin = Com.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getLogger().info("Listener (G) created!");

	}
	public void h(Event evt) { //Handle
		Com.getPlugin().evtgest.recieveEvent(evt);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent evt) {h(evt);}

	@EventHandler
	public void onItemDespawnEvent(ItemDespawnEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent evt) {h(evt);}
	@EventHandler
	public void onProjectileLaunchEvent(ProjectileLaunchEvent evt) {h(evt);}
	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent evt) {h(evt);}
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {h(evt);}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {h(evt);}
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent evt) {h(evt);}
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent evt) {h(evt);}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent evt) {h(evt);}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent evt) {h(evt);}
	@EventHandler
	public void onBlockDispense(BlockDispenseEvent evt) {h(evt);}
	@EventHandler
	public void onBlockFade(BlockFadeEvent evt) {h(evt);}
	@EventHandler
	public void onBlockForm(BlockFormEvent evt) {h(evt);}
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent evt) {h(evt);}
	@EventHandler
	public void onBlockGrow(BlockGrowEvent evt) {h(evt);}
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent evt) {h(evt);}
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent evt) {h(evt);}
	//	@EventHandler
	//	public void onBlockPiston(BlockPistonEvent evt) {h(evt);}
	//	@EventHandler
	//	public void onBlockPistonExtend(BlockPistonExtendEvent evt) {h(evt);}
	@EventHandler
	public void onBlockPistonRetract(BlockPistonRetractEvent evt) {h(evt);}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {h(evt);}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {h(evt);}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent evt) {h(evt);}

	//		@EventHandler
	//		public void onBlockRedstone(BlockRedstoneEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onBlockSpread(BlockSpreadEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityBlockForm(EntityBlockFormEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onLeavesDecay(LeavesDecayEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onNotePlay(NotePlayEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onSignChange(SignChangeEvent evt) {h(evt);}
	@EventHandler
	public void onEnchantItem(EnchantItemEvent evt) {h(evt);}
	@EventHandler
	public void onPrepareItemEnchant(PrepareItemEnchantEvent evt) {h(evt);}
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent evt) {h(evt);}
	@EventHandler
	public void onEntityBreakDoor(EntityBreakDoorEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityChangeBlock(EntityChangeBlockEvent evt) {h(evt);}
	@EventHandler
	public void onEntityCombustByBlock(EntityCombustByBlockEvent evt) {h(evt);}
	@EventHandler
	public void onEntityCombustByEntity(EntityCombustByEntityEvent evt) {h(evt);}
	@EventHandler
	public void onEntityCombust(EntityCombustEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityCreatePortal(EntityCreatePortalEvent evt) {h(evt);}
	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityDamage(EntityDamageEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityDeath(EntityDeathEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityExplode(EntityExplodeEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityInteract(EntityInteractEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityPortalEnterEvent(EntityPortalEnterEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityRegainHealth(EntityRegainHealthEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityShootBow(EntityShootBowEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityTame(EntityTameEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onEntityTarget(EntityTargetEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onEntityTeleport(EntityTeleportEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onExpBottle(ExpBottleEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onExplosionPrime(ExplosionPrimeEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onFoodLevelChange(FoodLevelChangeEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onItemDespawn(ItemDespawnEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onPigZap(PigZapEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onPlayerDeath(PlayerDeathEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onPotionSplash(PotionSplashEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onSheepDyeWool(SheepDyeWoolEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onSheepRegrowWool(SheepRegrowWoolEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onSlimeSplit(SlimeSplitEvent evt) {h(evt);}
	//		@EventHandler
	////		public void onBrew(BrewEvent evt) {h(evt);}
	////		@EventHandler
	//		public void onCraftItem(CraftItemEvent evt) {h(evt);}
	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent evt) {h(evt);}
	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent evt) {h(evt);}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) {h(evt);}
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent evt) {h(evt);}
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onAsyncPlayerChat(AsyncPlayerChatEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent evt) {h(evt);}
	///////////////////////
	@EventHandler
	public void onPlayerAnimation(PlayerAnimationEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent evt) {h(evt);}
	//		@EventHandler
	////		public void onPlayerChannel(PlayerChannelEvent evt) {h(evt);}
	////		@EventHandler
	//		public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent evt) {h(evt);}
	//		@EventHandler
	////		public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evt) {h(evt);}
	////		@EventHandler
	//		public void onPlayerDropItem(PlayerDropItemEvent evt) {h(evt);}
	//		@EventHandler
	////		public void onPlayerEggThrow(PlayerEggThrowEvent evt) {h(evt);}
	////		@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerFish(PlayerFishEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerItemBreak(PlayerItemBreakEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onPlayerKick(PlayerKickEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onPlayerLevelChange(PlayerLevelChangeEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent evt) {h(evt);}

	//	@EventHandler
	//	public void onPlayerPortal(PlayerPortalEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onPlayerRegisterChannel(PlayerRegisterChannelEvent evt) {h(evt);}

	@EventHandler
	public void onPlayerShearEntity(PlayerShearEntityEvent evt) {h(evt);}
	//	@EventHandler
	//	public void onPlayerTeleport(PlayerTeleportEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onPlayerUnregisterChannel(PlayerUnregisterChannelEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerVelocity(PlayerVelocityEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleBlockCollision(VehicleBlockCollisionEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleCollsion(VehicleCollisionEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleCreate(VehicleCreateEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleDamage(VehicleDamageEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleDestroy(VehicleDestroyEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleEnter(VehicleEnterEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleEntityCollision(VehicleEntityCollisionEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleExit(VehicleExitEvent evt) {h(evt);}
	////		@EventHandler
	//		public void onVehicleMove(VehicleMoveEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onVehicleUpdate(VehicleUpdateEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onLightningStrike(LightningStrikeEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onThunderChange(ThunderChangeEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onWeatherChange(WeatherChangeEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onChunkLoad(ChunkLoadEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onChunkPopulate(ChunkPopulateEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onChunkUnload(ChunkUnloadEvent evt) {h(evt);}
	@EventHandler
	public void onPortalCreate(PortalCreateEvent evt) {h(evt);}
	//		@EventHandler
	//		public void onSpawnChange(SpawnChangeEvent evt) {h(evt);}
	@EventHandler
	public void onStructureGrow(StructureGrowEvent evt) {h(evt);}
	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent evt) {h(evt);}

	////		@EventHandler
	////		public void onWorldInit(WorldInitEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onWorldLoad(WorldLoadEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onWorldSave(WorldSaveEvent evt) {h(evt);}
	////		@EventHandler
	////		public void onWorldUnload(WorldUnloadEvent evt) {h(evt);}
}
