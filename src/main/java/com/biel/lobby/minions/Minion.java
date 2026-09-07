package com.biel.lobby.minions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.destroystokyo.paper.entity.ai.GoalType;

/**
 * A mob enlisted by a side: a body, the team it fights for, optionally the player who
 * owns it, and the goals that drive it. The game keeps the list ({@link JocEquips#enlist}),
 * resolves sides ({@link JocEquips#teamOf}), cancels friendly fire, routes hits to
 * {@link #onHit}, ticks once a second, and removes every minion when the match ends.
 * Design: minicat-repo docs/games/obsidian-defenders/snowman-turrets-design.md.
 */
public abstract class Minion {
	protected final JocEquips game;
	private final Equip team;
	private final UUID ownerId;
	private final String ownerName;
	private final int bornAtSecond;
	private UUID entityId;

	protected Minion(JocEquips game, Equip team, Player owner) {
		this.game = game;
		this.team = team;
		this.ownerId = owner == null ? null : owner.getUniqueId();
		this.ownerName = owner == null ? null : owner.getName();
		this.bornAtSecond = game.segonsTranscorreguts();
	}

	/** Spawns the body, strips every vanilla movement and targeting goal, installs ours. Called by {@link JocEquips#enlist}. */
	public final Mob spawn(Location at) {
		Mob mob = spawnBody(at);
		entityId = mob.getUniqueId();
		mob.setRemoveWhenFarAway(false);
		mob.setPersistent(true);
		mob.setCanPickupItems(false);
		Bukkit.getMobGoals().removeAllGoals(mob, GoalType.MOVE);
		Bukkit.getMobGoals().removeAllGoals(mob, GoalType.TARGET);
		installGoals(mob);
		return mob;
	}

	/** Spawn and dress the body at the given spot. */
	protected abstract Mob spawnBody(Location at);

	/** Add this minion's goals; the vanilla movement and targeting goals are already gone. */
	protected abstract void installGoals(Mob mob);

	/** A hit by this minion, or by its projectile, landed on an enemy; set what it is worth. */
	public void onHit(EntityDamageByEntityEvent evt, LivingEntity victim) {
	}

	/** Once a second while alive. */
	public void tick() {
	}

	/** The body died; the drops are already cleared. Killer may be null. */
	public void onMinionDeath(EntityDeathEvent evt, Player killer) {
	}

	/** Enemy players who are playing, and minions of another team. Neutral mobs are never enemies. */
	public boolean isEnemy(LivingEntity candidate) {
		Equip side = game.teamOf(candidate);
		if (side == null || side == team) return false;
		if (candidate instanceof Player player) {
			return !player.isDead() && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR && !game.isSpectator(player);
		}
		return game.minionOf(candidate) != null;
	}

	public Mob mob() {
		if (entityId == null) return null;
		Entity entity = Bukkit.getEntity(entityId);
		return entity instanceof Mob mob && mob.isValid() && !mob.isDead() ? mob : null;
	}

	public boolean isAlive() {
		return mob() != null;
	}

	/** Removes the body; the game drops the minion from its list. */
	public void remove() {
		Mob mob = mob();
		if (mob != null) mob.remove();
	}

	public Equip team() {
		return team;
	}

	public UUID entityId() {
		return entityId;
	}

	public UUID ownerId() {
		return ownerId;
	}

	public String ownerName() {
		return ownerName;
	}

	public Player owner() {
		return ownerId == null ? null : Bukkit.getPlayer(ownerId);
	}

	public boolean isOwnedBy(Player player) {
		return ownerId != null && ownerId.equals(player.getUniqueId());
	}

	public int bornAtSecond() {
		return bornAtSecond;
	}
}
