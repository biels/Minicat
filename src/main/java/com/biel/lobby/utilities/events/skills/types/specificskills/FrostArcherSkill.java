/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
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
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FrostArcherSkill
extends InherentSkill {
    private static final /* synthetic */ String[] llIlIlII;
    private static final /* synthetic */ int[] lllllIll;
    private /* synthetic */ int stacks;

    private static String llIIllIIII(String lllllIllIIllll, String lllllIllIlIIII) {
        try {
            SecretKeySpec lllllIllIlIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllIllIlIIII.getBytes(StandardCharsets.UTF_8)), lllllIll[8]), "DES");
            Cipher lllllIllIlIIll = Cipher.getInstance("DES");
            lllllIllIlIIll.init(lllllIll[2], lllllIllIlIlII);
            return new String(lllllIllIlIIll.doFinal(Base64.getDecoder().decode(lllllIllIIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIllIlIIlI) {
            lllllIllIlIIlI.printStackTrace();
            return null;
        }
    }

    private static void lllIlIllll() {
        lllllIll = new int[9];
        FrostArcherSkill.lllllIll[0] = (181 ^ 176) & ~(143 ^ 138);
        FrostArcherSkill.lllllIll[1] = " ".length();
        FrostArcherSkill.lllllIll[2] = "  ".length();
        FrostArcherSkill.lllllIll[3] = "   ".length();
        FrostArcherSkill.lllllIll[4] = 251 ^ 162 ^ (56 ^ 103);
        FrostArcherSkill.lllllIll[5] = -28869 & 29148;
        FrostArcherSkill.lllllIll[6] = 15 ^ 95;
        FrostArcherSkill.lllllIll[7] = 62 ^ 19 ^ (164 ^ 141);
        FrostArcherSkill.lllllIll[8] = 190 ^ 182;
    }

    private static boolean lllIllIIIl(Object object, Object object2) {
        return object == object2;
    }

    private static boolean lllIlllIIl(int n, int n2) {
        return n < n2;
    }

    public void empresonar(Player lllllIlllllIlI, Player lllllIlllllIIl) {
        FrostArcherSkill lllllIllllIllI;
        ArrayList<BlockFace> lllllIlllllIII = new ArrayList<BlockFace>();
        "".length();
        lllllIlllllIII.add(BlockFace.NORTH);
        "".length();
        lllllIlllllIII.add(BlockFace.SOUTH);
        "".length();
        lllllIlllllIII.add(BlockFace.WEST);
        "".length();
        lllllIlllllIII.add(BlockFace.EAST);
        Iterator lllllIllllIIlI = lllllIlllllIII.iterator();
        while (FrostArcherSkill.lllIllIlII((int)lllllIllllIIlI.hasNext())) {
            BlockFace lllllIllllllII = (BlockFace)lllllIllllIIlI.next();
            Block lllllIllllllIl = lllllIlllllIlI.getLocation().getBlock().getRelative(lllllIllllllII);
            if (FrostArcherSkill.lllIllIlII((int)lllllIllllllIl.getType().isSolid())) {
                "".length();
                if (-"   ".length() < 0) continue;
                return;
            }
            lllllIllllllIl.setType(Material.ICE);
            Utils.BreakBlockLater(lllllIllllllIl, (int)(20.0 * FrostArcherSkill.getModifier()), lllllIll[0]);
            "".length();
            if (-"  ".length() < 0) continue;
            return;
        }
        "".length();
        lllllIlllllIlI.teleport(lllllIlllllIlI.getLocation().getBlock().getLocation().add(new Vector(0.5, 0.0, 0.5)));
        Block lllllIllllIlll = lllllIlllllIlI.getLocation().add(0.0, 2.0, 0.0).getBlock();
        if (FrostArcherSkill.lllIllIIIl((Object)lllllIllllIlll.getType(), (Object)Material.AIR)) {
            lllllIllllIlll.setType(Material.GOLD_BLOCK);
            Utils.BreakBlockLater(lllllIllllIlll, (int)(20.0 * FrostArcherSkill.getModifier()), lllllIll[0]);
        }
        lllllIlllllIlI.playSound(lllllIlllllIIl.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 0.5f);
        lllllIllllIllI.removeDefaultNamedAura();
    }

    private static int getMaxStacks() {
        return lllllIll[4];
    }

    private static boolean lllIllIIll(int n) {
        return n >= 0;
    }

    private static String llIIlIllll(String lllllIlIllllII, String lllllIllIIIIII) {
        lllllIlIllllII = new String(Base64.getDecoder().decode(lllllIlIllllII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllllIlIllllll = new StringBuilder();
        char[] lllllIlIlllllI = lllllIllIIIIII.toCharArray();
        int lllllIlIllllIl = lllllIll[0];
        char[] lllllIlIllIlll = lllllIlIllllII.toCharArray();
        int lllllIlIllIllI = lllllIlIllIlll.length;
        int lllllIlIllIlIl = lllllIll[0];
        while (FrostArcherSkill.lllIlllIIl(lllllIlIllIlIl, lllllIlIllIllI)) {
            char lllllIllIIIIlI = lllllIlIllIlll[lllllIlIllIlIl];
            "".length();
            lllllIlIllllll.append((char)(lllllIllIIIIlI ^ lllllIlIlllllI[lllllIlIllllIl % lllllIlIlllllI.length]));
            ++lllllIlIllllIl;
            ++lllllIlIllIlIl;
            "".length();
            if (-" ".length() == -" ".length()) continue;
            return null;
        }
        return String.valueOf(lllllIlIllllll);
    }

    private static boolean lllIllIlII(int n) {
        return n != 0;
    }

    public FrostArcherStatusEffect getAssociatedEffect() {
        FrostArcherStatusEffect lllllIlllIlIlI;
        FrostArcherSkill lllllIlllIlIll;
        Joc.PlayerInfo lllllIlllIlIIl = lllllIlllIlIll.getPlayerInfo(lllllIlllIlIll.getPlayer());
        if (FrostArcherSkill.lllIllIlII((int)lllllIlllIlIIl.hasStatusEffect(FrostArcherStatusEffect.class))) {
            FrostArcherStatusEffect lllllIlllIllII = lllllIlllIlIIl.getStatusEffect(FrostArcherStatusEffect.class);
            "".length();
            if (-(76 ^ 47 ^ (80 ^ 55)) > 0) {
                return null;
            }
        } else {
            lllllIlllIlIlI = lllllIlllIlIll.new FrostArcherStatusEffect(lllllIlllIlIll.getPlayer());
            lllllIlllIlIlI.setValue(1.0);
            lllllIlllIlIIl.addStatusEffect(lllllIlllIlIlI);
        }
        return lllllIlllIlIlI;
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    @Override
    public Material getMaterial() {
        return Material.ICE;
    }

    private static void llIIllIlII() {
        llIlIlII = new String[lllllIll[7]];
        FrostArcherSkill.llIlIlII[FrostArcherSkill.lllllIll[0]] = FrostArcherSkill.llIIlIllll("Ch4kGTw5TDEJeSwJOQ==", "KlUlY");
        FrostArcherSkill.llIlIlII[FrostArcherSkill.lllllIll[1]] = FrostArcherSkill.llIIllIIII("RcCzfxRCfLc=", "Jktlu");
        FrostArcherSkill.llIlIlII[FrostArcherSkill.lllllIll[2]] = FrostArcherSkill.llIIllIIlI("DYhNrASeqXtoaBxugtbDejvLBiowMpQIWVNcFSpJOwqkyuCYIevnaPoUIsiSaubYXUo4/FR+q3o=", "npGLC");
        FrostArcherSkill.llIlIlII[FrostArcherSkill.lllllIll[3]] = FrostArcherSkill.llIIllIIII("JazVJhUEUkBDf3DGreGiWdPQrIHF50nzuVyp6dyhFEfQPUEQUogvDM20V+3ZJPCX", "lsGDX");
    }

    private static int lllIllIIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public String getDescription() {
        String llllllIIlIlIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llIlIlII[lllllIll[1]]).append(FrostArcherSkill.getModifier()).append((Object)ChatColor.WHITE));
        return String.valueOf(new StringBuilder().append(llIlIlII[lllllIll[2]]).append(llllllIIlIlIlI).append(llIlIlII[lllllIll[3]]));
    }

    private int getStacks() {
        FrostArcherSkill llllllIIlIIllI;
        return llllllIIlIIllI.stacks;
    }

    @Override
    public String getName() {
        return llIlIlII[lllllIll[0]];
    }

    public FrostArcherSkill(Player llllllIIllIIII) {
        FrostArcherSkill llllllIIllIIIl;
        super(llllllIIllIIII);
        llllllIIllIIIl.stacks = lllllIll[0];
    }

    static {
        FrostArcherSkill.lllIlIllll();
        FrostArcherSkill.llIIllIlII();
    }

    private static double getModifier() {
        return 4.25;
    }

    private static boolean lllIllIIlI(int n) {
        return n == 0;
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent llllllIIIIllII, Player llllllIIIIlIll, Player llllllIIIIlIlI, boolean llllllIIIIlIIl) {
        FrostArcherSkill llllllIIIlIlIl;
        super.onPlayerDamageByPlayer(llllllIIIIllII, llllllIIIIlIll, llllllIIIIlIlI, llllllIIIIlIIl);
        Player llllllIIIlIIII = llllllIIIlIlIl.getPlayer();
        if (!FrostArcherSkill.lllIllIIIl((Object)llllllIIIIlIlI, (Object)llllllIIIlIIII) || FrostArcherSkill.lllIllIIlI((int)llllllIIIlIlIl.getGame().areEnemies(llllllIIIIlIlI, llllllIIIIlIll).booleanValue())) {
            return;
        }
        FrostArcherStatusEffect llllllIIIIllll = llllllIIIlIlIl.getAssociatedEffect();
        int llllllIIIIlllI = lllllIll[5];
        llllllIIIIllll.setRemainingTicks(llllllIIIIlllI);
        if (FrostArcherSkill.lllIllIIll(FrostArcherSkill.lllIllIIII(llllllIIIIllll.getValue(), llllllIIIIllll.getMaxValue()))) {
            if (FrostArcherSkill.lllIllIlII((int)llllllIIIIlIIl)) {
                llllllIIIlIlIl.empresonar(llllllIIIIlIll, llllllIIIIlIlI);
                llllllIIIIllll.setValue(0.0);
                llllllIIIIllll.setModal(lllllIll[0]);
                llllllIIIIllII.setDamage(llllllIIIIllII.getDamage() / 3.0);
                FrostBindngStatusEffect llllllIIIlIllI = llllllIIIlIlIl.new FrostBindngStatusEffect(llllllIIIIlIll);
                llllllIIIlIllI.setRemainingTicks((int)(FrostArcherSkill.getModifier() * 20.0));
                llllllIIIlIlIl.getPlayerInfo(llllllIIIIlIll).addStatusEffect(llllllIIIlIllI);
                llllllIIIIllll.setRemainingTicks(lllllIll[0]);
                llllllIIIIllll.setModal(lllllIll[0]);
                "".length();
                if (((62 ^ 17) & ~(79 ^ 96)) > 0) {
                    return;
                }
            }
        } else {
            llllllIIIIllll.setValue(llllllIIIIllll.getValue() + 1.0);
            if (FrostArcherSkill.lllIllIIll(FrostArcherSkill.lllIllIIII(llllllIIIIllll.getValue(), llllllIIIIllll.getMaxValue()))) {
                llllllIIIIlIlI.playSound(llllllIIIIlIlI.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                llllllIIIIllll.setModal(lllllIll[1]);
                llllllIIIIllll.setModalRemainingTicks(lllllIll[6]);
                llllllIIIlIlIl.getPlayerInfo().addAura(new AuraInfo(llllllIIIlIlIl.getName(), lllllIll[3], 5.0, llllllIIIlIlIl.getItemStack()));
            }
        }
    }

    private static String llIIllIIlI(String lllllIllIllllI, String lllllIllIllIll) {
        try {
            SecretKeySpec lllllIlllIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllIllIllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllllIlllIIIII = Cipher.getInstance("Blowfish");
            lllllIlllIIIII.init(lllllIll[2], lllllIlllIIIIl);
            return new String(lllllIlllIIIII.doFinal(Base64.getDecoder().decode(lllllIllIllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIllIlllll) {
            lllllIllIlllll.printStackTrace();
            return null;
        }
    }

    private void setStacks(int llllllIIlIIIII) {
        llllllIIlIIIll.stacks = llllllIIlIIIII;
    }

    public class FrostBindngStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ String[] lIIlllIl;
        private static final /* synthetic */ int[] lIlIIlll;

        private static void lIlIIIllIl() {
            lIlIIlll = new int[4];
            FrostBindngStatusEffect.lIlIIlll[0] = " ".length();
            FrostBindngStatusEffect.lIlIIlll[1] = (96 + 144 - 113 + 34 ^ 140 + 53 - 57 + 30) & (111 + 149 - 217 + 110 ^ 59 + 28 - 35 + 106 ^ -" ".length());
            FrostBindngStatusEffect.lIlIIlll[2] = "  ".length();
            FrostBindngStatusEffect.lIlIIlll[3] = 108 ^ 100 ^ (144 ^ 175) & ~(146 ^ 173);
        }

        protected void onBlockBreak(BlockBreakEvent lIlIIlIlIlIlIlI, Block lIlIIlIlIlIlIIl) {
            FrostBindngStatusEffect lIlIIlIlIlIlllI;
            super.onBlockBreak(lIlIIlIlIlIlIlI, lIlIIlIlIlIlIIl);
            lIlIIlIlIlIlIlI.setCancelled(lIlIIlll[0]);
        }

        private static boolean lIlIIIllll(int n, int n2) {
            return n < n2;
        }

        @Override
        public String getDescription() {
            return lIIlllIl[lIlIIlll[0]];
        }

        @Override
        public double getMaxValue() {
            return 6.0;
        }

        private static String lIIlIllIlI(String lIlIIlIIIIlIlIl, String lIlIIlIIIIlllll) {
            try {
                SecretKeySpec lIlIIlIIIlIIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIlIIIIlllll.getBytes(StandardCharsets.UTF_8)), lIlIIlll[3]), "DES");
                Cipher lIlIIlIIIlIIIlI = Cipher.getInstance("DES");
                lIlIIlIIIlIIIlI.init(lIlIIlll[2], lIlIIlIIIlIIIll);
                return new String(lIlIIlIIIlIIIlI.doFinal(Base64.getDecoder().decode(lIlIIlIIIIlIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIlIIIlIIIIl) {
                lIlIIlIIIlIIIIl.printStackTrace();
                return null;
            }
        }

        @Override
        public String getName() {
            return lIIlllIl[lIlIIlll[1]];
        }

        public FrostBindngStatusEffect(Player lIlIIlIlIllIlIl) {
            FrostBindngStatusEffect lIlIIlIlIllIlll;
            super(lIlIIlIlIllIlIl);
            lIlIIlIlIllIlll.setType(StatusEffect.StatusEffectType.DEBUFF);
            lIlIIlIlIllIlll.setModal(lIlIIlll[0]);
        }

        private static String lIIlIllIIl(String lIlIIlIlIIllllI, String lIlIIlIlIIlllIl) {
            lIlIIlIlIIllllI = new String(Base64.getDecoder().decode(lIlIIlIlIIllllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIIlIlIIlllII = new StringBuilder();
            char[] lIlIIlIlIIllIll = lIlIIlIlIIlllIl.toCharArray();
            int lIlIIlIlIIllIlI = lIlIIlll[1];
            char[] lIlIIlIlIIlIlII = lIlIIlIlIIllllI.toCharArray();
            int lIlIIlIlIIlIIll = lIlIIlIlIIlIlII.length;
            int lIlIIlIlIIlIIlI = lIlIIlll[1];
            while (FrostBindngStatusEffect.lIlIIIllll(lIlIIlIlIIlIIlI, lIlIIlIlIIlIIll)) {
                char lIlIIlIlIIlllll = lIlIIlIlIIlIlII[lIlIIlIlIIlIIlI];
                "".length();
                lIlIIlIlIIlllII.append((char)(lIlIIlIlIIlllll ^ lIlIIlIlIIllIll[lIlIIlIlIIllIlI % lIlIIlIlIIllIll.length]));
                ++lIlIIlIlIIllIlI;
                ++lIlIIlIlIIlIIlI;
                "".length();
                if ("  ".length() > 0) continue;
                return null;
            }
            return String.valueOf(lIlIIlIlIIlllII);
        }

        private static void lIIlIllIll() {
            lIIlllIl = new String[lIlIIlll[2]];
            FrostBindngStatusEffect.lIIlllIl[FrostBindngStatusEffect.lIlIIlll[1]] = FrostBindngStatusEffect.lIIlIllIIl("KyM7HhUELSE=", "hLUyp");
            FrostBindngStatusEffect.lIIlllIl[FrostBindngStatusEffect.lIlIIlll[0]] = FrostBindngStatusEffect.lIIlIllIlI("8NHuP4K2xxDld9gDZEV1Nhlq0vI7rb+VDGRKxthzDRA=", "RSlrF");
        }

        static {
            FrostBindngStatusEffect.lIlIIIllIl();
            FrostBindngStatusEffect.lIIlIllIll();
        }
    }

    public class FrostArcherStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ String[] lIIlIlIll;
        private static final /* synthetic */ int[] lIIlIlllI;

        private static void lIIIlIIIIlI() {
            lIIlIlIll = new String[lIIlIlllI[2]];
            FrostArcherStatusEffect.lIIlIlIll[FrostArcherStatusEffect.lIIlIlllI[0]] = FrostArcherStatusEffect.lIIIIllllll("KAMJ", "ofehF");
            FrostArcherStatusEffect.lIIlIlIll[FrostArcherStatusEffect.lIIlIlllI[1]] = FrostArcherStatusEffect.lIIIlIIIIII("HdZ/zAn+iCYhK0NZxE86/DLLZUtDthyoUNjCTYvBSO+oKttcNo1+qecd6pbP2RFH", "IIDJg");
        }

        private static void lIIIlIIIlIl() {
            lIIlIlllI = new int[4];
            FrostArcherStatusEffect.lIIlIlllI[0] = (28 ^ 40 ^ (170 ^ 138)) & (154 ^ 163 ^ (165 ^ 136) ^ -" ".length());
            FrostArcherStatusEffect.lIIlIlllI[1] = " ".length();
            FrostArcherStatusEffect.lIIlIlllI[2] = "  ".length();
            FrostArcherStatusEffect.lIIlIlllI[3] = 130 ^ 138;
        }

        private static boolean lIIIlIIlIII(int n, int n2) {
            return n < n2;
        }

        @Override
        public String getDescription() {
            return lIIlIlIll[lIIlIlllI[1]];
        }

        private static String lIIIIllllll(String llIllIlIIIIlII, String llIllIlIIIIIll) {
            llIllIlIIIIlII = new String(Base64.getDecoder().decode(llIllIlIIIIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llIllIlIIIIlll = new StringBuilder();
            char[] llIllIlIIIIllI = llIllIlIIIIIll.toCharArray();
            int llIllIlIIIIlIl = lIIlIlllI[0];
            char[] llIllIIlllllll = llIllIlIIIIlII.toCharArray();
            int llIllIIllllllI = llIllIIlllllll.length;
            int llIllIIlllllIl = lIIlIlllI[0];
            while (FrostArcherStatusEffect.lIIIlIIlIII(llIllIIlllllIl, llIllIIllllllI)) {
                char llIllIlIIIlIlI = llIllIIlllllll[llIllIIlllllIl];
                "".length();
                llIllIlIIIIlll.append((char)(llIllIlIIIlIlI ^ llIllIlIIIIllI[llIllIlIIIIlIl % llIllIlIIIIllI.length]));
                ++llIllIlIIIIlIl;
                ++llIllIIlllllIl;
                "".length();
                if (" ".length() <= "   ".length()) continue;
                return null;
            }
            return String.valueOf(llIllIlIIIIlll);
        }

        public FrostArcherStatusEffect(Player llIllIlIlIIIll) {
            FrostArcherStatusEffect llIllIlIlIlIII;
            super(llIllIlIlIIIll);
            llIllIlIlIlIII.setType(StatusEffect.StatusEffectType.SKILL_TRAY);
            llIllIlIlIlIII.setMaxValue(6.0);
        }

        static {
            FrostArcherStatusEffect.lIIIlIIIlIl();
            FrostArcherStatusEffect.lIIIlIIIIlI();
        }

        @Override
        public String getName() {
            return lIIlIlIll[lIIlIlllI[0]];
        }

        private static String lIIIlIIIIII(String llIllIlIIllIIl, String llIllIlIIllIII) {
            try {
                SecretKeySpec llIllIlIIlllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllIlIIllIII.getBytes(StandardCharsets.UTF_8)), lIIlIlllI[3]), "DES");
                Cipher llIllIlIIllIll = Cipher.getInstance("DES");
                llIllIlIIllIll.init(lIIlIlllI[2], llIllIlIIlllII);
                return new String(llIllIlIIllIll.doFinal(Base64.getDecoder().decode(llIllIlIIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIllIlIIllIlI) {
                llIllIlIIllIlI.printStackTrace();
                return null;
            }
        }
    }

}

