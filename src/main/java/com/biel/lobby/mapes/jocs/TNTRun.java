package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.biel.lobby.Com;
import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.mapes.JocLastStanding;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;

/** Hot potato: pass the TNT before the sixty-second round expires. */
public class TNTRun extends JocLastStanding {
    // Names outlive the Player entity across disconnects.
    final ArrayList<String> tntPlayers = new ArrayList<>();
    private final Map<String, Long> immunityTokens = new HashMap<>();
    private long nextImmunityToken;
    private long hyperSpeedToken;
    private boolean hyperSpeed;
    int temps = 60;
    int round;
    private int roundTaskId = -1;

    @Override protected void setCustomGameRules() {}
    @Override protected ArrayList<ItemStack> getStartingItems(Player player) { return new ArrayList<>(); }
    @Override protected int getBaseSkillUnlockerAmount() { return 0; }
    @Override public String getGameName() { return "TNTRun"; }

    @Override
    protected void teletransportarTothom() {
        for (Player player : getAlivePlayers()) player.teleport(getWorld().getSpawnLocation());
    }

    @Override
    protected void customJocIniciat() {
        super.customJocIniciat();
        startRound();
    }

    // Joc gives the starting kit after customJocIniciat; restore what that reset wipes.
    @Override
    protected void donarItemsInicials(Player player) {
        super.donarItemsInicials(player);
        restoreRoundState(player);
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent event, Player player) {
        super.onPlayerRespawnAfterTick(event, player);
        restoreRoundState(player);
    }

    @Override
    protected void onSeatResumed(Player player) {
        super.onSeatResumed(player);
        if (JocEnMarxa() && !isAlive(player)) {
            Com.teleportPlayerToLobby(player);
            return;
        }
        restoreRoundState(player);
    }

    void restoreRoundState(Player player) {
        if (!JocEnMarxa() || !isAlive(player) || !getPlayers().contains(player)) return;
        Material marker = hasTNT(player) ? Material.TNT : isImmune(player) ? Material.QUARTZ_BLOCK : null;
        player.getInventory().setHelmet(marker == null ? null : new ItemStack(marker));
        player.getInventory().setItem(8, marker == null ? null : new ItemStack(marker));
        applyEffects(player);
    }

