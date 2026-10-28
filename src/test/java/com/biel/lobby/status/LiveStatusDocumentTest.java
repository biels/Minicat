package com.biel.lobby.status;

import com.biel.lobby.status.LiveStatusDocument.InstanceEntry;
import com.biel.lobby.status.LiveStatusDocument.PlayerEntry;
import com.biel.lobby.status.LiveStatusDocument.ServerState;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LiveStatusDocumentTest {
    private static final UUID SESSION = UUID.fromString("0b8f5a52-6c39-4b2e-9a55-3c1d3e0f7a11");
    private static final Instant CAPTURED_AT = Instant.parse("2026-10-28T18:02:11.789Z");

    private static LiveStatusDocument sample() {
        return new LiveStatusDocument(SESSION, ServerState.RUNNING,
                List.of(new PlayerEntry(17, "JoniMega", "RainbowClay2", Instant.parse("2026-10-28T17:51:02Z")),
                        new PlayerEntry(3, "biel", null, Instant.parse("2026-10-28T17:40:13Z"))),
                List.of(new InstanceEntry("RainbowClay2", "RainbowClay", "RainbowClay", "InGame", 5012,
                        Instant.parse("2026-10-28T17:58:40Z"), List.of(17), List.of(), 1)));
    }

    @Test void runningDocumentHasTheSchemaOneShape() {
        JsonObject document = JsonParser.parseString(sample().toJson(CAPTURED_AT)).getAsJsonObject();
        assertEquals(1, document.get("schema").getAsInt());
        assertEquals(SESSION.toString(), document.get("serverSession").getAsString());
        assertEquals("2026-10-28T18:02:11Z", document.get("capturedAt").getAsString());
        assertEquals("running", document.get("state").getAsString());
        JsonObject lobbyPlayer = document.getAsJsonArray("players").get(0).getAsJsonObject();
        assertEquals(3, lobbyPlayer.get("playerId").getAsInt());
        assertTrue(lobbyPlayer.has("place"));
        assertTrue(lobbyPlayer.get("place").isJsonNull());
        JsonObject gamePlayer = document.getAsJsonArray("players").get(1).getAsJsonObject();
        assertEquals("JoniMega", gamePlayer.get("name").getAsString());
        assertEquals("RainbowClay2", gamePlayer.get("place").getAsString());
        assertEquals("2026-10-28T17:51:02Z", gamePlayer.get("onlineSince").getAsString());
        JsonObject instance = document.getAsJsonArray("instances").get(0).getAsJsonObject();
        assertEquals("RainbowClay2", instance.get("world").getAsString());
        assertEquals("RainbowClay", instance.get("game").getAsString());
        assertEquals("RainbowClay", instance.get("map").getAsString());
        assertEquals("InGame", instance.get("state").getAsString());
        assertEquals(5012, instance.get("matchId").getAsInt());
        assertEquals("2026-10-28T17:58:40Z", instance.get("startedAt").getAsString());
        assertEquals(17, instance.getAsJsonArray("players").get(0).getAsInt());
        assertEquals(0, instance.getAsJsonArray("spectators").size());
        assertEquals(1, instance.get("connections").getAsInt());
    }

    @Test void fieldsAreWrittenInTheDocumentedOrder() {
        String json = sample().toJson(CAPTURED_AT);
        assertTrue(json.startsWith("{\"schema\":1,\"serverSession\":\"" + SESSION + "\",\"capturedAt\":\"2026-10-28T18:02:11Z\",\"state\":\"running\",\"players\":["), json);
        assertTrue(json.contains("\"place\":null"), json);
    }

    @Test void anInstanceBeforeItsMatchHasNoMatchOrStart() {
        var waiting = new LiveStatusDocument(SESSION, ServerState.RUNNING, List.of(),
                List.of(new InstanceEntry("Tag1", "Tag", "Tag", "WaitingForPlayers", null, null, List.of(), List.of(), 0)));
        JsonObject instance = JsonParser.parseString(waiting.toJson(CAPTURED_AT)).getAsJsonObject()
                .getAsJsonArray("instances").get(0).getAsJsonObject();
        assertTrue(instance.get("matchId").isJsonNull());
        assertTrue(instance.get("startedAt").isJsonNull());
    }

    @Test void comparableJsonIgnoresTheCaptureTimeOnly() {
        assertFalse(sample().toComparableJson().contains("capturedAt"));
        assertEquals(sample().toComparableJson(), sample().toComparableJson());
        var someoneLeft = new LiveStatusDocument(SESSION, ServerState.RUNNING,
                sample().players().subList(0, 1), sample().instances());
        assertNotEquals(sample().toComparableJson(), someoneLeft.toComparableJson());
    }

    @Test void orderOfCaptureDoesNotChangeTheDocument() {
        var reversed = new LiveStatusDocument(SESSION, ServerState.RUNNING,
                List.of(sample().players().get(1), sample().players().get(0)),
                List.of(new InstanceEntry("RainbowClay2", "RainbowClay", "RainbowClay", "InGame", 5012,
                        Instant.parse("2026-10-28T17:58:40Z"), List.of(17), List.of(), 1)));
        assertEquals(sample().toComparableJson(), reversed.toComparableJson());
        var crowded = new InstanceEntry("A", "G", "G", "InGame", 1, null, List.of(9, 2, 5), List.of(8, 4), 3);
        assertEquals(List.of(2, 5, 9), crowded.players());
        assertEquals(List.of(4, 8), crowded.spectators());
    }

    @Test void stoppedDocumentIsEmpty() {
        JsonObject document = JsonParser.parseString(LiveStatusDocument.stopped(SESSION).toJson(CAPTURED_AT)).getAsJsonObject();
        assertEquals("stopped", document.get("state").getAsString());
        assertEquals(SESSION.toString(), document.get("serverSession").getAsString());
        assertEquals(0, document.getAsJsonArray("players").size());
        assertEquals(0, document.getAsJsonArray("instances").size());
    }
}
