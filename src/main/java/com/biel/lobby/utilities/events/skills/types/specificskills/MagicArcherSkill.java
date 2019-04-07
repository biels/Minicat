/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.ColorConverter
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Builder
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.BielAPI.Utils.ColorConverter;
import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.ItemAttatchedModeSkill;
import com.biel.lobby.utilities.events.skills.types.ItemAttatchedStackModeSkill;
import com.biel.lobby.utilities.events.statuseffects.LifeDrainStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class MagicArcherSkill
extends ItemAttatchedStackModeSkill {
    private static final /* synthetic */ String[] lIIIllIl;
    private static final /* synthetic */ int[] lIIlIIII;
    public /* synthetic */ boolean flyingEffect;

    @Override
    public String getDescription() {
        MagicArcherSkill lIllIlIlllllIlI;
        return String.valueOf(new StringBuilder().append(lIIIllIl[lIIlIIII[7]]).append(lIllIlIlllllIlI.getModeList()));
    }

    private static String llllllIlI(String lIllIlIlllIllII, String lIllIlIlllIlIll) {
        try {
            SecretKeySpec lIllIlIlllIllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlIlllIlIll.getBytes(StandardCharsets.UTF_8)), lIIlIIII[8]), "DES");
            Cipher lIllIlIlllIlllI = Cipher.getInstance("DES");
            lIllIlIlllIlllI.init(lIIlIIII[3], lIllIlIlllIllll);
            return new String(lIllIlIlllIlllI.doFinal(Base64.getDecoder().decode(lIllIlIlllIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIlIlllIllIl) {
            lIllIlIlllIllIl.printStackTrace();
            return null;
        }
    }

    public MagicArcherSkill(Player lIllIllIIIIlIII) {
        MagicArcherSkill lIllIllIIIIIlll;
        super(lIllIllIIIIlIII);
        lIllIllIIIIIlll.flyingEffect = lIIlIIII[0];
    }

    @Override
    public String getName() {
        return lIIIllIl[lIIlIIII[6]];
    }

    static {
        MagicArcherSkill.lIIIIIlllI();
        MagicArcherSkill.lIIIIIIlII();
    }

    @Override
    public Material getMaterial() {
        return Material.FIREWORK;
    }

    @Override
    protected Class<? extends ItemAttatchedModeSkill.ItemAttatchedModeSkillTrayEffect> getTrayEffectClass() {
        return MagicArcherTrayEffect.class;
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    @Override
    public boolean matchesItem(ItemStack lIllIllIIIIIIII) {
        int n;
        if (MagicArcherSkill.lIIIIIllll((Object)lIllIllIIIIIIII) && MagicArcherSkill.lIIIIlIIII((Object)lIllIllIIIIIIII.getType(), (Object)Material.BOW)) {
            n = lIIlIIII[1];
            "".length();
            if (((49 ^ 58) & ~(159 ^ 148)) < 0) {
                return (boolean)((176 ^ 145) & ~(189 ^ 156));
            }
        } else {
            n = lIIlIIII[0];
        }
        return (boolean)n;
    }

    private static boolean lIIIIlIIll(int n, int n2) {
        return n < n2;
    }

    private static boolean lIIIIIllll(Object object) {
        return object != null;
    }

    private static void lIIIIIIlII() {
        lIIIllIl = new String[lIIlIIII[8]];
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[0]] = MagicArcherSkill.llllllIlI("PmOr+JB32waNvZY0Qnp97Q==", "PXZiE");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[1]] = MagicArcherSkill.llllllIlI("Wm2s5ipXn+DZVdTb3R7CqZ6zkpJcBfvc34EDSudvq68=", "XDpmq");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[3]] = MagicArcherSkill.llllllIlI("FY/zEwQPEfi2QkhLjTy+sw==", "dylab");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[2]] = MagicArcherSkill.lIIIIIIIII("MRsPKREWEgwsAFgYCClYGRENLBsRGgckFA==", "xuiEx");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[4]] = MagicArcherSkill.llllllIlI("6Az9qo2Cv2sBeEcGfPx3DQ==", "lAGCE");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[5]] = MagicArcherSkill.lIIIIIIIII("MDoIJwMBNlgnAQZ3HSwIGD4bMQ==", "uWxBm");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[6]] = MagicArcherSkill.lIIIIIIIII("CCQ4ND07diTCoT8gNQ==", "IVIAX");
        MagicArcherSkill.lIIIllIl[MagicArcherSkill.lIIlIIII[7]] = MagicArcherSkill.lIIIIIIIII("KjsHETgfK1IcKgkjHh0/Cj5SB2wKOh4dKApqExg4SysAFzhFaicAIgcjBg4qSzkaHS0fallUJjQ9GhEuB2oCETlLKRMaPQIrAFQuBT4AEWsGJRYROEsrHxZrB20TBihLK1IYKksnE1prLiYBVCYELhcHcUs=", "kJrtK");
    }

    @Override
    public void registerModes() {
        MagicArcherSkill lIllIllIIIIIlII;
        lIllIllIIIIIlII.registerMode(new ItemAttatchedStackModeSkill.StackSkillMode(lIllIllIIIIIlII, Mode.ACCELERATOR.ordinal(), lIIIllIl[lIIlIIII[0]], lIIIllIl[lIIlIIII[1]], ChatColor.DARK_BLUE, lIIlIIII[2]));
        lIllIllIIIIIlII.registerMode(new ItemAttatchedStackModeSkill.StackSkillMode(lIllIllIIIIIlII, Mode.ENCHANCER.ordinal(), lIIIllIl[lIIlIIII[3]], lIIIllIl[lIIlIIII[2]], ChatColor.DARK_RED, lIIlIIII[2]));
        lIllIllIIIIIlII.registerMode(new ItemAttatchedStackModeSkill.StackSkillMode(lIllIllIIIIIlII, Mode.REPULSOR.ordinal(), lIIIllIl[lIIlIIII[4]], lIIIllIl[lIIlIIII[5]], ChatColor.YELLOW, lIIlIIII[4]));
    }

    private static String lIIIIIIIII(String lIllIlIllIlllII, String lIllIlIllIllIll) {
        lIllIlIllIlllII = new String(Base64.getDecoder().decode(lIllIlIllIlllII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIllIlIllIllIlI = new StringBuilder();
        char[] lIllIlIllIllIIl = lIllIlIllIllIll.toCharArray();
        int lIllIlIllIllIII = lIIlIIII[0];
        char[] lIllIlIllIlIIlI = lIllIlIllIlllII.toCharArray();
        int lIllIlIllIlIIIl = lIllIlIllIlIIlI.length;
        int lIllIlIllIlIIII = lIIlIIII[0];
        while (MagicArcherSkill.lIIIIlIIll(lIllIlIllIlIIII, lIllIlIllIlIIIl)) {
            char lIllIlIllIlllIl = lIllIlIllIlIIlI[lIllIlIllIlIIII];
            "".length();
            lIllIlIllIllIlI.append((char)(lIllIlIllIlllIl ^ lIllIlIllIllIIl[lIllIlIllIllIII % lIllIlIllIllIIl.length]));
            ++lIllIlIllIllIII;
            ++lIllIlIllIlIIII;
            "".length();
            if ("   ".length() > ((52 ^ 31) & ~(123 ^ 80))) continue;
            return null;
        }
        return String.valueOf(lIllIlIllIllIlI);
    }

    private static void lIIIIIlllI() {
        lIIlIIII = new int[9];
        MagicArcherSkill.lIIlIIII[0] = (179 + 169 - 164 + 5 ^ 116 + 46 - 53 + 54) & (115 ^ 57 ^ (248 ^ 172) ^ -" ".length());
        MagicArcherSkill.lIIlIIII[1] = " ".length();
        MagicArcherSkill.lIIlIIII[2] = "   ".length();
        MagicArcherSkill.lIIlIIII[3] = "  ".length();
        MagicArcherSkill.lIIlIIII[4] = 186 ^ 190;
        MagicArcherSkill.lIIlIIII[5] = 42 ^ 60 ^ (4 ^ 23);
        MagicArcherSkill.lIIlIIII[6] = 41 ^ 47;
        MagicArcherSkill.lIIlIIII[7] = 79 ^ 9 ^ (128 ^ 193);
        MagicArcherSkill.lIIlIIII[8] = 20 ^ 47 ^ (177 ^ 130);
    }

    private static boolean lIIIIlIIII(Object object, Object object2) {
        return object == object2;
    }

    protected Color getColor(ChatColor lIllIlIllllIllI) {
        return ColorConverter.hexToColor((String)ColorConverter.chatToHex((ChatColor)lIllIlIllllIllI));
    }

    public class MagicArcherTrayEffect
    extends ItemAttatchedStackModeSkill.ItemAttatchedStackModeSkillTrayEffect {
        private static final /* synthetic */ int[] lIIlllIll;
        private static final /* synthetic */ String[] lIIllIlII;

        private static void lIIIllIlIlI() {
            lIIlllIll = new int[5];
            MagicArcherTrayEffect.lIIlllIll[0] = "  ".length();
            MagicArcherTrayEffect.lIIlllIll[1] = -(-1505 & 12270) & (-16707 & 28671);
            MagicArcherTrayEffect.lIIlllIll[2] = " ".length();
            MagicArcherTrayEffect.lIIlllIll[3] = (127 ^ 21 ^ (174 ^ 134)) & (114 + 81 - -19 + 10 ^ 42 + 141 - 100 + 79 ^ -" ".length());
            MagicArcherTrayEffect.lIIlllIll[4] = -4167 & 4566;
        }

        private static boolean lIIIlllIlIl(int n, int n2) {
            return n < n2;
        }

        private static String lIIIllIIlII(String llIIlllIlIIlIl, String llIIlllIlIlIlI) {
            llIIlllIlIIlIl = new String(Base64.getDecoder().decode(llIIlllIlIIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llIIlllIlIlIIl = new StringBuilder();
            char[] llIIlllIlIlIII = llIIlllIlIlIlI.toCharArray();
            int llIIlllIlIIllI = lIIlllIll[3];
            char[] llIIlllIIllllI = llIIlllIlIIlIl.toCharArray();
            int llIIlllIIlllIl = llIIlllIIllllI.length;
            int llIIlllIIlllII = lIIlllIll[3];
            while (MagicArcherTrayEffect.lIIIlllIlIl(llIIlllIIlllII, llIIlllIIlllIl)) {
                char llIIlllIlIllII = llIIlllIIllllI[llIIlllIIlllII];
                "".length();
                llIIlllIlIlIIl.append((char)(llIIlllIlIllII ^ llIIlllIlIlIII[llIIlllIlIIllI % llIIlllIlIlIII.length]));
                ++llIIlllIlIIllI;
                ++llIIlllIIlllII;
                "".length();
                if ("   ".length() >= 0) continue;
                return null;
            }
            return String.valueOf(llIIlllIlIlIIl);
        }

        private static boolean lIIIllIlllI(int n) {
            return n == 0;
        }

        private static boolean lIIIllIllII(Object object, Object object2) {
            return object == object2;
        }

        private static String lIIIllIIIll(String llIIlllIIIlIll, String llIIlllIIIlIlI) {
            try {
                SecretKeySpec llIIlllIIlIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIlllIIIlIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llIIlllIIIllll = Cipher.getInstance("Blowfish");
                llIIlllIIIllll.init(lIIlllIll[0], llIIlllIIlIIII);
                return new String(llIIlllIIIllll.doFinal(Base64.getDecoder().decode(llIIlllIIIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIlllIIIlllI) {
                llIIlllIIIlllI.printStackTrace();
                return null;
            }
        }

        private static boolean lIIIllIllIl(int n) {
            return n != 0;
        }

        private Player[] getAllPlayersArray() {
            MagicArcherTrayEffect llIlIIIlIlllII;
            return llIlIIIlIlllII.getGame().getPlayers().toArray((T[])new Player[llIlIIIlIlllII.getGame().getPlayers().size()]);
        }

        protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent llIlIIlIIllIII, Player llIlIIlIIlIlll, Player llIlIIlIIlIllI, boolean llIlIIlIIlIIII) {
            MagicArcherTrayEffect llIlIIlIIlIlII;
            super.onPlayerDamageByPlayer(llIlIIlIIllIII, llIlIIlIIlIlll, llIlIIlIIlIllI, llIlIIlIIlIIII);
            if (MagicArcherTrayEffect.lIIIllIllII((Object)llIlIIlIIlIllI, (Object)llIlIIlIIlIlII.getPlayer()) && MagicArcherTrayEffect.lIIIllIllIl((int)llIlIIlIIlIIII) && MagicArcherTrayEffect.lIIIllIllIl((int)llIlIIlIIlIlII.getGame().areEnemies(llIlIIlIIlIllI, llIlIIlIIlIlll).booleanValue())) {
                int n;
                if (MagicArcherTrayEffect.lIIIllIlllI(MagicArcherTrayEffect.lIIIllIlIll(llIlIIlIIlIlII.getValue(), 0.0))) {
                    n = lIIlllIll[0];
                    "".length();
                    if ((199 ^ 195) < -" ".length()) {
                        return;
                    }
                } else {
                    n = lIIlllIll[0];
                }
                int llIlIIlIIllIlI = n;
                llIlIIlIIlIlII.setValue(llIlIIlIIlIlII.getValue() + (double)llIlIIlIIllIlI);
                if (MagicArcherTrayEffect.lIIIllIllIl((int)llIlIIlIIlIlII.MagicArcherSkill.this.flyingEffect)) {
                    FireworkEffect llIlIIlIIIlIIl;
                    ItemAttatchedStackModeSkill.StackSkillMode llIlIIlIIllIll = llIlIIlIIlIlII.MagicArcherSkill.this.getSelectedMode();
                    if (MagicArcherTrayEffect.lIIIllIllll(llIlIIlIIllIll.getId(), Mode.ACCELERATOR.ordinal())) {
                        "".length();
                        llIlIIlIIlIllI.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIlllIll[1], lIIlllIll[2]));
                        FireworkEffect.Builder llIlIIlIlIIlII = FireworkEffect.builder();
                        "".length();
                        llIlIIlIlIIlII.withColor(llIlIIlIIlIlII.MagicArcherSkill.this.getColor(llIlIIlIIlIlII.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                        "".length();
                        llIlIIlIlIIlII.with(FireworkEffect.Type.BALL_LARGE);
                        FireworkEffect llIlIIlIIIllII = llIlIIlIlIIlII.build();
                    }
                    if (MagicArcherTrayEffect.lIIIllIllll(llIlIIlIIllIll.getId(), Mode.ENCHANCER.ordinal())) {
                        Consumer<Player> llIlIIlIlIIIll = llIIllllIlllII -> {
                            MagicArcherTrayEffect llIIllllIlIllI;
                            llIIllllIlllII.damage(10.0, (Entity)llIlIIlIIlIllI);
                            FireworkEffect.Builder llIIllllIllIll = FireworkEffect.builder();
                            "".length();
                            llIIllllIllIll.withColor(llIIllllIlIllI.MagicArcherSkill.this.getColor(llIIllllIlIllI.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                            "".length();
                            llIIllllIllIll.with(FireworkEffect.Type.BALL);
                            FireworkEffect llIIllllIllIII = llIIllllIllIll.build();
                        };
                        Predicate<Player> llIlIIlIlIIIlI = llIIlllllIllIl -> {
                            MagicArcherTrayEffect llIIlllllIllII;
                            return llIIlllllIllII.getGame().areEnemies(llIlIIlIIlIllI, (Player)llIIlllllIllIl);
                        };
                        ArrayList llIlIIlIlIIIIl = GUtils.getNearbyPlayers((Location)llIlIIlIIlIlll.getLocation(), (double)12.0);
                        llIlIIlIlIIIIl.stream().filter(llIlIIlIlIIIlI).forEach(llIlIIlIlIIIll);
                        FireworkEffect.Builder llIlIIlIlIIIII = FireworkEffect.builder();
                        "".length();
                        llIlIIlIlIIIII.withColor(llIlIIlIIlIlII.MagicArcherSkill.this.getColor(llIlIIlIIlIlII.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                        "".length();
                        llIlIIlIlIIIII.with(FireworkEffect.Type.BALL_LARGE);
                        llIlIIlIIIlIIl = llIlIIlIlIIIII.build();
                    }
                    if (MagicArcherTrayEffect.lIIIllIllll(llIlIIlIIllIll.getId(), Mode.DRAINER.ordinal())) {
                        Consumer<Player> llIlIIlIIlllll = llIIllllllllll -> {
                            MagicArcherTrayEffect llIlIIIIIIIlll;
                            LifeDrainStatusEffect llIlIIIIIIIlIl = new LifeDrainStatusEffect((Player)llIIllllllllll);
                            llIlIIIIIIIlIl.setRemainingTicks(lIIlllIll[4]);
                            llIlIIIIIIIlIl.setOwnerPlayer(llIlIIIIIIIlll.getPlayer());
                            llIlIIIIIIIlll.getPlayerInfo((Player)llIIllllllllll).addStatusEffect(llIlIIIIIIIlIl);
                            FireworkEffect.Builder llIlIIIIIIIIll = FireworkEffect.builder();
                            "".length();
                            llIlIIIIIIIIll.withColor(llIlIIIIIIIlll.MagicArcherSkill.this.getColor(llIlIIIIIIIlll.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                            "".length();
                            llIlIIIIIIIIll.with(FireworkEffect.Type.BALL);
                            FireworkEffect llIlIIIIIIIIlI = llIlIIIIIIIIll.build();
                        };
                        Predicate<Player> llIlIIlIIllllI = llIlIIIIIlIIll -> {
                            MagicArcherTrayEffect llIlIIIIIllIIl;
                            return llIlIIIIIllIIl.getGame().areEnemies(llIlIIlIIlIllI, (Player)llIlIIIIIlIIll);
                        };
                        ArrayList llIlIIlIIlllIl = GUtils.getNearbyPlayers((Location)llIlIIlIIlIlll.getLocation(), (double)16.0);
                        llIlIIlIIlllIl.stream().filter(llIlIIlIIllllI).forEach(llIlIIlIIlllll);
                        FireworkEffect.Builder llIlIIlIIlllII = FireworkEffect.builder();
                        "".length();
                        llIlIIlIIlllII.withColor(llIlIIlIIlIlII.MagicArcherSkill.this.getColor(llIlIIlIIlIlII.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                        "".length();
                        llIlIIlIIlllII.with(FireworkEffect.Type.BALL_LARGE);
                        llIlIIlIIIlIIl = llIlIIlIIlllII.build();
                    }
                    llIlIIlIIlIlII.MagicArcherSkill.this.flyingEffect = lIIlllIll[3];
                }
            }
        }

        private static int lIIIllIlIll(double d, double d2) {
            return (int)(d DCMPL d2);
        }

        private static boolean lIIIllIllll(int n, int n2) {
            return n == n2;
        }

        private static void lIIIllIIlIl() {
            lIIllIlII = new String[lIIlllIll[0]];
            MagicArcherTrayEffect.lIIllIlII[MagicArcherTrayEffect.lIIlllIll[3]] = MagicArcherTrayEffect.lIIIllIIIll("+qJ6PGaoz3gJZwY+TnBLpw==", "paeSS");
            MagicArcherTrayEffect.lIIllIlII[MagicArcherTrayEffect.lIIlllIll[2]] = MagicArcherTrayEffect.lIIIllIIlII("W0U=", "weyze");
        }

        protected void onEntityShootBow(EntityShootBowEvent llIlIIIlllIlll, Entity llIlIIIlllllII, ItemStack llIlIIIlllIlIl, Projectile llIlIIIllllIlI, float llIlIIIllllIIl) {
            MagicArcherTrayEffect llIlIIIllllIII;
            super.onEntityShootBow(llIlIIIlllIlll, llIlIIIlllllII, llIlIIIlllIlIl, llIlIIIllllIlI, llIlIIIllllIIl);
            System.out.println(String.valueOf(new StringBuilder().append(lIIllIlII[lIIlllIll[3]]).append(llIlIIIlllllII.getName()).append(lIIllIlII[lIIlllIll[2]]).append(llIlIIIllllIII.getPlayer().getName())));
            if (MagicArcherTrayEffect.lIIIllIllIl((int)llIlIIIlllllII.getName().equalsIgnoreCase(llIlIIIllllIII.getPlayer().getName()))) {
                if (MagicArcherTrayEffect.lIIIllIllIl((int)llIlIIIllllIII.isMaxed())) {
                    FireworkEffect.Builder llIlIIlIIIIIII = FireworkEffect.builder();
                    "".length();
                    llIlIIlIIIIIII.withColor(llIlIIIllllIII.MagicArcherSkill.this.getColor(llIlIIIllllIII.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                    "".length();
                    llIlIIlIIIIIII.with(FireworkEffect.Type.BURST);
                    FireworkEffect llIlIIIlllllll = llIlIIlIIIIIII.build();
                    llIlIIIllllIII.setValue(0.0);
                    llIlIIIllllIII.MagicArcherSkill.this.flyingEffect = lIIlllIll[2];
                    "".length();
                    if (-" ".length() >= 0) {
                        return;
                    }
                } else {
                    llIlIIIllllIII.setValue(llIlIIIllllIII.getValue() - 1.0);
                }
            }
        }

        static {
            MagicArcherTrayEffect.lIIIllIlIlI();
            MagicArcherTrayEffect.lIIIllIIlIl();
        }

        protected void onPlayerShootBow(EntityShootBowEvent llIlIIIllIlIIl, Player llIlIIIllIIIlI, ItemStack llIlIIIllIIlll, Projectile llIlIIIllIIIII, float llIlIIIlIlllll) {
            MagicArcherTrayEffect llIlIIIllIlIlI;
            super.onPlayerShootBow(llIlIIIllIlIIl, llIlIIIllIIIlI, llIlIIIllIIlll, llIlIIIllIIIII, llIlIIIlIlllll);
        }

        public MagicArcherTrayEffect(Player llIlIIlIllIlII) {
            MagicArcherTrayEffect llIlIIlIllIllI;
            super(llIlIIlIllIllI.MagicArcherSkill.this, llIlIIlIllIlII);
            llIlIIlIllIllI.setMaxValue(llIlIIlIllIllI.MagicArcherSkill.this.getSelectedMode().getMaxStacks());
            llIlIIlIllIllI.setValue(0.0);
        }

        protected void onProjectileHit(ProjectileHitEvent llIlIIIlIlIllI, Projectile llIlIIIlIlIlIl) {
            MagicArcherTrayEffect llIlIIIlIlIlll;
            super.onProjectileHit(llIlIIIlIlIllI, llIlIIIlIlIlIl);
            Runnable llIlIIIlIlIlII = () -> {
                MagicArcherTrayEffect llIlIIIIlllllI;
                if (MagicArcherTrayEffect.lIIIllIllIl((int)llIlIIIIlllllI.MagicArcherSkill.this.flyingEffect)) {
                    ItemAttatchedStackModeSkill.StackSkillMode llIlIIIlIIIIlI = llIlIIIIlllllI.MagicArcherSkill.this.getSelectedMode();
                    Location llIlIIIlIIIIIl = llIlIIIlIlIlIl.getLocation();
                    if (MagicArcherTrayEffect.lIIIllIllll(llIlIIIlIIIIlI.getId(), Mode.REPULSOR.ordinal())) {
                        Consumer<Player> llIlIIIlIIIllI = llIlIIIIlIIlll -> {
                            MagicArcherTrayEffect llIlIIIIlIlIIl;
                            Vector llIlIIIIlIIllI = GUtils.CrearVector((Location)llIlIIIlIIIIIl, (Location)llIlIIIIlIIlll.getEyeLocation()).normalize();
                            "".length();
                            llIlIIIIlIIllI.setY(0.1 + llIlIIIIlIIllI.getY() / 3.0);
                            "".length();
                            llIlIIIIlIIllI.normalize().multiply(2.8);
                            llIlIIIIlIIlll.setVelocity(llIlIIIIlIIllI);
                            FireworkEffect.Builder llIlIIIIlIIlIl = FireworkEffect.builder();
                            "".length();
                            llIlIIIIlIIlIl.withColor(llIlIIIIlIlIIl.MagicArcherSkill.this.getColor(llIlIIIIlIlIIl.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                            "".length();
                            llIlIIIIlIIlIl.with(FireworkEffect.Type.BALL);
                            FireworkEffect llIlIIIIlIIlII = llIlIIIIlIIlIl.build();
                        };
                        Predicate<Player> llIlIIIlIIIlIl = llIlIIIIllIIII -> {
                            MagicArcherTrayEffect llIlIIIIllIIIl;
                            return llIlIIIIllIIIl.getGame().areEnemies(llIlIIIIllIIIl.getPlayer(), (Player)llIlIIIIllIIII);
                        };
                        ArrayList llIlIIIlIIIlII = GUtils.getNearbyPlayers((Location)llIlIIIlIIIIIl, (double)16.0);
                        llIlIIIlIIIlII.stream().filter(llIlIIIlIIIlIl).forEach(llIlIIIlIIIllI);
                        FireworkEffect.Builder llIlIIIlIIIIll = FireworkEffect.builder();
                        "".length();
                        llIlIIIlIIIIll.withColor(llIlIIIIlllllI.MagicArcherSkill.this.getColor(llIlIIIIlllllI.MagicArcherSkill.this.getSelectedMode().getChatColor()));
                        "".length();
                        llIlIIIlIIIIll.with(FireworkEffect.Type.BALL_LARGE);
                        FireworkEffect llIlIIIIllIllI = llIlIIIlIIIIll.build();
                    }
                    llIlIIIIlllllI.MagicArcherSkill.this.flyingEffect = lIIlllIll[3];
                }
            };
            "".length();
            Bukkit.getScheduler().runTaskLater((Plugin)Com.getPlugin(), llIlIIIlIlIlII, 2L);
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode ENCHANCER;
        private static final /* synthetic */ int[] lIlIIIlI;
        public static final /* synthetic */ /* enum */ Mode ACCELERATOR;
        private static final /* synthetic */ String[] lIIllIlI;
        public static final /* synthetic */ /* enum */ Mode DRAINER;
        public static final /* synthetic */ /* enum */ Mode REPULSOR;

        static {
            Mode.lIIllllIIl();
            Mode.lIIlIIllII();
            ACCELERATOR = new Mode();
            ENCHANCER = new Mode();
            REPULSOR = new Mode();
            DRAINER = new Mode();
            Mode[] arrmode = new Mode[lIlIIIlI[4]];
            arrmode[Mode.lIlIIIlI[0]] = ACCELERATOR;
            arrmode[Mode.lIlIIIlI[1]] = ENCHANCER;
            arrmode[Mode.lIlIIIlI[2]] = REPULSOR;
            arrmode[Mode.lIlIIIlI[3]] = DRAINER;
            $VALUES = arrmode;
        }

        private static String lIIlIIlIlI(String lIlIIllIIIIIlll, String lIlIIllIIIIIlII) {
            try {
                SecretKeySpec lIlIIllIIIIlIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIllIIIIIlII.getBytes(StandardCharsets.UTF_8)), lIlIIIlI[5]), "DES");
                Cipher lIlIIllIIIIlIIl = Cipher.getInstance("DES");
                lIlIIllIIIIlIIl.init(lIlIIIlI[2], lIlIIllIIIIlIlI);
                return new String(lIlIIllIIIIlIIl.doFinal(Base64.getDecoder().decode(lIlIIllIIIIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIllIIIIlIII) {
                lIlIIllIIIIlIII.printStackTrace();
                return null;
            }
        }

        public static Mode valueOf(String lIlIIllIIlIlllI) {
            return Enum.valueOf(Mode.class, lIlIIllIIlIlllI);
        }

        private static void lIIllllIIl() {
            lIlIIIlI = new int[6];
            Mode.lIlIIIlI[0] = (85 ^ 17) & ~(251 ^ 191);
            Mode.lIlIIIlI[1] = " ".length();
            Mode.lIlIIIlI[2] = "  ".length();
            Mode.lIlIIIlI[3] = "   ".length();
            Mode.lIlIIIlI[4] = 183 ^ 179;
            Mode.lIlIIIlI[5] = (31 ^ 39) & ~(145 ^ 169) ^ (10 ^ 2);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static String lIIlIIlIIl(String lIlIIllIIIlIlll, String lIlIIllIIIllIll) {
            lIlIIllIIIlIlll = new String(Base64.getDecoder().decode(lIlIIllIIIlIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIIllIIIllIlI = new StringBuilder();
            char[] lIlIIllIIIllIIl = lIlIIllIIIllIll.toCharArray();
            int lIlIIllIIIllIII = lIlIIIlI[0];
            char[] lIlIIllIIIlIIlI = lIlIIllIIIlIlll.toCharArray();
            int lIlIIllIIIlIIIl = lIlIIllIIIlIIlI.length;
            int lIlIIllIIIlIIII = lIlIIIlI[0];
            while (Mode.lIIllllIlI(lIlIIllIIIlIIII, lIlIIllIIIlIIIl)) {
                char lIlIIllIIIlllIl = lIlIIllIIIlIIlI[lIlIIllIIIlIIII];
                "".length();
                lIlIIllIIIllIlI.append((char)(lIlIIllIIIlllIl ^ lIlIIllIIIllIIl[lIlIIllIIIllIII % lIlIIllIIIllIIl.length]));
                ++lIlIIllIIIllIII;
                ++lIlIIllIIIlIIII;
                "".length();
                if ("  ".length() > 0) continue;
                return null;
            }
            return String.valueOf(lIlIIllIIIllIlI);
        }

        private Mode() {
            Mode lIlIIllIIlIlIlI;
        }

        private static boolean lIIllllIlI(int n, int n2) {
            return n < n2;
        }

        private static void lIIlIIllII() {
            lIIllIlI = new String[lIlIIIlI[4]];
            Mode.lIIllIlI[Mode.lIlIIIlI[0]] = Mode.lIIlIIlIIl("DykXNSgLOBUkKxw=", "NjTpd");
            Mode.lIIllIlI[Mode.lIlIIIlI[1]] = Mode.lIIlIIlIIl("LyQyGAYkKTQC", "jjqPG");
            Mode.lIIllIlI[Mode.lIlIIIlI[2]] = Mode.lIIlIIlIlI("iwExpHgWB/vlKML1B3hFEA==", "wDlUu");
            Mode.lIIllIlI[Mode.lIlIIIlI[3]] = Mode.lIIlIIlIlI("P42HGjzctoE=", "gNDUG");
        }
    }

}

