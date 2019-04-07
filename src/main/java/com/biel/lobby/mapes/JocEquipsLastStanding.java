/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class JocEquipsLastStanding
extends JocEquips {
    /* synthetic */ ArrayList<Player> AlivePlayers;
    /* synthetic */ ArrayList<Integer> AliveTeamIDs;
    private static final /* synthetic */ int[] lllIIll;
    private static final /* synthetic */ String[] lllIIII;

    static {
        JocEquipsLastStanding.llIIIlIII();
        JocEquipsLastStanding.llIIIIlIl();
    }

    public boolean isAlive(Player llIIIlIlIIlllII) {
        JocEquipsLastStanding llIIIlIlIIlllIl;
        return llIIIlIlIIlllIl.AlivePlayers.contains((Object)llIIIlIlIIlllII);
    }

    private static boolean llIIlIlII(int n, int n2) {
        return n < n2;
    }

    public boolean isAlive(int llIIIlIllIlIIIl) {
        JocEquipsLastStanding llIIIlIllIlIIII;
        return llIIIlIllIlIIII.AliveTeamIDs.contains(llIIIlIllIlIIIl);
    }

    public void fillAliveTeams() {
        JocEquipsLastStanding llIIIlIllllIIIl;
        Iterator llIIIlIllllIIII = llIIIlIllllIIIl.Equips.iterator();
        while (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIllllIIII.hasNext())) {
            JocEquips.Equip llIIIlIllllIIll = (JocEquips.Equip)llIIIlIllllIIII.next();
            if (JocEquipsLastStanding.llIIIlIlI(llIIIlIllllIIll.getPlayers().size())) {
                "".length();
                if ((219 ^ 193 ^ (71 ^ 89)) > 0) continue;
                return;
            }
            "".length();
            llIIIlIllllIIIl.AliveTeamIDs.add(llIIIlIllllIIll.getId());
            "".length();
            if (((55 ^ 87) & ~(240 ^ 144)) > -" ".length()) continue;
            return;
        }
    }

    public void removeAlive(int llIIIlIllIlllll) {
        JocEquipsLastStanding llIIIlIlllIIIII;
        ArrayList<Integer> llIIIlIllIllllI = new ArrayList<Integer>();
        "".length();
        llIIIlIllIllllI.add(llIIIlIllIlllll);
        "".length();
        llIIIlIlllIIIII.AliveTeamIDs.removeAll(llIIIlIllIllllI);
        llIIIlIlllIIIII.sendGlobalMessage(String.valueOf(new StringBuilder().append(lllIIII[lllIIll[0]]).append(llIIIlIlllIIIII.obtenirEquip(llIIIlIllIlllll).getAdjectiuColored()).append((Object)ChatColor.GRAY).append(lllIIII[lllIIll[1]])));
        llIIIlIlllIIIII.comprovarGuanyador();
        llIIIlIlllIIIII.updateScoreBoards();
    }

    public ArrayList<Player> getAlivePlayersTeam(JocEquips.Equip llIIIlIIlllllll) {
        ArrayList<Player> llIIIlIlIIIIIIl = new ArrayList<Player>();
        Iterator<Player> llIIIlIIlllllIl = llIIIlIIlllllll.getPlayers().iterator();
        while (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIIlllllIl.hasNext())) {
            JocEquipsLastStanding llIIIlIlIIIIIll;
            Player llIIIlIlIIIIlII = llIIIlIIlllllIl.next();
            if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIlIIIIIll.isAlive(llIIIlIlIIIIlII))) {
                "".length();
                llIIIlIlIIIIIIl.add(llIIIlIlIIIIlII);
            }
            "".length();
            if ("   ".length() >= "   ".length()) continue;
            return null;
        }
        return llIIIlIlIIIIIIl;
    }

    private static boolean llIIIlIll(int n, int n2) {
        return n == n2;
    }

    private static String lIllllllI(String llIIIlIIlIlIIIl, String llIIIlIIlIIlIll) {
        llIIIlIIlIlIIIl = new String(Base64.getDecoder().decode(llIIIlIIlIlIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIIlIIlIIllll = new StringBuilder();
        char[] llIIIlIIlIIlllI = llIIIlIIlIIlIll.toCharArray();
        int llIIIlIIlIIllIl = lllIIll[0];
        char[] llIIIlIIlIIIlll = llIIIlIIlIlIIIl.toCharArray();
        int llIIIlIIlIIIllI = llIIIlIIlIIIlll.length;
        int llIIIlIIlIIIlIl = lllIIll[0];
        while (JocEquipsLastStanding.llIIlIlII(llIIIlIIlIIIlIl, llIIIlIIlIIIllI)) {
            char llIIIlIIlIlIIlI = llIIIlIIlIIIlll[llIIIlIIlIIIlIl];
            "".length();
            llIIIlIIlIIllll.append((char)(llIIIlIIlIlIIlI ^ llIIIlIIlIIlllI[llIIIlIIlIIllIl % llIIIlIIlIIlllI.length]));
            ++llIIIlIIlIIllIl;
            ++llIIIlIIlIIIlIl;
            "".length();
            if ("  ".length() >= "  ".length()) continue;
            return null;
        }
        return String.valueOf(llIIIlIIlIIllll);
    }

    private static String lIlllllIl(String llIIIlIIlIlllll, String llIIIlIIlIllllI) {
        try {
            SecretKeySpec llIIIlIIllIIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIlIIlIllllI.getBytes(StandardCharsets.UTF_8)), lllIIll[8]), "DES");
            Cipher llIIIlIIllIIIll = Cipher.getInstance("DES");
            llIIIlIIllIIIll.init(lllIIll[2], llIIIlIIllIIlII);
            return new String(llIIIlIIllIIIll.doFinal(Base64.getDecoder().decode(llIIIlIIlIlllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlIIllIIIlI) {
            llIIIlIIllIIIlI.printStackTrace();
            return null;
        }
    }

    private JocEquips.Equip getWinner() {
        JocEquipsLastStanding llIIIlIlIllIlll;
        Integer llIIIlIlIllIllI = llIIIlIlIllIlll.AliveTeamIDs.get(lllIIll[0]);
        return llIIIlIlIllIlll.obtenirEquip(llIIIlIlIllIllI);
    }

    public void initAlivePlayers() {
        JocEquipsLastStanding llIIIlIlIlIlIll;
        llIIIlIlIlIlIll.AlivePlayers = llIIIlIlIlIlIll.getPlayers();
    }

    public boolean anyoneAlive() {
        JocEquipsLastStanding llIIIlIlIlllIll;
        boolean bl;
        if (JocEquipsLastStanding.llIIIlIIl(llIIIlIlIlllIll.AliveTeamIDs.size())) {
            bl = lllIIll[1];
            "".length();
            if ((78 ^ 74) < -" ".length()) {
                return (boolean)((226 ^ 175) & ~(118 ^ 59));
            }
        } else {
            bl = lllIIll[0];
        }
        return bl;
    }

    public ArrayList<JocEquips.Equip> getAliveTeams() {
        JocEquipsLastStanding llIIIlIlllIlIIl;
        ArrayList<JocEquips.Equip> llIIIlIlllIlIII = new ArrayList<JocEquips.Equip>();
        Iterator llIIIlIlllIIlIl = llIIIlIlllIlIIl.Equips.iterator();
        while (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIlllIIlIl.hasNext())) {
            JocEquips.Equip llIIIlIlllIlIlI = (JocEquips.Equip)llIIIlIlllIIlIl.next();
            if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIlllIlIIl.isAlive(llIIIlIlllIlIlI))) {
                "".length();
                llIIIlIlllIlIII.add(llIIIlIlllIlIlI);
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return llIIIlIlllIlIII;
    }

    public void removeIfAlive(int llIIIlIlIllllIl) {
        JocEquipsLastStanding llIIIlIllIIIIII;
        if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIllIIIIII.isAlive(llIIIlIlIllllIl))) {
            llIIIlIllIIIIII.removeAlive(llIIIlIlIllllIl);
        }
    }

    private static boolean llIIIlIlI(int n) {
        return n == 0;
    }

    public void comprovarGuanyador() {
        JocEquipsLastStanding llIIIlIlIllIIIl;
        JocEquips.Equip llIIIlIlIllIIII = null;
        if (JocEquipsLastStanding.llIIIlIll(llIIIlIlIllIIIl.AliveTeamIDs.size(), lllIIll[1])) {
            llIIIlIlIllIIII = llIIIlIlIllIIIl.getWinner();
            llIIIlIlIllIIIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lllIIII[lllIIll[2]]).append((Object)llIIIlIlIllIIII.getChatColor()).append(llIIIlIlIllIIII.getAdjectiu()).append((Object)ChatColor.GRAY).append(lllIIII[lllIIll[3]])));
            llIIIlIlIllIIIl.winGame(llIIIlIlIllIIII);
        }
        if (JocEquipsLastStanding.llIIIlIlI(llIIIlIlIllIIIl.AliveTeamIDs.size())) {
            llIIIlIlIllIIIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lllIIII[lllIIll[4]])));
            llIIIlIlIllIIIl.winGame(llIIIlIlIllIIII);
        }
    }

    public void setAlivePlayers(ArrayList<Player> llIIIlIlIlIIlII) {
        llIIIlIlIlIIlIl.AlivePlayers = llIIIlIlIlIIlII;
    }

    public void removeIfAlive(Player llIIIlIlIIIlIlI) {
        JocEquipsLastStanding llIIIlIlIIIlIll;
        if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIlIIIlIll.isAlive(llIIIlIlIIIlIlI))) {
            llIIIlIlIIIlIll.removeAlive(llIIIlIlIIIlIlI);
        }
    }

    public ArrayList<Player> getAlivePlayers() {
        JocEquipsLastStanding llIIIlIlIlIlIIl;
        return llIIIlIlIlIlIIl.AlivePlayers;
    }

    @Override
    protected void customJocIniciat() {
        JocEquipsLastStanding llIIIlIlllllIlI;
        llIIIlIlllllIlI.initAlivePlayers();
        llIIIlIlllllIlI.initAliveTeams();
        super.customJocIniciat();
        llIIIlIlllllIlI.fillAliveTeams();
    }

    public JocEquipsLastStanding() {
        JocEquipsLastStanding llIIIlIlllllllI;
    }

    @Override
    protected void updateScoreBoard(Player llIIIlIIllIllll) {
        JocEquipsLastStanding llIIIlIIllIlllI;
        if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIIllIlllI.JocIniciat.booleanValue())) {
            String string;
            ArrayList<String> llIIIlIIlllIIll = new ArrayList<String>();
            ArrayList<Integer> llIIIlIIlllIIlI = new ArrayList<Integer>();
            String llIIIlIIlllIIIl = llIIIlIIllIlllI.getTimer();
            Iterator llIIIlIIllIlIlI = llIIIlIIllIlllI.Equips.iterator();
            while (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIIllIlIlI.hasNext())) {
                String string2;
                JocEquips.Equip llIIIlIIlllIlII = (JocEquips.Equip)llIIIlIIllIlIlI.next();
                if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIIllIlllI.isAlive(llIIIlIIlllIlII))) {
                    string2 = llIIIlIIlllIlII.getDisplayName();
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    string2 = String.valueOf(new StringBuilder().append((Object)ChatColor.STRIKETHROUGH).append(llIIIlIIlllIlII.getDisplayName()));
                }
                "".length();
                llIIIlIIlllIIll.add(string2);
                "".length();
                llIIIlIIlllIIlI.add(llIIIlIIllIlllI.getAlivePlayersTeam(llIIIlIIlllIlII).size());
                "".length();
                if ("  ".length() > 0) continue;
                return;
            }
            if (JocEquipsLastStanding.llIIIlIlI((int)llIIIlIIlllIIIl.equals(lllIIII[lllIIll[11]]))) {
                string = String.valueOf(new StringBuilder().append(lllIIII[lllIIll[12]]).append((Object)ChatColor.GOLD).append(llIIIlIIlllIIIl));
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                string = lllIIII[lllIIll[13]];
            }
            ScoreBoardUpdater.setScoreBoard(llIIIlIIllIlllI.getPlayers(), String.valueOf(new StringBuilder().append(lllIIII[lllIIll[10]]).append(string)), llIIIlIIlllIIll, llIIIlIIlllIIlI);
        }
    }

    private static boolean llIIIlIIl(int n) {
        return n != 0;
    }

    public void removeIfAlive(JocEquips.Equip llIIIlIllIIIlIl) {
        JocEquipsLastStanding llIIIlIllIIIllI;
        if (JocEquipsLastStanding.llIIIlIIl((int)llIIIlIllIIIllI.isAlive(llIIIlIllIIIlIl))) {
            llIIIlIllIIIllI.removeAlive(llIIIlIllIIIlIl);
        }
    }

    private static void llIIIlIII() {
        lllIIll = new int[15];
        JocEquipsLastStanding.lllIIll[0] = (44 ^ 98 ^ (49 ^ 88)) & (95 + 116 - 146 + 97 ^ 124 + 109 - 103 + 3 ^ -" ".length());
        JocEquipsLastStanding.lllIIll[1] = " ".length();
        JocEquipsLastStanding.lllIIll[2] = "  ".length();
        JocEquipsLastStanding.lllIIll[3] = "   ".length();
        JocEquipsLastStanding.lllIIll[4] = 25 ^ 126 ^ (40 ^ 75);
        JocEquipsLastStanding.lllIIll[5] = 120 ^ 125;
        JocEquipsLastStanding.lllIIll[6] = 117 ^ 115;
        JocEquipsLastStanding.lllIIll[7] = 137 ^ 142;
        JocEquipsLastStanding.lllIIll[8] = 27 ^ 19;
        JocEquipsLastStanding.lllIIll[9] = 18 ^ 27;
        JocEquipsLastStanding.lllIIll[10] = 98 ^ 104;
        JocEquipsLastStanding.lllIIll[11] = 25 ^ 18;
        JocEquipsLastStanding.lllIIll[12] = 216 ^ 137 ^ (56 ^ 101);
        JocEquipsLastStanding.lllIIll[13] = 91 ^ 86;
        JocEquipsLastStanding.lllIIll[14] = 125 ^ 115;
    }

    public void removeAlive(JocEquips.Equip llIIIlIllIlIlIl) {
        JocEquipsLastStanding llIIIlIllIlIllI;
        llIIIlIllIlIllI.removeAlive(llIIIlIllIlIlIl.getId());
    }

    private static String lIlllllII(String llIIIlIIIllllII, String llIIIlIIIlllIll) {
        try {
            SecretKeySpec llIIIlIIIllllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIlIIIlllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIlIIIlllllI = Cipher.getInstance("Blowfish");
            llIIIlIIIlllllI.init(lllIIll[2], llIIIlIIIllllll);
            return new String(llIIIlIIIlllllI.doFinal(Base64.getDecoder().decode(llIIIlIIIllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlIIIllllIl) {
            llIIIlIIIllllIl.printStackTrace();
            return null;
        }
    }

    protected String getTimer() {
        return lllIIII[lllIIll[9]];
    }

    public boolean isAlive(JocEquips.Equip llIIIlIllIIlIll) {
        JocEquipsLastStanding llIIIlIllIIlIlI;
        return llIIIlIllIIlIlI.isAlive(llIIIlIllIIlIll.getId());
    }

    public void removeAlive(Player llIIIlIlIIlIIlI) {
        JocEquipsLastStanding llIIIlIlIIlIlll;
        "".length();
        llIIIlIlIIlIlll.AlivePlayers.remove((Object)llIIIlIlIIlIIlI);
        JocEquips.Equip llIIIlIlIIlIlIl = llIIIlIlIIlIlll.obtenirEquip(llIIIlIlIIlIIlI);
        int llIIIlIlIIlIlII = llIIIlIlIIlIlll.getAlivePlayersTeam(llIIIlIlIIlIlIl).size();
        if (JocEquipsLastStanding.llIIIlIlI(llIIIlIlIIlIlII)) {
            llIIIlIlIIlIlll.removeAlive(llIIIlIlIIlIlIl);
            "".length();
            if ((218 ^ 174 ^ (211 ^ 163)) != (213 ^ 131 ^ (34 ^ 112))) {
                return;
            }
        } else {
            llIIIlIlIIlIlll.sendGlobalMessage(String.valueOf(new StringBuilder().append(lllIIII[lllIIll[5]]).append((Object)llIIIlIlIIlIlIl.getChatColor()).append(llIIIlIlIIlIIlI.getName()).append((Object)ChatColor.GRAY).append(lllIIII[lllIIll[6]])));
            llIIIlIlIIlIlll.sendGlobalMessage(String.valueOf(new StringBuilder().append(lllIIII[lllIIll[7]]).append(Integer.toString(llIIIlIlIIlIlII)).append(lllIIII[lllIIll[8]]).append(llIIIlIlIIlIlIl.getAdjectiuColored())));
        }
    }

    private static void llIIIIlIl() {
        lllIIII = new String[lllIIll[14]];
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[0]] = JocEquipsLastStanding.lIlllllII("QWHqmhg2QcwstAAoEPOTQQ==", "DJLxN");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[1]] = JocEquipsLastStanding.lIlllllIl("Tvxa1e2eKX6ppUOEcqUnAj/9hgI/jd+8bs0AGNeOuMqGeIxpeTt3PQ==", "LvPQO");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[2]] = JocEquipsLastStanding.lIllllllI("J3EtCSICJmg=", "kVHxW");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[3]] = JocEquipsLastStanding.lIlllllII("tbUsfPpTytObHwIE2CG82Cg4yUu3zA0y", "BSAgC");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[4]] = JocEquipsLastStanding.lIlllllII("Uc3seyDWZkWMnZ70WqfWz4nQ5yqwBDOH", "lIKfU");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[5]] = JocEquipsLastStanding.lIllllllI("Dj5IGTMsMwwcNA==", "KRhsF");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[6]] = JocEquipsLastStanding.lIllllllI("ej4ucQ4pIi4lSz86JjwCNDc7", "ZVOQk");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[7]] = JocEquipsLastStanding.lIlllllIl("BYndy1Cc+KY=", "dLlCI");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[8]] = JocEquipsLastStanding.lIllllllI("PQMsJzY4BDhmM3cabCMjIh87Zg==", "WvKFR");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[9]] = JocEquipsLastStanding.lIlllllII("mLvzgcEHEi4=", "jYgZe");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[10]] = JocEquipsLastStanding.lIllllllI("FyMYNAF8cA==", "RPlUe");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[11]] = JocEquipsLastStanding.lIlllllIl("a+yyBGsGcXM=", "cSCdM");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[12]] = JocEquipsLastStanding.lIlllllIl("SQdGVp4hSw8=", "YQYBo");
        JocEquipsLastStanding.lllIIII[JocEquipsLastStanding.lllIIll[13]] = JocEquipsLastStanding.lIllllllI("", "Nsbyd");
    }

    public void initAliveTeams() {
        llIIIlIllllIlll.AliveTeamIDs = new ArrayList();
    }
}

