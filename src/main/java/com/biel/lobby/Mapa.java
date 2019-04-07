/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.events.WorldEventBus
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.PlayerInventory
 */
package com.biel.lobby;

import com.biel.BielAPI.events.WorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.MapaContinu;
import com.biel.lobby.mapes.MapaResetejable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.PlayerInventory;

public abstract class Mapa
extends WorldEventBus {
    public /* synthetic */ String NomWorld;
    /* synthetic */ String OriginalMapName;
    public /* synthetic */ lobby plugin;
    private static final /* synthetic */ int[] lIllIlIIl;
    /* synthetic */ int BukkitWorldId;
    protected /* synthetic */ World world;
    private static final /* synthetic */ String[] lIllIIIlI;

    private static boolean lIlIIlIIlIl(int n) {
        return n > 0;
    }

    public Mapa() {
        Mapa lIlIlIIIlIllll;
        lIlIlIIIlIllll.NomWorld = lIllIIIlI[lIllIlIIl[0]];
        lIlIlIIIlIllll.OriginalMapName = lIllIIIlI[lIllIlIIl[1]];
        lIlIlIIIlIllll.plugin = lobby.getPlugin();
        if (Mapa.lIlIIIlllIl((int)lIlIlIIIlIllll.isWorldLoaded().booleanValue())) {
            lIlIlIIIlIllll.setWorld(lIlIlIIIlIllll.getWorld());
        }
    }

    public void sendGlobalSound(Sound lIlIIlllIlIIII, float lIlIIlllIlIIll, float lIlIIlllIlIIlI) {
        Mapa lIlIIlllIlIlIl;
        Iterator lIlIIlllIIllIl = lIlIIlllIlIlIl.world.getPlayers().iterator();
        while (Mapa.lIlIIIlllIl((int)lIlIIlllIIllIl.hasNext())) {
            Player lIlIIlllIlIllI = (Player)lIlIIlllIIllIl.next();
            lIlIIlllIlIllI.playSound(lIlIIlllIlIllI.getLocation(), lIlIIlllIlIIII, lIlIIlllIlIIll, lIlIIlllIlIIlI);
            "".length();
            if (-"   ".length() <= 0) continue;
            return;
        }
    }

    public void sendPlayerMessage(Player lIlIIlllIIIllI, String lIlIIlllIIIlIl) {
        lIlIIlllIIIllI.sendMessage(lIlIIlllIIIlIl);
    }

    protected void onPlayerTeleport(PlayerTeleportEvent lIlIIllllllIII, Player lIlIIlllllllIl, Location lIlIIlllllIllI, Location lIlIIlllllIlIl, PlayerTeleportEvent.TeleportCause lIlIIllllllIlI) {
        Mapa lIlIIllllllIIl;
        super.onPlayerTeleport(lIlIIllllllIII, lIlIIlllllllIl, lIlIIlllllIllI, lIlIIlllllIlIl, lIlIIllllllIlI);
        if (Mapa.lIlIIIlllll((Object)lIlIIlllllIllI.getWorld(), (Object)lIlIIlllllIlIl.getWorld()) && Mapa.lIlIIlIIIII((Object)lIlIIlllllIllI.getWorld(), (Object)lIlIIllllllIIl.getWorld())) {
            lIlIIllllllIIl.Leave(lIlIIlllllllIl);
        }
    }

    public String getMapDisplayName() {
        Mapa lIlIlIIIlIIlII;
        return String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIllIIIlI[lIllIlIIl[4]]).append((Object)ChatColor.AQUA).append(lIlIlIIIlIIlII.getMapName()).append((Object)ChatColor.GOLD).append(lIllIIIlI[lIllIlIIl[5]]).append((Object)ChatColor.GRAY));
    }

    public String getGameDisplayName() {
        Mapa lIlIlIIIlIlIlI;
        return String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIllIIIlI[lIllIlIIl[2]]).append((Object)ChatColor.AQUA).append(lIlIlIIIlIlIlI.getGameName()).append((Object)ChatColor.GOLD).append(lIllIIIlI[lIllIlIIl[3]]).append((Object)ChatColor.GRAY));
    }

    static {
        Mapa.lIlIIIlllII();
        Mapa.lIIlllllIIl();
    }

    private static boolean lIlIIlIIIII(Object object, Object object2) {
        return object == object2;
    }

    public World getWorld() {
        Mapa lIlIIllllIlIII;
        return lIlIIllllIlIII.world;
    }

    private static boolean lIlIIIlllIl(int n) {
        return n != 0;
    }

    private static boolean lIlIIIllllI(Object object) {
        return object != null;
    }

    public abstract String getGameName();

    public void Leave(Player lIlIlIIIIlIIll) {
        Mapa lIlIlIIIIlIlII;
        ArrayList<String> lIlIlIIIIlIIlI = new ArrayList<String>();
        lIlIlIIIIlIlII.customLeave(lIlIlIIIIlIIll, lIlIlIIIIlIIlI);
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIlIIIIlIlII.getGameDisplayName()).append(lIlIlIIIIlIIll.getName()).append((Object)ChatColor.GRAY).append(lIllIIIlI[lIllIlIIl[10]])));
    }

    public void copyDirectory(File lIlIIllIllIIlI, File lIlIIllIllIIIl) throws IOException {
        if (Mapa.lIlIIIlllIl((int)lIlIIllIllIIlI.isDirectory())) {
            String[] lIlIIllIlllIII;
            if (Mapa.lIlIIlIIIIl((int)lIlIIllIllIIIl.exists())) {
                "".length();
                lIlIIllIllIIIl.mkdir();
            }
            String[] lIlIIllIlIllII = lIlIIllIlllIII = lIlIIllIllIIlI.list();
            int lIlIIllIlIlIll = lIlIIllIlIllII.length;
            int lIlIIllIlIlIlI = lIllIlIIl[0];
            while (Mapa.lIlIIlIIlII(lIlIIllIlIlIlI, lIlIIllIlIlIll)) {
                Mapa lIlIIllIllIIll;
                String lIlIIllIlllIIl = lIlIIllIlIllII[lIlIIllIlIlIlI];
                lIlIIllIllIIll.copyDirectory(new File(lIlIIllIllIIlI, lIlIIllIlllIIl), new File(lIlIIllIllIIIl, lIlIIllIlllIIl));
                ++lIlIIllIlIlIlI;
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
            if (null != null) {
                return;
            }
        } else {
            int lIlIIllIllIlII;
            FileInputStream lIlIIllIllIlll = new FileInputStream(lIlIIllIllIIlI);
            FileOutputStream lIlIIllIllIllI = new FileOutputStream(lIlIIllIllIIIl);
            byte[] lIlIIllIllIlIl = new byte[lIllIlIIl[12]];
            while (Mapa.lIlIIlIIlIl(lIlIIllIllIlII = ((InputStream)lIlIIllIllIlll).read(lIlIIllIllIlIl))) {
                ((OutputStream)lIlIIllIllIllI).write(lIlIIllIllIlIl, lIllIlIIl[0], lIlIIllIllIlII);
                "".length();
                if ((160 ^ 137 ^ (94 ^ 115)) != 0) continue;
                return;
            }
            ((InputStream)lIlIIllIllIlll).close();
            ((OutputStream)lIlIIllIllIllI).close();
        }
    }

    public TipusMapa getTipusMapa() {
        Mapa lIlIIlllIIIIll;
        if (Mapa.lIlIIIlllIl(lIlIIlllIIIIll instanceof MapaResetejable)) {
            return TipusMapa.Resetejable;
        }
        if (Mapa.lIlIIIlllIl(lIlIIlllIIIIll instanceof MapaContinu)) {
            return TipusMapa.Continu;
        }
        "".length();
        Bukkit.broadcastMessage((String)lIllIIIlI[lIllIlIIl[11]]);
        return null;
    }

    protected void onPlayerQuit(PlayerQuitEvent lIlIIllllIllll, Player lIlIIllllIlIll) {
        Mapa lIlIIllllIllIl;
        super.onPlayerQuit(lIlIIllllIllll, lIlIIllllIlIll);
        if (Mapa.lIlIIlIIIII((Object)lIlIIllllIlIll.getWorld(), (Object)lIlIIllllIllIl.getWorld())) {
            lIlIIllllIllIl.Leave(lIlIIllllIlIll);
        }
    }

    public void Join(Player lIlIlIIIIllIlI) {
        Mapa lIlIlIIIIllIll;
        "".length();
        lIlIlIIIIllIlI.teleport(lIlIlIIIIllIll.world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        lIlIlIIIIllIlI.setBedSpawnLocation(lIlIlIIIIllIll.world.getSpawnLocation(), lIllIlIIl[1]);
        Iterator<E> lIlIlIIIIllIIl = Bukkit.getOnlinePlayers().iterator();
        while (Mapa.lIlIIIlllIl((int)lIlIlIIIIllIIl.hasNext())) {
            Player lIlIlIIIIllllI = (Player)lIlIlIIIIllIIl.next();
            if (Mapa.lIlIIIlllIl((int)lobby.isOnLobby(lIlIlIIIIllllI).booleanValue())) {
                lIlIlIIIIllllI.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIlIlIIIIllIlI.getName()).append(lIllIIIlI[lIllIlIIl[6]]).append(lIlIlIIIIllIll.getGameName()).append(lIllIIIlI[lIllIlIIl[7]]).append(lIlIlIIIIllIll.NomWorld).append(lIllIIIlI[lIllIlIIl[8]])));
            }
            "".length();
            if (((120 ^ 93) & ~(122 ^ 95)) < "  ".length()) continue;
            return;
        }
        lIlIlIIIIllIlI.getInventory().clear();
        Com.setSuffix(lIlIlIIIIllIlI, lIllIIIlI[lIllIlIIl[9]]);
        Com.setHeadColor(lIlIlIIIIllIlI, ChatColor.GRAY);
        lIlIlIIIIllIll.customJoin(lIlIlIIIIllIlI);
    }

    private static boolean lIlIIIlllll(Object object, Object object2) {
        return object != object2;
    }

    private static String lIIllIlllII(String lIlIIlIllIllIl, String lIlIIlIllIllII) {
        try {
            SecretKeySpec lIlIIlIlllIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIIlIllIllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIIlIllIllll = Cipher.getInstance("Blowfish");
            lIlIIlIllIllll.init(lIllIlIIl[2], lIlIIlIlllIIII);
            return new String(lIlIIlIllIllll.doFinal(Base64.getDecoder().decode(lIlIIlIllIllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIIlIllIlllI) {
            lIlIIlIllIlllI.printStackTrace();
            return null;
        }
    }

    protected abstract void customJoin(Player var1);

    private static String lIIllllIlll(String lIlIIllIIlIIlI, String lIlIIllIIlIIIl) {
        try {
            SecretKeySpec lIlIIllIIlIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIllIIlIIIl.getBytes(StandardCharsets.UTF_8)), lIllIlIIl[8]), "DES");
            Cipher lIlIIllIIlIlII = Cipher.getInstance("DES");
            lIlIIllIIlIlII.init(lIllIlIIl[2], lIlIIllIIlIlIl);
            return new String(lIlIIllIIlIlII.doFinal(Base64.getDecoder().decode(lIlIIllIIlIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIIllIIlIIll) {
            lIlIIllIIlIIll.printStackTrace();
            return null;
        }
    }

    private static void lIlIIIlllII() {
        lIllIlIIl = new int[14];
        Mapa.lIllIlIIl[0] = (115 ^ 84) & ~(14 ^ 41);
        Mapa.lIllIlIIl[1] = " ".length();
        Mapa.lIllIlIIl[2] = "  ".length();
        Mapa.lIllIlIIl[3] = "   ".length();
        Mapa.lIllIlIIl[4] = 109 ^ 105;
        Mapa.lIllIlIIl[5] = 192 ^ 197;
        Mapa.lIllIlIIl[6] = 119 ^ 113;
        Mapa.lIllIlIIl[7] = 12 ^ 120 ^ (75 ^ 56);
        Mapa.lIllIlIIl[8] = 53 ^ 9 ^ (124 ^ 72);
        Mapa.lIllIlIIl[9] = 22 ^ 31;
        Mapa.lIllIlIIl[10] = 52 + 41 - 54 + 128 ^ 151 + 60 - 138 + 100;
        Mapa.lIllIlIIl[11] = 145 ^ 142 ^ (208 ^ 196);
        Mapa.lIllIlIIl[12] = -18604 & 19627;
        Mapa.lIllIlIIl[13] = 24 ^ 118 ^ (224 ^ 130);
    }

    public void sendGlobalMessage(String lIlIIllllIIIIl) {
        Mapa lIlIIllllIIIII;
        Iterator<E> lIlIIlllIllllI = lIlIIllllIIIII.world.getPlayers().iterator();
        while (Mapa.lIlIIIlllIl((int)lIlIIlllIllllI.hasNext())) {
            Player lIlIIllllIIIll = (Player)lIlIIlllIllllI.next();
            lIlIIllllIIIll.sendMessage(lIlIIllllIIIIl);
            "".length();
            if (" ".length() != 0) continue;
            return;
        }
    }

    private static boolean lIlIIlIIIIl(int n) {
        return n == 0;
    }

    protected Boolean isWorldLoaded() {
        boolean bl;
        Mapa lIlIlIIIlIllIl;
        if (Mapa.lIlIIIllllI((Object)Bukkit.getWorld((String)lIlIlIIIlIllIl.NomWorld))) {
            bl = lIllIlIIl[1];
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            bl = lIllIlIIl[0];
        }
        return bl;
    }

    protected abstract void customLeave(Player var1, List<String> var2);

    private static String lIIllIllIll(String lIlIIllIIIIIlI, String lIlIIllIIIIIIl) {
        lIlIIllIIIIIlI = new String(Base64.getDecoder().decode(lIlIIllIIIIIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIIllIIIIIII = new StringBuilder();
        char[] lIlIIlIlllllll = lIlIIllIIIIIIl.toCharArray();
        int lIlIIlIllllllI = lIllIlIIl[0];
        char[] lIlIIlIllllIII = lIlIIllIIIIIlI.toCharArray();
        int lIlIIlIlllIlll = lIlIIlIllllIII.length;
        int lIlIIlIlllIllI = lIllIlIIl[0];
        while (Mapa.lIlIIlIIlII(lIlIIlIlllIllI, lIlIIlIlllIlll)) {
            char lIlIIllIIIIIll = lIlIIlIllllIII[lIlIIlIlllIllI];
            "".length();
            lIlIIllIIIIIII.append((char)(lIlIIllIIIIIll ^ lIlIIlIlllllll[lIlIIlIllllllI % lIlIIlIlllllll.length]));
            ++lIlIIlIllllllI;
            ++lIlIIlIlllIllI;
            "".length();
            if (-" ".length() < 0) continue;
            return null;
        }
        return String.valueOf(lIlIIllIIIIIII);
    }

    private static void lIIlllllIIl() {
        lIllIIIlI = new String[lIllIlIIl[13]];
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[0]] = Mapa.lIIllIllIll("", "XueXM");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[1]] = Mapa.lIIllIlllII("D+JEHXsN9LI=", "ulgAp");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[2]] = Mapa.lIIllIllIll("Ig==", "yonPi");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[3]] = Mapa.lIIllIlllII("v74L1O5Tcb8=", "TvesL");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[4]] = Mapa.lIIllIllIll("Eg==", "IekHX");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[5]] = Mapa.lIIllllIlll("GWIJWkA99IE=", "HYsmX");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[6]] = Mapa.lIIllIlllII("deAXr/n//RtnDAFZUihdNg==", "ePYNQ");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[7]] = Mapa.lIIllIllIll("Z1g=", "GpTKo");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[8]] = Mapa.lIIllIllIll("QQ==", "haume");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[9]] = Mapa.lIIllllIlll("0hG2Hs0u0dM=", "JVOjU");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[10]] = Mapa.lIIllIllIll("SwInYjMJCygmPQULMmI+Cko2IyAfAyIj", "kjFBR");
        Mapa.lIllIIIlI[Mapa.lIllIlIIl[11]] = Mapa.lIIllIllIll("ChhkCgY/FWTCjhRvECFHEyYEMRRHJhogAhMqBikOCS4A", "OtDgg");
    }

    protected void onPlayerChangedWorld(PlayerChangedWorldEvent lIlIlIIIIIIlll, Player lIlIlIIIIIlIIl) {
        Mapa lIlIlIIIIIlIll;
        super.onPlayerChangedWorld(lIlIlIIIIIIlll, lIlIlIIIIIlIIl);
    }

    public String getMapName() {
        Mapa lIlIlIIIlIIlll;
        return lIlIlIIIlIIlll.NomWorld;
    }

    public static void deleteFolder(File lIlIIllIIlllll) {
        File[] lIlIIllIlIIIII = lIlIIllIIlllll.listFiles();
        if (Mapa.lIlIIIllllI(lIlIIllIlIIIII)) {
            File[] lIlIIllIIlllIl = lIlIIllIlIIIII;
            int lIlIIllIIlllII = lIlIIllIIlllIl.length;
            int lIlIIllIIllIll = lIllIlIIl[0];
            while (Mapa.lIlIIlIIlII(lIlIIllIIllIll, lIlIIllIIlllII)) {
                File lIlIIllIlIIIlI = lIlIIllIIlllIl[lIlIIllIIllIll];
                if (Mapa.lIlIIIlllIl((int)lIlIIllIlIIIlI.isDirectory())) {
                    Mapa.deleteFolder(lIlIIllIlIIIlI);
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    "".length();
                    lIlIIllIlIIIlI.delete();
                }
                ++lIlIIllIIllIll;
                "".length();
                if (" ".length() >= ((21 + 103 - 106 + 189 ^ 69 + 121 - 143 + 89) & (" ".length() ^ (206 ^ 136) ^ -" ".length()))) continue;
                return;
            }
        }
        "".length();
        lIlIIllIIlllll.delete();
    }

    private static boolean lIlIIlIIlII(int n, int n2) {
        return n < n2;
    }

    static final class TipusMapa
    extends Enum<TipusMapa> {
        public static final /* synthetic */ /* enum */ TipusMapa Continu;
        public static final /* synthetic */ /* enum */ TipusMapa Resetejable;
        private static final /* synthetic */ int[] lIlIIllll;
        private static final /* synthetic */ String[] lIlIIIlll;
        private static final /* synthetic */ TipusMapa[] $VALUES;

        private static void lIIllIIIlII() {
            lIlIIllll = new int[4];
            TipusMapa.lIlIIllll[0] = (168 ^ 185) & ~(74 ^ 91);
            TipusMapa.lIlIIllll[1] = " ".length();
            TipusMapa.lIlIIllll[2] = "  ".length();
            TipusMapa.lIlIIllll[3] = 81 ^ 89;
        }

        static {
            TipusMapa.lIIllIIIlII();
            TipusMapa.lIIlIlIIlIl();
            Continu = new TipusMapa();
            Resetejable = new TipusMapa();
            TipusMapa[] arrtipusMapa = new TipusMapa[lIlIIllll[2]];
            arrtipusMapa[TipusMapa.lIlIIllll[0]] = Continu;
            arrtipusMapa[TipusMapa.lIlIIllll[1]] = Resetejable;
            $VALUES = arrtipusMapa;
        }

        private static String lIIlIlIIIll(String lIllIlIIIIlllI, String lIllIlIIIIllIl) {
            try {
                SecretKeySpec lIllIlIIIlIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIlIIIIllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIllIlIIIlIIII = Cipher.getInstance("Blowfish");
                lIllIlIIIlIIII.init(lIlIIllll[2], lIllIlIIIlIIIl);
                return new String(lIllIlIIIlIIII.doFinal(Base64.getDecoder().decode(lIllIlIIIIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIllIlIIIIllll) {
                lIllIlIIIIllll.printStackTrace();
                return null;
            }
        }

        private static String lIIlIlIIlII(String lIllIlIIIIIIIl, String lIllIlIIIIIIII) {
            try {
                SecretKeySpec lIllIlIIIIIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlIIIIIIII.getBytes(StandardCharsets.UTF_8)), lIlIIllll[3]), "DES");
                Cipher lIllIlIIIIIIll = Cipher.getInstance("DES");
                lIllIlIIIIIIll.init(lIlIIllll[2], lIllIlIIIIIlII);
                return new String(lIllIlIIIIIIll.doFinal(Base64.getDecoder().decode(lIllIlIIIIIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIllIlIIIIIIlI) {
                lIllIlIIIIIIlI.printStackTrace();
                return null;
            }
        }

        public static TipusMapa valueOf(String lIllIlIIIllllI) {
            return Enum.valueOf(TipusMapa.class, lIllIlIIIllllI);
        }

        public static TipusMapa[] values() {
            return (TipusMapa[])$VALUES.clone();
        }

        private TipusMapa() {
            TipusMapa lIllIlIIIllIII;
        }

        private static void lIIlIlIIlIl() {
            lIlIIIlll = new String[lIlIIllll[2]];
            TipusMapa.lIlIIIlll[TipusMapa.lIlIIllll[0]] = TipusMapa.lIIlIlIIIll("hckWIsigGT4=", "ZzfbM");
            TipusMapa.lIlIIIlll[TipusMapa.lIlIIllll[1]] = TipusMapa.lIIlIlIIlII("pwzsOtD0KVCQ6aAeVDiRCw==", "DPdKr");
        }
    }

}

