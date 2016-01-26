package com.biel.lobby.utilities.events.skills.types;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.biel.lobby.utilities.events.statuseffects.StatusEffect;

public abstract class ItemAttatchedModeSkill extends InherentSkill {
	private ArrayList<SkillMode> modes = new ArrayList<SkillMode>();
	boolean knowsHowToUse= false;
	boolean trayAdded = false;
	int selectedMode = 0;
	public ItemAttatchedModeSkill(Player ply) {
		super(ply);
		registerModes();
		if(modes.size() == 0)sendSkillMessage("Habilitat mal configurada, 0 modes");
	}

	public abstract void registerModes();
	public void registerMode(SkillMode mode){
		modes.add(mode);
	}
	
	public ArrayList<SkillMode> getModes() {
		return modes;
	}
	public SkillMode getSelectedMode(){
		if(selectedMode >= 0 && selectedMode < modes.size())return modes.get(selectedMode);
		return null;
	}
	public void onModeSwitch(SkillMode newMode){
		knowsHowToUse = true;
	}
	public class SkillMode{
		private int id;
		private String name;
		private String description;
		private ChatColor chatColor;
		
		public SkillMode(int id, String name, String description) {
			super();
			this.id = id;
			this.name = name;
			this.description = description;
		}
		
		public SkillMode(int id, String name, String description, ChatColor chatColor) {
			super();
			this.id = id;
			this.name = name;
			this.description = description;
			this.chatColor = chatColor;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public ChatColor getChatColor() {
			return chatColor;
		}
		public void setChatColor(ChatColor chatColor) {
			this.chatColor = chatColor;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return MessageFormat.format("{0}{1}{2}: {3}", chatColor, name, ChatColor.WHITE, description);
		}
	}
	public List<String> getModeFormattedList(){
		return getModes().stream().map(m -> " + " + m.toString()).collect(Collectors.toList());
	}
	public String getModeList() {
		return getModes().stream().map(m -> m.getChatColor() + m.getName() + ChatColor.WHITE).collect(Collectors.joining(", ", "[", "]"));//.collect(Collectors.joining(", ", "[", "]")));
	}
	//Wheel selection logic
	public abstract boolean matchesItem(ItemStack i);
	public boolean isInHand(){
		return matchesItem(getPlayer().getItemInHand());
	}
	@Override
	protected void onPlayerItemHeld(PlayerItemHeldEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerItemHeld(evt, p);
		//When item in hand
		PlayerInventory inv = getPlayer().getInventory();
		ItemStack previousItem = inv.getContents()[evt.getPreviousSlot()];
		if(previousItem != null && matchesItem(previousItem)){
			tellHowToUse();
			getTrayEffect();
		}
		if(matchesItem(previousItem) && p.isSneaking()){
			int increment = evt.getNewSlot() - evt.getPreviousSlot();
			getPlayer().getInventory().setHeldItemSlot(evt.getPreviousSlot());
			scroll(increment);
		}
	}
	public void scroll(boolean left){
		int increment = (left ? -1 : 1);
		scroll(increment);
	}
	public void scroll(int increment){
		selectedMode += increment;
		if(selectedMode < 0)selectedMode = modes.size() - 1;
		if(selectedMode >= modes.size())selectedMode = 0;
		onModeSwitch(getSelectedMode());
	}
	//Announcer
	private void tellHowToUse(){
		if(!knowsHowToUse)sendSkillMessage("Canvia entre modes amb SHIFT + M_WHEEL");
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		
	}
	//Status effect
	protected abstract Class<? extends ItemAttatchedModeSkillTrayEffect> getTrayEffectClass();
	protected ItemAttatchedModeSkillTrayEffect getTrayEffect(){
		if(!trayAdded)getPlayerInfo(getPlayer()).addStatusEffect(getTrayEffectInstance());
		trayAdded = true;
		return getPlayerInfo(getPlayer()).getStatusEffect(getTrayEffectClass());
	}
	private ItemAttatchedModeSkillTrayEffect getTrayEffectInstance(){
		try {
			Constructor<?> constructor = getTrayEffectClass().getConstructors()[0];
			return (ItemAttatchedModeSkillTrayEffect) constructor.newInstance(this, getPlayer());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Null TrayEffectInstance");
		return null;
	}
	public class ItemAttatchedModeSkillTrayEffect extends StatusEffect{

		public ItemAttatchedModeSkillTrayEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.SKILL_TRAY);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return getSelectedMode().getName();
		}
		@Override
		public String getDisplayText() {
			// TODO Auto-generated method stub
			if(!isInHand())return null;
			return super.getDisplayText();
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "Mode " + getSelectedMode().getName() + ": " + getSelectedMode().getDescription();
		}
		@Override
		public ChatColor getChatColor() {
			if(getSelectedMode().getChatColor() != null)return getSelectedMode().getChatColor();
			return super.getChatColor();
		}
	}
}
