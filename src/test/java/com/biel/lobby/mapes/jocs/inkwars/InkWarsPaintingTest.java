package com.biel.lobby.mapes.jocs.inkwars;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Server-free ownership and transition checks; real Paper verifies the compatible property matrix. */
class InkWarsPaintingTest {
    private static final String[] NEW_FAMILIES = {
            "_WOOL_SLAB", "_WOOL_STAIRS", "_CONCRETE_SLAB", "_CONCRETE_STAIRS"
    };
    private static final String[] OLD_FAMILIES = {
            "_GLAZED_TERRACOTTA", "_STAINED_GLASS_PANE", "_STAINED_GLASS", "_CONCRETE_POWDER",
            "_CONCRETE", "_TERRACOTTA", "_WOOL", "_CARPET"
    };
    private static final Map<BlockData, Data> blockData = new java.util.IdentityHashMap<>();

    @BeforeAll
    static void installServer() throws Exception {
        PluginManager plugins = proxy(PluginManager.class, (object, method, args) -> null);
        var serverField = Bukkit.class.getDeclaredField("server");
        serverField.setAccessible(true);
        serverField.set(null, proxy(Server.class, (object, method, args) -> switch (method.getName()) {
            case "getPluginManager" -> plugins;
            case "getPlayer" -> null; // exercise the actual offline-painter team score path
            case "getLogger" -> Logger.getLogger("InkWarsPaintingTest");
            case "getName", "getVersion", "getBukkitVersion" -> "test";
            case "createBlockData" -> new Data((Material) args[0], Map.of()).proxy;
            default -> null;
        }));
    }

    @Test
    void everySourceAndTargetColorKeepsItsSupportedFamily() throws Exception {
        InkWars game = new InkWars();
        Method mapping = InkWars.class.getDeclaredMethod("getPaintMaterial", Material.class, DyeColor.class);
        mapping.setAccessible(true);
        for (String family : java.util.stream.Stream.concat(java.util.Arrays.stream(OLD_FAMILIES),
                java.util.Arrays.stream(NEW_FAMILIES)).toList()) {
            for (DyeColor source : DyeColor.values()) {
                Material original = Material.valueOf(source.name() + family);
                assertTrue(game.isPaintable(new Surface(original, Map.of()).block), original.name());
                for (DyeColor target : DyeColor.values()) {
                    assertEquals(Material.valueOf(target.name() + family), mapping.invoke(game, original, target),
                            original + " -> " + target);
                }
            }
        }
        assertEquals(Material.RED_STAINED_GLASS, mapping.invoke(game, Material.GLASS, DyeColor.RED));
        assertEquals(Material.RED_STAINED_GLASS_PANE, mapping.invoke(game, Material.GLASS_PANE, DyeColor.RED));
        assertEquals(Material.RED_STAINED_GLASS_PANE, mapping.invoke(game, Material.IRON_BARS, DyeColor.RED));
        assertEquals(Material.RED_TERRACOTTA, mapping.invoke(game, Material.TERRACOTTA, DyeColor.RED));
        assertEquals(Material.RED_TERRACOTTA, mapping.invoke(game, Material.STONE, DyeColor.RED));
        for (Material unsupported : new Material[]{Material.RED_BED, Material.RED_BANNER,
                Material.RED_SHULKER_BOX, Material.RED_CANDLE, Material.RED_CUSHION, Material.RED_DYE}) {
            assertFalse(game.isPaintable(new Surface(unsupported, Map.of()).block), unsupported.name());
        }
    }

    @Test
    void dyedShapesOwnTheirPaintAndScoreWithoutTouchingSupport() {
        for (String family : NEW_FAMILIES) {
            InkWars game = new InkWars();
            var orange = game.new EquipInkWars(DyeColor.ORANGE, DyeColor.RED, "Orange");
            var blue = game.new EquipInkWars(DyeColor.LIGHT_BLUE, DyeColor.BLUE, "Blue");
            game.Equips.add(orange);
            game.Equips.add(blue);
            Surface support = new Surface(Material.BLUE_WOOL, Map.of());
            Surface shape = new Surface(Material.valueOf("WHITE" + family), Map.of("geometry", "unchanged"));
            shape.support = support;
            assertTrue(game.isShape(shape.block), "movement still sees the shape");
            assertNull(game.getTeamOwningBlock(shape.block), "neutral shape ignores enemy substrate");
            assertTrue(game.paint(shape.block, orange, "offline", 1));
            assertSame(orange, game.getTeamOwningBlock(shape.block));
            assertEquals(Material.valueOf("RED" + family), shape.data.material);
            assertEquals(1, orange.getOwnedBlocks());
            assertEquals(0, blue.getOwnedBlocks());
            assertTrue(game.paint(shape.block, orange, "offline", .25));
            assertEquals(1, orange.getOwnedBlocks(), "same-team repaint does not score twice");
            assertFalse(game.paint(shape.block, blue, "offline", .5));
            assertSame(orange, game.getTeamOwningBlock(shape.block));
            assertEquals(.75, game.wetInk.get(shape.block).amount, 1e-9);
            assertTrue(game.paint(shape.block, blue, "offline", 1));
            assertSame(blue, game.getTeamOwningBlock(shape.block));
            assertEquals(0, orange.getOwnedBlocks());
            assertEquals(1, blue.getOwnedBlocks());
            assertEquals(.25, game.wetInk.get(shape.block).amount, 1e-9);
            assertEquals(Map.of("geometry", "unchanged"), shape.data.properties);
            assertEquals(0, shape.supportReads);
            assertEquals(0, support.writes);
            assertEquals(Material.BLUE_WOOL, support.data.material);
            assertFalse(game.wetInk.containsKey(support.block));
        }
    }

