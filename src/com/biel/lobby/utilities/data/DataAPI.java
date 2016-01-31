package com.biel.lobby.utilities.data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.biel.BielAPI.Utils.Pair;
import com.biel.lobby.Com;
import com.mysql.jdbc.Statement;

public class DataAPI {

	public DataAPI() {

	}

	public Connection connection;

	void openConnection() {
		String host = "localhost";
		String port = "3306";
		String db = "minicat";
		String user = "minicat_usr";
		String password = "minicat";
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host
					+ ":" + port + "/" + db, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void repairConnection(){
		if(connection == null)openConnection();
	}
	public void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registerNewPlayer(Player player) {
		repairConnection();
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
			PreparedStatement sql = connection.prepareStatement("SELECT `username` FROM `players` WHERE player_id=?;");
			sql.setInt(1, id);
			ResultSet result = sql.executeQuery();
			result.next();
			String name = result.getString("username");
			sql.close();
			result.close();

			return name;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return "[NotOnDB]";
	}
	public double getMoney(int id) {

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
		ArrayList<Integer> r = new ArrayList<Integer>();
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
		ArrayList<Pair<String, Double>> r = new ArrayList<Pair<String, Double>>();
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT name, time_list.total_time * 100 /  time_list.max_total_time AS auto_rating FROM	(SELECT games.game_id, games.name, (AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) * count(distinct(match_id))) AS total_time, count(*),count(distinct(match_id)), MAX(t.average) AS max_total_time FROM games, match_history, (SELECT games.game_id, (AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) * count(*)) AS average FROM match_history LEFT JOIN games ON(games.game_id = match_history.game_id) WHERE winner != -1 && start_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() && end_time IS NOT NULL GROUP BY match_history.game_id) AS t	WHERE(games.game_id = match_history.game_id) &&	winner != -1 && start_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() && end_time IS NOT NULL GROUP BY match_history.game_id) AS time_list ORDER BY auto_rating DESC;");
			ResultSet result = sql.executeQuery();
			while(result.next()){
				r.add(new Pair<String, Double>(result.getString("name"), result.getDouble("auto_rating")));
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
		repairConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO `match_history` "
					+ "(`game_id`, `map_id`, `teams`) VALUES "
					+ "(?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, gameId);
			ps.setInt(2, mapId);
			ps.setString(3, teams);
			ps.executeUpdate();
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {return new MatchData(generatedKeys.getInt(1));}
	        }
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void registerMatchEnd(int matchId, int winner) {
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
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) FROM match_history WHERE game_id=? && winner != -1 && end_time IS NOT NULL;");
			sql.setInt(1, gameId);
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
	//TIMESTAMP
	public void registerTimestamp(int matchId, int playerId, int frameId, int kills, int deaths, double damageDealt, boolean isAlive, int itemInHand, int blocksPlaced, int blocksBroken, int objectivesCompleted, int spree) { //Gamemode
		repairConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `player_match_timestamps` "
					+ "(`match_id`, `player_id`, `frame_id`, `kills`, `deaths`, `damage_dealt`, `is_alive`, `item_in_hand`, `blocks_placed`, `blocks_broken`, `objectives_completed`, `spree`) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
			ps.setInt(1, matchId);
			ps.setInt(2, playerId);
			ps.setInt(3, frameId);
			ps.setInt(4, kills);
			ps.setInt(5, deaths);
			ps.setDouble(6, damageDealt);
			ps.setBoolean(7, isAlive);
			ps.setInt(8, itemInHand);
			ps.setInt(9, blocksPlaced);
			ps.setInt(10, blocksBroken);
			ps.setInt(11, objectivesCompleted);
			ps.setInt(12, spree);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
