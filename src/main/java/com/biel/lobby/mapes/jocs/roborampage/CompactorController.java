package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.jocs.roborampage.utils.CompactorCycle;
import com.biel.lobby.mapes.jocs.roborampage.utils.CompactorCycle.Phase;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

/** Match-owned Compactor: committed warnings, grounded shockwaves and visible recovery. */
final class CompactorController implements AutoCloseable {
    private static final double RAW_SLAM_DAMAGE = 30; // Existing robot scaling makes this 6 before armor.
    private final World world;
    private final Supplier<List<Player>> participants;
    private final Predicate<Player> activeParticipant;
    private final Predicate<UUID> energized;
    private final BiConsumer<Mob, Location> throwMetal;
    private final ScaffoldController scaffolds;
    private final BiFunction<Player, MessageKey, Component> localize;
    private final Map<Player, BossBar> bars = new HashMap<>();
    private IronGolem boss;
    private CompactorCycle cycle;
    private double maximumHealth;
    private Location committedOrigin;
    private Location committedAim;
    private UUID committedTarget;
    private boolean applyingSlam;
    private boolean enraged;
    private int ticks;

    CompactorController(World world, Supplier<List<Player>> participants, Predicate<Player> activeParticipant,
            Predicate<UUID> energized, BiConsumer<Mob, Location> throwMetal, ScaffoldController scaffolds) {
        this(world, participants, activeParticipant, energized, throwMetal, scaffolds,
                (player, key) -> Messages.component(player, key));
    }

    CompactorController(World world, Supplier<List<Player>> participants, Predicate<Player> activeParticipant,
            Predicate<UUID> energized, BiConsumer<Mob, Location> throwMetal, ScaffoldController scaffolds,
            BiFunction<Player, MessageKey, Component> localize) {
        this.localize = localize;
        this.world = world;
        this.participants = participants;
        this.activeParticipant = activeParticipant;
        this.energized = energized;
        this.throwMetal = throwMetal;
        this.scaffolds = scaffolds;
    }

    void attach(IronGolem golem, double health) {
        clear();
        boss = golem;
        maximumHealth = health;
        cycle = new CompactorCycle();
        enraged = false;
        ticks = 0;
    }

    boolean owns(UUID entityId) { return boss != null && boss.getUniqueId().equals(entityId); }
    boolean isApplyingSlam() { return applyingSlam; }
    double incomingDamageMultiplier() { return cycle == null ? 1 : cycle.incomingDamageMultiplier(); }

    void tick() {
        if (boss == null) return;
        if (!boss.isValid() || boss.isDead() || boss.getWorld() != world) {
            clear();
            return;
        }
        ticks++;
        List<Player> players = participants.get().stream().filter(activeParticipant).toList();
        Player target = players.stream()
                .min(Comparator.comparingDouble(player -> player.getLocation().distanceSquared(boss.getLocation())))
                .orElse(null);
        boolean nowEnraged = boss.getHealth() <= maximumHealth / 2;
        if (nowEnraged && !enraged) {
            enraged = true;
            for (Player player : players) player.sendMessage(localize.apply(player, MessageKey.ROBO_RAMPAGE_BOSS_ENRAGED));
            world.playSound(boss.getLocation(), Sound.ENTITY_IRON_GOLEM_HURT, 1.6F, 0.5F);
        }
        boolean nearby = target != null && horizontalDistanceSquared(target.getLocation(), boss.getLocation())
                <= CompactorCycle.SLAM_RADIUS * CompactorCycle.SLAM_RADIUS
                && Math.abs(target.getLocation().getY() - boss.getLocation().getY()) <= 1.5;
        boolean targetAvailable = target != null;
        if (cycle.windingUp()) {
            targetAvailable = players.stream().anyMatch(player -> player.getUniqueId().equals(committedTarget));
        }
        var action = cycle.tick(targetAvailable, nearby, energized.test(boss.getUniqueId()), enraged);
        boss.setAI(cycle.phase() == Phase.APPROACH);
        if (cycle.phase() == Phase.APPROACH && target != null && ticks % 10 == 0) boss.setTarget(target);
        switch (action) {
            case WARN_SLAM, WARN_THROW -> {
                committedOrigin = boss.getLocation();
                committedAim = target.getEyeLocation();
                committedTarget = target.getUniqueId();
                boss.setVelocity(new Vector(0, boss.getVelocity().getY(), 0));
                world.playSound(committedOrigin, Sound.BLOCK_PISTON_EXTEND, 1.5F, 0.5F);
            }
            case SLAM -> slam(players);
            case THROW -> throwMetal.accept(boss, committedAim.clone());
            case STUN -> {
                world.playSound(boss.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.3F, 0.6F);
                world.spawnParticle(Particle.ELECTRIC_SPARK, boss.getEyeLocation(), 30, 0.7, 0.7, 0.7, 0.08);
            }
            case NONE -> { }
        }
        if (cycle.windingUp() && ticks % 3 == 0) drawWarning();
        if (cycle.exposed() && ticks % 5 == 0) {
            world.spawnParticle(Particle.WAX_OFF, boss.getEyeLocation(), 10, 0.6, 0.5, 0.6, 0.02);
        }
        if (ticks % 5 == 0) updateBars(players);
    }

