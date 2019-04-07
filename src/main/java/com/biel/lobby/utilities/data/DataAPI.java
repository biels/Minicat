/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.Pair
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.data;

import com.biel.BielAPI.Utils.Pair;
import com.biel.lobby.utilities.data.DatalessMatchData;
import com.biel.lobby.utilities.data.MatchData;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.entity.Player;

public class DataAPI {
    private static final /* synthetic */ int[] llIlll;
    private static final /* synthetic */ String[] lIIIII;
    /* synthetic */ Logger logger;
    /* synthetic */ boolean datalessMode;
    public /* synthetic */ Connection connection;

    private static boolean llIIIIIl(Object object) {
        return object != null;
    }

    public double getAvgElo() {
        DataAPI lllIlIIlIlIIIll;
        if (DataAPI.lIlllIII((int)lllIlIIlIlIIIll.datalessMode)) {
            return 1200.0;
        }
        try {
            PreparedStatement lllIlIIlIlIlIII = lllIlIIlIlIIIll.connection.prepareStatement(lIIIII[llIlll[33]]);
            ResultSet lllIlIIlIlIIlll = lllIlIIlIlIlIII.executeQuery();
            "".length();
            lllIlIIlIlIIlll.next();
            double lllIlIIlIlIIllI = lllIlIIlIlIIlll.getDouble(llIlll[1]);
            lllIlIIlIlIlIII.close();
            lllIlIIlIlIIlll.close();
            return lllIlIIlIlIIllI;
        }
        catch (SQLException lllIlIIlIlIIlIl) {
            lllIlIIlIlIIlIl.printStackTrace();
            return 0.0;
        }
    }

    public String getPlayerName(int lllIlIIllllllII) {
        try {
            DataAPI lllIlIIlllllIll;
            if (DataAPI.lIlllIII((int)lllIlIIlllllIll.datalessMode)) {
                return String.valueOf(new StringBuilder().append(lIIIII[llIlll[16]]).append(lllIlIIllllllII));
            }
            PreparedStatement lllIlIlIIIIIIIl = lllIlIIlllllIll.connection.prepareStatement(lIIIII[llIlll[17]]);
            "".length();
            lllIlIIlllllIll.connection.isValid(llIlll[18]);
            lllIlIlIIIIIIIl.setInt(llIlll[1], lllIlIIllllllII);
            ResultSet lllIlIlIIIIIIII = lllIlIlIIIIIIIl.executeQuery();
            "".length();
            lllIlIlIIIIIIII.next();
            String lllIlIIllllllll = lllIlIlIIIIIIII.getString(lIIIII[llIlll[19]]);
            lllIlIlIIIIIIIl.close();
            lllIlIlIIIIIIII.close();
            return lllIlIIllllllll;
        }
        catch (SQLException lllIlIIlllllllI) {
            lllIlIIlllllllI.printStackTrace();
            return lIIIII[llIlll[20]];
        }
    }

    public double getAvgGameLength(int lllIlIIIIIIllII) {
        DataAPI lllIlIIIIIIlIll;
        if (DataAPI.lIlllIII((int)lllIlIIIIIIlIll.datalessMode)) {
            return 1800.0;
        }
        try {
            PreparedStatement lllIlIIIIIlIIIl = lllIlIIIIIIlIll.connection.prepareStatement(lIIIII[llIlll[51]]);
            lllIlIIIIIlIIIl.setInt(llIlll[1], lllIlIIIIIIllII);
            ResultSet lllIlIIIIIlIIII = lllIlIIIIIlIIIl.executeQuery();
            "".length();
            lllIlIIIIIlIIII.next();
            double lllIlIIIIIIllll = lllIlIIIIIlIIII.getDouble(llIlll[1]);
            lllIlIIIIIlIIIl.close();
            lllIlIIIIIlIIII.close();
            return lllIlIIIIIIllll;
        }
        catch (SQLException lllIlIIIIIIlllI) {
            lllIlIIIIIIlllI.printStackTrace();
            return 0.0;
        }
    }

