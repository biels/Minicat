package com.biel.FastSurvival.Dimensions.Moon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;

public class TeleporterListener implements Listener {
	@EventHandler
    public void onClick(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		Block b = event.getClickedBlock();
		if (b == null){return;}
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK){return;}
		if(b.getType() == Material.STONE_BUTTON){
			ArrayList<Player> pls = Utils.getNearbyPlayers(p, 32);
			pls.add(p);
			double energy = detectMoonPortal(b.getLocation());
			double Maxenergy = 730 + pls.size() * 200;
			double ratio = energy / Maxenergy;
			//if(energy != 0){
				//Teleport
			for (Player pl : pls){
				String pName = "";
				if(!p.getName().equalsIgnoreCase(pl.getName())){
					pName = "("+ ChatColor.YELLOW + p.getName() + ChatColor.WHITE + ")";
				}
				pl.sendMessage(Utils.L("D_MOON_TELEPORTER_ENERGY") +": (" + energy + "/"+ Maxenergy +  ") " + Integer.toString((int)Math.round(ratio * 100)) + "%" + pName);
			}
			if(ratio >= 1){

				if (MoonUtils.IsInMoon(p)){
					Bukkit.broadcastMessage(Utils.L("D_TOEARTH"));
					MoonUtils.portalActivateToEarth(pls, b);
				}else{
					Bukkit.broadcastMessage(Utils.L("D_TOMOON"));
					MoonUtils.portalActivateToMoon(pls, b);
				}

			}
			//}
		}
	}
	public ArrayList<Player> detectMoonPortalPlayers(Location l){
		ArrayList<Player> pls = new ArrayList<Player>();
		
		return pls;
	}
	public double detectMoonPortal(Location l){
		List<Block> blocks = MoonUtils.detectMoonPortalBlocks(l);
		int wButtons = 0;
		int iBlocks = 0;
		int iBars = 0;
		int dBlocks = 0;
		int gBlocks = 0;
		int glowBlocks = 0;
		int iPlate = 0;
		int nFence = 0;
		int oFurnance = 0;
		int tTorch = 0;
		int tTorchOff = 0;
		int gBlock = 0;
		int rBlock = 0;
		int cBlock = 0;
		for(Block b : blocks){
			Material t = b.getType();
			if(t == Material.WOOD_BUTTON){wButtons++;}
			if(t == Material.IRON_BLOCK){iBlocks++;}
			if(t == Material.IRON_FENCE){iBars++;}
			if(t == Material.DIAMOND_BLOCK){dBlocks++;}
			if(t == Material.GLASS){gBlocks++;}
			if(t == Material.REDSTONE_LAMP_ON){glowBlocks++;}
			if(t == Material.IRON_PLATE){iPlate++;}
			if(t == Material.NETHER_FENCE){nFence++;}
			if(t == Material.FURNACE){oFurnance++;}
			if(t == Material.REDSTONE_TORCH_ON){tTorch++;}
			if(t == Material.REDSTONE_TORCH_OFF){tTorchOff++;}
			if(t == Material.REDSTONE_BLOCK){rBlock++;}
			if(t == Material.COAL_BLOCK){cBlock++;}
			if(t == Material.GOLD_BLOCK){cBlock++;}
		}
		double e = 0;
		
		if(iBlocks < 8 || iBars < 4 || wButtons < 2){return 0;}
		e = e + iBlocks * 38;
		//e = e + gBlocks * (5 + oFurnance);
		if (dBlocks >= 1){e = e + 75;}
		if (dBlocks >= 2){e = e + 18;}
		e = e + dBlocks * 150;
		e = e + rBlock * 8; //30% loss
		e = e + cBlock * 12; //70% loss
		if (cBlock != 0){e = e + 45;};
		e = e + tTorchOff * 1;
		if (wButtons != 0){e = e + (wButtons * 2 + 15) + 10;};
		if (iPlate != 0){e = e + (iPlate * 5 + 12);};
		if (iBars != 0){e = e + (iBars * 5);};
		if (glowBlocks == 1){e = e + 50;}
		if (nFence != 0){e = e + 5 * nFence - (tTorch * 2);}
		//if (oFurnance != 0){e = e + 25 + 2 * oFurnance;}
		if (nFence == 4){e = e + 18;}
		if (nFence == 1){e = e + 60;}
		return e;
	}
}

