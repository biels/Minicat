package com.biel.lobby.mapes.jocs.disabled;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Utils;

public class TeamDeathMatchJaneatorForest extends JocTeamDeathMatch {

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 10 + (getPlayers().size() * 2);
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new EquipScoreRace(DyeColor.RED, "vermell")); //Id 0
		equips.add(new EquipScoreRace(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}

	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();
		world.setTime(4000);
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Equip e = obtenirEquip(ply);
		double balancingMultiplier = getBalancingMultiplier(ply);
		items.add(new ItemStack(Material.STONE_SWORD, 1));
		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		arc.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		//arc.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		items.add(arc);
		items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(new ItemStack(Material.IRON_BOOTS, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		items.add(new ItemStack(Material.GOLDEN_APPLE, 2));
		items.add(Utils.setItemNameAndLore(new ItemStack(Material.SNOWBALL, (int) Math.round(4 * (balancingMultiplier - 0.2))), "Bomba", "Explota a l'impacte"));
		items.add(new ItemStack(Material.ARROW, (int) (20 * balancingMultiplier)));
		return items;
	}
	@Override
	protected boolean canBeDropped(ItemStack i, Player p) {
		// TODO Auto-generated method stub
		Material t = i.getType();
		if(t == Material.GOLDEN_APPLE || t == Material.SNOWBALL)return true;
		return super.canBeDropped(i, p);
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "JaneatorForest";
	}
	@Override
	protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
		// TODO Auto-generated method stub
		super.onProjectileHit(evt, proj);
		if(proj instanceof Snowball){
			Location l = evt.getEntity().getLocation();
			ProjectileSource shooter = proj.getShooter();
			if(shooter instanceof Player){				
				Player p = (Player)shooter;
				world.createExplosion(l.getX(), l.getY(), l.getZ(), 2.6F * (float)getBalancingMultiplier(p), false, false);
			}
		}
	}
}
