package com.biel.lobby.utilities.data;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import static org.junit.jupiter.api.Assertions.*;

/** Runs against an explicitly configured disposable PostgreSQL database. */
@EnabledIfEnvironmentVariable(named="MINICAT_TEST_DATABASE_CONFIG", matches=".+")
class PostgresqlIntegrationTest {
    private static DatabaseConfig config;
    private static Connection fixture;
    private static String schema;
    private DataAPI api;

    @BeforeAll static void installSchema() throws Exception {
        var properties = new java.util.Properties();
        try (var reader = Files.newBufferedReader(Path.of(System.getenv("MINICAT_TEST_DATABASE_CONFIG")))) { properties.load(reader); }
        String url = properties.getProperty("url");
        if (!url.matches("jdbc:postgresql://[^/]+/minicat_test")) throw new IllegalStateException("Integration tests require a dedicated minicat_test database");
        config = new DatabaseConfig(url, properties.getProperty("user"), properties.getProperty("password"));
        fixture = config.open("Minicat test fixture");
        schema = "test_" + java.util.UUID.randomUUID().toString().replace("-", "");
        try (Statement statement = fixture.createStatement()) {
            statement.execute("CREATE SCHEMA " + schema);
            statement.execute("SET search_path TO " + schema);
            String ddl = new String(PostgresqlIntegrationTest.class.getResourceAsStream("/db/migration/V001__postgresql_baseline.sql").readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            statement.execute(ddl);
            statement.execute("INSERT INTO minicat_schema_history VALUES (1,'test',repeat('0',64),now())");
        }
        config = new DatabaseConfig(url + "?currentSchema=" + schema, config.user(), config.password());
    }
    @AfterAll static void removeSchema() throws Exception {
        if (fixture != null) {
            try (Statement statement = fixture.createStatement()) { if (schema != null) statement.execute("DROP SCHEMA " + schema + " CASCADE"); }
            fixture.close();
        }
    }
    @BeforeEach void prepare() throws Exception {
        fixture.createStatement().execute("TRUNCATE player_match_timestamps, match_history, maps, games, players, map_ratings RESTART IDENTITY CASCADE");
        api = new DataAPI(config);
        api.requireReady();
    }
    @AfterEach void close() { api.closeConnection(); }

    @Test void playerValuesAndNameMatching() {
        assertEquals(1200, api.getAvgElo());
        assertTrue(api.registerNewPlayer("Pepe"));
        int player = api.getPlayerId("PEPE ");
        assertTrue(player > 0);
        assertTrue(api.registerNewPlayer("pepe"));
        assertEquals(player, api.getPlayerId("pepe"));
        assertEquals("Pepe", api.getPlayerName(player));
        assertEquals(1200, api.readElo(player).orElseThrow());
        assertTrue(api.setMoney(player, 12.75));
        assertEquals(12.75, api.getMoney(player));
        assertTrue(api.setScore(player, 10.25));
        assertEquals(10.25, api.getScore(player));
        assertFalse(api.addElo(99999, 15));
        assertTrue(api.readElo(99999).isEmpty());
        assertFalse(api.setElo(player, Double.NaN));
    }

    @Test void mapIdAndGlobalNameConflict() {
        int game = api.getGameId("Café");
        assertEquals(game, api.getGameId("CAFE "));
        int secondGame = api.getGameId("Other");
        int map = api.getMapId("Shared", secondGame);
        assertEquals(1, map);
        assertNotEquals(secondGame, map);
        assertEquals(map, api.getMapId("shared ", secondGame));
        assertEquals(-1, api.getMapId("Shared", game));
        assertInstanceOf(DatalessMatchData.class, api.registerMatchStart(game, -1, ""));
    }

    @Test void matchLifecycleAndTelemetryDrain() throws Exception {
        api.registerNewPlayer("Recorder");
        int player = api.getPlayerId("Recorder");
        int game = api.getGameId("OD");
        int map = api.getMapId("Arena", game);
        MatchData match = api.registerMatchStart(game, map, Integer.toString(player));
        assertFalse(match instanceof DatalessMatchData);
        for (int i=0; i<25; i++) api.registerTimestamp(match.id, player, i, 3, 2, 1.25, i%2==0, "minecraft:air", 4, 5, 6, 7);
        assertTrue(api.registerMatchEnd(match.id, player));
        api.closeConnection();
        try (ResultSet rows = fixture.createStatement().executeQuery("SELECT count(*),sum(damage_dealt),sum(item_in_hand),count(*) FILTER(WHERE is_alive) FROM player_match_timestamps")) {
            rows.next(); assertEquals(25, rows.getInt(1)); assertEquals(31.25, rows.getDouble(2)); assertEquals(0, rows.getInt(3)); assertEquals(13, rows.getInt(4));
        }
        try (ResultSet rows = fixture.createStatement().executeQuery("SELECT map_id, winner, end_time IS NOT NULL FROM match_history")) {
            rows.next(); assertEquals(map, rows.getInt(1)); assertEquals(player, rows.getInt(2)); assertTrue(rows.getBoolean(3));
        }
    }

    @Test void reportsKeepEligibilityAndDurations() throws Exception {
        api.registerNewPlayer("Recent"); api.registerNewPlayer("Old");
        int recent = api.getPlayerId("Recent");
        fixture.createStatement().execute("UPDATE players SET last_played=now()-interval '15 days 1 second' WHERE username='Old'");
        assertEquals(java.util.List.of(recent), api.getRanking());
        int first = api.getGameId("First"); int second = api.getGameId("Second");
        try (PreparedStatement statement = fixture.prepareStatement("INSERT INTO match_history(game_id,map_id,teams,start_time,end_time,winner) VALUES (?,NULL,'',now()-interval '120 seconds',now()-interval '60 seconds',1), (?,NULL,'',now()-interval '120 seconds',now()-interval '90 seconds',1), (?,NULL,'',now()-interval '1000 seconds',now(),-1), (?,NULL,'',now(),NULL,NULL)")) {
            statement.setInt(1,first);statement.setInt(2,second);statement.setInt(3,second);statement.setInt(4,second);statement.executeUpdate();
        }
        assertEquals(60,api.getAvgGameLength(first)); assertEquals(30,api.getAvgGameLength(second));
        var ratings=api.getAutoRating();
        assertEquals(2,ratings.size());assertEquals("First",ratings.get(0).getFirst());assertEquals(100,ratings.get(0).getSecond());assertEquals(50,ratings.get(1).getSecond());
        assertEquals(1800,api.getAvgGameLength(99999));
    }

    @Test void durationReportsPreserveLegacyLocalClockAcrossDst() throws Exception {
        int game=api.getGameId("DST");
        try (PreparedStatement statement=fixture.prepareStatement("INSERT INTO match_history(game_id,teams,start_time,end_time,winner) VALUES (?,'','2026-03-29 00:30:00+00','2026-03-29 01:30:00+00',1)")) {
            statement.setInt(1,game); statement.executeUpdate();
        }
        // MySQL TIMEDIFF used the server's local clock (01:30 to 03:30).
        assertEquals(7200,api.getAvgGameLength(game));
    }

    @Test void incrementsAreAtomicAcrossConnections() throws Exception {
        api.registerNewPlayer("Concurrent"); int player=api.getPlayerId("Concurrent");
        try (var workers=Executors.newFixedThreadPool(4)) {
            var actions=new ArrayList<Callable<Void>>();
            for(int i=0;i<4;i++) actions.add(() -> {
                DataAPI independent=new DataAPI(config);
                try { for(int j=0;j<10;j++) assertTrue(independent.addElo(player,1)); }
                finally { independent.closeConnection(); }
                return null;
            });
            for(var result:workers.invokeAll(actions)) result.get();
        }
        assertEquals(1240,api.readElo(player).orElseThrow());
    }

    @Test void failedWritesAreNotAcknowledgedAndReconnectWorks() throws Exception {
        api.registerNewPlayer("Reconnect"); int player=api.getPlayerId("Reconnect");
        try (Statement statement=fixture.createStatement()) {
            statement.execute("ALTER TABLE players ADD CONSTRAINT test_rating_limit CHECK(elo<1300)");
            try { assertFalse(api.addElo(player,200)); assertEquals(1200,api.readElo(player).orElseThrow()); }
            finally { statement.execute("ALTER TABLE players DROP CONSTRAINT test_rating_limit"); }
            statement.execute("SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE application_name='Minicat gameplay' AND datname=current_database() AND pid<>pg_backend_pid()");
        }
        // The first operation may detect the closed socket; a subsequent operation must recover.
        api.readElo(player);
        assertEquals(1200,api.readElo(player).orElseThrow());
        assertTrue(api.addElo(player,5));
    }
}
