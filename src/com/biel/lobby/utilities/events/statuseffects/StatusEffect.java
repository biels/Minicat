package com.biel.lobby.utilities.events.statuseffects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.Utils;

public abstract class StatusEffect extends PlayerWorldEventBus {
	private int remainingTicks = -1; // -1 -> Forever
	private double value = -1; // -1 -> Forever
	private double maxValue = -1; // -1 -> Forever
	private String ownerName;
	private boolean modal = false;
	private int modalRemainingTicks = -1; // -1 -> Never
	private int ticksLived = 0;
	private AuraStatusEffect aura = null;
	public int id = Utils.NombreEntre(0, 100);
	private StatusEffectType type = StatusEffectType.UNDEFINED;
	public enum StatusEffectType{UNDEFINED, SKILL_TRAY, BUFF, DEBUFF}
	public StatusEffect(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}
	public abstract String getName();
	public abstract String getDescription();
	/**
	 * @return The value associated with this effect. Set to -1 to disable.
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value The new value to associate with this effect. Set to -1 to disable.
	 */
	public void setValue(double value) {
		double oldValue = this.value;
		boolean wasMaxed = isMaxed();
		if(maxValue != -1){
			if(value > maxValue)value = maxValue;
			if(value < 0)value = maxValue;			
		}
		this.value = value;
		tryTriggerOnMaxes(oldValue, wasMaxed);
	}
	private void tryTriggerOnMaxes(double oldValue, boolean wasMaxed) {
		boolean canTrigger = getMaxValue() != -1 && getValue() != -1; 
		if(!wasMaxed && isMaxed() && canTrigger)onMaxUp();
		if(wasMaxed && !isMaxed() && canTrigger)onMaxLose();
	}
	/**
	 * @return The max value. Set to -1 to disable.
	 */
	public double getMaxValue() {
		return maxValue;
	}
	/**
	 * @param maxValue Sets the max value. Set to -1 to disable.
	 */
	public void setMaxValue(double maxValue) {
		double oldValue = this.value;
		boolean wasMaxed = isMaxed();
		this.maxValue = maxValue;
		tryTriggerOnMaxes(oldValue, wasMaxed);
	}
	public boolean isMaxed(){
		return getValue() == getMaxValue();
	}
	public Player getOwnerPlayer() {
		if(ownerName == null)return null;
		return Bukkit.getPlayer(ownerName);
	}

	public void setOwnerPlayer(Player player) {
		this.ownerName = player.getName();
	}
	public StatusEffectType getType() {
		return type;
	}
	public void setType(StatusEffectType type) {
		this.type = type;
	}
	
