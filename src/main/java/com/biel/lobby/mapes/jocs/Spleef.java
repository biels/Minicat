package com.biel.lobby.mapes.jocs;

import java.util.*;

import com.biel.BielAPI.Utils.GUtils;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.biel.lobby.mapes.JocLastStanding;
import com.biel.lobby.utilities.Utils;


public class Spleef extends JocLastStanding {
	private static final int WOOD_HOE_REVEAL_TICKS = 12;
	private static final int STONE_HOE_REVEAL_TICKS = 14;
	private static final int DIAMOND_HOE_REVEAL_TICKS = 16;
	private static final int[] IRON_HOE_REVEAL_DELAYS = {
			0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 15, 19, 24
	};
	private static final int[] GOLD_HOE_REVEAL_DELAYS = {
			0, 1, 2, 3, 5, 6, 8, 10, 12, 14, 16, 19, 22, 25, 29, 33, 41
	};

	public Spleef() {

	}
	public Material BREAK_TYPE = Material.AIR;
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Spleef";
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}
	@Override
	public void customJocIniciat() {
		super.customJocIniciat();
		setBlockBreakPlace(true);
		initAlivePlayers();
		BukkitTask shovelTask = new DonarPales().runTaskTimer(plugin, 4 * 20L, 6 * 20L);
		handleTask(shovelTask.getTaskId());

		for (Player p : getPlayers()) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 400 * 20, 8, true), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 20 * 20, 85, true), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 16 * 20, 2, true), true);
		}
	}



	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Color color1 = getDeterministicColorForPlayer(ply, false);
		Color color2 = getDeterministicColorForPlayer(ply, true);
		items.add(GUtils.createColoredArmor(Material.LEATHER_HELMET, color2));
		items.add(GUtils.createColoredArmor(Material.LEATHER_CHESTPLATE, color1));
		items.add(GUtils.createColoredArmor(Material.LEATHER_LEGGINGS, color2));
		items.add(GUtils.createColoredArmor(Material.LEATHER_BOOTS, color1));
		return items;
	}

	@Override
	protected void teletransportarTothom() {
		for (Player d : getPlayers()) {  // d gets successively each value in ar.
			ArrayList<Location> locs = pMapaActual().ObtenirLocations("arena", world);
			Location loc = new Location(world, Utils.NombreEntre(locs.get(0).getBlockX(), locs.get(1).getBlockX()), locs.get(0).getY() + 1, Utils.NombreEntre(locs.get(0).getBlockZ(), locs.get(1).getBlockZ()));
			d.teleport(loc);					
		} 
	}

	void Pales(){

	}
	public class DonarPales extends BukkitRunnable {
		public int vegades = 0;

		@Override
		public void run() {
			if (world == null || !JocEnMarxa()) {
				cancel();
				return;
			}

			for (Player player : getPlayers()) {
				Material material = pala(vegades);
				Inventory inventory = player.getInventory();
				ItemStack itemStack = new ItemStack(material, 1);

				if (vegades == 10) {
					itemStack.addUnsafeEnchantment(Enchantment.PUNCH, 1);
					inventory.addItem(new ItemStack(Material.ARROW, 3));
				}
				if (vegades == 12) {
					inventory.addItem(new ItemStack(Material.ARROW, 2));
				}

				if (material.name().endsWith("_SHOVEL")) {
					inventory.setItem(0, itemStack);
				} else {
					inventory.addItem(itemStack);
				}
			}

			vegades++;
		}

		public Material pala(int vegades){
				Material mat = Material.WOODEN_SHOVEL;
				if (vegades == 0){mat = Material.WOODEN_SHOVEL;}
				if (vegades == 1){mat = Material.WOODEN_HOE;}
				if (vegades == 2){mat = Material.STONE_SHOVEL;}
				if (vegades == 3){mat = Material.STONE_HOE;}	
				if (vegades == 4){mat = Material.IRON_SHOVEL;}
				if (vegades == 5){mat = Material.IRON_HOE;}	
				if (vegades == 6){mat = Material.TNT;}//{mat = Material.GOLD_SPADE;}
				//if (vegades == 7){mat = Material.ENDER_PEARL;}
				if (vegades == 7){mat = Material.GOLDEN_HOE;}
				if (vegades == 8){mat = Material.DIAMOND_SHOVEL;}
				if (vegades == 9){mat = Material.DIAMOND_HOE;}
				if (vegades == 10){mat = Material.BOW;}
				if (vegades == 11){mat = Material.TNT;}
				if (vegades == 12){mat = Material.ARROW;}
				if (vegades >= 13){
					if (Utils.Possibilitat(50)){
						mat = Material.DIAMOND_HOE;
						if (Utils.Possibilitat(68)){
							mat = Material.ARROW;
						}
					}else{
						mat = Material.IRON_HOE;
						if (Utils.Possibilitat(65)){
							mat = Material.TNT;
						}
					}
				}
				return mat;
		}

	}




	@Override
	protected synchronized void gameEvent(Event event) {
		//Bukkit.broadcastMessage("EVENT");
		super.gameEvent(event);
		if (event instanceof BlockEvent){
			Block blk = ((BlockEvent)event).getBlock();
			Location loc = blk.getLocation();
			if (event instanceof BlockBreakEvent){
				BlockBreakEvent evt = (BlockBreakEvent)event;
				if (blk.getType() != Material.SNOW_BLOCK){evt.setCancelled(true);}
				if(!evt.isCancelled()){

				}

			}
		}
		if (event instanceof EntityEvent){
			Entity entity = ((EntityEvent)event).getEntity();
			Location loc = entity.getLocation();
			if (event instanceof EntityDamageByEntityEvent){
				EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent)event;
				evt.setCancelled(true);
			}
			if (event instanceof ProjectileHitEvent){
				ProjectileHitEvent evt = (ProjectileHitEvent)event;
				Projectile proj = (Projectile)entity;
				LivingEntity shooter = (LivingEntity) proj.getShooter();
				if(proj instanceof Arrow)arrowAoE(loc, evt, shooter);

			}
			if (event instanceof ExplosionPrimeEvent){
				ExplosionPrimeEvent evt = (ExplosionPrimeEvent)event;
				evt.setRadius(6F);
			}
			if (event instanceof EntityExplodeEvent){
				EntityExplodeEvent evt = (EntityExplodeEvent)event;
				evt.setYield(8);
				if (entity.getType() == EntityType.TNT){
					ArrayList<Block> rem = new ArrayList<>();
					for (Block b : evt.blockList()){
						if (b.getType() != Material.SNOW_BLOCK){
							rem.add(b);
						}
					}
					evt.blockList().removeAll(rem);
				}
			}
		}
		if (event instanceof PlayerEvent){
			Player p = ((PlayerEvent)event).getPlayer();

			if (event instanceof PlayerInteractEvent){
				PlayerInteractEvent evt = (PlayerInteractEvent)event;
				ItemStack stack = evt.getItem();
				Inventory inv = p.getInventory();

				efectesPales(p, evt, stack, inv);    		

			}
			//PlayerPickupItemEvent
			if (event instanceof PlayerPickupItemEvent){
				PlayerPickupItemEvent evt = (PlayerPickupItemEvent)event;
				evt.setCancelled(true);
			}
			if (event instanceof PlayerMoveEvent) {

				PlayerMoveEvent evt = (PlayerMoveEvent)event;

				if (p.getLocation().getBlockY() < 12 && JocIniciat) {

					p.getInventory().clear();
					p.setHealth(20);
					p.setGameMode(GameMode.SPECTATOR);
					removeIfAlive(p);

					if(anyoneAlive()) {

						Player randAlivePlayer = getRandomAlivePlayer();
						p.teleport(randAlivePlayer.getLocation().add(0.0, 2.0, 0.0));

					}

				}		

			}
		}

	}

	private void arrowAoE(Location loc, ProjectileHitEvent evt,
			LivingEntity shooter) {
		if (evt.getEntityType() == EntityType.ARROW){        			
			//        			loc.getBlock().setType(BREAK_TYPE);
			int i = 2;
			while (i > 0){
				loc.setY(loc.getY() - 1);
				//            			loc.getBlock().setType(BREAK_TYPE);        			
				int x = -1;
				int z = -1;
				while(x <= 1){
					while(z <= 1){
						//            					Bukkit.broadcastMessage("X:" + Integer.toString(x));
						//            					Bukkit.broadcastMessage("Z:" + Integer.toString(z));

						Location loc2 = loc.clone();
						loc2.setZ(loc2.getZ() + z);
						loc2.setX(loc2.getX() + x);
						if (loc2.getBlock().getType() == Material.SNOW_BLOCK || loc2.getBlock().getType() == Material.AIR){
							loc2.getBlock().setType(BREAK_TYPE);
							ItemStack itemstack = new ItemStack(Material.DIAMOND, 1); // A stack of diamonds  
							world.dropItem(loc2, itemstack);
						}else{
							Material mat = Material.REDSTONE;
							if (Utils.Possibilitat(8)){mat = Material.ANVIL;}
							if (Utils.Possibilitat(10)){mat = Material.BLAZE_POWDER;}
							if (Utils.Possibilitat(10)){mat = Material.DRAGON_EGG;}
							if (Utils.Possibilitat(10)){mat = Material.BEDROCK;}
							ItemStack itemstack = new ItemStack(mat, 1); // A stack of diamonds  
							world.dropItem(loc2, itemstack);
						}


						z = z + 1;

					}
					x = x + 1;
					z = -1;
				}
				i = i - 1;
			}

		}
	}

	private void efectesPales(Player p, PlayerInteractEvent evt,
			ItemStack stack, Inventory inv) {
		if (stack == null) {return;}
		Material hand = stack.getType();
		if (hand == Material.LAPIS_BLOCK || hand == Material.REDSTONE_BLOCK || hand == Material.GOLD_BLOCK || hand == Material.IRON_BLOCK){
			if (evt.getAction() == Action.RIGHT_CLICK_AIR){
				Byte blockData = 0x0;
				FallingBlock tnt = world.spawnFallingBlock(p.getLocation(), hand, blockData);
				tnt.setVelocity(p.getLocation().getDirection().multiply(2));
				p.getInventory().removeItem(new ItemStack(hand, 1));
			}else{
				evt.setCancelled(true);
			}

		}
		if (hand == Material.TNT){
			if (evt.getAction() == Action.RIGHT_CLICK_AIR){
				Byte blockData = 0x0;
				TNTPrimed tnt = p.getWorld().spawn(p.getEyeLocation(), TNTPrimed.class);
				tnt.setVelocity(p.getLocation().getDirection().multiply(1.32F));
				tnt.setFuseTicks(2*20);
				p.getInventory().removeItem(new ItemStack(Material.TNT, 1));
			}else{
				evt.setCancelled(true);
			}

		}

		Boolean utilitzat = false;
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			Location loc = evt.getClickedBlock().getLocation();
			if (stack.getType() == Material.WOODEN_HOE || stack.getType() == Material.STONE_HOE  || stack.getType() == Material.IRON_HOE || stack.getType() == Material.GOLDEN_HOE || stack.getType() == Material.DIAMOND_HOE){
				Location ploc = p.getLocation();
				ploc.setY(ploc.getY() - 1);
				int dir = 0;

				Boolean CoordX = Math.abs(loc.getBlockX() - ploc.getBlockX())
						>= Math.abs(loc.getBlockZ() - ploc.getBlockZ());
				if (CoordX != null){
					Boolean Signe = null;
					if (CoordX == true){
                        Signe = loc.getBlockX() > ploc.getBlockX();
					}
					if (CoordX == false){
                        Signe = loc.getBlockZ() > ploc.getBlockZ();
					}
					if (Signe != null){
						//Modificar terreny
						int Increment = 1;
						if (Signe == false){Increment = -1;}
						//Location bloc = ploc.clone();
						Location bloc = loc.clone();
						//Bukkit.broadcastMessage("Signe=" + Signe.toString() + " CoordX=" + CoordX.toString());
						if (stack.getType() == Material.WOODEN_HOE || stack.getType() == Material.STONE_HOE){
							int pass = 0;
							int lastPass = Math.max(0, countSnowRun(bloc, CoordX, Increment) - 1);
							Boolean wood = false;
							while (bloc.getBlock().getType() == Material.SNOW_BLOCK){
								int revealTicks = stack.getType() == Material.WOODEN_HOE
										? WOOD_HOE_REVEAL_TICKS : STONE_HOE_REVEAL_TICKS;
								int delay = easeOutRevealDelay(pass, lastPass, revealTicks);
								if (bloc.equals(ploc) == false){
									if (stack.getType() == Material.WOODEN_HOE){
										utilitzat = true;
										if (wood == true){       								

											Location blocmod = bloc.clone();
											if (CoordX == false){
												blocmod.setX(bloc.getX() + 1);
												//blocmod.getBlock().setType(BREAK_TYPE);
												setBlockLater(blocmod.getBlock(), Material.SAND, delay);
												blocmod.setX(bloc.getX() - 1);
												//blocmod.getBlock().setType(BREAK_TYPE);
												setBlockLater(blocmod.getBlock(), Material.SAND, delay);
											}else{
												blocmod.setZ(bloc.getZ() + 1);
												setBlockLater(blocmod.getBlock(), BREAK_TYPE, delay);
												blocmod.setZ(bloc.getZ() - 1);
												setBlockLater(blocmod.getBlock(), BREAK_TYPE, delay);
											}
										}else{
											//bloc.getBlock().setType(BREAK_TYPE);
											setBlockLater(bloc.getBlock(), Material.SAND, delay);
										}
										wood = !wood;
									}
									if (stack.getType() == Material.STONE_HOE){
										utilitzat = true;
										setBlockLater(bloc.getBlock(), BREAK_TYPE, delay);
										if (Utils.Possibilitat(30)){        								
											int direcció = 1;
											if (Utils.Possibilitat(50)){
												direcció = -1;
											}        								
											Location blocmod = bloc.clone();
											if (CoordX == false){
												blocmod.setX(bloc.getX() + direcció);
											}else{
												blocmod.setZ(bloc.getZ() + direcció);
											}
											setBlockLater(blocmod.getBlock(), BREAK_TYPE, delay);
											ItemStack itemstack = new ItemStack(Material.STONE, 1); // A stack of diamonds  
											//world.dropItem(blocmod, itemstack);
										}
									}
								}
								if (CoordX == true){
									bloc.setX(bloc.getX() + Increment);
								}else{
									bloc.setZ(bloc.getZ() + Increment);
								}
								pass = pass + 1;
							}


						}
						if (stack.getType() == Material.IRON_HOE || stack.getType() == Material.GOLDEN_HOE || stack.getType() == Material.DIAMOND_HOE){

							int pass = 0;
							Boolean iron = true;
							int amp = 0;
							//
							//if (bloc.equals(ploc) == false){
							while (bloc.getBlock().getType() == Material.SNOW_BLOCK || bloc.getBlock().getType() == Material.AIR || stack.getType() == Material.DIAMOND_HOE){
								if (stack.getType() == Material.IRON_HOE){
									utilitzat = true;
									int delay = authoredRevealDelay(pass, IRON_HOE_REVEAL_DELAYS);
									if (pass <= 8){
										if (iron == true){
											Location blocmodp = bloc.clone();
											Location blocmodn = bloc.clone();
											breakBlockLater(bloc.getBlock(), delay,true);
											//bloc.getBlock().setType(Material.SAND);
											if (CoordX == false){
												int i = 0;
												while (i < amp){
													blocmodp.setX(blocmodp.getX() + 1);    											
													//blocmodp.getBlock().setType(Material.SAND);
													breakBlockLater(blocmodp.getBlock(), delay,true);
													blocmodn.setX(blocmodn.getX() - 1);    											
													//blocmodn.getBlock().setType(Material.SAND);
													breakBlockLater(blocmodn.getBlock(), delay,true);
													i = i + 1;
												}        											
											}else{
												int i = 0;
												while (i < amp){
													blocmodp.setZ(blocmodp.getZ() + 1);    											
													//blocmodp.getBlock().setType(Material.SAND);
													breakBlockLater(blocmodp.getBlock(), delay,true);
													blocmodn.setZ(blocmodn.getZ() - 1);    											
													//blocmodn.getBlock().setType(Material.SAND);
													breakBlockLater(blocmodn.getBlock(), delay,true);
													i = i + 1;
												}     
											}



											amp = amp + 1;
										}

										iron = !iron;
									}
									if (pass > 9){        									
										if (pass == 10){amp = 4;}
										if (pass == 11){amp = 4;}
										if (pass == 12){amp = 3;}
										if (pass == 13){amp = 1;}
										if (pass == 14){break;}
										Location blocmodp = bloc.clone();
										Location blocmodn = bloc.clone();
										breakBlockLater(bloc.getBlock(), delay,true);
										//bloc.getBlock().setType(BREAK_TYPE);
										if (CoordX == false){
											int i = 0;
											while (i < amp){
												blocmodp.setX(blocmodp.getX() + 1);    											
												//blocmodp.getBlock().setType(Material.SAND);
												breakBlockLater(blocmodp.getBlock(), delay,true);
												blocmodn.setX(blocmodn.getX() - 1);    											
												//blocmodn.getBlock().setType(Material.SAND);
												breakBlockLater(blocmodn.getBlock(), delay,true);
												i = i + 1;
											}        											
										}else{
											int i = 0;
											while (i < amp){
												blocmodp.setZ(blocmodp.getZ() + 1);    											
												//blocmodp.getBlock().setType(Material.SAND);
												breakBlockLater(blocmodp.getBlock(), delay,true);
												blocmodn.setZ(blocmodn.getZ() - 1);    											
												//blocmodn.getBlock().setType(Material.SAND);
												breakBlockLater(blocmodn.getBlock(), delay,true);
												i = i + 1;
											}     
										}
									}
								}
								if (stack.getType() == Material.GOLDEN_HOE){
									utilitzat = true;       								

									if (pass == 0){amp = 0;}
									if (pass == 1){amp = 1;}
									if (pass == 2){amp = 0;}
									if (pass == 3){amp = 0;}
									if (pass == 4){amp = 1;}
									if (pass == 5){amp = 2;}
									if (pass == 6){amp = 1;}
									if (pass == 7){amp = 0;}
									if (pass == 8){amp = 1;}
									if (pass == 9){amp = 2;}
									if (pass == 10){amp = 3;}
									if (pass == 11){amp = 3;}
									if (pass == 12){amp = 3;}
									if (pass == 13){amp = 2;}
									if (pass == 14){amp = 1;}
									if (pass == 15){amp = 0;}
									if (pass == 16){amp = 4;}
									if (pass == 17){break;}
									Location blocmodp = bloc.clone();
									Location blocmodn = bloc.clone();
									//bloc.getBlock().setType(BREAK_TYPE);
									int delay = authoredRevealDelay(pass, GOLD_HOE_REVEAL_DELAYS);
									breakBlockLater(bloc.getBlock(), delay,true);
									if (CoordX == false){
										int i = 0;
										while (i < amp){
											blocmodp.setX(blocmodp.getX() + 1);
											//blocmodp.getBlock().setType(Material.SAND);
											breakBlockLater(blocmodp.getBlock(), delay,true);
											blocmodn.setX(blocmodn.getX() - 1);    											
											//blocmodn.getBlock().setType(Material.SAND);
											breakBlockLater(blocmodn.getBlock(), delay,true);
											i = i + 1;
										}
									}else{
										int i = 0;
										while (i < amp){
											blocmodp.setZ(blocmodp.getZ() + 1);    											
											//blocmodp.getBlock().setType(Material.SAND);
											breakBlockLater(blocmodp.getBlock(), delay,true);
											blocmodn.setZ(blocmodn.getZ() - 1);    											
											//blocmodn.getBlock().setType(Material.SAND);
											breakBlockLater(blocmodn.getBlock(), delay,true);
											i = i + 1;
										}     
									}


								}
								if (stack.getType() == Material.DIAMOND_HOE){
									utilitzat = true;       								

									if (pass == 0){amp = 0;}
									if (pass == 1){amp = 0;}
									if (pass == 2){amp = 2;}
									if (pass >= 3 && pass <= 10){amp = 1;}
									if (pass == 11){amp = 0;}    									
									if (pass == 12){break;}
									Location blocmodp = bloc.clone();
									Location blocmodn = bloc.clone();
									int delay = easeOutRevealDelay(pass, 11, DIAMOND_HOE_REVEAL_TICKS);
									setBlockLater(bloc.getBlock(), BREAK_TYPE, delay);
									if (CoordX == false){
										int i = 0;
										while (i < amp){
											blocmodp.setX(blocmodp.getX() + 1);
											setBlockLater(blocmodp.getBlock(), BREAK_TYPE, delay);
											blocmodn.setX(blocmodn.getX() - 1);
											setBlockLater(blocmodn.getBlock(), BREAK_TYPE, delay);
											i = i + 1;
										}
									}else{
										int i = 0;
										while (i < amp){
											blocmodp.setZ(blocmodp.getZ() + 1);
											setBlockLater(blocmodp.getBlock(), BREAK_TYPE, delay);
											blocmodn.setZ(blocmodn.getZ() - 1);
											setBlockLater(blocmodn.getBlock(), BREAK_TYPE, delay);
											i = i + 1;
										}     
									}

								}
								if (CoordX == true){
									bloc.setX(bloc.getX() + Increment);
								}else{
									bloc.setZ(bloc.getZ() + Increment);
								}
								pass = pass + 1;
							}


							//}
						}

					}
				}
				//        			int distX = loc.getBlockX() - ploc.getBlockX();
				//        			int distZ = loc.getBlockZ() - ploc.getBlockZ();
				//        			if (distX > distZ){
				//        				Location bloc = ploc.clone();
				//        				while (bloc.getBlock().getType() == Material.SNOW_BLOCK){
				//        					bloc.getBlock().setType(BREAK_TYPE);
				//        					bloc.setX(bloc.getX() + 1);
				//        				}
				//        			}else{
				//        				Location bloc = ploc.clone();
				//        				while (bloc.getBlock().getType() == Material.SNOW_BLOCK){
				//        					bloc.getBlock().setType(BREAK_TYPE);
				//        					bloc.setZ(bloc.getZ() + 1);
				//        				}
				//        			}


				if (utilitzat == true && p.getGameMode() != GameMode.CREATIVE){
					inv.removeItem(new ItemStack(stack.getType()));	
				}        			
			}        			
		}
	}

	private int countSnowRun(Location start, boolean alongX, int increment) {
		Location cursor = start.clone();
		int length = 0;
		while (cursor.getBlock().getType() == Material.SNOW_BLOCK) {
			length++;
			if (alongX) {
				cursor.setX(cursor.getX() + increment);
			} else {
				cursor.setZ(cursor.getZ() + increment);
			}
		}
		return length;
	}

	private int easeOutRevealDelay(int step, int lastStep, int durationTicks) {
		if (lastStep <= 0 || step <= 0) return 0;
		double spatialProgress = Math.min(1D, step / (double) lastStep);
		// Invert easeOutCubic: the game knows distance and needs the tick when it is revealed.
		double timeProgress = 1D - Math.cbrt(1D - spatialProgress);
		return (int) Math.round(durationTicks * timeProgress);
	}

	private int authoredRevealDelay(int step, int[] timeline) {
		return timeline[Math.min(Math.max(step, 0), timeline.length - 1)];
	}

	private void setBlockLater(Block block, Material material, int delay) {
		Material expectedMaterial = block.getType();
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				if (world != null && block.getWorld() == world && block.getType() == expectedMaterial) {
					block.setType(material);
				}
			}
		}.runTaskLater(plugin, delay);
		handleTask(task.getTaskId());
	}

	private void breakBlockLater(Block block, int delay, boolean dropItems) {
		scheduleTrackedBlockRemoval(block, delay, dropItems);
	}

}
