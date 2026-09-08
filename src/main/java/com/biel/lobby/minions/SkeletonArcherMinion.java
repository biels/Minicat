package com.biel.lobby.minions;

import java.util.EnumSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import com.biel.BielAPI.ai.NearestTargetGoal;
import com.biel.BielAPI.ai.RangedAttackGoal;
import com.biel.BielAPI.ai.Volley;
import com.biel.BielAPI.ai.WaypointWalkGoal;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Catalan;
import com.biel.lobby.utilities.PaperMessages;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

/** A lane archer with an owner, fixed equipment and room-seeking ranged combat. */
public final class SkeletonArcherMinion extends Minion {
    public static final int TEAM_CAP = 10;
    public static final double HEALTH = 20;
    public static final double DAMAGE = 3;
    public static final double RANGE = 14;
    public static final int SHOT_INTERVAL_TICKS = 40;
    private final Lane lane;
    private final ItemStack[] armor;
    private int nextShotTick;

    public SkeletonArcherMinion(JocEquips game, Equip team, Player owner, Lane lane, boolean armored) {
        super(game, team, owner);
        this.lane = lane;
        armor = armorSnapshot(owner.getInventory().getArmorContents(), armored);
    }

    public static ItemStack[] armorSnapshot(ItemStack[] worn, boolean armored) {
        ItemStack[] result = new ItemStack[worn.length];
        if (armored) for (int slot = 0; slot < worn.length; slot++) {
            ItemStack item = worn[slot];
            if (item != null && !item.getType().isAir()) result[slot] = new ItemStack(item.getType());
        }
        return result;
    }

    @Override
    protected Mob spawnBody(Location at) {
        Skeleton skeleton = at.getWorld().spawn(at, Skeleton.class);
        skeleton.setShouldBurnInDay(false);
        skeleton.getAttribute(Attribute.MAX_HEALTH).setBaseValue(HEALTH);
        skeleton.setHealth(HEALTH);
        skeleton.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(48);
        var equipment = skeleton.getEquipment();
        equipment.setArmorContents(armor);
        equipment.setItemInMainHand(new ItemStack(Material.BOW));
        equipment.setItemInOffHand(null);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHandDropChance(0);
        equipment.setHelmetDropChance(0);
        equipment.setChestplateDropChance(0);
        equipment.setLeggingsDropChance(0);
        equipment.setBootsDropChance(0);
        skeleton.customName(PaperMessages.legacy(team().getChatColor() + "Arquer ossi " + Catalan.de(ownerName())));
        skeleton.setCustomNameVisible(true);
        nextShotTick = skeleton.getTicksLived() + 10 + Math.floorMod(skeleton.getEntityId(), 20);
        return skeleton;
    }

    @Override
    protected void installGoals(Mob mob) {
        var goals = Bukkit.getMobGoals();
        goals.addGoal(mob, 1, new NearestTargetGoal(mob, RANGE + 4, true, 10, this::isEnemy));
        goals.addGoal(mob, 2, new NearestTargetGoal(mob, RANGE + 4, true, 10, this::isStrayHostile));
        goals.addGoal(mob, 3, new RetreatGoal(mob));
        goals.addGoal(mob, 4, new RangedAttackGoal(mob, 0, RANGE, SHOT_INTERVAL_TICKS, false, 1, this::shoot));
        goals.addGoal(mob, 5, new WaypointWalkGoal(mob, lane.ahead(mob.getLocation()), 1, 3));
    }

    private void shoot(Mob shooter, LivingEntity target) {
        if (shooter.getTicksLived() < nextShotTick || !mayTarget(target) || !shooter.hasLineOfSight(target)) return;
        if (shooter.getLocation().distanceSquared(target.getLocation()) > RANGE * RANGE) return;
        Volley.innate().fire(shooter, target);
        nextShotTick = shooter.getTicksLived() + SHOT_INTERVAL_TICKS;
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, LivingEntity victim) { event.setDamage(DAMAGE); }

    /** Pathfinding checks reachability; the ground checks reject water, fire and cliff endpoints. */
    private final class RetreatGoal implements Goal<Mob> {
        private final Mob mob;
        private int nextRepathTick;
        private int retreatUntilTick;
        private int retryAfterTick;
        RetreatGoal(Mob mob) { this.mob = mob; }
        public boolean shouldActivate() {
            LivingEntity target = mob.getTarget();
            return mob.getTicksLived() >= retryAfterTick && target != null && mayTarget(target)
                    && mob.getLocation().distanceSquared(target.getLocation()) < 16;
        }
        public boolean shouldStayActive() {
            LivingEntity target = mob.getTarget();
            return mob.getTicksLived() < retreatUntilTick && target != null && target.isValid() && !target.isDead()
                    && mayTarget(target) && mob.getLocation().distanceSquared(target.getLocation()) < 36;
        }
        public void start() { nextRepathTick = 0; retreatUntilTick = mob.getTicksLived() + 80; }
        public void stop() { mob.getPathfinder().stopPathfinding(); retryAfterTick = mob.getTicksLived() + 10; }
        public void tick() {
            LivingEntity target = mob.getTarget();
            if (target == null) return;
            mob.lookAt(target);
            shoot(mob, target);
            if (mob.getTicksLived() < nextRepathTick) return;
            nextRepathTick = mob.getTicksLived() + 10;
            Location origin = mob.getLocation();
            Vector away = origin.toVector().subtract(target.getLocation().toVector()).setY(0);
            if (away.lengthSquared() < 0.01) away = new Vector(1, 0, 0);
            away.normalize().multiply(4);
            for (double angle : new double[]{0, Math.PI / 3, -Math.PI / 3, Math.PI / 2, -Math.PI / 2}) {
                Location candidate = origin.clone().add(away.clone().rotateAroundY(angle));
                for (int dy : new int[]{0, 1, -1}) {
                    Location feet = candidate.clone().add(0, dy, 0);
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
        public GoalKey<Mob> getKey() { return GoalKey.of(Mob.class, new NamespacedKey("minicat", "archer_retreat")); }
        public EnumSet<GoalType> getTypes() { return EnumSet.of(GoalType.MOVE, GoalType.LOOK); }
    }
}
