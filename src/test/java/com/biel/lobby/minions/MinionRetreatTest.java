package com.biel.lobby.minions;

import java.lang.reflect.Proxy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import com.biel.BielAPI.ai.RangedAttackGoal;
import com.biel.BielAPI.ai.Volley;
import com.biel.lobby.mapes.JocEquips;
import com.destroystokyo.paper.entity.Pathfinder;

/** Exercises the shared movement goal without a running server. */
public final class MinionRetreatTest {
    private int tick = 100;
    private double targetDistance = 2;
    private boolean targetValid = true;
    private boolean enemy = true;
    private boolean reachable = true;
    private int rejectedPaths;
    private int shots;
    private int stops;
    private Material floor = Material.STONE;
    private Location destination;
    private final List<Location> pathRequests = new ArrayList<>();
    private World world;
    private Mob mob;
    private LivingEntity target;

    @org.junit.jupiter.api.Test
    void retreatAndFiring() throws Exception {
        installBlockRegistry();
        new MinionRetreatTest().run();
        System.out.println("Minion retreat thresholds, retry, side steps, hazards and firing callback passed");
    }

    private void run() throws Exception {
        world = stub(World.class, (method, args) -> switch (method) {
            case "getBlockAt" -> stub(Block.class, (name, values) -> name.equals("getType")
                    ? (((Location) args[0]).getBlockY() == 0 ? floor : Material.AIR) : null);
            default -> null;
        });
        target = stub(Zombie.class, (method, args) -> switch (method) {
            case "getLocation" -> new Location(world, targetDistance, 1, 0);
            case "getWorld" -> world;
            case "isValid" -> targetValid;
            default -> null;
        });
        Pathfinder pathfinder = stub(Pathfinder.class, (method, args) -> switch (method) {
            case "findPath" -> {
                Location candidate = (Location) args[0];
                pathRequests.add(candidate);
                boolean canReach = reachable && pathRequests.size() > rejectedPaths;
                yield stub(Pathfinder.PathResult.class, (name, values) -> name.equals("canReachFinalPoint") ? canReach : null);
            }
            case "moveTo" -> { destination = pathRequests.getLast(); yield true; }
            case "stopPathfinding" -> { stops++; yield null; }
            default -> null;
        });
        mob = stub(Mob.class, (method, args) -> switch (method) {
            case "getTarget" -> target;
            case "getTicksLived" -> tick;
            case "getLocation" -> new Location(world, 0, 1, 0);
            case "getWorld" -> world;
            case "getPathfinder" -> pathfinder;
            case "hasLineOfSight" -> true;
            default -> null;
        });
        MinionRetreatGoal goal = new MinionRetreatGoal(mob, candidate -> enemy, (shooter, victim) -> shots++);
        require(goal.shouldActivate(), "close enemy activates retreat");
        targetDistance = 4;
        require(!goal.shouldActivate(), "four-block boundary does not start retreat");
        targetDistance = 2;
        enemy = false;
        require(!goal.shouldActivate(), "friendly target does not start retreat");
        enemy = true;
        targetValid = false;
        require(!goal.shouldActivate(), "invalid target does not start retreat");
        targetValid = true;
        goal.start();
        goal.tick();
        require(destination.getX() == -4 && destination.getZ() == 0, "backs directly away on clear ground");
        tick++;
        goal.tick();
        require(shots == 2 && pathRequests.size() == 1, "fires while moving but throttles pathfinding");
        targetDistance = 5;
        require(goal.shouldStayActive(), "continues until six blocks away");
        targetDistance = 6;
        require(!goal.shouldStayActive(), "releases at six blocks");
        targetDistance = 2;
        tick = 180;
        require(!goal.shouldStayActive(), "retreat times out after eighty ticks");
        goal.stop();
        require(stops == 1 && !goal.shouldActivate(), "stops path and yields to approach before retry");
        tick += 10;
        require(goal.shouldActivate(), "retry after ten ticks");
        pathRequests.clear();
        rejectedPaths = 1;
        goal.start();
        goal.tick();
        require(pathRequests.size() == 2 && Math.abs(destination.getZ()) > 1, "tries a side step when straight path is unreachable");
        reachable = false;
        tick += 10;
        goal.tick();
        require(!goal.shouldStayActive(), "unreachable terrain yields to approach");
        for (Material hazard : new Material[]{Material.WATER, Material.AIR, Material.MAGMA_BLOCK, Material.CACTUS, Material.CAMPFIRE, Material.SOUL_CAMPFIRE}) {
            floor = hazard;
            pathRequests.clear();
            goal.start();
            goal.tick();
            require(pathRequests.isEmpty() && !goal.shouldStayActive(), "rejects unsafe floor " + hazard);
        }
        int shotsBefore = shots;
        targetValid = false;
        goal.tick();
        require(shots == shotsBefore, "invalid target is not fired at");
        firingCadence();
    }

