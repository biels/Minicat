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
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.projectiles.ProjectileSource
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class TeamDeathMatch
extends JocTeamDeathMatch {
    private static final /* synthetic */ int[] llllIl;
    private static final /* synthetic */ String[] lllIlI;

    private static String lIlllIll(String lllIIllIIllllII, String lllIIllIIllllIl) {
        try {
            SecretKeySpec lllIIllIlIIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIllIIllllIl.getBytes(StandardCharsets.UTF_8)), llllIl[7]), "DES");
            Cipher lllIIllIlIIIIII = Cipher.getInstance("DES");
            lllIIllIlIIIIII.init(llllIl[1], lllIIllIlIIIIIl);
            return new String(lllIIllIlIIIIII.doFinal(Base64.getDecoder().decode(lllIIllIIllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIllIIllllll) {
            lllIIllIIllllll.printStackTrace();
            return null;
        }
    }

    private static void llIIIIlI() {
        lllIlI = new String[llllIl[6]];
        TeamDeathMatch.lllIlI[TeamDeathMatch.llllIl[2]] = TeamDeathMatch.lIlllIll("SyEzSetv2dg=", "yCzRP");
        TeamDeathMatch.lllIlI[TeamDeathMatch.llllIl[3]] = TeamDeathMatch.lIlllIll("XvDHjnaAMeM=", "rDgez");
        TeamDeathMatch.lllIlI[TeamDeathMatch.llllIl[1]] = TeamDeathMatch.llIIIIII("CgIFMxU=", "HmhQt");
        TeamDeathMatch.lllIlI[TeamDeathMatch.llllIl[4]] = TeamDeathMatch.lIlllIll("BxF2Z9V9M8GCU34fp7qK0YGTBNlJeocf", "IRWgv");
        TeamDeathMatch.lllIlI[TeamDeathMatch.llllIl[5]] = TeamDeathMatch.llIIIIII("Hi0mAAYvKTMFDys8JAU=", "JHGmB");
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllIIlllIIIIIIl) {
        TeamDeathMatch lllIIlllIIIlIII;
        ArrayList<ItemStack> lllIIlllIIIIllI = new ArrayList<ItemStack>();
        JocEquips.Equip lllIIlllIIIIlIl = lllIIlllIIIlIII.obtenirEquip(lllIIlllIIIIIIl);
        double lllIIlllIIIIlII = lllIIlllIIIlIII.getBalancingMultiplier(lllIIlllIIIIIIl);
        "".length();
        lllIIlllIIIIllI.add(new ItemStack(Material.STONE_SWORD, llllIl[3]));
        ItemStack lllIIlllIIIIIll = new ItemStack(Material.BOW, llllIl[3]);
        lllIIlllIIIIIll.addUnsafeEnchantment(Enchantment.DURABILITY, llllIl[0]);
        "".length();
        lllIIlllIIIIllI.add(lllIIlllIIIIIll);
        "".length();
        lllIIlllIIIIllI.add(new ItemStack(Material.CHAINMAIL_HELMET, llllIl[3]));
        "".length();
        lllIIlllIIIIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lllIIlllIIIIlIl));
        "".length();
        lllIIlllIIIIllI.add(new ItemStack(Material.IRON_BOOTS, llllIl[3]));
        "".length();
        lllIIlllIIIIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lllIIlllIIIIlIl));
        "".length();
        lllIIlllIIIIllI.add(new ItemStack(Material.GOLDEN_APPLE, llllIl[1]));
        String[] arrstring = new String[llllIl[3]];
        arrstring[TeamDeathMatch.llllIl[2]] = lllIlI[llllIl[4]];
        "".length();
        lllIIlllIIIIllI.add(Utils.setItemNameAndLore(new ItemStack(Material.SNOW_BALL, (int)Math.round(4.0 * (lllIIlllIIIIlII - 0.2))), lllIlI[llllIl[1]], arrstring));
        "".length();
        lllIIlllIIIIllI.add(new ItemStack(Material.ARROW, (int)(20.0 * lllIIlllIIIIlII)));
        return lllIIlllIIIIllI;
    }

    static {
        TeamDeathMatch.llIIIllI();
        TeamDeathMatch.llIIIIlI();
    }

    @Override
    protected void onProjectileHit(ProjectileHitEvent lllIIllIllIIIlI, Projectile lllIIllIllIIIIl) {
        TeamDeathMatch lllIIllIllIIIll;
        super.onProjectileHit(lllIIllIllIIIlI, lllIIllIllIIIIl);
        if (TeamDeathMatch.llIIlIll(lllIIllIllIIIIl instanceof Snowball)) {
            Location lllIIllIllIlIII = lllIIllIllIIIlI.getEntity().getLocation();
            ProjectileSource lllIIllIllIIlll = lllIIllIllIIIIl.getShooter();
            if (TeamDeathMatch.llIIlIll(lllIIllIllIIlll instanceof Player)) {
                Player lllIIllIllIlIIl = (Player)lllIIllIllIIlll;
                "".length();
                lllIIllIllIIIll.world.createExplosion(lllIIllIllIlIII.getX(), lllIIllIllIlIII.getY(), lllIIllIllIlIII.getZ(), 2.6f * (float)lllIIllIllIIIll.getBalancingMultiplier(lllIIllIllIlIIl), llllIl[2], llllIl[2]);
            }
        }
    }

    public TeamDeathMatch() {
        TeamDeathMatch lllIIlllIlIIlll;
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected boolean canBeDropped(ItemStack lllIIllIlllIlll, Player lllIIllIlllIIlI) {
        TeamDeathMatch lllIIllIllllIII;
        Material lllIIllIlllIlIl = lllIIllIlllIlll.getType();
        if (!TeamDeathMatch.llIIlIIl((Object)lllIIllIlllIlIl, (Object)Material.GOLDEN_APPLE) || TeamDeathMatch.llIIlIlI((Object)lllIIllIlllIlIl, (Object)Material.SNOW_BALL)) {
            return llllIl[3];
        }
        return super.canBeDropped(lllIIllIlllIlll, lllIIllIlllIIlI);
    }

    private static boolean llIIlIll(int n) {
        return n != 0;
    }

    private static String llIIIIII(String lllIIllIlIIlllI, String lllIIllIlIIllIl) {
        lllIIllIlIIlllI = new String(Base64.getDecoder().decode(lllIIllIlIIlllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIIllIlIlIIIl = new StringBuilder();
        char[] lllIIllIlIlIIII = lllIIllIlIIllIl.toCharArray();
        int lllIIllIlIIllll = llllIl[2];
        char[] lllIIllIlIIlIIl = lllIIllIlIIlllI.toCharArray();
        int lllIIllIlIIlIII = lllIIllIlIIlIIl.length;
        int lllIIllIlIIIlll = llllIl[2];
        while (TeamDeathMatch.llIIllIl(lllIIllIlIIIlll, lllIIllIlIIlIII)) {
            char lllIIllIlIlIlII = lllIIllIlIIlIIl[lllIIllIlIIIlll];
            "".length();
            lllIIllIlIlIIIl.append((char)(lllIIllIlIlIlII ^ lllIIllIlIlIIII[lllIIllIlIIllll % lllIIllIlIlIIII.length]));
            ++lllIIllIlIIllll;
            ++lllIIllIlIIIlll;
            "".length();
            if ("  ".length() < (84 ^ 80)) continue;
            return null;
        }
        return String.valueOf(lllIIllIlIlIIIl);
    }

    private static void llIIIllI() {
        llllIl = new int[8];
        TeamDeathMatch.llllIl[0] = 226 ^ 136 ^ (74 ^ 42);
        TeamDeathMatch.llllIl[1] = "  ".length();
        TeamDeathMatch.llllIl[2] = (144 ^ 164 ^ (244 ^ 130)) & (184 + 54 - 1 + 2 ^ 19 + 68 - 64 + 150 ^ -" ".length());
        TeamDeathMatch.llllIl[3] = " ".length();
        TeamDeathMatch.llllIl[4] = "   ".length();
        TeamDeathMatch.llllIl[5] = 151 ^ 147;
        TeamDeathMatch.llllIl[6] = 112 ^ 48 ^ (83 ^ 22);
        TeamDeathMatch.llllIl[7] = 133 ^ 184 ^ (91 ^ 110);
    }

    private static boolean llIIlIIl(Object object, Object object2) {
        return object != object2;
    }

    @Override
    public String getGameName() {
        return lllIlI[llllIl[5]];
    }

    @Override
    protected int getFinishScore() {
        TeamDeathMatch lllIIlllIlIIlII;
        return llllIl[0] + lllIIlllIlIIlII.getPlayers().size() * llllIl[1];
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        TeamDeathMatch lllIIlllIIlllll;
        ArrayList<JocEquips.Equip> lllIIlllIlIIIII = new ArrayList<JocEquips.Equip>();
        "".length();
        lllIIlllIlIIIII.add(new JocTeamScoreRace.EquipScoreRace(lllIIlllIIlllll, DyeColor.GREEN, lllIlI[llllIl[2]]));
        "".length();
        lllIIlllIlIIIII.add(new JocTeamScoreRace.EquipScoreRace(lllIIlllIIlllll, DyeColor.BLUE, lllIlI[llllIl[3]]));
        return lllIIlllIlIIIII;
    }

    private static boolean llIIllIl(int n, int n2) {
        return n < n2;
    }

    private static boolean llIIlIlI(Object object, Object object2) {
        return object == object2;
    }
}

