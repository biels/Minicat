/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Blaze
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocCooperatiu;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class RoboRampage
extends JocCooperatiu {
    private static final /* synthetic */ int[] lIlllIl;
    private static final /* synthetic */ String[] lIIIIIl;
    /* synthetic */ int tid;

    public void spawnZombie() {
        RoboRampage llIIlllIIIIIIII;
        Location llIIlllIIIIIIll = llIIlllIIIIIIII.getRandomLocation().add(new Vector(0.5, 4.0, 0.5));
        Zombie llIIlllIIIIIIlI = (Zombie)llIIlllIIIIIIII.world.spawnEntity(llIIlllIIIIIIll, EntityType.ZOMBIE);
        llIIlllIIIIIIlI.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        llIIlllIIIIIIlI.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        llIIlllIIIIIIlI.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        llIIlllIIIIIIlI.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
        ItemStack llIIlllIIIIIIIl = new ItemStack(Material.IRON_HELMET);
        if (RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[9]))) {
            llIIlllIIIIIIIl = new ItemStack(Material.IRON_BLOCK);
        }
        if (RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[8]))) {
            llIIlllIIIIIIIl = new ItemStack(Material.REDSTONE_BLOCK);
        }
        llIIlllIIIIIIlI.getEquipment().setHelmet(llIIlllIIIIIIIl);
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent llIIllIllIlllIl, Block llIIllIllIlllII) {
        RoboRampage llIIllIllIllllI;
        super.onBlockPlace(llIIllIllIlllIl, llIIllIllIlllII);
        llIIllIllIllllI.updateScoreBoards();
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIIlllIlIlIIIl) {
        ArrayList<ItemStack> llIIlllIlIlIIII = new ArrayList<ItemStack>();
        "".length();
        llIIlllIlIlIIII.add(new ItemStack(Material.DIAMOND_SWORD, lIlllIl[0]));
        return llIIlllIlIlIIII;
    }

    public void spawnBlaze() {
        RoboRampage llIIllIlllIllIl;
        Location llIIllIlllIllII = llIIllIlllIllIl.getRandomLocation().add(new Vector(0.5, 5.0, 0.5));
        Blaze llIIllIlllIlIll = (Blaze)llIIllIlllIllIl.world.spawnEntity(llIIllIlllIllII, EntityType.BLAZE);
    }

    @Override
    protected void teletransportarTothom() {
        RoboRampage llIIlllIlIIlIlI;
        Iterator<Player> llIIlllIlIIlIII = llIIlllIlIIlIlI.getPlayers().iterator();
        while (RoboRampage.lIlIIllll((int)llIIlllIlIIlIII.hasNext())) {
            Player llIIlllIlIIlIll = llIIlllIlIIlIII.next();
            "".length();
            llIIlllIlIIlIll.teleport(llIIlllIlIIlIlI.getCenterOfBattle());
            "".length();
            if ("   ".length() == "   ".length()) continue;
            return;
        }
    }

    public Location getRandomLocation() {
        int llIIlllIIIIllIl = lIlllIl[1];
        while (RoboRampage.lIlIlIIIl(llIIlllIIIIllIl, lIlllIl[7])) {
            RoboRampage llIIlllIIIIllII;
            ++llIIlllIIIIllIl;
            Iterator<Block> llIIlllIIIIlIlI = llIIlllIIIIllII.getBattleCuboid(lIlllIl[1]).iterator();
            while (RoboRampage.lIlIIllll((int)llIIlllIIIIlIlI.hasNext())) {
                Block llIIlllIIIIllll = llIIlllIIIIlIlI.next();
                if (RoboRampage.lIlIIllll((int)Utils.Possibilitat(llIIlllIIIIllIl)) && RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[8]))) {
                    return llIIlllIIIIllII.world.getHighestBlockAt(llIIlllIIIIllll.getLocation()).getLocation();
                }
                "".length();
                if (null == null) continue;
                return null;
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return null;
    }

    @Override
    protected void customJocFinalitzat() {
        RoboRampage llIIlllIIlllllI;
        llIIlllIIlllllI.plugin.getServer().getScheduler().cancelTask(llIIlllIIlllllI.tid);
    }

    static {
        RoboRampage.lIlIIlllI();
        RoboRampage.lIIIIlIII();
    }

    private static void lIlIIlllI() {
        lIlllIl = new int[15];
        RoboRampage.lIlllIl[0] = " ".length();
        RoboRampage.lIlllIl[1] = (169 ^ 143) & ~(100 ^ 66);
        RoboRampage.lIlllIl[2] = "  ".length();
        RoboRampage.lIlllIl[3] = "   ".length();
        RoboRampage.lIlllIl[4] = 130 ^ 133;
        RoboRampage.lIlllIl[5] = 40 + 134 - 142 + 121 + (45 ^ 55) - (26 ^ 44) + (57 + 101 - 86 + 58);
        RoboRampage.lIlllIl[6] = "  ".length() ^ (72 ^ 78);
        RoboRampage.lIlllIl[7] = 214 ^ 194;
        RoboRampage.lIlllIl[8] = 70 ^ 76;
        RoboRampage.lIlllIl[9] = 91 ^ 84;
        RoboRampage.lIlllIl[10] = 224 ^ 167 ^ (241 ^ 186);
        RoboRampage.lIlllIl[11] = 142 ^ 128;
        RoboRampage.lIlllIl[12] = -(-11209 & 16363) & (-19022 & 24575);
        RoboRampage.lIlllIl[13] = 181 ^ 197 ^ (13 ^ 120);
        RoboRampage.lIlllIl[14] = 184 ^ 190 ^ (1 ^ 15);
    }

    private static String lllIIlII(String llIIllIlIlIIlII, String llIIllIlIlIIIIl) {
        try {
            SecretKeySpec llIIllIlIlIIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIllIlIlIIIIl.getBytes(StandardCharsets.UTF_8)), lIlllIl[14]), "DES");
            Cipher llIIllIlIlIIllI = Cipher.getInstance("DES");
            llIIllIlIlIIllI.init(lIlllIl[2], llIIllIlIlIIlll);
            return new String(llIIllIlIlIIllI.doFinal(Base64.getDecoder().decode(llIIllIlIlIIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllIlIlIIlIl) {
            llIIllIlIlIIlIl.printStackTrace();
            return null;
        }
    }

    @Override
    public String getGameName() {
        return lIIIIIl[lIlllIl[1]];
    }

    public Cuboid getBattleCuboid(boolean llIIlllIIlIlIII) {
        RoboRampage llIIlllIIlIlllI;
        int llIIlllIIlIllII = lIlllIl[4];
        int llIIlllIIlIlIll = lIlllIl[0];
        if (RoboRampage.lIlIIllll((int)llIIlllIIlIlIII)) {
            llIIlllIIlIlIll = lIlllIl[5];
        }
        Location llIIlllIIlIlIlI = llIIlllIIlIlllI.getCenterOfBattle();
        llIIlllIIlIlIlI.setY(0.0);
        return Utils.getCuboidCenteredOnBase(llIIlllIIlIlIlI, llIIlllIIlIllII, llIIlllIIlIlIll, llIIlllIIlIllII);
    }

    private static boolean lIlIlIlII(Object object, Object object2) {
        return object != object2;
    }

    @Override
    protected void customJocIniciat() {
        RoboRampage llIIlllIlIIIlII;
        llIIlllIlIIIlII.updateScoreBoards();
        llIIlllIlIIIlII.ProgTask();
    }

    private static String lIIIIIlII(String llIIllIlIIIIlll, String llIIllIlIIIIIIl) {
        llIIllIlIIIIlll = new String(Base64.getDecoder().decode(llIIllIlIIIIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIllIlIIIIlIl = new StringBuilder();
        char[] llIIllIlIIIIlII = llIIllIlIIIIIIl.toCharArray();
        int llIIllIlIIIIIll = lIlllIl[1];
        char[] llIIllIIlllllIl = llIIllIlIIIIlll.toCharArray();
        int llIIllIIlllllII = llIIllIIlllllIl.length;
        int llIIllIIllllIll = lIlllIl[1];
        while (RoboRampage.lIlIlIIIl(llIIllIIllllIll, llIIllIIlllllII)) {
            char llIIllIlIIIlIII = llIIllIIlllllIl[llIIllIIllllIll];
            "".length();
            llIIllIlIIIIlIl.append((char)(llIIllIlIIIlIII ^ llIIllIlIIIIlII[llIIllIlIIIIIll % llIIllIlIIIIlII.length]));
            ++llIIllIlIIIIIll;
            ++llIIllIIllllIll;
            "".length();
            if ("   ".length() > 0) continue;
            return null;
        }
        return String.valueOf(llIIllIlIIIIlIl);
    }

    @Override
    protected void setCustomGameRules() {
        RoboRampage llIIlllIlIIIIlI;
        super.setCustomGameRules();
    }

    public void spawnSkeleton() {
        RoboRampage llIIllIlllllIII;
        Location llIIllIllllIlll = llIIllIlllllIII.getRandomLocation().add(new Vector(0.5, 4.0, 0.5));
        Skeleton llIIllIllllIllI = (Skeleton)llIIllIlllllIII.world.spawnEntity(llIIllIllllIlll, EntityType.SKELETON);
        llIIllIllllIllI.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        llIIllIllllIllI.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        llIIllIllllIllI.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        llIIllIllllIllI.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
        ItemStack llIIllIllllIlIl = new ItemStack(Material.IRON_HELMET);
        if (RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[9]))) {
            llIIllIllllIlIl = new ItemStack(Material.IRON_BLOCK);
        }
        if (RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[10]))) {
            llIIllIllllIlIl = new ItemStack(Material.REDSTONE_BLOCK);
        }
        if (RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[11]))) {
            llIIllIllllIlIl = new ItemStack(Material.LAPIS_BLOCK);
        }
        llIIllIllllIllI.getEquipment().setHelmet(llIIllIllllIlIl);
    }

    @Override
    protected void updateScoreBoard(Player llIIlllIIllIlll) {
        RoboRampage llIIlllIIllIllI;
        super.updateScoreBoard(llIIlllIIllIlll);
        if (RoboRampage.lIlIIllll((int)llIIlllIIllIllI.JocIniciat.booleanValue())) {
            ArrayList<String> llIIlllIIlllIIl = new ArrayList<String>();
            "".length();
            llIIlllIIlllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIIl[lIlllIl[0]])));
            "".length();
            llIIlllIIlllIIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(Integer.toString(llIIlllIIllIllI.getMaxScrapHeight())).append(lIIIIIl[lIlllIl[2]])));
            ScoreBoardUpdater.setScoreBoard(llIIlllIIllIllI.getViewers(), lIIIIIl[lIlllIl[3]], llIIlllIIlllIIl, null);
        }
    }

    private Location getCenterOfBattle() {
        RoboRampage llIIlllIIlIIIlI;
        return llIIlllIIlIIIlI.pMapaActual().ObtenirLocation(lIIIIIl[lIlllIl[6]], llIIlllIIlIIIlI.world);
    }

    protected void onEntityDeath(EntityDeathEvent llIIllIllIIIllI, Entity llIIllIllIIIlIl) {
        RoboRampage llIIllIllIIIlll;
        super.onEntityDeath(llIIllIllIIIllI, llIIllIllIIIlIl);
        Player llIIllIllIIIlII = llIIllIllIIIllI.getEntity().getKiller();
        if (!RoboRampage.lIlIlIlII((Object)llIIllIllIIIllI.getEntityType(), (Object)EntityType.ZOMBIE) || RoboRampage.lIlIlIlIl((Object)llIIllIllIIIllI.getEntityType(), (Object)EntityType.SKELETON)) {
            Zombie llIIllIllIIllIl = (Zombie)llIIllIllIIIllI.getEntity();
            Block llIIllIllIIllII = llIIllIllIIIlll.world.getHighestBlockAt(llIIllIllIIllIl.getLocation().getBlock().getLocation());
            llIIllIllIIllII.setType(Material.IRON_BLOCK);
            Material llIIllIllIIlIll = llIIllIllIIllIl.getEquipment().getHelmet().getType();
            if (RoboRampage.lIlIlIlIl((Object)llIIllIllIIlIll, (Object)Material.REDSTONE_BLOCK)) {
                "".length();
                llIIllIllIIIlII.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, lIlllIl[12], lIlllIl[0]));
            }
            if (RoboRampage.lIlIlIlIl((Object)llIIllIllIIlIll, (Object)Material.LAPIS_BLOCK)) {
                "".length();
                llIIllIllIIIlII.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIlllIl[12], lIlllIl[0]));
            }
            if (RoboRampage.lIlIlIlIl((Object)llIIllIllIIlIll, (Object)Material.CHEST)) {
                // empty if block
            }
            if (RoboRampage.lIlIlIlIl((Object)llIIllIllIIlIll, (Object)Material.IRON_BLOCK)) {
                Byte llIIllIllIIllll = lIlllIl[1];
                FallingBlock llIIllIllIIlllI = llIIllIllIIIlll.world.spawnFallingBlock(llIIllIllIIllIl.getEyeLocation().add(0.0, 0.35, 0.0), Material.IRON_BLOCK, llIIllIllIIllll.byteValue());
                llIIllIllIIlllI.setVelocity(Utils.CrearVector(llIIllIllIIIlII.getLocation(), llIIllIllIIllIl.getEyeLocation()));
                llIIllIllIIlllI.setDropItem(lIlllIl[1]);
            }
        }
        if (RoboRampage.lIlIlIlIl((Object)llIIllIllIIIllI.getEntityType(), (Object)EntityType.BLAZE)) {
            Blaze llIIllIllIIlIlI = (Blaze)llIIllIllIIIllI.getEntity();
            Byte llIIllIllIIlIIl = lIlllIl[1];
            FallingBlock llIIllIllIIlIII = llIIllIllIIIlll.world.spawnFallingBlock(llIIllIllIIlIlI.getLocation().getBlock().getLocation(), Material.GOLD_BLOCK, llIIllIllIIlIIl.byteValue());
            llIIllIllIIlIII.setVelocity(new Vector(lIlllIl[1], lIlllIl[0], lIlllIl[1]));
            llIIllIllIIlIII.setDropItem(lIlllIl[1]);
            llIIllIllIIIlII.setFireTicks(lIlllIl[1]);
        }
        llIIllIllIIIlll.updateScoreBoards();
    }

    private static String lIIIIIlIl(String llIIllIlIIlIlll, String llIIllIlIIlIlII) {
        try {
            SecretKeySpec llIIllIlIIllIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIllIlIIlIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIllIlIIllIIl = Cipher.getInstance("Blowfish");
            llIIllIlIIllIIl.init(lIlllIl[2], llIIllIlIIllIlI);
            return new String(llIIllIlIIllIIl.doFinal(Base64.getDecoder().decode(llIIllIlIIlIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllIlIIllIII) {
            llIIllIlIIllIII.printStackTrace();
            return null;
        }
    }

    public RoboRampage() {
        RoboRampage llIIlllIlIlIlIl;
    }

    private static boolean lIlIlIIII(int n, int n2) {
        return n > n2;
    }

    private static boolean lIlIlIlIl(Object object, Object object2) {
        return object == object2;
    }

    public void ProgTask() {
        RoboRampage llIIllIlllIIIll;
        llIIllIlllIIIll.tid = llIIllIlllIIIll.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)llIIllIlllIIIll.plugin, () -> {
            RoboRampage llIIllIlIlIllII;
            llIIllIlIlIllII.spawnEnemy();
        }, 1L, 40L);
    }

    private static boolean lIlIlIIIl(int n, int n2) {
        return n < n2;
    }

    public int getMaxScrapHeight() {
        RoboRampage llIIlllIIIllIlI;
        int llIIlllIIIllIIl = lIlllIl[1];
        Iterator<Block> llIIlllIIIlIllI = llIIlllIIIllIlI.getBattleCuboid(lIlllIl[1]).iterator();
        while (RoboRampage.lIlIIllll((int)llIIlllIIIlIllI.hasNext())) {
            Block llIIlllIIIllIll = llIIlllIIIlIllI.next();
            int llIIlllIIIlllII = llIIlllIIIllIlI.world.getHighestBlockYAt(llIIlllIIIllIll.getLocation()) - lIlllIl[0];
            if (RoboRampage.lIlIlIIII(llIIlllIIIlllII, llIIlllIIIllIIl)) {
                llIIlllIIIllIIl = llIIlllIIIlllII;
            }
            "".length();
            if ((45 + 6 - -99 + 38 ^ 12 + 130 - -40 + 2) >= "   ".length()) continue;
            return (170 ^ 136 ^ (71 ^ 119)) & ((153 ^ 182) & ~(102 ^ 73) ^ (118 ^ 100) ^ -" ".length());
        }
        return llIIlllIIIllIIl;
    }

    private static boolean lIlIIllll(int n) {
        return n != 0;
    }

    public void spawnEnemy() {
        RoboRampage llIIllIlllIIlIl;
        do {
            if (RoboRampage.lIlIlIIIl(llIIllIlllIIlIl.world.getEntitiesByClass(Zombie.class).size(), llIIllIlllIIlIl.getMaxScrapHeight() / lIlllIl[2] + lIlllIl[2]) && RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[7]))) {
                llIIllIlllIIlIl.spawnZombie();
                return;
            }
            if (!RoboRampage.lIlIlIIIl(llIIllIlllIIlIl.world.getEntitiesByClass(Skeleton.class).size(), llIIllIlllIIlIl.getMaxScrapHeight() / lIlllIl[2]) || !RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[7]))) continue;
            llIIllIlllIIlIl.spawnSkeleton();
            return;
        } while (!RoboRampage.lIlIlIIIl(llIIllIlllIIlIl.world.getEntitiesByClass(Blaze.class).size(), llIIllIlllIIlIl.getMaxScrapHeight() / lIlllIl[3]) || !RoboRampage.lIlIIllll((int)Utils.Possibilitat(lIlllIl[7])));
        llIIllIlllIIlIl.spawnBlaze();
    }

    private static void lIIIIlIII() {
        lIIIIIl = new String[lIlllIl[13]];
        RoboRampage.lIIIIIl[RoboRampage.lIlllIl[1]] = RoboRampage.lllIIlII("nkKswGKmPTZP0N/dfRgnMg==", "jAeCm");
        RoboRampage.lIIIIIl[RoboRampage.lIlllIl[0]] = RoboRampage.lllIIlII("RtJyYkc/nkxs+vQcky9++Q==", "uZopN");
        RoboRampage.lIIIIIl[RoboRampage.lIlllIl[2]] = RoboRampage.lIIIIIlII("UQM=", "qnJKE");
        RoboRampage.lIIIIIl[RoboRampage.lIlllIl[3]] = RoboRampage.lIIIIIlIl("1+bmfo+4RlJvef9xesbqqA==", "aFBOf");
        RoboRampage.lIIIIIl[RoboRampage.lIlllIl[6]] = RoboRampage.lIIIIIlIl("8GXSWhHwkGI=", "qBxWL");
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent llIIllIlIllIIIl, Entity llIIllIlIllIlII, Entity llIIllIlIlIllll) {
        RoboRampage llIIllIlIllIllI;
        super.onEntityDamageByEntity(llIIllIlIllIIIl, llIIllIlIllIlII, llIIllIlIlIllll);
        if (RoboRampage.lIlIIllll(llIIllIlIllIlII instanceof Player)) {
            if (RoboRampage.lIlIIllll(llIIllIlIlIllll instanceof Player)) {
                llIIllIlIllIIIl.setCancelled(lIlllIl[0]);
            }
            llIIllIlIllIIIl.setDamage(llIIllIlIllIIIl.getDamage() / 4.0);
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        } else {
            llIIllIlIllIIIl.setDamage(llIIllIlIllIIIl.getDamage() * 2.65);
        }
    }
}