    void applyEffects(Player player) {
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.JUMP_BOOST);
        int speed = hyperSpeed ? 20 : hasTNT(player) ? 4 : 2;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800 * 20, speed, true), true);
        if (!hyperSpeed) player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 800 * 20, 0, true), true);
    }

    private void updateEffects() {
        for (Player player : getAlivePlayers()) {
            if (getPlayers().contains(player)) applyEffects(player);
        }
    }

    Boolean hasTNT(Player player) { return tntPlayers.contains(player.getName()); }
    Boolean isImmune(Player player) { return immunityTokens.containsKey(player.getName()); }

    void giveImmunity(Player player, int seconds) {
        String playerName = player.getName();
        long token = ++nextImmunityToken;
        immunityTokens.put(playerName, token);
        restoreRoundState(player);
        scheduleGameplayTask(() -> {
            if (!Long.valueOf(token).equals(immunityTokens.get(playerName))) return;
            immunityTokens.remove(playerName);
            // Resolve the current entity and fence inventory writes to this active match.
            Player currentPlayer = Bukkit.getPlayerExact(playerName);
            if (currentPlayer != null) restoreRoundState(currentPlayer);
        }, 20L * seconds);
    }

    void passarTNT(Player from, Player to) {
        if (!JocEnMarxa() || !isAlive(from) || !isAlive(to)
                || !getPlayers().contains(from) || !getPlayers().contains(to)
                || isImmune(to) || !hasTNT(from) || hasTNT(to)) return;
        treureTNT(from);
        posarTNT(to);
        giveImmunity(from, 2);
        updateEffects();
        sendGlobalMessage(ChatColor.YELLOW + from.getName() + ChatColor.WHITE + " ha posat el seu TNT a " + ChatColor.RED + to.getName());
    }

    void posarTNT(Player player) {
        if (!hasTNT(player)) {
            tntPlayers.add(player.getName());
            player.sendMessage("Tens un " + ChatColor.DARK_RED + "TNT" + ChatColor.WHITE + "!");
        }
        restoreRoundState(player);
    }

    void treureTNT(Player player) {
        tntPlayers.remove(player.getName());
        restoreRoundState(player);
    }

    int getTNTAmount() { return (getAlivePlayers().size() + 2) / 3; }

    void tntInicial() {
        ArrayList<Player> alivePlayers = new ArrayList<>(getAlivePlayers());
        Collections.shuffle(alivePlayers);
        for (int index = 0; index < getTNTAmount(); index++) posarTNT(alivePlayers.get(index));
    }

    protected void returnEliminatedPlayerToLobby(Player player) { Com.teleportPlayerToLobby(player); }

    void explotarJugadors() {
        // Offline holders still lose. getPlayers() alone allowed disconnecting to dodge the timer.
        for (String playerName : new ArrayList<>(tntPlayers)) {
            tntPlayers.remove(playerName);
            immunityTokens.remove(playerName);
            removeIfAlive(playerName);
            Player player = Bukkit.getPlayerExact(playerName);
            if (player != null && getPlayers().contains(player)) returnEliminatedPlayerToLobby(player);
        }
    }

    void startRound() {
        if (!JocEnMarxa() || getAliveNames().size() <= 1) return;
        cancelRoundTask();
        round++;
        temps = 60;
        tntPlayers.clear();
        immunityTokens.clear();
        hyperSpeed = false;
        hyperSpeedToken++;
        sendGlobalMessage(MessageKey.TNT_RUN_ROUND_START, MessageArgument.number("round", round));
        Utils.clearPlayers(getAlivePlayers());
        teletransportarTothom();
        tntInicial();
        updateEffects();
        updateScoreBoards();
        ProgTask();
    }

    void endRound() {
        cancelRoundTask();
        explotarJugadors();
        if (JocEnMarxa() && getAliveNames().size() > 1) startRound();
    }

    private void cancelRoundTask() {
        if (roundTaskId != -1) Bukkit.getScheduler().cancelTask(roundTaskId);
        roundTaskId = -1;
    }

    public void ProgTask() {
        int scheduledRound = round;
        // Three seconds of preparation, then sixty one-second countdown steps.
        roundTaskId = scheduleGameplayRepeatingTask(() -> {
            if (!JocEnMarxa() || round != scheduledRound) return;
            temps--;
            if (temps <= 0) {
                endRound();
                return;
            }
            if (Utils.Possibilitat(1) && Utils.Possibilitat(80)) hipervelocitat();
            updateScoreBoards();
        }, 20L * 4, 20L);
    }

    public void hipervelocitat() {
        int seconds = Utils.NombreEntre(2, 8);
        long token = ++hyperSpeedToken;
        hyperSpeed = true;
        updateEffects();
        sendGlobalMessage(ChatColor.AQUA + "HIPERVELOCITAT! (" + seconds + "s)");
        scheduleGameplayTask(() -> {
            if (!JocEnMarxa() || token != hyperSpeedToken) return;
            hyperSpeed = false;
            updateEffects();
        }, 20L * seconds);
    }

    @Override
    protected void customLeave(Player player, List<String> attachments) {
        // Round elimination returns players to the lobby; that is not a forfeit.
        if (!JocIniciat || isAlive(player)) super.customLeave(player, attachments);
        eliminateLeaver(player.getName());
    }

    @Override
    protected void onSeatAbandoned(Seat seat, List<String> attachments) {
        if (!JocIniciat || isAlive(seat.getName())) super.onSeatAbandoned(seat, attachments);
        eliminateLeaver(seat.getName());
    }

    private void eliminateLeaver(String playerName) {
        tntPlayers.remove(playerName);
        immunityTokens.remove(playerName);
        if (!JocEnMarxa()) return;
        removeIfAlive(playerName);
        if (JocEnMarxa() && tntPlayers.isEmpty()) startRound();
    }

    @Override
    public void clearExternals() {
        super.clearExternals();
        roundTaskId = -1;
        tntPlayers.clear();
        immunityTokens.clear();
        hyperSpeed = false;
        hyperSpeedToken++;
    }

    @Override
    protected void updateScoreBoard(Player player) {
        if (!JocEnMarxa()) return;
        ArrayList<String> lines = new ArrayList<>();
        ChatColor color = ChatColor.DARK_GREEN;
        if (temps <= 45) color = ChatColor.GREEN;
        if (temps <= 25) color = ChatColor.YELLOW;
        if (temps <= 12) color = ChatColor.RED;
        if (temps <= 4) color = ChatColor.DARK_RED;
        lines.add(ChatColor.YELLOW + "Temps: " + color + "" + ChatColor.BOLD + temps);
        lines.add(ChatColor.GREEN + "Ronda: " + ChatColor.WHITE + round);
        lines.add(ChatColor.BLUE + "Jugadors: " + ChatColor.WHITE + getAliveNames().size());
        ScoreBoardUpdater.setScoreBoard(player, "Estadístiques", lines, null);
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent event, Player damaged, Player damager, boolean ranged) {
        super.onPlayerDamageByPlayer(event, damaged, damager, ranged);
        if (!event.isCancelled() && !ranged) passarTNT(damager, damaged);
    }

    @Override
    protected void onPlayerDamage(EntityDamageEvent event, Player player) {
        super.onPlayerDamage(event, player);
        event.setDamage(0.2);
        if (JocEnMarxa()) {
            if (event.getCause() == DamageCause.VOID) event.setDamage(40);
            if (!event.isCancelled() && event.getCause() == DamageCause.BLOCK_EXPLOSION) {
                eliminateLeaver(player.getName());
                returnEliminatedPlayerToLobby(player);
            }
        }
    }
}
