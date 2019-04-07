/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Arena1v1
extends JocTeamDeathMatch {
    private static final /* synthetic */ String[] lllI;
    private static final /* synthetic */ int[] llIII;

    @Override
    protected void updateScoreBoard(Player lllllIIIIIllIlI) {
        Arena1v1 lllllIIIIIllIIl;
        if (Arena1v1.lIIIlII((int)lllllIIIIIllIIl.JocIniciat.booleanValue())) {
            ArrayList<String> lllllIIIIIlllll = new ArrayList<String>();
            ArrayList<Integer> lllllIIIIIllllI = new ArrayList<Integer>();
            int lllllIIIIIlllIl = llIII[4];
            int lllllIIIIIlllII = llIII[0];
            Iterator lllllIIIIIlIlII = lllllIIIIIllIIl.Equips.iterator();
            while (Arena1v1.lIIIlII((int)lllllIIIIIlIlII.hasNext())) {
                JocEquips.Equip lllllIIIIlIIIII = (JocEquips.Equip)lllllIIIIIlIlII.next();
                "".length();
                lllllIIIIIlllll.add(String.valueOf(new StringBuilder().append((Object)lllllIIIIlIIIII.getChatColor()).append(lllI[llIII[5]]).append(lllllIIIIlIIIII.getAdjectiu())));
                "".length();
                lllllIIIIIllllI.add(lllllIIIIlIIIII.getPlayers().size());
                "".length();
                if ("   ".length() != 0) continue;
                return;
            }
            ScoreBoardUpdater.setScoreBoard(lllllIIIIIllIIl.getPlayers(), String.valueOf(new StringBuilder().append(lllI[llIII[6]]).append(Integer.toString(lllllIIIIIlllII)).append(lllI[llIII[7]]).append(Integer.toString(lllllIIIIIlllIl)).append(lllI[llIII[8]])), lllllIIIIIlllll, lllllIIIIIllllI);
        }
    }

    private static String lllIII(String llllIlllIllIllI, String llllIlllIllIlII) {
        try {
            SecretKeySpec llllIlllIllllIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlllIllIlII.getBytes(StandardCharsets.UTF_8)), llIII[9]), "DES");
            Cipher llllIlllIlllIll = Cipher.getInstance("DES");
            llllIlllIlllIll.init(llIII[2], llllIlllIllllIl);
            return new String(llllIlllIlllIll.doFinal(Base64.getDecoder().decode(llllIlllIllIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIlllIlllIlI) {
            llllIlllIlllIlI.printStackTrace();
            return null;
        }
    }

    private static void lllIlI() {
        lllI = new String[llIII[9]];
        Arena1v1.lllI[Arena1v1.llIII[0]] = Arena1v1.llIlll("Lp0FAtbmLcc=", "GuoHY");
        Arena1v1.lllI[Arena1v1.llIII[1]] = Arena1v1.llIlll("nhW0++cLBYc=", "EYyox");
        Arena1v1.lllI[Arena1v1.llIII[2]] = Arena1v1.lllIII("5GaMgLH3EItlPNK7C2sVNg==", "uCOXq");
        Arena1v1.lllI[Arena1v1.llIII[3]] = Arena1v1.lllIII("j25gzedd3okYvMu3oTqV1w==", "mhsCh");
        Arena1v1.lllI[Arena1v1.llIII[5]] = Arena1v1.lllIIl("MAUSHQJV", "utgtr");
        Arena1v1.lllI[Arena1v1.llIII[6]] = Arena1v1.lllIII("slkTW7nZTE0=", "EKDwy");
        Arena1v1.lllI[Arena1v1.llIII[7]] = Arena1v1.lllIIl("ZQ==", "JPhyq");
        Arena1v1.lllI[Arena1v1.llIII[8]] = Arena1v1.lllIIl("Bw==", "ZsmOO");
    }

    @Override
    public String getGameName() {
        return lllI[llIII[2]];
    }

    private static void lIIIIll() {
        llIII = new int[10];
        Arena1v1.llIII[0] = (227 ^ 166) & ~(87 ^ 18);
        Arena1v1.llIII[1] = " ".length();
        Arena1v1.llIII[2] = "  ".length();
        Arena1v1.llIII[3] = "   ".length();
        Arena1v1.llIII[4] = 63 ^ 74 ^ (228 ^ 133);
        Arena1v1.llIII[5] = 108 ^ 16 ^ (249 ^ 129);
        Arena1v1.llIII[6] = 174 ^ 171;
        Arena1v1.llIII[7] = 13 ^ 11;
        Arena1v1.llIII[8] = 110 ^ 105;
        Arena1v1.llIII[9] = 200 ^ 192;
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lllllIIIIlIlIll, Player lllllIIIIlIllll, Player lllllIIIIlIlIIl) {
        Arena1v1 lllllIIIIlIllII;
        super.onPlayerDeathByPlayer(lllllIIIIlIlIll, lllllIIIIlIllll, lllllIIIIlIlIIl);
        Joc.PlayerInfo lllllIIIIlIllIl = lllllIIIIlIllII.getPlayerInfo(lllllIIIIlIlIIl);
        lllllIIIIlIllIl.setValue(lllllIIIIlIllIl.getValue() + llIII[1]);
        lllllIIIIlIllII.sendGlobalMessage(String.valueOf(new StringBuilder().append(lllllIIIIlIlIIl.getName()).append(lllI[llIII[3]]).append(lllllIIIIlIllIl.getValue())));
    }

    public Arena1v1() {
        Arena1v1 lllllIIIlIIIlII;
    }

    private static String llIlll(String llllIlllllllllI, String llllIlllllllIII) {
        try {
            SecretKeySpec lllllIIIIIIIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIlllllllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllllIIIIIIIlII = Cipher.getInstance("Blowfish");
            lllllIIIIIIIlII.init(llIII[2], lllllIIIIIIIllI);
            return new String(lllllIIIIIIIlII.doFinal(Base64.getDecoder().decode(llllIlllllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIIIIIIIIlI) {
            lllllIIIIIIIIlI.printStackTrace();
            return null;
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllllIIIIlllIlI) {
        ArrayList<ItemStack> lllllIIIIlllIIl = new ArrayList<ItemStack>();
        "".length();
        lllllIIIIlllIIl.add(new ItemStack(Material.WOOD_SWORD, llIII[1]));
        return lllllIIIIlllIIl;
    }

    static {
        Arena1v1.lIIIIll();
        Arena1v1.lllIlI();
    }

    @Override
    protected void setCustomGameRules() {
    }

    private static String lllIIl(String llllIlllllIIIII, String llllIllllIlIlII) {
        llllIlllllIIIII = new String(Base64.getDecoder().decode(llllIlllllIIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llllIllllIlllII = new StringBuilder();
        char[] llllIllllIllIlI = llllIllllIlIlII.toCharArray();
        int llllIllllIllIII = llIII[0];
        char[] llllIllllIIllII = llllIlllllIIIII.toCharArray();
        int llllIllllIIlIlI = llllIllllIIllII.length;
        int llllIllllIIlIII = llIII[0];
        while (Arena1v1.lIIIllI(llllIllllIIlIII, llllIllllIIlIlI)) {
            char llllIlllllIIIIl = llllIllllIIllII[llllIllllIIlIII];
            "".length();
            llllIllllIlllII.append((char)(llllIlllllIIIIl ^ llllIllllIllIlI[llllIllllIllIII % llllIllllIllIlI.length]));
            ++llllIllllIllIII;
            ++llllIllllIIlIII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(llllIllllIlllII);
    }

    @Override
    protected int getFinishScore() {
        return llIII[6];
    }

    private static boolean lIIIllI(int n, int n2) {
        return n < n2;
    }

    private static boolean lIIIlII(int n) {
        return n != 0;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        Arena1v1 lllllIIIIllllll;
        ArrayList<JocEquips.Equip> lllllIIIlIIIIII = new ArrayList<JocEquips.Equip>();
        "".length();
        lllllIIIlIIIIII.add(lllllIIIIllllll.new JocEquips.Equip(DyeColor.RED, lllI[llIII[0]]));
        "".length();
        lllllIIIlIIIIII.add(lllllIIIIllllll.new JocEquips.Equip(DyeColor.BLUE, lllI[llIII[1]]));
        return lllllIIIlIIIIII;
    }
}

