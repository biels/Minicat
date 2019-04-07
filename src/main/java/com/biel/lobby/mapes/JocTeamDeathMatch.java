/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamScoreRace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public abstract class JocTeamDeathMatch
extends JocTeamScoreRace {
    private static final /* synthetic */ int[] lllll;

    private static void lIIllll() {
        lllll = new int[1];
        JocTeamDeathMatch.lllll[0] = " ".length();
    }

    static {
        JocTeamDeathMatch.lIIllll();
    }

    public JocTeamDeathMatch() {
        JocTeamDeathMatch llllIlIlllIllII;
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent llllIlIllIllllI, Player llllIlIllIlllIl, Player llllIlIlllIIIlI) {
        JocTeamDeathMatch llllIlIlllIIlIl;
        super.onPlayerDeathByPlayer(llllIlIllIllllI, llllIlIllIlllIl, llllIlIlllIIIlI);
        JocTeamScoreRace.EquipScoreRace llllIlIlllIIIIl = (JocTeamScoreRace.EquipScoreRace)llllIlIlllIIlIl.obtenirEquip(llllIlIlllIIIlI);
        int llllIlIlllIIIII = lllll[0];
        llllIlIlllIIIIl.incrementScore(llllIlIlllIIIII);
    }
}

