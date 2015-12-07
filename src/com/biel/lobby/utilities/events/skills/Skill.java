package com.biel.lobby.utilities.events.skills;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.Utils;

public abstract class Skill extends PlayerWorldEventBus {
	private Player player;
	public int id = Utils.NombreEntre(0, 100);
	public Skill(){
		super(null);
	}
	public Skill(Player ply) {
		super(ply);
		this.player = ply;
	}
	public abstract String getName();
	public abstract String getDescription();
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void tick() {
		//TICK!!
	}
	protected int getTickSpacing(){
		return 20;
	}
	//GAME-WRAPPING
	protected Joc getGame(){
		Mapa mapWherePlayerIs = Com.getPlugin().gest.getMapWherePlayerIs(getPlayer());
		if(mapWherePlayerIs == null)return null;
		if(mapWherePlayerIs instanceof Joc){
			return (Joc) mapWherePlayerIs;			
		}
		return null;
	}
	protected void sendGlobalMessage(String message){
		getGame().sendGlobalMessage(message);
	}
	protected void sendPlayerMessage(Player p, String message){
		getGame().sendPlayerMessage(p, message);
	}
	protected void sendPlayerMessage(String message){
		sendPlayerMessage(getPlayer(), message);
	}
	protected void sendSkillMessage(String message){
		sendSkillMessage(getPlayer(), message);
	}
	protected void sendSkillMessage(Player p, String message){
		sendPlayerMessage(p, ChatColor.DARK_AQUA + "[" + getName() + " ] > " + ChatColor.GRAY + message);
	}
	public PlayerInfo getPlayerInfo(Player p) {
		if (player == null)return null;
		return getGame().getPlayerInfo(p);
	}
	//-OLD-
	public Material getMaterial(){
		return Material.DIAMOND;
	}
	public Short getDamageValue(){
		return 0;
	}
	public Byte getData(){
		return 0;
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
	private String[] getLoreArr(){
		ArrayList<String> arr = getDescLines();
		String valueLine = getValueLine();
		if (valueLine != null){
			arr.add(valueLine);
		}
		//arr.add("D" + Integer.toString(d.iId));
		//To array
		String[] arrayResult = arr.toArray(new String[arr.size() - 1]);
		return arrayResult;
	}
	public String getValueName() {
		return null;
	}
	private static boolean hasValue(){
		return getMaxValue() != 0;
	}
	public String getValueLine() {
		if(hasValue()){
			Double value = getValue();
			Double roundValue = (double)Math.round(value * 100) / 100;
			String s = ChatColor.BLUE + Double.toString(roundValue);
			if(getMaxValue() > 0){
				Double maxValue = getMaxValue();
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
	private static Double getMaxValue() {
		// TODO Auto-generated method stub
		return 0D;
	}
	private static Double getValue() {
		// TODO Auto-generated method stub
		return 0D;
	}
	public ArrayList<String> getDescLines() {
		ArrayList<String> arr = new ArrayList<String>();
		String desc = getDescription();
		int i = 0;
		int lastBreak = 0;
		int charsForLine = getCharsForDescLine();
		int maxChar = desc.length();
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
	private static int getCharsForDescLine() {
		return 32;
	}
	protected ItemStack getItemStack(){
		ItemStack i = Utils.setItemNameAndLore(new ItemStack(getMaterial(), 1, getDamageValue(), getData()), getTierChatColor() + getName(), getLoreArr());
		//ItemStack i = new ItemStack(getMaterial());
		i.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, getTier());
		return i;//"[Line1]", "[Line2]", "Info{Current/Max}");
	}
	//-END-OLD-
}