	public AuraStatusEffect getAura() {
		return aura;
	}
	public void setAura(AuraStatusEffect aura) {
		this.aura = aura;
	}
	/**
	 * @return Whether the effect is modal or not.
	 */
	public boolean isModal() {
		return modal;
	}
	/**
	 * @param modal Do not use externally for true
	 */
	public void setModal(boolean modal) {
		this.modal = modal;
		if(!modal)setModalRemainingTicks(-1); //-1 -> Never!
	}
	public int getModalRemainingTicks() {
		return modalRemainingTicks;
	}
	public void setModalRemainingTicks(int modalRemainingTicks) {
		this.modalRemainingTicks = modalRemainingTicks;
		if(modalRemainingTicks > 0)setModal(true);
	}
	public ChatColor getChatColor(){
		ChatColor c = ChatColor.GRAY;
		switch(getType()){
		case BUFF:
			c = ChatColor.GREEN;
			if(modal)c = ChatColor.DARK_GREEN;
			break;
		case DEBUFF:
			c = ChatColor.RED;
			if(modal)c = ChatColor.DARK_RED;
			break;
		case SKILL_TRAY:
			c = ChatColor.DARK_AQUA;
			if(modal)c = ChatColor.AQUA;
			break;
		case UNDEFINED:
			if(modal)c = ChatColor.BLACK;
			break;
		default:
			break;		
		}
		return c;
	}
	/**
	 * @return The display text. Override to change or override to null to disable. 
	 */
	public String getDisplayText() { //Per deshabilitar sobreescriure a null
		String cdString = getRemainingSecondsString();
		String valueString = getValueString();
		String modalHeading = "";
		if(modal)modalHeading += ChatColor.BOLD;
		String r = ChatColor.RESET + "" + getChatColor() + modalHeading + getName() + " " + valueString + " " + cdString;
		return r.trim(); //+ " id=" + id;
	}
	public String getRemainingSecondsString() {
		if(remainingTicks == -1)return "";
		return "(" + (Math.round(getRemainingSeconds() * 10) / 10.0) + "s)";
	}
	public String getValueString() {
		if(value != -1 && getMaxValue() != -1){
			return "(" + Math.round(getValue()) + "/" + Math.round(getMaxValue()) + ")";			
		}
		if(value != -1 && getMaxValue() == -1){
			return "(" + Math.round(getValue()) + ")";			
		}
		return "";
	}
	/**
	 * @return The remaining ticks for the effect to expire
	 */
	public int getRemainingTicks() {
		return remainingTicks;
	}
	public void setRemainingTicks(int remainingTicks) {
		this.remainingTicks = remainingTicks;
	}
	/**
	 * @return The remaining seconds for the effect to expire
	 */
	public double getRemainingSeconds(){
		return getRemainingTicks() / getTickSpacing();
	}
	public int getTicksLived() {
		return ticksLived;
	}
	public boolean isNthTick(int nth){
		return ticksLived % nth == 0;
	}
	public void onMaxUp(){
		
	}
	public void onMaxLose(){
		
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		if(getGame() == null)return false;
		return super.isValid() && getGame().getPlayers().contains(getPlayer()) && (remainingTicks > 0 || remainingTicks == -1);
	}
	/**
	 * Tick method called every tick (20 times a second) by ultraHeartbeat
	 */
	public void tick() {
		if(remainingTicks == 0)return;
		if(remainingTicks > 0)remainingTicks -= getTickSpacing();
		//if(remainingTicks < 0)remainingTicks = 0; //ALLOW -1 VALUES	
		if(modalRemainingTicks == 0)setModal(false);
		if(modalRemainingTicks > 0)modalRemainingTicks -= getTickSpacing();
		if(modalRemainingTicks < 0)modalRemainingTicks = 0;
		ticksLived++;
	}
	private static double getTickSpacing(){
		return 20;
	}
	/**
	 * @return Whether this element is marked for removal or it is still valid, allowing -1 values to stay valid indefinitely
	 */
	public boolean hasExpired(){
		if(remainingTicks == -1)return false;
		return remainingTicks <= 0 && isValid();
	}
	/**
	 * Sets remaining ticks to 0 thus removing this StatusEffect
	 */
	public void expire(){
		setRemainingTicks(0);
	}
	/**
	 * Called before effect removal
	 */
	public void clearExternals(){
		
	}
	//GAME-WRAPPING
		/**
		 * @return The underlying game instance
		 */
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
		protected void sendEffectMessage(String message){
			sendEffectMessage(getPlayer(), message);
		}
		protected void sendEffectMessage(Player p, String message){
			sendPlayerMessage(p, ChatColor.DARK_AQUA + "{" + getName() + "} > " + ChatColor.GRAY + message);
		}
		/**
		 * @param p The player to get the info from
		 * @return Wraps the getPlayerInfo() method from the Game class
		 */
		public PlayerInfo getPlayerInfo(Player p) {
			return getGame().getPlayerInfo(p);
		}
		/**
		 * @return Gets the info of the player owning this Effect
		 */
		public PlayerInfo getPlayerInfo() {
			return getGame().getPlayerInfo(getPlayer());
		}
		//
		@Override
		protected Boolean verifyEvent(Event evt) {
			// TODO Auto-generated method stub
			return super.verifyEvent(evt);
		}
}
