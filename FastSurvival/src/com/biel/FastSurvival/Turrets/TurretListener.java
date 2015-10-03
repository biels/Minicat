package com.biel.FastSurvival.Turrets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Vine;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;

import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Turrets.*;
import com.biel.FastSurvival.Utils.Utils;

public class TurretListener implements Listener{
	//	@EventHandler
	//	public void onHit(ProjectileHitEvent evt) {
	//
	//
	//	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		Player ply = evt.getPlayer();
		Block blk = evt.getBlock();
		TurretData data = TurretUtils.getTurretAt(blk.getLocation());
		if (data == null){return;}
		Turret t = new Turret(data);
		Boolean containsTurretBlock = t.ContainsTurretBlock(blk.getLocation());
		if (containsTurretBlock && ply.getGameMode() == GameMode.CREATIVE){
			t.Destroy();
			evt.setCancelled(true);
			return;
		}
		if (containsTurretBlock  && ply.getGameMode() != GameMode.CREATIVE){
			if (t.isFriendly(ply)){
				t.Destroy();
			}
			evt.setCancelled(true);
			return;
		}

	}
	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {

		Player p = evt.getPlayer();
		ItemStack i = p.getItemInHand();
		World world = FastSurvival.getPlugin().getServer().getWorlds().get(0);
		Inventory inv = p.getInventory();


		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			Block clickedBlock = evt.getClickedBlock();
			if (p.getItemInHand().getType() == Material.ARROW && TurretUtils.isTurret(i)){
				Block blk = clickedBlock.getRelative(evt.getBlockFace());

				Location l = blk.getLocation();
				if(Turret.canBeBuiltAt(l.clone())){
					TurretData data = TurretUtils.getItemStackData(i);
					data.setLocation(blk.getLocation());
					data.setOwner(p.getName());
					Turret t = new Turret(data);
					t.Build();
					p.getInventory().removeItem(i);
				}
				return;
			}

			TurretData d = TurretUtils.getTurretAt(clickedBlock.getLocation());
			if (d != null){
				Turret t = new Turret(d);
				if (t.isFriendly(p)){
					TurretMenu.displayMenu(p, d.iId);
				}
			}



		}
		if (evt.getAction() == Action.LEFT_CLICK_BLOCK){
			Block clickedBlock = evt.getClickedBlock();
			TurretData data = TurretUtils.getTurretAt(clickedBlock.getLocation());
			if (data != null){
				Turret t = new Turret(data);
				Boolean hit = true;

				if (t.isFriendly(p)){
					hit = false;
				}

				if (hit){
					t.Hit(1);
				}

			}
		}

	}
	//Handlers
	@EventHandler
	public void onEntDmg(EntityDamageByEntityEvent evt) {
		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		Entity rawDamager = evt.getDamager();
		if (rawDamager instanceof Projectile){
			Projectile p = (Projectile) rawDamager;
			TurretData data = TurretUtils.getShooterTurret(p);
			if (data == null){return;}
			Turret t = new Turret(data);
			if(damaged instanceof Player){
				Player ply = (Player) damaged;
				if (t.isFriendly(ply)){
					evt.setCancelled(true);
				}
			}
			evt.setDamage(t.getDamage());
		}

	}
	@EventHandler
	public void onHit(ProjectileHitEvent evt) {
		Projectile p = evt.getEntity();
		TurretData data = TurretUtils.getShooterTurret(p);
		if (data == null){return;}
		Turret t = new Turret(data);
		//t.d.setTicks(7);
		if (t.d.getTier() == 3){
			Location l = p.getLocation();
			Location location = t.d.getLocation();
			if (location != null){
				if (location.distance(l) > 25){
					p.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 2.5F, false, false);
				}
			}
		}
		if (Utils.Possibilitat(90)){
			p.remove();
		}


	}
	@EventHandler
	public void onHit2(ProjectileHitEvent evt) {
		//DAMAGING
		if (evt.getEntity() instanceof Arrow){
			Arrow entity = (Arrow)evt.getEntity();

			World world = entity.getWorld();
			Location loc = entity.getLocation();

			Projectile proj = (Projectile)entity;

			ProjectileSource source = proj.getShooter();
			LivingEntity shooter;
			if(source instanceof LivingEntity){
				shooter = (LivingEntity) source;
			}
			//Location land = loc.add(entity.getVelocity().normalize().multiply(0.8));
			Arrow arrow = (Arrow)proj;
			if((arrow.getShooter() instanceof Player)){
				Player player = (Player)arrow.getShooter();
				World world1 = arrow.getWorld();
				BlockIterator iterator = new BlockIterator(world1, arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0, 4);
				Block hitBlock = null;

				while(iterator.hasNext()) {
					hitBlock = iterator.next();
					// hitBlock.breakNaturally();
					if(hitBlock.getTypeId()!=0) //Check all non-solid blockid's here.
					{ break;}
				}
				//land.getBlock().setType(Material.IRON_BLOCK);
				TurretData data = TurretUtils.getTurretAt(hitBlock.getLocation());
				if (data != null){
					Turret t = new Turret(data);
					Boolean hit = true;

					if (t.isFriendly(player)){
						hit = false;
					}
					if (player.getLocation().distance(t.d.getLocation()) > t.getRange()){
						hit = false;
					}
					if (hit){
						t.Hit(3);
					}

				}

			}

		}
	}
	@EventHandler
	public void Craft(PrepareItemCraftEvent evt){
		ItemStack result = evt.getRecipe().getResult();
		ItemStack i = result;
		if(result.hasItemMeta()){
			ItemMeta itemMeta = result.getItemMeta();
			if (itemMeta.hasDisplayName()){
				String name = itemMeta.getDisplayName();
				if (name.equalsIgnoreCase("Crafted turret")){
					if (itemMeta.hasLore()){
						List<String> lore = itemMeta.getLore();
						if(lore.size() == 1){
							String strTier = lore.get(0);
							int tier = Integer.parseInt(strTier);
							evt.getInventory().setResult(TurretUtils.createNewItemStack(tier));
						}
					}
				}
			}

		}
	}
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent evt) {
		List<Block> toRemove = new ArrayList<Block>();
		List<Block> blockList = evt.blockList();
		for(Block b : blockList){
			if (b.getType() == Material.BRICK || b.getType() == Material.BRICK_STAIRS || b.getType() == Material.SMOOTH_BRICK || b.getType() == Material.SMOOTH_STAIRS){
				toRemove.add(b);
				continue;
			}
			TurretData turretAt = TurretUtils.getTurretAt(b.getLocation());
			if(turretAt != null){
				toRemove.add(b);
				if(turretAt.getTier() > 1){
					evt.setCancelled(true);
					return;
				}
			}
		}
		evt.blockList().removeAll(toRemove);
	}
	@EventHandler
	public void onPistonPush(BlockPistonExtendEvent evt) {
		List<Block> blockList = evt.getBlocks();
		for(Block b : blockList){
			TurretData turretAt = TurretUtils.getTurretAt(b.getLocation());
			if(turretAt != null){
				evt.setCancelled(true);
				return;
			}
		}
	}
}    

