package com.biel.lobby;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.biel.lobby.utilities.CBUtils;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.Options;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.PlayerData;
import com.connorlinfoot.titleapi.TitleAPI;

public class Com {
 //HIO
	static char NBSP = '\u00A0';
	static public lobby getPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("lobby");
	
		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof lobby)) {
			return null; // Maybe you want throw an exception instead
		}
	
		return (lobby) plugin;
	}

	static public World getLobbyWorld(){
		return Bukkit.getWorlds().get(0);
	}
	static public GestorMapes getGest(){
		return getPlugin().gest;
	}
	static public DataAPI getDataAPI(){
		return getPlugin().dataAPI;
	}
	public static void teleportPlayerToLobby(Player p){
		Mapa m = getGest().getMapWherePlayerIs(p);
		if(m!=null){
			m.Leave(p);
		}
		com.biel.lobby.utilities.Utils.clearPlayer(p);
		p.teleport(getLobbyWorld().getSpawnLocation());
		p.setDisplayName(p.getName());
		if (p.getName().equals("bielCAT")){
			p.setDisplayName(ChatColor.RED + "[Admin]" + ChatColor.GREEN +  p.getName() + ChatColor.WHITE);
		}
		ScoreBoardUpdater.clearScoreBoard(p);
		Options.giveStartingButtons(p);
		p.getInventory().setHeldItemSlot(1);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), () -> {
            setHeadColor(p, ChatColor.WHITE);
            int rank = new PlayerData(p).getRank();
            Utils.donarItemsPlayer(p, getRankEquipment(rank));
            setSuffix(p, ColorConverter.chatToRaw(ChatColor.GRAY) + NBSP + ColorConverter.chatToRaw(ChatColor.YELLOW) + "#" + rank);
        }, 2);
		playMinicatAnimation(p);
	}
	public static Boolean isOnLobby(Player ply){
		return getLobbyWorld().getPlayers().contains(ply);
	}
	public static void sendLobbyMessage(String msg){
		for(Player p : Bukkit.getOnlinePlayers())if(isOnLobby(p))p.sendMessage(msg);
	}
	public static void setHeadColor(Player ply, ChatColor color){
		setHeadColor(ply, ColorConverter.chatToRaw(color));
	}
	public static void setHeadColor(Player ply, String color){
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ne prefix " + ply.getName() + " " + color);
	}
	public static void setSuffix(Player ply, String suffix){
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ne suffix " + ply.getName() + " " + suffix);
	}
	public static void displayRanking(Player p){
		if(Com.getDataAPI().isInDatalessMode()){
			p.sendMessage("El rànquing no es pot visualitzar en mode sense dades");
			return;
		}
		ArrayList<Integer> pIDs = getDataAPI().getRanking();
		p.sendMessage("-----Rànquing global-----");
		int max = 10;
		for (Integer id : pIDs){
			PlayerData data = new PlayerData(id);
			int index = pIDs.indexOf(id) + 1;
			String msg = "";
			String c = "";
			c = "" + ChatColor.BLUE;
			if (index <= 10){c = "" + ChatColor.YELLOW;}
			if (index <= 3){c = "" + ChatColor.GREEN;}
			if (index <= 1){c = "" + ChatColor.AQUA;}
			if (index == pIDs.size()){c = "" + ChatColor.RED;}
			msg += ChatColor.GREEN;
			msg += Integer.toString(index);
			msg += ChatColor.RED;
			msg += " - ";
			msg += c;
			msg += data.getUserName();
			msg += ChatColor.DARK_AQUA + " [" + Math.round(data.getElo()) + "]";
			p.sendMessage(msg);
			max--;
			if(max <= 0)break;
		}
		
	}
	public static String getRankingString(int num){
		if(Com.getDataAPI().isInDatalessMode()){
			return "[Not Avaliable]";
		}
		ArrayList<String> positions = new ArrayList<>();
		ArrayList<Integer> pIDs = getDataAPI().getRanking();
		int max = num;
		//positions.add(ChatColor.BOLD + "");
		for (Integer id : pIDs){
			PlayerData data = new PlayerData(id);
			int index = pIDs.indexOf(id) + 1;
			String msg = "";
			String c = "";
			c = "" + ChatColor.BLUE;
			if (index <= 10){c = "" + ChatColor.YELLOW;}
			if (index <= 3){c = "" + ChatColor.GREEN;}
			if (index <= 1){c = "" + ChatColor.AQUA;}
			if (index == pIDs.size()){c = "" + ChatColor.RED;}
			msg += ChatColor.DARK_RED;
			msg += Integer.toString(index);
			msg += " - ";
			msg += c;
			msg += data.getUserName();
			//msg += ChatColor.DARK_AQUA + " [" + Math.round(data.getElo()) + "]";
			positions.add(msg);
			max--;
			if(max <= 0)break;
		}
		return String.join(", ", positions);
	}
	public static ArrayList<ItemStack> getRankEquipment(int r){
		ArrayList<ItemStack> l = new ArrayList<>();
		if(r == 1){
			l.add(new ItemStack(Material.DIAMOND_HELMET));
			l.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			l.add(new ItemStack(Material.DIAMOND_LEGGINGS));
			l.add(new ItemStack(Material.DIAMOND_BOOTS));
			return l;
		}
		if(r == 2){
			l.add(new ItemStack(Material.GOLD_HELMET));
			l.add(new ItemStack(Material.GOLD_CHESTPLATE));
			l.add(new ItemStack(Material.GOLD_LEGGINGS));
			l.add(new ItemStack(Material.GOLD_BOOTS));
			return l;
		}
		if(r == 3){
			l.add(new ItemStack(Material.GOLD_HELMET));
			l.add(new ItemStack(Material.IRON_CHESTPLATE));
			l.add(new ItemStack(Material.IRON_LEGGINGS));
			l.add(new ItemStack(Material.IRON_BOOTS));
			return l;
		}
		if(r == 4){
			l.add(new ItemStack(Material.IRON_HELMET));
			l.add(new ItemStack(Material.IRON_CHESTPLATE));
			l.add(new ItemStack(Material.IRON_LEGGINGS));
			l.add(new ItemStack(Material.IRON_BOOTS));
			return l;
		}
		if(r == 5){
			l.add(new ItemStack(Material.IRON_HELMET));
			l.add(new ItemStack(Material.LEATHER_CHESTPLATE));
			l.add(new ItemStack(Material.LEATHER_LEGGINGS));
			l.add(new ItemStack(Material.LEATHER_BOOTS));
			return l;
		}
		if(r <= 10){
			l.add(new ItemStack(Material.LEATHER_CHESTPLATE));
			l.add(new ItemStack(Material.LEATHER_CHESTPLATE));
			l.add(new ItemStack(Material.LEATHER_LEGGINGS));
			l.add(new ItemStack(Material.LEATHER_BOOTS));
			return l;
		}
		return l;
	}
	public static String getMinicatString(){
		return ChatColor.YELLOW + "MINICAT";
	}
	public static void playMinicatAnimation(Player p){
		ArrayList<String> f = new ArrayList<>();
		String bold = "";
		f.add(ChatColor.WHITE + "" + bold + "MINICAT");
		f.add(ChatColor.YELLOW + "" + bold + "M" + ChatColor.WHITE + "" + bold + "INICAT");
		f.add(ChatColor.GOLD + "" + bold + "M" + ChatColor.YELLOW + "" + bold + "I" + ChatColor.WHITE + "" + bold + "NICAT");
		f.add(ChatColor.YELLOW + "" + bold + "M" + ChatColor.GOLD + "" + bold + "I" + ChatColor.YELLOW + "" + bold + "N" + ChatColor.WHITE + "" + bold + "ICAT");
		f.add(ChatColor.WHITE + "" + bold + "M" + ChatColor.YELLOW + "" + bold + "I" + ChatColor.GOLD + "" + bold + "N" + ChatColor.YELLOW + "" + bold + "I" + ChatColor.WHITE + "" + bold + "CAT");
		f.add(ChatColor.WHITE + "" + bold + "MI" + ChatColor.YELLOW + "" + bold + "N" + ChatColor.GOLD + "" + bold + "I" + ChatColor.YELLOW + "" + bold + "C" + ChatColor.WHITE + "" + bold + "AT");
		f.add(ChatColor.WHITE + "" + bold + "MIN" + ChatColor.YELLOW + "" + bold + "I" + ChatColor.GOLD + "" + bold + "C" + ChatColor.YELLOW + "" + bold + "A" + ChatColor.WHITE + "" + bold + "T");
		f.add(ChatColor.WHITE + "" + bold + "MINI" + ChatColor.YELLOW + "" + bold + "C" + ChatColor.GOLD + "" + bold + "A" + ChatColor.YELLOW + "" + bold + "T");
		f.add(ChatColor.WHITE + "" + bold + "MINIC" + ChatColor.YELLOW + "" + bold + "A" + ChatColor.GOLD + "" + bold + "T");
		f.add(ChatColor.WHITE + "" + bold + "MINICA" + ChatColor.YELLOW + "" + bold + "T");
		f.add(ChatColor.WHITE + "" + bold + "MINICAT");
		int i = 0;
		for (String s : f){
			Bukkit.getScheduler().runTaskLater(getPlugin(), () -> TitleAPI.sendTitle(p,0,6,0,s,Integer.toString(Bukkit.getOnlinePlayers().size())), 4 * i + 14 + (CBUtils.getPing(p) * 20 / 1000));
			i++;
		}
	}
	public static Material getSkullIconMaterial(Player p){
		Material m = Material.SKULL_ITEM;
		if(Stream.of("amiguet", "pilota", "ball", "").anyMatch(s -> (p.getName().equalsIgnoreCase(s) || p.getName().contains(s))))
			m = Material.SLIME_BALL;
		return m;
	}
}
