package com.biel.lobby;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RankedCommandTest {
    private final Command rankedCommand = new Command("r") {
        @Override public boolean execute(CommandSender sender, String label, String[] arguments) { return false; }
    };

    @BeforeAll static void installServer() throws Exception {
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        server.set(null, Proxy.newProxyInstance(Server.class.getClassLoader(), new Class<?>[]{Server.class},
                (object, method, arguments) -> method.getName().equals("getOnlinePlayers") ? List.of() : null));
    }

    @Test void ordinaryPlayerCannotChangeEitherRankedState() throws Exception {
        lobby plugin = plugin();
        for (boolean ranked : new boolean[]{true, false}) {
            plugin.ranked = ranked;
            assertTrue(plugin.onCommand(sender(Player.class, false), rankedCommand, "r", new String[0]));
            assertEquals(ranked, plugin.isInRankedMode());
        }
    }

    @Test void operatorCanToggleRankedModeInBothDirections() throws Exception {
        lobby plugin = plugin();
        assertTrue(plugin.onCommand(sender(Player.class, true), rankedCommand, "r", new String[0]));
        assertFalse(plugin.isInRankedMode());
        plugin.onCommand(sender(Player.class, true), rankedCommand, "r", new String[0]);
        assertTrue(plugin.isInRankedMode());
    }

    @Test void consoleCanToggleRankedMode() throws Exception {
        lobby plugin = plugin();
        assertTrue(plugin.onCommand(sender(ConsoleCommandSender.class, true), rankedCommand, "r", new String[0]));
        assertFalse(plugin.isInRankedMode());
    }

    private static lobby plugin() throws Exception {
        Field singleton = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        singleton.setAccessible(true);
        lobby plugin = (lobby) ((sun.misc.Unsafe) singleton.get(null)).allocateInstance(lobby.class);
        plugin.ranked = true;
        return plugin;
    }

    private static <T extends CommandSender> T sender(Class<T> type, boolean operator) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type},
                (object, method, arguments) -> method.getName().equals("isOp") ? operator : null));
    }
}
