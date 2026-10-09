package com.biel.lobby.mapes.jocs.parkour;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.biel.lobby.mapes.jocs.parkour.utils.CourseProfile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/** Observes the controller's Bukkit boundary; does not emulate datapack commands. */
class Spiral3NativeControllerTest {
    private static final String NATIVE_SOURCE = "function main:snake/2/init";
    private static final String CONTEXT = "{dimension:\"$(dimension)\",prefix:\"$(prefix)\",players:\"$(players)\",instance:\"$(instance)\"}";
    private static final String NATIVE_COMMAND = "execute in $(dimension) run function minicat_spiral3:main/snake/2/init " + CONTEXT;
    private static final String DELAYED = "main/console/timer/iron_door/close";
    private static final Pattern PREFIX = Pattern.compile("prefix:\"([^\"]+)\"");
    @TempDir Path temporaryDirectory;
    private Harness harness;
    private Object previousServer;

    @BeforeEach void installServer() throws Exception {
        ((Map<?, ?>) field(Spiral3NativeController.class, "INSTANCES").get(null)).clear();
        ((Set<?>) field(Spiral3NativeController.class, "PREFIXES").get(null)).clear();
        field(Spiral3NativeController.class, "commandRegistered").setBoolean(null, false);
        previousServer = field(Bukkit.class, "server").get(null);
        harness = new Harness();
        field(Bukkit.class, "server").set(null, harness.server);
    }

    @AfterEach void releaseControllersAndRestoreServer() throws Exception {
        List<Spiral3NativeController> controllers = new ArrayList<>();
        ((Map<?, ?>) field(Spiral3NativeController.class, "INSTANCES").get(null)).values()
                .forEach(value -> controllers.add((Spiral3NativeController) value));
        controllers.forEach(Spiral3NativeController::clear);
        field(Bukkit.class, "server").set(null, previousServer);
    }

    @Test void twoInstancesHaveIndependentObjectivesMacroArgumentsAndActivePlayers() throws Exception {
        Fixture first = fixture("first"), second = fixture("second");
        Runner active = first.addRunner("Biel", true), waiting = first.addRunner("Waiting", false);
        Runner other = second.addRunner("Other", true);
        Spiral3NativeController firstController = first.create(), secondController = second.create();
        assertNotEquals(firstController.prefix(), secondController.prefix());
        assertEquals(2, firstController.prefix().length());
        String firstCommand = first.block.command, secondCommand = second.block.command;
        assertTrue(firstCommand.startsWith("execute in " + first.key + " run function minicat_spiral3:"));
        assertTrue(secondCommand.startsWith("execute in " + second.key + " run function minicat_spiral3:"));
        assertFalse(firstCommand.contains("$("));
        assertTrue(firstCommand.contains("prefix:\"" + firstController.prefix() + "\""));
        assertTrue(firstCommand.contains("instance:\"" + first.id + "\""));
        assertFalse(firstCommand.contains(second.id.toString()));

        harness.commands.clear();
        firstController.tick(12);
        assertEquals(4, harness.commands.size(), "tick plus cadences 2, 3 and 4; cadence 10 is not due");
        assertTrue(harness.commands.stream().allMatch(command -> command.contains(first.key.toString()) && command.contains(first.id.toString())));
        assertEquals(Set.of(first.playersTag(), "ingame"), active.tags);
        assertTrue(waiting.tags.isEmpty());
        assertTrue(other.tags.isEmpty());
        secondController.tick(10);
        assertEquals(Set.of(second.playersTag(), "ingame"), other.tags);
        active.active = false;
        firstController.tick(13);
        assertTrue(active.tags.isEmpty(), "finishing or dropping a seat removes native participation immediately");
    }

    @Test void checkpointBlocksBecomeNoOpsWithoutChangingTraversalBlocks() throws Exception {
        Fixture fixture = fixture("checkpoint");
        fixture.block.command = "function main:pressure_plate/checkpoint/active";
        fixture.sourceCommand = fixture.block.command;
        fixture.compiledCommand = "";
        Spiral3NativeController controller = fixture.create();
        assertEquals("", fixture.block.command);
        assertEquals(1, fixture.block.updates);
        assertEquals(2, fixture.totalTicketsAdded());
        controller.clear();
        assertEquals(2, fixture.totalTicketsRemoved());
    }

