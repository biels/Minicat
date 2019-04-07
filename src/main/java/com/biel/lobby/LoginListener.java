/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.nametagedit.plugin.NametagEdit
 *  com.nametagedit.plugin.api.data.Nametag
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.FoodLevelChangeEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.server.ServerListPingEvent
 *  org.bukkit.event.weather.WeatherChangeEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.biel.lobby;

import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes;
import com.biel.lobby.Mapa;
import com.biel.lobby.lobby;
import com.biel.lobby.utilities.Utils;
import com.nametagedit.plugin.NametagEdit;
import com.nametagedit.plugin.api.data.Nametag;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class LoginListener
implements Listener {
    public /* synthetic */ lobby plugin;
    private static final /* synthetic */ String[] llIlllIl;
    private static final /* synthetic */ int[] lIIIIllII;

    @EventHandler
    public void onPlace(BlockPlaceEvent lllIllllIIIlII) {
        Player lllIllllIIIlll;
        if (LoginListener.lllllIIlll((Object)lllIllllIIIlII.getPlayer()) && LoginListener.lllllIlIII((int)lobby.isOnLobby(lllIllllIIIlll = lllIllllIIIlII.getPlayer()).booleanValue())) {
            lllIllllIIIlII.setCancelled(lIIIIllII[1]);
        }
    }

    private static String llIlIIlIlI(String lllIllIlIIlIlI, String lllIllIlIIllll) {
        lllIllIlIIlIlI = new String(Base64.getDecoder().decode(lllIllIlIIlIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIllIlIIllIl = new StringBuilder();
        char[] lllIllIlIIllII = lllIllIlIIllll.toCharArray();
        int lllIllIlIIlIll = lIIIIllII[0];
        char[] lllIllIlIIIlIl = lllIllIlIIlIlI.toCharArray();
        int lllIllIlIIIlII = lllIllIlIIIlIl.length;
        int lllIllIlIIIIll = lIIIIllII[0];
        while (LoginListener.lllllIlllI(lllIllIlIIIIll, lllIllIlIIIlII)) {
            char lllIllIlIlIIll = lllIllIlIIIlIl[lllIllIlIIIIll];
            "".length();
            lllIllIlIIllIl.append((char)(lllIllIlIlIIll ^ lllIllIlIIllII[lllIllIlIIlIll % lllIllIlIIllII.length]));
            ++lllIllIlIIlIll;
            ++lllIllIlIIIIll;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lllIllIlIIllIl);
    }

    private static String llIlIIlIIl(String lllIllIlllIlII, String lllIllIlllIIlI) {
        try {
            SecretKeySpec lllIllIlllIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIllIlllIIlI.getBytes(StandardCharsets.UTF_8)), lIIIIllII[11]), "DES");
            Cipher lllIllIlllIllI = Cipher.getInstance("DES");
            lllIllIlllIllI.init(lIIIIllII[2], lllIllIlllIlll);
            return new String(lllIllIlllIllI.doFinal(Base64.getDecoder().decode(lllIllIlllIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIllIlllIlIl) {
            lllIllIlllIlIl.printStackTrace();
            return null;
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent lllIlllIlllllI) {
        Player lllIllllIIIIII;
        if (LoginListener.lllllIIlll((Object)lllIlllIlllllI.getPlayer()) && LoginListener.lllllIlIII((int)lobby.isOnLobby(lllIllllIIIIII = lllIlllIlllllI.getPlayer()).booleanValue())) {
            lllIlllIlllllI.setCancelled(lIIIIllII[1]);
        }
    }

    private static String llIlIIIllI(String lllIlllIIIIIlI, String lllIllIlllllll) {
        try {
            SecretKeySpec lllIlllIIIIlIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIllIlllllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIlllIIIIlII = Cipher.getInstance("Blowfish");
            lllIlllIIIIlII.init(lIIIIllII[2], lllIlllIIIIlIl);
            return new String(lllIlllIIIIlII.doFinal(Base64.getDecoder().decode(lllIlllIIIIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIlllIIIIIll) {
            lllIlllIIIIIll.printStackTrace();
            return null;
        }
    }

    private static boolean lllllIlIII(int n) {
        return n != 0;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent lllIllllIIlIlI) {
        lllIllllIIlIlI.setCancelled(lIIIIllII[1]);
    }

    static {
        LoginListener.lllllIIIII();
        LoginListener.llIllllIll();
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent lllIlllIlIIlII) {
        String string;
        String lllIlllIlIIIII;
        LoginListener lllIlllIlIIlIl;
        Object object;
        String lllIlllIlIIIll = lllIlllIlIIlII.getMessage();
        Player lllIlllIlIIIlI = lllIlllIlIIlII.getPlayer();
        int lllIlllIlIIllI = lIIIIllII[8];
        while (LoginListener.lllllIlIll(lllIlllIlIIllI)) {
            lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(String.valueOf(new StringBuilder().append(llIlllIl[lIIIIllII[3]]).append(StringUtils.repeat((String)llIlllIl[lIIIIllII[9]], (int)lllIlllIlIIllI))), String.valueOf(new StringBuilder().append(llIlllIl[lIIIIllII[10]]).append(StringUtils.repeat((String)llIlllIl[lIIIIllII[11]], (int)lllIlllIlIIllI))));
            --lllIlllIlIIllI;
            "".length();
            if ("  ".length() != 0) continue;
            return;
        }
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[12]], llIlllIl[lIIIIllII[5]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[13]], llIlllIl[lIIIIllII[14]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[15]], llIlllIl[lIIIIllII[16]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[8]], llIlllIl[lIIIIllII[17]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[18]], llIlllIl[lIIIIllII[19]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[20]], llIlllIl[lIIIIllII[21]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[22]], llIlllIl[lIIIIllII[23]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[24]], llIlllIl[lIIIIllII[25]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[26]], llIlllIl[lIIIIllII[27]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[28]], llIlllIl[lIIIIllII[29]]);
        lllIlllIlIIIll = lllIlllIlIIIll.replaceAll(llIlllIl[lIIIIllII[30]], llIlllIl[lIIIIllII[31]]);
        lllIlllIlIIlII.setMessage(lllIlllIlIIIll);
        if (!(LoginListener.lllllIllII((int)lllIlllIlIIIll.contains(llIlllIl[lIIIIllII[32]])) && LoginListener.lllllIllII((int)lllIlllIlIIIll.contains(llIlllIl[lIIIIllII[33]])) && LoginListener.lllllIllII((int)lllIlllIlIIIll.contains(llIlllIl[lIIIIllII[34]])) && LoginListener.lllllIllII((int)lllIlllIlIIIll.contains(llIlllIl[lIIIIllII[35]])) && LoginListener.lllllIllII((int)lllIlllIlIIIll.contains(llIlllIl[lIIIIllII[36]])) && !LoginListener.lllllIlIII((int)lllIlllIlIIIll.contains(llIlllIl[lIIIIllII[37]])))) {
            if (LoginListener.lllllIlIII((int)Com.isOnLobby(lllIlllIlIIlII.getPlayer()).booleanValue())) {
                if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[38]))) {
                    lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[39]]);
                }
                if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[38]))) {
                    lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[40]]);
                }
                if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[5]))) {
                    lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[41]]);
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
            } else {
                lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[42]]);
                if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[42]))) {
                    lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[43]]);
                }
            }
            if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[3]))) {
                lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[44]]);
            }
            if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[11]))) {
                lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[45]]);
            }
            if (LoginListener.lllllIlIII((int)Utils.Possibilitat(lIIIIllII[6]))) {
                lllIlllIlIIlII.setMessage(llIlllIl[lIIIIllII[46]]);
            }
        }
        Mapa lllIlllIlIIIIl = lllIlllIlIIlIl.plugin.gest.getMapWherePlayerIs(lllIlllIlIIlII.getPlayer());
        if (LoginListener.lllllIlIII((int)lobby.isOnLobby(lllIlllIlIIIlI).booleanValue())) {
            string = String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(llIlllIl[lIIIIllII[47]]).append((Object)ChatColor.AQUA).append(llIlllIl[lIIIIllII[48]]).append((Object)ChatColor.GOLD).append(llIlllIl[lIIIIllII[49]]).append((Object)ChatColor.GRAY));
            "".length();
            if (((9 + 107 - 61 + 84 ^ 39 + 116 - 72 + 100) & ("  ".length() ^ (123 ^ 69) ^ -" ".length())) > 0) {
                return;
            }
        } else {
            string = lllIlllIlIIIII = String.valueOf(new StringBuilder().append(lllIlllIlIIIIl.getMapDisplayName()).append((Object)ChatColor.RESET));
        }
        if (LoginListener.lllllIlIII((int)lobby.isOnLobby(lllIlllIlIIIlI).booleanValue())) {
            object = ChatColor.GRAY;
            "".length();
            if ((8 ^ 12) <= 0) {
                return;
            }
        } else {
            object = NametagEdit.getApi().getNametag(lllIlllIlIIIlI).getPrefix();
        }
        String lllIlllIIlllll = String.valueOf(new StringBuilder().append(object).append(lllIlllIlIIIlI.getDisplayName()));
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lllIlllIlIIIII).append((Object)ChatColor.GRAY).append(lllIlllIIlllll).append((Object)ChatColor.GRAY).append(llIlllIl[lIIIIllII[50]]).append(lllIlllIlIIlII.getMessage())));
        lllIlllIlIIlII.setCancelled(lIIIIllII[1]);
    }

    private static void lllllIIIII() {
        lIIIIllII = new int[52];
        LoginListener.lIIIIllII[0] = (94 ^ 67 ^ (13 ^ 37) & ~(179 ^ 155)) & (39 + 85 - 2 + 20 ^ 1 + 117 - 15 + 44 ^ -" ".length());
        LoginListener.lIIIIllII[1] = " ".length();
        LoginListener.lIIIIllII[2] = "  ".length();
        LoginListener.lIIIIllII[3] = 118 ^ 49 ^ (24 ^ 90);
        LoginListener.lIIIIllII[4] = (205 ^ 157) + (10 + 31 - 4 + 98) - (39 + 106 - 83 + 96) + (96 ^ 51);
        LoginListener.lIIIIllII[5] = 40 ^ 34;
        LoginListener.lIIIIllII[6] = "   ".length();
        LoginListener.lIIIIllII[7] = 82 ^ 86;
        LoginListener.lIIIIllII[8] = 31 ^ 21 ^ (34 ^ 39);
        LoginListener.lIIIIllII[9] = 108 + 94 - 197 + 160 ^ 147 + 47 - 180 + 149;
        LoginListener.lIIIIllII[10] = 160 ^ 192 ^ (88 ^ 63);
        LoginListener.lIIIIllII[11] = 64 ^ 72;
        LoginListener.lIIIIllII[12] = 187 ^ 139 ^ (138 ^ 179);
        LoginListener.lIIIIllII[13] = 11 + 38 - -126 + 4 ^ 108 + 177 - 234 + 133;
        LoginListener.lIIIIllII[14] = 100 ^ 97 ^ (86 ^ 95);
        LoginListener.lIIIIllII[15] = 3 ^ 14;
        LoginListener.lIIIIllII[16] = 0 ^ 14;
        LoginListener.lIIIIllII[17] = 118 + 6 - 78 + 112 ^ 58 + 75 - 113 + 122;
        LoginListener.lIIIIllII[18] = 190 ^ 155 ^ (140 ^ 184);
        LoginListener.lIIIIllII[19] = 151 ^ 133;
        LoginListener.lIIIIllII[20] = 137 + 184 - 195 + 60 ^ 19 + 98 - 83 + 135;
        LoginListener.lIIIIllII[21] = 114 ^ 102;
        LoginListener.lIIIIllII[22] = 124 ^ 105;
        LoginListener.lIIIIllII[23] = 109 + 152 - 58 + 6 ^ 118 + 74 - 137 + 144;
        LoginListener.lIIIIllII[24] = 108 ^ 120 ^ "   ".length();
        LoginListener.lIIIIllII[25] = 56 ^ 10 ^ (14 ^ 36);
        LoginListener.lIIIIllII[26] = 54 + 143 - 195 + 157 ^ 63 + 119 - 60 + 12;
        LoginListener.lIIIIllII[27] = 83 ^ 38 ^ (71 ^ 40);
        LoginListener.lIIIIllII[28] = 4 ^ 31;
        LoginListener.lIIIIllII[29] = 38 ^ 58;
        LoginListener.lIIIIllII[30] = 175 ^ 178;
        LoginListener.lIIIIllII[31] = 192 ^ 148 ^ (28 ^ 86);
        LoginListener.lIIIIllII[32] = 101 ^ 122;
        LoginListener.lIIIIllII[33] = 67 ^ 78 ^ (236 ^ 193);
        LoginListener.lIIIIllII[34] = 186 ^ 155;
        LoginListener.lIIIIllII[35] = 123 ^ 89;
        LoginListener.lIIIIllII[36] = 34 ^ 118 ^ (87 ^ 32);
        LoginListener.lIIIIllII[37] = 16 ^ 52;
        LoginListener.lIIIIllII[38] = 23 ^ 3 ^ (122 ^ 82);
        LoginListener.lIIIIllII[39] = 0 ^ 37;
        LoginListener.lIIIIllII[40] = 96 ^ 70;
        LoginListener.lIIIIllII[41] = 68 ^ 99;
        LoginListener.lIIIIllII[42] = 193 ^ 162 ^ (225 ^ 170);
        LoginListener.lIIIIllII[43] = 66 ^ 107;
        LoginListener.lIIIIllII[44] = 141 ^ 167;
        LoginListener.lIIIIllII[45] = 58 ^ 55 ^ (142 ^ 168);
        LoginListener.lIIIIllII[46] = 153 ^ 181;
        LoginListener.lIIIIllII[47] = 227 ^ 197 ^ (135 ^ 140);
        LoginListener.lIIIIllII[48] = 101 + 73 - 68 + 43 ^ 58 + 23 - -17 + 89;
        LoginListener.lIIIIllII[49] = 205 + 51 - 38 + 18 ^ 50 + 126 - 91 + 110;
        LoginListener.lIIIIllII[50] = 175 ^ 159;
        LoginListener.lIIIIllII[51] = 33 + 12 - 12 + 101 ^ 27 + 133 - 3 + 26;
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent lllIllllIIlllI) {
        lllIllllIIlllI.setCancelled(lIIIIllII[1]);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent lllIlllIIlIIlI) {
        Player lllIlllIIlIlIl;
        if (LoginListener.lllllIlllI(lllIlllIIlIIlI.getTo().getBlockY(), lIIIIllII[38]) && LoginListener.lllllIlIII((int)lobby.isOnLobby(lllIlllIIlIlIl = lllIlllIIlIIlI.getPlayer()).booleanValue())) {
            Com.teleportPlayerToLobby(lllIlllIIlIlIl);
        }
    }

    public LoginListener() {
        LoginListener lllIllllIlllIl;
        lllIllllIlllIl.plugin = lobby.getPlugin();
        lllIllllIlllIl.plugin.getServer().getPluginManager().registerEvents((Listener)lllIllllIlllIl, (Plugin)lllIllllIlllIl.plugin);
        lllIllllIlllIl.plugin.getLogger().info(llIlllIl[lIIIIllII[0]]);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent lllIllllIlIlII) {
        Player lllIllllIlIllI = lllIllllIlIlII.getPlayer();
        String lllIllllIlIlIl = lllIllllIlIllI.getName();
        Com.getDataAPI().registerNewPlayer(lllIllllIlIllI);
        Com.teleportPlayerToLobby(lllIllllIlIllI);
        lllIllllIlIllI.setCanPickupItems(lIIIIllII[1]);
    }

    private static boolean lllllIlIlI(int n, int n2) {
        return n <= n2;
    }

    private static boolean lllllIlllI(int n, int n2) {
        return n < n2;
    }

    private static boolean lllllIllII(int n) {
        return n == 0;
    }

    private static boolean llllllIIII(Object object, Object object2) {
        return object == object2;
    }

    private static boolean lllllIIlll(Object object) {
        return object != null;
    }

    @EventHandler
    public void onPing(ServerListPingEvent lllIlllIllIIIl) {
        int lllIlllIllIIlI = lobby.getPlugin().gest.getAllInstances().size();
        if (LoginListener.lllllIlIII((int)Com.getDataAPI().isInDatalessMode())) {
            lllIlllIllIIIl.setMotd(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(llIlllIl[lIIIIllII[1]]).append((Object)ChatColor.RED).append(llIlllIl[lIIIIllII[2]])));
            return;
        }
        try {
            int lllIlllIllIlll = lIIIIllII[3];
            while (LoginListener.lllllIlIlI(Com.getRankingString(lllIlllIllIlll + lIIIIllII[1]).length(), lIIIIllII[4]) && LoginListener.lllllIlIlI(lllIlllIllIlll, lIIIIllII[5])) {
                ++lllIlllIllIlll;
                "".length();
                if ((26 ^ 30) == (97 ^ 101)) continue;
                return;
            }
            String lllIlllIllIllI = Com.getRankingString(lllIlllIllIlll);
            lllIlllIllIIIl.setMotd(lllIlllIllIllI);
            "".length();
        }
        catch (Exception lllIlllIllIlIl) {
            System.out.println(String.valueOf(new StringBuilder().append(llIlllIl[lIIIIllII[6]]).append(lllIlllIllIlIl.toString())));
            lllIlllIllIIIl.setMotd(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llIlllIl[lIIIIllII[7]])));
            lllIlllIllIlIl.printStackTrace();
        }
        if (((28 ^ 27) & ~(24 ^ 31)) > "  ".length()) {
            return;
        }
    }

    private static boolean lllllIlIll(int n) {
        return n > 0;
    }

    private static void llIllllIll() {
        llIlllIl = new String[lIIIIllII[51]];
        LoginListener.llIlllIl[LoginListener.lIIIIllII[0]] = LoginListener.llIlIIIllI("o6udY0TuCnslwrPKME9j/qjB7eFaO/Ok", "RSCYS");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[1]] = LoginListener.llIlIIlIIl("/UAOihzX8dXnQy0NyZ16UA==", "LqbTW");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[2]] = LoginListener.llIlIIlIlI("AjY9J2c0PTw0Gg==", "YRXQG");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[6]] = LoginListener.llIlIIlIlI("LxQRIzZKAw1sJwsUESshCxRDKShKFMKDIjUfDw0rfko=", "jfcLD");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[7]] = LoginListener.llIlIIlIlI("GwIXNis/AgswbirCgws1OzENAmpgdg==", "XceDN");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[3]] = LoginListener.llIlIIlIlI("LSk=", "AHFtN");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[9]] = LoginListener.llIlIIlIlI("Ig==", "EDGvT");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[10]] = LoginListener.llIlIIlIIl("l4lmxghRTPQ=", "fdOOl");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[11]] = LoginListener.llIlIIlIIl("qMw+EueQtMw=", "AvmIE");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[12]] = LoginListener.llIlIIlIIl("OLBf74pVSNWTqT3Fyf+F2w==", "ndVQz");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[5]] = LoginListener.llIlIIIllI("4BddIeNy+wGLomj/sAd4Ng==", "JbgWy");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[13]] = LoginListener.llIlIIIllI("HdhVvgdFytl2cl17fiStgw==", "uAQGB");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[14]] = LoginListener.llIlIIlIIl("+2dZ//gjZUE=", "arNJQ");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[15]] = LoginListener.llIlIIlIIl("dx/hJBWiGzM=", "lkOLG");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[16]] = LoginListener.llIlIIlIlI("JUIaXi1sBUYI", "BlhpH");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[8]] = LoginListener.llIlIIIllI("UU0GVB/s52w=", "ZeEsm");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[17]] = LoginListener.llIlIIlIlI("H0Q9cxxYDW8r", "xdOSy");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[18]] = LoginListener.llIlIIIllI("g4S9S89OQZE=", "uMyiG");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[19]] = LoginListener.llIlIIlIIl("tZ6xApGselk=", "tTwqZ");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[20]] = LoginListener.llIlIIIllI("vn9aYuyf6vo=", "CFyvg");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[21]] = LoginListener.llIlIIlIIl("eJEhhF2cQWM=", "ObRvB");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[22]] = LoginListener.llIlIIlIlI("Bi0bKA==", "vDuOF");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[23]] = LoginListener.llIlIIlIlI("Fg4nA0oWCCcD", "fgIdj");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[24]] = LoginListener.llIlIIlIIl("JO1YTWyCDv8=", "pjgyt");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[25]] = LoginListener.llIlIIlIlI("ABgVMAUHCgI=", "ekvQw");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[26]] = LoginListener.llIlIIlIIl("ONworVGwLFM=", "wxCSp");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[27]] = LoginListener.llIlIIlIIl("QqJDsIAoCFGQTkS2X4WeeIqTtR5zxaWJXXYYZr6NZnw=", "lPSYo");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[28]] = LoginListener.llIlIIIllI("F7qHvRs2NyY=", "HeJGr");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[29]] = LoginListener.llIlIIlIIl("8Z9Qpbh7nJaJ82l/6eiByO7cmYOHj7+cDUBCbm+jQJs=", "xzEME");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[30]] = LoginListener.llIlIIIllI("fDy+ZD/hr7I=", "cixEG");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[31]] = LoginListener.llIlIIIllI("iK1DWCBJU75lgTn4DSyOM8EgAtc0g6E3d55EOvxt+aY=", "cxFZr");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[32]] = LoginListener.llIlIIlIlI("HyAHKgA=", "vNqOs");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[33]] = LoginListener.llIlIIlIlI("JCMUIC8=", "TLxLN");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[34]] = LoginListener.llIlIIlIIl("Z7oOSvHiQiE=", "kvCyZ");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[35]] = LoginListener.llIlIIlIIl("UBi/R53mtQY=", "HDwDA");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[36]] = LoginListener.llIlIIIllI("senmMQp9Qrc=", "pckui");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[37]] = LoginListener.llIlIIIllI("LAdqjR7oWzU=", "pMFMc");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[39]] = LoginListener.llIlIIlIlI("IgYlD2kgFj4XLCFTIcKIOnMUOQAgclI=", "SsLaI");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[40]] = LoginListener.llIlIIlIIl("TXYsU7WxScrnGR8wnhUe8Xc6smLA/7Cq", "TtKRD");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[41]] = LoginListener.llIlIIlIlI("HAkdUDIDFUgdPgMKBwIkTkc=", "ofhpW");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[42]] = LoginListener.llIlIIIllI("J0zi9gR0Pm9YGclZf3wuspG0m7BQqI9k", "CqxSe");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[43]] = LoginListener.llIlIIIllI("haIXJYR0K5Whpw41apUgYx2vwv7oMyA+", "gotZx");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[44]] = LoginListener.llIlIIlIlI("MzsJAEJ4cw0DDSAyTAxMMjoeQ0J4cwEEADo8Hk0PNz8AAkwuFw==", "VSlml");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[45]] = LoginListener.llIlIIlIlI("AjVxMDgFMT/CtDhINXEjMhowIzZ3DTgicycJJDQhJER0Pzx3DTlxNTIddDYyPhoxcTA2G3Q7Mj0J", "hTQSW");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[46]] = LoginListener.llIlIIlIIl("BMRyOaGybB4Vi42Yzvk2XiKLSq2YngmR", "JesFG");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[47]] = LoginListener.llIlIIlIlI("Lw==", "tTzjV");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[48]] = LoginListener.llIlIIIllI("vVKgASFCqqk=", "wpxdH");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[49]] = LoginListener.llIlIIlIlI("OFk=", "eyZNL");
        LoginListener.llIlllIl[LoginListener.lIIIIllII[50]] = LoginListener.llIlIIlIIl("tsZLvXRdA4k=", "pFRnm");
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent lllIlllIIIllII) {
        Player lllIlllIIIlllI;
        if (LoginListener.lllllIlIII(lllIlllIIIllII.getEntity() instanceof Player) && LoginListener.lllllIlIII((int)lobby.isOnLobby(lllIlllIIIlllI = (Player)lllIlllIIIllII.getEntity()).booleanValue())) {
            if (LoginListener.llllllIIII((Object)lllIlllIIIllII.getCause(), (Object)EntityDamageEvent.DamageCause.VOID)) {
                Com.teleportPlayerToLobby(lllIlllIIIlllI);
            }
            lllIlllIIIllII.setCancelled(lIIIIllII[1]);
        }
    }
}

