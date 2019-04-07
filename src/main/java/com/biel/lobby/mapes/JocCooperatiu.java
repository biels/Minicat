/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.Joc;
import org.bukkit.entity.Player;

public abstract class JocCooperatiu
extends Joc {
    private static final /* synthetic */ int[] lIIIlII;

    public JocCooperatiu() {
        JocCooperatiu lllIIlIlIIIIlll;
    }

    static {
        JocCooperatiu.lllIlIlI();
    }

    @Override
    protected void setCustomGameRules() {
    }

    private static void lllIlIlI() {
        lIIIlII = new int[1];
        JocCooperatiu.lIIIlII[0] = " ".length();
    }

    @Override
    public Boolean areAllies(Player lllIIlIlIIIIIll, Player lllIIlIlIIIIIlI) {
        return lIIIlII[0];
    }
}

