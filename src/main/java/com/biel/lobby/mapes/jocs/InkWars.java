/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  com.biel.BielAPI.Utils.IconMenu
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEvent
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEventHandler
 *  com.biel.BielAPI.Utils.ItemButton
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEvent
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEventHandler
 *  com.biel.BielAPI.events.PlayerWorldEventBus
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.util.BlockIterator
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Pair;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class InkWars
extends JocEquips {
    /* synthetic */ HashMap<Block, Pair<String, Double>> highInkBlocks;
    private static final /* synthetic */ String[] lIlIlIIlI;
    private static final /* synthetic */ int[] lIllIlIlI;

    private static boolean lIlIIlllIlI(int n, int n2) {
        return n != n2;
    }

    private static boolean lIlIIlllIll(Object object, Object object2) {
        return object != object2;
    }

    private static void lIlIIlIlIIl() {
        lIlIlIIlI = new String[lIllIlIlI[53]];
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[0]] = InkWars.lIIllIIIllI("acnL+hZ9aK4=", "qAsul");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[1]] = InkWars.lIIllIIlIll("uWyz+3hcB7KmzzDHT0AJIPtLIvCjyQ/52Jm5qQzR8l40ek24PpHo5s08+ODnoBrqqjSbSUWW0JeYHa4BV/YJjmSIMp6A/Bxy", "TRQpv");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[2]] = InkWars.lIIllIIllIl("AwgmUSQzCXMTKnoPMgc6NABzHDwoAnMFOzsJc0ljf0c8F3MuDzZRPjsXcwEyMwknFDc=", "ZgSqS");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[3]] = InkWars.lIIllIIllIl("GglVEQw6FFUIBSNHDAoRcwsQEwE/RwAVRDYREBcdcw==", "Sgued");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[4]] = InkWars.lIIllIIIllI("6OPRsYq0Otu0zQDBsTt2YioLdz3gP5OW1U92iFdIliw=", "vPHkf");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[5]] = InkWars.lIIllIIlIll("3MPUbTK8bVRupjL5lH9KPHPnNwj/2rOYdsEJUUpQIOe2FIp7V/MyMbs9cew/2Ez8YdORw7lzi5I=", "yFHgF");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[6]] = InkWars.lIIllIIIllI("1INa2+KL4dUxdvN1/jvtbP4x7myZbVkOclfY3GWkP6PAObnYe2Nyo7SSLw8Vn/jN", "fBOdh");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[8]] = InkWars.lIIllIIIllI("tDE/fci2L4c=", "tubTW");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[9]] = InkWars.lIIllIIlIll("RMliYKQgjwo=", "xjUpf");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[10]] = InkWars.lIIllIIllIl("fm5jSng=", "SCYgU");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[12]] = InkWars.lIIllIIlIll("T267uU+hZmg=", "tmjeP");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[13]] = InkWars.lIIllIIIllI("ijEydUhLODE=", "tLvAU");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[16]] = InkWars.lIIllIIIllI("4mfdJJnakCufjxh5SNabiLltP2cxTc+V", "utpkX");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[17]] = InkWars.lIIllIIllIl("BAw3DTwqAzMbEhAIOQcUJx4=", "CmZhz");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[19]] = InkWars.lIIllIIlIll("DhBqIeZWwdY=", "dlFCs");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[20]] = InkWars.lIIllIIIllI("r2U2MQay25Kvw0zLNI4HIULaXGVsbK2gHyJlS+J05EOp27a2KTrRnG4Ok7OktHYfbdSbgJdy6F4=", "TlHdW");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[21]] = InkWars.lIIllIIlIll("1r9Vd8L2liI=", "bjNQm");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[22]] = InkWars.lIIllIIllIl("NiYsdw==", "bNIWY");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[23]] = InkWars.lIIllIIIllI("ecSLgXtHLO0F2p4I9XfS6w==", "pcfmi");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[24]] = InkWars.lIIllIIIllI("sJIY/fG6eK0OflTk2n9bllrnr7nqBPUQUWTW6Q+uo6isjNDbFnVVQA==", "tHpGU");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[25]] = InkWars.lIIllIIIllI("47WDfWAhHN7GQECMzSZzXkP1ng5yVq7S2uwwPiM8OCMUy2xyOpcryS5CvZOrkQCCKFh5tTlFnfcuS5zLkjt98w==", "tBnrm");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[26]] = InkWars.lIIllIIllIl("LBo1H1MlBykEICNvJ2skIw42BD1H", "fOfKs");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[27]] = InkWars.lIIllIIIllI("Fuc9svdJDnup+CrAGwZocOnaoH18wG0g26yM44jj83k=", "WjwHO");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[28]] = InkWars.lIIllIIIllI("8d4xpuNTNLSN6j/kT29D/Jy6sun4r2vLn3p+E0jqNk4=", "MXjyZ");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[29]] = InkWars.lIIllIIIllI("Yi4xlFYxc6q9Hl5csSeTG1qmnFDj9kK2xl/7uuR6BFQ=", "FlraT");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[30]] = InkWars.lIIllIIIllI("+xmgbTyLbaNvH9/xbRBybA==", "InKJX");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[31]] = InkWars.lIIllIIlIll("Z9lv7Z0FhAxLI6ZRwUlFZQ==", "BKePr");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[32]] = InkWars.lIIllIIllIl("WQ==", "xJFtb");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[34]] = InkWars.lIIllIIllIl("OBUACB8hADQBHBcbBQ==", "tpvms");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[35]] = InkWars.lIIllIIIllI("I4WFQ8uBrsmiro5CG9qvBw==", "xcSyu");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[37]] = InkWars.lIIllIIlIll("xl4/LFZX3XE=", "CIzQo");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[38]] = InkWars.lIIllIIIllI("eH2KzUmUpkOIYaJMKkgNuA==", "zBEbg");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[39]] = InkWars.lIIllIIlIll("ggYRWqUl50aqbbs2mtviISt9k7UCMCW42iXWuTR/hYM=", "Zarpx");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[40]] = InkWars.lIIllIIllIl("Lw0zFywWSCECLx0LJggxWA==", "xhRgC");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[41]] = InkWars.lIIllIIlIll("FRKlx9KzqnM=", "iZwNs");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[42]] = InkWars.lIIllIIIllI("dR6S8DumbBxIi8tqpf/rhA==", "ylwIb");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[43]] = InkWars.lIIllIIllIl("JA==", "yudpq");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[44]] = InkWars.lIIllIIlIll("cjAhQHiASEg=", "LTwAa");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[45]] = InkWars.lIIllIIIllI("XPo4PjgEHiM=", "clSOW");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[46]] = InkWars.lIIllIIlIll("2hgltcytmcM=", "inGJU");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[47]] = InkWars.lIIllIIllIl("NyobHQM=", "uXnnk");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[48]] = InkWars.lIIllIIlIll("VKxY/iypa4Q=", "FxcAv");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[49]] = InkWars.lIIllIIIllI("8cbsfLCsl+HOc9a/gdXGkA==", "GAujv");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[50]] = InkWars.lIIllIIlIll("11NoqNXRNcc=", "JKtnD");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[51]] = InkWars.lIIllIIllIl("PSsXPQs=", "xEsXy");
        InkWars.lIlIlIIlI[InkWars.lIllIlIlI[52]] = InkWars.lIIllIIlIll("br7FPbCV2vJPttYyEuQpNw==", "zEoXV");
    }

    public void initializeLevels() {
        InkWars lIIIlllllIIIll;
        Iterator<Player> lIIIlllllIIIIl = lIIIlllllIIIll.getPlayers().iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIlllllIIIIl.hasNext())) {
            Player lIIIlllllIIlII = lIIIlllllIIIIl.next();
            Joc.PlayerInfo lIIIlllllIIlIl = lIIIlllllIIIll.getPlayerInfo(lIIIlllllIIlII);
            ((InkWarsPlayerInfo)lIIIlllllIIlIl).setWeaponLevel(lIllIlIlI[1]);
            "".length();
            if ((145 ^ 149) >= 0) continue;
            return;
        }
    }

    public EquipInkWars getTeamOwningBlock(Block lIIIllIIlIIIlI) {
        InkWars lIIIllIIlIIlIl;
        if (InkWars.lIlIIllIIlI((Object)lIIIllIIlIIIlI)) {
            return null;
        }
        if (InkWars.lIlIIlIllII((int)lIIIllIIlIIlIl.isPaintable(lIIIllIIlIIIlI))) {
            return null;
        }
        Iterator lIIIllIIlIIIIl = lIIIllIIlIIlIl.Equips.iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIllIIlIIIIl.hasNext())) {
            JocEquips.Equip lIIIllIIlIIllI = (JocEquips.Equip)lIIIllIIlIIIIl.next();
            EquipInkWars lIIIllIIlIIlll = (EquipInkWars)lIIIllIIlIIllI;
            if (!InkWars.lIlIIlllIlI(lIIIllIIlIIlll.getColor().getWoolData(), lIIIllIIlIIIlI.getData()) || InkWars.lIlIIllIlll(lIIIllIIlIIlll.getStrongColor().getWoolData(), lIIIllIIlIIIlI.getData())) {
                return lIIIllIIlIIlll;
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return null;
    }

    private static boolean lIlIIlllIII(int n, int n2) {
        return n >= n2;
    }

    @Override
    protected void updateScoreBoard(Player lIIIllllIlIIII) {
        block4 : {
            InkWars lIIIllllIlIIIl;
            super.updateScoreBoard(lIIIllllIlIIII);
            if (!InkWars.lIlIIlIlIll((int)lIIIllllIlIIIl.JocIniciat.booleanValue()) || !InkWars.lIlIIlIllII((int)lIIIllllIlIIIl.JocFinalitzat.booleanValue())) break block4;
            ArrayList<String> lIIIllllIlIlIl = new ArrayList<String>();
            ArrayList<Integer> lIIIllllIlIlII = new ArrayList<Integer>();
            Iterator lIIIllllIIllIl = lIIIllllIlIIIl.Equips.iterator();
            while (InkWars.lIlIIlIlIll((int)lIIIllllIIllIl.hasNext())) {
                JocEquips.Equip lIIIllllIlIllI = (JocEquips.Equip)lIIIllllIIllIl.next();
                try {
                    "".length();
                    lIIIllllIlIlIl.add(lIIIllllIlIllI.getAdjectiuColored());
                    EquipInkWars lIIIllllIlIlll = (EquipInkWars)lIIIllllIlIllI;
                    "".length();
                    lIIIllllIlIlII.add((int)Math.round(lIIIllllIlIlll.getOwnedPercent()));
                    "".length();
                }
                catch (Exception lIIIllllIlIlll) {
                    // empty catch block
                }
                if (-"   ".length() > 0) {
                    return;
                }
                "".length();
                if ((12 ^ 8) >= 0) continue;
                return;
            }
            ScoreBoardUpdater.setScoreBoard(lIIIllllIlIIII, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIlIlIIlI[lIllIlIlI[8]]).append((Object)ChatColor.DARK_AQUA).append(lIlIlIIlI[lIllIlIlI[9]]).append(lIIIllllIlIIIl.getTimer())), lIIIllllIlIlIl, lIIIllllIlIlII);
        }
    }

    private static String lIIllIIIllI(String lIIIlIlIIIlIIl, String lIIIlIlIIIlIII) {
        try {
            SecretKeySpec lIIIlIlIIIlllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlIlIIIlIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIlIlIIIllIl = Cipher.getInstance("Blowfish");
            lIIIlIlIIIllIl.init(lIllIlIlI[2], lIIIlIlIIIlllI);
            return new String(lIIIlIlIIIllIl.doFinal(Base64.getDecoder().decode(lIIIlIlIIIlIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIlIlIIIllII) {
            lIIIlIlIIIllII.printStackTrace();
            return null;
        }
    }

    public int getRemainingSeconds() {
        InkWars lIIIlllIllIlIl;
        return lIIIlllIllIlIl.getGameFinishSeconds() - lIIIlllIllIlIl.segonsTranscorreguts();
    }

    private static boolean lIlIIllIllI(int n) {
        return n < 0;
    }

    private static int lIlIIlIllIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    @Override
    protected void setCustomGameRules() {
    }

    private static boolean lIlIIllIIlI(Object object) {
        return object == null;
    }

    private static void lIlIIlIlIlI() {
        lIllIlIlI = new int[54];
        InkWars.lIllIlIlI[0] = (246 ^ 193) & ~(21 ^ 34);
        InkWars.lIllIlIlI[1] = " ".length();
        InkWars.lIllIlIlI[2] = "  ".length();
        InkWars.lIllIlIlI[3] = "   ".length();
        InkWars.lIllIlIlI[4] = 199 ^ 195;
        InkWars.lIllIlIlI[5] = 105 + 99 - 193 + 144 ^ 84 + 155 - 175 + 94;
        InkWars.lIllIlIlI[6] = 69 ^ 61 ^ (52 ^ 74);
        InkWars.lIllIlIlI[7] = -1 & Integer.MAX_VALUE;
        InkWars.lIllIlIlI[8] = 221 ^ 136 ^ (209 ^ 131);
        InkWars.lIllIlIlI[9] = 150 ^ 158;
        InkWars.lIllIlIlI[10] = 119 ^ 16 ^ (123 ^ 21);
        InkWars.lIllIlIlI[11] = 253 ^ 193;
        InkWars.lIllIlIlI[12] = 61 ^ 55;
        InkWars.lIllIlIlI[13] = 40 ^ 35;
        InkWars.lIllIlIlI[14] = 220 ^ 184;
        InkWars.lIllIlIlI[15] = -19175 & 19454;
        InkWars.lIllIlIlI[16] = 172 ^ 160;
        InkWars.lIllIlIlI[17] = 226 ^ 198 ^ (3 ^ 42);
        InkWars.lIllIlIlI[18] = 225 ^ 153;
        InkWars.lIllIlIlI[19] = 225 ^ 176 ^ (3 ^ 92);
        InkWars.lIllIlIlI[20] = 41 ^ 56 ^ (88 ^ 70);
        InkWars.lIllIlIlI[21] = 152 + 69 - 140 + 74 ^ 7 + 107 - -21 + 4;
        InkWars.lIllIlIlI[22] = 68 ^ 85;
        InkWars.lIllIlIlI[23] = 142 ^ 156;
        InkWars.lIllIlIlI[24] = 81 ^ 43 ^ (104 ^ 1);
        InkWars.lIllIlIlI[25] = 53 ^ 33;
        InkWars.lIllIlIlI[26] = 105 + 49 - 119 + 103 ^ 29 + 99 - -9 + 22;
        InkWars.lIllIlIlI[27] = 139 ^ 198 ^ (2 ^ 89);
        InkWars.lIllIlIlI[28] = 46 ^ 57;
        InkWars.lIllIlIlI[29] = 36 ^ 60;
        InkWars.lIllIlIlI[30] = 6 ^ 31;
        InkWars.lIllIlIlI[31] = 122 ^ 96;
        InkWars.lIllIlIlI[32] = 159 ^ 132;
        InkWars.lIllIlIlI[33] = -27139 & 27638;
        InkWars.lIllIlIlI[34] = "  ".length() ^ (44 ^ 50);
        InkWars.lIllIlIlI[35] = 101 ^ 120;
        InkWars.lIllIlIlI[36] = -(-2577 & 31263) & (-2082 & 32767);
        InkWars.lIllIlIlI[37] = 160 ^ 190;
        InkWars.lIllIlIlI[38] = 171 ^ 180;
        InkWars.lIllIlIlI[39] = 102 ^ 70;
        InkWars.lIllIlIlI[40] = 7 ^ 38;
        InkWars.lIllIlIlI[41] = 41 ^ 118 ^ (185 ^ 196);
        InkWars.lIllIlIlI[42] = 181 ^ 150;
        InkWars.lIllIlIlI[43] = 121 ^ 93;
        InkWars.lIllIlIlI[44] = 175 ^ 138;
        InkWars.lIllIlIlI[45] = 5 + 3 - -109 + 69 ^ 26 + 127 - 13 + 16;
        InkWars.lIllIlIlI[46] = 175 + 95 - 216 + 123 ^ 6 + 49 - -42 + 53;
        InkWars.lIllIlIlI[47] = 227 ^ 176 ^ (90 ^ 33);
        InkWars.lIllIlIlI[48] = 18 ^ 11 ^ (91 ^ 107);
        InkWars.lIllIlIlI[49] = 110 ^ 115 ^ (179 ^ 132);
        InkWars.lIllIlIlI[50] = 129 + 26 - 72 + 87 ^ 93 + 20 - 96 + 112;
        InkWars.lIllIlIlI[51] = 36 ^ 8;
        InkWars.lIllIlIlI[52] = 236 ^ 142 ^ (127 ^ 48);
        InkWars.lIllIlIlI[53] = 157 ^ 179;
    }

    @Override
    public void heartbeat() {
        InkWars lIIIllIllllllI;
        super.heartbeat();
        if (InkWars.lIlIIlIlIll((int)lIIIllIllllllI.JocIniciat.booleanValue())) {
            lIIIllIllllllI.controlWeaponSelector();
            lIIIllIllllllI.updateScoreBoards();
            lIIIllIllllllI.checkForWinner();
        }
    }

    private static boolean lIlIIlIllII(int n) {
        return n == 0;
    }

    protected void processHighInkBlocks() {
        InkWars lIIIllIIlllIlI;
        Object lIIIllIIllllll;
        HashMap<Block, Pair<String, Double>> lIIIllIIlllIIl = new HashMap<Block, Pair<String, Double>>();
        Iterator<Map.Entry<Block, Pair<String, Double>>> lIIIllIIlllIII = lIIIllIIlllIlI.highInkBlocks.entrySet().iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIllIIlllIII.hasNext())) {
            lIIIllIIllllll = lIIIllIIlllIII.next();
            ((Pair)lIIIllIIllllll.getValue()).setSecond((Double)((Pair)lIIIllIIllllll.getValue()).getSecond() - 0.05);
            if (InkWars.lIlIIlIlllI(InkWars.lIlIIlllIIl((Double)((Pair)lIIIllIIllllll.getValue()).getSecond(), 0.0))) {
                lIIIllIIlllIII.remove();
                Block lIIIllIlIIIllI = (Block)lIIIllIIllllll.getKey();
                if (InkWars.lIlIIllIlII(lIIIllIIlllIlI.getTeamOwningBlock(lIIIllIlIIIllI))) {
                    lIIIllIlIIIllI.setData(lIIIllIIlllIlI.getTeamOwningBlock(lIIIllIlIIIllI).getColor().getWoolData());
                }
                "".length();
                if ("  ".length() == ((46 ^ 123) & ~(17 ^ 68))) {
                    return;
                }
            } else {
                Block lIIIllIlIIIIlI = (Block)lIIIllIIllllll.getKey();
                double lIIIllIlIIIIIl = (Double)((Pair)lIIIllIIllllll.getValue()).getSecond();
                int lIIIllIlIIIIII = (int)(7L + Math.round(lIIIllIlIIIIIl * 12.0));
                if (InkWars.lIlIIlIlIll((int)Utils.Possibilitat(lIIIllIlIIIIII, lIllIlIlI[36]))) {
                    Block lIIIllIlIIIlIl = lIIIllIlIIIIlI.getRelative(BlockFace.DOWN);
                    double lIIIllIlIIIlII = lIIIllIlIIIIlI.getLocation().distance(lIIIllIlIIIlIl.getLocation());
                    Player lIIIllIlIIIIll = Bukkit.getPlayer((String)((String)((Pair)lIIIllIIllllll.getValue()).getFirst()));
                    if (InkWars.lIlIIllIlII((Object)lIIIllIlIIIIll)) {
                        int n;
                        if (InkWars.lIlIIlIlIll((int)Utils.Possibilitat(lIllIlIlI[21]))) {
                            n = lIllIlIlI[1];
                            "".length();
                            if ((126 ^ 103 ^ (164 ^ 185)) < 0) {
                                return;
                            }
                        } else {
                            n = lIllIlIlI[1];
                        }
                        "".length();
                        lIIIllIIlllIIl.put(lIIIllIlIIIlIl, new Pair<String, Double>(lIIIllIlIIIIll.getName(), lIIIllIlIIIIIl * (double)n));
                        ((Pair)lIIIllIIllllll.getValue()).setSecond((Double)((Pair)lIIIllIIllllll.getValue()).getSecond() / 4.0);
                    }
                }
            }
            "".length();
            if (-" ".length() <= 0) continue;
            return;
        }
        lIIIllIIllllll = lIIIllIIlllIIl.entrySet().iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIllIIllllll.hasNext())) {
            Map.Entry lIIIllIIlllIll = (Map.Entry)lIIIllIIllllll.next();
            Block lIIIllIIllllIl = (Block)lIIIllIIlllIll.getKey();
            Player lIIIllIIllllII = Bukkit.getPlayer((String)((String)((Pair)lIIIllIIlllIll.getValue()).getFirst()));
            if (InkWars.lIlIIllIlII((Object)lIIIllIIllllII)) {
                InkWeapon lIIIllIIlllllI = ((InkWarsPlayerInfo)lIIIllIIlllIlI.getPlayerInfo(lIIIllIIllllII)).getActiveWeapon();
                lIIIllIIlllllI.paintBlock(lIIIllIIllllIl, (Double)((Pair)lIIIllIIlllIll.getValue()).getSecond());
            }
            "".length();
            if ("   ".length() != 0) continue;
            return;
        }
    }

    @Override
    protected void donarEfectesInicials(Player lIIIllllllllll) {
        InkWars lIIlIIIIIIIIII;
        super.donarEfectesInicials(lIIIllllllllll);
        double lIIIlllllllllI = lIIlIIIIIIIIII.getBalancingMultiplier(lIIlIIIIIIIIII.obtenirEquip(lIIIllllllllll));
        "".length();
        lIIIllllllllll.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, lIllIlIlI[7], lIllIlIlI[3], lIllIlIlI[1]), lIllIlIlI[1]);
    }

    private static boolean lIlIIllllIl(int n, int n2) {
        return n < n2;
    }

    @Override
    public EquipInkWars obtenirEquip(Player lIIlIIIIIIlllI) {
        InkWars lIIlIIIIIIllIl;
        return lIIlIIIIIIllIl.obtenirEquip(lIIlIIIIIIlllI, EquipInkWars.class);
    }

    private static int lIlIIlIllll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public boolean isPaintable(Block lIIIlIllllllll) {
        ArrayList<Material> lIIIlIlllllllI = new ArrayList<Material>();
        "".length();
        lIIIlIlllllllI.add(Material.WOOL);
        "".length();
        lIIIlIlllllllI.add(Material.STAINED_CLAY);
        "".length();
        lIIIlIlllllllI.add(Material.STAINED_GLASS);
        "".length();
        lIIIlIlllllllI.add(Material.STAINED_GLASS_PANE);
        Material lIIIlIllllllIl = lIIIlIllllllll.getType();
        return lIIIlIlllllllI.contains((Object)lIIIlIllllllIl);
    }

    protected void onPlayerInteract(PlayerInteractEvent lIIIlIlllIIIIl, Player lIIIlIlllIIlII) {
        InkWars lIIIlIlllIIIlI;
        super.onPlayerInteract(lIIIlIlllIIIIl, lIIIlIlllIIlII);
        Block lIIIlIlllIIIll = lIIIlIlllIIIIl.getClickedBlock();
        if (InkWars.lIlIIllIlII((Object)lIIIlIlllIIIll) && InkWars.lIlIIllllII((Object)lIIIlIlllIIIll.getType(), (Object)Material.WALL_SIGN)) {
            lIIIlIlllIIIlI.openWeaponSelectionMenu(lIIIlIlllIIlII);
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIIIlllllIllll) {
        InkWars lIIIllllllIIII;
        ArrayList<ItemStack> lIIIllllllIIlI = new ArrayList<ItemStack>();
        EquipInkWars lIIIllllllIIIl = lIIIllllllIIII.obtenirEquip(lIIIlllllIllll);
        "".length();
        lIIIllllllIIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, lIIIllllllIIIl));
        "".length();
        lIIIllllllIIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lIIIllllllIIIl));
        "".length();
        lIIIllllllIIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, lIIIllllllIIIl));
        "".length();
        lIIIllllllIIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lIIIllllllIIIl));
        return lIIIllllllIIlI;
    }

    public void controlWeaponSelector() {
        InkWars lIIIllIllIllIl;
        Iterator<Player> lIIIllIllIllII = lIIIllIllIllIl.getPlayers().iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIllIllIllII.hasNext())) {
            int n;
            int lIIIllIlllIIII;
            Player lIIIllIllIllll = lIIIllIllIllII.next();
            EquipInkWars lIIIllIlllIIlI = lIIIllIllIllIl.obtenirEquip(lIIIllIllIllll);
            Joc.PlayerInfo lIIIllIlllIIIl = lIIIllIllIllIl.getPlayerInfo(lIIIllIllIllll);
            if (InkWars.lIlIIllIllI(InkWars.lIlIIllIlIl(lIIIllIllIllll.getLocation().distance(lIIIllIlllIIlI.getTeamSpawnLocation()), 10.0))) {
                n = lIllIlIlI[1];
                "".length();
                if ((133 ^ 129) <= 0) {
                    return;
                }
            } else {
                n = lIllIlIlI[0];
            }
            if (InkWars.lIlIIlIlIll(lIIIllIlllIIII = n)) {
                if (InkWars.lIlIIlIllII((int)lIIIllIllIllll.getInventory().contains(Material.WORKBENCH))) {
                    lIIIllIllIllIl.giveWeaponSelectionButton(lIIIllIllIllll);
                    lIIIllIllIllIl.sendPlayerMessage(lIIIllIllIllll, String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIlIlIIlI[lIllIlIlI[24]])));
                    "".length();
                    if ((24 ^ 28) != (168 ^ 172)) {
                        return;
                    }
                }
            } else if (InkWars.lIlIIlIlIll((int)lIIIllIllIllll.getInventory().contains(Material.WORKBENCH))) {
                lIIIllIllIllll.getInventory().remove(Material.WORKBENCH);
                InkWeapon lIIIllIlllIIll = ((InkWarsPlayerInfo)lIIIllIlllIIIl).getActiveWeapon();
                if (InkWars.lIlIIllIIlI((Object)lIIIllIlllIIll)) {
                    String lIIIllIlllIlII = lIlIlIIlI[lIllIlIlI[25]];
                    if (InkWars.lIlIIllIlll(((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponAlerts(), lIllIlIlI[2])) {
                        lIIIllIlllIlII = lIlIlIIlI[lIllIlIlI[26]];
                    }
                    if (InkWars.lIlIIllIlll(((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponAlerts(), lIllIlIlI[3])) {
                        lIIIllIlllIlII = lIlIlIIlI[lIllIlIlI[27]];
                    }
                    if (InkWars.lIlIIlllIII(((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponAlerts(), lIllIlIlI[4])) {
                        lIIIllIlllIlII = lIlIlIIlI[lIllIlIlI[28]];
                    }
                    lIIIllIllIllIl.sendPlayerMessage(lIIIllIllIllll, String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(lIIIllIlllIlII)));
                    ((InkWarsPlayerInfo)lIIIllIlllIIIl).registerWeaponAlert();
                    lIIIllIlllIIlI.teleportToTeamSpawn(lIIIllIllIllll);
                }
            }
            if (InkWars.lIlIIllIIII(((InkWarsPlayerInfo)lIIIllIlllIIIl).getAlivePaintedBlocks(), ((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponLevel() * lIIIllIllIllIl.getBlockCountToLevelUp())) {
                ((InkWarsPlayerInfo)lIIIllIlllIIIl).setWeaponLevel(((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponLevel() + lIllIlIlI[1]);
                lIIIllIllIllIl.sendPlayerMessage(lIIIllIllIllll, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIlIlIIlI[lIllIlIlI[29]]).append(((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponLevel())));
                lIIIllIllIllIl.sendTeamMessage(lIIIllIlllIIlI, String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIlIlIIlI[lIllIlIlI[30]]).append((Object)ChatColor.YELLOW).append(lIIIllIllIllll.getName()).append((Object)ChatColor.GRAY).append(lIlIlIIlI[lIllIlIlI[31]]).append((Object)ChatColor.YELLOW).append(((InkWarsPlayerInfo)lIIIllIlllIIIl).getWeaponLevel()).append((Object)ChatColor.WHITE).append(lIlIlIIlI[lIllIlIlI[32]])));
            }
            "".length();
            if ((168 ^ 172) >= " ".length()) continue;
            return;
        }
    }

    public void giveWeaponSelectionButton(Player lIIIllIIIllIlI) {
        InkWars lIIIllIIIllIII;
        String[] arrstring = new String[lIllIlIlI[1]];
        arrstring[InkWars.lIllIlIlI[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIlIIlI[lIllIlIlI[39]]));
        ItemButton lIIIllIIIllIIl = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.WORKBENCH), String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIlIIlI[lIllIlIlI[37]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[38]])), arrstring), lIIIllIIIllIlI, lIIIlIlIllllII -> {
            InkWars lIIIlIlIlllIll;
            lIIIlIlIlllIll.openWeaponSelectionMenu(lIIIlIlIllllII.getPlayer());
        });
        ItemStack[] arritemStack = new ItemStack[lIllIlIlI[1]];
        arritemStack[InkWars.lIllIlIlI[0]] = lIIIllIIIllIIl.getItemStack();
        "".length();
        lIIIllIIIllIlI.getInventory().addItem(arritemStack);
    }

    public void openWeaponSelectionMenu(Player lIIIllIIIIlIII) {
        InkWars lIIIllIIIIllll;
        IconMenu lIIIllIIIIllIl = new IconMenu(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIlIlIIlI[lIllIlIlI[40]]).append(Utils.NombreEntre(lIllIlIlI[0], lIllIlIlI[14]))), lIllIlIlI[10], lIIIlIllIIIllI -> {
            InkWars lIIIlIllIIIlll;
            lIIIlIllIIIllI.setWillClose(lIllIlIlI[1]);
            Player lIIIlIllIIIlIl = lIIIlIllIIIllI.getPlayer();
            int lIIIlIllIIIlII = lIIIlIllIIIllI.getPosition();
            if (InkWars.lIlIIlIllII(lIIIlIllIIIlII)) {
                ((InkWarsPlayerInfo)lIIIlIllIIIlll.getPlayerInfo(lIIIlIllIIIlIl)).setActiveWeapon(lIIIlIllIIIlll.new RollerInkWeapon(lIIIlIllIIIlIl));
            }
            if (InkWars.lIlIIllIlll(lIIIlIllIIIlII, lIllIlIlI[1])) {
                ((InkWarsPlayerInfo)lIIIlIllIIIlll.getPlayerInfo(lIIIlIllIIIlIl)).setActiveWeapon(lIIIlIllIIIlll.new BrushInkWeapon(lIIIlIllIIIlIl));
            }
            if (InkWars.lIlIIllIlll(lIIIlIllIIIlII, lIllIlIlI[2])) {
                ((InkWarsPlayerInfo)lIIIlIllIIIlll.getPlayerInfo(lIIIlIllIIIlIl)).setActiveWeapon(lIIIlIllIIIlll.new MachinegunInkWeapon(lIIIlIllIIIlIl));
            }
            if (InkWars.lIlIIllIlll(lIIIlIllIIIlII, lIllIlIlI[3])) {
                ((InkWarsPlayerInfo)lIIIlIllIIIlll.getPlayerInfo(lIIIlIllIIIlIl)).setActiveWeapon(lIIIlIllIIIlll.new EnderInkWeapon(lIIIlIllIIIlIl));
            }
            lIIIlIllIIIlll.sendTeamMessage(lIIIlIllIIIlll.obtenirEquip(lIIIlIllIIIllI.getPlayer()), String.valueOf(new StringBuilder().append(lIIIlIllIIIllI.getPlayer().getName()).append(lIlIlIIlI[lIllIlIlI[52]]).append(lIIIlIllIIIllI.getMenu().getOptionNames()[lIIIlIllIIIlII])));
        });
        int lIIIllIIIIllII = ((InkWarsPlayerInfo)lIIIllIIIIllll.getPlayerInfo(lIIIllIIIIlIII)).getWeaponLevel();
        String lIIIllIIIIlIll = Integer.toString(lIIIllIIIIllII);
        String lIIIllIIIIlIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIlIIlI[lIllIlIlI[41]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[42]]).append(lIIIllIIIIlIll).append(lIlIlIIlI[lIllIlIlI[43]]));
        "".length();
        lIIIllIIIIllIl.setOption(lIllIlIlI[0], new ItemStack(Material.STICK, lIllIlIlI[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIlIIlI[lIllIlIlI[44]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[45]]).append(lIIIllIIIIlIlI)), new String[lIllIlIlI[0]]);
        "".length();
        lIIIllIIIIllIl.setOption(lIllIlIlI[1], new ItemStack(Material.TORCH, lIllIlIlI[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIlIlIIlI[lIllIlIlI[46]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[47]]).append(lIIIllIIIIlIlI)), new String[lIllIlIlI[0]]);
        "".length();
        lIIIllIIIIllIl.setOption(lIllIlIlI[2], new ItemStack(Material.SNOW_BALL, lIllIlIlI[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIlIlIIlI[lIllIlIlI[48]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[49]]).append(lIIIllIIIIlIlI)), new String[lIllIlIlI[0]]);
        "".length();
        lIIIllIIIIllIl.setOption(lIllIlIlI[3], new ItemStack(Material.ENDER_PEARL, lIllIlIlI[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.LIGHT_PURPLE).append(lIlIlIIlI[lIllIlIlI[50]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[51]]).append(lIIIllIIIIlIlI)), new String[lIllIlIlI[0]]);
        lIIIllIIIIllIl.open(lIIIllIIIIlIII);
    }

    public void winAction(EquipInkWars lIIIlllIIlIIll) {
        InkWars lIIIlllIIlIlII;
        Iterator<Player> lIIIlllIIlIIlI = lIIIlllIIlIIll.getPlayers().iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIlllIIlIIlI.hasNext())) {
            Player lIIIlllIIlIlll = lIIIlllIIlIIlI.next();
            lIIIlllIIlIlII.getWorld().playEffect(lIIIlllIIlIlll.getEyeLocation(), Effect.FIREWORKS_SPARK, lIllIlIlI[0]);
            "".length();
            if (" ".length() >= ((203 ^ 158) & ~(34 ^ 119))) continue;
            return;
        }
        lIIIlllIIlIlII.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)lIIIlllIIlIIll.getChatColor()).append(lIlIlIIlI[lIllIlIlI[21]]).append((Object)ChatColor.BOLD).append(lIlIlIIlI[lIllIlIlI[22]]).append(lIIIlllIIlIIll.getAdjectiu().toLowerCase()).append(lIlIlIIlI[lIllIlIlI[23]])));
        lIIIlllIIlIlII.JocFinalitzat();
    }

    private static boolean lIlIIllIlII(Object object) {
        return object != null;
    }

    protected String getTimer() {
        InkWars lIIIlllIllllIl;
        if (!InkWars.lIlIIlIllII((int)lIIIlllIllllIl.JocFinalitzat.booleanValue()) || InkWars.lIlIIlIllII((int)lIIIlllIllllIl.JocIniciat.booleanValue())) {
            return String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIlIIlI[lIllIlIlI[10]]));
        }
        int lIIIllllIIIIlI = lIIIlllIllllIl.getRemainingSeconds();
        int lIIIllllIIIIIl = Math.round(lIIIllllIIIIlI / lIllIlIlI[11]);
        int lIIIllllIIIIII = (int)(((double)lIIIllllIIIIlI / 60.0 - (double)lIIIllllIIIIIl) * 60.0);
        ChatColor lIIIlllIllllll = ChatColor.DARK_GREEN;
        double lIIIlllIlllllI = lIIIlllIllllIl.getRemainingTimePercent();
        if (InkWars.lIlIIlIlllI(InkWars.lIlIIlIllIl(lIIIlllIlllllI, 50.0))) {
            lIIIlllIllllll = ChatColor.GREEN;
        }
        if (InkWars.lIlIIlIlllI(InkWars.lIlIIlIllIl(lIIIlllIlllllI, 25.0))) {
            lIIIlllIllllll = ChatColor.YELLOW;
        }
        if (InkWars.lIlIIlIlllI(InkWars.lIlIIlIllIl(lIIIlllIlllllI, 12.0))) {
            lIIIlllIllllll = ChatColor.RED;
        }
        if (InkWars.lIlIIlIlllI(InkWars.lIlIIlIllIl(lIIIlllIlllllI, 5.0))) {
            lIIIlllIllllll = ChatColor.DARK_RED;
        }
        return String.valueOf(new StringBuilder().append((Object)lIIIlllIllllll).append(lIlIlIIlI[lIllIlIlI[12]]).append(lIIIllllIIIIIl).append(lIlIlIIlI[lIllIlIlI[13]]).append(lIIIllllIIIIII));
    }

    int getGameFinishSeconds() {
        InkWars lIIIlllIlIllIl;
        int lIIIlllIlIlllI = lIllIlIlI[15] + lIIIlllIlIllIl.getPlayers().size() * lIllIlIlI[12];
        if (InkWars.lIlIIlIlIll((int)lIIIlllIlIllIl.pMapaActual().ExisteixPropietat(lIlIlIIlI[lIllIlIlI[16]]))) {
            lIIIlllIlIlllI = lIIIlllIlIllIl.pMapaActual().ObtenirPropietatInt(lIlIlIIlI[lIllIlIlI[17]]);
        }
        return lIIIlllIlIlllI;
    }

    private static String lIIllIIllIl(String lIIIlIlIlIIIII, String lIIIlIlIIlllll) {
        lIIIlIlIlIIIII = new String(Base64.getDecoder().decode(lIIIlIlIlIIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIlIlIIllllI = new StringBuilder();
        char[] lIIIlIlIIlllIl = lIIIlIlIIlllll.toCharArray();
        int lIIIlIlIIlllII = lIllIlIlI[0];
        char[] lIIIlIlIIlIllI = lIIIlIlIlIIIII.toCharArray();
        int lIIIlIlIIlIlIl = lIIIlIlIIlIllI.length;
        int lIIIlIlIIlIlII = lIllIlIlI[0];
        while (InkWars.lIlIIllllIl(lIIIlIlIIlIlII, lIIIlIlIIlIlIl)) {
            char lIIIlIlIlIIIIl = lIIIlIlIIlIllI[lIIIlIlIIlIlII];
            "".length();
            lIIIlIlIIllllI.append((char)(lIIIlIlIlIIIIl ^ lIIIlIlIIlllIl[lIIIlIlIIlllII % lIIIlIlIIlllIl.length]));
            ++lIIIlIlIIlllII;
            ++lIIIlIlIIlIlII;
            "".length();
            if (" ".length() > -" ".length()) continue;
            return null;
        }
        return String.valueOf(lIIIlIlIIllllI);
    }

    private static int lIlIIllIlIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public double getRemainingTimePercent() {
        InkWars lIIIlllIllIIlI;
        return lIllIlIlI[14] * lIIIlllIllIIlI.getRemainingSeconds() / lIIIlllIllIIlI.getGameFinishSeconds();
    }

    private static boolean lIlIIllIlll(int n, int n2) {
        return n == n2;
    }

    private static boolean lIlIIllIIIl(int n) {
        return n >= 0;
    }

    private static boolean lIlIIllIIII(int n, int n2) {
        return n > n2;
    }

    public InkWars() {
        InkWars lIIlIIIIIlIlII;
        lIIlIIIIIlIlII.highInkBlocks = new HashMap();
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return lIllIlIlI[0];
    }

    @Override
    protected boolean isRecallEnabled() {
        return lIllIlIlI[1];
    }

    private static boolean lIlIIllIIll(int n) {
        return n > 0;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        return null;
    }

    public void checkForWinner() {
        InkWars lIIIlllIlIIIII;
        Object lIIIlllIlIIlIl;
        Iterator lIIIlllIIlllll = lIIIlllIlIIIII.Equips.iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIlllIIlllll.hasNext())) {
            lIIIlllIlIIlIl = (JocEquips.Equip)lIIIlllIIlllll.next();
            EquipInkWars lIIIlllIlIIllI = (EquipInkWars)lIIIlllIlIIlIl;
            if (InkWars.lIlIIllIIII(lIIIlllIlIIIII.segonsTranscorreguts(), lIllIlIlI[18]) && InkWars.lIlIIllIIIl(InkWars.lIlIIlIllll(lIIIlllIlIIllI.getOwnedPercent(), 80.0))) {
                lIIIlllIlIIIII.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIIIlllIlIIllI.getAdjectiuColored()).append(lIlIlIIlI[lIllIlIlI[19]]).append((Object)ChatColor.GOLD).append(lIlIlIIlI[lIllIlIlI[20]])));
                lIIIlllIlIIIII.winGame(lIIIlllIlIIllI);
            }
            "".length();
            if (-" ".length() < " ".length()) continue;
            return;
        }
        if (InkWars.lIlIIlIlllI(lIIIlllIlIIIII.getRemainingSeconds())) {
            EquipInkWars lIIIlllIlIIIlI = null;
            lIIIlllIlIIlIl = lIIIlllIlIIIII.Equips.iterator();
            while (InkWars.lIlIIlIlIll((int)lIIIlllIlIIlIl.hasNext())) {
                JocEquips.Equip lIIIlllIlIIIll = (JocEquips.Equip)lIIIlllIlIIlIl.next();
                EquipInkWars lIIIlllIlIIlII = (EquipInkWars)lIIIlllIlIIIll;
                if (InkWars.lIlIIllIIlI(lIIIlllIlIIIlI)) {
                    lIIIlllIlIIIlI = lIIIlllIlIIlII;
                }
                if (InkWars.lIlIIllIIll(InkWars.lIlIIlIllll(lIIIlllIlIIlII.getOwnedPercent(), lIIIlllIlIIIlI.getOwnedPercent()))) {
                    lIIIlllIlIIIlI = lIIIlllIlIIlII;
                }
                "".length();
                if (-"   ".length() <= 0) continue;
                return;
            }
            if (InkWars.lIlIIllIlII(lIIIlllIlIIIlI)) {
                lIIIlllIlIIIII.winGame(lIIIlllIlIIIlI);
            }
        }
    }

    public void tickPlayerWeapons() {
        InkWars lIIIllIlIlIllI;
        Iterator<Player> lIIIllIlIlIlIl = lIIIllIlIlIllI.getPlayers().iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIllIlIlIlIl.hasNext())) {
            Player lIIIllIlIllIII = lIIIllIlIlIlIl.next();
            Joc.PlayerInfo lIIIllIlIllIlI = lIIIllIlIlIllI.getPlayerInfo(lIIIllIlIllIII);
            InkWeapon lIIIllIlIllIIl = ((InkWarsPlayerInfo)lIIIllIlIllIlI).getActiveWeapon();
            if (InkWars.lIlIIllIlII((Object)lIIIllIlIllIIl)) {
                lIIIllIlIllIIl.tick();
            }
            "".length();
            if ((43 + 16 - 39 + 151 ^ 4 + 65 - 31 + 137) != 0) continue;
            return;
        }
    }

    public int getTotalPaintedBlocks() {
        InkWars lIIIlllIIIlIIl;
        int lIIIlllIIIlIII = lIllIlIlI[0];
        Iterator lIIIlllIIIIlIl = lIIIlllIIIlIIl.Equips.iterator();
        while (InkWars.lIlIIlIlIll((int)lIIIlllIIIIlIl.hasNext())) {
            JocEquips.Equip lIIIlllIIIlIlI = (JocEquips.Equip)lIIIlllIIIIlIl.next();
            EquipInkWars lIIIlllIIIlIll = (EquipInkWars)lIIIlllIIIlIlI;
            lIIIlllIIIlIII += lIIIlllIIIlIll.getOwnedBlocks();
            "".length();
            if (null == null) continue;
            return (91 ^ 105) & ~(132 ^ 182);
        }
        return lIIIlllIIIlIII;
    }

    @Override
    public String getGameName() {
        return lIlIlIIlI[lIllIlIlI[0]];
    }

    @Override
    protected void customJocIniciat() {
        InkWars lIIIlllllIlIll;
        super.customJocIniciat();
        lIIIlllllIlIll.setGiveStartingItemsRespawn(lIllIlIlI[0]);
        lIIIlllllIlIll.initializeLevels();
    }

    public boolean isPaintableUnsafely(Block lIIIlIllllIIlI) {
        InkWars lIIIlIllllIllI;
        int n;
        Material lIIIlIllllIlII = lIIIlIllllIIlI.getType();
        if (InkWars.lIlIIlIlIll((int)lIIIlIllllIlII.isBlock()) && InkWars.lIlIIlIlIll((int)lIIIlIllllIlII.isOccluding()) && InkWars.lIlIIlllIll((Object)lIIIlIllllIlII, (Object)Material.BARRIER) && InkWars.lIlIIlIllII((int)lIIIlIllllIllI.isPaintable(lIIIlIllllIIlI))) {
            n = lIllIlIlI[1];
            "".length();
            if (" ".length() == ("  ".length() & ("  ".length() ^ -" ".length()))) {
                return (boolean)((196 + 42 - 107 + 90 ^ 122 + 111 - 158 + 124) & (99 + 142 - 212 + 117 ^ 118 + 2 - 61 + 77 ^ -" ".length()));
            }
        } else {
            n = lIllIlIlI[0];
        }
        return (boolean)n;
    }

    @Override
    protected ArrayList<String> getGameInfo(Player lIIlIIIIIIlIII) {
        InkWars lIIlIIIIIIlIIl;
        ArrayList<String> lIIlIIIIIIIlll = new ArrayList<String>();
        "".length();
        lIIlIIIIIIIlll.add(lIlIlIIlI[lIllIlIlI[1]]);
        "".length();
        lIIlIIIIIIIlll.add(lIlIlIIlI[lIllIlIlI[2]]);
        "".length();
        lIIlIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lIlIlIIlI[lIllIlIlI[3]]).append(lIIlIIIIIIlIIl.getBlockCountToLevelUp()).append(lIlIlIIlI[lIllIlIlI[4]])));
        "".length();
        lIIlIIIIIIIlll.add(lIlIlIIlI[lIllIlIlI[5]]);
        "".length();
        lIIlIIIIIIIlll.add(lIlIlIIlI[lIllIlIlI[6]]);
        return lIIlIIIIIIIlll;
    }

    private static int lIlIIlllIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    static {
        InkWars.lIlIIlIlIlI();
        InkWars.lIlIIlIlIIl();
    }

    @Override
    public InkWarsPlayerInfo getPlayerInfo(Player lIIIlIlllIllIl) {
        InkWars lIIIlIlllIllII;
        return lIIIlIlllIllII.getPlayerInfo(lIIIlIlllIllIl, InkWarsPlayerInfo.class);
    }

    private static boolean lIlIIlIlllI(int n) {
        return n <= 0;
    }

    int getBlockCountToLevelUp() {
        InkWars lIIIllIllIIIIl;
        int lIIIllIllIIIlI = lIllIlIlI[33];
        if (InkWars.lIlIIlIlIll((int)lIIIllIllIIIIl.pMapaActual().ExisteixPropietat(lIlIlIIlI[lIllIlIlI[34]]))) {
            lIIIllIllIIIlI = lIIIllIllIIIIl.pMapaActual().ObtenirPropietatInt(lIlIlIIlI[lIllIlIlI[35]]);
        }
        return lIIIllIllIIIlI;
    }

    private static boolean lIlIIlIlIll(int n) {
        return n != 0;
    }

    private static String lIIllIIlIll(String lIIIlIIllllllI, String lIIIlIIllllIll) {
        try {
            SecretKeySpec lIIIlIlIIIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIlIIllllIll.getBytes(StandardCharsets.UTF_8)), lIllIlIlI[9]), "DES");
            Cipher lIIIlIlIIIIIII = Cipher.getInstance("DES");
            lIIIlIlIIIIIII.init(lIllIlIlI[2], lIIIlIlIIIIIIl);
            return new String(lIIIlIlIIIIIII.doFinal(Base64.getDecoder().decode(lIIIlIIllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIlIIlllllll) {
            lIIIlIIlllllll.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean getResetPlayerOnRespawn() {
        return lIllIlIlI[0];
    }

    private static boolean lIlIIllllII(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lIIIlIllIllIlI, Player lIIIlIllIlIllI) {
        InkWars lIIIlIllIllIll;
        super.onPlayerDeath(lIIIlIllIllIlI, lIIIlIllIlIllI);
        ((InkWarsPlayerInfo)lIIIlIllIllIll.getPlayerInfo(lIIIlIllIlIllI)).registerDeath();
    }

    @Override
    public void ultraHeartbeat() {
        InkWars lIIIlllIIIIIIl;
        super.ultraHeartbeat();
        if (InkWars.lIlIIlIlIll((int)lIIIlllIIIIIIl.JocIniciat.booleanValue())) {
            lIIIlllIIIIIIl.processHighInkBlocks();
            lIIIlllIIIIIIl.tickPlayerWeapons();
        }
    }

    class EnderInkWeapon
    extends ProjectileInkWeapon {
        /* synthetic */ int toolTicks;
        /* synthetic */ Player targeted;
        private static final /* synthetic */ int[] lIlIlllI;
        /* synthetic */ int chargeTicks;
        private static final /* synthetic */ String[] lIIllIIl;

        private static boolean lIlIlIllll(int n, int n2) {
            return n <= n2;
        }

        private static boolean lIlIlIlIlI(int n) {
            return n == 0;
        }

        @Override
        public int neededReloadTicks() {
            return lIlIlllI[2];
        }

        @Override
        public ItemStack getToolMaterial() {
            ItemStack lIlIIIIIIIIllll = new ItemStack(Material.BLAZE_ROD, lIlIlllI[4]);
            lIlIIIIIIIIllll.addUnsafeEnchantment(Enchantment.KNOCKBACK, lIlIlllI[4]);
            return lIlIIIIIIIIllll;
        }

        private static void lIlIlIlIII() {
            lIlIlllI = new int[11];
            EnderInkWeapon.lIlIlllI[0] = (77 ^ 65) & ~(69 ^ 73);
            EnderInkWeapon.lIlIlllI[1] = "  ".length();
            EnderInkWeapon.lIlIlllI[2] = -(-26175 & 28479) & (-12489 & 15342);
            EnderInkWeapon.lIlIlllI[3] = 196 ^ 192;
            EnderInkWeapon.lIlIlllI[4] = " ".length();
            EnderInkWeapon.lIlIlllI[5] = 6 + 122 - 5 + 102 ^ 111 + 56 - 40 + 26;
            EnderInkWeapon.lIlIlllI[6] = 9 ^ 109;
            EnderInkWeapon.lIlIlllI[7] = -(-23689 & 31931) & (-4745 & 212986);
            EnderInkWeapon.lIlIlllI[8] = "   ".length();
            EnderInkWeapon.lIlIlllI[9] = 121 ^ 41;
            EnderInkWeapon.lIlIlllI[10] = 126 ^ 123;
        }

        @Override
        public double getWeaponSpeedModifier() {
            return 8.0;
        }

        private static String lIIlIIIllI(String lIIllllllIlIllI, String lIIllllllIlIlIl) {
            try {
                SecretKeySpec lIIllllllIllIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIllllllIlIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIllllllIllIlI = Cipher.getInstance("Blowfish");
                lIIllllllIllIlI.init(lIlIlllI[1], lIIllllllIllIll);
                return new String(lIIllllllIllIlI.doFinal(Base64.getDecoder().decode(lIIllllllIlIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIllllllIllIIl) {
                lIIllllllIllIIl.printStackTrace();
                return null;
            }
        }

        private static boolean lIlIlIlIll(int n) {
            return n != 0;
        }

        private static boolean lIlIlIllII(Object object) {
            return object != null;
        }

        private static String lIIlIIIlll(String lIIllllllIIIIll, String lIIllllllIIIlll) {
            lIIllllllIIIIll = new String(Base64.getDecoder().decode(lIIllllllIIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIllllllIIIllI = new StringBuilder();
            char[] lIIllllllIIIlIl = lIIllllllIIIlll.toCharArray();
            int lIIllllllIIIlII = lIlIlllI[0];
            char[] lIIlllllIlllllI = lIIllllllIIIIll.toCharArray();
            int lIIlllllIllllIl = lIIlllllIlllllI.length;
            int lIIlllllIllllII = lIlIlllI[0];
            while (EnderInkWeapon.lIlIllIIII(lIIlllllIllllII, lIIlllllIllllIl)) {
                char lIIllllllIIlIIl = lIIlllllIlllllI[lIIlllllIllllII];
                "".length();
                lIIllllllIIIllI.append((char)(lIIllllllIIlIIl ^ lIIllllllIIIlIl[lIIllllllIIIlII % lIIllllllIIIlIl.length]));
                ++lIIllllllIIIlII;
                ++lIIlllllIllllII;
                "".length();
                if ((155 ^ 158) != 0) continue;
                return null;
            }
            return String.valueOf(lIIllllllIIIllI);
        }

        private static boolean lIlIlIllIl(int n) {
            return n > 0;
        }

        private static boolean lIlIlIlllI(int n, int n2) {
            return n == n2;
        }

        static {
            EnderInkWeapon.lIlIlIlIII();
            EnderInkWeapon.lIIlIIlIII();
        }

        @Override
        public void tick() {
            EnderInkWeapon lIIllllllllIlll;
            super.tick();
            if (EnderInkWeapon.lIlIlIlIlI(lIIllllllllIlll.toolTicks)) {
                ItemStack[] arritemStack = new ItemStack[lIlIlllI[4]];
                arritemStack[EnderInkWeapon.lIlIlllI[0]] = lIIllllllllIlll.getToolMaterial();
                "".length();
                lIIllllllllIlll.getPlayer().getInventory().addItem(arritemStack);
                lIIllllllllIlll.toolTicks = lIlIlllI[7];
            }
            lIIllllllllIlll.toolTicks -= lIlIlllI[4];
            if (EnderInkWeapon.lIlIlIllIl(lIIllllllllIlll.chargeTicks) && EnderInkWeapon.lIlIlIllII((Object)lIIllllllllIlll.targeted)) {
                lIIllllllllIlll.rollerLinePaint(1.5 + (double)(lIIllllllllIlll.getWeaponLevel() / lIlIlllI[8]) + (double)(lIIllllllllIlll.chargeTicks / lIlIlllI[9]), 1.2, lIIllllllllIlll.targeted);
                if (EnderInkWeapon.lIlIlIlllI(lIIllllllllIlll.chargeTicks, lIlIlllI[1])) {
                    lIIllllllllIlll.paintRadius(lIIllllllllIlll.targeted.getEyeLocation(), Math.sqrt(4.5 + (double)lIIllllllllIlll.getWeaponLevel() * 0.25), Math.sqrt(4.5 + (double)lIIllllllllIlll.getWeaponLevel() * 0.3));
                    lIIllllllllIlll.getWorld().playEffect(lIIllllllllIlll.targeted.getEyeLocation(), Effect.INSTANT_SPELL, lIlIlllI[3]);
                    lIIllllllllIlll.getWorld().playEffect(lIIllllllllIlll.targeted.getEyeLocation(), Effect.CLOUD, lIlIlllI[3]);
                    lIIllllllllIlll.getWorld().playSound(lIIllllllllIlll.targeted.getEyeLocation(), Sound.ENTITY_FIREWORK_BLAST, 1.0f, 1.2f);
                }
                if (EnderInkWeapon.lIlIlIllll(lIIllllllllIlll.chargeTicks, lIlIlllI[4])) {
                    InkWeapon lIIlllllllllIII = ((InkWarsPlayerInfo)lIIllllllllIlll.InkWars.this.getPlayerInfo(lIIllllllllIlll.targeted)).getActiveWeapon();
                    if (EnderInkWeapon.lIlIlIllII((Object)lIIlllllllllIII) && EnderInkWeapon.lIlIlIlIll(lIIlllllllllIII instanceof RollerInkWeapon)) {
                        lIIlllllllllIII.setEnabled(lIlIlllI[4]);
                    }
                    lIIllllllllIlll.chargeTicks = lIlIlllI[0];
                    lIIllllllllIlll.targeted = null;
                }
                lIIllllllllIlll.chargeTicks -= lIlIlllI[4];
                lIIllllllllIlll.getWorld().playEffect(lIIllllllllIlll.targeted.getLocation().add(0.5, 0.12, 0.5), Effect.LAVA_POP, lIlIlllI[3]);
            }
        }

        protected void onPlayerDeathByPlayer(PlayerDeathEvent lIIlllllllIIIlI, Player lIIlllllllIIIIl, Player lIIlllllllIIlII) {
            EnderInkWeapon lIIlllllllIIlll;
            super.onPlayerDeathByPlayer(lIIlllllllIIIlI, lIIlllllllIIIIl, lIIlllllllIIlII);
            if (EnderInkWeapon.lIlIlIlIIl((Object)lIIlllllllIIlII, (Object)lIIlllllllIIlll.getPlayer())) {
                lIIlllllllIIlll.paintRadius(lIIlllllllIIIIl.getEyeLocation(), Math.sqrt(lIIlllllllIIlll.getWeaponLevel()) + 7.0, lIlIlllI[10] + lIIlllllllIIlll.getWeaponLevel() / lIlIlllI[8]);
            }
        }

        @Override
        public int getMaxLoad() {
            return lIlIlllI[1];
        }

        protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIlIIIIIIIIIlII, Player lIlIIIIIIIIIIll, Player lIIllllllllllIl, boolean lIIllllllllllII) {
            EnderInkWeapon lIlIIIIIIIIIIII;
            super.onPlayerDamageByPlayer(lIlIIIIIIIIIlII, lIlIIIIIIIIIIll, lIIllllllllllIl, lIIllllllllllII);
            if (EnderInkWeapon.lIlIlIlIIl((Object)lIIllllllllllIl, (Object)lIlIIIIIIIIIIII.getPlayer()) && EnderInkWeapon.lIlIlIlIlI((int)lIlIIIIIIIIIlII.isCancelled())) {
                if (EnderInkWeapon.lIlIlIlIll((int)lIIllllllllllII)) {
                    if (EnderInkWeapon.lIlIlIlIIl((Object)lIlIIIIIIIIIIll, (Object)lIlIIIIIIIIIIII.targeted)) {
                        GUtils.swapPositions((Entity)lIIllllllllllIl, (Entity)lIlIIIIIIIIIIll);
                        "".length();
                        if (((72 ^ 11) & ~(135 ^ 196)) != ((104 ^ 65) & ~(58 ^ 19))) {
                            return;
                        }
                    } else {
                        lIlIIIIIIIIIIII.targeted = lIlIIIIIIIIIIll;
                        lIlIIIIIIIIIIII.chargeTicks = lIlIlllI[5];
                        lIIllllllllllIl.playSound(lIlIIIIIIIIIIll.getEyeLocation(), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1.0f, 1.2f);
                        lIlIIIIIIIIIIll.playSound(lIlIIIIIIIIIIll.getEyeLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 1.2f);
                        lIlIIIIIIIIIIII.getWorld().playEffect(lIlIIIIIIIIIIII.targeted.getEyeLocation(), Effect.VILLAGER_THUNDERCLOUD, lIlIlllI[3]);
                        lIlIIIIIIIIIIII.getWorld().playEffect(lIlIIIIIIIIIIII.targeted.getEyeLocation(), Effect.VILLAGER_THUNDERCLOUD, lIlIlllI[3]);
                        lIlIIIIIIIIIIII.InkWars.this.sendPlayerMessage(lIlIIIIIIIIIIII.targeted, String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIlIIIIIIIIIIII.getPlayer().getName()).append(lIIllIIl[lIlIlllI[1]])));
                        InkWeapon lIlIIIIIIIIIllI = ((InkWarsPlayerInfo)lIlIIIIIIIIIIII.InkWars.this.getPlayerInfo(lIlIIIIIIIIIIII.targeted)).getActiveWeapon();
                        if (EnderInkWeapon.lIlIlIllII((Object)lIlIIIIIIIIIllI) && EnderInkWeapon.lIlIlIlIll(lIlIIIIIIIIIllI instanceof RollerInkWeapon)) {
                            lIlIIIIIIIIIllI.setEnabled(lIlIlllI[0]);
                        }
                    }
                    ((InkWarsPlayerInfo)lIlIIIIIIIIIIII.InkWars.this.getPlayerInfo(lIlIIIIIIIIIIll)).setShieldTicks(lIlIIIIIIIIIIII.chargeTicks);
                    lIlIIIIIIIIIIII.toolTicks = lIlIlllI[3];
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else if (EnderInkWeapon.lIlIlIlIIl((Object)lIlIIIIIIIIIIII.getPlayer().getItemInHand().getType(), (Object)Material.BLAZE_ROD)) {
                    lIlIIIIIIIIIIII.getPlayer().getInventory().remove(lIlIIIIIIIIIIII.getToolMaterial().getType());
                    lIlIIIIIIIIIlII.setDamage(4.0);
                    lIlIIIIIIIIIIII.toolTicks = lIlIlllI[6];
                    "".length();
                    if (" ".length() < (" ".length() & ~" ".length())) {
                        return;
                    }
                } else {
                    lIlIIIIIIIIIlII.setCancelled(lIlIlllI[4]);
                }
            }
        }

        public EnderInkWeapon(Player lIlIIIIIIlIIIIl) {
            EnderInkWeapon lIlIIIIIIlIIIll;
            super(lIlIIIIIIlIIIIl);
            lIlIIIIIIlIIIll.chargeTicks = lIlIlllI[0];
            lIlIIIIIIlIIIll.toolTicks = lIlIlllI[0];
            lIlIIIIIIlIIIIl.setMaxHealth(10.0);
        }

        @Override
        public ItemStack getLoadMaterial() {
            String[] arrstring = new String[lIlIlllI[4]];
            arrstring[EnderInkWeapon.lIlIlllI[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIllIIl[lIlIlllI[4]]));
            return Utils.setItemNameAndLore(new ItemStack(Material.ENDER_PEARL, lIlIlllI[4]), String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIIllIIl[lIlIlllI[0]])), arrstring);
        }

        private static boolean lIlIlIlIIl(Object object, Object object2) {
            return object == object2;
        }

        protected void onPlayerDeath(PlayerDeathEvent lIIllllllllIIII, Player lIIlllllllIllll) {
            EnderInkWeapon lIIlllllllIlllI;
            super.onPlayerDeath(lIIllllllllIIII, lIIlllllllIllll);
            if (EnderInkWeapon.lIlIlIlIIl((Object)lIIlllllllIllll, (Object)lIIlllllllIlllI.targeted)) {
                lIIlllllllIlllI.paintRadius(lIIlllllllIlllI.targeted.getEyeLocation(), (long)lIIlllllllIlllI.getWeaponLevel() + Math.round((double)lIIlllllllIlllI.chargeTicks / 30.0), 4.5 + (double)lIIlllllllIlllI.getWeaponLevel() * 0.25);
            }
        }

        @Override
        public void onWeaponHit(ProjectileHitEvent lIlIIIIIIIlIlll, Projectile lIlIIIIIIIlIllI, Block lIlIIIIIIIlIlIl, Block lIlIIIIIIIlIlII) {
            EnderInkWeapon lIlIIIIIIIllIII;
            lIlIIIIIIIllIII.paintRadius(lIlIIIIIIIlIlII.getLocation(), Math.sqrt((double)lIlIIIIIIIllIII.getWeaponLevel() / 2.0) + 1.75, lIlIlllI[3] + lIlIIIIIIIllIII.getWeaponLevel() * lIlIlllI[1] + lIlIlllI[4]);
            lIlIIIIIIIllIII.InkWars.this.world.playEffect(lIlIIIIIIIlIlII.getLocation(), Effect.COLOURED_DUST, lIlIlllI[0]);
            lIlIIIIIIIllIII.InkWars.this.world.playEffect(lIlIIIIIIIlIlII.getLocation(), Effect.COLOURED_DUST, lIlIlllI[0]);
            lIlIIIIIIIllIII.InkWars.this.world.playEffect(lIlIIIIIIIllIII.getPlayer().getEyeLocation(), Effect.COLOURED_DUST, lIlIlllI[0]);
            ((InkWarsPlayerInfo)lIlIIIIIIIllIII.InkWars.this.getPlayerInfo(lIlIIIIIIIllIII.getPlayer())).setShieldTicks(lIlIlllI[5]);
        }

        private static boolean lIlIllIIII(int n, int n2) {
            return n < n2;
        }

        private static void lIIlIIlIII() {
            lIIllIIl = new String[lIlIlllI[8]];
            EnderInkWeapon.lIIllIIl[EnderInkWeapon.lIlIlllI[0]] = EnderInkWeapon.lIIlIIIllI("BtGUWLsxaYExyZ3nSpnNdg==", "mRnTA");
            EnderInkWeapon.lIIllIIl[EnderInkWeapon.lIlIlllI[4]] = EnderInkWeapon.lIIlIIIlll("Hg4pDxxzCiwPAjoKMUofMgYsHk81ADBKFjwaYgMJcwY2SgY+HyMJGyBPLQRPJwcnAx1zCSMJCg==", "SoBjo");
            EnderInkWeapon.lIIllIIl[EnderInkWeapon.lIlIlllI[1]] = EnderInkWeapon.lIIlIIIlll("dCcNO1YgPQUrHTErTDEZIW8YJ1YkLgUmAnQpAzpWPCYBaF5iPEVp", "TOlHv");
        }
    }

    abstract class ProjectileInkWeapon
    extends InkWeapon {
        private static final /* synthetic */ int[] lIlll;
        /* synthetic */ ArrayList<Projectile> onHoldProjectileList;

        protected boolean getPlayerSpecificEventFiltering() {
            return lIlll[0];
        }

        private static void llllII() {
            lIlll = new int[2];
            ProjectileInkWeapon.lIlll[0] = (123 + 53 - 39 + 23 ^ 98 + 99 - 128 + 89) & (50 + 6 - -45 + 42 ^ 19 + 132 - 99 + 125 ^ -" ".length());
            ProjectileInkWeapon.lIlll[1] = 9 + 67 - -83 + 12 ^ 120 + 30 - 122 + 147;
        }

        private static boolean lIIIIII(Object object, Object object2) {
            return object == object2;
        }

        protected void onProjectileHit(ProjectileHitEvent lllllIIIlIlIlll, Projectile lllllIIIlIlIllI) {
            ProjectileInkWeapon lllllIIIlIlIlIl;
            super.onProjectileHit(lllllIIIlIlIlll, lllllIIIlIlIllI);
            if (ProjectileInkWeapon.llllll((int)lllllIIIlIlIlIl.onHoldProjectileList.contains((Object)lllllIIIlIlIllI))) {
                Location lllllIIIlIlllII = lllllIIIlIlIllI.getLocation();
                BlockIterator lllllIIIlIllIll = new BlockIterator(lllllIIIlIlIlIl.getWorld(), lllllIIIlIlllII.toVector(), lllllIIIlIlIllI.getVelocity().normalize(), 0.0, lIlll[1]);
                Block lllllIIIlIllIlI = null;
                Block lllllIIIlIllIIl = null;
                while (ProjectileInkWeapon.llllll((int)lllllIIIlIllIll.hasNext())) {
                    lllllIIIlIllIIl = lllllIIIlIllIlI;
                    lllllIIIlIllIlI = lllllIIIlIllIll.next();
                    if (!ProjectileInkWeapon.llllll(lllllIIIlIllIlI.getTypeId())) continue;
                    "".length();
                    if ("  ".length() != 0) break;
                    return;
                }
                if (ProjectileInkWeapon.lIIIIIl(lllllIIIlIllIlI) && ProjectileInkWeapon.lIIIIIl((Object)lllllIIIlIllIIl)) {
                    lllllIIIlIlIlIl.onWeaponHit(lllllIIIlIlIlll, lllllIIIlIlIllI, lllllIIIlIllIlI, lllllIIIlIllIIl);
                }
            }
        }

        protected void onProjectileLaunch(ProjectileLaunchEvent lllllIIIllIllIl, Projectile lllllIIIllIllII) {
            Player lllllIIIlllIIll;
            ProjectileInkWeapon lllllIIIlllIIlI;
            super.onProjectileLaunch(lllllIIIllIllIl, lllllIIIllIllII);
            ProjectileSource lllllIIIllIllll = lllllIIIllIllII.getShooter();
            if (ProjectileInkWeapon.llllll(lllllIIIllIllll instanceof Player) && ProjectileInkWeapon.lIIIIII((Object)(lllllIIIlllIIll = (Player)lllllIIIllIllll), (Object)lllllIIIlllIIlI.getPlayer())) {
                lllllIIIlllIIlI.registerProjectile(lllllIIIllIllII);
            }
        }

        private static boolean llllll(int n) {
            return n != 0;
        }

        public void registerProjectile(Projectile lllllIIIllIIllI) {
            ProjectileInkWeapon lllllIIIllIIlll;
            "".length();
            lllllIIIllIIlll.onHoldProjectileList.add(lllllIIIllIIllI);
        }

        public ProjectileInkWeapon(Player lllllIIIllllIlI) {
            ProjectileInkWeapon lllllIIIlllllII;
            super(lllllIIIllllIlI);
            lllllIIIlllllII.onHoldProjectileList = new ArrayList();
        }

        private static boolean lIIIIIl(Object object) {
            return object != null;
        }

        static {
            ProjectileInkWeapon.llllII();
        }

        public abstract void onWeaponHit(ProjectileHitEvent var1, Projectile var2, Block var3, Block var4);
    }

    class BrushInkWeapon
    extends InkWeapon {
        /* synthetic */ Player targeted;
        /* synthetic */ int charges;
        private static final /* synthetic */ int[] llllIIII;
        private static final /* synthetic */ String[] lllIlllI;

        @Override
        public double getWeaponSpeedModifier() {
            return 10.0;
        }

        private static void lllIIIlIIl() {
            llllIIII = new int[5];
            BrushInkWeapon.llllIIII[0] = (56 ^ 117) & ~(194 ^ 143);
            BrushInkWeapon.llllIIII[1] = " ".length();
            BrushInkWeapon.llllIIII[2] = "   ".length();
            BrushInkWeapon.llllIIII[3] = "  ".length();
            BrushInkWeapon.llllIIII[4] = 42 ^ 34;
        }

        private static String lllIIIIIIl(String lIIIIIllIIIlIII, String lIIIIIllIIIIlll) {
            try {
                SecretKeySpec lIIIIIllIIIllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIIllIIIIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIIIllIIIllII = Cipher.getInstance("Blowfish");
                lIIIIIllIIIllII.init(llllIIII[3], lIIIIIllIIIllIl);
                return new String(lIIIIIllIIIllII.doFinal(Base64.getDecoder().decode(lIIIIIllIIIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIIIllIIIlIll) {
                lIIIIIllIIIlIll.printStackTrace();
                return null;
            }
        }

        private static void lllIIIIIll() {
            lllIlllI = new String[llllIIII[3]];
            BrushInkWeapon.lllIlllI[BrushInkWeapon.llllIIII[0]] = BrushInkWeapon.lllIIIIIII("Yb5YoAkMaR8=", "kQSxm");
            BrushInkWeapon.lllIlllI[BrushInkWeapon.llllIIII[1]] = BrushInkWeapon.lllIIIIIIl("auHcfrufzdAsEHDo0o8IASSlEu5snVEC9H60hebwKvzyFsWY6fZPAXJof/cIOYXqdJ+6qe8SiNRSZ+3nuNevh4C9GtkKXmv6T8Donw4OpaI=", "PUOIR");
        }

        private static boolean lllIIIllll(Object object, Object object2) {
            return object != object2;
        }

        public BrushInkWeapon(Player lIIIIIlllIIlIlI) {
            BrushInkWeapon lIIIIIlllIIllII;
            super(lIIIIIlllIIlIlI);
            lIIIIIlllIIllII.charges = llllIIII[0];
            lIIIIIlllIIlIlI.setMaxHealth(18.0);
        }

        private static boolean lllIIIlllI(Object object) {
            return object == null;
        }

        private static boolean lllIIIlIlI(Object object, Object object2) {
            return object == object2;
        }

        public double getWidth() {
            BrushInkWeapon lIIIIIllIlllIll;
            return 0.1 + Math.sqrt(lIIIIIllIlllIll.getWeaponLevel()) / 4.0;
        }

        private static boolean lllIIIllII(int n) {
            return n != 0;
        }

        static {
            BrushInkWeapon.lllIIIlIIl();
            BrushInkWeapon.lllIIIIIll();
        }

        private static String lllIIIIIII(String lIIIIIllIIlIlIl, String lIIIIIllIIlIllI) {
            try {
                SecretKeySpec lIIIIIllIIllIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIIIllIIlIllI.getBytes(StandardCharsets.UTF_8)), llllIIII[4]), "DES");
                Cipher lIIIIIllIIllIIl = Cipher.getInstance("DES");
                lIIIIIllIIllIIl.init(llllIIII[3], lIIIIIllIIllIlI);
                return new String(lIIIIIllIIllIIl.doFinal(Base64.getDecoder().decode(lIIIIIllIIlIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIIIllIIllIII) {
                lIIIIIllIIllIII.printStackTrace();
                return null;
            }
        }

        private static boolean lllIIlIIII(int n, int n2) {
            return n > n2;
        }

        private static boolean lllIIIllIl(int n) {
            return n == 0;
        }

        protected void onPlayerMove(PlayerMoveEvent lIIIIIlllIIIIlI, Player lIIIIIllIlllllI) {
            BrushInkWeapon lIIIIIlllIIIIll;
            super.onPlayerMove(lIIIIIlllIIIIlI, lIIIIIllIlllllI);
            if (BrushInkWeapon.lllIIIlIlI((Object)lIIIIIllIlllllI, (Object)lIIIIIlllIIIIll.getPlayer())) {
                lIIIIIlllIIIIll.rollerLinePaint(lIIIIIlllIIIIll.getWidth(), 3.2 + (double)lIIIIIlllIIIIll.getWeaponLevel() / 1.8, lIIIIIlllIIIIll.getPlayer());
            }
        }

        private static boolean lllIIIlIll(Object object) {
            return object != null;
        }

        @Override
        public ItemStack getToolMaterial() {
            BrushInkWeapon lIIIIIllIllIlll;
            String[] arrstring = new String[llllIIII[1]];
            arrstring[BrushInkWeapon.llllIIII[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lllIlllI[llllIIII[1]]));
            return Utils.setItemNameAndLore(new ItemStack(Material.TORCH, llllIIII[1]), String.valueOf(new StringBuilder().append((Object)lIIIIIllIllIlll.InkWars.this.obtenirEquip(lIIIIIllIllIlll.getPlayer()).getChatColor()).append(lllIlllI[llllIIII[0]])), arrstring);
        }

        @Override
        public ItemStack getLoadMaterial() {
            return null;
        }

        protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIIIIIllIlIIlll, Player lIIIIIllIlIIIIl, Player lIIIIIllIlIIIII, boolean lIIIIIllIIlllll) {
            BrushInkWeapon lIIIIIllIlIIIll;
            super.onPlayerDamageByPlayer(lIIIIIllIlIIlll, lIIIIIllIlIIIIl, lIIIIIllIlIIIII, lIIIIIllIIlllll);
            if (BrushInkWeapon.lllIIIllIl((int)lIIIIIllIIlllll) && BrushInkWeapon.lllIIIlIlI((Object)lIIIIIllIlIIIII, (Object)lIIIIIllIlIIIll.getPlayer()) && BrushInkWeapon.lllIIIllIl((int)lIIIIIllIlIIIIl.hasPotionEffect(PotionEffectType.WITHER)) && BrushInkWeapon.lllIIIllIl((int)lIIIIIllIlIIlll.isCancelled())) {
                if (BrushInkWeapon.lllIIIlllI((Object)lIIIIIllIlIIIll.targeted)) {
                    lIIIIIllIlIIIll.targeted = lIIIIIllIlIIIIl;
                }
                if (BrushInkWeapon.lllIIIllll((Object)lIIIIIllIlIIIll.targeted, (Object)lIIIIIllIlIIIIl)) {
                    lIIIIIllIlIIIll.targeted = lIIIIIllIlIIIIl;
                    lIIIIIllIlIIIll.charges = llllIIII[0];
                }
                lIIIIIllIlIIlll.setDamage(0.25 + 0.1 * (double)lIIIIIllIlIIIll.getWeaponLevel());
                lIIIIIllIlIIIll.getWorld().playSound(lIIIIIllIlIIIIl.getEyeLocation(), Sound.ENTITY_SLIME_ATTACK, 1.0f, 1.1f + 0.4f * (float)lIIIIIllIlIIIll.charges);
                lIIIIIllIlIIIll.getWorld().playSound(lIIIIIllIlIIIIl.getEyeLocation(), Sound.ENTITY_SLIME_JUMP, 1.0f, 1.1f + 0.4f * (float)lIIIIIllIlIIIll.charges);
                lIIIIIllIlIIIll.paintRadius(lIIIIIllIlIIIIl.getEyeLocation(), (0.25 + 0.1 * (double)lIIIIIllIlIIIll.getWeaponLevel()) * (double)lIIIIIllIlIIIll.charges, 2.0);
                lIIIIIllIlIIIll.charges += llllIIII[1];
                if (BrushInkWeapon.lllIIlIIII(lIIIIIllIlIIIll.charges, llllIIII[2])) {
                    lIIIIIllIlIIIll.charges = llllIIII[0];
                    "".length();
                    lIIIIIllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int)Math.round(20.0 * (5.0 + 0.6 * (double)lIIIIIllIlIIIll.getWeaponLevel())), Math.round(lIIIIIllIlIIIll.getWeaponLevel() + llllIIII[0])), llllIIII[1]);
                    lIIIIIllIlIIIll.getWorld().playSound(lIIIIIllIlIIIIl.getEyeLocation(), Sound.ENTITY_GENERIC_SWIM, 1.0f, 1.25f);
                    "".length();
                    if (((182 ^ 131) & ~(121 ^ 76)) != 0) {
                        return;
                    }
                }
            } else {
                lIIIIIllIlIIlll.setCancelled(llllIIII[1]);
            }
        }

        @Override
        public void tick() {
            BrushInkWeapon lIIIIIllIllIIIl;
            super.tick();
            if (BrushInkWeapon.lllIIIlIll((Object)lIIIIIllIllIIIl.targeted) && BrushInkWeapon.lllIIIllII((int)lIIIIIllIllIIIl.targeted.hasPotionEffect(PotionEffectType.WITHER))) {
                Iterator<Player> lIIIIIllIlIllll = Utils.getNearbyPlayers((Entity)lIIIIIllIllIIIl.targeted, 1.5).iterator();
                while (BrushInkWeapon.lllIIIllII((int)lIIIIIllIlIllll.hasNext())) {
                    Player lIIIIIllIllIIlI = lIIIIIllIlIllll.next();
                    if (BrushInkWeapon.lllIIIllII((int)lIIIIIllIllIIIl.InkWars.this.areAllies(lIIIIIllIllIIIl.targeted, lIIIIIllIllIIlI).booleanValue())) {
                        lIIIIIllIllIIlI.damage(0.1, (Entity)lIIIIIllIllIIIl.getPlayer());
                    }
                    "".length();
                    if (((21 ^ 3 ^ (142 ^ 156)) & (106 + 53 - 158 + 146 ^ 130 + 29 - 120 + 112 ^ -" ".length())) == 0) continue;
                    return;
                }
            }
        }
    }

    class RollerInkWeapon
    extends InkWeapon {
        private static final /* synthetic */ String[] lllIIIl;
        private static final /* synthetic */ int[] llllIlI;

        private static String llIIIIIII(String llIIIIlllIlIlll, String llIIIIlllIlIllI) {
            try {
                SecretKeySpec llIIIIlllIllIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIIlllIlIllI.getBytes(StandardCharsets.UTF_8)), llllIlI[3]), "DES");
                Cipher llIIIIlllIllIIl = Cipher.getInstance("DES");
                llIIIIlllIllIIl.init(llllIlI[2], llIIIIlllIllIlI);
                return new String(llIIIIlllIllIIl.doFinal(Base64.getDecoder().decode(llIIIIlllIlIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIIIlllIllIII) {
                llIIIIlllIllIII.printStackTrace();
                return null;
            }
        }

        @Override
        public ItemStack getLoadMaterial() {
            return null;
        }

        private static boolean llIlIIIll(Object object, Object object2) {
            return object == object2;
        }

        public double getWidth() {
            RollerInkWeapon llIIIIlllllIIll;
            return 1.0 + Math.sqrt(llIIIIlllllIIll.getWeaponLevel());
        }

        private static void llIlIIIIl() {
            llllIlI = new int[4];
            RollerInkWeapon.llllIlI[0] = " ".length();
            RollerInkWeapon.llllIlI[1] = (67 ^ 2 ^ (83 ^ 53)) & (165 + 172 - 198 + 42 ^ 60 + 72 - 50 + 64 ^ -" ".length());
            RollerInkWeapon.llllIlI[2] = "  ".length();
            RollerInkWeapon.llllIlI[3] = 202 ^ 194;
        }

        protected void onPlayerMove(PlayerMoveEvent llIIIIllllllIIl, Player llIIIIlllllIlIl) {
            RollerInkWeapon llIIIIlllllIlll;
            super.onPlayerMove(llIIIIllllllIIl, llIIIIlllllIlIl);
            if (RollerInkWeapon.llIlIIIll((Object)llIIIIlllllIlIl, (Object)llIIIIlllllIlll.getPlayer())) {
                llIIIIlllllIlll.rollerLinePaint(llIIIIlllllIlll.getWidth(), 1.2 + (double)llIIIIlllllIlll.getWeaponLevel() / 8.0, llIIIIlllllIlll.getPlayer());
            }
        }

        @Override
        public ItemStack getToolMaterial() {
            RollerInkWeapon llIIIIllllIllll;
            String[] arrstring = new String[llllIlI[0]];
            arrstring[RollerInkWeapon.llllIlI[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lllIIIl[llllIlI[0]]));
            return Utils.setItemNameAndLore(new ItemStack(Material.STICK, llllIlI[0]), String.valueOf(new StringBuilder().append((Object)llIIIIllllIllll.InkWars.this.obtenirEquip(llIIIIllllIllll.getPlayer()).getChatColor()).append(lllIIIl[llllIlI[1]])), arrstring);
        }

        private static String lIlllllll(String llIIIIlllIIlIII, String llIIIIlllIIIlll) {
            try {
                SecretKeySpec llIIIIlllIIllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIIlllIIIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llIIIIlllIIllII = Cipher.getInstance("Blowfish");
                llIIIIlllIIllII.init(llllIlI[2], llIIIIlllIIllIl);
                return new String(llIIIIlllIIllII.doFinal(Base64.getDecoder().decode(llIIIIlllIIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIIIlllIIlIll) {
                llIIIIlllIIlIll.printStackTrace();
                return null;
            }
        }

        public RollerInkWeapon(Player llIIIlIIIIIIIIl) {
            RollerInkWeapon llIIIlIIIIIIIII;
            super(llIIIlIIIIIIIIl);
            llIIIlIIIIIIIIl.setMaxHealth(18.0);
        }

        private static void llIIIIIIl() {
            lllIIIl = new String[llllIlI[2]];
            RollerInkWeapon.lllIIIl[RollerInkWeapon.llllIlI[1]] = RollerInkWeapon.lIlllllll("4wul5HSzKSU=", "sOsCS");
            RollerInkWeapon.lllIIIl[RollerInkWeapon.llllIlI[0]] = RollerInkWeapon.llIIIIIII("Wo7eLYUGa/8Lo9Q4ZYx7+k9NN88O5wP9Up1+++3ThwaDLXMrTniB7/RQVqT9+/7v9ODggoD87XeqcvvOcHgFg6xVTCZJLMcukKs89Imy1H8=", "RxcxF");
        }

        protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent llIIIIllllIIIlI, Player llIIIIllllIIIIl, Player llIIIIllllIIlIl, boolean llIIIIllllIIlII) {
            RollerInkWeapon llIIIIllllIIIll;
            super.onPlayerDamageByPlayer(llIIIIllllIIIlI, llIIIIllllIIIIl, llIIIIllllIIlIl, llIIIIllllIIlII);
            if (RollerInkWeapon.llIlIIlII((int)llIIIIllllIIlII) && RollerInkWeapon.llIlIIIll((Object)llIIIIllllIIlIl, (Object)llIIIIllllIIIll.getPlayer())) {
                llIIIIllllIIIlI.setCancelled(llllIlI[0]);
            }
        }

        private static boolean llIlIIlII(int n) {
            return n == 0;
        }

        static {
            RollerInkWeapon.llIlIIIIl();
            RollerInkWeapon.llIIIIIIl();
        }
    }

    public class InkWarsPlayerInfo
    extends Joc.PlayerInfo {
        private /* synthetic */ int paintedBlockCount;
        private /* synthetic */ int shieldTicks;
        private /* synthetic */ InkWeapon activeWeapon;
        private static final /* synthetic */ String[] lllIIIIl;
        private static final /* synthetic */ int[] lllIIIlI;
        private /* synthetic */ int weaponAlerts;
        private /* synthetic */ int dmgTicks;
        private /* synthetic */ int alivePaintedBlocks;
        private /* synthetic */ int weaponLevel;

        public int getWeaponLevel() {
            InkWarsPlayerInfo lIIIlIIIIlllIII;
            return lIIIlIIIIlllIII.weaponLevel;
        }

        public void registerDeath() {
            InkWarsPlayerInfo lIIIlIIIlIIIlll;
            lIIIlIIIlIIIlll.alivePaintedBlocks = (int)((double)lIIIlIIIlIIIlll.alivePaintedBlocks * 0.8);
            lIIIlIIIlIIIlll.getPlayer().setLevel(lIIIlIIIlIIIlll.alivePaintedBlocks);
            lIIIlIIIlIIIlll.InkWars.this.sendPlayerMessage(lIIIlIIIlIIIlll.getPlayer(), String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lllIIIIl[lllIIIlI[0]])));
        }

        public InkWarsPlayerInfo() {
            InkWarsPlayerInfo lIIIlIIlIIIIIll;
            lIIIlIIlIIIIIll.paintedBlockCount = lllIIIlI[0];
            lIIIlIIlIIIIIll.alivePaintedBlocks = lllIIIlI[0];
            lIIIlIIlIIIIIll.activeWeapon = null;
            lIIIlIIlIIIIIll.weaponLevel = lllIIIlI[1];
            lIIIlIIlIIIIIll.weaponAlerts = lllIIIlI[0];
            lIIIlIIlIIIIIll.dmgTicks = lllIIIlI[0];
            lIIIlIIlIIIIIll.shieldTicks = lllIIIlI[0];
        }

        public int getPaintedBlockCount() {
            InkWarsPlayerInfo lIIIlIIIlllllll;
            return lIIIlIIIlllllll.paintedBlockCount;
        }

        @Override
        public void ultraTick() {
            InkWarsPlayerInfo lIIIlIIIlIlllIl;
            EquipInkWars lIIIlIIIlIlllII = lIIIlIIIlIlllIl.InkWars.this.obtenirEquip(lIIIlIIIlIlllIl.getPlayer());
            Block lIIIlIIIlIllIll = lIIIlIIIlIlllIl.getBlockWherePlayerStands();
            EquipInkWars lIIIlIIIlIllIlI = lIIIlIIIlIlllIl.getTeamColorWherePlayerStands();
            if (InkWarsPlayerInfo.llIllIIIII(lIIIlIIIlIllIlI)) {
                lIIIlIIIlIlllIl.setSpeedModifier(0.0);
            }
            if (InkWarsPlayerInfo.llIllIIIIl((Object)lIIIlIIIlIllIll) && InkWarsPlayerInfo.llIllIIIIl(lIIIlIIIlIllIlI)) {
                int n;
                int n2;
                if (!InkWarsPlayerInfo.llIllIIIlI(lIIIlIIIlIllIlI, lIIIlIIIlIlllII) || InkWarsPlayerInfo.llIllIIIll((int)lIIIlIIIlIlllIl.isShileded())) {
                    n2 = lllIIIlI[1];
                    "".length();
                    if ((187 ^ 191) < "   ".length()) {
                        return;
                    }
                } else {
                    n2 = lllIIIlI[0];
                }
                int lIIIlIIIllIIIIl = n2;
                int lIIIlIIIllIIIII = lIIIlIIIlIlllIl.InkWars.this.highInkBlocks.containsKey((Object)lIIIlIIIlIllIll);
                double lIIIlIIIlIlllll = 0.0;
                if (InkWarsPlayerInfo.llIllIIIll(lIIIlIIIllIIIIl)) {
                    lIIIlIIIlIlllll += 5.0;
                    "".length();
                    if (-"  ".length() >= 0) {
                        return;
                    }
                } else {
                    lIIIlIIIlIlllll += -5.0;
                }
                if (InkWarsPlayerInfo.llIllIIIll(lIIIlIIIllIIIII)) {
                    lIIIlIIIlIlllll *= 2.0;
                }
                if (InkWarsPlayerInfo.llIllIIIIl((Object)lIIIlIIIlIlllIl.getActiveWeapon())) {
                    lIIIlIIIlIlllll += lIIIlIIIlIlllIl.getActiveWeapon().getWeaponSpeedModifier();
                }
                if (InkWarsPlayerInfo.llIllIIIll(lIIIlIIIllIIIIl)) {
                    n = lllIIIlI[2];
                    "".length();
                    if (-"  ".length() >= 0) {
                        return;
                    }
                } else {
                    n = lllIIIlI[3];
                }
                double lIIIlIIIlIllllI = n;
                lIIIlIIIlIlllIl.setSpeedModifier(lIIIlIIIlIlllll / 2.0);
                lIIIlIIIlIlllIl.dmgTicks = (int)((double)lIIIlIIIlIlllIl.dmgTicks + lIIIlIIIlIllllI);
                if (InkWarsPlayerInfo.llIllIIlII(lIIIlIIIlIlllIl.dmgTicks)) {
                    lIIIlIIIlIlllIl.dmgTicks = lllIIIlI[0];
                }
                if (InkWarsPlayerInfo.llIllIIlIl(lIIIlIIIlIlllIl.dmgTicks, lllIIIlI[3])) {
                    lIIIlIIIlIlllIl.getPlayer().damage(1.0);
                    lIIIlIIIlIlllIl.dmgTicks = lllIIIlI[0];
                }
            }
            lIIIlIIIlIlllIl.shieldTicks -= lllIIIlI[1];
            super.tick();
        }

        private static boolean llIlIlllll(int n) {
            return n > 0;
        }

        public void registerWeaponAlert() {
            lIIIlIIIllllIlI.weaponAlerts += lllIIIlI[1];
        }

        public InkWeapon getActiveWeapon() {
            InkWarsPlayerInfo lIIIlIIIlIIIIll;
            return lIIIlIIIlIIIIll.activeWeapon;
        }

        private static boolean llIllIIIII(Object object) {
            return object == null;
        }

        private static void llIlIlllII() {
            lllIIIIl = new String[lllIIIlI[1]];
            InkWarsPlayerInfo.lllIIIIl[InkWarsPlayerInfo.lllIIIlI[0]] = InkWarsPlayerInfo.llIlIllIll("Iy0NUx4bNB1TGhUxDFNESmdYHBBaOxcGBFoyFxoYDjE=", "zBxsv");
        }

        public void setShieldTicks(int lIIIlIIIllIllII) {
            lIIIlIIIllIllIl.shieldTicks = lIIIlIIIllIllII;
        }

        public EquipInkWars getTeamColorWherePlayerStands() {
            InkWarsPlayerInfo lIIIlIIIIlIlIll;
            return lIIIlIIIIlIlIll.InkWars.this.getTeamOwningBlock(lIIIlIIIIlIlIll.getBlockWherePlayerStands());
        }

        public void registerBlockPaint(EquipInkWars lIIIlIIIlIIllIl) {
            InkWarsPlayerInfo lIIIlIIIlIIlllI;
            lIIIlIIIlIIlllI.paintedBlockCount += lllIIIlI[1];
            lIIIlIIIlIIlllI.alivePaintedBlocks += lllIIIlI[1];
            lIIIlIIIlIIlllI.getPlayer().setLevel(lIIIlIIIlIIlllI.alivePaintedBlocks);
            EquipInkWars lIIIlIIIlIIllII = lIIIlIIIlIIlllI.InkWars.this.obtenirEquip(lIIIlIIIlIIlllI.getPlayer());
            if (InkWarsPlayerInfo.llIllIIIIl(lIIIlIIIlIIllIl)) {
                lIIIlIIIlIIllIl.incrementOwnedBlocks(lllIIIlI[4]);
            }
            lIIIlIIIlIIllII.incrementOwnedBlocks(lllIIIlI[1]);
        }

        public int getWeaponAlerts() {
            InkWarsPlayerInfo lIIIlIIIlllIlll;
            return lIIIlIIIlllIlll.weaponAlerts;
        }

        public int getAlivePaintedBlocks() {
            InkWarsPlayerInfo lIIIlIIIlllllII;
            return lIIIlIIIlllllII.alivePaintedBlocks;
        }

        private static boolean llIllIIlll(int n, int n2) {
            return n < n2;
        }

        private static boolean llIllIIIll(int n) {
            return n != 0;
        }

        public void weaponLevelUp() {
            InkWarsPlayerInfo lIIIlIIIIlIllll;
            lIIIlIIIIlIllll.setWeaponLevel(lIIIlIIIIlIllll.getWeaponLevel() + lllIIIlI[1]);
        }

        private static boolean llIllIIlII(int n) {
            return n < 0;
        }

        private static String llIlIllIll(String lIIIlIIIIIllIll, String lIIIlIIIIIllIlI) {
            lIIIlIIIIIllIll = new String(Base64.getDecoder().decode(lIIIlIIIIIllIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIIlIIIIIllllI = new StringBuilder();
            char[] lIIIlIIIIIlllIl = lIIIlIIIIIllIlI.toCharArray();
            int lIIIlIIIIIlllII = lllIIIlI[0];
            char[] lIIIlIIIIIlIllI = lIIIlIIIIIllIll.toCharArray();
            int lIIIlIIIIIlIlIl = lIIIlIIIIIlIllI.length;
            int lIIIlIIIIIlIlII = lllIIIlI[0];
            while (InkWarsPlayerInfo.llIllIIlll(lIIIlIIIIIlIlII, lIIIlIIIIIlIlIl)) {
                char lIIIlIIIIlIIIIl = lIIIlIIIIIlIllI[lIIIlIIIIIlIlII];
                "".length();
                lIIIlIIIIIllllI.append((char)(lIIIlIIIIlIIIIl ^ lIIIlIIIIIlllIl[lIIIlIIIIIlllII % lIIIlIIIIIlllIl.length]));
                ++lIIIlIIIIIlllII;
                ++lIIIlIIIIIlIlII;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.valueOf(lIIIlIIIIIllllI);
        }

        private static boolean llIllIIlIl(int n, int n2) {
            return n >= n2;
        }

        private static boolean llIllIIIlI(Object object, Object object2) {
            return object != object2;
        }

        public void setActiveWeapon(InkWeapon lIIIlIIIIlllllI) {
            InkWarsPlayerInfo lIIIlIIIIllllll;
            InkWeapon lIIIlIIIIllllIl = lIIIlIIIIllllll.activeWeapon;
            if (InkWarsPlayerInfo.llIllIIIIl((Object)lIIIlIIIIllllIl)) {
                lIIIlIIIIllllIl.destroy();
            }
            lIIIlIIIIllllll.InkWars.this.donarItemsInicials(lIIIlIIIIllllll.getPlayer());
            lIIIlIIIIllllll.activeWeapon = lIIIlIIIIlllllI;
            lIIIlIIIIllllll.activeWeapon.giveTool();
        }

        private static boolean llIllIIIIl(Object object) {
            return object != null;
        }

        public int getShieldTicks() {
            InkWarsPlayerInfo lIIIlIIIlllIIIl;
            return lIIIlIIIlllIIIl.shieldTicks;
        }

        public void setWeaponLevel(int lIIIlIIIIllIIIl) {
            lIIIlIIIIllIIlI.weaponLevel = lIIIlIIIIllIIIl;
        }

        static {
            InkWarsPlayerInfo.llIlIlllIl();
            InkWarsPlayerInfo.llIlIlllII();
        }

        public boolean isShileded() {
            boolean bl;
            InkWarsPlayerInfo lIIIlIIIlllIIll;
            if (InkWarsPlayerInfo.llIlIlllll(lIIIlIIIlllIIll.getShieldTicks())) {
                bl = lllIIIlI[1];
                "".length();
                if ((20 ^ 16) < 0) {
                    return (boolean)((70 ^ 103) & ~(108 ^ 77));
                }
            } else {
                bl = lllIIIlI[0];
            }
            return bl;
        }

        private static void llIlIlllIl() {
            lllIIIlI = new int[5];
            InkWarsPlayerInfo.lllIIIlI[0] = "   ".length() & ("   ".length() ^ -" ".length());
            InkWarsPlayerInfo.lllIIIlI[1] = " ".length();
            InkWarsPlayerInfo.lllIIIlI[2] = -(0 + 86 - -12 + 76 ^ 45 + 157 - 47 + 11);
            InkWarsPlayerInfo.lllIIIlI[3] = 188 ^ 168;
            InkWarsPlayerInfo.lllIIIlI[4] = -" ".length();
        }
    }

    abstract class InkWeapon
    extends PlayerWorldEventBus {
        private static final /* synthetic */ int[] lllllll;
        private /* synthetic */ boolean validWeapon;
        private /* synthetic */ boolean enabled;
        private /* synthetic */ int reloadTicks;

        public int getMaxLoad() {
            return lllllll[3];
        }

        public void setReloadTicks(int lIlllIlIlIIIlll) {
            lIlllIlIlIIlIlI.reloadTicks = lIlllIlIlIIIlll;
        }

        public double getWeaponSpeedModifier() {
            return 0.0;
        }

        static {
            InkWeapon.llIlIllIl();
        }

        public int reloadTickIncrement() {
            int lIlllIlIlllIlII;
            InkWeapon lIlllIlIllllIII;
            int n;
            int n2;
            int lIlllIlIlllIIll;
            int lIlllIlIlllIlll = lllllll[1];
            EquipInkWars lIlllIlIlllIllI = lIlllIlIllllIII.InkWars.this.obtenirEquip(lIlllIlIllllIII.getPlayer());
            EquipInkWars lIlllIlIlllIlIl = ((InkWarsPlayerInfo)lIlllIlIllllIII.InkWars.this.getPlayerInfo(lIlllIlIllllIII.getPlayer())).getTeamColorWherePlayerStands();
            if (InkWeapon.lllIIlllI(lIlllIlIlllIlIl, lIlllIlIlllIllI)) {
                n2 = lllllll[1];
                "".length();
                if ("   ".length() == 0) {
                    return (77 ^ 96 ^ (137 ^ 162)) & (170 ^ 160 ^ (129 ^ 141) ^ -" ".length());
                }
            } else {
                n2 = lIlllIlIlllIlII = lllllll[0];
            }
            if (InkWeapon.lllIIllll(InkWeapon.lllIIllII(lIlllIlIllllIII.getPlayer().getLocation().distance(lIlllIlIlllIllI.getTeamSpawnLocation()), 10.0))) {
                n = lllllll[1];
                "".length();
                if ((131 + 75 - 78 + 37 ^ 104 + 48 - 37 + 46) <= 0) {
                    return (105 + 64 - 81 + 54 ^ 2 + 20 - -75 + 37) & (81 ^ 85 ^ (161 ^ 173) ^ -" ".length());
                }
            } else {
                n = lIlllIlIlllIIll = lllllll[0];
            }
            if (!InkWeapon.lllIlIIII(lIlllIlIlllIlII) || InkWeapon.lllIlIIIl(lIlllIlIlllIIll)) {
                lIlllIlIlllIlll += 4;
            }
            return lIlllIlIlllIlll;
        }

        public boolean isEnabled() {
            InkWeapon lIlllIllIIIlllI;
            return lIlllIllIIIlllI.enabled;
        }

        private static boolean lllIlIIIl(int n) {
            return n != 0;
        }

        private static boolean lllIllIll(int n) {
            return n <= 0;
        }

        public abstract ItemStack getLoadMaterial();

        private static int lllIlIlII(double d, double d2) {
            return (int)(d DCMPG d2);
        }

        private static void llIlIllIl() {
            lllllll = new int[4];
            InkWeapon.lllllll[0] = (177 + 103 - 218 + 124 ^ 93 + 106 - 149 + 127) & (17 ^ 124 ^ (37 ^ 67) ^ -" ".length());
            InkWeapon.lllllll[1] = " ".length();
            InkWeapon.lllllll[2] = 55 + 68 - -1 + 17 ^ 35 + 113 - 123 + 166;
            InkWeapon.lllllll[3] = 119 ^ 55;
        }

        public abstract ItemStack getToolMaterial();

        private static boolean lllIlIIlI(Object object) {
            return object == null;
        }

        public int neededReloadTicks() {
            return lllllll[2];
        }

        private static int lllIIllII(double d, double d2) {
            return (int)(d DCMPG d2);
        }

        public int getWeaponLevel() {
            InkWeapon lIlllIllIIlIlII;
            return ((InkWarsPlayerInfo)lIlllIllIIlIlII.InkWars.this.getPlayerInfo(lIlllIllIIlIlII.getPlayer())).getWeaponLevel();
        }

        private static int lllIllIII(double d, double d2) {
            return (int)(d DCMPG d2);
        }

        public void giveTool() {
            InkWeapon lIlllIlIllIlIIl;
            ItemStack lIlllIlIllIlIII = lIlllIlIllIlIIl.getToolMaterial();
            if (InkWeapon.lllIlIIlI((Object)lIlllIlIllIlIII)) {
                return;
            }
            Utils.giveItemStack(lIlllIlIllIlIII, lIlllIlIllIlIIl.getPlayer());
            lIlllIlIllIlIIl.getPlayer().playSound(lIlllIlIllIlIIl.getPlayer().getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
            lIlllIlIllIlIIl.getPlayer().playSound(lIlllIlIllIlIIl.getPlayer().getEyeLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
        }

        public void destroy() {
            lIlllIllIIIIlIl.validWeapon = lllllll[0];
        }

        public InkWeapon(Player lIlllIllIIlIllI) {
            InkWeapon lIlllIllIIllIII;
            super(lIlllIllIIlIllI);
            lIlllIllIIllIII.reloadTicks = lllllll[0];
            lIlllIllIIllIII.validWeapon = lllllll[1];
            lIlllIllIIllIII.enabled = lllllll[1];
        }

        public boolean isValid() {
            InkWeapon lIlllIllIIlIIIl;
            return lIlllIllIIlIIIl.validWeapon;
        }

        private static boolean lllIlIlIl(Object object, Object object2) {
            return object != object2;
        }

        private static boolean lllIlIIll(int n, int n2) {
            return n >= n2;
        }

        public void rollerLinePaint(double lIlllIlIIIlIlIl, double lIlllIlIIIIIlII, Player lIlllIlIIIIIIll) {
            InkWeapon lIlllIlIIIlIlll;
            Vector lIlllIlIIIIlIll = new Vector(lllllll[0], lllllll[1], lllllll[0]);
            Vector lIlllIlIIIIlIlI = lIlllIlIIIIIIll.getLocation().getDirection();
            Vector lIlllIlIIIIlIIl = lIlllIlIIIIlIll.crossProduct(lIlllIlIIIIlIlI).normalize().multiply(lIlllIlIIIlIlIl);
            Vector lIlllIlIIIIlIII = lIlllIlIIIIIIll.getLocation().toVector().subtract(lIlllIlIIIIlIIl);
            BlockIterator lIlllIlIIIIIlll = new BlockIterator(lIlllIlIIIlIlll.getWorld(), lIlllIlIIIIlIII, lIlllIlIIIIlIIl, -1.0, (int)Math.round(2.0 * lIlllIlIIIlIlIl));
            while (InkWeapon.lllIlIIIl((int)lIlllIlIIIIIlll.hasNext())) {
                Block lIlllIlIIIllIIl = lIlllIlIIIIIlll.next();
                if (InkWeapon.lllIIllll(InkWeapon.lllIlIlII(Utils.pointToLineDistance(lIlllIlIIIIlIII, lIlllIlIIIIlIIl, lIlllIlIIIllIIl.getLocation().toVector()), 0.8))) {
                    lIlllIlIIIlIlll.paintBlock(lIlllIlIIIllIIl, lIlllIlIIIIIlII);
                }
                "".length();
                if ((36 + 54 - -24 + 44 ^ 118 + 89 - 189 + 136) > 0) continue;
                return;
            }
        }

        public void reloadTick() {
            InkWeapon lIlllIlIllIIIlI;
            if (InkWeapon.lllIlIIlI((Object)lIlllIlIllIIIlI.getLoadMaterial())) {
                return;
            }
            if (InkWeapon.lllIlIIIl((int)lIlllIlIllIIIlI.getPlayer().getInventory().contains(lIlllIlIllIIIlI.getLoadMaterial().getType(), lIlllIlIllIIIlI.getMaxLoad()))) {
                return;
            }
            if (InkWeapon.lllIlIIll(lIlllIlIllIIIlI.reloadTicks, lIlllIlIllIIIlI.neededReloadTicks())) {
                ItemStack lIlllIlIllIIIll = lIlllIlIllIIIlI.getLoadMaterial();
                if (InkWeapon.lllIlIIlI((Object)lIlllIlIllIIIll)) {
                    return;
                }
                Utils.giveItemStack(lIlllIlIllIIIll, lIlllIlIllIIIlI.getPlayer());
                lIlllIlIllIIIlI.getPlayer().updateInventory();
                lIlllIlIllIIIlI.getPlayer().playSound(lIlllIlIllIIIlI.getPlayer().getEyeLocation(), Sound.ENTITY_ITEM_PICKUP, 0.4f, 1.0f);
                lIlllIlIllIIIlI.reloadTicks = lllllll[0];
                "".length();
                if (((83 ^ 49) & ~(57 ^ 91)) != 0) {
                    return;
                }
            } else {
                lIlllIlIllIIIlI.reloadTicks += lIlllIlIllIIIlI.reloadTickIncrement();
            }
        }

        private static boolean lllIlIllI(Object object) {
            return object != null;
        }

        private static boolean lllIlIIII(int n) {
            return n == 0;
        }

        private static boolean lllIIlllI(Object object, Object object2) {
            return object == object2;
        }

        public void setEnabled(boolean lIlllIllIIIlIIl) {
            lIlllIllIIIlIII.enabled = lIlllIllIIIlIIl;
        }

        public void tick() {
            InkWeapon lIlllIllIIIIIlI;
            lIlllIllIIIIIlI.reloadTick();
        }

        private static boolean lllIIllll(int n) {
            return n < 0;
        }

        public int getReloadTicks() {
            InkWeapon lIlllIlIlIlIlIl;
            return lIlllIlIlIlIlIl.reloadTicks;
        }

        protected void paintRadius(Location lIlllIIllIlIIII, double lIlllIIllIIllll, double lIlllIIllIIlIlI) {
            Iterator<Block> lIlllIIllIIlIIl = Utils.getCuboidAround(lIlllIIllIlIIII, (int)Math.round(lIlllIIllIIllll)).getBlocks().iterator();
            while (InkWeapon.lllIlIIIl((int)lIlllIIllIIlIIl.hasNext())) {
                Block lIlllIIllIlIIlI = lIlllIIllIIlIIl.next();
                double lIlllIIllIlIIll = lIlllIIllIlIIII.distance(lIlllIIllIlIIlI.getLocation());
                if (InkWeapon.lllIllIll(InkWeapon.lllIllIII(lIlllIIllIlIIll, lIlllIIllIIllll))) {
                    InkWeapon lIlllIIllIIllIl;
                    lIlllIIllIIllIl.paintBlock(lIlllIIllIlIIlI, lIlllIIllIIlIlI * Math.sqrt(1.0 / lIlllIIllIlIIll));
                }
                "".length();
                if (-" ".length() < (62 ^ 58)) continue;
                return;
            }
        }

        public void paintBlock(Block lIlllIIlllIIlll, double lIlllIIlllIIllI) {
            InkWeapon lIlllIIlllIlIII;
            int lIlllIIlllIIlIl = lIlllIIlllIlIII.InkWars.this.isPaintableUnsafely(lIlllIIlllIIlll);
            int lIlllIIlllIIlII = lIlllIIlllIlIII.InkWars.this.isPaintable(lIlllIIlllIIlll) ? 1 : 0;
            if ((!InkWeapon.lllIlIIII(lIlllIIlllIIlII) || InkWeapon.lllIlIIIl(lIlllIIlllIIlIl)) && InkWeapon.lllIlIIIl((int)lIlllIIlllIlIII.isEnabled())) {
                EquipInkWars lIlllIIlllIllII = lIlllIIlllIlIII.InkWars.this.obtenirEquip(lIlllIIlllIlIII.getPlayer());
                DyeColor lIlllIIlllIlIll = lIlllIIlllIllII.getStrongColor();
                EquipInkWars lIlllIIlllIlIlI = lIlllIIlllIlIII.InkWars.this.getTeamOwningBlock(lIlllIIlllIIlll);
                if (InkWeapon.lllIlIlIl(lIlllIIlllIlIlI, lIlllIIlllIllII) && InkWeapon.lllIlIllI(lIlllIIlllIlIlI) && InkWeapon.lllIlIIIl((int)lIlllIIlllIlIII.InkWars.this.highInkBlocks.containsKey((Object)lIlllIIlllIIlll))) {
                    double lIlllIIlllIllIl = lIlllIIlllIlIII.InkWars.this.highInkBlocks.get((Object)lIlllIIlllIIlll).getSecond();
                    return;
                }
                if (InkWeapon.lllIlIlIl(lIlllIIlllIlIlI, lIlllIIlllIllII)) {
                    ((InkWarsPlayerInfo)lIlllIIlllIlIII.InkWars.this.getPlayerInfo(lIlllIIlllIlIII.getPlayer())).registerBlockPaint(lIlllIIlllIlIlI);
                }
                if (InkWeapon.lllIlIIIl(lIlllIIlllIIlIl)) {
                    lIlllIIlllIIlll.setType(Material.STAINED_CLAY);
                }
                lIlllIIlllIIlll.setData(lIlllIIlllIlIll.getWoolData(), lllllll[0]);
                double lIlllIIlllIlIIl = 0.0;
                if (InkWeapon.lllIlIIIl((int)lIlllIIlllIlIII.InkWars.this.highInkBlocks.containsKey((Object)lIlllIIlllIIlll))) {
                    lIlllIIlllIlIIl = lIlllIIlllIlIII.InkWars.this.highInkBlocks.get((Object)lIlllIIlllIIlll).getSecond();
                    "".length();
                    lIlllIIlllIlIII.InkWars.this.highInkBlocks.remove((Object)lIlllIIlllIIlll);
                }
                if (InkWeapon.lllIlIlIl(lIlllIIlllIlIlI, lIlllIIlllIllII)) {
                    lIlllIIlllIlIIl = 0.0;
                }
                "".length();
                lIlllIIlllIlIII.InkWars.this.highInkBlocks.put(lIlllIIlllIIlll, new Pair<String, Double>(lIlllIIlllIlIII.getPlayer().getName(), lIlllIIlllIIllI / 3.0 + lIlllIIlllIlIIl / 2.0));
            }
        }
    }

    class EquipInkWars
    extends JocEquips.Equip {
        private /* synthetic */ int ownedBlocks;
        private /* synthetic */ DyeColor strongColor;
        private static final /* synthetic */ int[] lIIIIII;

        private static boolean llIlllII(int n) {
            return n == 0;
        }

        public int getOwnedBlocks() {
            EquipInkWars lllIIllIIIIlIlI;
            return lllIIllIIIIlIlI.ownedBlocks;
        }

        public void setOwnedBlocks(int lllIIllIIIIIlIl) {
            lllIIllIIIIIllI.ownedBlocks = lllIIllIIIIIlIl;
        }

        public DyeColor getStrongColor() {
            EquipInkWars lllIIllIIIlIIlI;
            return lllIIllIIIlIIlI.strongColor;
        }

        public EquipInkWars(DyeColor lllIIllIIIlllII, DyeColor lllIIllIIIllIll, String lllIIllIIIllIlI) {
            EquipInkWars lllIIllIIIllllI;
            super(lllIIllIIIlllII, lllIIllIIIllIlI);
            lllIIllIIIllllI.ownedBlocks = lIIIIII[0];
            lllIIllIIIllllI.strongColor = lllIIllIIIllIll;
        }

        private static void llIllIll() {
            lIIIIII = new int[1];
            EquipInkWars.lIIIIII[0] = (106 ^ 44 ^ "  ".length()) & (129 ^ 144 ^ (0 ^ 85) ^ -" ".length());
        }

        static {
            EquipInkWars.llIllIll();
        }

        public void incrementOwnedBlocks(int lllIIlIllllllll) {
            EquipInkWars lllIIllIIIIIIII;
            lllIIllIIIIIIII.setOwnedBlocks(lllIIllIIIIIIII.getOwnedBlocks() + lllIIlIllllllll);
        }

        public void setStrongColor(DyeColor lllIIllIIIIllII) {
            lllIIllIIIIllIl.strongColor = lllIIllIIIIllII;
        }

        public double getOwnedPercent() {
            EquipInkWars lllIIlIlllllIlI;
            if (EquipInkWars.llIlllII(lllIIlIlllllIlI.InkWars.this.getTotalPaintedBlocks())) {
                return 0.0;
            }
            return (double)lllIIlIlllllIlI.getOwnedBlocks() / (double)lllIIlIlllllIlI.InkWars.this.getTotalPaintedBlocks() * 100.0;
        }
    }

    class MachinegunInkWeapon
    extends ProjectileInkWeapon {
        private static final /* synthetic */ String[] llIlII;
        private static final /* synthetic */ int[] lllIIl;

        @Override
        public int neededReloadTicks() {
            MachinegunInkWeapon lllIlllIIIIIIll;
            return lllIIl[2] - Math.round(lllIlllIIIIIIll.getWeaponLevel() * lllIIl[1]);
        }

        private static boolean lIllIlII(int n, int n2) {
            return n < n2;
        }

        @Override
        public ItemStack getLoadMaterial() {
            MachinegunInkWeapon lllIllIlllIlIll;
            return Utils.setItemName(new ItemStack(Material.SNOW_BALL, lllIIl[4]), String.valueOf(new StringBuilder().append((Object)lllIllIlllIlIll.InkWars.this.obtenirEquip(lllIllIlllIlIll.getPlayer()).getChatColor()).append(llIlII[lllIIl[3]])));
        }

        private static boolean lIllIIll(Object object, Object object2) {
            return object == object2;
        }

        private static boolean lIllIIIl(int n) {
            return n != 0;
        }

        private static void lIIllIll() {
            llIlII = new String[lllIIl[4]];
            MachinegunInkWeapon.llIlII[MachinegunInkWeapon.lllIIl[3]] = MachinegunInkWeapon.lIIllIlI("JxozVAYPGDQ=", "ntXtd");
        }

        public MachinegunInkWeapon(Player lllIlllIIIIlIIl) {
            MachinegunInkWeapon lllIlllIIIIlllI;
            super(lllIlllIIIIlIIl);
            lllIlllIIIIlIIl.setMaxHealth(20.0);
        }

        private static String lIIllIlI(String lllIllIllIIlIll, String lllIllIllIIlIlI) {
            lllIllIllIIlIll = new String(Base64.getDecoder().decode(lllIllIllIIlIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lllIllIllIIlllI = new StringBuilder();
            char[] lllIllIllIIllIl = lllIllIllIIlIlI.toCharArray();
            int lllIllIllIIllII = lllIIl[3];
            char[] lllIllIllIIIllI = lllIllIllIIlIll.toCharArray();
            int lllIllIllIIIlIl = lllIllIllIIIllI.length;
            int lllIllIllIIIlII = lllIIl[3];
            while (MachinegunInkWeapon.lIllIlII(lllIllIllIIIlII, lllIllIllIIIlIl)) {
                char lllIllIllIlIIIl = lllIllIllIIIllI[lllIllIllIIIlII];
                "".length();
                lllIllIllIIlllI.append((char)(lllIllIllIlIIIl ^ lllIllIllIIllIl[lllIllIllIIllII % lllIllIllIIllIl.length]));
                ++lllIllIllIIllII;
                ++lllIllIllIIIlII;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.valueOf(lllIllIllIIlllI);
        }

        @Override
        public int getMaxLoad() {
            MachinegunInkWeapon lllIlllIIIIIlll;
            return lllIIl[0] + Math.round(lllIlllIIIIIlll.getWeaponLevel() / lllIIl[1]);
        }

        @Override
        public void onWeaponHit(ProjectileHitEvent lllIllIlllllIII, Projectile lllIllIllllIlll, Block lllIllIllllIIlI, Block lllIllIllllIIIl) {
            if (MachinegunInkWeapon.lIllIIIl(lllIllIllllIlll instanceof Snowball)) {
                MachinegunInkWeapon lllIllIlllllIIl;
                Snowball lllIllIlllllIlI = (Snowball)lllIllIllllIlll;
                lllIllIlllllIIl.getWorld().playSound(lllIllIllllIIlI.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1.0f, 1.1f);
                lllIllIlllllIIl.getWorld().playSound(lllIllIllllIIlI.getLocation(), Sound.ENTITY_SLIME_JUMP, 1.0f, 1.1f);
                lllIllIlllllIIl.getWorld().playEffect(lllIllIllllIIIl.getLocation(), Effect.SPLASH, lllIIl[3]);
                lllIllIlllllIIl.paintRadius(lllIllIllllIIIl.getLocation(), 1.1 + Math.sqrt((double)lllIllIlllllIIl.getWeaponLevel() * 0.75), 4.0 + (double)lllIllIlllllIIl.getWeaponLevel() / 2.0);
                Iterator<Player> lllIllIlllIllll = Utils.getNearbyPlayers(lllIllIllllIIIl.getLocation(), (double)(lllIIl[4] + lllIllIlllllIIl.getWeaponLevel())).iterator();
                while (MachinegunInkWeapon.lIllIIIl((int)lllIllIlllIllll.hasNext())) {
                    Player lllIllIlllllIll = lllIllIlllIllll.next();
                    if (MachinegunInkWeapon.lIllIIIl((int)lllIllIlllllIIl.InkWars.this.areEnemies(lllIllIlllllIll, lllIllIlllllIIl.getPlayer()).booleanValue())) {
                        lllIllIlllllIll.damage(2.0 + (6.5 + (double)lllIllIlllllIIl.getWeaponLevel() / 2.2) / (lllIllIlllllIll.getLocation().distance(lllIllIllllIIIl.getLocation()) * (lllIllIllllIIIl.getLocation().distance(lllIllIlllllIIl.getPlayer().getEyeLocation()) / 3.0)), (Entity)lllIllIlllllIIl.getPlayer());
                    }
                    "".length();
                    if ((154 ^ 158) > "  ".length()) continue;
                    return;
                }
            }
        }

        private static void lIllIIII() {
            lllIIl = new int[5];
            MachinegunInkWeapon.lllIIl[0] = 80 + 74 - 20 + 26 ^ 48 + 105 - 4 + 15;
            MachinegunInkWeapon.lllIIl[1] = "  ".length();
            MachinegunInkWeapon.lllIIl[2] = 139 + 56 - 136 + 123 ^ 62 + 45 - 105 + 128;
            MachinegunInkWeapon.lllIIl[3] = (183 + 107 - 266 + 185 ^ 109 + 72 - 114 + 68) & (98 ^ 10 ^ (57 ^ 7) ^ -" ".length());
            MachinegunInkWeapon.lllIIl[4] = " ".length();
        }

        static {
            MachinegunInkWeapon.lIllIIII();
            MachinegunInkWeapon.lIIllIll();
        }

        protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lllIllIllIllllI, Player lllIllIllIlllIl, Player lllIllIllIlllII, boolean lllIllIllIllIll) {
            MachinegunInkWeapon lllIllIlllIIlII;
            super.onPlayerDamageByPlayer(lllIllIllIllllI, lllIllIllIlllIl, lllIllIllIlllII, lllIllIllIllIll);
            if (MachinegunInkWeapon.lIllIIlI((int)lllIllIllIllIll) && MachinegunInkWeapon.lIllIIll((Object)lllIllIllIlllII, (Object)lllIllIlllIIlII.getPlayer())) {
                lllIllIllIllllI.setCancelled(lllIIl[4]);
            }
        }

        @Override
        public ItemStack getToolMaterial() {
            return null;
        }

        private static boolean lIllIIlI(int n) {
            return n == 0;
        }
    }

}

