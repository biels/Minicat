/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.data;

import com.biel.lobby.Com;
import java.util.ArrayList;
import org.bukkit.entity.Player;

public class PlayerData {
    /* synthetic */ int id;
    private static final /* synthetic */ int[] lIlIIlI;
    /* synthetic */ String userName;

    public int getRank() {
        PlayerData llIlIIIlIIlIIll;
        if (PlayerData.lIIllIIII((int)Com.getDataAPI().getRanking().contains(llIlIIIlIIlIIll.id))) {
            return lIlIIlI[0];
        }
        return Com.getDataAPI().getRanking().indexOf(llIlIIIlIIlIIll.id) + lIlIIlI[1];
    }

    public void addElo(double llIlIIIlIIllIIl) {
        PlayerData llIlIIIlIIlllII;
        llIlIIIlIIlllII.setElo(llIlIIIlIIlllII.getElo() + llIlIIIlIIllIIl);
    }

    public int getId() {
        PlayerData llIlIIIllIIlIlI;
        return llIlIIIllIIlIlI.id;
    }

    public double getRelativeElo() {
        PlayerData llIlIIIlIIlIlll;
        return llIlIIIlIIlIlll.getElo() - Com.getDataAPI().getAvgElo();
    }

    public PlayerData(Player llIlIIIllIIlllI) {
        PlayerData llIlIIIllIIllIl;
        llIlIIIllIIllIl(llIlIIIllIIlllI.getName());
    }

    public void setMoney(double llIlIIIlIllllIl) {
        PlayerData llIlIIIlIlllllI;
        Com.getDataAPI().setMoney(llIlIIIlIlllllI.id, llIlIIIlIllllIl);
    }

    public PlayerData(String llIlIIIllIlIIlI) {
        PlayerData llIlIIIllIlIIll;
        llIlIIIllIlIIll.userName = llIlIIIllIlIIlI;
        llIlIIIllIlIIll.id = Com.getDataAPI().getPlayerId(llIlIIIllIlIIlI);
    }

    public void addMoney(double llIlIIIlIlllIIl) {
        PlayerData llIlIIIlIlllIlI;
        llIlIIIlIlllIlI.setMoney(llIlIIIlIlllIlI.getMoney() + llIlIIIlIlllIIl);
    }

    public double getElo() {
        PlayerData llIlIIIlIlIIlIl;
        return Com.getDataAPI().getElo(llIlIIIlIlIIlIl.id);
    }

    public String getUserName() {
        PlayerData llIlIIIllIIIlll;
        return llIlIIIllIIIlll.userName;
    }

    public double getScore() {
        PlayerData llIlIIIlIllIlIl;
        return Com.getDataAPI().getScore(llIlIIIlIllIlIl.id);
    }

    public void addScore(double llIlIIIlIlIlIlI) {
        PlayerData llIlIIIlIlIlIll;
        llIlIIIlIlIlIll.setMoney(llIlIIIlIlIlIll.getScore() + llIlIIIlIlIlIlI);
    }

    public double getMoney() {
        PlayerData llIlIIIllIIIIll;
        return Com.getDataAPI().getMoney(llIlIIIllIIIIll.id);
    }

    static {
        PlayerData.lIIlIllll();
    }

    public PlayerData(int llIlIIIllIllIlI) {
        PlayerData llIlIIIllIllIIl;
        llIlIIIllIllIIl.id = llIlIIIllIllIlI;
        llIlIIIllIllIIl.userName = Com.getDataAPI().getPlayerName(llIlIIIllIllIlI);
    }

    private static void lIIlIllll() {
        lIlIIlI = new int[2];
        PlayerData.lIlIIlI[0] = -" ".length();
        PlayerData.lIlIIlI[1] = " ".length();
    }

    public void setScore(double llIlIIIlIllIIII) {
        PlayerData llIlIIIlIllIIIl;
        Com.getDataAPI().setScore(llIlIIIlIllIIIl.id, llIlIIIlIllIIII);
    }

    private static boolean lIIllIIII(int n) {
        return n == 0;
    }

    public void setElo(double llIlIIIlIlIIIIl) {
        PlayerData llIlIIIlIlIIIlI;
        Com.getDataAPI().setElo(llIlIIIlIlIIIlI.id, llIlIIIlIlIIIIl);
    }
}

