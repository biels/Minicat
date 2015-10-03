package com.biel.FastSurvival.Dimensions.Moon;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import com.biel.FastSurvival.Utils.Utils;

public class MoonListener implements Listener{
	@EventHandler
    public void onClick(PlayerInteractEvent evt){
		
        final Player p = evt.getPlayer();
        Block b = evt.getClickedBlock();
        if (b == null){return;}
        Block r = b.getRelative(evt.getBlockFace());
        Action a = evt.getAction();
        if(p.getItemInHand() == MoonUtils.getSpaceGlass()){
        	p.getEquipment().setHelmet(MoonUtils.getSpaceGlass());
        	evt.setCancelled(true);
        	return;
        }
        if (MoonUtils.IsInMoon(p)){
        	
        	if (a == Action.RIGHT_CLICK_BLOCK){
            	Material t = p.getItemInHand().getType();
    			if (t == Material.WATER_BUCKET){
    				r.setType(Material.SNOW_BLOCK);
    				evt.setCancelled(true);   			
            	}
    			if (t == Material.LAVA_BUCKET){
    				if (Utils.Possibilitat(30)){
    					r.setType(Material.STONE);
    				}else{
    					r.setType(Material.OBSIDIAN);
    				}
    				
    				evt.setCancelled(true);
            	}
    			if (t == Material.FLINT_AND_STEEL){
    				evt.setCancelled(true);
    			}
    			if (t == Material.TNT){
    				evt.setCancelled(true);
    			}
    			if (t == Material.GLOWSTONE){
    				evt.setCancelled(true);
    			}
    			///EFFECT
    			if (evt.isCancelled()){
    				evt.getPlayer().getWorld().playEffect(evt.getClickedBlock().getLocation(), Effect.SMOKE, 4);
    			}
    			
            }
        }
		
		
	}
	@EventHandler
    public void BlockFromToEvent(BlockPlaceEvent event){
        final Player p = event.getPlayer();
        Block b = event.getBlock();
        if (MoonUtils.IsInMoon(p)){
        	 if (b.getType() == Material.TORCH){
             	event.setCancelled(true);
             }
             if (b.getType() == Material.FIRE){
             	event.setCancelled(true);
             }
        }
       
		if(p.getItemInHand() == MoonUtils.getSpaceGlass()){
        	p.getEquipment().setHelmet(MoonUtils.getSpaceGlass());
        }
	}
	@EventHandler
    public void onDmg(EntityDamageEvent evt){
        if (MoonUtils.IsInMoon(evt.getEntity())){
        	if (evt.getCause() == DamageCause.FALL){
        		Double d = evt.getDamage();
        		d = d / 6;
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
    public void onDecay(LeavesDecayEvent evt){
        if (MoonUtils.IsMoon(evt.getBlock().getWorld())){
        	evt.setCancelled(true);
        }
	}
	@EventHandler
	public void onBlockBrak(BlockBreakEvent evt) {	
		Block b = evt.getBlock();
		World w = b.getWorld();
		if (MoonUtils.IsMoon(w)){
			//--
			if(b.getType() == Material.LEAVES && b.getLocation().getBlockY() > 80){
				evt.setCancelled(true);
			}
			
		
		}
	}
		
}
