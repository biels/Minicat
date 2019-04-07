/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.WorldCreator
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.biel.lobby.mapes;

import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.utilities.GestorPropietats;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class MapaResetejable
extends Mapa {
    private /* synthetic */ int multiMapId;
    private static final /* synthetic */ String[] llIlllIl;
    private static final /* synthetic */ int[] lllIIlII;
    static /* synthetic */ String FolderCopies;
    static /* synthetic */ String FolderMaps;
    protected /* synthetic */ Boolean EditMode;
    static /* synthetic */ String FolderLiveWorlds;

    public void save() {
        block6 : {
            block4 : {
                MapaResetejable lIIIIlIlIlIllIl;
                if (!MapaResetejable.llIllIllll((int)lIIIIlIlIlIllIl.EditMode.booleanValue())) break block4;
                lIIIIlIlIlIllIl.world.save();
                File lIIIIlIlIllIIIl = lIIIIlIlIlIllIl.getWorldOriginMappedFile();
                File lIIIIlIlIllIIII = lIIIIlIlIlIllIl.getLiveWorldFile();
                String lIIIIlIlIlIllll = lIIIIlIlIlIllIl.getLiveWorldAvaliableName(FolderCopies);
                File lIIIIlIlIlIlllI = new File(String.valueOf(new StringBuilder().append(FolderCopies).append(llIlllIl[lllIIlII[8]]).append(lIIIIlIlIlIllll)));
                try {
                    lIIIIlIlIlIllIl.copyDirectory(lIIIIlIlIllIIIl, lIIIIlIlIlIlllI);
                    lIIIIlIlIlIllIl.copyDirectory(lIIIIlIlIllIIII, lIIIIlIlIllIIIl);
                    "".length();
                    Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(llIlllIl[lllIIlII[9]]).append(lIIIIlIlIlIllIl.NomWorld).append(llIlllIl[lllIIlII[10]]).append(lIIIIlIlIlIllll).append(llIlllIl[lllIIlII[11]])));
                    "".length();
                }
                catch (IOException lIIIIlIlIllIIlI) {
                    "".length();
                    Bukkit.broadcastMessage((String)llIlllIl[lllIIlII[12]]);
                    lIIIIlIlIllIIlI.printStackTrace();
                }
                if (-"  ".length() > 0) {
                    return;
                }
                "".length();
                if (-(26 ^ 31) >= 0) {
                    return;
                }
                break block6;
            }
            "".length();
            Bukkit.broadcastMessage((String)llIlllIl[lllIIlII[13]]);
        }
    }

    public String getActiveMultipleMapName() {
        MapaResetejable lIIIIlIlIIlIllI;
        MapMode lIIIIlIlIIlIlll = lIIIIlIlIIlIllI.getMapMode();
        if (MapaResetejable.llIlllIIlI((Object)lIIIIlIlIIlIlll, (Object)MapMode.SINGLE)) {
            return lIIIIlIlIIlIllI.getGameName();
        }
        return lIIIIlIlIIlIllI.getMultiWorldList().get(lIIIIlIlIIlIllI.multiMapId);
    }

    public static ArrayList<String> getAllMapNames() {
        File[] lIIIIlIllIIlIlI;
        ArrayList<String> lIIIIlIllIIllII = new ArrayList<String>();
        File lIIIIlIllIIlIll = new File(FolderMaps);
        if (MapaResetejable.llIllIllIl((int)lIIIIlIllIIlIll.exists())) {
            "".length();
            lIIIIlIllIIlIll.mkdir();
        }
        File[] lIIIIlIllIIIllI = lIIIIlIllIIlIlI = lIIIIlIllIIlIll.listFiles();
        int lIIIIlIllIIIlIl = lIIIIlIllIIIllI.length;
        int lIIIIlIllIIIlII = lllIIlII[0];
        while (MapaResetejable.llIllIlllI(lIIIIlIllIIIlII, lIIIIlIllIIIlIl)) {
            File lIIIIlIllIIllIl = lIIIIlIllIIIllI[lIIIIlIllIIIlII];
            if (MapaResetejable.llIllIllll((int)lIIIIlIllIIllIl.isDirectory())) {
                String lIIIIlIllIIlllI = lIIIIlIllIIllIl.getName();
                "".length();
                lIIIIlIllIIllII.add(lIIIIlIllIIlllI);
            }
            ++lIIIIlIllIIIlII;
            "".length();
            if ("  ".length() == "  ".length()) continue;
            return null;
        }
        return lIIIIlIllIIllII;
    }

    private File getMapOriginFile() {
        MapaResetejable lIIIIlIlIlIIIlI;
        return new File(String.valueOf(new StringBuilder().append(FolderMaps).append(llIlllIl[lllIIlII[14]]).append(lIIIIlIlIlIIIlI.getGameName())));
    }

    public GestorPropietats pTemp() {
        MapaResetejable lIIIIlIlIIIIlll;
        return new GestorPropietats(String.valueOf(new StringBuilder().append(lIIIIlIlIIIIlll.getLiveWorldFolder()).append(llIlllIl[lllIIlII[20]])));
    }

    String getLiveWorldAvaliableName(String lIIIIllIIIlllII) {
        MapaResetejable lIIIIllIIlIIIlI;
        String lIIIIllIIlIIIII = llIlllIl[lllIIlII[0]];
        int lIIIIllIIIlllll = lllIIlII[1];
        File lIIIIllIIIllllI = new File(lIIIIllIIIlllII);
        if (MapaResetejable.llIllIllIl((int)lIIIIllIIIllllI.exists())) {
            "".length();
            lIIIIllIIIllllI.mkdir();
        }
        File[] lIIIIllIIIllIII = lIIIIllIIIllllI.listFiles();
        int lIIIIllIIIlIlll = lIIIIllIIIllIII.length;
        int lIIIIllIIIlIllI = lllIIlII[0];
        while (MapaResetejable.llIllIlllI(lIIIIllIIIlIllI, lIIIIllIIIlIlll)) {
            File lIIIIllIIlIIIll = lIIIIllIIIllIII[lIIIIllIIIlIllI];
            if (MapaResetejable.llIllIllll((int)lIIIIllIIlIIIll.isDirectory())) {
                int lIIIIllIIlIIllI;
                String lIIIIllIIlIIlIl = lIIIIllIIlIIIll.getName();
                int lIIIIllIIlIIlII = lIIIIllIIlIIIlI.getGameName().length();
                if (MapaResetejable.llIllIlllI(lIIIIllIIlIIlIl.length(), lIIIIllIIlIIlII)) {
                    "".length();
                    if (-" ".length() == ((153 ^ 137 ^ (152 ^ 166)) & (62 + 49 - 81 + 99 ^ 21 + 91 - -22 + 41 ^ -" ".length()))) {
                        return null;
                    }
                } else if (MapaResetejable.llIllIllll((int)lIIIIllIIlIIIlI.getGameName().equals(lIIIIllIIlIIlIl.substring(lllIIlII[0], lIIIIllIIlIIlII))) && MapaResetejable.llIlllIIII(lIIIIllIIlIIllI = Integer.parseInt(lIIIIllIIlIIlIl.substring(lIIIIllIIlIIlII)), lIIIIllIIIlllll)) {
                    lIIIIllIIIlllll = lIIIIllIIlIIllI;
                }
            }
            ++lIIIIllIIIlIllI;
            "".length();
            if (-" ".length() < (35 ^ 101 ^ (39 ^ 101))) continue;
            return null;
        }
        lIIIIllIIlIIIII = String.valueOf(new StringBuilder().append(lIIIIllIIlIIIlI.getGameName()).append(Integer.toString(lIIIIllIIIlllll + lllIIlII[1])));
        if (MapaResetejable.llIllIllll((int)lIIIIllIIlIIIII.equals(llIlllIl[lllIIlII[1]]))) {
            lIIIIllIIlIIIII = String.valueOf(new StringBuilder().append(lIIIIllIIlIIIlI.getGameName()).append(llIlllIl[lllIIlII[2]]));
        }
        return lIIIIllIIlIIIII;
    }

    private static boolean llIlllIIlI(Object object, Object object2) {
        return object == object2;
    }

    private static void llIlIllllI() {
        llIlllIl = new String[lllIIlII[29]];
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[0]] = MapaResetejable.llIlIIIllI("", "DXcYd");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[1]] = MapaResetejable.llIlIIIllI("", "wWMHA");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[2]] = MapaResetejable.llIlIIIlll("sOX9/AfBfx4=", "fZcLr");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[3]] = MapaResetejable.llIlIIIllI("", "TSRtD");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[4]] = MapaResetejable.llIlIIlIII("Nd7wkv1rd94zLt2EitCazw==", "cAqiU");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[5]] = MapaResetejable.llIlIIlIII("QgrGalA3NAKq7J1hduCrt/6alN0ozQEeD4inQP1fnTY=", "yvsbI");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[6]] = MapaResetejable.llIlIIIlll("pDyS5GpWbt0=", "kflYJ");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[7]] = MapaResetejable.llIlIIIllI("CRw7FiFsDCYLIS0ALRZzIAE6WT8lGCwOPD4CLQo=", "LnIyS");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[8]] = MapaResetejable.llIlIIIllI("bQ==", "BqjJx");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[9]] = MapaResetejable.llIlIIlIII("Mh9rp7iaXroQ31YNrM39Gg==", "VgxER");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[10]] = MapaResetejable.llIlIIIllI("cWZ4ET4oIzlSNT1qKxc2LTg9BjAsanA=", "XJXrQ");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[11]] = MapaResetejable.llIlIIlIII("vAIbTg3czSU=", "iZWXl");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[12]] = MapaResetejable.llIlIIIllI("Hy51FyQ0YjsVayllPRtrKi0yDz96IToKIjswdVIsLyMnHio0Nnw=", "ZBUzK");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[13]] = MapaResetejable.llIlIIlIII("YX2bJNcQKoPxgJhxXdSWVg0kCPpHR3XicJeb+GdtfTsbt52uOj4xAtkfS8kuulB5Y5wAJwnnjE6Xn5OGU2RDCA==", "mvMJF");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[14]] = MapaResetejable.llIlIIlIII("opaDNirxxrY=", "iLmvQ");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[15]] = MapaResetejable.llIlIIIllI("XQ==", "rzPwB");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[16]] = MapaResetejable.llIlIIIlll("bn8PyBla1Q0=", "kSXbT");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[17]] = MapaResetejable.llIlIIIlll("hhZVw99kbBU=", "HTUWj");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[18]] = MapaResetejable.llIlIIlIII("RBrEEOwvXlsewUR9hE8EPg==", "eJayO");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[19]] = MapaResetejable.llIlIIIllI("WxYbESUVJzUEIBUKeAQtAA==", "tfVpU");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[20]] = MapaResetejable.llIlIIIllI("XTkwERwCZxAMBQ==", "rIdtq");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[21]] = MapaResetejable.llIlIIIllI("YiIyJQI0NxA6", "MRbIc");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[22]] = MapaResetejable.llIlIIIlll("7tP9+T8OEBVagyrTOQMHXA==", "WnVWo");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[23]] = MapaResetejable.llIlIIIlll("fLnW3nkS5ds=", "lhjzE");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[24]] = MapaResetejable.llIlIIIlll("MNg/lVVqtPXGukTF8r7iBS7/ELuhSIc0", "ggmZL");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[25]] = MapaResetejable.llIlIIlIII("Rp49Z/lVvpY=", "ELZbC");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[26]] = MapaResetejable.llIlIIIllI("IDAGJCMDKxwlBw==", "lYpAt");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[27]] = MapaResetejable.llIlIIIlll("ZyI7KpLOFNY=", "BWdeV");
        MapaResetejable.llIlllIl[MapaResetejable.lllIIlII[28]] = MapaResetejable.llIlIIIllI("LB46AzA8", "OqJjU");
    }

    private static void llIllIllII() {
        lllIIlII = new int[30];
        MapaResetejable.lllIIlII[0] = (78 ^ 101 ^ (167 ^ 131)) & (90 ^ 16 ^ (133 ^ 192) ^ -" ".length());
        MapaResetejable.lllIIlII[1] = " ".length();
        MapaResetejable.lllIIlII[2] = "  ".length();
        MapaResetejable.lllIIlII[3] = "   ".length();
        MapaResetejable.lllIIlII[4] = 115 ^ 6 ^ (251 ^ 138);
        MapaResetejable.lllIIlII[5] = 65 + 77 - -17 + 39 ^ 43 + 79 - -61 + 12;
        MapaResetejable.lllIIlII[6] = 125 ^ 123;
        MapaResetejable.lllIIlII[7] = 126 + 107 - 146 + 80 ^ 113 + 61 - 92 + 78;
        MapaResetejable.lllIIlII[8] = 57 + 72 - -24 + 28 ^ 78 + 56 - -51 + 4;
        MapaResetejable.lllIIlII[9] = 252 ^ 155 ^ (10 ^ 100);
        MapaResetejable.lllIIlII[10] = 145 ^ 131 ^ (145 ^ 137);
        MapaResetejable.lllIIlII[11] = 37 + 37 - -16 + 53 ^ 36 + 127 - 126 + 95;
        MapaResetejable.lllIIlII[12] = 102 + 168 - 238 + 137 ^ 56 + 116 - 99 + 92;
        MapaResetejable.lllIIlII[13] = 165 ^ 168;
        MapaResetejable.lllIIlII[14] = 104 ^ 102;
        MapaResetejable.lllIIlII[15] = 180 ^ 187;
        MapaResetejable.lllIIlII[16] = 67 ^ 13 ^ (48 ^ 110);
        MapaResetejable.lllIIlII[17] = 210 ^ 195;
        MapaResetejable.lllIIlII[18] = 29 ^ 40 ^ (30 ^ 57);
        MapaResetejable.lllIIlII[19] = 53 + 89 - 78 + 75 ^ 18 + 79 - 53 + 108;
        MapaResetejable.lllIIlII[20] = 209 ^ 197;
        MapaResetejable.lllIIlII[21] = 122 ^ 15 ^ (69 ^ 37);
        MapaResetejable.lllIIlII[22] = 90 ^ 76;
        MapaResetejable.lllIIlII[23] = 5 ^ 18;
        MapaResetejable.lllIIlII[24] = 107 + 93 - 193 + 134 ^ 11 + 106 - -2 + 30;
        MapaResetejable.lllIIlII[25] = 83 ^ 74;
        MapaResetejable.lllIIlII[26] = 10 ^ 101 ^ (5 ^ 112);
        MapaResetejable.lllIIlII[27] = 78 ^ 85;
        MapaResetejable.lllIIlII[28] = 186 ^ 166;
        MapaResetejable.lllIIlII[29] = 55 + 112 - 36 + 18 ^ 2 + 89 - 46 + 91;
    }

    public void deleteVirtualWorld() {
        MapaResetejable lIIIIlIlIlllIlI;
        "".length();
        Bukkit.unloadWorld((World)lIIIIlIlIlllIlI.world, (boolean)lllIIlII[0]);
        "".length();
        Com.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Com.getPlugin(), () -> {
            MapaResetejable lIIIIlIIlllIlII;
            File lIIIIlIIlllIIll = lIIIIlIIlllIlII.getLiveWorldFile();
            MapaResetejable.deleteFolder(lIIIIlIIlllIIll);
            "".length();
            Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(llIlllIl[lllIIlII[24]]).append(lIIIIlIIlllIlII.NomWorld)));
        }, 200L);
    }

    private static boolean llIlllIIll(Object object) {
        return object != null;
    }

    private static boolean llIllIllIl(int n) {
        return n == 0;
    }

    private static boolean llIlllIlII(int n) {
        return n > 0;
    }

    public void initialize() {
        MapaResetejable lIIIIllIIllIlII;
        lIIIIllIIllIlII.createVirtualWorld();
    }

    public MapMode getMapMode() {
        MapaResetejable lIIIIlIllllIlIl;
        MapMode mapMode;
        if (MapaResetejable.llIllIllll((int)lIIIIlIllllIlIl.isWorld(lIIIIlIllllIlIl.getMapOriginFile()))) {
            mapMode = MapMode.SINGLE;
            "".length();
            if ((37 ^ 33) <= "  ".length()) {
                return null;
            }
        } else {
            mapMode = MapMode.MULTIPLE;
        }
        return mapMode;
    }

    public void setMultiMapId(int lIIIIlIllIlIlll) {
        lIIIIlIllIllIII.multiMapId = lIIIIlIllIlIlll;
    }

    private static boolean llIlllIIIl(int n, int n2) {
        return n == n2;
    }

    private void updateWorldToRegisteredHandler() {
        MapaResetejable lIIIIllIIIIIIlI;
        lIIIIllIIIIIIlI.setWorld(lIIIIllIIIIIIlI.world);
    }

    private static boolean llIllIlllI(int n, int n2) {
        return n < n2;
    }

    public String getMultiMapName() {
        MapaResetejable lIIIIlIllIllllI;
        return lIIIIlIllIllllI.getActiveMultipleMapName();
    }

    public void setEditMode(Boolean lIIIIlIlIIIlllI) {
        MapaResetejable lIIIIlIlIIIllIl;
        lIIIIlIlIIIllIl.EditMode = lIIIIlIlIIIlllI;
        lIIIIlIlIIIllIl.sendGlobalMessage(String.valueOf(new StringBuilder().append(llIlllIl[lllIIlII[18]]).append(Boolean.toString(lIIIIlIlIIIlllI))));
    }

    private static String llIlIIIlll(String lIIIIlIIllIIIll, String lIIIIlIIllIIIlI) {
        try {
            SecretKeySpec lIIIIlIIllIlIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIIlIIllIIIlI.getBytes(StandardCharsets.UTF_8)), lllIIlII[8]), "DES");
            Cipher lIIIIlIIllIIlll = Cipher.getInstance("DES");
            lIIIIlIIllIIlll.init(lllIIlII[2], lIIIIlIIllIlIII);
            return new String(lIIIIlIIllIIlll.doFinal(Base64.getDecoder().decode(lIIIIlIIllIIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIlIIllIIllI) {
            lIIIIlIIllIIllI.printStackTrace();
            return null;
        }
    }

    public boolean isWorld(File lIIIIlIllllllIl) {
        boolean bl;
        ArrayList lIIIIlIllllllII = new ArrayList();
        if (MapaResetejable.llIllIllIl((int)lIIIIlIllllllIl.exists())) {
            return lllIIlII[0];
        }
        File[] lIIIIlIlllllIll = lIIIIlIllllllIl.listFiles((lIIIIlIIllIllll, lIIIIlIIllIlllI) -> lIIIIlIIllIlllI.equals(llIlllIl[lllIIlII[25]]));
        if (MapaResetejable.llIllIllll(lIIIIlIlllllIll.length)) {
            bl = lllIIlII[1];
            "".length();
            if ((148 ^ 144) != (145 ^ 149)) {
                return (boolean)((39 ^ 29) & ~(111 ^ 85));
            }
        } else {
            bl = lllIIlII[0];
        }
        return bl;
    }

    private static String llIlIIlIII(String lIIIIlIIlIlIllI, String lIIIIlIIlIlIlIl) {
        try {
            SecretKeySpec lIIIIlIIlIllIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIlIIlIlIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIIlIIlIllIlI = Cipher.getInstance("Blowfish");
            lIIIIlIIlIllIlI.init(lllIIlII[2], lIIIIlIIlIllIll);
            return new String(lIIIIlIIlIllIlI.doFinal(Base64.getDecoder().decode(lIIIIlIIlIlIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIlIIlIllIIl) {
            lIIIIlIIlIllIIl.printStackTrace();
            return null;
        }
    }

    private String getLiveWorldFolder() {
        MapaResetejable lIIIIlIlIllllll;
        return String.valueOf(new StringBuilder().append(FolderLiveWorlds).append(llIlllIl[lllIIlII[6]]).append(lIIIIlIlIllllll.NomWorld));
    }

    private static String llIlIIIllI(String lIIIIlIIlIIIIll, String lIIIIlIIlIIIIlI) {
        lIIIIlIIlIIIIll = new String(Base64.getDecoder().decode(lIIIIlIIlIIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIIlIIlIIIllI = new StringBuilder();
        char[] lIIIIlIIlIIIlIl = lIIIIlIIlIIIIlI.toCharArray();
        int lIIIIlIIlIIIlII = lllIIlII[0];
        char[] lIIIIlIIIlllllI = lIIIIlIIlIIIIll.toCharArray();
        int lIIIIlIIIllllIl = lIIIIlIIIlllllI.length;
        int lIIIIlIIIllllII = lllIIlII[0];
        while (MapaResetejable.llIllIlllI(lIIIIlIIIllllII, lIIIIlIIIllllIl)) {
            char lIIIIlIIlIIlIIl = lIIIIlIIIlllllI[lIIIIlIIIllllII];
            "".length();
            lIIIIlIIlIIIllI.append((char)(lIIIIlIIlIIlIIl ^ lIIIIlIIlIIIlIl[lIIIIlIIlIIIlII % lIIIIlIIlIIIlIl.length]));
            ++lIIIIlIIlIIIlII;
            ++lIIIIlIIIllllII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lIIIIlIIlIIIllI);
    }

    public GestorPropietats pMapaActual() {
        MapaResetejable lIIIIlIlIIIlIIl;
        return new GestorPropietats(String.valueOf(new StringBuilder().append(lIIIIlIlIIIlIIl.getLiveWorldFolder()).append(llIlllIl[lllIIlII[19]])));
    }

    private static boolean llIlllIIII(int n, int n2) {
        return n > n2;
    }

    public ArrayList<String> getMultiWorldList() {
        File lIIIIlIlllIlIll;
        MapaResetejable lIIIIlIlllIIlll;
        File[] lIIIIlIlllIlIlI;
        ArrayList<String> lIIIIlIlllIlIII = new ArrayList<String>();
        if (MapaResetejable.llIlllIIlI((Object)lIIIIlIlllIIlll.getMapMode(), (Object)MapMode.MULTIPLE) && MapaResetejable.llIlllIIll(lIIIIlIlllIlIlI = (lIIIIlIlllIlIll = lIIIIlIlllIIlll.getMapOriginFile()).listFiles()) && MapaResetejable.llIlllIlII(lIIIIlIlllIlIlI.length)) {
            File[] lIIIIlIlllIIIll = lIIIIlIlllIlIlI;
            int lIIIIlIlllIIIlI = lIIIIlIlllIIIll.length;
            int lIIIIlIlllIIIIl = lllIIlII[0];
            while (MapaResetejable.llIllIlllI(lIIIIlIlllIIIIl, lIIIIlIlllIIIlI)) {
                File lIIIIlIlllIllII = lIIIIlIlllIIIll[lIIIIlIlllIIIIl];
                if (MapaResetejable.llIllIllll((int)lIIIIlIlllIllII.isDirectory())) {
                    "".length();
                    lIIIIlIlllIlIII.add(lIIIIlIlllIllII.getName());
                }
                ++lIIIIlIlllIIIIl;
                "".length();
                if ("   ".length() > -" ".length()) continue;
                return null;
            }
        }
        if (MapaResetejable.llIlllIIlI((Object)lIIIIlIlllIIlll.getMapMode(), (Object)MapMode.SINGLE)) {
            "".length();
            lIIIIlIlllIlIII.add(lIIIIlIlllIIlll.getMapName());
        }
        return lIIIIlIlllIlIII;
    }

    public GestorPropietats pPlayer(Player lIIIIlIIllllllI) {
        MapaResetejable lIIIIlIlIIIIIlI;
        File lIIIIlIlIIIIIII = new File(String.valueOf(new StringBuilder().append(lIIIIlIlIIIIIlI.getLiveWorldFolder()).append(llIlllIl[lllIIlII[21]])));
        if (MapaResetejable.llIllIllIl((int)lIIIIlIlIIIIIII.exists())) {
            "".length();
            lIIIIlIlIIIIIII.mkdir();
        }
        return new GestorPropietats(String.valueOf(new StringBuilder().append(lIIIIlIlIIIIIlI.getLiveWorldFolder()).append(llIlllIl[lllIIlII[22]]).append(lIIIIlIIllllllI.getName()).append(llIlllIl[lllIIlII[23]])));
    }

    void createVirtualWorld() {
        MapaResetejable lIIIIllIIIIlIII;
        if (MapaResetejable.llIllIllll((int)lIIIIllIIIIlIII.getGameName().equals(llIlllIl[lllIIlII[3]]))) {
            return;
        }
        if (MapaResetejable.llIlllIIIl((int)lIIIIllIIIIlIII.isWorldLoaded().booleanValue(), lllIIlII[1])) {
            return;
        }
        lIIIIllIIIIlIII.NomWorld = lIIIIllIIIIlIII.getLiveWorldAvaliableName(FolderLiveWorlds);
        File lIIIIllIIIIlIlI = lIIIIllIIIIlIII.getWorldOriginMappedFile();
        File lIIIIllIIIIlIIl = lIIIIllIIIIlIII.getLiveWorldFile();
        try {
            lIIIIllIIIIlIII.copyDirectory(lIIIIllIIIIlIlI, lIIIIllIIIIlIIl);
            File lIIIIllIIIIllIl = new File(String.valueOf(new StringBuilder().append(lIIIIllIIIIlIIl.getPath()).append(llIlllIl[lllIIlII[4]])));
            "".length();
            lIIIIllIIIIllIl.delete();
            "".length();
            if ("  ".length() < " ".length()) {
                return;
            }
        }
        catch (IOException lIIIIllIIIIllII) {
            "".length();
            Bukkit.broadcastMessage((String)llIlllIl[lllIIlII[5]]);
            lIIIIllIIIIllII.printStackTrace();
        }
        lIIIIllIIIIlIII.world = Bukkit.createWorld((WorldCreator)new WorldCreator(lIIIIllIIIIlIII.getLiveWorldFolder()));
        lIIIIllIIIIlIII.updateWorldToRegisteredHandler();
    }

    public MapaResetejable() {
        MapaResetejable lIIIIllIIllIllI;
        lIIIIllIIllIllI.EditMode = lllIIlII[0];
    }

    private File getLiveWorldFile() {
        MapaResetejable lIIIIlIlIlIIlII;
        return new File(lIIIIlIlIlIIlII.getLiveWorldFolder());
    }

    private static boolean llIllIllll(int n) {
        return n != 0;
    }

    public static void deleteLiveWorldsFolder() {
        try {
            FileUtils.deleteDirectory((File)new File(FolderLiveWorlds));
            "".length();
            if ((207 ^ 143 ^ (3 ^ 71)) != (111 + 18 - 110 + 143 ^ 144 + 11 - 133 + 144)) {
                return;
            }
        }
        catch (IOException lIIIIlIlIllllIl) {
            System.out.println(llIlllIl[lllIIlII[7]]);
        }
    }

    public Boolean getEditMode() {
        MapaResetejable lIIIIlIlIIlIIlI;
        return lIIIIlIlIIlIIlI.EditMode;
    }

    protected synchronized void gameEvent(Event lIIIIlIIllllIIl) {
        MapaResetejable lIIIIlIIllllIII;
        if (MapaResetejable.llIllIllIl((int)lIIIIlIIllllIII.EditMode.booleanValue())) {
            super.gameEvent(lIIIIlIIllllIIl);
        }
    }

    private File getWorldOriginMappedFile() {
        MapaResetejable lIIIIlIlIIlllII;
        MapMode lIIIIlIlIIlllIl = lIIIIlIlIIlllII.getMapMode();
        if (MapaResetejable.llIlllIIlI((Object)lIIIIlIlIIlllIl, (Object)MapMode.SINGLE)) {
            return new File(String.valueOf(new StringBuilder().append(FolderMaps).append(llIlllIl[lllIIlII[15]]).append(lIIIIlIlIIlllII.getGameName())));
        }
        if (MapaResetejable.llIlllIIlI((Object)lIIIIlIlIIlllIl, (Object)MapMode.MULTIPLE)) {
            return new File(String.valueOf(new StringBuilder().append(FolderMaps).append(llIlllIl[lllIIlII[16]]).append(lIIIIlIlIIlllII.getGameName()).append(llIlllIl[lllIIlII[17]]).append(lIIIIlIlIIlllII.getActiveMultipleMapName())));
        }
        return null;
    }

    static {
        MapaResetejable.llIllIllII();
        MapaResetejable.llIlIllllI();
        FolderLiveWorlds = llIlllIl[lllIIlII[26]];
        FolderMaps = llIlllIl[lllIIlII[27]];
        FolderCopies = llIlllIl[lllIIlII[28]];
    }

    public static final class MapMode
    extends Enum<MapMode> {
        public static final /* synthetic */ /* enum */ MapMode SINGLE;
        private static final /* synthetic */ int[] llIIll;
        private static final /* synthetic */ String[] llIIII;
        private static final /* synthetic */ MapMode[] $VALUES;
        public static final /* synthetic */ /* enum */ MapMode MULTIPLE;

        private static void lIIlIIll() {
            llIIII = new String[llIIll[2]];
            MapMode.llIIII[MapMode.llIIll[0]] = MapMode.lIIlIIlI("NhwaFS8g", "eUTRc");
            MapMode.llIIII[MapMode.llIIll[1]] = MapMode.lIIlIIlI("JQwtHSg4FSQ=", "hYaIa");
        }

        private static boolean lIIlllIl(int n, int n2) {
            return n < n2;
        }

        static {
            MapMode.lIIlllII();
            MapMode.lIIlIIll();
            SINGLE = new MapMode();
            MULTIPLE = new MapMode();
            MapMode[] arrmapMode = new MapMode[llIIll[2]];
            arrmapMode[MapMode.llIIll[0]] = SINGLE;
            arrmapMode[MapMode.llIIll[1]] = MULTIPLE;
            $VALUES = arrmapMode;
        }

        private MapMode() {
            MapMode lllIllllllllIIl;
        }

        public static MapMode[] values() {
            return (MapMode[])$VALUES.clone();
        }

        public static MapMode valueOf(String lllIllllllllllI) {
            return Enum.valueOf(MapMode.class, lllIllllllllllI);
        }

        private static String lIIlIIlI(String lllIllllllIllII, String lllIllllllIIllI) {
            lllIllllllIllII = new String(Base64.getDecoder().decode(lllIllllllIllII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lllIllllllIlIlI = new StringBuilder();
            char[] lllIllllllIlIIl = lllIllllllIIllI.toCharArray();
            int lllIllllllIlIII = llIIll[0];
            char[] lllIllllllIIIlI = lllIllllllIllII.toCharArray();
            int lllIllllllIIIIl = lllIllllllIIIlI.length;
            int lllIllllllIIIII = llIIll[0];
            while (MapMode.lIIlllIl(lllIllllllIIIII, lllIllllllIIIIl)) {
                char lllIllllllIllIl = lllIllllllIIIlI[lllIllllllIIIII];
                "".length();
                lllIllllllIlIlI.append((char)(lllIllllllIllIl ^ lllIllllllIlIIl[lllIllllllIlIII % lllIllllllIlIIl.length]));
                ++lllIllllllIlIII;
                ++lllIllllllIIIII;
                "".length();
                if ("  ".length() == "  ".length()) continue;
                return null;
            }
            return String.valueOf(lllIllllllIlIlI);
        }

        private static void lIIlllII() {
            llIIll = new int[3];
            MapMode.llIIll[0] = (250 ^ 161) & ~(20 ^ 79);
            MapMode.llIIll[1] = " ".length();
            MapMode.llIIll[2] = "  ".length();
        }
    }

}

