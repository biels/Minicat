/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package com.biel.lobby.utilities;

import com.biel.lobby.utilities.Cuboid;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GestorPropietats {
    private static final /* synthetic */ int[] lllllII;
    private static final /* synthetic */ String[] llIIIlI;
    /* synthetic */ String Ruta;

    @Deprecated
    public Location ObtenirLocation(String llIIIIIlIlIlllI) {
        GestorPropietats llIIIIIlIlIllll;
        World llIIIIIlIlIllIl = (World)Bukkit.getServer().getWorlds().get(lllllII[0]);
        return llIIIIIlIlIllll.ObtenirLocation(llIIIIIlIlIlllI, llIIIIIlIlIllIl);
    }

    private ArrayList<String> LlegirArxiuPropietats() {
        ArrayList<String> llIIIIlIllllIII = new ArrayList<String>();
        try {
            String llIIIIlIllllIll;
            GestorPropietats llIIIIlIllllIIl;
            FileInputStream llIIIIlIllllllI = new FileInputStream(llIIIIlIllllIIl.Ruta);
            DataInputStream llIIIIlIlllllIl = new DataInputStream(llIIIIlIllllllI);
            BufferedReader llIIIIlIlllllII = new BufferedReader(new InputStreamReader(llIIIIlIlllllIl));
            while (GestorPropietats.llIllIlII(llIIIIlIllllIll = llIIIIlIlllllII.readLine())) {
                "".length();
                llIIIIlIllllIII.add(llIIIIlIllllIll);
                "".length();
                if (" ".length() != 0) continue;
                return null;
            }
            llIIIIlIlllllIl.close();
            "".length();
            if ("  ".length() == 0) {
                return null;
            }
        }
        catch (Exception llIIIIlIllllIlI) {
            System.err.println(String.valueOf(new StringBuilder().append(llIIIlI[lllllII[1]]).append(llIIIIlIllllIlI.getMessage())));
        }
        return llIIIIlIllllIII;
    }

    public Double ObtenirPropietatDouble(String llIIIIlIIllllIl) {
        GestorPropietats llIIIIlIIlllllI;
        return Double.parseDouble(llIIIIlIIlllllI.ObtenirPropietat(llIIIIlIIllllIl));
    }

    public ArrayList<String> ObtenirPropietats() {
        GestorPropietats llIIIIlIllIIIII;
        ArrayList<String> llIIIIlIllIIIIl = new ArrayList<String>();
        Iterator<String> llIIIIlIlIllllI = llIIIIlIllIIIII.LlegirArxiuPropietats().iterator();
        while (GestorPropietats.llIllIlIl((int)llIIIIlIlIllllI.hasNext())) {
            String llIIIIlIllIIIll = llIIIIlIlIllllI.next();
            String[] llIIIIlIllIIlIl = llIIIIlIllIIIll.split(llIIIlI[lllllII[2]]);
            String llIIIIlIllIIlII = llIIIIlIllIIlIl[lllllII[0]];
            "".length();
            llIIIIlIllIIIIl.add(llIIIIlIllIIlII);
            "".length();
            if (null == null) continue;
            return null;
        }
        return llIIIIlIllIIIIl;
    }

    public int ObtenirPropietatInt(String llIIIIlIlIIIIIl) {
        GestorPropietats llIIIIlIlIIIlII;
        return Integer.parseInt(llIIIIlIlIIIlII.ObtenirPropietat(llIIIIlIlIIIIIl));
    }

    private static boolean llIllIlIl(int n) {
        return n != 0;
    }

    public boolean ExisteixArray(String lIlllllllllllIl) {
        GestorPropietats lIllllllllllIll;
        Iterator<String> lIllllllllllIII = lIllllllllllIll.LlegirArxiuPropietats().iterator();
        while (GestorPropietats.llIllIlIl((int)lIllllllllllIII.hasNext())) {
            String lIlllllllllllll = lIllllllllllIII.next();
            String[] llIIIIIIIIIIIIl = lIlllllllllllll.split(llIIIlI[lllllII[16]]);
            String llIIIIIIIIIIIII = llIIIIIIIIIIIIl[lllllII[0]];
            if (GestorPropietats.llIllIlIl((int)llIIIIIIIIIIIII.startsWith(lIlllllllllllIl)) && GestorPropietats.llIllIlIl((int)llIIIIIIIIIIIII.contains(llIIIlI[lllllII[17]]))) {
                return lllllII[1];
            }
            "".length();
            if ((120 + 74 - 122 + 97 ^ 90 + 50 - 51 + 84) > " ".length()) continue;
            return (boolean)((146 ^ 141 ^ (76 ^ 85)) & (81 ^ 113 ^ (5 ^ 35) ^ -" ".length()));
        }
        return lllllII[0];
    }

    private static String lIllIIlII(String lIlllllIIlllllI, String lIlllllIIllllIl) {
        try {
            SecretKeySpec lIlllllIlIIIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllllIIllllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlllllIlIIIIlI = Cipher.getInstance("Blowfish");
            lIlllllIlIIIIlI.init(lllllII[2], lIlllllIlIIIIll);
            return new String(lIlllllIlIIIIlI.doFinal(Base64.getDecoder().decode(lIlllllIIlllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllIlIIIIIl) {
            lIlllllIlIIIIIl.printStackTrace();
            return null;
        }
    }

    private static boolean llIllIlII(Object object) {
        return object != null;
    }

    public void EstablirLocation(String llIIIIIIllllIlI, Location llIIIIIIllllIIl) {
        GestorPropietats llIIIIIIllllIII;
        llIIIIIIllllIII.EstablirPropietat(llIIIIIIllllIlI, String.valueOf(new StringBuilder().append(Integer.toString((int)llIIIIIIllllIIl.getX())).append(llIIIlI[lllllII[12]]).append(Integer.toString((int)llIIIIIIllllIIl.getY())).append(llIIIlI[lllllII[13]]).append(Integer.toString((int)llIIIIIIllllIIl.getZ()))));
    }

    public void EstablirLocations(String llIIIIIIlIlIllI, ArrayList<Location> llIIIIIIlIllIll) {
        Iterator<Location> llIIIIIIlIlIlII = llIIIIIIlIllIll.iterator();
        while (GestorPropietats.llIllIlIl((int)llIIIIIIlIlIlII.hasNext())) {
            GestorPropietats llIIIIIIllIIIll;
            Location llIIIIIIllIIlIl = llIIIIIIlIlIlII.next();
            llIIIIIIllIIIll.EstablirLocation(String.valueOf(new StringBuilder().append(llIIIIIIlIlIllI).append(llIIIlI[lllllII[14]]).append(Integer.toString(llIIIIIIlIllIll.indexOf((Object)llIIIIIIllIIlIl)))), llIIIIIIllIIlIl);
            "".length();
            if (-(136 + 88 - 148 + 92 ^ 24 + 3 - 2 + 147) < 0) continue;
            return;
        }
    }

    public void IncrementarPropietat(String llIIIIIllllIlII) {
        GestorPropietats llIIIIIlllllIIl;
        llIIIIIlllllIIl.IncrementarPropietat(llIIIIIllllIlII, lllllII[1]);
    }

    public void EstablirPropietat(String llIIIIlIIIlIlIl, int llIIIIlIIIlIIIl) {
        GestorPropietats llIIIIlIIIlIIll;
        llIIIIlIIIlIIll.EstablirPropietat(llIIIIlIIIlIlIl, Integer.toString(llIIIIlIIIlIIIl));
    }

    public boolean ExisteixPropietat(String llIIIIlIllIllII) {
        GestorPropietats llIIIIlIllIllIl;
        return llIIIIlIllIllIl.ObtenirPropietats().contains(llIIIIlIllIllII);
    }

    private static String lIllIlIII(String lIlllllIIllIIIl, String lIlllllIIllIIII) {
        try {
            SecretKeySpec lIlllllIIllIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllllIIllIIII.getBytes(StandardCharsets.UTF_8)), lllllII[8]), "DES");
            Cipher lIlllllIIllIlIl = Cipher.getInstance("DES");
            lIlllllIIllIlIl.init(lllllII[2], lIlllllIIllIllI);
            return new String(lIlllllIIllIlIl.doFinal(Base64.getDecoder().decode(lIlllllIIllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllIIllIlII) {
            lIlllllIIllIlII.printStackTrace();
            return null;
        }
    }

    public void EstablirCuboid(String lIllllllIlIlllI, Location lIllllllIlIllIl, Location lIllllllIlIllII) {
        GestorPropietats lIllllllIlIlIIl;
        ArrayList<Location> lIllllllIlIlIlI = new ArrayList<Location>();
        "".length();
        lIllllllIlIlIlI.add(lIllllllIlIllIl);
        "".length();
        lIllllllIlIlIlI.add(lIllllllIlIllII);
        lIllllllIlIlIIl.EstablirLocations(lIllllllIlIlllI, lIllllllIlIlIlI);
    }

    public void EstablirArray(String lIllllllllIIIII, ArrayList<String> lIlllllllIlllII) {
        Iterator<String> lIlllllllIllIlI = lIlllllllIlllII.iterator();
        while (GestorPropietats.llIllIlIl((int)lIlllllllIllIlI.hasNext())) {
            GestorPropietats lIlllllllIllllI;
            String lIllllllllIIIlI = lIlllllllIllIlI.next();
            lIlllllllIllllI.EstablirPropietat(String.valueOf(new StringBuilder().append(lIllllllllIIIII).append(llIIIlI[lllllII[18]]).append(Integer.toString(lIlllllllIlllII.indexOf(lIllllllllIIIlI)))), lIllllllllIIIlI);
            "".length();
            if (null == null) continue;
            return;
        }
    }

    public void EstablirPropietat(String llIIIIlIIlIIIll, String llIIIIlIIlIlIII) {
        GestorPropietats llIIIIlIIlIlIlI;
        int llIIIIlIIlIIlll = lllllII[0];
        ArrayList<String> llIIIIlIIlIIllI = llIIIIlIIlIlIlI.LlegirArxiuPropietats();
        File llIIIIlIIlIIlIl = new File(llIIIIlIIlIlIlI.Ruta);
        "".length();
        llIIIIlIIlIIlIl.delete();
        try {
            BufferedWriter llIIIIlIIlIllII = new BufferedWriter(new FileWriter(llIIIIlIIlIIlIl, lllllII[0]));
            Iterator<String> llIIIIlIIIlllIl = llIIIIlIIlIIllI.iterator();
            while (GestorPropietats.llIllIlIl((int)llIIIIlIIIlllIl.hasNext())) {
                String llIIIIlIIlIllIl = llIIIIlIIIlllIl.next();
                String[] llIIIIlIIlIllll = llIIIIlIIlIllIl.split(llIIIlI[lllllII[6]]);
                String llIIIIlIIlIlllI = llIIIIlIIlIllll[lllllII[0]];
                if (GestorPropietats.llIllIlIl((int)llIIIIlIIlIIIll.equals(llIIIIlIIlIlllI))) {
                    llIIIIlIIlIllII.write(String.valueOf(new StringBuilder().append(llIIIIlIIlIIIll).append(llIIIlI[lllllII[7]]).append(llIIIIlIIlIlIII)));
                    llIIIIlIIlIllII.newLine();
                    llIIIIlIIlIIlll = lllllII[1];
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    llIIIIlIIlIllII.write(llIIIIlIIlIllIl);
                    llIIIIlIIlIllII.newLine();
                }
                "".length();
                if (null == null) continue;
                return;
            }
            if (GestorPropietats.llIllIlll(llIIIIlIIlIIlll)) {
                llIIIIlIIlIllII.write(String.valueOf(new StringBuilder().append(llIIIIlIIlIIIll).append(llIIIlI[lllllII[8]]).append(llIIIIlIIlIlIII)));
                llIIIIlIIlIllII.newLine();
            }
            llIIIIlIIlIllII.close();
            "".length();
        }
        catch (IOException llIIIIlIIlIlIll) {
            llIIIIlIIlIlIll.printStackTrace();
        }
        if (-"   ".length() >= 0) {
            return;
        }
    }

    public String[] ObtenirLlista(String llIIIIIllIIIlII) {
        GestorPropietats llIIIIIllIIIIlI;
        return llIIIIIllIIIIlI.ObtenirPropietat(llIIIIIllIIIlII).split(llIIIlI[lllllII[11]]);
    }

    private static boolean lllIIIlll(int n, int n2) {
        return n < n2;
    }

    public void IncrementarPropietat(String llIIIIlIIIIIlIl, int llIIIIlIIIIIlII) {
        GestorPropietats llIIIIlIIIIIllI;
        String llIIIIlIIIIlIII = llIIIIlIIIIIllI.ObtenirPropietat(llIIIIlIIIIIlIl);
        int llIIIIlIIIIIlll = Integer.parseInt(llIIIIlIIIIlIII);
        llIIIIlIIIIIllI.EstablirPropietat(llIIIIlIIIIIlIl, llIIIIlIIIIIlll += llIIIIlIIIIIlII);
    }

    private static boolean llIllIlll(int n) {
        return n == 0;
    }

    public GestorPropietats(String llIIIIllIIIIlIl) {
        GestorPropietats llIIIIllIIIlIII;
        llIIIIllIIIlIII.Ruta = llIIIlI[lllllII[0]];
        llIIIIllIIIlIII.Ruta = llIIIIllIIIIlIl;
    }

    public Location ObtenirLocation(String llIIIIIlIIlIllI, World llIIIIIlIIIlIlI) {
        GestorPropietats llIIIIIlIIlIlll;
        String[] llIIIIIlIIlIIlI = llIIIIIlIIlIlll.ObtenirLlista(llIIIIIlIIlIllI);
        Location llIIIIIlIIlIIII = new Location(llIIIIIlIIIlIlI, (double)Integer.parseInt(llIIIIIlIIlIIlI[lllllII[0]]), (double)Integer.parseInt(llIIIIIlIIlIIlI[lllllII[1]]), (double)Integer.parseInt(llIIIIIlIIlIIlI[lllllII[2]]));
        return llIIIIIlIIlIIII;
    }

    public ArrayList<Location> ObtenirLocations(String llIIIIIIIlIlIll, World llIIIIIIIlIIlIl) {
        ArrayList<Location> llIIIIIIIlIlIII;
        block3 : {
            int llIIIIIIIlIlIIl = lllllII[0];
            llIIIIIIIlIlIII = new ArrayList<Location>();
            do {
                String llIIIIIIIlIlllI;
                GestorPropietats llIIIIIIIlIllII;
                if (GestorPropietats.llIllIlll((int)llIIIIIIIlIllII.ExisteixPropietat(llIIIIIIIlIlllI = String.valueOf(new StringBuilder().append(llIIIIIIIlIlIll).append(llIIIlI[lllllII[15]]).append(Integer.toString(llIIIIIIIlIlIIl)))))) {
                    "".length();
                    if (null != null) {
                        return null;
                    }
                    break block3;
                }
                Location llIIIIIIIlIllIl = llIIIIIIIlIllII.ObtenirLocation(llIIIIIIIlIlllI, llIIIIIIIlIIlIl);
                "".length();
                llIIIIIIIlIlIII.add(llIIIIIIIlIllIl);
                llIIIIIIIlIlIIl += lllllII[1];
                "".length();
            } while (null == null);
            return null;
        }
        return llIIIIIIIlIlIII;
    }

    private static String lIlllIIII(String lIlllllIlIlIlIl, String lIlllllIlIlIlII) {
        lIlllllIlIlIlIl = new String(Base64.getDecoder().decode(lIlllllIlIlIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlllllIlIlIIll = new StringBuilder();
        char[] lIlllllIlIlIIlI = lIlllllIlIlIlII.toCharArray();
        int lIlllllIlIlIIIl = lllllII[0];
        char[] lIlllllIlIIlIll = lIlllllIlIlIlIl.toCharArray();
        int lIlllllIlIIlIlI = lIlllllIlIIlIll.length;
        int lIlllllIlIIlIIl = lllllII[0];
        while (GestorPropietats.lllIIIlll(lIlllllIlIIlIIl, lIlllllIlIIlIlI)) {
            char lIlllllIlIlIllI = lIlllllIlIIlIll[lIlllllIlIIlIIl];
            "".length();
            lIlllllIlIlIIll.append((char)(lIlllllIlIlIllI ^ lIlllllIlIlIIlI[lIlllllIlIlIIIl % lIlllllIlIlIIlI.length]));
            ++lIlllllIlIlIIIl;
            ++lIlllllIlIIlIIl;
            "".length();
            if (((112 + 93 - 124 + 99 ^ 75 + 37 - 30 + 59) & (32 + 21 - -113 + 7 ^ 55 + 26 - -38 + 29 ^ -" ".length())) >= -" ".length()) continue;
            return null;
        }
        return String.valueOf(lIlllllIlIlIIll);
    }

    static {
        GestorPropietats.llIllIIlI();
        GestorPropietats.lIlllIlII();
    }

    public ArrayList<String> ObtenirArray(String lIlllllllIIIllI) {
        ArrayList<String> lIlllllllIIlIII;
        block3 : {
            int lIlllllllIIlIIl = lllllII[0];
            lIlllllllIIlIII = new ArrayList<String>();
            do {
                String lIlllllllIIllIl;
                GestorPropietats lIlllllllIIlIll;
                if (GestorPropietats.llIllIlll((int)lIlllllllIIlIll.ExisteixPropietat(lIlllllllIIllIl = String.valueOf(new StringBuilder().append(lIlllllllIIIllI).append(llIIIlI[lllllII[19]]).append(Integer.toString(lIlllllllIIlIIl)))))) {
                    "".length();
                    if ("  ".length() < "  ".length()) {
                        return null;
                    }
                    break block3;
                }
                String lIlllllllIIllII = lIlllllllIIlIll.ObtenirPropietat(lIlllllllIIllIl);
                "".length();
                lIlllllllIIlIII.add(lIlllllllIIllII);
                lIlllllllIIlIIl += lllllII[1];
                "".length();
            } while (((230 ^ 163) & ~(55 ^ 114)) == 0);
            return null;
        }
        return lIlllllllIIlIII;
    }

    public void EstablirLlista(String llIIIIIllIllllI, ArrayList<String> llIIIIIllIlIlIl) {
        GestorPropietats llIIIIIlllIIIII;
        String llIIIIIllIllIlI = llIIIlI[lllllII[9]];
        Iterator<String> llIIIIIllIlIIIl = llIIIIIllIlIlIl.iterator();
        while (GestorPropietats.llIllIlIl((int)llIIIIIllIlIIIl.hasNext())) {
            String llIIIIIlllIIIIl = llIIIIIllIlIIIl.next();
            if (GestorPropietats.llIllIlIl(llIIIIIllIlIlIl.indexOf(llIIIIIlllIIIIl))) {
                llIIIIIllIllIlI = String.valueOf(new StringBuilder().append(llIIIIIllIllIlI).append(llIIIlI[lllllII[10]]));
            }
            llIIIIIllIllIlI = String.valueOf(new StringBuilder().append(llIIIIIllIllIlI).append(llIIIIIlllIIIIl));
            "".length();
            if (((82 ^ 44 ^ (122 ^ 0)) & (70 + 154 - 168 + 143 ^ 59 + 157 - 199 + 178 ^ -" ".length())) == 0) continue;
            return;
        }
        llIIIIIlllIIIII.EstablirPropietat(llIIIIIllIllllI, llIIIIIllIllIlI);
    }

    private static void llIllIIlI() {
        lllllII = new int[24];
        GestorPropietats.lllllII[0] = (247 ^ 194) & ~(155 ^ 174);
        GestorPropietats.lllllII[1] = " ".length();
        GestorPropietats.lllllII[2] = "  ".length();
        GestorPropietats.lllllII[3] = "   ".length();
        GestorPropietats.lllllII[4] = 87 ^ 83;
        GestorPropietats.lllllII[5] = 197 ^ 192;
        GestorPropietats.lllllII[6] = 3 ^ 61 ^ (141 ^ 181);
        GestorPropietats.lllllII[7] = 164 ^ 163;
        GestorPropietats.lllllII[8] = 105 + 143 - 148 + 86 ^ 152 + 174 - 207 + 59;
        GestorPropietats.lllllII[9] = 8 ^ 123 ^ (46 ^ 84);
        GestorPropietats.lllllII[10] = 83 ^ 89;
        GestorPropietats.lllllII[11] = 109 ^ 32 ^ (246 ^ 176);
        GestorPropietats.lllllII[12] = 180 ^ 184;
        GestorPropietats.lllllII[13] = 146 ^ 159;
        GestorPropietats.lllllII[14] = 95 + 80 - 84 + 56 ^ 0 + 155 - 129 + 131;
        GestorPropietats.lllllII[15] = 56 ^ 55;
        GestorPropietats.lllllII[16] = 212 ^ 197 ^ " ".length();
        GestorPropietats.lllllII[17] = 133 ^ 148;
        GestorPropietats.lllllII[18] = 40 ^ 58;
        GestorPropietats.lllllII[19] = 88 ^ 75;
        GestorPropietats.lllllII[20] = 100 + 123 - 145 + 62 ^ 127 + 137 - 227 + 115;
        GestorPropietats.lllllII[21] = 52 + 19 - -75 + 25 ^ 34 + 72 - 89 + 173;
        GestorPropietats.lllllII[22] = 33 ^ 55;
        GestorPropietats.lllllII[23] = 231 ^ 132 ^ (26 ^ 110);
    }

    private static boolean lllIIIIll(int n, int n2) {
        return n != n2;
    }

    public String ObtenirPropietat(String llIIIIlIlIIlllI) {
        GestorPropietats llIIIIlIlIIllll;
        Iterator<String> llIIIIlIlIIlIll = llIIIIlIlIIllll.LlegirArxiuPropietats().iterator();
        while (GestorPropietats.llIllIlIl((int)llIIIIlIlIIlIll.hasNext())) {
            String llIIIIlIlIlIIII = llIIIIlIlIIlIll.next();
            String[] llIIIIlIlIlIIll = llIIIIlIlIlIIII.split(llIIIlI[lllllII[3]]);
            String llIIIIlIlIlIIlI = llIIIIlIlIlIIll[lllllII[0]];
            String llIIIIlIlIlIIIl = llIIIIlIlIlIIll[lllllII[1]];
            if (GestorPropietats.llIllIlIl((int)llIIIIlIlIIlllI.equals(llIIIIlIlIlIIlI))) {
                return llIIIIlIlIlIIIl;
            }
            "".length();
            if ((237 ^ 199 ^ (237 ^ 195)) > -" ".length()) continue;
            return null;
        }
        llIIIIlIlIIllll.EstablirPropietat(llIIIIlIlIIlllI, llIIIlI[lllllII[4]]);
        return llIIIlI[lllllII[5]];
    }

    private static void lIlllIlII() {
        llIIIlI = new String[lllllII[23]];
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[0]] = GestorPropietats.lIllIIlII("M5wuT9cHQ8c=", "qkgFp");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[1]] = GestorPropietats.lIllIIlII("+vJYqH85D2Q=", "qvmdL");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[2]] = GestorPropietats.lIllIIlII("RdUAW2ejnIs=", "UQoRJ");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[3]] = GestorPropietats.lIllIlIII("sUXto/kxesw=", "UXAaT");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[4]] = GestorPropietats.lIllIlIII("d40/WwwyQR0=", "INDxb");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[5]] = GestorPropietats.lIllIIlII("2muMKxx5eQ4=", "RxyAH");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[6]] = GestorPropietats.lIllIIlII("MyfMSNFAKjQ=", "wPFsL");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[7]] = GestorPropietats.lIllIlIII("HpYm/6DZYCM=", "YRNAX");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[8]] = GestorPropietats.lIllIlIII("eSH/m7pcR5A=", "GGaaw");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[9]] = GestorPropietats.lIlllIIII("", "hCvZW");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[10]] = GestorPropietats.lIlllIIII("XQ==", "qndrH");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[11]] = GestorPropietats.lIllIIlII("CfoccCeqebU=", "xGslw");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[12]] = GestorPropietats.lIllIIlII("8FnUat+Fag4=", "DKWvl");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[13]] = GestorPropietats.lIlllIIII("RA==", "hcytF");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[14]] = GestorPropietats.lIllIlIII("WpGfOllkEDA=", "SGhRF");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[15]] = GestorPropietats.lIllIIlII("OmmKqKeuIKc=", "VxcOt");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[16]] = GestorPropietats.lIllIIlII("37Av+c4ZqsA=", "Cisnh");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[17]] = GestorPropietats.lIlllIIII("EQ==", "NUsHx");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[18]] = GestorPropietats.lIllIlIII("N7zDI+6gsYA=", "dMtyN");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[19]] = GestorPropietats.lIlllIIII("Fw==", "HKAuF");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[20]] = GestorPropietats.lIlllIIII("LzIkPSIIZyszJ0wjIzQiAi4yaGs=", "lGFRK");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[21]] = GestorPropietats.lIllIIlII("+HkHJalRuao=", "iNexO");
        GestorPropietats.llIIIlI[GestorPropietats.lllllII[22]] = GestorPropietats.lIlllIIII("YQkMLBs1DAwhUjJM", "AecOz");
    }

    public Cuboid ObtenirCuboid(String lIllllllIIIIIIl, World lIlllllIlllIlll) {
        GestorPropietats lIllllllIIIIlIl;
        ArrayList<Location> lIlllllIllllIll = lIllllllIIIIlIl.ObtenirLocations(lIllllllIIIIIIl, lIlllllIlllIlll);
        if (GestorPropietats.lllIIIIll(lIlllllIllllIll.size(), lllllII[2])) {
            System.out.println(String.valueOf(new StringBuilder().append(llIIIlI[lllllII[20]]).append(lIllllllIIIIIIl).append(llIIIlI[lllllII[21]]).append(Integer.toString(lIlllllIllllIll.size())).append(llIIIlI[lllllII[22]])));
            return null;
        }
        return new Cuboid(lIlllllIllllIll.get(lllllII[0]), lIlllllIllllIll.get(lllllII[1]));
    }

    public ArrayList<Location> ObtenirLocations(String llIIIIIIlIIIlll) {
        GestorPropietats llIIIIIIlIIIllI;
        return llIIIIIIlIIIllI.ObtenirLocations(llIIIIIIlIIIlll, null);
    }
}

