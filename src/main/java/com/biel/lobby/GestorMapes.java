/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.IconMenu
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEvent
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEventHandler
 *  com.biel.BielAPI.Utils.Pair
 *  com.connorlinfoot.bountifulapi.BountifulAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.Wool
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.biel.lobby;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.Pair;
import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.mapes.jocs.Arena4;
import com.biel.lobby.mapes.jocs.ArenaAllvAll;
import com.biel.lobby.mapes.jocs.BaseLunar;
import com.biel.lobby.mapes.jocs.BedWars;
import com.biel.lobby.mapes.jocs.BoletumDTC;
import com.biel.lobby.mapes.jocs.Dominion;
import com.biel.lobby.mapes.jocs.InkWars;
import com.biel.lobby.mapes.jocs.KingSkeletonChallenge;
import com.biel.lobby.mapes.jocs.OneInTheChamber;
import com.biel.lobby.mapes.jocs.Parkour;
import com.biel.lobby.mapes.jocs.PilotaSplash;
import com.biel.lobby.mapes.jocs.Quakecraft;
import com.biel.lobby.mapes.jocs.RainbowClay;
import com.biel.lobby.mapes.jocs.RedstoneWars;
import com.biel.lobby.mapes.jocs.Spleef;
import com.biel.lobby.mapes.jocs.TNTRun;
import com.biel.lobby.mapes.jocs.TeamDeathMatch;
import com.biel.lobby.mapes.jocs.Torres;
import com.connorlinfoot.bountifulapi.BountifulAPI;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class GestorMapes
implements Listener {
    private static final /* synthetic */ int[] lIlIIlIII;
    /* synthetic */ ArrayList<Pair<String, Double>> auto_ratings;
    public /* synthetic */ lobby plugin;
    /* synthetic */ ArrayList<ContenidorMapa> Mapes;
    private static final /* synthetic */ String[] lIlIIIllI;

    public List<Joc> getGames() {
        GestorMapes llIIIllllIllIl;
        return llIIIllllIllIl.getAllInstances().stream().filter(llIIIllllIIlll -> llIIIllllIIlll instanceof Joc).map(llIIIllllIlIlI -> (Joc)llIIIllllIlIlI).collect(Collectors.toList());
    }

    public ArrayList<Mapa> getAllInstances() {
        GestorMapes llIIlIIIIllIll;
        ArrayList<Mapa> llIIlIIIIllIlI = new ArrayList<Mapa>();
        Iterator<ContenidorMapa> llIIlIIIIlIlll = llIIlIIIIllIll.Mapes.iterator();
        while (GestorMapes.lIIlIlIIlll((int)llIIlIIIIlIlll.hasNext())) {
            ContenidorMapa llIIlIIIIlllII = llIIlIIIIlIlll.next();
            if (GestorMapes.lIIlIlIIlll(llIIlIIIIlllII instanceof ContenidorJoc)) {
                ContenidorJoc llIIlIIIIlllIl = (ContenidorJoc)llIIlIIIIlllII;
                "".length();
                llIIlIIIIllIlI.addAll(llIIlIIIIlllIl.getInst\u00e0ncies());
            }
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return llIIlIIIIllIlI;
    }

    static {
        GestorMapes.lIIlIlIIllI();
        GestorMapes.lIIlIlIIIlI();
    }

    private static void lIIlIlIIIlI() {
        lIlIIIllI = new String[lIlIIlIII[27]];
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[0]] = GestorMapes.lIIlIIlllII("eTSanTWw00c=", "iruAL");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[1]] = GestorMapes.lIIlIIlllll("PA87NgMBGXIbDQ8X", "nnRXa");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[2]] = GestorMapes.lIIlIlIIIII("e0DfiYR9AgBE86WAqiMAZEDIHa97rQ6S", "jcGpH");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[3]] = GestorMapes.lIIlIIlllll("BjwOKAE0Ow4lEA==", "WIoCd");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[4]] = GestorMapes.lIIlIIlllll("BQQUMDgoBBc=", "AkyYV");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[5]] = GestorMapes.lIIlIIlllll("GRw4NVUJHDgsHW00OCwWJQ==", "MyYXu");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[6]] = GestorMapes.lIIlIIlllll("LDU9NztNcw==", "mGXYZ");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[7]] = GestorMapes.lIIlIlIIIII("+9nL5NabyfY=", "JcGJk");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[8]] = GestorMapes.lIIlIIlllll("FyAsAgl2EwUgHiUTBSA=", "VRIlh");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[9]] = GestorMapes.lIIlIIlllll("DzUlNHMBITgwIQ==", "MTVQS");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[10]] = GestorMapes.lIIlIlIIIII("iwNQ43oGi4/3GDyiidi0lA==", "Icrfo");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[11]] = GestorMapes.lIIlIIlllII("Yd0zqOv8wdoP7cmtSUYAfA==", "fBNXI");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[12]] = GestorMapes.lIIlIIlllll("Ci4/CAwvPQsPDC0=", "ZOMcc");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[13]] = GestorMapes.lIIlIIlllII("3Pn7ua6MEIWtD/0cd9zSqQ==", "ZOrpW");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[14]] = GestorMapes.lIIlIIlllll("GRMxNS0kGDBmDioEJg==", "KvUFY");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[15]] = GestorMapes.lIIlIlIIIII("8fV05iGX34Y1GsaW25+/xg==", "KieiB");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[16]] = GestorMapes.lIIlIlIIIII("G2Z2u6oqfzhrYUOn+TKIUA==", "iDSIR");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[17]] = GestorMapes.lIIlIIlllII("Cd5mrTYj5fmeZZYoxd5diQ==", "KgaEQ");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[18]] = GestorMapes.lIIlIIlllII("fwjfDgCttywFQ+01iXU28w==", "yXiui");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[19]] = GestorMapes.lIIlIIlllII("jXVVHm26ncAJr+5JEYvkzQ==", "WKWqb");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[21]] = GestorMapes.lIIlIIlllll("GRpvEgU=", "twUav");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[22]] = GestorMapes.lIIlIIlllll("QX8=", "aWCDb");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[23]] = GestorMapes.lIIlIlIIIII("GTM1eHpvEGg=", "VfBoU");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[24]] = GestorMapes.lIIlIlIIIII("XfRnbN9ny6ESYcKW0RqmqA==", "WFqcA");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[25]] = GestorMapes.lIIlIlIIIII("8JxUTWBI2mH8aX7EuaCxtQ==", "EwHCy");
        GestorMapes.lIlIIIllI[GestorMapes.lIlIIlIII[26]] = GestorMapes.lIIlIIlllll("MAEjGSReRA==", "ddNiW");
    }

    private static void lIIlIlIIllI() {
        lIlIIlIII = new int[28];
        GestorMapes.lIlIIlIII[0] = (253 ^ 192) & ~(167 ^ 154);
        GestorMapes.lIlIIlIII[1] = " ".length();
        GestorMapes.lIlIIlIII[2] = "  ".length();
        GestorMapes.lIlIIlIII[3] = "   ".length();
        GestorMapes.lIlIIlIII[4] = 102 ^ 96 ^ "  ".length();
        GestorMapes.lIlIIlIII[5] = 190 ^ 187;
        GestorMapes.lIlIIlIII[6] = 193 ^ 199;
        GestorMapes.lIlIIlIII[7] = 59 ^ 122 ^ (3 ^ 69);
        GestorMapes.lIlIIlIII[8] = 70 + 10 - -1 + 70 ^ 138 + 29 - 128 + 120;
        GestorMapes.lIlIIlIII[9] = 157 ^ 148;
        GestorMapes.lIlIIlIII[10] = 26 ^ 16;
        GestorMapes.lIlIIlIII[11] = 109 ^ 102;
        GestorMapes.lIlIIlIII[12] = 53 ^ 57;
        GestorMapes.lIlIIlIII[13] = 145 ^ 156;
        GestorMapes.lIlIIlIII[14] = 119 ^ 98 ^ (166 ^ 189);
        GestorMapes.lIlIIlIII[15] = 93 ^ 82;
        GestorMapes.lIlIIlIII[16] = 16 + 17 - -120 + 8 ^ 2 + 128 - 44 + 91;
        GestorMapes.lIlIIlIII[17] = 67 + 112 - 87 + 69 ^ 32 + 140 - 30 + 34;
        GestorMapes.lIlIIlIII[18] = 50 ^ 32;
        GestorMapes.lIlIIlIII[19] = 138 ^ 153;
        GestorMapes.lIlIIlIII[20] = 66 ^ 27 ^ (68 ^ 6);
        GestorMapes.lIlIIlIII[21] = 126 ^ 106;
        GestorMapes.lIlIIlIII[22] = 145 ^ 132;
        GestorMapes.lIlIIlIII[23] = 27 ^ 40 ^ (86 ^ 115);
        GestorMapes.lIlIIlIII[24] = 143 ^ 152;
        GestorMapes.lIlIIlIII[25] = 220 ^ 146 ^ (76 ^ 26);
        GestorMapes.lIlIIlIII[26] = 26 ^ 86 ^ (18 ^ 71);
        GestorMapes.lIlIIlIII[27] = 26 + 99 - 0 + 27 ^ 67 + 30 - 96 + 129;
    }

    public GestorMapes() {
        GestorMapes llIIlIIIlllIll;
        llIIlIIIlllIll.Mapes = new ArrayList();
        llIIlIIIlllIll.plugin = lobby.getPlugin();
        llIIlIIIlllIll.plugin.getServer().getPluginManager().registerEvents((Listener)llIIlIIIlllIll, (Plugin)llIIlIIIlllIll.plugin);
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(Spleef.class, lIlIIIllI[lIlIIlIII[0]], Material.SNOW, DevelopmentState.Release));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(RainbowClay.class, lIlIIIllI[lIlIIlIII[1]], Material.HARD_CLAY, DevelopmentState.Beta));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(Torres.class, lIlIIIllI[lIlIIlIII[2]], Material.ARROW, DevelopmentState.Beta));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(Quakecraft.class, lIlIIIllI[lIlIIlIII[3]], Material.STONE_HOE, DevelopmentState.Beta));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(Dominion.class, lIlIIIllI[lIlIIlIII[4]], Material.DIAMOND, DevelopmentState.Beta));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(TeamDeathMatch.class, lIlIIIllI[lIlIIlIII[5]], Material.IRON_SWORD, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(Arena4.class, lIlIIIllI[lIlIIlIII[6]], Material.BAKED_POTATO, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(TNTRun.class, lIlIIIllI[lIlIIlIII[7]], Material.TNT, DevelopmentState.KnownIssues));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(ArenaAllvAll.class, lIlIIIllI[lIlIIlIII[8]], Material.SAND, DevelopmentState.Beta));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(BaseLunar.class, lIlIIIllI[lIlIIlIII[9]], Material.GLASS, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(BoletumDTC.class, lIlIIIllI[lIlIIlIII[10]], Material.MUSHROOM_SOUP, DevelopmentState.Beta));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(KingSkeletonChallenge.class, lIlIIIllI[lIlIIlIII[11]], Material.GOLD_HELMET, DevelopmentState.PreAlpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(Parkour.class, lIlIIIllI[lIlIIlIII[12]], Material.GOLD_BLOCK, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(InkWars.class, lIlIIIllI[lIlIIlIII[13]], Material.COAL_BLOCK, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(RedstoneWars.class, lIlIIIllI[lIlIIlIII[14]], Material.REDSTONE_BLOCK, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(PilotaSplash.class, lIlIIIllI[lIlIIlIII[15]], Material.SLIME_BALL, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(OneInTheChamber.class, lIlIIIllI[lIlIIlIII[16]], Material.BOW, DevelopmentState.Alpha));
        "".length();
        llIIlIIIlllIll.Mapes.add(llIIlIIIlllIll.new ContenidorJoc(BedWars.class, lIlIIIllI[lIlIIlIII[17]], Material.BED, DevelopmentState.InDevelopment));
    }

    private static String lIIlIIlllll(String llIIIlIlIlIlIl, String llIIIlIlIIlllI) {
        llIIIlIlIlIlIl = new String(Base64.getDecoder().decode(llIIIlIlIlIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIIlIlIlIIlI = new StringBuilder();
        char[] llIIIlIlIlIIIl = llIIIlIlIIlllI.toCharArray();
        int llIIIlIlIlIIII = lIlIIlIII[0];
        char[] llIIIlIlIIlIlI = llIIIlIlIlIlIl.toCharArray();
        int llIIIlIlIIlIIl = llIIIlIlIIlIlI.length;
        int llIIIlIlIIIlll = lIlIIlIII[0];
        while (GestorMapes.lIIlIlIlIIl(llIIIlIlIIIlll, llIIIlIlIIlIIl)) {
            char llIIIlIlIlIlll = llIIIlIlIIlIlI[llIIIlIlIIIlll];
            "".length();
            llIIIlIlIlIIlI.append((char)(llIIIlIlIlIlll ^ llIIIlIlIlIIIl[llIIIlIlIlIIII % llIIIlIlIlIIIl.length]));
            ++llIIIlIlIlIIII;
            ++llIIIlIlIIIlll;
            "".length();
            if ("   ".length() > " ".length()) continue;
            return null;
        }
        return String.valueOf(llIIIlIlIlIIlI);
    }

    public void queryAutoRatings() {
        GestorMapes llIIlIIIlllIII;
        llIIlIIIlllIII.auto_ratings = Com.getDataAPI().getAutoRating();
        llIIlIIIlllIII.Mapes.sort((llIIIlllIIlIIl, llIIIlllIIlIII) -> Double.compare(llIIIlllIIlIII.getRating(), llIIIlllIIlIIl.getRating()));
    }

    public void openAllGamesMenu(Player llIIIlllllIlll) {
        GestorMapes llIIIlllllllII;
        IconMenu llIIIllllllIlI = new IconMenu(lIlIIIllI[lIlIIlIII[19]], lIlIIlIII[20], llIIIllllIIIII -> {
            GestorMapes llIIIllllIIIIl;
            llIIIllllIIIII.setWillClose(lIlIIlIII[1]);
            List<Joc> llIIIlllIlllll = llIIIllllIIIIl.getGames();
            int llIIIlllIllllI = llIIIllllIIIII.getPosition();
            Joc llIIIlllIlllIl = llIIIlllIlllll.get(llIIIlllIllllI);
            llIIIlllIlllIl.Join(llIIIllllIIIII.getPlayer());
        });
        List<Joc> llIIIllllllIIl = llIIIlllllllII.getGames();
        Iterator<Joc> llIIIlllllIlII = llIIIllllllIIl.iterator();
        while (GestorMapes.lIIlIlIIlll((int)llIIIlllllIlII.hasNext())) {
            Joc llIIIlllllllIl = llIIIlllllIlII.next();
            Wool llIIlIIIIIIIII = new Wool(DyeColor.BLACK);
            ItemStack llIIIlllllllll = llIIlIIIIIIIII.toItemStack();
            llIIIlllllllll.setAmount(llIIIlllllllIl.getPlayers().size());
            String llIIIllllllllI = new SimpleDateFormat(lIlIIIllI[lIlIIlIII[21]]).format(new Date(llIIIlllllllIl.tempsTranscorregut()));
            String[] arrstring = new String[lIlIIlIII[5]];
            arrstring[GestorMapes.lIlIIlIII[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(llIIIlllllllIl.getGameName()).append(lIlIIIllI[lIlIIlIII[22]]).append(llIIIlllllllIl.NomWorld).append(lIlIIIllI[lIlIIlIII[23]]));
            arrstring[GestorMapes.lIlIIlIII[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(llIIIlllllllIl.getGameState().name()));
            arrstring[GestorMapes.lIlIIlIII[2]] = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIIIllI[lIlIIlIII[24]]).append(Integer.toString(llIIIlllllllIl.getPlayers().size())));
            arrstring[GestorMapes.lIlIIlIII[3]] = String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIlIIIllI[lIlIIlIII[25]]).append(llIIIlllllllIl.getSpectators().size()));
            arrstring[GestorMapes.lIlIIlIII[4]] = String.valueOf(new StringBuilder().append(lIlIIIllI[lIlIIlIII[26]]).append(llIIIllllllllI));
            "".length();
            llIIIllllllIlI.setOption(llIIIllllllIIl.indexOf((Object)llIIIlllllllIl), llIIIlllllllll, llIIIlllllllIl.getGameName(), arrstring);
            "".length();
            if (" ".length() <= "  ".length()) continue;
            return;
        }
        llIIIllllllIlI.open(llIIIlllllIlll);
    }

    private static boolean lIIlIlIlIII(int n) {
        return n > 0;
    }

    public Mapa getMapWherePlayerIs(Player llIIlIIIIIlllI) {
        GestorMapes llIIlIIIIIllll;
        if (GestorMapes.lIIlIlIIlll((int)lobby.isOnLobby(llIIlIIIIIlllI).booleanValue())) {
            return null;
        }
        Iterator<Mapa> llIIlIIIIIlIll = llIIlIIIIIllll.getAllInstances().iterator();
        while (GestorMapes.lIIlIlIIlll((int)llIIlIIIIIlIll.hasNext())) {
            Mapa llIIlIIIIlIIII = llIIlIIIIIlIll.next();
            if (GestorMapes.lIIlIlIIlll((int)llIIlIIIIlIIII.getWorld().getPlayers().contains((Object)llIIlIIIIIlllI))) {
                return llIIlIIIIlIIII;
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return null;
    }

    private static String lIIlIIlllII(String llIIIlIllIlIll, String llIIIlIllIllII) {
        try {
            SecretKeySpec llIIIlIlllIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIlIllIllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIlIllIllll = Cipher.getInstance("Blowfish");
            llIIIlIllIllll.init(lIlIIlIII[2], llIIIlIlllIIII);
            return new String(llIIIlIllIllll.doFinal(Base64.getDecoder().decode(llIIIlIllIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlIllIlllI) {
            llIIIlIllIlllI.printStackTrace();
            return null;
        }
    }

    private static String lIIlIlIIIII(String llIIIllIlllllI, String llIIIllIllllIl) {
        try {
            SecretKeySpec llIIIlllIIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIllIllllIl.getBytes(StandardCharsets.UTF_8)), lIlIIlIII[8]), "DES");
            Cipher llIIIlllIIIIII = Cipher.getInstance("DES");
            llIIIlllIIIIII.init(lIlIIlIII[2], llIIIlllIIIIIl);
            return new String(llIIIlllIIIIII.doFinal(Base64.getDecoder().decode(llIIIllIlllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIllIllllll) {
            llIIIllIllllll.printStackTrace();
            return null;
        }
    }

    private static boolean lIIlIlIIlll(int n) {
        return n != 0;
    }

    private static boolean lIIlIlIlIIl(int n, int n2) {
        return n < n2;
    }

    public void ObrirMenuMapes(Player llIIlIIIlIlIll) {
        GestorMapes llIIlIIIlIlIIl;
        llIIlIIIlIlIIl.queryAutoRatings();
        IconMenu llIIlIIIlIlIlI = new IconMenu(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIlIIIllI[lIlIIlIII[18]])), (int)(9.0 * (Math.ceil(llIIlIIIlIlIIl.Mapes.size() / lIlIIlIII[9]) + 1.0)), llIIIlllIlIIlI -> {
            GestorMapes llIIIlllIIllll;
            llIIIlllIlIIlI.setWillClose(lIlIIlIII[0]);
            int llIIIlllIlIIIl = llIIIlllIlIIlI.getPosition();
            ContenidorMapa llIIIlllIlIIII = llIIIlllIIllll.Mapes.get(llIIIlllIlIIIl);
            llIIIlllIlIIII.playerClick(llIIIlllIlIIlI.getPlayer());
        });
        Iterator<ContenidorMapa> llIIlIIIlIIllI = llIIlIIIlIlIIl.Mapes.iterator();
        while (GestorMapes.lIIlIlIIlll((int)llIIlIIIlIIllI.hasNext())) {
            ContenidorMapa llIIlIIIlIllIl = llIIlIIIlIIllI.next();
            int llIIlIIIlIllll = lIlIIlIII[1];
            if (GestorMapes.lIIlIlIlIII(llIIlIIIlIllIl.getPlayerAmount())) {
                llIIlIIIlIllll = llIIlIIIlIllIl.getPlayerAmount();
            }
            ItemStack llIIlIIIlIlllI = new ItemStack(llIIlIIIlIllIl.mat, llIIlIIIlIllll);
            "".length();
            llIIlIIIlIlIlI.setOption(llIIlIIIlIlIIl.Mapes.indexOf(llIIlIIIlIllIl), llIIlIIIlIlllI, llIIlIIIlIllIl.getDisplayName(), llIIlIIIlIllIl.getDescription());
            "".length();
            if ("   ".length() >= 0) continue;
            return;
        }
        llIIlIIIlIlIlI.open(llIIlIIIlIlIll);
    }

    public static final class DevelopmentState
    extends Enum<DevelopmentState> {
        public static final /* synthetic */ /* enum */ DevelopmentState Alpha;
        public static final /* synthetic */ /* enum */ DevelopmentState Beta;
        public static final /* synthetic */ /* enum */ DevelopmentState NotWorking;
        public static final /* synthetic */ /* enum */ DevelopmentState KnownIssues;
        private static final /* synthetic */ String[] lIllIIlI;
        private static final /* synthetic */ DevelopmentState[] $VALUES;
        public static final /* synthetic */ /* enum */ DevelopmentState Release;
        public static final /* synthetic */ /* enum */ DevelopmentState PreAlpha;
        private static final /* synthetic */ int[] lIlllIll;
        public static final /* synthetic */ /* enum */ DevelopmentState InDevelopment;

        private static boolean lIlllIIlIl(int n, int n2) {
            return n < n2;
        }

        private DevelopmentState() {
            DevelopmentState lIIllIIIllIlIll;
        }

        private static void lIllIIIIIl() {
            lIllIIlI = new String[lIlllIll[7]];
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[0]] = DevelopmentState.lIlIlllIll("HQ4ZJzshCgQeMw==", "SampT");
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[1]] = DevelopmentState.lIlIllllII("/TCXbzQUFaHpOKP7osOJkA==", "xsmef");
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[2]] = DevelopmentState.lIlIllllIl("24el0208mi7+6JJlY6wYGA==", "WfvTy");
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[3]] = DevelopmentState.lIlIllllII("H1gdP0KoDRJ/NyMLgflVrw==", "odsSw");
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[4]] = DevelopmentState.lIlIllllII("R6Qf97Zbduk=", "nPUwr");
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[5]] = DevelopmentState.lIlIllllIl("fzsjhVCao0I=", "EOloi");
            DevelopmentState.lIllIIlI[DevelopmentState.lIlllIll[6]] = DevelopmentState.lIlIllllIl("EHEy5g5Xpyk=", "vWvGj");
        }

        static {
            DevelopmentState.lIlllIIlII();
            DevelopmentState.lIllIIIIIl();
            NotWorking = new DevelopmentState();
            KnownIssues = new DevelopmentState();
            InDevelopment = new DevelopmentState();
            PreAlpha = new DevelopmentState();
            Alpha = new DevelopmentState();
            Beta = new DevelopmentState();
            Release = new DevelopmentState();
            DevelopmentState[] arrdevelopmentState = new DevelopmentState[lIlllIll[7]];
            arrdevelopmentState[DevelopmentState.lIlllIll[0]] = NotWorking;
            arrdevelopmentState[DevelopmentState.lIlllIll[1]] = KnownIssues;
            arrdevelopmentState[DevelopmentState.lIlllIll[2]] = InDevelopment;
            arrdevelopmentState[DevelopmentState.lIlllIll[3]] = PreAlpha;
            arrdevelopmentState[DevelopmentState.lIlllIll[4]] = Alpha;
            arrdevelopmentState[DevelopmentState.lIlllIll[5]] = Beta;
            arrdevelopmentState[DevelopmentState.lIlllIll[6]] = Release;
            $VALUES = arrdevelopmentState;
        }

        private static String lIlIlllIll(String lIIllIIIlIIllII, String lIIllIIIlIIlIll) {
            lIIllIIIlIIllII = new String(Base64.getDecoder().decode(lIIllIIIlIIllII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIllIIIlIIllll = new StringBuilder();
            char[] lIIllIIIlIIlllI = lIIllIIIlIIlIll.toCharArray();
            int lIIllIIIlIIllIl = lIlllIll[0];
            char[] lIIllIIIlIIIlll = lIIllIIIlIIllII.toCharArray();
            int lIIllIIIlIIIllI = lIIllIIIlIIIlll.length;
            int lIIllIIIlIIIlIl = lIlllIll[0];
            while (DevelopmentState.lIlllIIlIl(lIIllIIIlIIIlIl, lIIllIIIlIIIllI)) {
                char lIIllIIIlIlIIlI = lIIllIIIlIIIlll[lIIllIIIlIIIlIl];
                "".length();
                lIIllIIIlIIllll.append((char)(lIIllIIIlIlIIlI ^ lIIllIIIlIIlllI[lIIllIIIlIIllIl % lIIllIIIlIIlllI.length]));
                ++lIIllIIIlIIllIl;
                ++lIIllIIIlIIIlIl;
                "".length();
                if (" ".length() != (126 ^ 22 ^ (220 ^ 176))) continue;
                return null;
            }
            return String.valueOf(lIIllIIIlIIllll);
        }

        private static String lIlIllllII(String lIIllIIIIllllII, String lIIllIIIIlllIll) {
            try {
                SecretKeySpec lIIllIIIIllllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIllIIIIlllIll.getBytes(StandardCharsets.UTF_8)), lIlllIll[8]), "DES");
                Cipher lIIllIIIIlllllI = Cipher.getInstance("DES");
                lIIllIIIIlllllI.init(lIlllIll[2], lIIllIIIIllllll);
                return new String(lIIllIIIIlllllI.doFinal(Base64.getDecoder().decode(lIIllIIIIllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIllIIIIllllIl) {
                lIIllIIIIllllIl.printStackTrace();
                return null;
            }
        }

        private static String lIlIllllIl(String lIIllIIIlIlllll, String lIIllIIIllIIIII) {
            try {
                SecretKeySpec lIIllIIIllIIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIllIIIllIIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIllIIIllIIIll = Cipher.getInstance("Blowfish");
                lIIllIIIllIIIll.init(lIlllIll[2], lIIllIIIllIIlII);
                return new String(lIIllIIIllIIIll.doFinal(Base64.getDecoder().decode(lIIllIIIlIlllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIllIIIllIIIlI) {
                lIIllIIIllIIIlI.printStackTrace();
                return null;
            }
        }

        public static DevelopmentState valueOf(String lIIllIIIlllIIII) {
            return Enum.valueOf(DevelopmentState.class, lIIllIIIlllIIII);
        }

        private static void lIlllIIlII() {
            lIlllIll = new int[9];
            DevelopmentState.lIlllIll[0] = (111 ^ 15 ^ (194 ^ 185)) & (83 ^ 5 ^ (12 ^ 65) ^ -" ".length());
            DevelopmentState.lIlllIll[1] = " ".length();
            DevelopmentState.lIlllIll[2] = "  ".length();
            DevelopmentState.lIlllIll[3] = "   ".length();
            DevelopmentState.lIlllIll[4] = 11 ^ 15;
            DevelopmentState.lIlllIll[5] = 90 ^ 95;
            DevelopmentState.lIlllIll[6] = 97 ^ 103;
            DevelopmentState.lIlllIll[7] = 25 + 140 - 107 + 86 ^ 59 + 20 - -68 + 4;
            DevelopmentState.lIlllIll[8] = 126 ^ 118;
        }

        public static DevelopmentState[] values() {
            return (DevelopmentState[])$VALUES.clone();
        }
    }

    public abstract class ContenidorMapa
    implements Listener {
        public /* synthetic */ lobby plugin;
        /* synthetic */ String nom;
        private static final /* synthetic */ String[] llllIllI;
        /* synthetic */ Material mat;
        private static final /* synthetic */ int[] lIIIIllll;
        /* synthetic */ Class<?> ClassMapa;

        public void setNom(String llllIIIllIIIIl) {
            llllIIIllIIIlI.nom = llllIIIllIIIIl;
        }

        public String getNom() {
            ContenidorMapa llllIIIlllIIlI;
            return llllIIIlllIIlI.nom;
        }

        public double getRating() {
            return 0.0;
        }

        private static boolean lllllIIIlI(int n) {
            return n > 0;
        }

        public ArrayList<String> getDescription() {
            ContenidorMapa llllIIIllIlllI;
            ArrayList<String> llllIIIllIllIl = new ArrayList<String>();
            int llllIIIllIllII = llllIIIllIlllI.getPlayerAmount();
            if (ContenidorMapa.lllllIIIlI(llllIIIllIllII)) {
                String string;
                if (ContenidorMapa.lllllIIIll(llllIIIllIllII, lIIIIllll[1])) {
                    string = llllIllI[lIIIIllll[2]];
                    "".length();
                    if (-"  ".length() > 0) {
                        return null;
                    }
                } else {
                    string = llllIllI[lIIIIllll[3]];
                }
                "".length();
                llllIIIllIllIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llllIllI[lIIIIllll[0]]).append(llllIIIllIllII).append(llllIllI[lIIIIllll[1]]).append(string)));
            }
            return llllIIIllIllIl;
        }

        public String getDisplayName() {
            ContenidorMapa llllIIIllIIlIl;
            if (ContenidorMapa.lllllIIlII(llllIIIllIIlIl.getMapCount())) {
                return String.valueOf(new StringBuilder().append((Object)ChatColor.STRIKETHROUGH).append(llllIllI[lIIIIllll[4]]).append((Object)ChatColor.BOLD).append(llllIIIllIIlIl.nom).append((Object)ChatColor.RESET));
            }
            return String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(llllIIIllIIlIl.nom).append((Object)ChatColor.RESET));
        }

        private static void lllIlIlIll() {
            llllIllI = new String[lIIIIllll[5]];
            ContenidorMapa.llllIllI[ContenidorMapa.lIIIIllll[0]] = ContenidorMapa.lllIlIIllI("", "XtxmM");
            ContenidorMapa.llllIllI[ContenidorMapa.lIIIIllll[1]] = ContenidorMapa.lllIlIIlll("iz0GZj/pkiV3dTinGuty8g==", "GJtdN");
            ContenidorMapa.llllIllI[ContenidorMapa.lIIIIllll[2]] = ContenidorMapa.lllIlIIllI("Gw==", "hfIhs");
            ContenidorMapa.llllIllI[ContenidorMapa.lIIIIllll[3]] = ContenidorMapa.lllIlIIlll("4HuWlo9vne4=", "bCoAX");
            ContenidorMapa.llllIllI[ContenidorMapa.lIIIIllll[4]] = ContenidorMapa.lllIlIIlll("ZwRR8bkC6rY=", "YhMmn");
        }

        static {
            ContenidorMapa.lllllIIIIl();
            ContenidorMapa.lllIlIlIll();
        }

        private static boolean lllllIIlII(int n) {
            return n == 0;
        }

        private static String lllIlIIllI(String llllIIIIlllIII, String llllIIIIllIlll) {
            llllIIIIlllIII = new String(Base64.getDecoder().decode(llllIIIIlllIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllIIIIllIllI = new StringBuilder();
            char[] llllIIIIllIlIl = llllIIIIllIlll.toCharArray();
            int llllIIIIllIlII = lIIIIllll[0];
            char[] llllIIIIlIlllI = llllIIIIlllIII.toCharArray();
            int llllIIIIlIllIl = llllIIIIlIlllI.length;
            int llllIIIIlIllII = lIIIIllll[0];
            while (ContenidorMapa.lllllIIlIl(llllIIIIlIllII, llllIIIIlIllIl)) {
                char llllIIIIlllIIl = llllIIIIlIlllI[llllIIIIlIllII];
                "".length();
                llllIIIIllIllI.append((char)(llllIIIIlllIIl ^ llllIIIIllIlIl[llllIIIIllIlII % llllIIIIllIlIl.length]));
                ++llllIIIIllIlII;
                ++llllIIIIlIllII;
                "".length();
                if (" ".length() > ((151 ^ 140) & ~(222 ^ 197))) continue;
                return null;
            }
            return String.valueOf(llllIIIIllIllI);
        }

        private static void lllllIIIIl() {
            lIIIIllll = new int[7];
            ContenidorMapa.lIIIIllll[0] = (110 ^ 90) & ~(50 ^ 6);
            ContenidorMapa.lIIIIllll[1] = " ".length();
            ContenidorMapa.lIIIIllll[2] = "  ".length();
            ContenidorMapa.lIIIIllll[3] = "   ".length();
            ContenidorMapa.lIIIIllll[4] = 93 ^ 26 ^ (12 ^ 79);
            ContenidorMapa.lIIIIllll[5] = 95 ^ 90;
            ContenidorMapa.lIIIIllll[6] = 5 + 23 - -106 + 19 ^ 83 + 91 - 123 + 94;
        }

        public abstract int getMapCount();

        private static boolean lllllIIIll(int n, int n2) {
            return n > n2;
        }

        public ContenidorMapa(Class<?> llllIIIlIlIlll, String llllIIIlIlIllI, Material llllIIIlIlIIII) {
            ContenidorMapa llllIIIlIlIlII;
            llllIIIlIlIlII.plugin = lobby.getPlugin();
            llllIIIlIlIlII.plugin.getServer().getPluginManager().registerEvents((Listener)llllIIIlIlIlII, (Plugin)llllIIIlIlIlII.plugin);
            llllIIIlIlIlII.ClassMapa = llllIIIlIlIlll;
            llllIIIlIlIlII.nom = llllIIIlIlIllI;
            llllIIIlIlIlII.mat = llllIIIlIlIIII;
        }

        private static String lllIlIIlll(String llllIIIlIIlIII, String llllIIIlIIIlll) {
            try {
                SecretKeySpec llllIIIlIIlIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIIlIIIlll.getBytes(StandardCharsets.UTF_8)), lIIIIllll[6]), "DES");
                Cipher llllIIIlIIlIlI = Cipher.getInstance("DES");
                llllIIIlIIlIlI.init(lIIIIllll[2], llllIIIlIIlIll);
                return new String(llllIIIlIIlIlI.doFinal(Base64.getDecoder().decode(llllIIIlIIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIIlIIlIIl) {
                llllIIIlIIlIIl.printStackTrace();
                return null;
            }
        }

        abstract void playerClick(Player var1);

        private static boolean lllllIIlIl(int n, int n2) {
            return n < n2;
        }

        public abstract int getPlayerAmount();
    }

    public class ContenidorJoc
    extends ContenidorMapa {
        /* synthetic */ DevelopmentState developmentState;
        private static final /* synthetic */ int[] lIIIllIIl;
        /* synthetic */ ArrayList<Joc> Inst\u00e0ncies;
        private static final /* synthetic */ String[] lIIIIlllI;

        @Override
        public double getRating() {
            ContenidorJoc lllIIIIllIlIlI;
            Joc lllIIIIllIlIll = lllIIIIllIlIlI.getTempInstance();
            return Math.sqrt(lllIIIIllIlIlI.GestorMapes.this.auto_ratings.stream().filter(lllIIIIIIIllII -> ((String)lllIIIIIIIllII.getFirst()).equals(lllIIIIllIlIll.getGameName())).mapToDouble(Pair::getSecond).findAny().orElse(0.0) / 100.0) * 100.0;
        }

        public ArrayList<Joc> getInst\u00e0ncies() {
            ContenidorJoc lllIIIIIlIlIIl;
            return lllIIIIIlIlIIl.Inst\u00e0ncies;
        }

        public void ObrirMenu(Player lllIIIIIllIlIl) {
            ContenidorJoc lllIIIIIllIllI;
            Joc lllIIIIIlllIIl = lllIIIIIllIllI.getTempInstance();
            IconMenu lllIIIIIlllIII = new IconMenu(lIIIIlllI[lIIIllIIl[21]], lIIIllIIl[22], lllIIIIIIllIIl -> {
                ContenidorJoc lllIIIIIIlIlIl;
                int n;
                lllIIIIIIllIIl.setWillClose(lIIIllIIl[0]);
                Joc lllIIIIIIllIII = lllIIIIIIlIlIl.getTempInstance();
                int lllIIIIIIlIlll = lllIIIIIIllIIl.getPosition();
                MapaResetejable.MapMode lllIIIIIIlIllI = lllIIIIIIllIII.getMapMode();
                if (ContenidorJoc.lIIIIIllIIl((Object)lllIIIIIIlIllI, (Object)MapaResetejable.MapMode.MULTIPLE)) {
                    n = lIIIllIIl[22] - lllIIIIIIllIII.getMultiWorldList().size();
                    "".length();
                    if ("   ".length() <= 0) {
                        return;
                    }
                } else {
                    n = lIIIllIIl[29];
                }
                if (ContenidorJoc.lIIIIIlllIl(lllIIIIIIlIlll, n)) {
                    Joc lllIIIIIIlllII = lllIIIIIIlIlIl.Inst\u00e0ncies.get(lllIIIIIIlIlll);
                    lllIIIIIIlllII.Join(lllIIIIIIllIIl.getPlayer());
                    "".length();
                    if (-" ".length() > " ".length()) {
                        return;
                    }
                } else {
                    Integer n2;
                    if (ContenidorJoc.lIIIIIllIIl((Object)lllIIIIIIlIllI, (Object)MapaResetejable.MapMode.MULTIPLE)) {
                        n2 = lIIIllIIl[29] - lllIIIIIIllIIl.getPosition();
                        "".length();
                        if (-" ".length() > (185 ^ 189)) {
                            return;
                        }
                    } else {
                        n2 = null;
                    }
                    Joc lllIIIIIIllIll = lllIIIIIIlIlIl.addMap(n2);
                    lllIIIIIIllIll.Join(lllIIIIIIllIIl.getPlayer());
                }
            });
            if (ContenidorJoc.lIIIIIlIlIl(lllIIIIIllIllI.getMapCount())) {
                String lllIIIIlIIIlIl = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIIlllI[lIIIllIIl[23]]).append((Object)ChatColor.ITALIC).append(lIIIIlllI[lIIIllIIl[24]]));
                BountifulAPI.sendActionBar((Player)lllIIIIIllIlIl, (String)lllIIIIlIIIlIl, (int)lIIIllIIl[25]);
                lllIIIIIllIlIl.playSound(lllIIIIIllIlIl.getLocation(), Sound.ENTITY_VILLAGER_NO, 100.0f, 1.0f);
                return;
            }
            Iterator<Joc> lllIIIIlIIIlIl = lllIIIIIllIllI.Inst\u00e0ncies.iterator();
            while (ContenidorJoc.lIIIIIlIlll((int)lllIIIIlIIIlIl.hasNext())) {
                Joc lllIIIIIllllll = lllIIIIlIIIlIl.next();
                Wool lllIIIIlIIIlII = new Wool(lllIIIIIllIllI.getGameColor(lllIIIIIllllll));
                ItemStack lllIIIIlIIIIll = lllIIIIlIIIlII.toItemStack();
                lllIIIIlIIIIll.setAmount(lllIIIIIllllll.getPlayers().size());
                String lllIIIIlIIIIlI = new SimpleDateFormat(lIIIIlllI[lIIIllIIl[26]]).format(new Date(lllIIIIIllllll.tempsTranscorregut()));
                double lllIIIIlIIIIIl = lllIIIIIllllll.getGameProgressETA();
                String lllIIIIlIIIIII = String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIIIIlllI[lIIIllIIl[27]]).append(Math.round(lllIIIIlIIIIIl * 1000.0) / 10L).append(lIIIIlllI[lIIIllIIl[28]]));
                String[] arrstring = new String[lIIIllIIl[6]];
                arrstring[ContenidorJoc.lIIIllIIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lllIIIIIllllll.NomWorld));
                arrstring[ContenidorJoc.lIIIllIIl[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lllIIIIIllllll.getGameState().name()));
                arrstring[ContenidorJoc.lIIIllIIl[2]] = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIlllI[lIIIllIIl[29]]).append(Integer.toString(lllIIIIIllllll.getPlayers().size())));
                arrstring[ContenidorJoc.lIIIllIIl[3]] = String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIIIlllI[lIIIllIIl[22]]).append(lllIIIIIllllll.getSpectators().size()));
                arrstring[ContenidorJoc.lIIIllIIl[4]] = String.valueOf(new StringBuilder().append(lIIIIlllI[lIIIllIIl[30]]).append(lllIIIIlIIIIlI));
                arrstring[ContenidorJoc.lIIIllIIl[5]] = lllIIIIlIIIIII;
                "".length();
                lllIIIIIlllIII.setOption(lllIIIIIllIllI.Inst\u00e0ncies.indexOf((Object)lllIIIIIllllll), lllIIIIlIIIIll, lllIIIIIllllll.getGameName(), arrstring);
                "".length();
                if ("  ".length() != -" ".length()) continue;
                return;
            }
            MapaResetejable.MapMode lllIIIIIllIlll = lllIIIIIlllIIl.getMapMode();
            if (!ContenidorJoc.lIIIIIlIlll((int)lllIIIIIllIllI.AlgunMapaDisponible().booleanValue()) || ContenidorJoc.lIIIIIllIIl((Object)lllIIIIIllIlll, (Object)MapaResetejable.MapMode.MULTIPLE)) {
                if (ContenidorJoc.lIIIIIllIIl((Object)lllIIIIIllIlll, (Object)MapaResetejable.MapMode.SINGLE)) {
                    String[] arrstring = new String[lIIIllIIl[1]];
                    arrstring[ContenidorJoc.lIIIllIIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIlllI[lIIIllIIl[32]]));
                    "".length();
                    lllIIIIIlllIII.setOption(lIIIllIIl[29], new ItemStack(Material.EMERALD, lIIIllIIl[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIlllI[lIIIllIIl[31]])), arrstring);
                }
                if (ContenidorJoc.lIIIIIllIIl((Object)lllIIIIIllIlll, (Object)MapaResetejable.MapMode.MULTIPLE)) {
                    ArrayList<String> lllIIIIIllllII = lllIIIIIlllIIl.getMultiWorldList();
                    int lllIIIIIllllIl = lIIIllIIl[0];
                    while (ContenidorJoc.lIIIIIlllIl(lllIIIIIllllIl, lllIIIIIllllII.size())) {
                        String lllIIIIIlllllI = lllIIIIIllllII.get(lllIIIIIllllIl);
                        String[] arrstring = new String[lIIIllIIl[1]];
                        arrstring[ContenidorJoc.lIIIllIIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIlllI[lIIIllIIl[33]]));
                        "".length();
                        lllIIIIIlllIII.setOption(lIIIllIIl[29] - lllIIIIIllllIl, new ItemStack(Material.EMERALD, lIIIllIIl[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lllIIIIIlllllI)), arrstring);
                        ++lllIIIIIllllIl;
                        "".length();
                        if ("  ".length() != " ".length()) continue;
                        return;
                    }
                }
            }
            lllIIIIIlllIII.open(lllIIIIIllIlIl);
        }

        @Override
        public int getMapCount() {
            ContenidorJoc lllIIIlIlllllI;
            Joc lllIIIlIllllll = lllIIIlIlllllI.getTempInstance();
            return lllIIIlIllllll.getMultiWorldList().size();
        }

        Boolean AlgunMapaDisponible() {
            ContenidorJoc lllIIIlIIlIIll;
            Iterator<Joc> lllIIIlIIlIIIl = lllIIIlIIlIIll.Inst\u00e0ncies.iterator();
            while (ContenidorJoc.lIIIIIlIlll((int)lllIIIlIIlIIIl.hasNext())) {
                Joc lllIIIlIIlIlII = lllIIIlIIlIIIl.next();
                if (ContenidorJoc.lIIIIIllIIl((Object)lllIIIlIIlIlII.getGameState(), (Object)Joc.GameState.WaitingForPlayers)) {
                    return lIIIllIIl[1];
                }
                "".length();
                if ("  ".length() > 0) continue;
                return null;
            }
            return lIIIllIIl[0];
        }

        private static String llllIllIIl(String llIlllllllllll, String llIllllllllllI) {
            try {
                SecretKeySpec lllIIIIIIIIIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIllllllllllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lllIIIIIIIIIIl = Cipher.getInstance("Blowfish");
                lllIIIIIIIIIIl.init(lIIIllIIl[2], lllIIIIIIIIIlI);
                return new String(lllIIIIIIIIIIl.doFinal(Base64.getDecoder().decode(llIlllllllllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllIIIIIIIIIII) {
                lllIIIIIIIIIII.printStackTrace();
                return null;
            }
        }

        private static void lIIIIIIlIIl() {
            lIIIIlllI = new String[lIIIllIIl[34]];
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[0]] = ContenidorJoc.llllIllIIl("ihfS4cXV0B3bVnS9eWi/0crs3W9fcvQO", "MAwAV");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[1]] = ContenidorJoc.llllIlllIl("DB0yO3nvwEA=", "rjqrx");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[2]] = ContenidorJoc.llllIlllll("IzcSPxMl", "xuwKr");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[3]] = ContenidorJoc.llllIllIIl("To6TfHGV4DQ=", "OnTfR");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[4]] = ContenidorJoc.llllIllIIl("dL5/tOegolxpS+OXjLFB0w==", "FDLTi");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[5]] = ContenidorJoc.llllIllIIl("YKId6+9RuVKJn2VXxJU2kyii42qHR12g", "BLPwM");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[6]] = ContenidorJoc.llllIlllIl("cn2c73zOC/YKGNq6Ai9EfA==", "ZALmq");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[7]] = ContenidorJoc.llllIlllIl("vzGopefuNDs=", "JEaax");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[8]] = ContenidorJoc.llllIllIIl("gKSnAzfsR9A=", "LINbf");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[9]] = ContenidorJoc.llllIlllll("aX4=", "IVNtK");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[10]] = ContenidorJoc.llllIllIIl("IW5xNowxAhc=", "EDVZu");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[11]] = ContenidorJoc.llllIllIIl("eDHXvV0HWaP6MfEjfPWECRobgZzsokowPc+5Wpum5iE=", "owGZK");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[12]] = ContenidorJoc.llllIlllll("TQ==", "mWsdS");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[13]] = ContenidorJoc.llllIllIIl("meJMECSEYQjLGkZrMQS+SHyj7akVyKLwN5O+KZL5CZt381ShEJEfqnU/7yTIjWh6", "JOUkY");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[14]] = ContenidorJoc.llllIlllIl("O2fV3K5Hfts=", "ExJuc");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[15]] = ContenidorJoc.llllIlllIl("dlR4Zq0RpI9VmF1rR8+unQ==", "KEcSF");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[16]] = ContenidorJoc.llllIllIIl("hs309SZmKTQaODCHA3u4FQ==", "CQNKq");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[17]] = ContenidorJoc.llllIlllll("Nw4YHSsDQEo=", "dzjtE");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[18]] = ContenidorJoc.llllIlllIl("0KdUTpwVmulG/SSPJBY8bw==", "rqSsY");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[20]] = ContenidorJoc.llllIlllll("", "Umapn");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[21]] = ContenidorJoc.llllIlllll("Jjs7IcKTATYhMABPMSEmAwA7ITcfCiY=", "oUHUs");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[23]] = ContenidorJoc.llllIlllIl("i73tUA9Gumw=", "wcyvH");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[24]] = ContenidorJoc.llllIlllIl("eINrNkzkOm4L0fcDpdbs/0W2wlFcWBVHxC6OcbAYum4=", "mOgXJ");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[26]] = ContenidorJoc.llllIlllIl("hl6cCr4BQy8=", "pJprZ");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[27]] = ContenidorJoc.llllIllIIl("/z5MhlRPLPc0+NP0emQmKQ==", "WMRgL");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[28]] = ContenidorJoc.llllIllIIl("/Gq8xNHJa7s=", "FiKIx");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[29]] = ContenidorJoc.llllIlllIl("37glTKttGX5yi234i5IFig==", "aCBxB");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[22]] = ContenidorJoc.llllIlllIl("w2Q85XyobyNU+qa9JxDhZg==", "wfblj");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[30]] = ContenidorJoc.llllIlllll("ICgHHhdObQ==", "tMjnd");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[31]] = ContenidorJoc.llllIllIIl("YlqXpTn+Nwk=", "JlQSE");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[32]] = ContenidorJoc.llllIlllIl("9NZ0jpqxdE+L9uyp9RxnGFiBp/XALv/4yjjHer8ycZg=", "mSKLZ");
            ContenidorJoc.lIIIIlllI[ContenidorJoc.lIIIllIIl[33]] = ContenidorJoc.llllIlllIl("fpMolhwQQUnlI1HWOdlU8K2H+oTk39GQWXFajxG4SDo=", "YhQeH");
        }

        private static boolean lIIIIIllIIl(Object object, Object object2) {
            return object == object2;
        }

        private static boolean lIIIIIlllIl(int n, int n2) {
            return n < n2;
        }

        public void setDevelopmentState(DevelopmentState lllIIIllIlIIIl) {
            lllIIIllIlIIII.developmentState = lllIIIllIlIIIl;
        }

        private static String llllIlllll(String llIlllllIlIlll, String llIlllllIlIllI) {
            llIlllllIlIlll = new String(Base64.getDecoder().decode(llIlllllIlIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llIlllllIllIlI = new StringBuilder();
            char[] llIlllllIllIIl = llIlllllIlIllI.toCharArray();
            int llIlllllIllIII = lIIIllIIl[0];
            char[] llIlllllIlIIlI = llIlllllIlIlll.toCharArray();
            int llIlllllIlIIIl = llIlllllIlIIlI.length;
            int llIlllllIlIIII = lIIIllIIl[0];
            while (ContenidorJoc.lIIIIIlllIl(llIlllllIlIIII, llIlllllIlIIIl)) {
                char llIlllllIlllIl = llIlllllIlIIlI[llIlllllIlIIII];
                "".length();
                llIlllllIllIlI.append((char)(llIlllllIlllIl ^ llIlllllIllIIl[llIlllllIllIII % llIlllllIllIIl.length]));
                ++llIlllllIllIII;
                ++llIlllllIlIIII;
                "".length();
                if ((84 + 80 - 64 + 93 ^ 147 + 74 - 102 + 78) >= (111 + 7 - 96 + 113 ^ 46 + 90 - 84 + 79)) continue;
                return null;
            }
            return String.valueOf(llIlllllIllIlI);
        }

        public ContenidorJoc(Class<?> lllIIIllIllIll, String lllIIIlllIIIII, Material lllIIIllIlllll, DevelopmentState lllIIIllIllllI) {
            ContenidorJoc lllIIIlllIIIll;
            super(lllIIIllIllIll, lllIIIlllIIIII, lllIIIllIlllll);
            lllIIIlllIIIll.Inst\u00e0ncies = new ArrayList();
            lllIIIlllIIIll.developmentState = DevelopmentState.Release;
            lllIIIlllIIIll.developmentState = lllIIIllIllllI;
        }

        void autoJoin(Player lllIIIlIIllIII) {
            ContenidorJoc lllIIIlIIllIll;
            if (ContenidorJoc.lIIIIIlIlll((int)lllIIIlIIllIll.canAutoJoin().booleanValue())) {
                if (ContenidorJoc.lIIIIIlIlIl(lllIIIlIIllIll.Inst\u00e0ncies.size())) {
                    "".length();
                    lllIIIlIIllIll.addMap(null);
                }
                lllIIIlIIllIll.Inst\u00e0ncies.get(lIIIllIIl[0]).Join(lllIIIlIIllIII);
            }
        }

        public DevelopmentState getDevelopmentState() {
            ContenidorJoc lllIIIllIlIlIl;
            return lllIIIllIlIlIl.developmentState;
        }

        private static boolean lIIIIIlIllI(Object object) {
            return object != null;
        }

        public Joc addMap(Integer lllIIIlIlIIlIl) {
            ContenidorJoc lllIIIlIlIIlII;
            try {
                Joc lllIIIlIlIlIIl = (Joc)((Object)lllIIIlIlIIlII.ClassMapa.newInstance());
                if (ContenidorJoc.lIIIIIlIllI(lllIIIlIlIIlIl)) {
                    lllIIIlIlIlIIl.setMultiMapId(lllIIIlIlIIlIl);
                }
                lllIIIlIlIlIIl.initialize();
                "".length();
                lllIIIlIlIIlII.Inst\u00e0ncies.add(lllIIIlIlIlIIl);
                return lllIIIlIlIlIIl;
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException lllIIIlIlIIlll) {
                lllIIIlIlIIlll.printStackTrace();
                "".length();
                Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIIlllI[lIIIllIIl[13]]).append(lllIIIlIlIIlII.nom)));
                "".length();
                Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIIlllI[lIIIllIIl[14]]).append(lllIIIlIlIIlll.getMessage())));
                "".length();
                Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIIlllI[lIIIllIIl[15]]).append(lllIIIlIlIIlll.getClass().getName())));
                if (ContenidorJoc.lIIIIIlIlll(lllIIIlIlIIlll instanceof InvocationTargetException)) {
                    InvocationTargetException lllIIIlIlIlIII = (InvocationTargetException)lllIIIlIlIIlll;
                    "".length();
                    Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIIlllI[lIIIllIIl[16]]).append(lllIIIlIlIlIII.getTargetException().getMessage())));
                    "".length();
                    Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIIlllI[lIIIllIIl[17]]).append(lllIIIlIlIlIII.getTargetException().toString())));
                }
                return null;
            }
        }

        private static boolean lIIIIIllllI(int n, int n2) {
            return n == n2;
        }

        @Override
        public ArrayList<String> getDescription() {
            ContenidorJoc lllIIIllIIlIIl;
            ArrayList<String> lllIIIllIIlIII = super.getDescription();
            lllIIIllIIlIII.add(lIIIllIIl[0], String.valueOf(new StringBuilder().append(lllIIIllIIlIIl.getRatingString()).append((Object)ChatColor.DARK_GRAY).append(lIIIIlllI[lIIIllIIl[9]]).append((double)Math.round(lllIIIllIIlIIl.getRating() * 10.0) / 10.0).append(lIIIIlllI[lIIIllIIl[10]])));
            if (ContenidorJoc.lIIIIIlIlIl(lllIIIllIIlIIl.getMapCount())) {
                lllIIIllIIlIII.add(lIIIllIIl[1], String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIIlllI[lIIIllIIl[11]])));
            }
            return lllIIIllIIlIII;
        }

        void checkNecessary(Joc lllIIIIlllIIll) {
            if (ContenidorJoc.lIIIIIllIlI((Object)lllIIIIlllIIll.getWorld())) {
                "".length();
                Bukkit.broadcastMessage((String)lIIIIlllI[lIIIllIIl[18]]);
                return;
            }
            if (ContenidorJoc.lIIIIIlIlll((int)lllIIIIlllIIll.getEditMode().booleanValue())) {
                return;
            }
            if (ContenidorJoc.lIIIIIlIlIl(lllIIIIlllIIll.getWorld().getPlayers().size())) {
                ContenidorJoc lllIIIIlllIllI;
                lllIIIIlllIIll.clearExternals();
                lllIIIIlllIIll.deleteVirtualWorld();
                "".length();
                lllIIIIlllIllI.Inst\u00e0ncies.remove((Object)lllIIIIlllIIll);
                lllIIIIlllIIll.destroyEventBus();
                System.gc();
            }
        }

        @Override
        public String getDisplayName() {
            ContenidorJoc lllIIIlIllIlII;
            return String.valueOf(new StringBuilder().append(super.getDisplayName()).append(lIIIIlllI[lIIIllIIl[12]]).append(lllIIIlIllIlII.getDevelopmentString()));
        }

        @EventHandler
        public void onPlayerChangedWorld(PlayerChangedWorldEvent lllIIIIllllIll) {
            ContenidorJoc lllIIIIlllllII;
            Player lllIIIIllllllI = lllIIIIllllIll.getPlayer();
            Joc lllIIIIlllllIl = lllIIIIlllllII.getInst\u00e0nciaFromWorld(lllIIIIllllIll.getFrom());
            if (ContenidorJoc.lIIIIIlIllI((Object)lllIIIIlllllIl)) {
                lllIIIIlllllII.checkNecessary(lllIIIIlllllIl);
            }
        }

        private static boolean lIIIIIlllII(int n) {
            return n >= 0;
        }

        public DyeColor getGameColor(Joc lllIIIIlllIIII) {
            switch (1.$SwitchMap$com$biel$lobby$mapes$Joc$GameState[lllIIIIlllIIII.getGameState().ordinal()]) {
                case 1: {
                    return DyeColor.RED;
                }
                case 2: {
                    return DyeColor.YELLOW;
                }
                case 3: {
                    return DyeColor.GREEN;
                }
                case 4: {
                    return DyeColor.PURPLE;
                }
                case 5: {
                    return DyeColor.GRAY;
                }
                case 6: {
                    return DyeColor.BLUE;
                }
            }
            return DyeColor.WHITE;
        }

        public void setInst\u00e0ncies(ArrayList<Joc> lllIIIIIlIIIll) {
            lllIIIIIlIIlII.Inst\u00e0ncies = lllIIIIIlIIIll;
        }

        public String getRatingString() {
            ContenidorJoc lllIIIIlIlllll;
            Character lllIIIIlIllllI = Character.valueOf(lIIIllIIl[19]);
            int lllIIIIlIlllIl = lIIIllIIl[10];
            double lllIIIIlIlllII = lllIIIIlIlllll.getRating();
            int lllIIIIlIllIll = (int)Math.round(lllIIIIlIlllII / (double)lllIIIIlIlllIl);
            String lllIIIIlIllIlI = lIIIIlllI[lIIIllIIl[20]];
            ChatColor lllIIIIlIllIIl = ChatColor.GOLD;
            if (ContenidorJoc.lIIIIIlllII(ContenidorJoc.lIIIIIllIll(lllIIIIlIlllII, 75.0))) {
                lllIIIIlIllIIl = ChatColor.AQUA;
            }
            lllIIIIlIllIlI = String.valueOf(new StringBuilder().append(lllIIIIlIllIlI).append((Object)lllIIIIlIllIIl));
            int lllIIIIllIIIII = lIIIllIIl[0];
            while (ContenidorJoc.lIIIIIlllIl(lllIIIIllIIIII, lllIIIIlIlllIl)) {
                if (ContenidorJoc.lIIIIIllllI(lllIIIIllIIIII, lllIIIIlIllIll)) {
                    lllIIIIlIllIlI = String.valueOf(new StringBuilder().append(lllIIIIlIllIlI).append((Object)ChatColor.GRAY));
                }
                lllIIIIlIllIlI = String.valueOf(new StringBuilder().append(lllIIIIlIllIlI).append(lllIIIIlIllllI));
                ++lllIIIIllIIIII;
                "".length();
                if (null == null) continue;
                return null;
            }
            return lllIIIIlIllIlI;
        }

        private static int lIIIIIllIll(double d, double d2) {
            return (int)(d DCMPL d2);
        }

        public Joc getTempInstance() {
            try {
                ContenidorJoc lllIIIlIlIllll;
                return (Joc)((Object)lllIIIlIlIllll.ClassMapa.newInstance());
            }
            catch (IllegalAccessException | InstantiationException lllIIIlIllIIIl) {
                lllIIIlIllIIIl.printStackTrace();
                return null;
            }
        }

        Joc getInst\u00e0nciaFromWorld(World lllIIIlIIIlIIl) {
            ContenidorJoc lllIIIlIIIlIII;
            Iterator<Joc> lllIIIlIIIIllI = lllIIIlIIIlIII.Inst\u00e0ncies.iterator();
            while (ContenidorJoc.lIIIIIlIlll((int)lllIIIlIIIIllI.hasNext())) {
                Joc lllIIIlIIIlIll = lllIIIlIIIIllI.next();
                if (ContenidorJoc.lIIIIIlIlll((int)lllIIIlIIIlIll.world.getName().equals(lllIIIlIIIlIIl.getName()))) {
                    return lllIIIlIIIlIll;
                }
                "".length();
                if (null == null) continue;
                return null;
            }
            return null;
        }

        private static boolean lIIIIIlIlll(int n) {
            return n != 0;
        }

        private static boolean lIIIIIllIlI(Object object) {
            return object == null;
        }

        public String getDevelopmentString() {
            ContenidorJoc lllIIIllIIllIl;
            switch (1.$SwitchMap$com$biel$lobby$GestorMapes$DevelopmentState[lllIIIllIIllIl.developmentState.ordinal()]) {
                case 1: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIlllI[lIIIllIIl[0]]));
                }
                case 2: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_RED).append(lIIIIlllI[lIIIllIIl[1]]));
                }
                case 3: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIIIIlllI[lIIIllIIl[2]]));
                }
                case 4: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.STRIKETHROUGH).append(lIIIIlllI[lIIIllIIl[3]]).append((Object)ChatColor.RED).append(lIIIIlllI[lIIIllIIl[4]]));
                }
                case 5: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIIlllI[lIIIllIIl[5]]));
                }
                case 6: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIIlllI[lIIIllIIl[6]]));
                }
                case 7: {
                    return lIIIIlllI[lIIIllIIl[7]];
                }
            }
            return lIIIIlllI[lIIIllIIl[8]];
        }

        @Override
        void playerClick(Player lllIIIlIllIlll) {
            ContenidorJoc lllIIIlIlllIII;
            lllIIIlIlllIII.ObrirMenu(lllIIIlIllIlll);
        }

        Boolean canAutoJoin() {
            ContenidorJoc lllIIIlIIllllI;
            boolean bl;
            if (ContenidorJoc.lIIIIIllIII(lllIIIlIIllllI.Inst\u00e0ncies.size(), lIIIllIIl[1])) {
                bl = lIIIllIIl[1];
                "".length();
                if (((75 ^ 123) & ~(112 ^ 64)) < 0) {
                    return null;
                }
            } else {
                bl = lIIIllIIl[0];
            }
            return bl;
        }

        private static String llllIlllIl(String llIlllllllIIlI, String llIllllllIllll) {
            try {
                SecretKeySpec llIlllllllIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllllllIllll.getBytes(StandardCharsets.UTF_8)), lIIIllIIl[8]), "DES");
                Cipher llIlllllllIlII = Cipher.getInstance("DES");
                llIlllllllIlII.init(lIIIllIIl[2], llIlllllllIlIl);
                return new String(llIlllllllIlII.doFinal(Base64.getDecoder().decode(llIlllllllIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIlllllllIIll) {
                llIlllllllIIll.printStackTrace();
                return null;
            }
        }

        @Override
        public int getPlayerAmount() {
            ContenidorJoc lllIIIllIIIIll;
            return lllIIIllIIIIll.Inst\u00e0ncies.stream().mapToInt(lllIIIIIIIIlll -> lllIIIIIIIIlll.getPlayers().size()).sum();
        }

        static {
            ContenidorJoc.lIIIIIlIIll();
            ContenidorJoc.lIIIIIIlIIl();
        }

        private static boolean lIIIIIllIII(int n, int n2) {
            return n <= n2;
        }

        private static void lIIIIIlIIll() {
            lIIIllIIl = new int[35];
            ContenidorJoc.lIIIllIIl[0] = (16 ^ 37 ^ (40 ^ 6)) & (25 ^ 79 ^ (209 ^ 156) ^ -" ".length());
            ContenidorJoc.lIIIllIIl[1] = " ".length();
            ContenidorJoc.lIIIllIIl[2] = "  ".length();
            ContenidorJoc.lIIIllIIl[3] = "   ".length();
            ContenidorJoc.lIIIllIIl[4] = 62 ^ 58;
            ContenidorJoc.lIIIllIIl[5] = 180 ^ 187 ^ (167 ^ 173);
            ContenidorJoc.lIIIllIIl[6] = 32 ^ 38;
            ContenidorJoc.lIIIllIIl[7] = 165 ^ 195 ^ (58 ^ 91);
            ContenidorJoc.lIIIllIIl[8] = 173 ^ 165;
            ContenidorJoc.lIIIllIIl[9] = 128 ^ 137;
            ContenidorJoc.lIIIllIIl[10] = 62 + 71 - -19 + 24 ^ 167 + 40 - 70 + 49;
            ContenidorJoc.lIIIllIIl[11] = 39 ^ 44;
            ContenidorJoc.lIIIllIIl[12] = 76 ^ 60 ^ (226 ^ 158);
            ContenidorJoc.lIIIllIIl[13] = 35 ^ 46;
            ContenidorJoc.lIIIllIIl[14] = 48 ^ 70 ^ (227 ^ 155);
            ContenidorJoc.lIIIllIIl[15] = 103 + 51 - 80 + 66 ^ 92 + 58 - 123 + 104;
            ContenidorJoc.lIIIllIIl[16] = 180 ^ 164;
            ContenidorJoc.lIIIllIIl[17] = 43 ^ 58;
            ContenidorJoc.lIIIllIIl[18] = 127 ^ 109;
            ContenidorJoc.lIIIllIIl[19] = -22722 & 32751;
            ContenidorJoc.lIIIllIIl[20] = 28 ^ 72 ^ (199 ^ 128);
            ContenidorJoc.lIIIllIIl[21] = 167 ^ 179;
            ContenidorJoc.lIIIllIIl[22] = 149 ^ 134 ^ (15 ^ 7);
            ContenidorJoc.lIIIllIIl[23] = 26 ^ 15;
            ContenidorJoc.lIIIllIIl[24] = 6 ^ 16;
            ContenidorJoc.lIIIllIIl[25] = (94 ^ 98) + (73 ^ 14) - (87 ^ 116) + (95 ^ 105);
            ContenidorJoc.lIIIllIIl[26] = 28 ^ 11;
            ContenidorJoc.lIIIllIIl[27] = 137 ^ 145;
            ContenidorJoc.lIIIllIIl[28] = 98 ^ 123;
            ContenidorJoc.lIIIllIIl[29] = 2 + 108 - 56 + 83 ^ 105 + 7 - 93 + 128;
            ContenidorJoc.lIIIllIIl[30] = 50 ^ 46;
            ContenidorJoc.lIIIllIIl[31] = 9 ^ 20;
            ContenidorJoc.lIIIllIIl[32] = 15 ^ 17;
            ContenidorJoc.lIIIllIIl[33] = 174 ^ 177;
            ContenidorJoc.lIIIllIIl[34] = 60 ^ 73 ^ (58 ^ 111);
        }

        private static boolean lIIIIIlIlIl(int n) {
            return n == 0;
        }
    }

}

