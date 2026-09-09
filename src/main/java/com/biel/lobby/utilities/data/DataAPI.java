package com.biel.lobby.utilities.data;

import com.biel.BielAPI.Utils.Pair;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** The existing gameplay storage contract, backed exclusively by PostgreSQL. */
public class DataAPI {
    private static final double UNKNOWN_AVG_GAME_LENGTH_SECONDS = 1800;
    private final DatabaseConfig config;
    private final Logger logger = Logger.getLogger("DataAPI");
    private final Set<String> warnings = ConcurrentHashMap.newKeySet();
    private Connection connection;
    private Connection timestampConnection;
    private volatile boolean unavailable;
    private volatile boolean closed;
    private final ThreadPoolExecutor timestampWriter = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(4096), runnable -> {
                Thread thread = new Thread(runnable, "Minicat timestamp writer");
                thread.setDaemon(true);
                return thread;
            }, (runnable, executor) -> warnOnce("telemetry-full", "Timestamp queue is full or stopped; telemetry was not saved."));

    public DataAPI() { this(DatabaseConfig.load()); }
    public DataAPI(DatabaseConfig config) { this.config = config; }

    /** Refuse to enable gameplay against an absent or unmigrated database. */
    public synchronized void requireReady() {
        try (PreparedStatement statement = mainConnection().prepareStatement(
                "SELECT version FROM minicat_schema_history WHERE version=1")) {
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next()) throw new SQLException("Minicat schema version 1 is not installed");
            }
        } catch (SQLException exception) {
            unavailable = true;
            throw new IllegalStateException("Minicat PostgreSQL is unavailable or its schema is not installed", exception);
        }
    }

    public boolean isInDatalessMode() { return unavailable || closed; }

    private Connection mainConnection() throws SQLException {
        if (closed) throw new SQLException("Database is closed", "08003");
        if (connection == null || connection.isClosed() || !connection.isValid(2)) {
            closeQuietly(connection);
            connection = config.open("Minicat gameplay");
        }
        return connection;
    }

    @FunctionalInterface private interface DatabaseOperation<T> { T run(Connection connection) throws SQLException; }
    private synchronized <T> T execute(String operation, T fallback, DatabaseOperation<T> action) {
        try {
            T result = action.run(mainConnection());
            unavailable = false;
            warnings.remove(operation);
            return result;
        } catch (SQLException exception) {
            if (exception.getSQLState() != null && exception.getSQLState().startsWith("08")) {
                unavailable = true;
                closeQuietly(connection);
                connection = null;
            }
            warnOnce(operation, "Database operation failed: " + operation + " (SQLSTATE " + exception.getSQLState() + ")");
            return fallback;
        }
    }

    private void warnOnce(String key, String message) { if (warnings.add(key)) logger.warning(message); }
    private static void closeQuietly(Connection connection) {
        if (connection != null) try { connection.close(); } catch (SQLException ignored) { }
    }

    public void closeConnection() {
        timestampWriter.shutdown();
        try {
            if (!timestampWriter.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warning("Timestamp writer did not drain; remaining telemetry will be discarded.");
                timestampWriter.shutdownNow();
                timestampWriter.awaitTermination(6, TimeUnit.SECONDS);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            timestampWriter.shutdownNow();
        }
        synchronized (this) {
            closed = true;
            closeQuietly(connection);
            connection = null;
        }
        // A worker exceeding the shutdown timeout closes its connection when its final job exits.
        if (timestampWriter.isTerminated()) closeQuietly(timestampConnection);
    }

    public void registerNewPlayer(Player player) { registerNewPlayer(player.getName()); }
    public boolean registerNewPlayer(String name) {
        // Existing usernames are not unique in the legacy schema. Serialize check/insert
        // across server connections without changing the historical key constraints.
        return execute("register player", false, database -> {
            database.setAutoCommit(false);
            try {
                try (PreparedStatement lock = database.prepareStatement("SELECT pg_advisory_xact_lock(1296253268)")) { lock.execute(); }
                try (PreparedStatement insert = database.prepareStatement("""
                        INSERT INTO players(username, money, score, elo)
                        SELECT ?, 0, 0, COALESCE((SELECT AVG(elo) FROM players), 1200)
                        WHERE NOT EXISTS (SELECT 1 FROM players WHERE rtrim(username)=rtrim(?) COLLATE minicat_name)
                        """)) {
                    insert.setString(1, name); insert.setString(2, name); insert.executeUpdate();
                }
                database.commit();
                return true;
            } catch (SQLException exception) {
                database.rollback();
                throw exception;
            } finally { database.setAutoCommit(true); }
        });
    }

    public int getPlayerId(String name) {
        return execute("player id", -1, database -> {
            try (PreparedStatement statement = database.prepareStatement(
                    "SELECT player_id FROM players WHERE rtrim(username)=rtrim(?) COLLATE minicat_name ORDER BY player_id LIMIT 1")) {
                statement.setString(1, name);
                try (ResultSet rows = statement.executeQuery()) { return rows.next() ? rows.getInt(1) : -1; }
            }
        });
    }

    public String getPlayerName(int id) {
        return execute("player name", "[NotOnDB]", database -> {
            try (PreparedStatement statement = database.prepareStatement("SELECT username FROM players WHERE player_id=?")) {
                statement.setInt(1, id);
                try (ResultSet rows = statement.executeQuery()) { return rows.next() ? rows.getString(1) : "[NotOnDB]"; }
            }
        });
    }

    private OptionalDouble readPlayerValue(int id, String column) {
        return execute("read " + column, OptionalDouble.empty(), database -> {
            try (PreparedStatement statement = database.prepareStatement("SELECT " + column + " FROM players WHERE player_id=?")) {
                statement.setInt(1, id);
                try (ResultSet rows = statement.executeQuery()) { return rows.next() ? OptionalDouble.of(rows.getDouble(1)) : OptionalDouble.empty(); }
            }
        });
    }
    private boolean writePlayerValue(int id, String column, double value, boolean increment) {
        if (!Double.isFinite(value)) return false;
        return execute("write " + column, false, database -> {
            String assignment = increment ? column + "+?" : "?";
            String lastPlayed = column.equals("money") ? "" : ", last_played=statement_timestamp()";
            try (PreparedStatement statement = database.prepareStatement(
                    "UPDATE players SET " + column + "=" + assignment + lastPlayed + " WHERE player_id=?")) {
                statement.setDouble(1, value); statement.setInt(2, id);
                return statement.executeUpdate() == 1;
            }
        });
    }
    public double getMoney(int id) { return readPlayerValue(id, "money").orElse(0); }
    public boolean setMoney(int id, double value) { return writePlayerValue(id, "money", value, false); }
    public double getScore(int id) { return readPlayerValue(id, "score").orElse(0); }
    public boolean setScore(int id, double value) { return writePlayerValue(id, "score", value, false); }
    public OptionalDouble readElo(int id) { return readPlayerValue(id, "elo"); }
    public boolean setElo(int id, double value) { return writePlayerValue(id, "elo", value, false); }
    public boolean addElo(int id, double value) { return writePlayerValue(id, "elo", value, true); }
    public double getAvgElo() {
        return execute("average elo", 1200D, database -> {
            try (PreparedStatement statement = database.prepareStatement("SELECT COALESCE(AVG(elo), 1200) FROM players");
                 ResultSet rows = statement.executeQuery()) { rows.next(); return rows.getDouble(1); }
        });
    }

    public ArrayList<Integer> getRanking() {
        return execute("ranking", new ArrayList<>(), database -> {
            ArrayList<Integer> ranking = new ArrayList<>();
            try (PreparedStatement statement = database.prepareStatement(
                    "SELECT player_id FROM players WHERE last_played > statement_timestamp() - INTERVAL '15 days' ORDER BY elo DESC, player_id");
                 ResultSet rows = statement.executeQuery()) { while (rows.next()) ranking.add(rows.getInt(1)); }
            return ranking;
        });
    }

    public int getGameId(String name) {
        return execute("game id", -1, database -> {
            try (PreparedStatement insert = database.prepareStatement(
                    "INSERT INTO games(name) VALUES (?) ON CONFLICT DO NOTHING")) { insert.setString(1, name); insert.executeUpdate(); }
            try (PreparedStatement statement = database.prepareStatement(
                    "SELECT game_id FROM games WHERE rtrim(name)=rtrim(?) COLLATE minicat_name")) {
                statement.setString(1, name);
                try (ResultSet rows = statement.executeQuery()) { return rows.next() ? rows.getInt(1) : -1; }
            }
        });
    }

    public ArrayList<Pair<String, Double>> getAutoRating() {
        return execute("automatic rating", new ArrayList<>(), database -> {
            ArrayList<Pair<String, Double>> ratings = new ArrayList<>();
            try (PreparedStatement statement = database.prepareStatement("""
                    WITH durations AS (
                        SELECT games.game_id, games.name,
                               SUM(EXTRACT(EPOCH FROM (end_time AT TIME ZONE 'Europe/Madrid')-(start_time AT TIME ZONE 'Europe/Madrid'))) AS total_time
                        FROM match_history JOIN games USING(game_id)
                        WHERE winner != -1 AND end_time IS NOT NULL
                          AND start_time BETWEEN statement_timestamp()-INTERVAL '1 month' AND statement_timestamp()
                        GROUP BY games.game_id, games.name
                    )
                    SELECT name, COALESCE(total_time*100/NULLIF(MAX(total_time) OVER (), 0), 0) AS auto_rating
                    FROM durations ORDER BY auto_rating DESC, name
                    """); ResultSet rows = statement.executeQuery()) {
                while (rows.next()) ratings.add(new Pair<>(rows.getString(1), rows.getDouble(2)));
            }
            return ratings;
        });
    }

    public int getMapId(String name, int gameId) {
        if (gameId < 1) return -1;
        return execute("map id", -1, database -> {
            try (PreparedStatement insert = database.prepareStatement(
                    "INSERT INTO maps(game_id, map_name) VALUES (?, ?) ON CONFLICT DO NOTHING")) {
                insert.setInt(1, gameId); insert.setString(2, name); insert.executeUpdate();
            }
            try (PreparedStatement statement = database.prepareStatement(
                    "SELECT map_id, game_id FROM maps WHERE rtrim(map_name)=rtrim(?) COLLATE minicat_name")) {
                statement.setString(1, name);
                try (ResultSet rows = statement.executeQuery()) {
                    if (!rows.next()) return -1;
                    if (rows.getInt(2) != gameId) throw new SQLException("Map name belongs to a different game", "23514");
                    return rows.getInt(1);
                }
            }
        });
    }

    public MatchData registerMatchStart(int gameId, int mapId, String teams) {
        if (gameId < 1 || mapId < 1) return new DatalessMatchData();
        return execute("match start", new DatalessMatchData(), database -> {
            try (PreparedStatement statement = database.prepareStatement(
                    "INSERT INTO match_history(game_id, map_id, teams) VALUES (?, ?, ?) RETURNING match_id")) {
                statement.setInt(1, gameId); statement.setInt(2, mapId); statement.setString(3, teams);
                try (ResultSet rows = statement.executeQuery()) { rows.next(); return new MatchData(rows.getInt(1)); }
            }
        });
    }
    public boolean registerMatchEnd(int matchId, int winner) {
        return execute("match end", false, database -> {
            try (PreparedStatement statement = database.prepareStatement(
                    "UPDATE match_history SET end_time=statement_timestamp(), winner=? WHERE match_id=?")) {
                statement.setInt(1, winner); statement.setInt(2, matchId); return statement.executeUpdate() == 1;
            }
        });
    }
    public double getAvgGameLength(int gameId) {
        return execute("average duration", UNKNOWN_AVG_GAME_LENGTH_SECONDS, database -> {
            try (PreparedStatement statement = database.prepareStatement(
                    "SELECT AVG(EXTRACT(EPOCH FROM (end_time AT TIME ZONE 'Europe/Madrid')-(start_time AT TIME ZONE 'Europe/Madrid'))) FROM match_history WHERE game_id=? AND winner != -1 AND end_time IS NOT NULL")) {
                statement.setInt(1, gameId);
                try (ResultSet rows = statement.executeQuery()) {
                    rows.next(); double seconds = rows.getDouble(1);
                    return rows.wasNull() || seconds <= 0 ? UNKNOWN_AVG_GAME_LENGTH_SECONDS : seconds;
                }
            }
        });
    }

    public void registerTimestamp(int matchId, int playerId, int frameId, int kills, int deaths, double damageDealt,
                                  boolean isAlive, String itemInHand, int blocksPlaced, int blocksBroken, int objectivesCompleted, int spree) {
        if (closed || timestampWriter.isShutdown() || matchId < 1 || playerId < 1) return;
        timestampWriter.execute(() -> {
            try {
                if (timestampConnection == null || timestampConnection.isClosed() || !timestampConnection.isValid(1)) {
                    closeQuietly(timestampConnection);
                    timestampConnection = config.open("Minicat telemetry");
                }
                try (PreparedStatement statement = timestampConnection.prepareStatement("""
                        INSERT INTO player_match_timestamps(match_id, player_id, frame_id, kills, deaths, damage_dealt,
                          is_alive, item_in_hand, blocks_placed, blocks_broken, objectives_completed, spree)
                        VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, ?)
                        """)) {
                    statement.setInt(1, matchId); statement.setInt(2, playerId); statement.setInt(3, frameId);
                    statement.setInt(4, kills); statement.setInt(5, deaths); statement.setDouble(6, damageDealt);
                    statement.setBoolean(7, isAlive); statement.setInt(8, blocksPlaced); statement.setInt(9, blocksBroken);
                    statement.setInt(10, objectivesCompleted); statement.setInt(11, spree); statement.executeUpdate();
                }
                warnings.remove("telemetry");
            } catch (SQLException exception) {
                warnOnce("telemetry", "Timestamp persistence failed (SQLSTATE " + exception.getSQLState() + ")");
                closeQuietly(timestampConnection);
                timestampConnection = null;
            } finally {
                if (timestampWriter.isShutdown() && timestampWriter.getQueue().isEmpty()) closeQuietly(timestampConnection);
            }
        });
    }
}