    @Test void missingOrDuplicateConsoleAndChangedAuthoredBlocksRejectLaunchAndReleaseTickets() throws Exception {
        Fixture missing = fixture("missing");
        missing.consoleCount = 0;
        IllegalArgumentException missingFailure = assertThrows(IllegalArgumentException.class, missing::create);
        assertTrue(missingFailure.getMessage().contains("console marker"));
        assertEquals(missing.totalTicketsAdded(), missing.totalTicketsRemoved());

        Fixture duplicate = fixture("duplicate");
        duplicate.consoleCount = 2;
        assertThrows(IllegalArgumentException.class, duplicate::create);
        assertEquals(duplicate.totalTicketsAdded(), duplicate.totalTicketsRemoved());

        Fixture changed = fixture("changed");
        changed.block.command = "function main:player/restart";
        assertThrows(IllegalArgumentException.class, changed::create);
        assertEquals(changed.totalTicketsAdded(), changed.totalTicketsRemoved());
        assertEquals(0, changed.block.updates);

        Fixture absent = fixture("absent");
        absent.blockPresent = false;
        assertThrows(IllegalArgumentException.class, absent::create);
        assertEquals(absent.totalTicketsAdded(), absent.totalTicketsRemoved());
    }

    @Test void missingOrWrongInitializerSentinelFailsClosedAndRemovesPartialObjectives() throws Exception {
        for (Sentinel sentinel : List.of(Sentinel.MISSING, Sentinel.WRONG)) {
            Fixture fixture = fixture("sentinel_" + sentinel.name().toLowerCase());
            harness.sentinel = sentinel;
            IllegalArgumentException failure = assertThrows(IllegalArgumentException.class, fixture::create);
            assertTrue(failure.getMessage().contains("initialization"));
            assertTrue(harness.objectives.isEmpty(), "partly initialized objectives must not leak after rejected launch");
            assertEquals(fixture.totalTicketsAdded(), fixture.totalTicketsRemoved());
            assertFalse(harness.bridge.execute(harness.console, "minicatparkour", schedule(fixture)), "rejected instances cannot schedule work");
        }
    }

    @Test void scheduleKeepsOwningContextReplacesEarlierCloseAndCannotOutliveTeardown() throws Exception {
        Fixture first = fixture("scheduled"), second = fixture("unrelated");
        Spiral3NativeController controller = first.create();
        second.create();
        assertTrue(harness.bridge.execute(harness.console, "minicatparkour", schedule(first)));
        ScheduledTask previous = harness.tasks.getLast();
        assertEquals(50, previous.delay);
        assertTrue(harness.bridge.execute(first.blockSender(), "minicatparkour", schedule(first)));
        ScheduledTask replacement = harness.tasks.getLast();
        assertTrue(previous.cancelled);
        harness.commands.clear();
        replacement.callback.run();
        assertEquals(1, harness.commands.size());
        assertTrue(harness.commands.getFirst().contains("function minicat_spiral3:" + DELAYED));
        assertTrue(harness.commands.getFirst().contains(first.key.toString()));
        assertTrue(harness.commands.getFirst().contains(first.id.toString()));
        assertFalse(harness.commands.getFirst().contains(second.id.toString()));
        assertTrue(harness.bridge.execute(harness.console, "minicatparkour", schedule(first)));
        ScheduledTask pending = harness.tasks.getLast();
        controller.clear();
        assertTrue(pending.cancelled);
        harness.commands.clear();
        pending.callback.run();
        controller.tick(20);
        assertTrue(harness.commands.isEmpty(), "even a late callback cannot run functions after teardown");
        assertFalse(harness.bridge.execute(harness.console, "minicatparkour", schedule(first)));
    }

