/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Score
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.ScoreboardManager
 *  org.bukkit.scoreboard.Team
 */
package com.biel.lobby.utilities;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ColorConverter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreBoardUpdater {
    private static final /* synthetic */ int[] lIIIIIlIl;
    private static final /* synthetic */ String[] lIIIIIIII;

    private static boolean llllIIllIl(Object object) {
        return object != null;
    }

    private static boolean llllIIllII(int n) {
        return n != 0;
    }

    private static void lllIlllllI() {
        lIIIIIIII = new String[lIIIIIlIl[3]];
        ScoreBoardUpdater.lIIIIIIII[ScoreBoardUpdater.lIIIIIlIl[1]] = ScoreBoardUpdater.lllIlllIlI("kQRFoTZJuMM=", "fNgYm");
        ScoreBoardUpdater.lIIIIIIII[ScoreBoardUpdater.lIIIIIlIl[0]] = ScoreBoardUpdater.lllIllllIl("HzYs", "QYAug");
        ScoreBoardUpdater.lIIIIIIII[ScoreBoardUpdater.lIIIIIlIl[2]] = ScoreBoardUpdater.lllIlllIlI("U8J7/2+FxDU=", "cGwRy");
    }

    @Deprecated
    public static void updateSpectatorScore(ArrayList<Player> lllllIIIllllII) {
        ScoreboardManager lllllIIIlllIll = Bukkit.getScoreboardManager();
        Scoreboard lllllIIIlllIlI = lllllIIIlllIll.getNewScoreboard();
        Team lllllIIIlllIIl = lllllIIIlllIlI.registerNewTeam(lIIIIIIII[lIIIIIlIl[1]]);
        lllllIIIlllIIl.setCanSeeFriendlyInvisibles(lIIIIIlIl[0]);
        lllllIIIlllIIl.setAllowFriendlyFire(lIIIIIlIl[1]);
        Iterator<Player> lllllIIIllIlII = lllllIIIllllII.iterator();
        while (ScoreBoardUpdater.llllIIllII((int)lllllIIIllIlII.hasNext())) {
            Player lllllIIIllllIl = lllllIIIllIlII.next();
            lllllIIIllllIl.setScoreboard(lllllIIIlllIlI);
            "".length();
            if ("   ".length() >= 0) continue;
            return;
        }
    }

    private static boolean llllIIlllI(Object object) {
        return object == null;
    }

    private static boolean llllIIllll(int n, int n2) {
        return n < n2;
    }

    private static String lllIllllIl(String llllIllllIIIll, String llllIllllIIIlI) {
        llllIllllIIIll = new String(Base64.getDecoder().decode(llllIllllIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llllIllllIIIIl = new StringBuilder();
        char[] llllIllllIIIII = llllIllllIIIlI.toCharArray();
        int llllIlllIlllll = lIIIIIlIl[1];
        char[] llllIlllIllIIl = llllIllllIIIll.toCharArray();
        int llllIlllIllIII = llllIlllIllIIl.length;
        int llllIlllIlIlll = lIIIIIlIl[1];
        while (ScoreBoardUpdater.llllIIllll(llllIlllIlIlll, llllIlllIllIII)) {
            char llllIllllIIlII = llllIlllIllIIl[llllIlllIlIlll];
            "".length();
            llllIllllIIIIl.append((char)(llllIllllIIlII ^ llllIllllIIIII[llllIlllIlllll % llllIllllIIIII.length]));
            ++llllIlllIlllll;
            ++llllIlllIlIlll;
            "".length();
            if ("   ".length() != -" ".length()) continue;
            return null;
        }
        return String.valueOf(llllIllllIIIIl);
    }

    private static void llllIIlIll() {
        lIIIIIlIl = new int[5];
        ScoreBoardUpdater.lIIIIIlIl[0] = " ".length();
        ScoreBoardUpdater.lIIIIIlIl[1] = (127 ^ 97) & ~(30 ^ 0);
        ScoreBoardUpdater.lIIIIIlIl[2] = "  ".length();
        ScoreBoardUpdater.lIIIIIlIl[3] = "   ".length();
        ScoreBoardUpdater.lIIIIIlIl[4] = 51 ^ 59;
    }

    public static void clearScoreBoard(Player llllIlllllIIIl) {
        ScoreboardManager llllIlllllIIII = Bukkit.getScoreboardManager();
        llllIlllllIIIl.setScoreboard(llllIlllllIIII.getNewScoreboard());
    }

    private static String lllIlllIlI(String llllIlllIIllII, String llllIlllIIlIll) {
        try {
            SecretKeySpec llllIlllIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlllIIlIll.getBytes(StandardCharsets.UTF_8)), lIIIIIlIl[4]), "DES");
            Cipher llllIlllIlIIII = Cipher.getInstance("DES");
            llllIlllIlIIII.init(lIIIIIlIl[2], llllIlllIlIIIl);
            return new String(llllIlllIlIIII.doFinal(Base64.getDecoder().decode(llllIlllIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIlllIIllll) {
            llllIlllIIllll.printStackTrace();
            return null;
        }
    }

    public static void setScoreBoard(List<Player> lllllIIIIlllll, String lllllIIIIllllI, ArrayList<String> lllllIIIIllIIl, ArrayList<Integer> lllllIIIIllIII) {
        Iterator<Player> lllllIIIIlIlll = lllllIIIIlllll.iterator();
        while (ScoreBoardUpdater.llllIIllII((int)lllllIIIIlIlll.hasNext())) {
            Player lllllIIIlIIIII = lllllIIIIlIlll.next();
            ScoreBoardUpdater.setScoreBoard(lllllIIIlIIIII, lllllIIIIllllI, lllllIIIIllIIl, lllllIIIIllIII);
            "".length();
            if (" ".length() >= 0) continue;
            return;
        }
    }

    static {
        ScoreBoardUpdater.llllIIlIll();
        ScoreBoardUpdater.lllIlllllI();
    }

    public static void updateTeamScore(JocEquips lllllIIlIIlIll) {
        ScoreboardManager lllllIIlIIllIl = Bukkit.getScoreboardManager();
        Scoreboard lllllIIlIIllII = lllllIIlIIllIl.getNewScoreboard();
        Iterator<JocEquips.Equip> lllllIIlIIlIII = lllllIIlIIlIll.Equips.iterator();
        while (ScoreBoardUpdater.llllIIllII((int)lllllIIlIIlIII.hasNext())) {
            JocEquips.Equip lllllIIlIIllll = lllllIIlIIlIII.next();
            Team lllllIIlIlIIII = lllllIIlIIllII.registerNewTeam(lllllIIlIIllll.getAdjectiu());
            Iterator<Player> lllllIIlIIIlIl = lllllIIlIIllll.getPlayers().iterator();
            while (ScoreBoardUpdater.llllIIllII((int)lllllIIlIIIlIl.hasNext())) {
                Player lllllIIlIlIIIl = lllllIIlIIIlIl.next();
                lllllIIlIlIIIl.setScoreboard(lllllIIlIIllII);
                lllllIIlIlIIII.addPlayer((OfflinePlayer)lllllIIlIlIIIl);
                Com.setHeadColor(lllllIIlIlIIIl, ColorConverter.chatToRaw(lllllIIlIIllll.getChatColor()));
                "".length();
                if (null == null) continue;
                return;
            }
            lllllIIlIlIIII.setCanSeeFriendlyInvisibles(lIIIIIlIl[0]);
            lllllIIlIlIIII.setAllowFriendlyFire(lIIIIIlIl[1]);
            "".length();
            if (((51 ^ 55) & ~(84 ^ 80)) == 0) continue;
            return;
        }
    }

    public static void setScore(Objective lllllIIIlIlllI, String lllllIIIlIlIIl, int lllllIIIlIlIII) {
        Score lllllIIIlIlIll = lllllIIIlIlllI.getScore(Bukkit.getOfflinePlayer((String)lllllIIIlIlIIl));
        lllllIIIlIlIll.setScore(lllllIIIlIlIII);
    }

    public ScoreBoardUpdater() {
        ScoreBoardUpdater lllllIIlIllIlI;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void setScoreBoard(Player lllllIIIIIIlIl, String lllllIIIIIIlII, ArrayList<String> lllllIIIIIIIll, ArrayList<Integer> lllllIIIIIIIlI) {
        ScoreboardManager lllllIIIIIIIIl = Bukkit.getScoreboardManager();
        Scoreboard lllllIIIIIIIII = lllllIIIIIIIIl.getNewScoreboard();
        Objective llllIlllllllll = lllllIIIIIIIII.registerNewObjective(lIIIIIIII[lIIIIIlIl[0]], lIIIIIIII[lIIIIIlIl[2]]);
        llllIlllllllll.setDisplaySlot(DisplaySlot.SIDEBAR);
        llllIlllllllll.setDisplayName(lllllIIIIIIlII);
        Collections.reverse(lllllIIIIIIIll);
        if (ScoreBoardUpdater.llllIIllIl(lllllIIIIIIIlI)) {
            Collections.reverse(lllllIIIIIIIlI);
        }
        Iterator<String> llllIlllllIlll = lllllIIIIIIIll.iterator();
        do {
            String lllllIIIIIIllI;
            int lllllIIIIIIlll;
            block9 : {
                if (!ScoreBoardUpdater.llllIIllII((int)llllIlllllIlll.hasNext())) {
                    lllllIIIIIIlIl.setScoreboard(lllllIIIIIIIII);
                    return;
                }
                lllllIIIIIIllI = llllIlllllIlll.next();
                if (ScoreBoardUpdater.llllIIlllI(lllllIIIIIIIlI)) {
                    int lllllIIIIIlIlI = lllllIIIIIIIll.indexOf(lllllIIIIIIllI);
                    "".length();
                    if ("  ".length() <= " ".length()) {
                        return;
                    }
                } else {
                    int lllllIIIIIlIIl = lllllIIIIIIIlI.get(lllllIIIIIIIll.indexOf(lllllIIIIIIllI));
                    "".length();
                    if ("   ".length() > (95 ^ 91)) {
                        return;
                    }
                }
                break block9;
                catch (IndexOutOfBoundsException lllllIIIIIlIII) {
                    lllllIIIIIIlll = lIIIIIlIl[1];
                }
            }
            ScoreBoardUpdater.setScore(llllIlllllllll, lllllIIIIIIllI, lllllIIIIIIlll);
            "".length();
        } while (null == null);
    }
}

