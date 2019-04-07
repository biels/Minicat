/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public abstract class JocObjectius
extends JocEquips {
    private static final /* synthetic */ String[] llIllI;
    private static final /* synthetic */ int[] lIIIIlI;

    private static boolean lllIlIll(int n, int n2) {
        return n < n2;
    }

    public void comprovarGuanyador() {
        JocObjectius lllIIlIIIIIIllI;
        Iterator lllIIlIIIIIIlII = lllIIlIIIIIIllI.Equips.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIIIIIlII.hasNext())) {
            JocEquips.Equip lllIIlIIIIIIlll = (JocEquips.Equip)lllIIlIIIIIIlII.next();
            EquipObjectius lllIIlIIIIIlIII = (EquipObjectius)lllIIlIIIIIIlll;
            if (JocObjectius.lllIlIIl((int)lllIIlIIIIIlIII.checkIntegrity().booleanValue())) {
                EquipObjectius lllIIlIIIIIlIIl = (EquipObjectius)lllIIlIIIIIIllI.obtenirEquipEnemic(lllIIlIIIIIIlll);
                lllIIlIIIIIIllI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llIllI[lIIIIlI[1]]).append((Object)ChatColor.BOLD).append(llIllI[lIIIIlI[2]]).append(lllIIlIIIIIlIIl.getAdjectiu()).append(llIllI[lIIIIlI[3]])));
                lllIIlIIIIIIllI.winGame(lllIIlIIIIIlIIl);
            }
            "".length();
            if ("  ".length() <= (31 ^ 39 ^ (21 ^ 41))) continue;
            return;
        }
    }

    private static boolean lllIIllI(int n) {
        return n != 0;
    }

    private static String lIlIlIIl(String lllIIIlIllIIlll, String lllIIIlIllIIllI) {
        try {
            SecretKeySpec lllIIIlIllIlIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIIlIllIIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIIIlIllIlIIl = Cipher.getInstance("Blowfish");
            lllIIIlIllIlIIl.init(lIIIIlI[2], lllIIIlIllIlIlI);
            return new String(lllIIIlIllIlIIl.doFinal(Base64.getDecoder().decode(lllIIIlIllIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIIlIllIlIII) {
            lllIIIlIllIlIII.printStackTrace();
            return null;
        }
    }

    private static void lIlIlIlI() {
        llIllI = new String[lIIIIlI[8]];
        JocObjectius.llIllI[JocObjectius.lIIIIlI[0]] = JocObjectius.lIlIIlll("AzcMCCQ4PBNNNCk7FQhnKSQTBDdseEtNKTk5Cg==", "LUfmG");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[1]] = JocObjectius.lIlIlIII("F/KB2hxShg4=", "QnaPC");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[2]] = JocObjectius.lIlIlIII("hiayG9JEjoH4BfeDbwZdDA==", "tInfF");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[3]] = JocObjectius.lIlIlIII("p5o8qF6PpnH8q3m+5gXLyw==", "MFoJa");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[4]] = JocObjectius.lIlIlIIl("678GFi9Fatxt1/KoPcad0A==", "jPTIV");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[5]] = JocObjectius.lIlIIlll("PhY4ES4FHScHbVlRew==", "qtRtM");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[6]] = JocObjectius.lIlIlIIl("0F0eh5ywkl05UqcDTTqh/DtXznHa55i20Dh3MiiKE7M=", "dDtwd");
        JocObjectius.llIllI[JocObjectius.lIIIIlI[7]] = JocObjectius.lIlIlIII("/WpREnNy2Y0=", "MQuZn");
    }

    @Override
    protected void updateScoreBoard(Player lllIIIllllIllII) {
        JocObjectius lllIIIllllIllll;
        super.updateScoreBoard(lllIIIllllIllII);
        if (JocObjectius.lllIIllI((int)lllIIIllllIllll.JocIniciat.booleanValue()) && JocObjectius.lllIlIIl((int)lllIIIllllIllll.JocFinalitzat.booleanValue())) {
            ArrayList<String> lllIIIlllllIllI = new ArrayList<String>();
            lllIIIllllIllll.Equips.stream().map(lllIIIllIIlIlII -> (EquipObjectius)lllIIIllIIlIlII).sorted((lllIIIllIIllIII, lllIIIllIIlIlll) -> Integer.compare(lllIIIllIIlIlll.getCompletedObjectives().size(), lllIIIllIIllIII.getCompletedObjectives().size())).forEach(lllIIIllIlIIIIl -> {
                "".length();
                lllIIIlllllIllI.add(String.valueOf(new StringBuilder().append((Object)lllIIIllIlIIIIl.getChatColor()).append(llIllI[lIIIIlI[7]]).append(lllIIIllIlIIIIl.getAdjectiu())));
                Iterator<Objectiu> lllIIIllIIllllI = lllIIIllIlIIIIl.getObjectius().iterator();
                while (JocObjectius.lllIIllI((int)lllIIIllIIllllI.hasNext())) {
                    Objectiu lllIIIllIlIIIll = lllIIIllIIllllI.next();
                    "".length();
                    lllIIIlllllIllI.add(lllIIIllIlIIIll.getScoreboardStatusLine());
                    "".length();
                    if ("  ".length() >= " ".length()) continue;
                    return;
                }
            });
            ScoreBoardUpdater.setScoreBoard(lllIIIllllIllII, llIllI[lIIIIlI[4]], lllIIIlllllIllI, null);
        }
        if (JocObjectius.lllIIllI((int)lllIIIllllIllll.JocFinalitzat.booleanValue())) {
            ArrayList<String> lllIIIlllllIIIl = new ArrayList<String>();
            ArrayList<Integer> lllIIIlllllIIII = new ArrayList<Integer>();
            Iterator lllIIIllllIlIIl = lllIIIllllIllll.Equips.iterator();
            while (JocObjectius.lllIIllI((int)lllIIIllllIlIIl.hasNext())) {
                JocEquips.Equip lllIIIlllllIIlI = (JocEquips.Equip)lllIIIllllIlIIl.next();
                EquipObjectius lllIIIlllllIlII = (EquipObjectius)lllIIIlllllIIlI;
                EquipObjectius lllIIIlllllIIll = (EquipObjectius)lllIIIllllIllll.obtenirEquipEnemic(lllIIIlllllIIlI);
                Iterator<Player> lllIIIllllIIlIl = lllIIIlllllIlII.getPlayers().iterator();
                while (JocObjectius.lllIIllI((int)lllIIIllllIIlIl.hasNext())) {
                    Player lllIIIlllllIlIl = lllIIIllllIIlIl.next();
                    "".length();
                    lllIIIlllllIIIl.add(String.valueOf(new StringBuilder().append((Object)lllIIIlllllIlII.getChatColor()).append(lllIIIlllllIlIl.getName())));
                    "".length();
                    lllIIIlllllIIII.add((int)Math.round(lllIIIlllllIIll.getObjectiveRatio(lllIIIlllllIlIl) * 100.0));
                    "".length();
                    if (-" ".length() < " ".length()) continue;
                    return;
                }
                "".length();
                if ("   ".length() >= "  ".length()) continue;
                return;
            }
            ScoreBoardUpdater.setScoreBoard(lllIIIllllIllII, llIllI[lIIIIlI[5]], lllIIIlllllIIIl, lllIIIlllllIIII);
        }
    }

    private static void lllIIlIl() {
        lIIIIlI = new int[9];
        JocObjectius.lIIIIlI[0] = (32 ^ 23) & ~(150 ^ 161);
        JocObjectius.lIIIIlI[1] = " ".length();
        JocObjectius.lIIIIlI[2] = "  ".length();
        JocObjectius.lIIIIlI[3] = "   ".length();
        JocObjectius.lIIIIlI[4] = 57 ^ 64 ^ (17 ^ 108);
        JocObjectius.lIIIIlI[5] = 177 ^ 180;
        JocObjectius.lIIIIlI[6] = 235 ^ 139 ^ (253 ^ 155);
        JocObjectius.lIIIIlI[7] = 18 ^ 21;
        JocObjectius.lIIIIlI[8] = 82 ^ 90;
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent lllIIlIIlIIIIlI, Block lllIIlIIlIIIlII) {
        JocObjectius lllIIlIIlIIIllI;
        super.onBlockPlace(lllIIlIIlIIIIlI, lllIIlIIlIIIlII);
        Iterator lllIIlIIlIIIIII = lllIIlIIlIIIllI.Equips.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIlIIIIII.hasNext())) {
            JocEquips.Equip lllIIlIIlIIIlll = (JocEquips.Equip)lllIIlIIlIIIIII.next();
            EquipObjectius lllIIlIIlIIlIIl = (EquipObjectius)lllIIlIIlIIIlll;
            Objectiu lllIIlIIlIIlIII = lllIIlIIlIIIllI.getCloserObjective(lllIIlIIlIIIlII.getLocation(), lllIIlIIlIIlIIl.getObjectius());
            lllIIlIIlIIlIII.onBlockPlace(lllIIlIIlIIIIlI, lllIIlIIlIIIlII);
            "".length();
            if ("   ".length() >= ((163 ^ 198 ^ (18 ^ 120)) & (58 ^ 79 ^ (110 ^ 20) ^ -" ".length()))) continue;
            return;
        }
    }

    void ObjectiuCompletat() {
    }

    private static boolean lllIlIIl(int n) {
        return n == 0;
    }

    static {
        JocObjectius.lllIIlIl();
        JocObjectius.lIlIlIlI();
    }

    private static int lllIIlll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public double getGameProgressETA() {
        JocObjectius lllIIIlllIlIIII;
        double lllIIIlllIlIlII = lllIIIlllIlIIII.Equips.stream().map(lllIIIllIlIlIII -> (EquipObjectius)lllIIIllIlIlIII).map(lllIIIllIlIlIll -> lllIIIllIlIlIll.Objectius.size()).mapToInt(lllIIIllIlIlllI -> lllIIIllIlIlllI).sum();
        double lllIIIlllIlIIll = (double)lllIIIlllIlIIII.Equips.stream().map(lllIIIllIllIIlI -> (EquipObjectius)lllIIIllIllIIlI).map(lllIIIllIllIlII -> lllIIIllIllIlII.getCompletedObjectives().size()).mapToInt(lllIIIllIlllIII -> lllIIIllIlllIII).sum() / lllIIIlllIlIlII;
        Supplier<Stream> lllIIIlllIlIIlI = () -> {
            JocObjectius lllIIIlllIIIlII;
            return lllIIIlllIIIlII.Equips.stream().map(lllIIIllIlllIll -> (EquipObjectius)lllIIIllIlllIll).sorted((lllIIIlllIIIIII, lllIIIllIllllll) -> Integer.compare(lllIIIllIllllll.getCompletedObjectives().size(), lllIIIlllIIIIII.getCompletedObjectives().size()));
        };
        double lllIIIlllIlIIIl = ((double)((EquipObjectius)lllIIIlllIlIIlI.get().findFirst().get()).getCompletedObjectives().size() - lllIIIlllIlIIlI.get().skip(1L).map(lllIIIlllIIIlll -> lllIIIlllIIIlll.getCompletedObjectives().size()).mapToInt(lllIIIlllIIlIIl -> lllIIIlllIIlIIl).average().orElse(0.0)) / (double)Math.max(((EquipObjectius)lllIIIlllIlIIlI.get().findFirst().get()).getObjectius().size() - lIIIIlI[1], lIIIIlI[1]);
        return super.getGameProgressETA() * 0.45 + lllIIIlllIlIIll * 0.5 + lllIIIlllIlIIIl * 0.1;
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent lllIIlIIlIllIIl, Block lllIIlIIlIllIII) {
        JocObjectius lllIIlIIlIlIlll;
        super.onBlockBreak(lllIIlIIlIllIIl, lllIIlIIlIllIII);
        Iterator lllIIlIIlIlIlII = lllIIlIIlIlIlll.Equips.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIlIlIlII.hasNext())) {
            JocEquips.Equip lllIIlIIlIllIll = (JocEquips.Equip)lllIIlIIlIlIlII.next();
            EquipObjectius lllIIlIIlIlllIl = (EquipObjectius)lllIIlIIlIllIll;
            Objectiu lllIIlIIlIlllII = lllIIlIIlIlIlll.getCloserObjective(lllIIlIIlIllIII.getLocation(), lllIIlIIlIlllIl.getObjectius());
            lllIIlIIlIlllII.onBlockBreak(lllIIlIIlIllIIl, lllIIlIIlIllIII);
            "".length();
            if (-" ".length() == -" ".length()) continue;
            return;
        }
    }

    public ArrayList<Objectiu> obtenirObjectiusPly(Player lllIIlIIIlIlIlI) {
        JocObjectius lllIIlIIIlIlIIl;
        return ((EquipObjectius)lllIIlIIIlIlIIl.obtenirEquipEnemic(lllIIlIIIlIlIlI)).getObjectius();
    }

    @Override
    public void initTeams() {
        JocObjectius lllIIlIIllIlllI;
        super.initTeams();
        Iterator lllIIlIIllIllIl = lllIIlIIllIlllI.Equips.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIllIllIl.hasNext())) {
            JocEquips.Equip lllIIlIIlllIIII = (JocEquips.Equip)lllIIlIIllIllIl.next();
            EquipObjectius lllIIlIIlllIIIl = (EquipObjectius)lllIIlIIlllIIII;
            lllIIlIIlllIIIl.setObjectius(lllIIlIIllIlllI.getDesiredObjectivesTeam(lllIIlIIlllIIIl));
            "".length();
            if (-"   ".length() < 0) continue;
            return;
        }
    }

    public Objectiu getCloserObjective(Location lllIIlIIIllIlIl, ArrayList<Objectiu> lllIIlIIIllIlII) {
        Objectiu lllIIlIIIllIIll = lllIIlIIIllIlII.get(lIIIIlI[0]);
        Iterator<Objectiu> lllIIlIIIlIllll = lllIIlIIIllIlII.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIIlIllll.hasNext())) {
            Objectiu lllIIlIIIllIlll = lllIIlIIIlIllll.next();
            if (JocObjectius.lllIlIII(JocObjectius.lllIIlll(lllIIlIIIllIIll.getLocation().distanceSquared(lllIIlIIIllIlIl), lllIIlIIIllIlll.getLocation().distanceSquared(lllIIlIIIllIlIl)))) {
                lllIIlIIIllIIll = lllIIlIIIllIlll;
            }
            "".length();
            if ((31 ^ 26) > 0) continue;
            return null;
        }
        return lllIIlIIIllIIll;
    }

    private static String lIlIIlll(String lllIIIlIlllllII, String lllIIIlIlllIllI) {
        lllIIIlIlllllII = new String(Base64.getDecoder().decode(lllIIIlIlllllII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIIIlIllllIlI = new StringBuilder();
        char[] lllIIIlIllllIIl = lllIIIlIlllIllI.toCharArray();
        int lllIIIlIllllIII = lIIIIlI[0];
        char[] lllIIIlIlllIIlI = lllIIIlIlllllII.toCharArray();
        int lllIIIlIlllIIIl = lllIIIlIlllIIlI.length;
        int lllIIIlIlllIIII = lIIIIlI[0];
        while (JocObjectius.lllIlIll(lllIIIlIlllIIII, lllIIIlIlllIIIl)) {
            char lllIIIlIlllllIl = lllIIIlIlllIIlI[lllIIIlIlllIIII];
            "".length();
            lllIIIlIllllIlI.append((char)(lllIIIlIlllllIl ^ lllIIIlIllllIIl[lllIIIlIllllIII % lllIIIlIllllIIl.length]));
            ++lllIIIlIllllIII;
            ++lllIIIlIlllIIII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lllIIIlIllllIlI);
    }

    private static String lIlIlIII(String lllIIIllIIIlIlI, String lllIIIllIIIlIll) {
        try {
            SecretKeySpec lllIIIllIIIllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIIllIIIlIll.getBytes(StandardCharsets.UTF_8)), lIIIIlI[8]), "DES");
            Cipher lllIIIllIIIlllI = Cipher.getInstance("DES");
            lllIIIllIIIlllI.init(lIIIIlI[2], lllIIIllIIIllll);
            return new String(lllIIIllIIIlllI.doFinal(Base64.getDecoder().decode(lllIIIllIIIlIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIIllIIIllIl) {
            lllIIIllIIIllIl.printStackTrace();
            return null;
        }
    }

    private static boolean lllIlIII(int n) {
        return n > 0;
    }

    public JocObjectius() {
        JocObjectius lllIIlIIlllIlll;
    }

    protected abstract ArrayList<Objectiu> getDesiredObjectivesTeam(EquipObjectius var1);

    public JocEquips.Equip obtenirEquipObjectiu(Objectiu lllIIlIIIIlllll) {
        JocObjectius lllIIlIIIIllllI;
        Iterator<E> lllIIlIIIIlllII = lllIIlIIIIllllI.Equips.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIIIlllII.hasNext())) {
            JocEquips.Equip lllIIlIIIlIIIIl = (JocEquips.Equip)lllIIlIIIIlllII.next();
            EquipObjectius lllIIlIIIlIIIlI = (EquipObjectius)lllIIlIIIlIIIIl;
            if (JocObjectius.lllIIllI((int)lllIIlIIIlIIIlI.getObjectius().contains(lllIIlIIIIlllll))) {
                return lllIIlIIIlIIIIl;
            }
            "".length();
            if ("  ".length() > -" ".length()) continue;
            return null;
        }
        "".length();
        Bukkit.broadcastMessage((String)llIllI[lIIIIlI[0]]);
        return null;
    }

    protected int getObjectiveProtectionRadius() {
        JocObjectius lllIIIllllIIIII;
        int lllIIIlllIlllll = lIIIIlI[2];
        String lllIIIlllIllllI = llIllI[lIIIIlI[6]];
        if (JocObjectius.lllIIllI((int)lllIIIllllIIIII.pMapaActual().ExisteixPropietat(lllIIIlllIllllI))) {
            lllIIIlllIlllll = lllIIIllllIIIII.pMapaActual().ObtenirPropietatInt(lllIIIlllIllllI);
            "".length();
            if (-" ".length() == ((128 ^ 195) & ~(42 ^ 105))) {
                return (247 ^ 196) & ~(26 ^ 41);
            }
        } else {
            lllIIIllllIIIII.pMapaActual().EstablirPropietat(lllIIIlllIllllI, lllIIIlllIlllll);
        }
        return lllIIIlllIlllll;
    }

    public Boolean everyoneAlive() {
        JocObjectius lllIIlIIIIlIIll;
        Iterator<E> lllIIlIIIIlIIIl = lllIIlIIIIlIIll.Equips.iterator();
        while (JocObjectius.lllIIllI((int)lllIIlIIIIlIIIl.hasNext())) {
            JocEquips.Equip lllIIlIIIIlIlII = (JocEquips.Equip)lllIIlIIIIlIIIl.next();
            EquipObjectius lllIIlIIIIlIlIl = (EquipObjectius)lllIIlIIIIlIlII;
            if (JocObjectius.lllIlIIl((int)lllIIlIIIIlIlIl.checkIntegrity().booleanValue())) {
                return lIIIIlI[0];
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return lIIIIlI[1];
    }

    public class Objectiu {
        /* synthetic */ Player completer;
        /* synthetic */ Location location;
        /* synthetic */ Boolean completed;
        /* synthetic */ String nom;
        /* synthetic */ String verb;
        private static final /* synthetic */ int[] llllIIIl;
        private static final /* synthetic */ String[] lllIIlIl;
        /* synthetic */ Object info;

        public String getNom() {
            Objectiu lIIIIIlIllIIIII;
            return lIIIIIlIllIIIII.nom;
        }

        private static boolean lllIIlIIll(int n) {
            return n == 0;
        }

        private static boolean lllIIlIlII(int n) {
            return n != 0;
        }

        private static String llIllllllI(String lIIIIIIlllIIllI, String lIIIIIIlllIIlIl) {
            try {
                SecretKeySpec lIIIIIIlllIlIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIIIlllIIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIIIIlllIlIII = Cipher.getInstance("Blowfish");
                lIIIIIIlllIlIII.init(llllIIIl[2], lIIIIIIlllIlIIl);
                return new String(lIIIIIIlllIlIII.doFinal(Base64.getDecoder().decode(lIIIIIIlllIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIIIIlllIIlll) {
                lIIIIIIlllIIlll.printStackTrace();
                return null;
            }
        }

        public boolean canBeCompleted(Player lIIIIIlIIlllllI) {
            boolean bl;
            Objectiu lIIIIIlIlIIIIIl;
            if (Objectiu.lllIIlIIlI(lIIIIIlIlIIIIIl.JocObjectius.this.obtenirEquip(lIIIIIlIIlllllI).getId(), lIIIIIlIlIIIIIl.JocObjectius.this.obtenirEquipObjectiu(lIIIIIlIlIIIIIl).getId())) {
                bl = llllIIIl[1];
                "".length();
                if (((55 ^ 20 ^ (243 ^ 197)) & (246 ^ 128 ^ (39 ^ 68) ^ -" ".length())) != 0) {
                    return (boolean)((45 ^ 63 ^ (97 ^ 58)) & (50 ^ 6 ^ (214 ^ 171) ^ -" ".length()));
                }
            } else {
                bl = llllIIIl[0];
            }
            return bl;
        }

        public void misssatgeError(Player lIIIIIlIIlIIllI) {
            Objectiu lIIIIIlIIlIIlll;
            lIIIIIlIIlIIlll.JocObjectius.this.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lllIIlIl[llllIIIl[4]])));
        }

        private static void lllIIlIIIl() {
            llllIIIl = new int[9];
            Objectiu.llllIIIl[0] = (64 ^ 27) & ~(114 ^ 41);
            Objectiu.llllIIIl[1] = " ".length();
            Objectiu.llllIIIl[2] = "  ".length();
            Objectiu.llllIIIl[3] = "   ".length();
            Objectiu.llllIIIl[4] = 95 ^ 91;
            Objectiu.llllIIIl[5] = 144 ^ 149;
            Objectiu.llllIIIl[6] = 54 ^ 48;
            Objectiu.llllIIIl[7] = 187 ^ 188;
            Objectiu.llllIIIl[8] = 4 ^ 12;
        }

        public void setNom(String lIIIIIlIlIllIll) {
            lIIIIIlIlIlllII.nom = lIIIIIlIlIllIll;
        }

        private static boolean lllIIlIIlI(int n, int n2) {
            return n != n2;
        }

        public void anunciarCompletat(Player lIIIIIlIIlIlIll) {
            Objectiu lIIIIIlIIlIllII;
            lIIIIIlIIlIllII.JocObjectius.this.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)lIIIIIlIIlIllII.JocObjectius.this.obtenirEquip(lIIIIIlIIlIlIll).getChatColor()).append(lIIIIIlIIlIlIll.getName()).append((Object)ChatColor.WHITE).append(lllIIlIl[llllIIIl[1]]).append(lIIIIIlIIlIllII.verb).append(lllIIlIl[llllIIIl[2]]).append((Object)ChatColor.STRIKETHROUGH).append(lllIIlIl[llllIIIl[3]]).append((Object)lIIIIIlIIlIllII.JocObjectius.this.obtenirEquipObjectiu(lIIIIIlIIlIllII).getChatColor()).append(lIIIIIlIIlIllII.getNom())));
        }

        private static void lllIIIlIII() {
            lllIIlIl = new String[llllIIIl[7]];
            Objectiu.lllIIlIl[Objectiu.llllIIIl[0]] = Objectiu.llIllIlIlI("EjUYKT0ULhQt", "qZuYQ");
            Objectiu.lllIIlIl[Objectiu.llllIIIl[1]] = Objectiu.llIllIlIll("iRRyAT2FrVA=", "GWxJP");
            Objectiu.lllIIlIl[Objectiu.llllIIIl[2]] = Objectiu.llIllIlIll("PwywxqV2FvKOA0T5pe+n9w==", "ybGJH");
            Objectiu.lllIIlIl[Objectiu.llllIIIl[3]] = Objectiu.llIllllllI("IqmX9nw72lM=", "snZGb");
            Objectiu.lllIIlIl[Objectiu.llllIIIl[4]] = Objectiu.llIllIlIll("riT6TMuKZtm/M5Wp5m9h+1wWa1BczB8kdun5E6zytHycxkBV6MRJ2A==", "dJgZJ");
            Objectiu.lllIIlIl[Objectiu.llllIIIl[5]] = Objectiu.llIllIlIll("CewS+qKZDbk=", "ageQe");
            Objectiu.lllIIlIl[Objectiu.llllIIIl[6]] = Objectiu.llIllllllI("EH9ggWd9MlE=", "bZJgc");
        }

        public Objectiu(String lIIIIIlIllIlIIl, Location lIIIIIlIllIlIII, Object lIIIIIlIllIIlll) {
            Objectiu lIIIIIlIllIIllI;
            lIIIIIlIllIIllI.completed = llllIIIl[0];
            lIIIIIlIllIIllI.verb = lllIIlIl[llllIIIl[0]];
            lIIIIIlIllIIllI.nom = lIIIIIlIllIlIIl;
            lIIIIIlIllIIllI.location = lIIIIIlIllIlIII;
            lIIIIIlIllIIllI.info = lIIIIIlIllIIlll;
        }

        public void setInfo(Object lIIIIIlIIllIIIl) {
            lIIIIIlIIllIIlI.info = lIIIIIlIIllIIIl;
        }

        public Boolean isCompleted() {
            Objectiu lIIIIIlIlIlIllI;
            return lIIIIIlIlIlIllI.completed;
        }

        public void setCompleted(Boolean lIIIIIlIlIlIIlI) {
            lIIIIIlIlIlIIIl.completed = lIIIIIlIlIlIIlI;
        }

        public void setCompleter(Player lIIIIIlIIlllIII) {
            lIIIIIlIIlllIIl.completer = lIIIIIlIIlllIII;
        }

        public Player getCompleter() {
            Objectiu lIIIIIlIlIIIlII;
            return lIIIIIlIlIIIlII.completer;
        }

        private static boolean lllIIlIlIl(int n, int n2) {
            return n < n2;
        }

        protected void onBlockBreak(BlockBreakEvent lIIIIIlIIIlIlll, Block lIIIIIlIIIlIllI) {
        }

        public String getScoreboardStatusLine() {
            Objectiu lIIIIIlIIIllIlI;
            if (Objectiu.lllIIlIlII((int)lIIIIIlIIIllIlI.isCompleted().booleanValue())) {
                return String.valueOf(new StringBuilder().append((Object)ChatColor.STRIKETHROUGH).append(lllIIlIl[llllIIIl[5]]).append(lIIIIIlIIIllIlI.getNom()));
            }
            return String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lllIIlIl[llllIIIl[6]]).append(lIIIIIlIIIllIlI.getNom()));
        }

        public Location getLocation() {
            Objectiu lIIIIIlIlIIllIl;
            return lIIIIIlIlIIllIl.location;
        }

        public Object getInfo() {
            Objectiu lIIIIIlIIllIllI;
            return lIIIIIlIIllIllI.info;
        }

        public void setLocation(Location lIIIIIlIlIIIlll) {
            lIIIIIlIlIIlIlI.location = lIIIIIlIlIIIlll;
        }

        public void complete(Player lIIIIIlIIlIIIIl) {
            Objectiu lIIIIIlIIlIIIlI;
            if (Objectiu.lllIIlIIll((int)lIIIIIlIIlIIIlI.JocObjectius.this.JocFinalitzat.booleanValue()) && Objectiu.lllIIlIIll((int)lIIIIIlIIlIIIlI.isCompleted().booleanValue()) && Objectiu.lllIIlIlII((int)lIIIIIlIIlIIIlI.canBeCompleted(lIIIIIlIIlIIIIl))) {
                lIIIIIlIIlIIIlI.setCompleter(lIIIIIlIIlIIIIl);
                lIIIIIlIIlIIIlI.setCompleted(llllIIIl[1]);
                lIIIIIlIIlIIIlI.JocObjectius.this.getPlayerInfo(lIIIIIlIIlIIIIl).setObjectivesCompleted(lIIIIIlIIlIIIlI.JocObjectius.this.getPlayerInfo(lIIIIIlIIlIIIIl).getObjectivesCompleted() + llllIIIl[1]);
                lIIIIIlIIlIIIlI.anunciarCompletat(lIIIIIlIIlIIIIl);
                lIIIIIlIIlIIIlI.playCompletionEffect();
                lIIIIIlIIlIIIlI.JocObjectius.this.updateScoreBoards();
                lIIIIIlIIlIIIlI.JocObjectius.this.comprovarGuanyador();
                "".length();
                if (-" ".length() > ((68 ^ 30) & ~(213 ^ 143))) {
                    return;
                }
            } else {
                lIIIIIlIIlIIIlI.misssatgeError(lIIIIIlIIlIIIIl);
            }
        }

        public void playCompletionEffect() {
            Objectiu lIIIIIlIIIlllII;
            lIIIIIlIIIlllII.completer.getWorld().playEffect(lIIIIIlIIIlllII.location, Effect.MOBSPAWNER_FLAMES, llllIIIl[0]);
            lIIIIIlIIIlllII.completer.getWorld().playSound(lIIIIIlIIIlllII.location, Sound.ENTITY_WITHER_DEATH, 750.0f, 1.5f);
        }

        private static String llIllIlIll(String lIIIIIlIIIIlIll, String lIIIIIlIIIIlIlI) {
            try {
                SecretKeySpec lIIIIIlIIIIlllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIIIlIIIIlIlI.getBytes(StandardCharsets.UTF_8)), llllIIIl[8]), "DES");
                Cipher lIIIIIlIIIIllIl = Cipher.getInstance("DES");
                lIIIIIlIIIIllIl.init(llllIIIl[2], lIIIIIlIIIIlllI);
                return new String(lIIIIIlIIIIllIl.doFinal(Base64.getDecoder().decode(lIIIIIlIIIIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIIIlIIIIllII) {
                lIIIIIlIIIIllII.printStackTrace();
                return null;
            }
        }

        protected void onBlockPlace(BlockPlaceEvent lIIIIIlIIIlIlII, Block lIIIIIlIIIlIIll) {
        }

        private static String llIllIlIlI(String lIIIIIIllllIllI, String lIIIIIIlllllIlI) {
            lIIIIIIllllIllI = new String(Base64.getDecoder().decode(lIIIIIIllllIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIIIIIlllllIIl = new StringBuilder();
            char[] lIIIIIIlllllIII = lIIIIIIlllllIlI.toCharArray();
            int lIIIIIIllllIlll = llllIIIl[0];
            char[] lIIIIIIllllIIIl = lIIIIIIllllIllI.toCharArray();
            int lIIIIIIllllIIII = lIIIIIIllllIIIl.length;
            int lIIIIIIlllIllll = llllIIIl[0];
            while (Objectiu.lllIIlIlIl(lIIIIIIlllIllll, lIIIIIIllllIIII)) {
                char lIIIIIIllllllII = lIIIIIIllllIIIl[lIIIIIIlllIllll];
                "".length();
                lIIIIIIlllllIIl.append((char)(lIIIIIIllllllII ^ lIIIIIIlllllIII[lIIIIIIllllIlll % lIIIIIIlllllIII.length]));
                ++lIIIIIIllllIlll;
                ++lIIIIIIlllIllll;
                "".length();
                if ("  ".length() != 0) continue;
                return null;
            }
            return String.valueOf(lIIIIIIlllllIIl);
        }

        static {
            Objectiu.lllIIlIIIl();
            Objectiu.lllIIIlIII();
        }
    }

    public class EquipObjectius
    extends JocEquips.Equip {
        /* synthetic */ ArrayList<Objectiu> Objectius;
        private static final /* synthetic */ int[] llIlIIlI;

        public Double getObjectiveRatio(Player lIIIllIIllIIlll) {
            EquipObjectius lIIIllIIllIIlII;
            Double lIIIllIIllIIllI = lIIIllIIllIIlII.getCompletedObjectives(lIIIllIIllIIlll).size();
            Double lIIIllIIllIIlIl = lIIIllIIllIIlII.Objectius.size();
            return lIIIllIIllIIllI / lIIIllIIllIIlIl;
        }

        private static boolean llIIlIlIIl(int n) {
            return n == 0;
        }

        public Boolean checkIntegrity() {
            EquipObjectius lIIIllIIlIlIllI;
            boolean bl;
            if (EquipObjectius.llIIlIllIl(EquipObjectius.llIIlIllII(lIIIllIIlIlIllI.getIntegrity(), 0.0))) {
                bl = llIlIIlI[0];
                "".length();
                if ("   ".length() < 0) {
                    return null;
                }
            } else {
                bl = llIlIIlI[1];
            }
            return bl;
        }

        public ArrayList<Objectiu> getAliveObjectives() {
            EquipObjectius lIIIllIlIIIlIll;
            ArrayList<Objectiu> lIIIllIlIIIlIlI = new ArrayList<Objectiu>();
            Iterator<Objectiu> lIIIllIlIIIIlll = lIIIllIlIIIlIll.Objectius.iterator();
            while (EquipObjectius.llIIlIlIII((int)lIIIllIlIIIIlll.hasNext())) {
                Objectiu lIIIllIlIIIllII = lIIIllIlIIIIlll.next();
                if (EquipObjectius.llIIlIlIIl((int)lIIIllIlIIIllII.isCompleted().booleanValue())) {
                    "".length();
                    lIIIllIlIIIlIlI.add(lIIIllIlIIIllII);
                }
                "".length();
                if ((119 ^ 115) == (196 ^ 192)) continue;
                return null;
            }
            return lIIIllIlIIIlIlI;
        }

        private static boolean llIIlIllIl(int n) {
            return n > 0;
        }

        public EquipObjectius(DyeColor lIIIllIlIlIIIlI, String lIIIllIlIIlllIl) {
            EquipObjectius lIIIllIlIlIIIII;
            super(lIIIllIlIlIIIlI, lIIIllIlIIlllIl);
            lIIIllIlIlIIIII.Objectius = new ArrayList();
        }

        static {
            EquipObjectius.llIIlIIlll();
        }

        void initObjectives() {
            EquipObjectius lIIIllIlIIllIlI;
            lIIIllIlIIllIlI.Objectius = lIIIllIlIIllIlI.JocObjectius.this.getDesiredObjectivesTeam(lIIIllIlIIllIlI);
        }

        private static boolean llIIlIlIII(int n) {
            return n != 0;
        }

        public ArrayList<Objectiu> getCompletedObjectives(Player lIIIllIIlllIIII) {
            EquipObjectius lIIIllIIlllIlII;
            ArrayList<Objectiu> lIIIllIIlllIIlI = new ArrayList<Objectiu>();
            Iterator<Objectiu> lIIIllIIllIlllI = lIIIllIIlllIlII.getCompletedObjectives().iterator();
            while (EquipObjectius.llIIlIlIII((int)lIIIllIIllIlllI.hasNext())) {
                Objectiu lIIIllIIlllIlIl = lIIIllIIllIlllI.next();
                if (EquipObjectius.llIIlIlIll((Object)lIIIllIIlllIlIl.getCompleter(), (Object)lIIIllIIlllIIII)) {
                    "".length();
                    lIIIllIIlllIIlI.add(lIIIllIIlllIlIl);
                }
                "".length();
                if (((41 + 140 - 82 + 95 ^ 66 + 15 - 56 + 105) & (73 ^ 71 ^ (227 ^ 173) ^ -" ".length())) < " ".length()) continue;
                return null;
            }
            return lIIIllIIlllIIlI;
        }

        public ArrayList<Objectiu> getCompletedObjectives() {
            EquipObjectius lIIIllIlIIIIIII;
            ArrayList<Objectiu> lIIIllIIlllllll = new ArrayList<Objectiu>();
            Iterator<Objectiu> lIIIllIIlllllII = lIIIllIlIIIIIII.Objectius.iterator();
            while (EquipObjectius.llIIlIlIII((int)lIIIllIIlllllII.hasNext())) {
                Objectiu lIIIllIlIIIIIIl = lIIIllIIlllllII.next();
                if (EquipObjectius.llIIlIlIlI((int)lIIIllIlIIIIIIl.isCompleted().booleanValue(), llIlIIlI[0])) {
                    "".length();
                    lIIIllIIlllllll.add(lIIIllIlIIIIIIl);
                }
                "".length();
                if (null == null) continue;
                return null;
            }
            return lIIIllIIlllllll;
        }

        private static int llIIlIllII(double d, double d2) {
            return (int)(d DCMPL d2);
        }

        private static boolean llIIlIlIlI(int n, int n2) {
            return n == n2;
        }

        private static void llIIlIIlll() {
            llIlIIlI = new int[2];
            EquipObjectius.llIlIIlI[0] = " ".length();
            EquipObjectius.llIlIIlI[1] = (9 ^ 83) & ~(199 ^ 157);
        }

        public void setObjectius(ArrayList<Objectiu> lIIIllIlIIlIIll) {
            lIIIllIlIIlIlII.Objectius = lIIIllIlIIlIIll;
        }

        public ArrayList<Objectiu> getObjectius() {
            EquipObjectius lIIIllIlIIlIlll;
            return lIIIllIlIIlIlll.Objectius;
        }

        private static boolean llIIlIlIll(Object object, Object object2) {
            return object == object2;
        }

        public Double getIntegrity() {
            EquipObjectius lIIIllIIlIllIlI;
            Double lIIIllIIlIlllII = lIIIllIIlIllIlI.getAliveObjectives().size();
            Double lIIIllIIlIllIll = lIIIllIIlIllIlI.Objectius.size();
            return lIIIllIIlIlllII / lIIIllIIlIllIll;
        }
    }

    public class ObjectiuWoolPlace
    extends ObjectiuBlockChange {
        private static final /* synthetic */ int[] llIIl;
        /* synthetic */ DyeColor color;
        private static final /* synthetic */ String[] lIIll;

        public ObjectiuWoolPlace(String llllIlllIlIlIIl, Location llllIlllIlIllIl, DyeColor llllIlllIlIllII) {
            ObjectiuWoolPlace llllIlllIlIlIll;
            super(llllIlllIlIlIIl, llllIlllIlIllIl, Material.WOOL, llllIlllIlIllII.getWoolData());
            llllIlllIlIlIll.color = llllIlllIlIllII;
        }

        private static boolean lIIlIlI(int n, int n2) {
            return n < n2;
        }

        @Override
        public String getScoreboardStatusLine() {
            ObjectiuWoolPlace llllIlllIIllIlI;
            char c;
            if (ObjectiuWoolPlace.lIIlIIl((int)llllIlllIIllIlI.isCompleted().booleanValue())) {
                c = llIIl[1];
                "".length();
                if (((77 ^ 1) & ~(76 ^ 0)) != 0) {
                    return null;
                }
            } else {
                c = llIIl[2];
            }
            String llllIlllIIllIII = String.valueOf(new StringBuilder().append(lIIll[llIIl[0]]).append((Object)ColorConverter.dyeToChat(llllIlllIIllIlI.color)).append(c));
            return String.valueOf(new StringBuilder().append(llllIlllIIllIII).append(lIIll[llIIl[3]]).append((Object)ChatColor.WHITE).append(llllIlllIIllIlI.getNom()));
        }

        static {
            ObjectiuWoolPlace.lIIIlll();
            ObjectiuWoolPlace.lllllI();
        }

        private static boolean lIIlIIl(int n) {
            return n != 0;
        }

        private static String lllIll(String llllIllIllllllI, String llllIllIllllIIl) {
            try {
                SecretKeySpec llllIlllIIIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIllIllllIIl.getBytes(StandardCharsets.UTF_8)), llIIl[5]), "DES");
                Cipher llllIlllIIIIIII = Cipher.getInstance("DES");
                llllIlllIIIIIII.init(llIIl[4], llllIlllIIIIIIl);
                return new String(llllIlllIIIIIII.doFinal(Base64.getDecoder().decode(llllIllIllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIllIlllllll) {
                llllIllIlllllll.printStackTrace();
                return null;
            }
        }

        private static void lllllI() {
            lIIll = new String[llIIl[4]];
            ObjectiuWoolPlace.lIIll[ObjectiuWoolPlace.llIIl[0]] = ObjectiuWoolPlace.lllIll("JQbecinYk2E=", "fEutb");
            ObjectiuWoolPlace.lIIll[ObjectiuWoolPlace.llIIl[3]] = ObjectiuWoolPlace.llllIl("bg==", "NlBEe");
        }

        private static String llllIl(String llllIllIlIllllI, String llllIllIllIIIlI) {
            llllIllIlIllllI = new String(Base64.getDecoder().decode(llllIllIlIllllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllIllIllIIIIl = new StringBuilder();
            char[] llllIllIllIIIII = llllIllIllIIIlI.toCharArray();
            int llllIllIlIlllll = llIIl[0];
            char[] llllIllIlIllIIl = llllIllIlIllllI.toCharArray();
            int llllIllIlIllIII = llllIllIlIllIIl.length;
            int llllIllIlIlIllI = llIIl[0];
            while (ObjectiuWoolPlace.lIIlIlI(llllIllIlIlIllI, llllIllIlIllIII)) {
                char llllIllIllIIlII = llllIllIlIllIIl[llllIllIlIlIllI];
                "".length();
                llllIllIllIIIIl.append((char)(llllIllIllIIlII ^ llllIllIllIIIII[llllIllIlIlllll % llllIllIllIIIII.length]));
                ++llllIllIlIlllll;
                ++llllIllIlIlIllI;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.valueOf(llllIllIllIIIIl);
        }

        private static void lIIIlll() {
            llIIl = new int[6];
            ObjectiuWoolPlace.llIIl[0] = (36 ^ 55) & ~(5 ^ 22);
            ObjectiuWoolPlace.llIIl[1] = -2632 & 12239;
            ObjectiuWoolPlace.llIIl[2] = -(-283 & 18751) & (-4105 & 32189);
            ObjectiuWoolPlace.llIIl[3] = " ".length();
            ObjectiuWoolPlace.llIIl[4] = "  ".length();
            ObjectiuWoolPlace.llIIl[5] = 10 ^ 2;
        }
    }

    public class ObjectiuBlockChange
    extends Objectiu {
        private static final /* synthetic */ String[] lIIIlIIl;
        /* synthetic */ Material m;
        private static final /* synthetic */ int[] lIIIlIlI;
        /* synthetic */ byte data;

        private static String llllIIlll(String lIllIlllIIIllIl, String lIllIlllIIIllII) {
            lIllIlllIIIllIl = new String(Base64.getDecoder().decode(lIllIlllIIIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIllIlllIIIlIll = new StringBuilder();
            char[] lIllIlllIIIlIlI = lIllIlllIIIllII.toCharArray();
            int lIllIlllIIIlIIl = lIIIlIlI[1];
            char[] lIllIlllIIIIIll = lIllIlllIIIllIl.toCharArray();
            int lIllIlllIIIIIlI = lIllIlllIIIIIll.length;
            int lIllIlllIIIIIIl = lIIIlIlI[1];
            while (ObjectiuBlockChange.lllllIIlI(lIllIlllIIIIIIl, lIllIlllIIIIIlI)) {
                char lIllIlllIIIlllI = lIllIlllIIIIIll[lIllIlllIIIIIIl];
                "".length();
                lIllIlllIIIlIll.append((char)(lIllIlllIIIlllI ^ lIllIlllIIIlIlI[lIllIlllIIIlIIl % lIllIlllIIIlIlI.length]));
                ++lIllIlllIIIlIIl;
                ++lIllIlllIIIIIIl;
                "".length();
                if ("   ".length() > 0) continue;
                return null;
            }
            return String.valueOf(lIllIlllIIIlIll);
        }

        private static String llllIIllI(String lIllIlllIIlllIl, String lIllIlllIIllIlI) {
            try {
                SecretKeySpec lIllIlllIlIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlllIIllIlI.getBytes(StandardCharsets.UTF_8)), lIIIlIlI[4]), "DES");
                Cipher lIllIlllIIlllll = Cipher.getInstance("DES");
                lIllIlllIIlllll.init(lIIIlIlI[2], lIllIlllIlIIIII);
                return new String(lIllIlllIIlllll.doFinal(Base64.getDecoder().decode(lIllIlllIIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIllIlllIIllllI) {
                lIllIlllIIllllI.printStackTrace();
                return null;
            }
        }

        private static boolean llllIllll(int n, int n2) {
            return n == n2;
        }

        private static boolean lllllIIIl(Object object, Object object2) {
            return object == object2;
        }

        private static boolean lllllIIII(int n) {
            return n == 0;
        }

        public Boolean onChange(BlockPlaceEvent lIllIlllIlIlllI, Player lIllIlllIlIlIIl, Block lIllIlllIlIllII) {
            ObjectiuBlockChange lIllIlllIlIllll;
            if (ObjectiuBlockChange.llllIllII((int)lIllIlllIlIllll.canBeCompleted(lIllIlllIlIlIIl))) {
                int lIllIlllIllIIlI;
                int n;
                int n2;
                if (ObjectiuBlockChange.lllllIIIl((Object)lIllIlllIlIllll.m, (Object)lIllIlllIlIllII.getType())) {
                    n2 = lIIIlIlI[0];
                    "".length();
                    if ("   ".length() == " ".length()) {
                        return null;
                    }
                } else {
                    n2 = lIllIlllIllIIlI = lIIIlIlI[1];
                }
                if (ObjectiuBlockChange.llllIllll(lIllIlllIlIllll.data, lIllIlllIlIllII.getData())) {
                    n = lIIIlIlI[0];
                    "".length();
                    if (((66 + 141 - 90 + 130 ^ 26 + 10 - -32 + 103) & (40 ^ 56 ^ (7 ^ 75) ^ -" ".length())) < 0) {
                        return null;
                    }
                } else {
                    n = lIIIlIlI[1];
                }
                int lIllIlllIllIIIl = n;
                int lIllIlllIllIIII = lIllIlllIlIllII.getLocation().equals((Object)lIllIlllIlIllll.location);
                if (ObjectiuBlockChange.llllIllII(lIllIlllIllIIlI) && ObjectiuBlockChange.llllIllII(lIllIlllIllIIIl) && ObjectiuBlockChange.llllIllII(lIllIlllIllIIII)) {
                    lIllIlllIlIllll.complete(lIllIlllIlIlIIl);
                    return lIIIlIlI[0];
                }
                if (ObjectiuBlockChange.llllIllII(lIllIlllIllIIII)) {
                    lIllIlllIlIllll.JocObjectius.this.sendPlayerMessage(lIllIlllIlIlIIl, lIIIlIIl[lIIIlIlI[2]]);
                    lIllIlllIlIlllI.setCancelled(lIIIlIlI[0]);
                }
                return lIIIlIlI[1];
            }
            return lIIIlIlI[1];
        }

        static {
            ObjectiuBlockChange.llllIlIIl();
            ObjectiuBlockChange.llllIlIII();
        }

        @Override
        protected void onBlockPlace(BlockPlaceEvent lIllIlllIllllII, Block lIllIlllIlllIll) {
            ObjectiuBlockChange lIllIlllIllllIl;
            super.onBlockPlace(lIllIlllIllllII, lIllIlllIlllIll);
            if (ObjectiuBlockChange.llllIllII((int)lIllIlllIllllIl.isInsideProtectionRadius(lIllIlllIlllIll.getLocation()))) {
                Boolean lIllIllllIIIIIl = lIllIlllIllllIl.onChange(lIllIlllIllllII, lIllIlllIllllII.getPlayer(), lIllIlllIlllIll);
                if (ObjectiuBlockChange.llllIllll((int)lIllIllllIIIIIl.booleanValue(), lIIIlIlI[0])) {
                    return;
                }
                if (ObjectiuBlockChange.lllllIIII((int)lIllIlllIllllII.isCancelled())) {
                    lIllIlllIllllIl.JocObjectius.this.sendPlayerMessage(lIllIlllIllllII.getPlayer(), String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIlIIl[lIIIlIlI[0]])));
                }
                lIllIlllIllllII.setCancelled(lIIIlIlI[0]);
            }
        }

        private static boolean llllIllII(int n) {
            return n != 0;
        }

        public boolean isInsideProtectionRadius(Location lIllIllllIlIIII) {
            ObjectiuBlockChange lIllIllllIlIIIl;
            boolean bl;
            double lIllIllllIlIIlI = lIllIllllIlIIII.distance(lIllIllllIlIIIl.location);
            if (ObjectiuBlockChange.llllIlIll(ObjectiuBlockChange.llllIlIlI(lIllIllllIlIIlI, lIllIllllIlIIIl.JocObjectius.this.getObjectiveProtectionRadius()))) {
                bl = lIIIlIlI[0];
                "".length();
                if (((23 ^ 75) & ~(105 ^ 53)) > 0) {
                    return (boolean)((4 ^ 100) & ~(200 ^ 168));
                }
            } else {
                bl = lIIIlIlI[1];
            }
            return bl;
        }

        private static void llllIlIIl() {
            lIIIlIlI = new int[5];
            ObjectiuBlockChange.lIIIlIlI[0] = " ".length();
            ObjectiuBlockChange.lIIIlIlI[1] = (104 + 44 - 29 + 15 ^ 178 + 135 - 124 + 2) & (23 + 60 - -25 + 59 ^ 83 + 46 - 82 + 111 ^ -" ".length());
            ObjectiuBlockChange.lIIIlIlI[2] = "  ".length();
            ObjectiuBlockChange.lIIIlIlI[3] = "   ".length();
            ObjectiuBlockChange.lIIIlIlI[4] = 62 ^ 54;
        }

        private static boolean lllllIIlI(int n, int n2) {
            return n < n2;
        }

        private static void llllIlIII() {
            lIIIlIIl = new String[lIIIlIlI[3]];
            ObjectiuBlockChange.lIIIlIIl[ObjectiuBlockChange.lIIIlIlI[1]] = ObjectiuBlockChange.llllIIllI("i0whypzmcPIw8j2UmzXgLX9esdKYB5JEM6biJY07di4=", "jwzoW");
            ObjectiuBlockChange.lIIIlIIl[ObjectiuBlockChange.lIIIlIlI[0]] = ObjectiuBlockChange.llllIIlll("JTpWIggfJlY/CA88EDsECidWNwtLOBk8EgYwGCY=", "kUvRg");
            ObjectiuBlockChange.lIIIlIIl[ObjectiuBlockChange.lIIIlIlI[2]] = ObjectiuBlockChange.llllIIlll("MTwbNiYcJwotMVg+DmQlFDMBJWkbPR02LAsiACosFiY=", "xRoDI");
        }

        private static int llllIlIlI(double d, double d2) {
            return (int)(d DCMPG d2);
        }

        @Override
        protected void onBlockBreak(BlockBreakEvent lIllIllllIIlIlI, Block lIllIllllIIlIIl) {
            ObjectiuBlockChange lIllIllllIIlIll;
            super.onBlockBreak(lIllIllllIIlIlI, lIllIllllIIlIIl);
            if (ObjectiuBlockChange.llllIllII((int)lIllIllllIIlIll.isInsideProtectionRadius(lIllIllllIIlIIl.getLocation()))) {
                lIllIllllIIlIlI.setCancelled(lIIIlIlI[0]);
                lIllIllllIIlIll.JocObjectius.this.sendPlayerMessage(lIllIllllIIlIlI.getPlayer(), String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIlIIl[lIIIlIlI[1]])));
            }
        }

        public ObjectiuBlockChange(String lIllIllllIllIll, Location lIllIlllllIIIII, Material lIllIllllIlllll, byte lIllIllllIllIII) {
            ObjectiuBlockChange lIllIlllllIIIll;
            super(lIllIllllIllIll, lIllIlllllIIIII, lIllIllllIllIII);
            lIllIlllllIIIll.m = lIllIllllIlllll;
            lIllIlllllIIIll.data = lIllIllllIllIII;
        }

        private static boolean llllIlIll(int n) {
            return n <= 0;
        }
    }

    public class ObjectiuBlockBreak
    extends Objectiu {
        private static final /* synthetic */ String[] lIIIlIIlI;
        private static final /* synthetic */ int[] lIIIlIIll;

        private static boolean lIIIIIIIIII(int n) {
            return n != 0;
        }

        private static String lllllllIlI(String lllIlIllIllllI, String lllIlIllIlllll) {
            try {
                SecretKeySpec lllIlIlllIIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIlIllIlllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lllIlIlllIIIlI = Cipher.getInstance("Blowfish");
                lllIlIlllIIIlI.init(lIIIlIIll[2], lllIlIlllIIIll);
                return new String(lllIlIlllIIIlI.doFinal(Base64.getDecoder().decode(lllIlIllIllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllIlIlllIIIIl) {
                lllIlIlllIIIIl.printStackTrace();
                return null;
            }
        }

        private static void llllllllIl() {
            lIIIlIIlI = new String[lIIIlIIll[1]];
            ObjectiuBlockBreak.lIIIlIIlI[ObjectiuBlockBreak.lIIIlIIll[0]] = ObjectiuBlockBreak.lllllllIlI("/1PqivHO3GMp0x4MqYeBdQ==", "HRtfM");
        }

        public ObjectiuBlockBreak(String lllIlIlllllIIl, Location lllIlIllllIlII) {
            ObjectiuBlockBreak lllIlIlllllIll;
            super(lllIlIlllllIIl, lllIlIllllIlII, null);
            lllIlIlllllIll.verb = lIIIlIIlI[lIIIlIIll[0]];
        }

        static {
            ObjectiuBlockBreak.llllllllll();
            ObjectiuBlockBreak.llllllllIl();
        }

        private static void llllllllll() {
            lIIIlIIll = new int[3];
            ObjectiuBlockBreak.lIIIlIIll[0] = (45 ^ 1 ^ (16 ^ 96)) & (73 ^ 70 ^ (213 ^ 134) ^ -" ".length());
            ObjectiuBlockBreak.lIIIlIIll[1] = " ".length();
            ObjectiuBlockBreak.lIIIlIIll[2] = "  ".length();
        }

        @Override
        protected void onBlockBreak(BlockBreakEvent lllIlIlllIlIlI, Block lllIlIlllIllIl) {
            ObjectiuBlockBreak lllIlIlllIlIll;
            super.onBlockBreak(lllIlIlllIlIlI, lllIlIlllIllIl);
            Player lllIlIlllIllII = lllIlIlllIlIlI.getPlayer();
            if (ObjectiuBlockBreak.lIIIIIIIIII((int)lllIlIlllIllIl.getLocation().equals((Object)lllIlIlllIlIll.location))) {
                if (ObjectiuBlockBreak.lIIIIIIIIII((int)lllIlIlllIlIll.canBeCompleted(lllIlIlllIllII))) {
                    lllIlIlllIlIll.complete(lllIlIlllIllII);
                    "".length();
                    if ("   ".length() > "   ".length()) {
                        return;
                    }
                } else {
                    lllIlIlllIlIlI.setCancelled(lIIIlIIll[1]);
                }
            }
        }
    }

}

