package com.biel.lobby.mapes;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.biel.lobby.mapes.jocs.Quakecraft;

class JocImmunityTest {
    @BeforeAll static void installServer() throws Exception {
        PluginManager plugins = (PluginManager) Proxy.newProxyInstance(PluginManager.class.getClassLoader(),
                new Class<?>[]{PluginManager.class}, (object, method, arguments) -> null);
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        server.set(null, Proxy.newProxyInstance(Server.class.getClassLoader(), new Class<?>[]{Server.class},
                (object, method, arguments) -> method.getName().equals("getPluginManager") ? plugins : null));
    }

    @Test void beingIdleDoesNotRestoreDamageImmunity() {
        TestGame game = new TestGame();
        Joc.PlayerInfo info = game.new PlayerInfo();
        info.setImmune(false);
        info.lastMoveEvent = ZonedDateTime.now().minusMinutes(1);
        assertTrue(info.isAFK());
        assertFalse(info.isImmune());
    }

    @Test void explicitSpawnProtectionStillWorksAndCanBeClearedWhileIdle() {
        TestGame game = new TestGame();
        Joc.PlayerInfo info = game.new PlayerInfo();
        info.lastMoveEvent = ZonedDateTime.now().minusMinutes(1);
        assertTrue(info.isImmune());
        info.setImmune(false);
        assertFalse(info.isImmune());
        info.setImmune(true);
        assertTrue(info.isImmune());
    }

    @Test void quakecraftRailgunChainCanContinueThroughAnIdleUnprotectedPlayer() throws Exception {
        TestGame game = new TestGame();
        game.info.setImmune(false);
        game.info.lastMoveEvent = ZonedDateTime.now().minusMinutes(1);
        Player next = player(2, List.of());
        Player idle = player(1, List.of(next));
        Entity origin = player(0, List.of(idle));
        Method affected = Quakecraft.class.getDeclaredMethod("getAffectedEntities", Entity.class, int.class, Double.class);
        affected.setAccessible(true);
        List<?> result = (List<?>) affected.invoke(game, origin, 2, 0D);
        assertTrue(result.contains(next));
        game.info.setImmune(true);
        result = (List<?>) affected.invoke(game, origin, 2, 0D);
        assertFalse(result.contains(next));
    }

    private static final class TestGame extends Quakecraft {
        final PlayerInfo info = new PlayerInfo();
        @Override public PlayerInfo getPlayerInfo(Player player) { return info; }
        @Override public Boolean isSpectator(Player player) { return false; }
    }

    private static Player player(int entityId, List<? extends Entity> nearby) {
        return (Player) Proxy.newProxyInstance(Player.class.getClassLoader(), new Class<?>[]{Player.class},
                (object, method, arguments) -> switch (method.getName()) {
                    case "getEntityId" -> entityId;
                    case "getNearbyEntities" -> new ArrayList<>(nearby);
                    case "isDead" -> false;
                    case "isValid" -> true;
                    case "equals" -> object == arguments[0];
                    case "hashCode" -> entityId;
                    default -> null;
                });
    }
}
