package com.biel.lobby.utilities.data;

import com.biel.lobby.mapes.JocEquips;
import org.bukkit.entity.Player;

/**
 * Created by Biel on 2/1/2017.
 */
public class DatalessMatchData extends MatchData {
    public DatalessMatchData() {
        super(-1);
    }

    @Override
    public void registerEnd(int winner) {

    }

    @Override
    public void registerEnd(JocEquips.Equip winner) {

    }

    @Override
    public void registerEnd(Player winner) {

    }

    @Override
    public void registerTimestamp(Player p, boolean ending, int kills, int deaths, double damageDealt, boolean isAlive, int itemInHand, int blocksPlaced, int blocksBroken, int objectivesCompleted, int spree) {
    }
}