    @Test void bridgeRejectsPlayersProxiedPlayersForeignBlocksAndUnqualifiedSchedules() throws Exception {
        Fixture owner = fixture("owner"), foreign = fixture("foreign");
        Runner runner = owner.addRunner("Biel", true);
        owner.create();
        assertFalse(harness.bridge.execute(runner.player, "minicatparkour", schedule(owner)));
        ProxiedCommandSender proxy = stub(ProxiedCommandSender.class, (method, args) -> method.equals("getCaller") ? runner.player : harness.console);
        assertFalse(harness.bridge.execute(proxy, "minicatparkour", schedule(owner)));
        assertFalse(harness.bridge.execute(foreign.blockSender(), "minicatparkour", schedule(owner)));
        assertFalse(harness.bridge.execute(harness.console, "minicatparkour", new String[]{"schedule", owner.id.toString(), "main/player/on_join", "50"}));
        assertFalse(harness.bridge.execute(harness.console, "minicatparkour", new String[]{"schedule", owner.id.toString(), DELAYED, "1"}));
        assertTrue(harness.tasks.isEmpty());
    }

    @Test void dedicatedMechanicsSenderCanScheduleOnlyItsOwningInstance() throws Exception {
        Fixture first = fixture("sender_first"), second = fixture("sender_second");
        Spiral3NativeController firstController = first.create();
        second.create();
        CommandSender firstSender = harness.mechanicsSenders.getFirst(), secondSender = harness.mechanicsSenders.getLast();
        assertFalse(firstSender instanceof ConsoleCommandSender);
        assertNotSame(firstSender, secondSender);
        assertTrue(harness.commandSenders.contains(firstSender), "native functions dispatch through the feedback callback sender");
        assertTrue(harness.commandSenders.contains(secondSender));
        assertFalse(harness.commandSenders.contains(harness.console));
        assertTrue(harness.bridge.execute(firstSender, "minicatparkour", schedule(first)));
        ProxiedCommandSender proxiedMechanics = stub(ProxiedCommandSender.class,
                (method, args) -> method.equals("getCaller") ? firstSender : harness.console);
        assertTrue(harness.bridge.execute(proxiedMechanics, "minicatparkour", schedule(first)));
        assertFalse(harness.bridge.execute(firstSender, "minicatparkour", schedule(second)), "trusted identity still belongs to one match");
        assertFalse(harness.bridge.execute(secondSender, "minicatparkour", schedule(first)));
        assertFalse(harness.bridge.execute(stub(CommandSender.class, (method, args) -> null), "minicatparkour", schedule(first)));
        firstController.clear();
        assertFalse(harness.bridge.execute(firstSender, "minicatparkour", schedule(first)), "retired sender loses bridge trust");
    }

    @Test void reconnectRestoresClonedNativeEquipmentToTheReplacementPlayerAfterBaseKitReset() throws Exception {
        Fixture fixture = fixture("inventory_reconnect");
        Runner original = fixture.addRunner("Biel", true);
        original.contents = new ItemStack[41];
        original.contents[0] = new NativeItem("snowball", 4);
        original.contents[4] = new NativeItem("rocket", 1);
        original.contents[36] = new NativeItem("fire_boots", 1);
        original.contents[38] = new NativeItem("elytra", 1);
        Spiral3NativeController controller = fixture.create();
        controller.tick(1);
        controller.suspendPlayer(original.player);
        assertFalse(original.tags.contains(fixture.playersTag()));
        assertFalse(original.tags.contains("ingame"));
        NativeItem originalBoots = (NativeItem) original.contents[36];
        originalBoots.customData = "changed_after_disconnect";
        original.contents[0] = null;
        fixture.runners.remove(original);
        Runner replacement = fixture.addRunner("Biel", true);
        replacement.contents = new ItemStack[]{new NativeItem("base_kit", 1)};
        assertNotSame(original.player, replacement.player);
        controller.resumePlayer(replacement.player);
        assertEquals(41, replacement.contents.length);
        assertEquals("snowball", ((NativeItem) replacement.contents[0]).customData);
        assertEquals(4, replacement.contents[0].getAmount());
        assertEquals("rocket", ((NativeItem) replacement.contents[4]).customData);
        assertEquals("fire_boots", ((NativeItem) replacement.contents[36]).customData);
        assertEquals("elytra", ((NativeItem) replacement.contents[38]).customData);
        assertNotSame(originalBoots, replacement.contents[36], "saved equipment must not alias the disconnected entity's items");
        replacement.contents[4] = null;
        controller.resumePlayer(replacement.player);
        assertNull(replacement.contents[4], "resume consumes the saved snapshot instead of duplicating equipment");
    }

