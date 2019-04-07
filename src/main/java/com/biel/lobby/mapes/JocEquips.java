/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.IconMenu
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEvent
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEventHandler
 *  com.biel.BielAPI.Utils.ItemButton
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEvent
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEventHandler
 *  com.biel.BielAPI.Utils.RecallUtils
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.material.Wool
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.Utils.RecallUtils;
import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.MatchData;
import com.biel.lobby.utilities.data.PlayerData;
import com.biel.lobby.utilities.events.skills.Skill;
import com.biel.lobby.utilities.events.skills.SkillPool;
import com.google.common.collect.Lists;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public abstract class JocEquips
extends Joc {
    private /* synthetic */ boolean equipsBloquejats;
    public /* synthetic */ TeamGenerationMode generationMode;
    public /* synthetic */ ArrayList<Equip> Equips;
    private static final /* synthetic */ String[] lIll;
    private static final /* synthetic */ int[] lIlllI;

    public void resetTeams() {
        JocEquips llIllllIIllIlIl;
        llIllllIIllIlIl.initTeams();
        llIllllIIllIlIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_BLUE).append(lIll[lIlllI[23]])));
        llIllllIIllIlIl.generationMode = TeamGenerationMode.DEFAULT;
    }

    public <T extends Equip> T obtenirEquipEnemic(T lllIIIIIIllIIlI) {
        JocEquips lllIIIIIIllIlIl;
        if (JocEquips.llIllllI(lllIIIIIIllIlIl.Equips.size(), lIlllI[3])) {
            Iterator<Equip> lllIIIIIIllIIIl = lllIIIIIIllIlIl.Equips.iterator();
            while (JocEquips.llIlIIII((int)lllIIIIIIllIIIl.hasNext())) {
                Equip lllIIIIIIllIllI = lllIIIIIIllIIIl.next();
                if (JocEquips.llIlIlll((int)lllIIIIIIllIllI.equals(lllIIIIIIllIIlI))) {
                    return (T)lllIIIIIIllIllI;
                }
                "".length();
                if (null == null) continue;
                return null;
            }
        }
        "".length();
        Bukkit.broadcastMessage((String)lIll[lIlllI[10]]);
        return null;
    }

    public JocEquips() {
        JocEquips lllIIIlIIlllllI;
        lllIIIlIIlllllI.equipsBloquejats = lIlllI[0];
        lllIIIlIIlllllI.Equips = new ArrayList();
        lllIIIlIIlllllI.generationMode = TeamGenerationMode.DEFAULT;
    }

    public void ferEquipsAleatoris(boolean llIllllIIIIIlII) {
        JocEquips llIlllIllllllII;
        if (JocEquips.llIlIlll(llIlllIllllllII.Equips.size())) {
            return;
        }
        ArrayList<Player> llIllllIIIIIIlI = new ArrayList<Player>(llIlllIllllllII.getPlayers());
        Collections.shuffle(llIllllIIIIIIlI);
        int llIllllIIIIIIII = lIlllI[0];
        int llIlllIlllllllI = lIlllI[0];
        int llIlllIllllllIl = llIlllIllllllII.Equips.size() * lIlllI[5];
        Iterator<Player> llIlllIllllIllI = llIllllIIIIIIlI.iterator();
        while (JocEquips.llIlIIII((int)llIlllIllllIllI.hasNext())) {
            Equip llIllllIIIIlIlI;
            Player llIllllIIIIlIIl = llIlllIllllIllI.next();
            if (JocEquips.lllIIIIl(llIlllIlllllllI, llIlllIllllllIl)) {
                return;
            }
            ++llIlllIlllllllI;
            if (JocEquips.llIlIlll((int)llIllllIIIIIlII) && JocEquips.llIlllIl(llIlllIllllllII.obtenirEquip(llIllllIIIIlIIl))) {
                "".length();
                if (-(10 ^ 41 ^ (179 ^ 148)) <= 0) continue;
                return;
            }
            if (JocEquips.lllIIIIl(llIllllIIIIIIII, llIlllIllllllII.Equips.size() - lIlllI[2])) {
                llIllllIIIIIIII = lIlllI[0];
            }
            if (JocEquips.lllIIIlI(JocEquips.lllIIIII((llIllllIIIIlIlI = llIlllIllllllII.Equips.get(llIllllIIIIIIII)).getPlayers().size(), Math.ceil(llIlllIllllllII.getTeamSize())))) {
                "".length();
                if (((3 ^ 75) & ~(79 ^ 7)) == 0) continue;
                return;
            }
            llIlllIllllllII.establirEquipJugador(llIllllIIIIlIIl, llIllllIIIIlIlI);
            ++llIllllIIIIIIII;
            "".length();
            if ("   ".length() >= "   ".length()) continue;
            return;
        }
        llIlllIllllllII.generationMode = TeamGenerationMode.RANDOM;
    }

    protected void establirColorsNoms() {
        JocEquips lllIIIlIIIIlIII;
        Iterator<Equip> lllIIIlIIIIIlll = lllIIIlIIIIlIII.Equips.iterator();
        while (JocEquips.llIlIIII((int)lllIIIlIIIIIlll.hasNext())) {
            Equip lllIIIlIIIIlIlI = lllIIIlIIIIIlll.next();
            Iterator<Player> lllIIIlIIIIIlIl = lllIIIlIIIIlIlI.getPlayers().iterator();
            while (JocEquips.llIlIIII((int)lllIIIlIIIIIlIl.hasNext())) {
                Player lllIIIlIIIIlIll = lllIIIlIIIIIlIl.next();
                lllIIIlIIIIlIll.setDisplayName(String.valueOf(new StringBuilder().append((Object)lllIIIlIIIIlIlI.getChatColor()).append(lllIIIlIIIIlIll.getName()).append((Object)ChatColor.WHITE)));
                "".length();
                if (((67 ^ 90 ^ (47 ^ 86)) & (130 ^ 179 ^ (205 ^ 156) ^ -" ".length())) == 0) continue;
                return;
            }
            "".length();
            if ((" ".length() & (" ".length() ^ -" ".length())) <= 0) continue;
            return;
        }
    }

    private static boolean llIllllI(int n, int n2) {
        return n == n2;
    }

    private static boolean llIlIIII(int n) {
        return n != 0;
    }

    ForcefieldType getForcefieldType() {
        JocEquips llIllIlIlllIlIl;
        ForcefieldType llIllIlIlllIlII = ForcefieldType.VERTICAL;
        try {
            if (JocEquips.llIlIIII((int)llIllIlIlllIlIl.pMapaActual().ExisteixPropietat(lIll[lIlllI[38]]))) {
                llIllIlIlllIlII = ForcefieldType.valueOf(llIllIlIlllIlIl.pMapaActual().ObtenirPropietat(lIll[lIlllI[39]]));
            }
            "".length();
            if (null != null) {
                return null;
            }
        }
        catch (Exception llIllIlIlllIllI) {
            llIllIlIlllIlIl.sendGlobalMessage(lIll[lIlllI[40]]);
        }
        return llIllIlIlllIlII;
    }

    private static String lIIlIl(String llIlIllIlIlllII, String llIlIllIlIllllI) {
        try {
            SecretKeySpec llIlIllIllIIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIllIlIllllI.getBytes(StandardCharsets.UTF_8)), lIlllI[9]), "DES");
            Cipher llIlIllIllIIIll = Cipher.getInstance("DES");
            llIlIllIllIIIll.init(lIlllI[3], llIlIllIllIIlII);
            return new String(llIlIllIllIIIll.doFinal(Base64.getDecoder().decode(llIlIllIlIlllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIllIllIIIIl) {
            llIlIllIllIIIIl.printStackTrace();
            return null;
        }
    }

    public void sendTeamMessage(Equip llIlllllIlIllIl, String llIlllllIlIllII) {
        JocEquips llIlllllIlIlllI;
        if (JocEquips.llIIlllI((Object)llIlllllIlIlllI.world)) {
            return;
        }
        Iterator<Player> llIlllllIlIlIll = llIlllllIlIllIl.getPlayers().iterator();
        while (JocEquips.llIlIIII((int)llIlllllIlIlIll.hasNext())) {
            Player llIlllllIllIIlI = llIlllllIlIlIll.next();
            llIlllllIlIlllI.sendPlayerMessage(llIlllllIllIIlI, llIlllllIlIllII);
            "".length();
            if (((213 ^ 193) & ~(166 ^ 178)) >= 0) continue;
            return;
        }
    }

    @Override
    public void giveFixedPlaceItems(Player llIllIllllIIIll) {
        JocEquips llIllIllllIIlIl;
        super.giveFixedPlaceItems(llIllIllllIIIll);
        if (JocEquips.llIlIIII((int)llIllIllllIIlIl.isRecallEnabled()) && JocEquips.llIlIlll(llIllIllllIIlIl.getPlayerInfo(llIllIllllIIIll).getUnselectedSkillAmount())) {
            llIllIllllIIlIl.obtenirEquip(llIllIllllIIIll).giveRecallButton(llIllIllllIIIll);
        }
    }

    void keepAwayFromForcefields(Player llIllIlIIIlllIl) {
        JocEquips llIllIlIIlIIIlI;
        if (JocEquips.llIlIlll((int)llIllIlIIlIIIlI.isForcefieldEnabled())) {
            return;
        }
        int llIllIlIIlIIIII = llIllIlIIlIIIlI.getProtectionRadius();
        ArrayList<Location> llIllIlIIIlllll = llIllIlIIlIIIlI.getTeamForcefields(llIllIlIIlIIIlI.obtenirEquipEnemic(llIllIlIIIlllIl));
        Iterator<Location> llIllIlIIIllIlI = llIllIlIIIlllll.iterator();
        while (JocEquips.llIlIIII((int)llIllIlIIIllIlI.hasNext())) {
            Location llIllIlIIlIIlII = llIllIlIIIllIlI.next();
            Location llIllIlIIlIIlll = llIllIlIIlIIIlI.normalizeForcefield(llIllIlIIlIIlII, llIllIlIIIlllIl);
            if (JocEquips.lIIIIlllI(JocEquips.lIIIIllIl(llIllIlIIIlllIl.getLocation().distance(llIllIlIIlIIlll), llIllIlIIlIIIII))) {
                llIllIlIIlIIIlI.forcefieldKickOff(llIllIlIIIlllIl, llIllIlIIlIIlll);
            }
            "".length();
            if ("   ".length() != 0) continue;
            return;
        }
    }

    public Location getHalfwayMiddle() {
        JocEquips llIllllllIIIlII;
        ArrayList<Vector> llIllllllIIIlIl = new ArrayList<Vector>();
        Iterator<Equip> llIllllllIIIIlI = llIllllllIIIlII.Equips.iterator();
        while (JocEquips.llIlIIII((int)llIllllllIIIIlI.hasNext())) {
            Equip llIllllllIIlIIl = llIllllllIIIIlI.next();
            "".length();
            llIllllllIIIlIl.add(llIllllllIIlIIl.getTeamSpawnLocation().toVector());
            "".length();
            if (-" ".length() <= "   ".length()) continue;
            return null;
        }
        return Utils.averageVector(llIllllllIIIlIl).toLocation(llIllllllIIIlII.getWorld());
    }

    int getProtectionRadius() {
        JocEquips llIllIlIllIIlIl;
        int llIllIlIllIIlII = lIlllI[11];
        if (JocEquips.llIlIIII((int)llIllIlIllIIlIl.pMapaActual().ExisteixPropietat(lIll[lIlllI[41]]))) {
            llIllIlIllIIlII = llIllIlIllIIlIl.pMapaActual().ObtenirPropietatInt(lIll[lIlllI[42]]);
        }
        return llIllIlIllIIlII;
    }

    public void establirEquipJugador(Player lllIIIIlIIllIll, Equip lllIIIIlIIllIlI) {
        if (JocEquips.llIlIlll((int)lllIIIIlIIllIlI.getPlayers().contains((Object)lllIIIIlIIllIll))) {
            JocEquips lllIIIIlIIlllII;
            Iterator<Equip> lllIIIIlIIlIllI = lllIIIIlIIlllII.Equips.iterator();
            while (JocEquips.llIlIIII((int)lllIIIIlIIlIllI.hasNext())) {
                Equip lllIIIIlIIlllIl = lllIIIIlIIlIllI.next();
                if (JocEquips.llIIllll(lllIIIIlIIlllIl, lllIIIIlIIllIlI)) {
                    lllIIIIlIIlllIl.addPlayer(lllIIIIlIIllIll);
                    "".length();
                    if (("  ".length() & ~"  ".length()) > 0) {
                        return;
                    }
                } else {
                    lllIIIIlIIlllIl.removePlayer(lllIIIIlIIllIll);
                }
                "".length();
                if (" ".length() > ((5 ^ 44) & ~(2 ^ 43))) continue;
                return;
            }
            if (JocEquips.llIIllll((Object)lllIIIIlIIlllII.generationMode, (Object)TeamGenerationMode.RANDOM)) {
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                lllIIIIlIIlllII.sendGlobalMessage(String.valueOf(new StringBuilder().append(lllIIIIlIIlllII.getGameDisplayName()).append(lllIIIIlIIllIll.getName()).append(lIll[lIlllI[8]]).append((Object)lllIIIIlIIllIlI.getChatColor()).append(lllIIIIlIIllIlI.getAdjectiu())));
            }
            lllIIIIlIIlllII.updateScoreBoards();
            ScoreBoardUpdater.updateTeamScore(lllIIIIlIIlllII);
            lllIIIIlIIlllII.updateHeadColor(lllIIIIlIIllIll);
            lllIIIIlIIlllII.updateScoreBoards();
        }
    }

    public void ferEquipsEquilibrats() {
        JocEquips llIlllIllIIllII;
        int llIlllIllIIlIlI = lIlllI[11] + llIlllIllIIllII.getPlayers().size() * lIlllI[24];
        if (JocEquips.lllIIIIl(llIlllIllIIlIlI, lIlllI[25])) {
            llIlllIllIIlIlI = lIlllI[25];
        }
        if (JocEquips.llIlIlll(llIlllIllIIllII.Equips.size())) {
            return;
        }
        ZonedDateTime llIlllIllIIlIIl = ZonedDateTime.now();
        double llIlllIllIIIlll = Double.NaN;
        Iterable llIlllIllIIIlII = null;
        int llIlllIllIIllIl = lIlllI[0];
        while (JocEquips.llIllIII(llIlllIllIIllIl, llIlllIllIIlIlI)) {
            ArrayList<Player> llIlllIllIlIIlI = new ArrayList<Player>(llIlllIllIIllII.getPlayers());
            Collections.shuffle(llIlllIllIlIIlI);
            List llIlllIllIlIIIl = Lists.partition(llIlllIllIlIIlI, (int)((int)Math.ceil((double)llIlllIllIlIIlI.size() / (double)llIlllIllIIllII.Equips.size())));
            double llIlllIllIIllll = JocEquips.variance(llIlllIllIlIIIl.stream().mapToDouble(llIlIllllIIllII -> llIlIllllIIllII.stream().mapToDouble(llIlIllllIIlIIl -> new PlayerData(llIlIllllIIlIIl).getElo()).sum()).boxed().collect(Collectors.toList()));
            if (!JocEquips.lllIIIlI(JocEquips.lllIIIll(llIlllIllIIllll, llIlllIllIIIlll)) || JocEquips.llIIlllI(llIlllIllIIIlII)) {
                llIlllIllIIIlll = llIlllIllIIllll;
                llIlllIllIIIlII = llIlllIllIlIIIl;
            }
            ++llIlllIllIIllIl;
            "".length();
            if (-" ".length() <= 0) continue;
            return;
        }
        llIlllIllIIllII.resetTeams();
        long llIlllIllIIIIlI = Duration.between(llIlllIllIIlIIl, ZonedDateTime.now()).toMillis();
        Iterable llIlllIllIIIIII = llIlllIllIIIlII;
        if (JocEquips.llIlllIl(llIlllIllIIIIII)) {
            llIlllIllIIIIII.forEach(llIlIlllllIllIl -> {
                JocEquips llIlIllllllIIII;
                llIlIlllllIllIl.forEach(llIlIllllIIllll -> {
                    JocEquips llIlIllllIlIIlI;
                    llIlIllllIlIIlI.establirEquipJugador(llIlIllllIIllll, llIlIllllIlIIlI.Equips.get(llIlIlllllIllll.indexOf(llIlIlllllIllIl)));
                });
            });
        }
        llIlllIllIIllII.generationMode = TeamGenerationMode.BALANCED;
    }

    @Override
    public ArrayList<Player> getEnemies(Player lllIIIIIIIIIIIl) {
        JocEquips lllIIIIIIIIIIlI;
        ArrayList<Player> lllIIIIIIIIIIII = lllIIIIIIIIIIlI.getViewers();
        "".length();
        lllIIIIIIIIIIII.removeAll(lllIIIIIIIIIIlI.obtenirEquip(lllIIIIIIIIIIIl).getPlayers());
        return lllIIIIIIIIIIII;
    }

    public void fixarSpawn(Player llIlllllllIllII) {
        JocEquips llIlllllllIllIl;
        if (JocEquips.llIlllIl(llIlllllllIllIl.obtenirEquip(llIlllllllIllII))) {
            llIlllllllIllII.setBedSpawnLocation(llIlllllllIllIl.obtenirEquip(llIlllllllIllII).getTeamSpawnLocation(), lIlllI[2]);
        }
    }

    public Equip obtenirEquip(int lllIIIIIllIlIlI) {
        JocEquips lllIIIIIllIlIII;
        return lllIIIIIllIlIII.Equips.get(lllIIIIIllIlIlI);
    }

    private static boolean lllIIIIl(int n, int n2) {
        return n > n2;
    }

    static {
        JocEquips.llIIllII();
        JocEquips.lIIIIIll();
    }

    public double getBalancingMultiplier(Player llIlllIlIIIlllI) {
        JocEquips llIlllIlIIIllll;
        return llIlllIlIIIllll.getBalancingMultiplier(llIlllIlIIIllll.obtenirEquip(llIlllIlIIIlllI));
    }

    private static int lIIIlIIll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static int lIIIlIIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent llIllIIIllIllII, Player llIllIIIllIlIlI, Player llIllIIIlllIIlI) {
        JocEquips llIllIIIllIllIl;
        Equip llIllIIIlllIIII = llIllIIIllIllIl.obtenirEquip(llIllIIIllIlIlI);
        Equip llIllIIIllIllll = llIllIIIllIllIl.obtenirEquip(llIllIIIlllIIlI);
        if (JocEquips.llIlllIl(llIllIIIlllIIII) && JocEquips.llIlllIl(llIllIIIllIllll)) {
            llIllIIIllIllII.setDeathMessage(String.valueOf(new StringBuilder().append((Object)llIllIIIlllIIII.getChatColor()).append(llIllIIIllIlIlI.getName()).append((Object)ChatColor.GRAY).append(lIll[lIlllI[48]]).append((Object)llIllIIIllIllll.getChatColor()).append(llIllIIIlllIIlI.getName())));
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIllIllllllIlI) {
        JocEquips llIlllIIIIIIIlI;
        ArrayList<ItemStack> llIlllIIIIIIIII = new ArrayList<ItemStack>();
        Equip llIllIllllllllI = llIlllIIIIIIIlI.obtenirEquip(llIllIllllllIlI);
        "".length();
        llIlllIIIIIIIII.add(new ItemStack(Material.STONE_SWORD, lIlllI[2]));
        "".length();
        llIlllIIIIIIIII.add(new ItemStack(Material.IRON_PICKAXE, lIlllI[2]));
        "".length();
        llIlllIIIIIIIII.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, llIllIllllllllI));
        "".length();
        llIlllIIIIIIIII.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, llIllIllllllllI));
        "".length();
        llIlllIIIIIIIII.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, llIllIllllllllI));
        ItemStack llIllIlllllllIl = new ItemStack(Material.BOW, lIlllI[2]);
        llIllIlllllllIl.addUnsafeEnchantment(Enchantment.DURABILITY, lIlllI[11]);
        "".length();
        llIlllIIIIIIIII.add(llIllIlllllllIl);
        "".length();
        llIlllIIIIIIIII.add(new ItemStack(Material.ARROW, lIlllI[34]));
        "".length();
        llIlllIIIIIIIII.add(new ItemStack(Material.LOG, lIlllI[34]));
        "".length();
        llIlllIIIIIIIII.add(new ItemStack(Material.GRILLED_PORK, lIlllI[35]));
        return llIlllIIIIIIIII;
    }

    private static int llIlllll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    double getEloM() {
        JocEquips lllIIIlIIlIIlIl;
        double lllIIIlIIlIIlII = 1.0 - Math.sqrt(lllIIIlIIlIIlIl.getAvgNumericDeviation());
        if (JocEquips.llIlIllI(JocEquips.llIlIlII(lllIIIlIIlIIlIl.getAvgNumericDeviation(), 0.3))) {
            lllIIIlIIlIIlII = 0.0;
        }
        return lllIIIlIIlIIlII;
    }

    private static boolean lIIIlIIlI(int n) {
        return n < 0;
    }

    int getBaseProtectionRadius() {
        JocEquips llIllIIlllIIlll;
        int llIllIIlllIIlIl = lIlllI[4];
        if (JocEquips.llIlIIII((int)llIllIIlllIIlll.pMapaActual().ExisteixPropietat(lIll[lIlllI[45]]))) {
            llIllIIlllIIlIl = llIllIIlllIIlll.pMapaActual().ObtenirPropietatInt(lIll[lIlllI[46]]);
        }
        return llIllIIlllIIlIl;
    }

    public void fixarSpawns() {
        JocEquips llIllllllIlllll;
        Iterator<Player> llIllllllIlllIl = llIllllllIlllll.getPlayers().iterator();
        while (JocEquips.llIlIIII((int)llIllllllIlllIl.hasNext())) {
            Player llIlllllllIIIIl = llIllllllIlllIl.next();
            llIllllllIlllll.fixarSpawn(llIlllllllIIIIl);
            "".length();
            if (((33 ^ 122 ^ (66 ^ 42)) & (29 + 13 - -18 + 108 ^ 38 + 120 - 103 + 100 ^ -" ".length())) < (97 ^ 118 ^ (124 ^ 111))) continue;
            return;
        }
    }

    public ArrayList<Player> getPlayersOutOfTeam() {
        JocEquips llIllllIlIIIIIl;
        ArrayList<Player> llIllllIlIIIIlI = new ArrayList<Player>();
        Iterator<Player> llIllllIIllllll = llIllllIlIIIIIl.getPlayers().iterator();
        while (JocEquips.llIlIIII((int)llIllllIIllllll.hasNext())) {
            Player llIllllIlIIIlII = llIllllIIllllll.next();
            if (JocEquips.llIIlllI(llIllllIlIIIIIl.obtenirEquip(llIllllIlIIIlII))) {
                "".length();
                llIllllIlIIIIlI.add(llIllllIlIIIlII);
            }
            "".length();
            if (null == null) continue;
            return null;
        }
        return llIllllIlIIIIlI;
    }

    public double getAvgTeamSize() {
        JocEquips lllIIIlIIIlllIl;
        return lllIIIlIIIlllIl.Equips.stream().mapToInt(llIlIlllIllIIll -> llIlIlllIllIIll.getPlayers().size()).average().getAsDouble();
    }

    public void updateHeadColor(Player lllIIIIlIlIIlll) {
        JocEquips lllIIIIlIlIlIII;
        Equip lllIIIIlIlIIllI = lllIIIIlIlIlIII.obtenirEquip(lllIIIIlIlIIlll);
        if (JocEquips.llIlllIl(lllIIIIlIlIIllI)) {
            Com.setHeadColor(lllIIIIlIlIIlll, ColorConverter.chatToRaw(lllIIIIlIlIIllI.getChatColor()));
        }
    }

    private static void llIIllII() {
        lIlllI = new int[55];
        JocEquips.lIlllI[0] = (98 ^ 54 ^ (193 ^ 186)) & (91 + 81 - 110 + 69 ^ 15 + 32 - -59 + 66 ^ -" ".length());
        JocEquips.lIlllI[1] = -31811 & 32210;
        JocEquips.lIlllI[2] = " ".length();
        JocEquips.lIlllI[3] = "  ".length();
        JocEquips.lIlllI[4] = "   ".length();
        JocEquips.lIlllI[5] = 68 ^ 64;
        JocEquips.lIlllI[6] = 49 ^ 11 ^ (156 ^ 163);
        JocEquips.lIlllI[7] = 53 ^ 51;
        JocEquips.lIlllI[8] = 135 + 104 - 180 + 116 ^ 144 + 83 - 158 + 99;
        JocEquips.lIlllI[9] = 207 ^ 199;
        JocEquips.lIlllI[10] = 19 + 133 - 49 + 49 ^ 99 + 6 - 67 + 107;
        JocEquips.lIlllI[11] = 201 ^ 195;
        JocEquips.lIlllI[12] = 177 ^ 186;
        JocEquips.lIlllI[13] = 206 ^ 194;
        JocEquips.lIlllI[14] = 34 ^ 71 ^ (250 ^ 146);
        JocEquips.lIlllI[15] = 44 + 159 - 50 + 27 ^ 108 + 7 - -39 + 32;
        JocEquips.lIlllI[16] = 18 ^ 29;
        JocEquips.lIlllI[17] = 161 ^ 177;
        JocEquips.lIlllI[18] = 17 + 25 - 8 + 114 ^ 62 + 132 - 175 + 114;
        JocEquips.lIlllI[19] = 228 ^ 183 ^ (47 ^ 110);
        JocEquips.lIlllI[20] = 108 ^ 11 ^ (114 ^ 6);
        JocEquips.lIlllI[21] = 67 + 74 - 71 + 61 ^ 117 + 125 - 113 + 22;
        JocEquips.lIlllI[22] = 104 ^ 125;
        JocEquips.lIlllI[23] = 13 ^ 88 ^ (13 ^ 78);
        JocEquips.lIlllI[24] = 37 ^ 60;
        JocEquips.lIlllI[25] = -(-18349 & 20398) & (-8715 & 11263);
        JocEquips.lIlllI[26] = 17 ^ 33 ^ (83 ^ 116);
        JocEquips.lIlllI[27] = 121 ^ 97;
        JocEquips.lIlllI[28] = 69 ^ 94;
        JocEquips.lIlllI[29] = 38 ^ 60;
        JocEquips.lIlllI[30] = 4 ^ 24;
        JocEquips.lIlllI[31] = 64 ^ 93;
        JocEquips.lIlllI[32] = 74 ^ 84;
        JocEquips.lIlllI[33] = 74 ^ 85;
        JocEquips.lIlllI[34] = 89 + 151 - 80 + 32 ^ 30 + 123 - 28 + 3;
        JocEquips.lIlllI[35] = 162 ^ 156 ^ (57 ^ 39);
        JocEquips.lIlllI[36] = 56 ^ 96 ^ (18 ^ 107);
        JocEquips.lIlllI[37] = 54 ^ 110 ^ (100 ^ 30);
        JocEquips.lIlllI[38] = 35 ^ 120 ^ (69 ^ 61);
        JocEquips.lIlllI[39] = 120 ^ 110 ^ (154 ^ 168);
        JocEquips.lIlllI[40] = 231 ^ 194;
        JocEquips.lIlllI[41] = 7 + 40 - -26 + 103 ^ 31 + 116 - 133 + 136;
        JocEquips.lIlllI[42] = 150 ^ 162 ^ (142 ^ 157);
        JocEquips.lIlllI[43] = 81 ^ 96 ^ (61 ^ 36);
        JocEquips.lIlllI[44] = 104 ^ 65;
        JocEquips.lIlllI[45] = 207 ^ 192 ^ (94 ^ 123);
        JocEquips.lIlllI[46] = 5 ^ 46;
        JocEquips.lIlllI[47] = 120 ^ 84;
        JocEquips.lIlllI[48] = 160 + 93 - 94 + 30 ^ 56 + 111 - 29 + 6;
        JocEquips.lIlllI[49] = 158 ^ 176;
        JocEquips.lIlllI[50] = 112 ^ 95;
        JocEquips.lIlllI[51] = 61 ^ 13;
        JocEquips.lIlllI[52] = "  ".length() ^ (78 ^ 125);
        JocEquips.lIlllI[53] = 56 ^ 79 ^ (22 ^ 83);
        JocEquips.lIlllI[54] = 100 + 137 - 180 + 111 ^ 73 + 14 - -65 + 3;
    }

    private static void lIIIIIll() {
        lIll = new String[lIlllI[54]];
        JocEquips.lIll[JocEquips.lIlllI[0]] = JocEquips.lIIlIl("3hrJjNfaXVYdcAGPW+1p2R0RTK+OoC0pK/ux3p9d/b2/0KPid4ZQBelNpoQ4dzsqqAPn73cLj8qQYhI34OYYyhScR20Eotcig40a9ak+ZYjQWZgsjlogLXTT3Zc7j7rTAprqNQRHACmnGi6U3/WnKw==", "onONZ");
        JocEquips.lIll[JocEquips.lIlllI[2]] = JocEquips.lIIlIl("sGODZfxlKa9uDTmnHpemmw==", "pURNs");
        JocEquips.lIll[JocEquips.lIlllI[3]] = JocEquips.lIIlll("ZjgPRCQvPg8IKzIqDxBiNTUAFydmNxsFLD8xCgswNQ==", "FPndB");
        JocEquips.lIll[JocEquips.lIlllI[4]] = JocEquips.lIIlll("P2YiKSUaMWc=", "sAGXP");
        JocEquips.lIll[JocEquips.lIlllI[5]] = JocEquips.lIlIII("vYMUIUOQxl0L5uuB1ZXqkw==", "CNyNb");
        JocEquips.lIll[JocEquips.lIlllI[6]] = JocEquips.lIIlll("EQ==", "tAwgy");
        JocEquips.lIll[JocEquips.lIlllI[7]] = JocEquips.lIlIII("E7ZKE7XLyT4=", "STCWk");
        JocEquips.lIll[JocEquips.lIlllI[8]] = JocEquips.lIlIII("RCfd0yDwDk59bknCx7sWHg==", "oWlSP");
        JocEquips.lIll[JocEquips.lIlllI[9]] = JocEquips.lIlIII("+iM7O/UvrDAhs/ozMlcguQ==", "JeprW");
        JocEquips.lIll[JocEquips.lIlllI[10]] = JocEquips.lIIlIl("663R0UaOB/n0rpTGi7AGCDvR4lVAD3qlfQ2yZrExXrBeWG0+Z7nx3aFVE6mRDSz5", "RWopz");
        JocEquips.lIll[JocEquips.lIlllI[11]] = JocEquips.lIIlll("FiMUATxz", "SRahL");
        JocEquips.lIll[JocEquips.lIlllI[12]] = JocEquips.lIIlIl("Bw61JK6Ti24=", "hmiIF");
        JocEquips.lIll[JocEquips.lIlllI[13]] = JocEquips.lIIlIl("49/0pZzd90U=", "GnozS");
        JocEquips.lIll[JocEquips.lIlllI[14]] = JocEquips.lIlIII("19Kfh4qrMUgLcQZiHJ45nFM4b5qndDAR", "acTel");
        JocEquips.lIll[JocEquips.lIlllI[15]] = JocEquips.lIIlll("UA==", "ZeVth");
        JocEquips.lIll[JocEquips.lIlllI[16]] = JocEquips.lIIlll("cU5hVkQ=", "QcLhd");
        JocEquips.lIll[JocEquips.lIlllI[17]] = JocEquips.lIIlIl("okhWWioKyMM=", "aiQxA");
        JocEquips.lIll[JocEquips.lIlllI[18]] = JocEquips.lIIlIl("D33uo0qbvJc=", "vHGND");
        JocEquips.lIll[JocEquips.lIlllI[19]] = JocEquips.lIIlIl("V77E7R6Y5XM8nnqx0YzuJQ==", "nnXDB");
        JocEquips.lIll[JocEquips.lIlllI[20]] = JocEquips.lIIlll("Zw==", "mYWBy");
        JocEquips.lIll[JocEquips.lIlllI[21]] = JocEquips.lIIlIl("l+RELeN2NSWZtmToaK3DXA==", "ZEkkR");
        JocEquips.lIll[JocEquips.lIlllI[22]] = JocEquips.lIIlll("WWpgX2M=", "yVMrC");
        JocEquips.lIll[JocEquips.lIlllI[23]] = JocEquips.lIIlll("LSopagAZMzM6FkguOyRFDTUuKxFIND85ABwjMCsRG2g=", "hFZJe");
        JocEquips.lIll[JocEquips.lIlllI[26]] = JocEquips.lIIlIl("BpCJFpkow7kcGcp+s2Yy+YeJmWPKyMkyeJFTI7M2Qgc=", "xdoQu");
        JocEquips.lIll[JocEquips.lIlllI[27]] = JocEquips.lIIlll("JDUDWhYQLBkKAEExERRTBCoEGwdBPRUJEQ02AQ8WCzgECQ==", "aYpzs");
        JocEquips.lIll[JocEquips.lIlllI[24]] = JocEquips.lIlIII("f24zvgISP7E=", "ylLxr");
        JocEquips.lIll[JocEquips.lIlllI[29]] = JocEquips.lIlIII("UpJKN0LeSQBhJB8MDHoEyw==", "SYwWd");
        JocEquips.lIll[JocEquips.lIlllI[28]] = JocEquips.lIlIII("3e/5aXAF+RcK/c+ZnF61zTSzYtvxH7rep6g9a6nrbKU=", "vVAwh");
        JocEquips.lIll[JocEquips.lIlllI[30]] = JocEquips.lIIlll("Hx0uKTAvES0iMmwUZSkiOREy", "LxBLS");
        JocEquips.lIll[JocEquips.lIlllI[31]] = JocEquips.lIlIII("/loKhNa6jBSRw9Huii2cQVvD1pxSl+8j", "WhDAd");
        JocEquips.lIll[JocEquips.lIlllI[32]] = JocEquips.lIlIII("FuV+kbXkxOlklVNTK3guGv4PiT9K8+clVzvDoVDPMgQ=", "ziVlM");
        JocEquips.lIll[JocEquips.lIlllI[33]] = JocEquips.lIIlIl("kZT5IfQSVfaaiO22cgU8MA==", "lgfKc");
        JocEquips.lIll[JocEquips.lIlllI[35]] = JocEquips.lIlIII("hpAGOFOTUKg=", "OOpCE");
        JocEquips.lIll[JocEquips.lIlllI[36]] = JocEquips.lIIlll("aA==", "ASRxZ");
        JocEquips.lIll[JocEquips.lIlllI[37]] = JocEquips.lIIlll("Jzw6AAkUOjwbAiUvMR0ZBA==", "wNUtl");
        JocEquips.lIll[JocEquips.lIlllI[38]] = JocEquips.lIlIII("4tqC5lmk6Gh3FPF2rZyUvQ==", "YAyRF");
        JocEquips.lIll[JocEquips.lIlllI[39]] = JocEquips.lIIlIl("gcMHTA52Jtrq4QbvNQHUYg==", "hATOL");
        JocEquips.lIll[JocEquips.lIlllI[40]] = JocEquips.lIlIII("x+I8cBaF33X8nQM2sdoU52lIo4CnbCah1tyPNIh+8V6lc6V1WwcNAPuiuYw/Ud8k", "axVDK");
        JocEquips.lIll[JocEquips.lIlllI[41]] = JocEquips.lIIlIl("2L0NKTs5OAufxOMbw+Lz7wSrRanm0Gpg", "mNPBP");
        JocEquips.lIll[JocEquips.lIlllI[42]] = JocEquips.lIlIII("6ruJnRfJE4QYgg064/x1dZWBmhfZA17K", "zfhuz");
        JocEquips.lIll[JocEquips.lIlllI[43]] = JocEquips.lIIlll("NTQ3EygcFDEEJx0mPQAm", "yrXaK");
        JocEquips.lIll[JocEquips.lIlllI[44]] = JocEquips.lIlIII("yNRfgDyvc815px2Vdzv42MHY6Tez2DvLf7gMd5rhZrT5zne7TX9teQ==", "NunbN");
        JocEquips.lIll[JocEquips.lIlllI[45]] = JocEquips.lIlIII("PJXMIOHE6NpJbW/JzBrl3c/yh2LyDzVt", "yJbEY");
        JocEquips.lIll[JocEquips.lIlllI[46]] = JocEquips.lIIlll("NgYFEz4GCAITDQAOGRg8FQMfAx0=", "tgvvn");
        JocEquips.lIll[JocEquips.lIlllI[47]] = JocEquips.lIIlll("ExwyMR4nHSBzDXILJHQdNxwjOBYjGiQ+GCBPNTsNNxxhOBwhTyk1GzsDKCAYJhxhfAo+ADUnWWVPKHRBew==", "RoATy");
        JocEquips.lIll[JocEquips.lIlllI[48]] = JocEquips.lIIlll("WAcldwELGyUjRBkcNzYXCwYqNhBYHyElRA==", "xoDWd");
        JocEquips.lIll[JocEquips.lIlllI[49]] = JocEquips.lIlIII("bad5vYzgontzyIo3W1Wc2vHmqeqL6YO8eV5tvuf9gbD0VLb3SLGMfle5iyJnJuTsHlzFkxCs9WaNLYBd7zTxdsUuALxL2mu0jSJv+gZFCheG3yOpb5C+jIKFKti8vngDwDGpVpeIXfc=", "VUroy");
        JocEquips.lIll[JocEquips.lIlllI[50]] = JocEquips.lIIlIl("PvEQFZkxf81/JrQNw6Xuv6jlgVnXk+0c", "PpCrZ");
        JocEquips.lIll[JocEquips.lIlllI[51]] = JocEquips.lIIlIl("FxxwnUc9lH47j/fB5cgNXosY/Dugy62s3rWPS1XbWtmlCqoKk3/veHmu6T3GxVaU", "oHoLk");
        JocEquips.lIll[JocEquips.lIlllI[52]] = JocEquips.lIIlll("eS0CRRk8KQYGCTAqDQQeeTANRQ8oMAoVRHkIDAEPeTYGCQ86JgrClko9YgYUHzA1EEUaPDcQCgQ4KQoREDgxEElKKiAPAAk6LAwLDyxlBgkZeTMMFh4rIBBFDygwChUZdw==", "YEcej");
        JocEquips.lIll[JocEquips.lIlllI[53]] = JocEquips.lIlIII("1sNs44JbHOqsfqMccksiQT4b3K/LSW7OtwMAXAT8TDUebNrzHDRDOg==", "iIvvW");
    }

    public Equip obtenirEquipEnemic(Player lllIIIIIlIllIlI) {
        JocEquips lllIIIIIlIlllII;
        return lllIIIIIlIlllII.obtenirEquipEnemic(lllIIIIIlIllIlI, Equip.class);
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent llIllIIlIIIlIll, Player llIllIIlIIIlIlI) {
        JocEquips llIllIIlIIIllII;
        double llIllIIlIIlIIIl;
        super.onPlayerMove(llIllIIlIIIlIll, llIllIIlIIIlIlI);
        if (JocEquips.llIlIIII((int)llIllIIlIIIlIll.isCancelled())) {
            return;
        }
        llIllIIlIIIllII.keepAwayFromForcefields(llIllIIlIIIlIlI);
        Location llIllIIlIIIllIl = llIllIIlIIIlIlI.getBedSpawnLocation();
        if (JocEquips.llIlllIl((Object)llIllIIlIIIllIl) && JocEquips.lIIIlIIlI(JocEquips.lIIIlIIll(llIllIIlIIlIIIl = llIllIIlIIIlIlI.getLocation().distance(llIllIIlIIIllIl), 30.0)) && JocEquips.llIlIllI(JocEquips.lIIIlIlII(llIllIIlIIlIIIl, 6.0)) && JocEquips.llIllIII(llIllIIlIIIllII.segonsTranscorreguts(), lIlllI[9]) && JocEquips.llIllIII(llIllIIlIIIllII.s.getSkillsForPlayer(llIllIIlIIIlIlI).size(), llIllIIlIIIllII.getBaseSkillUnlockerAmount())) {
            llIllIIlIIIllII.sendPlayerMessage(llIllIIlIIIlIlI, String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIll[lIlllI[47]])));
            "".length();
            llIllIIlIIIlIlI.teleport(llIllIIlIIIllIl);
        }
    }

    private static int lllIIIll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public double getBalancingMultiplier(Equip llIlllIlIlIIIlI) {
        JocEquips llIlllIlIlIIIll;
        if (JocEquips.llIIlllI(llIlllIlIlIIIlI)) {
            return 0.1;
        }
        if (JocEquips.llIlIlll(llIlllIlIlIIIlI.getPlayers().size())) {
            return 1.0;
        }
        return (double)Math.round((1.0 / ((double)llIlllIlIlIIIlI.getPlayers().size() / llIlllIlIlIIIll.getTeamSize()) + 1.0) / 2.0 * 100.0) / 100.0;
    }

    boolean isForcefieldEnabled() {
        JocEquips llIllIllIIIlIII;
        return llIllIllIIIlIII.pMapaActual().ExisteixPropietat(lIll[lIlllI[37]]);
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent llIllIIlIllIlIl, Block llIllIIlIlIlllI) {
        JocEquips llIllIIlIllIllI;
        super.onBlockPlace(llIllIIlIllIlIl, llIllIIlIlIlllI);
        if (JocEquips.llIlIIII((int)llIllIIlIllIllI.isUndeBaseProtection(llIllIIlIlIlllI))) {
            llIllIIlIllIlIl.setCancelled(lIlllI[2]);
        }
    }

    public Boolean getFriendlyFireEnabled() {
        return lIlllI[0];
    }

    private static boolean llIIlllI(Object object) {
        return object == null;
    }

    public void anunciarEquips(Equip llIllllIlIlllII) {
        JocEquips llIllllIlIlllIl;
        Boolean llIllllIlIllIlI = lIlllI[2];
        String llIllllIlIllIIl = lIll[lIlllI[13]];
        Iterator<Equip> llIllllIlIlIIII = llIllllIlIlllIl.Equips.iterator();
        while (JocEquips.llIlIIII((int)llIllllIlIlIIII.hasNext())) {
            Equip llIllllIlIlllll = llIllllIlIlIIII.next();
            String llIllllIllIIIII = String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIll[lIlllI[14]]));
            if (JocEquips.llIlIlll((int)llIllllIlIllIlI.booleanValue())) {
                llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append(llIllllIllIIIII).append(lIll[lIlllI[15]]));
            }
            if (JocEquips.llIIllll(llIllllIlIlllll, llIllllIlIlllII)) {
                llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append((Object)ChatColor.BOLD).append(lIll[lIlllI[16]]));
            }
            llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append((Object)llIllllIlIlllll.getChatColor()));
            if (JocEquips.llIlIIII(JocEquips.llIlllll(llIllllIlIlllIl.getBalancingMultiplier(llIllllIlIlllll), 1.0))) {
                llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append(lIll[lIlllI[17]]).append(llIllllIlIlllIl.getBalancingMultiplier(llIllllIlIlllll)).append(lIll[lIlllI[18]]));
            }
            Iterator<Player> llIllllIlIIllIl = llIllllIlIlllll.getPlayers().iterator();
            while (JocEquips.llIlIIII((int)llIllllIlIIllIl.hasNext())) {
                Player llIllllIllIIIlI = llIllllIlIIllIl.next();
                llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append(lIll[lIlllI[19]]).append(llIllllIllIIIlI.getName()).append(lIll[lIlllI[20]]));
                "".length();
                if ((46 ^ 42) != 0) continue;
                return;
            }
            if (JocEquips.llIlIlll(llIllllIlIlllll.getPlayers().size())) {
                llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append(lIll[lIlllI[21]]));
            }
            if (JocEquips.llIIllll(llIllllIlIlllll, llIllllIlIlllII)) {
                llIllllIlIllIIl = String.valueOf(new StringBuilder().append(llIllllIlIllIIl).append((Object)ChatColor.BOLD).append(lIll[lIlllI[22]]));
            }
            llIllllIlIllIlI = lIlllI[0];
            "".length();
            if (-" ".length() < 0) continue;
            return;
        }
        llIllllIlIlllIl.sendGlobalMessage(llIllllIlIllIIl);
    }

    private static boolean llIIllll(Object object, Object object2) {
        return object == object2;
    }

    private static boolean llIllIIl(int n, int n2) {
        return n != n2;
    }

    private static boolean lIIIIlllI(int n) {
        return n <= 0;
    }

    public boolean isEquipsBloquejats() {
        JocEquips llIlllIlIIIllII;
        return llIlllIlIIIllII.equipsBloquejats;
    }

    private static String lIlIII(String llIlIllIlIIIIll, String llIlIllIlIIIlII) {
        try {
            SecretKeySpec llIlIllIlIIlIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIllIlIIIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIlIllIlIIIlll = Cipher.getInstance("Blowfish");
            llIlIllIlIIIlll.init(lIlllI[3], llIlIllIlIIlIII);
            return new String(llIlIllIlIIIlll.doFinal(Base64.getDecoder().decode(llIlIllIlIIIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIllIlIIIllI) {
            llIlIllIlIIIllI.printStackTrace();
            return null;
        }
    }

    private static int lIIIIllIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public void winGame(Equip lllIIIlIIlIlIII) {
        JocEquips lllIIIlIIlIlIIl;
        if (JocEquips.llIlIIII((int)lllIIIlIIlIlIIl.won)) {
            return;
        }
        if (JocEquips.llIIlllI(lllIIIlIIlIlIII)) {
            lllIIIlIIlIlIIl.JocFinalitzat();
            "".length();
            Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIll[lIlllI[2]]).append(lllIIIlIIlIlIIl.getGameName()).append(lIll[lIlllI[3]])));
            return;
        }
        lllIIIlIIlIlIIl.won = lIlllI[2];
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIll[lIlllI[4]]).append(lllIIIlIIlIlIII.getAdjectiuColored()).append((Object)ChatColor.GRAY).append(lIll[lIlllI[5]]).append((Object)ChatColor.YELLOW).append(lllIIIlIIlIlIIl.getGameName())));
        lllIIIlIIlIlIIl.anunciarEquips(lllIIIlIIlIlIII);
        lllIIIlIIlIlIIl.matchData.registerEnd(lllIIIlIIlIlIII);
        lllIIIlIIlIlIIl.JocFinalitzat();
        lllIIIlIIlIlIIl.updateElo(lllIIIlIIlIlIII.getPlayers());
    }

    private static int lIIIlIlII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    protected void onPlayerInteractEntity(PlayerInteractEntityEvent llIllIlllIlIIII, Player llIllIlllIlIIll) {
        JocEquips llIllIlllIlIIlI;
        super.onPlayerInteractEntity(llIllIlllIlIIII, llIllIlllIlIIll);
        if (JocEquips.llIlIlll((int)llIllIlllIlIIlI.JocIniciat.booleanValue()) && JocEquips.llIlIIII(llIllIlllIlIIII.getRightClicked() instanceof Player)) {
            Player llIllIlllIllIII = llIllIlllIlIIII.getPlayer();
            Player llIllIlllIlIlll = (Player)llIllIlllIlIIII.getRightClicked();
            if (JocEquips.llIIllll((Object)llIllIlllIllIII.getItemInHand().getType(), (Object)Material.BONE)) {
                llIllIlllIlIIlI.openTemSelectionMenu(llIllIlllIlIlll, llIllIlllIllIII, String.valueOf(new StringBuilder().append(lIll[lIlllI[35]]).append(llIllIlllIlIlll.getName()).append(lIll[lIlllI[36]])));
            }
        }
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent llIllIIlIlllllI, Block llIllIIlIlllIlI) {
        JocEquips llIllIIlIllllII;
        super.onBlockBreak(llIllIIlIlllllI, llIllIIlIlllIlI);
        if (JocEquips.llIlIIII((int)llIllIIlIllllII.isUndeBaseProtection(llIllIIlIlllIlI))) {
            llIllIIlIlllllI.setCancelled(lIlllI[2]);
        }
    }

    public <T extends Equip> T obtenirEquipEnemic(Player lllIIIIIlIlIlII, Class<T> lllIIIIIlIlIIll) {
        JocEquips lllIIIIIlIlIlIl;
        return lllIIIIIlIlIlIl.obtenirEquipEnemic(lllIIIIIlIlIlIl.obtenirEquip(lllIIIIIlIlIlII, lllIIIIIlIlIIll));
    }

    ArrayList<Location> getTeamForcefields(Equip llIllIlIlIllIIl) {
        JocEquips llIllIlIlIllIII;
        return llIllIlIlIllIII.pMapaActual().ObtenirLocations(String.valueOf(new StringBuilder().append(lIll[lIlllI[43]]).append(Integer.toString(llIllIlIlIllIIl.getId()))), llIllIlIlIllIII.world);
    }

    private static int lllIIIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    protected boolean isRecallEnabled() {
        return lIlllI[0];
    }

    private static boolean llIlllIl(Object object) {
        return object != null;
    }

    private static String lIIlll(String llIlIllIlllIlll, String llIlIllIlllIllI) {
        llIlIllIlllIlll = new String(Base64.getDecoder().decode(llIlIllIlllIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIlIllIllllIlI = new StringBuilder();
        char[] llIlIllIllllIIl = llIlIllIlllIllI.toCharArray();
        int llIlIllIllllIII = lIlllI[0];
        char[] llIlIllIlllIIlI = llIlIllIlllIlll.toCharArray();
        int llIlIllIlllIIIl = llIlIllIlllIIlI.length;
        int llIlIllIlllIIII = lIlllI[0];
        while (JocEquips.llIllIII(llIlIllIlllIIII, llIlIllIlllIIIl)) {
            char llIlIllIlllllll = llIlIllIlllIIlI[llIlIllIlllIIII];
            "".length();
            llIlIllIllllIlI.append((char)(llIlIllIlllllll ^ llIlIllIllllIIl[llIlIllIllllIII % llIlIllIllllIIl.length]));
            ++llIlIllIllllIII;
            ++llIlIllIlllIIII;
            "".length();
            if (((0 ^ 52) & ~(36 ^ 16)) < " ".length()) continue;
            return null;
        }
        return String.valueOf(llIlIllIllllIlI);
    }

    @Override
    protected void customJocFinalitzat() {
        JocEquips lllIIIlIIllIIlI;
        lllIIIlIIllIIlI.planificarReseteig(lIlllI[1]);
        lllIIIlIIllIIlI.raisePlayersToSpectatorZone();
    }

    private static boolean llIlIlll(int n) {
        return n == 0;
    }

    private static boolean lIIIllIII(Object object, Object object2) {
        return object != object2;
    }

    protected void raisePlayersToSpectatorZone() {
        JocEquips lllIIIlIIIlIlll;
        Iterator<Player> lllIIIlIIIlIlIl = lllIIIlIIIlIlll.getPlayers().iterator();
        while (JocEquips.llIlIIII((int)lllIIIlIIIlIlIl.hasNext())) {
            Player lllIIIlIIIllIII = lllIIIlIIIlIlIl.next();
            lllIIIlIIIllIII.setGameMode(GameMode.SPECTATOR);
            "".length();
            lllIIIlIIIllIII.teleport(lllIIIlIIIlIlll.getHalfwayMiddle().add(0.0, 12.0, 0.0));
            "".length();
            if (((237 ^ 176 ^ (23 ^ 28)) & (62 ^ 107 ^ "   ".length() ^ -" ".length())) == ((146 ^ 161 ^ (34 ^ 5) & ~(180 ^ 147)) & (107 ^ 33 ^ (224 ^ 153) ^ -" ".length()))) continue;
            return;
        }
    }

    private static boolean llIlIllI(int n) {
        return n > 0;
    }

    @Override
    public void initialize() {
        JocEquips lllIIIlIIlllIll;
        super.initialize();
        lllIIIlIIlllIll.initTeams();
    }

    public Equip obtenirEquip(Player lllIIIIIlllIIIl) {
        JocEquips lllIIIIIlllIllI;
        return lllIIIIIlllIllI.obtenirEquip(lllIIIIIlllIIIl, Equip.class);
    }

    public void initTeams() {
        JocEquips lllIIIlIIllIlll;
        ArrayList<Equip> lllIIIlIIllIllI = lllIIIlIIllIlll.getDesiredTeams();
        if (JocEquips.llIIlllI(lllIIIlIIllIllI)) {
            lllIIIlIIllIllI = lllIIIlIIllIlll.getDesiredTeamsFromFile();
        }
        lllIIIlIIllIlll.Equips = lllIIIlIIllIllI;
    }

    private static boolean lllIIIlI(int n) {
        return n >= 0;
    }

    void openTemSelectionMenu(Player llIlllIIlIIllIl, Player llIlllIIlIlIIlI, String llIlllIIlIIlIIl) {
        JocEquips llIlllIIlIIlllI;
        IconMenu llIlllIIlIlIIII = new IconMenu(llIlllIIlIIlIIl, lIlllI[28], llIllIIIIIlIIll -> {
            llIllIIIIIlIIll.setWillClose(lIlllI[2]);
            int llIllIIIIIlIlll = llIllIIIIIlIIll.getPosition();
            if (JocEquips.llIllIIl(llIllIIIIIlIlll, lIlllI[29])) {
                JocEquips llIllIIIIIlIlIl;
                Equip llIllIIIIIlllIl = llIllIIIIIlIlIl.Equips.get(llIllIIIIIlIlll);
                llIllIIIIIlIlIl.establirEquipJugador(llIlllIIlIIllIl, llIllIIIIIlllIl);
                if (JocEquips.lIIIllIII((Object)((Object)llIllIIIIIlIlIl.generationMode), (Object)((Object)TeamGenerationMode.CUSTOM))) {
                    llIllIIIIIlIlIl.generationMode = TeamGenerationMode.CUSTOM;
                    llIllIIIIIlIlIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(llIlllIIlIIllIl.getName()).append(lIll[lIlllI[52]])));
                }
                "".length();
                if (" ".length() == 0) {
                    return;
                }
            } else {
                llIlllIIlIIllIl.sendMessage(lIll[lIlllI[53]]);
                llIllIIIIIlIIll.setWillClose(lIlllI[0]);
            }
        });
        Iterator<Equip> llIlllIIlIIIlll = llIlllIIlIIlllI.Equips.iterator();
        while (JocEquips.llIlIIII((int)llIlllIIlIIIlll.hasNext())) {
            Equip llIlllIIlIlIlIl = llIlllIIlIIIlll.next();
            Wool llIlllIIlIlIlll = new Wool(llIlllIIlIlIlIl.getColor());
            ItemStack llIlllIIlIlIllI = llIlllIIlIlIlll.toItemStack();
            "".length();
            llIlllIIlIlIIII.setOption(llIlllIIlIIlllI.Equips.indexOf(llIlllIIlIlIlIl), llIlllIIlIlIllI, String.valueOf(new StringBuilder().append((Object)llIlllIIlIlIlIl.getChatColor()).append(lIll[lIlllI[24]]).append(llIlllIIlIlIlIl.getAdjectiu())), new String[lIlllI[0]]);
            "".length();
            if ("   ".length() == "   ".length()) continue;
            return;
        }
        String[] arrstring = new String[lIlllI[2]];
        arrstring[JocEquips.lIlllI[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIll[lIlllI[28]]));
        "".length();
        llIlllIIlIlIIII.setOption(lIlllI[29], new ItemStack(Material.GLASS, lIlllI[2]), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIll[lIlllI[29]])), arrstring);
        llIlllIIlIlIIII.open(llIlllIIlIlIIlI);
    }

    private void delayedUpdateHeadColors() {
        JocEquips lllIIIlIIIlIIIl;
        "".length();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Com.getPlugin(), () -> {
            JocEquips llIlIlllIllllIl;
            llIlIlllIllllIl.updateHeadColors();
        }, 20L);
    }

    @Override
    protected void donarItemsPreparatiusGenerals(Player llIlllIIIlIllll) {
        JocEquips llIlllIIIllIIII;
        super.donarItemsPreparatiusGenerals(llIlllIIIlIllll);
        PlayerInventory llIlllIIIlIllIl = llIlllIIIlIllll.getInventory();
        ItemButton llIlllIIIlIlIll = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BOOKSHELF), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIll[lIlllI[30]])), new String[lIlllI[0]]), llIlllIIIlIllll, llIllIIIIlIllll -> {
            JocEquips llIllIIIIllIIIl;
            if (JocEquips.llIlIlll((int)llIllIIIIllIIIl.hasHostPrivilleges(llIllIIIIlIllll.getPlayer()))) {
                llIllIIIIlIllll.getPlayer().sendMessage(lIll[lIlllI[49]]);
            }
            if (!JocEquips.llIlIIII((int)llIllIIIIllIIIl.isEquipsBloquejats()) || JocEquips.llIlIIII((int)llIlllIIIlIllll.isOp())) {
                llIllIIIIllIIIl.openTemSelectionMenu(llIlllIIIlIllll, llIlllIIIlIllll, lIll[lIlllI[50]]);
                "".length();
                if (((118 ^ 64 ^ (184 ^ 197)) & (148 + 52 - 38 + 81 ^ 88 + 52 - 33 + 77 ^ -" ".length())) <= -" ".length()) {
                    return;
                }
            } else {
                llIllIIIIlIllll.getPlayer().sendMessage(lIll[lIlllI[51]]);
            }
        });
        llIlllIIIlIllIl.setItem(lIlllI[3], llIlllIIIlIlIll.getItemStack());
        ItemButton llIlllIIIlIlIlI = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.COMMAND), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIll[lIlllI[31]])), new String[lIlllI[0]]), llIlllIIIlIllll, llIllIIIlIIlIII -> {
            JocEquips llIllIIIlIIIlll;
            llIllIIIlIIIlll.ferEquipsEquilibrats();
        });
        if (JocEquips.llIlIIII((int)llIlllIIIllIIII.hasHostPrivilleges(llIlllIIIlIllll))) {
            llIlllIIIlIllIl.setItem(lIlllI[4], llIlllIIIlIlIlI.getItemStack());
        }
        ItemButton llIlllIIIlIlIII = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.SLIME_BALL), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIll[lIlllI[32]])), new String[lIlllI[0]]), llIlllIIIlIllll, llIllIIIlIIllll -> {
            JocEquips llIllIIIlIIllIl;
            if (JocEquips.llIlIIII((int)llIllIIIlIIllIl.canBeStartedBy(llIlllIIIlIllll, lIlllI[2]))) {
                llIllIIIlIIllIl.ferEquipsAleatoris(lIlllI[2]);
                llIllIIIlIIllIl.JocIniciat();
            }
        });
        if (JocEquips.llIlIIII((int)llIlllIIIllIIII.hasHostPrivilleges(llIlllIIIlIllll))) {
            llIlllIIIlIllIl.setItem(lIlllI[5], llIlllIIIlIlIII.getItemStack());
        }
        ItemButton llIlllIIIlIIllI = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.EMERALD_BLOCK), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIll[lIlllI[33]])), new String[lIlllI[0]]), llIlllIIIlIllll, llIllIIIlIlllIl -> {
            JocEquips llIllIIIlIllllI;
            llIllIIIlIllllI.resetTeams();
        });
        if (JocEquips.llIlIIII((int)llIlllIIIllIIII.hasHostPrivilleges(llIlllIIIlIllll))) {
            llIlllIIIlIllIl.setItem(lIlllI[6], llIlllIIIlIIllI.getItemStack());
        }
        llIlllIIIlIllll.updateInventory();
    }

    @Override
    public Boolean areAllies(Player lllIIIIIIIlllll, Player lllIIIIIIIllllI) {
        JocEquips lllIIIIIIlIIIll;
        return lllIIIIIIlIIIll.obtenirEquip(lllIIIIIIIlllll).getPlayers().contains((Object)lllIIIIIIIllllI);
    }

    @Override
    public Boolean areEnemies(Player lllIIIIIIIlIIII, Player lllIIIIIIIIllll) {
        JocEquips lllIIIIIIIlIIIl;
        boolean bl;
        if (JocEquips.llIlIlll((int)lllIIIIIIIlIIIl.areAllies(lllIIIIIIIlIIII, lllIIIIIIIIllll).booleanValue())) {
            bl = lIlllI[2];
            "".length();
            if ("   ".length() <= 0) {
                return null;
            }
        } else {
            bl = lIlllI[0];
        }
        return bl;
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent llIllIllIlIIIII, Player llIllIllIIlllll, Player llIllIllIlIIIll, boolean llIllIllIlIIIlI) {
        JocEquips llIllIllIlIIllI;
        Boolean llIllIllIlIIlll;
        super.onPlayerDamageByPlayer(llIllIllIlIIIII, llIllIllIIlllll, llIllIllIlIIIll, llIllIllIlIIIlI);
        if (JocEquips.llIlIlll((int)llIllIllIlIIllI.getFriendlyFireEnabled().booleanValue()) && JocEquips.llIlIIII((int)(llIllIllIlIIlll = Boolean.valueOf(llIllIllIlIIllI.areInSameTeam(llIllIllIIlllll, llIllIllIlIIIll))).booleanValue())) {
            llIllIllIlIIIII.setCancelled(lIlllI[2]);
        }
        if (JocEquips.llIlIIII((int)llIllIllIlIIllI.isUndeBaseProtection(llIllIllIIlllll.getLocation().getBlock()))) {
            Utils.healDamageable((Damageable)llIllIllIIlllll, llIllIllIlIIIII.getDamage());
            llIllIllIlIIIII.setDamage(0.0);
        }
    }

    private boolean areInSameTeam(Player llIllIllIIlIIll, Player llIllIllIIlIIlI) {
        JocEquips llIllIllIIlIlIl;
        return llIllIllIIlIlIl.obtenirEquip(llIllIllIIlIIll).equals(llIllIllIIlIlIl.obtenirEquip(llIllIllIIlIIlI));
    }

    Location normalizeForcefield(Location llIllIlIlIIIlIl, Player llIllIlIlIIIlII) {
        JocEquips llIllIlIlIIIIlI;
        Location llIllIlIlIIIIll = llIllIlIlIIIlIl.clone();
        if (JocEquips.llIIllll((Object)llIllIlIlIIIIlI.getForcefieldType(), (Object)ForcefieldType.RADIAL)) {
            return llIllIlIlIIIIll;
        }
        if (JocEquips.llIIllll((Object)llIllIlIlIIIIlI.getForcefieldType(), (Object)ForcefieldType.VERTICAL)) {
            llIllIlIlIIIIll.setY(llIllIlIlIIIlII.getLocation().getY());
            return llIllIlIlIIIIll;
        }
        if (JocEquips.llIIllll((Object)llIllIlIlIIIIlI.getForcefieldType(), (Object)ForcefieldType.HORIZONTALX)) {
            llIllIlIlIIIIll.setY(llIllIlIlIIIlII.getLocation().getX());
            return llIllIlIlIIIIll;
        }
        if (JocEquips.llIIllll((Object)llIllIlIlIIIIlI.getForcefieldType(), (Object)ForcefieldType.HORIZONTALZ)) {
            llIllIlIlIIIIll.setY(llIllIlIlIIIlII.getLocation().getX());
            return llIllIlIlIIIIll;
        }
        return llIllIlIlIIIIll;
    }

    protected abstract ArrayList<Equip> getDesiredTeams();

    @Override
    protected void customJocIniciat() {
        JocEquips lllIIIlIIlIlllI;
        if (JocEquips.llIIllll((Object)((Object)lllIIIlIIlIlllI.generationMode), (Object)((Object)TeamGenerationMode.DEFAULT))) {
            lllIIIlIIlIlllI.ferEquipsEquilibrats();
        }
        if (JocEquips.llIIllll((Object)((Object)lllIIIlIIlIlllI.generationMode), (Object)((Object)TeamGenerationMode.CUSTOM)) && JocEquips.llIlIIII(lllIIIlIIlIlllI.getPlayersOutOfTeam().size())) {
            lllIIIlIIlIlllI.sendGlobalMessage(lIll[lIlllI[0]]);
            lllIIIlIIlIlllI.anunciarEquips(null);
        }
        lllIIIlIIlIlllI.anunciarEquips(null);
        lllIIIlIIlIlllI.fixarSpawns();
        lllIIIlIIlIlllI.delayedUpdateHeadColors();
        lllIIIlIIlIlllI.updateScoreBoards();
    }

    public void updateHeadColors() {
        JocEquips lllIIIIlIllIIIl;
        Iterator<Equip> lllIIIIlIlIllll = lllIIIIlIllIIIl.Equips.iterator();
        while (JocEquips.llIlIIII((int)lllIIIIlIlIllll.hasNext())) {
            Equip lllIIIIlIllIIlI = lllIIIIlIlIllll.next();
            Iterator<Player> lllIIIIlIlIllIl = lllIIIIlIllIIlI.getPlayers().iterator();
            while (JocEquips.llIlIIII((int)lllIIIIlIlIllIl.hasNext())) {
                Player lllIIIIlIllIIll = lllIIIIlIlIllIl.next();
                Com.setHeadColor(lllIIIIlIllIIll, ColorConverter.chatToRaw(lllIIIIlIllIIlI.getChatColor()));
                "".length();
                if (((246 ^ 193) & ~(98 ^ 85)) <= ((31 ^ 95) & ~(83 ^ 19))) continue;
                return;
            }
            "".length();
            if ((85 ^ 41 ^ (211 ^ 171)) >= ((73 ^ 107 ^ (99 ^ 21)) & (215 ^ 132 ^ (57 ^ 62) ^ -" ".length()))) continue;
            return;
        }
    }

    @Override
    protected void teletransportarTothom() {
        JocEquips lllIIIIlIlllIll;
        Iterator<Equip> lllIIIIlIlllIlI = lllIIIIlIlllIll.Equips.iterator();
        while (JocEquips.llIlIIII((int)lllIIIIlIlllIlI.hasNext())) {
            Equip lllIIIIlIllllIl = lllIIIIlIlllIlI.next();
            lllIIIIlIllllIl.teleportToTeamSpawn();
            "".length();
            if (null == null) continue;
            return;
        }
    }

    boolean isUndeBaseProtection(Block llIllIIllIlIlll) {
        JocEquips llIllIIllIlIlIl;
        Iterator<Equip> llIllIIllIlIIll = llIllIIllIlIlIl.Equips.iterator();
        while (JocEquips.llIlIIII((int)llIllIIllIlIIll.hasNext())) {
            Equip llIllIIllIllIIl = llIllIIllIlIIll.next();
            if (JocEquips.lIIIlIIlI(JocEquips.lIIIlIIIl(llIllIIllIlIlll.getLocation().distance(llIllIIllIllIIl.getTeamSpawnLocation()), llIllIIllIlIlIl.getBaseProtectionRadius()))) {
                return lIlllI[2];
            }
            "".length();
            if (((54 ^ 61) & ~(42 ^ 33)) < "   ".length()) continue;
            return (boolean)((122 ^ 59) & ~(5 ^ 68) & ~("  ".length() & ~"  ".length()));
        }
        return lIlllI[0];
    }

    void forcefieldKickOff(Player llIllIlIIIIIIIl, Location llIllIlIIIIIIII) {
        JocEquips llIllIlIIIIIIlI;
        Location llIllIlIIIIIlIl = llIllIlIIIIIIIl.getLocation();
        Vector llIllIlIIIIIlII = Utils.CrearVector(llIllIlIIIIIIII, llIllIlIIIIIlIl).normalize().add(new Vector(lIlllI[0], lIlllI[2], lIlllI[0])).normalize();
        llIllIlIIIIIIlI.getWorld().playSound(llIllIlIIIIIlIl, Sound.ENTITY_IRONGOLEM_HURT, 1.0f, 2.2f);
        llIllIlIIIIIIlI.getWorld().playEffect(llIllIlIIIIIlIl, Effect.MOBSPAWNER_FLAMES, lIlllI[4]);
        llIllIlIIIIIIlI.getWorld().playEffect(llIllIlIIIIIlIl.clone().add(new Vector(lIlllI[0], lIlllI[2], lIlllI[0])), Effect.MOBSPAWNER_FLAMES, lIlllI[4]);
        Location llIllIlIIIIIIll = llIllIlIIIIIlIl.clone().add(llIllIlIIIIIlII);
        "".length();
        llIllIlIIIIIIIl.teleport(llIllIlIIIIIIll);
        llIllIlIIIIIIIl.setVelocity(llIllIlIIIIIlII);
        llIllIlIIIIIIlI.sendPlayerMessage(llIllIlIIIIIIIl, String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIll[lIlllI[44]])));
    }

    public Double getTeamSize() {
        JocEquips llIllllIIllIIII;
        return (double)llIllllIIllIIII.getPlayers().size() / (double)llIllllIIllIIII.Equips.size();
    }

    public double getAvgNumericDeviation() {
        JocEquips lllIIIlIIlIIIII;
        return Math.abs(lllIIIlIIlIIIII.getAvgTeamSize() - (double)Math.round(lllIIIlIIlIIIII.getAvgTeamSize()));
    }

    public void setEquipsBloquejats(boolean llIlllIlIIIIlll) {
        JocEquips llIlllIlIIIIllI;
        llIlllIlIIIIllI.equipsBloquejats = llIlllIlIIIIlll;
        if (JocEquips.llIlIIII((int)llIlllIlIIIIllI.isEquipsBloquejats())) {
            llIlllIlIIIIllI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIll[lIlllI[26]])));
            "".length();
            if ((18 ^ 122 ^ (47 ^ 66)) == 0) {
                return;
            }
        } else {
            llIlllIlIIIIllI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIll[lIlllI[27]])));
        }
    }

    public <T extends Equip> T obtenirEquip(Player lllIIIIlIIIlIlI, Class<T> lllIIIIlIIIlIIl) {
        JocEquips lllIIIIlIIIIlll;
        if (JocEquips.llIIlllI((Object)lllIIIIlIIIlIlI)) {
            System.out.println(lIll[lIlllI[9]]);
            return null;
        }
        String lllIIIIlIIIlIII = lllIIIIlIIIlIlI.getName();
        Iterator<Equip> lllIIIIlIIIIlII = lllIIIIlIIIIlll.Equips.iterator();
        while (JocEquips.llIlIIII((int)lllIIIIlIIIIlII.hasNext())) {
            Equip lllIIIIlIIIllII = lllIIIIlIIIIlII.next();
            if (JocEquips.llIlIIII((int)Utils.containsPlayerByName(lllIIIIlIIIllII.getPlayers(), lllIIIIlIIIlIII).booleanValue())) {
                return (T)lllIIIIlIIIllII;
            }
            "".length();
            if (((55 ^ 13) & ~(70 ^ 124)) < "   ".length()) continue;
            return null;
        }
        if (JocEquips.llIlIIII((int)lllIIIIlIIIIlll.JocIniciat.booleanValue())) {
            // empty if block
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected ArrayList<Equip> getDesiredTeamsFromFile() {
        JocEquips lllIIIIlllIIIlI;
        ArrayList<Equip> lllIIIIlllIIIIl = new ArrayList<Equip>();
        ArrayList<String> lllIIIIlllIIIII = lllIIIIlllIIIlI.pMapaActual().ObtenirArray(lIll[lIlllI[6]]);
        if (JocEquips.llIlIlll(lllIIIIlllIIIII.size())) {
            return null;
        }
        Iterator<String> lllIIIIllIlllII = lllIIIIlllIIIII.iterator();
        block4 : do {
            Constructor<?>[] lllIIIIlllIIlII;
            if (!JocEquips.llIlIIII((int)lllIIIIllIlllII.hasNext())) {
                return lllIIIIlllIIIIl;
            }
            String lllIIIIlllIIIll = lllIIIIllIlllII.next();
            Class<? extends Equip> lllIIIIlllIIlIl = lllIIIIlllIIIlI.getCustomTeamClass();
            Constructor<?>[] lllIIIIllIllIII = lllIIIIlllIIlII = lllIIIIlllIIlIl.getConstructors();
            int lllIIIIllIlIlll = lllIIIIllIllIII.length;
            int lllIIIIllIlIllI = lIlllI[0];
            do {
                block17 : {
                    Class<?>[] lllIIIIlllIlIIl;
                    int lllIIIIlllIlIll;
                    Constructor<?> lllIIIIlllIIllI;
                    String[] lllIIIIlllIlIII;
                    ArrayList<Object> lllIIIIlllIIlll;
                    block18 : {
                        block15 : {
                            block16 : {
                                if (!JocEquips.llIllIII(lllIIIIllIlIllI, lllIIIIllIlIlll)) break block15;
                                lllIIIIlllIIllI = lllIIIIllIllIII[lllIIIIllIlIllI];
                                lllIIIIlllIlIIl = lllIIIIlllIIllI.getParameterTypes();
                                if (!JocEquips.llIllIIl(lllIIIIlllIlIIl.length - lIlllI[2], (lllIIIIlllIlIII = lllIIIIlllIIIll.split(lIll[lIlllI[7]])).length)) break block16;
                                "".length();
                                if (" ".length() > " ".length()) {
                                    return null;
                                }
                                break block17;
                            }
                            lllIIIIlllIIlll = new ArrayList<Object>();
                            "".length();
                            lllIIIIlllIIlll.add((Object)((Object)lllIIIIlllIIIlI));
                            lllIIIIlllIlIll = lIlllI[0];
                            break block18;
                        }
                        "".length();
                        if (((142 ^ 165) & ~(156 ^ 183)) >= 0) continue block4;
                        return null;
                    }
                    while (JocEquips.llIllIII(lllIIIIlllIlIll, lllIIIIlllIlIII.length)) {
                        block14 : {
                            String lllIIIIlllIllIl = lllIIIIlllIlIII[lllIIIIlllIlIll];
                            Class<?> lllIIIIlllIllII = lllIIIIlllIlIIl[lllIIIIlllIlIll + lIlllI[2]];
                            try {
                                String lllIIIIlllIllll = null;
                                if (JocEquips.llIlIIII((int)lllIIIIlllIllII.isEnum())) {
                                    Class<?> lllIIIIllllIIII = lllIIIIlllIllII;
                                    lllIIIIlllIllll = (String)Enum.valueOf(lllIIIIllllIIII, lllIIIIlllIllIl);
                                }
                                if (JocEquips.llIlIIII((int)lllIIIIlllIllII.equals(String.class))) {
                                    lllIIIIlllIllll = lllIIIIlllIllIl;
                                }
                                "".length();
                                lllIIIIlllIIlll.add(lllIIIIlllIllll);
                                "".length();
                                if (null != null) {
                                    return null;
                                }
                            }
                            catch (Exception lllIIIIlllIlllI) {
                                lllIIIIlllIlllI.printStackTrace();
                                "".length();
                                if ("  ".length() != 0) break block14;
                                return null;
                            }
                        }
                        ++lllIIIIlllIlIll;
                        "".length();
                        if ((20 ^ 127 ^ (168 ^ 199)) >= "  ".length()) continue;
                        return null;
                    }
                    try {
                        lllIIIIlllIIllI.setAccessible(lIlllI[2]);
                        "".length();
                        lllIIIIlllIIIIl.add((Equip)lllIIIIlllIIllI.newInstance(lllIIIIlllIIlll.toArray()));
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException lllIIIIlllIlIlI) {
                        lllIIIIlllIlIlI.printStackTrace();
                    }
                    "".length();
                    if ("   ".length() != "   ".length()) {
                        return null;
                    }
                }
                ++lllIIIIllIlIllI;
                "".length();
            } while ((39 ^ 35) == (158 ^ 154));
            break;
        } while (true);
        return null;
    }

    private static boolean llIllIII(int n, int n2) {
        return n < n2;
    }

    public static double variance(List<Double> llIlllIlIlIlIlI) {
        double llIlllIlIlIlIIl = llIlllIlIlIlIlI.stream().mapToDouble(llIlIllllllIlIl -> llIlIllllllIlIl).average().orElse(0.0);
        return llIlllIlIlIlIlI.stream().mapToDouble(llIlIlllllllIlI -> Math.pow(llIlIlllllllIlI - llIlllIlIlIlIIl, 2.0)).average().orElse(0.0);
    }

    public Class<? extends Equip> getCustomTeamClass() {
        JocEquips lllIIIIllIIIlIl;
        Class<?>[] lllIIIIllIIIlII = ((Object)((Object)lllIIIIllIIIlIl)).getClass().getDeclaredClasses();
        int lllIIIIllIIIIll = lllIIIIllIIIlII.length;
        int lllIIIIllIIIIlI = lIlllI[0];
        while (JocEquips.llIllIII(lllIIIIllIIIIlI, lllIIIIllIIIIll)) {
            Class<?> lllIIIIllIIIlll = lllIIIIllIIIlII[lllIIIIllIIIIlI];
            if (JocEquips.llIlIIII((int)Equip.class.isAssignableFrom(lllIIIIllIIIlll))) {
                return lllIIIIllIIIlll;
            }
            ++lllIIIIllIIIIlI;
            "".length();
            if (-(68 ^ 65) < 0) continue;
            return null;
        }
        return Equip.class;
    }

    @Override
    protected void updateScoreBoard(Player llIlllllIIIlllI) {
        JocEquips llIlllllIIlIIIl;
        super.updateScoreBoard(llIlllllIIIlllI);
        if (JocEquips.llIlIlll((int)llIlllllIIlIIIl.JocIniciat.booleanValue())) {
            ArrayList<String> llIlllllIIlIIll = new ArrayList<String>();
            ArrayList<Integer> llIlllllIIlIIlI = new ArrayList<Integer>();
            Iterator<Equip> llIlllllIIIlIll = llIlllllIIlIIIl.Equips.iterator();
            while (JocEquips.llIlIIII((int)llIlllllIIIlIll.hasNext())) {
                Equip llIlllllIIlIlII = llIlllllIIIlIll.next();
                "".length();
                llIlllllIIlIIll.add(String.valueOf(new StringBuilder().append((Object)llIlllllIIlIlII.getChatColor()).append(lIll[lIlllI[11]]).append(llIlllllIIlIlII.getAdjectiu())));
                "".length();
                llIlllllIIlIIlI.add(llIlllllIIlIlII.getPlayers().size());
                "".length();
                if ((" ".length() & ~" ".length()) < "   ".length()) continue;
                return;
            }
            ScoreBoardUpdater.setScoreBoard(llIlllllIIIlllI, lIll[lIlllI[12]], llIlllllIIlIIll, llIlllllIIlIIlI);
        }
    }

    private static int llIlIlII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    static final class ForcefieldType
    extends Enum<ForcefieldType> {
        private static final /* synthetic */ int[] llIIIlll;
        public static final /* synthetic */ /* enum */ ForcefieldType HORIZONTALX;
        public static final /* synthetic */ /* enum */ ForcefieldType HORIZONTALZ;
        private static final /* synthetic */ ForcefieldType[] $VALUES;
        public static final /* synthetic */ /* enum */ ForcefieldType VERTICAL;
        public static final /* synthetic */ /* enum */ ForcefieldType RADIAL;
        private static final /* synthetic */ String[] llIIIllI;

        private static void llIIIlIIIl() {
            llIIIllI = new String[llIIIlll[4]];
            ForcefieldType.llIIIllI[ForcefieldType.llIIIlll[0]] = ForcefieldType.llIIIIIllI("5+e0WAOtsPra6mgVVh6izw==", "vQJFf");
            ForcefieldType.llIIIllI[ForcefieldType.llIIIlll[1]] = ForcefieldType.llIIIIlIIl("KTcLGxQuNg0TAjk=", "axYRN");
            ForcefieldType.llIIIllI[ForcefieldType.llIIIlll[2]] = ForcefieldType.llIIIIlIll("/O1d9YNuJUpsp/nMkpc5gg==", "ntdCm");
            ForcefieldType.llIIIllI[ForcefieldType.llIIIlll[3]] = ForcefieldType.llIIIIlIIl("JSMDORY7", "wbGpW");
        }

        private static boolean llIIIlllll(int n, int n2) {
            return n < n2;
        }

        private ForcefieldType() {
            ForcefieldType lIIlIIlIlIlllll;
        }

        private static String llIIIIIllI(String lIIlIIlIlIlIIlI, String lIIlIIlIlIlIIll) {
            try {
                SecretKeySpec lIIlIIlIlIlIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIIlIlIlIIll.getBytes(StandardCharsets.UTF_8)), llIIIlll[5]), "DES");
                Cipher lIIlIIlIlIlIllI = Cipher.getInstance("DES");
                lIIlIIlIlIlIllI.init(llIIIlll[2], lIIlIIlIlIlIlll);
                return new String(lIIlIIlIlIlIllI.doFinal(Base64.getDecoder().decode(lIIlIIlIlIlIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlIIlIlIlIlIl) {
                lIIlIIlIlIlIlIl.printStackTrace();
                return null;
            }
        }

        public static ForcefieldType valueOf(String lIIlIIlIllIIlII) {
            return Enum.valueOf(ForcefieldType.class, lIIlIIlIllIIlII);
        }

        private static String llIIIIlIIl(String lIIlIIlIIlllllI, String lIIlIIlIlIIIIll) {
            lIIlIIlIIlllllI = new String(Base64.getDecoder().decode(lIIlIIlIIlllllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIlIIlIlIIIIlI = new StringBuilder();
            char[] lIIlIIlIlIIIIII = lIIlIIlIlIIIIll.toCharArray();
            int lIIlIIlIIllllll = llIIIlll[0];
            char[] lIIlIIlIIlllIII = lIIlIIlIIlllllI.toCharArray();
            int lIIlIIlIIllIllI = lIIlIIlIIlllIII.length;
            int lIIlIIlIIllIlIl = llIIIlll[0];
            while (ForcefieldType.llIIIlllll(lIIlIIlIIllIlIl, lIIlIIlIIllIllI)) {
                char lIIlIIlIlIIIlIl = lIIlIIlIIlllIII[lIIlIIlIIllIlIl];
                "".length();
                lIIlIIlIlIIIIlI.append((char)(lIIlIIlIlIIIlIl ^ lIIlIIlIlIIIIII[lIIlIIlIIllllll % lIIlIIlIlIIIIII.length]));
                ++lIIlIIlIIllllll;
                ++lIIlIIlIIllIlIl;
                "".length();
                if ((109 ^ 105) != 0) continue;
                return null;
            }
            return String.valueOf(lIIlIIlIlIIIIlI);
        }

        static {
            ForcefieldType.llIIIllllI();
            ForcefieldType.llIIIlIIIl();
            VERTICAL = new ForcefieldType();
            HORIZONTALX = new ForcefieldType();
            HORIZONTALZ = new ForcefieldType();
            RADIAL = new ForcefieldType();
            ForcefieldType[] arrforcefieldType = new ForcefieldType[llIIIlll[4]];
            arrforcefieldType[ForcefieldType.llIIIlll[0]] = VERTICAL;
            arrforcefieldType[ForcefieldType.llIIIlll[1]] = HORIZONTALX;
            arrforcefieldType[ForcefieldType.llIIIlll[2]] = HORIZONTALZ;
            arrforcefieldType[ForcefieldType.llIIIlll[3]] = RADIAL;
            $VALUES = arrforcefieldType;
        }

        public static ForcefieldType[] values() {
            return (ForcefieldType[])$VALUES.clone();
        }

        private static void llIIIllllI() {
            llIIIlll = new int[6];
            ForcefieldType.llIIIlll[0] = (175 ^ 153) & ~(63 ^ 9);
            ForcefieldType.llIIIlll[1] = " ".length();
            ForcefieldType.llIIIlll[2] = "  ".length();
            ForcefieldType.llIIIlll[3] = "   ".length();
            ForcefieldType.llIIIlll[4] = 27 ^ 46 ^ (16 ^ 33);
            ForcefieldType.llIIIlll[5] = 39 ^ 47;
        }

        private static String llIIIIlIll(String lIIlIIlIIlIIIll, String lIIlIIlIIlIIIlI) {
            try {
                SecretKeySpec lIIlIIlIIlIlIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlIIlIIlIIIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIlIIlIIlIIlll = Cipher.getInstance("Blowfish");
                lIIlIIlIIlIIlll.init(llIIIlll[2], lIIlIIlIIlIlIII);
                return new String(lIIlIIlIIlIIlll.doFinal(Base64.getDecoder().decode(lIIlIIlIIlIIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlIIlIIlIIllI) {
                lIIlIIlIIlIIllI.printStackTrace();
                return null;
            }
        }
    }

    public static final class TeamGenerationMode
    extends Enum<TeamGenerationMode> {
        public static final /* synthetic */ /* enum */ TeamGenerationMode CUSTOM;
        private static final /* synthetic */ TeamGenerationMode[] $VALUES;
        private static final /* synthetic */ String[] lllIllI;
        public static final /* synthetic */ /* enum */ TeamGenerationMode BALANCED;
        public static final /* synthetic */ /* enum */ TeamGenerationMode DEFAULT;
        private static final /* synthetic */ int[] lllIlll;
        public static final /* synthetic */ /* enum */ TeamGenerationMode RANDOM;

        private TeamGenerationMode() {
            TeamGenerationMode llIIIlIIIlIIlII;
        }

        static {
            TeamGenerationMode.llIIlllll();
            TeamGenerationMode.llIIllllI();
            DEFAULT = new TeamGenerationMode();
            RANDOM = new TeamGenerationMode();
            BALANCED = new TeamGenerationMode();
            CUSTOM = new TeamGenerationMode();
            TeamGenerationMode[] arrteamGenerationMode = new TeamGenerationMode[lllIlll[4]];
            arrteamGenerationMode[TeamGenerationMode.lllIlll[0]] = DEFAULT;
            arrteamGenerationMode[TeamGenerationMode.lllIlll[1]] = RANDOM;
            arrteamGenerationMode[TeamGenerationMode.lllIlll[2]] = BALANCED;
            arrteamGenerationMode[TeamGenerationMode.lllIlll[3]] = CUSTOM;
            $VALUES = arrteamGenerationMode;
        }

        private static void llIIlllll() {
            lllIlll = new int[6];
            TeamGenerationMode.lllIlll[0] = (115 + 41 - 146 + 243 ^ 76 + 88 - 89 + 121) & (27 ^ 91 ^ (242 ^ 139) ^ -" ".length());
            TeamGenerationMode.lllIlll[1] = " ".length();
            TeamGenerationMode.lllIlll[2] = "  ".length();
            TeamGenerationMode.lllIlll[3] = "   ".length();
            TeamGenerationMode.lllIlll[4] = 97 ^ 101;
            TeamGenerationMode.lllIlll[5] = 124 ^ 116;
        }

        public static TeamGenerationMode[] values() {
            return (TeamGenerationMode[])$VALUES.clone();
        }

        private static String llIIlllIl(String llIIIlIIIIIllII, String llIIIlIIIIIlIIl) {
            try {
                SecretKeySpec llIIIlIIIIIllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIlIIIIIlIIl.getBytes(StandardCharsets.UTF_8)), lllIlll[5]), "DES");
                Cipher llIIIlIIIIIlllI = Cipher.getInstance("DES");
                llIIIlIIIIIlllI.init(lllIlll[2], llIIIlIIIIIllll);
                return new String(llIIIlIIIIIlllI.doFinal(Base64.getDecoder().decode(llIIIlIIIIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIIlIIIIIllIl) {
                llIIIlIIIIIllIl.printStackTrace();
                return null;
            }
        }

        public static TeamGenerationMode valueOf(String llIIIlIIIlIlIII) {
            return Enum.valueOf(TeamGenerationMode.class, llIIIlIIIlIlIII);
        }

        private static String llIIlllII(String llIIIlIIIIllIIl, String llIIIlIIIIlIllI) {
            try {
                SecretKeySpec llIIIlIIIIlllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIlIIIIlIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llIIIlIIIIllIll = Cipher.getInstance("Blowfish");
                llIIIlIIIIllIll.init(lllIlll[2], llIIIlIIIIlllII);
                return new String(llIIIlIIIIllIll.doFinal(Base64.getDecoder().decode(llIIIlIIIIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIIlIIIIllIlI) {
                llIIIlIIIIllIlI.printStackTrace();
                return null;
            }
        }

        private static void llIIllllI() {
            lllIllI = new String[lllIlll[4]];
            TeamGenerationMode.lllIllI[TeamGenerationMode.lllIlll[0]] = TeamGenerationMode.llIIlllII("3q4k7xzIMok=", "XTsZC");
            TeamGenerationMode.lllIllI[TeamGenerationMode.lllIlll[1]] = TeamGenerationMode.llIIlllIl("RuJh+TS1FJI=", "TsrRQ");
            TeamGenerationMode.lllIllI[TeamGenerationMode.lllIlll[2]] = TeamGenerationMode.llIIlllIl("pprJ2NnMpqcbGmPHEGS74w==", "OAhsb");
            TeamGenerationMode.lllIllI[TeamGenerationMode.lllIlll[3]] = TeamGenerationMode.llIIlllII("vzBHjaub/uI=", "NpsCQ");
        }
    }

    public class Equip {
        private static final /* synthetic */ int[] lIIIIllIl;
        /* synthetic */ DyeColor color;
        private static final /* synthetic */ String[] lllIllll;
        /* synthetic */ ArrayList<String> Players;
        /* synthetic */ String adjectiu;

        public void setAdjectiu(String llllIIllllIlll) {
            llllIIlllllIII.adjectiu = llllIIllllIlll;
        }

        public Equip(DyeColor llllIlIIIlIlll, String llllIlIIIlIllI) {
            Equip llllIlIIIlIlIl;
            llllIlIIIlIlIl.Players = new ArrayList();
            llllIlIIIlIlIl.color = DyeColor.GRAY;
            llllIlIIIlIlIl.adjectiu = lllIllll[lIIIIllIl[0]];
            llllIlIIIlIlIl.color = llllIlIIIlIlll;
            llllIlIIIlIlIl.adjectiu = llllIlIIIlIllI;
        }

        public int getId() {
            Equip llllIIllllIlII;
            return llllIIllllIlII.JocEquips.this.Equips.indexOf(llllIIllllIlII);
        }

        public String getDisplayName() {
            Equip llllIIlllllllI;
            if (Equip.llllIllIlI(llllIIlllllllI.getPlayers().size(), lIIIIllIl[1])) {
                return String.valueOf(new StringBuilder().append((Object)llllIIlllllllI.getChatColor()).append(llllIIlllllllI.getPlayers().get(lIIIIllIl[0]).getName()));
            }
            return String.valueOf(new StringBuilder().append((Object)llllIIlllllllI.getChatColor()).append(lllIllll[lIIIIllIl[1]]).append(llllIIlllllllI.getAdjectiu()));
        }

        private static String lllIIIIllI(String llllIIlIIllllI, String llllIIlIIlllIl) {
            try {
                SecretKeySpec llllIIlIlIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIIlIIlllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llllIIlIlIIIII = Cipher.getInstance("Blowfish");
                llllIIlIlIIIII.init(lIIIIllIl[2], llllIIlIlIIIIl);
                return new String(llllIIlIlIIIII.doFinal(Base64.getDecoder().decode(llllIIlIIllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIlIIlllll) {
                llllIIlIIlllll.printStackTrace();
                return null;
            }
        }

        public void teleportToTeamSpawn() {
            Equip llllIIllIIlIlI;
            Iterator<Player> llllIIllIIlIII = llllIIllIIlIlI.getPlayers().iterator();
            while (Equip.llllIllIll((int)llllIIllIIlIII.hasNext())) {
                Player llllIIllIIlIll = llllIIllIIlIII.next();
                llllIIllIIlIlI.teleportToTeamSpawn(llllIIllIIlIll);
                "".length();
                if (" ".length() >= 0) continue;
                return;
            }
        }

        public DyeColor getColor() {
            Equip llllIlIIIIllll;
            return llllIlIIIIllll.color;
        }

        private static boolean llllIllllI(int n, int n2) {
            return n < n2;
        }

        public ChatColor getChatColor() {
            Equip llllIlIIIIllIl;
            return ColorConverter.dyeToChat(llllIlIIIIllIl.color);
        }

        public void giveRecallButton(Player llllIIlIllIlIl) {
            Equip llllIIlIlllIll;
            ItemStack llllIIlIlllIIl = new ItemStack(Material.DIAMOND_BLOCK);
            llllIIlIlllIIl.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, lIIIIllIl[3]);
            String[] arrstring = new String[lIIIIllIl[1]];
            arrstring[Equip.lIIIIllIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lllIllll[lIIIIllIl[5]]));
            ItemButton llllIIlIlllIII = new ItemButton(Utils.setItemNameAndLore(llllIIlIlllIIl, String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lllIllll[lIIIIllIl[4]])), arrstring), llllIIlIllIlIl, llllIIlIlIlIII -> {
                Equip llllIIlIlIIlll;
                RecallUtils.startRecallTeleport((Player)llllIIlIlIlIII.getPlayer(), (Location)llllIIlIlIIlll.getTeamSpawnLocation());
            });
            PlayerInventory llllIIlIllIlll = llllIIlIllIlIl.getInventory();
            llllIIlIllIlll.setItem(lIIIIllIl[6], llllIIlIlllIII.getItemStack());
        }

        public String getAdjectiuColored() {
            Equip llllIlIIIIIIIl;
            return String.valueOf(new StringBuilder().append((Object)llllIlIIIIIIIl.getChatColor()).append(llllIlIIIIIIIl.adjectiu));
        }

        void addPlayer(Player llllIIlllIIIlI) {
            Equip llllIIlllIIIll;
            "".length();
            llllIIlllIIIll.Players.add(llllIIlllIIIlI.getName());
        }

        public ArrayList<Player> getPlayers() {
            Equip llllIIlllIlIlI;
            ArrayList<Player> llllIIlllIlIll = new ArrayList<Player>();
            Iterator<String> llllIIlllIlIII = llllIIlllIlIlI.Players.iterator();
            while (Equip.llllIllIll((int)llllIIlllIlIII.hasNext())) {
                String llllIIlllIllIl = llllIIlllIlIII.next();
                Player llllIIlllIlllI = Bukkit.getPlayer((String)llllIIlllIllIl);
                if (Equip.llllIlllII((Object)llllIIlllIlllI)) {
                    "".length();
                    llllIIlllIlIll.add(llllIIlllIlllI);
                }
                "".length();
                if ((160 + 54 - 98 + 49 ^ 45 + 119 - 44 + 41) > -" ".length()) continue;
                return null;
            }
            return llllIIlllIlIll;
        }

        public Location getTeamSpawnLocation() {
            Equip llllIIllIIllll;
            return llllIIllIIllll.JocEquips.this.pMapaActual().ObtenirLocation(String.valueOf(new StringBuilder().append(lllIllll[lIIIIllIl[2]]).append(llllIIllIIllll.getId())), llllIIllIIllll.JocEquips.this.getWorld());
        }

        void removePlayer(Player llllIIllIlIlIl) {
            Equip llllIIllIlIllI;
            Iterator<String> llllIIllIlIlII = llllIIllIlIllI.Players.iterator();
            while (Equip.llllIllIll((int)llllIIllIlIlII.hasNext())) {
                String llllIIllIllIlI;
                String llllIIllIllIIl = llllIIllIlIlII.next();
                if (Equip.llllIllIll((int)llllIIllIllIIl.equals(llllIIllIllIlI = llllIIllIlIlIl.getName()))) {
                    "".length();
                    llllIIllIlIllI.Players.remove(llllIIllIllIIl);
                }
                "".length();
                if ((156 + 64 - 73 + 40 ^ 149 + 9 - 48 + 81) >= 0) continue;
                return;
            }
        }

        private static boolean llllIlllII(Object object) {
            return object != null;
        }

        public String getAdjectiu() {
            Equip llllIlIIIIIIll;
            return llllIlIIIIIIll.adjectiu;
        }

        static {
            Equip.llllIllIII();
            Equip.lllIIIIlll();
        }

        private static boolean llllIllIlI(int n, int n2) {
            return n == n2;
        }

        private static void llllIllIII() {
            lIIIIllIl = new int[8];
            Equip.lIIIIllIl[0] = (116 + 170 - 234 + 145 ^ 19 + 84 - 73 + 123) & (152 ^ 128 ^ (26 ^ 94) ^ -" ".length());
            Equip.lIIIIllIl[1] = " ".length();
            Equip.lIIIIllIl[2] = "  ".length();
            Equip.lIIIIllIl[3] = 85 + 154 - 166 + 111 ^ 36 + 102 - -36 + 4;
            Equip.lIIIIllIl[4] = "   ".length();
            Equip.lIIIIllIl[5] = 31 ^ 27;
            Equip.lIIIIllIl[6] = 38 ^ 114 ^ (115 ^ 47);
            Equip.lIIIIllIl[7] = 156 + 46 - 30 + 18 ^ 162 + 95 - 233 + 163;
        }

        public void sendMessage(String llllIIlIlIllII) {
            Equip llllIIlIlIllIl;
            llllIIlIlIllIl.JocEquips.this.sendTeamMessage(llllIIlIlIllIl, llllIIlIlIllII);
        }

        private static String lllIIIIlIl(String llllIIlIIIlllI, String llllIIlIIIllIl) {
            llllIIlIIIlllI = new String(Base64.getDecoder().decode(llllIIlIIIlllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllIIlIIIllII = new StringBuilder();
            char[] llllIIlIIIlIll = llllIIlIIIllIl.toCharArray();
            int llllIIlIIIlIlI = lIIIIllIl[0];
            char[] llllIIlIIIIlII = llllIIlIIIlllI.toCharArray();
            int llllIIlIIIIIll = llllIIlIIIIlII.length;
            int llllIIlIIIIIlI = lIIIIllIl[0];
            while (Equip.llllIllllI(llllIIlIIIIIlI, llllIIlIIIIIll)) {
                char llllIIlIIIllll = llllIIlIIIIlII[llllIIlIIIIIlI];
                "".length();
                llllIIlIIIllII.append((char)(llllIIlIIIllll ^ llllIIlIIIlIll[llllIIlIIIlIlI % llllIIlIIIlIll.length]));
                ++llllIIlIIIlIlI;
                ++llllIIlIIIIIlI;
                "".length();
                if (((145 + 162 - 222 + 88 ^ 158 + 63 - 187 + 143) & (1 ^ 98 ^ 19 + 42 - -21 + 45 ^ -" ".length())) < "   ".length()) continue;
                return null;
            }
            return String.valueOf(llllIIlIIIllII);
        }

        private static boolean llllIllIll(int n) {
            return n != 0;
        }

        private static void lllIIIIlll() {
            lllIllll = new String[lIIIIllIl[7]];
            Equip.lllIllll[Equip.lIIIIllIl[0]] = Equip.lllIIIIlIl("", "yTygg");
            Equip.lllIllll[Equip.lIIIIllIl[1]] = Equip.lllIIIIlIl("MBIHOCJV", "ucrQR");
            Equip.lllIllll[Equip.lIIIIllIl[2]] = Equip.lllIIIIlIl("FzgcHQ==", "uYoxD");
            Equip.lllIllll[Equip.lIIIIllIl[4]] = Equip.lllIIIIllI("rVDxJC5YgW0=", "QRRNH");
            Equip.lllIllll[Equip.lIIIIllIl[5]] = Equip.lllIIIIllI("ySpGmsHiN9K/lxmeh8rQXr4TF9gn4+v79IuM61LHMZ0=", "ZuThd");
        }

        public void teleportToTeamSpawn(Player llllIIllIIIIIl) {
            Equip llllIIllIIIlII;
            "".length();
            llllIIllIIIIIl.teleport(llllIIllIIIlII.getTeamSpawnLocation());
        }

        public void setColor(DyeColor llllIlIIIIIllI) {
            llllIlIIIIlIIl.color = llllIlIIIIIllI;
        }
    }

}

