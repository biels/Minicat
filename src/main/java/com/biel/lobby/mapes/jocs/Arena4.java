/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocEquips;
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
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Arena4
extends JocEquips {
    private static final /* synthetic */ String[] lIIlIlI;
    private static final /* synthetic */ int[] lIlIllI;

    static {
        Arena4.lIIllIlII();
        Arena4.lIIIllIlI();
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        Arena4 llIlIIIIllIIlll;
        ArrayList<JocEquips.Equip> llIlIIIIllIlIII = new ArrayList<JocEquips.Equip>();
        "".length();
        llIlIIIIllIlIII.add(llIlIIIIllIIlll.new Arena4Equip(DyeColor.RED, lIIlIlI[lIlIllI[1]], lIlIllI[2]));
        "".length();
        llIlIIIIllIlIII.add(llIlIIIIllIIlll.new Arena4Equip(DyeColor.BLUE, lIIlIlI[lIlIllI[3]], lIlIllI[0]));
        "".length();
        llIlIIIIllIlIII.add(llIlIIIIllIIlll.new Arena4Equip(DyeColor.GREEN, lIIlIlI[lIlIllI[2]], lIlIllI[1]));
        "".length();
        llIlIIIIllIlIII.add(llIlIIIIllIIlll.new Arena4Equip(DyeColor.YELLOW, lIIlIlI[lIlIllI[4]], lIlIllI[3]));
        return llIlIIIIllIlIII;
    }

    @Override
    public String getGameName() {
        return lIIlIlI[lIlIllI[0]];
    }

    private static void lIIllIlII() {
        lIlIllI = new int[15];
        Arena4.lIlIllI[0] = (41 ^ 104) & ~(216 ^ 153);
        Arena4.lIlIllI[1] = " ".length();
        Arena4.lIlIllI[2] = "   ".length();
        Arena4.lIlIllI[3] = "  ".length();
        Arena4.lIlIllI[4] = 71 ^ 35 ^ (234 ^ 138);
        Arena4.lIlIllI[5] = 104 + 53 - 35 + 14 ^ 113 + 42 - 34 + 20;
        Arena4.lIlIllI[6] = 121 + 11 - -9 + 48 ^ 134 + 149 - 227 + 131;
        Arena4.lIlIllI[7] = 118 ^ 113;
        Arena4.lIlIllI[8] = 112 + 26 - 67 + 117 ^ 164 + 65 - 108 + 59;
        Arena4.lIlIllI[9] = 50 ^ 59;
        Arena4.lIlIllI[10] = 107 ^ 97;
        Arena4.lIlIllI[11] = 105 ^ 24 ^ (3 ^ 121);
        Arena4.lIlIllI[12] = 99 ^ 2 ^ (42 ^ 71);
        Arena4.lIlIllI[13] = 103 ^ 106;
        Arena4.lIlIllI[14] = 75 ^ 49 ^ (60 ^ 72);
    }

    private static boolean lIIllIllI(int n, int n2) {
        return n >= n2;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIlIIIIllIIIll) {
        ArrayList<ItemStack> llIlIIIIllIIIlI = new ArrayList<ItemStack>();
        "".length();
        llIlIIIIllIIIlI.add(new ItemStack(Material.WOOD_SWORD));
        return llIlIIIIllIIIlI;
    }

    private static boolean lIIllIlIl(int n) {
        return n != 0;
    }

    private static boolean lIIlllIll(int n, int n2) {
        return n < n2;
    }

    private static void lIIIllIlI() {
        lIIlIlI = new String[lIlIllI[14]];
        Arena4.lIIlIlI[Arena4.lIlIllI[0]] = Arena4.lIIIIIIlI("CastDNFKPGs=", "hgYpS");
        Arena4.lIIlIlI[Arena4.lIlIllI[1]] = Arena4.lIIIIIIlI("meLG6dr8qLg=", "ThBLY");
        Arena4.lIIlIlI[Arena4.lIlIllI[3]] = Arena4.lIIIIIIlI("Cz5AQ5hYJbE=", "vkrqy");
        Arena4.lIIlIlI[Arena4.lIlIllI[2]] = Arena4.lIIIIIlll("w3qPk7Fnd8s=", "uflHM");
        Arena4.lIIlIlI[Arena4.lIlIllI[4]] = Arena4.lIIIIlIll("MDcpFg==", "WEFux");
        Arena4.lIIlIlI[Arena4.lIlIllI[5]] = Arena4.lIIIIlIll("DwMTGwlq", "Jrfry");
        Arena4.lIIlIlI[Arena4.lIlIllI[6]] = Arena4.lIIIIlIll("Fh4KPjlmMCkrMmZZVBc=", "FkdJJ");
        Arena4.lIIlIlI[Arena4.lIlIllI[7]] = Arena4.lIIIIIIlI("cAV60wFxTQY=", "Cgcaf");
        Arena4.lIIlIlI[Arena4.lIlIllI[8]] = Arena4.lIIIIIIlI("fZV0D8uMHcQ=", "jxfIb");
        Arena4.lIIlIlI[Arena4.lIlIllI[9]] = Arena4.lIIIIIlll("4Zbqa2R8dYg=", "DelCz");
        Arena4.lIIlIlI[Arena4.lIlIllI[10]] = Arena4.lIIIIIlll("n4RcBi4lcXx0/afZFjbZJQ==", "VxBpD");
        Arena4.lIIlIlI[Arena4.lIlIllI[11]] = Arena4.lIIIIIlll("yxdKAwhZuh0=", "oDCdj");
        Arena4.lIIlIlI[Arena4.lIlIllI[12]] = Arena4.lIIIIlIll("Sg==", "gDvPD");
        Arena4.lIIlIlI[Arena4.lIlIllI[13]] = Arena4.lIIIIlIll("cw==", "ZFeiZ");
    }

    void comprovarGuanyador() {
        Arena4 llIlIIIIlIllIII;
        Iterator llIlIIIIlIlIlll = llIlIIIIlIllIII.Equips.iterator();
        while (Arena4.lIIllIlIl((int)llIlIIIIlIlIlll.hasNext())) {
            JocEquips.Equip llIlIIIIlIllIlI = (JocEquips.Equip)llIlIIIIlIlIlll.next();
            Arena4Equip llIlIIIIlIllIll = (Arena4Equip)llIlIIIIlIllIlI;
            if (Arena4.lIIllIllI(llIlIIIIlIllIll.Score, llIlIIIIlIllIll.MaxScore)) {
                llIlIIIIlIllIII.JocFinalitzat();
            }
            "".length();
            if (-"   ".length() <= 0) continue;
            return;
        }
    }

    @Override
    protected void setCustomGameRules() {
    }

    private static int lIIllIlll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static String lIIIIIIlI(String llIIllllIlIlIIl, String llIIllllIlIlIlI) {
        try {
            SecretKeySpec llIIllllIlIlllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIllllIlIlIlI.getBytes(StandardCharsets.UTF_8)), lIlIllI[8]), "DES");
            Cipher llIIllllIlIllIl = Cipher.getInstance("DES");
            llIIllllIlIllIl.init(lIlIllI[3], llIIllllIlIlllI);
            return new String(llIIllllIlIllIl.doFinal(Base64.getDecoder().decode(llIIllllIlIlIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllllIlIllII) {
            llIIllllIlIllII.printStackTrace();
            return null;
        }
    }

    private static boolean lIIlllIII(Object object) {
        return object == null;
    }

    private static String lIIIIlIll(String llIIlllllIIIlII, String llIIlllllIIIIll) {
        llIIlllllIIIlII = new String(Base64.getDecoder().decode(llIIlllllIIIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIlllllIIIIlI = new StringBuilder();
        char[] llIIlllllIIIIIl = llIIlllllIIIIll.toCharArray();
        int llIIlllllIIIIII = lIlIllI[0];
        char[] llIIllllIlllIIl = llIIlllllIIIlII.toCharArray();
        int llIIllllIllIlll = llIIllllIlllIIl.length;
        int llIIllllIllIllI = lIlIllI[0];
        while (Arena4.lIIlllIll(llIIllllIllIllI, llIIllllIllIlll)) {
            char llIIlllllIIIlIl = llIIllllIlllIIl[llIIllllIllIllI];
            "".length();
            llIIlllllIIIIlI.append((char)(llIIlllllIIIlIl ^ llIIlllllIIIIIl[llIIlllllIIIIII % llIIlllllIIIIIl.length]));
            ++llIIlllllIIIIII;
            ++llIIllllIllIllI;
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return String.valueOf(llIIlllllIIIIlI);
    }

    @Override
    protected synchronized void gameEvent(Event llIlIIIIIIIllll) {
        if (Arena4.lIIllIlIl(llIlIIIIIIIllll instanceof PlayerDeathEvent)) {
            Arena4 llIlIIIIIIIlllI;
            PlayerDeathEvent llIlIIIIIIlIlIl = (PlayerDeathEvent)llIlIIIIIIIllll;
            Player llIlIIIIIIlIlII = llIlIIIIIIlIlIl.getEntity();
            Player llIlIIIIIIlIIll = llIlIIIIIIlIlII.getKiller();
            Arena4Equip llIlIIIIIIlIIlI = (Arena4Equip)llIlIIIIIIIlllI.obtenirEquip(llIlIIIIIIlIlII);
            Arena4Equip llIlIIIIIIlIIIl = (Arena4Equip)llIlIIIIIIIlllI.obtenirEquip(llIlIIIIIIlIIll);
            if (Arena4.lIIlllIII((Object)llIlIIIIIIlIIll)) {
                return;
            }
            if (Arena4.lIIllIlIl((int)llIlIIIIIIIlllI.areEnemies(llIlIIIIIIlIlII, llIlIIIIIIlIIll).booleanValue())) {
                int llIlIIIIIIllIII = lIlIllI[0];
                int llIlIIIIIIlIlll = lIlIllI[1];
                if (Arena4.lIIlllIIl(llIlIIIIIIlIIIl.equipObjectiu(), llIlIIIIIIlIIlI)) {
                    llIlIIIIIIllIII = lIlIllI[2];
                    "".length();
                    if (-(103 ^ 99) >= 0) {
                        return;
                    }
                } else {
                    llIlIIIIIIllIII = lIlIllI[1];
                }
                if (Arena4.lIIlllIlI(Arena4.lIIllIlll(llIlIIIIIIIlllI.getTeamSize(), 1.0))) {
                    llIlIIIIIIllIII = lIlIllI[3];
                }
                llIlIIIIIIlIIIl.afegirPunts(llIlIIIIIIllIII);
                llIlIIIIIIlIIlI.restarPunts(llIlIIIIIIlIlll);
                String llIlIIIIIIlIllI = String.valueOf(new StringBuilder().append((Object)llIlIIIIIIlIIIl.getChatColor()).append(llIlIIIIIIlIIll.getName()).append(lIIlIlI[lIlIllI[7]]).append((Object)ChatColor.WHITE).append(lIIlIlI[lIlIllI[8]]).append(Integer.toString(llIlIIIIIIllIII)).append((Object)llIlIIIIIIlIIIl.getChatColor()).append(lIIlIlI[lIlIllI[9]]).append((Object)ChatColor.WHITE).append(lIIlIlI[lIlIllI[10]]).append(llIlIIIIIIlIlII.getName()).append((Object)llIlIIIIIIlIIlI.getChatColor()).append(lIIlIlI[lIlIllI[11]]).append((Object)ChatColor.WHITE).append(lIIlIlI[lIlIllI[12]]).append(Integer.toString(llIlIIIIIIlIlll)).append((Object)llIlIIIIIIlIIlI.getChatColor()).append(lIIlIlI[lIlIllI[13]]));
                llIlIIIIIIlIlIl.setDeathMessage(llIlIIIIIIlIllI);
                llIlIIIIIIIlllI.updateScoreboard();
                llIlIIIIIIIlllI.comprovarGuanyador();
            }
        }
    }

    @Override
    protected void customJocIniciat() {
        Arena4 llIlIIIIllIlllI;
        super.customJocIniciat();
        llIlIIIIllIlllI.setBlockBreakPlace(lIlIllI[0]);
    }

    private static boolean lIIlllIlI(int n) {
        return n < 0;
    }

    public Arena4() {
        Arena4 llIlIIIIlllIIIl;
    }

    void updateScoreboard() {
        Arena4 llIlIIIIlIIIlIl;
        ArrayList<String> llIlIIIIlIIIlII = new ArrayList<String>();
        ArrayList<Integer> llIlIIIIlIIIIlI = new ArrayList<Integer>();
        Iterator llIlIIIIIllllII = llIlIIIIlIIIlIl.Equips.iterator();
        while (Arena4.lIIllIlIl((int)llIlIIIIIllllII.hasNext())) {
            JocEquips.Equip llIlIIIIlIIIlll = (JocEquips.Equip)llIlIIIIIllllII.next();
            Arena4Equip llIlIIIIlIIlIII = (Arena4Equip)llIlIIIIlIIIlll;
            "".length();
            llIlIIIIlIIIlII.add(String.valueOf(new StringBuilder().append((Object)llIlIIIIlIIIlll.getChatColor()).append(lIIlIlI[lIlIllI[5]]).append(llIlIIIIlIIIlll.getAdjectiu())));
            "".length();
            llIlIIIIlIIIIlI.add(llIlIIIIlIIlIII.getScore());
            "".length();
            if (null == null) continue;
            return;
        }
        ScoreBoardUpdater.setScoreBoard(llIlIIIIlIIIlIl.getPlayers(), lIIlIlI[lIlIllI[6]], llIlIIIIlIIIlII, llIlIIIIlIIIIlI);
    }

    private static boolean lIIlllIIl(Object object, Object object2) {
        return object == object2;
    }

    private static String lIIIIIlll(String llIIllllIIllllI, String llIIllllIIlllIl) {
        try {
            SecretKeySpec llIIllllIlIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIllllIIlllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIllllIlIIIII = Cipher.getInstance("Blowfish");
            llIIllllIlIIIII.init(lIlIllI[3], llIIllllIlIIIIl);
            return new String(llIIllllIlIIIII.doFinal(Base64.getDecoder().decode(llIIllllIIllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllllIIlllll) {
            llIIllllIIlllll.printStackTrace();
            return null;
        }
    }

    public class Arena4Equip
    extends JocEquips.Equip {
        /* synthetic */ int MaxScore;
        /* synthetic */ int Score;
        /* synthetic */ int idEnemic;
        private static final /* synthetic */ int[] lIIIlIlIl;

        private static boolean lIIIIIIIIll(int n, int n2) {
            return n >= n2;
        }

        public void restarPunts(int lllIlIlIlllIII) {
            Arena4Equip lllIlIlIlllIIl;
            if (Arena4Equip.lIIIIIIIIlI(lllIlIlIlllIII)) {
                return;
            }
            int lllIlIlIllIlll = lllIlIlIlllIIl.Score - lllIlIlIlllIII;
            if (Arena4Equip.lIIIIIIIIlI(lllIlIlIllIlll)) {
                lllIlIlIlllIIl.Score = lIIIlIlIl[2];
                return;
            }
            lllIlIlIlllIIl.Score = lllIlIlIllIlll;
        }

        public int getScore() {
            Arena4Equip lllIlIllIlIIlI;
            return lllIlIllIlIIlI.Score;
        }

        public void afegirPunts(int lllIlIlIlIllII) {
            Arena4Equip lllIlIlIlIllIl;
            if (Arena4Equip.lIIIIIIIIlI(lllIlIlIlIllII)) {
                return;
            }
            int lllIlIlIlIlllI = lllIlIlIlIllIl.Score + lllIlIlIlIllII;
            if (Arena4Equip.lIIIIIIIIll(lllIlIlIlIlllI, lllIlIlIlIllIl.MaxScore)) {
                lllIlIlIlIllIl.Score = lllIlIlIlIllIl.MaxScore;
                return;
            }
            lllIlIlIlIllIl.Score = lllIlIlIlIlllI;
        }

        private static boolean lIIIIIIIIlI(int n) {
            return n <= 0;
        }

        private static void lIIIIIIIIIl() {
            lIIIlIlIl = new int[3];
            Arena4Equip.lIIIlIlIl[0] = 45 ^ 19 ^ (149 ^ 174);
            Arena4Equip.lIIIlIlIl[1] = 152 ^ 140;
            Arena4Equip.lIIIlIlIl[2] = (25 ^ 42) & ~(37 ^ 22);
        }

        public JocEquips.Equip equipObjectiu() {
            Arena4Equip lllIlIlIlIlIIl;
            return lllIlIlIlIlIIl.Arena4.this.obtenirEquip(lllIlIlIlIlIIl.idEnemic);
        }

        public Arena4Equip(DyeColor lllIlIlIllllll, String lllIlIllIIIIll, int lllIlIllIIIIlI) {
            Arena4Equip lllIlIllIIIllI;
            super(lllIlIlIllllll, lllIlIllIIIIll);
            lllIlIllIIIllI.Score = lIIIlIlIl[0];
            lllIlIllIIIllI.MaxScore = lIIIlIlIl[1];
            lllIlIllIIIllI.idEnemic = lllIlIllIIIIlI;
        }

        public void setScore(int lllIlIllIIlllI) {
            lllIlIllIIllll.Score = lllIlIllIIlllI;
        }

        static {
            Arena4Equip.lIIIIIIIIIl();
        }
    }

}