    @Test
    void paintDryAndRewetUseTheSameStatePreservingTransition() {
        for (String family : new String[]{"_WOOL_SLAB", "_WOOL_STAIRS", "_CONCRETE_SLAB", "_CONCRETE_STAIRS",
                "_GLAZED_TERRACOTTA", "_STAINED_GLASS_PANE"}) {
            InkWars game = new InkWars();
            var team = game.new EquipInkWars(DyeColor.ORANGE, DyeColor.RED, "Orange");
            game.Equips.add(team);
            Surface surface = new Surface(Material.valueOf("WHITE" + family), Map.of("compatible", "retained"));
            assertTrue(game.paint(surface.block, team, "offline", .01));
            assertEquals(Material.valueOf("RED" + family), surface.data.material);
            assertEquals(Map.of("compatible", "retained"), surface.data.properties);
            game.tickWetInk();
            assertEquals(Material.valueOf("ORANGE" + family), surface.data.material);
            assertFalse(game.wetInk.containsKey(surface.block));
            assertSame(team, game.getTeamOwningBlock(surface.block));
            assertEquals(Map.of("compatible", "retained"), surface.data.properties);
            game.rewet(surface.block, team, "offline");
            assertEquals(Material.valueOf("RED" + family), surface.data.material);
            assertEquals(1, game.wetInk.get(surface.block).amount);
            assertEquals(Map.of("compatible", "retained"), surface.data.properties);
            assertEquals(1, team.getOwnedBlocks(), "drying and rewet do not score");
            assertEquals(3, surface.writes);
        }
    }

    @Test
    void everyUndyedStairAndSlabHasAConcreteTranslationInEveryColor() throws Exception {
        InkWars game = new InkWars();
        Method mapping = InkWars.class.getDeclaredMethod("getPaintMaterial", Material.class, DyeColor.class);
        mapping.setAccessible(true);
        int neutralShapes = 0;
        for (Material material : Material.values()) {
            if (material.isLegacy() || !(material.name().endsWith("_STAIRS") || material.name().endsWith("_SLAB"))) continue;
            Surface surface = new Surface(material, Map.of());
            if (game.isPaintable(surface.block)) continue;
            neutralShapes++;
            assertTrue(game.isPaintableUnsafely(surface.block), material.name());
            String targetFamily = material.name().endsWith("_STAIRS") ? "_CONCRETE_STAIRS" : "_CONCRETE_SLAB";
            for (DyeColor color : DyeColor.values()) {
                assertEquals(Material.valueOf(color.name() + targetFamily), mapping.invoke(game, material, color), material.name());
            }
        }
        assertTrue(neutralShapes > 100, "cover the whole registered neutral shape palette");
    }

