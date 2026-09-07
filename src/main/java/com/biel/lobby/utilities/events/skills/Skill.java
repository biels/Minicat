package com.biel.lobby.utilities.events.skills;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.Utils;

public abstract class Skill extends PlayerWorldEventBus {
	// The holder by name, as the bus underneath keeps it: a Player object held here
	// went stale the moment its holder reconnected (a new entity, never equal again),
	// so the skill kept acting on a ghost while the pool still found it by name.
	private String playerName;
	public int id = Utils.NombreEntre(0, 100);
	public Skill(){
		super(null);
	}
	public Skill(Player ply) {
		super(ply);
		this.playerName = ply == null ? null : ply.getName();
	}
	/**
	 * @return The name of the skill
	 */
	public abstract String getName();
	/**
	 * @return The description of the skill
	 */
	public abstract String getDescription();
	
	/** The holder as they are right now: null while they are offline. */
	public Player getPlayer() {
		return playerName == null ? null : Bukkit.getPlayer(playerName);
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayer(Player player) {
		if (player == null) return; // The pool's templates are built without a player; there is nothing to unset.
		super.setPlayer(player);
		this.playerName = player.getName();
	}
	public void tick() {
		//TICK!!
		//Auto-destroy on game destroy
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		Player player = getPlayer();
		return super.isValid() && player != null && player.getWorld().equals(getWorld()) && getGame() != null;
	}
	protected int getTickSpacing(){
		return 20;
	}
	protected void removeDefaultNamedAura(){
		getPlayerInfo().removeAura(getName());
	}
	//GAME-WRAPPING
	protected Joc getGame(){
		Player player = getPlayer();
		if(player == null)return null;
		Mapa mapWherePlayerIs = Com.getPlugin().gest.getMapWherePlayerIs(player);
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
		Joc game = getGame();
		if (p == null || game == null)return null;
		return game.getPlayerInfo(p);
	}
	public PlayerInfo getPlayerInfo() {
		return getPlayerInfo(getPlayer());
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
					Double ratio = value / maxValue;
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
		ArrayList<String> arr = new ArrayList<>();
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
		ItemStack i = Utils.setItemNameAndLore(new ItemStack(getMaterial()), getTierChatColor() + getName(), getLoreArr());
		i.editMeta(meta -> meta.setEnchantmentGlintOverride(true));
		return i;//"[Line1]", "[Line2]", "Info{Current/Max}");
	}
	//-END-OLD-
}
