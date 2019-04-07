/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class JocScoreRace
extends Joc {
    private static final /* synthetic */ String[] lIIlll;
    private static final /* synthetic */ int[] lIllII;

    public void comprovarGuanyador() {
        JocScoreRace lllIllIIlIllIlI;
        ArrayList<Player> lllIllIIlIllIIl = lllIllIIlIllIlI.getOrderedWinnerList();
        Player lllIllIIlIllIII = lllIllIIlIllIIl.get(lIllII[2]);
        if (JocScoreRace.lIIIlIIl(lllIllIIlIllIlI.getScore(lllIllIIlIllIII), lllIllIIlIllIlI.getFinishScore())) {
            lllIllIIlIllIlI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlll[lIllII[2]]).append((Object)ChatColor.BOLD).append(lllIllIIlIllIII.getName()).append(lIIlll[lIllII[1]])));
            if (JocScoreRace.lIIIlIll((int)lllIllIIlIllIlI.getDisplayRanking())) {
                lllIllIIlIllIlI.displayRanking();
            }
            lllIllIIlIllIlI.winGame(lllIllIIlIllIIl);
        }
    }

    @Override
    protected void updateScoreBoard(Player lllIllIIIIlIllI) {
        JocScoreRace lllIllIIIIlIlIl;
        if (JocScoreRace.lIIIlIll((int)lllIllIIIIlIlIl.JocIniciat.booleanValue())) {
            ArrayList<String> lllIllIIIIllIlI = new ArrayList<String>();
            ArrayList<Integer> lllIllIIIIllIIl = new ArrayList<Integer>();
            ArrayList<Player> lllIllIIIIllIII = lllIllIIIIlIlIl.getOrderedWinnerList();
            if (JocScoreRace.lIlllllI(lllIllIIIIllIII.size())) {
                int lllIllIIIIllIll = lllIllIIIIlIlIl.getScore(lllIllIIIIllIII.get(lIllII[2]));
                Iterator<Player> lllIllIIIIlIIII = lllIllIIIIllIII.iterator();
                while (JocScoreRace.lIIIlIll((int)lllIllIIIIlIIII.hasNext())) {
                    Player lllIllIIIIlllII = lllIllIIIIlIIII.next();
                    String lllIllIIIlIIIIl = lIIlll[lIllII[10]];
                    int lllIllIIIlIIIII = lllIllIIIIllIII.indexOf((Object)lllIllIIIIlllII) + lIllII[1];
                    lllIllIIIlIIIIl = String.valueOf(new StringBuilder().append(lIIlll[lIllII[11]]).append((Object)ChatColor.BLUE));
                    if (JocScoreRace.lIlllIlI(lllIllIIIlIIIII, lIllII[4])) {
                        lllIllIIIlIIIIl = String.valueOf(new StringBuilder().append(lIIlll[lIllII[12]]).append((Object)ChatColor.YELLOW));
                    }
                    if (JocScoreRace.lIlllIlI(lllIllIIIlIIIII, lIllII[1])) {
                        lllIllIIIlIIIIl = String.valueOf(new StringBuilder().append(lIIlll[lIllII[13]]).append((Object)ChatColor.GREEN));
                    }
                    if (JocScoreRace.lIllllII(lllIllIIIlIIIII, lllIllIIIIllIII.size()) && JocScoreRace.lIIIlIIl(lllIllIIIIllIII.size(), lIllII[3])) {
                        lllIllIIIlIIIIl = String.valueOf(new StringBuilder().append(lIIlll[lIllII[14]]).append((Object)ChatColor.RED));
                    }
                    String lllIllIIIIlllll = lllIllIIIIlllII.getName();
                    String lllIllIIIIllllI = String.valueOf(new StringBuilder().append(lllIllIIIlIIIIl).append(StringUtils.abbreviate((String)lllIllIIIIlllll, (int)lIllII[9])));
                    int lllIllIIIIlllIl = lllIllIIIIlIlIl.getSpree(lllIllIIIIlllII);
                    if (JocScoreRace.lIlllllI(lllIllIIIIlllIl)) {
                        lllIllIIIIllllI = String.valueOf(new StringBuilder().append(lllIllIIIIllllI).append(lIIlll[lIllII[15]]).append(Integer.toString(lllIllIIIIlllIl)).append(lIIlll[lIllII[16]]));
                    }
                    if (JocScoreRace.lIllllll(lllIllIIIIllllI.length(), lIllII[16])) {
                        lllIllIIIIllllI = lllIllIIIIllllI.substring(lIllII[2], lIllII[16]);
                    }
                    "".length();
                    lllIllIIIIllIlI.add(lllIllIIIIllllI);
                    "".length();
                    lllIllIIIIllIIl.add(lllIllIIIIlIlIl.getScore(lllIllIIIIlllII));
                    "".length();
                    if (-" ".length() <= ((122 ^ 35 ^ (72 ^ 29)) & (44 ^ 126 ^ (194 ^ 156) ^ -" ".length()))) continue;
                    return;
                }
                ScoreBoardUpdater.setScoreBoard(lllIllIIIIlIlIl.getPlayers(), String.valueOf(new StringBuilder().append(lIIlll[lIllII[17]]).append(Integer.toString(lllIllIIIIllIll)).append(lIIlll[lIllII[18]]).append(Integer.toString(lllIllIIIIlIlIl.getFinishScore())).append(lIIlll[lIllII[19]])), lllIllIIIIllIlI, lllIllIIIIllIIl);
            }
        }
    }

    public boolean getDisplayRanking() {
        return lIllII[1];
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected void customJocIniciat() {
    }

    @Override
    public double getGameProgressETA() {
        JocScoreRace lllIllIIIllIIIl;
        ArrayList<Player> lllIllIIIllIlII = lllIllIIIllIIIl.getOrderedWinnerList();
        if (JocScoreRace.lIllllIl(lllIllIIIllIlII.size())) {
            return 0.0;
        }
        int lllIllIIIllIIll = lllIllIIIllIIIl.getScore(lllIllIIIllIlII.get(lIllII[2]));
        double lllIllIIIllIIlI = (double)lllIllIIIllIIll / (double)lllIllIIIllIIIl.getFinishScore();
        return super.getGameProgressETA() * 0.2 + lllIllIIIllIIlI * 0.8;
    }

    private static boolean lIllllII(int n, int n2) {
        return n == n2;
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lllIllIIIIIIlIl, Player lllIllIIIIIIlII) {
        JocScoreRace lllIllIIIIIIllI;
        super.onPlayerDeath(lllIllIIIIIIlIl, lllIllIIIIIIlII);
        lllIllIIIIIIllI.resetSpree(lllIllIIIIIIlII);
    }

    @Override
    protected void customJocFinalitzat() {
        JocScoreRace lllIllIlIlIllII;
        lllIllIlIlIllII.planificarReseteig(lIllII[0]);
    }

    private static boolean lIIIlIll(int n) {
        return n != 0;
    }

    public void displayRanking() {
        JocScoreRace lllIllIIlIIlIIl;
        ArrayList<Player> lllIllIIlIIlIII = lllIllIIlIIlIIl.getOrderedWinnerList();
        lllIllIIlIIlIIl.sendGlobalMessage(lIIlll[lIllII[3]]);
        Iterator<Player> lllIllIIlIIIlIl = lllIllIIlIIlIII.iterator();
        while (JocScoreRace.lIIIlIll((int)lllIllIIlIIIlIl.hasNext())) {
            Player lllIllIIlIIlIlI = lllIllIIlIIIlIl.next();
            int lllIllIIlIIllIl = lllIllIIlIIlIII.indexOf((Object)lllIllIIlIIlIlI) + lIllII[1];
            String lllIllIIlIIllII = lIIlll[lIllII[4]];
            String lllIllIIlIIlIll = lIIlll[lIllII[5]];
            lllIllIIlIIlIll = String.valueOf(new StringBuilder().append(lIIlll[lIllII[6]]).append((Object)ChatColor.BLUE));
            if (JocScoreRace.lIlllIlI(lllIllIIlIIllIl, lIllII[1])) {
                lllIllIIlIIlIll = String.valueOf(new StringBuilder().append(lIIlll[lIllII[0]]).append((Object)ChatColor.GREEN));
            }
            if (JocScoreRace.lIlllIlI(lllIllIIlIIllIl, lIllII[4])) {
                lllIllIIlIIlIll = String.valueOf(new StringBuilder().append(lIIlll[lIllII[7]]).append((Object)ChatColor.YELLOW));
            }
            if (JocScoreRace.lIllllII(lllIllIIlIIllIl, lllIllIIlIIlIII.size())) {
                lllIllIIlIIlIll = String.valueOf(new StringBuilder().append(lIIlll[lIllII[8]]).append((Object)ChatColor.RED));
            }
            lllIllIIlIIllII = String.valueOf(new StringBuilder().append(lllIllIIlIIllII).append(lllIllIIlIIlIll));
            lllIllIIlIIllII = String.valueOf(new StringBuilder().append(lllIllIIlIIllII).append(Integer.toString(lllIllIIlIIllIl)));
            lllIllIIlIIllII = String.valueOf(new StringBuilder().append(lllIllIIlIIllII).append(lIIlll[lIllII[9]]));
            lllIllIIlIIllII = String.valueOf(new StringBuilder().append(lllIllIIlIIllII).append(lllIllIIlIIlIlI.getName()));
            lllIllIIlIIlIIl.sendGlobalMessage(lllIllIIlIIllII);
            "".length();
            if (null == null) continue;
            return;
        }
    }

    protected int getSpree(Player lllIllIlIlIIIlI) {
        JocScoreRace lllIllIlIlIIIIl;
        return lllIllIlIlIIIIl.getPlayerInfo(lllIllIlIlIIIlI).getSpree();
    }

    protected void setScore(Player lllIllIlIIlIllI, int lllIllIlIIlIlIl) {
        JocScoreRace lllIllIlIIlIlll;
        Joc.PlayerInfo lllIllIlIIllIII = lllIllIlIIlIlll.getPlayerInfo(lllIllIlIIlIllI);
        lllIllIlIIllIII.setValue(lllIllIlIIlIlIl);
        lllIllIlIIlIlll.updateScoreBoards();
        lllIllIlIIlIlll.comprovarGuanyador();
    }

    private static void lIIIIIII() {
        lIIlll = new String[lIllII[20]];
        JocScoreRace.lIIlll[JocScoreRace.lIllII[2]] = JocScoreRace.lllIIII("xfiQAWsmrFo=", "gqLRl");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[1]] = JocScoreRace.lllIlII("Qz8QSSsWNh8QLRd2", "cWqiL");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[3]] = JocScoreRace.lllIIII("zuTwbOe2pURBILB9CtJ3mHyKPnqy1oZL", "mbDzr");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[4]] = JocScoreRace.lllIIII("DBIs8IpERXQ=", "wWgDY");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[5]] = JocScoreRace.lllIIII("F5IBEK9iPuc=", "tsjoR");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[6]] = JocScoreRace.llllIIl("s9PuIw+qPFQ=", "Jtsay");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[0]] = JocScoreRace.lllIlII("", "SAGIZ");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[7]] = JocScoreRace.llllIIl("dkFZU0vXYRY=", "DEGeR");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[8]] = JocScoreRace.lllIIII("0EvrNIjbmOw=", "BGaMF");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[9]] = JocScoreRace.lllIIII("SxZIU/I+rZo=", "tQCcb");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[10]] = JocScoreRace.llllIIl("4sUpv+jY1y4=", "RqdFa");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[11]] = JocScoreRace.llllIIl("hVtm5u9oxrs=", "ghgqe");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[12]] = JocScoreRace.lllIIII("ky53uimAtj8=", "lDamy");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[13]] = JocScoreRace.llllIIl("j08SHoIz7YE=", "hcaNg");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[14]] = JocScoreRace.lllIlII("", "cUWKo");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[15]] = JocScoreRace.llllIIl("tsF9fQNi1co=", "auOcd");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[16]] = JocScoreRace.lllIlII("SA==", "avyTR");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[17]] = JocScoreRace.lllIIII("C18xWQt1VU8=", "WkqZu");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[18]] = JocScoreRace.lllIIII("cgrD4pZr+90=", "KlOhT");
        JocScoreRace.lIIlll[JocScoreRace.lIllII[19]] = JocScoreRace.lllIlII("JA==", "ynBcW");
    }

    public ArrayList<Player> getOrderedWinnerList() {
        JocScoreRace lllIllIIIlllIll;
        ArrayList<Player> lllIllIIIllllII = lllIllIIIlllIll.getPlayers();
        lllIllIIIllllII.sort((lllIlIllllllIIl, lllIlIllllllIll) -> {
            JocScoreRace lllIlIllllllIlI;
            return lllIlIllllllIlI.getScore(lllIlIllllllIll) - lllIlIllllllIlI.getScore(lllIlIllllllIIl);
        });
        return lllIllIIIllllII;
    }

    protected int getScore(Player lllIllIlIlIlIII) {
        JocScoreRace lllIllIlIlIlIIl;
        return lllIllIlIlIlIIl.getPlayerInfo(lllIllIlIlIlIII).getValue();
    }

    protected void incrementSpree(Player lllIllIIllllIll) {
        JocScoreRace lllIllIIllllIlI;
        lllIllIIllllIlI.incrementSpree(lllIllIIllllIll, lIllII[1]);
    }

    private static boolean lIllllIl(int n) {
        return n == 0;
    }

    protected void incrementScore(Player lllIllIIllIIlII) {
        JocScoreRace lllIllIIllIIlll;
        lllIllIIllIIlll.incrementScore(lllIllIIllIIlII, lIllII[1]);
    }

    private static boolean lIIIlIIl(int n, int n2) {
        return n >= n2;
    }

    protected abstract int getFinishScore();

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllIllIlIllIIIl) {
        return null;
    }

    private static String lllIlII(String lllIlIllllIIIII, String lllIlIlllIlllll) {
        lllIlIllllIIIII = new String(Base64.getDecoder().decode(lllIlIllllIIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIlIlllIllllI = new StringBuilder();
        char[] lllIlIlllIlllIl = lllIlIlllIlllll.toCharArray();
        int lllIlIlllIlllII = lIllII[2];
        char[] lllIlIlllIlIllI = lllIlIllllIIIII.toCharArray();
        int lllIlIlllIlIlIl = lllIlIlllIlIllI.length;
        int lllIlIlllIlIlII = lIllII[2];
        while (JocScoreRace.llIIIIll(lllIlIlllIlIlII, lllIlIlllIlIlIl)) {
            char lllIlIllllIIIIl = lllIlIlllIlIllI[lllIlIlllIlIlII];
            "".length();
            lllIlIlllIllllI.append((char)(lllIlIllllIIIIl ^ lllIlIlllIlllIl[lllIlIlllIlllII % lllIlIlllIlllIl.length]));
            ++lllIlIlllIlllII;
            ++lllIlIlllIlIlII;
            "".length();
            if ("  ".length() >= -" ".length()) continue;
            return null;
        }
        return String.valueOf(lllIlIlllIllllI);
    }

    private static String llllIIl(String lllIlIllllIlllI, String lllIlIllllIllll) {
        try {
            SecretKeySpec lllIlIlllllIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIlIllllIllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIlIlllllIIlI = Cipher.getInstance("Blowfish");
            lllIlIlllllIIlI.init(lIllII[3], lllIlIlllllIIll);
            return new String(lllIlIlllllIIlI.doFinal(Base64.getDecoder().decode(lllIlIllllIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIlIlllllIIIl) {
            lllIlIlllllIIIl.printStackTrace();
            return null;
        }
    }

    protected void incrementScore(Player lllIllIIllIlllI, int lllIllIIllIlIlI) {
        JocScoreRace lllIllIIllIllll;
        lllIllIIllIllll.incrementSpree(lllIllIIllIlllI, lllIllIIllIlIlI);
        lllIllIIllIllll.setScore(lllIllIIllIlllI, lllIllIIllIllll.getScore(lllIllIIllIlllI) + lllIllIIllIlIlI);
    }

    protected void resetSpree(Player lllIllIIlllIlIl) {
        JocScoreRace lllIllIIlllIllI;
        lllIllIIlllIllI.setSpree(lllIllIIlllIlIl, lIllII[2]);
        lllIllIIlllIllI.updateScoreBoards();
    }

    private static boolean lIllllll(int n, int n2) {
        return n > n2;
    }

    public JocScoreRace() {
        JocScoreRace lllIllIlIllIlII;
    }

    @Override
    protected void teletransportarTothom() {
    }

    private static String lllIIII(String lllIlIlllIIlIll, String lllIlIlllIIlIlI) {
        try {
            SecretKeySpec lllIlIlllIIlllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIlIlllIIlIlI.getBytes(StandardCharsets.UTF_8)), lIllII[8]), "DES");
            Cipher lllIlIlllIIllIl = Cipher.getInstance("DES");
            lllIlIlllIIllIl.init(lIllII[3], lllIlIlllIIlllI);
            return new String(lllIlIlllIIllIl.doFinal(Base64.getDecoder().decode(lllIlIlllIIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIlIlllIIllII) {
            lllIlIlllIIllII.printStackTrace();
            return null;
        }
    }

    protected void setSpree(Player lllIllIlIIIlIlI, int lllIllIlIIIllIl) {
        JocScoreRace lllIllIlIIIlIll;
        Joc.PlayerInfo lllIllIlIIIllII = lllIllIlIIIlIll.getPlayerInfo(lllIllIlIIIlIlI);
        lllIllIlIIIllII.setSpree(lllIllIlIIIllIl);
        lllIllIlIIIlIlI.setLevel(lllIllIlIIIllIl);
    }

    private static boolean llIIIIll(int n, int n2) {
        return n < n2;
    }

    protected void incrementSpree(Player lllIllIlIIIIIII, int lllIllIlIIIIIlI) {
        JocScoreRace lllIllIlIIIIlII;
        lllIllIlIIIIlII.setSpree(lllIllIlIIIIIII, lllIllIlIIIIlII.getSpree(lllIllIlIIIIIII) + lllIllIlIIIIIlI);
    }

    protected void resetScore(Player lllIllIIlIllllI) {
        JocScoreRace lllIllIIlIlllll;
        lllIllIIlIlllll.setScore(lllIllIIlIllllI, lIllII[2]);
    }

    private static boolean lIlllIlI(int n, int n2) {
        return n <= n2;
    }

    static {
        JocScoreRace.lIIIlIII();
        JocScoreRace.lIIIIIII();
    }

    private static void lIIIlIII() {
        lIllII = new int[21];
        JocScoreRace.lIllII[0] = 56 ^ 62;
        JocScoreRace.lIllII[1] = " ".length();
        JocScoreRace.lIllII[2] = (218 ^ 187) & ~(8 ^ 105);
        JocScoreRace.lIllII[3] = "  ".length();
        JocScoreRace.lIllII[4] = "   ".length();
        JocScoreRace.lIllII[5] = 79 ^ 75;
        JocScoreRace.lIllII[6] = 158 ^ 155;
        JocScoreRace.lIllII[7] = 180 ^ 179;
        JocScoreRace.lIllII[8] = 34 ^ 6 ^ (26 ^ 54);
        JocScoreRace.lIllII[9] = 101 ^ 108;
        JocScoreRace.lIllII[10] = 142 ^ 132;
        JocScoreRace.lIllII[11] = 180 ^ 191;
        JocScoreRace.lIllII[12] = 19 ^ 54 ^ (50 ^ 27);
        JocScoreRace.lIllII[13] = 149 ^ 152;
        JocScoreRace.lIllII[14] = 38 + 142 - 23 + 25 ^ 121 + 120 - 179 + 122;
        JocScoreRace.lIllII[15] = 153 + 59 - 90 + 48 ^ 13 + 6 - 13 + 159;
        JocScoreRace.lIllII[16] = 59 ^ 43;
        JocScoreRace.lIllII[17] = 65 ^ 80;
        JocScoreRace.lIllII[18] = 11 ^ 61 ^ (111 ^ 75);
        JocScoreRace.lIllII[19] = 144 ^ 165 ^ (182 ^ 144);
        JocScoreRace.lIllII[20] = 38 ^ 50;
    }

    private static boolean lIlllllI(int n) {
        return n > 0;
    }
}