    @Test
    void neutralCounterpartsPaintThemselvesAndStayIndependentOfTheirSupport() {
        Map<Material, String> translations = Map.of(
                Material.QUARTZ_STAIRS, "_CONCRETE_STAIRS",
                Material.OAK_STAIRS, "_CONCRETE_STAIRS",
                Material.STONE_SLAB, "_CONCRETE_SLAB",
                Material.PETRIFIED_OAK_SLAB, "_CONCRETE_SLAB",
                Material.GLASS, "_STAINED_GLASS",
                Material.TINTED_GLASS, "_STAINED_GLASS",
                Material.GLASS_PANE, "_STAINED_GLASS_PANE",
                Material.IRON_BARS, "_STAINED_GLASS_PANE",
                Material.MOSS_CARPET, "_CARPET");
        for (var translation : translations.entrySet()) {
            InkWars game = new InkWars();
            var orange = game.new EquipInkWars(DyeColor.ORANGE, DyeColor.RED, "Orange");
            var blue = game.new EquipInkWars(DyeColor.LIGHT_BLUE, DyeColor.BLUE, "Blue");
            game.Equips.add(orange);
            game.Equips.add(blue);
            Surface surface = new Surface(translation.getKey(), Map.of("compatible", "retained"));
            Surface support = new Surface(Material.BLUE_WOOL, Map.of());
            surface.support = support;
            assertNull(game.getTeamOwningBlock(surface.block), "unpainted shape does not inherit the substrate's owner");
            assertTrue(game.isPaintableUnsafely(surface.block));
            assertTrue(game.paint(surface.block, orange, "offline", .01), translation.getKey().name());
            assertEquals(Material.valueOf("RED" + translation.getValue()), surface.data.material);
            assertEquals(1, orange.getOwnedBlocks());
            game.tickWetInk();
            assertEquals(Material.valueOf("ORANGE" + translation.getValue()), surface.data.material);
            assertSame(orange, game.getTeamOwningBlock(surface.block));
            game.rewet(surface.block, orange, "offline");
            assertEquals(Material.valueOf("RED" + translation.getValue()), surface.data.material);
            assertTrue(game.paint(surface.block, orange, "offline", .25));
            assertEquals(1, orange.getOwnedBlocks(), "same-team repaint does not score twice");
            assertFalse(game.paint(surface.block, blue, "offline", .5));
            assertTrue(game.paint(surface.block, blue, "offline", 1));
            assertEquals(Material.valueOf("BLUE" + translation.getValue()), surface.data.material);
            assertEquals(0, orange.getOwnedBlocks());
            assertEquals(1, blue.getOwnedBlocks());
            assertEquals(Map.of("compatible", "retained"), surface.data.properties);
            assertEquals(0, surface.supportReads);
            assertEquals(0, support.writes);
            assertEquals(Material.BLUE_WOOL, support.data.material);
            assertFalse(game.wetInk.containsKey(support.block));
        }
    }

    @Test
    void unsupportedShapesStillBorrowSupportWhilePlainGlassKeepsItsFamily() {
        InkWars game = new InkWars();
        var team = game.new EquipInkWars(DyeColor.ORANGE, DyeColor.RED, "Orange");
        game.Equips.add(team);
        for (Material material : new Material[]{Material.OAK_FENCE,
                Material.COBBLESTONE_WALL, Material.OAK_TRAPDOOR, Material.OAK_LEAVES}) {
            Surface shape = new Surface(material, Map.of());
            shape.support = new Surface(Material.WHITE_WOOL, Map.of());
            assertTrue(game.paint(shape.block, team, "offline", .1));
            assertEquals(material, shape.data.material);
            assertEquals(0, shape.writes);
            assertEquals(Material.RED_WOOL, shape.support.data.material);
            assertSame(team, game.getTeamOwningBlock(shape.block));
            assertTrue(game.wetInk.containsKey(shape.support.block));
            assertFalse(game.wetInk.containsKey(shape.block));
        }
        for (Material material : new Material[]{Material.GLASS, Material.GLASS_PANE, Material.IRON_BARS}) {
            Surface glass = new Surface(material, Map.of("compatible", "retained"));
            assertTrue(game.isPaintableUnsafely(glass.block));
            assertTrue(game.paint(glass.block, team, "offline", .01));
            assertEquals(material == Material.GLASS ? Material.RED_STAINED_GLASS : Material.RED_STAINED_GLASS_PANE,
                    glass.data.material);
            assertEquals(Map.of("compatible", "retained"), glass.data.properties);
        }
    }

    private static final class Data {
        final Material material;
        final Map<String, String> properties = new HashMap<>();
        final BlockData proxy;
        Data(Material material, Map<String, String> properties) {
            this.material = material;
            this.properties.putAll(properties);
            proxy = proxy(BlockData.class, (object, method, args) -> switch (method.getName()) {
                case "getMaterial" -> material;
                case "copyTo" -> { blockData.get((BlockData) args[0]).properties.putAll(this.properties); yield null; }
                default -> null;
            });
            blockData.put(proxy, this);
        }
    }

    private static final class Surface {
        Data data;
        Surface support;
        int supportReads;
        int writes;
        final Block block;
        Surface(Material material, Map<String, String> properties) {
            data = new Data(material, properties);
            block = proxy(Block.class, (object, method, args) -> switch (method.getName()) {
                case "getType" -> data.material;
                case "getBlockData" -> data.proxy;
                case "setBlockData" -> {
                    assertFalse((boolean) args[1], "paint does not apply neighbour physics");
                    data = blockData.get((BlockData) args[0]);
                    writes++;
                    yield null;
                }
                case "setType" -> throw new AssertionError("Recolour must preserve BlockData");
                case "getRelative" -> {
                    assertEquals(BlockFace.DOWN, args[0]);
                    supportReads++;
                    assertNotNull(support, "directly paintable shapes must not inspect their support");
                    yield support.block;
                }
                default -> null;
            });
        }
    }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (object, method, args) -> {
            if (method.getName().equals("equals")) return object == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(object);
            Object result = handler.invoke(object, method, args);
            if (result == null && method.getReturnType() == boolean.class) return false;
            return result;
        }));
    }
}