    @Test void deliberateReleaseOrTeardownDiscardsSuspendedEquipment() throws Exception {
        Fixture fixture = fixture("inventory_release");
        Runner original = fixture.addRunner("Biel", true);
        original.contents = new ItemStack[]{new NativeItem("fire_boots", 1)};
        Spiral3NativeController controller = fixture.create();
        controller.suspendPlayer(original.player);
        controller.releasePlayer(original.player);
        Runner replacement = fixture.addRunner("Biel", true);
        NativeItem baseKit = new NativeItem("base_kit", 1);
        replacement.contents = new ItemStack[]{baseKit};
        controller.resumePlayer(replacement.player);
        assertSame(baseKit, replacement.contents[0], "a deliberate leave or finish abandons equipment snapshots");
        controller.suspendPlayer(original.player);
        controller.clear();
        controller.resumePlayer(replacement.player);
        assertSame(baseKit, replacement.contents[0], "teardown cannot hand an old match's equipment to a later entity");
    }

    @Test void cleanupIsIdempotentAndRemovesOnlyThisInstancesTagsObjectivesAndTickets() throws Exception {
        Fixture first = fixture("cleanup_first"), second = fixture("cleanup_second");
        Runner firstRunner = first.addRunner("Biel", true), secondRunner = second.addRunner("Other", true);
        Spiral3NativeController firstController = first.create(), secondController = second.create();
        firstController.tick(1);
        secondController.tick(1);
        firstRunner.tags.add("fire_boots");
        firstRunner.tags.add("unrelated_tag");
        harness.commands.clear();
        firstController.clear();
        firstController.clear();
        assertEquals(Set.of("unrelated_tag"), firstRunner.tags);
        assertEquals(Set.of(second.playersTag(), "ingame"), secondRunner.tags);
        assertFalse(harness.objectives.containsKey(firstController.prefix() + "adapter"));
        assertTrue(harness.objectives.containsKey(secondController.prefix() + "adapter"));
        assertEquals(1, harness.commands.stream().filter(command -> command.contains("function minicat_spiral3:cleanup")).count());
        assertEquals(first.totalTicketsAdded(), first.totalTicketsRemoved());
        assertEquals(0, second.totalTicketsRemoved());
    }

    @Test void teardownDisablesEveryNativeCallbackBeforeTheRankingWorldsPrefixCanBeReused() throws Exception {
        Fixture finished = fixture("ranking_world");
        finished.additionalCommandBlock = true;
        Spiral3NativeController completed = finished.create();
        assertFalse(finished.block.command.isEmpty());
        assertFalse(finished.secondBlock.command.isEmpty());
        harness.cleanupObserver = () -> {
            assertEquals("", finished.block.command);
            assertEquals("", finished.secondBlock.command);
        };
        completed.clear();
        harness.cleanupObserver = () -> {};
        Fixture next = fixture("next_match");
        Spiral3NativeController replacement = next.create();
        assertEquals(completed.prefix(), replacement.prefix(), "a fully cleaned prefix may be allocated to the next match");
        assertEquals("", finished.block.command, "ranking world callbacks stay inert while its world remains loaded");
        assertEquals("", finished.secondBlock.command);
        assertFalse(next.block.command.isEmpty());
        assertEquals(2, finished.block.updates);
        assertEquals(2, finished.secondBlock.updates);
    }

