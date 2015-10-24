package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Utils;


public class RedstoneWars extends JocEquips{
	int chargeCount = 0;
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<Equip>();
		equips.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		equips.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		Equip e = obtenirEquip(ply);
		items.add(new ItemStack(Material.IRON_SWORD, 1));
		items.add(new ItemStack(Material.IRON_PICKAXE, 1));
		//		items.add(new ItemStack(Material.DIAMOND_PICKAXE, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		arc.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
		arc.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		arc.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
		items.add(arc);
		items.add(new ItemStack(Material.ARROW, 1));
		//items.add(new Potion(PotionType.INSTANT_DAMAGE, 1).toItemStack(2));

		return items;
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		Block b = killed.getLocation().getBlock();
		if(b.getType() == Material.AIR)b.setType(Material.REDSTONE_ORE);
		GUtils.getLocationsCircle(killed.getEyeLocation(), 1.1, 72).forEach(l -> {
			FallingBlock tnt = world.spawnFallingBlock(l, Material.REDSTONE_ORE, (byte) 0x0);
			tnt.setVelocity(Utils.CrearVector(killer.getLocation(), l).normalize().add(new Vector(0, 0.35, 0).multiply(0.7)));
			tnt.setDropItem(false);
		});
		
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
			boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return (int) (1 + Math.floor(segonsTranscorreguts() / 200D));
	}
	@Override
	public void heartbeat() {
		// TODO Auto-generated method stub
		super.heartbeat();
		getPlayers().forEach(p -> compactRedstone(p));
		if(JocIniciat && segonsTranscorreguts() % 80 == 0)placeBonusOnTheMiddle();
		if(JocIniciat && segonsTranscorreguts() % 200 == 0)sendGlobalMessage(ChatColor.GREEN + "Nova habilitat disponible!");
	}
	@Override
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerPickupItem(evt, p);
		Item item = evt.getItem();
		if(item.getItemStack().getType() == Material.REDSTONE){
			p.playSound(p.getEyeLocation(), Sound.ORB_PICKUP, 1, 1.2F);
			compactRedstone(p);
		}
	}
	@Override
	protected void onInventoryClick(InventoryClickEvent evt, Inventory inv) {
		// TODO Auto-generated method stub
		super.onInventoryClick(evt, inv);
		compactRedstone((Player) evt.getWhoClicked());
	}
	public void placeBonusOnTheMiddle(){
		Location halfwayMiddle = getHalfwayMiddle();
		int y = getWorld().getHighestBlockYAt(halfwayMiddle);
		halfwayMiddle.setY(y + 40);
		FallingBlock tnt = world.spawnFallingBlock(halfwayMiddle, Material.GOLD_BLOCK, (byte) 0x0);
	}
	@Override
	public Boolean getBlockBreakPlace() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockBreak(evt, blk);
		if(blk.getType() == Material.GLOWING_REDSTONE_ORE)evt.setCancelled(false);
		if (blk.getType() == Material.GOLD_BLOCK) {
			GUtils.getLocationsCircle(blk.getLocation(), 1.1, 32).forEach(l -> {
				FallingBlock tnt = world.spawnFallingBlock(l, Material.REDSTONE_ORE, (byte) 0x0);
				tnt.setVelocity(
						Utils.CrearVector(blk.getLocation(), l).normalize().add(new Vector(0, 0.45, 0).multiply(0.7)));
				tnt.setDropItem(false);
			});
			blk.setType(Material.AIR);
			getWorld().playSound(blk.getLocation(), Sound.ZOMBIE_REMEDY, 2, 1.15F);
		}
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockPlace(evt, blk);
		if(blk.getType() == Material.REDSTONE_BLOCK){
			evt.setCancelled(!Utils.getFacesVert().stream().allMatch(f -> GUtils.isValidSolidBlock(blk.getRelative(f))));
		}
	}
	void compactRedstone(Player p){
		PlayerInventory inv = p.getInventory();
		if(inv.contains(Material.REDSTONE, 9)){
			inv.removeItem(new ItemStack(Material.REDSTONE, 9));
			inv.addItem(new ItemStack(Material.REDSTONE_BLOCK, 1));
			p.playSound(p.getEyeLocation(), Sound.CLICK, 1, 1.1F);
			p.playSound(p.getEyeLocation(), Sound.WOOD_CLICK, 1, 1.1F);
		}
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "RedstoneWars";
	}
	@Override
	protected void onExplosionPrime(ExplosionPrimeEvent evt) {
		// TODO Auto-generated method stub
		super.onExplosionPrime(evt);
		//evt.setRadius(15);
		Location l = evt.getEntity().getLocation();
		Equip winner = Equips.stream().reduce(Equips.get(0), 
				(Equip e1, Equip e2) -> (e1.getTeamSpawnLocation().distance(l) > e2.getTeamSpawnLocation().distance(l) ? e1 : e2));
		winGame(winner);
	}
	@Override
	protected void onEntityExplode(EntityExplodeEvent evt, Entity e) {
		// TODO Auto-generated method stub
		super.onEntityExplode(evt, e);
		evt.setYield((float) (evt.getYield() * 1.2));
	}
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}
	
}
