package com.biel.FastSurvival.Dimensions.Sky;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.Utils.Utils;

public class SkyListener implements Listener{
	@EventHandler
    public void onClick(PlayerInteractEvent evt){
		
        final Player p = evt.getPlayer();
        Block b = evt.getClickedBlock();
        if (b == null){return;}
        Block r = b.getRelative(evt.getBlockFace());
        Action a = evt.getAction();
       
        if (SkyUtils.IsInSky(p)){
        	
        	if (a == Action.RIGHT_CLICK_BLOCK){
            	Material t = p.getItemInHand().getType();
    			if (t == Material.WATER_BUCKET){
    				r.setType(Material.SNOW_BLOCK);
    				evt.setCancelled(true);   			
            	}
    			if (t == Material.LAVA_BUCKET){
    				if (Utils.Possibilitat(30)){
    					r.setType(Material.DIRT);
    				}else{
    					r.setType(Material.OBSIDIAN);
    				}
    				
    				evt.setCancelled(true);
            	}
    			if (t == Material.FLINT_AND_STEEL){
    				evt.setCancelled(true);
    			}
    			//Exprosions --> Neu
//    			if (t == Material.TNT){
//    				evt.setCancelled(true);
//    			}
    			if (t == Material.GLOWSTONE){
    				evt.setCancelled(true);
    			}
            }
        }
		
		
	}
	@SuppressWarnings("deprecation")
	@EventHandler
    public void BlockFromToEvent(BlockPlaceEvent event){
        final Player p = event.getPlayer();
        Block b = event.getBlock();
        if (SkyUtils.IsInSky(p)){
        	 if (b.getType() == Material.TORCH){
             	event.setCancelled(true);
             	p.playEffect(b.getLocation(), Effect.SMOKE, 4);
             }
             if (b.getType() == Material.FIRE){
             	event.setCancelled(true);
             }
        }
       
		
	}
	@EventHandler
    public void onDmg(EntityDamageEvent evt){
        if (SkyUtils.IsInSky(evt.getEntity())){
        	if (evt.getCause() == DamageCause.FALL){
        		Double d = evt.getDamage();
        		d = d / 2;
        		d = d - 1;
        		if (d < 0){
        			d = 0.0;
        			evt.setCancelled(true);
        		}
        		evt.setDamage(d);
        	}
        }
	}
	@EventHandler
    public void onEntityExplod(EntityExplodeEvent  evt){
		if (!SkyUtils.IsInSky(evt.getEntity())){return;}
		Entity ent = evt.getEntity();
		if (ent instanceof Creeper){
			Creeper creep = (Creeper) ent;
			//int blocks = Utils.NombreEntre(5, 205);
			ArrayList<Location> sphereLocs = Utils.getSphereLocations(creep.getLocation(), 3.5D, false);
			
			for (Location l : sphereLocs){
				Block blk = l.getBlock();
				if (!blk.isEmpty()){continue;}
				FallingBlock fallingBlock = creep.getWorld().spawnFallingBlock(
						l.clone().add(new Vector(0, 3.5,0)), Material.SNOW_BLOCK, (byte) 0);
				fallingBlock.setDropItem(false);
				
				//fallingBlock.setVelocity(Utils.CrearVector(l, location).setY(0).add(vr));
				//fallingBlock.setVelocity(Vector.getRandom().subtract(Vector.getRandom()).add(new Vector(0,1,0)).multiply(0.5));
				//b.setType(Material.AIR);
				//blocks--;
			}
			evt.setYield(100);
			evt.setCancelled(true);
		}
	}
}

