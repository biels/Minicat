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
			String name = s.getPlayer().getName();
			if(name.equals(ply.getName())){r.add(s);}
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
				System.out.println("--Clicat--");
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
						System.out.println("--Error reflexió / removing habilitat--");
						e.printStackTrace();
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
		dBlk.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(dBlk, ChatColor.AQUA + "Habilitat #" + n,  ChatColor.WHITE + "Obre l'inventari de selecció d'habilitats", Integer.toString(n)), p, event -> {
            int data = (int) event.getData();
            openSelectionMenu(event.getPlayer(), true, data);
        });
		button.setData(n);
		return button;
	}
	public void giveUnlockers(Player p, int amount){
		if(p == null)return;
		if(amount == 0)return;
		boolean lasSlotOccupied = p.getInventory().getItem(8) == null;
		int startingN = getSkillsForPlayer(p).size() + 1;
		for (int i = 0; i < amount; i++) {
			int j = 8 - i;
			if (j < 0) j = 35 - i;if (j < 0) j = 26 - (i + 18);if (j < 0) j = 17 - i;if (j < 0) break;
			if(p.getInventory().getItem(j) != null){i--; continue;}
			ItemButton unlockerButton = getUnlockerButton(p, startingN + i);
			p.getInventory().setItem(j, unlockerButton.getItemStack());
		}
	}
	public void giveRemainingUnlockers(Player p){
		
	}
}
