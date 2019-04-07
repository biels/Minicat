/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.PistonMoveReaction
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.EnderCrystal
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Fish
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.ItemDespawnEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Turret;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Torres
extends JocEquips {
    /* synthetic */ boolean debug;
    private static final /* synthetic */ int[] lIIIIllI;
    public /* synthetic */ ArrayList<Turret> Turrets;
    private static final /* synthetic */ String[] llllllI;

    boolean getTNTEnabled() {
        Torres lIllIlIIlllllIl;
        boolean lIllIlIIlllllII = lIIIIllI[1];
        if (Torres.llllllIll((int)lIllIlIIlllllIl.pMapaActual().ExisteixPropietat(llllllI[lIIIIllI[16]]))) {
            lIllIlIIlllllII = lIllIlIIlllllIl.pMapaActual().ObtenirPropietat(llllllI[lIIIIllI[17]]).equalsIgnoreCase(llllllI[lIIIIllI[18]]);
        }
        return lIllIlIIlllllII;
    }

    private static String llIlIlllI(String lIllIIIlllllllI, String lIllIIIllllllll) {
        try {
            SecretKeySpec lIllIIlIIIIIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIIIllllllll.getBytes(StandardCharsets.UTF_8)), lIIIIllI[17]), "DES");
            Cipher lIllIIlIIIIIIlI = Cipher.getInstance("DES");
            lIllIIlIIIIIIlI.init(lIIIIllI[2], lIllIIlIIIIIIll);
            return new String(lIllIIlIIIIIIlI.doFinal(Base64.getDecoder().decode(lIllIIIlllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIIlIIIIIIIl) {
            lIllIIlIIIIIIIl.printStackTrace();
            return null;
        }
    }

    private static boolean lIIIIIIIIl(int n, int n2) {
        return n <= n2;
    }

    private static boolean lIIIIIIlIl(int n, int n2) {
        return n == n2;
    }

    public ArrayList<Location> getInhibitors(JocEquips.Equip lIllIlIIlllIllI) {
        Torres lIllIlIIlllIlll;
        return lIllIlIIlllIlll.pMapaActual().ObtenirLocations(String.valueOf(new StringBuilder().append(llllllI[lIIIIllI[14]]).append(lIllIlIIlllIllI.getId())), lIllIlIIlllIlll.getWorld());
    }

    private static void lllIIlIll() {
        llllllI = new String[lIIIIllI[43]];
        Torres.llllllI[Torres.lIIIIllI[0]] = Torres.llIlIlllI("ppanLbJQqeI=", "Lscgg");
        Torres.llllllI[Torres.lIIIIllI[1]] = Torres.llIllIIII("OwY5LQ==", "YjXXY");
        Torres.llllllI[Torres.lIIIIllI[2]] = Torres.llIlIlllI("shDbnJP+rFBwR4kHqwCFKg==", "qQHFK");
        Torres.llllllI[Torres.lIIIIllI[3]] = Torres.llIllIIII("DgYiACo=", "hgNsO");
        Torres.llllllI[Torres.lIIIIllI[5]] = Torres.llIlIlllI("IS5ctAS2OPw=", "YHJrj");
        Torres.llllllI[Torres.lIIIIllI[8]] = Torres.llIlIlllI("ITBCp0Nx5YAmr1Rv0HtEgA==", "fuNuN");
        Torres.llllllI[Torres.lIIIIllI[15]] = Torres.llIlIlllI("ffV01TBo/wR+AZwFUKtbfWDgnvjSe0a4p2JgwalTqIE=", "jxWsw");
        Torres.llllllI[Torres.lIIIIllI[16]] = Torres.llIllIIII("IBgNED8VNDUwNQ==", "tVYUQ");
        Torres.llllllI[Torres.lIIIIllI[17]] = Torres.lllIIIIII("/D8kdAehdc23uTjr+xJ/Yg==", "CJKbr");
        Torres.llllllI[Torres.lIIIIllI[18]] = Torres.llIllIIII("EiQFEg==", "fVpwW");
        Torres.llllllI[Torres.lIIIIllI[14]] = Torres.llIlIlllI("bK/atScFV80L4mS2THmAVA==", "XTnPy");
        Torres.llllllI[Torres.lIIIIllI[19]] = Torres.llIllIIII("IyE2NzQHIA==", "sSSDQ");
        Torres.llllllI[Torres.lIIIIllI[20]] = Torres.lllIIIIII("xNJDAUXYCPM=", "ZoxGp");
        Torres.llllllI[Torres.lIIIIllI[22]] = Torres.llIllIIII("Kj8NEDQsPQQ=", "hPceG");
        Torres.llllllI[Torres.lIIIIllI[23]] = Torres.lllIIIIII("otHG9Z4sHKMyXeVITC98zg==", "uLmnu");
        Torres.llllllI[Torres.lIIIIllI[24]] = Torres.lllIIIIII("nBN7kke483B6VEIPtD0lmw==", "wDDeg");
        Torres.llllllI[Torres.lIIIIllI[25]] = Torres.llIlIlllI("mD3zkelAxHOP4Qs36okv/A==", "vLSFi");
        Torres.llllllI[Torres.lIIIIllI[26]] = Torres.llIllIIII("Bg4kPwcOFCc6", "DaJJt");
        Torres.llllllI[Torres.lIIIIllI[29]] = Torres.llIlIlllI("wkuWk3zDft8=", "aUqKb");
        Torres.llllllI[Torres.lIIIIllI[30]] = Torres.llIlIlllI("XN92YQHfUwKwAL5T/s9slQ==", "olJEB");
        Torres.llllllI[Torres.lIIIIllI[27]] = Torres.lllIIIIII("rJEGu/CZJ7NFn8RvPriXu0lLenX/dK3kMw+IkTXVLorlGItLAt4JYQ7Ek6ir+SwK", "uMhPk");
        Torres.llllllI[Torres.lIIIIllI[31]] = Torres.lllIIIIII("uoKswujXFpzA9ohsukiSyA==", "HhfmJ");
        Torres.llllllI[Torres.lIIIIllI[34]] = Torres.llIllIIII("PSkmACnCn2cuSy0WIjgLIRlnLgk7DDU/woM8WQ==", "xGJlH");
        Torres.llllllI[Torres.lIIIIllI[37]] = Torres.lllIIIIII("NM6R15yWG5LoBc08Qp4IBRc2/E5M941s", "BnZNm");
        Torres.llllllI[Torres.lIIIIllI[39]] = Torres.lllIIIIII("6fKyLbMNvyQ=", "zifkx");
        Torres.llllllI[Torres.lIIIIllI[40]] = Torres.llIllIIII("BisebhkrOAk7HW8=", "NJmNi");
        Torres.llllllI[Torres.lIIIIllI[41]] = Torres.llIllIIII("", "ITgjB");
        Torres.llllllI[Torres.lIIIIllI[42]] = Torres.lllIIIIII("zY3S7tOl+BmY6aE2NYQVxg==", "CzgoN");
    }

    @Override
    protected boolean isRecallEnabled() {
        return lIIIIllI[1];
    }

    private static boolean lllllllII(Object object, Object object2) {
        return object == object2;
    }

    public TurretPreset getTurretPreset(int lIllIlIIllIllIl) {
        TurretPreset lIllIlIIllIllll;
        Torres lIllIlIIllIlllI;
        String[] lIllIlIIllIllII = lIllIlIIllIlllI.pMapaActual().ObtenirLlista(llllllI[lIIIIllI[19]]);
        if (Torres.llllllllI(lIllIlIIllIllIl, lIllIlIIllIllII.length) && Torres.lllllllll((Object)(lIllIlIIllIllll = TurretPreset.valueOf(lIllIlIIllIllII[lIllIlIIllIllIl])))) {
            return lIllIlIIllIllll;
        }
        return TurretPreset.OUTER;
    }

    private static boolean llllllIII(int n, int n2) {
        return n > n2;
    }

    private static boolean lIIIIlIlII(int n) {
        return n < 0;
    }

    @Override
    protected void customJocIniciat() {
        Object lIllIlIIlIllllI;
        Torres lIllIlIIlIlIlll;
        super.customJocIniciat();
        int lIllIlIIlIllIII = lIIIIllI[0];
        while (Torres.lIIIIIIIIl(lIllIlIIlIllIII, lIIIIllI[1])) {
            lIllIlIIlIllllI = Integer.toString(lIllIlIIlIllIII);
            int lIllIlIIlIlllIl = lIIIIllI[0];
            ArrayList<Location> lIllIlIIlIlllII = lIllIlIIlIlIlll.pMapaActual().ObtenirLocations(String.valueOf(new StringBuilder().append(llllllI[lIIIIllI[20]]).append((String)lIllIlIIlIllllI)), lIllIlIIlIlIlll.world);
            while (Torres.llllllllI(lIllIlIIlIlllIl, lIllIlIIlIlllII.size())) {
                TurretPreset lIllIlIIllIIIII = lIllIlIIlIlIlll.getTurretPreset(lIllIlIIlIlllIl);
                Turret lIllIlIIlIlllll = Turret.createTurret(Com.getPlugin(), lIllIlIIlIlllII.get(lIllIlIIlIlllIl), null, lIllIlIIlIlIlll, lIllIlIIlIlIlll.obtenirEquip(lIllIlIIlIllIII), lIIIIllI[0], lIIIIllI[0]);
                lIllIlIIlIlllll.Build();
                lIllIlIIlIlllll.xp = lIIIIllI[6];
                lIllIlIIlIlllll.hasInventory = lIIIIllI[0];
                if (!Torres.lIIIIIIIll((Object)lIllIlIIllIIIII, (Object)TurretPreset.INHIBITOR) || Torres.lllllllII((Object)lIllIlIIllIIIII, (Object)TurretPreset.NEXUS)) {
                    int n;
                    if (Torres.lllllllII((Object)lIllIlIIllIIIII, (Object)TurretPreset.NEXUS)) {
                        n = lIIIIllI[12];
                        "".length();
                        if (" ".length() == 0) {
                            return;
                        }
                    } else {
                        n = lIIIIllI[0];
                    }
                    lIllIlIIlIlllll.maxHpEscut = lIIIIllI[14] + n;
                    lIllIlIIlIlllll.getByTipus((Turret.TipusMillora)Turret.TipusMillora.RESIST\u00c8NCIA).lvl = lIIIIllI[1];
                    lIllIlIIlIlllll.tempsEscut = lIIIIllI[17];
                    lIllIlIIlIlllll.VelAtac = lIIIIllI[14];
                    lIllIlIIlIlllll.resetArmorCD();
                }
                if (Torres.lllllllII((Object)lIllIlIIllIIIII, (Object)TurretPreset.LASER)) {
                    lIllIlIIlIlllll.getByTipus((Turret.TipusMillora)Turret.TipusMillora.FOC).lvl = lIIIIllI[1];
                    lIllIlIIlIlllll.hp = lIIIIllI[21];
                    lIllIlIIlIlllll.distAtac += lIIIIllI[8];
                    lIllIlIIlIlllll.Atac = lIIIIllI[20];
                }
                lIllIlIIlIlllll.distAtac += lIIIIllI[5];
                lIllIlIIlIlllll.Atac += lIIIIllI[3];
                lIllIlIIlIlllll.Attack();
                lIllIlIIlIlllIl += lIIIIllI[1];
                "".length();
                if ((90 ^ 94) > " ".length()) continue;
                return;
            }
            lIllIlIIlIllIII += lIIIIllI[1];
            "".length();
            if (null == null) continue;
            return;
        }
        lIllIlIIlIlIlll.GenerarBonus(llllllI[lIIIIllI[22]], TipusBonus.DAMAGE);
        lIllIlIIlIlIlll.GenerarBonus(llllllI[lIIIIllI[23]], TipusBonus.SPEED);
        lIllIlIIlIlIlll.GenerarBonus(llllllI[lIIIIllI[24]], TipusBonus.HEAL);
        lIllIlIIlIlIlll.GenerarBonus(llllllI[lIIIIllI[25]], TipusBonus.PROTECTION);
        lIllIlIIlIlIlll.GenerarBonus(llllllI[lIIIIllI[26]], TipusBonus.JUMP);
        lIllIlIIlIllllI = lIllIlIIlIlIlll.getPlayers().iterator();
        while (Torres.llllllIll((int)lIllIlIIlIllllI.hasNext())) {
            Player lIllIlIIlIllIlI = (Player)lIllIlIIlIllllI.next();
            Turret lIllIlIIlIllIll = Turret.createTurret(Com.getPlugin(), lIllIlIIlIllIlI.getLocation(), lIllIlIIlIllIlI, lIllIlIIlIlIlll, lIllIlIIlIlIlll.obtenirEquip(lIllIlIIlIllIlI), lIIIIllI[0], lIIIIllI[1]);
            lIllIlIIlIllIll.xp = lIIIIllI[27];
            "".length();
            if (((93 ^ 118 ^ (213 ^ 169)) & (60 ^ 5 ^ (83 ^ 61) ^ -" ".length())) != "  ".length()) continue;
            return;
        }
    }

    private static boolean llllllllI(int n, int n2) {
        return n < n2;
    }

    private static int lIIIIIlIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIIIIIllII(int n) {
        return n >= 0;
    }

    protected void onEntityDamage(EntityDamageEvent lIllIIllIlIIllI, Entity lIllIIllIlIIlIl) {
        Torres lIllIIllIlIIlll;
        super.onEntityDamage(lIllIIllIlIIllI, lIllIIllIlIIlIl);
        if (Torres.lllllllII((Object)lIllIIllIlIIllI.getEntityType(), (Object)EntityType.ENDER_CRYSTAL)) {
            lIllIIllIlIIllI.setCancelled(lIIIIllI[1]);
        }
        lIllIIllIlIIllI.setDamage(lIllIIllIlIIllI.getDamage() * 0.6);
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent lIllIlIIIIlIIIl, Entity lIllIlIIIIlIIII, Entity lIllIlIIIIIllll) {
        Torres lIllIlIIIIIlllI;
        super.onEntityDamageByEntity(lIllIlIIIIlIIIl, lIllIlIIIIlIIII, lIllIlIIIIIllll);
        if (Torres.lllllllII((Object)lIllIlIIIIlIIIl.getEntityType(), (Object)EntityType.ENDER_CRYSTAL)) {
            Object lIllIlIIIlIIIIl;
            EnderCrystal lIllIlIIIIlIllI = (EnderCrystal)lIllIlIIIIlIIIl.getEntity();
            int lIllIlIIIIlIlIl = lIIIIllI[0];
            List lIllIlIIIIlIlII = lIllIlIIIIlIllI.getMetadata(llllllI[lIIIIllI[29]]);
            Iterator lIllIlIIIIIIlll = lIllIlIIIIlIlII.iterator();
            while (Torres.llllllIll((int)lIllIlIIIIIIlll.hasNext())) {
                MetadataValue lIllIlIIIlIIIll = (MetadataValue)lIllIlIIIIIIlll.next();
                if (Torres.llllllIll((int)lIllIlIIIlIIIll.getOwningPlugin().getDescription().getName().equals(lIllIlIIIIIlllI.plugin.getDescription().getName()))) {
                    lIllIlIIIIlIlIl = lIllIlIIIlIIIll.asInt();
                }
                "".length();
                if (-" ".length() < 0) continue;
                return;
            }
            int lIllIlIIIIlIIll = lIllIlIIIIlIlIl;
            if (Torres.llllllIll(lIllIlIIIIlIIIl.getDamager() instanceof Player)) {
                Player lIllIlIIIlIIIlI = (Player)lIllIlIIIIlIIIl.getDamager();
                if (Torres.lIIIIIIlIl((int)lIllIlIIIIIlllI.obtenirEquip(lIllIlIIIIlIIll).getPlayers().contains((Object)lIllIlIIIlIIIlI), lIIIIllI[1])) {
                    lIllIlIIIIlIIIl.setCancelled(lIIIIllI[1]);
                }
            }
            if (Torres.llllllIll(lIllIlIIIIlIIIl.getDamager() instanceof Arrow)) {
                lIllIlIIIlIIIIl = (Arrow)lIllIlIIIIlIIIl.getDamager();
                LivingEntity lIllIlIIIlIIIII = (LivingEntity)lIllIlIIIlIIIIl.getShooter();
                lIllIlIIIIlIIIl.setCancelled(lIIIIllI[1]);
            }
            if (Torres.lllllllIl((int)lIllIlIIIIlIIIl.isCancelled())) {
                lIllIlIIIlIIIIl = lIllIlIIIIIlllI.pMapaActual().ObtenirLocations(String.valueOf(new StringBuilder().append(llllllI[lIIIIllI[30]]).append(Integer.toString(lIllIlIIIIlIIll))), lIllIlIIIIIlllI.world).iterator();
                while (Torres.llllllIll((int)lIllIlIIIlIIIIl.hasNext())) {
                    Location lIllIlIIIIllllI = (Location)lIllIlIIIlIIIIl.next();
                    if (Torres.lllllllII((Object)lIllIlIIIIllllI.getBlock().getType(), (Object)Material.BEACON)) {
                        lIllIlIIIIlIIIl.setCancelled(lIIIIllI[1]);
                        if (Torres.llllllIll(lIllIlIIIIlIIIl.getDamager() instanceof Player)) {
                            Player lIllIlIIIIlllll = (Player)lIllIlIIIIlIIIl.getDamager();
                            lIllIlIIIIlllll.sendMessage(llllllI[lIIIIllI[27]]);
                        }
                    }
                    "".length();
                    if ("   ".length() == "   ".length()) continue;
                    return;
                }
            }
            if (Torres.lllllllIl((int)lIllIlIIIIlIIIl.isCancelled())) {
                ArrayList<Player> lIllIlIIIIllIIl = lIllIlIIIIIlllI.getPlayers();
                ArrayList<Location> lIllIlIIIIllIII = Utils.getLocationsCircle(lIllIlIIIIlIllI.getLocation(), 2.2, lIIIIllI[14]);
                ArrayList<Location> lIllIlIIIIlIlll = new ArrayList<Location>();
                Iterator<Location> lIllIlIIIIIIIll = lIllIlIIIIllIII.iterator();
                while (Torres.llllllIll((int)lIllIlIIIIIIIll.hasNext())) {
                    Location lIllIlIIIIlllIl = lIllIlIIIIIIIll.next();
                    if (Torres.lllllllII((Object)lIllIlIIIIlllIl.getBlock().getType(), (Object)Material.AIR)) {
                        "".length();
                        lIllIlIIIIlIlll.add(lIllIlIIIIlllIl);
                    }
                    "".length();
                    if ((148 ^ 132 ^ (70 ^ 82)) > "  ".length()) continue;
                    return;
                }
                lIllIlIIIIIIIll = lIllIlIIIIllIIl.iterator();
                while (Torres.llllllIll((int)lIllIlIIIIIIIll.hasNext())) {
                    Player lIllIlIIIIllIlI = (Player)lIllIlIIIIIIIll.next();
                    Location lIllIlIIIIlllII = (Location)lIllIlIIIIlIlll.get(Utils.NombreEntre(lIIIIllI[0], lIllIlIIIIlIlll.size() - lIIIIllI[1]));
                    Location lIllIlIIIIllIll = Utils.lookAt(lIllIlIIIIlllII, lIllIlIIIIlIllI.getLocation());
                    "".length();
                    lIllIlIIIIllIlI.teleport(lIllIlIIIIllIll);
                    if (Torres.lIIIIIIlIl(lIllIlIIIIIlllI.obtenirEquip(lIllIlIIIIllIlI).getId(), lIllIlIIIIlIIll)) {
                        lIllIlIIIIllIlI.setGameMode(GameMode.SURVIVAL);
                        "".length();
                        if (-(32 ^ 36) > 0) {
                            return;
                        }
                    } else {
                        lIllIlIIIIllIlI.setGameMode(GameMode.CREATIVE);
                    }
                    "".length();
                    if (((127 ^ 107 ^ (220 ^ 154)) & (130 ^ 171 ^ (96 ^ 27) ^ -" ".length())) <= 0) continue;
                    return;
                }
                lIllIlIIIIIlllI.world.playSound(lIllIlIIIIlIllI.getLocation(), Sound.AMBIENT_CAVE, 30.0f, 3.0f);
                "".length();
                lIllIlIIIIIlllI.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIllIlIIIIIlllI.plugin, () -> {
                    Torres lIllIIlIIIIlllI;
                    Iterator lIllIIlIIIIllII = lIllIIlIIIIlllI.plugin.getServer().getOnlinePlayers().iterator();
                    while (Torres.llllllIll((int)lIllIIlIIIIllII.hasNext())) {
                        Player lIllIIlIIIIllll = (Player)lIllIIlIIIIllII.next();
                        "".length();
                        lIllIIlIIIIllll.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIIIllI[36], lIIIIllI[1], lIIIIllI[0]), lIIIIllI[1]);
                        "".length();
                        if (-"  ".length() <= 0) continue;
                        return;
                    }
                }, 20L);
                "".length();
                lIllIlIIIIIlllI.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIllIlIIIIIlllI.plugin, () -> {
                    Torres lIllIIlIIIlIllI;
                    Iterator lIllIIlIIIlIlII = lIllIIlIIIlIllI.plugin.getServer().getOnlinePlayers().iterator();
                    while (Torres.llllllIll((int)lIllIIlIIIlIlII.hasNext())) {
                        Player lIllIIlIIIlIlll = (Player)lIllIIlIIIlIlII.next();
                        "".length();
                        lIllIIlIIIlIlll.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIIIllI[4], lIIIIllI[3], lIIIIllI[0]), lIIIIllI[1]);
                        "".length();
                        if (" ".length() < "  ".length()) continue;
                        return;
                    }
                }, 25L);
                "".length();
                lIllIlIIIIIlllI.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIllIlIIIIIlllI.plugin, () -> {
                    Torres lIllIIlIIIllllI;
                    Iterator lIllIIlIIIlllII = lIllIIlIIIllllI.plugin.getServer().getOnlinePlayers().iterator();
                    while (Torres.llllllIll((int)lIllIIlIIIlllII.hasNext())) {
                        Player lIllIIlIIIlllll = (Player)lIllIIlIIIlllII.next();
                        "".length();
                        lIllIIlIIIlllll.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIIIllI[11], lIIIIllI[15], lIIIIllI[0]), lIIIIllI[1]);
                        "".length();
                        if (-" ".length() <= ((82 ^ 69 ^ (92 ^ 15)) & (133 + 160 - 186 + 117 ^ 81 + 136 - 113 + 60 ^ -" ".length()))) continue;
                        return;
                    }
                }, 30L);
                "".length();
                lIllIlIIIIIlllI.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIllIlIIIIIlllI.plugin, () -> {
                    Torres lIllIIlIIlIIlll;
                    Iterator lIllIIlIIlIIlII = lIllIIlIIlIIlll.plugin.getServer().getOnlinePlayers().iterator();
                    while (Torres.llllllIll((int)lIllIIlIIlIIlII.hasNext())) {
                        Player lIllIIlIIlIlIll = (Player)lIllIIlIIlIIlII.next();
                        if (Torres.lIIIIIIlIl(lIllIIlIIlIIlll.obtenirEquip(lIllIIlIIlIlIll).getId(), lIllIlIIIIlIIll)) {
                            lIllIIlIIlIlIll.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(llllllI[lIIIIllI[39]]).append((Object)ChatColor.RED).append(llllllI[lIIIIllI[40]])));
                            "".length();
                            if ((105 ^ 109) < 0) {
                                return;
                            }
                        } else {
                            lIllIIlIIlIlIll.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(llllllI[lIIIIllI[41]]).append((Object)ChatColor.GREEN).append(llllllI[lIIIIllI[42]])));
                        }
                        "".length();
                        lIllIIlIIlIIlll.world.createExplosion(lIllIlIIIIlIllI.getLocation(), 18.0f);
                        lIllIlIIIIlIllI.remove();
                        "".length();
                        if (-" ".length() != "   ".length()) continue;
                        return;
                    }
                }, 35L);
            }
            lIllIlIIIIlIIIl.setCancelled(lIIIIllI[1]);
        }
    }

    public Torres() {
        Torres lIllIlIllIIIIlI;
        lIllIlIllIIIIlI.debug = lIIIIllI[0];
        lIllIlIllIIIIlI.Turrets = new ArrayList();
    }

    private static int lIIIIlIlIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIIIIIllIl(int n) {
        return n > 0;
    }

    public ItemStack getTurretItem() {
        ItemStack lIllIIllIlIlllI = new ItemStack(Material.ARROW, lIIIIllI[1]);
        lIllIIllIlIlllI.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, lIIIIllI[1]);
        ItemMeta lIllIIllIlIllIl = lIllIIllIlIlllI.getItemMeta();
        lIllIIllIlIllIl.setDisplayName(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(llllllI[lIIIIllI[37]])));
        "".length();
        lIllIIllIlIlllI.setItemMeta(lIllIIllIlIllIl);
        return lIllIIllIlIlllI;
    }

    protected void onPlayerInteract(PlayerInteractEvent lIllIIlllIllIlI, Player lIllIIlllIllIIl) {
        Torres lIllIIlllIllIll;
        super.onPlayerInteract(lIllIIlllIllIlI, lIllIIlllIllIIl);
        ItemStack lIllIIlllIllIII = lIllIIlllIllIlI.getItem();
        PlayerInventory lIllIIlllIlIlll = lIllIIlllIllIIl.getInventory();
        Player lIllIIlllIlIllI = lIllIIlllIllIlI.getPlayer();
        if (Torres.lllllllll((Object)lIllIIlllIllIII)) {
            if (Torres.lllllllII((Object)lIllIIlllIllIII.getType(), (Object)Material.ENCHANTED_BOOK)) {
                Turret lIllIIllllIIlIl = Turret.getAdmin(lIllIIlllIllIll, lIllIIlllIlIllI);
                if (Torres.lIIIIIIlll((Object)lIllIIllllIIlIl)) {
                    lIllIIlllIllIll.sendGlobalMessage(llllllI[lIIIIllI[31]]);
                }
                if (Torres.lllllllll((Object)lIllIIllllIIlIl)) {
                    lIllIIllllIIlIl.openOrRefreshInventory(lIllIIlllIlIllI);
                }
            }
            if (Torres.lllllllII((Object)lIllIIlllIllIII.getType(), (Object)Material.DIAMOND_AXE)) {
                Vector lIllIIllllIIlII = lIllIIlllIlIllI.getLocation().getDirection();
                int lIllIIllllIIIll = lIIIIllI[0];
                int lIllIIllllIIIlI = lIIIIllI[8];
                int lIllIIllllIIIIl = lIIIIllI[8];
                while (Torres.llllllllI(lIllIIllllIIIll, lIllIIllllIIIlI)) {
                    "".length();
                    lIllIIlllIllIll.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIllIIlllIllIll.plugin, () -> {
                        int lIllIIlIIlllIIl = lIIIIllI[38];
                        while (Torres.lIIIIIIIIl(lIllIIlIIlllIIl, lIIIIllI[3])) {
                            Torres lIllIIlIIlllIll;
                            float lIllIIlIlIIIIII = lIllIIlllIlIllI.getLocation().getYaw() + (float)(lIIIIllI[16] * lIllIIlIIlllIIl) + 90.0f;
                            double lIllIIlIIllllll = 0.017453292519943295;
                            Location lIllIIlIIlllllI = lIllIIlllIlIllI.getLocation().add(0.0, 1.05, 0.0).add(new Location(lIllIIlIIlllIll.world, Math.cos((double)lIllIIlIlIIIIII * lIllIIlIIllllll), 0.0, Math.sin((double)lIllIIlIlIIIIII * lIllIIlIIllllll)));
                            Vector lIllIIlIIllllIl = lIllIIlIIlllllI.toVector().subtract(lIllIIlllIlIllI.getLocation().add(0.0, 1.0, 0.0).toVector()).normalize().multiply(0.5);
                            Arrow lIllIIlIIllllII = (Arrow)lIllIIlIIlllIll.world.spawnEntity(lIllIIlIIlllllI, EntityType.ARROW);
                            lIllIIlIIllllII.setShooter((ProjectileSource)lIllIIlllIlIllI);
                            lIllIIlIIllllII.setFireTicks(lIIIIllI[6]);
                            lIllIIlIIllllII.setVelocity(lIllIIlIIllllIl.multiply(lIIIIllI[17]));
                            lIllIIlIIlllIIl += lIIIIllI[1];
                            lIllIIlIIlllIll.world.playSound(lIllIIlIIlllllI, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                            "".length();
                            if (((71 ^ 117 ^ (76 ^ 108)) & (233 ^ 156 ^ (241 ^ 150) ^ -" ".length())) >= (((5 ^ 12) & ~(17 ^ 24) ^ (111 ^ 71)) & (88 ^ 36 ^ (21 ^ 65) ^ -" ".length()))) continue;
                            return;
                        }
                    }, (long)(lIllIIllllIIIIl * lIllIIllllIIIll));
                    lIllIIllllIIIll += lIIIIllI[1];
                    lIllIIlllIllIII.setDurability((short)(lIllIIlllIllIII.getDurability() + lIIIIllI[32]));
                    "".length();
                    if ("   ".length() >= 0) continue;
                    return;
                }
                if (Torres.lIIIIIlIII(lIllIIlllIllIII.getDurability(), lIllIIlllIllIII.getType().getMaxDurability())) {
                    ItemStack[] arritemStack = new ItemStack[lIIIIllI[1]];
                    arritemStack[Torres.lIIIIllI[0]] = lIllIIlllIllIII;
                    "".length();
                    lIllIIlllIlIlll.removeItem(arritemStack);
                }
                lIllIIlllIllIlI.setCancelled(lIIIIllI[1]);
            }
        }
        if (Torres.lllllllII((Object)lIllIIlllIllIlI.getAction(), (Object)Action.RIGHT_CLICK_BLOCK)) {
            Location lIllIIlllIlllIl = lIllIIlllIllIlI.getClickedBlock().getLocation().add(new Location(lIllIIlllIllIll.world, 0.0, 1.0, 0.0));
            Block lIllIIlllIlllII = lIllIIlllIllIlI.getClickedBlock();
            if (Torres.lllllllll((Object)lIllIIlllIllIII) && Torres.lllllllII((Object)lIllIIlllIllIII.getType(), (Object)Material.ARROW) && (!Torres.llllllllI(lIllIIlllIllIII.getEnchantments().size(), lIIIIllI[1]) || Torres.lIIIIIIlIl((int)lIllIIlllIllIll.debug, lIIIIllI[1]))) {
                if (Torres.lllllllII((Object)lIllIIlllIlllII.getPistonMoveReaction(), (Object)PistonMoveReaction.BREAK)) {
                    lIllIIlllIlllII.setType(Material.AIR);
                    lIllIIlllIlllII = lIllIIlllIlllII.getLocation().clone().add(new Vector(lIIIIllI[0], lIIIIllI[33], lIIIIllI[0])).getBlock();
                }
                Turret lIllIIlllIlllll = Turret.createTurret(lIllIIlllIllIll.plugin, lIllIIlllIlllIl, lIllIIlllIlIllI, lIllIIlllIllIll, lIllIIlllIllIll.obtenirEquip(lIllIIlllIlIllI), lIIIIllI[0], lIIIIllI[0]);
                Turret lIllIIlllIllllI = Turret.getAdmin(lIllIIlllIllIll, lIllIIlllIllIIl);
                if (Torres.lllllllll((Object)lIllIIlllIllllI)) {
                    lIllIIlllIllllI.updateChildStats();
                    lIllIIlllIlllll.linkCreador = lIIIIllI[1];
                }
                lIllIIlllIlllll.setHp((int)(15.0 * lIllIIlllIllIll.getBalancingMultiplier(lIllIIlllIlIllI)));
                lIllIIlllIlllll.Build();
                lIllIIlllIlllll.Attack();
                if (Torres.llllllIll((int)lIllIIlllIlllll.built.booleanValue()) && Torres.lllllllIl((int)lIllIIlllIllIll.debug)) {
                    ItemStack lIllIIllllIIIII = lIllIIlllIllIII.clone();
                    lIllIIllllIIIII.setAmount(lIIIIllI[1]);
                    ItemStack[] arritemStack = new ItemStack[lIIIIllI[1]];
                    arritemStack[Torres.lIIIIllI[0]] = lIllIIllllIIIII;
                    "".length();
                    lIllIIlllIlIlll.removeItem(arritemStack);
                }
            }
            if (Torres.lllllllII((Object)lIllIIlllIlllII.getType(), (Object)Material.BEACON)) {
                lIllIIlllIlllII.setType(Material.LAVA);
                lIllIIlllIllIll.world.playSound(lIllIIlllIllIll.getHalfwayMiddle(), Sound.ENTITY_ENDERDRAGON_HURT, 150.0f, 1.1f);
                lIllIIlllIllIll.sendGlobalMessage(llllllI[lIIIIllI[34]]);
                lIllIIlllIllIll.givePoints(lIllIIlllIllIIl, lIIIIllI[14]);
                lIllIIlllIllIll.comprovarGuanyador();
            }
        }
    }

    private static boolean lIIIIIIIll(Object object, Object object2) {
        return object != object2;
    }

    private static int lIIIIIlIlI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    protected void donarEfectesInicials(Player lIllIlIlIllIIIl) {
        Torres lIllIlIlIllIIlI;
        super.donarEfectesInicials(lIllIlIlIllIIIl);
        int lIllIlIlIllIIll = (int)(20.0 * (5.0 + Math.sqrt(lIllIlIlIllIIlI.segonsTranscorreguts()) / 9.0));
        "".length();
        lIllIlIlIllIIIl.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, lIIIIllI[4] + lIllIlIlIllIIll, lIIIIllI[5], lIIIIllI[0]), lIIIIllI[1]);
        "".length();
        lIllIlIlIllIIIl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, lIIIIllI[6], lIIIIllI[2], lIIIIllI[0]), lIIIIllI[1]);
        "".length();
        lIllIlIlIllIIIl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIllIlIlIllIIll, lIIIIllI[7], lIIIIllI[0]), lIIIIllI[1]);
        "".length();
        lIllIlIlIllIIIl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, lIllIlIlIllIIll, lIIIIllI[8], lIIIIllI[0]), lIIIIllI[1]);
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent lIllIIlIllIIIll, Block lIllIIlIllIIllI) {
        Torres lIllIIlIllIIlII;
        super.onBlockPlace(lIllIIlIllIIIll, lIllIIlIllIIllI);
        int lIllIIlIllIIlIl = lIllIIlIllIIlII.Equips.stream().anyMatch(lIllIIlIlIllIII -> {
            Torres lIllIIlIlIllIlI;
            return lIllIIlIlIllIlI.getInhibitors((JocEquips.Equip)lIllIIlIlIllIII).stream().anyMatch(lIllIIlIlIlIIlI -> {
                boolean bl;
                if (Torres.lIIIIlIlII(Torres.lIIIIlIIlI(lIllIIlIlIlllII.getLocation().distance(lIllIIlIlIlIIlI), 36.0))) {
                    bl = lIIIIllI[1];
                    "".length();
                    if (null != null) {
                        return (boolean)((109 ^ 71) & ~(172 ^ 134));
                    }
                } else {
                    bl = lIIIIllI[0];
                }
                return bl;
            });
        });
        if (Torres.lllllllII((Object)lIllIIlIllIIllI.getType(), (Object)Material.TNT) && Torres.lllllllIl(lIllIIlIllIIlIl)) {
            lIllIIlIllIIIll.setCancelled(lIIIIllI[0]);
        }
        if (Torres.lllllllIl((int)lIllIIlIllIIIll.isCancelled())) {
            lIllIIlIllIIlII.givePoints(lIllIIlIllIIIll.getPlayer(), lIIIIllI[1]);
        }
    }

    private static boolean llllllIll(int n) {
        return n != 0;
    }

    @Override
    protected void setCustomGameRules() {
        Torres lIllIlIlIlllIlI;
        "".length();
        lIllIlIlIlllIlI.world.setGameRuleValue(llllllI[lIIIIllI[2]], llllllI[lIIIIllI[3]]);
    }

    protected void onPlayerFish(PlayerFishEvent lIllIIllIIIllll, Player lIllIIllIIlIIll) {
        Torres lIllIIllIIlIIII;
        super.onPlayerFish(lIllIIllIIIllll, lIllIIllIIlIIll);
        Player lIllIIllIIlIIlI = lIllIIllIIIllll.getPlayer();
        Entity lIllIIllIIlIIIl = lIllIIllIIIllll.getCaught();
        if (Torres.lllllllII((Object)lIllIIllIIlIIlI.getItemInHand().getType(), (Object)Material.FISHING_ROD)) {
            ArrayList<Player> lIllIIllIIllIIl;
            Location lIllIIllIIlIllI = lIllIIllIIlIIlI.getLocation().clone();
            if (Torres.lIIIIIIlll((Object)lIllIIllIIlIIIl) && Torres.lIIIIIllIl((lIllIIllIIllIIl = Utils.getNearbyPlayers((Entity)lIllIIllIIIllll.getHook(), 3.0)).size())) {
                lIllIIllIIlIIIl = (Entity)lIllIIllIIllIIl.get(lIIIIllI[0]);
            }
            if (Torres.lllllllll((Object)lIllIIllIIlIIIl) && Torres.llllllIll(lIllIIllIIlIIIl instanceof LivingEntity)) {
                LivingEntity lIllIIllIIllIII = (LivingEntity)lIllIIllIIlIIIl;
                Location lIllIIllIIlIlll = lIllIIllIIlIIIl.getLocation().clone();
                "".length();
                lIllIIllIIlIIlI.teleport(lIllIIllIIlIlll);
                "".length();
                lIllIIllIIlIIIl.teleport(lIllIIllIIlIllI);
                "".length();
                lIllIIllIIllIII.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIIIIllI[36], lIIIIllI[2]));
                lIllIIllIIlIIII.world.playEffect(lIllIIllIIllIII.getEyeLocation(), Effect.WITCH_MAGIC, lIIIIllI[0]);
            }
            lIllIIllIIlIIII.world.playEffect(lIllIIllIIIllll.getHook().getLocation(), Effect.VILLAGER_THUNDERCLOUD, lIIIIllI[0]);
        }
    }

    protected void comprovarGuanyador() {
        Torres lIllIlIlIIIIlIl;
        Iterator lIllIlIlIIIIlII = lIllIlIlIIIIlIl.Equips.iterator();
        while (Torres.llllllIll((int)lIllIlIlIIIIlII.hasNext())) {
            JocEquips.Equip lIllIlIlIIIIlll = (JocEquips.Equip)lIllIlIlIIIIlII.next();
            int lIllIlIlIIIlIII = lIIIIllI[0];
            Iterator<Location> lIllIlIlIIIIIIl = lIllIlIlIIIIlIl.getInhibitors(lIllIlIlIIIIlll).iterator();
            while (Torres.llllllIll((int)lIllIlIlIIIIIIl.hasNext())) {
                Location lIllIlIlIIIlIIl = lIllIlIlIIIIIIl.next();
                if (Torres.lllllllII((Object)lIllIlIlIIIlIIl.getBlock().getType(), (Object)Material.BEACON)) {
                    lIllIlIlIIIlIII = lIIIIllI[1];
                }
                "".length();
                if (((14 ^ 97 ^ (101 ^ 70)) & (86 ^ 15 ^ (69 ^ 80) ^ -" ".length())) == 0) continue;
                return;
            }
            if (Torres.lllllllIl(lIllIlIlIIIlIII)) {
                lIllIlIlIIIIlIl.winGame(lIllIlIlIIIIlIl.obtenirEquipEnemic(lIllIlIlIIIIlll));
            }
            "".length();
            if (-" ".length() <= "   ".length()) continue;
            return;
        }
    }

    public void GenerarBonus(String lIllIlIIlIIIlll, TipusBonus lIllIlIIlIIIIlI) {
        Torres lIllIlIIlIIIlII;
        ArrayList<Location> lIllIlIIlIIIlIl = lIllIlIIlIIIlII.pMapaActual().ObtenirLocations(lIllIlIIlIIIlll, lIllIlIIlIIIlII.world);
        Iterator<Location> lIllIlIIlIIIIII = lIllIlIIlIIIlIl.iterator();
        while (Torres.llllllIll((int)lIllIlIIlIIIIII.hasNext())) {
            Location lIllIlIIlIIlIIl = lIllIlIIlIIIIII.next();
            Bonus lIllIlIIIlllllI = lIllIlIIlIIIlII.new Bonus(Com.getPlugin(), lIllIlIIlIIIIlI, lIllIlIIlIIlIIl, lIIIIllI[0], lIIIIllI[28]);
            "".length();
            if (" ".length() == " ".length()) continue;
            return;
        }
    }

    private static boolean lIIIIIlIII(int n, int n2) {
        return n >= n2;
    }

    private static String lllIIIIII(String lIllIIIllIllIll, String lIllIIIllIllIII) {
        try {
            SecretKeySpec lIllIIIllIllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIIIllIllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIllIIIllIlllIl = Cipher.getInstance("Blowfish");
            lIllIIIllIlllIl.init(lIIIIllI[2], lIllIIIllIllllI);
            return new String(lIllIIIllIlllIl.doFinal(Base64.getDecoder().decode(lIllIIIllIllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIIIllIlllII) {
            lIllIIIllIlllII.printStackTrace();
            return null;
        }
    }

    protected void onEntityExplode(EntityExplodeEvent lIllIIlIllllIlI, Entity lIllIIlIlllllII) {
        Torres lIllIIlIllllIll;
        super.onEntityExplode(lIllIIlIllllIlI, lIllIIlIlllllII);
        if (Torres.lllllllII((Object)lIllIIlIllllIlI.getEntityType(), (Object)EntityType.ENDER_CRYSTAL)) {
            EnderCrystal lIllIIllIIIIIlI = (EnderCrystal)lIllIIlIllllIlI.getEntity();
            lIllIIlIllllIlI.setYield(0.0f);
            lIllIIlIllllIll.JocFinalitzat();
        }
        if (Torres.lllllllII((Object)lIllIIlIllllIlI.getEntityType(), (Object)EntityType.PRIMED_TNT)) {
            Iterator lIllIIlIlllllll = lIllIIlIllllIlI.blockList().iterator();
            while (Torres.llllllIll((int)lIllIIlIlllllll.hasNext())) {
                Block lIllIIllIIIIIIl = (Block)lIllIIlIlllllll.next();
                Material lIllIIllIIIIIII = lIllIIllIIIIIIl.getType();
                if (!Torres.lIIIIIIIll((Object)lIllIIllIIIIIII, (Object)Material.GRASS) || !Torres.lIIIIIIIll((Object)lIllIIllIIIIIII, (Object)Material.DIRT) || Torres.lllllllII((Object)lIllIIllIIIIIII, (Object)Material.GRAVEL)) {
                    lIllIIlIlllllll.remove();
                }
                "".length();
                if (((44 ^ 73 ^ (1 ^ 111)) & (86 ^ 21 ^ (90 ^ 18) ^ -" ".length())) == 0) continue;
                return;
            }
            lIllIIlIllllIll.getPlayers().stream().filter(lIllIIlIlIIlIll -> {
                boolean bl;
                if (Torres.lIIIIlIlII(Torres.lIIIIlIlIl(lIllIIlIlIIlIll.getLocation().distance(lIllIIlIlllllII.getLocation()), 12.0))) {
                    bl = lIIIIllI[1];
                    "".length();
                    if ("  ".length() <= -" ".length()) {
                        return (boolean)((128 ^ 144 ^ (117 ^ 43)) & (224 ^ 191 ^ (136 ^ 153) ^ -" ".length()));
                    }
                } else {
                    bl = lIIIIllI[0];
                }
                return bl;
            }).forEach(lIllIIlIlIlIIII -> {
                lIllIIlIlIlIIII.damage(10.0);
                "".length();
                lIllIIlIlIlIIII.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, lIIIIllI[27], lIIIIllI[1]));
            });
        }
    }

    @Override
    public void heartbeat() {
        Torres lIllIlIIIllIllI;
        super.heartbeat();
        ArrayList<Turret> lIllIlIIIllIlll = new ArrayList<Turret>();
        Iterator<Turret> lIllIlIIIllIlII = lIllIlIIIllIllI.Turrets.iterator();
        while (Torres.llllllIll((int)lIllIlIIIllIlII.hasNext())) {
            Turret lIllIlIIIlllIIl = lIllIlIIIllIlII.next();
            if (Torres.llllllIll((int)lIllIlIIIlllIIl.destroyed)) {
                "".length();
                lIllIlIIIllIlll.add(lIllIlIIIlllIIl);
            }
            "".length();
            if (-" ".length() <= (62 ^ 58)) continue;
            return;
        }
        "".length();
        lIllIlIIIllIllI.Turrets.removeAll(lIllIlIIIllIlll);
    }

    @Override
    public String getGameName() {
        return llllllI[lIIIIllI[5]];
    }

    private static String llIllIIII(String lIllIIIllllIIII, String lIllIIIlllIlIlI) {
        lIllIIIllllIIII = new String(Base64.getDecoder().decode(lIllIIIllllIIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIllIIIlllIlllI = new StringBuilder();
        char[] lIllIIIlllIllIl = lIllIIIlllIlIlI.toCharArray();
        int lIllIIIlllIllII = lIIIIllI[0];
        char[] lIllIIIlllIIllI = lIllIIIllllIIII.toCharArray();
        int lIllIIIlllIIlIl = lIllIIIlllIIllI.length;
        int lIllIIIlllIIlII = lIIIIllI[0];
        while (Torres.llllllllI(lIllIIIlllIIlII, lIllIIIlllIIlIl)) {
            char lIllIIIllllIIIl = lIllIIIlllIIllI[lIllIIIlllIIlII];
            "".length();
            lIllIIIlllIlllI.append((char)(lIllIIIllllIIIl ^ lIllIIIlllIllIl[lIllIIIlllIllII % lIllIIIlllIllIl.length]));
            ++lIllIIIlllIllII;
            ++lIllIIIlllIIlII;
            "".length();
            if ("   ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(lIllIIIlllIlllI);
    }

    private static void lllllIllI() {
        lIIIIllI = new int[44];
        Torres.lIIIIllI[0] = (150 ^ 187) & ~(139 ^ 166);
        Torres.lIIIIllI[1] = " ".length();
        Torres.lIIIIllI[2] = "  ".length();
        Torres.lIIIIllI[3] = "   ".length();
        Torres.lIIIIllI[4] = 69 ^ 78 ^ (221 ^ 134);
        Torres.lIIIIllI[5] = 137 ^ 135 ^ (73 ^ 67);
        Torres.lIIIIllI[6] = 79 + 70 - -39 + 12;
        Torres.lIIIIllI[7] = 20 + 118 - -40 + 5 ^ 78 + 94 - 54 + 15;
        Torres.lIIIIllI[8] = 220 ^ 166 ^ 94 + 6 - 44 + 71;
        Torres.lIIIIllI[9] = 203 ^ 194 ^ (102 ^ 23);
        Torres.lIIIIllI[10] = (24 ^ 85) + (41 ^ 55) - (209 ^ 187) + (10 + 121 - 99 + 107);
        Torres.lIIIIllI[11] = 126 ^ 66;
        Torres.lIIIIllI[12] = 52 + 169 - 149 + 98 ^ 160 + 134 - 278 + 164;
        Torres.lIIIIllI[13] = 56 ^ 78 ^ (30 ^ 50);
        Torres.lIIIIllI[14] = 96 ^ 32 ^ (48 ^ 122);
        Torres.lIIIIllI[15] = 22 + 108 - 5 + 67 ^ 69 + 56 - -64 + 9;
        Torres.lIIIIllI[16] = 191 ^ 154 ^ (94 ^ 124);
        Torres.lIIIIllI[17] = 207 ^ 159 ^ (75 ^ 19);
        Torres.lIIIIllI[18] = 118 + 6 - 40 + 63 ^ 0 + 61 - -2 + 91;
        Torres.lIIIIllI[19] = 88 ^ 83;
        Torres.lIIIIllI[20] = 13 ^ 50 ^ (127 ^ 76);
        Torres.lIIIIllI[21] = -75 & 900074;
        Torres.lIIIIllI[22] = 166 ^ 171;
        Torres.lIIIIllI[23] = 36 + 92 - -40 + 2 ^ 61 + 101 - 60 + 62;
        Torres.lIIIIllI[24] = 28 ^ 19;
        Torres.lIIIIllI[25] = "  ".length() ^ (62 ^ 44);
        Torres.lIIIIllI[26] = 16 ^ 1;
        Torres.lIIIIllI[27] = 156 ^ 170 ^ (84 ^ 118);
        Torres.lIIIIllI[28] = -17623 & 18422;
        Torres.lIIIIllI[29] = 27 ^ 73 ^ (32 ^ 96);
        Torres.lIIIIllI[30] = 190 ^ 173;
        Torres.lIIIIllI[31] = 126 ^ 107;
        Torres.lIIIIllI[32] = 34 ^ 100 ^ (21 ^ 112);
        Torres.lIIIIllI[33] = -" ".length();
        Torres.lIIIIllI[34] = 83 ^ 69;
        Torres.lIIIIllI[35] = 23 ^ 78 ^ (85 ^ 76);
        Torres.lIIIIllI[36] = 2 ^ 102;
        Torres.lIIIIllI[37] = 96 + 65 - 84 + 62 ^ 118 + 92 - 193 + 139;
        Torres.lIIIIllI[38] = -"   ".length();
        Torres.lIIIIllI[39] = 189 ^ 165;
        Torres.lIIIIllI[40] = 175 ^ 182;
        Torres.lIIIIllI[41] = 73 ^ 83;
        Torres.lIIIIllI[42] = 10 ^ 17;
        Torres.lIIIIllI[43] = 124 + 70 - 120 + 116 ^ 99 + 148 - 220 + 135;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIllIlIlIIllIIl) {
        Torres lIllIlIlIIllIlI;
        ItemStack itemStack;
        ItemStack itemStack2;
        ItemStack itemStack3;
        Material material;
        ArrayList<ItemStack> lIllIlIlIlIIIlI = new ArrayList<ItemStack>();
        JocEquips.Equip lIllIlIlIlIIIIl = lIllIlIlIIllIlI.obtenirEquip(lIllIlIlIIllIIl);
        Joc.PlayerInfo lIllIlIlIlIIIII = lIllIlIlIIllIlI.getPlayerInfo(lIllIlIlIIllIIl);
        int lIllIlIlIIlllll = lIllIlIlIlIIIII.getValue();
        if (Torres.llllllIII(lIllIlIlIIlllll, lIIIIllI[9])) {
            material = Material.DIAMOND_SWORD;
            "".length();
            if ((24 ^ 28) <= 0) {
                return null;
            }
        } else {
            material = Material.IRON_SWORD;
        }
        "".length();
        lIllIlIlIlIIIlI.add(new ItemStack(material, lIIIIllI[1]));
        if (Torres.llllllIII(lIllIlIlIIlllll, lIIIIllI[10])) {
            "".length();
            lIllIlIlIlIIIlI.add(lIllIlIlIIllIlI.getTurretItem());
        }
        if (Torres.llllllIII(lIllIlIlIIlllll, lIIIIllI[11])) {
            itemStack3 = new ItemStack(Material.DIAMOND_CHESTPLATE, lIIIIllI[1]);
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            itemStack3 = Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, lIllIlIlIlIIIIl);
        }
        "".length();
        lIllIlIlIlIIIlI.add(itemStack3);
        if (Torres.llllllIII(lIllIlIlIIlllll, lIIIIllI[12])) {
            itemStack2 = new ItemStack(Material.IRON_HELMET, lIIIIllI[1]);
            "".length();
            if (((83 ^ 59 ^ (110 ^ 15)) & (68 ^ 120 ^ (161 ^ 148) ^ -" ".length())) != 0) {
                return null;
            }
        } else {
            itemStack2 = Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lIllIlIlIlIIIIl);
        }
        "".length();
        lIllIlIlIlIIIlI.add(itemStack2);
        if (Torres.llllllIII(lIllIlIlIIlllll, lIIIIllI[13])) {
            itemStack = new ItemStack(Material.DIAMOND_BOOTS, lIIIIllI[1]);
            "".length();
            if (((38 ^ 124) & ~(38 ^ 124)) != 0) {
                return null;
            }
        } else {
            itemStack = Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, lIllIlIlIlIIIIl);
        }
        "".length();
        lIllIlIlIlIIIlI.add(itemStack);
        "".length();
        lIllIlIlIlIIIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lIllIlIlIlIIIIl));
        ItemStack lIllIlIlIIllllI = new ItemStack(Material.BOW, lIIIIllI[1]);
        lIllIlIlIIllllI.addEnchantment(Enchantment.ARROW_KNOCKBACK, lIIIIllI[1]);
        lIllIlIlIIllllI.addEnchantment(Enchantment.ARROW_INFINITE, lIIIIllI[1]);
        lIllIlIlIIllllI.addUnsafeEnchantment(Enchantment.DURABILITY, lIIIIllI[14]);
        ItemStack lIllIlIlIIlllIl = new ItemStack(Material.ENCHANTED_BOOK, lIIIIllI[1]);
        ItemMeta lIllIlIlIIlllII = lIllIlIlIIlllIl.getItemMeta();
        lIllIlIlIIlllII.setDisplayName(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(llllllI[lIIIIllI[8]])));
        ArrayList<String> lIllIlIlIIllIll = new ArrayList<String>();
        "".length();
        lIllIlIlIIllIll.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(llllllI[lIIIIllI[15]])));
        lIllIlIlIIlllII.setLore(lIllIlIlIIllIll);
        "".length();
        lIllIlIlIIlllIl.setItemMeta(lIllIlIlIIlllII);
        "".length();
        lIllIlIlIlIIIlI.add(lIllIlIlIIlllIl);
        "".length();
        lIllIlIlIlIIIlI.add(lIllIlIlIIllllI);
        "".length();
        lIllIlIlIlIIIlI.add(new ItemStack(Material.ARROW, lIIIIllI[1]));
        "".length();
        lIllIlIlIlIIIlI.add(new ItemStack(Material.FLINT_AND_STEEL, lIIIIllI[1]));
        return lIllIlIlIlIIIlI;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        Torres lIllIlIlIllllIl;
        ArrayList<JocEquips.Equip> lIllIlIlIlllllI = new ArrayList<JocEquips.Equip>();
        "".length();
        lIllIlIlIlllllI.add(lIllIlIlIllllIl.new JocEquips.Equip(DyeColor.RED, llllllI[lIIIIllI[0]]));
        "".length();
        lIllIlIlIlllllI.add(lIllIlIlIllllIl.new JocEquips.Equip(DyeColor.BLUE, llllllI[lIIIIllI[1]]));
        return lIllIlIlIlllllI;
    }

    private static boolean lIIIIIIlll(Object object) {
        return object == null;
    }

    private static int lIIIIlIIlI(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lIllIIllIlllIII, Player lIllIIllIllllIl) {
        ItemStack lIllIIllIlllIlI;
        int lIllIIllIlllIll;
        Torres lIllIIllIlllIIl;
        int n;
        ItemStack itemStack;
        super.onPlayerDeath(lIllIIllIlllIII, lIllIIllIllllIl);
        Player lIllIIllIllllII = lIllIIllIllllIl.getKiller();
        if (Torres.lIIIIIIlll((Object)lIllIIllIllllII)) {
            Double lIllIIlllIIIIII = -1.0;
            Iterator<Player> lIllIIllIllIlII = lIllIIllIlllIIl.getPlayers().iterator();
            while (Torres.llllllIll((int)lIllIIllIllIlII.hasNext())) {
                Player lIllIIlllIIIIIl = lIllIIllIllIlII.next();
                if (Torres.llllllIll((int)lIllIIllIlllIIl.areEnemies(lIllIIllIllllIl, lIllIIlllIIIIIl).booleanValue())) {
                    Double lIllIIlllIIIIlI = lIllIIlllIIIIIl.getLocation().distance(lIllIIllIllllIl.getLocation());
                    if (!Torres.lIIIIIllII(Torres.lIIIIIlIIl(lIllIIlllIIIIII, 0.0)) || Torres.lIIIIIllIl(Torres.lIIIIIlIlI(lIllIIlllIIIIII, lIllIIlllIIIIlI))) {
                        lIllIIlllIIIIII = lIllIIlllIIIIlI;
                        lIllIIllIllllII = lIllIIlllIIIIIl;
                    }
                }
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
        }
        if (Torres.llllllIll((int)lIllIIllIlllIIl.getTNTEnabled())) {
            n = lIIIIllI[35];
            "".length();
            if (((92 ^ 24) & ~(114 ^ 54)) < 0) {
                return;
            }
        } else {
            n = lIIIIllI[36];
        }
        if (Torres.llllllIll((int)GUtils.Possibilitat((int)(lIllIIllIlllIll = n)))) {
            itemStack = lIllIIllIlllIIl.getTurretItem();
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        } else {
            itemStack = lIllIIllIlllIlI = new ItemStack(Material.TNT, lIIIIllI[1]);
        }
        if (Torres.lllllllll((Object)lIllIIllIllllII) && Torres.lIIIIIIIll(lIllIIllIlllIIl.obtenirEquip(lIllIIllIllllII), lIllIIllIlllIIl.obtenirEquip(lIllIIllIllllIl))) {
            ItemStack[] arritemStack = new ItemStack[lIIIIllI[1]];
            arritemStack[Torres.lIIIIllI[0]] = lIllIIllIlllIlI;
            "".length();
            lIllIIllIllllII.getInventory().addItem(arritemStack);
            lIllIIllIlllIIl.givePoints(lIllIIllIllllII, lIIIIllI[3]);
        }
    }

    void givePoints(Player lIllIIllllllIIl, int lIllIIllllllIII) {
        Torres lIllIIlllllIlIl;
        Joc.PlayerInfo lIllIIlllllIlll = lIllIIlllllIlIl.getPlayerInfo(lIllIIllllllIIl);
        int lIllIIlllllIllI = lIllIIlllllIlll.getValue() + lIllIIllllllIII;
        lIllIIlllllIlll.setValue(lIllIIlllllIllI);
        lIllIIllllllIIl.setLevel(lIllIIlllllIlll.getValue());
    }

    private static boolean lllllllIl(int n) {
        return n == 0;
    }

    private static boolean lllllllll(Object object) {
        return object != null;
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent lIllIIlIllIlllI, Block lIllIIlIllIllIl) {
        Torres lIllIIlIllIllll;
        super.onBlockBreak(lIllIIlIllIlllI, lIllIIlIllIllIl);
        if (Torres.lllllllII((Object)lIllIIlIllIllIl.getType(), (Object)Material.TNT)) {
            lIllIIlIllIlllI.setCancelled(lIIIIllI[0]);
            lIllIIlIllIlllI.getPlayer().damage(6.0);
        }
    }

    static {
        Torres.lllllIllI();
        Torres.lllIIlIll();
    }

    public static final class TurretPreset
    extends Enum<TurretPreset> {
        private static final /* synthetic */ String[] lIIllI;
        public static final /* synthetic */ /* enum */ TurretPreset NEXUS;
        private static final /* synthetic */ TurretPreset[] $VALUES;
        public static final /* synthetic */ /* enum */ TurretPreset INNER;
        public static final /* synthetic */ /* enum */ TurretPreset INHIBITOR;
        public static final /* synthetic */ /* enum */ TurretPreset LASER;
        public static final /* synthetic */ /* enum */ TurretPreset OUTER;
        private static final /* synthetic */ int[] lIlIll;

        private static void llIllll() {
            lIIllI = new String[lIlIll[5]];
            TurretPreset.lIIllI[TurretPreset.lIlIll[0]] = TurretPreset.llIIlII("Ij0MMhA=", "mhXwB");
            TurretPreset.lIIllI[TurretPreset.lIlIll[1]] = TurretPreset.llIIlIl("R6B26kPdcHQ=", "fiIGn");
            TurretPreset.lIIllI[TurretPreset.lIlIll[2]] = TurretPreset.llIIlIl("nX838Xr3MLVjbxbXHCKE+g==", "eAhOm");
            TurretPreset.lIIllI[TurretPreset.lIlIll[3]] = TurretPreset.llIllIl("zWUbFZthyB4=", "zpasp");
            TurretPreset.lIIllI[TurretPreset.lIlIll[4]] = TurretPreset.llIIlII("OBURKBc=", "tTBmE");
        }

        private static String llIIlIl(String llllIIIlIllllIl, String llllIIIlIlllllI) {
            try {
                SecretKeySpec llllIIIllIIIIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIIIlIlllllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llllIIIllIIIIIl = Cipher.getInstance("Blowfish");
                llllIIIllIIIIIl.init(lIlIll[2], llllIIIllIIIIlI);
                return new String(llllIIIllIIIIIl.doFinal(Base64.getDecoder().decode(llllIIIlIllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIIllIIIIII) {
                llllIIIllIIIIII.printStackTrace();
                return null;
            }
        }

        public static TurretPreset valueOf(String llllIIIllIIllll) {
            return Enum.valueOf(TurretPreset.class, llllIIIllIIllll);
        }

        private TurretPreset() {
            TurretPreset llllIIIllIIlIlI;
        }

        private static boolean lIIIIIlI(int n, int n2) {
            return n < n2;
        }

        private static String llIllIl(String llllIIIlIIllIII, String llllIIIlIIlIlll) {
            try {
                SecretKeySpec llllIIIlIIlllIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIIlIIlIlll.getBytes(StandardCharsets.UTF_8)), lIlIll[6]), "DES");
                Cipher llllIIIlIIlllII = Cipher.getInstance("DES");
                llllIIIlIIlllII.init(lIlIll[2], llllIIIlIIlllIl);
                return new String(llllIIIlIIlllII.doFinal(Base64.getDecoder().decode(llllIIIlIIllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIIlIIllIll) {
                llllIIIlIIllIll.printStackTrace();
                return null;
            }
        }

        public static TurretPreset[] values() {
            return (TurretPreset[])$VALUES.clone();
        }

        private static void lIIIIIIl() {
            lIlIll = new int[7];
            TurretPreset.lIlIll[0] = (197 ^ 164) & ~(32 ^ 65);
            TurretPreset.lIlIll[1] = " ".length();
            TurretPreset.lIlIll[2] = "  ".length();
            TurretPreset.lIlIll[3] = "   ".length();
            TurretPreset.lIlIll[4] = 105 ^ 84 ^ (123 ^ 66);
            TurretPreset.lIlIll[5] = 37 ^ 32;
            TurretPreset.lIlIll[6] = 29 ^ 10 ^ (157 ^ 130);
        }

        private static String llIIlII(String llllIIIlIlIlIlI, String llllIIIlIlIlllI) {
            llllIIIlIlIlIlI = new String(Base64.getDecoder().decode(llllIIIlIlIlIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllIIIlIlIllIl = new StringBuilder();
            char[] llllIIIlIlIllII = llllIIIlIlIlllI.toCharArray();
            int llllIIIlIlIlIll = lIlIll[0];
            char[] llllIIIlIlIIlIl = llllIIIlIlIlIlI.toCharArray();
            int llllIIIlIlIIlII = llllIIIlIlIIlIl.length;
            int llllIIIlIlIIIll = lIlIll[0];
            while (TurretPreset.lIIIIIlI(llllIIIlIlIIIll, llllIIIlIlIIlII)) {
                char llllIIIlIllIIII = llllIIIlIlIIlIl[llllIIIlIlIIIll];
                "".length();
                llllIIIlIlIllIl.append((char)(llllIIIlIllIIII ^ llllIIIlIlIllII[llllIIIlIlIlIll % llllIIIlIlIllII.length]));
                ++llllIIIlIlIlIll;
                ++llllIIIlIlIIIll;
                "".length();
                if (-" ".length() >= -" ".length()) continue;
                return null;
            }
            return String.valueOf(llllIIIlIlIllIl);
        }

        static {
            TurretPreset.lIIIIIIl();
            TurretPreset.llIllll();
            OUTER = new TurretPreset();
            INNER = new TurretPreset();
            INHIBITOR = new TurretPreset();
            NEXUS = new TurretPreset();
            LASER = new TurretPreset();
            TurretPreset[] arrturretPreset = new TurretPreset[lIlIll[5]];
            arrturretPreset[TurretPreset.lIlIll[0]] = OUTER;
            arrturretPreset[TurretPreset.lIlIll[1]] = INNER;
            arrturretPreset[TurretPreset.lIlIll[2]] = INHIBITOR;
            arrturretPreset[TurretPreset.lIlIll[3]] = NEXUS;
            arrturretPreset[TurretPreset.lIlIll[4]] = LASER;
            $VALUES = arrturretPreset;
        }
    }

    public class Bonus
    implements Listener {
        final /* synthetic */ TipusBonus tipus;
        /* synthetic */ int entityId;
        final /* synthetic */ int temps;
        private final /* synthetic */ lobby plugin;
        private static final /* synthetic */ int[] lIllI;
        final /* synthetic */ int for\u00e7a;
        /* synthetic */ Boolean predeterminat;
        final /* synthetic */ Location loc;

        static {
            Bonus.lIIIIlI();
        }

        private static boolean lIIlIII(int n, int n2) {
            return n >= n2;
        }

        void replaceItem() {
            Bonus llllIlllIIIIIll;
            "".length();
            Com.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Com.getPlugin(), () -> {
                Bonus llllIllIllIlIII;
                ItemStack llllIllIllIllII = new ItemStack(llllIllIllIlIII.getItem());
                Item llllIllIllIlIlI = llllIllIllIlIII.Torres.this.world.dropItem(llllIllIllIlIII.loc.clone().add(new Vector(0.5, 1.1, 0.5)), llllIllIllIllII);
                llllIllIllIlIlI.setVelocity(new Vector(lIllI[1], lIllI[1], lIllI[1]));
                llllIllIllIlIII.entityId = llllIllIllIlIlI.getEntityId();
            }, (long)llllIlllIIIIIll.temps);
        }

        public Bonus(lobby llllIllllIllIll, TipusBonus llllIllllIIlIll, Location llllIllllIlIlll, ItemStack llllIllllIlIlIl, int llllIllllIIIlll) {
            Bonus llllIllllIlllll;
            llllIllllIlllll.predeterminat = lIllI[1];
            llllIllllIlllll.tipus = llllIllllIIlIll;
            llllIllllIlllll.for\u00e7a = lIllI[1];
            llllIllllIlllll.temps = llllIllllIIIlll;
            llllIllllIlllll.loc = llllIllllIlIlll;
            llllIllllIlllll.plugin = llllIllllIllIll;
            llllIllllIllIll.getServer().getPluginManager().registerEvents((Listener)llllIllllIlllll, (Plugin)Com.getPlugin());
            llllIllllIlllll.Initialize();
        }

        public void Initialize() {
            Bonus llllIllllIIIIll;
            llllIllllIIIIll.loc.getBlock().setType(Material.FLOWER_POT);
            llllIllllIIIIll.replaceItem();
        }

        Material getItem() {
            Bonus llllIllIllllIII;
            switch (1.$SwitchMap$com$biel$lobby$mapes$jocs$Torres$TipusBonus[llllIllIllllIII.tipus.ordinal()]) {
                case 1: {
                    return Material.IRON_SWORD;
                }
                case 2: {
                    return Material.EMERALD;
                }
                case 3: {
                    return Material.FEATHER;
                }
                case 4: {
                    if (Bonus.lIIIlIl(llllIllIllllIII.for\u00e7a, lIllI[7])) {
                        return Material.DIAMOND_CHESTPLATE;
                    }
                    return Material.GOLD_CHESTPLATE;
                }
                case 5: {
                    if (Bonus.lIIlIII(llllIllIllllIII.for\u00e7a, lIllI[7])) {
                        return Material.IRON_PLATE;
                    }
                    return Material.WOOD_PLATE;
                }
            }
            return Material.COBBLESTONE;
        }

        void setEffect(Player llllIlllIlIIIIl) {
            Bonus llllIlllIlIIlII;
            switch (1.$SwitchMap$com$biel$lobby$mapes$jocs$Torres$TipusBonus[llllIlllIlIIlII.tipus.ordinal()]) {
                case 1: {
                    "".length();
                    llllIlllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, lIllI[2], lIllI[1], lIllI[1]), lIllI[0]);
                    "".length();
                    if (-" ".length() < "   ".length()) break;
                    return;
                }
                case 2: {
                    Utils.healDamageable((Damageable)llllIlllIlIIIIl, llllIlllIlIIIIl.getMaxHealth() * 0.6);
                    llllIlllIlIIIIl.setFoodLevel(lIllI[3]);
                    "".length();
                    llllIlllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIllI[4], lIllI[0], lIllI[1]), lIllI[0]);
                    "".length();
                    if (null == null) break;
                    return;
                }
                case 3: {
                    "".length();
                    llllIlllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIllI[5], lIllI[6], lIllI[1]), lIllI[0]);
                    "".length();
                    if (" ".length() != 0) break;
                    return;
                }
                case 4: {
                    if (Bonus.lIIIlIl(llllIlllIlIIlII.for\u00e7a, lIllI[7])) {
                        "".length();
                        llllIlllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, lIllI[8], lIllI[7], lIllI[1]), lIllI[0]);
                        "".length();
                        if ("   ".length() >= 0) break;
                        return;
                    }
                    "".length();
                    llllIlllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, lIllI[9], lIllI[0], lIllI[1]), lIllI[0]);
                    "".length();
                    if ("   ".length() >= 0) break;
                    return;
                }
                case 5: {
                    "".length();
                    llllIlllIlIIIIl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, lIllI[10], lIllI[11], lIllI[1]), lIllI[0]);
                    "".length();
                    if ("   ".length() != " ".length()) break;
                    return;
                }
            }
        }

        @EventHandler(priority=EventPriority.HIGH)
        public void onPickup(PlayerPickupItemEvent llllIlllIIIllll) {
            Bonus llllIlllIIlIlII;
            if (Bonus.lIIIlIl(llllIlllIIIllll.getItem().getEntityId(), llllIlllIIlIlII.entityId)) {
                llllIlllIIlIlII.setEffect(llllIlllIIIllll.getPlayer());
                llllIlllIIIllll.setCancelled(lIllI[0]);
                llllIlllIIIllll.getItem().remove();
                llllIlllIIlIlII.replaceItem();
            }
        }

        @EventHandler(priority=EventPriority.HIGH)
        public void onDespawn(ItemDespawnEvent llllIlllIIIlIll) {
            Bonus llllIlllIIIlIlI;
            if (Bonus.lIIIlIl(llllIlllIIIlIll.getEntity().getEntityId(), llllIlllIIIlIlI.entityId)) {
                llllIlllIIIlIll.setCancelled(lIllI[0]);
            }
        }

        private static void lIIIIlI() {
            lIllI = new int[12];
            Bonus.lIllI[0] = " ".length();
            Bonus.lIllI[1] = (197 ^ 146) & ~(196 ^ 147);
            Bonus.lIllI[2] = -(-25507 & 28647) & (-12305 & 16124);
            Bonus.lIllI[3] = 111 ^ 123;
            Bonus.lIllI[4] = 100 + 81 - 54 + 17 ^ 62 + 63 - -5 + 54;
            Bonus.lIllI[5] = -24078 & 24477;
            Bonus.lIllI[6] = "  ".length();
            Bonus.lIllI[7] = "   ".length();
            Bonus.lIllI[8] = -4101 & 5500;
            Bonus.lIllI[9] = -(-17929 & 24334) & (-8193 & 15357);
            Bonus.lIllI[10] = 165 + 67 - 121 + 134 ^ 60 + 13 - 37 + 105;
            Bonus.lIllI[11] = 191 ^ 187;
        }

        private static boolean lIIIlIl(int n, int n2) {
            return n == n2;
        }

        public Bonus(lobby llllIllllllIllI, TipusBonus lllllIIIIIIIIII, Location llllIllllllIIlI, int llllIllllllllIl, int llllIlllllllIll) {
            Bonus llllIlllllllIIl;
            llllIlllllllIIl.predeterminat = lIllI[0];
            llllIlllllllIIl.tipus = lllllIIIIIIIIII;
            llllIlllllllIIl.for\u00e7a = llllIllllllllIl;
            llllIlllllllIIl.temps = llllIlllllllIll;
            llllIlllllllIIl.loc = llllIllllllIIlI;
            llllIlllllllIIl.plugin = llllIllllllIllI;
            llllIllllllIllI.getServer().getPluginManager().registerEvents((Listener)llllIlllllllIIl, (Plugin)Com.getPlugin());
            llllIlllllllIIl.Initialize();
        }
    }

    public static final class TipusBonus
    extends Enum<TipusBonus> {
        public static final /* synthetic */ /* enum */ TipusBonus DAMAGE;
        private static final /* synthetic */ String[] lIlIIIII;
        public static final /* synthetic */ /* enum */ TipusBonus HEAL;
        private static final /* synthetic */ int[] lIlIIlIl;
        private static final /* synthetic */ TipusBonus[] $VALUES;
        public static final /* synthetic */ /* enum */ TipusBonus JUMP;
        public static final /* synthetic */ /* enum */ TipusBonus SPEED;
        public static final /* synthetic */ /* enum */ TipusBonus PROTECTION;

        static {
            TipusBonus.lIlIIIlIlI();
            TipusBonus.lIIllllIII();
            HEAL = new TipusBonus();
            SPEED = new TipusBonus();
            DAMAGE = new TipusBonus();
            PROTECTION = new TipusBonus();
            JUMP = new TipusBonus();
            TipusBonus[] arrtipusBonus = new TipusBonus[lIlIIlIl[5]];
            arrtipusBonus[TipusBonus.lIlIIlIl[0]] = HEAL;
            arrtipusBonus[TipusBonus.lIlIIlIl[1]] = SPEED;
            arrtipusBonus[TipusBonus.lIlIIlIl[2]] = DAMAGE;
            arrtipusBonus[TipusBonus.lIlIIlIl[3]] = PROTECTION;
            arrtipusBonus[TipusBonus.lIlIIlIl[4]] = JUMP;
            $VALUES = arrtipusBonus;
        }

        private static boolean lIlIIIlIll(int n, int n2) {
            return n < n2;
        }

        private static String lIIlllIlIl(String lIlIIlIllIIIlll, String lIlIIlIllIIIllI) {
            try {
                SecretKeySpec lIlIIlIllIIlIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIIlIllIIIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIlIIlIllIIlIIl = Cipher.getInstance("Blowfish");
                lIlIIlIllIIlIIl.init(lIlIIlIl[2], lIlIIlIllIIlIlI);
                return new String(lIlIIlIllIIlIIl.doFinal(Base64.getDecoder().decode(lIlIIlIllIIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIlIllIIlIII) {
                lIlIIlIllIIlIII.printStackTrace();
                return null;
            }
        }

        public static TipusBonus[] values() {
            return (TipusBonus[])$VALUES.clone();
        }

        private TipusBonus() {
            TipusBonus lIlIIlIllllIllI;
        }

        private static void lIlIIIlIlI() {
            lIlIIlIl = new int[7];
            TipusBonus.lIlIIlIl[0] = (140 + 6 - -43 + 22 ^ 48 + 17 - -50 + 39) & (16 + 64 - -50 + 71 ^ 106 + 123 - 131 + 30 ^ -" ".length());
            TipusBonus.lIlIIlIl[1] = " ".length();
            TipusBonus.lIlIIlIl[2] = "  ".length();
            TipusBonus.lIlIIlIl[3] = "   ".length();
            TipusBonus.lIlIIlIl[4] = 145 + 34 - 69 + 61 ^ 18 + 174 - 178 + 161;
            TipusBonus.lIlIIlIl[5] = 87 + 89 - 133 + 123 ^ 48 + 121 - 107 + 101;
            TipusBonus.lIlIIlIl[6] = 143 ^ 135;
        }

        public static TipusBonus valueOf(String lIlIIlIllllllII) {
            return Enum.valueOf(TipusBonus.class, lIlIIlIllllllII);
        }

        private static String lIIlllIlll(String lIlIIlIlllIIlII, String lIlIIlIlllIIIll) {
            lIlIIlIlllIIlII = new String(Base64.getDecoder().decode(lIlIIlIlllIIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIIlIlllIIlll = new StringBuilder();
            char[] lIlIIlIlllIIllI = lIlIIlIlllIIIll.toCharArray();
            int lIlIIlIlllIIlIl = lIlIIlIl[0];
            char[] lIlIIlIllIlllll = lIlIIlIlllIIlII.toCharArray();
            int lIlIIlIllIllllI = lIlIIlIllIlllll.length;
            int lIlIIlIllIlllIl = lIlIIlIl[0];
            while (TipusBonus.lIlIIIlIll(lIlIIlIllIlllIl, lIlIIlIllIllllI)) {
                char lIlIIlIlllIlIlI = lIlIIlIllIlllll[lIlIIlIllIlllIl];
                "".length();
                lIlIIlIlllIIlll.append((char)(lIlIIlIlllIlIlI ^ lIlIIlIlllIIllI[lIlIIlIlllIIlIl % lIlIIlIlllIIllI.length]));
                ++lIlIIlIlllIIlIl;
                ++lIlIIlIllIlllIl;
                "".length();
                if ("   ".length() <= (163 ^ 167)) continue;
                return null;
            }
            return String.valueOf(lIlIIlIlllIIlll);
        }

        private static String lIIlllIlII(String lIlIIlIllIlIIlI, String lIlIIlIllIlIIIl) {
            try {
                SecretKeySpec lIlIIlIllIlIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIlIllIlIIIl.getBytes(StandardCharsets.UTF_8)), lIlIIlIl[6]), "DES");
                Cipher lIlIIlIllIlIllI = Cipher.getInstance("DES");
                lIlIIlIllIlIllI.init(lIlIIlIl[2], lIlIIlIllIlIlll);
                return new String(lIlIIlIllIlIllI.doFinal(Base64.getDecoder().decode(lIlIIlIllIlIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIlIllIlIlIl) {
                lIlIIlIllIlIlIl.printStackTrace();
                return null;
            }
        }

        private static void lIIllllIII() {
            lIlIIIII = new String[lIlIIlIl[5]];
            TipusBonus.lIlIIIII[TipusBonus.lIlIIlIl[0]] = TipusBonus.lIIlllIlII("5xYC+u82nkM=", "dYMfa");
            TipusBonus.lIlIIIII[TipusBonus.lIlIIlIl[1]] = TipusBonus.lIIlllIlIl("laEWDoZbaWs=", "npsMi");
            TipusBonus.lIlIIIII[TipusBonus.lIlIIlIl[2]] = TipusBonus.lIIlllIlll("BjErOAMH", "BpfyD");
            TipusBonus.lIlIIIII[TipusBonus.lIlIIlIl[3]] = TipusBonus.lIIlllIlll("NgcgLAMlASY3CA==", "fUoxF");
            TipusBonus.lIlIIIII[TipusBonus.lIlIIlIl[4]] = TipusBonus.lIIlllIlll("MCwHBQ==", "zyJUI");
        }
    }

}

