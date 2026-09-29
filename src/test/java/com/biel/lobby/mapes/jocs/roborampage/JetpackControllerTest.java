package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JetpackControllerTest {
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
                    return stub(BlockType.class, (m, a) -> m.equals("isSolid"));
                }) : null);
        allocator.putObject(allocator.staticFieldBase(providerField), allocator.staticFieldOffset(providerField), Optional.of(provider));
    }

    @Test
    void rightClickTogglesFixedLiftAndHorizontalFacingRegardlessOfPitch() {
        Fixture fixture = new Fixture();
        fixture.location.setYaw(-90);
        fixture.location.setPitch(-90);
        assertTrue(fixture.toggle().isCancelled());
        fixture.controller.tick();
        assertEquals(1, fixture.launches.size());
        assertEquals(0.16, fixture.launches.getFirst().getX(), 1e-9);
        assertEquals(0.36, fixture.launches.getFirst().getY(), 1e-9);
        assertEquals(0, fixture.launches.getFirst().getZ(), 1e-9);
        fixture.toggle();
        fixture.controller.tick();
        assertEquals(1, fixture.launches.size());
        assertTrue(fixture.controller.protectsFall(fixture.player));
        assertFalse(fixture.controller.protectsFall(fixture.player));
    }

    @Test
    void onlyTheMainHandJetpackHandlesRightClickAndSwitchingItemsStopsThrust() {
        Fixture fixture = new Fixture();
        PlayerInteractEvent offHand = new PlayerInteractEvent(fixture.player, Action.RIGHT_CLICK_AIR,
                fixture.jetpack, null, null, EquipmentSlot.OFF_HAND);
        assertFalse(fixture.controller.handleInteraction(offHand, fixture.player));
        fixture.toggle();
        fixture.controller.tick();
        fixture.held = null;
        fixture.controller.tick();
        fixture.held = fixture.jetpack;
        fixture.controller.tick();
        assertEquals(1, fixture.launches.size(), "returning to the item does not restart thrust");
    }

    @Test
    void spectatorsOtherWorldsDeadAndInactivePlayersCannotFlyOrKeepProtection() {
        Fixture fixture = new Fixture();
        fixture.gameMode = GameMode.SPECTATOR;
        fixture.toggle();
        fixture.controller.tick();
        fixture.gameMode = GameMode.SURVIVAL;
        fixture.toggle();
        fixture.controller.tick();
        fixture.playerWorld = stub(World.class, (m, a) -> null);
        fixture.controller.tick();
        assertFalse(fixture.controller.protectsFall(fixture.player));
        fixture.playerWorld = fixture.world;
        fixture.dead = true;
        fixture.toggle();
        fixture.controller.tick();
        fixture.dead = false;
        fixture.active = false;
        fixture.toggle();
        fixture.controller.tick();
        assertEquals(1, fixture.launches.size());
    }

    @Test
    void respawnStopsThrustDropIsCancelledAndClosingRemovesMatchItems() {
        Fixture fixture = new Fixture();
        fixture.toggle();
        fixture.controller.tick();
        fixture.controller.onRespawn(fixture.player);
        fixture.controller.tick();
        assertEquals(1, fixture.launches.size());
        assertFalse(fixture.controller.protectsFall(fixture.player));
        PlayerDropItemEvent drop = new PlayerDropItemEvent(fixture.player, fixture.droppedItem);
        fixture.controller.onDrop(drop);
        assertTrue(drop.isCancelled());
        fixture.controller.close();
        fixture.controller.close();
        fixture.controller.tick();
        assertNull(fixture.held);
        assertTrue(fixture.removedDrop);
        assertEquals(1, fixture.launches.size());
        assertFalse(fixture.controller.protectsFall(fixture.player));
    }

    private static final class JetpackItem extends ItemStack {
        final ItemMeta meta = stub(ItemMeta.class, (m, a) -> m.equals("getPersistentDataContainer")
                ? stub(PersistentDataContainer.class, (operation, args) -> operation.equals("has")) : null);
        JetpackItem() { super(); }
        @Override public Material getType() { return Material.FEATHER; }
        @Override public boolean hasItemMeta() { return true; }
        @Override public ItemMeta getItemMeta() { return meta; }
    }

    private static final class Fixture {
        final UUID playerId = UUID.randomUUID();
        final List<Vector> launches = new ArrayList<>();
        final ItemStack jetpack = new JetpackItem();
        ItemStack held = jetpack;
        boolean active = true, dead, removedDrop;
        GameMode gameMode = GameMode.SURVIVAL;
        final World world = stub(World.class, (m, a) -> switch (m) {
            case "getPlayers" -> List.of(player());
            case "getMaxHeight" -> 320;
            case "getEntitiesByClass" -> List.of(droppedItem());
            case "getBlockAt" -> stub(Block.class, (operation, args) -> operation.equals("getType") ? Material.STONE : null);
            default -> null;
        });
        World playerWorld = world;
        final Location location = new Location(world, 0, 1, 0);
        final PlayerInventory inventory = stub(PlayerInventory.class, (m, a) -> switch (m) {
            case "getItemInMainHand", "getItem" -> held;
            case "getSize" -> 1;
            case "setItem" -> { held = (ItemStack) a[1]; yield null; }
            default -> null;
        });
        final Player player = stub(Player.class, (m, a) -> switch (m) {
            case "getUniqueId" -> playerId;
            case "getWorld" -> playerWorld;
            case "getLocation" -> location.clone();
            case "getInventory" -> inventory;
            case "getGameMode" -> gameMode;
            case "isOnline", "isOnGround" -> true;
            case "isDead" -> dead;
            case "setVelocity" -> { launches.add(((Vector) a[0]).clone()); yield null; }
            default -> null;
        });
        final Item droppedItem = stub(Item.class, (m, a) -> switch (m) {
            case "getItemStack" -> jetpack;
            case "remove" -> { removedDrop = true; yield null; }
            default -> null;
        });
        final Plugin plugin = stub(Plugin.class, (m, a) -> switch (m) {
            case "getName" -> "Minicat";
            case "namespace" -> "minicat";
            default -> null;
        });
        final JetpackController controller = new JetpackController(plugin, world, p -> active);
        Player player() { return player; }
        Item droppedItem() { return droppedItem; }
        PlayerInteractEvent toggle() {
            PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR,
                    jetpack, null, null, EquipmentSlot.HAND);
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
