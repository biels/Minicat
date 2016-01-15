package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;








import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;

public class ResourceRush extends JocTeamScoreRace {
	ArrayList<Item> thrownItems = new ArrayList<Item>();
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<Equip>();
		equips.add(new EquipScoreRace(DyeColor.RED, "vermell")); //Id 0
		equips.add(new EquipScoreRace(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}

	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "ResourceRush";
	}
	@Override
	public void JocIniciat() {
		// TODO Auto-generated method stub
		super.JocIniciat();
		fillResourceSources();
		for(Player p : getPlayers()){
			getPlayerInfo(p).addStatusEffect(new WeightStatusEffect(p));
			getPlayerInfo(p).addStatusEffect(new ValueStatusEffect(p));
		}
	}

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 50 * getPlayers().size();
	}
	public double getMaterialValue(Material m){
		switch(m){
		case DIAMOND:
			return 30;
		case EMERALD:
			return 25;
		case GOLD_INGOT:
			return 20;
		case IRON_INGOT:
			return 15;
		case INK_SACK:
			return 15;
		case COAL:
			return 4;
		case LOG:
			return 1;
		default:
			return 0;

		}
	}
	public double getMaterialStartingAmount(Material m){
		switch(m){
		case DIAMOND:
			return 1;
		case EMERALD:
			return 1;
		case GOLD_INGOT:
			return 2;
		case IRON_INGOT:
			return 4;
		case INK_SACK:
			return 0;
		case COAL:
			return 4;
		case LOG:
			return 1;
		default:
			return 0;
		}
	}
	public ArrayList<ItemStack> getChestStartingContents(int m){
		ArrayList<ItemStack> r = new ArrayList<ItemStack>();
		r.add(new ItemStack(Material.LOG, 2 * m));
		r.add(new ItemStack(Material.COAL, 4 * m));
		r.add(new ItemStack(Material.COAL, 4 * m));
		r.add(new ItemStack(Material.IRON_INGOT, 4 * m));
		r.add(new ItemStack(Material.IRON_INGOT, 4 * m));
		r.add(new ItemStack(Material.GOLD_INGOT, 4 * m));
		r.add(new ItemStack(Material.EMERALD, 2 * m));
		r.add(new ItemStack(Material.DIAMOND, 1 * m));
		return r;
	}
	public ChatColor getMaterialColor(Material m){
		switch(m){
		case DIAMOND:
			return ChatColor.AQUA;
		case EMERALD:
			return ChatColor.DARK_GREEN;
		case GOLD_INGOT:
			return ChatColor.GOLD;
		case IRON_INGOT:
			return ChatColor.DARK_GRAY;
		case INK_SACK:
			return ChatColor.DARK_BLUE;
		case COAL:
			return ChatColor.BLACK;
		case LOG:
			return ChatColor.GRAY;
		default:
			return ChatColor.WHITE;

		}
	}

	@Override
	public void heartbeat() {
		// TODO Auto-generated method stub
		super.heartbeat();
//		for(Item e : world.getEntitiesByClass(Item.class)){
//			if(e.getTicksLived() > 20 * 5){
//				e.remove();
//				getWorld().createExplosion(e.getLocation(), 0F);
//				Utils.getNearbyPlayers(e, 8);
//			}
//		}
		
	}
	@Override
	protected void onPlayerDropItem(PlayerDropItemEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDropItem(evt, p);
		Item itemDrop = evt.getItemDrop();
//		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//			public void run() {
//
//			}
//		}, 1);
	}
	@Override
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerPickupItem(evt, p);
		double dmg = getMaterialValue(evt.getItem().getItemStack().getType()) / 2;
		evt.getPlayer().damage(dmg, evt.getItem());
		sendPlayerMessage(p, ChatColor.GRAY + "Agafar ítems del terra inflingeix un mal proporcional al seu valor");
	}
	public void fillResourceSources(){
		for(Location l : pMapaActual().ObtenirLocations("Recursos")){
			fillResourceSource(l);
		}
		sendGlobalMessage(ChatColor.GREEN + "S'han omplert les fonts de recursos, " + ChatColor.YELLOW + getTeamFilledChests() + ChatColor.GREEN + " cofres per equip!");
	}
	public void fillResourceSource(Location center){
		Cuboid c = Utils.getCuboidAround(center, 10);
		int i = 0;
		int max = getTeamFilledChests();
		for(Block b : c.getBlocks()){
			if (b.getType() == Material.CHEST){
				Utils.fillChestRandomly(b, getChestStartingContents(2));
				i++;
			}
			if(i > max)break;
		}
	}

	public int getTeamFilledChests() {
		return 2 + Math.round(getPlayers().size() / 3);
	}
	public void deliverPlayerContents(Player p){
		int n = 0;
		ItemStack[] contents = p.getInventory().getContents();
		for(ItemStack i : contents){
			if(i == null)continue;
			double m = getMaterialValue(i.getType());
			n += i.getAmount() * m;
		}
		EquipScoreRace e = (EquipScoreRace)obtenirEquip(p);
		sendGlobalMessage(e.getChatColor() + p.getName() + ChatColor.WHITE + " ha sumat " + ChatColor.BOLD + n + ChatColor.RESET + " al seu equip:");
		e.incrementScore(n);
	}

	public class WeightStatusEffect extends StatusEffect{

		public WeightStatusEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.SKILL_TRAY);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			String symbol = "+";
			if(getSpeedPercentage() < 0)symbol = "-";
			return "Pes: " + Math.round(getWeight() * 10) / 10  + "kg (" + symbol + getSpeedPercentage() + "% velocitat)";
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void tick() {
			// TODO Auto-generated method stub
			super.tick();
			updatePotionEffects();
		}
		public double getWeight(){
			int n = 0;
			ItemStack[] contents = getPlayer().getInventory().getContents();
			for(ItemStack i : contents){
				if(i == null)continue;
				double m = getUnitWeight(i.getType());
				n += i.getAmount() * m;
			}
			return n;
		}
		public double getUnitWeight(Material m){
			String n = m.name();
			if(n.contains("BLOCK"))return 5;
			if(m == Material.ANVIL)return 12;
			return 1;
		}
		public int getSpeedPercentage(){
			//Per cada 1kg -5%
			int base = 25;
			return (int) (base - Math.round(getWeight()) * 5);
		}
		public void updatePotionEffects(){
			int speedPercentage = getSpeedPercentage();
			int SP = 0;
			int SL = 0;
			if (speedPercentage > 0) {
				SP += Math.floor(speedPercentage / 20D);
				double rem = speedPercentage / 20D - SP;
				SP += rem * 4;
				SL += rem * 4;
			}else{
				speedPercentage *= -1;
				SL += Math.floor(speedPercentage / 15D);
				double rem = speedPercentage / 15D - SL;
				SL += Math.round(rem * 3 * 3);
				SP += Math.round(rem * 3 * 2);
			}
			if (SP > 0) {
				getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, SP - 1, true, true));
			}else{
				getPlayer().removePotionEffect(PotionEffectType.SPEED);
			}
			if (SL > 0) {
				getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, SL - 1, true, true));
			}else{
				getPlayer().removePotionEffect(PotionEffectType.SLOW);
			}
		}
		private int getModifier(int SP, int SL){
			return 20 * SP - 15 * SL;
		}
	}
	public class ValueStatusEffect extends StatusEffect{

		public ValueStatusEffect(Player ply) {
			super(ply);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Valor: " + getCarriedValue();
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}
		public int getCarriedValue(){
			int n = 0;
			ItemStack[] contents = getPlayer().getInventory().getContents();
			for(ItemStack i : contents){
				if(i == null)continue;
				double m = getMaterialValue(i.getType());
				n += i.getAmount() * m;
			}
			return n;
		}
	}
}
