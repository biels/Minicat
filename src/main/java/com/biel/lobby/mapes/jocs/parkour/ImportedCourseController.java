package com.biel.lobby.mapes.jocs.parkour;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.jocs.parkour.utils.CourseProfile;
import com.biel.lobby.mapes.jocs.parkour.utils.CourseRun;

/** Owns one imported world's runners; the game supplies roster and scoring hooks. */
final class ImportedCourseController {
    private final World world;
    private final CourseProfile profile;
    private final Predicate<Player> activeRunner;
    private final Consumer<Player> onCheckpoint;
    private final Consumer<Player> onFailure;
    private final BiConsumer<Player, Long> onFinish;
    private final Map<String, CourseRun> runs = new HashMap<>();
    private long currentTick;

    ImportedCourseController(World world, Predicate<Player> activeRunner, Consumer<Player> onCheckpoint,
                             Consumer<Player> onFailure, BiConsumer<Player, Long> onFinish) {
        this(world, CourseProfile.load(world.getWorldFolder().toPath().resolve(CourseProfile.FILE_NAME)),
                activeRunner, onCheckpoint, onFailure, onFinish);
    }

    ImportedCourseController(World world, CourseProfile profile, Predicate<Player> activeRunner,
                             Consumer<Player> onCheckpoint, Consumer<Player> onFailure,
                             BiConsumer<Player, Long> onFinish) {
        this.world = Objects.requireNonNull(world);
        this.profile = Objects.requireNonNull(profile);
        this.activeRunner = Objects.requireNonNull(activeRunner);
        this.onCheckpoint = Objects.requireNonNull(onCheckpoint);
        this.onFailure = Objects.requireNonNull(onFailure);
        this.onFinish = Objects.requireNonNull(onFinish);
        validateWorld();
    }

    CourseProfile profile() { return profile; }
    CourseRun getRun(String playerName) { return runs.get(playerName); }

    void initializePlayer(Player player) {
        if (!belongsToWorld(player)) return;
        if (runs.containsKey(player.getName())) { resumePlayer(player); return; }
        CourseRun run = new CourseRun(profile.start().entry());
        runs.put(player.getName(), run);
        player.setGameMode(GameMode.ADVENTURE);
        teleport(player, run.returnPosition(), false);
        run.guard(currentTick);
    }

    /** The new Player entity inherits the retained roster seat's course state. */
    void resumePlayer(Player player) {
        if (!belongsToWorld(player)) return;
        CourseRun run = runs.get(player.getName());
        if (run == null) { initializePlayer(player); return; }
        if (run.finished()) { player.setGameMode(GameMode.SPECTATOR); return; }
        player.setGameMode(GameMode.ADVENTURE);
        teleport(player, run.returnPosition(), true);
        run.guard(currentTick);
    }

    void tick(long tick) {
        currentTick = tick;
        for (Player player : world.getPlayers()) {
            CourseRun run = runs.get(player.getName());
            if (!canProcess(player, run) || run.guarded(tick)) continue;
            Location feet = player.getLocation();
            if (failedAt(player, run, feet)) { recover(player); continue; }
            if (!run.started() && touches(profile.start().trigger(), feet)) {
                run.start(tick, profile.start().trigger().safePosition(feet.getYaw(), feet.getPitch()));
            }
            if (!run.started()) continue;
            for (CourseProfile.Checkpoint checkpoint : profile.checkpoints()) {
                if (touches(checkpoint.trigger(), feet)) {
                    if (run.checkpoint(checkpoint, tick)) onCheckpoint.accept(player);
                    break;
                }
            }
            if (profile.finish().contains(feet.getX(), feet.getY(), feet.getZ()) && run.finish(tick))
                onFinish.accept(player, run.elapsedTicks(tick));
        }
    }

    /** Cancels neither the damage nor the game event; the entry class owns those. */
    boolean shouldRecoverDamage(Player player, DamageCause cause) {
        if (!canProcess(player, runs.get(player.getName()))) return false;
        if (fireProtected(player) && (cause == DamageCause.LAVA || cause == DamageCause.FIRE
                || cause == DamageCause.FIRE_TICK || cause == DamageCause.HOT_FLOOR)) return false;
        return profile.failure().damageCauses().contains(cause.name());
    }

    boolean recover(Player player) {
        CourseRun run = runs.get(player.getName());
        if (!canProcess(player, run) || !run.fail(currentTick)) return false;
        teleport(player, run.returnPosition(), true);
        onFailure.accept(player);
        return true;
    }

