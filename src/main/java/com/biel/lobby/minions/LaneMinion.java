package com.biel.lobby.minions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.biel.BielAPI.ai.MeleeAttackGoal;
import com.biel.BielAPI.ai.NearestTargetGoal;
import com.biel.BielAPI.ai.WaypointWalkGoal;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Catalan;
import com.biel.lobby.utilities.PaperMessages;

/**
 * A minion that marches its team's lane toward the enemy base and fights what it meets
 * hand to hand: it joins the lane on the leg nearest to where it is enlisted and walks
 * the rest ({@link Lane#ahead}), picks the nearest enemy within its kind's radius,
 * pursues and hits it, and goes back to the march when nothing is left. The body and
 * the numbers are the {@link LaneMinionKind}; what a hit on a player is worth is the
 * game's rule, passed in. An owned one credits its kills to the owner like a snowman.
 */
public final class LaneMinion extends Minion {
	private static final int RESCAN_TICKS = 10;
	private static final double FOLLOW_RANGE = 48;
	private static final double ARRIVE_DISTANCE = 3;

	/** What a blow from this minion does to an enemy player. */
	@FunctionalInterface
	public interface MeleeHit {
		void apply(LaneMinion minion, EntityDamageByEntityEvent evt, Player victim);
	}

	private final LaneMinionKind kind;
	private final Lane lane;
	private final MeleeHit hitOnPlayer;

	public LaneMinion(JocEquips game, Equip team, Player owner, LaneMinionKind kind, Lane lane, MeleeHit hitOnPlayer) {
		super(game, team, owner);
		this.kind = kind;
		this.lane = lane;
		this.hitOnPlayer = hitOnPlayer;
	}

	public LaneMinionKind kind() {
		return kind;
	}

	@Override
	protected Mob spawnBody(Location at) {
		Mob mob = kind.body().apply(at);
		mob.getAttribute(Attribute.MAX_HEALTH).setBaseValue(kind.maxHealth());
		mob.setHealth(kind.maxHealth());
		if (mob.getAttribute(Attribute.ATTACK_DAMAGE) == null) mob.registerAttribute(Attribute.ATTACK_DAMAGE);
		mob.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(kind.attackDamage());
		mob.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
		String owned = ownerName() == null ? "" : " " + Catalan.de(ownerName());
		mob.customName(PaperMessages.legacy(team().getChatColor() + kind.label() + owned));
		mob.setCustomNameVisible(true);
		return mob;
	}

	@Override
	protected void installGoals(Mob mob) {
		Bukkit.getMobGoals().addGoal(mob, 1, new NearestTargetGoal(mob, kind.acquireRadius(), true, RESCAN_TICKS, this::isEnemy));
		Bukkit.getMobGoals().addGoal(mob, 2, new MeleeAttackGoal(mob, kind.reach(), kind.acquireRadius(), kind.meleeCooldownTicks(), kind.speed()));
		Bukkit.getMobGoals().addGoal(mob, 3, new WaypointWalkGoal(mob, lane.ahead(mob.getLocation()), kind.speed(), ARRIVE_DISTANCE));
	}

	@Override
	public void onHit(EntityDamageByEntityEvent evt, LivingEntity victim) {
		if (victim instanceof Player player) hitOnPlayer.apply(this, evt, player);
	}
}
