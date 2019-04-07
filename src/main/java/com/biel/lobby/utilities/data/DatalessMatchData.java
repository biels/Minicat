/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.data;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.data.MatchData;
import org.bukkit.entity.Player;

public class DatalessMatchData
extends MatchData {
    private static final /* synthetic */ int[] lIIllIl;

    static {
        DatalessMatchData.lIIlIIlll();
    }

    public DatalessMatchData() {
        DatalessMatchData llIlIIlIIlIIllI;
        super(lIIllIl[0]);
    }

    @Override
    public void registerTimestamp(Player llIlIIlIIIllllI, boolean llIlIIlIIIlllIl, int llIlIIlIIIlllII, int llIlIIlIIIllIll, double llIlIIlIIIllIlI, boolean llIlIIlIIIllIIl, int llIlIIlIIIllIII, int llIlIIlIIIlIlll, int llIlIIlIIIlIllI, int llIlIIlIIIlIlIl, int llIlIIlIIIlIlII) {
    }

    @Override
    public void registerEnd(int llIlIIlIIlIIlII) {
    }

    @Override
    public void registerEnd(Player llIlIIlIIlIIIII) {
    }

    private static void lIIlIIlll() {
        lIIllIl = new int[1];
        DatalessMatchData.lIIllIl[0] = -" ".length();
    }

    @Override
    public void registerEnd(JocEquips.Equip llIlIIlIIlIIIlI) {
    }
}

