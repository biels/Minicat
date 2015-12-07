package com.biel.FastSurvival.SpecialItems;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.biel.FastSurvival.BuilderWand.BuilderWandUtils;
import com.biel.FastSurvival.Utils.NbtFactory;
import com.biel.FastSurvival.Utils.Utils;
import com.biel.FastSurvival.Utils.NbtFactory.NbtCompound;
import com.biel.FastSurvival.Utils.NbtFactory.NbtList;
import com.mysql.jdbc.Util;

public class SpecialItem implements Listener{
	public Material getMaterial(){
		return Material.DIAMOND;
	}
	public Short getDamageValue(){
		return 0;
	}
	public Byte getData(){
		return 0;
	}
	public String getName(){
		return "SpecialItemDefault";
	}
	public String getDescription(SpecialItemData d){
		return "[Description]";
	}
	public int getTier(){
		return -1;
	}
	public Boolean hasDataField(){
		return false; //default
	}
	public ChatColor getTierChatColor(){
		switch (getTier()) {
		case 1:
			return ChatColor.YELLOW;
		case 2:
			return ChatColor.AQUA;
		case 3:
			return ChatColor.DARK_PURPLE;
		default:
			return ChatColor.WHITE;
		}
	}
	private String[] getLoreArr(SpecialItemData d){
		ArrayList<String> arr = getDescLines(d);
		String valueLine = getValueLine(d);
		if (valueLine != null){
			arr.add(valueLine);
		}
		arr.add("D" + Integer.toString(d.iId));
		//To array
		String[] arrayResult = arr.toArray(new String[arr.size() - 1]);
		return arrayResult;
	}
	public String getValueName() {
		return null;
	}
	public String getValueLine(SpecialItemData d) {
		if(d.hasValue()){
			Double value = d.getValue();
			Double roundValue = (double)Math.round(value * 100) / 100;
			String s = ChatColor.BLUE + Double.toString(roundValue);
			if(d.hasMaxValue()){
				Double maxValue = d.getMaxValue();
				Double roundMaxValue = (double)Math.round(maxValue * 100) / 100;
				s = s + "/" + Double.toString(roundMaxValue);
				if (maxValue > 3){
					Double ratio = (double) (value / maxValue);
					ratio = ratio * 100;
					Double percent = (double)Math.round(ratio * 10) / 10;
					s = s + " - " + Double.toString(percent) + "%";
				}
			}
			return s;
		}
		return null;
	}
	public ArrayList<String> getDescLines(SpecialItemData d) {
		ArrayList<String> arr = new ArrayList<String>();
		String desc = getDescription(d);
		int i = 0;
		int lastBreak = 0;
		int charsForLine = getCharsForDescLine();
		int maxChar = desc.length() - 1;
		while (i <= maxChar){
			if (i == maxChar){
				String substring = desc.substring(lastBreak, i);
				arr.add(ChatColor.WHITE + substring.trim());
			}else{
				char c = desc.charAt(i);
				if (c == ' '){
					int increment = i - lastBreak;
					if (increment > charsForLine){
						//Arrange new line
						String substring = desc.substring(lastBreak, i);
						arr.add(ChatColor.WHITE + substring.trim());
						//Set vars
						lastBreak = i;
					}
				}
			}
			i++;
		}
		return arr;
	}
	public int getCharsForDescLine() {
		return 32;
	}
	public void initializeData(SpecialItemData d){
		d.setID(getClassID());
	}
	//public ItemStack 
	public ItemStack createNewItemStack(){
		int newiId = SpecialItemData.getNextiId();
		//Bukkit.broadcastMessage(Integer.toString(newiId));
		SpecialItemData data = getData(newiId);
		initializeData(data);
		return recreateItemStack(newiId);
	}
//	public ItemStack recreateItemStack(int iId){
//		SpecialItemData d = getData(iId);
//		ItemStack i = Utils.setItemNameAndLore(new ItemStack(getMaterial(), getDamageValue(), getData()), getTierChatColor() + getName(), getLoreArr(d));
//		i.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, getTier());
//		return i;//"[Line1]", "[Line2]", "Info{Current/Max}");
//	}
	@SuppressWarnings("deprecation")
	public ItemStack recreateItemStack(int iId){
		SpecialItemData d = getData(iId);
		ItemStack i = Utils.setItemNameAndLore(new ItemStack(getMaterial(), 1, getDamageValue(), getData()), getTierChatColor() + getName(), getLoreArr(d));
		//ItemStack i = new ItemStack(getMaterial());
		i.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, getTier());
		return i;//"[Line1]", "[Line2]", "Info{Current/Max}");
	}
	public int getClassID(){
		Bukkit.broadcastMessage("IDless special item! (" + getName()+ "). " + "Set it on the getClassID()");
		return -1;
	}
	public Boolean isItemStack(ItemStack i){
		if (hasiId(i)){
			SpecialItemData data = getData(i);
			if (data.hasID() == true){
				if (data.getID() == getClassID()){
					return true;
				}
			}else{
				//Bukkit.broadcastMessage("NoID");
			}
		}
		return false;
	}
	public int getItemSlot(Player p){
		PlayerInventory inv = p.getInventory();
		ItemStack[] contents = inv.getContents();
		int index = 0;
		for(ItemStack i : contents){
			if(isItemStack(i)){return index;}
			index++;
		}
		return -500;
	}
	public SpecialItemData getData(Player p){
		ItemStack i = getItemStack(p);
		return getData(i);
	}
	public ItemStack getItemStack(Player p) {
		ItemStack i = p.getInventory().getContents()[getItemSlot(p)];
		return i;
	}
	public Boolean hasItem(Player p){
		return getItemSlot(p) != -500;
	}
	public Boolean hasItemInHand(Player p){
		return isItemStack(p.getItemInHand());
	}
	@SuppressWarnings("deprecation")
	public void replaceItem(Player p, ItemStack newItem){
		p.getInventory().setItem(getItemSlot(p), newItem);
		p.updateInventory();
	}
	public void replaceItem(Player p, int iId){
		replaceItem(p, recreateItemStack(iId));
	}
	public void replaceItem(Player p, SpecialItemData d){
		replaceItem(p, d.iId);
	}
	@SuppressWarnings("deprecation")
	public void removeItem(Player p){
		if (hasItem(p)){
			p.getInventory().getContents()[getItemSlot(p)] = null;
		}
		p.updateInventory();
	}
	private int getiId(ItemStack i){
		if(i == null){return -1;}
		if(!i.hasItemMeta()){return -1;}
		ItemMeta meta = i.getItemMeta();
		if(!meta.hasLore()){return -1;}
		List<String> l = meta.getLore();
		if(l.size() == 0){return -1;}
		try {
			int id = l.size() - 1;
			String string2 = l.get(id);
			if (string2.startsWith("D")){
				String string = string2.substring(1);
				int iId = Integer.parseInt(string);
				if (iId > 0){
					return iId;
				}
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}
	private boolean hasiId(ItemStack i){
		return getiId(i) != -1;
	}
	public SpecialItemData getData(ItemStack i){
		return new SpecialItemData(getiId(i));
	}
	public SpecialItemData getData(int iId){
		return new SpecialItemData(iId);
	}
//	public static ItemStack setNBTData(ItemStack i, String name, int value){
//		ItemStack stack = NbtFactory.getCraftItemStack(i);
//		NbtCompound other = NbtFactory.fromItemTag(stack);
//		 
//		// Do whatever
//		String path = "special" + "." + name;
//		other.putPath(path, value);
//		//other.putPath("display.Lore", NbtFactory.createList("Line 1", "Line 2"));
//		NbtFactory.setItemTag(stack, other);
//		return stack;
//	}
//	public static int getNBTData(ItemStack i, String name){
//		ItemStack stack = NbtFactory.getCraftItemStack(i);
//		if (stack == null){Bukkit.broadcastMessage("null stack"); return Integer.MIN_VALUE;}
//		//Bukkit.broadcastMessage("Non-null stack");
//		NbtCompound other = NbtFactory.fromItemTag(stack);
//		//Bukkit.broadcastMessage(Integer.toString(other.size()));
////		for (Object c : other.values()){
////			if (c instanceof NbtFactory.NbtCompound){
////				Bukkit.broadcastMessage(c.toString());
////			}
////		}
//		// Do whatever
//		
//		//other.putPath("display.Lore", NbtFactory.createList("Line 1", "Line 2"));
//		NbtCompound special = other.getPath("special");
//		if (special == null){return Integer.MIN_VALUE;}
//		String key = "special" + "." + name;
//		return special.getInteger(name, Integer.MIN_VALUE);
//	}
//	public static Boolean hasNBTData(ItemStack i, String name){
//		return (getNBTData(i, name) != Integer.MIN_VALUE);
//	}
//	//Default NBT Calls
//	//set
//	public static ItemStack setValue(ItemStack i, int value){
//		return setNBTData(i, "value", value);
//	}
//	public static ItemStack setMaxValue(ItemStack i, int value){
//		return setNBTData(i, "maxvalue", value);
//	}
//	public static ItemStack setModifier(ItemStack i, int value){
//		return setNBTData(i, "mod", value);
//	}
//	public static ItemStack setSpecialID(ItemStack i, int ID){
//		return setNBTData(i, "id", ID);
//	}
//	//get
//	public static int getValue(ItemStack i){
//		return getNBTData(i, "value");
//	}
//	public static int getMaxValue(ItemStack i){
//		return getNBTData(i, "maxvalue");
//	}
//	public static int getModifier(ItemStack i){
//		return getNBTData(i, "mod");
//	}
//	public static int getSpecialID(ItemStack i){
//		return getNBTData(i, "id");
//	}
//	//has
//	public static Boolean hasValue(ItemStack i){
//		return hasNBTData(i, "value");
//	}
//	public static Boolean hasMaxValue(ItemStack i){
//		return hasNBTData(i, "maxvalue");
//	}
//	public static Boolean hasModifier(ItemStack i){
//		return hasNBTData(i, "mod");
//	}
//	public static Boolean hasSpecialID(ItemStack i){
//		return hasNBTData(i, "id");
//	}
	//----------------
	//Listeners
	@EventHandler
    public void onInteract(PlayerInteractEvent evt) {
		Action a = evt.getAction();
		Player ply = evt.getPlayer();
		if (!hasItem(ply)){return;}
		if (!hasItemInHand(ply)){return;}
		//ply.sendMessage(getName());
		evt.setCancelled(true);
	}
	@EventHandler
    public void onItemDespawn(ItemDespawnEvent evt) {
		Item i = evt.getEntity();
		if (!isItemStack(i.getItemStack())){return;}
		if (Utils.Possibilitat(85)){
			evt.setCancelled(true);
		}
	}
	@EventHandler
    public void onPlace(BlockPlaceEvent evt) {
		Player ply = evt.getPlayer();
		if (!hasItem(ply)){return;}
		if (!hasItemInHand(ply)){return;}
		evt.setCancelled(true);
	}
	@EventHandler
    public void onPrepareItemCraft(CraftItemEvent evt) {
		for (ItemStack i : evt.getInventory().getMatrix()){
			if(isItemStack(i)){
				evt.setCancelled(true);
			}
		}
		
	}
}