    @Test void crashedInstancesExistingAdapterObjectiveReservesItsPrefix() throws Exception {
        Objective stale = Harness.objective(1);
        harness.objectives.put("00adapter", stale);
        Fixture fixture = fixture("after_crash");
        Spiral3NativeController controller = fixture.create();
        assertEquals("01", controller.prefix());
        controller.clear();
        assertSame(stale, harness.objectives.get("00adapter"), "launching and clearing a fresh match must not overwrite a stale instance's state");
    }

    @Test void invalidCompiledResourceDimensionOrMacroCannotBeInstalled() throws Exception {
        for (String command : List.of("execute in $(dimension) run function foreign:danger " + CONTEXT,
                "execute in $(dimension) run execute in minecraft:overworld run kill @a",
                NATIVE_COMMAND + " $(unresolved)", NATIVE_COMMAND + "\nkill @a")) {
            Fixture fixture = fixture("invalid_" + UUID.randomUUID().toString().replace("-", ""));
            fixture.compiledCommand = command;
            assertThrows(IllegalArgumentException.class, fixture::create, command);
            assertEquals(fixture.totalTicketsAdded(), fixture.totalTicketsRemoved());
        }
    }

    private Fixture fixture(String name) throws Exception { return new Fixture(name, temporaryDirectory.resolve(name)); }
    private static String[] schedule(Fixture fixture) { return new String[]{"schedule", fixture.id.toString(), DELAYED, "50"}; }
    private enum Sentinel { OK, MISSING, WRONG }
    private record ChunkPosition(int x, int z) {}

    private final class Fixture {
        final UUID id = UUID.randomUUID();
        final NamespacedKey key;
        final Path directory;
        final World world;
        final Plugin plugin = stub(Plugin.class, (method, args) -> method.equals("getLogger") ? Logger.getLogger("Spiral3NativeControllerTest") : null);
        final List<Runner> runners = new ArrayList<>();
        final Map<ChunkPosition, ChunkState> chunks = new HashMap<>();
        final CommandBlockState block = new CommandBlockState();
        final CommandBlockState secondBlock = new CommandBlockState();
        int consoleCount = 1;
        boolean blockPresent = true;
        boolean additionalCommandBlock;
        String sourceCommand = NATIVE_SOURCE, compiledCommand = NATIVE_COMMAND;

        Fixture(String name, Path directory) throws Exception {
            this.directory = directory;
            Files.createDirectories(directory);
            key = new NamespacedKey("minicat", "spiral_" + name);
            world = stub(World.class, (method, args) -> switch (method) {
                case "getUID" -> id;
                case "getName" -> name;
                case "getKey" -> key;
                case "getWorldFolder" -> directory.toFile();
                case "getPlayers" -> runners.stream().map(runner -> runner.player).toList();
                case "getChunkAt" -> chunks.computeIfAbsent(new ChunkPosition((Integer) args[0], (Integer) args[1]), ignored -> new ChunkState()).chunk;
                case "getEntitiesByClass" -> {
                    List<Marker> markers = new ArrayList<>();
                    for (int index = 0; index < consoleCount; index++) markers.add(stub(Marker.class, (entityMethod, ignored) -> entityMethod.equals("getCustomName") ? "console" : null));
                    yield markers;
                }
                case "getBlockAt" -> (Integer) args[0] == 30 ? block(secondBlock) : block();
                default -> null;
            });
            block.owningBlock = this::block;
            secondBlock.owningBlock = () -> block(secondBlock);
        }
        Block block() { return block(block); }
        Block block(CommandBlockState selected) { return stub(Block.class, (method, args) -> switch (method) {
            case "getWorld" -> world;
            case "getState" -> blockPresent ? selected.state : stub(BlockState.class, (ignored, values) -> null);
            default -> null;
        }); }
        BlockCommandSender blockSender() { return stub(BlockCommandSender.class, (method, args) -> method.equals("getBlock") ? block() : null); }
        Runner addRunner(String name, boolean active) { Runner runner = new Runner(this, name, active); runners.add(runner); return runner; }
        String playersTag() { return "minicat_spiral_" + id; }
        int totalTicketsAdded() { return chunks.values().stream().mapToInt(chunk -> chunk.added).sum(); }
        int totalTicketsRemoved() { return chunks.values().stream().mapToInt(chunk -> chunk.removed).sum(); }
        Spiral3NativeController create() throws Exception {
            JsonObject profile = new JsonObject(), nativeRules = new JsonObject();
            nativeRules.addProperty("mechanicsVersion", "spiral3-scoped-v1");
            JsonArray requiredChunks = new JsonArray();
            for (ChunkPosition coordinates : List.of(new ChunkPosition(0, -2), new ChunkPosition(-2, 6))) {
                JsonObject point = new JsonObject(); point.addProperty("x", coordinates.x); point.addProperty("z", coordinates.z);
                requiredChunks.add(point);
            }
            nativeRules.add("requiredChunks", requiredChunks);
            JsonObject command = new JsonObject(), position = new JsonObject();
            position.addProperty("x", -28); position.addProperty("y", 145); position.addProperty("z", 101);
            command.add("position", position); command.addProperty("sourceCommand", sourceCommand); command.addProperty("command", compiledCommand);
            JsonArray commandBlocks = new JsonArray(); commandBlocks.add(command);
            if (additionalCommandBlock) {
                JsonObject additional = command.deepCopy(), additionalPosition = new JsonObject();
                additionalPosition.addProperty("x", 30); additionalPosition.addProperty("y", 268); additionalPosition.addProperty("z", 113);
                additional.add("position", additionalPosition); commandBlocks.add(additional);
            }
            nativeRules.add("commandBlocks", commandBlocks);
            profile.add("native", nativeRules);
            Files.writeString(directory.resolve(CourseProfile.FILE_NAME), profile.toString());
            return new Spiral3NativeController(world, plugin, player -> runners.stream().filter(runner -> runner.player == player).findFirst().orElseThrow().active, () -> "fixture");
        }
    }

