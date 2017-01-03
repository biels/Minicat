package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
		BukkitTask task = new DonarPales().runTaskLater(plugin, 4 * 20);

		for (Player p : getPlayers()) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400 * 20, 8, true), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 20, 85, true), true); 
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 16 * 20, 2, true), true);
		}
	}



	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
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
		public Boolean Hoe = true;


		public DonarPales() {

		}

		public void run() {    	
			timer.schedule (hourlyTask, 0L, 1000 * 5 + 58);
		}
		Timer timer = new Timer ();
		TimerTask hourlyTask = new TimerTask () {
			@Override
			public void run () {
				List<Player> play = getPlayers();



				for (Player d : play) {  // d gets successively each value in ar.
					//	        		
					Material mat = pala(vegades);

                    Hoe = !mat.name().contains("_SPADE");
					Inventory inv = d.getInventory();


					ItemStack itemstack = new ItemStack(mat, 1);

					if (vegades == 6){
						//itemstack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
					}
					if (vegades == 10){
						itemstack.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
						ItemStack arr = new ItemStack(Material.ARROW, 3);
						inv.addItem(arr);
					}
					if (vegades == 12){            		
						ItemStack arr = new ItemStack(Material.ARROW, 2);
						inv.addItem(arr);
					}
					if (Hoe){
						inv.addItem(itemstack);  
					}else{
						inv.setItem(0, itemstack);
					}
				}
				if (vegades != 10){
					//	        			World world = plugin.getServer().getWorlds().get(0);
					//	        			ArrayList<Location> locs = plugin.pMapaActual.ObtenirLocations("arena", world);
					//	        			Location loc = new Location(world, plugin.NombreEntre(locs.get(0).getBlockX(), locs.get(1).getBlockX()), locs.get(0).getY() + 1, plugin.NombreEntre(locs.get(0).getBlockZ(), locs.get(1).getBlockZ()));
					//	        			ItemStack arr = new ItemStack(Material.LAPIS_BLOCK, 1);
					//	    				world.dropItem(loc, arr);
				}


				vegades = vegades + 1;

			}
			public Material pala(int vegades){
				Material mat = Material.WOOD_SPADE;    			
				if (vegades == 0){mat = Material.WOOD_SPADE;}
				if (vegades == 1){mat = Material.WOOD_HOE;}	
				if (vegades == 2){mat = Material.STONE_SPADE;}
				if (vegades == 3){mat = Material.STONE_HOE;}	
				if (vegades == 4){mat = Material.IRON_SPADE;}
				if (vegades == 5){mat = Material.IRON_HOE;}	
				if (vegades == 6){mat = Material.TNT;}//{mat = Material.GOLD_SPADE;}
				//if (vegades == 7){mat = Material.ENDER_PEARL;}
				if (vegades == 7){mat = Material.GOLD_HOE;}
				if (vegades == 8){mat = Material.DIAMOND_SPADE;}
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


		};

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
//					PlayerInfo playerInfo = getPlayerInfo(evt.getPlayer());
//					int step = playerInfo.getValue();
//					ArrayList<BlockFace> blockFaces = new ArrayList<BlockFace>();
//					if(step >= 2){
//						blockFaces.add(BlockFace.SOUTH);
//						blockFaces.add(BlockFace.NORTH);
//						blockFaces.add(BlockFace.EAST);
//						blockFaces.add(BlockFace.WEST);
//					}
//					if(step >= 4){
//						blockFaces.add(BlockFace.SOUTH_EAST);
//						blockFaces.add(BlockFace.NORTH_EAST);
//						blockFaces.add(BlockFace.SOUTH_WEST);
//						blockFaces.add(BlockFace.NORTH_WEST);
//						getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
//					}
//					getWorld().playSound(loc, Sound.CLICK, 0.8F + (0.4F * step), 1F + (0.4F * step));
//					blockFaces.forEach(f -> blk.getRelative(f).setType(BREAK_TYPE));
//					playerInfo.setValue(playerInfo.getValue() + 1);
//					if(playerInfo.getValue() > 4)playerInfo.setValue(0);
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
				if (entity.getType() == EntityType.PRIMED_TNT){
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
			if (event instanceof PlayerMoveEvent){
				PlayerMoveEvent evt = (PlayerMoveEvent)event;
				if (p.getLocation().getBlockY() < 6 && JocIniciat == true){
					p.getInventory().clear();
					removeIfAlive(p); 		 
				}		

			}
		}

	}

	private void arrowAoE(Location loc, ProjectileHitEvent evt,
			LivingEntity shooter) {
		if (evt.getEntityType() == EntityType.ARROW && shooter.getLocation().getBlockY() > 20){        			
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
		Material hand = p.getItemInHand().getType();
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
			if (stack.getType() == Material.WOOD_HOE || stack.getType() == Material.STONE_HOE  || stack.getType() == Material.IRON_HOE || stack.getType() == Material.GOLD_HOE || stack.getType() == Material.DIAMOND_HOE){  
				Location ploc = p.getLocation();
				ploc.setY(ploc.getY() - 1);
				int dir = 0;

				Boolean CoordX = null;
				if (loc.getBlockX() == ploc.getBlockX()){
					CoordX = false;
				}
				if (loc.getBlockZ() == ploc.getBlockZ()){
					CoordX = true;
				}
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
						if (stack.getType() == Material.WOOD_HOE || stack.getType() == Material.STONE_HOE){          						
							int pass = 0;
							Boolean wood = false;
							while (bloc.getBlock().getType() == Material.SNOW_BLOCK){
								if (bloc.equals(ploc) == false){
									if (stack.getType() == Material.WOOD_HOE){
										utilitzat = true;
										if (wood == true){       								

											Location blocmod = bloc.clone();
											if (CoordX == false){
												blocmod.setX(bloc.getX() + 1);
												//blocmod.getBlock().setType(BREAK_TYPE);
												blocmod.getBlock().setType(Material.SAND);
												blocmod.setX(bloc.getX() - 1);
												//blocmod.getBlock().setType(BREAK_TYPE);
												blocmod.getBlock().setType(Material.SAND);
											}else{
												blocmod.setZ(bloc.getZ() + 1);
												blocmod.getBlock().setType(BREAK_TYPE);
												blocmod.setZ(bloc.getZ() - 1);
												blocmod.getBlock().setType(BREAK_TYPE);
											}        										
										}else{
											//bloc.getBlock().setType(BREAK_TYPE);
											bloc.getBlock().setType(Material.SAND);
										}
										wood = !wood;
									}
									if (stack.getType() == Material.STONE_HOE){
										utilitzat = true;
										bloc.getBlock().setType(BREAK_TYPE);
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
											blocmod.getBlock().setType(BREAK_TYPE);
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
						if (stack.getType() == Material.IRON_HOE || stack.getType() == Material.GOLD_HOE || stack.getType() == Material.DIAMOND_HOE){ 

							int pass = 0;
							Boolean iron = true;
							int amp = 0;
							//
							//if (bloc.equals(ploc) == false){
							while (bloc.getBlock().getType() == Material.SNOW_BLOCK || bloc.getBlock().getType() == Material.AIR || stack.getType() == Material.DIAMOND_HOE){
								if (stack.getType() == Material.IRON_HOE){
									utilitzat = true;
									if (pass <= 8){
										if (iron == true){
											Location blocmodp = bloc.clone();
											Location blocmodn = bloc.clone();
											Utils.BreakBlockLater(bloc.getBlock(), (1 * pass)/2,true);
											//bloc.getBlock().setType(Material.SAND);
											if (CoordX == false){
												int i = 0;
												while (i < amp){
													blocmodp.setX(blocmodp.getX() + 1);    											
													//blocmodp.getBlock().setType(Material.SAND);
													Utils.BreakBlockLater(blocmodp.getBlock(), (1 * pass)/2,true);
													blocmodn.setX(blocmodn.getX() - 1);    											
													//blocmodn.getBlock().setType(Material.SAND);
													Utils.BreakBlockLater(blocmodn.getBlock(), (1 * pass)/2,true);
													i = i + 1;
												}        											
											}else{
												int i = 0;
												while (i < amp){
													blocmodp.setZ(blocmodp.getZ() + 1);    											
													//blocmodp.getBlock().setType(Material.SAND);
													Utils.BreakBlockLater(blocmodp.getBlock(), (1 * pass)/2,true);
													blocmodn.setZ(blocmodn.getZ() - 1);    											
													//blocmodn.getBlock().setType(Material.SAND);
													Utils.BreakBlockLater(blocmodn.getBlock(), (1 * pass)/2,true);
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
										Utils.BreakBlockLater(bloc.getBlock(), 1 * 9,true);
										//bloc.getBlock().setType(BREAK_TYPE);
										if (CoordX == false){
											int i = 0;
											while (i < amp){
												blocmodp.setX(blocmodp.getX() + 1);    											
												//blocmodp.getBlock().setType(Material.SAND);
												Utils.BreakBlockLater(blocmodp.getBlock(), 1 * 9,true);
												blocmodn.setX(blocmodn.getX() - 1);    											
												//blocmodn.getBlock().setType(Material.SAND);
												Utils.BreakBlockLater(blocmodn.getBlock(), 1 * 9,true);
												i = i + 1;
											}        											
										}else{
											int i = 0;
											while (i < amp){
												blocmodp.setZ(blocmodp.getZ() + 1);    											
												//blocmodp.getBlock().setType(Material.SAND);
												Utils.BreakBlockLater(blocmodp.getBlock(), 1 * 9,true);
												blocmodn.setZ(blocmodn.getZ() - 1);    											
												//blocmodn.getBlock().setType(Material.SAND);
												Utils.BreakBlockLater(blocmodn.getBlock(), 1 * 9,true);
												i = i + 1;
											}     
										}
									}
								}
								if (stack.getType() == Material.GOLD_HOE){
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
									int delay = 2 * pass;
									if(pass >= 16){
										delay += 20;
									}
									Utils.BreakBlockLater(bloc.getBlock(), delay,true);
									if (CoordX == false){
										int i = 0;
										while (i < amp){
											blocmodp.setX(blocmodp.getX() + 1);    											
											//blocmodp.getBlock().setType(Material.SAND);
											Utils.BreakBlockLater(blocmodp.getBlock(), delay,true);
											blocmodn.setX(blocmodn.getX() - 1);    											
											//blocmodn.getBlock().setType(Material.SAND);
											Utils.BreakBlockLater(blocmodn.getBlock(), delay,true);
											i = i + 1;
										}        											
									}else{
										int i = 0;
										while (i < amp){
											blocmodp.setZ(blocmodp.getZ() + 1);    											
											//blocmodp.getBlock().setType(Material.SAND);
											Utils.BreakBlockLater(blocmodp.getBlock(), delay,true);
											blocmodn.setZ(blocmodn.getZ() - 1);    											
											//blocmodn.getBlock().setType(Material.SAND);
											Utils.BreakBlockLater(blocmodn.getBlock(), delay,true);
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
									bloc.getBlock().setType(BREAK_TYPE);
									if (CoordX == false){
										int i = 0;
										while (i < amp){
											blocmodp.setX(blocmodp.getX() + 1);    											
											blocmodp.getBlock().setType(BREAK_TYPE);
											blocmodn.setX(blocmodn.getX() - 1);    											
											blocmodn.getBlock().setType(BREAK_TYPE);
											i = i + 1;
										}        											
									}else{
										int i = 0;
										while (i < amp){
											blocmodp.setZ(blocmodp.getZ() + 1);
											blocmodp.getBlock().setType(BREAK_TYPE);
											blocmodn.setZ(blocmodn.getZ() - 1);
											blocmodn.getBlock().setType(BREAK_TYPE);
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



}
