package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public class Arena4 extends JocEquips {
	private static final long WRONG_TARGET_MESSAGE_COOLDOWN_MILLIS = 5_000L;
	private final Map<UUID, Long> lastWrongTargetMessage = new HashMap<>();

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();
		setBlockBreakPlace(false);
		sendGlobalMessage(ChatColor.GOLD + "Arena 4: each team hunts one marked target team.");
		sendGlobalMessage(ChatColor.YELLOW + "RED hunts YELLOW, YELLOW hunts GREEN, GREEN hunts BLUE, and BLUE hunts RED.");
		sendGlobalMessage(ChatColor.AQUA + "You deal 2x damage and earn 3 points against your target. You cannot damage the team hunting you; other kills earn 1 point.");
		for (Player player : getPlayers()) {
			Arena4Equip team = (Arena4Equip) obtenirEquip(player);
			if (team == null) continue;
			Arena4Equip target = (Arena4Equip) team.equipObjectiu();
			Arena4Equip hunter = getHunterOf(team);
			sendPlayerMessage(player, ChatColor.GREEN + "Your target: " + formatTeam(target)
					+ ChatColor.GRAY + " | " + ChatColor.RED + "Your hunter: " + formatTeam(hunter));
		}
	}

	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if (evt.isCancelled()) return;
		Arena4Equip attackerTeam = (Arena4Equip) obtenirEquip(damager);
		Arena4Equip defenderTeam = (Arena4Equip) obtenirEquip(damaged);
		if (attackerTeam == null || defenderTeam == null || attackerTeam == defenderTeam) return;

		if (defenderTeam.equipObjectiu() == attackerTeam) {
			evt.setCancelled(true);
			sendWrongTargetMessage(damager, ChatColor.RED + "You cannot attack " + formatTeam(defenderTeam)
					+ ChatColor.RED + "; they are hunting your team. Your target is "
					+ formatTeam((Arena4Equip) attackerTeam.equipObjectiu()) + ChatColor.RED + ".");
			return;
		}
		if (attackerTeam.equipObjectiu() == defenderTeam) {
			evt.setDamage(evt.getDamage() * 2.0);
			return;
		}
		sendWrongTargetMessage(damager, ChatColor.YELLOW + "Wrong target: " + formatTeam(defenderTeam)
				+ ChatColor.YELLOW + " takes normal damage. Hunt "
				+ formatTeam((Arena4Equip) attackerTeam.equipObjectiu())
				+ ChatColor.YELLOW + " for 2x damage and 3 points.");
	}

	private Arena4Equip getHunterOf(Arena4Equip huntedTeam) {
		for (Equip team : Equips) {
			Arena4Equip arenaTeam = (Arena4Equip) team;
			if (arenaTeam.equipObjectiu() == huntedTeam) return arenaTeam;
		}
		return null;
	}

	private String formatTeam(Arena4Equip team) {
		if (team == null) return "unknown team";
		return team.getChatColor() + team.getColor().name().toLowerCase(Locale.ROOT) + " team";
	}

	private void sendWrongTargetMessage(Player player, String message) {
		long now = System.currentTimeMillis();
		long lastMessage = lastWrongTargetMessage.getOrDefault(player.getUniqueId(), 0L);
		if (now - lastMessage < WRONG_TARGET_MESSAGE_COOLDOWN_MILLIS) return;
		lastWrongTargetMessage.put(player.getUniqueId(), now);
		sendPlayerMessage(player, message);
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Arena 4";
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new Arena4Equip(DyeColor.RED, "vermell", 3)); //Id 0
		equips.add(new Arena4Equip(DyeColor.BLUE, "blau", 0)); //Id 1
		equips.add(new Arena4Equip(DyeColor.GREEN, "verd", 1)); //Id 2
		equips.add(new Arena4Equip(DyeColor.YELLOW, "groc", 2)); //Id 3
		return equips;
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(Material.WOODEN_SWORD));
		return items;
	}

	
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub
		
	}
	
	
	

	void comprovarGuanyador(){
		for (Equip e : Equips){
			Arena4Equip eq = (Arena4Equip) e;
			if (eq.Score >= eq.MaxScore){
				JocFinalitzat();
			}
		}
	}
	void updateScoreboard(){
		ArrayList<String> list = new ArrayList<>();
		ArrayList<Integer> nums = new ArrayList<>();
		for (Equip e : Equips){
			Arena4Equip eq = (Arena4Equip) e;
			list.add(e.getChatColor() + "Equip " + e.getAdjectiu());
			nums.add(eq.getScore());
		}
		ScoreBoardUpdater.setScoreBoard(getPlayers(), "Punts [Max 20]", list, nums);
	}
	@Override
	protected synchronized void gameEvent(Event event) {
		if (event instanceof PlayerDeathEvent){
			PlayerDeathEvent evt = (PlayerDeathEvent)event;
			Player killed = evt.getEntity();
			Player killer = killed.getKiller();
			if (killer == null){return;}
			Arena4Equip eqKilled = (Arena4Equip) obtenirEquip(killed);
			Arena4Equip eqKiller = (Arena4Equip) obtenirEquip(killer);
			if (eqKilled == null || eqKiller == null){return;}
			if (areEnemies(killed, killer)){
				int suma = 0;
				int resta = 1;
				if(eqKiller.equipObjectiu() == eqKilled){
					suma = 3;
				}else{
					suma = 1;
				}
				if(getTeamSize() < 1){
					suma = 2;
				}
				eqKiller.afegirPunts(suma);
				eqKilled.restarPunts(resta);
				// biel(+3) ha matat a amigiuet(-1)
				String msg = eqKiller.getChatColor() + killer.getName() +"("+ ChatColor.WHITE + "+" + Integer.toString(suma) + eqKiller.getChatColor() + ")" + ChatColor.WHITE + " ha matat a " + killed.getName() + eqKilled.getChatColor() + "("+ ChatColor.WHITE + "-" + Integer.toString(resta) + eqKilled.getChatColor() + ")";
				evt.setDeathMessage(msg);
				updateScoreboard();
				comprovarGuanyador();
			}
		}
	}


	public class Arena4Equip extends Equip{
		int Score = 5;
		public int getScore() {
			return Score;
		}
		public void setScore(int score) {
			Score = score;
		}
		int MaxScore = 20;
		int idEnemic;
		public Arena4Equip(DyeColor color, String adj, int idEnemic) {
			super(color, adj);
			this.idEnemic = idEnemic;
			// TODO Auto-generated constructor stub
		}
		public void restarPunts(int num){
			if (num <= 0){return;}
			int finalscore = Score - num;
			if (finalscore <= 0){Score = 0; return;}
			Score = finalscore;
		}
		public void afegirPunts(int num){
			if (num <= 0){return;}
			int finalscore = Score + num;
			if (finalscore >= MaxScore){Score = MaxScore; return;}
			Score = finalscore;
		}
		public Equip equipObjectiu(){
			return obtenirEquip(idEnemic);
		}
	}




	
	

	

	
	

}
