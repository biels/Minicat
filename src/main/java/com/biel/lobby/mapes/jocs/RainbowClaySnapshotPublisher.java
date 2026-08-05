package com.biel.lobby.mapes.jocs;

import com.biel.lobby.agent.AgentSnapshotHttpServer;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocObjectius.EquipObjectius;
import com.biel.lobby.mapes.JocObjectius.Objectiu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

final class RainbowClaySnapshotPublisher {
    private static final long SNAPSHOT_PERIOD_TICKS = 100;
    private static final long SNAPSHOT_TTL_MS = 7_500;

    private final RainbowClay game;
    private final AgentSnapshotHttpServer endpoint;
    private final AtomicLong sequence = new AtomicLong();
    private boolean closed;

    RainbowClaySnapshotPublisher(RainbowClay game, AgentSnapshotHttpServer endpoint) {
        this.game = game;
        this.endpoint = endpoint;
    }

    void start() {
        publish();
        game.scheduleGameplayRepeatingTask(this::publish, SNAPSHOT_PERIOD_TICKS, SNAPSHOT_PERIOD_TICKS);
    }

    void close() {
        if (closed) return;
        closed = true;
        endpoint.unpublish(game.getMapName());
    }

    private void publish() {
        if (closed || game.getWorld() == null || !endpoint.isRunning()) return;
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("RainbowClay snapshots must be captured on Paper's primary thread");
        }
        long currentSequence = sequence.incrementAndGet();
        Instant capturedAt = Instant.now();
        String snapshotId = "s-" + safeId(game.getMapName()) + "-" + currentSequence;
        Map<UUID, AgentSnapshotHttpServer.PublishedSnapshot> snapshots = new LinkedHashMap<>();
        for (Player player : game.getWorld().getPlayers()) {
            String xml = serialize(player, snapshotId, capturedAt);
            snapshots.put(player.getUniqueId(), new AgentSnapshotHttpServer.PublishedSnapshot(
                    player.getUniqueId(), player.getName(), snapshotId, capturedAt, xml));
        }
        endpoint.publish(game.getMapName(), snapshots);
    }

    private String serialize(Player self, String snapshotId, Instant capturedAt) {
        try {
            StringWriter destination = new StringWriter(12_000);
            XMLStreamWriter xml = XMLOutputFactory.newFactory().createXMLStreamWriter(destination);
            World world = game.getWorld();
            Equip selfTeam = game.obtenirEquip(self);

            xml.writeStartDocument("UTF-8", "1.0");
            xml.writeCharacters("\n");
            xml.writeStartElement("mcSnapshot");
            attribute(xml, "serverSessionId", endpoint.getServerSessionId());
            attribute(xml, "gameType", game.getGameName());
            attribute(xml, "instanceId", game.getMapName());
            attribute(xml, "worldId", world.getKey().toString());
            attribute(xml, "botId", self.getUniqueId().toString());
            attribute(xml, "snapshotId", snapshotId);
            attribute(xml, "tick", world.getGameTime());
            attribute(xml, "ts", capturedAt);
            attribute(xml, "ttlMs", SNAPSHOT_TTL_MS);
            attribute(xml, "schema", "1.0");

            emptyElement(xml, "mode", Map.of("current", "UNSPECIFIED", "owner", "controller"));
            appendGame(xml, selfTeam);
            appendPlayers(xml, self, selfTeam);
            appendTeamChat(xml);
            appendRoiBoundary(xml);
            xml.writeEndElement();
            xml.writeCharacters("\n");
            xml.writeEndDocument();
            xml.close();
            return destination.toString();
        } catch (Exception exception) {
            throw new IllegalStateException("Could not serialize RainbowClay snapshot", exception);
        }
    }

    private void appendGame(XMLStreamWriter xml, Equip selfTeam) throws Exception {
        xml.writeStartElement("game");
        attribute(xml, "phase", game.JocEnMarxa() ? "ACTIVE" : "WAITING");
        attribute(xml, "teamMode", "RED_VS_BLUE");
        attribute(xml, "selfTeam", teamId(selfTeam));

        xml.writeStartElement("teams");
        for (Equip team : game.Equips) {
            EquipObjectius objectives = (EquipObjectius) team;
            xml.writeStartElement("team");
            attribute(xml, "id", teamId(team));
            attribute(xml, "relation", team == selfTeam ? "ALLY" : "ENEMY");
            attribute(xml, "players", team.getPlayers().size());
            attribute(xml, "objectivesAlive", objectives.getAliveObjectives().size());
            xml.writeEndElement();
        }
        xml.writeEndElement();

        xml.writeStartElement("objectives");
        for (Equip team : game.Equips) {
            for (Objectiu objective : ((EquipObjectius) team).getObjectius()) {
                Location location = objective.getLocation();
                xml.writeStartElement("objective");
                attribute(xml, "id", objectiveId(team, objective));
                attribute(xml, "type", objectiveType(objective));
                attribute(xml, "owner", teamId(team));
                attribute(xml, "state", objective.isCompleted() ? "COMPLETED" : "ALIVE");
                attribute(xml, "x", location.getBlockX());
                attribute(xml, "y", location.getBlockY());
                attribute(xml, "z", location.getBlockZ());
                if (objective.getCompleter() != null) {
                    attribute(xml, "completedBy", objective.getCompleter().getUniqueId());
                }
                xml.writeEndElement();
            }
        }
        xml.writeEndElement();
        xml.writeEndElement();
    }

    private void appendPlayers(XMLStreamWriter xml, Player self, Equip selfTeam) throws Exception {
        xml.writeStartElement("players");
        for (Player player : game.getWorld().getPlayers()) {
            Equip team = game.obtenirEquip(player);
            Location location = player.getLocation();
            xml.writeStartElement("player");
            attribute(xml, "id", player.getUniqueId());
            attribute(xml, "name", player.getName());
            attribute(xml, "isSelf", player == self);
            attribute(xml, "team", teamId(team));
            attribute(xml, "relation", player == self ? "SELF" : team != null && team == selfTeam ? "ALLY" : "ENEMY");

            emptyElement(xml, "pos", Map.of(
                    "x", decimal(location.getX()),
                    "y", decimal(location.getY()),
                    "z", decimal(location.getZ()),
                    "yaw", decimal(location.getYaw()),
                    "pitch", decimal(location.getPitch()),
                    "onGround", Boolean.toString(player.isOnGround())));
            emptyElement(xml, "state", Map.of(
                    "health", decimal(player.getHealth()),
                    "hunger", Integer.toString(player.getFoodLevel()),
                    "air", Integer.toString(player.getRemainingAir()),
                    "gamemode", player.getGameMode().name().toLowerCase(Locale.ROOT),
                    "alive", Boolean.toString(!player.isDead())));
            if (player == self) {
                appendInventory(xml, player);
                appendEffects(xml, player);
            }
            xml.writeEndElement();
        }
        xml.writeEndElement();
    }

    private void appendInventory(XMLStreamWriter xml, Player player) throws Exception {
        xml.writeStartElement("inventory");
        attribute(xml, "selectedSlot", player.getInventory().getHeldItemSlot());
        for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item == null || item.getType().isAir()) continue;
            xml.writeStartElement("slot");
            attribute(xml, "index", slot);
            attribute(xml, "itemId", item.getType().getKey().toString());
            attribute(xml, "count", item.getAmount());
            xml.writeEndElement();
        }
        xml.writeEndElement();
    }

    // Published for the bot itself only. The spawn window is short and large —
    // Strength 3 for 5s, Resistance 1 for ~19s, Slowness 1 for the first 3s —
    // so an engagement inside it is favourable in a way it will not be forty
    // seconds later. Without remaining durations the bot cannot see the window
    // it is standing in.
    private void appendEffects(XMLStreamWriter xml, Player player) throws Exception {
        xml.writeStartElement("effects");
        for (PotionEffect effect : player.getActivePotionEffects()) {
            xml.writeStartElement("effect");
            attribute(xml, "id", effect.getType().getKey().toString());
            // Bukkit amplifiers are zero-based; level is what the game displays.
            attribute(xml, "level", effect.getAmplifier() + 1);
            attribute(xml, "remainingMs", effect.getDuration() < 0 ? -1L : effect.getDuration() * 50L);
            xml.writeEndElement();
        }
        xml.writeEndElement();
    }

    private void appendTeamChat(XMLStreamWriter xml) throws Exception {
        xml.writeStartElement("teamChat");
        attribute(xml, "cursor", "0");
        attribute(xml, "status", "not-yet-published");
        xml.writeEndElement();
    }

    private void appendRoiBoundary(XMLStreamWriter xml) throws Exception {
        xml.writeStartElement("roi");
        attribute(xml, "source", "mineflayer-client");
        attribute(xml, "status", "client-side-perception-required");
        xml.writeEndElement();
    }

    private void emptyElement(XMLStreamWriter xml, String name, Map<String, String> attributes) throws Exception {
        xml.writeStartElement(name);
        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            attribute(xml, attribute.getKey(), attribute.getValue());
        }
        xml.writeEndElement();
    }

    private void attribute(XMLStreamWriter xml, String name, Object value) throws Exception {
        xml.writeAttribute(name, String.valueOf(value));
    }

    private String teamId(Equip team) {
        return team == null ? "UNASSIGNED" : team.getColor().name().toLowerCase(Locale.ROOT);
    }

    private String objectiveId(Equip team, Objectiu objective) {
        return teamId(team) + "-" + safeId(objective.getNom());
    }

    private String objectiveType(Objectiu objective) {
        return objective.getClass().getSimpleName().contains("BlockBreak") ? "CORE" : "WOOL_MONUMENT";
    }

    private String safeId(String value) {
        return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
    }

    private String decimal(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}
