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
    void ignitionHasOneFuseAndOnlyHarmsRobotsAndItsActiveIgniter() {
        Fixture f = new Fixture();
        Block charge = f.block(0, 1, Material.TNT);
        f.place(charge);
        assertTrue(f.ignite(charge).isCancelled());
        f.ignite(charge);
        assertEquals(1, f.spawned.size());
        Charge tnt = f.spawned.getFirst();
        assertEquals(80, tnt.fuse);
        assertSame(f.player, tnt.source);
        assertEquals(Material.AIR, charge.getType());
        assertTrue(f.controller.mayDamage(tnt.entity, f.player, false));
        assertTrue(f.controller.mayDamage(tnt.entity, stub(Mob.class, (m, a) -> null), true));
        assertFalse(f.controller.mayDamage(tnt.entity, f.teammate, false));
        assertFalse(f.controller.mayDamage(tnt.entity, stub(Item.class, (m, a) -> null), false));
        f.active = false;
        assertFalse(f.controller.mayDamage(tnt.entity, f.player, false));
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
        boolean valid = true;
        final TNTPrimed entity = stub(TNTPrimed.class, (method, args) -> switch (method) {
            case "getUniqueId" -> id;
            case "getSource" -> source;
            case "setSource" -> { source = (Entity) args[0]; yield null; }
            case "setFuseTicks" -> { fuse = (int) args[0]; yield null; }
            case "isValid" -> valid;
            case "remove" -> { valid = false; yield null; }
            default -> null;
        });
    }

    private static class Fixture {
        boolean active = true;
        final List<Charge> spawned = new ArrayList<>();
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
            default -> null;
        });
        final ScaffoldController scaffolds = new ScaffoldController(world, new Location(world, 0, 1, 0),
                7, () -> 32, List::of, robot -> 1);
        final DemolitionController controller = new DemolitionController(world, new Location(world, 0, 1, 0),
                7, () -> 32, p -> active, scaffolds);
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
