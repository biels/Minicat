package com.biel.lobby.utilities.events.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.lobby.Com;
import com.biel.lobby.utilities.Utils;

public class SkillPool {
	ArrayList<Skill> registered = new ArrayList<>();
	ArrayList<Skill> skills = new ArrayList<>();
	public boolean addSkill(Skill s){
		if(!hasSkill(s.getPlayer(), s.getClass())){
			skills.add(s);
			return true;
		}
		return false;
	}
	public void removeSkill(Skill bus){
		if(skills.contains(bus)){
			skills.remove(bus);
		}
	}
	public void clear(){
		skills.clear();
	}
	public ArrayList<Skill> getSkillsForPlayer(Player ply){
		ArrayList<Skill> r = new ArrayList<>();
		for(Skill s : skills){
			if(ply.getName().equals(s.getPlayerName())){r.add(s);}
		}
		return r;
	}
	public boolean hasSkill(Player p, Class<? extends Skill> type){
		for(Skill s : getSkillsForPlayer(p)){
			String name = s.getClass().getName();
			String name2 = type.getName();
			if (name.equals(name2))return true;
		}
		return false;
	}
	public void tickPool(){ //Call from heartbeat
		for(Skill s : skills){
			if(s.isValid())s.tick();
		}
	}
	public void registerSkill(Skill s){
		if(s.getPlayer() != null){s.setPlayer(null);} //Fora el jugador, això és una llista genèrica!!
		if(registered.contains(s))return;
		registered.add(s);
	}
	//Get active skills and full skill list
	public void openSelectionMenu(Player p, final boolean clickable, final int n){
		String msg;
		if(clickable){msg = "Tria una habilitat";}else{msg = "Totes les habilitats";}
		IconMenu menu = new IconMenu(ChatColor.DARK_GREEN + msg, (int) (9 * (Math.ceil(skills.size() / 9) + 1 + 1)), new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				event.setWillClose(clickable);
				//select skill
				int pos = event.getPosition();
				Com.getPlugin().getLogger().fine("Skill selection clicked");
				if(clickable){
					Skill s = registered.get(pos);
					Skill reflectedS;
					try {
						reflectedS = s.getClass().getConstructor(Player.class).newInstance(event.getPlayer());
						boolean added = addSkill(reflectedS);
						if (added) {
							reflectedS.getGame().sendGlobalMessage(
									ChatColor.YELLOW
											+ event.getPlayer().getName()
											+ ChatColor.GRAY
											+ " ha desbloquejat l'habilitat "
											+ ChatColor.YELLOW
											+ reflectedS.getName());
							removeUnlocker(event.getPlayer(), n);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Com.getPlugin().getLogger().log(java.util.logging.Level.SEVERE, "Error creating or assigning a selected skill", e);
					} 
				}
			}
			public void removeUnlocker(Player p, int n){
				PlayerInventory i = p.getInventory();
				int removeid = -1;

				for(ItemStack s : i){					
					int unlockerID = getUnlockerID(s);					
					if (n == unlockerID){
						removeid = Arrays.asList(i.getContents()).indexOf(s);
					}
				}
				if(removeid != -1){
					//i.getContents()[removeid] = new ItemStack(Material.AIR);
					i.setItem(removeid, new ItemStack(Material.AIR));
				}
			}
		});
		for(Skill s : registered){
			if(hasSkill(p, s.getClass()))continue;
			menu.setOption(registered.indexOf(s), s.getItemStack(), s.getName(), s.getDescLines());
		}
		menu.open(p);
	}
	public int getUnlockerID(ItemStack s){
		if (s == null)return -1;
		if (s.hasItemMeta()){
			ItemMeta meta = s.getItemMeta();
			if(meta.hasLore()){
				List<String> lore = meta.getLore();
				if (lore.size() == 2){
					String snum = lore.get(1);
					int num = Integer.parseInt(snum);
					//System.out.println(num);
					return num;
				}
			}
		}
		return -1;
	}
	public ItemButton getUnlockerButton(Player p, int n){
		ItemStack dBlk = new ItemStack(Material.CHEST);
		dBlk.addUnsafeEnchantment(Enchantment.FORTUNE, 1);
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(dBlk, ChatColor.AQUA + "Habilitat #" + n,  ChatColor.WHITE + "Obre l'inventari de selecció d'habilitats", Integer.toString(n)), p, event -> {
            int data = (int) event.getData();
            openSelectionMenu(event.getPlayer(), true, data);
        });
		button.setData(n);
		return button;
	}
	/**
	 * Tops the player up to {@code amount} unlocker buttons, counting the ones already
	 * carried, in the first free slots from the right of the hotbar and then from the
	 * end of the main inventory. Skipping occupied slots used to be done by stepping
	 * the loop counter back, which re-tried the same occupied slot forever: a player
	 * awarded a skill mid-life with anything in slot 8 froze the server.
	 */
	public void giveUnlockers(Player p, int amount){
		if(p == null || amount <= 0)return;
		int carried = 0;
		for (ItemStack s : p.getInventory().getContents()) {
			if (isUnlocker(s)) carried++;
		}
		int missing = amount - carried;
		int startingN = getSkillsForPlayer(p).size() + carried + 1;
		int placed = 0;
		for (int order = 0; order < 36 && placed < missing; order++) {
			// Hotbar slots 8..0 first, then 35..9.
			int candidate = order < 9 ? 8 - order : 35 - (order - 9);
			if (p.getInventory().getItem(candidate) != null) continue;
			p.getInventory().setItem(candidate, getUnlockerButton(p, startingN + placed).getItemStack());
			placed++;
		}
	}
	private boolean isUnlocker(ItemStack s){
		try {
			return s != null && s.getType() == Material.CHEST && getUnlockerID(s) != -1;
		} catch (NumberFormatException notANumberedLore) {
			return false;
		}
	}
	public void giveRemainingUnlockers(Player p){
		
	}
}
