/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamDominion;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Dominion
extends JocTeamDominion {
    private static final /* synthetic */ String[] lIllllI;
    private static final /* synthetic */ int[] llIllII;

    private static boolean lIllllIII(int n, int n2) {
        return n < n2;
    }

    private static void lIlllIlIl() {
        llIllII = new int[15];
        Dominion.llIllII[0] = (89 + 80 - 48 + 86 ^ 79 + 20 - 47 + 91) & (97 ^ 15 ^ (119 ^ 89) ^ -" ".length());
        Dominion.llIllII[1] = " ".length();
        Dominion.llIllII[2] = "  ".length();
        Dominion.llIllII[3] = "   ".length();
        Dominion.llIllII[4] = 142 + 30 - 112 + 125 ^ 48 + 184 - 134 + 91;
        Dominion.llIllII[5] = 56 ^ 7 ^ (128 ^ 186);
        Dominion.llIllII[6] = 113 ^ 119;
        Dominion.llIllII[7] = 96 ^ 9 ^ (2 ^ 108);
        Dominion.llIllII[8] = 60 ^ 54;
        Dominion.llIllII[9] = (98 ^ 42) + (42 ^ 35) - (109 ^ 105) + (73 ^ 50);
        Dominion.llIllII[10] = 45 ^ 105 ^ (37 ^ 93);
        Dominion.llIllII[11] = 45 ^ 14 ^ (69 ^ 2);
        Dominion.llIllII[12] = -11777 & 12156;
        Dominion.llIllII[13] = 108 ^ 78 ^ (55 ^ 29);
        Dominion.llIllII[14] = 120 + 30 - 33 + 20 ^ 7 + 108 - 5 + 18;
    }

    private static String lIlIIllII(String llIIIllllIllllI, String llIIIllllIlllIl) {
        try {
            SecretKeySpec llIIIlllllIIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIllllIlllIl.getBytes(StandardCharsets.UTF_8)), llIllII[13]), "DES");
            Cipher llIIIlllllIIIlI = Cipher.getInstance("DES");
            llIIIlllllIIIlI.init(llIllII[2], llIIIlllllIIIll);
            return new String(llIIIlllllIIIlI.doFinal(Base64.getDecoder().decode(llIIIllllIllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlllllIIIIl) {
            llIIIlllllIIIIl.printStackTrace();
            return null;
        }
    }

    @Override
    protected void setCustomGameRules() {
    }

    public Dominion() {
        Dominion llIIlIIIIlllIlI;
    }

    @Override
    protected void donarEfectesInicials(Player llIIIlllllIlIIl) {
        Dominion llIIIlllllIllII;
        super.donarEfectesInicials(llIIIlllllIlIIl);
        "".length();
        llIIIlllllIlIIl.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, llIllII[9], llIllII[4], llIllII[1]), llIllII[1]);
        "".length();
        llIIIlllllIlIIl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, llIllII[10], llIllII[1], llIllII[1]), llIllII[1]);
        "".length();
        llIIIlllllIlIIl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, llIllII[11], llIllII[0], llIllII[1]), llIllII[1]);
        "".length();
        llIIIlllllIlIIl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, llIllII[12], llIllII[1], llIllII[1]), llIllII[1]);
    }

    private static boolean lIlllIllI(int n) {
        return n != 0;
    }

    @Override
    protected ArrayList<JocTeamDominion.ControlPoint> getDesiredControlPoints() {
        Dominion llIIlIIIIIlIIll;
        ArrayList<JocTeamDominion.ControlPoint> llIIlIIIIIllIIl = new ArrayList<JocTeamDominion.ControlPoint>();
        ArrayList<Location> llIIlIIIIIllIII = llIIlIIIIIlIIll.pMapaActual().ObtenirLocations(lIllllI[llIllII[0]], llIIlIIIIIlIIll.world);
        String[] llIIlIIIIIlIlll = llIIlIIIIIlIIll.pMapaActual().ObtenirLlista(lIllllI[llIllII[1]]);
        String[] llIIlIIIIIlIllI = llIIlIIIIIlIIll.pMapaActual().ObtenirLlista(lIllllI[llIllII[2]]);
        String[] llIIlIIIIIlIlIl = llIIlIIIIIlIIll.pMapaActual().ObtenirLlista(lIllllI[llIllII[3]]);
        double llIIlIIIIIlIlII = 10.0;
        Iterator<Location> llIIlIIIIIIllII = llIIlIIIIIllIII.iterator();
        while (Dominion.lIlllIllI((int)llIIlIIIIIIllII.hasNext())) {
            Location llIIlIIIIIllIll;
            String llIIlIIIIlIIIII;
            float llIIlIIIIIlllIl;
            double llIIlIIIIIllllI;
            int llIIlIIIIIlllll;
            llIIlIIIIIllIll = llIIlIIIIIIllII.next();
            llIIlIIIIIlllll = llIIlIIIIIllIII.indexOf((Object)llIIlIIIIIllIll);
            try {
                String llIIlIIIIlIIllI = llIIlIIIIIlIlll[llIIlIIIIIlllll];
                "".length();
            }
            catch (Exception llIIlIIIIlIIlIl) {
                System.out.print(lIllllI[llIllII[4]]);
                llIIlIIIIlIIIII = lIllllI[llIllII[5]];
            }
            if (-" ".length() != -" ".length()) {
                return null;
            }
            try {
                double llIIlIIIIlIIlII = Double.parseDouble(llIIlIIIIIlIllI[llIIlIIIIIlllll]);
                "".length();
            }
            catch (Exception llIIlIIIIlIIIll) {
                llIIlIIIIIllllI = llIIlIIIIIlIlII;
            }
            if ("  ".length() >= (64 ^ 98 ^ (3 ^ 37))) {
                return null;
            }
            try {
                float llIIlIIIIlIIIlI = Float.parseFloat(llIIlIIIIIlIlIl[llIIlIIIIIlllll]);
                "".length();
            }
            catch (Exception llIIlIIIIlIIIIl) {
                llIIlIIIIIlllIl = 1.0f;
            }
            if (((45 ^ 31) & ~(118 ^ 68)) > "  ".length()) {
                return null;
            }
            ConquersPVPControlPoint llIIlIIIIIlllII = llIIlIIIIIlIIll.new ConquersPVPControlPoint(llIIlIIIIlIIIII, llIIlIIIIIllIll.add(0.5, 0.5, 0.5), llIIlIIIIIllllI);
            llIIlIIIIIlllII.setCaptureRateMultiplier(llIIlIIIIIlllIl);
            llIIlIIIIIlllII.setBasePointReward(4.0f);
            "".length();
            llIIlIIIIIllIIl.add(llIIlIIIIIlllII);
            "".length();
            if (((224 ^ 140 ^ (192 ^ 131)) & (146 ^ 188 ^ " ".length() ^ -" ".length())) == 0) continue;
            return null;
        }
        return llIIlIIIIIllIIl;
    }

    @Override
    protected void customJocIniciat() {
        Dominion llIIlIIIIllIllI;
        super.customJocIniciat();
        llIIlIIIIllIllI.setBlockBreakPlace(llIllII[0]);
        llIIlIIIIllIllI.setGiveStartingItemsRespawn(llIllII[1]);
    }

    @Override
    protected JocTeamDominion.GameGoalType getGameGoal() {
        return JocTeamDominion.GameGoalType.ScoreRace;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        Dominion llIIlIIIIIIIIII;
        ArrayList<JocEquips.Equip> llIIlIIIIIIIIIl = new ArrayList<JocEquips.Equip>();
        "".length();
        llIIlIIIIIIIIIl.add(new JocTeamDominion.EquipDominion(llIIlIIIIIIIIII, DyeColor.RED, lIllllI[llIllII[6]]));
        "".length();
        llIIlIIIIIIIIIl.add(new JocTeamDominion.EquipDominion(llIIlIIIIIIIIII, DyeColor.GREEN, lIllllI[llIllII[7]]));
        return llIIlIIIIIIIIIl;
    }

    @Override
    public boolean giveSnowLauncherOnKill() {
        return llIllII[1];
    }

    private static String lIlIIllIl(String llIIIlllIlllIll, String llIIIlllIlllIII) {
        try {
            SecretKeySpec llIIIlllIlllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIlllIlllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIlllIllllIl = Cipher.getInstance("Blowfish");
            llIIIlllIllllIl.init(llIllII[2], llIIIlllIlllllI);
            return new String(llIIIlllIllllIl.doFinal(Base64.getDecoder().decode(llIIIlllIlllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlllIllllII) {
            llIIIlllIllllII.printStackTrace();
            return null;
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIIIllllllIlll) {
        Dominion llIIIllllllIIll;
        ArrayList<ItemStack> llIIIllllllIllI = new ArrayList<ItemStack>();
        JocEquips.Equip llIIIllllllIlIl = llIIIllllllIIll.obtenirEquip(llIIIllllllIlll);
        "".length();
        llIIIllllllIllI.add(new ItemStack(Material.STONE_SWORD, llIllII[1]));
        ItemStack llIIIllllllIlII = new ItemStack(Material.BOW, llIllII[1]);
        llIIIllllllIlII.addUnsafeEnchantment(Enchantment.DURABILITY, llIllII[8]);
        llIIIllllllIlII.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, llIllII[1]);
        "".length();
        llIIIllllllIllI.add(llIIIllllllIlII);
        "".length();
        llIIIllllllIllI.add(new ItemStack(Material.CHAINMAIL_HELMET, llIllII[1]));
        "".length();
        llIIIllllllIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, llIIIllllllIlIl));
        "".length();
        llIIIllllllIllI.add(new ItemStack(Material.IRON_BOOTS, llIllII[1]));
        "".length();
        llIIIllllllIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, llIIIllllllIlIl));
        "".length();
        llIIIllllllIllI.add(new ItemStack(Material.GOLDEN_APPLE, llIllII[2]));
        "".length();
        llIIIllllllIllI.add(new ItemStack(Material.ARROW, llIllII[1]));
        return llIIIllllllIllI;
    }

    private static String lIlIIlIll(String llIIIllllIIlIll, String llIIIllllIIllll) {
        llIIIllllIIlIll = new String(Base64.getDecoder().decode(llIIIllllIIlIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIIllllIIlllI = new StringBuilder();
        char[] llIIIllllIIllIl = llIIIllllIIllll.toCharArray();
        int llIIIllllIIllII = llIllII[0];
        char[] llIIIllllIIIllI = llIIIllllIIlIll.toCharArray();
        int llIIIllllIIIlIl = llIIIllllIIIllI.length;
        int llIIIllllIIIlII = llIllII[0];
        while (Dominion.lIllllIII(llIIIllllIIIlII, llIIIllllIIIlIl)) {
            char llIIIllllIlIIIl = llIIIllllIIIllI[llIIIllllIIIlII];
            "".length();
            llIIIllllIIlllI.append((char)(llIIIllllIlIIIl ^ llIIIllllIIllIl[llIIIllllIIllII % llIIIllllIIllIl.length]));
            ++llIIIllllIIllII;
            ++llIIIllllIIIlII;
            "".length();
            if ("   ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(llIIIllllIIlllI);
    }

    private static void lIlIlIIll() {
        lIllllI = new String[llIllII[14]];
        Dominion.lIllllI[Dominion.llIllII[0]] = Dominion.lIlIIlIll("GyohPi04", "KEHPY");
        Dominion.lIllllI[Dominion.llIllII[1]] = Dominion.lIlIIllII("SeJCFH0RkgRhWvi5fCHWHg==", "oWBRn");
        Dominion.lIllllI[Dominion.llIllII[2]] = Dominion.lIlIIlIll("IQgqBTgjBicCOQICMA==", "qgCkL");
        Dominion.lIllllI[Dominion.llIllII[3]] = Dominion.lIlIIllIl("CptUnmsHk5rTwOGv5tSjJ3zSwbyS7R7p", "ifEFF");
        Dominion.lIllllI[Dominion.llIllII[4]] = Dominion.lIlIIllII("soKrkiNcjNU9i14g/Sc1K1ovbKMdZotaIDpBhDsLuUANaS+jJkyhl0Ra8sPqzqDq", "sZxwo");
        Dominion.lIllllI[Dominion.llIllII[5]] = Dominion.lIlIIllII("JbiPZ8TBBpmtbr0pIH+Q+Q==", "bCImm");
        Dominion.lIllllI[Dominion.llIllII[6]] = Dominion.lIlIIlIll("LAgaBzI2AQ==", "ZmhjW");
        Dominion.lIllllI[Dominion.llIllII[7]] = Dominion.lIlIIllII("KR4+VkoSetY=", "PyZUz");
        Dominion.lIllllI[Dominion.llIllII[13]] = Dominion.lIlIIlIll("NR4mCCgYHiU=", "qqKaF");
    }

    @Override
    public String getGameName() {
        return lIllllI[llIllII[13]];
    }

    static {
        Dominion.lIlllIlIl();
        Dominion.lIlIlIIll();
    }

    public class ConquersPVPControlPoint
    extends JocTeamDominion.RadialControlPoint {
        public ConquersPVPControlPoint(String lIIIllllIIIlIlI, Location lIIIllllIIIlIIl, Double lIIIllllIIIlIII) {
            ConquersPVPControlPoint lIIIllllIIlIIIl;
            super(lIIIllllIIlIIIl.Dominion.this, lIIIllllIIIlIlI, new JocTeamDominion.BubbleControlPointRenderer(lIIIllllIIlIIIl.Dominion.this), lIIIllllIIIlIIl, lIIIllllIIIlIII);
        }
    }

}

