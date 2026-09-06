package com.biel.lobby.utilities.data;


import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.biel.BielAPI.Utils.Pair;
public class DataAPI {
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/minicat?connectTimeout=2000&socketTimeout=3000&useSSL=false";
	private static final String DATABASE_USER = "minicat_usr";
	private static final String DATABASE_PASSWORD = "minicat";
	//MySQL drops a connection that has been idle for wait_timeout (eight hours by
	//default). The Connection object stays non-null once that happens, so liveness
	//has to be asked for rather than inferred from the field being set.
	private static final int CONNECTION_VALIDATION_TIMEOUT_SECONDS = 2;
	//A game whose typical length is unknown counts as a half-hour game rather than a
	//zero-length one. Callers divide by this figure, and zero makes the quotient
	//infinite instead of merely wrong.
	private static final double UNKNOWN_AVG_GAME_LENGTH_SECONDS = 60 * 30;
	boolean datalessMode = false;
	Logger logger = Logger.getLogger("DataAPI");
	private final Set<String> emittedDatalessWarnings = ConcurrentHashMap.newKeySet();
	private final Set<String> emittedTimestampWarnings = ConcurrentHashMap.newKeySet();
	private final ThreadPoolExecutor timestampWriter = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<>(4096), runnable -> {
				Thread thread = new Thread(runnable, "Minicat timestamp writer");
				thread.setDaemon(true);
				return thread;
			}, (runnable, executor) -> warnTimestampOnce("queue-full",
					"Timestamp queue is full; dropping telemetry until the database writer catches up."));
	private Connection timestampConnection;
	public DataAPI() {

	}

	public boolean isInDatalessMode() {
		return datalessMode;
	}
	private void warnDatalessOnce(String operation, String message) {
		if (emittedDatalessWarnings.add(operation)) {
			logger.warning(message);
		}
	}
	public Connection connection;

	void openConnection() {
		try {
			connection = openDatabaseConnection();
		} catch (Exception e) {
			datalessMode = true;
			logger.warning("Could not connect to database; switching to dataless mode: " + e.getMessage());
		}
	}
	public void repairConnection(){
		if(datalessMode)return;
		try {
			if(connection != null && connection.isValid(CONNECTION_VALIDATION_TIMEOUT_SECONDS))return;
		} catch (SQLException exception) {
			//A connection too broken to report its own validity is simply replaced.
		}
		discardUnusableConnection();
		openConnection();
	}
	private void discardUnusableConnection(){
		if(connection == null)return;
		try {
			connection.close();
		} catch (SQLException exception) {
			//It is already unusable; failing to close it changes nothing.
		}
		connection = null;
	}
	public void closeConnection() {
		timestampWriter.shutdown();
		try {
			if (!timestampWriter.awaitTermination(5, TimeUnit.SECONDS)) {
				logger.warning("Timestamp writer did not drain within 5 seconds; remaining telemetry will be discarded.");
				timestampWriter.shutdownNow();
			}
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			timestampWriter.shutdownNow();
		}
		try {
			if (timestampConnection != null) timestampConnection.close();
			if (connection != null) connection.close();
		} catch (SQLException exception) {
			logger.warning("Could not close a database connection cleanly: " + exception.getMessage());
		}
	}
	private Connection openDatabaseConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
	}
	private void warnTimestampOnce(String key, String message) {
		if (emittedTimestampWarnings.add(key)) logger.warning(message);
	}
	
	public void registerNewPlayer(Player player) {
		repairConnection();
		if(datalessMode)return;
		double avgElo = getAvgElo();
		if(getPlayerId(player.getName()) > 0)return;
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `players`(`player_id`,`username`, `money`, `score`, `elo`) VALUES (null,?,0,0,?)");
			ps.setString(1, player.getName());
			ps.setDouble(2, avgElo);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getPlayerId(String player) {
		if(datalessMode){
			warnDatalessOnce("player-id", "Player IDs are unavailable in dataless mode; returning the fallback ID.");
			return -1;
		}
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT `player_id` FROM `players` WHERE username=?;");
			sql.setString(1, player);
			ResultSet result = sql.executeQuery();
			result.next();
			int id = result.getInt("player_id");
			sql.close();
			result.close();

			return id;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return 0;
	}
	public String getPlayerName(int id) { 
		try {
			if(datalessMode){
				return "Player" + id;
			}
			PreparedStatement sql = connection.prepareStatement("SELECT `username` FROM `players` WHERE player_id=?;");
			connection.isValid(2000);
			sql.setInt(1, id);
			ResultSet result = sql.executeQuery();
			result.next();
			String name = result.getString("username");
			sql.close();
			result.close();

			return name;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "[NotOnDB]";
	}
	public double getMoney(int id) {
		if(datalessMode){
			return 0.0;
		}
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT `money` FROM `players` WHERE player_id=?;");
			sql.setInt(1, id);
			ResultSet result = sql.executeQuery();
			result.next();
			double points = result.getInt("money");
			sql.close();
			result.close();

			return points;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void setMoney(int id, double newValue) {
		if(datalessMode){
			logger.info("New value for money of player " + id + " would be " + newValue);
			return;
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `players` SET `money`=? WHERE player_id=?");
			ps.setDouble(1, newValue);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public double getScore(int id) {
		if(datalessMode){
			return 0;
		}
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT `score` FROM `players` WHERE player_id=?;");
			sql.setInt(1, id);
			ResultSet result = sql.executeQuery();
			result.next();
			double points = result.getDouble("score");
			sql.close();
			result.close();

			return points;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void setScore(int id, double newValue) {
		if(datalessMode){
			logger.info("New value for score of player " + id + " would be " + newValue);
			return;
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `players` SET `score`=?, `last_played`=NOW() WHERE player_id=?");
			ps.setDouble(1, newValue);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public double getElo(int id) {
		if(datalessMode){
			return 1200;
		}
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT `elo` FROM `players` WHERE player_id=?;");
			sql.setInt(1, id);
			ResultSet result = sql.executeQuery();
			result.next();
			double points = result.getDouble("elo");
			sql.close();
			result.close();

			return points;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public double getAvgElo() {
		if(datalessMode){
			return 1200;
		}
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT AVG(elo) FROM players;");
			ResultSet result = sql.executeQuery();
			result.next();
			double points = result.getDouble(1);
			sql.close();
			result.close();

			return points;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void setElo(int id, double newValue) {
		if(datalessMode){
			logger.info("New value for elo of player " + id + " would be " + newValue);
			return;
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `players` SET `elo`=?, `last_played`=NOW() WHERE player_id=?");
			ps.setDouble(1, newValue);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Integer> getRanking() {

		ArrayList<Integer> r = new ArrayList<>();
		if(datalessMode){
			warnDatalessOnce("ranking", "Rankings are unavailable in dataless mode; returning an empty ranking.");
			return r;
		}
		repairConnection();
		if(datalessMode || connection == null)return r;
		try { //TODO
			PreparedStatement sql = connection.prepareStatement("SELECT `player_id` FROM `players` WHERE TIMESTAMPDIFF(DAY, players.last_played, NOW()) < 15  ORDER BY `elo` DESC;");
			ResultSet result = sql.executeQuery();
			while(result.next()){
				r.add(result.getInt("player_id"));
			}
			sql.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	//GAME
	private void registerNewGame(String name) { //Gamemode
		repairConnection();
		if(datalessMode)return;
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `games` (`name`) VALUES (?);");
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	public int getGameId(String name) {
		if(datalessMode)return (int) (Math.random() * 100000);
		registerNewGame(name);
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT `game_id` FROM `games` WHERE name=?;");
			sql.setString(1, name);
			ResultSet result = sql.executeQuery();
			result.next();
			int id = result.getInt("game_id");
			sql.close();
			result.close();

			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public ArrayList<Pair<String, Double>> getAutoRating() {
		ArrayList<Pair<String, Double>> r = new ArrayList<>();
		if(datalessMode){
			warnDatalessOnce("automatic-rating", "Automatic game ratings are unavailable in dataless mode; using the configured order.");
			return r;
		}
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT name, time_list.total_time * 100 /  time_list.max_total_time AS auto_rating FROM	(SELECT games.game_id, games.name, (AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) * count(distinct(match_id))) AS total_time, count(*),count(distinct(match_id)), MAX(t.average) AS max_total_time FROM games, match_history, (SELECT games.game_id, (AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) * count(*)) AS average FROM match_history LEFT JOIN games ON(games.game_id = match_history.game_id) WHERE winner != -1 && start_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() && end_time IS NOT NULL GROUP BY match_history.game_id) AS t	WHERE(games.game_id = match_history.game_id) &&	winner != -1 && start_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() && end_time IS NOT NULL GROUP BY match_history.game_id) AS time_list ORDER BY auto_rating DESC;");
			ResultSet result = sql.executeQuery();
			while(result.next()){
				r.add(new Pair<>(result.getString("name"), result.getDouble("auto_rating")));
			}
			sql.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	//MAP
	private void registerNewMap(String name, int gameId) { //Gamemode
		if(datalessMode)return;
		repairConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `maps` (`game_id`, `map_name`) VALUES (?, ?);");
			ps.setInt(1, gameId);
			ps.setString(2, name);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	public int getMapId(String name, int gameId) {
		registerNewMap(name, gameId);
		if(datalessMode)return -1;
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT `game_id` FROM `maps` WHERE map_name=?;");
			sql.setString(1, name);
			ResultSet result = sql.executeQuery();
			result.next();
			int id = result.getInt("game_id");
			sql.close();
			result.close();

			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//MATCH 
	public MatchData registerMatchStart(int gameId, int mapId, String teams) {
		if(datalessMode)return new DatalessMatchData();
		repairConnection();
		try (PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO `match_history` "
					+ "(`game_id`, `map_id`, `teams`) VALUES "
					+ "(?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, gameId);
			ps.setInt(2, mapId);
			ps.setString(3, teams);
			ps.executeUpdate();
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) return new MatchData(generatedKeys.getInt(1));
			}
			logger.severe("The database did not return an id for the new match; continuing without match persistence.");
		} catch (SQLException e) {
			logger.log(java.util.logging.Level.SEVERE,
					"Could not register match start; continuing without match persistence.", e);
		}
		return new DatalessMatchData();
	}
	public void registerMatchEnd(int matchId, int winner) {
		if(datalessMode)return;
		repairConnection();
		try {
			PreparedStatement ps = connection.prepareStatement( //UPDATE `minicat`.`match_history` SET `end_time`=NOW(), `winner`='4' WHERE `match_id`='1';
					"UPDATE `match_history` SET `end_time`=NOW(), `winner`=? WHERE `match_id`=?;");
			ps.setInt(1, winner);
			ps.setInt(2, matchId);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public double getAvgGameLength(int gameId) {
		if(datalessMode)return UNKNOWN_AVG_GAME_LENGTH_SECONDS;
		repairConnection();
		if(datalessMode || connection == null)return UNKNOWN_AVG_GAME_LENGTH_SECONDS;
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) FROM match_history WHERE game_id=? && winner != -1 && end_time IS NOT NULL;");
			sql.setInt(1, gameId);
			ResultSet result = sql.executeQuery();
			result.next();
			double avgGameLengthSeconds = result.getDouble(1);
			boolean gameHasNoFinishedMatches = result.wasNull();
			sql.close();
			result.close();

			if(gameHasNoFinishedMatches || avgGameLengthSeconds <= 0)return UNKNOWN_AVG_GAME_LENGTH_SECONDS;
			return avgGameLengthSeconds;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UNKNOWN_AVG_GAME_LENGTH_SECONDS;
	}
	//TIMESTAMP
	public void registerTimestamp(int matchId, int playerId, int frameId, int kills, int deaths, double damageDealt, boolean isAlive, String itemInHand, int blocksPlaced, int blocksBroken, int objectivesCompleted, int spree) { //Gamemode
		if (datalessMode || timestampWriter.isShutdown()) return;
		timestampWriter.execute(() -> writeTimestamp(matchId, playerId, frameId, kills, deaths, damageDealt,
				isAlive, blocksPlaced, blocksBroken, objectivesCompleted, spree));
	}
	private void writeTimestamp(int matchId, int playerId, int frameId, int kills, int deaths, double damageDealt,
			boolean isAlive, int blocksPlaced, int blocksBroken, int objectivesCompleted, int spree) {
		try {
			if (timestampConnection == null || timestampConnection.isClosed() || !timestampConnection.isValid(1)) {
				timestampConnection = openDatabaseConnection();
			}
			PreparedStatement ps = timestampConnection.prepareStatement("INSERT INTO `player_match_timestamps` "
					+ "(`match_id`, `player_id`, `frame_id`, `kills`, `deaths`, `damage_dealt`, `is_alive`, `item_in_hand`, `blocks_placed`, `blocks_broken`, `objectives_completed`, `spree`) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
			ps.setInt(1, matchId);
			ps.setInt(2, playerId);
			ps.setInt(3, frameId);
			ps.setInt(4, kills);
			ps.setInt(5, deaths);
			ps.setDouble(6, damageDealt);
			ps.setBoolean(7, isAlive);
			// Recovered history stores legacy numeric material ids. Modern Paper
			// exposes namespaced keys, so use the legacy "unknown" value until the
			// telemetry column receives a dedicated schema migration.
			ps.setInt(8, 0);
			ps.setInt(9, blocksPlaced);
			ps.setInt(10, blocksBroken);
			ps.setInt(11, objectivesCompleted);
			ps.setInt(12, spree);
			ps.executeUpdate();
			ps.close();
			
			emittedTimestampWarnings.clear();
		} catch (SQLException exception) {
			warnTimestampOnce("sql-" + exception.getErrorCode(),
					"Timestamp persistence is temporarily unavailable: " + exception.getMessage());
			try {
				if (timestampConnection != null) timestampConnection.close();
			} catch (SQLException ignored) {
			}
			timestampConnection = null;
		}
	}
}
