package com.biel.lobby.mapes.jocs.parkour;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.biel.lobby.mapes.jocs.parkour.utils.CourseProfile;

/** Runs the map's traversal commands with immutable arguments belonging to one world. */
final class Spiral3NativeController {
    private static final String NAMESPACE = "minicat_spiral3:";
    private static final String DELAYED_FUNCTION = "main/console/timer/iron_door/close";
    private static final Map<String, Spiral3NativeController> INSTANCES = new HashMap<>();
    private static final Set<String> PREFIXES = new HashSet<>();
    private static final Set<String> PLAYER_TAGS = Set.of("ingame", "joined", "finished", "water_damage",
            "fire_boots", "snow_boots", "has_snowball", "playsound_teleport", "playsound_portal", "dripstone_fall");
    private static boolean commandRegistered;
    private final World world;
    private final Plugin plugin;
    private final Predicate<Player> activeRunner;
    private final Supplier<String> status;
    private final String instance;
    private final String prefix;
    private final String playersTag;
    private final String context;
    private final CommandSender mechanicsSender;
    private final Set<String> reportedFeedback = new HashSet<>();
    private final List<Chunk> loadedChunks = new ArrayList<>();
    private final List<org.bukkit.block.Block> nativeCommandBlocks = new ArrayList<>();
    private final Set<BukkitTask> delayedTasks = new HashSet<>();
    private final Map<String, ItemStack[]> suspendedInventories = new HashMap<>();
    private boolean initialized;
    private boolean initializationAttempted;
    private boolean closed;

    Spiral3NativeController(World world, Plugin plugin, Predicate<Player> activeRunner, Supplier<String> status) {
        this.world = world;
        this.plugin = plugin;
        this.activeRunner = activeRunner;
        this.status = status;
        mechanicsSender = Bukkit.getServer().createCommandSender(feedback -> {
            if (feedback instanceof net.kyori.adventure.text.TranslatableComponent translated
                    && translated.key().startsWith("commands.function.success")) return;
            String message = net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText().serialize(feedback);
            if (reportedFeedback.size() < 20 && reportedFeedback.add(message))
                plugin.getLogger().warning("Spiral 3 command feedback in " + world.getName() + ": " + message);
        });
        instance = world.getUID().toString();
        prefix = allocatePrefix();
        playersTag = "minicat_spiral_" + instance;
        context = "{dimension:\"" + world.getKey() + "\",prefix:\"" + prefix
                + "\",players:\"" + playersTag + "\",instance:\"" + instance + "\"}";
        try {
            registerBridge();
            JsonObject profile = JsonParser.parseString(Files.readString(
                    world.getWorldFolder().toPath().resolve(CourseProfile.FILE_NAME), StandardCharsets.UTF_8)).getAsJsonObject();
            JsonObject nativeRules = profile.getAsJsonObject("native");
            if (nativeRules == null || !"spiral3-scoped-v1".equals(nativeRules.get("mechanicsVersion").getAsString()))
                throw new IllegalArgumentException("Spiral 3 requires its qualified scoped mechanics profile");
            var chunks = nativeRules.getAsJsonArray("requiredChunks");
            if (chunks == null || chunks.isEmpty() || chunks.size() > 256)
                throw new IllegalArgumentException("Invalid Spiral 3 mechanics chunk inventory");
            for (var value : chunks) {
                var coordinates = value.getAsJsonObject();
                Chunk chunk = world.getChunkAt(coordinates.get("x").getAsInt(), coordinates.get("z").getAsInt());
                chunk.addPluginChunkTicket(plugin);
                chunk.getEntities();
                loadedChunks.add(chunk);
            }
            long consoles = world.getEntitiesByClass(org.bukkit.entity.Marker.class).stream()
                    .filter(entity -> "console".equals(entity.getCustomName())).count();
            if (consoles != 1) throw new IllegalArgumentException("Spiral 3 needs exactly one native console marker; found " + consoles);
            for (var value : nativeRules.getAsJsonArray("commandBlocks")) {
                JsonObject record = value.getAsJsonObject(), position = record.getAsJsonObject("position");
                var state = world.getBlockAt(position.get("x").getAsInt(), position.get("y").getAsInt(), position.get("z").getAsInt()).getState();
                if (!(state instanceof CommandBlock block))
                    throw new IllegalArgumentException("Spiral 3 authored command block missing at " + position);
                String sourceCommand = record.get("sourceCommand").getAsString().strip().replaceFirst("^/", "");
                String command = substitute(record.get("command").getAsString());
                String current = block.getCommand().strip().replaceFirst("^/", "");
                if (!current.equals(sourceCommand) && !current.equals(command))
                    throw new IllegalArgumentException("Spiral 3 command block differs from audited source at " + position);
                block.setCommand(command);
                block.update(true, false);
                nativeCommandBlocks.add(block.getBlock());
            }
            INSTANCES.put(instance, this);
            initializationAttempted = true;
            call("initialize");
            initialized = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(prefix + "adapter") != null;
            if (!initialized || Bukkit.getScoreboardManager().getMainScoreboard().getObjective(prefix + "adapter")
                    .getScore("#version").getScore() != 1)
                throw new IllegalStateException("Spiral 3 mechanics pack is missing or failed initialization; install minicat-spiral3.zip and restart Paper");
            plugin.getLogger().info("Spiral 3 mechanics ready: world=" + world.getName() + " prefix=" + prefix + " players=" + playersTag);
        } catch (IOException | RuntimeException failure) {
            clear();
            throw new IllegalArgumentException("Cannot initialize Spiral 3 in " + world.getName() + ": " + failure.getMessage(), failure);
        }
    }

    String prefix() { return prefix; }

