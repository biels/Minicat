/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.biel.lobby;

import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes;
import com.biel.lobby.LoginListener;
import com.biel.lobby.Mapa;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Options;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.PlayerData;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class lobby
extends JavaPlugin {
    private static final /* synthetic */ String[] lIIIIlIl;
    private static final /* synthetic */ int[] lIIlIllI;
    public /* synthetic */ DataAPI dataAPI;
    /* synthetic */ boolean ranked;
    public /* synthetic */ GestorMapes gest;

    private static String lllIlllII(String lIlIlllllIIllII, String lIlIlllllIIllIl) {
        try {
            SecretKeySpec lIlIlllllIlIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlllllIIllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIlllllIlIIII = Cipher.getInstance("Blowfish");
            lIlIlllllIlIIII.init(lIIlIllI[2], lIlIlllllIlIIIl);
            return new String(lIlIlllllIlIIII.doFinal(Base64.getDecoder().decode(lIlIlllllIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlllllIIllll) {
            lIlIlllllIIllll.printStackTrace();
            return null;
        }
    }

    public static World getLobbyWorld() {
        return (World)Bukkit.getWorlds().get(lIIlIllI[1]);
    }

    public static lobby getPlugin() {
        Plugin lIlIlllllllIlIl = Bukkit.getServer().getPluginManager().getPlugin(lIIIIlIl[lIIlIllI[29]]);
        if (!lobby.lIIIllIIII((Object)lIlIlllllllIlIl) || lobby.lIIIlIlIll(lIlIlllllllIlIl instanceof lobby)) {
            return null;
        }
        return (lobby)lIlIlllllllIlIl;
    }

    static {
        lobby.lIIIlIlIII();
        lobby.lIIIIlllIl();
    }

    private static boolean lIIIlIllll(Object object) {
        return object == null;
    }

    public lobby() {
        lobby lIllIIIIIIllIlI;
        lIllIIIIIIllIlI.ranked = lIIlIllI[0];
    }

    private static boolean lIIIlIllIl(int n, int n2) {
        return n == n2;
    }

    public void onDisable() {
    }

    private static boolean lIIIlIlIll(int n) {
        return n == 0;
    }

    private static boolean lIIIlIlIlI(int n) {
        return n != 0;
    }

    private static void lIIIIlllIl() {
        lIIIIlIl = new String[lIIlIllI[30]];
        lobby.lIIIIlIl[lobby.lIIlIllI[1]] = lobby.lllIlllII("m78MR+arIsY=", "tUfmS");
        lobby.lIIIIlIl[lobby.lIIlIllI[0]] = lobby.llllIIIII("cjGgYuV7s14=", "mHNVp");
        lobby.lIIIIlIl[lobby.lIIlIllI[2]] = lobby.llllIIIII("Ah7Ax48n7pe6pKllGYFDGJ45nJ6oclgg", "EhOFx");
        lobby.lIIIIlIl[lobby.lIIlIllI[3]] = lobby.llllIIIII("CnVccjq8aM5diThJ9uO6RQ==", "sberd");
        lobby.lIIIIlIl[lobby.lIIlIllI[4]] = lobby.llllIIIII("WfD8Kky9hz6DSfyXT/pf0Q==", "neBQW");
        lobby.lIIIIlIl[lobby.lIIlIllI[5]] = lobby.lIIIIIlIll("GA==", "yUxWR");
        lobby.lIIIIlIl[lobby.lIIlIllI[6]] = lobby.lIIIIIlIll("Fw==", "xEGFI");
        lobby.lIIIIlIl[lobby.lIIlIllI[7]] = lobby.lIIIIIlIll("Jg==", "JHczj");
        lobby.lIIIIlIl[lobby.lIIlIllI[8]] = lobby.lIIIIIlIll("Dw==", "OWmvS");
        lobby.lIIIIlIl[lobby.lIIlIllI[9]] = lobby.lllIlllII("dyHk8+rBen6zZMSTFCMyxF3ZwyekpnpFht8t90X6C+9NytexSGjOH/46s9Su9y59Im8BSA6UNt8=", "aCCpf");
        lobby.lIIIIlIl[lobby.lIIlIllI[10]] = lobby.lIIIIIlIll("LTVOAjlDLhwIKAJ6CwtqCS8JBi4MKEA=", "cZngJ");
        lobby.lIIIIlIl[lobby.lIIlIllI[11]] = lobby.lllIlllII("9j9QZd7C7iHin71Y9GP/Vk1oUDhRj3gg7xomVKdTdETzOO5KEr9JSg==", "weNTC");
        lobby.lIIIIlIl[lobby.lIIlIllI[12]] = lobby.lIIIIIlIll("PA==", "LeidP");
        lobby.lIIIIlIl[lobby.lIIlIllI[13]] = lobby.lIIIIIlIll("am4=", "PNyBb");
        lobby.lIIIIlIl[lobby.lIIlIllI[14]] = lobby.llllIIIII("hQJH7E4PVG4=", "iHUdd");
        lobby.lIIIIlIl[lobby.lIIlIllI[15]] = lobby.lIIIIIlIll("AA==", "ebRct");
        lobby.lIIIIlIl[lobby.lIIlIllI[16]] = lobby.llllIIIII("dlnSAg9EUPY=", "WOYIH");
        lobby.lIIIIlIl[lobby.lIIlIllI[17]] = lobby.llllIIIII("42w/2XF/2Uw=", "YusXR");
        lobby.lIIIIlIl[lobby.lIIlIllI[19]] = lobby.lllIlllII("HxM5Gp9mob4USiOhDh++EhfAxdQIEuZY5fqQF3Bp674ycWCEaOxBd4EuX4lr/vz4", "Oxzpl");
        lobby.lIIIIlIl[lobby.lIIlIllI[20]] = lobby.llllIIIII("dRzvE/aJBPw=", "xqdlR");
        lobby.lIIIIlIl[lobby.lIIlIllI[21]] = lobby.llllIIIII("7HEWJpJpfPY=", "DAMwU");
        lobby.lIIIIlIl[lobby.lIIlIllI[22]] = lobby.lIIIIIlIll("MSIa", "EMjqj");
        lobby.lIIIIlIl[lobby.lIIlIllI[23]] = lobby.llllIIIII("GRTzqahTMtI=", "QMJlL");
        lobby.lIIIIlIl[lobby.lIIlIllI[24]] = lobby.lIIIIIlIll("OAEjJQ==", "HsLBY");
        lobby.lIIIIlIl[lobby.lIIlIllI[25]] = lobby.lIIIIIlIll("FSUoMwTCrCRnMQUxPio1An93", "EWGTv");
        lobby.lIIIIlIl[lobby.lIIlIllI[26]] = lobby.lIIIIIlIll("Yw==", "FVEEB");
        lobby.lIIIIlIl[lobby.lIIlIllI[27]] = lobby.llllIIIII("YK5jJxUprIDzsB8O3O0k2L6DFronAKFr+RuRuqG+dbXH3ONY4bLQPg==", "RbKxy");
        lobby.lIIIIlIl[lobby.lIIlIllI[28]] = lobby.llllIIIII("YpQjLqJ4ok/A2cLCehoPWfq2dn4K4RKElzqgpy1vdmZ+kXr9JaQvUkgqVCIX4zlW", "igACb");
        lobby.lIIIIlIl[lobby.lIIlIllI[29]] = lobby.lllIlllII("q9XcLGl9DUw=", "AGUBB");
    }

    private static void lIIIlIlIII() {
        lIIlIllI = new int[31];
        lobby.lIIlIllI[0] = " ".length();
        lobby.lIIlIllI[1] = (43 + 43 - 31 + 112 ^ 94 + 27 - 108 + 173) & (79 + 137 - 145 + 99 ^ 165 + 51 - 81 + 48 ^ -" ".length());
        lobby.lIIlIllI[2] = "  ".length();
        lobby.lIIlIllI[3] = "   ".length();
        lobby.lIIlIllI[4] = 97 + 30 - 7 + 73 ^ 138 + 180 - 267 + 146;
        lobby.lIIlIllI[5] = 52 ^ 45 ^ (42 ^ 54);
        lobby.lIIlIllI[6] = (213 ^ 155) & ~(98 ^ 44) ^ (1 ^ 7);
        lobby.lIIlIllI[7] = 155 ^ 156;
        lobby.lIIlIllI[8] = 78 + 56 - -43 + 12 ^ 136 + 10 - -17 + 18;
        lobby.lIIlIllI[9] = 43 ^ 34;
        lobby.lIIlIllI[10] = 128 + 78 - 89 + 19 ^ 7 + 39 - -31 + 53;
        lobby.lIIlIllI[11] = 19 ^ 24;
        lobby.lIIlIllI[12] = 80 ^ 92;
        lobby.lIIlIllI[13] = 64 ^ 110 ^ (228 ^ 199);
        lobby.lIIlIllI[14] = 189 ^ 179;
        lobby.lIIlIllI[15] = 4 ^ 11;
        lobby.lIIlIllI[16] = 116 + 55 - 70 + 34 ^ 136 + 147 - 207 + 75;
        lobby.lIIlIllI[17] = 90 ^ 93 ^ (139 ^ 157);
        lobby.lIIlIllI[18] = -" ".length();
        lobby.lIIlIllI[19] = 210 ^ 174 ^ (193 ^ 175);
        lobby.lIIlIllI[20] = 55 + 84 - 52 + 53 ^ 93 + 135 - 94 + 25;
        lobby.lIIlIllI[21] = 57 ^ 45;
        lobby.lIIlIllI[22] = 15 ^ 26;
        lobby.lIIlIllI[23] = 2 ^ 20;
        lobby.lIIlIllI[24] = 36 ^ 51;
        lobby.lIIlIllI[25] = "   ".length() ^ (142 ^ 149);
        lobby.lIIlIllI[26] = 252 ^ 180 ^ (235 ^ 186);
        lobby.lIIlIllI[27] = 49 + 65 - -37 + 2 ^ 116 + 65 - 67 + 17;
        lobby.lIIlIllI[28] = 45 ^ 23 ^ (136 ^ 169);
        lobby.lIIlIllI[29] = 173 ^ 170 ^ (44 ^ 55);
        lobby.lIIlIllI[30] = 51 ^ 46;
    }

    private static boolean lIIIllIIII(Object object) {
        return object != null;
    }

    public static Boolean isOnLobby(Player lIlIlllllllIIlI) {
        return lobby.getLobbyWorld().getPlayers().contains((Object)lIlIlllllllIIlI);
    }

    private static boolean lIIIllIllI(int n, int n2) {
        return n < n2;
    }

    public boolean onCommand(CommandSender lIllIIIIIIIIIlI, Command lIllIIIIIIIIIIl, String lIllIIIIIIIIIII, String[] lIlIllllllllIlI) {
        Mapa lIllIIIIIIIlIIl;
        Mapa lIllIIIIIIIIlll;
        lobby lIlIlllllllllIl;
        Player lIlIllllllllllI = (Player)lIllIIIIIIIIIlI;
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[1]]))) {
            lIlIlllllllllIl.gest.ObrirMenuMapes(lIlIllllllllllI);
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[0]]))) {
            String string;
            int n;
            if (lobby.lIIIlIlIll((int)lIlIlllllllllIl.ranked)) {
                n = lIIlIllI[0];
                "".length();
                if ("  ".length() <= -" ".length()) {
                    return (boolean)((189 + 54 - 106 + 75 ^ 23 + 101 - -15 + 14) & (130 + 9 - -93 + 9 ^ 76 + 180 - 223 + 155 ^ -" ".length()));
                }
            } else {
                lIlIlllllllllIl.ranked = lIIlIllI[1];
                n = lIlIlllllllllIl.ranked ? 1 : 0;
            }
            if (lobby.lIIIlIlIlI((int)lIlIlllllllllIl.ranked)) {
                string = lIIIIlIl[lIIlIllI[3]];
                "".length();
                if (((120 ^ 51) & ~(4 ^ 79)) != 0) {
                    return (boolean)((121 ^ 112) & ~(201 ^ 192));
                }
            } else {
                string = lIIIIlIl[lIIlIllI[4]];
            }
            "".length();
            Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIIlIl[lIIlIllI[2]]).append(string)));
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[5]]))) {
            lIlIlllllllllIl.gest.openAllGamesMenu(lIlIllllllllllI);
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[6]]))) {
            Options.giveCommonOptionsMenu(lIlIllllllllllI);
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[7]]))) {
            if (lobby.lIIIlIllIl(lIlIllllllllIlI.length, lIIlIllI[0])) {
                if (lobby.lIIIlIlllI(lIlIllllllllIlI[lIIlIllI[1]], lIIIIlIl[lIIlIllI[8]]) && lobby.lIIIlIlIlI((int)lIlIllllllllllI.isOp())) {
                    Bukkit.getOnlinePlayers().forEach(Com::teleportPlayerToLobby);
                    "".length();
                    Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIIIIlIl[lIIlIllI[9]])));
                    return lIIlIllI[0];
                }
                Player lIllIIIIIIIlllI = Bukkit.getPlayer((String)lIlIllllllllIlI[lIIlIllI[1]]);
                if (lobby.lIIIlIllll((Object)lIllIIIIIIIlllI)) {
                    lIlIllllllllllI.sendMessage(lIIIIlIl[lIIlIllI[10]]);
                    return lIIlIllI[1];
                }
                if (lobby.lIIIlIlIlI((int)lIlIllllllllllI.isOp())) {
                    Com.teleportPlayerToLobby(lIllIIIIIIIlllI);
                    lIllIIIIIIIlllI.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIIIIlIl[lIIlIllI[11]]).append(lIlIllllllllllI.getName())));
                }
            }
            if (lobby.lIIIlIlIll(lIlIllllllllIlI.length)) {
                Com.teleportPlayerToLobby(lIlIllllllllllI);
            }
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[12]]))) {
            GestorPropietats lIllIIIIIIIlIll = Utils.getpMapaFromWorld(lIlIllllllllllI.getWorld());
            if (lobby.lIIIlIlIlI((int)lIlIllllllllllI.isOp())) {
                String lIllIIIIIIIllII;
                if (lobby.lIIIlIllIl(lIlIllllllllIlI.length, lIIlIllI[2])) {
                    lIllIIIIIIIlIll.EstablirPropietat(lIlIllllllllIlI[lIIlIllI[1]], lIlIllllllllIlI[lIIlIllI[0]]);
                }
                if (lobby.lIIIlIllIl(lIlIllllllllIlI.length, lIIlIllI[0])) {
                    Location lIllIIIIIIIllIl = lIlIllllllllllI.getLocation().getBlock().getLocation();
                    lIllIIIIIIIllIl.setY(lIllIIIIIIIllIl.getY() - 1.0);
                    lIllIIIIIIIllIl.setZ(lIllIIIIIIIllIl.getZ());
                    lIllIIIIIIIllIl.setX(lIllIIIIIIIllIl.getX());
                    lIllIIIIIIIlIll.EstablirLocation(lIlIllllllllIlI[lIIlIllI[1]], lIllIIIIIIIllIl);
                }
                if (lobby.lIIIllIIII(lIllIIIIIIIllII = lIllIIIIIIIlIll.ObtenirPropietat(lIlIllllllllIlI[lIIlIllI[1]]))) {
                    "".length();
                    Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIllllllllllI.getWorld().getName()).append(lIIIIlIl[lIIlIllI[13]]).append((Object)ChatColor.YELLOW).append(lIlIllllllllIlI[lIIlIllI[1]]).append(lIIIIlIl[lIIlIllI[14]]).append((Object)ChatColor.GREEN).append(lIllIIIIIIIllII)));
                    return lIIlIllI[0];
                }
            }
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[15]])) && lobby.lIIIlIlIlI((int)lIlIllllllllllI.isOp()) && lobby.lIIIlIlIlI((lIllIIIIIIIlIIl = lIlIlllllllllIl.gest.getMapWherePlayerIs(lIlIllllllllllI)) instanceof MapaResetejable)) {
            boolean bl;
            MapaResetejable lIllIIIIIIIlIlI = (MapaResetejable)lIllIIIIIIIlIIl;
            if (lobby.lIIIlIlIll((int)lIllIIIIIIIlIlI.getEditMode().booleanValue())) {
                bl = lIIlIllI[0];
                "".length();
                if (-" ".length() < -" ".length()) {
                    return (boolean)((92 ^ 38 ^ (143 ^ 178)) & (162 ^ 133 ^ (236 ^ 140) ^ -" ".length()));
                }
            } else {
                bl = lIIlIllI[1];
            }
            lIllIIIIIIIlIlI.setEditMode(bl);
            if (lobby.lIIIlIlIlI((int)lIllIIIIIIIlIlI.getEditMode().booleanValue())) {
                lIlIllllllllllI.setGameMode(GameMode.CREATIVE);
            }
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[16]])) && lobby.lIIIlIlIlI((int)lIlIllllllllllI.isOp()) && lobby.lIIIlIlIlI((lIllIIIIIIIIlll = lIlIlllllllllIl.gest.getMapWherePlayerIs(lIlIllllllllllI)) instanceof MapaResetejable)) {
            MapaResetejable lIllIIIIIIIlIII = (MapaResetejable)lIllIIIIIIIIlll;
            lIllIIIIIIIlIII.save();
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[17]]))) {
            PlayerData lIllIIIIIIIIllI = new PlayerData(lIlIllllllllllI.getName());
            if (lobby.lIIIlIllIl(lIllIIIIIIIIllI.getRank(), lIIlIllI[18])) {
                lIlIllllllllllI.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIIIIlIl[lIIlIllI[19]])));
                return lIIlIllI[0];
            }
            lIlIllllllllllI.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_AQUA).append(lIIIIlIl[lIIlIllI[20]]).append((Object)ChatColor.WHITE).append(Math.round(lIllIIIIIIIIllI.getElo())).append((Object)ChatColor.YELLOW).append(lIIIIlIl[lIIlIllI[21]]).append(lIllIIIIIIIIllI.getRank())));
            return lIIlIllI[0];
        }
        if (!lobby.lIIIlIlIll((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[22]])) || lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[23]]))) {
            Com.displayRanking(lIlIllllllllllI);
            return lIIlIllI[0];
        }
        if (lobby.lIIIlIlIlI((int)lIllIIIIIIIIIIl.getName().equalsIgnoreCase(lIIIIlIl[lIIlIllI[24]]))) {
            Mapa lIllIIIIIIIIlII = Com.getGest().getMapWherePlayerIs(lIlIllllllllllI);
            if (lobby.lIIIllIIII((Object)lIllIIIIIIIIlII)) {
                if (lobby.lIIIlIlIlI(lIllIIIIIIIIlII instanceof Joc)) {
                    Joc lIllIIIIIIIIlIl = (Joc)lIllIIIIIIIIlII;
                    lIlIllllllllllI.sendMessage(String.valueOf(new StringBuilder().append(lIIIIlIl[lIIlIllI[25]]).append(Math.round(lIllIIIIIIIIlIl.getGameProgressETA() * 10000.0) / 100L).append(lIIIIlIl[lIIlIllI[26]])));
                    "".length();
                    if ((9 ^ 13) == 0) {
                        return (boolean)((228 ^ 196) & ~(184 ^ 152));
                    }
                } else {
                    lIlIllllllllllI.sendMessage(lIIIIlIl[lIIlIllI[27]]);
                    "".length();
                    if (-" ".length() == (104 + 97 - 113 + 73 ^ 25 + 11 - 14 + 143)) {
                        return (boolean)((52 + 76 - -8 + 34 ^ 55 + 62 - -40 + 20) & (195 ^ 169 ^ (127 ^ 14) ^ -" ".length()));
                    }
                }
            } else {
                lIlIllllllllllI.sendMessage(lIIIIlIl[lIIlIllI[28]]);
            }
            return lIIlIllI[0];
        }
        return lIIlIllI[1];
    }

    private static String llllIIIII(String lIlIlllllIIIIIl, String lIlIllllIlllllI) {
        try {
            SecretKeySpec lIlIlllllIIIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIllllIlllllI.getBytes(StandardCharsets.UTF_8)), lIIlIllI[8]), "DES");
            Cipher lIlIlllllIIIIll = Cipher.getInstance("DES");
            lIlIlllllIIIIll.init(lIIlIllI[2], lIlIlllllIIIlII);
            return new String(lIlIlllllIIIIll.doFinal(Base64.getDecoder().decode(lIlIlllllIIIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlllllIIIIlI) {
            lIlIlllllIIIIlI.printStackTrace();
            return null;
        }
    }

    private static boolean lIIIlIlllI(Object object, Object object2) {
        return object == object2;
    }

    public boolean isInRankedMode() {
        lobby lIlIllllllIllll;
        return lIlIllllllIllll.ranked;
    }

    private static String lIIIIIlIll(String lIlIllllllIIIll, String lIlIllllllIIIlI) {
        lIlIllllllIIIll = new String(Base64.getDecoder().decode(lIlIllllllIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIllllllIIIIl = new StringBuilder();
        char[] lIlIllllllIIIII = lIlIllllllIIIlI.toCharArray();
        int lIlIlllllIlllll = lIIlIllI[1];
        char[] lIlIlllllIllIIl = lIlIllllllIIIll.toCharArray();
        int lIlIlllllIllIII = lIlIlllllIllIIl.length;
        int lIlIlllllIlIlll = lIIlIllI[1];
        while (lobby.lIIIllIllI(lIlIlllllIlIlll, lIlIlllllIllIII)) {
            char lIlIllllllIIlII = lIlIlllllIllIIl[lIlIlllllIlIlll];
            "".length();
            lIlIllllllIIIIl.append((char)(lIlIllllllIIlII ^ lIlIllllllIIIII[lIlIlllllIlllll % lIlIllllllIIIII.length]));
            ++lIlIlllllIlllll;
            ++lIlIlllllIlIlll;
            "".length();
            if (-" ".length() < (118 ^ 114)) continue;
            return null;
        }
        return String.valueOf(lIlIllllllIIIIl);
    }

    public void onEnable() {
        "".length();
        new LoginListener();
        lobby.getLobbyWorld().setAutoSave(lIIlIllI[0]);
        lIllIIIIIIlIlll.gest = new GestorMapes();
        lIllIIIIIIlIlll.dataAPI = new DataAPI();
    }
}

