/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  com.biel.BielAPI.events.EventBus
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.PistonMoveReaction
 *  org.bukkit.block.Sign
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.Potion
 *  org.bukkit.potion.PotionType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.BlockIterator
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.events.EventBus;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.jocs.Torres;
import com.biel.lobby.utilities.GestorPropietats;
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
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Turret
extends EventBus {
    public /* synthetic */ int VelAtac;
    public /* synthetic */ Boolean built;
    private static final /* synthetic */ String[] lIIllIII;
    public /* synthetic */ int maxHpEscut;
    final /* synthetic */ Player creador;
    public /* synthetic */ Boolean damaged;
    public /* synthetic */ int xp;
    /* synthetic */ Boolean headless;
    public /* synthetic */ Boolean linkCreador;
    /* synthetic */ String invString;
    public /* synthetic */ Boolean foc;
    public /* synthetic */ int distAtac;
    /* synthetic */ int tirs;
    final /* synthetic */ ArrayList<Location> TurretBlocks;
    /* synthetic */ int id;
    public /* synthetic */ int xpPerTir;
    public /* synthetic */ Boolean hasInventory;
    private /* synthetic */ int taskEscutId;
    private /* synthetic */ int taskId;
    final /* synthetic */ lobby plugin;
    final /* synthetic */ World world;
    private static final /* synthetic */ int[] lIIllllI;
    public /* synthetic */ boolean destroyed;
    final /* synthetic */ ArrayList<Location> ArmorBlocks;
    public /* synthetic */ Boolean isAdmin;
    /* synthetic */ JocEquips.Equip equip;
    public /* synthetic */ int tempsEscut;
    /* synthetic */ int hpEscut;
    /* synthetic */ int tirsquim;
    public /* synthetic */ int Atac;
    final /* synthetic */ Location location;
    private /* synthetic */ ArrayList<Millora> Millores;
    public /* synthetic */ int hp;
    public /* synthetic */ Boolean autoUpgrade;
    /* synthetic */ Torres joc;

    public Millora getByTipus(TipusMillora lIlIIllllIllllI) {
        Turret lIlIIllllIlllIl;
        Iterator<Millora> lIlIIllllIllIll = lIlIIllllIlllIl.Millores.iterator();
        while (Turret.lIIllIIllI((int)lIlIIllllIllIll.hasNext())) {
            Millora lIlIIlllllIIIII = lIlIIllllIllIll.next();
            if (Turret.lIIllIlIII((Object)lIlIIlllllIIIII.tipus, (Object)lIlIIllllIllllI)) {
                return lIlIIlllllIIIII;
            }
            "".length();
            if ((14 + 6 - -109 + 28 ^ 66 + 1 - -49 + 37) >= (99 ^ 88 ^ (124 ^ 67))) continue;
            return null;
        }
        return null;
    }

    int upgradeLvlSum() {
        Turret lIlIIIlIIlIIIII;
        int lIlIIIlIIlIIIIl = lIIllllI[0];
        Iterator<Millora> lIlIIIlIIIllllI = lIlIIIlIIlIIIII.Millores.iterator();
        while (Turret.lIIllIIllI((int)lIlIIIlIIIllllI.hasNext())) {
            Millora lIlIIIlIIlIIIll = lIlIIIlIIIllllI.next();
            lIlIIIlIIlIIIIl += lIlIIIlIIlIIIll.lvl;
            "".length();
            if (((113 ^ 75) & ~(69 ^ 127)) <= 0) continue;
            return (193 ^ 135) & ~(195 ^ 133);
        }
        return lIlIIIlIIlIIIIl;
    }

    public static Turret getAdmin(Torres lIlIlIIIIlIlIII, Player lIlIlIIIIlIIlIl) {
        Iterator<Turret> lIlIlIIIIlIIlII = Turret.getTurrets(lIlIlIIIIlIlIII).iterator();
        while (Turret.lIIllIIllI((int)lIlIlIIIIlIIlII.hasNext())) {
            Turret lIlIlIIIIlIlIIl = lIlIlIIIIlIIlII.next();
            if (Turret.lIIllIlIII((Object)lIlIlIIIIlIlIIl.creador, (Object)lIlIlIIIIlIIlIl) && Turret.lIIllIIlll((int)lIlIlIIIIlIlIIl.isAdmin.booleanValue(), lIIllllI[2])) {
                return lIlIlIIIIlIlIIl;
            }
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return null;
    }

    private static boolean lIIllIllll(int n, int n2) {
        return n > n2;
    }

    private static boolean lIIllIllII(int n, int n2) {
        return n < n2;
    }

    public void Build() {
        Turret lIlIIlllllllIll;
        if (Turret.lIIllIlIlI((int)lIlIIlllllllIll.headless.booleanValue())) {
            if (Turret.lIIllIlIlI((int)lIlIIlllllllIll.canBuild().booleanValue())) {
                lIlIIlllllllIll.built = lIIllllI[0];
                return;
            }
            Material lIlIIlllllllllI = Material.GOLD_BLOCK;
            if (Turret.lIIllIlIlI(lIlIIlllllllIll.equip.getId())) {
                lIlIIlllllllllI = Material.REDSTONE_BLOCK;
                "".length();
                if ("   ".length() < 0) {
                    return;
                }
            } else {
                lIlIIlllllllllI = Material.LAPIS_BLOCK;
            }
            lIlIIlllllllIll.TurretBlocks.clear();
            Location lIlIIllllllllIl = lIlIIlllllllIll.location.clone();
            lIlIIllllllllIl.getBlock().setType(lIlIIlllllllllI);
            "".length();
            lIlIIlllllllIll.TurretBlocks.add(lIlIIllllllllIl.clone());
            lIlIIllllllllIl.setY(lIlIIllllllllIl.getY() + 1.0);
            lIlIIllllllllIl.getBlock().setType(Material.NETHER_FENCE);
            "".length();
            lIlIIlllllllIll.TurretBlocks.add(lIlIIllllllllIl.clone());
            lIlIIllllllllIl.setY(lIlIIllllllllIl.getY() + 1.0);
            lIlIIllllllllIl.getBlock().setType(Material.REDSTONE_TORCH_ON);
            "".length();
            lIlIIlllllllIll.TurretBlocks.add(lIlIIllllllllIl.clone());
            lIlIIlllllllIll.resetArmorCD();
            lIlIIlllllllIll.built = lIIllllI[2];
        }
    }

    public int getHp() {
        Turret lIlIlIIIllllIll;
        return lIlIlIIIllllIll.hp;
    }

    void Hit(int lIlIIIlIIllIIlI) {
        Turret lIlIIIlIIllIllI;
        Location lIlIIIlIIllIlII = lIlIIIlIIllIllI.location.clone().add(new Vector((double)Utils.NombreEntre(lIIllllI[0], lIIllllI[2]) * 0.3, 1.1 + (double)Utils.NombreEntre(lIIllllI[0], lIIllllI[2]) * 0.3, (double)Utils.NombreEntre(lIIllllI[0], lIIllllI[2]) * 0.3));
        lIlIIIlIIllIllI.damaged = lIIllllI[2];
        if (Turret.lIIlllIIIl(lIlIIIlIIllIllI.hpEscut)) {
            lIlIIIlIIllIllI.hpEscut -= lIlIIIlIIllIIlI;
            Boolean lIlIIIlIIlllIII = lIlIIIlIIllIllI.CheckArmor();
            if (Turret.lIIllIlIlI((int)lIlIIIlIIlllIII.booleanValue())) {
                lIlIIIlIIllIllI.world.playSound(lIlIIIlIIllIlII, Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 3.0f, 1.0f);
                lIlIIIlIIllIllI.resetArmorCD();
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                lIlIIIlIIllIllI.world.playSound(lIlIIIlIIllIlII, Sound.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, 3.0f, 1.0f);
            }
            "".length();
            if ("   ".length() < "   ".length()) {
                return;
            }
        } else {
            lIlIIIlIIllIllI.hp -= lIlIIIlIIllIIlI;
            int lIlIIIlIIllIlll = Utils.NombreEntre(lIIllllI[4], lIIllllI[13]);
            lIlIIIlIIllIllI.world.playSound(lIlIIIlIIllIlII, Sound.ENTITY_PLAYER_HURT, 3.0f, 1.0f);
            while (Turret.lIIlllIIII(lIlIIIlIIllIlll)) {
                lIlIIIlIIllIllI.world.playEffect(lIlIIIlIIllIlII, Effect.SMOKE, Utils.NombreEntre(lIIllllI[0], lIIllllI[13]));
                lIlIIIlIIllIlll -= lIIllllI[2];
                "".length();
                if (((112 ^ 107) & ~(34 ^ 57)) == 0) continue;
                return;
            }
            lIlIIIlIIllIllI.resetArmorCD();
        }
        if (Turret.lIIllIlllI(lIlIIIlIIllIllI.hp)) {
            "".length();
            lIlIIIlIIllIllI.world.createExplosion(lIlIIIlIIllIlII.getX(), lIlIIIlIIllIlII.getY(), lIlIIIlIIllIlII.getZ(), 4.6f, lIIllllI[0], lIIllllI[0]);
            lIlIIIlIIllIllI.Stop();
            lIlIIIlIIllIllI.Destroy();
        }
    }

    private static void lIIllIIlIl() {
        lIIllllI = new int[23];
        Turret.lIIllllI[0] = (25 ^ 2 ^ (91 ^ 112)) & (65 + 16 - 17 + 84 ^ 103 + 80 - 141 + 122 ^ -" ".length());
        Turret.lIIllllI[1] = 144 + 154 - 254 + 111 ^ 5 + 140 - 53 + 77;
        Turret.lIIllllI[2] = " ".length();
        Turret.lIIllllI[3] = 20 ^ 2;
        Turret.lIIllllI[4] = "  ".length();
        Turret.lIIllllI[5] = 121 ^ 119;
        Turret.lIIllllI[6] = 109 ^ 43;
        Turret.lIIllllI[7] = 23 ^ 3;
        Turret.lIIllllI[8] = 147 ^ 153;
        Turret.lIIllllI[9] = "   ".length();
        Turret.lIIllllI[10] = "   ".length() ^ (151 ^ 144);
        Turret.lIIllllI[11] = 187 ^ 195 ^ (75 ^ 54);
        Turret.lIIllllI[12] = -" ".length();
        Turret.lIIllllI[13] = 7 ^ 32 ^ (156 ^ 179);
        Turret.lIIllllI[14] = 44 + 125 - 103 + 73 ^ 110 + 27 - 134 + 138;
        Turret.lIIllllI[15] = 52 ^ 28;
        Turret.lIIllllI[16] = 77 ^ 74;
        Turret.lIIllllI[17] = 181 ^ 137 ^ (155 ^ 174);
        Turret.lIIllllI[18] = 113 + 170 - 186 + 129 ^ 158 + 176 - 294 + 154;
        Turret.lIIllllI[19] = 62 + 37 - 94 + 139 ^ 49 + 112 - 118 + 112;
        Turret.lIIllllI[20] = -(-1000 & 26623) & (-2049 & 28671);
        Turret.lIIllllI[21] = 27 ^ 23;
        Turret.lIIllllI[22] = 164 ^ 169;
    }

    protected void onInventoryClick(InventoryClickEvent lIlIIIIlIIlIllI, Inventory lIlIIIIlIIlIlIl) {
        Turret lIlIIIIlIIlIlll;
        super.onInventoryClick(lIlIIIIlIIlIllI, lIlIIIIlIIlIlIl);
        if (Turret.lIIllIIllI((int)lIlIIIIlIIlIllI.getInventory().getName().equals(lIlIIIIlIIlIlll.invString)) && Turret.lIIllIllII(lIlIIIIlIIlIllI.getRawSlot(), lIIllllI[17]) && Turret.lIIllIllll(lIlIIIIlIIlIllI.getRawSlot(), lIIllllI[12])) {
            ItemStack lIlIIIIlIIllIIl = lIlIIIIlIIlIllI.getCurrentItem();
            ItemStack lIlIIIIlIIllIII = lIlIIIIlIIlIllI.getCursor();
            lIlIIIIlIIlIllI.setCancelled(lIIllllI[2]);
            if (Turret.lIIllIlIlI(lIlIIIIlIIllIII.getTypeId())) {
                Millora lIlIIIIlIIlllII;
                Location lIlIIIIlIIllIlI;
                Player lIlIIIIlIIllIll = (Player)lIlIIIIlIIlIllI.getWhoClicked();
                if (Turret.lIIllIIllI((int)lIlIIIIlIIlIlll.isAdmin.booleanValue())) {
                    Location lIlIIIIlIIlllIl = lIlIIIIlIIllIll.getLocation();
                    "".length();
                    if ((85 ^ 81) < (14 ^ 10)) {
                        return;
                    }
                } else {
                    lIlIIIIlIIllIlI = lIlIIIIlIIlIlll.joc.pTemp().ObtenirLocation(String.valueOf(new StringBuilder().append(lIIllIII[lIIllllI[22]]).append(lIlIIIIlIIllIll.getName())), lIlIIIIlIIlIlll.world);
                }
                if ((!Turret.lIIllIlIlI((int)lIlIIIIlIIlIlll.ContainsTurretBlock(lIlIIIIlIIllIlI).booleanValue()) || Turret.lIIllIIllI((int)lIlIIIIlIIlIlll.isAdmin.booleanValue())) && Turret.lIIllIIllI((int)(lIlIIIIlIIlllII = lIlIIIIlIIlIlll.Millores.get(lIlIIIIlIIlIllI.getRawSlot())).possibleUpgrade().booleanValue())) {
                    if (Turret.lIIllIlIII((Object)lIlIIIIlIIlIllI.getAction(), (Object)InventoryAction.PICKUP_ALL)) {
                        lIlIIIIlIIlllII.lvlUp();
                    }
                    if (Turret.lIIllIlIII((Object)lIlIIIIlIIlIllI.getAction(), (Object)InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                        lIlIIIIlIIlllII.upgradeMaximum();
                    }
                    if (Turret.lIIllIlIlI((int)lIlIIIIlIIlIlll.anyUpgradePossible().booleanValue())) {
                        lIlIIIIlIIllIll.closeInventory();
                        "".length();
                        if (" ".length() != " ".length()) {
                            return;
                        }
                    } else {
                        lIlIIIIlIIlIlll.openOrRefreshInventory(lIlIIIIlIIllIll);
                    }
                }
            }
        }
    }

    private static boolean lIIllIlllI(int n) {
        return n <= 0;
    }

    public Turret(lobby lIlIlIIIlIllIII, int lIlIlIIIlIlIlll, Location lIlIlIIIlIlIllI, Player lIlIlIIIlIllllI, Torres lIlIlIIIlIlllIl, JocEquips.Equip lIlIlIIIlIlllII, Boolean lIlIlIIIlIlIIlI, Boolean lIlIlIIIlIllIlI) {
        Turret lIlIlIIIllIIIlI;
        lIlIlIIIllIIIlI.id = lIIllllI[0];
        lIlIlIIIllIIIlI.joc = null;
        lIlIlIIIllIIIlI.headless = lIIllllI[0];
        lIlIlIIIllIIIlI.equip = null;
        lIlIlIIIllIIIlI.TurretBlocks = new ArrayList();
        lIlIlIIIllIIIlI.ArmorBlocks = new ArrayList();
        lIlIlIIIllIIIlI.Millores = new ArrayList();
        lIlIlIIIllIIIlI.tirs = lIIllllI[0];
        lIlIlIIIllIIIlI.tirsquim = lIIllllI[0];
        lIlIlIIIllIIIlI.xp = lIIllllI[0];
        lIlIlIIIllIIIlI.destroyed = lIIllllI[0];
        lIlIlIIIllIIIlI.hp = lIIllllI[1];
        lIlIlIIIllIIIlI.hpEscut = lIIllllI[0];
        lIlIlIIIllIIIlI.damaged = lIIllllI[0];
        lIlIlIIIllIIIlI.hasInventory = lIIllllI[2];
        lIlIlIIIllIIIlI.linkCreador = lIIllllI[0];
        lIlIlIIIllIIIlI.isAdmin = lIIllllI[0];
        lIlIlIIIllIIIlI.built = lIIllllI[0];
        lIlIlIIIllIIIlI.autoUpgrade = lIIllllI[0];
        lIlIlIIIllIIIlI.invString = lIIllIII[lIIllllI[0]];
        lIlIlIIIllIIIlI.VelAtac = lIIllllI[3];
        lIlIlIIIllIIIlI.Atac = lIIllllI[4];
        lIlIlIIIllIIIlI.distAtac = lIIllllI[5];
        lIlIlIIIllIIIlI.xpPerTir = lIIllllI[2];
        lIlIlIIIllIIIlI.maxHpEscut = lIIllllI[0];
        lIlIlIIIllIIIlI.tempsEscut = lIIllllI[6];
        lIlIlIIIllIIIlI.foc = lIIllllI[0];
        lIlIlIIIllIIIlI.plugin = lIlIlIIIlIllIII;
        lIlIlIIIllIIIlI.location = lIlIlIIIlIlIllI;
        lIlIlIIIllIIIlI.world = lIlIlIIIlIlIllI.getWorld();
        lIlIlIIIllIIIlI.creador = lIlIlIIIlIllllI;
        lIlIlIIIllIIIlI.joc = lIlIlIIIlIlllIl;
        lIlIlIIIllIIIlI.id = lIlIlIIIlIlIlll;
        lIlIlIIIllIIIlI.equip = lIlIlIIIlIlllII;
        lIlIlIIIllIIIlI.headless = lIlIlIIIlIlIIlI;
        lIlIlIIIllIIIlI.isAdmin = lIlIlIIIlIllIlI;
        lIlIlIIIllIIIlI.inicialitzarMillores();
    }

    public void PotionAttack(int lIlIIIllIIIIIll, int lIlIIIllIIIIIlI, int lIlIIIllIIIIlIl) {
        Location lIlIIIllIIlIllI;
        Turret lIlIIIllIIIlIII;
        if (Turret.lIIllIlIlI((int)lIlIIIllIIIlIII.built.booleanValue())) {
            return;
        }
        if (Turret.lIIllIlIlI(lIlIIIllIIIIIll)) {
            ArrayList<BlockFace> lIlIIIllIIlIIlI = new ArrayList<BlockFace>();
            "".length();
            lIlIIIllIIlIIlI.add(BlockFace.NORTH);
            "".length();
            lIlIIIllIIlIIlI.add(BlockFace.SOUTH);
            "".length();
            lIlIIIllIIlIIlI.add(BlockFace.WEST);
            "".length();
            lIlIIIllIIlIIlI.add(BlockFace.EAST);
            Iterator lIlIIIlIlllllll = lIlIIIllIIlIIlI.iterator();
            while (Turret.lIIllIIllI((int)lIlIIIlIlllllll.hasNext())) {
                BlockFace lIlIIIllIIlIIll = (BlockFace)lIlIIIlIlllllll.next();
                lIlIIIllIIlIllI = lIlIIIllIIIlIII.location.clone();
                int lIlIIIllIIlIlIl = lIIllllI[13] + lIlIIIllIIIIIlI;
                int lIlIIIllIIlIlII = lIIllllI[0];
                while (Turret.lIIlllIIll(lIlIIIllIIlIlII, lIlIIIllIIlIlIl)) {
                    Block lIlIIIllIIllIII = lIlIIIllIIlIllI.getBlock().getRelative(lIlIIIllIIlIIll);
                    Location lIlIIIllIIlIlll = lIlIIIllIIllIII.getLocation().clone();
                    lIlIIIllIIIlIII.tirarPoci\u00f3(lIlIIIllIIlIlll, PotionType.INSTANT_DAMAGE, lIIllllI[9] * lIlIIIllIIlIlII + lIIllllI[2] + lIlIIIllIIIIlIl);
                    lIlIIIllIIlIllI = lIlIIIllIIlIlll;
                    ++lIlIIIllIIlIlII;
                    "".length();
                    if (-(10 ^ 15) < 0) continue;
                    return;
                }
                "".length();
                if (-"  ".length() < 0) continue;
                return;
            }
        }
        if (Turret.lIIllIIlll(lIlIIIllIIIIIll, lIIllllI[2])) {
            int lIlIIIllIIlIIII = lIIllllI[14];
            int lIlIIIllIIIllll = lIIllllI[15] - lIlIIIllIIIIIlI;
            if (Turret.lIIlllIIll(lIlIIIllIIIllll, lIIllllI[13])) {
                lIlIIIllIIIllll = lIIllllI[13];
            }
            ArrayList<Location> lIlIIIllIIIlllI = Utils.getLocationsCircle(lIlIIIllIIIlIII.location.clone(), Double.valueOf(lIlIIIllIIlIIII), lIlIIIllIIIllll);
            lIlIIIllIIlIllI = lIlIIIllIIIlllI.iterator();
            while (Turret.lIIllIIllI((int)lIlIIIllIIlIllI.hasNext())) {
                Location lIlIIIllIIlIIIl = lIlIIIllIIlIllI.next();
                lIlIIIllIIIlIII.tirarPoci\u00f3(lIlIIIllIIlIIIl, PotionType.POISON, lIlIIIllIIIIlIl);
                "".length();
                if (" ".length() >= " ".length()) continue;
                return;
            }
        }
        if (Turret.lIIllIIlll(lIlIIIllIIIIIll, lIIllllI[4])) {
            int lIlIIIllIIIllII = lIIllllI[0];
            int lIlIIIllIIIlIll = lIIllllI[11];
            int lIlIIIllIIIlIlI = lIIllllI[15] - lIlIIIllIIIIIlI * lIIllllI[4];
            if (Turret.lIIlllIIll(lIlIIIllIIIlIlI, lIIllllI[11])) {
                lIlIIIllIIIlIlI = lIIllllI[11];
            }
            ArrayList<Location> lIlIIIllIIIlIIl = Utils.getLocationsCircle(lIlIIIllIIIlIII.location.clone(), Double.valueOf(lIlIIIllIIIlIll), lIlIIIllIIIlIlI);
            Iterator<Location> lIlIIIllIIlIIIl = lIlIIIllIIIlIIl.iterator();
            while (Turret.lIIllIIllI((int)lIlIIIllIIlIIIl.hasNext())) {
                Location lIlIIIllIIIllIl = lIlIIIllIIlIIIl.next();
                lIlIIIllIIIlIII.tirarPoci\u00f3(lIlIIIllIIIllIl, PotionType.WEAKNESS, lIlIIIllIIIIlIl + lIlIIIllIIIllII * lIIllllI[4]);
                ++lIlIIIllIIIllII;
                "".length();
                if (" ".length() <= "   ".length()) continue;
                return;
            }
        }
    }

    private static String lIIlIIIlII(String lIlIIIIIlllIlll, String lIlIIIIIlllIllI) {
        try {
            SecretKeySpec lIlIIIIIlllllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIIIIIlllIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIIIIIllllIll = Cipher.getInstance("Blowfish");
            lIlIIIIIllllIll.init(lIIllllI[4], lIlIIIIIlllllII);
            return new String(lIlIIIIIllllIll.doFinal(Base64.getDecoder().decode(lIlIIIIIlllIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIIIIIllllIlI) {
            lIlIIIIIllllIlI.printStackTrace();
            return null;
        }
    }

    private static boolean lIIlllIIII(int n) {
        return n >= 0;
    }

    public void Attack() {
        Turret lIlIIIlIlIIIllI;
        if (Turret.lIIllIlIlI((int)lIlIIIlIlIIIllI.built.booleanValue())) {
            return;
        }
        lIlIIIlIlIIIllI.taskId = lIlIIIlIlIIIllI.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)lIlIIIlIlIIIllI.plugin, new Runnable(){
            private static final /* synthetic */ String[] lIIlIllII;
            private static final /* synthetic */ int[] lIIlIllll;

            private static boolean lIIIlIlIIIl(Object object) {
                return object != null;
            }

            private static int lIIIlIllIII(double d, double d2) {
                return (int)(d DCMPG d2);
            }

            private static int lIIIlIlIlIl(double d, double d2) {
                return (int)(d DCMPG d2);
            }

            private static boolean lIIIlIlIllI(int n) {
                return n != 0;
            }

            private static boolean lIIIlIllIlI(int n, int n2) {
                return n <= n2;
            }

            private static boolean lIIIlIlIlll(int n) {
                return n < 0;
            }

            @Override
            public void run() {
                1 llIlIlllllllII;
                llIlIlllllllII.Turret.this.checkIntegrity();
                LivingEntity llIlIlllllllIl = llIlIlllllllII.getTarget();
                if (1.lIIIlIlIIIl((Object)llIlIlllllllIl)) {
                    if (1.lIIIlIlIIlI(llIlIlllllllII.Turret.this.tirs, lIIlIllll[0]) && 1.lIIIlIlIIll(llIlIlllllllII.Turret.this.getByTipus((TipusMillora)TipusMillora.MEC\u00c0NICA).lvl)) {
                        llIlIlllllllII.AtacEspecial();
                        llIlIlllllllII.Turret.this.tirs = lIIlIllll[1];
                        return;
                    }
                    if (1.lIIIlIlIIlI(llIlIlllllllII.Turret.this.tirsquim, lIIlIllll[2]) && 1.lIIIlIlIIll(llIlIlllllllII.Turret.this.getByTipus((TipusMillora)TipusMillora.QU\u00cdMICA).lvl)) {
                        int llIllIIIIIIlIl = llIlIlllllllII.Turret.this.getByTipus((TipusMillora)TipusMillora.QU\u00cdMICA).lvl;
                        llIlIlllllllII.Turret.this.randomPotionAttack((int)(1.0 + Math.rint(llIllIIIIIIlIl / lIIlIllll[2])), llIllIIIIIIlIl * lIIlIllll[2] + lIIlIllll[3]);
                        llIlIlllllllII.Turret.this.tirsquim = lIIlIllll[1];
                        return;
                    }
                    Location llIllIIIIIIlII = llIlIlllllllII.getTarget().getEyeLocation();
                    Location llIllIIIIIIIll = llIlIlllllllII.Turret.this.location.clone().add(new Location(llIlIlllllllII.Turret.this.world, 0.5, 2.6, 0.5));
                    Vector llIllIIIIIIIlI = llIllIIIIIIlII.toVector().subtract(llIllIIIIIIIll.toVector());
                    Vector llIllIIIIIIIIl = llIllIIIIIIIlI.normalize();
                    Vector llIllIIIIIIIII = new Vector(0.0, llIllIIIIIIIlI.length() / 40.0, 0.0);
                    "".length();
                    llIllIIIIIIIIl.add(llIllIIIIIIIII);
                    Arrow llIlIlllllllll = (Arrow)llIlIlllllllII.Turret.this.world.spawnEntity(llIllIIIIIIIll, EntityType.ARROW);
                    llIlIlllllllll.setShooter((ProjectileSource)llIlIlllllllII.Turret.this.creador);
                    if (1.lIIIlIlIlII((int)llIlIlllllllII.Turret.this.foc.booleanValue(), lIIlIllll[3])) {
                        llIlIlllllllll.setFireTicks(lIIlIllll[4]);
                        llIlIlllllllII.Turret.this.world.playEffect(llIllIIIIIIIll, Effect.MOBSPAWNER_FLAMES, lIIlIllll[1]);
                    }
                    llIlIlllllllll.setMetadata(lIIlIllII[lIIlIllll[1]], (MetadataValue)new FixedMetadataValue((Plugin)llIlIlllllllII.Turret.this.plugin, (Object)llIlIlllllllII.Turret.this.id));
                    llIlIlllllllll.setMetadata(lIIlIllII[lIIlIllll[3]], (MetadataValue)new FixedMetadataValue((Plugin)llIlIlllllllII.Turret.this.plugin, (Object)lIIlIllll[1]));
                    llIlIlllllllll.setBounce(lIIlIllll[1]);
                    llIlIlllllllll.setKnockbackStrength(lIIlIllll[1]);
                    llIlIlllllllll.setVelocity(llIllIIIIIIIIl.multiply(3.4));
                    llIlIlllllllII.Turret.this.world.playSound(llIllIIIIIIIll, Sound.ENTITY_IRONGOLEM_ATTACK, 1.0f, 0.3f);
                    llIlIlllllllII.Turret.this.Learn(llIlIlllllllII.Turret.this.xpPerTir);
                    llIlIlllllllII.Turret.this.tirs += lIIlIllll[3];
                    llIlIlllllllII.Turret.this.tirsquim += lIIlIllll[3];
                }
            }

            void AtacEspecial() {
                1 llIlIlllIIIIlI;
                Location llIlIlllIIIllI = llIlIlllIIIIlI.Turret.this.location.clone().add(0.0, 1.5, 0.0);
                int llIlIlllIIIlIl = lIIlIllll[1];
                int llIlIlllIIIlII = lIIlIllll[3] + llIlIlllIIIIlI.Turret.this.getByTipus((TipusMillora)TipusMillora.MEC\u00c0NICA).lvl;
                int llIlIlllIIIIll = lIIlIllll[5];
                while (1.lIIIlIllIIl(llIlIlllIIIlIl, llIlIlllIIIlII)) {
                    "".length();
                    llIlIlllIIIIlI.Turret.this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)llIlIlllIIIIlI.Turret.this.plugin, () -> {
                        1 llIlIllIlIlIll;
                        int llIlIllIlIllIl = lIIlIllll[1];
                        int llIlIllIlIllII = lIIlIllll[6] - llIlIllIlIlIll.Turret.this.getByTipus((TipusMillora)TipusMillora.MEC\u00c0NICA).lvl * lIIlIllll[7];
                        while (1.lIIIlIllIlI(llIlIllIlIllIl, lIIlIllll[8])) {
                            float llIlIllIllIlII = llIlIllIlIllIl;
                            double llIlIllIllIIll = 0.017453292519943295;
                            Location llIlIllIllIIlI = llIlIlllIIIllI.clone().add(new Location(llIlIllIlIlIll.Turret.this.world, Math.cos((double)llIlIllIllIlII * llIlIllIllIIll) + 0.5, 0.0, Math.sin((double)llIlIllIllIlII * llIlIllIllIIll) + 0.5));
                            Vector llIlIllIllIIIl = llIlIllIllIIlI.toVector().subtract(llIlIlllIIIllI.toVector()).normalize().multiply(0.5);
                            Arrow llIlIllIllIIII = (Arrow)llIlIllIlIlIll.Turret.this.world.spawnEntity(llIlIllIllIIlI, EntityType.ARROW);
                            llIlIllIllIIII.setMetadata(lIIlIllII[lIIlIllll[9]], (MetadataValue)new FixedMetadataValue((Plugin)llIlIllIlIlIll.Turret.this.plugin, (Object)llIlIllIlIlIll.Turret.this.id));
                            llIlIllIllIIII.setMetadata(lIIlIllII[lIIlIllll[2]], (MetadataValue)new FixedMetadataValue((Plugin)llIlIllIlIlIll.Turret.this.plugin, (Object)lIIlIllll[3]));
                            llIlIllIllIIII.setFireTicks(lIIlIllll[10]);
                            llIlIllIllIIII.setVelocity(llIlIllIllIIIl.multiply(lIIlIllll[11]));
                            llIlIllIlIllIl += llIlIllIlIllII;
                            "".length();
                            if (" ".length() >= 0) continue;
                            return;
                        }
                        llIlIllIlIlIll.Turret.this.world.playSound(llIlIlllIIIllI, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                    }, (long)(llIlIlllIIIIll * llIlIlllIIIlIl));
                    llIlIlllIIIlIl += lIIlIllll[3];
                    "".length();
                    if (-" ".length() < (74 ^ 78)) continue;
                    return;
                }
            }

            private static boolean lIIIlIlIIll(int n) {
                return n > 0;
            }

            LivingEntity getTarget() {
                1 llIlIllllIIlll;
                double llIlIllllIlIIl = llIlIllllIIlll.Turret.this.distAtac;
                LivingEntity llIlIllllIlIII = null;
                Iterator<LivingEntity> llIlIllllIIlII = llIlIllllIIlll.getTargets().iterator();
                while (1.lIIIlIlIllI((int)llIlIllllIIlII.hasNext())) {
                    double llIlIllllIllII;
                    Entity llIlIllllIlIll = (Entity)llIlIllllIIlII.next();
                    double llIlIllllIllIl = llIlIllllIIlll.Turret.this.location.getY() - llIlIllllIlIll.getLocation().getY();
                    if (1.lIIIlIlIlll(1.lIIIlIlIlIl(llIlIllllIllIl, 0.0))) {
                        llIlIllllIllIl = 0.0;
                    }
                    if (1.lIIIlIlIlll(1.lIIIlIlIlIl(llIlIllllIllII = llIlIllllIIlll.Turret.this.location.distance(llIlIllllIlIll.getLocation()), llIlIllllIlIIl + llIlIllllIllIl))) {
                        llIlIllllIlIIl = llIlIllllIllII;
                        llIlIllllIlIII = (LivingEntity)llIlIllllIlIll;
                    }
                    "".length();
                    if ((91 + 74 - 129 + 104 ^ 32 + 88 - 7 + 23) > "   ".length()) continue;
                    return null;
                }
                return llIlIllllIlIII;
            }

            private static void lIIIlIIlIlI() {
                lIIlIllII = new String[lIIlIllll[7]];
                1.lIIlIllII[1.lIIlIllll[1]] = 1.lIIIlIIIIll("s8JsB9jmsEo=", "CzpXv");
                1.lIIlIllII[1.lIIlIllll[3]] = 1.lIIIlIIIIll("P0/jB7EaIpk=", "EGWjn");
                1.lIIlIllII[1.lIIlIllll[9]] = 1.lIIIlIIlIIl("hJvQy2pWjNs=", "HohJB");
                1.lIIlIllII[1.lIIlIllll[2]] = 1.lIIIlIIIIll("qs2knHkJryw=", "iSoNW");
            }

            ArrayList<LivingEntity> getTargets() {
                1 llIlIlllIlIIll;
                double llIlIlllIlIlIl = llIlIlllIlIIll.Turret.this.distAtac;
                ArrayList<LivingEntity> llIlIlllIlIlII = new ArrayList<LivingEntity>();
                Iterator llIlIlllIlIIII = llIlIlllIlIIll.Turret.this.world.getEntities().iterator();
                while (1.lIIIlIlIllI((int)llIlIlllIlIIII.hasNext())) {
                    Entity llIlIlllIlIlll = (Entity)llIlIlllIlIIII.next();
                    if (1.lIIIlIlIllI((int)llIlIlllIlIIll.Turret.this.Targetable(llIlIlllIlIlll).booleanValue())) {
                        double llIlIlllIllIII;
                        double llIlIlllIllIIl = llIlIlllIlIIll.Turret.this.location.getY() - llIlIlllIlIlll.getLocation().getY();
                        if (1.lIIIlIlIlll(1.lIIIlIllIII(llIlIlllIllIIl, 0.0))) {
                            llIlIlllIllIIl = 0.0;
                        }
                        if (1.lIIIlIlIlll(1.lIIIlIllIII(llIlIlllIllIII = llIlIlllIlIIll.Turret.this.location.distance(llIlIlllIlIlll.getLocation()), llIlIlllIlIlIl + llIlIlllIllIIl))) {
                            "".length();
                            llIlIlllIlIlII.add((LivingEntity)llIlIlllIlIlll);
                        }
                    }
                    "".length();
                    if ("  ".length() < (13 ^ 9)) continue;
                    return null;
                }
                return llIlIlllIlIlII;
            }

            static {
                1.lIIIlIlIIII();
                1.lIIIlIIlIlI();
            }

            private static void lIIIlIlIIII() {
                lIIlIllll = new int[12];
                1.lIIlIllll[0] = 97 + 102 - 75 + 3 ^ (5 ^ 118);
                1.lIIlIllll[1] = (1 + 76 - -63 + 41 ^ 147 + 49 - 182 + 155) & (161 ^ 152 ^ (99 ^ 70) ^ -" ".length());
                1.lIIlIllll[2] = "   ".length();
                1.lIIlIllll[3] = " ".length();
                1.lIIlIllll[4] = 126 ^ 39 ^ (119 ^ 58);
                1.lIIlIllll[5] = 19 ^ 22;
                1.lIIlIllll[6] = 219 + 46 - 134 + 94 ^ 161 + 117 - 112 + 27;
                1.lIIlIllll[7] = 44 ^ 40;
                1.lIIlIllll[8] = -7825 & 8184;
                1.lIIlIllll[9] = "  ".length();
                1.lIIlIllll[10] = 95 + 18 - 92 + 179;
                1.lIIlIllll[11] = 141 ^ 133;
            }

            private static String lIIIlIIIIll(String llIlIllIIllIIl, String llIlIllIIllIII) {
                try {
                    SecretKeySpec llIlIllIIllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIllIIllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                    Cipher llIlIllIIlllIl = Cipher.getInstance("Blowfish");
                    llIlIllIIlllIl.init(lIIlIllll[9], llIlIllIIllllI);
                    return new String(llIlIllIIlllIl.doFinal(Base64.getDecoder().decode(llIlIllIIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception llIlIllIIlllII) {
                    llIlIllIIlllII.printStackTrace();
                    return null;
                }
            }

            private static String lIIIlIIlIIl(String llIlIllIIIlllI, String llIlIllIIIlIll) {
                try {
                    SecretKeySpec llIlIllIIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIllIIIlIll.getBytes(StandardCharsets.UTF_8)), lIIlIllll[11]), "DES");
                    Cipher llIlIllIIlIIII = Cipher.getInstance("DES");
                    llIlIllIIlIIII.init(lIIlIllll[9], llIlIllIIlIIIl);
                    return new String(llIlIllIIlIIII.doFinal(Base64.getDecoder().decode(llIlIllIIIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception llIlIllIIIllll) {
                    llIlIllIIIllll.printStackTrace();
                    return null;
                }
            }
            {
                1 llIllIIIIIllll;
            }

            private static boolean lIIIlIlIIlI(int n, int n2) {
                return n >= n2;
            }

            private static boolean lIIIlIlIlII(int n, int n2) {
                return n == n2;
            }

            private static boolean lIIIlIllIIl(int n, int n2) {
                return n < n2;
            }
        }, 10L, (long)lIlIIIlIlIIIllI.VelAtac);
    }

    private static boolean lIIlllIIlI(int n) {
        return n < 0;
    }

    private static boolean lIIlllIIIl(int n) {
        return n > 0;
    }

    public void setArmorCD(int lIlIIlllIIlIIII) {
        Turret lIlIIlllIIlIIIl;
        lIlIIlllIIlIIIl.plugin.getServer().getScheduler().cancelTask(lIlIIlllIIlIIIl.taskEscutId);
        if (Turret.lIIlllIIlI(lIlIIlllIIlIIII)) {
            return;
        }
        lIlIIlllIIlIIIl.taskEscutId = lIlIIlllIIlIIIl.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIlIIlllIIlIIIl.plugin, () -> {
            Turret lIlIIIIlIIIIIlI;
            lIlIIIIlIIIIIlI.hpEscut = lIlIIIIlIIIIIlI.maxHpEscut;
            if (Turret.lIIllIIllI((int)lIlIIIIlIIIIIlI.CheckArmor().booleanValue())) {
                lIlIIIIlIIIIIlI.world.playSound(lIlIIIIlIIIIIlI.location, Sound.BLOCK_PISTON_EXTEND, 3.0f, 1.0f);
            }
        }, (long)(lIlIIlllIIlIIII * lIIllllI[7]));
    }

    public void setHp(int lIlIlIIIlllIlll) {
        lIlIlIIIllllIII.hp = lIlIlIIIlllIlll;
    }

    public Boolean CheckArmor() {
        Turret lIlIIllllIIIIIl;
        lIlIIllllIIIIIl.DestroyArmor();
        if (Turret.lIIllIlllI(lIlIIllllIIIIIl.hpEscut)) {
            return lIIllllI[0];
        }
        int lIlIIllllIIIIlI = lIIllllI[0];
        if (Turret.lIIllIllll(lIlIIllllIIIIIl.hpEscut, lIIllllI[8])) {
            lIlIIllllIIIIlI = lIIllllI[2];
        }
        lIlIIllllIIIIIl.BuildArmor(lIlIIllllIIIIlI);
        return lIIllllI[2];
    }

    private static boolean lIIllIllIl(Object object) {
        return object != null;
    }

    public void defineTurretBlocks() {
        Turret lIlIlIIIIIIllIl;
        lIlIlIIIIIIllIl.TurretBlocks.clear();
        Location lIlIlIIIIIIllII = lIlIlIIIIIIllIl.location.clone();
        "".length();
        lIlIlIIIIIIllIl.TurretBlocks.add(lIlIlIIIIIIllII.clone());
        lIlIlIIIIIIllII.setY(lIlIlIIIIIIllII.getY() + 1.0);
        "".length();
        lIlIlIIIIIIllIl.TurretBlocks.add(lIlIlIIIIIIllII.clone());
        lIlIlIIIIIIllII.setY(lIlIlIIIIIIllII.getY() + 1.0);
        "".length();
        lIlIlIIIIIIllIl.TurretBlocks.add(lIlIlIIIIIIllII.clone());
    }

    public void Destroy() {
        Turret lIlIIllllllIIll;
        if (Turret.lIIllIIlll((int)lIlIIllllllIIll.headless.booleanValue(), lIIllllI[2])) {
            return;
        }
        lIlIIllllllIIll.DestroyArmor();
        Iterator<Location> lIlIIllllllIIlI = lIlIIllllllIIll.TurretBlocks.iterator();
        while (Turret.lIIllIIllI((int)lIlIIllllllIIlI.hasNext())) {
            Location lIlIIllllllIlIl = lIlIIllllllIIlI.next();
            lIlIIllllllIlIl.getBlock().setType(Material.AIR);
            "".length();
            if (" ".length() <= "   ".length()) continue;
            return;
        }
        lIlIIllllllIIll.built = lIIllllI[0];
        lIlIIllllllIIll.resetArmorCD();
        lIlIIllllllIIll.destroyed = lIIllllI[2];
        lIlIIllllllIIll.destroyEventBus();
    }

    private static boolean lIIllIIllI(int n) {
        return n != 0;
    }

    private static boolean lIIllIlIlI(int n) {
        return n == 0;
    }

    public Turret getAdmin() {
        Turret lIlIlIIIIIlllIl;
        if (Turret.lIIllIIlll((int)lIlIlIIIIIlllIl.linkCreador.booleanValue(), lIIllllI[2])) {
            Iterator<Turret> lIlIlIIIIIlllII = lIlIlIIIIIlllIl.joc.Turrets.iterator();
            while (Turret.lIIllIIllI((int)lIlIlIIIIIlllII.hasNext())) {
                Turret lIlIlIIIIIlllll = lIlIlIIIIIlllII.next();
                if (Turret.lIIllIlIII((Object)lIlIlIIIIIlllll.creador, (Object)lIlIlIIIIIlllIl.creador) && Turret.lIIllIIlll((int)lIlIlIIIIIlllll.isAdmin.booleanValue(), lIIllllI[2])) {
                    return lIlIlIIIIIlllll;
                }
                "".length();
                if (" ".length() == " ".length()) continue;
                return null;
            }
        }
        return null;
    }

    public Boolean canBuild() {
        Turret lIlIlIIIIIIIlII;
        lIlIlIIIIIIIlII.defineTurretBlocks();
        Iterator<Location> lIlIlIIIIIIIIll = lIlIlIIIIIIIlII.TurretBlocks.iterator();
        while (Turret.lIIllIIllI((int)lIlIlIIIIIIIIll.hasNext())) {
            Location lIlIlIIIIIIIllI = lIlIlIIIIIIIIll.next();
            if (Turret.lIIllIlIll((Object)lIlIlIIIIIIIllI.getBlock().getType(), (Object)Material.AIR)) {
                return lIIllllI[0];
            }
            if (Turret.lIIllIlIII((Object)lIlIlIIIIIIIllI.getBlock().getRelative(BlockFace.DOWN).getType(), (Object)Material.LEAVES)) {
                return lIIllllI[0];
            }
            "".length();
            if ("   ".length() < (21 ^ 103 ^ (25 ^ 111))) continue;
            return null;
        }
        return lIIllllI[2];
    }

    public static Turret createTurret(lobby lIlIlIIIlIIIIlI, Location lIlIlIIIlIIlIII, Player lIlIlIIIlIIIlll, Torres lIlIlIIIIllllll, JocEquips.Equip lIlIlIIIlIIIlIl, Boolean lIlIlIIIlIIIlII, Boolean lIlIlIIIlIIIIll) {
        "".length();
        Turret.getTurrets(lIlIlIIIIllllll).add(new Turret(lIlIlIIIlIIIIlI, Turret.getTurrets(lIlIlIIIIllllll).size(), lIlIlIIIlIIlIII, lIlIlIIIlIIIlll, lIlIlIIIIllllll, lIlIlIIIlIIIlIl, lIlIlIIIlIIIlII, lIlIlIIIlIIIIll));
        return Turret.getTurrets(lIlIlIIIIllllll).get(Turret.getTurrets(lIlIlIIIIllllll).size() - lIIllllI[2]);
    }

    public void Stop() {
        Turret lIlIIllllIlIlll;
        lIlIIllllIlIlll.plugin.getServer().getScheduler().cancelTask(lIlIIllllIlIlll.taskId);
    }

    private static boolean lIIlllIIll(int n, int n2) {
        return n <= n2;
    }

    public void inicialitzarMillores() {
        Turret lIlIIlllllIlIIl;
        lIlIIlllllIlIIl.Millores.clear();
        TipusMillora[] lIlIIlllllIlIII = TipusMillora.values();
        int lIlIIlllllIIlll = lIlIIlllllIlIII.length;
        int lIlIIlllllIIllI = lIIllllI[0];
        while (Turret.lIIllIllII(lIlIIlllllIIllI, lIlIIlllllIIlll)) {
            TipusMillora lIlIIlllllIlIll = lIlIIlllllIlIII[lIlIIlllllIIllI];
            "".length();
            lIlIIlllllIlIIl.Millores.add(lIlIIlllllIlIIl.new Millora(lIlIIlllllIlIll));
            ++lIlIIlllllIIllI;
            "".length();
            if (" ".length() >= 0) continue;
            return;
        }
    }

    private static String lIIlIIIIll(String lIlIIIIIllIlIlI, String lIlIIIIIllIlIll) {
        try {
            SecretKeySpec lIlIIIIIllIllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIIIIllIlIll.getBytes(StandardCharsets.UTF_8)), lIIllllI[13]), "DES");
            Cipher lIlIIIIIllIlllI = Cipher.getInstance("DES");
            lIlIIIIIllIlllI.init(lIIllllI[4], lIlIIIIIllIllll);
            return new String(lIlIIIIIllIlllI.doFinal(Base64.getDecoder().decode(lIlIIIIIllIlIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIIIIIllIllIl) {
            lIlIIIIIllIllIl.printStackTrace();
            return null;
        }
    }

    public void openOrRefreshInventory(Player lIlIIIIlIlllIll) {
        Turret lIlIIIIlIllllII;
        if (Turret.lIIllIlIlI((int)lIlIIIIlIllllII.headless.booleanValue())) {
            lIlIIIIlIllllII.invString = lIlIIIIlIllllII.generateInvString();
            Inventory lIlIIIIlIllllll = Bukkit.getServer().createInventory((InventoryHolder)lIlIIIIlIlllIll, lIIllllI[17], lIlIIIIlIllllII.invString);
            if (Turret.lIIllIlIlI((int)lIlIIIIlIllllII.linkCreador.booleanValue())) {
                Iterator<Millora> lIlIIIIlIlllIIl = lIlIIIIlIllllII.Millores.iterator();
                while (Turret.lIIllIIllI((int)lIlIIIIlIlllIIl.hasNext())) {
                    Millora lIlIIIIllIIIIII = lIlIIIIlIlllIIl.next();
                    ItemStack[] arritemStack = new ItemStack[lIIllllI[2]];
                    arritemStack[Turret.lIIllllI[0]] = lIlIIIIllIIIIII.toItemStack();
                    "".length();
                    lIlIIIIlIllllll.addItem(arritemStack);
                    "".length();
                    if (" ".length() > ((70 ^ 85 ^ (191 ^ 143)) & (211 ^ 134 ^ (1 ^ 119) ^ -" ".length()))) continue;
                    return;
                }
            }
            "".length();
            lIlIIIIlIlllIll.openInventory(lIlIIIIlIllllll);
        }
    }

    public void resetArmorCD() {
        Turret lIlIIlllIIlIlIl;
        if (Turret.lIIllIlIlI((int)lIlIIlllIIlIlIl.built.booleanValue())) {
            lIlIIlllIIlIlIl.setArmorCD(lIIllllI[12]);
            return;
        }
        if (Turret.lIIlllIIIl(lIlIIlllIIlIlIl.getByTipus((TipusMillora)TipusMillora.RESIST\u00c8NCIA).lvl)) {
            int lIlIIlllIIlIlll = lIlIIlllIIlIlIl.tempsEscut;
            if (Turret.lIIllIlIlI((int)lIlIIlllIIlIlIl.damaged.booleanValue())) {
                lIlIIlllIIlIlll /= lIIllllI[4];
            }
            lIlIIlllIIlIlIl.setArmorCD(lIlIIlllIIlIlll);
        }
    }

    public void updateChildStats() {
        Turret lIlIlIIIIIlIlIl;
        ArrayList<Turret> lIlIlIIIIIlIlII = lIlIlIIIIIlIlIl.joc.Turrets;
        if (Turret.lIIllIlIIl(lIlIlIIIIIlIlII)) {
            return;
        }
        Iterator<Turret> lIlIlIIIIIlIIIl = lIlIlIIIIIlIlII.iterator();
        while (Turret.lIIllIIllI((int)lIlIlIIIIIlIIIl.hasNext())) {
            Turret lIlIlIIIIIlIllI = lIlIlIIIIIlIIIl.next();
            if (Turret.lIIllIlIII((Object)lIlIlIIIIIlIllI.creador, (Object)lIlIlIIIIIlIlIl.creador) && Turret.lIIllIlIlI((int)lIlIlIIIIIlIllI.isAdmin.booleanValue())) {
                lIlIlIIIIIlIllI.Atac = lIlIlIIIIIlIlIl.Atac;
                lIlIlIIIIIlIllI.VelAtac = lIlIlIIIIIlIlIl.VelAtac;
                lIlIlIIIIIlIllI.distAtac = lIlIlIIIIIlIlIl.distAtac;
                lIlIlIIIIIlIllI.xpPerTir = lIlIlIIIIIlIlIl.xpPerTir;
                lIlIlIIIIIlIllI.foc = lIlIlIIIIIlIlIl.foc;
                lIlIlIIIIIlIllI.maxHpEscut = lIlIlIIIIIlIlIl.maxHpEscut;
                lIlIlIIIIIlIllI.tempsEscut = lIlIlIIIIIlIlIl.tempsEscut;
                lIlIlIIIIIlIllI.xp = lIlIlIIIIIlIlIl.xp;
                lIlIlIIIIIlIllI.Millores = lIlIlIIIIIlIlIl.Millores;
                lIlIlIIIIIlIllI.resetArmorCD();
            }
            "".length();
            if ("  ".length() != 0) continue;
            return;
        }
    }

    public void checkIntegrity() {
        Turret lIlIIIlIlIIlIlI;
        if (Turret.lIIllIIllI((int)lIlIIIlIlIIlIlI.built.booleanValue()) && Turret.lIIllIIllI((int)lIlIIIlIlIIlIlI.canBuild().booleanValue())) {
            lIlIIIlIlIIlIlI.Build();
        }
    }

    public void AutoUpgradeRandom() {
        do {
            Turret lIlIIllllIIIlll;
            Iterator<Millora> lIlIIllllIIIllI = lIlIIllllIIIlll.Millores.iterator();
            while (Turret.lIIllIIllI((int)lIlIIllllIIIllI.hasNext())) {
                Millora lIlIIllllIIlIIl = lIlIIllllIIIllI.next();
                if (Turret.lIIllIIllI((int)Utils.Possibilitat(lIIllllI[7]))) {
                    if (Turret.lIIllIIllI((int)lIlIIllllIIlIIl.possibleUpgrade().booleanValue())) {
                        lIlIIllllIIlIIl.lvlUp();
                    }
                    return;
                }
                "".length();
                if (" ".length() < (4 ^ 122 ^ (119 ^ 13))) continue;
                return;
            }
            "".length();
        } while (((59 ^ 28) & ~(53 ^ 18)) == 0);
    }

    private static void lIIlIIlIll() {
        lIIllIII = new String[lIIllllI[5]];
        Turret.lIIllIII[Turret.lIIllllI[0]] = Turret.lIIlIIIIll("/atVBC2XD28=", "HyGdh");
        Turret.lIIllIII[Turret.lIIllllI[2]] = Turret.lIIlIIIlII("gOaNMj9P1og=", "UXSZi");
        Turret.lIIllIII[Turret.lIIllllI[4]] = Turret.lIIlIIIlIl("UQ==", "yJGgA");
        Turret.lIIllIII[Turret.lIIllllI[9]] = Turret.lIIlIIIIll("JyGVotrEIWI=", "PvJlj");
        Turret.lIIllIII[Turret.lIIllllI[10]] = Turret.lIIlIIIlIl("", "MDMnF");
        Turret.lIIllIII[Turret.lIIllllI[11]] = Turret.lIIlIIIlIl("cAo8", "PbLHr");
        Turret.lIIllIII[Turret.lIIllllI[14]] = Turret.lIIlIIIlIl("Ug==", "ryhiY");
        Turret.lIIllIII[Turret.lIIllllI[16]] = Turret.lIIlIIIIll("vgZ7frkfMPY=", "JIGFE");
        Turret.lIIllIII[Turret.lIIllllI[13]] = Turret.lIIlIIIlII("SJDNUC2gMbk=", "EZYkv");
        Turret.lIIllIII[Turret.lIIllllI[17]] = Turret.lIIlIIIIll("rTZajUqZZ3w=", "GzNBr");
        Turret.lIIllIII[Turret.lIIllllI[8]] = Turret.lIIlIIIlIl("", "Zuvrp");
        Turret.lIIllIII[Turret.lIIllllI[19]] = Turret.lIIlIIIlIl("Rw==", "gmLgC");
        Turret.lIIllIII[Turret.lIIllllI[21]] = Turret.lIIlIIIIll("F42sLTR06dzRgIH8+HalBw==", "bmYfj");
        Turret.lIIllIII[Turret.lIIllllI[22]] = Turret.lIIlIIIlII("BdipfCSimji6OIP4W9FYDg==", "kjmFw");
    }

    public void Learn(int lIlIIllllIIlllI) {
        Turret lIlIIllllIlIIIl;
        if (Turret.lIIllIIllI((int)lIlIIllllIlIIIl.linkCreador.booleanValue())) {
            Turret lIlIIllllIlIIll = lIlIIllllIlIIIl.getAdmin();
            lIlIIllllIlIIll.xp += lIlIIllllIIlllI;
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        } else {
            double lIlIIllllIlIIlI = 1.0;
            if (Turret.lIIllIllIl((Object)lIlIIllllIlIIIl.creador)) {
                lIlIIllllIlIIlI = lIlIIllllIlIIIl.joc.getBalancingMultiplier(lIlIIllllIlIIIl.creador);
            }
            lIlIIllllIlIIIl.xp = (int)((double)lIlIIllllIlIIIl.xp + (double)lIlIIllllIIlllI * lIlIIllllIlIIlI);
        }
        if (Turret.lIIllIIllI((int)lIlIIllllIlIIIl.autoUpgrade.booleanValue())) {
            lIlIIllllIlIIIl.AutoUpgradeRandom();
        }
    }

    static {
        Turret.lIIllIIlIl();
        Turret.lIIlIIlIll();
    }

    Boolean Targetable(Entity lIlIIIlIIlllllI) {
        if (Turret.lIIllIIllI(lIlIIIlIIlllllI instanceof LivingEntity)) {
            if (Turret.lIIllIIllI((int)lIlIIIlIIlllllI.isDead())) {
                return lIIllllI[0];
            }
            if (Turret.lIIllIIllI(lIlIIIlIIlllllI instanceof Player)) {
                Turret lIlIIIlIIllllll;
                Player lIlIIIlIlIIIIlI = (Player)lIlIIIlIIlllllI;
                if (Turret.lIIllIlIII((Object)lIlIIIlIIlllllI, (Object)lIlIIIlIIllllll.creador)) {
                    return lIIllllI[0];
                }
                if (Turret.lIIllIllIl(lIlIIIlIIllllll.equip) && Turret.lIIllIIllI((int)lIlIIIlIIllllll.equip.getPlayers().contains((Object)lIlIIIlIlIIIIlI))) {
                    return lIIllllI[0];
                }
                if (Turret.lIIllIlIII((Object)lIlIIIlIlIIIIlI.getGameMode(), (Object)GameMode.CREATIVE)) {
                    return lIIllllI[0];
                }
            }
            return lIIllllI[2];
        }
        return lIIllllI[0];
    }

    Boolean anyUpgradePossible() {
        Turret lIlIIIlIIlIlIll;
        Iterator<Millora> lIlIIIlIIlIlIIl = lIlIIIlIIlIlIll.Millores.iterator();
        while (Turret.lIIllIIllI((int)lIlIIIlIIlIlIIl.hasNext())) {
            Millora lIlIIIlIIlIllII = lIlIIIlIIlIlIIl.next();
            if (Turret.lIIllIIllI((int)lIlIIIlIIlIllII.possibleUpgrade().booleanValue())) {
                return lIIllllI[2];
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return lIIllllI[0];
    }

    public void randomPotionAttack(int lIlIIllIlllllll, int lIlIIllIlllIlll) {
        ArrayList<Integer> lIlIIllIlllllIl = new ArrayList<Integer>();
        int lIlIIllIlllllII = lIIllllI[0];
        while (Turret.lIIlllIIll(lIlIIllIlllllII, lIlIIllIlllllll)) {
            int lIlIIlllIIIIIll = lIIllllI[4];
            int lIlIIlllIIIIIlI = lIIllllI[12];
            while (Turret.lIIllIIlll(lIlIIlllIIIIIlI, lIIllllI[12])) {
                int lIlIIlllIIIIlII = Utils.NombreEntre(lIIllllI[0], lIlIIlllIIIIIll);
                if (Turret.lIIllIlIlI((int)lIlIIllIlllllIl.contains(lIlIIlllIIIIlII))) {
                    lIlIIlllIIIIIlI = lIlIIlllIIIIlII;
                }
                "".length();
                if (" ".length() > 0) continue;
                return;
            }
            "".length();
            lIlIIllIlllllIl.add(lIlIIlllIIIIIlI);
            ++lIlIIllIlllllII;
            "".length();
            if ("  ".length() != -" ".length()) continue;
            return;
        }
        int lIlIIllIllllIll = lIIllllI[0];
        int lIlIIllIllllIlI = lIlIIllIlllIlll;
        Iterator lIlIIlllIIIIlII = lIlIIllIlllllIl.iterator();
        while (Turret.lIIllIIllI((int)lIlIIlllIIIIlII.hasNext())) {
            Turret lIlIIllIllllIIl;
            int lIlIIlllIIIIIIl = (Integer)lIlIIlllIIIIlII.next();
            lIlIIllIllllIIl.PotionAttack(lIlIIlllIIIIIIl, lIlIIllIllllIlI, lIIllllI[7] * lIlIIllIllllIll);
            ++lIlIIllIllllIll;
            lIlIIllIllllIlI = lIlIIllIllllIlI / lIIllllI[4] + lIIllllI[2];
            "".length();
            if ("   ".length() > 0) continue;
            return;
        }
    }

    public void BuildArmor(int lIlIIlllIlIlIlI) {
        Turret lIlIIlllIlIlllI;
        int lIlIIlllIlIllII = lIlIIlllIlIlIlI;
        lIlIIlllIlIlllI.ArmorBlocks.clear();
        while (Turret.lIIlllIIII(lIlIIlllIlIllII)) {
            Location lIlIIlllIllIIIl = lIlIIlllIlIlllI.location.clone().add(new Vector(lIIllllI[0], lIlIIlllIlIllII, lIIllllI[0]));
            ArrayList<Byte> lIlIIlllIllIIII = new ArrayList<Byte>();
            "".length();
            lIlIIlllIllIIII.add(lIIllllI[4]);
            "".length();
            lIlIIlllIllIIII.add(lIIllllI[9]);
            "".length();
            lIlIIlllIllIIII.add(lIIllllI[10]);
            "".length();
            lIlIIlllIllIIII.add(lIIllllI[11]);
            ArrayList<BlockFace> lIlIIlllIlIllll = new ArrayList<BlockFace>();
            "".length();
            lIlIIlllIlIllll.add(BlockFace.NORTH);
            "".length();
            lIlIIlllIlIllll.add(BlockFace.SOUTH);
            "".length();
            lIlIIlllIlIllll.add(BlockFace.WEST);
            "".length();
            lIlIIlllIlIllll.add(BlockFace.EAST);
            Iterator lIlIIlllIlIIlIl = lIlIIlllIlIllll.iterator();
            while (Turret.lIIllIIllI((int)lIlIIlllIlIIlIl.hasNext())) {
                BlockFace lIlIIlllIllIIlI = (BlockFace)lIlIIlllIlIIlIl.next();
                Block lIlIIlllIllIlII = lIlIIlllIllIIIl.getBlock().getRelative(lIlIIlllIllIIlI);
                if (Turret.lIIllIlIll((Object)lIlIIlllIllIlII.getType(), (Object)Material.AIR) && Turret.lIIllIlIll((Object)lIlIIlllIllIlII.getPistonMoveReaction(), (Object)PistonMoveReaction.BREAK)) {
                    "".length();
                    if ("  ".length() != ((97 + 114 - 56 + 39 ^ 117 + 127 - 199 + 101) & (81 ^ 63 ^ (162 ^ 156) ^ -" ".length()))) continue;
                    return;
                }
                lIlIIlllIllIlII.setType(Material.WALL_SIGN);
                Sign lIlIIlllIllIIll = (Sign)lIlIIlllIllIlII.getState();
                if (Turret.lIIllIllIl((Object)lIlIIlllIlIlllI.creador)) {
                    lIlIIlllIllIIll.setLine(lIIllllI[2], lIlIIlllIlIlllI.creador.getName());
                }
                "".length();
                lIlIIlllIllIIll.update();
                lIlIIlllIllIlII.setData(((Byte)lIlIIlllIllIIII.get(lIlIIlllIlIllll.indexOf((Object)lIlIIlllIllIIlI))).byteValue());
                "".length();
                lIlIIlllIlIlllI.ArmorBlocks.add(lIlIIlllIllIlII.getLocation());
                "".length();
                if (((68 ^ 14) & ~(40 ^ 98)) <= 0) continue;
                return;
            }
            lIlIIlllIlIllII -= lIIllllI[2];
            "".length();
            if ("  ".length() < (85 ^ 81)) continue;
            return;
        }
    }

    public void setXp(int lIlIlIIlIIIIIII) {
        lIlIlIIlIIIIIIl.xp = lIlIlIIlIIIIIII;
    }

    public void tirarPoci\u00f3(Location lIlIIIlIllIllII, PotionType lIlIIIlIllIIIII) {
        Turret lIlIIIlIllIIIlI;
        World lIlIIIlIllIlIlI = (World)Bukkit.getServer().getWorlds().get(lIIllllI[0]);
        Location lIlIIIlIllIlIIl = lIlIIIlIllIIIlI.location.clone().add(new Location(lIlIIIlIllIlIlI, 0.5, 3.2, 0.5));
        Vector lIlIIIlIllIlIII = lIlIIIlIllIllII.toVector().subtract(lIlIIIlIllIlIIl.toVector());
        Vector lIlIIIlIllIIlll = lIlIIIlIllIlIII.normalize();
        Vector lIlIIIlIllIIllI = new Vector(0.0, lIlIIIlIllIlIII.length() / 40.0, 0.0);
        "".length();
        lIlIIIlIllIIlll.add(lIlIIIlIllIIllI);
        ThrownPotion lIlIIIlIllIIlIl = (ThrownPotion)lIlIIIlIllIlIlI.spawnEntity(lIlIIIlIllIlIIl.add(lIlIIIlIllIIlll.multiply(0.65)), EntityType.SPLASH_POTION);
        lIlIIIlIllIIlIl.setVelocity(lIlIIIlIllIIlll);
        Potion lIlIIIlIllIIlII = new Potion(lIlIIIlIllIIIII).splash();
        ItemStack lIlIIIlIllIIIll = lIlIIIlIllIIlII.toItemStack(lIIllllI[2]);
        lIlIIIlIllIIlIl.setItem(lIlIIIlIllIIIll);
        lIlIIIlIllIIlIl.setShooter((ProjectileSource)lIlIIIlIllIIIlI.creador);
    }

    private static boolean lIIllIlIIl(Object object) {
        return object == null;
    }

    public int getXp() {
        Turret lIlIlIIlIIIIlIl;
        return lIlIlIIlIIIIlIl.xp;
    }

    private static boolean lIIllIlIII(Object object, Object object2) {
        return object == object2;
    }

    public static ArrayList<Turret> getTurrets(Torres lIlIlIIIIlIlllI) {
        return lIlIlIIIIlIlllI.Turrets;
    }

    private static String lIIlIIIlIl(String lIlIIIIIlIlIlll, String lIlIIIIIlIllIll) {
        lIlIIIIIlIlIlll = new String(Base64.getDecoder().decode(lIlIIIIIlIlIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIIIIIlIllIlI = new StringBuilder();
        char[] lIlIIIIIlIllIIl = lIlIIIIIlIllIll.toCharArray();
        int lIlIIIIIlIllIII = lIIllllI[0];
        char[] lIlIIIIIlIlIIlI = lIlIIIIIlIlIlll.toCharArray();
        int lIlIIIIIlIlIIIl = lIlIIIIIlIlIIlI.length;
        int lIlIIIIIlIlIIII = lIIllllI[0];
        while (Turret.lIIllIllII(lIlIIIIIlIlIIII, lIlIIIIIlIlIIIl)) {
            char lIlIIIIIlIlllIl = lIlIIIIIlIlIIlI[lIlIIIIIlIlIIII];
            "".length();
            lIlIIIIIlIllIlI.append((char)(lIlIIIIIlIlllIl ^ lIlIIIIIlIllIIl[lIlIIIIIlIllIII % lIlIIIIIlIllIIl.length]));
            ++lIlIIIIIlIllIII;
            ++lIlIIIIIlIlIIII;
            "".length();
            if ("   ".length() != (192 ^ 196)) continue;
            return null;
        }
        return String.valueOf(lIlIIIIIlIllIlI);
    }

    protected void onPlayerInteract(PlayerInteractEvent lIlIIIIlIlIlIlI, Player lIlIIIIlIlIlIIl) {
        Turret lIlIIIIlIlIlIll;
        super.onPlayerInteract(lIlIIIIlIlIlIlI, lIlIIIIlIlIlIIl);
        Player lIlIIIIlIlIlllI = lIlIIIIlIlIlIlI.getPlayer();
        ItemStack lIlIIIIlIlIllIl = lIlIIIIlIlIlIlI.getItem();
        PlayerInventory lIlIIIIlIlIllII = lIlIIIIlIlIlllI.getInventory();
        if (Turret.lIIllIlIII((Object)lIlIIIIlIlIlIlI.getAction(), (Object)Action.RIGHT_CLICK_BLOCK) && Turret.lIIllIIllI((int)lIlIIIIlIlIlIll.ContainsTurretBlock(lIlIIIIlIlIlIlI.getClickedBlock().getLocation()).booleanValue())) {
            if (Turret.lIIllIlIII((Object)lIlIIIIlIlIlllI.getItemInHand().getType(), (Object)Material.EXP_BOTTLE)) {
                lIlIIIIlIlIlIll.Learn(lIIllllI[20]);
                lIlIIIIlIlIlIlI.setCancelled(lIIllllI[2]);
                return;
            }
            if (Turret.lIIllIlIlI((int)lIlIIIIlIlIlIll.hasInventory.booleanValue())) {
                return;
            }
            if (Turret.lIIllIllIl(lIlIIIIlIlIlIll.equip) && Turret.lIIllIIllI((int)lIlIIIIlIlIlIll.equip.getPlayers().contains((Object)lIlIIIIlIlIlllI))) {
                return;
            }
            lIlIIIIlIlIlIll.joc.pTemp().EstablirLocation(String.valueOf(new StringBuilder().append(lIIllIII[lIIllllI[21]]).append(lIlIIIIlIlIlllI.getName())), lIlIIIIlIlIlIlI.getClickedBlock().getLocation());
            lIlIIIIlIlIlIll.openOrRefreshInventory(lIlIIIIlIlIlllI);
            lIlIIIIlIlIlIlI.setCancelled(lIIllllI[2]);
        }
        if (Turret.lIIllIlIII((Object)lIlIIIIlIlIlIlI.getAction(), (Object)Action.LEFT_CLICK_BLOCK) && Turret.lIIllIIllI((int)lIlIIIIlIlIlIll.ContainsTurretBlock(lIlIIIIlIlIlIlI.getClickedBlock().getLocation()).booleanValue())) {
            lIlIIIIlIlIlIll.Hit(lIIllllI[9]);
        }
    }

    public Boolean getAutoUpgrade() {
        Turret lIlIlIIIlllIIll;
        return lIlIlIIIlllIIll.autoUpgrade;
    }

    public static Turret getTurret(Torres lIlIlIIIIllIlII, int lIlIlIIIIllIlIl) {
        Iterator<Turret> lIlIlIIIIllIIlI = Turret.getTurrets(lIlIlIIIIllIlII).iterator();
        while (Turret.lIIllIIllI((int)lIlIlIIIIllIIlI.hasNext())) {
            Turret lIlIlIIIIllIlll = lIlIlIIIIllIIlI.next();
            if (Turret.lIIllIIlll(lIlIlIIIIllIlll.id, lIlIlIIIIllIlIl)) {
                return lIlIlIIIIllIlll;
            }
            "".length();
            if (-" ".length() != (80 ^ 84)) continue;
            return null;
        }
        return null;
    }

    private static boolean lIIllIlIll(Object object, Object object2) {
        return object != object2;
    }

    public Boolean ContainsTurretBlock(Location lIlIIIlIIIllIIl) {
        boolean bl;
        Turret lIlIIIlIIIllIlI;
        if (!Turret.lIIllIlIlI((int)lIlIIIlIIIllIlI.TurretBlocks.contains((Object)lIlIIIlIIIllIIl)) || Turret.lIIllIIllI((int)lIlIIIlIIIllIlI.ArmorBlocks.contains((Object)lIlIIIlIIIllIIl))) {
            bl = lIIllllI[2];
            "".length();
            if (((65 + 169 - 225 + 192 ^ 96 + 106 - 155 + 106) & (67 ^ 0 ^ (176 ^ 163) ^ -" ".length())) != 0) {
                return null;
            }
        } else {
            bl = lIIllllI[0];
        }
        return bl;
    }

    public void DestroyArmor() {
        Turret lIlIIlllIIlllII;
        Iterator<Location> lIlIIlllIIllIll = lIlIIlllIIlllII.ArmorBlocks.iterator();
        while (Turret.lIIllIIllI((int)lIlIIlllIIllIll.hasNext())) {
            Location lIlIIlllIIllllI = lIlIIlllIIllIll.next();
            lIlIIlllIIllllI.getBlock().setType(Material.AIR);
            "".length();
            if (((9 ^ 49) & ~(0 ^ 56)) > -" ".length()) continue;
            return;
        }
        lIlIIlllIIlllII.ArmorBlocks.clear();
    }

    protected void onBlockBreak(BlockBreakEvent lIlIIIIlllIIllI, Block lIlIIIIlllIlIIl) {
        Turret lIlIIIIlllIlIll;
        super.onBlockBreak(lIlIIIIlllIIllI, lIlIIIIlllIlIIl);
        Player lIlIIIIlllIlIII = lIlIIIIlllIIllI.getPlayer();
        if (Turret.lIIllIIllI((int)lIlIIIIlllIlIll.ContainsTurretBlock(lIlIIIIlllIlIIl.getLocation()).booleanValue()) && Turret.lIIllIlIII((Object)lIlIIIIlllIlIII.getGameMode(), (Object)GameMode.CREATIVE)) {
            lIlIIIIlllIlIll.Stop();
            lIlIIIIlllIlIll.Destroy();
            lIlIIIIlllIIllI.setCancelled(lIIllllI[2]);
        }
    }

    String generateInvString() {
        Turret lIlIIIIllIIllll;
        String lIlIIIIllIllIII = lIIllIII[lIIllllI[2]];
        if (Turret.lIIllIIlll((int)lIlIIIIllIIllll.linkCreador.booleanValue(), lIIllllI[2])) {
            lIlIIIIllIllIII = String.valueOf(new StringBuilder().append(lIlIIIIllIllIII).append(lIIllIII[lIIllllI[4]]).append(lIlIIIIllIIllll.creador.getName()).append(lIIllIII[lIIllllI[9]]));
        }
        String lIlIIIIllIlIlll = lIIllIII[lIIllllI[10]];
        String lIlIIIIllIlIllI = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(Integer.toString(lIlIIIIllIIllll.hp)).append(lIIllIII[lIIllllI[11]]));
        String lIlIIIIllIlIlIl = String.valueOf(new StringBuilder().append(lIIllIII[lIIllllI[14]]).append((Object)ChatColor.BLUE).append(Integer.toString(lIlIIIIllIIllll.xp)).append(lIIllIII[lIIllllI[16]]));
        if (Turret.lIIllIIlll((int)lIlIIIIllIIllll.linkCreador.booleanValue(), lIIllllI[2])) {
            lIlIIIIllIlIlIl = lIIllIII[lIIllllI[13]];
            if (Turret.lIIlllIIIl(lIlIIIIllIIllll.hpEscut)) {
                lIlIIIIllIlIlll = String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(Integer.toString(lIlIIIIllIIllll.hpEscut)).append(lIIllIII[lIIllllI[17]]));
            }
        }
        String lIlIIIIllIlIlII = String.valueOf(new StringBuilder().append(lIlIIIIllIlIlll).append(lIlIIIIllIlIllI).append(lIlIIIIllIlIlIl));
        String lIlIIIIllIlIIll = lIIllIII[lIIllllI[8]];
        int lIlIIIIllIlIIlI = lIIllllI[18] - (lIlIIIIllIllIII.length() + lIlIIIIllIlIlII.length());
        int lIlIIIIllIlIIIl = lIIllllI[0];
        while (Turret.lIIllIllII(lIlIIIIllIlIIIl, lIlIIIIllIlIIlI)) {
            lIlIIIIllIlIIll = String.valueOf(new StringBuilder().append(lIlIIIIllIlIIll).append(lIIllIII[lIIllllI[19]]));
            lIlIIIIllIlIIIl += lIIllllI[2];
            "".length();
            if (((112 ^ 31 ^ (83 ^ 40)) & (58 ^ 64 ^ (36 ^ 74) ^ -" ".length())) <= 0) continue;
            return null;
        }
        String lIlIIIIllIlIIII = String.valueOf(new StringBuilder().append(lIlIIIIllIllIII).append(lIlIIIIllIlIIll).append(lIlIIIIllIlIlII));
        if (Turret.lIIllIllll(lIlIIIIllIlIIII.length(), lIIllllI[18])) {
            lIlIIIIllIlIIII = lIlIIIIllIlIIII.substring(lIIllllI[0], lIIllllI[18]);
        }
        return lIlIIIIllIlIIII;
    }

    public void setAutoUpgrade(Boolean lIlIlIIIllIllII) {
        lIlIlIIIllIllll.autoUpgrade = lIlIlIIIllIllII;
    }

    protected void onProjectileHit(ProjectileHitEvent lIlIIIIlllllIll, Projectile lIlIIIIllllllIl) {
        Turret lIlIIIIllllllII;
        super.onProjectileHit(lIlIIIIlllllIll, lIlIIIIllllllIl);
        if (Turret.lIIllIIllI(lIlIIIIlllllIll.getEntity() instanceof Arrow)) {
            Arrow lIlIIIlIIIIIlII = (Arrow)lIlIIIIlllllIll.getEntity();
            World lIlIIIlIIIIIIll = lIlIIIlIIIIIlII.getWorld();
            Location lIlIIIlIIIIIIlI = lIlIIIlIIIIIlII.getLocation();
            LivingEntity lIlIIIlIIIIIIIl = (LivingEntity)lIlIIIIllllllIl.getShooter();
            Arrow lIlIIIlIIIIIIII = (Arrow)lIlIIIIllllllIl;
            if (Turret.lIIllIIllI(lIlIIIlIIIIIIII.getShooter() instanceof Player)) {
                Player lIlIIIlIIIIlIII = (Player)lIlIIIlIIIIIIII.getShooter();
                World lIlIIIlIIIIIlll = lIlIIIlIIIIIIII.getWorld();
                BlockIterator lIlIIIlIIIIIllI = new BlockIterator(lIlIIIlIIIIIlll, lIlIIIlIIIIIIII.getLocation().toVector(), lIlIIIlIIIIIIII.getVelocity().normalize(), 0.0, lIIllllI[10]);
                Block lIlIIIlIIIIIlIl = null;
                while (Turret.lIIllIIllI((int)lIlIIIlIIIIIllI.hasNext())) {
                    lIlIIIlIIIIIlIl = lIlIIIlIIIIIllI.next();
                    if (!Turret.lIIllIIllI((int)GUtils.isValidSolidBlock((Block)lIlIIIlIIIIIlIl))) continue;
                    "".length();
                    if (((46 ^ 26) & ~(32 ^ 20)) <= 0) break;
                    return;
                }
                if (Turret.lIIllIllIl(lIlIIIlIIIIIlIl) && Turret.lIIllIIllI((int)lIlIIIIllllllII.ContainsTurretBlock(lIlIIIlIIIIIlIl.getLocation()).booleanValue())) {
                    Boolean lIlIIIlIIIIlIIl = lIIllllI[2];
                    if (Turret.lIIllIllIl(lIlIIIIllllllII.equip)) {
                        if (Turret.lIIllIIlll((int)lIlIIIIllllllII.equip.getPlayers().contains((Object)lIlIIIlIIIIlIII), lIIllllI[2])) {
                            lIlIIIlIIIIlIIl = lIIllllI[0];
                            "".length();
                            if (-(123 ^ 127) >= 0) {
                                return;
                            }
                        }
                    } else if (Turret.lIIllIlIII((Object)lIlIIIlIIIIlIII, (Object)lIlIIIIllllllII.creador)) {
                        lIlIIIlIIIIlIIl = lIIllllI[0];
                    }
                    lIlIIIlIIIIIIII.setBounce(lIlIIIlIIIIlIIl.booleanValue());
                    if (Turret.lIIllIIllI((int)lIlIIIlIIIIlIIl.booleanValue())) {
                        lIlIIIIllllllII.Hit(lIIllllI[8]);
                        lIlIIIlIIIIlIII.playSound(lIlIIIlIIIIlIII.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 0.9f);
                        lIlIIIlIIIIIIII.remove();
                    }
                }
            }
        }
    }

    public void tirarPoci\u00f3(Location lIlIIIlIlIIlllI, PotionType lIlIIIlIlIlIIIl, int lIlIIIlIlIlIIII) {
        Turret lIlIIIlIlIIllll;
        "".length();
        lIlIIIlIlIIllll.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lIlIIIlIlIIllll.plugin, () -> {
            Turret lIlIIIIlIIIIllI;
            lIlIIIIlIIIIllI.tirarPoci\u00f3(lIlIIIlIlIIlllI, lIlIIIlIlIlIIIl);
        }, (long)lIlIIIlIlIlIIII);
    }

    private static boolean lIIllIIlll(int n, int n2) {
        return n == n2;
    }

    public static final class TipusMillora
    extends Enum<TipusMillora> {
        private static final /* synthetic */ TipusMillora[] $VALUES;
        public static final /* synthetic */ /* enum */ TipusMillora DIST_ATAC;
        public static final /* synthetic */ /* enum */ TipusMillora APRENENTATGE;
        public static final /* synthetic */ /* enum */ TipusMillora MAL;
        private static final /* synthetic */ String[] lIIlIlIl;
        public static final /* synthetic */ /* enum */ TipusMillora MAGNETISME;
        public static final /* synthetic */ /* enum */ TipusMillora VELOCITAT_ATAC;
        public static final /* synthetic */ /* enum */ TipusMillora MEC\u00c0NICA;
        public static final /* synthetic */ /* enum */ TipusMillora RESIST\u00c8NCIA;
        private static final /* synthetic */ int[] lIlIIIll;
        public static final /* synthetic */ /* enum */ TipusMillora QU\u00cdMICA;
        public static final /* synthetic */ /* enum */ TipusMillora FOC;

        public static TipusMillora[] values() {
            return (TipusMillora[])$VALUES.clone();
        }

        static {
            TipusMillora.lIIllllIll();
            TipusMillora.lIIlIIIIlI();
            MAL = new TipusMillora();
            VELOCITAT_ATAC = new TipusMillora();
            FOC = new TipusMillora();
            DIST_ATAC = new TipusMillora();
            RESIST\u00c8NCIA = new TipusMillora();
            QU\u00cdMICA = new TipusMillora();
            MEC\u00c0NICA = new TipusMillora();
            MAGNETISME = new TipusMillora();
            APRENENTATGE = new TipusMillora();
            TipusMillora[] arrtipusMillora = new TipusMillora[lIlIIIll[9]];
            arrtipusMillora[TipusMillora.lIlIIIll[0]] = MAL;
            arrtipusMillora[TipusMillora.lIlIIIll[1]] = VELOCITAT_ATAC;
            arrtipusMillora[TipusMillora.lIlIIIll[2]] = FOC;
            arrtipusMillora[TipusMillora.lIlIIIll[3]] = DIST_ATAC;
            arrtipusMillora[TipusMillora.lIlIIIll[4]] = RESIST\u00c8NCIA;
            arrtipusMillora[TipusMillora.lIlIIIll[5]] = QU\u00cdMICA;
            arrtipusMillora[TipusMillora.lIlIIIll[6]] = MEC\u00c0NICA;
            arrtipusMillora[TipusMillora.lIlIIIll[7]] = MAGNETISME;
            arrtipusMillora[TipusMillora.lIlIIIll[8]] = APRENENTATGE;
            $VALUES = arrtipusMillora;
        }

        private static String lIIIllllII(String lIlIIllIlIlIIII, String lIlIIllIlIIllll) {
            try {
                SecretKeySpec lIlIIllIlIlIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIllIlIIllll.getBytes(StandardCharsets.UTF_8)), lIlIIIll[8]), "DES");
                Cipher lIlIIllIlIlIlII = Cipher.getInstance("DES");
                lIlIIllIlIlIlII.init(lIlIIIll[2], lIlIIllIlIlIlIl);
                return new String(lIlIIllIlIlIlII.doFinal(Base64.getDecoder().decode(lIlIIllIlIlIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIllIlIlIIll) {
                lIlIIllIlIlIIll.printStackTrace();
                return null;
            }
        }

        private static String lIIIlIIIlI(String lIlIIllIlIlllIl, String lIlIIllIlIlllII) {
            try {
                SecretKeySpec lIlIIllIllIIIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIIllIlIlllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIlIIllIllIIIIl = Cipher.getInstance("Blowfish");
                lIlIIllIllIIIIl.init(lIlIIIll[2], lIlIIllIllIIIlI);
                return new String(lIlIIllIllIIIIl.doFinal(Base64.getDecoder().decode(lIlIIllIlIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIllIllIIIII) {
                lIlIIllIllIIIII.printStackTrace();
                return null;
            }
        }

        private static void lIIlIIIIlI() {
            lIIlIlIl = new String[lIlIIIll[9]];
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[0]] = TipusMillora.lIIIlIIIlI("XdJ8tigS5GY=", "eFanJ");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[1]] = TipusMillora.lIIIllllII("5/O1rY1ibRcC0u+YbtOoAQ==", "WVOCA");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[2]] = TipusMillora.lIIIllllIl("CTgg", "OwcMQ");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[3]] = TipusMillora.lIIIllllII("9IlPdqV8uSjlij8wt2zfnQ==", "tmkVI");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[4]] = TipusMillora.lIIIllllII("K44z0ik/jEdpQJvgmbK+VQ==", "gvOKz");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[5]] = TipusMillora.lIIIllllII("pYXv7jgue28/FrJH5P/7Jw==", "nyrdv");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[6]] = TipusMillora.lIIIllllIl("KzwKwrEMLzoI", "fyIqB");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[7]] = TipusMillora.lIIIllllII("EORDzFK57eOjR0cG562BdQ==", "NWnRe");
            TipusMillora.lIIlIlIl[TipusMillora.lIlIIIll[8]] = TipusMillora.lIIIlIIIlI("1xn5Iin5UrXyJ9HFM/ZrjQ==", "NyxoT");
        }

        private static String lIIIllllIl(String lIlIIllIlIIIIlI, String lIlIIllIIllllII) {
            lIlIIllIlIIIIlI = new String(Base64.getDecoder().decode(lIlIIllIlIIIIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIIllIlIIIIII = new StringBuilder();
            char[] lIlIIllIIllllll = lIlIIllIIllllII.toCharArray();
            int lIlIIllIIlllllI = lIlIIIll[0];
            char[] lIlIIllIIlllIII = lIlIIllIlIIIIlI.toCharArray();
            int lIlIIllIIllIlll = lIlIIllIIlllIII.length;
            int lIlIIllIIllIllI = lIlIIIll[0];
            while (TipusMillora.lIIlllllII(lIlIIllIIllIllI, lIlIIllIIllIlll)) {
                char lIlIIllIlIIIIll = lIlIIllIIlllIII[lIlIIllIIllIllI];
                "".length();
                lIlIIllIlIIIIII.append((char)(lIlIIllIlIIIIll ^ lIlIIllIIllllll[lIlIIllIIlllllI % lIlIIllIIllllll.length]));
                ++lIlIIllIIlllllI;
                ++lIlIIllIIllIllI;
                "".length();
                if ("   ".length() != 0) continue;
                return null;
            }
            return String.valueOf(lIlIIllIlIIIIII);
        }

        public static TipusMillora valueOf(String lIlIIllIllIllll) {
            return Enum.valueOf(TipusMillora.class, lIlIIllIllIllll);
        }

        private TipusMillora() {
            TipusMillora lIlIIllIllIlIIl;
        }

        private static void lIIllllIll() {
            lIlIIIll = new int[10];
            TipusMillora.lIlIIIll[0] = (155 ^ 181) & ~(8 ^ 38);
            TipusMillora.lIlIIIll[1] = " ".length();
            TipusMillora.lIlIIIll[2] = "  ".length();
            TipusMillora.lIlIIIll[3] = "   ".length();
            TipusMillora.lIlIIIll[4] = 75 ^ 112 ^ (134 ^ 185);
            TipusMillora.lIlIIIll[5] = 230 ^ 130 ^ (83 ^ 50);
            TipusMillora.lIlIIIll[6] = 102 ^ 0 ^ (84 ^ 52);
            TipusMillora.lIlIIIll[7] = 65 ^ 73 ^ (11 ^ 4);
            TipusMillora.lIlIIIll[8] = 111 ^ 103;
            TipusMillora.lIlIIIll[9] = 100 + 156 - 242 + 150 ^ 85 + 21 - 27 + 94;
        }

        private static boolean lIIlllllII(int n, int n2) {
            return n < n2;
        }
    }

    public class Millora {
        public /* synthetic */ int lvl;
        /* synthetic */ int max;
        private static final /* synthetic */ int[] llIllIll;
        /* synthetic */ Material material;
        private static final /* synthetic */ String[] lIlIllII;
        /* synthetic */ String Description;
        /* synthetic */ String name;
        public /* synthetic */ TipusMillora tipus;
        /* synthetic */ int Cost;

        private static boolean llIlIllIIl(int n) {
            return n > 0;
        }

        private static void llIlIlIIIl() {
            llIllIll = new int[34];
            Millora.llIllIll[0] = -" ".length();
            Millora.llIllIll[1] = (105 + 98 - 65 + 9 ^ 130 + 136 - 199 + 82) & (108 ^ 94 ^ (34 ^ 22) ^ -" ".length());
            Millora.llIllIll[2] = " ".length();
            Millora.llIllIll[3] = 90 ^ 80 ^ (145 ^ 130);
            Millora.llIllIll[4] = "  ".length();
            Millora.llIllIll[5] = "   ".length();
            Millora.llIllIll[6] = 22 ^ 48;
            Millora.llIllIll[7] = 115 ^ 80 ^ (11 ^ 34);
            Millora.llIllIll[8] = 36 ^ 32;
            Millora.llIllIll[9] = 76 ^ 68 ^ (102 ^ 107);
            Millora.llIllIll[10] = 98 ^ 36;
            Millora.llIllIll[11] = 154 ^ 156;
            Millora.llIllIll[12] = 90 ^ 82 ^ (182 ^ 185);
            Millora.llIllIll[13] = 107 ^ 117;
            Millora.llIllIll[14] = 89 ^ 1 ^ (0 ^ 80);
            Millora.llIllIll[15] = 64 ^ 73;
            Millora.llIllIll[16] = 139 ^ 167 ^ (22 ^ 16);
            Millora.llIllIll[17] = 39 + 66 - -48 + 33 ^ 4 + 6 - -68 + 96;
            Millora.llIllIll[18] = (82 ^ 85) & ~(191 ^ 184) ^ (163 ^ 168);
            Millora.llIllIll[19] = 105 + 162 - 155 + 79 ^ 86 + 65 - 50 + 40;
            Millora.llIllIll[20] = 180 + 120 - 277 + 182 ^ 169 + 58 - 49 + 15;
            Millora.llIllIll[21] = 110 ^ 99;
            Millora.llIllIll[22] = 70 ^ 34;
            Millora.llIllIll[23] = 34 + 87 - 14 + 28 ^ 38 + 57 - 20 + 62;
            Millora.llIllIll[24] = 146 ^ 157;
            Millora.llIllIll[25] = 82 + 59 - -41 + 31 ^ 103 + 128 - 165 + 129;
            Millora.llIllIll[26] = 71 ^ 87;
            Millora.llIllIll[27] = 63 ^ 46;
            Millora.llIllIll[28] = 98 ^ 5 ^ (125 ^ 81);
            Millora.llIllIll[29] = 201 ^ 140 ^ (148 ^ 195);
            Millora.llIllIll[30] = 14 + 69 - -50 + 22 ^ 28 + 131 - 126 + 103;
            Millora.llIllIll[31] = 18 + 43 - 49 + 144 ^ 89 + 136 - 181 + 93;
            Millora.llIllIll[32] = 102 ^ 113;
            Millora.llIllIll[33] = 211 ^ 146 ^ (24 ^ 65);
        }

        int getCost() {
            Millora lIIIlIIlllllIll;
            return (int)((double)lIIIlIIlllllIll.Cost * Math.pow((double)lIIIlIIlllllIll.lvl + 1.0 + (double)lIIIlIIlllllIll.Turret.this.upgradeLvlSum() / 4.0, 1.2) / 2.0);
        }

        private static void lIlIllIlIl() {
            lIlIllII = new String[llIllIll[33]];
            Millora.lIlIllII[Millora.llIllIll[1]] = Millora.lIlIIllIIl("ARsCHD8iBEcNNCoYCwkjIgQ=", "GwghG");
            Millora.lIlIllII[Millora.llIllIll[2]] = Millora.lIlIIllIIl("bF9QKicr", "GnpGF");
            Millora.lIlIllII[Millora.llIllIll[4]] = Millora.lIlIIllIlI("vdP1q2NCMviC31UkZ82rFl8gZr75B7I7", "VIcEQ");
            Millora.lIlIllII[Millora.llIllIll[5]] = Millora.lIlIlIIlII("ZnLtxn3+9PAixssYaA8H+EDied34Rndz", "aLWPi");
            Millora.lIlIllII[Millora.llIllIll[8]] = Millora.lIlIlIIlII("hzupGcv3vk7i/UhYyKI8raxYi8QEg7/v", "jEIfQ");
            Millora.lIlIllII[Millora.llIllIll[9]] = Millora.lIlIIllIlI("+EFMjzRJBAbiA7Pg5TpxGxv7mvovhGcJLtFGozg51yA=", "kOsJE");
            Millora.lIlIllII[Millora.llIllIll[11]] = Millora.lIlIlIIlII("BO+x7Qqw3W5f6JO/gtLPHg==", "yRABm");
            Millora.lIlIllII[Millora.llIllIll[12]] = Millora.lIlIIllIIl("bXtmNQ8pKjV3By86MnlDJz0nNA==", "FIFWc");
            Millora.lIlIllII[Millora.llIllIll[14]] = Millora.lIlIIllIlI("fmdYez2ln1DEM1zTUb/o2A==", "KlaXz");
            Millora.lIlIllII[Millora.llIllIll[15]] = Millora.lIlIIllIIl("TlMdZjwGAAsoQEMiHSUbF0tObVgrF04jHQASGmpOSFUmNg==", "cgnFn");
            Millora.lIlIllII[Millora.llIllIll[7]] = Millora.lIlIIllIlI("6zE7KqK6jUsTZUFpjAeUiA==", "rYjYV");
            Millora.lIlIllII[Millora.llIllIll[18]] = Millora.lIlIIllIlI("yPk7Ib16LLCRx1WXnvzhNwx08V64ZtWZ", "CQWmz");
            Millora.lIlIllII[Millora.llIllIll[20]] = Millora.lIlIIllIlI("ds0c31JKlvFL5f/2+1eZgg0Wz46y3YHC", "eVqAB");
            Millora.lIlIllII[Millora.llIllIll[21]] = Millora.lIlIIllIIl("MAQrGScRESgEOFgAOgAuGwwoHDhYBigUKlhUeVA/ERc6", "xeIpK");
            Millora.lIlIllII[Millora.llIllIll[23]] = Millora.lIlIIllIlI("+qC7ZHO+W0Oq/dEY52pjzA==", "xNvKS");
            Millora.lIlIllII[Millora.llIllIll[24]] = Millora.lIlIlIIlII("FjfAQqLNwe0Dz0WjTE7SqRsnL2q6Oc9lhaOjKaN6K6wPDTZZtWukZ4RPoIJcaORz", "zfNRz");
            Millora.lIlIllII[Millora.llIllIll[26]] = Millora.lIlIlIIlII("9DI2oz7kbrZuonB45VrbNQ==", "MQQMN");
            Millora.lIlIllII[Millora.llIllIll[27]] = Millora.lIlIIllIIl("KmFlJh48JzZ2D3U2PSYOIDrCrTgIOzI=", "RSEVk");
            Millora.lIlIllII[Millora.llIllIll[29]] = Millora.lIlIIllIlI("HJJ9WdNpOic=", "wNDDW");
            Millora.lIlIllII[Millora.llIllIll[30]] = Millora.lIlIIllIIl("Tg==", "gRYBw");
            Millora.lIlIllII[Millora.llIllIll[17]] = Millora.lIlIIllIIl("XCMIIFE=", "tnIxx");
            Millora.lIlIllII[Millora.llIllIll[31]] = Millora.lIlIIllIlI("iANcq0rambI=", "MVmMq");
            Millora.lIlIllII[Millora.llIllIll[25]] = Millora.lIlIIllIlI("iQusnHQ0n34=", "WVMzP");
            Millora.lIlIllII[Millora.llIllIll[32]] = Millora.lIlIlIIlII("37xz57iBRPH4OsqZ/Dxk2A==", "IuRKg");
        }

        Boolean getMaxed() {
            boolean bl;
            Millora lIIIlIIlllllIIl;
            if (Millora.llIlIlIIlI(lIIIlIIlllllIIl.max, llIllIll[0])) {
                return llIllIll[1];
            }
            if (Millora.llIlIlIIll(lIIIlIIlllllIIl.lvl + llIllIll[2], lIIIlIIlllllIIl.max)) {
                bl = llIllIll[2];
                "".length();
                if ("   ".length() <= ((194 ^ 190 ^ (52 ^ 83)) & (131 + 152 - 181 + 88 ^ 22 + 20 - -27 + 96 ^ -" ".length()))) {
                    return null;
                }
            } else {
                bl = llIllIll[1];
            }
            return bl;
        }

        private static boolean llIlIlIIlI(int n, int n2) {
            return n == n2;
        }

        public Millora(TipusMillora lIIIlIIlllllllI) {
            Millora lIIIlIlIIIIIIII;
            lIIIlIlIIIIIIII.max = llIllIll[0];
            lIIIlIlIIIIIIII.tipus = lIIIlIIlllllllI;
            switch (2.$SwitchMap$com$biel$lobby$utilities$Turret$TipusMillora[lIIIlIIlllllllI.ordinal()]) {
                case 1: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[1]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[2]];
                    lIIIlIlIIIIIIII.material = Material.IRON_AXE;
                    lIIIlIlIIIIIIII.Cost = llIllIll[3];
                    "".length();
                    if ((181 ^ 176) > 0) break;
                    throw null;
                }
                case 2: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[4]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[5]];
                    lIIIlIlIIIIIIII.Cost = llIllIll[6];
                    lIIIlIlIIIIIIII.material = Material.FEATHER;
                    lIIIlIlIIIIIIII.max = llIllIll[7];
                    "".length();
                    if (((20 ^ 11) & ~(79 ^ 80)) == 0) break;
                    throw null;
                }
                case 3: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[8]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[9]];
                    lIIIlIlIIIIIIII.material = Material.BLAZE_POWDER;
                    lIIIlIlIIIIIIII.Cost = llIllIll[10];
                    lIIIlIlIIIIIIII.max = llIllIll[2];
                    "".length();
                    if ("  ".length() > " ".length()) break;
                    throw null;
                }
                case 4: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[11]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[12]];
                    lIIIlIlIIIIIIII.material = Material.BOW;
                    lIIIlIlIIIIIIII.Cost = llIllIll[13];
                    "".length();
                    if (-" ".length() < "   ".length()) break;
                    throw null;
                }
                case 5: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[14]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[15]];
                    lIIIlIlIIIIIIII.material = Material.IRON_CHESTPLATE;
                    lIIIlIlIIIIIIII.Cost = llIllIll[16];
                    lIIIlIlIIIIIIII.max = llIllIll[17];
                    "".length();
                    if (null == null) break;
                    throw null;
                }
                case 6: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[7]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[18]];
                    lIIIlIlIIIIIIII.material = Material.BREWING_STAND_ITEM;
                    lIIIlIlIIIIIIII.Cost = llIllIll[19];
                    "".length();
                    if (" ".length() > 0) break;
                    throw null;
                }
                case 7: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[20]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[21]];
                    lIIIlIlIIIIIIII.material = Material.PISTON_BASE;
                    lIIIlIlIIIIIIII.Cost = llIllIll[22];
                    lIIIlIlIIIIIIII.max = llIllIll[9];
                    "".length();
                    if ("  ".length() != 0) break;
                    throw null;
                }
                case 8: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[23]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[24]];
                    lIIIlIlIIIIIIII.material = Material.IRON_INGOT;
                    lIIIlIlIIIIIIII.Cost = llIllIll[25];
                    "".length();
                    if (-"   ".length() <= 0) break;
                    throw null;
                }
                case 9: {
                    lIIIlIlIIIIIIII.name = lIlIllII[llIllIll[26]];
                    lIIIlIlIIIIIIII.Description = lIlIllII[llIllIll[27]];
                    lIIIlIlIIIIIIII.material = Material.BOOK;
                    lIIIlIlIIIIIIII.Cost = llIllIll[28];
                    "".length();
                    if (null == null) break;
                    throw null;
                }
            }
        }

        private static boolean llIlIlIllI(int n, int n2) {
            return n <= n2;
        }

        public void lvlUp() {
            Millora lIIIlIIllllIlIl;
            lIIIlIIllllIlIl.lvlUp(llIllIll[1]);
        }

        public void lvlUp(Boolean lIIIlIIlllIllll) {
            Millora lIIIlIIllllIIlI;
            if (Millora.llIlIlIlII((int)lIIIlIIlllIllll.booleanValue())) {
                lIIIlIIllllIIlI.Turret.this.xp -= lIIIlIIllllIIlI.getCost();
            }
            lIIIlIIllllIIlI.lvl += llIllIll[2];
            lIIIlIIllllIIlI.acci\u00f3lvlUp();
            if (Millora.llIlIlIlIl((int)lIIIlIIllllIIlI.Turret.this.isAdmin.booleanValue())) {
                lIIIlIIllllIIlI.Turret.this.updateChildStats();
            }
        }

        private static String lIlIIllIlI(String lIIIlIIlIlIIIIl, String lIIIlIIlIlIIIlI) {
            try {
                SecretKeySpec lIIIlIIlIlIIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlIIlIlIIIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIlIIlIlIIlIl = Cipher.getInstance("Blowfish");
                lIIIlIIlIlIIlIl.init(llIllIll[4], lIIIlIIlIlIIllI);
                return new String(lIIIlIIlIlIIlIl.doFinal(Base64.getDecoder().decode(lIIIlIIlIlIIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlIIlIlIIlII) {
                lIIIlIIlIlIIlII.printStackTrace();
                return null;
            }
        }

        private static boolean llIlIlIlII(int n) {
            return n == 0;
        }

        private static boolean llIlIlIIll(int n, int n2) {
            return n > n2;
        }

        private static String lIlIIllIIl(String lIIIlIIllIIIlIl, String lIIIlIIlIllllll) {
            lIIIlIIllIIIlIl = new String(Base64.getDecoder().decode(lIIIlIIllIIIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIIlIIllIIIIll = new StringBuilder();
            char[] lIIIlIIllIIIIlI = lIIIlIIlIllllll.toCharArray();
            int lIIIlIIllIIIIIl = llIllIll[1];
            char[] lIIIlIIlIlllIll = lIIIlIIllIIIlIl.toCharArray();
            int lIIIlIIlIlllIlI = lIIIlIIlIlllIll.length;
            int lIIIlIIlIlllIIl = llIllIll[1];
            while (Millora.llIlIllIlI(lIIIlIIlIlllIIl, lIIIlIIlIlllIlI)) {
                char lIIIlIIllIIIllI = lIIIlIIlIlllIll[lIIIlIIlIlllIIl];
                "".length();
                lIIIlIIllIIIIll.append((char)(lIIIlIIllIIIllI ^ lIIIlIIllIIIIlI[lIIIlIIllIIIIIl % lIIIlIIllIIIIlI.length]));
                ++lIIIlIIllIIIIIl;
                ++lIIIlIIlIlllIIl;
                "".length();
                if (-"  ".length() < 0) continue;
                return null;
            }
            return String.valueOf(lIIIlIIllIIIIll);
        }

        private static boolean llIlIllIlI(int n, int n2) {
            return n < n2;
        }

        public void acci\u00f3lvlUp() {
            Millora lIIIlIIllIlIIlI;
            switch (2.$SwitchMap$com$biel$lobby$utilities$Turret$TipusMillora[lIIIlIIllIlIIlI.tipus.ordinal()]) {
                case 1: {
                    lIIIlIIllIlIIlI.Turret.this.Atac += llIllIll[4];
                    "".length();
                    if ((159 ^ 154) != 0) break;
                    return;
                }
                case 2: {
                    int lIIIlIIllIlIIll = lIIIlIIllIlIIlI.Turret.this.VelAtac - llIllIll[4];
                    if (Millora.llIlIlIllI(lIIIlIIllIlIIll, llIllIll[7])) {
                        lIIIlIIllIlIIll = llIllIll[7];
                    }
                    lIIIlIIllIlIIlI.Turret.this.VelAtac = lIIIlIIllIlIIll;
                    lIIIlIIllIlIIlI.Turret.this.Stop();
                    lIIIlIIllIlIIlI.Turret.this.Attack();
                    "".length();
                    if (null == null) break;
                    return;
                }
                case 3: {
                    lIIIlIIllIlIIlI.Turret.this.foc = llIllIll[2];
                    "".length();
                    if ((104 ^ 108) != "   ".length()) break;
                    return;
                }
                case 4: {
                    lIIIlIIllIlIIlI.name = lIlIllII[llIllIll[32]];
                    lIIIlIIllIlIIlI.Turret.this.distAtac += llIllIll[4];
                    "".length();
                    if (-" ".length() < "   ".length()) break;
                    return;
                }
                case 5: {
                    lIIIlIIllIlIIlI.Turret.this.maxHpEscut += llIllIll[11];
                    lIIIlIIllIlIIlI.Turret.this.hp += llIllIll[4];
                    lIIIlIIllIlIIlI.Turret.this.tempsEscut -= llIllIll[8];
                    if (!Millora.llIlIllIlI(lIIIlIIllIlIIlI.Turret.this.tempsEscut, llIllIll[7])) break;
                    lIIIlIIllIlIIlI.Turret.this.tempsEscut = llIllIll[7];
                    "".length();
                    if ("   ".length() > "  ".length()) break;
                    return;
                }
                case 7: {
                    "".length();
                    if (((172 ^ 141) & ~(91 ^ 122)) <= (25 ^ 29)) break;
                    return;
                }
                case 8: {
                    "".length();
                    if ((53 ^ 35 ^ (178 ^ 160)) == (28 + 44 - -40 + 61 ^ 116 + 69 - 117 + 101)) break;
                    return;
                }
                case 9: {
                    lIIIlIIllIlIIlI.Turret.this.xpPerTir *= llIllIll[4];
                    "".length();
                    if ((90 ^ 94) > 0) break;
                    return;
                }
            }
        }

        private static boolean llIlIlIlIl(int n) {
            return n != 0;
        }

        void upgradeMaximum() {
            Millora lIIIlIIlllIllIl;
            while (Millora.llIlIlIlIl((int)lIIIlIIlllIllIl.possibleUpgrade().booleanValue())) {
                lIIIlIIlllIllIl.lvlUp();
                "".length();
                if ((30 ^ 17 ^ (65 ^ 74)) == (33 ^ 101 ^ (112 ^ 48))) continue;
                return;
            }
        }

        ItemStack toItemStack() {
            Millora lIIIlIIllIllIll;
            ItemStack lIIIlIIlllIIIII = new ItemStack(lIIIlIIllIllIll.material, llIllIll[2]);
            ItemMeta lIIIlIIllIlllll = lIIIlIIlllIIIII.getItemMeta();
            ChatColor lIIIlIIllIllllI = ChatColor.GREEN;
            if (Millora.llIlIlIlII((int)lIIIlIIllIllIll.possibleUpgrade().booleanValue())) {
                lIIIlIIllIllllI = ChatColor.YELLOW;
            }
            String lIIIlIIllIlllIl = lIIIlIIllIllIll.name;
            if (Millora.llIlIllIIl(lIIIlIIllIllIll.lvl)) {
                String lIIIlIIlllIIIlI = String.valueOf(new StringBuilder().append(lIlIllII[llIllIll[29]]).append(Integer.toString(lIIIlIIllIllIll.lvl)).append(lIlIllII[llIllIll[30]]));
                if (Millora.llIlIlIlIl((int)lIIIlIIllIllIll.getMaxed().booleanValue())) {
                    lIIIlIIlllIIIlI = String.valueOf(new StringBuilder().append(lIIIlIIlllIIIlI).append((Object)ChatColor.RED).append(lIlIllII[llIllIll[17]]));
                }
                lIIIlIIllIlllIl = String.valueOf(new StringBuilder().append(lIIIlIIllIllIll.name).append((Object)ChatColor.BLUE).append(lIIIlIIlllIIIlI));
            }
            lIIIlIIllIlllll.setDisplayName(String.valueOf(new StringBuilder().append((Object)lIIIlIIllIllllI).append(lIIIlIIllIlllIl)));
            ArrayList<String> lIIIlIIllIlllII = new ArrayList<String>();
            "".length();
            lIIIlIIllIlllII.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIlIIllIllIll.Description)));
            if (Millora.llIlIlIlII((int)lIIIlIIllIllIll.getMaxed().booleanValue())) {
                "".length();
                lIIIlIIllIlllII.add(String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIlIllII[llIllIll[31]]).append(Integer.toString(lIIIlIIllIllIll.getCost())).append(lIlIllII[llIllIll[25]])));
            }
            lIIIlIIllIlllll.setLore(lIIIlIIllIlllII);
            "".length();
            lIIIlIIlllIIIII.setItemMeta(lIIIlIIllIlllll);
            return lIIIlIIlllIIIII;
        }

        private static String lIlIlIIlII(String lIIIlIIlIlIlllI, String lIIIlIIlIlIllll) {
            try {
                SecretKeySpec lIIIlIIlIllIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIlIIlIlIllll.getBytes(StandardCharsets.UTF_8)), llIllIll[14]), "DES");
                Cipher lIIIlIIlIllIIlI = Cipher.getInstance("DES");
                lIIIlIIlIllIIlI.init(llIllIll[4], lIIIlIIlIllIIll);
                return new String(lIIIlIIlIllIIlI.doFinal(Base64.getDecoder().decode(lIIIlIIlIlIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlIIlIllIIIl) {
                lIIIlIIlIllIIIl.printStackTrace();
                return null;
            }
        }

        Boolean possibleUpgrade() {
            Millora lIIIlIIlllIlIlI;
            int n;
            if (Millora.llIlIlIllI(lIIIlIIlllIlIlI.getCost(), lIIIlIIlllIlIlI.Turret.this.xp) && Millora.llIlIlIlII((int)lIIIlIIlllIlIlI.getMaxed().booleanValue())) {
                n = llIllIll[2];
                "".length();
                if (null != null) {
                    return null;
                }
            } else {
                n = llIllIll[1];
            }
            return (boolean)n;
        }

        static {
            Millora.llIlIlIIIl();
            Millora.lIlIllIlIl();
        }
    }

}

