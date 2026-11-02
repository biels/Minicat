package com.biel.lobby;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.junit.jupiter.api.Test;

class SurvivalEntryTest {
    @Test void theMenuActionUsesTheRegisteredBackendChannelAndExactSurvivalDestination() throws Exception {
        List<Object[]> registrations = new ArrayList<>();
        Messenger messenger = (Messenger) Proxy.newProxyInstance(Messenger.class.getClassLoader(), new Class<?>[]{Messenger.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("registerOutgoingPluginChannel")) registrations.add(arguments);
                    return null;
                });
        Server server = (Server) Proxy.newProxyInstance(Server.class.getClassLoader(), new Class<?>[]{Server.class},
                (proxy, method, arguments) -> method.getName().equals("getMessenger") ? messenger : null);
        Plugin plugin = (Plugin) Proxy.newProxyInstance(Plugin.class.getClassLoader(), new Class<?>[]{Plugin.class},
                (proxy, method, arguments) -> method.getName().equals("getServer") ? server : null);
        SurvivalEntry entry = new SurvivalEntry(plugin);
        assertEquals(1, registrations.size());
        assertSame(plugin, registrations.getFirst()[0]);
        assertEquals("BungeeCord", registrations.getFirst()[1]);

        List<Object[]> outgoing = new ArrayList<>();
        Player player = (Player) Proxy.newProxyInstance(Player.class.getClassLoader(), new Class<?>[]{Player.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("sendPluginMessage")) outgoing.add(arguments);
                    if (method.getName().equals("performCommand")) fail("Travel must use the backend Connect message");
                    return null;
                });
        entry.connect(player);
        assertEquals(1, outgoing.size());
        assertSame(plugin, outgoing.getFirst()[0]);
        assertEquals("BungeeCord", outgoing.getFirst()[1]);
        var message = new DataInputStream(new ByteArrayInputStream((byte[]) outgoing.getFirst()[2]));
        assertEquals("Connect", message.readUTF());
        assertEquals("survival", message.readUTF());
        assertEquals(-1, message.read());
    }
}
