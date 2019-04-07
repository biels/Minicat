/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.gmail.filoghost.holographicdisplays.api.Hologram
 *  com.gmail.filoghost.holographicdisplays.api.HologramsAPI
 *  com.gmail.filoghost.holographicdisplays.api.line.TextLine
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.plugin.Plugin
 */
package com.biel.lobby.mapes;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public abstract class JocTeamDominion
extends JocEquips {
    private static final /* synthetic */ String[] lIlIlIlll;
    public /* synthetic */ ArrayList<ControlPoint> ControlPoints;
    private static final /* synthetic */ int[] lIllIIllI;

    @Override
    protected void updateScoreBoard(Player lIlIlIlIlIIIll) {
        block6 : {
            JocTeamDominion lIlIlIlIlIIlII;
            if (!JocTeamDominion.lIlIIIIIlIl((int)lIlIlIlIlIIlII.JocIniciat.booleanValue())) break block6;
            ArrayList<String> lIlIlIlIlIIllI = new ArrayList<String>();
            ArrayList<Integer> lIlIlIlIlIIlIl = new ArrayList<Integer>();
            "".length();
            lIlIlIlIlIIllI.add(lIlIlIlll[lIllIIllI[2]]);
            "".length();
            lIlIlIlIlIIlIl.add(Math.round(lIlIlIlIlIIlII.getMaxHealth()));
            Iterator<Object> lIlIlIlIIlllll = lIlIlIlIlIIlII.Equips.iterator();
            while (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIlIIlllll.hasNext())) {
                JocEquips.Equip lIlIlIlIlIlIIl = (JocEquips.Equip)lIlIlIlIIlllll.next();
                try {
                    "".length();
                    lIlIlIlIlIIllI.add(lIlIlIlIlIlIIl.getDisplayName());
                    EquipDominion lIlIlIlIlIlIlI = (EquipDominion)lIlIlIlIlIlIIl;
                    "".length();
                    lIlIlIlIlIIlIl.add(Math.round(lIlIlIlIlIlIlI.getHealth().floatValue()));
                    "".length();
                }
                catch (Exception lIlIlIlIlIlIlI) {
                    // empty catch block
                }
                if (((168 ^ 153) & ~(62 ^ 15)) == "   ".length()) {
                    return;
                }
                "".length();
                if ("  ".length() >= -" ".length()) continue;
                return;
            }
            lIlIlIlIIlllll = lIlIlIlIlIIlII.ControlPoints.iterator();
            while (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIlIIlllll.hasNext())) {
                ControlPoint lIlIlIlIlIIlll = (ControlPoint)lIlIlIlIIlllll.next();
                String lIlIlIlIlIlIII = lIlIlIlIlIIlll.getDisplayName();
                if (JocTeamDominion.lIlIIIIllII(lIlIlIlIlIlIII.length(), lIllIIllI[3])) {
                    lIlIlIlIlIlIII = lIlIlIlIlIlIII.substring(lIllIIllI[0], lIllIIllI[3]);
                }
                "".length();
                lIlIlIlIlIIllI.add(String.valueOf(new StringBuilder().append(lIlIlIlll[lIllIIllI[4]]).append(lIlIlIlIlIlIII)));
                "".length();
                lIlIlIlIlIIlIl.add(Math.round(lIlIlIlIlIIlll.getPercent()));
                "".length();
                if ("  ".length() >= -" ".length()) continue;
                return;
            }
            ScoreBoardUpdater.setScoreBoard(lIlIlIlIlIIlII.getPlayers(), String.valueOf(new StringBuilder().append(lIlIlIlll[lIllIIllI[5]]).append((Object)ChatColor.RED).append(lIlIlIlll[lIllIIllI[6]])), lIlIlIlIlIIllI, lIlIlIlIlIIlIl);
        }
    }

    private static boolean lIlIIIIlIlI(Object object) {
        return object != null;
    }

    private static int lIlIIIIlIll(long l, long l2) {
        return (int)(l LCMP l2);
    }

    private static boolean lIlIIIIIlll(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void customJocIniciat() {
        JocTeamDominion lIlIlIllIlllll;
        super.customJocIniciat();
        lIlIlIllIlllll.initControlPoints();
    }

    private static boolean lIlIIIIllIl(Object object, Object object2) {
        return object != object2;
    }

    public JocTeamDominion() {
        JocTeamDominion lIlIlIlllIIIlI;
        lIlIlIlllIIIlI.ControlPoints = new ArrayList();
    }

    protected void processControlPoints() {
        JocTeamDominion lIlIlIllIIllll;
        Iterator<ControlPoint> lIlIlIllIIlllI = lIlIlIllIIllll.ControlPoints.iterator();
        while (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIllIIlllI.hasNext())) {
            ControlPoint lIlIlIllIlIIIl = lIlIlIllIIlllI.next();
            lIlIlIllIlIIIl.processStandingPlayers();
            lIlIlIllIlIIIl.incrementTeamScore();
            lIlIlIllIlIIIl.updateHologram();
            "".length();
            if (((238 ^ 129 ^ (242 ^ 153)) & (86 + 151 - 163 + 101 ^ 31 + 156 - 185 + 169 ^ -" ".length())) == ((89 ^ 24 ^ (89 ^ 1)) & (70 ^ 78 ^ (182 ^ 167) ^ -" ".length()))) continue;
            return;
        }
    }

    private static boolean lIlIIIlIIIl(int n, int n2) {
        return n < n2;
    }

    private static boolean lIlIIIIIlIl(int n) {
        return n != 0;
    }

    private static boolean lIlIIIIlIIl(int n, int n2) {
        return n == n2;
    }

    private static String lIIllIlIIII(String lIlIlIIllIlIll, String lIlIlIIllIlIlI) {
        try {
            SecretKeySpec lIlIlIIlllIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIlIIllIlIlI.getBytes(StandardCharsets.UTF_8)), lIllIIllI[10]), "DES");
            Cipher lIlIlIIllIllll = Cipher.getInstance("DES");
            lIlIlIIllIllll.init(lIllIIllI[2], lIlIlIIlllIIII);
            return new String(lIlIlIIllIllll.doFinal(Base64.getDecoder().decode(lIlIlIIllIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlIIllIlllI) {
            lIlIlIIllIlllI.printStackTrace();
            return null;
        }
    }

    private static String lIIllllIlIl(String lIlIlIIllllIII, String lIlIlIIlllIlll) {
        try {
            SecretKeySpec lIlIlIIlllllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlIIlllIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIlIIlllllII = Cipher.getInstance("Blowfish");
            lIlIlIIlllllII.init(lIllIIllI[2], lIlIlIIlllllIl);
            return new String(lIlIlIIlllllII.doFinal(Base64.getDecoder().decode(lIlIlIIllllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlIIllllIll) {
            lIlIlIIllllIll.printStackTrace();
            return null;
        }
    }

    @Override
    public void heartbeat() {
        JocTeamDominion lIlIlIlIllIIIl;
        super.heartbeat();
        if (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIlIllIIIl.JocIniciat.booleanValue())) {
            lIlIlIlIllIIIl.processControlPoints();
            lIlIlIlIllIIIl.updateScoreBoards();
            if (JocTeamDominion.lIlIIIIIllI(JocTeamDominion.lIlIIIIlIll(lIlIlIlIllIIIl.getHeartbeatCount() % 2L, 0L))) {
                lIlIlIlIllIIIl.comprovarGuanyador();
            }
        }
    }

    static {
        JocTeamDominion.lIlIIIIIlII();
        JocTeamDominion.lIIlllllIlI();
    }

    protected JocEquips.Equip getWinnerTeam() {
        JocTeamDominion lIlIlIlIllllll;
        if (JocTeamDominion.lIlIIIIIllI(lIlIlIlIllllll.ControlPoints.size())) {
            return null;
        }
        if (JocTeamDominion.lIlIIIIIlll((Object)lIlIlIlIllllll.getGameGoal(), (Object)GameGoalType.CaptureAll)) {
            JocEquips.Equip lIlIlIllIIIlIl = lIlIlIlIllllll.ControlPoints.get(lIllIIllI[0]).getOwnerTeam();
            if (JocTeamDominion.lIlIIIIlIII(lIlIlIllIIIlIl)) {
                return null;
            }
            int lIlIlIllIIIlII = lIlIlIllIIIlIl.getId();
            int lIlIlIllIIIIll = lIllIIllI[1];
            Iterator<ControlPoint> lIlIlIlIlllIll = lIlIlIlIllllll.ControlPoints.iterator();
            while (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIlIlllIll.hasNext())) {
                ControlPoint lIlIlIllIIIllI = lIlIlIlIlllIll.next();
                if (!JocTeamDominion.lIlIIIIlIIl(lIlIlIllIIIllI.getOwnerTeam().getId(), lIlIlIllIIIlII) || JocTeamDominion.lIlIIIIIllI((int)lIlIlIllIIIllI.isCaptured())) {
                    return null;
                }
                "".length();
                if (-"   ".length() <= 0) continue;
                return null;
            }
            return lIlIlIlIllllll.obtenirEquip(lIlIlIllIIIlII);
        }
        if (JocTeamDominion.lIlIIIIIlll((Object)lIlIlIlIllllll.getGameGoal(), (Object)GameGoalType.ScoreRace)) {
            Iterator lIlIlIllIIIlIl = lIlIlIlIllllll.Equips.iterator();
            while (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIllIIIlIl.hasNext())) {
                JocEquips.Equip lIlIlIllIIIIIl = (JocEquips.Equip)lIlIlIllIIIlIl.next();
                EquipDominion lIlIlIllIIIIlI = (EquipDominion)lIlIlIllIIIIIl;
                if (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIllIIIIlI.hasWon())) {
                    return lIlIlIllIIIIIl;
                }
                "".length();
                if ((141 + 67 - 118 + 61 ^ 74 + 79 - 152 + 145) > 0) continue;
                return null;
            }
        }
        return null;
    }

    private static String lIIllllIIll(String lIlIlIIlIllIII, String lIlIlIIlIlIlll) {
        lIlIlIIlIllIII = new String(Base64.getDecoder().decode(lIlIlIIlIllIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIlIIlIllIll = new StringBuilder();
        char[] lIlIlIIlIllIlI = lIlIlIIlIlIlll.toCharArray();
        int lIlIlIIlIllIIl = lIllIIllI[0];
        char[] lIlIlIIlIlIIll = lIlIlIIlIllIII.toCharArray();
        int lIlIlIIlIlIIlI = lIlIlIIlIlIIll.length;
        int lIlIlIIlIlIIIl = lIllIIllI[0];
        while (JocTeamDominion.lIlIIIlIIIl(lIlIlIIlIlIIIl, lIlIlIIlIlIIlI)) {
            char lIlIlIIlIllllI = lIlIlIIlIlIIll[lIlIlIIlIlIIIl];
            "".length();
            lIlIlIIlIllIll.append((char)(lIlIlIIlIllllI ^ lIlIlIIlIllIlI[lIlIlIIlIllIIl % lIlIlIIlIllIlI.length]));
            ++lIlIlIIlIllIIl;
            ++lIlIlIIlIlIIIl;
            "".length();
            if (("  ".length() & ("  ".length() ^ -" ".length())) <= 0) continue;
            return null;
        }
        return String.valueOf(lIlIlIIlIllIll);
    }

    private static boolean lIlIIIIllII(int n, int n2) {
        return n > n2;
    }

    public float getMaxHealth() {
        JocTeamDominion lIlIlIlIIIIIll;
        return 500.0f + (float)lIlIlIlIIIIIll.ControlPoints.size() * 150.0f;
    }

    private static void lIlIIIIIlII() {
        lIllIIllI = new int[11];
        JocTeamDominion.lIllIIllI[0] = (140 ^ 147) & ~(216 ^ 199);
        JocTeamDominion.lIllIIllI[1] = " ".length();
        JocTeamDominion.lIllIIllI[2] = "  ".length();
        JocTeamDominion.lIllIIllI[3] = 108 + 126 - 87 + 18 ^ 90 + 27 - 108 + 172;
        JocTeamDominion.lIllIIllI[4] = "   ".length();
        JocTeamDominion.lIllIIllI[5] = 145 + 22 - 111 + 107 ^ 118 + 27 - 1 + 23;
        JocTeamDominion.lIllIIllI[6] = 129 + 151 - 273 + 159 ^ 76 + 21 - 55 + 121;
        JocTeamDominion.lIllIIllI[7] = 50 ^ 60;
        JocTeamDominion.lIllIIllI[8] = 74 ^ 76;
        JocTeamDominion.lIllIIllI[9] = 175 ^ 168;
        JocTeamDominion.lIllIIllI[10] = 25 ^ 17;
    }

    protected void comprovarGuanyador() {
        JocTeamDominion lIlIlIlIllIlIl;
        JocEquips.Equip lIlIlIlIllIllI = lIlIlIlIllIlIl.getWinnerTeam();
        if (JocTeamDominion.lIlIIIIlIlI(lIlIlIlIllIllI)) {
            lIlIlIlIllIlIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIlIlIlll[lIllIIllI[0]]).append((Object)lIlIlIlIllIllI.getChatColor()).append(lIlIlIlIllIllI.getAdjectiu()).append((Object)ChatColor.GRAY).append(lIlIlIlll[lIllIIllI[1]])));
            lIlIlIlIllIlIl.winGame(lIlIlIlIllIllI);
        }
    }

    private static boolean lIlIIIIIllI(int n) {
        return n == 0;
    }

    protected void initControlPoints() {
        JocTeamDominion lIlIlIllIllIIl;
        lIlIlIllIllIIl.ControlPoints = lIlIlIllIllIIl.getDesiredControlPoints();
        Iterator<ControlPoint> lIlIlIllIlIlll = lIlIlIllIllIIl.ControlPoints.iterator();
        while (JocTeamDominion.lIlIIIIIlIl((int)lIlIlIllIlIlll.hasNext())) {
            ControlPoint lIlIlIllIllIlI = lIlIlIllIlIlll.next();
            lIlIlIllIllIlI.init();
            "".length();
            if (-" ".length() <= " ".length()) continue;
            return;
        }
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIlIlIlIIIlIll, Player lIlIlIlIIIlllI, Player lIlIlIlIIIllIl) {
        JocTeamDominion lIlIlIlIIIllII;
        super.onPlayerDeathByPlayer(lIlIlIlIIIlIll, lIlIlIlIIIlllI, lIlIlIlIIIllIl);
        if (JocTeamDominion.lIlIIIIIlll((Object)lIlIlIlIIIllII.getGameGoal(), (Object)GameGoalType.ScoreRace)) {
            EquipDominion lIlIlIlIIlIIlI = (EquipDominion)lIlIlIlIIIllII.obtenirEquip(lIlIlIlIIIllIl);
            EquipDominion lIlIlIlIIlIIIl = (EquipDominion)lIlIlIlIIIllII.obtenirEquip(lIlIlIlIIIlllI);
            if (JocTeamDominion.lIlIIIIlIlI(lIlIlIlIIlIIlI) && JocTeamDominion.lIlIIIIlIlI(lIlIlIlIIlIIIl) && JocTeamDominion.lIlIIIIllIl(lIlIlIlIIlIIlI, lIlIlIlIIlIIIl)) {
                int lIlIlIlIIlIlII = lIllIIllI[7] - lIlIlIlIIIllII.getPlayers().size();
                if (JocTeamDominion.lIlIIIIlllI(lIlIlIlIIlIlII)) {
                    lIlIlIlIIlIlII = lIllIIllI[0];
                }
                int lIlIlIlIIlIIll = (int)Math.ceil((double)lIlIlIlIIlIlII * Math.sqrt(lIlIlIlIIlIIIl.getPercent() / 100.0f) * lIlIlIlIIIllII.getBalancingMultiplier(lIlIlIlIIlIIlI));
                lIlIlIlIIlIIIl.setHealth(Float.valueOf(lIlIlIlIIlIIIl.getHealth().floatValue() - (float)lIlIlIlIIlIIll));
                if (JocTeamDominion.lIlIIIIIlIl(lIlIlIlIIlIIll)) {
                    lIlIlIlIIIlIll.setDeathMessage(String.valueOf(new StringBuilder().append(lIlIlIlIIIlIll.getDeathMessage()).append((Object)ChatColor.GOLD).append(lIlIlIlll[lIllIIllI[8]]).append(Integer.toString(lIlIlIlIIlIIll)).append(lIlIlIlll[lIllIIllI[9]])));
                }
            }
        }
    }

    private static boolean lIlIIIIlIII(Object object) {
        return object == null;
    }

    private static boolean lIlIIIIlllI(int n) {
        return n < 0;
    }

    protected GameGoalType getGameGoal() {
        return GameGoalType.CaptureAll;
    }

    private static void lIIlllllIlI() {
        lIlIlIlll = new String[lIllIIllI[10]];
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[0]] = JocTeamDominion.lIIllIlIIII("LdlEv3dYoyKC12Qla/NOAg==", "fCALs");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[1]] = JocTeamDominion.lIIllIlIIII("MAMhn1XRaoRnPUuS4MJSIycrXHSxdv8e", "SfHMm");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[2]] = JocTeamDominion.lIIllllIIll("DR7CuRQHOw4=", "VSYln");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[4]] = JocTeamDominion.lIIllllIIll("bE0=", "GmpbG");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[5]] = JocTeamDominion.lIIllllIlIl("Vmw6Pv67OyYNumb5h0CWgw==", "TPzMv");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[6]] = JocTeamDominion.lIIllllIlIl("cxXUNy3B/6A=", "rdAdL");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[8]] = JocTeamDominion.lIIllllIIll("Qzhb", "ccvEW");
        JocTeamDominion.lIlIlIlll[JocTeamDominion.lIllIIllI[9]] = JocTeamDominion.lIIllIlIIII("kVf3yKHB/SA=", "ynWaD");
    }

    protected abstract ArrayList<ControlPoint> getDesiredControlPoints();

    public class BubbleControlPointRenderer
    extends DyeColorControlPointRenderer {
        /* synthetic */ byte neutralColor;
        private static final /* synthetic */ int[] lII;

        @Override
        public byte getDyeColorForBlock(PhysicalControlPoint llllllllllIIlII, Block llllllllllIIIll) {
            JocEquips.Equip llllllllllIlllI;
            Location llllllllllIIllI;
            float llllllllllIlIlI = llllllllllIIlII.getPercent() / 100.0f;
            DyeColor llllllllllIlIIl = DyeColor.CYAN;
            double llllllllllIlIII = llllllllllIIlII.getEquivalentRadius() * Math.sqrt(llllllllllIlIlI);
            Location llllllllllIIlll = llllllllllIIlII.getCenter().getBlock().getLocation();
            double llllllllllIIlIl = Utils.distanceXZ(llllllllllIIlll, llllllllllIIllI = llllllllllIIIll.getLocation());
            if (BubbleControlPointRenderer.llI(BubbleControlPointRenderer.lIl(llllllllllIIlIl, llllllllllIlIII)) && BubbleControlPointRenderer.lll(llllllllllIlllI = llllllllllIIlII.getOwnerTeam())) {
                llllllllllIlIIl = llllllllllIlllI.getColor();
            }
            return llllllllllIlIIl.getWoolData();
        }

        private static boolean llI(int n) {
            return n < 0;
        }

        private static boolean lll(Object object) {
            return object != null;
        }

        static {
            BubbleControlPointRenderer.ll();
        }

        public BubbleControlPointRenderer() {
            BubbleControlPointRenderer llllllllllllIIl;
            super();
            llllllllllllIIl.neutralColor = (byte)lII[0];
        }

        private static int lIl(double d, double d2) {
            return (int)(d DCMPG d2);
        }

        private static void ll() {
            lII = new int[1];
            BubbleControlPointRenderer.lII[0] = (40 ^ 27) & ~(121 ^ 74);
        }
    }

    public abstract class DyeColorControlPointRenderer
    extends ControlPointRenderer {
        @Override
        public void renderEnvironment(PhysicalControlPoint llllllIlIIIIlI) {
            Iterator<Block> llllllIlIIIIIl = llllllIlIIIIlI.getEnvironmentBlocks().iterator();
            while (DyeColorControlPointRenderer.lllIllIllI((int)llllllIlIIIIIl.hasNext())) {
                Block llllllIlIIIllI = llllllIlIIIIIl.next();
                if (!DyeColorControlPointRenderer.lllIllIlll((Object)llllllIlIIIllI.getType(), (Object)Material.WOOL) || !DyeColorControlPointRenderer.lllIllIlll((Object)llllllIlIIIllI.getType(), (Object)Material.STAINED_CLAY) || !DyeColorControlPointRenderer.lllIllIlll((Object)llllllIlIIIllI.getType(), (Object)Material.STAINED_GLASS) || DyeColorControlPointRenderer.lllIlllIII((Object)llllllIlIIIllI.getType(), (Object)Material.STAINED_GLASS_PANE)) {
                    DyeColorControlPointRenderer llllllIlIIIlIl;
                    llllllIlIIIllI.setData(llllllIlIIIlIl.getDyeColorForBlock(llllllIlIIIIlI, llllllIlIIIllI));
                }
                "".length();
                if (null == null) continue;
                return;
            }
        }

        private static boolean lllIllIlll(Object object, Object object2) {
            return object != object2;
        }

        public abstract byte getDyeColorForBlock(PhysicalControlPoint var1, Block var2);

        public DyeColorControlPointRenderer() {
            DyeColorControlPointRenderer llllllIlIIllII;
            super();
        }

        private static boolean lllIlllIII(Object object, Object object2) {
            return object == object2;
        }

        private static boolean lllIllIllI(int n) {
            return n != 0;
        }
    }

    public class PieControlPointRenderer
    extends DyeColorControlPointRenderer {
        private static final /* synthetic */ int[] lIllIII;

        private static int lIIllllll(double d, double d2) {
            return (int)(d DCMPG d2);
        }

        private static void lIIllllII() {
            lIllIII = new int[2];
            PieControlPointRenderer.lIllIII[0] = (131 ^ 182 ^ (65 ^ 49)) & ((0 ^ 42) & ~(183 ^ 157) ^ (104 ^ 45) ^ -" ".length());
            PieControlPointRenderer.lIllIII[1] = " ".length();
        }

        static {
            PieControlPointRenderer.lIIllllII();
        }

        private static boolean lIlIIIIlI(int n) {
            return n <= 0;
        }

        private static int lIIlllllI(double d, double d2) {
            return (int)(d DCMPL d2);
        }

        public Slice getSliceForBlock(PhysicalControlPoint llIIllllllIIIlI, Block llIIllllllIIIIl) {
            PieControlPointRenderer llIIllllllIIIll;
            ArrayList<Slice> llIIllllllIllII = llIIllllllIIIll.getSlices(llIIllllllIIIlI);
            double llIIllllllIlIll = 0.0;
            Iterator<Slice> llIIlllllIllllI = llIIllllllIllII.iterator();
            while (PieControlPointRenderer.lIlIIIIII((int)llIIlllllIllllI.hasNext())) {
                Slice llIIlllllllIIll = llIIlllllIllllI.next();
                llIIllllllIlIll += llIIlllllllIIll.value;
                "".length();
                if ("   ".length() >= 0) continue;
                return null;
            }
            double llIIllllllIlIlI = 0.0;
            int llIIllllllIlIIl = llIIllllllIIIIl.getX();
            int llIIllllllIlIII = llIIllllllIIIIl.getZ();
            double llIIllllllIIlll = Math.sqrt(llIIllllllIlIII * llIIllllllIlIII + llIIllllllIlIIl * llIIllllllIlIIl + lIllIII[1]);
            double llIIllllllIIllI = (double)llIIllllllIlIIl / llIIllllllIIlll;
            double llIIllllllIIlIl = Math.acos(llIIllllllIIllI);
            double llIIllllllIIlII = llIIllllllIlIlI;
            Iterator<Slice> llIIlllllIlIllI = llIIllllllIllII.iterator();
            while (PieControlPointRenderer.lIlIIIIII((int)llIIlllllIlIllI.hasNext())) {
                Slice llIIlllllllIIII = llIIlllllIlIllI.next();
                double llIIlllllllIIlI = llIIlllllllIIII.value / llIIllllllIlIll * 2.0 * 3.141592653589793;
                double llIIlllllllIIIl = llIIllllllIIlII + llIIlllllllIIlI;
                if (PieControlPointRenderer.lIlIIIIIl(PieControlPointRenderer.lIIlllllI(llIIllllllIIlIl, llIIllllllIIlII)) && PieControlPointRenderer.lIlIIIIlI(PieControlPointRenderer.lIIllllll(llIIllllllIIlIl, llIIlllllllIIIl))) {
                    return llIIlllllllIIII;
                }
                llIIllllllIIlII += llIIlllllllIIlI;
                "".length();
                if (" ".length() != 0) continue;
                return null;
            }
            return null;
        }

        private static boolean lIlIIIIIl(int n) {
            return n >= 0;
        }

        @Override
        public byte getDyeColorForBlock(PhysicalControlPoint llIlIIIIIlllIII, Block llIlIIIIIllIIll) {
            PieControlPointRenderer llIlIIIIIllIlIl;
            Slice llIlIIIIIllIllI = llIlIIIIIllIlIl.getSliceForBlock(llIlIIIIIlllIII, llIlIIIIIllIIll);
            if (PieControlPointRenderer.lIIllllIl(llIlIIIIIllIllI)) {
                return llIlIIIIIllIllI.color;
            }
            return lIllIII[0];
        }

        public PieControlPointRenderer() {
            PieControlPointRenderer llIlIIIIlIlIIII;
        }

        private static boolean lIIllllIl(Object object) {
            return object != null;
        }

        private static boolean lIlIIIIII(int n) {
            return n != 0;
        }

        public ArrayList<Slice> getSlices(PhysicalControlPoint llIlIIIIIlIlIll) {
            PieControlPointRenderer llIlIIIIIlIllII;
            ArrayList<Slice> llIlIIIIIlIlIlI = new ArrayList<Slice>();
            JocEquips.Equip llIlIIIIIlIlIIl = llIlIIIIIlIlIll.getOwnerTeam();
            DyeColor llIlIIIIIlIlIII = DyeColor.WHITE;
            if (PieControlPointRenderer.lIIllllIl(llIlIIIIIlIlIIl)) {
                llIlIIIIIlIlIII = llIlIIIIIlIlIIl.getColor();
            }
            "".length();
            llIlIIIIIlIlIlI.add(llIlIIIIIlIllII.new Slice(llIlIIIIIlIlIll.getPercent(), llIlIIIIIlIlIII.getWoolData()));
            "".length();
            llIlIIIIIlIlIlI.add(llIlIIIIIlIllII.new Slice(100.0f - llIlIIIIIlIlIll.getPercent(), DyeColor.WHITE.getWoolData()));
            return llIlIIIIIlIlIlI;
        }

        class Slice {
            /* synthetic */ double value;
            /* synthetic */ byte color;

            public Slice(double lIlIIlIlIIIIllI, byte lIlIIlIlIIIlIIl) {
                Slice lIlIIlIlIIIllII;
                lIlIIlIlIIIllII.value = lIlIIlIlIIIIllI;
                lIlIIlIlIIIllII.color = lIlIIlIlIIIlIIl;
            }
        }

    }

    public static final class GameGoalType
    extends Enum<GameGoalType> {
        public static final /* synthetic */ /* enum */ GameGoalType LifeDraining;
        public static final /* synthetic */ /* enum */ GameGoalType CaptureAll;
        private static final /* synthetic */ GameGoalType[] $VALUES;
        public static final /* synthetic */ /* enum */ GameGoalType ScoreRace;
        private static final /* synthetic */ String[] lIIlIllIl;
        private static final /* synthetic */ int[] lIIllIIIl;

        public static GameGoalType valueOf(String llIlIlIllIIIIl) {
            return Enum.valueOf(GameGoalType.class, llIlIlIllIIIIl);
        }

        private static boolean lIIIllIIIlI(int n, int n2) {
            return n < n2;
        }

        private static String lIIIlIIIlII(String llIlIlIlIIlIIl, String llIlIlIlIIlIII) {
            try {
                SecretKeySpec llIlIlIlIIllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIlIlIIlIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llIlIlIlIIlIll = Cipher.getInstance("Blowfish");
                llIlIlIlIIlIll.init(lIIllIIIl[2], llIlIlIlIIllII);
                return new String(llIlIlIlIIlIll.doFinal(Base64.getDecoder().decode(llIlIlIlIIlIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIlIlIlIIlIlI) {
                llIlIlIlIIlIlI.printStackTrace();
                return null;
            }
        }

        public static GameGoalType[] values() {
            return (GameGoalType[])$VALUES.clone();
        }

        static {
            GameGoalType.lIIIlIlllIl();
            GameGoalType.lIIIlIIIlll();
            CaptureAll = new GameGoalType();
            LifeDraining = new GameGoalType();
            ScoreRace = new GameGoalType();
            GameGoalType[] arrgameGoalType = new GameGoalType[lIIllIIIl[3]];
            arrgameGoalType[GameGoalType.lIIllIIIl[0]] = CaptureAll;
            arrgameGoalType[GameGoalType.lIIllIIIl[1]] = LifeDraining;
            arrgameGoalType[GameGoalType.lIIllIIIl[2]] = ScoreRace;
            $VALUES = arrgameGoalType;
        }

        private static void lIIIlIlllIl() {
            lIIllIIIl = new int[4];
            GameGoalType.lIIllIIIl[0] = (28 + 54 - 60 + 154 ^ 131 + 36 - 47 + 46) & (219 ^ 186 ^ (99 ^ 20) ^ -" ".length());
            GameGoalType.lIIllIIIl[1] = " ".length();
            GameGoalType.lIIllIIIl[2] = "  ".length();
            GameGoalType.lIIllIIIl[3] = "   ".length();
        }

        private static void lIIIlIIIlll() {
            lIIlIllIl = new String[lIIllIIIl[3]];
            GameGoalType.lIIlIllIl[GameGoalType.lIIllIIIl[0]] = GameGoalType.lIIIlIIIlII("d52QPwQDgollqD1DXaapVw==", "tOigM");
            GameGoalType.lIIlIllIl[GameGoalType.lIIllIIIl[1]] = GameGoalType.lIIIlIIIllI("FiIxPx4oKj40MzQs", "ZKWZZ");
            GameGoalType.lIIlIllIl[GameGoalType.lIIllIIIl[2]] = GameGoalType.lIIIlIIIllI("FCsVJCkVKRkz", "GHzVL");
        }

        private GameGoalType() {
            GameGoalType llIlIlIlIllIII;
        }

        private static String lIIIlIIIllI(String llIlIlIIlllIIl, String llIlIlIIllIIll) {
            llIlIlIIlllIIl = new String(Base64.getDecoder().decode(llIlIlIIlllIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llIlIlIIllIlll = new StringBuilder();
            char[] llIlIlIIllIllI = llIlIlIIllIIll.toCharArray();
            int llIlIlIIllIlIl = lIIllIIIl[0];
            char[] llIlIlIIlIllll = llIlIlIIlllIIl.toCharArray();
            int llIlIlIIlIlllI = llIlIlIIlIllll.length;
            int llIlIlIIlIllIl = lIIllIIIl[0];
            while (GameGoalType.lIIIllIIIlI(llIlIlIIlIllIl, llIlIlIIlIlllI)) {
                char llIlIlIIlllIlI = llIlIlIIlIllll[llIlIlIIlIllIl];
                "".length();
                llIlIlIIllIlll.append((char)(llIlIlIIlllIlI ^ llIlIlIIllIllI[llIlIlIIllIlIl % llIlIlIIllIllI.length]));
                ++llIlIlIIllIlIl;
                ++llIlIlIIlIllIl;
                "".length();
                if ("  ".length() > ((48 ^ 97) & ~(67 ^ 18))) continue;
                return null;
            }
            return String.valueOf(llIlIlIIllIlll);
        }
    }

    public class RadialControlPoint
    extends PhysicalControlPoint {
        /* synthetic */ Double radius;
        private static final /* synthetic */ int[] lIIlIlIIl;
        /* synthetic */ Location center;

        public RadialControlPoint(String llIllIlllIIIlI, ControlPointRenderer llIllIlllIIIIl, Location llIllIlllIIllI, Double llIllIlllIIlIl) {
            RadialControlPoint llIllIlllIIlII;
            super(llIllIlllIIIlI, llIllIlllIIIIl);
            llIllIlllIIlII.center = llIllIlllIIllI;
            llIllIlllIIlII.radius = llIllIlllIIlIl;
        }

        @Override
        public double getEquivalentRadius() {
            RadialControlPoint llIllIllIIIlIl;
            return llIllIllIIIlIl.getRadius();
        }

        public Double getRadius() {
            RadialControlPoint llIllIllIlIIll;
            return llIllIllIlIIll.radius;
        }

        @Override
        public void playIncrementScoreEffect() {
            RadialControlPoint llIllIllIIIIIl;
            llIllIllIIIIIl.center.getWorld().playEffect(llIllIllIIIIIl.getCenter().add(0.0, 1.0, 0.0), Effect.MOBSPAWNER_FLAMES, lIIlIlIIl[1]);
        }

        public void setCenter(Location llIllIllIllIII) {
            llIllIllIllIIl.center = llIllIllIllIII;
        }

        @Override
        public Location getCenter() {
            RadialControlPoint llIllIllIlllIl;
            return llIllIllIlllIl.center.clone();
        }

        @Override
        public ArrayList<Block> getEnvironmentBlocks() {
            RadialControlPoint llIllIllIIlIII;
            return Utils.getCylBlocks(llIllIllIIlIII.getCenter().getBlock().getLocation().add(0.0, -8.0, 0.0), (int)Math.round(llIllIllIIlIII.radius), (int)(5.0 * llIllIllIIlIII.radius), lIIlIlIIl[0]);
        }

        @Override
        public void playNeutralizeEffect() {
            RadialControlPoint llIllIlIllIlII;
            Iterator<Location> llIllIlIllIIlI = Utils.getLocationsCircle(llIllIlIllIlII.getCenter().add(0.0, 1.0, 0.0), llIllIlIllIlII.radius / 2.0, lIIlIlIIl[2]).iterator();
            while (RadialControlPoint.lIIIIllllIl((int)llIllIlIllIIlI.hasNext())) {
                Location llIllIlIllIlIl = llIllIlIllIIlI.next();
                llIllIlIllIlII.center.getWorld().playEffect(llIllIlIllIlIl, Effect.SMOKE, lIIlIlIIl[3]);
                "".length();
                if (" ".length() > 0) continue;
                return;
            }
            llIllIlIllIlII.center.getWorld().playSound(llIllIlIllIlII.getCenter(), Sound.BLOCK_PISTON_CONTRACT, 1.0f, 1.0f);
        }

        public void setRadius(Double llIllIllIIllIl) {
            llIllIllIlIIII.radius = llIllIllIIllIl;
        }

        private static void lIIIIllllII() {
            lIIlIlIIl = new int[4];
            RadialControlPoint.lIIlIlIIl[0] = " ".length();
            RadialControlPoint.lIIlIlIIl[1] = (140 + 31 - 66 + 37 ^ 35 + 169 - 194 + 161) & (98 ^ 46 ^ (63 ^ 86) ^ -" ".length());
            RadialControlPoint.lIIlIlIIl[2] = 109 + 70 - 72 + 78 ^ 136 + 173 - 129 + 8;
            RadialControlPoint.lIIlIlIIl[3] = 71 ^ 108 ^ (166 ^ 137);
        }

        static {
            RadialControlPoint.lIIIIllllII();
        }

        @Override
        public void playCaptureEffect() {
            RadialControlPoint llIllIlIlllIll;
            Iterator<Location> llIllIlIlllIlI = Utils.getLocationsCircle(llIllIlIlllIll.getCenter().add(0.0, 1.0, 0.0), llIllIlIlllIll.radius / 2.0, lIIlIlIIl[2]).iterator();
            while (RadialControlPoint.lIIIIllllIl((int)llIllIlIlllIlI.hasNext())) {
                Location llIllIlIllllIl = llIllIlIlllIlI.next();
                llIllIlIlllIll.center.getWorld().playEffect(llIllIlIllllIl, Effect.MOBSPAWNER_FLAMES, lIIlIlIIl[1]);
                "".length();
                if (-"   ".length() <= 0) continue;
                return;
            }
            llIllIlIlllIll.center.getWorld().playSound(llIllIlIlllIll.getCenter(), Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
        }

        @Override
        public ArrayList<Player> getStandingPlayers() {
            RadialControlPoint llIllIllIIlIll;
            return Utils.getNearbyPlayers(llIllIllIIlIll.center, (double)llIllIllIIlIll.radius);
        }

        private static boolean lIIIIllllIl(int n) {
            return n != 0;
        }

        @Override
        public Location getHologramLocation() {
            RadialControlPoint llIllIlIlIllll;
            return llIllIlIlIllll.getCenter().add(-1.0, 3.0, -1.0);
        }
    }

    public abstract class PhysicalControlPoint
    extends ControlPoint {
        public /* synthetic */ ControlPointRenderer renderer;

        @Override
        protected void renderEnvironment() {
            PhysicalControlPoint llIllllIlIIlll;
            llIllllIlIIlll.renderer.renderEnvironment(llIllllIlIIlll);
        }

        public abstract double getEquivalentRadius();

        public PhysicalControlPoint(String llIllllIlIlIlI, ControlPointRenderer llIllllIlIlIIl) {
            PhysicalControlPoint llIllllIlIllII;
            super(llIllllIlIlIlI);
            llIllllIlIllII.renderer = llIllllIlIlIIl;
        }

        @Override
        public abstract ArrayList<Player> getStandingPlayers();

        public abstract Location getCenter();

        public abstract ArrayList<Block> getEnvironmentBlocks();
    }

    protected class EquipDominion
    extends JocEquips.Equip {
        /* synthetic */ Float health;
        private static final /* synthetic */ int[] lIIlllII;
        private static final /* synthetic */ String[] lIIlIlll;

        private static boolean lIIlIlIIlI(int n, int n2) {
            return n < n2;
        }

        public float getMaxHealth() {
            EquipDominion lIlIlIlIlIIllII;
            return 500.0f + (float)lIlIlIlIlIIllII.JocTeamDominion.this.ControlPoints.size() * 150.0f;
        }

        private static void lIIlIlIIII() {
            lIIlllII = new int[7];
            EquipDominion.lIIlllII[0] = 22 ^ 91 ^ (105 ^ 34);
            EquipDominion.lIIlllII[1] = (40 ^ 20 ^ (90 ^ 124)) & (138 + 69 - 166 + 146 ^ 158 + 56 - 70 + 17 ^ -" ".length());
            EquipDominion.lIIlllII[2] = " ".length();
            EquipDominion.lIIlllII[3] = "  ".length();
            EquipDominion.lIIlllII[4] = "   ".length();
            EquipDominion.lIIlllII[5] = 53 ^ 49;
            EquipDominion.lIIlllII[6] = 113 ^ 116;
        }

        private static int lIIlIlIlIl(float f, float f2) {
            return (int)(f FCMPL f2);
        }

        private static String lIIIlllIIl(String lIlIlIlIIllIlIl, String lIlIlIlIIllIlII) {
            lIlIlIlIIllIlIl = new String(Base64.getDecoder().decode(lIlIlIlIIllIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIlIlIIllIIll = new StringBuilder();
            char[] lIlIlIlIIllIIlI = lIlIlIlIIllIlII.toCharArray();
            int lIlIlIlIIllIIIl = lIIlllII[1];
            char[] lIlIlIlIIlIlIll = lIlIlIlIIllIlIl.toCharArray();
            int lIlIlIlIIlIlIlI = lIlIlIlIIlIlIll.length;
            int lIlIlIlIIlIlIIl = lIIlllII[1];
            while (EquipDominion.lIIlIlIIlI(lIlIlIlIIlIlIIl, lIlIlIlIIlIlIlI)) {
                char lIlIlIlIIllIllI = lIlIlIlIIlIlIll[lIlIlIlIIlIlIIl];
                "".length();
                lIlIlIlIIllIIll.append((char)(lIlIlIlIIllIllI ^ lIlIlIlIIllIIlI[lIlIlIlIIllIIIl % lIlIlIlIIllIIlI.length]));
                ++lIlIlIlIIllIIIl;
                ++lIlIlIlIIlIlIIl;
                "".length();
                if (" ".length() <= " ".length()) continue;
                return null;
            }
            return String.valueOf(lIlIlIlIIllIIll);
        }

        private static boolean lIIlIlIlII(int n) {
            return n != 0;
        }

        private static int lIIlIlIlll(float f, float f2) {
            return (int)(f FCMPL f2);
        }

        private static int lIIlIlIIIl(float f, float f2) {
            return (int)(f FCMPG f2);
        }

        public boolean getIntegrity() {
            boolean bl;
            EquipDominion lIlIlIlIlIIllll;
            if (EquipDominion.lIIlIlIllI(EquipDominion.lIIlIlIlIl(lIlIlIlIlIIllll.health.floatValue(), 0.0f))) {
                bl = lIIlllII[2];
                "".length();
                if (" ".length() == 0) {
                    return (boolean)((186 ^ 191) & ~(51 ^ 54));
                }
            } else {
                bl = lIIlllII[1];
            }
            return bl;
        }

        public void broadcastPoint(Float lIlIlIlIlIlIlII, Float lIlIlIlIlIlIlll, Float lIlIlIlIlIlIllI) {
            if (EquipDominion.lIIlIlIlII((int)Utils.testPointUpDown(lIlIlIlIlIlIlII, lIlIlIlIlIlIlll.floatValue(), lIlIlIlIlIlIllI.floatValue()))) {
                EquipDominion lIlIlIlIlIlIlIl;
                lIlIlIlIlIlIlIl.JocTeamDominion.this.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIIlIlll[lIIlllII[1]]).append(lIlIlIlIlIlIlIl.getAdjectiuColored()).append((Object)ChatColor.GRAY).append(lIIlIlll[lIIlllII[2]]).append(Integer.toString(Math.round(lIlIlIlIlIlIlIl.health.floatValue()))).append(lIIlIlll[lIIlllII[3]]).append(Integer.toString(Math.round(lIlIlIlIlIlIlIl.getMaxHealth()))).append(lIIlIlll[lIIlllII[4]]).append(Integer.toString(Math.round(lIlIlIlIlIlIlIl.getPercent()))).append(lIIlIlll[lIIlllII[5]])));
            }
        }

        static {
            EquipDominion.lIIlIlIIII();
            EquipDominion.lIIIlllIll();
        }

        private static boolean lIIlIlIIll(int n) {
            return n < 0;
        }

        private static String lIIIlllIlI(String lIlIlIlIIlIIIII, String lIlIlIlIIIlllIl) {
            try {
                SecretKeySpec lIlIlIlIIlIIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlIlIIIlllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIlIlIlIIlIIIlI = Cipher.getInstance("Blowfish");
                lIlIlIlIIlIIIlI.init(lIIlllII[3], lIlIlIlIIlIIIll);
                return new String(lIlIlIlIIlIIIlI.doFinal(Base64.getDecoder().decode(lIlIlIlIIlIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIlIlIIlIIIIl) {
                lIlIlIlIIlIIIIl.printStackTrace();
                return null;
            }
        }

        private static void lIIIlllIll() {
            lIIlIlll = new String[lIIlllII[6]];
            EquipDominion.lIIlIlll[EquipDominion.lIIlllII[1]] = EquipDominion.lIIIlllIIl("L24KGQ0KOU8=", "cIohx");
            EquipDominion.lIIlIlll[EquipDominion.lIIlllII[2]] = EquipDominion.lIIIlllIlI("CaiJrQ3aVPw=", "HJQzH");
            EquipDominion.lIIlIlll[EquipDominion.lIIlllII[3]] = EquipDominion.lIIIlllIIl("ZQ==", "JzFWA");
            EquipDominion.lIIlIlll[EquipDominion.lIIlllII[4]] = EquipDominion.lIIIlllIIl("VQcwBhEGV20=", "uwEhe");
            EquipDominion.lIIlIlll[EquipDominion.lIIlllII[5]] = EquipDominion.lIIIlllIIl("cls=", "WrkwK");
        }

        public Float getHealth() {
            EquipDominion lIlIlIlIlllIIIl;
            return lIlIlIlIlllIIIl.health;
        }

        public EquipDominion(DyeColor lIlIlIlIllllIII, String lIlIlIlIlllIIll) {
            EquipDominion lIlIlIlIlllIllI;
            super(lIlIlIlIllllIII, lIlIlIlIlllIIll);
            lIlIlIlIlllIllI.health = Float.valueOf(0.0f);
        }

        private static boolean lIIlIlIllI(int n) {
            return n > 0;
        }

        public float getPercent(Float lIlIlIlIlIIIlIl) {
            EquipDominion lIlIlIlIlIIIllI;
            return lIlIlIlIlIIIlIl.floatValue() / lIlIlIlIlIIIllI.getMaxHealth() * 100.0f;
        }

        private static boolean lIIlIllIII(int n) {
            return n >= 0;
        }

        public void setHealth(Float lIlIlIlIllIIIll) {
            EquipDominion lIlIlIlIllIIlll;
            float[] lIlIlIlIllIIlIl;
            float[] arrf = new float[lIIlllII[0]];
            arrf[EquipDominion.lIIlllII[1]] = 10.0f;
            arrf[EquipDominion.lIIlllII[2]] = 25.0f;
            arrf[EquipDominion.lIIlllII[3]] = 50.0f;
            arrf[EquipDominion.lIIlllII[4]] = 75.0f;
            arrf[EquipDominion.lIIlllII[5]] = 90.0f;
            arrf[EquipDominion.lIIlllII[6]] = 95.0f;
            float[] lIlIlIlIllIIIIl = lIlIlIlIllIIlIl = arrf;
            int lIlIlIlIllIIIII = lIlIlIlIllIIIIl.length;
            int lIlIlIlIlIlllll = lIIlllII[1];
            while (EquipDominion.lIIlIlIIlI(lIlIlIlIlIlllll, lIlIlIlIllIIIII)) {
                float lIlIlIlIllIlIII = lIlIlIlIllIIIIl[lIlIlIlIlIlllll];
                lIlIlIlIllIIlll.broadcastPoint(Float.valueOf(lIlIlIlIllIlIII), Float.valueOf(lIlIlIlIllIIlll.getPercent()), Float.valueOf(lIlIlIlIllIIlll.getPercent(lIlIlIlIllIIIll)));
                ++lIlIlIlIlIlllll;
                "".length();
                if ("  ".length() == "  ".length()) continue;
                return;
            }
            lIlIlIlIllIIlll.health = lIlIlIlIllIIIll;
            if (EquipDominion.lIIlIlIIll(EquipDominion.lIIlIlIIIl(lIlIlIlIllIIlll.health.floatValue(), 0.0f))) {
                lIlIlIlIllIIlll.health = Float.valueOf(0.0f);
            }
        }

        public boolean hasWon() {
            boolean bl;
            EquipDominion lIlIlIlIlIIlIIl;
            if (EquipDominion.lIIlIllIII(EquipDominion.lIIlIlIlll(lIlIlIlIlIIlIIl.health.floatValue(), lIlIlIlIlIIlIIl.getMaxHealth()))) {
                bl = lIIlllII[2];
                "".length();
                if ("   ".length() != "   ".length()) {
                    return (boolean)((158 ^ 171 ^ (243 ^ 158)) & (161 ^ 174 ^ (108 ^ 59) ^ -" ".length()));
                }
            } else {
                bl = lIIlllII[1];
            }
            return bl;
        }

        public float getPercent() {
            EquipDominion lIlIlIlIlIIIIII;
            return lIlIlIlIlIIIIII.getPercent(lIlIlIlIlIIIIII.health);
        }
    }

    protected abstract class ControlPoint {
        /* synthetic */ float captureRateMultiplier;
        /* synthetic */ boolean captured;
        /* synthetic */ int tendency;
        /* synthetic */ Hologram h;
        /* synthetic */ float basePointReward;
        /* synthetic */ int ownerTeam;
        /* synthetic */ String name;
        private static final /* synthetic */ int[] lIlIIlII;
        private static final /* synthetic */ String[] lIIlIIlI;
        /* synthetic */ int dominationPower;
        /* synthetic */ float percent;
        /* synthetic */ int dominatingTeamID;

        public void createHolgram() {
            ControlPoint lIIllllIllIlllI;
            if (ControlPoint.lIIlllllll((Object)lIIllllIllIlllI.h)) {
                return;
            }
            lIIllllIllIlllI.h = HologramsAPI.createHologram((Plugin)Com.getPlugin(), (Location)lIIllllIllIlllI.getHologramLocation());
        }

        public abstract Location getHologramLocation();

        public String getName() {
            ControlPoint lIIlllllIIlIlll;
            return lIIlllllIIlIlll.name;
        }

        public abstract void playCaptureEffect();

        public void updateHologram() {
            ControlPoint lIIllllIllIlIll;
            if (ControlPoint.lIlIIIIIIl((Object)lIIllllIllIlIll.h)) {
                return;
            }
            lIIllllIllIlIll.h.clearLines();
            "".length();
            lIIllllIllIlIll.h.appendTextLine(String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(lIIllllIllIlIll.getDisplayName())));
            if (ControlPoint.lIlIIIIIlI(ControlPoint.lIlIIIIIII(lIIllllIllIlIll.percent, 100.0f))) {
                "".length();
                lIIllllIllIlIll.h.appendTextLine(lIIllllIllIlIll.getProgressBar());
            }
            "".length();
            lIIllllIllIlIll.h.appendTextLine(String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(Integer.toString(Math.round(lIIllllIllIlIll.percent))).append(lIIlIIlI[lIlIIlII[1]])));
            if (ControlPoint.lIlIIIIIlI((int)lIIllllIllIlIll.captured)) {
                "".length();
                lIIllllIllIlIll.h.appendTextLine(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIIlIIlI[lIlIIlII[2]]).append(Float.toString((float)Math.round(lIIllllIllIlIll.getPointReward() * 10.0f) / 10.0f)).append(lIIlIIlI[lIlIIlII[3]])));
            }
        }

        public int getDominationPower(ArrayList<ArrayList<Player>> lIIlllIlllIlIII) {
            Object lIIlllIlllIlIll;
            int lIIlllIlllIIlll = lIlIIlII[1];
            int lIIlllIlllIIllI = lIlIIlII[1];
            if (ControlPoint.lIlIlIIIlI(lIIlllIlllIlIII.size())) {
                lIIlllIlllIlIll = lIIlllIlllIlIII.get(lIlIIlII[1]);
                lIIlllIlllIIlll = ((ArrayList)lIIlllIlllIlIll).size();
            }
            if (ControlPoint.lIlIIllIII(lIIlllIlllIlIII.size(), lIlIIlII[2])) {
                lIIlllIlllIlIll = lIIlllIlllIlIII.iterator();
                while (ControlPoint.lIlIIIIIlI((int)lIIlllIlllIlIll.hasNext())) {
                    ArrayList lIIlllIlllIlIlI = (ArrayList)lIIlllIlllIlIll.next();
                    if (ControlPoint.lIlIIlllll(lIIlllIlllIlIII.indexOf(lIIlllIlllIlIlI))) {
                        "".length();
                        if (null == null) continue;
                        return (110 + 189 - 150 + 58 ^ 86 + 88 - 116 + 70) & (103 + 30 - 106 + 112 ^ 84 + 70 - 151 + 193 ^ -" ".length());
                    }
                    lIIlllIlllIIllI += lIIlllIlllIlIlI.size();
                    "".length();
                    if ("  ".length() >= "  ".length()) continue;
                    return (223 ^ 133 ^ (122 ^ 56)) & (16 ^ 30 ^ (104 ^ 126) ^ -" ".length());
                }
            }
            return lIIlllIlllIIlll - lIIlllIlllIIllI;
        }

        public JocEquips.Equip getOwnerTeam() {
            ControlPoint lIIllllIlIIllII;
            if (ControlPoint.lIlIIIIlII(lIIllllIlIIllII.ownerTeam, lIlIIlII[0])) {
                return null;
            }
            return lIIllllIlIIllII.JocTeamDominion.this.obtenirEquip(lIIllllIlIIllII.ownerTeam);
        }

        private static int lIlIIIIlIl(float f, float f2) {
            return (int)(f FCMPL f2);
        }

        public void sendActionMessage(String lIIllllIIIllIII) {
            ControlPoint lIIllllIIIllIIl;
            lIIllllIIIllIIl.JocTeamDominion.this.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIIlIIlI[lIlIIlII[15]]).append(lIIllllIIIllIIl.getOwnerTeam().getAdjectiuColored()).append((Object)ChatColor.GRAY).append(lIIlIIlI[lIlIIlII[16]]).append(lIIllllIIIllIII).append(lIIlIIlI[lIlIIlII[17]]).append((Object)ChatColor.YELLOW).append(lIIllllIIIllIIl.getName().toLowerCase())));
        }

        private static boolean lIlIIllIll(Object object, Object object2) {
            return object == object2;
        }

        public void setBasePointReward(float lIIllllIllllIll) {
            lIIllllIllllIlI.basePointReward = lIIllllIllllIll;
        }

        private static String lIIIIllIlI(String lIIlllIlIIlIlll, String lIIlllIlIIlIlII) {
            try {
                SecretKeySpec lIIlllIlIIllIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlllIlIIlIlII.getBytes(StandardCharsets.UTF_8)), lIlIIlII[13]), "DES");
                Cipher lIIlllIlIIllIIl = Cipher.getInstance("DES");
                lIIlllIlIIllIIl.init(lIlIIlII[3], lIIlllIlIIllIlI);
                return new String(lIIlllIlIIllIIl.doFinal(Base64.getDecoder().decode(lIIlllIlIIlIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlllIlIIllIII) {
                lIIlllIlIIllIII.printStackTrace();
                return null;
            }
        }

        protected void onNeutralize() {
            ControlPoint lIIllllIIlIIIIl;
            lIIllllIIlIIIIl.captured = lIlIIlII[1];
            lIIllllIIlIIIIl.playNeutralizeEffect();
            lIIllllIIlIIIIl.JocTeamDominion.this.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIIlIIlI[lIlIIlII[14]]).append((Object)ChatColor.YELLOW).append(lIIllllIIlIIIIl.getName()).append((Object)ChatColor.GRAY).append(lIIlIIlI[lIlIIlII[5]])));
        }

        private static boolean lIlIIIIIIl(Object object) {
            return object == null;
        }

        public abstract void playIncrementScoreEffect();

        public int getDominatingTeamID(ArrayList<ArrayList<Player>> lIIlllIllllIlIl) {
            ArrayList<Player> lIIlllIllllIlll;
            if (ControlPoint.lIlIlIIIlI(lIIlllIllllIlIl.size()) && ControlPoint.lIlIlIIIlI((lIIlllIllllIlll = lIIlllIllllIlIl.get(lIlIIlII[1])).size())) {
                ControlPoint lIIlllIllllIllI;
                Player lIIlllIlllllIII = lIIlllIllllIlll.get(lIlIIlII[1]);
                return lIIlllIllllIllI.JocTeamDominion.this.obtenirEquip(lIIlllIlllllIII).getId();
            }
            return lIlIIlII[0];
        }

        static {
            ControlPoint.lIIllllllI();
            ControlPoint.lIIIlllIII();
        }

        public void setOwnerTeam(int lIIllllIlllIlIl) {
            lIIllllIlllIllI.ownerTeam = lIIllllIlllIlIl;
        }

        public float getBasePointReward() {
            ControlPoint lIIlllllIIIIIII;
            return lIIlllllIIIIIII.basePointReward;
        }

        public String getDisplayName() {
            ControlPoint lIIllllIlIIIIII;
            JocEquips.Equip lIIllllIlIIIIll = lIIllllIlIIIIII.getOwnerTeam();
            ChatColor lIIllllIlIIIIlI = ChatColor.GRAY;
            String lIIllllIlIIIIIl = lIIllllIlIIIIII.getCapturingNameModifier();
            if (ControlPoint.lIIlllllll(lIIllllIlIIIIll) && ControlPoint.lIlIIIIIlI((int)lIIllllIlIIIIII.captured)) {
                lIIllllIlIIIIlI = lIIllllIlIIIIll.getChatColor();
            }
            return String.valueOf(new StringBuilder().append((Object)lIIllllIlIIIIlI).append(lIIllllIlIIIIII.name).append(lIIllllIlIIIIIl));
        }

        public void init() {
            ControlPoint lIIllllIlllIIIl;
            lIIllllIlllIIIl.createHolgram();
        }

        private static int lIlIIlllIl(float f, float f2) {
            return (int)(f FCMPL f2);
        }

        private static boolean lIlIIllIII(int n, int n2) {
            return n > n2;
        }

        private static boolean lIlIIlllII(int n, int n2) {
            return n != n2;
        }

        private float getPointReward() {
            ControlPoint lIIllllIllIlIII;
            if (ControlPoint.lIlIIIIIIl(lIIllllIllIlIII.getOwnerTeam())) {
                return lIIllllIllIlIII.basePointReward;
            }
            return (float)((double)lIIllllIllIlIII.basePointReward * lIIllllIllIlIII.JocTeamDominion.this.getBalancingMultiplier(lIIllllIllIlIII.getOwnerTeam()));
        }

        public void incrementTeamScore() {
            ControlPoint lIIllllIIIlIIll;
            if (ControlPoint.lIlIIllIll((Object)((Object)lIIllllIIIlIIll.JocTeamDominion.this.getGameGoal()), (Object)((Object)GameGoalType.ScoreRace)) && ControlPoint.lIlIIlllII(lIIllllIIIlIIll.ownerTeam, lIlIIlII[0]) && ControlPoint.lIlIIIIIlI((int)lIIllllIIIlIIll.captured)) {
                EquipDominion lIIllllIIIlIlIl = (EquipDominion)lIIllllIIIlIIll.getOwnerTeam();
                lIIllllIIIlIlIl.setHealth(Float.valueOf(lIIllllIIIlIlIl.getHealth().floatValue() + lIIllllIIIlIIll.getPointReward()));
                lIIllllIIIlIIll.playIncrementScoreEffect();
            }
        }

        private static String lIIIlIIIll(String lIIlllIlIlllIlI, String lIIlllIlIlllIll) {
            try {
                SecretKeySpec lIIlllIlIllllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlllIlIlllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIlllIlIlllllI = Cipher.getInstance("Blowfish");
                lIIlllIlIlllllI.init(lIlIIlII[3], lIIlllIlIllllll);
                return new String(lIIlllIlIlllllI.doFinal(Base64.getDecoder().decode(lIIlllIlIlllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlllIlIllllIl) {
                lIIlllIlIllllIl.printStackTrace();
                return null;
            }
        }

        public void setCaptureRateMultiplier(float lIIlllllIIIIIlI) {
            lIIlllllIIIIIll.captureRateMultiplier = lIIlllllIIIIIlI;
        }

        private static String lIIIIllIIl(String lIIlllIlIlIIlll, String lIIlllIlIlIIllI) {
            lIIlllIlIlIIlll = new String(Base64.getDecoder().decode(lIIlllIlIlIIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIlllIlIlIlIlI = new StringBuilder();
            char[] lIIlllIlIlIlIIl = lIIlllIlIlIIllI.toCharArray();
            int lIIlllIlIlIlIII = lIlIIlII[1];
            char[] lIIlllIlIlIIIlI = lIIlllIlIlIIlll.toCharArray();
            int lIIlllIlIlIIIIl = lIIlllIlIlIIIlI.length;
            int lIIlllIlIlIIIII = lIlIIlII[1];
            while (ControlPoint.lIlIIIIIll(lIIlllIlIlIIIII, lIIlllIlIlIIIIl)) {
                char lIIlllIlIlIllIl = lIIlllIlIlIIIlI[lIIlllIlIlIIIII];
                "".length();
                lIIlllIlIlIlIlI.append((char)(lIIlllIlIlIllIl ^ lIIlllIlIlIlIIl[lIIlllIlIlIlIII % lIIlllIlIlIlIIl.length]));
                ++lIIlllIlIlIlIII;
                ++lIIlllIlIlIIIII;
                "".length();
                if (" ".length() != ((50 ^ 57 ^ (119 ^ 62)) & (11 ^ 127 ^ (83 ^ 101) ^ -" ".length()))) continue;
                return null;
            }
            return String.valueOf(lIIlllIlIlIlIlI);
        }

        protected void onCapture(int lIIllllIIlIIlIl) {
            ControlPoint lIIllllIIlIIllI;
            lIIllllIIlIIllI.captured = lIlIIlII[2];
            lIIllllIIlIIllI.playCaptureEffect();
            lIIllllIIlIIllI.sendActionMessage(lIIlIIlI[lIlIIlII[13]]);
        }

        private static int lIlIIllllI(float f, float f2) {
            return (int)(f FCMPG f2);
        }

        private static boolean lIIlllllll(Object object) {
            return object != null;
        }

        private static boolean lIlIIIIIll(int n, int n2) {
            return n < n2;
        }

        private static void lIIllllllI() {
            lIlIIlII = new int[19];
            ControlPoint.lIlIIlII[0] = -" ".length();
            ControlPoint.lIlIIlII[1] = (0 ^ 77 ^ (43 ^ 16) & ~(42 ^ 17)) & (135 ^ 189 ^ (25 ^ 110) ^ -" ".length());
            ControlPoint.lIlIIlII[2] = " ".length();
            ControlPoint.lIlIIlII[3] = "  ".length();
            ControlPoint.lIlIIlII[4] = -(-1859 & 22503) & (-1089 & 32767);
            ControlPoint.lIlIIlII[5] = 138 ^ 128;
            ControlPoint.lIlIIlII[6] = "   ".length();
            ControlPoint.lIlIIlII[7] = -18506 & 28155;
            ControlPoint.lIlIIlII[8] = -(-14267 & 30718) & (-6657 & 32767);
            ControlPoint.lIlIIlII[9] = 116 ^ 89 ^ (181 ^ 156);
            ControlPoint.lIlIIlII[10] = 218 ^ 151 ^ (143 ^ 199);
            ControlPoint.lIlIIlII[11] = (65 ^ 1) & ~(114 ^ 50) ^ (21 ^ 19);
            ControlPoint.lIlIIlII[12] = 80 + 87 - 104 + 73 ^ 60 + 69 - 92 + 106;
            ControlPoint.lIlIIlII[13] = 223 ^ 168 ^ 106 + 3 - -14 + 4;
            ControlPoint.lIlIIlII[14] = 2 ^ 11;
            ControlPoint.lIlIIlII[15] = 50 ^ 57;
            ControlPoint.lIlIIlII[16] = 50 ^ 65 ^ 63 + 1 - -5 + 58;
            ControlPoint.lIlIIlII[17] = 111 ^ 98;
            ControlPoint.lIlIIlII[18] = 1 ^ 15;
        }

        protected abstract void renderEnvironment();

        private void updateLocalVariables(ArrayList<ArrayList<Player>> lIIlllIllllllIl) {
            ControlPoint lIIlllIlllllllI;
            lIIlllIlllllllI.dominatingTeamID = lIIlllIlllllllI.getDominatingTeamID(lIIlllIllllllIl);
            lIIlllIlllllllI.dominationPower = lIIlllIlllllllI.getDominationPower(lIIlllIllllllIl);
        }

        public ArrayList<ArrayList<Player>> getPlayerGroups() {
            ControlPoint lIIlllIllIIllll;
            ArrayList<Player> lIIlllIllIlIIIl = lIIlllIllIIllll.getStandingPlayers();
            ArrayList<ArrayList<Player>> lIIlllIllIlIIII = new ArrayList<ArrayList<Player>>();
            Iterator<E> lIIlllIllIIllII = lIIlllIllIIllll.JocTeamDominion.this.Equips.iterator();
            while (ControlPoint.lIlIIIIIlI((int)lIIlllIllIIllII.hasNext())) {
                JocEquips.Equip lIIlllIllIlIIll = (JocEquips.Equip)lIIlllIllIIllII.next();
                ArrayList lIIlllIllIlIlII = (ArrayList)lIIlllIllIlIIIl.clone();
                "".length();
                lIIlllIllIlIlII.retainAll(lIIlllIllIlIIll.getPlayers());
                "".length();
                lIIlllIllIlIIII.add(lIIlllIllIlIlII);
                "".length();
                if (" ".length() < (246 ^ 153 ^ (80 ^ 59))) continue;
                return null;
            }
            return lIIlllIllIlIIII;
        }

        protected void onPercentChange() {
            ControlPoint lIIllllIIIllllI;
            lIIllllIIIllllI.renderEnvironment();
        }

        private static void lIIIlllIII() {
            lIIlIIlI = new String[lIlIIlII[18]];
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[1]] = ControlPoint.lIIIIllIIl("Sg==", "oLRmq");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[2]] = ControlPoint.lIIIIllIlI("a/KUYmhGC7g=", "mrijZ");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[3]] = ControlPoint.lIIIIllIIl("SiUAHSAZegY=", "jUusT");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[6]] = ControlPoint.lIIIlIIIll("3pUbk0cj97A=", "jQlOr");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[9]] = ControlPoint.lIIIIllIIl("", "WFsuJ");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[10]] = ControlPoint.lIIIIllIIl("FA==", "OQCcK");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[11]] = ControlPoint.lIIIIllIIl("PA==", "adCTC");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[12]] = ControlPoint.lIIIIllIIl("", "QzBip");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[13]] = ControlPoint.lIIIIllIlI("rt2gTZyHlOt+Z/TpLPdrxw==", "fmxzQ");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[14]] = ControlPoint.lIIIlIIIll("QehgVDj5zKg0Yb9YdGcB9p/h8tSuyVMz", "ftTXP");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[5]] = ControlPoint.lIIIIllIlI("9bu+BOhUy1QCn0JzIT+56E2EFZogNRKa", "wPZWH");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[15]] = ControlPoint.lIIIlIIIll("91iSkyktXpeoM2fuDqPeTg==", "pDxmL");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[16]] = ControlPoint.lIIIIllIlI("sxJqOkAXw/8=", "cZFDT");
            ControlPoint.lIIlIIlI[ControlPoint.lIlIIlII[17]] = ControlPoint.lIIIlIIIll("f1XOOuFeZc0=", "gRmHB");
        }

        public abstract ArrayList<Player> getStandingPlayers();

        public boolean isCaptured() {
            ControlPoint lIIlllllIIlIlIl;
            return lIIlllllIIlIlIl.captured;
        }

        private static boolean lIlIIIIlII(int n, int n2) {
            return n == n2;
        }

        public void setPercent(int lIIlllllIIIlIll) {
            lIIlllllIIIlllI.percent = lIIlllllIIIlIll;
        }

        private static boolean lIlIlIIIll(int n) {
            return n <= 0;
        }

        public ControlPoint(String lIIlllllIIllIlI) {
            ControlPoint lIIlllllIIlllll;
            lIIlllllIIlllll.percent = 0.0f;
            lIIlllllIIlllll.captureRateMultiplier = 1.0f;
            lIIlllllIIlllll.ownerTeam = lIlIIlII[0];
            lIIlllllIIlllll.dominatingTeamID = lIlIIlII[0];
            lIIlllllIIlllll.dominationPower = lIlIIlII[1];
            lIIlllllIIlllll.captured = lIlIIlII[1];
            lIIlllllIIlllll.tendency = lIlIIlII[1];
            lIIlllllIIlllll.basePointReward = 3.0f;
            lIIlllllIIlllll.name = lIIlllllIIllIlI;
        }

        public String getProgressBar() {
            ControlPoint lIIllllIlIlIllI;
            Character lIIllllIlIllIll = Character.valueOf(lIlIIlII[4]);
            int lIIllllIlIllIlI = lIlIIlII[5];
            int lIIllllIlIllIIl = Math.round(lIIllllIlIlIllI.percent / (float)lIIllllIlIllIlI);
            JocEquips.Equip lIIllllIlIllIII = lIIllllIlIlIllI.getOwnerTeam();
            String lIIllllIlIlIlll = lIIlIIlI[lIlIIlII[6]];
            if (ControlPoint.lIIlllllll(lIIllllIlIllIII)) {
                ChatColor lIIllllIlIllllI = lIIllllIlIllIII.getChatColor();
                lIIllllIlIlIlll = String.valueOf(new StringBuilder().append(lIIllllIlIlIlll).append((Object)lIIllllIlIllllI));
            }
            int lIIllllIlIlllIl = lIlIIlII[1];
            while (ControlPoint.lIlIIIIIll(lIIllllIlIlllIl, lIIllllIlIllIlI)) {
                if (ControlPoint.lIlIIIIlII(lIIllllIlIlllIl, lIIllllIlIllIIl)) {
                    lIIllllIlIlIlll = String.valueOf(new StringBuilder().append(lIIllllIlIlIlll).append((Object)ChatColor.GRAY));
                }
                lIIllllIlIlIlll = String.valueOf(new StringBuilder().append(lIIllllIlIlIlll).append(lIIllllIlIllIll));
                ++lIIllllIlIlllIl;
                "".length();
                if (null == null) continue;
                return null;
            }
            return lIIllllIlIlIlll;
        }

        private static boolean lIlIlIIIII(int n) {
            return n >= 0;
        }

        public float getPercent() {
            ControlPoint lIIlllllIIlIIlI;
            return lIIlllllIIlIIlI.percent;
        }

        public ArrayList<ArrayList<Player>> getOrderedPlayerGroups() {
            ControlPoint lIIlllIllIlllII;
            ArrayList<ArrayList<Player>> lIIlllIllIlllIl = lIIlllIllIlllII.getPlayerGroups();
            lIIlllIllIlllIl.sort((lIIlllIllIIIlIl, lIIlllIllIIIlII) -> Integer.compare(lIIlllIllIIIlII.size(), lIIlllIllIIIlIl.size()));
            return lIIlllIllIlllIl;
        }

        public void processStandingPlayers() {
            ControlPoint lIIllllIIIIllII;
            ArrayList<ArrayList<Player>> lIIllllIIIIlIll = lIIllllIIIIllII.getOrderedPlayerGroups();
            lIIllllIIIIllII.updateLocalVariables(lIIllllIIIIlIll);
            double lIIllllIIIIlIlI = Math.sqrt(lIIllllIIIIllII.dominationPower) * (double)lIIllllIIIIllII.captureRateMultiplier;
            double lIIllllIIIIlIIl = 0.5 * (double)lIIllllIIIIllII.captureRateMultiplier;
            float lIIllllIIIIlIII = lIIllllIIIIllII.percent;
            if (ControlPoint.lIlIIIIIlI((int)lIIllllIIIIllII.captured) && (!ControlPoint.lIlIIlllII(lIIllllIIIIllII.dominatingTeamID, lIIllllIIIIllII.ownerTeam) || ControlPoint.lIlIIlllll(lIIllllIIIIllII.dominationPower))) {
                lIIllllIIIIlIII = (float)((double)lIIllllIIIIlIII + lIIllllIIIIlIIl);
            }
            if (ControlPoint.lIlIIlllll((int)lIIllllIIIIllII.captured) && ControlPoint.lIlIIlllll(lIIllllIIIIllII.dominationPower)) {
                lIIllllIIIIlIII = (float)((double)lIIllllIIIIlIII - lIIllllIIIIlIIl);
            }
            if (ControlPoint.lIlIIIIlII(lIIllllIIIIllII.ownerTeam, lIlIIlII[0])) {
                lIIllllIIIIllII.ownerTeam = lIIllllIIIIllII.dominatingTeamID;
            }
            if (ControlPoint.lIlIIlllII(lIIllllIIIIllII.dominatingTeamID, lIlIIlII[0])) {
                if (ControlPoint.lIlIIIIlII(lIIllllIIIIllII.dominatingTeamID, lIIllllIIIIllII.ownerTeam)) {
                    if (ControlPoint.lIlIlIIIII(ControlPoint.lIlIIlllIl(lIIllllIIIIlIII, 100.0f)) && ControlPoint.lIlIIlllll((int)lIIllllIIIIllII.captured)) {
                        lIIllllIIIIllII.onCapture(lIIllllIIIIllII.dominatingTeamID);
                    }
                    if (ControlPoint.lIlIlIIIIl(ControlPoint.lIlIIllllI(lIIllllIIIIlIII, 100.0f))) {
                        lIIllllIIIIlIII = (float)((double)lIIllllIIIIlIII + lIIllllIIIIlIlI);
                        lIIllllIIIIllII.tendency = lIlIIlII[3];
                        "".length();
                        if (((214 ^ 139) & ~(123 ^ 38)) != 0) {
                            return;
                        }
                    }
                } else {
                    if (ControlPoint.lIlIlIIIlI(ControlPoint.lIlIIlllIl(lIIllllIIIIlIII, 0.0f))) {
                        lIIllllIIIIlIII = (float)((double)lIIllllIIIIlIII - lIIllllIIIIlIlI);
                        lIIllllIIIIllII.tendency = lIlIIlII[2];
                    }
                    if (ControlPoint.lIlIlIIIll(ControlPoint.lIlIIllllI(lIIllllIIIIllII.percent, 0.0f))) {
                        lIIllllIIIIllII.ownerTeam = lIIllllIIIIllII.dominatingTeamID;
                    }
                    if (ControlPoint.lIlIIlllll(ControlPoint.lIlIIlllIl(lIIllllIIIIlIII, 0.0f)) && ControlPoint.lIlIIIIIlI((int)lIIllllIIIIllII.captured)) {
                        lIIllllIIIIllII.onNeutralize();
                        "".length();
                        if (((37 ^ 101 ^ (127 ^ 24)) & (155 + 53 - 94 + 55 ^ 20 + 29 - 28 + 121 ^ -" ".length())) != 0) {
                            return;
                        }
                    }
                }
            } else {
                lIIllllIIIIllII.tendency = lIlIIlII[1];
            }
            if (ControlPoint.lIlIlIIIlI(ControlPoint.lIlIIlllIl(lIIllllIIIIlIII, 100.0f))) {
                lIIllllIIIIlIII = 100.0f;
            }
            if (ControlPoint.lIlIlIIIIl(ControlPoint.lIlIIllllI(lIIllllIIIIlIII, 0.0f))) {
                lIIllllIIIIlIII = 0.0f;
            }
            if (ControlPoint.lIlIIIIIlI(ControlPoint.lIlIIlllIl(lIIllllIIIIllII.percent, lIIllllIIIIlIII))) {
                lIIllllIIIIllII.percent = lIIllllIIIIlIII;
                lIIllllIIIIllII.onPercentChange();
            }
        }

        protected String getCapturingNameModifier() {
            ControlPoint lIIllllIIlIlllI;
            if (ControlPoint.lIlIIIIIlI(lIIllllIIlIlllI.tendency) && ControlPoint.lIlIIIIIlI(ControlPoint.lIlIIIIlIl(lIIllllIIlIlllI.percent, 100.0f))) {
                char c;
                int n;
                JocEquips.Equip lIIllllIIllIlIl = lIIllllIIlIlllI.getOwnerTeam();
                EquipDominion lIIllllIIllIlII = (EquipDominion)lIIllllIIlIlllI.getDominatingTeam();
                int lIIllllIIllIIll = lIlIIlII[2];
                if (ControlPoint.lIlIIIIlII(lIIllllIIlIlllI.tendency, lIlIIlII[3])) {
                    n = lIlIIlII[2];
                    "".length();
                    if (-" ".length() >= (9 ^ 13)) {
                        return null;
                    }
                } else {
                    n = lIlIIlII[1];
                }
                if (ControlPoint.lIlIIIIIlI(lIIllllIIllIIll = n)) {
                    c = lIlIIlII[7];
                    "".length();
                    if ("   ".length() <= ((218 ^ 146) & ~(60 ^ 116))) {
                        return null;
                    }
                } else {
                    c = lIlIIlII[8];
                }
                Character lIIllllIIllIIlI = Character.valueOf(c);
                ChatColor lIIllllIIllIIIl = lIIllllIIllIlII.getChatColor();
                String lIIllllIIllIIII = lIIlIIlI[lIlIIlII[9]];
                if (ControlPoint.lIlIIllIII(lIIllllIIlIlllI.dominationPower, lIlIIlII[2])) {
                    lIIllllIIllIIII = Integer.toString(lIIllllIIlIlllI.dominationPower);
                }
                return String.valueOf(new StringBuilder().append((Object)lIIllllIIllIIIl).append(lIIlIIlI[lIlIIlII[10]]).append(lIIllllIIllIIII).append(lIIllllIIllIIlI).append(lIIlIIlI[lIlIIlII[11]]));
            }
            return lIIlIIlI[lIlIIlII[12]];
        }

        public abstract void playNeutralizeEffect();

        public float getCaptureRateMultiplier() {
            ControlPoint lIIlllllIIIlIIl;
            return lIIlllllIIIlIIl.captureRateMultiplier;
        }

        private static boolean lIlIIIIIlI(int n) {
            return n != 0;
        }

        private static boolean lIlIlIIIIl(int n) {
            return n < 0;
        }

        private static int lIlIIIIIII(float f, float f2) {
            return (int)(f FCMPL f2);
        }

        private static boolean lIlIIlllll(int n) {
            return n == 0;
        }

        public JocEquips.Equip getDominatingTeam() {
            ControlPoint lIIllllIlIIlIIl;
            if (ControlPoint.lIlIIIIlII(lIIllllIlIIlIIl.dominatingTeamID, lIlIIlII[0])) {
                return null;
            }
            return lIIllllIlIIlIIl.JocTeamDominion.this.obtenirEquip(lIIllllIlIIlIIl.dominatingTeamID);
        }

        private static boolean lIlIlIIIlI(int n) {
            return n > 0;
        }
    }

    public abstract class ControlPointRenderer {
        public ControlPointRenderer() {
            ControlPointRenderer llIIlIIIlIIllll;
        }

        public abstract void renderEnvironment(PhysicalControlPoint var1);
    }

}

