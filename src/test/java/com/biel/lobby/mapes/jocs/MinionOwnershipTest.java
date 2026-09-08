package com.biel.lobby.mapes.jocs;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.minions.Minion;

/** Exercises the real registry when a projectile outlives its shooter. */
public final class MinionOwnershipTest {
    public static void main(String[] args) throws Exception {
        TestGame game = allocate(TestGame.class);
        set(JocEquips.class, game, "minions", new ArrayList<Minion>());
        set(JocEquips.class, game, "minionShots", new HashMap<>());
        Player owner = entity(Player.class, UUID.randomUUID(), null, new boolean[]{true});
        Player teammate = entity(Player.class, UUID.randomUUID(), null, new boolean[]{true});
        JocEquips.Equip team = allocate(JocEquips.Equip.class);
        Mob body = entity(Mob.class, UUID.randomUUID(), null, new boolean[]{true});
        TestMinion minion = new TestMinion(game, team, owner);
        set(Minion.class, minion, "entityId", body.getUniqueId());
        Field registry = JocEquips.class.getDeclaredField("minions");
        registry.setAccessible(true);
        @SuppressWarnings("unchecked") var minions = (ArrayList<Minion>) registry.get(game);
        minions.add(minion);
        boolean[] arrowAlive = {true};
        Projectile arrow = entity(Projectile.class, UUID.randomUUID(), body, arrowAlive);
        require(game.attacker(arrow) == minion && game.teamOf(arrow) == team, "live shooter supplies ownership and team");
        game.launch(new ProjectileLaunchEvent(arrow), arrow);
        game.discharge(minion);
        require(game.minions().isEmpty(), "shooter removed from living army");
        require(game.attacker(arrow) == minion && game.teamOf(arrow) == team, "in-flight arrow retains attribution and friendly-fire team");
        require(minion.isOwnedBy(owner) && !minion.isOwnedBy(teammate), "ownership is per player");
        game.removeShots(teammate);
        require(arrowAlive[0], "teammate death leaves owner's arrow alone");
        game.removeShots(owner);
        require(!arrowAlive[0] && game.attacker(arrow) == null, "owner death removes arrow and its registration");
        Projectile cancelled = entity(Projectile.class, UUID.randomUUID(), body, new boolean[]{true});
        minions.add(minion);
        ProjectileLaunchEvent cancelledLaunch = new ProjectileLaunchEvent(cancelled);
        cancelledLaunch.setCancelled(true);
        game.launch(cancelledLaunch, cancelled);
        game.discharge(minion);
        require(game.attacker(cancelled) == null, "cancelled launch creates no persistent credit");
        System.out.println("Minion projectile ownership, shooter death, owner cleanup and cancelled launches passed");
    }
    private static final class TestGame extends JocEquips {
        @Override public int segonsTranscorreguts() { return 0; }
        @Override public String getGameName() { return "test"; }
        @Override protected void setCustomGameRules() {}
        @Override protected ArrayList<ItemStack> getStartingItems(Player player) { return new ArrayList<>(); }
        @Override protected ArrayList<Equip> getDesiredTeams() { return new ArrayList<>(); }
        Minion attacker(Entity entity) { return attackingMinion(entity); }
        void launch(ProjectileLaunchEvent event, Projectile projectile) { onProjectileLaunch(event, projectile); }
        void removeShots(Player player) { removeMinionShots(player, TestMinion.class); }
    }
    private static final class TestMinion extends Minion {
        TestMinion(JocEquips game, JocEquips.Equip team, Player owner) { super(game, team, owner); }
        @Override protected Mob spawnBody(Location at) { throw new UnsupportedOperationException(); }
        @Override protected void installGoals(Mob mob) {}
        @Override public void remove() {}
    }
    private static <T> T entity(Class<T> type, UUID id, Mob shooter, boolean[] alive) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> switch (method.getName()) {
            case "getUniqueId" -> id;
            case "getName" -> id.toString();
            case "getShooter" -> shooter;
            case "isValid", "isOnline" -> alive[0];
            case "remove" -> { alive[0] = false; yield null; }
            case "equals" -> proxy == args[0];
            case "hashCode" -> id.hashCode();
            default -> method.getReturnType() == boolean.class ? false : method.getReturnType() == int.class ? 0 : null;
        }));
    }
    private static void set(Class<?> type, Object object, String name, Object value) throws Exception {
        Field field = type.getDeclaredField(name); field.setAccessible(true); field.set(object, value);
    }
    private static <T> T allocate(Class<T> type) throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true);
        return type.cast(((sun.misc.Unsafe) field.get(null)).allocateInstance(type));
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
