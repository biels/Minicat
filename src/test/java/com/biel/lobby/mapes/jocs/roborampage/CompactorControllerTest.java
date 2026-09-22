package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

class CompactorControllerTest {
    @BeforeAll static void registries() throws Exception { MobilityAndCutterControllerTest.registries(); }

    @Test void slamHitsGroundedParticipantsButMissesJumpersCoverAndOutsideWarning() {
        for (int scenario = 0; scenario < 5; scenario++) {
            Fixture f = new Fixture();
            f.tick(60);
            assertFalse(f.ai);
            assertEquals(BossBar.Color.YELLOW, f.bars.getFirst().color());
            switch (scenario) {
                case 1 -> f.grounded = false;
                case 2 -> f.lineOfSight = false;
                case 3 -> f.location.setX(6);
                case 4 -> f.active = false;
            }
            f.tick(30);
            assertEquals(scenario == 0 ? List.of(30.0) : List.of(), f.damage);
            if (scenario == 0) {
                assertEquals(1.5, f.controller.incomingDamageMultiplier());
                assertEquals(BossBar.Color.GREEN, f.bars.getFirst().color());
                assertEquals(1, f.knockbacks);
            }
        }
    }

    @Test void throwKeepsItsOriginalAimAndTaserInterruptRemovesTheAttack() {
        Fixture moving = new Fixture();
        moving.location.setX(8);
        moving.tick(60);
        moving.location.setX(-8);
        moving.tick(30);
        assertEquals(1, moving.throwsAt.size());
        assertEquals(8, moving.throwsAt.getFirst().getX());
        assertTrue(moving.damage.isEmpty());
        Fixture interrupted = new Fixture();
        interrupted.location.setY(8);
        interrupted.tick(80);
        interrupted.energized = true;
        interrupted.tick(10);
        assertTrue(interrupted.throwsAt.isEmpty());
        assertEquals(1.5, interrupted.controller.incomingDamageMultiplier());
        assertEquals(BossBar.Color.GREEN, interrupted.bars.getFirst().color());
    }

    @Test void losingTargetCancelsWarningAndCleanupRemovesBarsAndRestoresAI() {
        Fixture f = new Fixture();
        f.tick(60);
        assertFalse(f.ai);
        f.active = false;
        f.tick(30);
        assertTrue(f.ai);
        assertEquals(1, f.hiddenBars);
        assertTrue(f.damage.isEmpty());
        f.active = true;
        f.tick(40);
        assertEquals(2, f.bars.size());
        f.controller.close();
        f.controller.close();
        assertTrue(f.ai);
        assertEquals(2, f.hiddenBars);
        assertFalse(f.controller.owns(f.bossId));
        f.tick(100);
        assertTrue(f.damage.isEmpty());
    }

    @Test void enrageAnnouncesOnlyOnceAndDeathRemovesBossState() {
        Fixture f = new Fixture();
        f.health = 120;
        f.tick(40);
        assertEquals(1, f.messages);
        f.dead = true;
        f.tick(1);
        assertEquals(1, f.hiddenBars);
        assertFalse(f.controller.owns(f.bossId));
    }

    private static final class Fixture {
        final UUID bossId = UUID.randomUUID(), playerId = UUID.randomUUID();
        final World world = stub(World.class, (method, args) -> null);
        final Location location = new Location(world, 2, 1, 0);
        final List<Double> damage = new ArrayList<>();
        final List<Location> throwsAt = new ArrayList<>();
        final List<BossBar> bars = new ArrayList<>();
        boolean grounded = true, active = true, ai = true, lineOfSight = true, energized, dead;
        int hiddenBars, knockbacks, messages;
        double health = 240;
        final Player player = stub(Player.class, (method, args) -> switch (method) {
            case "getUniqueId" -> playerId;
            case "getWorld" -> world;
            case "getLocation" -> location.clone();
            case "getEyeLocation" -> location.clone().add(0, 1.62, 0);
            case "isOnGround" -> grounded;
            case "damage" -> { damage.add((Double) args[0]); yield null; }
            case "setVelocity" -> { knockbacks++; yield null; }
            case "showBossBar" -> { bars.add((BossBar) args[0]); yield null; }
            case "hideBossBar" -> { hiddenBars++; yield null; }
            case "sendMessage" -> { messages++; yield null; }
            default -> null;
        });
        final IronGolem boss = stub(IronGolem.class, (method, args) -> switch (method) {
            case "getUniqueId" -> bossId;
            case "getWorld" -> world;
            case "getLocation" -> new Location(world, 0, 1, 0);
            case "getEyeLocation" -> new Location(world, 0, 3.4, 0);
            case "getVelocity" -> new Vector();
            case "getHealth" -> health;
            case "isValid" -> true;
            case "isDead" -> dead;
            case "hasLineOfSight" -> lineOfSight;
            case "setAI" -> { ai = (Boolean) args[0]; yield null; }
            default -> null;
        });
        final ScaffoldController scaffolds = new ScaffoldController(world, new Location(world, 0, 1, 0),
                7, () -> 32, List::of, robot -> 1);
        final CompactorController controller = new CompactorController(world, () -> List.of(player),
                p -> active, id -> energized, (mob, aim) -> throwsAt.add(aim), scaffolds,
                (p, key) -> Component.text(key.name()));
        Fixture() { controller.attach(boss, 240); }
        void tick(int count) { for (int tick = 0; tick < count; tick++) controller.tick(); }
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
            if (method.getReturnType() == double.class) return 0.0;
            return null;
        }));
    }
}
