package com.biel.lobby.minions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.biel.BielAPI.ai.NearestTargetGoal;
import com.biel.BielAPI.ai.RangedAttackGoal;
import com.biel.BielAPI.ai.Volley;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.PaperMessages;

/**
 * The Obsidian Defenders snowman (2013): a snow golem owned by the player who threw the
 * snowball, holding its post, shooting enemies within eight blocks. Its numbers are fixed
 * at birth; what a hit on a player is worth is the game's rule, passed in.
 */
public final class SnowmanMinion extends Minion {
	public static final double MAX_HEALTH = 5;
	public static final double ACQUIRE_RADIUS = 12;
	public static final double RANGE = 8;
	public static final int DEFAULT_COOLDOWN_TICKS = 20;
	private static final int RESCAN_TICKS = 10;
	private static final double SNOWBALL_DAMAGE_TO_MINIONS = 2;

	/** What a snowball from this snowman does to an enemy player. */
	@FunctionalInterface
	public interface SnowballHit {
		void apply(SnowmanMinion snowman, EntityDamageByEntityEvent evt, Player victim);
	}

	private final int cooldownTicks;
	private final SnowballHit hitOnPlayer;

	public SnowmanMinion(JocEquips game, Equip team, Player owner, int cooldownTicks, SnowballHit hitOnPlayer) {
		super(game, team, owner);
		this.cooldownTicks = cooldownTicks;
		this.hitOnPlayer = hitOnPlayer;
	}

	public int cooldownTicks() {
		return cooldownTicks;
	}

	@Override
	protected Mob spawnBody(Location at) {
		Snowman golem = at.getWorld().spawn(at, Snowman.class);
		golem.getAttribute(Attribute.MAX_HEALTH).setBaseValue(MAX_HEALTH);
		golem.setHealth(MAX_HEALTH);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, PotionEffect.INFINITE_DURATION, 0, true, false));
		golem.customName(PaperMessages.legacy(team().getChatColor() + "Ninot de " + ownerName()));
		golem.setCustomNameVisible(true);
		return golem;
	}

	@Override
	protected void installGoals(Mob mob) {
		Bukkit.getMobGoals().addGoal(mob, 1, new NearestTargetGoal(mob, ACQUIRE_RADIUS, true, RESCAN_TICKS, this::isEnemy));
		Bukkit.getMobGoals().addGoal(mob, 2, new RangedAttackGoal(mob, 0, RANGE, cooldownTicks, true, 1.0, Volley.innate()));
	}

	@Override
	public void onHit(EntityDamageByEntityEvent evt, LivingEntity victim) {
		if (victim instanceof Player player) {
			hitOnPlayer.apply(this, evt, player);
		} else {
			evt.setDamage(SNOWBALL_DAMAGE_TO_MINIONS);
		}
	}
}