    private void firingCadence() throws Exception {
        Field allocatorField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        allocatorField.setAccessible(true);
        TestGame game = (TestGame) ((sun.misc.Unsafe) allocatorField.get(null)).allocateInstance(TestGame.class);
        targetValid = true;
        floor = Material.STONE;
        reachable = true;
        for (boolean hero : new boolean[]{false, true}) {
            SnowmanMinion snowman = new SnowmanMinion(game, null, null, SnowmanKind.NEU, 30, hero, null, (minion, event, player) -> {});
            Field volley = SnowmanMinion.class.getDeclaredField("snowballVolley");
            volley.setAccessible(true);
            List<Integer> firedAt = new ArrayList<>();
            volley.set(snowman, (Volley) (shooter, victim) -> firedAt.add(tick));
            var shoot = SnowmanMinion.class.getDeclaredMethod("shoot", Mob.class, LivingEntity.class);
            shoot.setAccessible(true);
            Volley sharedShot = (shooter, victim) -> {
                try { shoot.invoke(snowman, shooter, victim); }
                catch (ReflectiveOperationException exception) { throw new AssertionError(exception); }
            };
            MinionRetreatGoal retreat = new MinionRetreatGoal(mob, snowman::mayTarget, sharedShot);
            RangedAttackGoal ranged = new RangedAttackGoal(mob, 0, 18, 1, false, 1, sharedShot);
            for (int age : new int[]{0, 60}) {
                Field ageSeconds = SnowmanMinion.class.getDeclaredField("ageSeconds");
                ageSeconds.setAccessible(true);
                ageSeconds.setInt(snowman, age);
                int interval = hero ? (age == 0 ? 10 : 15) : 30;
                firedAt.clear();
                tick += 100;
                retreat.start();
                int startedAt = tick;
                for (; tick <= startedAt + interval * 3; tick++) {
                    // Exercise both callbacks in a tick, stronger than normal goal switching.
                    retreat.tick();
                    ranged.tick();
                }
                require(firedAt.size() == 4, "one shared firing clock: hero=" + hero + ", age=" + age);
                for (int shot = 1; shot < firedAt.size(); shot++) {
                    require(firedAt.get(shot) - firedAt.get(shot - 1) == interval, "preserves regular/surge/post-surge cadence");
                }
            }
        }
    }

    private static final class TestGame extends JocEquips {
        @Override public int segonsTranscorreguts() { return 0; }
        @Override public Equip teamOf(Entity entity) { return null; }
        @Override public Minion minionOf(Entity entity) { return null; }
        @Override public String getGameName() { return "test"; }
        @Override protected void setCustomGameRules() {}
        @Override protected ArrayList<ItemStack> getStartingItems(Player player) { return new ArrayList<>(); }
        @Override protected ArrayList<Equip> getDesiredTeams() { return new ArrayList<>(); }
    }

    @FunctionalInterface private interface Answer { Object call(String method, Object[] args); }
    private static void installBlockRegistry() throws Exception {
        // Material solidity is registry-backed in Paper; supply only the block traits this fixture uses.
        Field allocatorField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        allocatorField.setAccessible(true);
        sun.misc.Unsafe allocator = (sun.misc.Unsafe) allocatorField.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = stub(io.papermc.paper.registry.RegistryAccess.class, (method, args) ->
                method.equals("getRegistry") ? stub(Registry.class, (operation, values) -> {
                    if (!operation.equals("get") && !operation.equals("getOrThrow")) return null;
                    String key = values[0].toString();
                    if (args[0].toString().toLowerCase(java.util.Locale.ROOT).contains("sound")) {
                        return stub(Sound.class, (trait, ignored) -> null);
                    }
                    return stub(BlockType.class, (trait, ignored) -> switch (trait) {
                        case "isSolid" -> !key.equals("minecraft:air") && !key.equals("minecraft:water");
                        case "isAir" -> key.equals("minecraft:air");
                        default -> null;
                    });
                }) : null);
        allocator.putObject(allocator.staticFieldBase(providerField), allocator.staticFieldOffset(providerField), Optional.of(provider));
    }
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
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
