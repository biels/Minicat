package com.biel.lobby.minions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import com.biel.BielAPI.ai.NearestTargetGoal;
import com.biel.BielAPI.ai.RangedAttackGoal;
import com.biel.BielAPI.ai.Volley;
import com.biel.BielAPI.ai.WaypointWalkGoal;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.PaperMessages;

/**
 * The Obsidian Defenders snowman (2013): a snow golem owned by the player who threw the
 * snowball, marching along its team's lane toward the enemy base and shooting enemies
 * within eight blocks on the way (Biel, 2026-09-07: "they should follow the natural path").
 * It joins the lane on the leg nearest to where it lands and walks the rest ({@link Lane#ahead}).
 * Its {@link SnowmanKind} fixes the body and the block on its head; what a hit on a
 * player is worth is the game's rule, passed in. The ice cage charges on the snowman
 * itself (Biel, 2026-09-07 night: "every reasonable number of shots gets blocked into a
 * cage; when the next shot will do that, the snowman should change his head to be the
 * ice block"): the game counts the hits with {@link #chargeCage}, and once armed the head
 * wears ice, whatever the kind, until {@link #dischargeCage} at the caging hit.
 */
public final class SnowmanMinion extends Minion {
	public static final double ACQUIRE_RADIUS = 12;
	public static final double RANGE = 8;
	private static final int RESCAN_TICKS = 10;
	private static final double SNOWBALL_DAMAGE_TO_MINIONS = 2;
	/** How far the pathfinder plans ahead; the enemy base is about a hundred blocks away. */
	private static final double FOLLOW_RANGE = 48;
	private static final double MARCH_SPEED = 1.0;
	private static final double ARRIVE_DISTANCE = 3;
	/** The head block: a cube this wide, riding at the golem's top and lowered onto the head, where the pumpkin sits. */
	private static final float HEAD_SIZE = 0.64f;
	private static final float HEAD_LIFT = -0.66f;
	/** The head while the cage is armed: the next snowball shuts its victim in ice. */
	private static final Material ARMED_HEAD = Material.ICE;

	/** What a snowball from this snowman does to an enemy player. */
	@FunctionalInterface
	public interface SnowballHit {
		void apply(SnowmanMinion snowman, EntityDamageByEntityEvent evt, Player victim);
	}

	private final SnowmanKind kind;
	private final int cooldownTicks;
	private final SnowballHit hitOnPlayer;
	private final Lane lane;
	private UUID headId;
	/** Hits on enemy players since the last cage; the head turns to ice when the next one cages. */
	private int cageCharge;
	private boolean cageArmed;

	/** {@code cooldownTicks}: the kind's, or faster when the thrower carried quartz. {@code lane}: this team's, base to enemy base. */
	public SnowmanMinion(JocEquips game, Equip team, Player owner, SnowmanKind kind, int cooldownTicks, Lane lane, SnowballHit hitOnPlayer) {
		super(game, team, owner);
		this.kind = kind;
		this.cooldownTicks = cooldownTicks;
		this.lane = lane;
		this.hitOnPlayer = hitOnPlayer;
	}

	public SnowmanKind kind() {
		return kind;
	}

	public int cooldownTicks() {
		return cooldownTicks;
	}

	/** Whether the next snowball to land on a player shuts them in ice. */
	public boolean cageArmed() {
		return cageArmed;
	}

	/**
	 * One more hit toward the cage; after {@code hitsToArm} of them the head turns to ice
	 * and the snowman is armed. Counts nothing while armed: the charge waits for the hit
	 * that spends it.
	 */
	public void chargeCage(int hitsToArm) {
		if (cageArmed) return;
		cageCharge++;
		if (cageCharge < hitsToArm) return;
		cageArmed = true;
		showHead(ARMED_HEAD);
		Mob body = mob();
		if (body != null) body.getWorld().playSound(body.getLocation(), Sound.BLOCK_GLASS_PLACE, 1F, 1.4F);
	}

	/** The cage was spent on a victim: back to the kind's head, charging from zero. */
	public void dischargeCage() {
		cageCharge = 0;
		cageArmed = false;
		showHead(kind.headBlock);
	}

	@Override
	protected Mob spawnBody(Location at) {
		Snowman golem = at.getWorld().spawn(at, Snowman.class);
		golem.getAttribute(Attribute.MAX_HEALTH).setBaseValue(kind.maxHealth);
		golem.setHealth(kind.maxHealth);
		golem.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, PotionEffect.INFINITE_DURATION, 0, true, false));
		golem.customName(PaperMessages.legacy(team().getChatColor() + "Ninot de " + kind.label + " de " + ownerName()));
		golem.setCustomNameVisible(true);
		showHead(golem, kind.headBlock);
		return golem;
	}

	private void showHead(Material block) {
		Mob body = mob();
		if (body instanceof Snowman golem) showHead(golem, block);
	}

	/** Puts this block on the golem's head in place of the pumpkin; null gives the pumpkin back. */
	private void showHead(Snowman golem, Material block) {
		if (block == null) {
			removeHead();
			golem.setDerp(false);
			return;
		}
		golem.setDerp(true);
		Entity existing = headId == null ? null : Bukkit.getEntity(headId);
		if (existing instanceof BlockDisplay head && head.isValid()) {
			head.setBlock(block.createBlockData());
			return;
		}
		BlockDisplay head = golem.getWorld().spawn(golem.getLocation(), BlockDisplay.class, display -> {
			display.setBlock(block.createBlockData());
			display.setTransformation(new Transformation(new Vector3f(-HEAD_SIZE / 2, HEAD_LIFT, -HEAD_SIZE / 2), new AxisAngle4f(), new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new AxisAngle4f()));
			display.setTeleportDuration(1);
			display.setPersistent(false);
		});
		golem.addPassenger(head);
		headId = head.getUniqueId();
	}

	@Override
	protected void installGoals(Mob mob) {
		Bukkit.getMobGoals().addGoal(mob, 1, new NearestTargetGoal(mob, ACQUIRE_RADIUS, true, RESCAN_TICKS, this::isEnemy));
		Bukkit.getMobGoals().addGoal(mob, 2, new RangedAttackGoal(mob, 0, RANGE, cooldownTicks, false, MARCH_SPEED, Volley.innate()));
		Bukkit.getMobGoals().addGoal(mob, 3, new WaypointWalkGoal(mob, lane.ahead(mob.getLocation()), MARCH_SPEED, ARRIVE_DISTANCE));
	}

	@Override
	public void onHit(EntityDamageByEntityEvent evt, LivingEntity victim) {
		if (victim instanceof Player player) {
			hitOnPlayer.apply(this, evt, player);
		} else {
			evt.setDamage(SNOWBALL_DAMAGE_TO_MINIONS);
		}
	}

	@Override
	public void onMinionDeath(EntityDeathEvent evt, Player killer) {
		removeHead();
	}

	@Override
	public void remove() {
		removeHead();
		super.remove();
	}

	/** The head block is a separate entity riding the golem; it goes when the golem goes. */
	private void removeHead() {
		if (headId == null) return;
		Entity head = Bukkit.getEntity(headId);
		if (head != null) head.remove();
		headId = null;
	}
}