    private void drawWarning() {
        if (cycle.phase() == Phase.SLAM_WINDUP) {
            for (int point = 0; point < 32; point++) {
                double angle = Math.PI * 2 * point / 32;
                Location marker = committedOrigin.clone().add(Math.cos(angle) * CompactorCycle.SLAM_RADIUS,
                        0.15, Math.sin(angle) * CompactorCycle.SLAM_RADIUS);
                world.spawnParticle(Particle.FLAME, marker, 1, 0, 0, 0, 0);
            }
        } else {
            world.spawnParticle(Particle.CRIT, committedAim, 14, 0.35, 0.4, 0.35, 0.015);
            Vector direction = committedAim.toVector().subtract(boss.getEyeLocation().toVector());
            int points = Math.min(24, (int) Math.ceil(direction.length()));
            for (int point = 0; point <= points; point++) {
                Location marker = boss.getEyeLocation().add(direction.clone().multiply(point / (double) Math.max(1, points)));
                world.spawnParticle(Particle.ELECTRIC_SPARK, marker, 1, 0, 0, 0, 0);
            }
        }
    }

    private void slam(List<Player> players) {
        world.playSound(committedOrigin, Sound.BLOCK_ANVIL_LAND, 1.8F, 0.5F);
        world.spawnParticle(Particle.EXPLOSION, committedOrigin.clone().add(0, 0.2, 0), 12, 2, 0.1, 2, 0);
        applyingSlam = true;
        try {
            for (Player player : players) {
                if (!player.isOnGround() || !boss.hasLineOfSight(player)
                        || Math.abs(player.getLocation().getY() - committedOrigin.getY()) > 1.5
                        || horizontalDistanceSquared(player.getLocation(), committedOrigin)
                                > CompactorCycle.SLAM_RADIUS * CompactorCycle.SLAM_RADIUS) continue;
                player.damage(RAW_SLAM_DAMAGE, boss);
                Vector away = player.getLocation().toVector().subtract(committedOrigin.toVector()).setY(0);
                if (away.lengthSquared() < 0.01) away = new Vector(1, 0, 0);
                player.setVelocity(away.normalize().multiply(0.65).setY(0.3));
            }
        } finally {
            applyingSlam = false;
        }
        scaffolds.collapseNear(committedOrigin, CompactorCycle.SLAM_RADIUS);
    }

    private void updateBars(List<Player> players) {
        Set<Player> present = new HashSet<>(players);
        for (Player player : new ArrayList<>(bars.keySet())) {
            if (!present.contains(player)) player.hideBossBar(bars.remove(player));
        }
        MessageKey key = switch (cycle.phase()) {
            case SLAM_WINDUP -> MessageKey.ROBO_RAMPAGE_BOSS_BAR_SLAM;
            case THROW_WINDUP -> MessageKey.ROBO_RAMPAGE_BOSS_BAR_THROW;
            case RECOVERY, STUNNED -> MessageKey.ROBO_RAMPAGE_BOSS_BAR_EXPOSED;
            case APPROACH -> enraged ? MessageKey.ROBO_RAMPAGE_BOSS_BAR_ENRAGED : MessageKey.ROBO_RAMPAGE_BOSS_BAR;
        };
        BossBar.Color color = cycle.exposed() ? BossBar.Color.GREEN
                : cycle.windingUp() ? BossBar.Color.YELLOW : BossBar.Color.RED;
        float progress = (float) Math.clamp(boss.getHealth() / maximumHealth, 0, 1);
        for (Player player : players) {
            BossBar bar = bars.computeIfAbsent(player, viewer -> {
                BossBar created = BossBar.bossBar(localize.apply(viewer, key), progress, color, BossBar.Overlay.NOTCHED_10);
                viewer.showBossBar(created);
                return created;
            });
            bar.name(localize.apply(player, key)).progress(progress).color(color);
        }
    }

    private static double horizontalDistanceSquared(Location first, Location second) {
        double x = first.getX() - second.getX(), z = first.getZ() - second.getZ();
        return x * x + z * z;
    }

    void clear() {
        if (boss != null && boss.isValid() && !boss.isDead()) boss.setAI(true);
        for (var entry : bars.entrySet()) entry.getKey().hideBossBar(entry.getValue());
        bars.clear();
        boss = null;
        cycle = null;
        committedOrigin = null;
        committedAim = null;
        committedTarget = null;
        applyingSlam = false;
    }

    @Override public void close() { clear(); }
}
