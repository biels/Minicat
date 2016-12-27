package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.biel.lobby.mapes.JocTeamDominion;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Utils;

public class Dominion extends JocTeamDominion {

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();
		setBlockBreakPlace(false);
		setGiveStartingItemsRespawn(true);
	}
	@Override
	protected GameGoalType getGameGoal() {
		// TODO Auto-generated method stub
		return GameGoalType.ScoreRace;
	}
	@Override
	protected ArrayList<ControlPoint> getDesiredControlPoints() {
		ArrayList<ControlPoint> controlPoints = new ArrayList<ControlPoint>();
		ArrayList<Location> pointLocations = pMapaActual().ObtenirLocations("Points", world);
		String[] pointNames = pMapaActual().ObtenirLlista("PointNames");
		String[] pointRadiuses = pMapaActual().ObtenirLlista("PointRadiuses");
		String[] captureRateMultipliers = pMapaActual().ObtenirLlista("CaptureRateMultipliers");
		double defaultRadius = 10D;
		for (Location l : pointLocations){
			String name; 
			int i = pointLocations.indexOf(l);
			try {
				name = pointNames[i];
			} catch (Exception e) {
				System.out.print("Falta especificar un nom de punt de control");
				name = "No_definit";
			}
			double radius;
			try {
				radius = Double.parseDouble(pointRadiuses[i]);
			} catch (Exception e) {
				radius = defaultRadius;
			}
			float captureRateMultiplier;
			try {
				captureRateMultiplier = Float.parseFloat(captureRateMultipliers[i]);
			} catch (Exception e) {
				captureRateMultiplier = 1F;
			}
			ConquersPVPControlPoint conquersPVPControlPoint = new ConquersPVPControlPoint(name, l.add(0.5, 0.5, 0.5), radius);
			conquersPVPControlPoint.setCaptureRateMultiplier(captureRateMultiplier);
			conquersPVPControlPoint.setBasePointReward(4F);
			controlPoints.add(conquersPVPControlPoint);
		}
		return controlPoints;
	}
	@Override
	public boolean giveSnowLauncherOnKill() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<Equip>();
		equips.add(new EquipDominion(DyeColor.RED, "vermell")); //Id 0
		equips.add(new EquipDominion(DyeColor.GREEN, "verd")); //Id 1
		return equips;
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
		items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(new ItemStack(Material.IRON_BOOTS, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		items.add(new ItemStack(Material.GOLDEN_APPLE, 2));
		items.add(new ItemStack(Material.ARROW, 1));
		return items;
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 10, 4, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 0, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 19, 1, true), true);
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Dominion";
	}
	public class ConquersPVPControlPoint extends RadialControlPoint{

		public ConquersPVPControlPoint(String name,
				Location center, Double radius) {
			super(name, new BubbleControlPointRenderer(), center, radius);
			// TODO Auto-generated constructor stub
		}
		
	}
}
