package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Input;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

class MobilityAndCutterControllerTest {
    @BeforeAll
    static void registries() throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        sun.misc.Unsafe allocator = (sun.misc.Unsafe) field.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = stub(io.papermc.paper.registry.RegistryAccess.class, (method, args) ->
                method.equals("getRegistry") ? stub(Registry.class, (operation, values) -> {
                    if (!operation.equals("get") && !operation.equals("getOrThrow")) return null;
                    if (args[0].toString().toLowerCase(Locale.ROOT).contains("sound")) return stub(Sound.class, (m, a) -> null);
                    return stub(BlockType.class, (m, a) -> m.equals("isAir") && values[0].toString().equals("minecraft:air"));
                }) : null);
        allocator.putObject(allocator.staticFieldBase(providerField), allocator.staticFieldOffset(providerField), Optional.of(provider));
    }

    @Test
    void sneakJumpHasFixedLiftAndFacingIndependentOfLookPitch() {
        Fixture f = new Fixture();
        f.playerLocation.setYaw(-90);
        f.playerLocation.setPitch(-90);
        f.jump();
        assertEquals(1, f.launches.size());
        assertEquals(0.34, f.launches.getFirst().getX(), 1e-9);
        assertEquals(0.48, f.launches.getFirst().getY(), 1e-9);
        assertEquals(0, f.launches.getFirst().getZ(), 1e-9);
        f.jump();
        assertEquals(1, f.launches.size(), "duplicate event cannot double-launch");
    }

    @Test
    void launchRequiresBothCooldownAndLandingAndCannotResetByRespawning() {
        Fixture f = new Fixture();
        f.jump();
        f.grounded = false;
        f.tickJump(160);
        f.jump();
        assertEquals(1, f.launches.size(), "elapsed cooldown alone cannot enable an air jump");
        f.grounded = true;
        f.tickJump(1);
        f.jump();
        assertEquals(2, f.launches.size());
        f.jumps.onRespawn(f.player);
        f.jump();
        assertEquals(2, f.launches.size(), "respawn preserves the cooldown");
        f.tickJump(157);
        f.jump();
        assertEquals(2, f.launches.size());
        f.tickJump(1);
        f.jump();
        assertEquals(3, f.launches.size());
    }

    @Test
    void ordinaryCancelledAndIneligibleJumpsDoNotBoost() {
        Fixture f = new Fixture();
        f.sneaking = false;
        f.jump();
        f.sneaking = true;
        f.active = false;
        f.jump();
        f.active = true;
        f.dead = true;
        f.jump();
        f.dead = false;
        f.flying = true;
        f.jump();
        f.flying = false;
        PlayerJumpEvent cancelled = f.jumpEvent();
        cancelled.setCancelled(true);
        f.jumps.onJump(cancelled);
        assertTrue(f.launches.isEmpty());
        f.jumps.close();
        f.jumps.close();
        f.jump();
        assertTrue(f.launches.isEmpty(), "closed match cannot launch players");
    }

    @Test
    void queuedJumpWaitsForServerJumpAndRespectsLateCancellationOrTeleport() {
        Fixture f = new Fixture();
        PlayerJumpEvent cancelled = f.jumpEvent();
        f.jumps.onJump(cancelled);
        assertTrue(f.launches.isEmpty());
        cancelled.setCancelled(true);
        f.tickJump(1);
        assertTrue(f.launches.isEmpty());
        f.jumps.onJump(f.jumpEvent());
        f.playerLocation.setX(5);
        f.tickJump(1);
        assertTrue(f.launches.isEmpty(), "a teleport must not receive a stale launch");
    }

    @Test
    void scaffoldTopAcceptsJumpInputWithoutEnablingMidairOrSideBoosts() {
        Fixture f = new Fixture();
        f.scaffold(0, 1);
        f.playerLocation.setY(2);
        f.jumps.onInput(f.input(true));
        f.tickJump(1);
        assertEquals(1, f.launches.size());
        assertEquals(0.54, f.launches.getFirst().getY(), 1e-9);
        f.grounded = false;
        f.playerLocation.setY(1.5);
        f.tickJump(170);
        f.jumps.onInput(f.input(false));
        f.jumps.onInput(f.input(true));
        f.tickJump(1);
        assertEquals(1, f.launches.size());
    }

    @Test
    void cutterWarnsBeforeBreakingAndRecoversBeforeItCanStartAgain() {
        Fixture f = new Fixture();
        Block bottom = f.scaffold(1, 1);
        Block top = f.scaffold(1, 2);
        f.tickSaw(1);
        assertFalse(f.robotAI);
        assertTrue(f.scaffolds.isSawBusy(f.robotId));
        f.tickSaw(23);
        assertEquals(Material.SCAFFOLDING, bottom.getType());
        f.tickSaw(1);
        assertEquals(Material.AIR, bottom.getType());
        assertEquals(Material.AIR, top.getType());
        Block next = f.scaffold(-1, 1);
        f.tickSaw(39);
        assertFalse(f.robotAI);
        assertEquals(Material.SCAFFOLDING, next.getType());
        f.tickSaw(1);
        assertTrue(f.robotAI);
        assertFalse(f.scaffolds.isSawBusy(f.robotId));
    }

    @Test
    void taserInterruptsEvenOnTheStrikeTickAndPreventsNewWindups() {
        Fixture f = new Fixture();
        Block target = f.scaffold(1, 1);
        f.tickSaw(24);
        f.energized = true;
        f.tickSaw(1);
        assertEquals(Material.SCAFFOLDING, target.getType());
        f.tickSaw(40);
        assertTrue(f.robotAI);
        f.tickSaw(40);
        assertFalse(f.scaffolds.isSawBusy(f.robotId));
        assertEquals(Material.SCAFFOLDING, target.getType());
        f.energized = false;
        f.tickSaw(25);
        assertEquals(Material.AIR, target.getType());
    }

    @Test
    void movingOutOfReachOrRemovingTargetCancelsTheCommittedStrike() {
        Fixture f = new Fixture();
        Block target = f.scaffold(1, 1);
        f.tickSaw(1);
        f.robotLocation.setX(6);
        f.tickSaw(24);
        assertEquals(Material.SCAFFOLDING, target.getType());
        f.tickSaw(17);
        assertTrue(f.robotAI);
        f.robotLocation.setX(0);
        f.tickSaw(1);
        target.setType(Material.AIR);
        Block replacement = f.scaffold(-1, 1);
        f.tickSaw(25);
        assertEquals(Material.SCAFFOLDING, replacement.getType(), "no silent retarget during windup");
    }

    @Test
    void ordinaryRobotsKeepThreeHitsOneSecondApart() {
        Fixture f = new Fixture();
        f.scaffoldDamage = 1;
        Block target = f.scaffold(1, 1);
        f.tickSaw(59);
        assertEquals(Material.SCAFFOLDING, target.getType());
        assertTrue(f.robotAI);
        f.tickSaw(1);
        assertEquals(Material.AIR, target.getType());
    }

    @Test
    void cleanupAndRemovedRobotRestoreOriginalAIWithoutDelayedStrike() {
        Fixture f = new Fixture();
        f.robotAI = false;
        f.scaffold(1, 1);
        f.tickSaw(1);
        f.scaffolds.close();
        assertFalse(f.robotAI, "an already disabled AI is not accidentally enabled");
        f.scaffolds.close();
        assertFalse(f.scaffolds.isSawBusy(f.robotId));
        Fixture removed = new Fixture();
        Block target = removed.scaffold(1, 1);
        removed.tickSaw(1);
        removed.robots.clear();
        removed.tickSaw(24);
        assertTrue(removed.robotAI);
        assertFalse(removed.scaffolds.isSawBusy(removed.robotId));
        assertEquals(Material.SCAFFOLDING, target.getType());
    }

    @Test
    void bossSlamCollapsesNearbyTrackedColumnsAndPreservesDistantScaffolds() {
        Fixture f = new Fixture();
        Block base = f.scaffold(1, 1);
        Block top = f.scaffold(1, 4);
        Block distant = f.scaffold(6, 1);
        Block elevated = f.scaffold(2, 5);
        f.scaffolds.collapseNear(new Location(f.world, 0, 1, 0), 3.5);
        assertEquals(Material.AIR, base.getType());
        assertEquals(Material.AIR, top.getType());
        assertEquals(Material.SCAFFOLDING, distant.getType());
        assertEquals(Material.SCAFFOLDING, elevated.getType());
        f.scaffolds.collapseNear(new Location(f.world, 0, 1, 0), 3.5);
        assertEquals(Material.SCAFFOLDING, distant.getType());
    }

    private static final class Fixture {
        final UUID playerId = UUID.randomUUID();
        final UUID robotId = UUID.randomUUID();
        final List<Vector> launches = new ArrayList<>();
        final List<Mob> robots = new ArrayList<>();
        final Map<String, Block> blocks = new HashMap<>();
        boolean active = true, sneaking = true, grounded = true, robotAI = true;
        boolean dead, flying, energized;
        int scaffoldDamage = 3;
        final World world = stub(World.class, this::worldAnswer);
        final Location playerLocation = new Location(world, 0, 1, 0);
        final Location robotLocation = new Location(world, 0, 1, 0);
        final Player player = stub(Player.class, (method, args) -> switch (method) {
            case "getUniqueId" -> playerId;
            case "getWorld" -> world;
            case "getLocation" -> playerLocation.clone();
            case "isOnline" -> true;
            case "isDead" -> dead;
            case "isFlying" -> flying;
            case "isSneaking" -> sneaking;
            case "isOnGround" -> grounded;
            case "setVelocity" -> { launches.add(((Vector) args[0]).clone()); yield null; }
            default -> null;
        });
        final Mob robot = stub(Mob.class, (method, args) -> switch (method) {
            case "getUniqueId" -> robotId;
            case "getWorld" -> world;
            case "isValid" -> true;
            case "getLocation" -> robotLocation.clone();
            case "getVelocity" -> new Vector();
            case "hasAI" -> robotAI;
            case "setAI" -> { robotAI = (boolean) args[0]; yield null; }
            default -> null;
        });
        final PistonJumpController jumps = new PistonJumpController(world, p -> active);
        final ScaffoldController scaffolds = new ScaffoldController(world, new Location(world, 0, 1, 0),
                7, () -> 32, () -> robots, r -> scaffoldDamage, r -> energized);
        Fixture() { robots.add(robot); }
        Object worldAnswer(String method, Object[] args) {
            return switch (method) {
                case "getPlayers" -> List.of(player);
                case "getBlockAt" -> args[0] instanceof Location location
                        ? blocks.get(location.getBlockX() + ":" + location.getBlockY())
                        : blocks.get(args[0] + ":" + args[1]);
                default -> null;
            };
        }
        PlayerJumpEvent jumpEvent() { return new PlayerJumpEvent(player, playerLocation, playerLocation.clone().add(0, 0.42, 0)); }
        void jump() { jumps.onJump(jumpEvent()); jumps.tick(); }
        PlayerInputEvent input(boolean jumping) {
            return new PlayerInputEvent(player, stub(Input.class, (method, args) -> switch (method) {
                case "isSneak" -> true;
                case "isJump" -> jumping;
                default -> null;
            }));
        }
        void tickJump(int ticks) { for (int i = 0; i < ticks; i++) jumps.tick(); }
        void tickSaw(int ticks) { for (int i = 0; i < ticks; i++) scaffolds.tickRobotDamage(); }
        Block scaffold(int x, int y) {
            Material[] type = {Material.SCAFFOLDING};
            Block block = stub(Block.class, (method, args) -> switch (method) {
                case "getType" -> type[0];
                case "setType" -> { type[0] = (Material) args[0]; yield null; }
                case "getWorld" -> world;
                case "getX" -> x;
                case "getY" -> y;
                case "getZ" -> 0;
                case "getLocation" -> new Location(world, x, y, 0);
                default -> null;
            });
            blocks.put(x + ":" + y, block);
            BlockState air = stub(BlockState.class, (method, args) -> method.equals("getType") ? Material.AIR : null);
            assertTrue(scaffolds.handlePlacement(new BlockPlaceEvent(block, air, block, null, player,
                    true, EquipmentSlot.HAND), block));
            return block;
        }
    }

    @FunctionalInterface private interface Answer { Object call(String method, Object[] args); }
    private static <T> T stub(Class<T> type, Answer answer) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("equals")) return proxy == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
            Object value = answer.call(method.getName(), args);
            if (value != null) return value;
            if (method.getReturnType() == boolean.class) return false;
            if (method.getReturnType() == int.class) return 0;
            return null;
        }));
    }
}
