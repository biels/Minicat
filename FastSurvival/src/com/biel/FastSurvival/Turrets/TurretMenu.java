package com.biel.FastSurvival.Turrets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Turrets.TurretLogic.AttackGroups;
import com.biel.FastSurvival.Utils.IconMenu;

public class TurretMenu {
	public static void displayMenu(Player p, final int iId){
		Turret t = new Turret(iId);
		final String name = p.getName();
		Material switchM = Material.LEVER;
		Material mPlayers = Material.EYE_OF_ENDER;
		String switchS = "off";
		String plS = "on";
		if (t.d.getEnabled()){switchM = Material.REDSTONE_TORCH_ON; switchS = "on";}
		if (t.d.getNoPlayers()){mPlayers = Material.ENDER_PEARL; plS = "off";}

		IconMenu menu = new IconMenu("Turret menu", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				Turret t = new Turret(iId);
				Player player = Bukkit.getPlayer(name);
				event.setWillClose(false);
				//event.getPlayer().sendMessage("You have chosen " + event.getName());
				if (event.getPosition() == 0){
					//event.ge
				}
				if (event.getPosition() == 3){
					//displayTargetingModesMenu(player, iId);
					boolean value = !t.d.getNoPlayers();
					if (!value){
						Bukkit.broadcastMessage("Player targeting enabled");
					}else{
						Bukkit.broadcastMessage("Player targeting disabled");
					}
					t.d.setNoPlayers(value);
					event.setWillClose(true);
				}
				if (event.getPosition() == 5){
					displayFriendlyPlayerMenu(player, iId);
				}
				if (event.getPosition() == 6){
					displayFriendlyPlayerAddMenu(player, iId);
				}
				if (event.getPosition() == 8){
					t.setEnabled(!t.d.getEnabled());
					event.setWillClose(true);
				}

				event.setWillDestroy(true);
			}
		}, FastSurvival.getPlugin())
		.setOption(0, new ItemStack(Material.PAPER, 0), ChatColor.BLUE + "say Information", ChatColor.GREEN + "Hp: " + Integer.toString(t.d.getHealth()) + "/" + Integer.toString(t.getMaxHp()), "Regen: +1 every " + Integer.toString((int) (t.getRegenTicks() / 2.0)) + "s" , "Tier: " + Integer.toString(t.d.getTier()))
		.setOption(3, new ItemStack(mPlayers, 0), "Attack players " + plS, "Set if the turret should attack any player")
		.setOption(5, new ItemStack(Material.BOOK, 0), "Friendly players", "List and remove friendly players")
		.setOption(6, new ItemStack(Material.EMERALD, 0), "Add friendly player", "Add new friendly player")
		.setOption(8, new ItemStack(switchM, 0), "Powered " + switchS, "Click to switch the power mode of the turret");

		menu.open(p);
	}
	public static void displayTargetingModesMenu(Player p, final int iId){
		Turret t = new Turret(iId);
		Material switchM = Material.LEVER;
		String switchS = "off";
		IconMenu menu = new IconMenu("Targeting modes [DISABLED]", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				Turret t = new Turret(iId);
				event.getPlayer().sendMessage("Desactivat de moment. Aquesta acció no és possible.");
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, FastSurvival.getPlugin());
		int i = 0;
		for(AttackGroups g : AttackGroups.values()){
			menu.setOption(i, new ItemStack(Material.STONE_SWORD, 1), g.name(), "Targeting group");
			i++;
		}
		menu.open(p);
	}
	@SuppressWarnings("deprecation")
	public static void displayFriendlyPlayerMenu(Player p, final int iId){
		Turret t = new Turret(iId);

		IconMenu menu = new IconMenu("Friendly player list", 9 * 2, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				Turret t = new Turret(iId);
				if (t.getCanBeModified()){
					if (t.d.getFriendlyPlayers().contains(event.getName())){
						t.removeFriendlyPlayer(event.getName());
						t.registerModification();
						event.getPlayer().sendMessage("You've removed " + event.getName() + " from the friendly list.");
					}	
				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, FastSurvival.getPlugin());
		int i = 0;
		for(String s : t.d.getFriendlyPlayers()){
			Wool w = new Wool(DyeColor.LIME);
			menu.setOption(i, new ItemStack(Material.WOOL, 1, (short) 0, w.getData()), s, "Click to remove");
			i++;
		}
		menu.open(p);
	}
	@SuppressWarnings("deprecation")
	public static void displayFriendlyPlayerAddMenu(Player p, final int iId){
		Turret t = new Turret(iId);

		IconMenu menu = new IconMenu("Friendly player list", 9 * 2, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				Turret t = new Turret(iId);
				if (t.getCanBeModified()){
					if (!t.d.getFriendlyPlayers().contains(event.getName())){
						t.addFriendlyPlayer(event.getName());
						t.registerModification();
						event.getPlayer().sendMessage("You've added " + event.getName() + " to the friendly list.");
					}
				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, FastSurvival.getPlugin());
		int i = 0;
		for(String s : t.getNearbyNonFriendlySuggestions()){
			Wool w = new Wool(DyeColor.YELLOW);
			menu.setOption(i, new ItemStack(Material.WOOL, 1, (short) 0, w.getData()), s, "Click to add");
			i++;
		}
		menu.open(p);
	}
}