    void tick(long tick) {
        if (closed) return;
        for (Player player : world.getPlayers()) {
            if (activeRunner.test(player)) {
                player.addScoreboardTag(playersTag);
                player.addScoreboardTag("ingame");
            } else {
                player.removeScoreboardTag(playersTag);
                player.removeScoreboardTag("ingame");
            }
        }
        call("main/tick");
        for (int period : new int[] {2, 3, 4, 10})
            if (tick % period == 0) call("main/tick_" + period);
    }

    void releasePlayer(Player player) {
        player.removeScoreboardTag(playersTag);
        PLAYER_TAGS.forEach(player::removeScoreboardTag);
        suspendedInventories.remove(player.getName());
    }

    void suspendPlayer(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        suspendedInventories.put(player.getName(), java.util.Arrays.stream(contents)
                .map(item -> item == null ? null : item.clone()).toArray(ItemStack[]::new));
        player.removeScoreboardTag(playersTag);
        player.removeScoreboardTag("ingame");
    }

    void resumePlayer(Player player) {
        ItemStack[] contents = suspendedInventories.remove(player.getName());
        if (contents != null) player.getInventory().setContents(contents);
    }

    void clear() {
        if (closed) return;
        closed = true;
        delayedTasks.forEach(BukkitTask::cancel);
        delayedTasks.clear();
        suspendedInventories.clear();
        world.getPlayers().forEach(this::releasePlayer);
        // A finished game's world can remain loaded for its ranking screen. Its
        // pressure-plate callbacks must be inert before this prefix is reused.
        nativeCommandBlocks.forEach(block -> {
            if (block.getState() instanceof CommandBlock commandBlock) {
                commandBlock.setCommand("");
                commandBlock.update(true, false);
            }
        });
        nativeCommandBlocks.clear();
        if (initializationAttempted) call("cleanup");
        INSTANCES.remove(instance, this);
        PREFIXES.remove(prefix);
        loadedChunks.forEach(chunk -> chunk.removePluginChunkTicket(plugin));
        loadedChunks.clear();
    }

    private void call(String function) {
        Bukkit.dispatchCommand(mechanicsSender, "execute in " + world.getKey()
                + " positioned 0 0 0 run function " + NAMESPACE + function + " " + context);
    }

    private String substitute(String command) {
        String result = command.replace("$(dimension)", world.getKey().toString()).replace("$(prefix)", prefix)
                .replace("$(players)", playersTag).replace("$(instance)", instance);
        if (result.contains("$(") || result.contains("\n") || result.contains("\r"))
            throw new IllegalArgumentException("Unresolved or multiline native command");
        if (!result.isEmpty() && !result.startsWith("execute in " + world.getKey() + " run "))
            throw new IllegalArgumentException("Native command lacks owning dimension");
        var dimensions = java.util.regex.Pattern.compile("execute in ([a-z0-9_:/.-]+)").matcher(result);
        while (dimensions.find()) if (!dimensions.group(1).equals(world.getKey().toString()))
            throw new IllegalArgumentException("Native command changes owning dimension");
        var functions = java.util.regex.Pattern.compile("function ([a-z0-9_:/.-]+)").matcher(result);
        while (functions.find()) if (!functions.group(1).startsWith(NAMESPACE))
            throw new IllegalArgumentException("Native command calls a foreign function");
        return result;
    }

    private static String allocatePrefix() {
        for (int number = 0; number < 1296; number++) {
            String candidate = Integer.toString(number, 36);
            if (candidate.length() == 1) candidate = "0" + candidate;
            if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective(candidate + "adapter") != null) continue;
            if (PREFIXES.add(candidate)) return candidate;
        }
        throw new IllegalStateException("Spiral 3 mechanics objective capacity exhausted");
    }

    static void registerBridge() {
        if (commandRegistered) return;
        Command bridge = new Command("minicatparkour") {
            @Override public boolean execute(CommandSender sender, String label, String[] arguments) {
                CommandSender caller = sender;
                while (caller instanceof ProxiedCommandSender proxy) caller = proxy.getCaller();
                CommandSender origin = caller;
                boolean trustedMechanics = INSTANCES.values().stream().anyMatch(controller -> controller.mechanicsSender == origin);
                if (!(caller instanceof ConsoleCommandSender) && !(caller instanceof BlockCommandSender) && !trustedMechanics) return false;
                if (arguments.length == 2 && arguments[0].equals("status") && caller instanceof ConsoleCommandSender) {
                    INSTANCES.values().stream().filter(controller -> controller.world.getName().equals(arguments[1]))
                            .findFirst().ifPresent(controller -> sender.sendMessage("PARKOUR_STATUS " + controller.status.get()));
                    return true;
                }
                if (arguments.length != 4 || !arguments[0].equals("schedule")
                        || !arguments[2].equals(DELAYED_FUNCTION) || !arguments[3].equals("50")) return false;
                Spiral3NativeController controller = INSTANCES.get(arguments[1]);
                if (controller == null || controller.closed) return false;
                if (trustedMechanics && controller.mechanicsSender != origin) return false;
                if (caller instanceof BlockCommandSender block && block.getBlock().getWorld() != controller.world) return false;
                // Native schedule replaces an earlier close; tasks never outlive their match.
                controller.delayedTasks.forEach(BukkitTask::cancel);
                controller.delayedTasks.clear();
                BukkitTask task = Bukkit.getScheduler().runTaskLater(controller.plugin, () -> {
                    controller.delayedTasks.clear();
                    if (!controller.closed) controller.call(DELAYED_FUNCTION);
                }, 50);
                controller.delayedTasks.add(task);
                return true;
            }
        };
        Bukkit.getServer().getCommandMap().register("minicat", bridge);
        commandRegistered = true;
    }
}