    private static String lIllIIl(String lllIIlllIlIllll, String lllIIlllIlIlllI) {
        try {
            SecretKeySpec lllIIlllIllIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIlllIlIlllI.getBytes(StandardCharsets.UTF_8)), llIlll[8]), "DES");
            Cipher lllIIlllIllIIIl = Cipher.getInstance("DES");
            lllIIlllIllIIIl.init(llIlll[2], lllIIlllIllIIlI);
            return new String(lllIIlllIllIIIl.doFinal(Base64.getDecoder().decode(lllIIlllIlIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlllIllIIII) {
            lllIIlllIllIIII.printStackTrace();
            return null;
        }
    }

    private static void lIllIlIl() {
        llIlll = new int[54];
        DataAPI.llIlll[0] = (233 ^ 189) & ~(232 ^ 188);
        DataAPI.llIlll[1] = " ".length();
        DataAPI.llIlll[2] = "  ".length();
        DataAPI.llIlll[3] = "   ".length();
        DataAPI.llIlll[4] = 23 ^ 19;
        DataAPI.llIlll[5] = 151 + 50 - 74 + 28 ^ 138 + 9 - 109 + 120;
        DataAPI.llIlll[6] = 101 + 64 - 34 + 30 ^ 75 + 51 - -33 + 8;
        DataAPI.llIlll[7] = 72 ^ 32 ^ (199 ^ 168);
        DataAPI.llIlll[8] = 42 ^ 34;
        DataAPI.llIlll[9] = "  ".length() ^ (127 ^ 116);
        DataAPI.llIlll[10] = 108 + 105 - 196 + 149 ^ 123 + 144 - 113 + 18;
        DataAPI.llIlll[11] = 0 ^ 105 ^ (194 ^ 160);
        DataAPI.llIlll[12] = 90 ^ 86;
        DataAPI.llIlll[13] = -" ".length();
        DataAPI.llIlll[14] = 47 ^ 25 ^ (190 ^ 133);
        DataAPI.llIlll[15] = 55 ^ 57;
        DataAPI.llIlll[16] = 166 ^ 169;
        DataAPI.llIlll[17] = 32 ^ 48;
        DataAPI.llIlll[18] = -22561 & 24560;
        DataAPI.llIlll[19] = 175 + 149 - 175 + 61 ^ 87 + 123 - 94 + 79;
        DataAPI.llIlll[20] = 62 ^ 44;
        DataAPI.llIlll[21] = 184 ^ 171;
        DataAPI.llIlll[22] = 71 + 122 - 54 + 70 ^ 81 + 22 - 68 + 162;
        DataAPI.llIlll[23] = 81 + 82 - 65 + 37 ^ 120 + 83 - 87 + 30;
        DataAPI.llIlll[24] = 90 ^ 86 ^ (190 ^ 164);
        DataAPI.llIlll[25] = 153 ^ 181 ^ (6 ^ 61);
        DataAPI.llIlll[26] = 131 ^ 155;
        DataAPI.llIlll[27] = 97 ^ 43 ^ (94 ^ 13);
        DataAPI.llIlll[28] = 14 ^ 20;
        DataAPI.llIlll[29] = 65 ^ 90;
        DataAPI.llIlll[30] = 169 ^ 181;
        DataAPI.llIlll[31] = "   ".length() ^ (121 ^ 103);
        DataAPI.llIlll[32] = 55 ^ 41;
        DataAPI.llIlll[33] = 33 + 96 - 1 + 33 ^ 123 + 152 - 208 + 123;
        DataAPI.llIlll[34] = 94 ^ 126;
        DataAPI.llIlll[35] = 7 ^ 38;
        DataAPI.llIlll[36] = 3 ^ 33;
        DataAPI.llIlll[37] = 190 ^ 157;
        DataAPI.llIlll[38] = 13 + 39 - -49 + 36 ^ 93 + 14 - -3 + 63;
        DataAPI.llIlll[39] = 164 ^ 183 ^ (27 ^ 45);
        DataAPI.llIlll[40] = 5 ^ 80 ^ (240 ^ 131);
        DataAPI.llIlll[41] = 6 ^ 124 ^ (109 ^ 48);
        DataAPI.llIlll[42] = 125 ^ 85;
        DataAPI.llIlll[43] = 242 ^ 174 ^ (254 ^ 139);
        DataAPI.llIlll[44] = 208 ^ 181 ^ (124 ^ 51);
        DataAPI.llIlll[45] = 190 ^ 149;
        DataAPI.llIlll[46] = 87 + 16 - 8 + 35 ^ 126 + 172 - 295 + 171;
        DataAPI.llIlll[47] = 94 ^ 115;
        DataAPI.llIlll[48] = 40 ^ 72 ^ (81 ^ 31);
        DataAPI.llIlll[49] = 75 ^ 100;
        DataAPI.llIlll[50] = 6 ^ 54;
        DataAPI.llIlll[51] = 72 + 144 - 165 + 124 ^ 106 + 128 - 150 + 74;
        DataAPI.llIlll[52] = 142 ^ 188;
        DataAPI.llIlll[53] = 38 + 129 - 161 + 159 ^ 40 + 51 - 11 + 70;
    }

    public void setMoney(int lllIlIIllIlllll, double lllIlIIllIllllI) {
        DataAPI lllIlIIlllIIIII;
        if (DataAPI.lIlllIII((int)lllIlIIlllIIIII.datalessMode)) {
            lllIlIIlllIIIII.logger.info(String.valueOf(new StringBuilder().append(lIIIII[llIlll[23]]).append(lllIlIIllIlllll).append(lIIIII[llIlll[24]]).append(lllIlIIllIllllI)));
            return;
        }
        try {
            PreparedStatement lllIlIIlllIIIlI = lllIlIIlllIIIII.connection.prepareStatement(lIIIII[llIlll[25]]);
            lllIlIIlllIIIlI.setDouble(llIlll[1], lllIlIIllIllllI);
            lllIlIIlllIIIlI.setInt(llIlll[2], lllIlIIllIlllll);
            "".length();
            lllIlIIlllIIIlI.executeUpdate();
            lllIlIIlllIIIlI.close();
            "".length();
        }
        catch (Exception lllIlIIlllIIIIl) {
            lllIlIIlllIIIIl.printStackTrace();
        }
        if (("   ".length() & ("   ".length() ^ -" ".length())) < 0) {
            return;
        }
    }

    public void setElo(int lllIlIIlIIlIlIl, double lllIlIIlIIlIlll) {
        DataAPI lllIlIIlIIllIIl;
        if (DataAPI.lIlllIII((int)lllIlIIlIIllIIl.datalessMode)) {
            lllIlIIlIIllIIl.logger.info(String.valueOf(new StringBuilder().append(lIIIII[llIlll[34]]).append(lllIlIIlIIlIlIl).append(lIIIII[llIlll[35]]).append(lllIlIIlIIlIlll)));
            return;
        }
        try {
            PreparedStatement lllIlIIlIIllIll = lllIlIIlIIllIIl.connection.prepareStatement(lIIIII[llIlll[36]]);
            lllIlIIlIIllIll.setDouble(llIlll[1], lllIlIIlIIlIlll);
            lllIlIIlIIllIll.setInt(llIlll[2], lllIlIIlIIlIlIl);
            "".length();
            lllIlIIlIIllIll.executeUpdate();
            lllIlIIlIIllIll.close();
            "".length();
        }
        catch (Exception lllIlIIlIIllIlI) {
            lllIlIIlIIllIlI.printStackTrace();
        }
        if ("  ".length() == -" ".length()) {
            return;
        }
    }

    public void repairConnection() {
        DataAPI lllIlIlIlIlllII;
        if (DataAPI.lIllIllI(lllIlIlIlIlllII.connection) && DataAPI.lIllIlll((int)lllIlIlIlIlllII.datalessMode)) {
            lllIlIlIlIlllII.openConnection();
        }
    }

    public int getPlayerId(String lllIlIlIIIlIlIl) {
        DataAPI lllIlIlIIIlIllI;
        if (DataAPI.lIlllIII((int)lllIlIlIIIlIllI.datalessMode)) {
            lllIlIlIIIlIllI.logger.warning(lIIIII[llIlll[12]]);
            return llIlll[13];
        }
        try {
            PreparedStatement lllIlIlIIIllIIl = lllIlIlIIIlIllI.connection.prepareStatement(lIIIII[llIlll[14]]);
            lllIlIlIIIllIIl.setString(llIlll[1], lllIlIlIIIlIlIl);
            ResultSet lllIlIlIIIllIII = lllIlIlIIIllIIl.executeQuery();
            "".length();
            lllIlIlIIIllIII.next();
            int lllIlIlIIIlIlll = lllIlIlIIIllIII.getInt(lIIIII[llIlll[15]]);
            lllIlIlIIIllIIl.close();
            lllIlIlIIIllIII.close();
            return lllIlIlIIIlIlll;
        }
        catch (SQLException lllIlIlIIIllIIl) {
            return llIlll[0];
        }
    }

    public ArrayList<Integer> getRanking() {
        DataAPI lllIlIIlIIIlIIl;
        ArrayList<Integer> lllIlIIlIIIlIlI = new ArrayList<Integer>();
        if (DataAPI.lIlllIII((int)lllIlIIlIIIlIIl.datalessMode)) {
            lllIlIIlIIIlIIl.logger.warning(lIIIII[llIlll[37]]);
            return lllIlIIlIIIlIlI;
        }
        try {
            PreparedStatement lllIlIIlIIIlllI = lllIlIIlIIIlIIl.connection.prepareStatement(lIIIII[llIlll[38]]);
            ResultSet lllIlIIlIIIllIl = lllIlIIlIIIlllI.executeQuery();
            while (DataAPI.lIlllIII((int)lllIlIIlIIIllIl.next())) {
                "".length();
                lllIlIIlIIIlIlI.add(lllIlIIlIIIllIl.getInt(lIIIII[llIlll[39]]));
                "".length();
                if (((46 ^ 19) & ~(248 ^ 197)) == 0) continue;
                return null;
            }
            lllIlIIlIIIlllI.close();
            lllIlIIlIIIllIl.close();
            "".length();
            if ("  ".length() != "  ".length()) {
                return null;
            }
        }
        catch (SQLException lllIlIIlIIIllII) {
            lllIlIIlIIIllII.printStackTrace();
        }
        return lllIlIIlIIIlIlI;
    }

    public void registerNewPlayer(Player lllIlIlIIllIIll) {
        DataAPI lllIlIlIIllIlII;
        lllIlIlIIllIlII.repairConnection();
        if (DataAPI.lIlllIII((int)lllIlIlIIllIlII.datalessMode)) {
            return;
        }
        double lllIlIlIIllIlIl = lllIlIlIIllIlII.getAvgElo();
        if (DataAPI.lIlllIIl(lllIlIlIIllIlII.getPlayerId(lllIlIlIIllIIll.getName()))) {
            return;
        }
        try {
            PreparedStatement lllIlIlIIlllIIl = lllIlIlIIllIlII.connection.prepareStatement(lIIIII[llIlll[11]]);
            lllIlIlIIlllIIl.setString(llIlll[1], lllIlIlIIllIIll.getName());
            lllIlIlIIlllIIl.setDouble(llIlll[2], lllIlIlIIllIlIl);
            "".length();
            lllIlIlIIlllIIl.executeUpdate();
            lllIlIlIIlllIIl.close();
            "".length();
            if (null != null) {
                return;
            }
        }
        catch (SQLException lllIlIlIIlllIII) {
            lllIlIlIIlllIII.printStackTrace();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MatchData registerMatchStart(int lllIlIIIIllIIIl, int lllIlIIIIllIIII, String lllIlIIIIlIlIll) {
        DataAPI lllIlIIIIlIlllI;
        if (DataAPI.lIlllIII((int)lllIlIIIIlIlllI.datalessMode)) {
            return new DatalessMatchData();
        }
        lllIlIIIIlIlllI.repairConnection();
        try {
            PreparedStatement lllIlIIIIllIlII;
            block19 : {
                ResultSet lllIlIIIIllIlIl;
                Throwable lllIlIIIIlIlIII;
                block20 : {
                    block18 : {
                        lllIlIIIIllIlII = lllIlIIIIlIlllI.connection.prepareStatement(lIIIII[llIlll[49]], llIlll[1]);
                        lllIlIIIIllIlII.setInt(llIlll[1], lllIlIIIIllIIIl);
                        lllIlIIIIllIlII.setInt(llIlll[2], lllIlIIIIllIIII);
                        lllIlIIIIllIlII.setString(llIlll[3], lllIlIIIIlIlIll);
                        "".length();
                        lllIlIIIIllIlII.executeUpdate();
                        lllIlIIIIllIlIl = lllIlIIIIllIlII.getGeneratedKeys();
                        lllIlIIIIlIlIII = null;
                        if (!DataAPI.lIlllIII((int)lllIlIIIIllIlIl.next())) break block18;
                        MatchData lllIlIIIIlIIlll = new MatchData(lllIlIIIIllIlIl.getInt(llIlll[1]));
                        if (!DataAPI.llIIIIIl(lllIlIIIIllIlIl)) return lllIlIIIIlIIlll;
                        if (!DataAPI.llIIIIIl(lllIlIIIIlIlIII)) {
                            lllIlIIIIllIlIl.close();
                            return lllIlIIIIlIIlll;
                        }
                        try {
                            lllIlIIIIllIlIl.close();
                            "".length();
                            if ("   ".length() != 0) return lllIlIIIIlIIlll;
                            return null;
                        }
                        catch (Throwable lllIlIIIIlIIllI) {
                            lllIlIIIIlIlIII.addSuppressed(lllIlIIIIlIIllI);
                            "".length();
                            if ((105 ^ 109) >= 0) return lllIlIIIIlIIlll;
                            return null;
                        }
                    }
                    if (!DataAPI.llIIIIIl(lllIlIIIIllIlIl)) break block19;
                    if (!DataAPI.llIIIIIl(lllIlIIIIlIlIII)) break block20;
                    try {
                        lllIlIIIIllIlIl.close();
                        "".length();
                    }
                    catch (Throwable lllIlIIIIlIIlll) {
                        lllIlIIIIlIlIII.addSuppressed(lllIlIIIIlIIlll);
                        "".length();
                        if (-" ".length() >= "   ".length()) {
                            return null;
                        }
                        break block19;
                    }
                    if (-(63 ^ 42 ^ (7 ^ 22)) >= 0) {
                        return null;
                    }
                    break block19;
                }
                lllIlIIIIllIlIl.close();
                "".length();
                if (((163 ^ 195 ^ (17 ^ 61)) & (18 ^ 96 ^ (125 ^ 67) ^ -" ".length())) < -" ".length()) {
                    return null;
                }
                break block19;
                catch (Throwable lllIlIIIIlIIlll) {
                    try {
                        lllIlIIIIlIlIII = lllIlIIIIlIIlll;
                        throw lllIlIIIIlIIlll;
                    }
                    catch (Throwable lllIlIIIIlIIlIl) {
                        if (!DataAPI.llIIIIIl(lllIlIIIIllIlIl)) throw lllIlIIIIlIIlIl;
                        if (!DataAPI.llIIIIIl(lllIlIIIIlIlIII)) {
                            lllIlIIIIllIlIl.close();
                            throw lllIlIIIIlIIlIl;
                        }
                        try {
                            lllIlIIIIllIlIl.close();
                            "".length();
                        }
                        catch (Throwable lllIlIIIIlIIlII) {
                            lllIlIIIIlIlIII.addSuppressed(lllIlIIIIlIIlII);
                            "".length();
                            if ("   ".length() != 0) throw lllIlIIIIlIIlIl;
                            return null;
                        }
                        if (((68 ^ 29) & ~(247 ^ 174)) == ((160 ^ 134) & ~(141 ^ 171))) throw lllIlIIIIlIIlIl;
                        return null;
                    }
                }
            }
            lllIlIIIIllIlII.close();
            "".length();
            if (" ".length() < (56 ^ 39 ^ (48 ^ 43))) return null;
            return null;
        }
        catch (SQLException lllIlIIIIllIIll) {
            lllIlIIIIllIIll.printStackTrace();
        }
        return null;
    }

    private static String lIlIllI(String lllIIlllIllllII, String lllIIlllIlllIll) {
        try {
            SecretKeySpec lllIIlllIllllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIlllIlllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIIlllIlllllI = Cipher.getInstance("Blowfish");
            lllIIlllIlllllI.init(llIlll[2], lllIIlllIllllll);
            return new String(lllIIlllIlllllI.doFinal(Base64.getDecoder().decode(lllIIlllIllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlllIllllIl) {
            lllIIlllIllllIl.printStackTrace();
            return null;
        }
    }

    public void registerTimestamp(int lllIIlllllIlIII, int lllIIlllllIIlll, int lllIIllllllIIll, int lllIIlllllIIlIl, int lllIIllllllIIIl, double lllIIlllllIIIll, boolean lllIIlllllIllll, int lllIIlllllIIIIl, int lllIIlllllIllIl, int lllIIlllllIllII, int lllIIllllIllllI, int lllIIllllIlllIl) {
        DataAPI lllIIllllllIllI;
        if (DataAPI.lIlllIII((int)lllIIllllllIllI.datalessMode)) {
            return;
        }
        lllIIllllllIllI.repairConnection();
        try {
            PreparedStatement lllIIlllllllIII = lllIIllllllIllI.connection.prepareStatement(lIIIII[llIlll[52]]);
            lllIIlllllllIII.setInt(llIlll[1], lllIIlllllIlIII);
            lllIIlllllllIII.setInt(llIlll[2], lllIIlllllIIlll);
            lllIIlllllllIII.setInt(llIlll[3], lllIIllllllIIll);
            lllIIlllllllIII.setInt(llIlll[4], lllIIlllllIIlIl);
            lllIIlllllllIII.setInt(llIlll[5], lllIIllllllIIIl);
            lllIIlllllllIII.setDouble(llIlll[6], lllIIlllllIIIll);
            lllIIlllllllIII.setBoolean(llIlll[7], lllIIlllllIllll);
            lllIIlllllllIII.setInt(llIlll[8], lllIIlllllIIIIl);
            lllIIlllllllIII.setInt(llIlll[9], lllIIlllllIllIl);
            lllIIlllllllIII.setInt(llIlll[10], lllIIlllllIllII);
            lllIIlllllllIII.setInt(llIlll[11], lllIIllllIllllI);
            lllIIlllllllIII.setInt(llIlll[12], lllIIllllIlllIl);
            "".length();
            lllIIlllllllIII.executeUpdate();
            lllIIlllllllIII.close();
            "".length();
        }
        catch (SQLException lllIIllllllIlll) {
            lllIIllllllIlll.printStackTrace();
        }
        if (" ".length() < -" ".length()) {
            return;
        }
    }

    public boolean isInDatalessMode() {
        DataAPI lllIlIllIIlIlll;
        return lllIlIllIIlIlll.datalessMode;
    }

    private static boolean lIllIlll(int n) {
        return n == 0;
    }

    private static boolean llIIIlII(int n, int n2) {
        return n < n2;
    }

    private void registerNewGame(String lllIlIIlIIIIIII) {
        DataAPI lllIlIIIlllllll;
        lllIlIIIlllllll.repairConnection();
        if (DataAPI.lIlllIII((int)lllIlIIIlllllll.datalessMode)) {
            return;
        }
        try {
            PreparedStatement lllIlIIlIIIIIlI = lllIlIIIlllllll.connection.prepareStatement(lIIIII[llIlll[40]]);
            lllIlIIlIIIIIlI.setString(llIlll[1], lllIlIIlIIIIIII);
            "".length();
            lllIlIIlIIIIIlI.executeUpdate();
            lllIlIIlIIIIIlI.close();
            "".length();
        }
        catch (SQLException lllIlIIlIIIIIlI) {
            // empty catch block
        }
        if (((56 + 105 - 43 + 10 ^ 12 + 59 - 59 + 161) & (21 + 111 - 68 + 70 ^ 75 + 124 - 145 + 117 ^ -" ".length())) != 0) {
            return;
        }
    }

    public void setScore(int lllIlIIllIIIIlI, double lllIlIIlIlllllI) {
        DataAPI lllIlIIllIIIIII;
        if (DataAPI.lIlllIII((int)lllIlIIllIIIIII.datalessMode)) {
            lllIlIIllIIIIII.logger.info(String.valueOf(new StringBuilder().append(lIIIII[llIlll[28]]).append(lllIlIIllIIIIlI).append(lIIIII[llIlll[29]]).append(lllIlIIlIlllllI)));
            return;
        }
        try {
            PreparedStatement lllIlIIllIIIlIl = lllIlIIllIIIIII.connection.prepareStatement(lIIIII[llIlll[30]]);
            lllIlIIllIIIlIl.setDouble(llIlll[1], lllIlIIlIlllllI);
            lllIlIIllIIIlIl.setInt(llIlll[2], lllIlIIllIIIIlI);
            "".length();
            lllIlIIllIIIlIl.executeUpdate();
            lllIlIIllIIIlIl.close();
            "".length();
            if ("  ".length() < " ".length()) {
                return;
            }
        }
        catch (Exception lllIlIIllIIIlII) {
            lllIlIIllIIIlII.printStackTrace();
        }
    }

    public double getMoney(int lllIlIIlllIlIlI) {
        DataAPI lllIlIIlllIlIll;
        if (DataAPI.lIlllIII((int)lllIlIIlllIlIll.datalessMode)) {
            return 0.0;
        }
        try {
            PreparedStatement lllIlIIllllIIIl = lllIlIIlllIlIll.connection.prepareStatement(lIIIII[llIlll[21]]);
            lllIlIIllllIIIl.setInt(llIlll[1], lllIlIIlllIlIlI);
            ResultSet lllIlIIllllIIII = lllIlIIllllIIIl.executeQuery();
            "".length();
            lllIlIIllllIIII.next();
            double lllIlIIlllIllll = lllIlIIllllIIII.getInt(lIIIII[llIlll[22]]);
            lllIlIIllllIIIl.close();
            lllIlIIllllIIII.close();
            return lllIlIIlllIllll;
        }
        catch (SQLException lllIlIIlllIlllI) {
            lllIlIIlllIlllI.printStackTrace();
            return 0.0;
        }
    }

    private void registerNewMap(String lllIlIIIlIlIllI, int lllIlIIIlIlIlIl) {
        DataAPI lllIlIIIlIllIlI;
        if (DataAPI.lIlllIII((int)lllIlIIIlIllIlI.datalessMode)) {
            return;
        }
        lllIlIIIlIllIlI.repairConnection();
        try {
            PreparedStatement lllIlIIIlIllIll = lllIlIIIlIllIlI.connection.prepareStatement(lIIIII[llIlll[46]]);
            lllIlIIIlIllIll.setInt(llIlll[1], lllIlIIIlIlIlIl);
            lllIlIIIlIllIll.setString(llIlll[2], lllIlIIIlIlIllI);
            "".length();
            lllIlIIIlIllIll.executeUpdate();
            lllIlIIIlIllIll.close();
            "".length();
        }
        catch (SQLException lllIlIIIlIllIll) {
            // empty catch block
        }
        if (((0 ^ 71) & ~(12 ^ 75)) <= -" ".length()) {
            return;
        }
    }

    public double getScore(int lllIlIIllIIllll) {
        DataAPI lllIlIIllIIlllI;
        if (DataAPI.lIlllIII((int)lllIlIIllIIlllI.datalessMode)) {
            return 0.0;
        }
        try {
            PreparedStatement lllIlIIllIlIlII = lllIlIIllIIlllI.connection.prepareStatement(lIIIII[llIlll[26]]);
            lllIlIIllIlIlII.setInt(llIlll[1], lllIlIIllIIllll);
            ResultSet lllIlIIllIlIIll = lllIlIIllIlIlII.executeQuery();
            "".length();
            lllIlIIllIlIIll.next();
            double lllIlIIllIlIIlI = lllIlIIllIlIIll.getDouble(lIIIII[llIlll[27]]);
            lllIlIIllIlIlII.close();
            lllIlIIllIlIIll.close();
            return lllIlIIllIlIIlI;
        }
        catch (SQLException lllIlIIllIlIIIl) {
            lllIlIIllIlIIIl.printStackTrace();
            return 0.0;
        }
    }

    public int getMapId(String lllIlIIIlIIIlIl, int lllIlIIIlIIIlII) {
        DataAPI lllIlIIIlIIlIIl;
        lllIlIIIlIIlIIl.registerNewMap(lllIlIIIlIIIlIl, lllIlIIIlIIIlII);
        if (DataAPI.lIlllIII((int)lllIlIIIlIIlIIl.datalessMode)) {
            return llIlll[13];
        }
        try {
            PreparedStatement lllIlIIIlIIllIl = lllIlIIIlIIlIIl.connection.prepareStatement(lIIIII[llIlll[47]]);
            lllIlIIIlIIllIl.setString(llIlll[1], lllIlIIIlIIIlIl);
            ResultSet lllIlIIIlIIllII = lllIlIIIlIIllIl.executeQuery();
            "".length();
            lllIlIIIlIIllII.next();
            int lllIlIIIlIIlIll = lllIlIIIlIIllII.getInt(lIIIII[llIlll[48]]);
            lllIlIIIlIIllIl.close();
            lllIlIIIlIIllII.close();
            return lllIlIIIlIIlIll;
        }
        catch (SQLException lllIlIIIlIIlIlI) {
            lllIlIIIlIIlIlI.printStackTrace();
            return llIlll[0];
        }
    }

    private static boolean lIllIllI(Object object) {
        return object == null;
    }

    public double getElo(int lllIlIIlIllIIlI) {
        DataAPI lllIlIIlIllIIIl;
        if (DataAPI.lIlllIII((int)lllIlIIlIllIIIl.datalessMode)) {
            return 1200.0;
        }
        try {
            PreparedStatement lllIlIIlIllIlll = lllIlIIlIllIIIl.connection.prepareStatement(lIIIII[llIlll[31]]);
            lllIlIIlIllIlll.setInt(llIlll[1], lllIlIIlIllIIlI);
            ResultSet lllIlIIlIllIllI = lllIlIIlIllIlll.executeQuery();
            "".length();
            lllIlIIlIllIllI.next();
            double lllIlIIlIllIlIl = lllIlIIlIllIllI.getDouble(lIIIII[llIlll[32]]);
            lllIlIIlIllIlll.close();
            lllIlIIlIllIllI.close();
            return lllIlIIlIllIlIl;
        }
        catch (SQLException lllIlIIlIllIlII) {
            lllIlIIlIllIlII.printStackTrace();
            return 0.0;
        }
    }

    private static boolean lIlllIIl(int n) {
        return n > 0;
    }

    private static boolean lIlllIII(int n) {
        return n != 0;
    }

    public void registerMatchEnd(int lllIlIIIIIllIIl, int lllIlIIIIIllIll) {
        DataAPI lllIlIIIIIlllIl;
        if (DataAPI.lIlllIII((int)lllIlIIIIIlllIl.datalessMode)) {
            return;
        }
        lllIlIIIIIlllIl.repairConnection();
        try {
            PreparedStatement lllIlIIIIIlllll = lllIlIIIIIlllIl.connection.prepareStatement(lIIIII[llIlll[50]]);
            lllIlIIIIIlllll.setInt(llIlll[1], lllIlIIIIIllIll);
            lllIlIIIIIlllll.setInt(llIlll[2], lllIlIIIIIllIIl);
            "".length();
            lllIlIIIIIlllll.executeUpdate();
            lllIlIIIIIlllll.close();
            "".length();
            if ("  ".length() == (115 + 62 - 65 + 18 ^ 88 + 4 - 49 + 91)) {
                return;
            }
        }
        catch (SQLException lllIlIIIIIllllI) {
            lllIlIIIIIllllI.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            DataAPI lllIlIlIlIlIIlI;
            lllIlIlIlIlIIlI.connection.close();
            "".length();
        }
        catch (Exception lllIlIlIlIlIIll) {
            lllIlIlIlIlIIll.printStackTrace();
        }
        if (-"   ".length() > 0) {
            return;
        }
    }

    private static void lIlIIllI() {
        lIIIII = new String[llIlll[53]];
        DataAPI.lIIIII[DataAPI.llIlll[0]] = DataAPI.lIlIllI("zJMH379exEw=", "ScyTj");
        DataAPI.lIIIII[DataAPI.llIlll[1]] = DataAPI.lIllIII("DyUmKB8LJTY9", "cJEIs");
        DataAPI.lIIIII[DataAPI.llIlll[2]] = DataAPI.lIllIII("YHBfUA==", "SCofh");
        DataAPI.lIIIII[DataAPI.llIlll[3]] = DataAPI.lIllIIl("A+Z3cpcXPSE=", "kpGzP");
        DataAPI.lIIIII[DataAPI.llIlll[4]] = DataAPI.lIllIII("AwofEBIPFy4MAhw=", "ncqyq");
        DataAPI.lIIIII[DataAPI.llIlll[5]] = DataAPI.lIllIII("GBM5GzYUDg==", "uzWrU");
        DataAPI.lIIIII[DataAPI.llIlll[6]] = DataAPI.lIllIII("AjUVG0gFKAQJHlJ+WA==", "hQwxr");
        DataAPI.lIIIII[DataAPI.llIlll[7]] = DataAPI.lIlIllI("RlczmABjqso=", "kuldu");
        DataAPI.lIIIII[DataAPI.llIlll[8]] = DataAPI.lIlIllI("VFIza/Wt8VI=", "vkVAm");
        DataAPI.lIIIII[DataAPI.llIlll[9]] = DataAPI.lIllIII("WwA5EyQ2BC8IJQoELxN2EBM5Am0REik0GChcKgYnFwQ=", "daLgK");
        DataAPI.lIIIII[DataAPI.llIlll[10]] = DataAPI.lIllIII("GQQEHzd6BR4HczkEHx02OR9RBzx6DxAHMjgKAhZ/ehgGGic5AxgdNHofHlM8PA1cADYoHRQBcz4KBRI/PxgCPjw+DlEePD4OXw==", "ZkqsS");
        DataAPI.lIIIII[DataAPI.llIlll[11]] = DataAPI.lIllIIl("WQlfd+tK9QLClLWaD7Fl115O3g2HGMoOrBuzZME4Z+CU+Ev+P12BQa3cyVOFPHICb/WfTAzK4apEIT8ZiDQ0Jb0qoCTya8AsisXsTG/IP2EAOUaI8hiFNgg4em6GfG9B", "nLoEI");
        DataAPI.lIIIII[DataAPI.llIlll[12]] = DataAPI.lIllIII("HBkwDAJoHzZJEjoKNxoKKR88SRYkCiAMFGgFOAQDaB82SQ8sSzAHRiwKLQgKLRgqSQsnDzw=", "HkYif");
        DataAPI.lIIIII[DataAPI.llIlll[14]] = DataAPI.lIllIIl("L6+PYTxrzuLIqIMbPU6oEUJcxh/WMTAShuNtL4Mtp6I3cqyG6UmG4hJxSKcfsXe5SH3PY3VOxIc=", "CBLbB");
        DataAPI.lIIIII[DataAPI.llIlll[15]] = DataAPI.lIllIIl("YKonxFUpE1o8eZrQVHUmLQ==", "QWRQR");
        DataAPI.lIIIII[DataAPI.llIlll[16]] = DataAPI.lIlIllI("+3Vi7fwS9pE=", "JBSNe");
        DataAPI.lIIIII[DataAPI.llIlll[17]] = DataAPI.lIllIIl("HGA395+SUGPNreFeZhfKCd8Y3K9a/FxHIBW90LEMHttpdH2T7q8VdFB07LV0m2rppt4QVBHSeX0=", "MaYxv");
        DataAPI.lIIIII[DataAPI.llIlll[19]] = DataAPI.lIlIllI("d84mB64ME4Qk6UcMAjJ4JA==", "XAugH");
        DataAPI.lIIIII[DataAPI.llIlll[20]] = DataAPI.lIllIII("Ihw/Oj8XFhIT", "yRPNp");
        DataAPI.lIIIII[DataAPI.llIlll[21]] = DataAPI.lIlIllI("aKKOuXIfLWuJsqtG3lzqT9SkOjHyk3WZUvK7pyDoexpPxNP3n45+SIkSI2NK+7rSmEr1WylIT8k=", "YmRmQ");
        DataAPI.lIIIII[DataAPI.llIlll[22]] = DataAPI.lIllIIl("peBEDs1Cli4=", "jstHA");
        DataAPI.lIIIII[DataAPI.llIlll[23]] = DataAPI.lIllIIl("cNntR35VEk1KdTYnW4rhonpTIpckdLE30emJdyiS/8Y=", "HKsNn");
        DataAPI.lIIIII[DataAPI.llIlll[24]] = DataAPI.lIlIllI("vFPQ19nhNAqBQIx9q05bbA==", "henLQ");
        DataAPI.lIIIII[DataAPI.llIlll[25]] = DataAPI.lIllIII("ATcVNDURRzEFDTUeNAcSNEcCMDV0BzwaDzEeMUhedDAZMDMRRyEZAC0CIyoIMFpu", "TgQua");
        DataAPI.lIIIII[DataAPI.llIlll[26]] = DataAPI.lIllIII("HiQ/ADEZQRM2ESITFiVSCzM8CFItER8kCygTACVSGik2FzdtER8kCygTLCwWcF5I", "MasEr");
        DataAPI.lIIIII[DataAPI.llIlll[27]] = DataAPI.lIlIllI("GZcIxUSVRQ0=", "iMjhX");
        DataAPI.lIIIII[DataAPI.llIlll[28]] = DataAPI.lIlIllI("iVnPcwEzWo3RqAYefYV2lUFFDiQ32FuIz+hKvlscwMg=", "cydMV");
        DataAPI.lIIIII[DataAPI.llIlll[29]] = DataAPI.lIllIII("VBMMDCIQRAEcbg==", "tdcyN");
        DataAPI.lIIIII[DataAPI.llIlll[30]] = DataAPI.lIllIIl("4J0yZZUlfCGkXwgzsgiIMuQdn8RaJ5hlVkAFn6ZCQXKusXWzCzTiN7oaDZeku7AEc2FRqBj1CZ3pt23s0tpjJzYIOOKg24xX", "clpKl");
        DataAPI.lIIIII[DataAPI.llIlll[31]] = DataAPI.lIllIII("NDUmBw0zUAonIggQSgQcKD1KIj4LERMnPBQQShUGIiIvYj4LERMnPDgZDn9xXA==", "gpjBN");
        DataAPI.lIIIII[DataAPI.llIlll[32]] = DataAPI.lIlIllI("0Ajk82PwarE=", "GMjRo");
        DataAPI.lIIIII[DataAPI.llIlll[33]] = DataAPI.lIlIllI("7w6gbt1RQb1PQcyfYq7puLcGdu7Dpv4w8igkXO60zqc=", "VylVr");
        DataAPI.lIIIII[DataAPI.llIlll[34]] = DataAPI.lIlIllI("UJG0IOLODN/xq3Lmc6APZE+HbMHs2ukdZPiAOU6qCwg=", "ikpZu");
        DataAPI.lIIIII[DataAPI.llIlll[35]] = DataAPI.lIllIII("bCArIw4odyYzQg==", "LWDVb");
        DataAPI.lIIIII[DataAPI.llIlll[36]] = DataAPI.lIllIII("IBcNMwUwZykCPRQ+LAAiFWcaNwVVJywePhV6dl5xFSsoASUqNyUTKBAjKU8fOhBhW3EiDwwgFFU3JRMoEDUWGzVIeA==", "uGIrQ");
        DataAPI.lIIIII[DataAPI.llIlll[37]] = DataAPI.lIlIllI("LEVwTC7GOUpoUenYGNzrwT4m3JmRZIdcFXh4voy5sVr3qBnWIi4BLw==", "XwhpH");
        DataAPI.lIIIII[DataAPI.llIlll[38]] = DataAPI.lIllIIl("v9NTgF/pURyx6tUi/Vy/XqtWQ6SEhTPdOxTEqaLY1JNG13gQJNsIlxyJ6mBm/pH9i9ukpgk/jNfGwKkZ38f2zjyG2sr4axpS3KZsTvpGCfe2aIuzF5OEKAyjn+fUoBI7cKMaUCqh3OgtVt//v/aaf5+r6Mjnm9bF", "FQbnG");
        DataAPI.lIIIII[DataAPI.llIlll[39]] = DataAPI.lIllIIl("pftWiteluT6Bb8/Y4fgrdA==", "mPoBF");
        DataAPI.lIIIII[DataAPI.llIlll[40]] = DataAPI.lIllIII("Cx42IwsWcCwoDQ1wBQE4LzUWBnlqMAsHNCcwTEYPAxwwIwpieFpPYg==", "BPefY");
        DataAPI.lIIIII[DataAPI.llIlll[41]] = DataAPI.lIlIllI("XIO50TE4rVHbiCnyTo52C2zC72W8fAFqf6ZQ0hpuBlOfWPi4HJpmHmtuq07LgvW9", "tHjPs");
        DataAPI.lIIIII[DataAPI.llIlll[42]] = DataAPI.lIlIllI("4VOqt1bCxlY=", "iIqOL");
        DataAPI.lIIIII[DataAPI.llIlll[43]] = DataAPI.lIllIII("Oj8PEQlOOQlUDA0uAwceTiwTAAIDLBIdDk4/BwAEACpGAwUHIQNUBABtAhUZDyEDBx5OIAkQCEA=", "nMftm");
        DataAPI.lIIIII[DataAPI.llIlll[44]] = DataAPI.lIllIII("CCwICQ==", "fMelu");
        DataAPI.lIIIII[DataAPI.llIlll[45]] = DataAPI.lIllIIl("cswYcAlahL5GFMfCibbWvw==", "SQDOC");
        DataAPI.lIIIII[DataAPI.llIlll[46]] = DataAPI.lIllIII("IjgmMT0/Vjw6OyRWFRkOGwUVVEcLERQZCjQfERRDSxYYFR80GBQZCgtfVSIuJyMwJ09DSVlUUEJN", "kvuto");
        DataAPI.lIIIII[DataAPI.llIlll[47]] = DataAPI.lIlIllI("KEuqQoKpiP8Dn+pfnva6D8wE5QV0y74nfzsvhRE3r/3KqkJ4HU3SKLhDBD733AUJ", "vzXyN");
        DataAPI.lIIIII[DataAPI.llIlll[48]] = DataAPI.lIllIII("NTAnHzU7NQ==", "RQJzj");
        DataAPI.lIIIII[DataAPI.llIlll[49]] = DataAPI.lIlIllI("gWKNBNC+ZmPLEUYkwjSzT9Fy3VEUE3s9s7o4jOWtpQMDE6Gab1DYnnrXTj8YcvHPI/oqRMLGE7Hd7YLo4kxk2Xbvx9aiVmqkJxc96+lMoGs=", "FtrHS");
        DataAPI.lIIIII[DataAPI.llIlll[50]] = DataAPI.lIllIII("GT0tDAwJTQkgOTgOARIwJR4dIio1DUkeHRhNCSg2KDIdJDUpDVQDFxtFQGF4LBoAIzYpHwlwZ2w6IQgKCU0JIDk4DgESMSgNVHJj", "LmiMX");
        DataAPI.lIIIII[DataAPI.llIlll[51]] = DataAPI.lIllIIl("Oaa0Cinddru7zoJbSbjH4ao1J+yHYKCD66EtWdKKR4hxAKhE6ig7i1f88yl1kOy6Dk+UXggG0KaRfiH4HCAcyVu1uB1iiOzU50C37SwRT2+QPJFdzsrldG2cYXmVypWi1nwOgZoR0OdtClSwliCpXvRIL0c/MtXFNb24vDYMA5biZ2meESi+eQ==", "GLuTp");
        DataAPI.lIIIII[DataAPI.llIlll[52]] = DataAPI.lIllIIl("eeT9gWqHLAgs1hvp5kc2bpRpElkY5HVSuwkhtyeUN6x1AK9IoD+2BkFDc8voHgw47IVO5gbMGmPMFTfJB35OSVio8oqpX1wPBzkE7u0efJXaTs4vjdBB+YEG4btEvvIW5SiL/lew57rO7ZDGg8r/AJd5Rhkf208gbq6G4ioGk6sjfjE3ApGEaYc2j5qRf9MhOsNolBToFzIvdUSLeJR5V7hSku+inGhfg/9e31K0PSgqHrm8GJ7svomiTd66f0LOY5XwYE5IaCy5nas/hBcb3xtos75Wv/VFG9nb2+pO8Eob2dvb6k7wShM/IeGn6Ra7", "ZosLk");
    }

    static {
        DataAPI.lIllIlIl();
        DataAPI.lIlIIllI();
    }

    void openConnection() {
        String lllIlIlIlllIIlI = lIIIII[llIlll[1]];
        String lllIlIlIlllIIII = lIIIII[llIlll[2]];
        String lllIlIlIllIlllI = lIIIII[llIlll[3]];
        String lllIlIlIllIllIl = lIIIII[llIlll[4]];
        String lllIlIlIllIllII = lIIIII[llIlll[5]];
        try {
            lllIlIlIlllIlIl.connection = DriverManager.getConnection(String.valueOf(new StringBuilder().append(lIIIII[llIlll[6]]).append(lllIlIlIlllIIlI).append(lIIIII[llIlll[7]]).append(lllIlIlIlllIIII).append(lIIIII[llIlll[8]]).append(lllIlIlIllIlllI).append(lIIIII[llIlll[9]])), lllIlIlIllIllIl, lllIlIlIllIllII);
            "".length();
        }
        catch (Exception lllIlIlIlllIllI) {
            DataAPI lllIlIlIlllIlIl;
            lllIlIlIlllIlIl.datalessMode = llIlll[1];
            lllIlIlIlllIlIl.logger.warning(lIIIII[llIlll[10]]);
        }
        if (((80 ^ 71 ^ "   ".length()) & (132 + 23 - 153 + 155 ^ 126 + 13 - 138 + 136 ^ -" ".length())) < 0) {
            return;
        }
    }

    private static String lIllIII(String lllIIllllIlIIIl, String lllIIllllIlIIII) {
        lllIIllllIlIIIl = new String(Base64.getDecoder().decode(lllIIllllIlIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIIllllIIllll = new StringBuilder();
        char[] lllIIllllIIlllI = lllIIllllIlIIII.toCharArray();
        int lllIIllllIIllIl = llIlll[0];
        char[] lllIIllllIIIlll = lllIIllllIlIIIl.toCharArray();
        int lllIIllllIIIllI = lllIIllllIIIlll.length;
        int lllIIllllIIIlIl = llIlll[0];
        while (DataAPI.llIIIlII(lllIIllllIIIlIl, lllIIllllIIIllI)) {
            char lllIIllllIlIIlI = lllIIllllIIIlll[lllIIllllIIIlIl];
            "".length();
            lllIIllllIIllll.append((char)(lllIIllllIlIIlI ^ lllIIllllIIlllI[lllIIllllIIllIl % lllIIllllIIlllI.length]));
            ++lllIIllllIIllIl;
            ++lllIIllllIIIlIl;
            "".length();
            if ("  ".length() > ((55 ^ 112) & ~(241 ^ 182))) continue;
            return null;
        }
        return String.valueOf(lllIIllllIIllll);
    }

    public int getGameId(String lllIlIIIlllIIII) {
        DataAPI lllIlIIIlllIIIl;
        if (DataAPI.lIlllIII((int)lllIlIIIlllIIIl.datalessMode)) {
            return (int)(Math.random() * 100000.0);
        }
        lllIlIIIlllIIIl.registerNewGame(lllIlIIIlllIIII);
        try {
            PreparedStatement lllIlIIIlllIlll = lllIlIIIlllIIIl.connection.prepareStatement(lIIIII[llIlll[41]]);
            lllIlIIIlllIlll.setString(llIlll[1], lllIlIIIlllIIII);
            ResultSet lllIlIIIlllIllI = lllIlIIIlllIlll.executeQuery();
            "".length();
            lllIlIIIlllIllI.next();
            int lllIlIIIlllIlIl = lllIlIIIlllIllI.getInt(lIIIII[llIlll[42]]);
            lllIlIIIlllIlll.close();
            lllIlIIIlllIllI.close();
            return lllIlIIIlllIlIl;
        }
        catch (SQLException lllIlIIIlllIlII) {
            lllIlIIIlllIlII.printStackTrace();
            return llIlll[0];
        }
    }

    public ArrayList<Pair<String, Double>> getAutoRating() {
        ArrayList<Pair<String, Double>> lllIlIIIllIIlII;
        DataAPI lllIlIIIllIIlIl;
        lllIlIIIllIIlII = new ArrayList<Pair<String, Double>>();
        if (DataAPI.lIlllIII((int)lllIlIIIllIIlIl.datalessMode)) {
            lllIlIIIllIIlIl.logger.warning(lIIIII[llIlll[43]]);
            return lllIlIIIllIIlII;
        }
        try {
            PreparedStatement lllIlIIIllIlIII = lllIlIIIllIIlIl.connection.prepareStatement("SELECT name, time_list.total_time * 100 /  time_list.max_total_time AS auto_rating FROM\t(SELECT games.game_id, games.name, (AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) * count(distinct(match_id))) AS total_time, count(*),count(distinct(match_id)), MAX(t.average) AS max_total_time FROM games, match_history, (SELECT games.game_id, (AVG(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) * count(*)) AS average FROM match_history LEFT JOIN games ON(games.game_id = match_history.game_id) WHERE winner != -1 && start_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() && end_time IS NOT NULL GROUP BY match_history.game_id) AS t\tWHERE(games.game_id = match_history.game_id) &&\twinner != -1 && start_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() && end_time IS NOT NULL GROUP BY match_history.game_id) AS time_list ORDER BY auto_rating DESC;");
            ResultSet lllIlIIIllIIlll = lllIlIIIllIlIII.executeQuery();
            while (DataAPI.lIlllIII((int)lllIlIIIllIIlll.next())) {
                "".length();
                lllIlIIIllIIlII.add((Pair<String, Double>)new Pair((Object)lllIlIIIllIIlll.getString(lIIIII[llIlll[44]]), (Object)lllIlIIIllIIlll.getDouble(lIIIII[llIlll[45]])));
                "".length();
                if (((223 ^ 154 ^ " ".length()) & (24 + 100 - 3 + 106 ^ 89 + 105 - 48 + 21 ^ -" ".length())) == 0) continue;
                return null;
            }
            lllIlIIIllIlIII.close();
            lllIlIIIllIIlll.close();
            "".length();
        }
        catch (SQLException lllIlIIIllIIllI) {
            lllIlIIIllIIllI.printStackTrace();
        }
        if (((59 ^ 118 ^ (156 ^ 135)) & (215 + 248 - 241 + 33 ^ 153 + 156 - 223 + 83 ^ -" ".length())) != 0) {
            return null;
        }
        return lllIlIIIllIIlII;
    }

    public DataAPI() {
        DataAPI lllIlIllIIllllI;
        lllIlIllIIllllI.datalessMode = llIlll[0];
        lllIlIllIIllllI.logger = Logger.getLogger(lIIIII[llIlll[0]]);
    }
}

