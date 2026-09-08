package com.biel.lobby.minions;

import java.util.EnumSet;
import java.util.function.Predicate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;
import com.biel.BielAPI.ai.Volley;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

/** Makes room for ranged attacks, trying reachable side steps when backing up is blocked. */
final class MinionRetreatGoal implements Goal<Mob> {
    private final Mob mob;
    private final Predicate<LivingEntity> mayTarget;
    private final Volley shoot;
    private int nextRepathTick;
    private int retreatUntilTick;
    private int retryAfterTick;

    MinionRetreatGoal(Mob mob, Predicate<LivingEntity> mayTarget, Volley shoot) {
        this.mob = mob;
        this.mayTarget = mayTarget;
        this.shoot = shoot;
    }

    private boolean validTarget(LivingEntity target) {
        return target != null && target.isValid() && !target.isDead()
                && mob.getWorld().equals(target.getWorld()) && mayTarget.test(target);
    }

    public boolean shouldActivate() {
        LivingEntity target = mob.getTarget();
        return mob.getTicksLived() >= retryAfterTick && validTarget(target)
                && mob.getLocation().distanceSquared(target.getLocation()) < 16;
    }

    public boolean shouldStayActive() {
        LivingEntity target = mob.getTarget();
        return mob.getTicksLived() < retreatUntilTick && validTarget(target)
                && mob.getLocation().distanceSquared(target.getLocation()) < 36;
    }

    public void start() { nextRepathTick = 0; retreatUntilTick = mob.getTicksLived() + 80; }
    public void stop() { mob.getPathfinder().stopPathfinding(); retryAfterTick = mob.getTicksLived() + 10; }

    public void tick() {
        LivingEntity target = mob.getTarget();
        if (!validTarget(target)) return;
        mob.lookAt(target);
        shoot.fire(mob, target);
        if (mob.getTicksLived() < nextRepathTick) return;
        nextRepathTick = mob.getTicksLived() + 10;
        Location origin = mob.getLocation();
        Vector away = origin.toVector().subtract(target.getLocation().toVector()).setY(0);
        if (away.lengthSquared() < 0.01) away = new Vector(1, 0, 0);
        away.normalize().multiply(4);
        for (double angle : new double[]{0, Math.PI / 3, -Math.PI / 3, Math.PI / 2, -Math.PI / 2}) {
            Location candidate = origin.clone().add(away.clone().rotateAroundY(angle));
            for (int heightOffset : new int[]{0, 1, -1}) {
                Location feet = candidate.clone().add(0, heightOffset, 0);
                Material floor = feet.clone().subtract(0, 1, 0).getBlock().getType();
                if (!floor.isSolid() || floor == Material.MAGMA_BLOCK || floor == Material.CACTUS
                        || floor == Material.CAMPFIRE || floor == Material.SOUL_CAMPFIRE) continue;
                if (!feet.getBlock().getType().isAir() || !feet.clone().add(0, 1, 0).getBlock().getType().isAir()) continue;
                var path = mob.getPathfinder().findPath(feet);
                if (path == null || !path.canReachFinalPoint()) continue;
                mob.getPathfinder().moveTo(path, 1.15);
                return;
            }
        }
        // Give the ranged approach goal a turn when retreat has no reachable ground.
        retreatUntilTick = mob.getTicksLived();
    }

    public GoalKey<Mob> getKey() { return GoalKey.of(Mob.class, new NamespacedKey("minicat", "minion_retreat")); }
    public EnumSet<GoalType> getTypes() { return EnumSet.of(GoalType.MOVE, GoalType.LOOK); }
}
