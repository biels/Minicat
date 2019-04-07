/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public abstract class JocTeamScoreRace
extends JocEquips {
    private static final /* synthetic */ String[] llllIIlI;
    private static final /* synthetic */ int[] llllIlII;

    private static boolean lllIlIIIII(int n, int n2) {
        return n < n2;
    }

    private static void lllIIllIll() {
        llllIlII = new int[5];
        JocTeamScoreRace.llllIlII[0] = (75 ^ 29) & ~(125 ^ 43);
        JocTeamScoreRace.llllIlII[1] = " ".length();
        JocTeamScoreRace.llllIlII[2] = 139 + 133 - 178 + 64 ^ 123 + 82 - 82 + 19;
        JocTeamScoreRace.llllIlII[3] = "  ".length();
        JocTeamScoreRace.llllIlII[4] = "   ".length();
    }

    public void comprovarGuanyador() {
        JocTeamScoreRace lIIIIIIllIIllIl;
        EquipScoreRace lIIIIIIllIIlllI = lIIIIIIllIIllIl.getOrderedWinnerList().get(llllIlII[0]);
        if (JocTeamScoreRace.lllIIlllII(lIIIIIIllIIlllI.getScore(), lIIIIIIllIIllIl.getFinishScore())) {
            lIIIIIIllIIllIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llllIIlI[llllIlII[0]]).append((Object)ChatColor.BOLD).append(lIIIIIIllIIlllI.getDisplayName()).append(llllIIlI[llllIlII[1]])));
            lIIIIIIllIIllIl.winGame(lIIIIIIllIIlllI);
        }
    }

    private static boolean lllIIlllII(int n, int n2) {
        return n >= n2;
    }

    private static boolean lllIIllllI(int n, int n2) {
        return n > n2;
    }

    public ArrayList<EquipScoreRace> getOrderedWinnerList() {
        JocTeamScoreRace lIIIIIIllIIIlll;
        ArrayList<EquipScoreRace> lIIIIIIllIIlIII = lIIIIIIllIIIlll.getSpecificTeams();
        lIIIIIIllIIlIII.sort((lIIIIIIlIIlIlIl, lIIIIIIlIIlIlII) -> lIIIIIIlIIlIlII.getScore() - lIIIIIIlIIlIlIl.getScore());
        return lIIIIIIllIIlIII;
    }

    static {
        JocTeamScoreRace.lllIIllIll();
        JocTeamScoreRace.lllIIllIII();
    }

    @Override
    protected void updateScoreBoard(Player lIIIIIIlIlllIII) {
        JocTeamScoreRace lIIIIIIlIllIlll;
        if (JocTeamScoreRace.lllIIlllIl((int)lIIIIIIlIllIlll.JocIniciat.booleanValue())) {
            ArrayList<String> lIIIIIIlIlllIll = new ArrayList<String>();
            ArrayList<Integer> lIIIIIIlIlllIlI = new ArrayList<Integer>();
            Iterator lIIIIIIlIllIlII = lIIIIIIlIllIlll.Equips.iterator();
            while (JocTeamScoreRace.lllIIlllIl((int)lIIIIIIlIllIlII.hasNext())) {
                JocEquips.Equip lIIIIIIlIllllII = (JocEquips.Equip)lIIIIIIlIllIlII.next();
                EquipScoreRace lIIIIIIlIlllllI = (EquipScoreRace)lIIIIIIlIllllII;
                String lIIIIIIlIllllIl = lIIIIIIlIlllllI.getDisplayName();
                if (JocTeamScoreRace.lllIIllllI(lIIIIIIlIllllIl.length(), llllIlII[2])) {
                    lIIIIIIlIllllIl = lIIIIIIlIllllIl.substring(llllIlII[0], llllIlII[2]);
                }
                "".length();
                lIIIIIIlIlllIll.add(lIIIIIIlIllllIl);
                "".length();
                lIIIIIIlIlllIlI.add(lIIIIIIlIlllllI.getScore());
                "".length();
                if ("   ".length() > 0) continue;
                return;
            }
            ScoreBoardUpdater.setScoreBoard(lIIIIIIlIllIlll.getPlayers(), String.valueOf(new StringBuilder().append(llllIIlI[llllIlII[3]]).append(lIIIIIIlIllIlll.getFinishScore())), lIIIIIIlIlllIll, lIIIIIIlIlllIlI);
        }
    }

    private static void lllIIllIII() {
        llllIIlI = new String[llllIlII[4]];
        JocTeamScoreRace.llllIIlI[JocTeamScoreRace.llllIlII[0]] = JocTeamScoreRace.lllIIlIllI("JQsxh69PF5U=", "ulbfw");
        JocTeamScoreRace.llllIIlI[JocTeamScoreRace.llllIlII[1]] = JocTeamScoreRace.lllIIlIlll("VyEwRyUCKD8eIwNpPQZiBygjEysTKHA=", "wIQgB");
        JocTeamScoreRace.llllIIlI[JocTeamScoreRace.llllIlII[3]] = JocTeamScoreRace.lllIIlIlll("DgQ2Ym8=", "CeNXO");
    }

    public JocTeamScoreRace() {
        JocTeamScoreRace lIIIIIIllIlIIlI;
    }

    private static boolean lllIIlllIl(int n) {
        return n != 0;
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lIIIIIIlIlIlIIl, Player lIIIIIIlIlIlIII) {
        JocTeamScoreRace lIIIIIIlIlIlIlI;
        super.onPlayerDeath(lIIIIIIlIlIlIIl, lIIIIIIlIlIlIII);
    }

    public ArrayList<EquipScoreRace> getSpecificTeams() {
        JocTeamScoreRace lIIIIIIlIIllllI;
        ArrayList<EquipScoreRace> lIIIIIIlIIlllll = new ArrayList<EquipScoreRace>();
        Iterator lIIIIIIlIIlllII = lIIIIIIlIIllllI.Equips.iterator();
        while (JocTeamScoreRace.lllIIlllIl((int)lIIIIIIlIIlllII.hasNext())) {
            JocEquips.Equip lIIIIIIlIlIIIIl = (JocEquips.Equip)lIIIIIIlIIlllII.next();
            EquipScoreRace lIIIIIIlIlIIIlI = (EquipScoreRace)lIIIIIIlIlIIIIl;
            "".length();
            lIIIIIIlIIlllll.add(lIIIIIIlIlIIIlI);
            "".length();
            if (" ".length() != ((159 ^ 137 ^ (77 ^ 93)) & (48 + 104 - 48 + 35 ^ 14 + 54 - -48 + 25 ^ -" ".length()))) continue;
            return null;
        }
        return lIIIIIIlIIlllll;
    }

    protected abstract int getFinishScore();

    private static String lllIIlIllI(String lllllllIIllIIl, String lllllllIIlIllI) {
        try {
            SecretKeySpec lllllllIIlllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllllIIlIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllllllIIllIll = Cipher.getInstance("Blowfish");
            lllllllIIllIll.init(llllIlII[3], lllllllIIlllII);
            return new String(lllllllIIllIll.doFinal(Base64.getDecoder().decode(lllllllIIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllllIIllIlI) {
            lllllllIIllIlI.printStackTrace();
            return null;
        }
    }

    private static String lllIIlIlll(String lllllllIlIlllI, String lllllllIlIllIl) {
        lllllllIlIlllI = new String(Base64.getDecoder().decode(lllllllIlIlllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllllllIlIllII = new StringBuilder();
        char[] lllllllIlIlIll = lllllllIlIllIl.toCharArray();
        int lllllllIlIlIlI = llllIlII[0];
        char[] lllllllIlIIlII = lllllllIlIlllI.toCharArray();
        int lllllllIlIIIll = lllllllIlIIlII.length;
        int lllllllIlIIIlI = llllIlII[0];
        while (JocTeamScoreRace.lllIlIIIII(lllllllIlIIIlI, lllllllIlIIIll)) {
            char lllllllIlIllll = lllllllIlIIlII[lllllllIlIIIlI];
            "".length();
            lllllllIlIllII.append((char)(lllllllIlIllll ^ lllllllIlIlIll[lllllllIlIlIlI % lllllllIlIlIll.length]));
            ++lllllllIlIlIlI;
            ++lllllllIlIIIlI;
            "".length();
            if (-" ".length() == -" ".length()) continue;
            return null;
        }
        return String.valueOf(lllllllIlIllII);
    }

    public class EquipScoreRace
    extends JocEquips.Equip {
        /* synthetic */ int score;
        private static final /* synthetic */ int[] lIlIlIl;

        static {
            EquipScoreRace.lIIllIIll();
        }

        public int getScore() {
            EquipScoreRace llIlIIIlIIIIIlI;
            return llIlIIIlIIIIIlI.score;
        }

        public void setScore(int llIlIIIIlllllII) {
            EquipScoreRace llIlIIIIlllllll;
            llIlIIIIlllllll.score = llIlIIIIlllllII;
            llIlIIIIlllllll.JocTeamScoreRace.this.updateScoreBoards();
            llIlIIIIlllllll.JocTeamScoreRace.this.comprovarGuanyador();
        }

        private static void lIIllIIll() {
            lIlIlIl = new int[2];
            EquipScoreRace.lIlIlIl[0] = (216 ^ 144) & ~(71 ^ 15);
            EquipScoreRace.lIlIlIl[1] = " ".length();
        }

        public void incrementScore() {
            EquipScoreRace llIlIIIIlllIlII;
            llIlIIIIlllIlII.incrementScore(lIlIlIl[1]);
        }

        public EquipScoreRace(DyeColor llIlIIIlIIIlIlI, String llIlIIIlIIIIlIl) {
            EquipScoreRace llIlIIIlIIIllII;
            super(llIlIIIlIIIlIlI, llIlIIIlIIIIlIl);
            llIlIIIlIIIllII.score = lIlIlIl[0];
        }

        public void incrementScore(int llIlIIIIllllIII) {
            EquipScoreRace llIlIIIIlllIlll;
            llIlIIIIlllIlll.setScore(llIlIIIIlllIlll.getScore() + llIlIIIIllllIII);
        }
    }

}

