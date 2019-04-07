/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

public class ExternalCombustionEngine
extends InherentSkill {
    private /* synthetic */ int stacks;
    private static final /* synthetic */ int[] lIIlIIlII;
    private static final /* synthetic */ String[] lIIlIIIll;

    private void setStacks(int llIlllIIllIlIl) {
        llIlllIIllIllI.stacks = llIlllIIllIlIl;
    }

    static {
        ExternalCombustionEngine.lIIIIllIIll();
        ExternalCombustionEngine.lIIIIllIIIl();
    }

    public double getDmgMultiplier() {
        return 1.2;
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public double getCDSeconds() {
        return 5.0;
    }

    private static String lIIIIlIllIl(String llIlllIIIllIlI, String llIlllIIIllIll) {
        try {
            SecretKeySpec llIlllIIIlllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlllIIIllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIlllIIIllllI = Cipher.getInstance("Blowfish");
            llIlllIIIllllI.init(lIIlIIlII[2], llIlllIIIlllll);
            return new String(llIlllIIIllllI.doFinal(Base64.getDecoder().decode(llIlllIIIllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlllIIIlllIl) {
            llIlllIIIlllIl.printStackTrace();
            return null;
        }
    }

    public ExternalCombustionEngine(Player llIlllIlIIllll) {
        ExternalCombustionEngine llIlllIlIlIIII;
        super(llIlllIlIIllll);
        llIlllIlIlIIII.stacks = lIIlIIlII[0];
        llIlllIlIlIIII.resetCooldown();
    }

    @Override
    public String getDescription() {
        ExternalCombustionEngine llIlllIlIIIlIl;
        String llIlllIlIIIlII = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlIIIll[lIIlIIlII[1]]).append(llIlllIlIIIlIl.getDmgMultiplier() * 100.0).append((Object)ChatColor.WHITE));
        String llIlllIlIIIIll = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlIIIll[lIIlIIlII[2]]).append(llIlllIlIIIlIl.getDmgMultiplierBlk() * 100.0).append((Object)ChatColor.WHITE));
        String llIlllIlIIIIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlIIIll[lIIlIIlII[3]]).append(lIIlIIlII[4]).append((Object)ChatColor.WHITE));
        return lIIlIIIll[lIIlIIlII[5]];
    }

    @Override
    public void tick() {
        ExternalCombustionEngine llIlllIIllIIII;
        super.tick();
        Joc.PlayerInfo llIlllIIllIIIl = llIlllIIllIIII.getPlayerInfo();
        if (ExternalCombustionEngine.lIIIIllIlII((int)llIlllIIllIIIl.hasStatusEffect(ExternalCombustionStatusEffect.class)) && ExternalCombustionEngine.lIIIIllIlIl((int)llIlllIIllIIII.tryUseCD())) {
            llIlllIIllIIIl.addStatusEffect(llIlllIIllIIII.new ExternalCombustionStatusEffect(llIlllIIllIIII.getPlayer()));
        }
    }

    private static String lIIIIllIIII(String llIlllIIIIIlll, String llIlllIIIIIllI) {
        llIlllIIIIIlll = new String(Base64.getDecoder().decode(llIlllIIIIIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIlllIIIIlIlI = new StringBuilder();
        char[] llIlllIIIIlIIl = llIlllIIIIIllI.toCharArray();
        int llIlllIIIIlIII = lIIlIIlII[0];
        char[] llIlllIIIIIIlI = llIlllIIIIIlll.toCharArray();
        int llIlllIIIIIIIl = llIlllIIIIIIlI.length;
        int llIlllIIIIIIII = lIIlIIlII[0];
        while (ExternalCombustionEngine.lIIIIllIlll(llIlllIIIIIIII, llIlllIIIIIIIl)) {
            char llIlllIIIIllIl = llIlllIIIIIIlI[llIlllIIIIIIII];
            "".length();
            llIlllIIIIlIlI.append((char)(llIlllIIIIllIl ^ llIlllIIIIlIIl[llIlllIIIIlIII % llIlllIIIIlIIl.length]));
            ++llIlllIIIIlIII;
            ++llIlllIIIIIIII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(llIlllIIIIlIlI);
    }

    private static void lIIIIllIIll() {
        lIIlIIlII = new int[7];
        ExternalCombustionEngine.lIIlIIlII[0] = (122 ^ 7 ^ (91 ^ 57)) & (31 ^ 51 ^ (147 ^ 160) ^ -" ".length());
        ExternalCombustionEngine.lIIlIIlII[1] = " ".length();
        ExternalCombustionEngine.lIIlIIlII[2] = "  ".length();
        ExternalCombustionEngine.lIIlIIlII[3] = "   ".length();
        ExternalCombustionEngine.lIIlIIlII[4] = 46 + 123 - 132 + 98 ^ 55 + 48 - -34 + 10;
        ExternalCombustionEngine.lIIlIIlII[5] = 17 ^ 10 ^ (86 ^ 73);
        ExternalCombustionEngine.lIIlIIlII[6] = 72 + 3 - -39 + 66 ^ 140 + 8 - 57 + 86;
    }

    private static boolean lIIIIllIllI(Object object, Object object2) {
        return object == object2;
    }

    @Override
    public String getName() {
        return lIIlIIIll[lIIlIIlII[0]];
    }

    private int getStacks() {
        ExternalCombustionEngine llIlllIIlllIll;
        return llIlllIIlllIll.stacks;
    }

    protected void onPlayerDeath(PlayerDeathEvent llIlllIIlIIlll, Player llIlllIIlIIllI) {
        ExternalCombustionEngine llIlllIIlIlIll;
        super.onPlayerDeath(llIlllIIlIIlll, llIlllIIlIIllI);
        if (ExternalCombustionEngine.lIIIIllIllI((Object)llIlllIIlIIllI, (Object)llIlllIIlIlIll.getPlayer())) {
            llIlllIIlIlIll.resetCooldown();
            llIlllIIlIlIll.getPlayerInfo().removeStatusEffect(ExternalCombustionStatusEffect.class);
        }
    }

    private static boolean lIIIIllIlll(int n, int n2) {
        return n < n2;
    }

    public double getDmgMultiplierBlk() {
        return 2.35;
    }

    private static boolean lIIIIllIlII(int n) {
        return n == 0;
    }

    private static void lIIIIllIIIl() {
        lIIlIIIll = new String[lIIlIIlII[6]];
        ExternalCombustionEngine.lIIlIIIll[ExternalCombustionEngine.lIIlIIlII[0]] = ExternalCombustionEngine.lIIIIlIllIl("96XmD+gND4diWpkqgrLD8srYw5/0gS5I", "mRPqq");
        ExternalCombustionEngine.lIIlIIIll[ExternalCombustionEngine.lIIlIIlII[1]] = ExternalCombustionEngine.lIIIIlIllIl("+C2dfo0oEwQ=", "qNBqg");
        ExternalCombustionEngine.lIIlIIIll[ExternalCombustionEngine.lIIlIIlII[2]] = ExternalCombustionEngine.lIIIIllIIII("", "vKaCU");
        ExternalCombustionEngine.lIIlIIIll[ExternalCombustionEngine.lIIlIIlII[3]] = ExternalCombustionEngine.lIIIIllIIII("", "rrmAE");
        ExternalCombustionEngine.lIIlIIIll[ExternalCombustionEngine.lIIlIIlII[5]] = ExternalCombustionEngine.lIIIIlIllIl("ve5VJMhjf0TnDmVHAbr0vstswvlELJdz48Bn4NPUoOJd0d8clv0SXKJ9kDZATGHvn0FJ0BwgDANVjuMM3gW4kZlVN3ESHmFSx4vJdry9LwTfxok+OgGNIyKheF/42lwxuY/OQ7EeJBBbxsqrmyIk48IIuhyUYP75h4AQPRX60Vm+JWsFssmIRVLiXJJz6BheGBWt9k3nKPA=", "oCDQq");
    }

    private static boolean lIIIIllIlIl(int n) {
        return n != 0;
    }

    public class ExternalCombustionStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ String[] lIIIlllI;
        private static final /* synthetic */ int[] lIIlllll;

        private static boolean lIIlIllllI(int n) {
            return n == 0;
        }

        @Override
        public void tick() {
            Block lIlIlIIlllllIll;
            ExternalCombustionStatusEffect lIlIlIIlllllIlI;
            super.tick();
            if (ExternalCombustionStatusEffect.lIIlIllllI((int)lIlIlIIlllllIlI.isNthTick(lIIlllll[3]))) {
                return;
            }
            lIlIlIIlllllIlI.setValue(Math.max(0.0, Math.pow(lIlIlIIlllllIlI.getTicksLived(), 0.6) / 75.0 - lIlIlIIlllllIlI.ExternalCombustionEngine.this.getCDSeconds()));
            Player lIlIlIIlllllIIl = lIlIlIIlllllIlI.getPlayer();
            Location lIlIlIIlllllIII = lIlIlIIlllllIIl.getEyeLocation();
            ArrayList lIlIlIIllllIlll = GUtils.getNearbyPlayers((Location)lIlIlIIlllllIII, (double)4.5);
            if (ExternalCombustionStatusEffect.lIIlIlllll((int)lIlIlIIlllllIlI.isNthTick(lIIlllll[4]))) {
                lIlIlIIlllllIlI.getWorld().playEffect(lIlIlIIlllllIIl.getLocation().add(0.0, -0.12, 0.0), Effect.MOBSPAWNER_FLAMES, lIIlllll[5]);
            }
            if (!(ExternalCombustionStatusEffect.lIIllIIIII(ExternalCombustionStatusEffect.lIIlIlllIl(lIlIlIIlllllIlI.getValue(), 90.0)) && ExternalCombustionStatusEffect.lIIlIlllll((int)lIlIlIIlllllIlI.getGame().getBlockBreakPlace().booleanValue()) && ExternalCombustionStatusEffect.lIIlIlllll((int)lIlIlIIlllllIlI.isNthTick(lIIlllll[3])) && ExternalCombustionStatusEffect.lIIllIIIIl((Object)(lIlIlIIlllllIll = lIlIlIIlllllIlI.getPlayer().getLocation().getBlock()).getType(), (Object)Material.AIR) && !ExternalCombustionStatusEffect.lIIlIlllll((int)GUtils.Possibilitat((double)(lIlIlIIlllllIlI.getValue() - 80.0), (double)100.0)))) {
                // empty if block
            }
            if (ExternalCombustionStatusEffect.lIIllIIIlI((Object)lIlIlIIlllllIlI.getPlayer().getLocation().getBlock().getType(), (Object)Material.WATER)) {
                lIlIlIIlllllIlI.expire();
            }
            Vector lIlIlIIllllIllI = lIlIlIIlllllIIl.getVelocity();
            Function<Player, Double> lIlIlIIllllIlIl = lIlIlIIllIIIIII -> 1.0 / lIlIlIIlllllIII.distance(lIlIlIIllIIIIII.getEyeLocation());
            Consumer<Player> lIlIlIIllllIlII = lIlIlIIllIIlIIl -> {
                ExternalCombustionStatusEffect lIlIlIIllIIlIII;
                if (ExternalCombustionStatusEffect.lIIllIIIII(ExternalCombustionStatusEffect.lIIllIIIll(lIlIlIIllIIlIIl.getHealth(), 2.0))) {
                    double lIlIlIIllIIllIl = lIlIlIIllIIlIII.getValue() / 10.0;
                    lIlIlIIllIIlIIl.damage(lIlIlIIllIIllIl, (Entity)lIlIlIIlllllIIl);
                    lIlIlIIllIIlIIl.setVelocity(lIlIlIIllllIllI);
                }
                lIlIlIIllIIlIII.setModalRemainingTicks(lIIlllll[7]);
            };
            Predicate<Player> lIlIlIIllllIIll = lIlIlIIllIlIIll -> {
                ExternalCombustionStatusEffect lIlIlIIllIllIII;
                return lIlIlIIllIllIII.getGame().areEnemies(lIlIlIIlllllIIl, (Player)lIlIlIIllIlIIll);
            };
            if (ExternalCombustionStatusEffect.lIIlIlllll((int)lIlIlIIlllllIlI.isNthTick(lIIlllll[6]))) {
                lIlIlIIllllIlll.stream().filter(lIlIlIIllllIIll).forEach(lIlIlIIllllIlII.andThen(lIlIlIIllIlllII -> {
                    ExternalCombustionStatusEffect lIlIlIIllIlllll;
                    lIlIlIIllIlllll.getWorld().playEffect(((LivingEntity)lIlIlIIllIlllII).getEyeLocation().subtract(Utils.NombreEntre(-0.1, 0.1), Utils.NombreEntre(-1.8, 0.1), Utils.NombreEntre(-0.1, 0.1)), Effect.LARGE_SMOKE, lIIlllll[1]);
                }).andThen(lIlIlIIlllIIlIl -> {
                    ExternalCombustionStatusEffect lIlIlIIlllIIlII;
                    lIlIlIIlllIIlII.getWorld().playSound(((LivingEntity)lIlIlIIlllIIlIl).getEyeLocation(), Sound.BLOCK_FIRE_AMBIENT, (float)(0.5 * (Double)lIlIlIIllllIlIl.apply((Player)lIlIlIIlllIIlIl)), 1.05f);
                }));
            }
        }

        public ExternalCombustionStatusEffect(Player lIlIlIlIIIIlIIl) {
            ExternalCombustionStatusEffect lIlIlIlIIIIlIII;
            super(lIlIlIlIIIIlIIl);
            lIlIlIlIIIIlIII.setType(StatusEffect.StatusEffectType.SKILL_TRAY);
            lIlIlIlIIIIlIII.setRemainingTicks(lIIlllll[0]);
        }

        static {
            ExternalCombustionStatusEffect.lIIlIlllII();
            ExternalCombustionStatusEffect.lIIIlIIlIl();
        }

        private static int lIIllIIIll(double d, double d2) {
            return (int)(d DCMPL d2);
        }

        private static boolean lIIllIIIlI(Object object, Object object2) {
            return object == object2;
        }

        private static void lIIIlIIlIl() {
            lIIIlllI = new String[lIIlllll[8]];
            ExternalCombustionStatusEffect.lIIIlllI[ExternalCombustionStatusEffect.lIIlllll[1]] = ExternalCombustionStatusEffect.lIIIIIIllI("h6i5TbLoBKvxvjV3BXs9zY4kp/O0bg9i", "VyPmQ");
            ExternalCombustionStatusEffect.lIIIlllI[ExternalCombustionStatusEffect.lIIlllll[2]] = ExternalCombustionStatusEffect.lIIIlIIlII("OxYuICscHy0lOlIVKSBiExQ7bCccHSUlIQFYOD4tAh06Pw==", "rxHLB");
        }

        private static void lIIlIlllII() {
            lIIlllll = new int[9];
            ExternalCombustionStatusEffect.lIIlllll[0] = -" ".length();
            ExternalCombustionStatusEffect.lIIlllll[1] = (116 ^ 61 ^ (95 ^ 6)) & (165 + 124 - 161 + 53 ^ 61 + 71 - -8 + 25 ^ -" ".length());
            ExternalCombustionStatusEffect.lIIlllll[2] = " ".length();
            ExternalCombustionStatusEffect.lIIlllll[3] = 217 ^ 188 ^ (122 ^ 11);
            ExternalCombustionStatusEffect.lIIlllll[4] = 93 ^ 106 ^ (147 ^ 171);
            ExternalCombustionStatusEffect.lIIlllll[5] = 98 ^ 106;
            ExternalCombustionStatusEffect.lIIlllll[6] = 95 ^ 7 ^ (52 ^ 102);
            ExternalCombustionStatusEffect.lIIlllll[7] = 53 ^ 48;
            ExternalCombustionStatusEffect.lIIlllll[8] = "  ".length();
        }

        private static String lIIIlIIlII(String lIlIlIIlIlIIllI, String lIlIlIIlIlIIIII) {
            lIlIlIIlIlIIllI = new String(Base64.getDecoder().decode(lIlIlIIlIlIIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIlIIlIlIIlII = new StringBuilder();
            char[] lIlIlIIlIlIIIll = lIlIlIIlIlIIIII.toCharArray();
            int lIlIlIIlIlIIIlI = lIIlllll[1];
            char[] lIlIlIIlIIlllII = lIlIlIIlIlIIllI.toCharArray();
            int lIlIlIIlIIllIll = lIlIlIIlIIlllII.length;
            int lIlIlIIlIIllIlI = lIIlllll[1];
            while (ExternalCombustionStatusEffect.lIIllIIlII(lIlIlIIlIIllIlI, lIlIlIIlIIllIll)) {
                char lIlIlIIlIlIIlll = lIlIlIIlIIlllII[lIlIlIIlIIllIlI];
                "".length();
                lIlIlIIlIlIIlII.append((char)(lIlIlIIlIlIIlll ^ lIlIlIIlIlIIIll[lIlIlIIlIlIIIlI % lIlIlIIlIlIIIll.length]));
                ++lIlIlIIlIlIIIlI;
                ++lIlIlIIlIIllIlI;
                "".length();
                if (((24 ^ 0 ^ (105 ^ 64)) & (224 ^ 140 ^ (233 ^ 180) ^ -" ".length())) < " ".length()) continue;
                return null;
            }
            return String.valueOf(lIlIlIIlIlIIlII);
        }

        private static boolean lIIllIIIII(int n) {
            return n > 0;
        }

        @Override
        public String getDescription() {
            return lIIIlllI[lIIlllll[2]];
        }

        private static boolean lIIllIIIIl(Object object, Object object2) {
            return object != object2;
        }

        private static String lIIIIIIllI(String lIlIlIIlIllIllI, String lIlIlIIlIllIIll) {
            try {
                SecretKeySpec lIlIlIIlIlllIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlIIlIllIIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIlIlIIlIlllIII = Cipher.getInstance("Blowfish");
                lIlIlIIlIlllIII.init(lIIlllll[8], lIlIlIIlIlllIIl);
                return new String(lIlIlIIlIlllIII.doFinal(Base64.getDecoder().decode(lIlIlIIlIllIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIlIIlIllIlll) {
                lIlIlIIlIllIlll.printStackTrace();
                return null;
            }
        }

        private static boolean lIIllIIlII(int n, int n2) {
            return n < n2;
        }

        private static boolean lIIlIlllll(int n) {
            return n != 0;
        }

        @Override
        public String getName() {
            return lIIIlllI[lIIlllll[1]];
        }

        private static int lIIlIlllIl(double d, double d2) {
            return (int)(d DCMPL d2);
        }
    }

}

