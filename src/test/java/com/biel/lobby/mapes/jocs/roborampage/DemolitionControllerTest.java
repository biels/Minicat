package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Consumer;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DemolitionControllerTest {
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
    void recoveryAndBoundsDoNotAllowArbitraryTerrainChanges() {
        Fixture f = new Fixture();
        Block charge = f.block(0, 1, Material.TNT);
        assertFalse(f.place(charge).isCancelled());
        BlockBreakEvent recovery = new BlockBreakEvent(charge, f.player);
        recovery.setCancelled(true);
        f.controller.handleBreak(recovery, charge);
        assertFalse(recovery.isCancelled());
        f.ignite(charge);
        assertTrue(f.spawned.isEmpty(), "recovered TNT cannot also be ignited");
        assertTrue(f.place(f.block(8, 1, Material.TNT)).isCancelled());
        assertTrue(f.place(f.block(0, 50, Material.TNT)).isCancelled());
        f.active = false;
        assertTrue(f.place(f.block(1, 1, Material.TNT)).isCancelled());
    }

    @Test
    void ignitionHasOneStrongFuseAndNeverHarmsAnyPlayer() {
        Fixture f = new Fixture();
        Block charge = f.block(0, 1, Material.TNT);
        f.place(charge);
        assertTrue(f.ignite(charge).isCancelled());
        f.ignite(charge);
        assertEquals(1, f.spawned.size());
        Charge tnt = f.spawned.getFirst();
        assertEquals(80, tnt.fuse);
        assertEquals(4.0F, tnt.power);
        assertSame(f.player, tnt.source);
        assertEquals(Material.AIR, charge.getType());
        assertFalse(f.controller.mayDamage(tnt.entity, f.player, false));
        assertFalse(f.controller.mayDamage(tnt.entity, f.player, true), "players remain safe even if misclassified");
        assertTrue(f.controller.mayDamage(tnt.entity, stub(Mob.class, (m, a) -> null), true));
        assertFalse(f.controller.mayDamage(tnt.entity, f.teammate, false));
        assertFalse(f.controller.mayDamage(tnt.entity, stub(Item.class, (m, a) -> null), false));
        assertEquals(18, f.controller.adjustedRobotDamage(12));
        f.active = false;
        assertFalse(f.controller.mayDamage(tnt.entity, f.player, false));
    }

    @Test
    void blastLaunchesOnlyActiveExposedPlayersAndTrackedRobotsAfterNativeKnockback() {
        Fixture f = new Fixture();
        Body robot = f.body(Mob.class, 2, true);
        Body helper = f.body(Mob.class, 2, false);
        Body spectator = f.body(Player.class, 2, false);
        spectator.gameMode = GameMode.SPECTATOR;
        Body inactivePlayer = f.body(Player.class, 2, false);
        inactivePlayer.active = false;
        Body outsideRadius = f.body(Mob.class, 9, true);
        Body player = f.body(Player.class, 3, false);
        f.nearby.addAll(List.of(robot.entity, helper.entity, spectator.entity,
                inactivePlayer.entity, outsideRadius.entity, player.entity));
        f.explodePlacedCharge();
        assertEquals(2, f.pendingLaunches.size());
        assertEquals(0, player.launches, "launch must wait until native explosion processing finishes");
        robot.velocity = new Vector(0.2, 3, 0.1);
        player.velocity = new Vector(0.3, -1, 0.2);
        f.flushLaunches();
        assertEquals(1, player.launches);
        assertEquals(1, robot.launches);
        assertTrue(player.velocity.getY() > 0.55 && player.velocity.getY() <= 0.9);
        assertTrue(robot.velocity.getY() <= 0.9, "existing vertical impulses do not stack");
        assertEquals(0.3, player.velocity.getX());
        assertEquals(0, player.fallDistance);
        assertTrue(f.controller.consumeBlastFallProtection((Player) player.entity));
        assertFalse(f.controller.consumeBlastFallProtection((Player) player.entity));
        assertEquals(0, helper.launches);
        assertEquals(0, spectator.launches);
        assertEquals(0, inactivePlayer.launches);
        assertEquals(0, outsideRadius.launches);
    }

    @Test
    void wallsCancellationAndMatchCleanupPreventLaunches() {
        Fixture blocked = new Fixture();
        Body sheltered = blocked.body(Mob.class, 2, true);
        blocked.nearby.add(sheltered.entity);
        blocked.sheltered = true;
        blocked.explodePlacedCharge();
        blocked.flushLaunches();
        assertEquals(0, sheltered.launches);

        Fixture closing = new Fixture();
        Body player = closing.body(Player.class, 2, false);
        closing.nearby.add(player.entity);
        closing.explodePlacedCharge();
        assertEquals(1, closing.pendingLaunches.size());
        closing.controller.close();
        closing.flushLaunches();
        assertEquals(0, player.launches);
        assertFalse(closing.controller.consumeBlastFallProtection((Player) player.entity));

        Fixture leaving = new Fixture();
        Body departed = leaving.body(Player.class, 2, false);
        leaving.nearby.add(departed.entity);
        leaving.explodePlacedCharge();
        departed.active = false;
        leaving.flushLaunches();
        assertEquals(0, departed.launches);

        Fixture cancelled = new Fixture();
        Body spared = cancelled.body(Player.class, 2, false);
        cancelled.nearby.add(spared.entity);
        EntityExplodeEvent blast = cancelled.explodePlacedCharge();
        blast.setCancelled(true); // A later listener cancels after the launch was queued.
        cancelled.flushLaunches();
        assertEquals(0, spared.launches);
    }

    @Test
    void blastFallProtectionExpiresAndIsClearedWhenPlayerLeaves() {
        Fixture f = new Fixture();
        Body player = f.body(Player.class, 2, false);
        f.nearby.add(player.entity);
        f.explodePlacedCharge();
        f.flushLaunches();
        f.gameTime = 160;
        assertFalse(f.controller.consumeBlastFallProtection((Player) player.entity));
        f.explodePlacedCharge();
        f.flushLaunches();
        f.controller.forgetPlayer(player.entity.getUniqueId());
        assertFalse(f.controller.consumeBlastFallProtection((Player) player.entity));
    }

    @Test
    void blastChainsChargesAndCollapsesScaffoldsWhilePreservingScrap() {
        Fixture f = new Fixture();
        Block first = f.block(0, 1, Material.TNT);
        Block second = f.block(1, 1, Material.TNT);
        Block scrap = f.block(2, 1, Material.IRON_BLOCK);
        Block scaffold = f.block(3, 1, Material.SCAFFOLDING);
        Block upperScaffold = f.block(3, 2, Material.SCAFFOLDING);
        f.place(first); f.place(second);
        f.scaffolds.handlePlacement(f.placement(scaffold), scaffold);
        f.scaffolds.handlePlacement(f.placement(upperScaffold), upperScaffold);
        f.ignite(first);
        EntityExplodeEvent blast = new EntityExplodeEvent(f.spawned.getFirst().entity,
                first.getLocation(), new ArrayList<>(List.of(second, scrap, scaffold)), 1, ExplosionResult.DESTROY);
        f.controller.handleExplosion(blast);
        assertTrue(blast.blockList().isEmpty());
        assertEquals(Material.IRON_BLOCK, scrap.getType());
        assertEquals(Material.AIR, scaffold.getType());
        assertEquals(Material.AIR, upperScaffold.getType());
        assertEquals(2, f.spawned.size());
        assertEquals(20, f.spawned.get(1).fuse);
        assertSame(f.player, f.spawned.get(1).source);
        f.controller.close();
        f.controller.close();
        assertTrue(f.spawned.stream().allMatch(tnt -> !tnt.valid));
    }

    @Test
    void cancellationAndAutomaticIgnitionCannotBypassTheController() {
        Fixture f = new Fixture();
        Block first = f.block(0, 1, Material.TNT);
        Block second = f.block(1, 1, Material.TNT);
        f.place(first); f.place(second); f.ignite(first);
        TNTPrimeEvent automatic = new TNTPrimeEvent(second, TNTPrimeEvent.PrimeCause.REDSTONE, null, null);
        f.controller.preventAutomaticIgnition(automatic);
        assertTrue(automatic.isCancelled());
        EntityExplodeEvent cancelled = new EntityExplodeEvent(f.spawned.getFirst().entity,
                first.getLocation(), new ArrayList<>(List.of(second)), 1, ExplosionResult.DESTROY);
        cancelled.setCancelled(true);
        f.controller.handleExplosion(cancelled);
        assertEquals(1, f.spawned.size());
        assertEquals(Material.TNT, second.getType());
        f.controller.close();
        assertEquals(Material.AIR, second.getType());
    }

    private static class Charge {
        final UUID id = UUID.randomUUID();
        Entity source;
        int fuse;
        float power;
        boolean valid = true;
        final TNTPrimed entity = stub(TNTPrimed.class, (method, args) -> switch (method) {
            case "getUniqueId" -> id;
            case "getSource" -> source;
            case "setSource" -> { source = (Entity) args[0]; yield null; }
            case "setFuseTicks" -> { fuse = (int) args[0]; yield null; }
            case "setYield" -> { power = (float) args[0]; yield null; }
            case "isValid" -> valid;
            case "remove" -> { valid = false; yield null; }
            default -> null;
        });
    }

    private static class Body {
        final UUID id = UUID.randomUUID();
        final Entity entity;
        Vector velocity = new Vector();
        float fallDistance = 4;
        int launches;
        boolean active = true;
        GameMode gameMode = GameMode.SURVIVAL;

        Body(Class<? extends Entity> type, World world, int x) {
            entity = stub(type, (method, args) -> switch (method) {
                case "getUniqueId" -> id;
                case "getWorld" -> world;
                case "isValid", "isOnline" -> true;
                case "getLocation" -> new Location(world, x, 1, 0);
                case "getBoundingBox" -> new BoundingBox(x - 0.3, 1, -0.3, x + 0.3, 2.8, 0.3);
                case "getGameMode" -> gameMode;
                case "getVelocity" -> velocity.clone();
                case "setVelocity" -> { velocity = ((Vector) args[0]).clone(); launches++; yield null; }
                case "setFallDistance" -> { fallDistance = (float) args[0]; yield null; }
                default -> null;
            });
        }
    }

    private static class Fixture {
        boolean active = true;
        boolean sheltered;
        long gameTime;
        final List<Charge> spawned = new ArrayList<>();
        final List<Entity> nearby = new ArrayList<>();
        final Set<Entity> robots = new HashSet<>();
        final Map<UUID, Body> bodies = new HashMap<>();
        final List<Runnable> pendingLaunches = new ArrayList<>();
        final Map<String, Block> blocks = new HashMap<>();
        final UUID playerId = UUID.randomUUID();
        final Player player = stub(Player.class, (m, a) -> m.equals("getUniqueId") ? playerId : null);
        final Player teammate = stub(Player.class, (m, a) -> m.equals("getUniqueId") ? UUID.randomUUID() : null);
        @SuppressWarnings("unchecked")
        final World world = stub(World.class, (m, a) -> switch (m) {
            case "spawn" -> {
                Charge charge = new Charge();
                ((Consumer<TNTPrimed>) a[2]).accept(charge.entity);
                spawned.add(charge);
                yield charge.entity;
            }
            case "getBlockAt" -> blocks.get(a[0] + ":" + a[1]);
            case "getNearbyEntities" -> nearby;
            case "getGameTime" -> gameTime;
            case "rayTraceBlocks" -> sheltered ? new RayTraceResult(new Vector(0, 1, 0)) : null;
            default -> null;
        });
        final ScaffoldController scaffolds = new ScaffoldController(world, new Location(world, 0, 1, 0),
                7, () -> 32, List::of, robot -> 1);
        final DemolitionController controller = new DemolitionController(world, new Location(world, 0, 1, 0),
                7, () -> 32, p -> active && (!bodies.containsKey(p.getUniqueId()) || bodies.get(p.getUniqueId()).active),
                scaffolds, robots::contains, pendingLaunches::add);
        Body body(Class<? extends Entity> type, int x, boolean trackedRobot) {
            Body body = new Body(type, world, x);
            bodies.put(body.id, body);
            if (trackedRobot) robots.add(body.entity);
            return body;
        }
        void flushLaunches() {
            new ArrayList<>(pendingLaunches).forEach(Runnable::run);
            pendingLaunches.clear();
        }
        EntityExplodeEvent explodePlacedCharge() {
            Block block = block(0, 1, Material.TNT);
            place(block);
            ignite(block);
            EntityExplodeEvent blast = new EntityExplodeEvent(spawned.getLast().entity,
                    block.getLocation(), new ArrayList<>(), 1, ExplosionResult.DESTROY);
            controller.handleExplosion(blast);
            return blast;
        }
        Block block(int x, int y, Material initialType) {
            Material[] type = {initialType};
            Block block = stub(Block.class, (m, a) -> switch (m) {
                case "getType" -> type[0];
                case "setType" -> { type[0] = (Material) a[0]; yield null; }
                case "getWorld" -> world;
                case "getX" -> x;
                case "getY" -> y;
                case "getZ" -> 0;
                case "getLocation" -> new Location(world, x, y, 0);
                default -> null;
            });
            blocks.put(x + ":" + y, block);
            return block;
        }
        BlockPlaceEvent placement(Block block) {
            BlockState air = stub(BlockState.class, (m, a) -> m.equals("getType") ? Material.AIR : null);
            return new BlockPlaceEvent(block, air, block, null, player, true, EquipmentSlot.HAND);
        }
        BlockPlaceEvent place(Block block) {
            BlockPlaceEvent event = placement(block);
            event.setCancelled(true);
            controller.handlePlacement(event, block);
            return event;
        }
        PlayerInteractEvent ignite(Block block) {
            PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK,
                    null, block, BlockFace.UP, EquipmentSlot.HAND) {
                @Override public Material getMaterial() { return Material.FLINT_AND_STEEL; }
            };
            controller.handleInteraction(event, player);
            return event;
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
