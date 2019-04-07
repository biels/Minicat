/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.connorlinfoot.titleapi.TitleAPI
 *  com.gmail.filoghost.holographicdisplays.api.Hologram
 *  com.gmail.filoghost.holographicdisplays.api.HologramsAPI
 *  com.gmail.filoghost.holographicdisplays.api.VisibilityManager
 *  com.gmail.filoghost.holographicdisplays.api.line.TextLine
 *  com.nametagedit.plugin.NametagEdit
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package com.biel.lobby;

import com.biel.lobby.GestorMapes;
import com.biel.lobby.Mapa;
import com.biel.lobby.lobby;
import com.biel.lobby.utilities.CBUtils;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.Options;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.PlayerData;
import com.connorlinfoot.titleapi.TitleAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.nametagedit.plugin.NametagEdit;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Com {
    private static /* synthetic */ HashMap<UUID, Hologram> playerHologramMap;
    private static final /* synthetic */ String[] lIIIIIIII;
    private static final /* synthetic */ int[] lIIIlIllI;
    static /* synthetic */ char NBSP;

    private static String llllIIIIll(String lIlllIlIlIllII, String lIlllIlIlIlIIl) {
        try {
            SecretKeySpec lIlllIlIlIllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllIlIlIlIIl.getBytes(StandardCharsets.UTF_8)), lIIIlIllI[9]), "DES");
            Cipher lIlllIlIlIlllI = Cipher.getInstance("DES");
            lIlllIlIlIlllI.init(lIIIlIllI[2], lIlllIlIlIllll);
            return new String(lIlllIlIlIlllI.doFinal(Base64.getDecoder().decode(lIlllIlIlIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIlIlIllIl) {
            lIlllIlIlIllIl.printStackTrace();
            return null;
        }
    }

    private static String lllIllllII(String lIlllIllIIllll, String lIlllIllIlIIII) {
        try {
            SecretKeySpec lIlllIllIlIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllIllIlIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlllIllIlIIll = Cipher.getInstance("Blowfish");
            lIlllIllIlIIll.init(lIIIlIllI[2], lIlllIllIlIlII);
            return new String(lIlllIllIlIIll.doFinal(Base64.getDecoder().decode(lIlllIllIIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIllIlIIlI) {
            lIlllIllIlIIlI.printStackTrace();
            return null;
        }
    }

    private static boolean lIIIIlIIlII(int n, int n2) {
        return n <= n2;
    }

    public static void setHeadColor(Player lIllllIlIIIllI, String lIllllIlIIIlll) {
        NametagEdit.getApi().setPrefix(lIllllIlIIIllI, lIllllIlIIIlll);
    }

    public static void playMinicatAnimation(Player lIlllIlllllIlI) {
        ArrayList<String> lIlllIlllllIIl = new ArrayList<String>();
        String lIlllIlllllIII = lIIIIIIII[lIIIlIllI[32]];
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[33]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[34]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[35]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[36]]).append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[37]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[38]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[39]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[40]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[41]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[42]]).append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[43]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[44]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[45]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[46]]).append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[47]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[48]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[49]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[50]]).append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[51]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[52]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[53]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[54]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[55]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[56]]).append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[57]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[58]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[59]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[60]]).append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[61]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[62]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[63]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[64]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[65]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[66]]).append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[67]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[68]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[69]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[70]]).append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[71]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[72]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[73]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[74]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[75]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[76]]).append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[77]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[78]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[79]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[80]]).append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[81]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[82]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[83]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[84]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[85]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[86]]).append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[87]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[88]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[89]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[90]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[91]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[92]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[93]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[94]]).append((Object)ChatColor.GOLD).append(lIIIIIIII[lIIIlIllI[95]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[96]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[97]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[98]]).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[99]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[100]])));
        "".length();
        lIlllIlllllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIII[lIIIlIllI[101]]).append(lIlllIlllllIII).append(lIIIIIIII[lIIIlIllI[102]])));
        int lIlllIllllIlll = lIIIlIllI[0];
        Iterator lIlllIllllIIlI = lIlllIlllllIIl.iterator();
        while (Com.lIIIIlIIIlI((int)lIlllIllllIIlI.hasNext())) {
            String lIlllIlllllIll = (String)lIlllIllllIIlI.next();
            "".length();
            Bukkit.getScheduler().runTaskLater((Plugin)Com.getPlugin(), () -> TitleAPI.sendTitle((Player)lIlllIlllllIlI, (Integer)lIIIlIllI[0], (Integer)lIIIlIllI[7], (Integer)lIIIlIllI[0], (String)lIlllIlllllIll, (String)Integer.toString(Bukkit.getOnlinePlayers().size())), (long)(lIIIlIllI[4] * lIlllIllllIlll + lIIIlIllI[14] + CBUtils.getPing(lIlllIlllllIlI) * lIIIlIllI[20] / lIIIlIllI[103]));
            ++lIlllIllllIlll;
            "".length();
            if (-"  ".length() <= 0) continue;
            return;
        }
    }

    private static void registerPlayerLeavingCurrentMap(Player lIllllIlIlllll) {
        Mapa lIllllIlIllllI = Com.getGest().getMapWherePlayerIs(lIllllIlIlllll);
        if (Com.lIIIIlIIIII((Object)lIllllIlIllllI)) {
            lIllllIlIllllI.Leave(lIllllIlIlllll);
        }
    }

    private static boolean lIIIIlIIIII(Object object) {
        return object != null;
    }

    private static boolean lIIIIlIIIIl(int n) {
        return n == 0;
    }

    public Com() {
        Com lIlllllIIIllII;
    }

    public static lobby getPlugin() {
        Plugin lIlllllIIIlIIl = Bukkit.getServer().getPluginManager().getPlugin(lIIIIIIII[lIIIlIllI[0]]);
        if (!Com.lIIIIlIIIII((Object)lIlllllIIIlIIl) || Com.lIIIIlIIIIl(lIlllllIIIlIIl instanceof lobby)) {
            return null;
        }
        return (lobby)lIlllllIIIlIIl;
    }

    private static void showRanking(Player lIllllIllIllIl) {
        Hologram lIllllIlllIIIl;
        if (Com.lIIIIlIIIlI((int)playerHologramMap.containsKey(lIllllIllIllIl.getUniqueId()))) {
            Hologram lIllllIllllIII = playerHologramMap.get(lIllllIllIllIl.getUniqueId());
            lIllllIllllIII.clearLines();
            "".length();
            if ((64 ^ 68) == "  ".length()) {
                return;
            }
        } else {
            Location lIllllIlllIlll = new Location(Com.getLobbyWorld(), -283.0, 72.5, 26.0);
            lIllllIlllIIIl = HologramsAPI.createHologram((Plugin)Com.getPlugin(), (Location)lIllllIlllIlll);
            "".length();
            playerHologramMap.put(lIllllIllIllIl.getUniqueId(), lIllllIlllIIIl);
        }
        VisibilityManager lIllllIlllIIII = lIllllIlllIIIl.getVisibilityManager();
        lIllllIlllIIII.showTo(lIllllIllIllIl);
        lIllllIlllIIII.setVisibleByDefault(lIIIlIllI[0]);
        "".length();
        lIllllIlllIIIl.appendTextLine(String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIIIIIIII[lIIIlIllI[2]])));
        "".length();
        lIllllIlllIIIl.appendTextLine(lIIIIIIII[lIIIlIllI[3]]);
        if (Com.lIIIIlIIIlI((int)Com.getDataAPI().isInDatalessMode())) {
            "".length();
            lIllllIlllIIIl.appendTextLine(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIIIIII[lIIIlIllI[4]])));
            return;
        }
        Integer lIllllIllIllll = lIIIlIllI[5];
        ArrayList<Integer> lIllllIllIlllI = Com.getDataAPI().getRanking();
        Iterator<Integer> lIllllIllIlIII = lIllllIllIlllI.iterator();
        while (Com.lIIIIlIIIlI((int)lIllllIllIlIII.hasNext())) {
            Integer lIllllIlllIIll = lIllllIllIlIII.next();
            PlayerData lIllllIlllIllI = new PlayerData(lIllllIlllIIll);
            int lIllllIlllIlIl = lIllllIllIlllI.indexOf(lIllllIlllIIll) + lIIIlIllI[1];
            String lIllllIlllIlII = lIIIIIIII[lIIIlIllI[6]];
            lIllllIlllIlII = String.valueOf(new StringBuilder().append(lIllllIlllIlII).append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[7]]).append(lIllllIlllIlIl).append(lIIIIIIII[lIIIlIllI[8]]));
            lIllllIlllIlII = String.valueOf(new StringBuilder().append(lIllllIlllIlII).append((Object)ChatColor.GOLD).append(lIllllIlllIllI.getUserName()));
            lIllllIlllIlII = String.valueOf(new StringBuilder().append(lIllllIlllIlII).append((Object)ChatColor.GRAY).append(lIIIIIIII[lIIIlIllI[9]]).append((Object)ChatColor.YELLOW).append(Math.round(lIllllIlllIllI.getElo())));
            "".length();
            lIllllIlllIIIl.appendTextLine(lIllllIlllIlII);
            Integer lIllllIllIIIll = lIllllIllIllll;
            Integer lIllllIllIIIlI = lIllllIllIllll = Integer.valueOf(lIllllIllIllll - lIIIlIllI[1]);
            "".length();
            if (Com.lIIIIlIIIll(lIllllIllIllll)) {
                "".length();
                if (" ".length() >= -" ".length()) break;
                return;
            }
            "".length();
            if ((105 ^ 108) != 0) continue;
            return;
        }
    }

    public static World getLobbyWorld() {
        return (World)Bukkit.getWorlds().get(lIIIlIllI[0]);
    }

    private static void lIIIIIlllll() {
        lIIIlIllI = new int[111];
        Com.lIIIlIllI[0] = (105 ^ 67) & ~(104 ^ 66);
        Com.lIIIlIllI[1] = " ".length();
        Com.lIIIlIllI[2] = "  ".length();
        Com.lIIIlIllI[3] = "   ".length();
        Com.lIIIlIllI[4] = 117 ^ 113;
        Com.lIIIlIllI[5] = 123 + 153 - 225 + 150 ^ 176 + 176 - 201 + 44;
        Com.lIIIlIllI[6] = 200 ^ 150 ^ (234 ^ 177);
        Com.lIIIlIllI[7] = 98 + 57 - 70 + 78 ^ 164 + 89 - 95 + 7;
        Com.lIIIlIllI[8] = 63 ^ 72 ^ (37 ^ 85);
        Com.lIIIlIllI[9] = 156 ^ 166 ^ (78 ^ 124);
        Com.lIIIlIllI[10] = 12 ^ 5;
        Com.lIIIlIllI[11] = 74 + 158 - 64 + 0 ^ 121 + 130 - 129 + 41;
        Com.lIIIlIllI[12] = 166 ^ 170;
        Com.lIIIlIllI[13] = 54 ^ 59;
        Com.lIIIlIllI[14] = 96 ^ 110;
        Com.lIIIlIllI[15] = 64 + 57 - 36 + 62 ^ 136 + 2 - 124 + 142;
        Com.lIIIlIllI[16] = 16 + 120 - 23 + 19 ^ 79 + 13 - 74 + 130;
        Com.lIIIlIllI[17] = 31 ^ 74 ^ (23 ^ 83);
        Com.lIIIlIllI[18] = 6 ^ 20;
        Com.lIIIlIllI[19] = 88 ^ 75;
        Com.lIIIlIllI[20] = 104 + 84 - 114 + 71 ^ 109 + 58 - 148 + 114;
        Com.lIIIlIllI[21] = 1 + 141 - 104 + 135 ^ 19 + 66 - 28 + 127;
        Com.lIIIlIllI[22] = 173 ^ 187;
        Com.lIIIlIllI[23] = 231 ^ 162 ^ (4 ^ 86);
        Com.lIIIlIllI[24] = 23 ^ 15;
        Com.lIIIlIllI[25] = 18 ^ 11;
        Com.lIIIlIllI[26] = 140 ^ 150;
        Com.lIIIlIllI[27] = 73 ^ 122 ^ (189 ^ 149);
        Com.lIIIlIllI[28] = 115 + 108 - 154 + 72 ^ 50 + 36 - -54 + 5;
        Com.lIIIlIllI[29] = 101 + 90 - 54 + 9 ^ 17 + 139 - 60 + 47;
        Com.lIIIlIllI[30] = 84 ^ 74;
        Com.lIIIlIllI[31] = 54 + 9 - -5 + 70 ^ 39 + 48 - 1 + 63;
        Com.lIIIlIllI[32] = 120 ^ 103 ^ (127 ^ 64);
        Com.lIIIlIllI[33] = 25 ^ 56;
        Com.lIIIlIllI[34] = 85 ^ 119;
        Com.lIIIlIllI[35] = 10 ^ 41;
        Com.lIIIlIllI[36] = 93 ^ 121;
        Com.lIIIlIllI[37] = 47 ^ 10;
        Com.lIIIlIllI[38] = 199 ^ 189 ^ (80 ^ 12);
        Com.lIIIlIllI[39] = 72 ^ 111;
        Com.lIIIlIllI[40] = 19 + 0 - -56 + 95 ^ 13 + 30 - -51 + 36;
        Com.lIIIlIllI[41] = "  ".length() ^ (153 ^ 178);
        Com.lIIIlIllI[42] = 98 + 16 - 78 + 139 ^ 5 + 71 - -10 + 47;
        Com.lIIIlIllI[43] = 160 ^ 139;
        Com.lIIIlIllI[44] = 128 ^ 172;
        Com.lIIIlIllI[45] = 114 ^ 95;
        Com.lIIIlIllI[46] = 94 + 20 - 51 + 67 ^ 6 + 148 - 42 + 60;
        Com.lIIIlIllI[47] = 173 ^ 130;
        Com.lIIIlIllI[48] = 40 ^ 24;
        Com.lIIIlIllI[49] = 155 ^ 170;
        Com.lIIIlIllI[50] = 216 ^ 149 ^ 109 + 48 - 132 + 102;
        Com.lIIIlIllI[51] = 154 ^ 136 ^ (22 ^ 55);
        Com.lIIIlIllI[52] = 55 ^ 31 ^ (105 ^ 117);
        Com.lIIIlIllI[53] = 52 ^ 1;
        Com.lIIIlIllI[54] = 79 + 86 - 65 + 66 ^ 41 + 15 - -50 + 38;
        Com.lIIIlIllI[55] = 127 ^ 72;
        Com.lIIIlIllI[56] = 155 ^ 163;
        Com.lIIIlIllI[57] = 72 ^ 113;
        Com.lIIIlIllI[58] = 24 ^ 94 ^ (65 ^ 61);
        Com.lIIIlIllI[59] = 180 ^ 196 ^ (127 ^ 52);
        Com.lIIIlIllI[60] = 46 ^ 18;
        Com.lIIIlIllI[61] = 0 + 6 - -25 + 107 ^ 161 + 63 - 69 + 28;
        Com.lIIIlIllI[62] = 3 ^ 73 ^ (59 ^ 79);
        Com.lIIIlIllI[63] = 6 + 53 - -66 + 4 ^ 4 + 160 - 157 + 183;
        Com.lIIIlIllI[64] = 33 + 130 - 13 + 82 ^ 142 + 30 - 69 + 65;
        Com.lIIIlIllI[65] = 203 ^ 138;
        Com.lIIIlIllI[66] = 66 ^ 0;
        Com.lIIIlIllI[67] = 86 + 93 - 109 + 64 ^ 123 + 65 - 116 + 125;
        Com.lIIIlIllI[68] = 220 ^ 173 ^ (240 ^ 197);
        Com.lIIIlIllI[69] = 67 ^ 6;
        Com.lIIIlIllI[70] = 40 + 187 - 177 + 160 ^ 18 + 58 - -69 + 3;
        Com.lIIIlIllI[71] = 212 + 75 - 229 + 165 ^ 128 + 55 - 115 + 84;
        Com.lIIIlIllI[72] = 61 ^ 93 ^ (123 ^ 83);
        Com.lIIIlIllI[73] = 10 + 75 - -89 + 21 ^ 97 + 75 - 166 + 132;
        Com.lIIIlIllI[74] = 79 ^ 11 ^ (10 ^ 4);
        Com.lIIIlIllI[75] = 225 ^ 170;
        Com.lIIIlIllI[76] = 111 ^ 35;
        Com.lIIIlIllI[77] = 234 ^ 167;
        Com.lIIIlIllI[78] = 210 ^ 129 ^ (166 ^ 187);
        Com.lIIIlIllI[79] = 69 ^ 7 ^ (105 ^ 100);
        Com.lIIIlIllI[80] = 194 + 196 - 203 + 43 ^ 40 + 24 - -71 + 47;
        Com.lIIIlIllI[81] = 11 ^ 90;
        Com.lIIIlIllI[82] = 54 ^ 100;
        Com.lIIIlIllI[83] = 191 + 9 - 163 + 174 ^ 73 + 5 - -50 + 0;
        Com.lIIIlIllI[84] = 217 ^ 159 ^ (99 ^ 113);
        Com.lIIIlIllI[85] = 73 + 125 - 36 + 37 ^ 124 + 46 - 165 + 141;
        Com.lIIIlIllI[86] = 33 + 184 - 86 + 72 ^ 111 + 0 - -20 + 26;
        Com.lIIIlIllI[87] = 24 ^ 79;
        Com.lIIIlIllI[88] = 221 ^ 133;
        Com.lIIIlIllI[89] = 180 ^ 174 ^ (98 ^ 33);
        Com.lIIIlIllI[90] = 1 ^ 91;
        Com.lIIIlIllI[91] = 100 + 24 - 92 + 194 ^ 0 + 161 - 107 + 131;
        Com.lIIIlIllI[92] = 234 ^ 182;
        Com.lIIIlIllI[93] = 7 ^ 41 ^ (96 ^ 19);
        Com.lIIIlIllI[94] = 1 ^ 35 ^ (118 ^ 10);
        Com.lIIIlIllI[95] = 5 ^ 90;
        Com.lIIIlIllI[96] = 30 ^ 126;
        Com.lIIIlIllI[97] = 82 ^ 71 ^ (89 ^ 45);
        Com.lIIIlIllI[98] = 209 ^ 179;
        Com.lIIIlIllI[99] = 88 ^ 59;
        Com.lIIIlIllI[100] = 231 ^ 131;
        Com.lIIIlIllI[101] = 207 ^ 170;
        Com.lIIIlIllI[102] = 226 ^ 132;
        Com.lIIIlIllI[103] = -(-14433 & 30837) & (-11267 & 28670);
        Com.lIIIlIllI[104] = 255 ^ 150 ^ (146 ^ 156);
        Com.lIIIlIllI[105] = 100 ^ 114 ^ (38 ^ 88);
        Com.lIIIlIllI[106] = 97 ^ 8;
        Com.lIIIlIllI[107] = 217 ^ 179;
        Com.lIIIlIllI[108] = 5 ^ 95 ^ (158 ^ 175);
        Com.lIIIlIllI[109] = (61 ^ 122) + (144 ^ 128) - (101 ^ 33) + (105 + 53 - 111 + 94);
        Com.lIIIlIllI[110] = 235 ^ 135;
    }

    public static void sendLobbyMessage(String lIllllIlIlIIll) {
        Iterator lIllllIlIlIIlI = Bukkit.getOnlinePlayers().iterator();
        while (Com.lIIIIlIIIlI((int)lIllllIlIlIIlI.hasNext())) {
            Player lIllllIlIlIlIl = (Player)lIllllIlIlIIlI.next();
            if (Com.lIIIIlIIIlI((int)Com.isOnLobby(lIllllIlIlIlIl).booleanValue())) {
                lIllllIlIlIlIl.sendMessage(lIllllIlIlIIll);
            }
            "".length();
            if (-"   ".length() <= 0) continue;
            return;
        }
    }

    public static void displayRanking(Player lIllllIIllIIII) {
        if (Com.lIIIIlIIIlI((int)Com.getDataAPI().isInDatalessMode())) {
            lIllllIIllIIII.sendMessage(lIIIIIIII[lIIIlIllI[10]]);
            return;
        }
        ArrayList<Integer> lIllllIIlIllll = Com.getDataAPI().getRanking();
        lIllllIIllIIII.sendMessage(lIIIIIIII[lIIIlIllI[5]]);
        int lIllllIIlIlllI = lIIIlIllI[5];
        Iterator<Integer> lIllllIIlIlIlI = lIllllIIlIllll.iterator();
        while (Com.lIIIIlIIIlI((int)lIllllIIlIlIlI.hasNext())) {
            Integer lIllllIIllIIIl = lIllllIIlIlIlI.next();
            PlayerData lIllllIIllIlIl = new PlayerData(lIllllIIllIIIl);
            int lIllllIIllIlII = lIllllIIlIllll.indexOf(lIllllIIllIIIl) + lIIIlIllI[1];
            String lIllllIIllIIll = lIIIIIIII[lIIIlIllI[11]];
            String lIllllIIllIIlI = lIIIIIIII[lIIIlIllI[12]];
            lIllllIIllIIlI = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[13]]).append((Object)ChatColor.BLUE));
            if (Com.lIIIIlIIlII(lIllllIIllIlII, lIIIlIllI[5])) {
                lIllllIIllIIlI = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[14]]).append((Object)ChatColor.YELLOW));
            }
            if (Com.lIIIIlIIlII(lIllllIIllIlII, lIIIlIllI[3])) {
                lIllllIIllIIlI = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[15]]).append((Object)ChatColor.GREEN));
            }
            if (Com.lIIIIlIIlII(lIllllIIllIlII, lIIIlIllI[1])) {
                lIllllIIllIIlI = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[16]]).append((Object)ChatColor.AQUA));
            }
            if (Com.lIIIIlIlIII(lIllllIIllIlII, lIllllIIlIllll.size())) {
                lIllllIIllIIlI = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[17]]).append((Object)ChatColor.RED));
            }
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append((Object)ChatColor.GREEN));
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append(Integer.toString(lIllllIIllIlII)));
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append((Object)ChatColor.RED));
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append(lIIIIIIII[lIIIlIllI[18]]));
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append(lIllllIIllIIlI));
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append(lIllllIIllIlIl.getUserName()));
            lIllllIIllIIll = String.valueOf(new StringBuilder().append(lIllllIIllIIll).append((Object)ChatColor.DARK_AQUA).append(lIIIIIIII[lIIIlIllI[19]]).append(Math.round(lIllllIIllIlIl.getElo())).append(lIIIIIIII[lIIIlIllI[20]]));
            lIllllIIllIIII.sendMessage(lIllllIIllIIll);
            if (Com.lIIIIlIIIll(--lIllllIIlIlllI)) {
                "".length();
                if (((243 ^ 180) & ~(5 ^ 66)) >= ((99 ^ 67) & ~(59 ^ 27))) break;
                return;
            }
            "".length();
            if (((71 ^ 73) & ~(169 ^ 167)) < (98 ^ 102)) continue;
            return;
        }
    }

    public static void setSuffix(Player lIllllIlIIIIII, String lIllllIlIIIIIl) {
        NametagEdit.getApi().setSuffix(lIllllIlIIIIII, lIllllIlIIIIIl);
    }

    private static void lIIIIIIIlII() {
        lIIIIIIII = new String[lIIIlIllI[110]];
        Com.lIIIIIIII[Com.lIIIlIllI[0]] = Com.lllIllllII("q499+CeK0+Y=", "PliLG");
        Com.lIIIIIIII[Com.lIIIlIllI[1]] = Com.lllIllllll("QTIKAiAHek4=", "iSnoI");
        Com.lIIIIIIII[Com.lIIIlIllI[2]] = Com.lllIllllII("ozX/ABJqLoJJjwXKA99cnw==", "Pawim");
        Com.lIIIIIIII[Com.lIIIlIllI[3]] = Com.lllIllllII("IMq+kDoAW7s=", "UGLrB");
        Com.lIIIIIIII[Com.lIIIlIllI[4]] = Com.lllIllllII("UgshovsWb14Nb61b6TwB912q69d33Jai6e+phXJqmbyLdHLMbJ8+7CB2vYW4sPjG", "PlSeZ");
        Com.lIIIIIIII[Com.lIIIlIllI[6]] = Com.lllIllllII("AInXvIIkxwg=", "hfvKw");
        Com.lIIIIIIII[Com.lIIIlIllI[7]] = Com.lllIllllII("07syk+y7Q2s=", "RurWj");
        Com.lIIIIIIII[Com.lIIIlIllI[8]] = Com.lllIllllll("QGs=", "nKfHu");
        Com.lIIIIIIII[Com.lIIIlIllI[9]] = Com.lllIllllII("fd83urDD630=", "KHJQS");
        Com.lIIIIIIII[Com.lIIIlIllI[10]] = Com.lllIllllll("MhtUCsKWGQYBERgQVxoXVhIEVAgZA1cCEQUCFhgRAg0WBlgTGVcZFxISVwcdGAQSVBwXExIH", "wwtxv");
        Com.lIIIIIIII[Com.lIIIlIllI[5]] = Com.llllIIIIll("VjJzTaJPBtXhbVBPYso8qzyXZ6CK1D249HMJrpA8ysI=", "UARUY");
        Com.lIIIIIIII[Com.lIIIlIllI[11]] = Com.lllIllllII("PFywRYy7A1A=", "pJuKF");
        Com.lIIIIIIII[Com.lIIIlIllI[12]] = Com.lllIllllII("/4l6Z095s/I=", "ncGtd");
        Com.lIIIIIIII[Com.lIIIlIllI[13]] = Com.lllIllllll("", "jhOay");
        Com.lIIIIIIII[Com.lIIIlIllI[14]] = Com.llllIIIIll("iUg2upUqBB4=", "zqjIf");
        Com.lIIIIIIII[Com.lIIIlIllI[15]] = Com.llllIIIIll("aPEAcWgtc/o=", "XMzyQ");
        Com.lIIIIIIII[Com.lIIIlIllI[16]] = Com.llllIIIIll("k/B22199jYs=", "arTGc");
        Com.lIIIIIIII[Com.lIIIlIllI[17]] = Com.llllIIIIll("1ZD7BvjADxQ=", "sZOQi");
        Com.lIIIIIIII[Com.lIIIlIllI[18]] = Com.lllIllllll("cnpr", "RWKcO");
        Com.lIIIIIIII[Com.lIIIlIllI[19]] = Com.lllIllllII("90iopEPDjWM=", "cIFHW");
        Com.lIIIIIIII[Com.lIIIlIllI[20]] = Com.lllIllllll("Mg==", "oehdv");
        Com.lIIIIIIII[Com.lIIIlIllI[21]] = Com.llllIIIIll("gJ+b/0I0tlLdT0mq12gCvA==", "kUbyU");
        Com.lIIIIIIII[Com.lIIIlIllI[22]] = Com.lllIllllII("JSZV0SyqDRM=", "TwUbn");
        Com.lIIIIIIII[Com.lIIIlIllI[23]] = Com.lllIllllll("", "pvmeA");
        Com.lIIIIIIII[Com.lIIIlIllI[24]] = Com.lllIllllll("", "QCpiW");
        Com.lIIIIIIII[Com.lIIIlIllI[25]] = Com.lllIllllII("oY3P8f8CyF0=", "sNrPg");
        Com.lIIIIIIII[Com.lIIIlIllI[26]] = Com.llllIIIIll("xB7rcPRE81Q=", "vSUTe");
        Com.lIIIIIIII[Com.lIIIlIllI[27]] = Com.llllIIIIll("5AQhfn54LKc=", "OxAsg");
        Com.lIIIIIIII[Com.lIIIlIllI[28]] = Com.lllIllllII("Obczu1kvYTM=", "KfPQm");
        Com.lIIIIIIII[Com.lIIIlIllI[29]] = Com.lllIllllll("VH9p", "tRIDN");
        Com.lIIIIIIII[Com.lIIIlIllI[30]] = Com.lllIllllll("Z2g=", "KHIqg");
        Com.lIIIIIIII[Com.lIIIlIllI[31]] = Com.lllIllllII("9NY4bZwl9Qo=", "tTkVu");
        Com.lIIIIIIII[Com.lIIIlIllI[32]] = Com.lllIllllII("GJnLbzwI++0=", "Znnhz");
        Com.lIIIIIIII[Com.lIIIlIllI[33]] = Com.llllIIIIll("SNBs+nqx3I8=", "tKwbX");
        Com.lIIIIIIII[Com.lIIIlIllI[34]] = Com.lllIllllll("GgIhGykWHw==", "WKoRj");
        Com.lIIIIIIII[Com.lIIIlIllI[35]] = Com.lllIllllII("5/rkEybHg8k=", "BQvnH");
        Com.lIIIIIIII[Com.lIIIlIllI[36]] = Com.lllIllllII("WvqdDZ7DTaM=", "zVekX");
        Com.lIIIIIIII[Com.lIIIlIllI[37]] = Com.llllIIIIll("YXBSrtiy18U=", "HuZfJ");
        Com.lIIIIIIII[Com.lIIIlIllI[38]] = Com.lllIllllll("Oyw/LBEm", "rbvoP");
        Com.lIIIIIIII[Com.lIIIlIllI[39]] = Com.llllIIIIll("9DHGiWAT7II=", "wReQY");
        Com.lIIIIIIII[Com.lIIIlIllI[40]] = Com.llllIIIIll("bs7wmiE7JBA=", "WbKRe");
        Com.lIIIIIIII[Com.lIIIlIllI[41]] = Com.lllIllllII("V+KW2iUDmRw=", "xeFAO");
        Com.lIIIIIIII[Com.lIIIlIllI[42]] = Com.llllIIIIll("ZEpnewhbpQI=", "zMAaf");
        Com.lIIIIIIII[Com.lIIIlIllI[43]] = Com.llllIIIIll("EE93GuC56zI=", "XmHFF");
        Com.lIIIIIIII[Com.lIIIlIllI[44]] = Com.lllIllllII("YJrZaC4UX3o=", "knbzS");
        Com.lIIIIIIII[Com.lIIIlIllI[45]] = Com.llllIIIIll("Z07dewvemG8=", "QBmDh");
        Com.lIIIIIIII[Com.lIIIlIllI[46]] = Com.lllIllllII("vagOGiloOnk=", "BumeG");
        Com.lIIIIIIII[Com.lIIIlIllI[47]] = Com.lllIllllII("QzPy06aJg2M=", "qualY");
        Com.lIIIIIIII[Com.lIIIlIllI[48]] = Com.lllIllllII("vbZO5ayfGvI=", "racfH");
        Com.lIIIIIIII[Com.lIIIlIllI[49]] = Com.llllIIIIll("dxMNDD7z1dE=", "aixaN");
        Com.lIIIIIIII[Com.lIIIlIllI[50]] = Com.lllIllllll("Aw==", "MeHLN");
        Com.lIIIIIIII[Com.lIIIlIllI[51]] = Com.lllIllllll("", "qKEbj");
        Com.lIIIIIIII[Com.lIIIlIllI[52]] = Com.llllIIIIll("kXhnE1VMElM=", "YXOHW");
        Com.lIIIIIIII[Com.lIIIlIllI[53]] = Com.llllIIIIll("EN5fte9DY18=", "luuVF");
        Com.lIIIIIIII[Com.lIIIlIllI[54]] = Com.llllIIIIll("yH6faTZHz+w=", "Fbxzg");
        Com.lIIIIIIII[Com.lIIIlIllI[55]] = Com.llllIIIIll("1LjKeVEsK28=", "xfbIt");
        Com.lIIIIIIII[Com.lIIIlIllI[56]] = Com.llllIIIIll("b9XBiXfpnAk=", "ghRFX");
        Com.lIIIIIIII[Com.lIIIlIllI[57]] = Com.lllIllllII("0tc7bOeBVS4=", "MPfXh");
        Com.lIIIIIIII[Com.lIIIlIllI[58]] = Com.llllIIIIll("LY8hpR4PoUQ=", "mDEFv");
        Com.lIIIIIIII[Com.lIIIlIllI[59]] = Com.lllIllllII("8cp9UfRVJ9c=", "GSlyE");
        Com.lIIIIIIII[Com.lIIIlIllI[60]] = Com.llllIIIIll("GoR4xfmeD6g=", "hsYPs");
        Com.lIIIIIIII[Com.lIIIlIllI[61]] = Com.lllIllllll("", "NeaJt");
        Com.lIIIIIIII[Com.lIIIlIllI[62]] = Com.lllIllllll("CBgA", "KYTZJ");
        Com.lIIIIIIII[Com.lIIIlIllI[63]] = Com.llllIIIIll("PVM0YqiZFA8=", "ZyWbg");
        Com.lIIIIIIII[Com.lIIIlIllI[64]] = Com.llllIIIIll("X/MBGvifYPM=", "NwsKh");
        Com.lIIIIIIII[Com.lIIIlIllI[65]] = Com.lllIllllII("dYHX4B0A7rg=", "LLxdV");
        Com.lIIIIIIII[Com.lIIIlIllI[66]] = Com.lllIllllll("Fw==", "YQKTX");
        Com.lIIIIIIII[Com.lIIIlIllI[67]] = Com.llllIIIIll("jWTMevUzSqo=", "aKbWQ");
        Com.lIIIIIIII[Com.lIIIlIllI[68]] = Com.lllIllllll("Ag==", "KwpNb");
        Com.lIIIIIIII[Com.lIIIlIllI[69]] = Com.llllIIIIll("Fynoce2tWlU=", "KtjuC");
        Com.lIIIIIIII[Com.lIIIlIllI[70]] = Com.lllIllllII("jr+uOfTBjkQ=", "kdzoY");
        Com.lIIIIIIII[Com.lIIIlIllI[71]] = Com.lllIllllll("", "qHeKS");
        Com.lIIIIIIII[Com.lIIIlIllI[72]] = Com.llllIIIIll("+2haJPowdwQ=", "mVktW");
        Com.lIIIIIIII[Com.lIIIlIllI[73]] = Com.lllIllllII("0UlRGJooWds=", "ktrEo");
        Com.lIIIIIIII[Com.lIIIlIllI[74]] = Com.lllIllllII("Q3dEWnBccSQ=", "joMWd");
        Com.lIIIIIIII[Com.lIIIlIllI[75]] = Com.lllIllllll("", "nAxmh");
        Com.lIIIIIIII[Com.lIIIlIllI[76]] = Com.lllIllllll("CA==", "ADCcy");
        Com.lIIIIIIII[Com.lIIIlIllI[77]] = Com.llllIIIIll("1xH7l4u31LM=", "mCrkd");
        Com.lIIIIIIII[Com.lIIIlIllI[78]] = Com.lllIllllll("Dg==", "MIQPz");
        Com.lIIIIIIII[Com.lIIIlIllI[79]] = Com.lllIllllII("89hs5O7+EeE=", "jEyZV");
        Com.lIIIIIIII[Com.lIIIlIllI[80]] = Com.llllIIIIll("Lty+gipx2AI=", "brPKq");
        Com.lIIIIIIII[Com.lIIIlIllI[81]] = Com.llllIIIIll("Qeqxm1uKmsY=", "aLVKa");
        Com.lIIIIIIII[Com.lIIIlIllI[82]] = Com.lllIllllII("2Tr32l6HX2k=", "uTjtM");
        Com.lIIIIIIII[Com.lIIIlIllI[83]] = Com.lllIllllll("", "cDLXS");
        Com.lIIIIIIII[Com.lIIIlIllI[84]] = Com.lllIllllII("jhk8XP7b/xI=", "RQLKX");
        Com.lIIIIIIII[Com.lIIIlIllI[85]] = Com.llllIIIIll("fu1fj7b1170=", "VwZSn");
        Com.lIIIIIIII[Com.lIIIlIllI[86]] = Com.llllIIIIll("mVmvqZ14R6w=", "AMMyd");
        Com.lIIIIIIII[Com.lIIIlIllI[87]] = Com.lllIllllll("", "MnWqP");
        Com.lIIIIIIII[Com.lIIIlIllI[88]] = Com.llllIIIIll("g8KopIFA+I8=", "yDvUV");
        Com.lIIIIIIII[Com.lIIIlIllI[89]] = Com.lllIllllII("5TwCz/4I8JU=", "HLDGu");
        Com.lIIIIIIII[Com.lIIIlIllI[90]] = Com.llllIIIIll("ghsruBphmWU=", "RANet");
        Com.lIIIIIIII[Com.lIIIlIllI[91]] = Com.lllIllllll("", "koFXK");
        Com.lIIIIIIII[Com.lIIIlIllI[92]] = Com.lllIllllll("OgU2KiA=", "wLxcc");
        Com.lIIIIIIII[Com.lIIIlIllI[93]] = Com.llllIIIIll("fY2q083AsZA=", "VDJsw");
        Com.lIIIIIIII[Com.lIIIlIllI[94]] = Com.llllIIIIll("aUCwSAxcyGE=", "RTpEY");
        Com.lIIIIIIII[Com.lIIIlIllI[95]] = Com.lllIllllll("", "CUpBn");
        Com.lIIIIIIII[Com.lIIIlIllI[96]] = Com.lllIllllll("Mw==", "grXne");
        Com.lIIIIIIII[Com.lIIIlIllI[97]] = Com.llllIIIIll("3xMlh8cq9O4=", "iwUoc");
        Com.lIIIIIIII[Com.lIIIlIllI[98]] = Com.llllIIIIll("SxPuOv3KBRk=", "UGyze");
        Com.lIIIIIIII[Com.lIIIlIllI[99]] = Com.lllIllllII("uEbH5UR72MU=", "Mppji");
        Com.lIIIIIIII[Com.lIIIlIllI[100]] = Com.lllIllllll("LA==", "xvVED");
        Com.lIIIIIIII[Com.lIIIlIllI[101]] = Com.llllIIIIll("lgEr3Kirly4=", "GRIQA");
        Com.lIIIIIIII[Com.lIIIlIllI[102]] = Com.lllIllllII("WD1YGX7sg40=", "ZwBqp");
        Com.lIIIIIIII[Com.lIIIlIllI[104]] = Com.llllIIIIll("940+HpVUh30=", "QKYwd");
        Com.lIIIIIIII[Com.lIIIlIllI[105]] = Com.lllIllllll("ABsjGQcR", "prOvs");
        Com.lIIIIIIII[Com.lIIIlIllI[106]] = Com.lllIllllll("MxsJPQ==", "QzeQM");
        Com.lIIIIIIII[Com.lIIIlIllI[107]] = Com.lllIllllII("p3czBhfp+JY=", "uiSnL");
        Com.lIIIIIIII[Com.lIIIlIllI[108]] = Com.lllIllllll("eQ==", "ZPilb");
    }

    private static boolean lIIIIlIlIII(int n, int n2) {
        return n == n2;
    }

    public static Material getSkullIconMaterial(Player lIlllIlllIllII) {
        Material lIlllIlllIllIl = Material.SKULL_ITEM;
        String[] arrstring = new String[lIIIlIllI[4]];
        arrstring[Com.lIIIlIllI[0]] = lIIIIIIII[lIIIlIllI[104]];
        arrstring[Com.lIIIlIllI[1]] = lIIIIIIII[lIIIlIllI[105]];
        arrstring[Com.lIIIlIllI[2]] = lIIIIIIII[lIIIlIllI[106]];
        arrstring[Com.lIIIlIllI[3]] = lIIIIIIII[lIIIlIllI[107]];
        if (Com.lIIIIlIIIlI((int)Stream.of(arrstring).anyMatch(lIlllIlllIIlIl -> {
            boolean bl;
            if (!Com.lIIIIlIIIIl((int)lIlllIlllIllII.getName().equalsIgnoreCase((String)lIlllIlllIIlIl)) || Com.lIIIIlIIIlI((int)lIlllIlllIllII.getName().contains((CharSequence)lIlllIlllIIlIl))) {
                bl = lIIIlIllI[1];
                "".length();
                if (" ".length() > "   ".length()) {
                    return (boolean)((169 ^ 184) & ~(109 ^ 124));
                }
            } else {
                bl = lIIIlIllI[0];
            }
            return bl;
        }))) {
            lIlllIlllIllIl = Material.SLIME_BALL;
        }
        return lIlllIlllIllIl;
    }

    public static GestorMapes getGest() {
        return Com.getPlugin().gest;
    }

    private static boolean lIIIIlIIIlI(int n) {
        return n != 0;
    }

    private static String lllIllllll(String lIlllIllIIIIIl, String lIlllIlIlllIll) {
        lIlllIllIIIIIl = new String(Base64.getDecoder().decode(lIlllIllIIIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlllIlIllllll = new StringBuilder();
        char[] lIlllIlIlllllI = lIlllIlIlllIll.toCharArray();
        int lIlllIlIllllIl = lIIIlIllI[0];
        char[] lIlllIlIllIlll = lIlllIllIIIIIl.toCharArray();
        int lIlllIlIllIllI = lIlllIlIllIlll.length;
        int lIlllIlIllIlIl = lIIIlIllI[0];
        while (Com.lIIIIllIIlI(lIlllIlIllIlIl, lIlllIlIllIllI)) {
            char lIlllIllIIIIlI = lIlllIlIllIlll[lIlllIlIllIlIl];
            "".length();
            lIlllIlIllllll.append((char)(lIlllIllIIIIlI ^ lIlllIlIlllllI[lIlllIlIllllIl % lIlllIlIlllllI.length]));
            ++lIlllIlIllllIl;
            ++lIlllIlIllIlIl;
            "".length();
            if (((19 ^ 29) & ~(109 ^ 99)) == 0) continue;
            return null;
        }
        return String.valueOf(lIlllIlIllllll);
    }

    public static void teleportPlayerToLobby(Player lIlllllIIIIlIl) {
        Com.registerPlayerLeavingCurrentMap(lIlllllIIIIlIl);
        Utils.clearPlayer(lIlllllIIIIlIl);
        "".length();
        lIlllllIIIIlIl.teleport(Com.getLobbyWorld().getSpawnLocation());
        lIlllllIIIIlIl.setDisplayName(lIlllllIIIIlIl.getName());
        if (Com.lIIIIlIIIlI((int)lIlllllIIIIlIl.isOp())) {
            lIlllllIIIIlIl.setDisplayName(String.valueOf(new StringBuilder().append((Object)ChatColor.LIGHT_PURPLE).append(lIIIIIIII[lIIIlIllI[1]]).append((Object)ChatColor.GRAY).append(lIlllllIIIIlIl.getDisplayName())));
        }
        ScoreBoardUpdater.clearScoreBoard(lIlllllIIIIlIl);
        Com.showRanking(lIlllllIIIIlIl);
        Options.giveStartingButtons(lIlllllIIIIlIl);
        lIlllllIIIIlIl.getInventory().setHeldItemSlot(lIIIlIllI[1]);
        "".length();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Com.getPlugin(), () -> {
            Com.setHeadColor(lIlllllIIIIlIl, ChatColor.WHITE);
            int lIlllIllIllIll = new PlayerData(lIlllllIIIIlIl).getRank();
            Utils.donarItemsPlayer(lIlllllIIIIlIl, Com.getRankEquipment(lIlllIllIllIll));
            Com.setSuffix(lIlllllIIIIlIl, String.valueOf(new StringBuilder().append(ColorConverter.chatToRaw(ChatColor.GRAY)).append(NBSP).append(ColorConverter.chatToRaw(ChatColor.YELLOW)).append(lIIIIIIII[lIIIlIllI[108]]).append(lIlllIllIllIll)));
        }, 2L);
        Com.playMinicatAnimation(lIlllllIIIIlIl);
    }

    public static ArrayList<ItemStack> getRankEquipment(int lIllllIIIIIlIl) {
        ArrayList<ItemStack> lIllllIIIIIlII = new ArrayList<ItemStack>();
        if (Com.lIIIIlIlIII(lIllllIIIIIlIl, lIIIlIllI[1])) {
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.DIAMOND_HELMET));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.DIAMOND_LEGGINGS));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.DIAMOND_BOOTS));
            return lIllllIIIIIlII;
        }
        if (Com.lIIIIlIlIII(lIllllIIIIIlIl, lIIIlIllI[2])) {
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.GOLD_HELMET));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.GOLD_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.GOLD_LEGGINGS));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.GOLD_BOOTS));
            return lIllllIIIIIlII;
        }
        if (Com.lIIIIlIlIII(lIllllIIIIIlIl, lIIIlIllI[3])) {
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.GOLD_HELMET));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_LEGGINGS));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_BOOTS));
            return lIllllIIIIIlII;
        }
        if (Com.lIIIIlIlIII(lIllllIIIIIlIl, lIIIlIllI[4])) {
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_HELMET));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_LEGGINGS));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_BOOTS));
            return lIllllIIIIIlII;
        }
        if (Com.lIIIIlIlIII(lIllllIIIIIlIl, lIIIlIllI[6])) {
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.IRON_HELMET));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_LEGGINGS));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_BOOTS));
            return lIllllIIIIIlII;
        }
        if (Com.lIIIIlIIlII(lIllllIIIIIlIl, lIIIlIllI[5])) {
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_CHESTPLATE));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_LEGGINGS));
            "".length();
            lIllllIIIIIlII.add(new ItemStack(Material.LEATHER_BOOTS));
            return lIllllIIIIIlII;
        }
        return lIllllIIIIIlII;
    }

    public static String getRankingString(int lIllllIIIlIIIl) {
        if (Com.lIIIIlIIIlI((int)Com.getDataAPI().isInDatalessMode())) {
            return lIIIIIIII[lIIIlIllI[21]];
        }
        ArrayList<String> lIllllIIIlIlII = new ArrayList<String>();
        ArrayList<Integer> lIllllIIIlIIll = Com.getDataAPI().getRanking();
        int lIllllIIIlIIlI = lIllllIIIlIIIl;
        Iterator<Integer> lIllllIIIIllIl = lIllllIIIlIIll.iterator();
        while (Com.lIIIIlIIIlI((int)lIllllIIIIllIl.hasNext())) {
            Integer lIllllIIIlIllI = lIllllIIIIllIl.next();
            PlayerData lIllllIIIllIlI = new PlayerData(lIllllIIIlIllI);
            int lIllllIIIllIIl = lIllllIIIlIIll.indexOf(lIllllIIIlIllI) + lIIIlIllI[1];
            String lIllllIIIllIII = lIIIIIIII[lIIIlIllI[22]];
            String lIllllIIIlIlll = lIIIIIIII[lIIIlIllI[23]];
            lIllllIIIlIlll = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[24]]).append((Object)ChatColor.BLUE));
            if (Com.lIIIIlIIlII(lIllllIIIllIIl, lIIIlIllI[5])) {
                lIllllIIIlIlll = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[25]]).append((Object)ChatColor.YELLOW));
            }
            if (Com.lIIIIlIIlII(lIllllIIIllIIl, lIIIlIllI[3])) {
                lIllllIIIlIlll = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[26]]).append((Object)ChatColor.GREEN));
            }
            if (Com.lIIIIlIIlII(lIllllIIIllIIl, lIIIlIllI[1])) {
                lIllllIIIlIlll = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[27]]).append((Object)ChatColor.AQUA));
            }
            if (Com.lIIIIlIlIII(lIllllIIIllIIl, lIllllIIIlIIll.size())) {
                lIllllIIIlIlll = String.valueOf(new StringBuilder().append(lIIIIIIII[lIIIlIllI[28]]).append((Object)ChatColor.RED));
            }
            lIllllIIIllIII = String.valueOf(new StringBuilder().append(lIllllIIIllIII).append((Object)ChatColor.DARK_RED));
            lIllllIIIllIII = String.valueOf(new StringBuilder().append(lIllllIIIllIII).append(Integer.toString(lIllllIIIllIIl)));
            lIllllIIIllIII = String.valueOf(new StringBuilder().append(lIllllIIIllIII).append(lIIIIIIII[lIIIlIllI[29]]));
            lIllllIIIllIII = String.valueOf(new StringBuilder().append(lIllllIIIllIII).append(lIllllIIIlIlll));
            lIllllIIIllIII = String.valueOf(new StringBuilder().append(lIllllIIIllIII).append(lIllllIIIllIlI.getUserName()));
            "".length();
            lIllllIIIlIlII.add(lIllllIIIllIII);
            if (Com.lIIIIlIIIll(--lIllllIIIlIIlI)) {
                "".length();
                if (((13 ^ 81) & ~(255 ^ 163)) == 0) break;
                return null;
            }
            "".length();
            if ("   ".length() > 0) continue;
            return null;
        }
        return String.join((CharSequence)lIIIIIIII[lIIIlIllI[30]], lIllllIIIlIlII);
    }

    private static boolean lIIIIllIIlI(int n, int n2) {
        return n < n2;
    }

    public static DataAPI getDataAPI() {
        return Com.getPlugin().dataAPI;
    }

    private static boolean lIIIIlIIIll(int n) {
        return n <= 0;
    }

    static {
        Com.lIIIIIlllll();
        Com.lIIIIIIIlII();
        NBSP = (char)lIIIlIllI[109];
        playerHologramMap = new HashMap();
    }

    public static void setHeadColor(Player lIllllIlIIlllI, ChatColor lIllllIlIIllIl) {
        Com.setHeadColor(lIllllIlIIlllI, ColorConverter.chatToRaw(lIllllIlIIllIl));
    }

    public static Boolean isOnLobby(Player lIllllIlIllIlI) {
        return Com.getLobbyWorld().getPlayers().contains((Object)lIllllIlIllIlI);
    }

    public static String getMinicatString() {
        return String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIIIIIII[lIIIlIllI[31]]));
    }
}

