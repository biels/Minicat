/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.Joc;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class JocLastStanding
extends Joc {
    private static final /* synthetic */ String[] llIIIIII;
    private static final /* synthetic */ int[] llIIIIlI;
    public /* synthetic */ ArrayList<Player> AlivePlayers;

    public void anunciarPerdedor(Player lIIlIlllIllllll) {
        JocLastStanding lIIlIllllIIIIlI;
        lIIlIllllIIIIlI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIlllIllllll.getName()).append(llIIIIII[llIIIIlI[0]])));
    }

    public void comprovarGuanyador() {
        JocLastStanding lIIlIlllIllllII;
        Player lIIlIlllIlllIll = null;
        if (JocLastStanding.lIllllIlII(lIIlIlllIllllII.getAlivePlayers().size(), llIIIIlI[1])) {
            lIIlIlllIlllIll = lIIlIlllIllllII.getWinner();
            lIIlIlllIllllII.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llIIIIII[llIIIIlI[1]]).append((Object)ChatColor.BOLD).append(lIIlIlllIlllIll.getName()).append(llIIIIII[llIIIIlI[2]])));
            lIIlIlllIllllII.winGame(lIIlIlllIlllIll);
        }
        if (JocLastStanding.lIllllIlIl(lIIlIlllIllllII.getAlivePlayers().size())) {
            lIIlIlllIllllII.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(llIIIIII[llIIIIlI[3]])));
            lIIlIlllIllllII.winGame(lIIlIlllIlllIll);
        }
    }

    @Override
    protected void customJocIniciat() {
        JocLastStanding lIIlIlllIllIIII;
        lIIlIlllIllIIII.initAlivePlayers();
    }

    public void setAlivePlayers(ArrayList<Player> lIIlIlllllIIIII) {
        lIIlIlllllIIIll.AlivePlayers = lIIlIlllllIIIII;
    }

    public void initAlivePlayers() {
        JocLastStanding lIIlIlllllIlIlI;
        lIIlIlllllIlIlI.AlivePlayers = lIIlIlllllIlIlI.getPlayers();
    }

    private Player getWinner() {
        JocLastStanding lIIlIlllIllIllI;
        return lIIlIlllIllIllI.getAlivePlayers().get(llIIIIlI[0]);
    }

    private static void lIlllIllll() {
        llIIIIII = new String[llIIIIlI[5]];
        JocLastStanding.llIIIIII[JocLastStanding.llIIIIlI[0]] = JocLastStanding.lIlllIllIl("UCNyK4Jvrx3ovvxH/afN8w==", "fBEWr");
        JocLastStanding.llIIIIII[JocLastStanding.llIIIIlI[1]] = JocLastStanding.lIlllIlllI("", "QPBRy");
        JocLastStanding.llIIIIII[JocLastStanding.llIIIIlI[2]] = JocLastStanding.lIlllIlllI("aw0gZSE+BC88Jz9E", "KeAEF");
        JocLastStanding.llIIIIII[JocLastStanding.llIIIIlI[3]] = JocLastStanding.lIlllIlllI("IxZEIwxNEQVrAhgYCjIECRYWOEQ=", "mydKe");
    }

    public void removeIfAlive(Player lIIlIllllIlIIII) {
        JocLastStanding lIIlIllllIlIIIl;
        if (JocLastStanding.lIllllIIll((int)lIIlIllllIlIIIl.isAlive(lIIlIllllIlIIII))) {
            lIIlIllllIlIIIl.removeAlive(lIIlIllllIlIIII);
        }
    }

    public Boolean anyoneAlive() {
        JocLastStanding lIIlIllllIIIllI;
        boolean bl;
        if (JocLastStanding.lIllllIIll(lIIlIllllIIIllI.AlivePlayers.size())) {
            bl = llIIIIlI[1];
            "".length();
            if ((165 ^ 190 ^ (2 ^ 29)) <= 0) {
                return null;
            }
        } else {
            bl = llIIIIlI[0];
        }
        return bl;
    }

    private static boolean lIlllllIII(int n, int n2) {
        return n < n2;
    }

    private static String lIlllIlllI(String lIIlIlllIIlIIII, String lIIlIlllIIIllll) {
        lIIlIlllIIlIIII = new String(Base64.getDecoder().decode(lIIlIlllIIlIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIlIlllIIlIIll = new StringBuilder();
        char[] lIIlIlllIIlIIlI = lIIlIlllIIIllll.toCharArray();
        int lIIlIlllIIlIIIl = llIIIIlI[0];
        char[] lIIlIlllIIIlIll = lIIlIlllIIlIIII.toCharArray();
        int lIIlIlllIIIlIlI = lIIlIlllIIIlIll.length;
        int lIIlIlllIIIlIIl = llIIIIlI[0];
        while (JocLastStanding.lIlllllIII(lIIlIlllIIIlIIl, lIIlIlllIIIlIlI)) {
            char lIIlIlllIIlIllI = lIIlIlllIIIlIll[lIIlIlllIIIlIIl];
            "".length();
            lIIlIlllIIlIIll.append((char)(lIIlIlllIIlIllI ^ lIIlIlllIIlIIlI[lIIlIlllIIlIIIl % lIIlIlllIIlIIlI.length]));
            ++lIIlIlllIIlIIIl;
            ++lIIlIlllIIIlIIl;
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return String.valueOf(lIIlIlllIIlIIll);
    }

    void removeAlive(Player lIIlIllllIlIlII) {
        JocLastStanding lIIlIllllIlIlIl;
        "".length();
        lIIlIllllIlIlIl.AlivePlayers.remove((Object)lIIlIllllIlIlII);
        lIIlIllllIlIlIl.anunciarPerdedor(lIIlIllllIlIlII);
        lIIlIllllIlIlIl.updateScoreBoard(lIIlIllllIlIlII);
        lIIlIllllIlIlIl.getPlayerInfo(lIIlIllllIlIlII).setAlive(llIIIIlI[0]);
        lIIlIllllIlIlIl.comprovarGuanyador();
    }

    private static String lIlllIllIl(String lIIlIlllIlIIlIl, String lIIlIlllIlIIIlI) {
        try {
            SecretKeySpec lIIlIlllIlIlIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIlllIlIIIlI.getBytes(StandardCharsets.UTF_8)), llIIIIlI[6]), "DES");
            Cipher lIIlIlllIlIIlll = Cipher.getInstance("DES");
            lIIlIlllIlIIlll.init(llIIIIlI[2], lIIlIlllIlIlIII);
            return new String(lIIlIlllIlIIlll.doFinal(Base64.getDecoder().decode(lIIlIlllIlIIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIlllIlIIllI) {
            lIIlIlllIlIIllI.printStackTrace();
            return null;
        }
    }

    public boolean isAlive(Player lIIlIllllIlllII) {
        JocLastStanding lIIlIllllIlllIl;
        return lIIlIllllIlllIl.AlivePlayers.contains((Object)lIIlIllllIlllII);
    }

    public Player getRandomAlivePlayer() {
        JocLastStanding lIIlIllllIIlIIl;
        Random lIIlIllllIIlIlI = new Random();
        return lIIlIllllIIlIIl.AlivePlayers.get(lIIlIllllIIlIlI.nextInt(lIIlIllllIIlIIl.AlivePlayers.size()));
    }

    static {
        JocLastStanding.lIllllIIIl();
        JocLastStanding.lIlllIllll();
    }

    public JocLastStanding() {
        JocLastStanding lIIlIlllllIllII;
    }

    private static void lIllllIIIl() {
        llIIIIlI = new int[7];
        JocLastStanding.llIIIIlI[0] = (74 ^ 117 ^ (114 ^ 99)) & (73 ^ 56 ^ (247 ^ 168) ^ -" ".length());
        JocLastStanding.llIIIIlI[1] = " ".length();
        JocLastStanding.llIIIIlI[2] = "  ".length();
        JocLastStanding.llIIIIlI[3] = "   ".length();
        JocLastStanding.llIIIIlI[4] = 96 ^ 111;
        JocLastStanding.llIIIIlI[5] = 94 ^ 90;
        JocLastStanding.llIIIIlI[6] = 134 ^ 129 ^ (93 ^ 82);
    }

    private static boolean lIllllIlIl(int n) {
        return n == 0;
    }

    public ArrayList<Player> getAlivePlayers() {
        JocLastStanding lIIlIlllllIIlll;
        return lIIlIlllllIIlll.AlivePlayers;
    }

    @Override
    public String getWinnerDisplayName() {
        JocLastStanding lIIlIlllIllIIll;
        if (JocLastStanding.lIllllIlII(lIIlIlllIllIIll.getAlivePlayers().size(), llIIIIlI[1])) {
            return lIIlIlllIllIIll.getWinner().getName();
        }
        return super.getWinnerDisplayName();
    }

    @Override
    protected void customJocFinalitzat() {
        JocLastStanding lIIlIlllIlIlllI;
        lIIlIlllIlIlllI.planificarReseteig(llIIIIlI[4]);
    }

    private static boolean lIllllIIll(int n) {
        return n != 0;
    }

    private static boolean lIllllIlII(int n, int n2) {
        return n == n2;
    }
}