    void clear() { runs.clear(); }

    private boolean failedAt(Player player, CourseRun run, Location feet) {
        CourseProfile.Failure failure = profile.failure();
        if (failure.contains(feet.getX(), feet.getY(), feet.getZ())
                || failure.belowCheckpoint(feet.getY(), run.returnPosition(), run.maxDrop())) return true;
        // Test feet, the lower contact surface and head. Water is a hazard only
        // when explicitly declared; Spiral's conditional water section is native.
        return hazardous(player, feet.getBlock().getType())
                || hazardous(player, feet.clone().add(0, -.08, 0).getBlock().getType())
                || hazardous(player, feet.clone().add(0, 1, 0).getBlock().getType());
    }

    private boolean hazardous(Player player, Material material) {
        if (fireProtected(player) && (material == Material.LAVA || material == Material.FIRE
                || material == Material.SOUL_FIRE || material == Material.MAGMA_BLOCK)) return false;
        return profile.failure().hazardMaterials().contains(material.name());
    }

    private boolean fireProtected(Player player) { return player.getScoreboardTags().contains("fire_boots"); }

    private boolean touches(CourseProfile.BlockPosition trigger, Location feet) {
        return trigger.touches(feet.getX(), feet.getY(), feet.getZ())
                && isPressurePlate(world.getBlockAt(trigger.x(), trigger.y(), trigger.z()).getType());
    }

    private boolean belongsToWorld(Player player) {
        return player.isOnline() && player.getWorld() == world;
    }

    private boolean canProcess(Player player, CourseRun run) {
        return run != null && !run.finished() && belongsToWorld(player) && !player.isDead()
                && !player.getScoreboardTags().contains("entityspect")
                && player.getGameMode() != GameMode.SPECTATOR && activeRunner.test(player);
    }

    private void teleport(Player player, CourseProfile.Position target, boolean retainFacing) {
        Location current = player.getLocation();
        Location destination = new Location(world, target.x(), target.y(), target.z(),
                retainFacing ? current.getYaw() : target.yaw(), retainFacing ? current.getPitch() : target.pitch());
        player.teleport(destination);
        player.setVelocity(new Vector());
        player.setFallDistance(0);
        player.setFireTicks(0);
    }

    private void validateWorld() {
        validateSafe("course entry", profile.start().entry());
        validatePlate("course start", profile.start().trigger());
        validateSafe("course start", profile.start().trigger().safePosition(0, 0));
        for (CourseProfile.Checkpoint checkpoint : profile.checkpoints()) {
            validatePlate(checkpoint.id(), checkpoint.trigger());
            validateSafe(checkpoint.id(), checkpoint.safePosition());
        }
        for (String materialName : profile.failure().hazardMaterials()) {
            try { Material.valueOf(materialName); }
            catch (IllegalArgumentException exception) { throw invalid("Unknown hazard material " + materialName); }
        }
        for (String causeName : profile.failure().damageCauses()) {
            try { DamageCause.valueOf(causeName); }
            catch (IllegalArgumentException exception) { throw invalid("Unknown damage cause " + causeName); }
        }
        if (profile.failure().minY() < world.getMinHeight() - 1 || profile.failure().minY() >= world.getMaxHeight())
            throw invalid("Failure minY lies outside the imported world");
    }

    private void validatePlate(String id, CourseProfile.BlockPosition trigger) {
        if (!isPressurePlate(world.getBlockAt(trigger.x(), trigger.y(), trigger.z()).getType()))
            throw invalid(id + " trigger is not a pressure plate at " + trigger);
    }

    private void validateSafe(String id, CourseProfile.Position position) {
        int x = (int) Math.floor(position.x()), y = (int) Math.floor(position.y()), z = (int) Math.floor(position.z());
        if (y <= world.getMinHeight() || y + 1 >= world.getMaxHeight()) throw invalid(id + " return is outside world height");
        Block feet = world.getBlockAt(x, y, z), head = world.getBlockAt(x, y + 1, z), support = world.getBlockAt(x, y - 1, z);
        if (!feet.isPassable() || !head.isPassable() || !support.getType().isSolid())
            throw invalid(id + " return requires clear feet/head and solid support at " + position);
    }

    private static boolean isPressurePlate(Material material) { return material.name().endsWith("_PRESSURE_PLATE"); }
    private IllegalArgumentException invalid(String message) {
        return new IllegalArgumentException("Invalid parkour course in " + world.getName() + ": " + message);
    }
}
