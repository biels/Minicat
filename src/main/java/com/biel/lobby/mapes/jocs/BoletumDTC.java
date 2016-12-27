package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.mapes.JocObjectius;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocObjectius.EquipObjectius;
import com.biel.lobby.mapes.JocObjectius.Objectiu;
import com.biel.lobby.mapes.JocObjectius.ObjectiuBlockBreak;
import com.biel.lobby.mapes.JocObjectius.ObjectiuBlockChange;
import com.biel.lobby.utilities.Utils;

public class BoletumDTC extends JocObjectius {

	@Override
	protected ArrayList<Objectiu> getDesiredObjectivesTeam(EquipObjectius e) {
		ArrayList<Objectiu> objectius = new ArrayList<Objectiu>();
		//Bukkit.broadcastMessage("Cores" + Integer.toString(e.getId() + 1));
		ArrayList<Location> coreLocs = pMapaActual().ObtenirLocations("Cores" + Integer.toString(e.getId() + 1), getWorld());
		objectius.add(new ObjectiuBlockBreak("Core " + e.getAdjectiu(), coreLocs.get(0)));
		return objectius;
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<Equip>();
		equips.add(new EquipObjectius(DyeColor.ORANGE, "taronja")); //Id 0
		equips.add(new EquipObjectius(DyeColor.GREEN, "verd")); //Id 1
		return equips;
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
	}
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		Equip e = obtenirEquip(ply);
		items.add(new ItemStack(Material.STONE_SWORD, 1));
		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		arc.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		arc.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		items.add(arc);
		items.add(new ItemStack(Material.DIAMOND_PICKAXE, 1));
		items.add(new ItemStack(Material.IRON_AXE));
		items.add(new ItemStack(Material.GLASS, 32));
		items.add(new ItemStack(Material.WOOD, 64));
		items.add(new ItemStack(Material.WOOD, 64));
		items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(new ItemStack(Material.IRON_BOOTS, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		items.add(new ItemStack(Material.LADDER, 8));
		items.add(new ItemStack(Material.GOLDEN_APPLE, 2));
		items.add(new ItemStack(Material.ARROW, 1));
		

		items.add(new ItemStack(Material.WEB, 1));
		return items;
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "BoletumDTC";
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockPlace(evt, blk);
		if (blk.getType() == Material.OBSIDIAN){
			evt.setCancelled(true);
			evt.getPlayer().damage(15);
		}
	}
}
