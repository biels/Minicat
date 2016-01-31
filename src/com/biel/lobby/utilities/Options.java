package com.biel.lobby.utilities;

import java.text.MessageFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

import com.biel.BielAPI.Utils.ItemButton;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.PlayerData;

public class Options {

	public Options() {
		// TODO Auto-generated constructor stub
	}

	public static void giveStartingButtons(final Player ply){
		PlayerData playerData = new PlayerData(ply);
		ItemButton.clearButtons(ply);
		PlayerInventory inventory = ply.getInventory();
		inventory.clear();
		ItemStack dBlk = new ItemStack(Material.DIAMOND_BLOCK);
		dBlk.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(dBlk, ChatColor.AQUA + "Teletransportador",  ChatColor.WHITE + "Obre l'inventari de teletransport."), ply, new ItemButton.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemButton.OptionClickEvent event) {
				lobby.getPlugin().gest.ObrirMenuMapes(Bukkit.getPlayer(ply.getName()));

			}
		});
		inventory.setItem(1, button.getItemStack());
		ItemButton button3 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.GOLD_INGOT), ChatColor.GOLD + "Ranking " + ChatColor.YELLOW + "(" + "#" + playerData.getRank() + ")",  ChatColor.WHITE + "Mostra el rànquing de jugadors.", ChatColor.DARK_AQUA + "Elo: " + ChatColor.WHITE + Math.round(playerData.getElo()) + ChatColor.YELLOW + " #" + playerData.getRank()), ply, new ItemButton.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemButton.OptionClickEvent event) {
				Com.displayRanking(event.getPlayer());				
			}
		});
		inventory.setItem(3, button3.getItemStack());
		ItemButton button4 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BLAZE_POWDER), ChatColor.BLUE + "Instàncies [BETA]", ""), ply, new ItemButton.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemButton.OptionClickEvent event) {
				Com.getGest().openAllGamesMenu(event.getPlayer());
			}
		});
		inventory.setItem(5, button4.getItemStack());
//		ItemButton button5 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.SAPLING), ChatColor.RED + "Fast" + ChatColor.GREEN + "Survival", "Servidor sense normes, mode de joc FastSurvival", "(Cercar FastSurvival al google)"), ply, new ItemButton.OptionClickEventHandler() {
//			@Override
//			public void onOptionClick(ItemButton.OptionClickEvent event) {
//				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format("sync console bungee send {0} FastSurvival", event.getPlayer().getName()));			
//			}
//		});
//		inventory.setItem(6, button5.getItemStack());
		if (ply.isOp()){
			ItemButton button2 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.LEVER), ChatColor.RED + "Reiniciar",  ChatColor.WHITE + "Reinicia el servidor.", ChatColor.WHITE + "Cal esperar uns 10-15s per tornar a entrar."), ply, new ItemButton.OptionClickEventHandler() {
				@Override
				public void onOptionClick(ItemButton.OptionClickEvent event) {
					for(Player p : Bukkit.getOnlinePlayers())p.kickPlayer(ChatColor.GREEN + "" + ChatColor.BOLD + "REINICIANT...");
					Bukkit.getServer().shutdown();
				}
			});
			inventory.setItem(4, button2.getItemStack());
		}

		ItemStack itembook = new ItemStack(Material.WRITTEN_BOOK); 
		BookMeta book = (BookMeta) itembook.getItemMeta();
		book.setAuthor("Minicat");
		book.setTitle(ChatColor.YELLOW + "Informació");
		book.addPage("Fes /o per obtenir el menú d'opcions, /l per tornar al lobby en qualsevol moment i /m per accedir a l'inventari del teletransportador. Servidor fibra òptica.");
		//book.addPage("<Normes256.txt> craftbookEventHandler-Result;Line:142");
		itembook.setItemMeta(book);
		ply.getInventory().setItem(8, itembook);
		//ply.updateInventory();
	}
	public static void giveCommonOptionsMenu(final Player ply){
		ItemButton.clearButtons(ply);
		PlayerInventory inventory = ply.getInventory();
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BOOK), ChatColor.BLUE + "Opcions",  ChatColor.WHITE + "Opcions generals."), ply, new ItemButton.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemButton.OptionClickEvent event) {
				Com.teleportPlayerToLobby(ply);

			}
		});
		inventory.setItem(8, button.getItemStack());
	}

}