    private static final class ChunkState {
        int added, removed;
        final Chunk chunk = stub(Chunk.class, (method, args) -> switch (method) {
            case "addPluginChunkTicket" -> { added++; yield true; }
            case "removePluginChunkTicket" -> { removed++; yield true; }
            default -> null;
        });
    }
    private static final class CommandBlockState {
        String command = NATIVE_SOURCE;
        int updates;
        Supplier<Block> owningBlock = () -> null;
        final CommandBlock state = stub(CommandBlock.class, (method, args) -> switch (method) {
            case "getCommand" -> command;
            case "getBlock" -> owningBlock.get();
            case "setCommand" -> { command = (String) args[0]; yield null; }
            case "update" -> { updates++; yield true; }
            default -> null;
        });
    }
    private static final class Runner {
        final Player player;
        final Set<String> tags = new HashSet<>();
        boolean active;
        ItemStack[] contents = new ItemStack[41];
        final PlayerInventory inventory = stub(PlayerInventory.class, (method, args) -> switch (method) {
            case "getContents" -> contents;
            case "setContents" -> { contents = ((ItemStack[]) args[0]).clone(); yield null; }
            default -> null;
        });
        Runner(Fixture fixture, String name, boolean active) {
            this.active = active;
            player = stub(Player.class, (method, args) -> switch (method) {
                case "getName" -> name;
                case "getWorld" -> fixture.world;
                case "getInventory" -> inventory;
                case "addScoreboardTag" -> tags.add((String) args[0]);
                case "removeScoreboardTag" -> tags.remove((String) args[0]);
                case "getScoreboardTags" -> tags;
                default -> null;
            });
        }
    }
    /** A metadata-bearing item without a server item factory; cloning mirrors the SDK contract. */
    private static final class NativeItem extends ItemStack {
        String customData;
        final int amount;
        NativeItem(String customData, int amount) { super(); this.customData = customData; this.amount = amount; }
        @Override public int getAmount() { return amount; }
        @Override public NativeItem clone() { return new NativeItem(customData, amount); }
    }
    private static final class ScheduledTask {
        final Runnable callback;
        final long delay;
        boolean cancelled;
        final BukkitTask task = stub(BukkitTask.class, (method, args) -> switch (method) {
            case "cancel" -> { cancelled = true; yield null; }
            case "isCancelled" -> cancelled;
            default -> null;
        });
        ScheduledTask(Runnable callback, long delay) { this.callback = callback; this.delay = delay; }
    }
    private static final class Harness {
        Sentinel sentinel = Sentinel.OK;
        final List<String> commands = new ArrayList<>();
        final List<CommandSender> commandSenders = new ArrayList<>(), mechanicsSenders = new ArrayList<>();
        final List<Consumer<net.kyori.adventure.text.Component>> feedbackCallbacks = new ArrayList<>();
        final List<ScheduledTask> tasks = new ArrayList<>();
        final Map<String, Objective> objectives = new HashMap<>();
        Command bridge;
        Runnable cleanupObserver = () -> {};
        final ConsoleCommandSender console = stub(ConsoleCommandSender.class, (method, args) -> null);
        final Scoreboard scoreboard = stub(Scoreboard.class, (method, args) -> method.equals("getObjective") ? objectives.get(args[0]) : null);
        final ScoreboardManager scoreboardManager = stub(ScoreboardManager.class, (method, args) -> method.equals("getMainScoreboard") ? scoreboard : null);
        final CommandMap commandMap = stub(CommandMap.class, (method, args) -> {
            if (method.equals("register")) { bridge = (Command) args[args.length - 1]; return true; }
            return null;
        });
        final BukkitScheduler scheduler = stub(BukkitScheduler.class, (method, args) -> {
            if (method.equals("runTaskLater")) {
                ScheduledTask task = new ScheduledTask((Runnable) args[1], ((Number) args[2]).longValue());
                tasks.add(task); return task.task;
            }
            return null;
        });
        final Server server = stub(Server.class, (method, args) -> switch (method) {
            case "getConsoleSender" -> console;
            case "getCommandMap" -> commandMap;
            case "getScheduler" -> scheduler;
            case "getScoreboardManager" -> scoreboardManager;
            case "getName", "getVersion", "getBukkitVersion" -> "test";
            case "getLogger" -> Logger.getLogger("Spiral3NativeControllerTest");
            case "createCommandSender" -> {
                CommandSender sender = stub(CommandSender.class, (senderMethod, ignored) -> null);
                mechanicsSenders.add(sender);
                @SuppressWarnings("unchecked")
                Consumer<net.kyori.adventure.text.Component> callback = (Consumer<net.kyori.adventure.text.Component>) args[0];
                feedbackCallbacks.add(callback);
                yield sender;
            }
            case "dispatchCommand" -> {
                String command = (String) args[1]; commands.add(command); commandSenders.add((CommandSender) args[0]);
                Matcher prefix = PREFIX.matcher(command);
                assertTrue(prefix.find());
                if (command.contains("function minicat_spiral3:initialize")) {
                    objectives.put(prefix.group(1) + "diamond_timer", objective(0));
                    if (sentinel != Sentinel.MISSING) objectives.put(prefix.group(1) + "adapter", objective(sentinel == Sentinel.OK ? 1 : 0));
                } else if (command.contains("function minicat_spiral3:cleanup")) {
                    cleanupObserver.run();
                    objectives.keySet().removeIf(name -> name.startsWith(prefix.group(1)));
                }
                yield true;
            }
            default -> null;
        });
        private static Objective objective(int scoreValue) {
            Score score = stub(Score.class, (method, args) -> method.equals("getScore") ? scoreValue : null);
            return stub(Objective.class, (method, args) -> method.equals("getScore") ? score : null);
        }
    }
    @FunctionalInterface private interface Answer { Object call(String method, Object[] arguments); }
    private static <T> T stub(Class<T> type, Answer answer) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("equals")) return proxy == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
            Object value = answer.call(method.getName(), args);
            if (value != null) return value;
            if (method.getReturnType() == boolean.class) return false;
            if (method.getReturnType() == int.class) return 0;
            if (method.getReturnType() == long.class) return 0L;
            return null;
        }));
    }
    private static Field field(Class<?> type, String name) throws Exception {
        Field field = type.getDeclaredField(name); field.setAccessible(true); return field;
    }
}
