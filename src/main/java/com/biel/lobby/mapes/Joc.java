/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.EloUtils
 *  com.biel.BielAPI.Utils.GUtils
 *  com.biel.BielAPI.Utils.IconMenu
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEvent
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEventHandler
 *  com.biel.BielAPI.Utils.ItemButton
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEvent
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEventHandler
 *  com.biel.BielAPI.events.EventUtils
 *  com.connorlinfoot.bountifulapi.BountifulAPI
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
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
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.BlockIterator
 *  org.bukkit.util.Vector
 *  org.inventivetalent.menubuilder.chat.ChatListener
 *  org.inventivetalent.menubuilder.chat.ChatMenuBuilder
 *  org.inventivetalent.menubuilder.chat.LineBuilder
 */
package com.biel.lobby.mapes;

import com.biel.BielAPI.Utils.EloUtils;
import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.events.EventUtils;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.utilities.CBUtils;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.MatchData;
import com.biel.lobby.utilities.data.PlayerData;
import com.biel.lobby.utilities.events.skills.Skill;
import com.biel.lobby.utilities.events.skills.SkillPool;
import com.biel.lobby.utilities.events.skills.types.specificskills.AssaultSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.BerserkSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.CalciumSourceSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.CorinthianHelmetSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.DeflectorSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.DiamondCoreSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.FrostArcherSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.GravityBendingSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.MagicArcherSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.SpeedyArcher;
import com.biel.lobby.utilities.events.skills.types.specificskills.SwordsmanSkill;
import com.biel.lobby.utilities.events.skills.types.specificskills.VampireSkill;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.AuraRendererStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import com.connorlinfoot.bountifulapi.BountifulAPI;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.inventivetalent.menubuilder.chat.ChatListener;
import org.inventivetalent.menubuilder.chat.ChatMenuBuilder;
import org.inventivetalent.menubuilder.chat.LineBuilder;

public abstract class Joc
extends MapaResetejable {
    protected /* synthetic */ boolean unfairFlag;
    /* synthetic */ double lastProgressETA;
    private static final /* synthetic */ int[] lIlIlIlIl;
    private static final /* synthetic */ String[] lIIIIIIll;
    private /* synthetic */ Boolean blockBreakPlace;
    protected /* synthetic */ boolean won;
    private /* synthetic */ Boolean isSnowLauncherEnabled;
    private /* synthetic */ Long ultraHeartbeatCount;
    private /* synthetic */ Long heartbeatCount;
    private /* synthetic */ int heartbeatId;
    /* synthetic */ ArrayList<Player> Espectadors;
    private /* synthetic */ Long announceCount;
    private /* synthetic */ Boolean giveStartingItemsRespawn;
    protected /* synthetic */ Boolean JocFinalitzat;
    private /* synthetic */ Boolean showPlayerHealthBar;
    protected /* synthetic */ MatchData matchData;
    protected /* synthetic */ Boolean JocIniciat;
    /* synthetic */ ArrayList<PlayerInfo> InfoStorage;
    protected /* synthetic */ SkillPool s;
    private /* synthetic */ Long startTimeMillis;
    protected /* synthetic */ String host;
    private /* synthetic */ ArrayList<Integer> handledBukkitSchedulerTasks;

    public void handleTask(int lIlIIlIIlIllll) {
        Joc lIlIIlIIllIIII;
        "".length();
        lIlIIlIIllIIII.handledBukkitSchedulerTasks.add(lIlIIlIIlIllll);
    }

    public void winGame(Player lIlIIlIIIllIll) {
        Joc lIlIIlIIIlllII;
        if (Joc.lIIllIlllll((int)lIlIIlIIIlllII.won)) {
            return;
        }
        if (Joc.lIIlllIIIII((Object)lIlIIlIIIllIll)) {
            lIlIIlIIIlllII.JocFinalitzat();
            "".length();
            Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIIlIIIlllII.getGameDisplayName()).append(lIIIIIIll[lIlIlIlIl[15]])));
            return;
        }
        lIlIIlIIIlllII.won = lIlIlIlIl[1];
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIIlIIIlllII.getGameDisplayName()).append(lIlIIlIIIllIll.getName()).append(lIIIIIIll[lIlIlIlIl[16]]).append((Object)ChatColor.UNDERLINE).append(lIlIIlIIIlllII.getGameName())));
        lIlIIlIIIlllII.matchData.registerEnd(lIlIIlIIIllIll);
        lIlIIlIIIlllII.JocFinalitzat();
        ArrayList<Player> lIlIIlIIIlllIl = new ArrayList<Player>();
        "".length();
        lIlIIlIIIlllIl.add(lIlIIlIIIllIll);
        lIlIIlIIIlllII.updateElo(lIlIIlIIIlllIl);
    }

    private static boolean lIlIIIllIll(int n) {
        return n >= 0;
    }

    private static int lIIlllIIlII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public PlayerInfo getPlayerInfo(Player lIIllIIlIIIlll) {
        Joc lIIllIIlIIlIlI;
        return lIIllIIlIIlIlI.getPlayerInfo(lIIllIIlIIIlll, PlayerInfo.class);
    }

    protected Location getOptimalSpawnLoc(Player lIlIIIlIlIlIll) {
        Joc lIlIIIlIlIllII;
        if (Joc.lIIlllIIllI(lIlIIIlIlIllII.getPlayers().size(), lIlIlIlIl[1])) {
            return lIlIIIlIlIllII.getRandomSpawnLoc(lIlIIIlIlIlIll);
        }
        ArrayList<Location> lIlIIIlIlIlllI = lIlIIIlIlIllII.pMapaActual().ObtenirLocations(lIIIIIIll[lIlIlIlIl[32]], lIlIIIlIlIllII.world);
        Location lIlIIIlIlIllIl = (Location)lIlIIIlIlIlllI.stream().sorted((lIIlIlIlIllIll, lIIlIlIlIlIllI) -> {
            Joc lIIlIlIlIlllIl;
            return (int)(((Player)GUtils.getNearestEntity((Location)lIIlIlIlIlIllI, lIIlIlIlIlllIl.getEnemies(lIlIIIlIlIlIll))).getLocation().distanceSquared(lIIlIlIlIlIllI) - ((Player)GUtils.getNearestEntity((Location)lIIlIlIlIllIll, lIIlIlIlIlllIl.getEnemies(lIlIIIlIlIlIll))).getLocation().distanceSquared(lIIlIlIlIllIll));
        }).skip(Utils.NombreEntre(lIlIlIlIl[0], lIlIlIlIl[4])).findFirst().get();
        "".length();
        lIlIIIlIlIllIl.add(0.0, 2.0, 0.0);
        return lIlIIIlIlIllIl;
    }

    private static int lIIlllllIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    protected void updateElo(ArrayList<Player> lIlIIIlllllIII) {
        Joc lIlIIIllllIIll;
        if (Joc.lIIllIllllI((int)lIlIIIllllIIll.canBeRanked())) {
            lIlIIIllllIIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIIIIIIll[lIlIlIlIl[21]])));
            return;
        }
        ArrayList<Player> lIlIIIllllIlll = new ArrayList<Player>();
        lIlIIIllllIIll.getPlayers().forEach(lIIlIlIIIlIlll -> {
            if (Joc.lIIllIllllI((int)lIlIIIlllllIII.contains((Object)lIIlIlIIIlIlll))) {
                "".length();
                lIlIIIllllIlll.add(lIIlIlIIIlIlll);
            }
        });
        ArrayList lIlIIIllllIllI = new ArrayList();
        lIlIIIlllllIII.forEach(lIIlIlIIlIIIII -> {
            "".length();
            lIlIIIllllIllI.add(new PlayerData(lIIlIlIIlIIIII.getName()).getElo());
        });
        ArrayList lIlIIIllllIlIl = new ArrayList();
        lIlIIIllllIlll.forEach(lIIlIlIIlIlIII -> {
            "".length();
            lIlIIIllllIlIl.add(new PlayerData(lIIlIlIIlIlIII.getName()).getElo());
        });
        ArrayList lIlIIIllllIlII = EloUtils.calculateEloGroupChange(lIlIIIllllIllI, lIlIIIllllIlIl, (double)lIlIIIllllIIll.getEloK(), (boolean)lIlIlIlIl[0]);
        ((ArrayList)lIlIIIllllIlII.get(lIlIlIlIl[0])).forEach(lIIlIlIIlIllII -> {
            Joc lIIlIlIIlIllll;
            lIIlIlIIlIllll.registerEloChange((Player)lIlIIIlllllIII.get(((ArrayList)lIlIIIllllIlII.get(lIlIlIlIl[0])).indexOf(lIIlIlIIlIllII)), (double)lIIlIlIIlIllII);
        });
        ((ArrayList)lIlIIIllllIlII.get(lIlIlIlIl[1])).forEach(lIIlIlIIlllIII -> {
            Joc lIIlIlIIlllIll;
            lIIlIlIIlllIll.registerEloChange((Player)lIlIIIllllIlll.get(((ArrayList)lIlIIIllllIlII.get(lIlIlIlIl[1])).indexOf(lIIlIlIIlllIII)), (double)lIIlIlIIlllIII);
        });
    }

    private static boolean lIIlllIIllI(int n, int n2) {
        return n == n2;
    }

    double getEloM() {
        return 1.0;
    }

    private static boolean lIIllIllllI(int n) {
        return n == 0;
    }

    public void addSpectator(Player lIlIIIIIIIlIll) {
        Joc lIlIIIIIIIlllI;
        Iterator<Player> lIlIIIIIIIlIlI = lIlIIIIIIIlllI.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIlIIIIIIIlIlI.hasNext())) {
            Player lIlIIIIIIIllll = lIlIIIIIIIlIlI.next();
            lIlIIIIIIIllll.sendMessage(String.valueOf(new StringBuilder().append(lIlIIIIIIIlllI.getGameDisplayName()).append(lIlIIIIIIIlIll.getName()).append(lIIIIIIll[lIlIlIlIl[42]])));
            "".length();
            if (null == null) continue;
            return;
        }
        if (Joc.lIIllIllllI((int)lIlIIIIIIIlllI.getSpectators().contains((Object)lIlIIIIIIIlIll))) {
            "".length();
            lIlIIIIIIIlllI.Espectadors.add(lIlIIIIIIIlIll);
        }
        Utils.clearPlayer(lIlIIIIIIIlIll);
        lIlIIIIIIIlllI.donarItemsEspectador(lIlIIIIIIIlIll);
        lIlIIIIIIIlllI.updateScoreBoard(lIlIIIIIIIlIll);
        lIlIIIIIIIlIll.setGameMode(GameMode.SPECTATOR);
        lIlIIIIIIIlllI.teleportCameraRandomly(lIlIIIIIIIlIll);
    }

    public ItemStack getSnowLauncher(int lIIlllllIIIllI) {
        lIIlllllIIIlII.isSnowLauncherEnabled = lIlIlIlIl[1];
        ItemStack lIIlllllIIIlIl = new ItemStack(Material.SNOW_BALL);
        lIIlllllIIIlIl.addUnsafeEnchantment(Enchantment.SILK_TOUCH, lIlIlIlIl[1]);
        lIIlllllIIIlIl.setAmount(lIIlllllIIIllI);
        String[] arrstring = new String[lIlIlIlIl[1]];
        arrstring[Joc.lIlIlIlIl[0]] = lIIIIIIll[lIlIlIlIl[49]];
        return Utils.setItemNameAndLore(lIIlllllIIIlIl, lIIIIIIll[lIlIlIlIl[48]], arrstring);
    }

    protected ArrayList<String> getGameInfo(Player lIlIIIlIIlIIIl) {
        return null;
    }

    private static String llllIIIlII(String lIIlIIllllllIl, String lIIlIIlllllllI) {
        try {
            SecretKeySpec lIIlIlIIIIIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIIlllllllI.getBytes(StandardCharsets.UTF_8)), lIlIlIlIl[9]), "DES");
            Cipher lIIlIlIIIIIIIl = Cipher.getInstance("DES");
            lIIlIlIIIIIIIl.init(lIlIlIlIl[3], lIIlIlIIIIIIlI);
            return new String(lIIlIlIIIIIIIl.doFinal(Base64.getDecoder().decode(lIIlIIllllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIlIIIIIIII) {
            lIIlIlIIIIIIII.printStackTrace();
            return null;
        }
    }

    public void sendGameInfo() {
        Joc lIlIIIlIlIIlII;
        Iterator<Player> lIlIIIlIlIIIlI = lIlIIIlIlIIlII.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIlIIIlIlIIIlI.hasNext())) {
            Player lIlIIIlIlIIlIl = lIlIIIlIlIIIlI.next();
            lIlIIIlIlIIlII.sendGameInfo(lIlIIIlIlIIlIl);
            "".length();
            if (null == null) continue;
            return;
        }
    }

    protected void donarItemsInicials() {
        Joc lIlIIIIllIlIll;
        Iterator<Player> lIlIIIIllIlIIl = lIlIIIIllIlIll.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIlIIIIllIlIIl.hasNext())) {
            Player lIlIIIIllIllII = lIlIIIIllIlIIl.next();
            lIlIIIIllIlIll.donarItemsInicials(lIlIIIIllIllII);
            "".length();
            if ((40 ^ 44) > ("  ".length() & ~"  ".length())) continue;
            return;
        }
    }

    public String getWikiLink(String lIIlllIIIllIIl, boolean lIIlllIIIllllI) {
        String lIIlllIIIlllIl = lIIIIIIll[lIlIlIlIl[76]];
        String lIIlllIIIlllII = lIIIIIIll[lIlIlIlIl[77]];
        if (Joc.lIIllIlllll((int)lIIlllIIIllllI)) {
            lIIlllIIIlllII = lIIIIIIll[lIlIlIlIl[78]];
        }
        String lIIlllIIIllIll = lIIIIIIll[lIlIlIlIl[79]];
        String lIIlllIIIllIlI = lIIIIIIll[lIlIlIlIl[80]];
        return String.valueOf(new StringBuilder().append(lIIlllIIIlllIl).append(lIIlllIIIlllII).append(lIIlllIIIllIll).append(lIIlllIIIllIlI).append(lIIlllIIIllIIl));
    }

    private static int lIIlllIIIlI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public void resetHeartbeat() {
        lIIllIIIIllIII.heartbeatCount = 0L;
    }

    public GameState getGameState() {
        Joc lIIllIIlIllllI;
        if (Joc.lIIllIlllll((int)lIIllIIlIllllI.EditMode.booleanValue())) {
            return GameState.Editant;
        }
        if (Joc.lIIllIlllll((int)lIIllIIlIllllI.JocFinalitzat.booleanValue())) {
            return GameState.Complete;
        }
        if (Joc.lIIllIlllll((int)lIIllIIlIllllI.JocIniciat.booleanValue())) {
            return GameState.InGame;
        }
        if (Joc.lIIlllIIlll((Object)lIIllIIlIllllI.world)) {
            if (Joc.lIIllIllllI(lIIllIIlIllllI.world.getPlayers().size())) {
                return GameState.WaitingForPlayers;
            }
            return GameState.Preparing;
        }
        return GameState.WaitingForPlayers;
    }

    public void inviteToGame(Player lIIllIllIlIIlI) {
        Joc lIIllIllIlIIll;
        TextComponent lIIllIllIlIIIl = new TextComponent(lIIIIIIll[lIlIlIlIl[87]]);
        lIIllIllIlIIlI.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIIIll[lIlIlIlIl[88]]).append((Object)ChatColor.ITALIC).append(lIIllIllIlIIll.host).append((Object)ChatColor.RESET).append(lIIIIIIll[lIlIlIlIl[89]]).append((Object)ChatColor.GREEN).append(lIIIIIIll[lIlIlIlIl[90]]).append(lIIllIllIlIIll.getGameName()).append(lIIIIIIll[lIlIlIlIl[91]])));
        LineBuilder[] arrlineBuilder = new LineBuilder[lIlIlIlIl[1]];
        BaseComponent[] arrbaseComponent = new BaseComponent[lIlIlIlIl[1]];
        arrbaseComponent[Joc.lIlIlIlIl[0]] = lIIllIllIlIIIl;
        arrlineBuilder[Joc.lIlIlIlIl[0]] = new LineBuilder().append(new ChatListener(){

            public void onClick(Player llIIlIlIIIlIll) {
                1 llIIlIlIIIlIlI;
                llIIlIlIIIlIlI.Joc.this.Join(llIIlIlIIIlIll);
            }
            {
                1 llIIlIlIIlIIII;
            }
        }, arrbaseComponent);
        HumanEntity[] arrhumanEntity = new HumanEntity[lIlIlIlIl[1]];
        arrhumanEntity[Joc.lIlIlIlIl[0]] = lIIllIllIlIIlI;
        "".length();
        new ChatMenuBuilder().withLine(arrlineBuilder).show(arrhumanEntity);
        lIIllIllIlIIlI.sendMessage(lIIIIIIll[lIlIlIlIl[92]]);
        lIIllIllIlIIlI.playSound(lIIllIllIlIIlI.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100.0f, 0.0f);
    }

    public void punishPlayerElo(Player lIIlllIlIlIIll, double lIIlllIlIlIIlI) {
        Joc lIIlllIlIlIlll;
        lIIlllIlIlIlll.unfairFlag = lIlIlIlIl[1];
        lIIlllIlIlIlll.registerEloChange(lIIlllIlIlIIll, lIIlllIlIlIIlI * -1.0);
    }

    private void killHeartbeat() {
        Joc lIIllIIIlIlIlI;
        Bukkit.getServer().getScheduler().cancelTask(lIIllIIIlIlIlI.heartbeatId);
    }

    public ArrayList<Player> getViewers() {
        Joc lIlIIIIlIIlIIl;
        return (ArrayList)lIlIIIIlIIlIIl.world.getPlayers();
    }

    protected boolean onlyPlayersFromSameIP() {
        Joc lIlIIlIIIIIlll;
        String lIlIIlIIIIlIII = null;
        Iterator<Player> lIlIIlIIIIIlIl = lIlIIlIIIIIlll.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIlIIlIIIIIlIl.hasNext())) {
            Player lIlIIlIIIIlIlI = lIlIIlIIIIIlIl.next();
            String lIlIIlIIIIlIll = lIlIIlIIIIlIlI.getAddress().getHostName();
            if (Joc.lIIlllIIIII(lIlIIlIIIIlIII)) {
                lIlIIlIIIIlIII = lIlIIlIIIIlIll;
            }
            if (Joc.lIIllIlllll((int)lIlIIlIIIIlIII.equals(lIlIIlIIIIlIll))) {
                return lIlIlIlIl[0];
            }
            "".length();
            if (((47 ^ 42) & ~(87 ^ 82)) == ((210 ^ 138) & ~(96 ^ 56))) continue;
            return (boolean)((129 ^ 135) & ~(2 ^ 4));
        }
        return lIlIlIlIl[1];
    }

    @Override
    protected void customLeave(Player lIIlllIllIIIIl, List<String> lIIlllIllIIIII) {
        Joc lIIlllIlIllllI;
        if (Joc.lIIllIlllll((int)lIIlllIlIllllI.hasHostPrivilleges(lIIlllIllIIIIl)) && Joc.lIIlllIIIll(lIIlllIlIllllI.getPlayers().size(), lIlIlIlIl[1]) && Joc.lIIllIllllI((int)lIIlllIlIllllI.JocIniciat.booleanValue())) {
            lIIlllIlIllllI.setHost((Player)GUtils.getRandomListItem(lIIlllIlIllllI.getPlayers().stream().filter(lIIlIlIlllIIII -> {
                boolean bl;
                if (Joc.lIlIIlIlIII((Object)lIIlIlIlllIIII, (Object)lIIlllIllIIIIl)) {
                    bl = lIlIlIlIl[1];
                    "".length();
                    if ("  ".length() > "   ".length()) {
                        return (boolean)((161 ^ 169 ^ " ".length()) & (118 ^ 21 ^ (127 ^ 21) ^ -" ".length()));
                    }
                } else {
                    bl = lIlIlIlIl[0];
                }
                return bl;
            }).collect(Collectors.toList())));
        }
        double lIIlllIlIlllll = lIIlllIlIllllI.getPunishForLeaving(lIIlllIllIIIIl);
        if (Joc.lIIlllIIIll(CBUtils.getPing(lIIlllIllIIIIl), lIlIlIlIl[59])) {
            "".length();
            lIIlllIllIIIII.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIIIIIIll[lIlIlIlIl[60]])));
            if (Joc.lIIlllIIlIl(Joc.lIIlllllIII(lIIlllIlIlllll, 0.0))) {
                "".length();
                lIIlllIllIIIII.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIIIll[lIlIlIlIl[61]])));
                "".length();
                if ("  ".length() >= "   ".length()) {
                    return;
                }
            }
        } else if (Joc.lIIllIlllll(Joc.lIIlllllIII(lIIlllIlIlllll, 0.0)) && Joc.lIIllIllllI((int)lIIlllIlIllllI.isSpectator(lIIlllIllIIIIl).booleanValue())) {
            "".length();
            lIIlllIllIIIII.add(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIIIIIll[lIlIlIlIl[62]])));
            lIIlllIlIllllI.punishPlayerElo(lIIlllIllIIIIl, lIIlllIlIlllll);
        }
    }

    public void winGame(ArrayList<Player> lIlIIlIIIlIIlI) {
        Joc lIlIIlIIIlIIll;
        if (Joc.lIIlllIIIII(lIlIIlIIIlIIlI)) {
            return;
        }
        if (Joc.lIIlllIIIIl(lIlIIlIIIlIIlI.size(), lIlIlIlIl[1])) {
            return;
        }
        if (Joc.lIIllIlllll((int)lIlIIlIIIlIIll.won)) {
            return;
        }
        Player lIlIIlIIIlIlII = lIlIIlIIIlIIlI.get(lIlIlIlIl[0]);
        if (Joc.lIIlllIIIII((Object)lIlIIlIIIlIlII)) {
            lIlIIlIIIlIIll.JocFinalitzat();
            "".length();
            Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIIlIIIlIIll.getGameDisplayName()).append(lIIIIIIll[lIlIlIlIl[17]])));
            return;
        }
        lIlIIlIIIlIIll.won = lIlIlIlIl[1];
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIIlIIIlIIll.getGameDisplayName()).append(lIlIIlIIIlIlII.getName()).append(lIIIIIIll[lIlIlIlIl[18]]).append((Object)ChatColor.UNDERLINE).append(lIlIIlIIIlIIll.getGameName())));
        lIlIIlIIIlIIll.matchData.registerEnd(lIlIIlIIIlIlII);
        lIlIIlIIIlIIll.JocFinalitzat();
        lIlIIlIIIlIIll.updateEloOrdered(lIlIIlIIIlIIlI);
    }

    private static int lIIlllIlIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public void clearExternals(Player lIlIIlIIlllIII) {
    }

    public <T extends PlayerInfo> T getPlayerInfo(Player lIIllIIlIlIIII, Class<T> lIIllIIlIIllll) {
        Joc lIIllIIlIlIIIl;
        Iterator<PlayerInfo> lIIllIIlIIlllI = lIIllIIlIlIIIl.InfoStorage.iterator();
        while (Joc.lIIllIlllll((int)lIIllIIlIIlllI.hasNext())) {
            PlayerInfo lIIllIIlIlIlll = lIIllIIlIIlllI.next();
            if (Joc.lIIllIlllll((int)lIIllIIlIlIIII.getName().equals(lIIllIIlIlIlll.getName()))) {
                return (T)lIIllIIlIlIlll;
            }
            "".length();
            if (-" ".length() != "   ".length()) continue;
            return null;
        }
        try {
            Object[] arrobject = new Object[lIlIlIlIl[1]];
            arrobject[Joc.lIlIlIlIl[0]] = lIIllIIlIlIIIl;
            PlayerInfo lIIllIIlIlIllI = (PlayerInfo)lIIllIIlIIllll.getConstructors()[lIlIlIlIl[0]].newInstance(arrobject);
            lIIllIIlIlIllI.setName(lIIllIIlIlIIII.getName());
            "".length();
            lIIllIIlIlIIIl.InfoStorage.add(lIIllIIlIlIllI);
            return lIIllIIlIlIIIl.getPlayerInfo(lIIllIIlIlIIII, lIIllIIlIIllll);
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException | InvocationTargetException lIIllIIlIlIlIl) {
            lIIllIIlIlIlIl.printStackTrace();
            return null;
        }
    }

    public Long getAnnounceCount() {
        Joc lIIllIIIIIlIII;
        return lIIllIIIIIlIII.announceCount;
    }

    protected void donarEfectesInicials(Player lIlIIIIlIllIII) {
    }

    public Boolean getShowPlayerHealthBar() {
        Joc lIIllIlIlllIIl;
        return lIIllIlIlllIIl.showPlayerHealthBar;
    }

    public String getWinnerDisplayName() {
        return lIIIIIIll[lIlIlIlIl[40]];
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIIllllIllIIII, Player lIIllllIllIlIl, Player lIIllllIlIlllI, boolean lIIllllIllIIll) {
        PlayerInfo lIIllllIllIIlI;
        Entity lIIllllIlllIII;
        Joc lIIllllIllIlll;
        super.onPlayerDamageByPlayer(lIIllllIllIIII, lIIllllIllIlIl, lIIllllIlIlllI, lIIllllIllIIll);
        lIIllllIllIlll.getPlayerInfo(lIIllllIllIlIl).setLastDamager(lIIllllIlIlllI);
        if (Joc.lIIllIlllll((int)lIIllllIllIIll) && Joc.lIIllIlllll((lIIllllIlllIII = lIIllllIllIIII.getDamager()) instanceof Snowball)) {
            Snowball lIIllllIlllIIl = (Snowball)lIIllllIlllIII;
            if (Joc.lIIllIlllll((int)lIIllllIllIlll.isSnowLauncherEnabled.booleanValue())) {
                lIIllllIllIIII.setCancelled(lIlIlIlIl[1]);
                "".length();
                lIIllllIllIlIl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, lIlIlIlIl[20], lIlIlIlIl[0]));
                "".length();
                lIIllllIllIlIl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIlIlIlIl[20], lIlIlIlIl[0]));
                "".length();
                lIIllllIlIlllI.teleport(lIIllllIllIlIl.getEyeLocation().add(0.0, 0.5, 0.0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                GUtils.healDamageable((Damageable)lIIllllIlIlllI, (Double)0.4);
                "".length();
                lIIllllIlIlllI.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, lIlIlIlIl[42], lIlIlIlIl[1]));
            }
        }
        if (!Joc.lIIllIllllI((int)lIIllllIllIlll.isSpectator(lIIllllIlIlllI).booleanValue()) || Joc.lIIllIlllll((int)lIIllllIllIlll.isSpectator(lIIllllIllIlIl).booleanValue())) {
            lIIllllIllIIII.setCancelled(lIlIlIlIl[1]);
        }
        if (Joc.lIIllIlllll((int)lIIllllIllIlll.getDisplayHealthBar())) {
            lIIllllIllIlll.updateHealthSuffix(lIIllllIllIlIl);
        }
        if (Joc.lIIllIlllll((int)(lIIllllIllIIlI = lIIllllIllIlll.getPlayerInfo(lIIllllIllIlIl)).isImmune())) {
            lIIllllIllIIII.setCancelled(lIlIlIlIl[1]);
            BountifulAPI.sendActionBar((Player)lIIllllIlIlllI, (String)String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIIIIIIll[lIlIlIlIl[50]]).append(lIIllllIllIlIl.getName()).append(lIIIIIIll[lIlIlIlIl[51]])), (int)lIlIlIlIl[52]);
            lIIllllIllIlll.getWorld().playSound(lIIllllIlIlllI.getLocation(), Sound.ENCHANT_THORNS_HIT, 1.2f, 0.88f);
            lIIllllIllIlll.getWorld().playEffect(lIIllllIllIlIl.getEyeLocation(), Effect.FIREWORKS_SPARK, (int)DyeColor.BLUE.getDyeData());
            lIIllllIllIlll.getWorld().playEffect(lIIllllIlIlllI.getEyeLocation(), Effect.FIREWORKS_SPARK, (int)DyeColor.RED.getDyeData());
        }
        if (Joc.lIIllIllllI((int)lIIllllIllIIII.isCancelled())) {
            lIIllllIllIIlI.setDamageDealt(lIIllllIllIIlI.getDamageDealt() + lIIllllIllIIII.getDamage());
        }
    }

    void giveRandomCameraItem(Player lIlIIIIIIlllll) {
        Joc lIlIIIIIlIIIII;
        ItemButton.clearButtons((Player)lIlIIIIIIlllll);
        PlayerInventory lIlIIIIIlIIIlI = lIlIIIIIIlllll.getInventory();
        lIlIIIIIlIIIlI.clear();
        ItemButton lIlIIIIIlIIIIl = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.GOLD_BLOCK), String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIIIIIIll[lIlIlIlIl[41]])), new String[lIlIlIlIl[0]]), lIlIIIIIIlllll, lIIlIlIllIIIll -> {
            Joc lIIlIlIllIIlll;
            Player lIIlIlIllIIlIl = lIIlIlIllIIIll.getPlayer();
            lIIlIlIllIIlll.teleportCameraRandomly(lIIlIlIllIIlIl);
        });
        lIlIIIIIlIIIlI.setItem(lIlIlIlIl[0], lIlIIIIIlIIIIl.getItemStack());
    }

    public int segonsTranscorreguts() {
        Joc lIlIIlIIlIIlll;
        return (int)(lIlIIlIIlIIlll.tempsTranscorregut() / 1000L);
    }

    public int segonsPerIniciar() {
        Joc lIIlllIIIIIlll;
        int lIIlllIIIIIllI = lobby.getLobbyWorld().getPlayers().size();
        int lIIlllIIIIIlIl = lIlIlIlIl[0] + lIIlllIIIIIllI * lIlIlIlIl[4];
        Boolean lIIlllIIIIIlII = lIlIlIlIl[0];
        Iterator<Player> lIIllIllllllll = lIIlllIIIIIlll.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIIllIllllllll.hasNext())) {
            Player lIIlllIIIIlIII = lIIllIllllllll.next();
            if (Joc.lIIllIlllll((int)lIIlllIIIIlIII.isOp())) {
                lIIlllIIIIIlII = lIlIlIlIl[1];
            }
            "".length();
            if (null == null) continue;
            return (73 ^ 68 ^ (24 ^ 119)) & (165 + 41 - 0 + 22 ^ 9 + 8 - -73 + 44 ^ -" ".length());
        }
        if (Joc.lIIllIlllll((int)lIIlllIIIIIlII.booleanValue())) {
            return lIIlllIIIIIlIl + lIlIlIlIl[1];
        }
        if (Joc.lIIllIlllll(lIIlllIIIIIllI)) {
            return lIIlllIIIIIlIl;
        }
        return lIlIlIlIl[6];
    }

    public void registerTimestamps(boolean lIIlIlllllllII) {
        Joc lIIlIlllllllIl;
        if (Joc.lIIlllIIIII(lIIlIlllllllIl.matchData)) {
            return;
        }
        Iterator<Player> lIIlIllllllIll = lIIlIlllllllIl.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIIlIllllllIll.hasNext())) {
            Player lIIllIIIIIIIII = lIIlIllllllIll.next();
            PlayerInfo lIIllIIIIIIIIl = lIIlIlllllllIl.getPlayerInfo(lIIllIIIIIIIII);
            lIIlIlllllllIl.matchData.registerTimestamp(lIIllIIIIIIIII, lIIlIlllllllII, lIIllIIIIIIIIl.getKills(), lIIllIIIIIIIIl.getDeaths(), lIIllIIIIIIIIl.getDamageDealt(), lIIllIIIIIIIIl.isAlive(), lIIllIIIIIIIII.getItemInHand().getTypeId(), lIIllIIIIIIIIl.getBlocksPlaced(), lIIllIIIIIIIIl.getBlocksBroken(), lIIllIIIIIIIIl.getObjectivesCompleted(), lIIllIIIIIIIIl.getSpree());
            "".length();
            if ("  ".length() >= "  ".length()) continue;
            return;
        }
    }

    private void scheduleAnnouncer() {
        Joc lIIllIIIIlIlIl;
        int lIIllIIIIlIlII = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)Com.getPlugin(), () -> {
            Joc lIIlIlllIllllI;
            lIIlIlllIllllI.announce();
        }, 400L, 1500L);
        lIIllIIIIlIlIl.handleTask(lIIllIIIIlIlII);
    }

    public boolean giveSnowLauncherOnKill() {
        return lIlIlIlIl[0];
    }

    private static boolean lIIlllIIlll(Object object) {
        return object != null;
    }

    private static boolean lIlIIlIlIII(Object object, Object object2) {
        return object != object2;
    }

    private static int lIIlllIlIll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public void allOnTheLobby() {
        Joc lIIlllIllllIll;
        Iterator lIIlllIllllIlI = lIIlllIllllIll.world.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIIlllIllllIlI.hasNext())) {
            Player lIIlllIlllllIl = (Player)lIIlllIllllIlI.next();
            Com.teleportPlayerToLobby(lIIlllIlllllIl);
            "".length();
            if (null == null) continue;
            return;
        }
    }

    public boolean canJoin(Player lIlIIIIlllIIII) {
        Joc lIlIIIIlllIIll;
        switch (2.$SwitchMap$com$biel$lobby$mapes$Joc$GameState[lIlIIIIlllIIll.getGameState().ordinal()]) {
            case 1: {
                return lIlIlIlIl[0];
            }
            case 2: {
                return lIlIIIIlllIIII.isOp();
            }
            case 3: {
                return lIlIIIIlllIIll.getAllowSpectators();
            }
            case 4: {
                return lIlIlIlIl[1];
            }
            case 5: {
                return lIlIlIlIl[0];
            }
            case 6: {
                return lIlIlIlIl[1];
            }
        }
        return lIlIlIlIl[1];
    }

    private static boolean lIIlllIlIlI(int n) {
        return n < 0;
    }

    protected Color getDeterministicColorForPlayer(Player lIIlIllllIIlIl, boolean lIIlIllllIlIlI) {
        Color color;
        int lIIlIllllIlIIl = lIIlIllllIIlIl.getName().hashCode();
        Random lIIlIllllIlIII = new Random(lIIlIllllIlIIl);
        Color lIIlIllllIIlll = Color.fromBGR((int)lIIlIllllIlIII.nextInt(lIlIlIlIl[99]), (int)lIIlIllllIlIII.nextInt(lIlIlIlIl[99]), (int)lIIlIllllIlIII.nextInt(lIlIlIlIl[99]));
        Color lIIlIllllIIllI = GUtils.getContrastColor((Color)lIIlIllllIIlll);
        if (Joc.lIIllIlllll((int)lIIlIllllIlIlI)) {
            color = lIIlIllllIIllI;
            "".length();
            if (-" ".length() > (78 + 30 - -12 + 35 ^ 81 + 73 - 109 + 114)) {
                return null;
            }
        } else {
            color = lIIlIllllIIlll;
        }
        return color;
    }

    public void setHost(Player lIlIIIlIIIlIIl) {
        Joc lIlIIIlIIIlIlI;
        int n;
        if (Joc.lIIlllIIlll(lIlIIIlIIIlIlI.host)) {
            n = lIlIlIlIl[1];
            "".length();
            if ((19 + 36 - 9 + 83 ^ 39 + 90 - 75 + 78) == 0) {
                return;
            }
        } else {
            n = lIlIlIlIl[0];
        }
        int lIlIIIlIIIlIll = n;
        lIlIIIlIIIlIlI.host = lIlIIIlIIIlIIl.getName();
        if (Joc.lIIllIlllll(lIlIIIlIIIlIll)) {
            lIlIIIlIIIlIlI.donarItemsInicials(lIlIIIlIIIlIIl);
            lIlIIIlIIIlIlI.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIlIIIlIIIlIIl.getName()).append(lIIIIIIll[lIlIlIlIl[39]])));
        }
    }

    public Boolean areEnemies(Player lIIllIIIlllIII, Player lIIllIIIlllIlI) {
        boolean bl;
        Joc lIIllIIIllllII;
        if (Joc.lIIllIllllI((int)lIIllIIIllllII.areAllies(lIIllIIIlllIII, lIIllIIIlllIlI).booleanValue())) {
            bl = lIlIlIlIl[1];
            "".length();
            if (((97 ^ 42) & ~(75 ^ 0)) != 0) {
                return null;
            }
        } else {
            bl = lIlIlIlIl[0];
        }
        return bl;
    }

    private static String llllIIIlll(String lIIlIlIIIIlIlI, String lIIlIlIIIIlIll) {
        try {
            SecretKeySpec lIIlIlIIIIllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlIlIIIIlIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIlIlIIIIlllI = Cipher.getInstance("Blowfish");
            lIIlIlIIIIlllI.init(lIlIlIlIl[3], lIIlIlIIIIllll);
            return new String(lIIlIlIIIIlllI.doFinal(Base64.getDecoder().decode(lIIlIlIIIIlIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIlIIIIllIl) {
            lIIlIlIIIIllIl.printStackTrace();
            return null;
        }
    }

    public void setBlockBreakPlace(Boolean lIIllIllIIIlIl) {
        lIIllIllIIlIII.blockBreakPlace = lIIllIllIIIlIl;
    }

    protected int getBaseSkillUnlockerAmount() {
        return lIlIlIlIl[0];
    }

    public void setGiveStartingItemsRespawn(Boolean lIIllIlIllllII) {
        lIIllIlIllllIl.giveStartingItemsRespawn = lIIllIlIllllII;
    }

    private static int lIlIIlIIIlI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static String getHealthProgressBar(Player lIIllllllllIIl) {
        String lIIllllllllIII = lIIIIIIll[lIlIlIlIl[43]];
        double lIIlllllllIlll = 10.0;
        double lIIlllllllIllI = lIIllllllllIIl.getHealth() * 100.0 / lIIllllllllIIl.getMaxHealth();
        int lIIlllllllIlIl = (int)Math.ceil(lIIlllllllIllI / lIIlllllllIlll);
        ChatColor lIIlllllllIlII = ChatColor.DARK_GREEN;
        if (Joc.lIIlllIlIlI(Joc.lIIlllIlIIl(lIIlllllllIllI, 65.0))) {
            lIIlllllllIlII = ChatColor.GREEN;
        }
        if (Joc.lIIlllIlIlI(Joc.lIIlllIlIIl(lIIlllllllIllI, 40.0))) {
            lIIlllllllIlII = ChatColor.YELLOW;
        }
        if (Joc.lIIlllIlIlI(Joc.lIIlllIlIIl(lIIlllllllIllI, 25.0))) {
            lIIlllllllIlII = ChatColor.RED;
        }
        if (Joc.lIIlllIlIlI(Joc.lIIlllIlIIl(lIIlllllllIllI, 15.0))) {
            lIIlllllllIlII = ChatColor.DARK_RED;
        }
        if (Joc.lIIllIlllll((int)lIIllllllllIIl.hasPotionEffect(PotionEffectType.ABSORPTION))) {
            lIIlllllllIlII = ChatColor.GOLD;
        }
        String lIIlllllllIIll = String.valueOf(new StringBuilder().append((Object)lIIlllllllIlII).append(lIIIIIIll[lIlIlIlIl[44]]));
        int lIIllllllllIlI = lIlIlIlIl[0];
        while (Joc.lIIlllIlIlI(Joc.lIIlllIlIIl(lIIllllllllIlI, lIIlllllllIlll))) {
            if (Joc.lIIlllIIllI(lIIllllllllIlI, lIIlllllllIlIl)) {
                lIIlllllllIIll = String.valueOf(new StringBuilder().append(lIIlllllllIIll).append((Object)ChatColor.GRAY));
            }
            lIIlllllllIIll = String.valueOf(new StringBuilder().append(lIIlllllllIIll).append(lIIllllllllIII));
            ++lIIllllllllIlI;
            "".length();
            if (((109 ^ 31 ^ (94 ^ 25)) & (214 ^ 195 ^ (32 ^ 0) ^ -" ".length())) <= (119 + 129 - 225 + 137 ^ 30 + 163 - 46 + 17)) continue;
            return null;
        }
        return lIIlllllllIIll;
    }

    public double getMinimumHeight() {
        Joc lIlIIIllIIIIlI;
        double lIlIIIllIIIIIl = 10.0;
        if (Joc.lIIllIlllll((int)lIlIIIllIIIIlI.pMapaActual().ExisteixPropietat(lIIIIIIll[lIlIlIlIl[29]]))) {
            lIlIIIllIIIIIl = lIlIIIllIIIIlI.pMapaActual().ObtenirPropietatInt(lIIIIIIll[lIlIlIlIl[30]]);
        }
        return lIlIIIllIIIIIl;
    }

    protected void donarItemsInicials(Player lIlIIIIllIIlII) {
        Joc lIlIIIIllIIIll;
        lIlIIIIllIIlII.setHealth(lIlIIIIllIIlII.getMaxHealth());
        Utils.clearPlayer(lIlIIIIllIIlII);
        lIlIIIIllIIIll.giveFixedPlaceItems(lIlIIIIllIIlII);
        lIlIIIIllIIIll.giveRemainingUnlockers(lIlIIIIllIIlII);
        lIlIIIIllIIIll.donarEfectesInicials(lIlIIIIllIIlII);
        if (Joc.lIIlllIIlll(lIlIIIIllIIIll.getStartingItems(lIlIIIIllIIlII))) {
            Utils.donarItemsPlayer(lIlIIIIllIIlII, lIlIIIIllIIIll.getStartingItems(lIlIIIIllIIlII));
        }
    }

    public List<Player> getSpectators() {
        Joc lIlIIIIIlllIII;
        return lIlIIIIIlllIII.Espectadors;
    }

    private void scheduleHeartbeat() {
        Joc lIIllIIIllIlIl;
        lIIllIIIllIlIl.heartbeatId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)Com.getPlugin(), () -> {
            Joc lIIlIlllIllIlI;
            if (Joc.lIIllIllllI(Joc.lIlIIlIIllI(lIIlIlllIllIlI.ultraHeartbeatCount % 20L, 0L))) {
                lIIlIlllIllIlI.heartbeat();
            }
            lIIlIlllIllIlI.ultraHeartbeat();
        }, 1L, 1L);
        lIIllIIIllIlIl.handleTask(lIIllIIIllIlIl.heartbeatId);
    }

    protected void registerEloChange(Player lIlIIIllIlllII, double lIlIIIllIlIlll) {
        String string;
        PlayerData lIlIIIllIllIlI = new PlayerData(lIlIIIllIlllII.getName());
        lIlIIIllIllIlI.addElo(lIlIIIllIlIlll);
        if (Joc.lIIlllIIlIl(Joc.lIIlllIIlII(lIlIIIllIlIlll, 0.0))) {
            string = String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_GREEN).append(lIIIIIIll[lIlIlIlIl[20]]));
            "".length();
            if (-" ".length() >= "   ".length()) {
                return;
            }
        } else {
            string = String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_RED).append(lIIIIIIll[lIlIlIlIl[23]]));
        }
        String lIlIIIllIllIIl = String.valueOf(new StringBuilder().append(string).append(Double.toString(Math.round(lIlIIIllIlIlll * 10.0) / 10L)));
        lIlIIIllIlllII.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_AQUA).append(lIIIIIIll[lIlIlIlIl[24]]).append((Object)ChatColor.WHITE).append(Math.round(lIlIIIllIllIlI.getElo())).append(lIIIIIIll[lIlIlIlIl[25]]).append(lIlIIIllIllIIl).append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[26]])));
    }

    protected void finalize() throws Throwable {
        Joc lIlIIlIlIIlllI;
        lIlIIlIlIIlllI.clearExternals();
        super.finalize();
    }

    public boolean canBeStartedBy(Player lIIllIllllIlII, boolean lIIllIllllIlll) {
        Joc lIIllIllllIlIl;
        int lIIllIllllIllI = lIIllIllllIlIl.segonsPerIniciar() - lIIllIllllIlIl.segonsTranscorreguts();
        if (!Joc.lIIlllIIlIl(lIIllIllllIllI) || Joc.lIIllIlllll((int)lIIllIllllIlII.isOp())) {
            return lIlIlIlIl[1];
        }
        if (Joc.lIIllIlllll((int)lIIllIllllIlll)) {
            lIIllIllllIlII.sendMessage(String.valueOf(new StringBuilder().append(lIIIIIIll[lIlIlIlIl[81]]).append((Object)ChatColor.YELLOW).append(Integer.toString(lIIllIllllIllI)).append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[82]])));
        }
        return lIlIlIlIl[0];
    }

    protected void registerSkills() {
        Joc lIlIIIIlIlIllI;
        lIlIIIIlIlIllI.s.registerSkill(new CalciumSourceSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new AssaultSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new GravityBendingSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new FrostArcherSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new SwordsmanSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new VampireSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new DeflectorSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new BerserkSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new SpeedyArcher(null));
        lIlIIIIlIlIllI.s.registerSkill(new DiamondCoreSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new CorinthianHelmetSkill(null));
        lIlIIIIlIlIllI.s.registerSkill(new MagicArcherSkill(null));
    }

    protected void onPlayerDeath(PlayerDeathEvent lIIllllIIllIll, Player lIIllllIIlIlII) {
        Joc lIIllllIIlIllI;
        super.onPlayerDeath(lIIllllIIllIll, lIIllllIIlIlII);
        ArrayList<ItemStack> lIIllllIIllIIl = new ArrayList<ItemStack>();
        List lIIllllIIllIII = lIIllllIIllIll.getDrops();
        Iterator lIIllllIIlIIIl = lIIllllIIllIII.iterator();
        while (Joc.lIIllIlllll((int)lIIllllIIlIIIl.hasNext())) {
            ItemStack lIIllllIIlllIl = (ItemStack)lIIllllIIlIIIl.next();
            Material lIIllllIIlllll = lIIllllIIlllIl.getType();
            int lIIllllIIllllI = lIlIlIlIl[0];
            if (Joc.lIIllIlllll((int)lIIllllIIlllIl.hasItemMeta())) {
                lIIllllIIllllI = lIIllllIIlllIl.getItemMeta().hasDisplayName() ? 1 : 0;
            }
            if (Joc.lIIllIllllI((int)lIIllllIIlIllI.canBeDropped(lIIllllIIlllIl, lIIllllIIlIlII)) && Joc.lIIlllIlIII((Object)lIIllllIIlllIl.getType(), (Object)lIIllllIIlllIl.getType())) {
                ItemStack lIIllllIlIIIII = lIIllllIIlllIl.clone();
                lIIllllIlIIIII.setAmount(lIIllllIIlllIl.getAmount());
                "".length();
                lIIllllIIllIIl.add(lIIllllIlIIIII);
            }
            "".length();
            if (-" ".length() <= (23 ^ 19)) continue;
            return;
        }
        "".length();
        lIIllllIIllIII.removeAll(lIIllllIIllIIl);
        List lIIllllIIlIlll = GUtils.subtractInventoryContents((List)lIIllllIIllIII, lIIllllIIlIllI.getStartingItems(lIIllllIIlIlII));
        lIIllllIIllIII.clear();
        "".length();
        lIIllllIIllIII.addAll(lIIllllIIlIlll);
    }

    private static int lIlIIIllIlI(float f, float f2) {
        return (int)(f FCMPL f2);
    }

    private static boolean lIIlllIIIIl(int n, int n2) {
        return n < n2;
    }

    public boolean JocEnMarxa() {
        Joc lIlIIlIIllllIl;
        int n;
        if (Joc.lIIllIlllll((int)lIlIIlIIllllIl.JocIniciat.booleanValue()) && Joc.lIIllIllllI((int)lIlIIlIIllllIl.JocFinalitzat.booleanValue())) {
            n = lIlIlIlIl[1];
            "".length();
            if ("   ".length() <= 0) {
                return (boolean)((186 ^ 141) & ~(39 ^ 16));
            }
        } else {
            n = lIlIlIlIl[0];
        }
        return (boolean)n;
    }

    protected Boolean verifyEvent(Event lIIlIlllllIIll) {
        Joc lIIlIlllllIlII;
        int n;
        if (Joc.lIIllIlllll((int)super.verifyEvent(lIIlIlllllIIll).booleanValue()) && Joc.lIIllIllllI((int)EventUtils.interactsWithAny((Object)lIIlIlllllIIll, lIIlIlllllIlII.getSpectators(), (int)lIlIlIlIl[7]))) {
            n = lIlIlIlIl[1];
            "".length();
            if (" ".length() < " ".length()) {
                return null;
            }
        } else {
            n = lIlIlIlIl[0];
        }
        return (boolean)n;
    }

    protected void updateScoreBoards() {
        Joc lIIlllIIIlIIIl;
        lIIlllIIIlIIIl.getViewers().forEach(lIIlllIIIlIIIl::updateScoreBoard);
    }

    public double getGameProgressETA() {
        Joc lIIlllIlIIIllI;
        if (Joc.lIIllIllllI((int)lIIlllIlIIIllI.JocEnMarxa())) {
            if (Joc.lIIllIllllI((int)lIIlllIlIIIllI.JocIniciat.booleanValue())) {
                return 0.0;
            }
            if (Joc.lIIllIlllll((int)lIIlllIlIIIllI.JocFinalitzat.booleanValue())) {
                return 1.0;
            }
        }
        return (double)lIIlllIlIIIllI.getGameTime().toMillis() / (double)lIIlllIlIIIllI.getAvgGameLength().toMillis();
    }

    public boolean hasHostPrivilleges(Player lIlIIIIlllllII) {
        Joc lIlIIIIlllllIl;
        return lIlIIIIlllllIl.host.equalsIgnoreCase(lIlIIIIlllllII.getName());
    }

    private void teleportCameraRandomly(Player lIlIIIIIIlIlIl) {
        Joc lIlIIIIIIllIIl;
        ArrayList<Player> lIlIIIIIIlIlll = lIlIIIIIIllIIl.getPlayers();
        if (Joc.lIIllIlllll(lIlIIIIIIlIlll.size())) {
            Collections.shuffle(lIlIIIIIIlIlll);
            "".length();
            lIlIIIIIIlIlIl.teleport((Entity)lIlIIIIIIlIlll.get(lIlIlIlIl[0]));
        }
    }

    protected void donarItemsPreparatiusGenerals(Player lIIllIllIlllII) {
        Joc lIIllIllIlllIl;
        ItemButton.clearButtons((Player)lIIllIllIlllII);
        PlayerInventory lIIllIlllIIIlI = lIIllIllIlllII.getInventory();
        ItemButton lIIllIlllIIIIl = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BLAZE_ROD), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIIIll[lIlIlIlIl[83]])), new String[lIlIlIlIl[0]]), lIIllIllIlllII, lIIlIlIlllIlII -> {
            Joc lIIlIlIlllIlll;
            lIIlIlIlllIlll.iniciarCommand(lIIlIlIlllIlII.getPlayer());
        });
        if (Joc.lIIllIlllll((int)lIIllIllIlllIl.hasHostPrivilleges(lIIllIllIlllII))) {
            lIIllIlllIIIlI.setItem(lIlIlIlIl[0], lIIllIlllIIIIl.getItemStack());
        }
        ItemButton lIIllIlllIIIII = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.POWERED_RAIL), String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(lIIIIIIll[lIlIlIlIl[84]]).append(lIIllIllIlllIl.getGameName())), new String[lIlIlIlIl[0]]), lIIllIllIlllII, lIIlIlIllllIlI -> {
            Joc lIIlIlIllllIll;
            lIIlIlIllllIll.anunciarWiki(lIIlIlIllllIlI.getPlayer(), lIlIlIlIl[1]);
        });
        ItemButton lIIllIllIlllll = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.SKULL_ITEM), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIIIll[lIlIlIlIl[85]])), new String[lIlIlIlIl[0]]), lIIllIllIlllII, lIIlIllIIlllll -> {
            Joc lIIlIllIlIIIIl;
            List lIIlIllIIllllI = lobby.getLobbyWorld().getPlayers();
            IconMenu lIIlIllIIlllIl = new IconMenu(lIIIIIIll[lIlIlIlIl[46]], lIlIlIlIl[29], lIIlIllIIIlIII -> {
                Joc lIIlIllIIIlIll;
                lIIlIllIIIlIII.setWillClose(lIlIlIlIl[1]);
                int lIIlIllIIIIlll = lIIlIllIIIlIII.getPosition();
                if (Joc.lIlIIlIIlll(lIIlIllIIIIlll, lIlIlIlIl[28])) {
                    Player lIIlIllIIIllIl = (Player)lIIlIllIIllllI.get(lIIlIllIIIIlll);
                    if (Joc.lIIllIlllll((int)lobby.isOnLobby(Bukkit.getPlayer((String)lIIlIllIIIllIl.getName())).booleanValue())) {
                        lIIlIllIIIlIll.Join(lIIlIllIIIllIl);
                        "".length();
                        if ("   ".length() == 0) {
                            return;
                        }
                    } else {
                        lIIlIllIlIIIII.sendMessage(lIIIIIIll[lIlIlIlIl[110]]);
                    }
                    "".length();
                    if (((127 ^ 112) & ~(123 ^ 116)) < -" ".length()) {
                        return;
                    }
                } else {
                    Iterator lIIlIllIIIllIl = lIIlIllIIllllI.iterator();
                    while (Joc.lIIllIlllll((int)lIIlIllIIIllIl.hasNext())) {
                        Player lIIlIllIIIllII = (Player)lIIlIllIIIllIl.next();
                        if (Joc.lIIllIlllll((int)lobby.isOnLobby(Bukkit.getPlayer((String)lIIlIllIIIllII.getName())).booleanValue())) {
                            lIIlIllIIIlIll.Join(lIIlIllIIIllII);
                            "".length();
                            if (null != null) {
                                return;
                            }
                        } else {
                            lIIlIllIlIIIII.sendMessage(String.valueOf(new StringBuilder().append(lIIIIIIll[lIlIlIlIl[111]]).append(lIIlIllIIIllII.getName()).append(lIIIIIIll[lIlIlIlIl[112]])));
                        }
                        "".length();
                        if (((161 + 188 - 298 + 191 ^ 77 + 37 - 5 + 63) & (94 ^ 88 ^ (125 ^ 37) ^ -" ".length())) > -" ".length()) continue;
                        return;
                    }
                }
            });
            Iterator lIIlIllIIllIII = lIIlIllIIllllI.iterator();
            while (Joc.lIIllIlllll((int)lIIlIllIIllIII.hasNext())) {
                Player lIIlIllIlIIIlI = (Player)lIIlIllIIllIII.next();
                Material lIIlIllIlIIlII = Com.getSkullIconMaterial(lIIlIllIlIIIlI);
                ItemStack lIIlIllIlIIIll = new ItemStack(lIIlIllIlIIlII, lIlIlIlIl[1]);
                String[] arrstring = new String[lIlIlIlIl[1]];
                arrstring[Joc.lIlIlIlIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[107]]));
                "".length();
                lIIlIllIIlllIl.setOption(lIIlIllIIllllI.indexOf((Object)lIIlIllIlIIIlI), lIIlIllIlIIIll, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIIlIllIlIIIlI.getName())), arrstring);
                "".length();
                if (-" ".length() != (98 ^ 102)) continue;
                return;
            }
            String[] arrstring = new String[lIlIlIlIl[1]];
            arrstring[Joc.lIlIlIlIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[109]]));
            "".length();
            lIIlIllIIlllIl.setOption(lIlIlIlIl[28], new ItemStack(Material.SPONGE, lIlIlIlIl[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIIIIIll[lIlIlIlIl[108]])), arrstring);
            lIIlIllIIlllIl.open(lIIllIllIlllII);
        });
        lIIllIlllIIIlI.setItem(lIlIlIlIl[7], lIIllIlllIIIII.getItemStack());
        ItemButton lIIllIllIllllI = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.DETECTOR_RAIL), String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIIIll[lIlIlIlIl[86]])), new String[lIlIlIlIl[0]]), lIIllIllIlllII, lIIlIlllIIllII -> {
            Joc lIIlIlllIIlllI;
            List lIIlIlllIIlIll = lobby.getLobbyWorld().getPlayers();
            IconMenu lIIlIlllIIlIlI = new IconMenu(lIIIIIIll[lIlIlIlIl[100]], lIlIlIlIl[29], lIIlIllIllIIII -> {
                Joc lIIlIllIlllIII;
                lIIlIllIllIIII.setWillClose(lIlIlIlIl[1]);
                int lIIlIllIllIlII = lIIlIllIllIIII.getPosition();
                if (Joc.lIlIIlIIlll(lIIlIllIllIlII, lIlIlIlIl[28])) {
                    Player lIIlIllIlllIlI = (Player)lIIlIlllIIlIll.get(lIIlIllIllIlII);
                    if (Joc.lIIllIlllll((int)lobby.isOnLobby(Bukkit.getPlayer((String)lIIlIllIlllIlI.getName())).booleanValue())) {
                        lIIlIllIlllIII.inviteToGame(lIIlIllIlllIlI);
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else {
                        lIIlIlllIIllIl.sendMessage(lIIIIIIll[lIlIlIlIl[104]]);
                    }
                    "".length();
                    if (((183 ^ 148) & ~(68 ^ 103)) < 0) {
                        return;
                    }
                } else {
                    Iterator lIIlIllIlllIlI = lIIlIlllIIlIll.iterator();
                    while (Joc.lIIllIlllll((int)lIIlIllIlllIlI.hasNext())) {
                        Player lIIlIllIlllIIl = (Player)lIIlIllIlllIlI.next();
                        if (Joc.lIIllIlllll((int)lobby.isOnLobby(Bukkit.getPlayer((String)lIIlIllIlllIIl.getName())).booleanValue())) {
                            lIIlIllIlllIII.inviteToGame(lIIlIllIlllIIl);
                            "".length();
                            if (-"   ".length() >= 0) {
                                return;
                            }
                        } else {
                            lIIlIlllIIllIl.sendMessage(String.valueOf(new StringBuilder().append(lIIIIIIll[lIlIlIlIl[105]]).append(lIIlIllIlllIIl.getName()).append(lIIIIIIll[lIlIlIlIl[106]])));
                        }
                        "".length();
                        if (null == null) continue;
                        return;
                    }
                }
            });
            Iterator<E> lIIlIlllIIIlIl = lIIlIlllIIlIll.iterator();
            while (Joc.lIIllIlllll((int)lIIlIlllIIIlIl.hasNext())) {
                Player lIIlIlllIIllll = (Player)lIIlIlllIIIlIl.next();
                Material lIIlIlllIlIIIl = Com.getSkullIconMaterial(lIIlIlllIIllll);
                ItemStack lIIlIlllIlIIII = new ItemStack(lIIlIlllIlIIIl, lIlIlIlIl[1]);
                String[] arrstring = new String[lIlIlIlIl[1]];
                arrstring[Joc.lIlIlIlIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[101]]));
                "".length();
                lIIlIlllIIlIlI.setOption(lIIlIlllIIlIll.indexOf((Object)lIIlIlllIIllll), lIIlIlllIlIIII, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIIlIlllIIllll.getName())), arrstring);
                "".length();
                if ("   ".length() >= 0) continue;
                return;
            }
            String[] arrstring = new String[lIlIlIlIl[1]];
            arrstring[Joc.lIlIlIlIl[0]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[103]]));
            "".length();
            lIIlIlllIIlIlI.setOption(lIlIlIlIl[28], new ItemStack(Material.SPONGE, lIlIlIlIl[1]), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIIIIIll[lIlIlIlIl[102]])), arrstring);
            lIIlIlllIIlIlI.open(lIIllIllIlllII);
        });
        if (Joc.lIIllIlllll((int)lIIllIllIlllIl.hasHostPrivilleges(lIIllIllIlllII))) {
            lIIllIlllIIIlI.setItem(lIlIlIlIl[9], lIIllIllIllllI.getItemStack());
        }
    }

    public void JocFinalitzat() {
        Joc lIlIIlIIlIIlII;
        if (Joc.lIIllIllllI((int)lIlIIlIIlIIlII.JocIniciat.booleanValue())) {
            "".length();
            Bukkit.broadcastMessage((String)lIIIIIIll[lIlIlIlIl[13]]);
            return;
        }
        if (Joc.lIIllIlllll((int)lIlIIlIIlIIlII.JocFinalitzat.booleanValue())) {
            "".length();
            Bukkit.broadcastMessage((String)lIIIIIIll[lIlIlIlIl[14]]);
            return;
        }
        lIlIIlIIlIIlII.world.setPVP(lIlIlIlIl[0]);
        lIlIIlIIlIIlII.customJocFinalitzat();
        lIlIIlIIlIIlII.clearExternals();
        if (Joc.lIIllIllllI((int)lIlIIlIIlIIlII.won)) {
            lIlIIlIIlIIlII.matchData.registerEnd(lIlIlIlIl[2]);
        }
        lIlIIlIIlIIlII.registerTimestamps(lIlIlIlIl[1]);
        lIlIIlIIlIIlII.JocFinalitzat = lIlIlIlIl[1];
        lIlIIlIIlIIlII.updateScoreBoards();
    }

    private static String llllIIIlIl(String lIIlIIlllIllll, String lIIlIIlllIlllI) {
        lIIlIIlllIllll = new String(Base64.getDecoder().decode(lIIlIIlllIllll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIlIIlllIllIl = new StringBuilder();
        char[] lIIlIIlllIllII = lIIlIIlllIlllI.toCharArray();
        int lIIlIIlllIlIll = lIlIlIlIl[0];
        char[] lIIlIIlllIIlIl = lIIlIIlllIllll.toCharArray();
        int lIIlIIlllIIlII = lIIlIIlllIIlIl.length;
        int lIIlIIlllIIIll = lIlIlIlIl[0];
        while (Joc.lIIlllIIIIl(lIIlIIlllIIIll, lIIlIIlllIIlII)) {
            char lIIlIIllllIIII = lIIlIIlllIIlIl[lIIlIIlllIIIll];
            "".length();
            lIIlIIlllIllIl.append((char)(lIIlIIllllIIII ^ lIIlIIlllIllII[lIIlIIlllIlIll % lIIlIIlllIllII.length]));
            ++lIIlIIlllIlIll;
            ++lIIlIIlllIIIll;
            "".length();
            if (" ".length() != 0) continue;
            return null;
        }
        return String.valueOf(lIIlIIlllIllIl);
    }

    private static int lIlIIIllIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    protected boolean canBeDropped(ItemStack lIlIIIIlIIllIl, Player lIlIIIIlIIllll) {
        Joc lIlIIIIlIlIIIl;
        if (Joc.lIIlllIIlll(lIlIIIIlIlIIIl.getStartingItems(lIlIIIIlIIllll))) {
            if (Joc.lIIlllIlIII((Object)lIlIIIIlIIllIl.getType(), (Object)Material.CHEST)) {
                return lIlIlIlIl[0];
            }
            if (Joc.lIIlllIlIII((Object)lIlIIIIlIIllIl.getType(), (Object)Material.DIAMOND_BLOCK)) {
                return lIlIlIlIl[0];
            }
            if (Joc.lIIllIlllll((int)Utils.isArmor(lIlIIIIlIIllIl))) {
                return lIlIlIlIl[0];
            }
        }
        return lIlIlIlIl[1];
    }

    private static int lIlIIlIIllI(long l, long l2) {
        return (int)(l LCMP l2);
    }

    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent lIIllIlIIIllIl, Player lIIllIlIIIllII) {
        Joc lIIllIlIIIlllI;
        super.onPlayerRespawnAfterTick(lIIllIlIIIllIl, lIIllIlIIIllII);
        PlayerInfo lIIllIlIIIlIll = lIIllIlIIIlllI.getPlayerInfo(lIIllIlIIIllII);
        lIIllIlIIIlIll.lastMoveEvent = ZonedDateTime.now();
        lIIllIlIIIlIll.setImmune(lIlIlIlIl[1]);
        lIIllIlIIIlIll.lastRespawnEvent = ZonedDateTime.now();
        if (Joc.lIIllIlllll((int)lIIllIlIIIlllI.getResetPlayerOnRespawn())) {
            Utils.clearPlayer(lIIllIlIIIllII);
            lIIllIlIIIlllI.donarItemsInicials(lIIllIlIIIllII);
        }
    }

    public void announce() {
        Joc lIIllIIIIIlllI;
        Long lIIllIIIIIlIll = lIIllIIIIIlllI.announceCount;
        Long lIIllIIIIIlIlI = lIIllIIIIIlllI.announceCount = Long.valueOf(lIIllIIIIIlllI.announceCount + 1L);
        "".length();
        String lIIllIIIIIllIl = String.valueOf(new StringBuilder().append(lIIIIIIll[lIlIlIlIl[93]]).append(Com.getMinicatString()).append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[94]]).append((Object)ChatColor.GRAY));
        lIIllIIIIIlllI.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIIllIIIIIllIl).append(lIIIIIIll[lIlIlIlIl[95]]).append(lIIllIIIIIlllI.getGameName()).append(lIIIIIIll[lIlIlIlIl[96]]).append(lIIllIIIIIlllI.getActiveMultipleMapName()).append(lIIIIIIll[lIlIlIlIl[97]]).append(Math.round(lIIllIIIIIlllI.getGameProgressETA() * 10.0 * 100.0) / 10L).append(lIIIIIIll[lIlIlIlIl[98]])));
    }

    @Override
    public void Join(Player lIlIIIIllllIII) {
        Joc lIlIIIIllllIIl;
        if (Joc.lIIllIlllll((int)lIlIIIIllllIIl.canJoin(lIlIIIIllllIII)) && Joc.lIIllIllllI(lIlIIIIllllIIl.getPlayers().size())) {
            lIlIIIIllllIIl.setHost(lIlIIIIllllIII);
        }
        super.Join(lIlIIIIllllIII);
    }

    public ArrayList<Player> getPlayers() {
        Joc lIlIIIIlIIIllI;
        ArrayList<Player> lIlIIIIlIIIlIl = lIlIIIIlIIIllI.getViewers();
        "".length();
        lIlIIIIlIIIlIl.removeAll(lIlIIIIlIIIllI.Espectadors);
        return lIlIIIIlIIIlIl;
    }

    private static boolean lIlIIIllIIl(int n, int n2) {
        return n <= n2;
    }

    public double getPunishForLeaving(Player lIIlllIlIIllIl) {
        Joc lIIlllIlIIlIlI;
        double lIIlllIlIIllII;
        double lIIlllIlIIlIll = lIIlllIlIIllII = 4.2 + lIIlllIlIIlIlI.getEloK() / 8.0 + (double)(lIIlllIlIIlIlI.getAvgGameLength().toHours() * 4L);
        if (Joc.lIIlllIlIlI(Joc.lIlIIIlIlll(lIIlllIlIIlIlI.getGameProgressETA(), 0.25))) {
            lIIlllIlIIlIll = 0.0;
        }
        lIIlllIlIIlIll = lIIlllIlIIllII * (lIIlllIlIIlIlI.getGameProgressETA() - 0.2);
        if (Joc.lIIlllIIlIl(Joc.lIlIIIllIII(lIIlllIlIIlIlI.getGameProgressETA(), 0.8))) {
            lIIlllIlIIlIll = lIIlllIlIIllII;
        }
        if (!Joc.lIIllIlllll((int)lIIlllIlIIlIlI.JocEnMarxa()) || !Joc.lIIllIlllll(Joc.lIlIIIllIII(lIIlllIlIIlIlI.getEloK(), 0.0)) || Joc.lIlIIIllIIl(lIIlllIlIIlIlI.getPlayers().size(), lIlIlIlIl[1])) {
            lIIlllIlIIlIll = 0.0;
        }
        return Math.max(0.0, lIIlllIlIIlIll);
    }

    public Long getUltraHeartbeatCount() {
        Joc lIIllIIIIllIll;
        return lIIllIIIIllIll.ultraHeartbeatCount;
    }

    public Boolean isSpectator(Player lIlIIIIIllIIlI) {
        Joc lIlIIIIIllIIll;
        return lIlIIIIIllIIll.Espectadors.contains((Object)lIlIIIIIllIIlI);
    }

    public void setHost(String lIlIIIlIIIIlII) {
        lIlIIIlIIIIIll.host = lIlIIIlIIIIlII;
    }

    private static void lIIllIlllIl() {
        lIlIlIlIl = new int[114];
        Joc.lIlIlIlIl[0] = (4 ^ 49) & ~(28 ^ 41);
        Joc.lIlIlIlIl[1] = " ".length();
        Joc.lIlIlIlIl[2] = -" ".length();
        Joc.lIlIlIlIl[3] = "  ".length();
        Joc.lIlIlIlIl[4] = "   ".length();
        Joc.lIlIlIlIl[5] = 46 + 120 - 72 + 95 ^ 168 + 96 - 116 + 37;
        Joc.lIlIlIlIl[6] = 23 ^ 18;
        Joc.lIlIlIlIl[7] = "  ".length() ^ (65 ^ 69);
        Joc.lIlIlIlIl[8] = 9 ^ 64 ^ (73 ^ 7);
        Joc.lIlIlIlIl[9] = 124 ^ 116;
        Joc.lIlIlIlIl[10] = 144 ^ 178 ^ (232 ^ 195);
        Joc.lIlIlIlIl[11] = 164 ^ 174;
        Joc.lIlIlIlIl[12] = 16 ^ 27;
        Joc.lIlIlIlIl[13] = 139 ^ 135;
        Joc.lIlIlIlIl[14] = 201 ^ 196;
        Joc.lIlIlIlIl[15] = 170 ^ 164;
        Joc.lIlIlIlIl[16] = 165 ^ 170;
        Joc.lIlIlIlIl[17] = 112 ^ 96;
        Joc.lIlIlIlIl[18] = 12 ^ 29;
        Joc.lIlIlIlIl[19] = -(-12034 & 16221) & (-19489 & 24575);
        Joc.lIlIlIlIl[20] = 166 ^ 178;
        Joc.lIlIlIlIl[21] = 110 + 72 - 170 + 123 ^ 30 + 144 - 117 + 92;
        Joc.lIlIlIlIl[22] = 168 ^ 174 ^ (55 ^ 34);
        Joc.lIlIlIlIl[23] = 130 ^ 151;
        Joc.lIlIlIlIl[24] = 17 ^ 7;
        Joc.lIlIlIlIl[25] = 142 ^ 134 ^ (173 ^ 178);
        Joc.lIlIlIlIl[26] = 78 ^ 86;
        Joc.lIlIlIlIl[27] = 105 ^ 17 ^ (192 ^ 161);
        Joc.lIlIlIlIl[28] = 164 + 64 - 74 + 31 ^ 124 + 73 - 53 + 19;
        Joc.lIlIlIlIl[29] = 240 ^ 164 ^ (205 ^ 130);
        Joc.lIlIlIlIl[30] = 100 ^ 19 ^ (194 ^ 169);
        Joc.lIlIlIlIl[31] = 73 ^ 34 ^ (47 ^ 89);
        Joc.lIlIlIlIl[32] = 216 ^ 198;
        Joc.lIlIlIlIl[33] = 172 ^ 179;
        Joc.lIlIlIlIl[34] = 43 + 128 - 91 + 61 ^ 104 + 101 - 44 + 12;
        Joc.lIlIlIlIl[35] = 119 ^ 86;
        Joc.lIlIlIlIl[36] = 237 ^ 195 ^ (24 ^ 20);
        Joc.lIlIlIlIl[37] = 154 ^ 185;
        Joc.lIlIlIlIl[38] = 104 ^ 76;
        Joc.lIlIlIlIl[39] = 204 ^ 190 ^ (65 ^ 22);
        Joc.lIlIlIlIl[40] = 128 ^ 166;
        Joc.lIlIlIlIl[41] = 47 ^ 109 ^ (16 ^ 117);
        Joc.lIlIlIlIl[42] = 135 ^ 175;
        Joc.lIlIlIlIl[43] = 181 ^ 156;
        Joc.lIlIlIlIl[44] = 196 ^ 174 ^ (69 ^ 5);
        Joc.lIlIlIlIl[45] = 87 + 223 - 243 + 165 ^ 142 + 132 - 130 + 51;
        Joc.lIlIlIlIl[46] = 93 ^ 57;
        Joc.lIlIlIlIl[47] = (186 ^ 192) + (189 ^ 181) - (193 ^ 144) + (39 ^ 89);
        Joc.lIlIlIlIl[48] = 253 ^ 159 ^ (58 ^ 116);
        Joc.lIlIlIlIl[49] = 154 ^ 183;
        Joc.lIlIlIlIl[50] = 88 ^ 118;
        Joc.lIlIlIlIl[51] = 10 ^ 37;
        Joc.lIlIlIlIl[52] = (197 ^ 147) + (82 ^ 99) - (114 ^ 70) + (82 ^ 17);
        Joc.lIlIlIlIl[53] = 83 + 2 - 32 + 100 ^ 153 + 156 - 190 + 50;
        Joc.lIlIlIlIl[54] = 37 ^ 6 ^ (82 ^ 64);
        Joc.lIlIlIlIl[55] = 72 ^ 122;
        Joc.lIlIlIlIl[56] = 64 ^ 45 ^ (126 ^ 32);
        Joc.lIlIlIlIl[57] = 217 ^ 193 ^ (27 ^ 55);
        Joc.lIlIlIlIl[58] = 150 + 4 - 100 + 128 ^ 57 + 44 - 96 + 126;
        Joc.lIlIlIlIl[59] = -1089 & 1488;
        Joc.lIlIlIlIl[60] = 44 ^ 26;
        Joc.lIlIlIlIl[61] = 80 ^ 103;
        Joc.lIlIlIlIl[62] = 185 ^ 129;
        Joc.lIlIlIlIl[63] = (185 ^ 155) & ~(146 ^ 176) ^ (133 ^ 188);
        Joc.lIlIlIlIl[64] = 47 ^ 21;
        Joc.lIlIlIlIl[65] = 34 ^ 25;
        Joc.lIlIlIlIl[66] = 47 ^ 19;
        Joc.lIlIlIlIl[67] = 43 ^ 22;
        Joc.lIlIlIlIl[68] = 25 ^ 39;
        Joc.lIlIlIlIl[69] = 5 + 36 - -35 + 74 ^ 51 + 56 - -51 + 11;
        Joc.lIlIlIlIl[70] = 85 ^ 21;
        Joc.lIlIlIlIl[71] = 253 ^ 188;
        Joc.lIlIlIlIl[72] = 132 ^ 142 ^ (36 ^ 108);
        Joc.lIlIlIlIl[73] = 90 ^ 114 ^ (96 ^ 11);
        Joc.lIlIlIlIl[74] = 80 ^ 34 ^ (19 ^ 37);
        Joc.lIlIlIlIl[75] = 119 ^ 87 ^ (231 ^ 130);
        Joc.lIlIlIlIl[76] = 115 ^ 110 ^ (97 ^ 58);
        Joc.lIlIlIlIl[77] = 11 ^ 76;
        Joc.lIlIlIlIl[78] = 133 + 250 - 244 + 116 ^ 89 + 123 - 69 + 40;
        Joc.lIlIlIlIl[79] = 212 ^ 157;
        Joc.lIlIlIlIl[80] = 239 ^ 165;
        Joc.lIlIlIlIl[81] = 32 ^ 107;
        Joc.lIlIlIlIl[82] = 132 + 46 - -28 + 26 ^ 160 + 8 - 82 + 78;
        Joc.lIlIlIlIl[83] = 30 ^ 83;
        Joc.lIlIlIlIl[84] = 54 ^ 121 ^ " ".length();
        Joc.lIlIlIlIl[85] = 53 ^ 122;
        Joc.lIlIlIlIl[86] = 71 ^ 23;
        Joc.lIlIlIlIl[87] = 18 ^ 35 ^ (240 ^ 144);
        Joc.lIlIlIlIl[88] = 113 ^ 85 ^ (113 ^ 7);
        Joc.lIlIlIlIl[89] = 71 + 60 - 41 + 37 ^ (185 ^ 149);
        Joc.lIlIlIlIl[90] = 113 ^ 37;
        Joc.lIlIlIlIl[91] = 101 ^ 16 ^ (187 ^ 155);
        Joc.lIlIlIlIl[92] = 46 ^ 120;
        Joc.lIlIlIlIl[93] = 42 ^ 125;
        Joc.lIlIlIlIl[94] = 107 ^ 51;
        Joc.lIlIlIlIl[95] = 232 ^ 131 ^ (74 ^ 120);
        Joc.lIlIlIlIl[96] = 91 ^ 1;
        Joc.lIlIlIlIl[97] = 98 ^ 16 ^ (26 ^ 51);
        Joc.lIlIlIlIl[98] = 158 ^ 130 ^ (25 ^ 89);
        Joc.lIlIlIlIl[99] = 0 + 25 - -170 + 60;
        Joc.lIlIlIlIl[100] = 205 ^ 191 ^ (131 ^ 172);
        Joc.lIlIlIlIl[101] = 7 + 220 - 134 + 162 ^ 43 + 81 - -25 + 12;
        Joc.lIlIlIlIl[102] = 110 ^ 49;
        Joc.lIlIlIlIl[103] = 88 + 1 - -73 + 31 ^ 48 + 100 - 36 + 49;
        Joc.lIlIlIlIl[104] = 77 ^ 44;
        Joc.lIlIlIlIl[105] = 3 ^ 26 ^ (28 ^ 103);
        Joc.lIlIlIlIl[106] = 162 ^ 193;
        Joc.lIlIlIlIl[107] = 125 ^ 24;
        Joc.lIlIlIlIl[108] = 204 ^ 171 ^ " ".length();
        Joc.lIlIlIlIl[109] = 96 ^ 7;
        Joc.lIlIlIlIl[110] = 113 ^ 25;
        Joc.lIlIlIlIl[111] = 118 ^ 31;
        Joc.lIlIlIlIl[112] = 216 ^ 185 ^ (29 ^ 22);
        Joc.lIlIlIlIl[113] = 224 + 148 - 198 + 64 ^ 18 + 48 - -48 + 19;
    }

    double getEloK() {
        Joc lIlIIIllIlIIlI;
        int lIlIIIllIlIIIl = lIlIlIlIl[13];
        if (Joc.lIIllIlllll((int)lIlIIIllIlIIlI.pMapaActual().ExisteixPropietat(lIIIIIIll[lIlIlIlIl[27]]))) {
            lIlIIIllIlIIIl = lIlIIIllIlIIlI.pMapaActual().ObtenirPropietatInt(lIIIIIIll[lIlIlIlIl[28]]);
        }
        return (double)lIlIIIllIlIIIl * lIlIIIllIlIIlI.getEloM();
    }

    public Boolean getBlockBreakPlace() {
        Joc lIIllIllIIllII;
        return lIIllIllIIllII.blockBreakPlace;
    }

    private static boolean lIIlllIIIII(Object object) {
        return object == null;
    }

    public boolean getDisplayHealthBar() {
        return lIlIlIlIl[1];
    }

    public void ultraHeartbeat() {
        Joc lIIllIIIlIlllI;
        Object lIIllIIIlIllIl = lIIllIIIlIlllI.ultraHeartbeatCount;
        Long lIIllIIIlIllII = lIIllIIIlIlllI.ultraHeartbeatCount = Long.valueOf(lIIllIIIlIlllI.ultraHeartbeatCount + 1L);
        "".length();
        lIIllIIIlIllIl = lIIllIIIlIlllI.InfoStorage.iterator();
        while (Joc.lIIllIlllll((int)lIIllIIIlIllIl.hasNext())) {
            PlayerInfo lIIllIIIllIIII = (PlayerInfo)lIIllIIIlIllIl.next();
            lIIllIIIllIIII.ultraTick();
            "".length();
            if (((107 + 128 - 216 + 142 ^ 164 + 78 - 91 + 30) & (221 ^ 193 ^ (97 ^ 105) ^ -" ".length())) < "   ".length()) continue;
            return;
        }
        lIIllIIIlIlllI.s.tickPool();
    }

    public void lobbyProgressAnoouncerTick() {
        float[] lIIlllIIllIIlI;
        Joc lIIlllIIllIlII;
        double lIIlllIIllIIll = lIIlllIIllIlII.getGameProgressETA() * 100.0;
        float[] arrf = new float[lIlIlIlIl[14]];
        arrf[Joc.lIlIlIlIl[0]] = 10.0f;
        arrf[Joc.lIlIlIlIl[1]] = 25.0f;
        arrf[Joc.lIlIlIlIl[3]] = 50.0f;
        arrf[Joc.lIlIlIlIl[4]] = 75.0f;
        arrf[Joc.lIlIlIlIl[5]] = 90.0f;
        arrf[Joc.lIlIlIlIl[6]] = 100.0f;
        arrf[Joc.lIlIlIlIl[7]] = 110.0f;
        arrf[Joc.lIlIlIlIl[8]] = 120.0f;
        arrf[Joc.lIlIlIlIl[9]] = 130.0f;
        arrf[Joc.lIlIlIlIl[10]] = 150.0f;
        arrf[Joc.lIlIlIlIl[11]] = 175.0f;
        arrf[Joc.lIlIlIlIl[12]] = 200.0f;
        arrf[Joc.lIlIlIlIl[13]] = 300.0f;
        float[] lIIlllIIlIlllI = lIIlllIIllIIlI = arrf;
        int lIIlllIIlIllIl = lIIlllIIlIlllI.length;
        int lIIlllIIlIllII = lIlIlIlIl[0];
        while (Joc.lIIlllIIIIl(lIIlllIIlIllII, lIIlllIIlIllIl)) {
            float lIIlllIIllIlIl = lIIlllIIlIlllI[lIIlllIIlIllII];
            if (Joc.lIIllIlllll((int)Utils.testPointUpDown(Float.valueOf(lIIlllIIllIlIl), lIIlllIIllIlII.lastProgressETA, lIIlllIIllIIll))) {
                String lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[63]];
                if (Joc.lIIllIllllI(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 10.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[64]];
                }
                if (Joc.lIIllIllllI(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 25.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[65]];
                }
                if (Joc.lIIllIllllI(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 50.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[66]];
                }
                if (Joc.lIIllIllllI(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 75.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[67]];
                }
                if (Joc.lIIllIllllI(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 90.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[68]];
                }
                if (Joc.lIIllIllllI(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 100.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[69]];
                }
                if (Joc.lIlIIIllIll(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 110.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[70]];
                }
                if (Joc.lIlIIIllIll(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 200.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[71]];
                }
                if (Joc.lIlIIIllIll(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 210.0f))) {
                    lIIlllIIllIllI = lIIIIIIll[lIlIlIlIl[72]];
                }
                Com.sendLobbyMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIIIIIIll[lIlIlIlIl[73]]).append((Object)ChatColor.DARK_AQUA).append(lIIlllIIllIlII.getGameName()).append((Object)ChatColor.GRAY).append(lIIIIIIll[lIlIlIlIl[74]]).append(lIIlllIIllIllI)));
                if (Joc.lIlIIIllIll(Joc.lIlIIIllIlI(lIIlllIIllIlIl, 150.0f))) {
                    lIIlllIIllIlII.sendGlobalMessage(lIIIIIIll[lIlIlIlIl[75]]);
                }
            }
            ++lIIlllIIlIllII;
            "".length();
            if ("   ".length() > "  ".length()) continue;
            return;
        }
        lIIlllIIllIlII.lastProgressETA = lIIlllIIllIIll;
    }

    protected Location getRandomSpawnLoc(Player lIlIIIlIlllIlI) {
        Joc lIlIIIlIllIlll;
        ArrayList<Location> lIlIIIlIlllIIl = lIlIIIlIllIlll.pMapaActual().ObtenirLocations(lIIIIIIll[lIlIlIlIl[31]], lIlIIIlIllIlll.world);
        Collections.shuffle(lIlIIIlIlllIIl);
        Location lIlIIIlIlllIII = lIlIIIlIlllIIl.get(lIlIlIlIl[0]);
        "".length();
        lIlIIIlIlllIII.add(0.0, 2.0, 0.0);
        return lIlIIIlIlllIII;
    }

    private static int lIIlllIllII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    protected abstract ArrayList<ItemStack> getStartingItems(Player var1);

    public Long tempsTranscorregut() {
        Joc lIlIIlIIlIlIlI;
        return System.currentTimeMillis() - lIlIIlIIlIlIlI.startTimeMillis;
    }

    public Duration getAvgGameLength() {
        Joc lIIlllIlIIIIII;
        return Duration.ofSeconds((long)Com.getDataAPI().getAvgGameLength(Com.getDataAPI().getGameId(lIIlllIlIIIIII.getGameName())));
    }

    void donarItemsEspectador(Player lIlIIIIIlIllII) {
        Joc lIlIIIIIlIlIll;
        lIlIIIIIlIlIll.giveRandomCameraItem(lIlIIIIIlIllII);
    }

    public void planificarReseteig(int lIIlllIllIllll) {
        Joc lIIlllIlllIIII;
        lIIlllIlllIIII.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIIIIIIll[lIlIlIlIl[56]]).append(Double.toString(lIIlllIllIllll / lIlIlIlIl[20])).append(lIIIIIIll[lIlIlIlIl[57]])));
        "".length();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)lobby.getPlugin(), () -> {
            Joc lIIlIlIllIllII;
            lIIlIlIllIllII.allOnTheLobby();
        }, (long)lIIlllIllIllll);
    }

    void establirSpawnPrincipal() {
        Joc lIIlllIlllIlIl;
        if (Joc.lIIllIlllll((int)lIIlllIlllIlIl.pMapaActual().ExisteixPropietat(lIIIIIIll[lIlIlIlIl[54]]))) {
            Location lIIlllIlllIllI = lIIlllIlllIlIl.pMapaActual().ObtenirLocation(lIIIIIIll[lIlIlIlIl[55]], lIIlllIlllIlIl.getWorld());
            "".length();
            lIIlllIlllIlIl.world.setSpawnLocation(lIIlllIlllIllI.getBlockX(), lIIlllIlllIllI.getBlockY(), lIIlllIlllIllI.getBlockZ());
        }
    }

    static {
        Joc.lIIllIlllIl();
        Joc.lIIlIlllIll();
    }

    private static boolean lIIlllIIlIl(int n) {
        return n > 0;
    }

    public void setShowPlayerHealthBar(Boolean lIIllIlIllIlIl) {
        lIIllIlIllIllI.showPlayerHealthBar = lIIllIlIllIlIl;
    }

    protected boolean canBeRanked() {
        Joc lIlIIlIIIIIIII;
        int n;
        int n2;
        if (Joc.lIIllIlllll((int)lIlIIlIIIIIIII.onlyPlayersFromSameIP())) {
            n2 = lIlIlIlIl[19];
            "".length();
            if ((67 + 20 - 16 + 101 ^ 14 + 97 - -7 + 50) == "  ".length()) {
                return (boolean)((106 + 59 - 124 + 102 ^ 93 + 27 - -26 + 9) & (56 + 89 - 28 + 10 ^ (253 ^ 150) ^ -" ".length()));
            }
        } else {
            n2 = lIlIlIlIl[20];
        }
        if (Joc.lIIlllIIIll(lIlIIlIIIIIIII.segonsTranscorreguts(), n2) && Joc.lIIllIlllll(Joc.lIIlllIIIlI(lIlIIlIIIIIIII.getEloK(), 0.0)) && Joc.lIIllIlllll((int)Com.getPlugin().isInRankedMode()) && Joc.lIIllIllllI((int)lIlIIlIIIIIIII.unfairFlag)) {
            n = lIlIlIlIl[1];
            "".length();
            if (((54 + 142 - 175 + 174 ^ 1 + 88 - 18 + 67) & (228 ^ 165 ^ (141 ^ 133) ^ -" ".length()) & ((28 + 66 - -77 + 29 ^ 58 + 40 - -20 + 15) & (59 ^ 42 ^ (202 ^ 150) ^ -" ".length()) ^ -" ".length())) == -" ".length()) {
                return (boolean)((2 + 48 - -47 + 60 ^ 107 + 81 - 109 + 81) & (153 + 39 - 102 + 159 ^ 94 + 171 - 261 + 192 ^ -" ".length()));
            }
        } else {
            n = lIlIlIlIl[0];
        }
        return (boolean)n;
    }

    public void removeSpectator(Player lIlIIIIIIIIlIl) {
        Joc lIlIIIIIIIIlII;
        if (Joc.lIIllIlllll((int)lIlIIIIIIIIlII.getSpectators().contains((Object)lIlIIIIIIIIlIl))) {
            Utils.clearPlayer(lIlIIIIIIIIlIl);
            lIlIIIIIIIIlIl.setAllowFlight(lIlIlIlIl[0]);
            lIlIIIIIIIIlIl.setCanPickupItems(lIlIlIlIl[1]);
            lIlIIIIIIIIlIl.setFlying(lIlIlIlIl[0]);
            "".length();
            lIlIIIIIIIIlII.Espectadors.remove((Object)lIlIIIIIIIIlIl);
        }
    }

    protected void onBlockPlace(BlockPlaceEvent lIIllIlIlIlIlI, Block lIIllIlIlIlIIl) {
        Joc lIIllIlIlIlIll;
        super.onBlockPlace(lIIllIlIlIlIlI, lIIllIlIlIlIIl);
        if (Joc.lIIlllIIlll((Object)lIIllIlIlIlIlI.getPlayer())) {
            Player lIIllIlIlIllII = lIIllIlIlIlIlI.getPlayer();
            if (Joc.lIIllIllllI((int)lIIllIlIlIlIll.blockBreakPlace.booleanValue())) {
                lIIllIlIlIlIlI.setCancelled(lIlIlIlIl[1]);
            }
            if (Joc.lIIlllIlIII((Object)lIIllIlIlIllII.getGameMode(), (Object)GameMode.CREATIVE)) {
                lIIllIlIlIlIlI.setCancelled(lIlIlIlIl[0]);
            }
            if (Joc.lIIllIllllI((int)lIIllIlIlIlIlI.isCancelled())) {
                PlayerInfo lIIllIlIlIllIl = lIIllIlIlIlIll.getPlayerInfo(lIIllIlIlIllII);
                lIIllIlIlIllIl.setBlocksPlaced(lIIllIlIlIllIl.getBlocksPlaced() + lIlIlIlIl[1]);
            }
        }
    }

    protected void teleportToRandomSpawn(Player lIlIIIllIIIllI) {
        Joc lIlIIIllIIIlll;
        Location lIlIIIllIIlIII = lIlIIIllIIIlll.getOptimalSpawnLoc(lIlIIIllIIIllI);
        "".length();
        lIlIIIllIIIllI.teleport(lIlIIIllIIlIII);
    }

    protected void onProjectileHit(ProjectileHitEvent lIIllIIllIIIIl, Projectile lIIllIIllIIIII) {
        Joc lIIllIIllIIIlI;
        super.onProjectileHit(lIIllIIllIIIIl, lIIllIIllIIIII);
    }

    public ArrayList<Player> getEnemies(Player lIlIIIIIlllIll) {
        Joc lIlIIIIIllllII;
        ArrayList<Player> lIlIIIIIllllIl = lIlIIIIIllllII.getViewers();
        "".length();
        lIlIIIIIllllIl.remove((Object)lIlIIIIIlllIll);
        return lIlIIIIIllllIl;
    }

    @Override
    protected void customJoin(Player lIIlllIllIlIIl) {
        Joc lIIlllIllIlIlI;
        Utils.clearPlayer(lIIlllIllIlIIl);
        if (Joc.lIIlllIlIII((Object)((Object)lIIlllIllIlIlI.getGameState()), (Object)((Object)GameState.InGame))) {
            lIIlllIllIlIlI.addSpectator(lIIlllIllIlIIl);
            "".length();
            if (null != null) {
                return;
            }
        } else {
            lIIlllIllIlIlI.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIIlllIllIlIlI.getGameDisplayName()).append(lIIlllIllIlIIl.getName()).append(lIIIIIIll[lIlIlIlIl[58]])));
            lIIlllIllIlIlI.donarItemsPreparatiusGenerals(lIIlllIllIlIIl);
        }
        lIIlllIllIlIlI.updateScoreBoard(lIIlllIllIlIIl);
        lIIlllIllIlIlI.anunciarWiki(lIIlllIllIlIIl, lIlIlIlIl[0]);
    }

    public Duration getGameTime() {
        Joc lIIlllIlIIIIlI;
        return Duration.ofSeconds(lIIlllIlIIIIlI.segonsTranscorreguts());
    }

    @Override
    public void initialize() {
        Joc lIlIIlIlIIlIll;
        super.initialize();
        lIlIIlIlIIlIll.world.setPVP(lIlIlIlIl[0]);
        lIlIIlIlIIlIll.setDefaultGameRules();
        lIlIIlIlIIlIll.establirSpawnPrincipal();
        "".length();
        lIlIIlIlIIlIll.establirTempsInicial();
        lIlIIlIlIIlIll.registerSkills();
        lIlIIlIlIIlIll.scheduleHeartbeat();
        lIlIIlIlIIlIll.scheduleAnnouncer();
    }

    private static boolean lIIlllIlIII(Object object, Object object2) {
        return object == object2;
    }

    private static int lIlIIlIIIll(float f, float f2) {
        return (int)(f FCMPL f2);
    }

    public void sendGameInfo(Player lIlIIIlIIllIIl) {
        Joc lIlIIIlIIlIlll;
        ArrayList<String> lIlIIIlIIllIII = lIlIIIlIIlIlll.getGameInfo(lIlIIIlIIllIIl);
        if (Joc.lIIlllIIlll(lIlIIIlIIllIII)) {
            lIlIIIlIIlIlll.sendPlayerMessage(lIlIIIlIIllIIl, String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIIIIIIll[lIlIlIlIl[33]]).append((Object)ChatColor.BOLD).append(lIIIIIIll[lIlIlIlIl[34]])));
            Iterator<String> lIlIIIlIIlIlII = lIlIIIlIIllIII.iterator();
            while (Joc.lIIllIlllll((int)lIlIIIlIIlIlII.hasNext())) {
                String lIlIIIlIIllIll = lIlIIIlIIlIlII.next();
                lIlIIIlIIlIlll.sendPlayerMessage(lIlIIIlIIllIIl, String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIIIIIll[lIlIlIlIl[35]]).append((Object)ChatColor.BOLD).append(lIIIIIIll[lIlIlIlIl[36]]).append((Object)ChatColor.RESET).append(lIlIIIlIIllIll)));
                "".length();
                if ((97 ^ 121 ^ (48 ^ 44)) > "   ".length()) continue;
                return;
            }
            lIlIIIlIIlIlll.sendPlayerMessage(lIlIIIlIIllIIl, String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIIIIIIll[lIlIlIlIl[37]]).append((Object)ChatColor.BOLD).append(lIIIIIIll[lIlIlIlIl[38]])));
        }
    }

    protected void onProjectileLaunch(ProjectileLaunchEvent lIIllIIllIllll, Projectile lIIllIIllIlIll) {
        ProjectileSource lIIllIIlllIIIl;
        Joc lIIllIIllIllIl;
        super.onProjectileLaunch(lIIllIIllIllll, lIIllIIllIlIll);
        if (Joc.lIIlllIlIII((Object)lIIllIIllIlIll.getType(), (Object)EntityType.SPLASH_POTION) && Joc.lIIllIlllll((lIIllIIlllIIIl = lIIllIIllIlIll.getShooter()) instanceof LivingEntity)) {
            Vector lIIllIIlllIIlI = ((LivingEntity)lIIllIIlllIIIl).getLocation().getDirection();
            lIIllIIllIlIll.setVelocity(lIIllIIlllIIlI.multiply(lIlIlIlIl[6]));
        }
    }

    private static boolean lIlIIlIIlll(int n, int n2) {
        return n != n2;
    }

    public Boolean areAllies(Player lIIllIIlIIIIll, Player lIIllIIlIIIIII) {
        return lIIllIIlIIIIll.equals((Object)lIIllIIlIIIIII);
    }

    public void anunciarWiki(Player lIIlllIIlIlIII, boolean lIIlllIIlIIlll) {
    }

    public void giveRemainingUnlockers(Player lIlIIIIlIllllI) {
        Joc lIlIIIIlIlllIl;
        lIlIIIIlIlllIl.s.giveUnlockers(lIlIIIIlIllllI, lIlIIIIlIlllIl.getPlayerInfo(lIlIIIIlIllllI).getUnselectedSkillAmount());
    }

    protected abstract void teletransportarTothom();

    private static int lIlIIIlIlll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static void lIIlIlllIll() {
        lIIIIIIll = new String[lIlIlIlIl[113]];
        Joc.lIIIIIIll[Joc.lIlIlIlIl[0]] = Joc.llllIIIlII("bVecNiPGIJuxNLkBoxrheg==", "OjOsu");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[1]] = Joc.llllIIIlIl("CzACEAo=", "mQnco");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[3]] = Joc.llllIIIlII("JPlLfSM4Iy2e2+8D1MBMZQ==", "CFqZq");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[4]] = Joc.llllIIIlII("vPiaP/XRqrY=", "GMxBY");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[5]] = Joc.llllIIIlII("uWvtX/B1B1n24NXlu5WEGQ==", "RDFXW");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[6]] = Joc.llllIIIlll("fJtkcFmKGEg=", "ExXtI");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[7]] = Joc.llllIIIlII("4nCccAot62BD4SkVCod8Qw==", "aSPqu");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[8]] = Joc.llllIIIlIl("KjMVBiQ=", "LRyuA");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[9]] = Joc.llllIIIlll("eKcmN50BG3Fy/ERzCcQHtg==", "tiCkf");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[10]] = Joc.llllIIIlll("0wikHacgQ9M=", "qaQdm");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[11]] = Joc.llllIIIlIl("Hk0BK0UkBB0vCzkLHWoMIwMKIwQ/ShwkBG0aCDgRJA4IahQ4D0kgBG0PGj4EOwtJIwskCQArASxESQUVKBgIKQzCvkoIJBAhw50FKwEsSw==", "MjiJe");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[12]] = Joc.llllIIIlII("eu/hhWukruz7FWv+oXG15FdAXRSrGEWpXvFYkI6pvVA=", "RPqAp");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[13]] = Joc.llllIIIlII("KBYYtV5iJRD7eWTPZijpTnjFvaqhoMKyuUsdx2bIZFdZfv41F2K2+dadqcFjUUiBe+/aSN9OfgiVRwP+/0Fk2A==", "RGuJR");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[14]] = Joc.llllIIIlIl("NU4iCHQPBz4MOhIIPkkyDwcrBT0SEysbdBMHK0kkBxs+ADAHSTscMUYDK0k8Bx8jCHQHCisLNRJH", "fiJiT");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[15]] = Joc.llllIIIlll("otnnYXqe4aSZBVIVisnwCufbc9i7MvPNEoHOGmuqeisVo3Qh5G/MJf4OMonlSlKj", "XfSUE");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[16]] = Joc.llllIIIlll("RTIcdZ17L4aAgrU7kGBUKA==", "RkvSD");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[17]] = Joc.llllIIIlll("TGOGzlgyA3j4Iyn+OqBsd1LIGK4rfFRpwG7V4vPMsAGXF8X3Bx3Oi2kki8VRiH5e", "ZAJNk");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[18]] = Joc.llllIIIlll("aRmUEasm4svVL81PxSsTsw==", "qlDJh");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[21]] = Joc.llllIIIlll("zQby9rSUFID1TVgM0VNygQxetjTQFXBMr1IrHTT6nlhiyUmGOBQodA==", "dqibC");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[22]] = Joc.llllIIIlll("cNlbguUxiVLxzfCVnIelVXpPSeKAha/z8beVQLcFFzUP1Wc38fxwIQ==", "SSqZY");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[20]] = Joc.llllIIIlIl("YA==", "KPUeq");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[23]] = Joc.llllIIIlll("T4d/MM9zRVI=", "QBnnW");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[24]] = Joc.llllIIIlll("h27rQStt2Uc=", "HyMwD");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[25]] = Joc.llllIIIlll("AZ/IPcuzDzA=", "spQut");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[26]] = Joc.llllIIIlll("LTyqY+Y3tDc=", "pxNaI");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[27]] = Joc.llllIIIlIl("PA==", "wdvOY");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[28]] = Joc.llllIIIlII("Yyp1ci9ZShA=", "lwnlp");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[29]] = Joc.llllIIIlIl("HQsdDxA5BRsz", "PbsGu");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[30]] = Joc.llllIIIlll("zQumfWLz6g3QLd/4WWVlLA==", "muaSP");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[31]] = Joc.llllIIIlII("KUwSW8Nn440=", "lUGWH");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[32]] = Joc.llllIIIlll("5fRN/oXOYs4=", "tFPDP");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[33]] = Joc.llllIIIlIl("", "gkjgZ");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[34]] = Joc.llllIIIlll("0AxUeyw5fp0mRlzKjkIje8J0AZX96P7Q", "Ozsdm");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[35]] = Joc.llllIIIlII("Hb1yOMMa2Qk=", "WwDuv");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[36]] = Joc.llllIIIlll("VWMin2yQtac=", "kZDzi");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[37]] = Joc.llllIIIlII("Bdv4eoDxxtg=", "xpIYY");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[38]] = Joc.llllIIIlII("S+8+cWCVzj5L7z5xYJXOPvHXpS/0EPAU", "nPiRv");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[39]] = Joc.llllIIIlII("Y3XZdw34q5CmyXMghiCHQxxwGNwFVJylszM59PhbU45NpNSrXtYYsA==", "DGUAY");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[40]] = Joc.llllIIIlIl("FGo/", "OGblI");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[41]] = Joc.llllIIIlIl("LcKhHB06D2EQFC0PNcKDCiEP", "nAqxH");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[42]] = Joc.llllIIIlII("bNMeM2jwdHXqKWIo9Jd87kxbV/SByE9B2lz7aVFhchs=", "GanWA");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[43]] = Joc.llllIIIlll("mGgrkWF3xvs=", "rdGTb");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[44]] = Joc.llllIIIlll("S/5PzD6fXuk=", "HDDel");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[45]] = Joc.llllIIIlII("JFxPtWqskmM=", "BBQrH");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[48]] = Joc.llllIIIlll("Y5/alhTRKjrhhSUk8ROs8rP8/mHINipd", "sNurF");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[49]] = Joc.llllIIIlII("iJzJl1XBwUHH/cxzVSGslVjkhc/As5x9C27ZijarA8P1p4iNp8434A==", "eVnyO");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[50]] = Joc.llllIIIlll("tYC5Xup1ZjN66CEbdt+IJg==", "kYmaL");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[51]] = Joc.llllIIIlII("UUeqjSb5c5CTsi92TegmWAzSnIryC7mD", "NxLbm");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[53]] = Joc.llllIIIlII("ILSDZKb7WvLI+3mjAmCpww==", "sHenh");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[54]] = Joc.llllIIIlIl("OQMrHy0=", "JsJhC");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[55]] = Joc.llllIIIlII("z/NwImEWLzQ=", "wCneu");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[56]] = Joc.llllIIIlll("Phjdq+2RfR6oqZsGPb+247/zOMkPaz+K", "sIvPF");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[57]] = Joc.llllIIIlIl("Og==", "IUcwd");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[58]] = Joc.llllIIIlII("h6FbnsZAWZatyR/kQUH/siCpX9uxA28X", "xlQNK");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[60]] = Joc.llllIIIlII("LDFAhQdg+i+hLbEHXbT/82ScQxF0fo/t", "iDXHT");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[61]] = Joc.llllIIIlll("Mg8+lgoAxWvTVf6EUpwYyA==", "OoDCD");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[62]] = Joc.llllIIIlII("omtBVgWQDIzz3nLZPF6yhw==", "ztMXi");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[63]] = Joc.llllIIIlIl("", "UlDye");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[64]] = Joc.llllIIIlII("i9GxPM8S1HTWYr835BIgciS3clijRmWx", "xUhLT");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[65]] = Joc.llllIIIlII("+tEHJsnQVssSJMcBOPTvGJNK8ZqRoPsD2hBaeCeTEaM=", "nqMhE");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[66]] = Joc.llllIIIlll("XM+m3f+trngnpMtR5M/2M7XWCkfCqkU2DGtNY2BBiS0=", "Larta");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[67]] = Joc.llllIIIlll("QkBPb+F81iNJUxSlaC2nUQ==", "pNqHk");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[68]] = Joc.llllIIIlIl("wqA0cgBTJWA3FRI5JnIFFiouIQgFKGlyIBAoJTMTwpNpJiQIEj1p", "IGRas");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[69]] = Joc.llllIIIlII("+qSiXr7j+5FXrs63mjRI2GS4D4SFzQx5", "nLpGb");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[70]] = Joc.llllIIIlll("5khjaWQVk9JWfRvV+PbqQ6BmtgUwmJD0zeZB0cAP3VA=", "sPHAz");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[71]] = Joc.llllIIIlII("iAPP8H9rV/8pInmRF5iXJvM7l2UIGcdW4iok2mD2vXpxfWUIKrUrkA==", "cIeuO");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[72]] = Joc.llllIIIlIl("BzkHAiTCg2wBDCICbBlEMxcpBwo4CjgUFw==", "cLucV");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[73]] = Joc.llllIIIlll("JCowsU3K8E8pzeaY9WJ98g==", "tEywH");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[74]] = Joc.llllIIIlIl("Yw==", "CaUXn");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[75]] = Joc.llllIIIlII("hUEqzEvJfqDGiQV9hWFWvWf9dbq9Zvx19muMx0bhdQTepdz4flEgZ4yQenD9busLDRyHwb47W4swHUGaI6a4t9eqXvMNKgVnC5DuslIvpBKYWj6W2xpwd7oKW/yxAese", "OKjER");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[76]] = Joc.llllIIIlII("QCLDgJTNRPc=", "ilPod");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[77]] = Joc.llllIIIlIl("HBAoEzYSBiMIOxIRLVQ2HE8lCnYcECs=", "sbLzX");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[78]] = Joc.llllIIIlII("dR0rLLWVdWcTnTY9WxeXyA==", "NeoPF");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[79]] = Joc.llllIIIlIl("Vhg=", "yomSo");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[80]] = Joc.llllIIIlll("4/49iS0hIeRetCnJnbc8n/1MlAB3CA/A", "VhjBM");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[81]] = Joc.llllIIIlll("1co5yE2eIRzy0lxXVj7fn/w+BYC077eV", "cCJQv");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[82]] = Joc.llllIIIlII("Qm1Od4j7VOdOjpFxrooR48MZ/gcMGcQmub6ImpicJdI=", "Lnqjt");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[83]] = Joc.llllIIIlll("17p2tHowNrozrTuw3lIyp80t1FSzXCAl", "anPou");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[84]] = Joc.llllIIIlII("hQ6JAsn92HM=", "hHWoG");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[85]] = Joc.llllIIIlll("mNodVV6JXUbrDla4KnSKCg==", "CjWhk");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[86]] = Joc.llllIIIlII("46BOWt72zeMVivBMPGVlx57gE/89Xbmx", "fGVjn");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[87]] = Joc.llllIIIlll("jqQKgdJvJr8jUOjkyBFHv1SAzvHG/y0TKZkpwU2Si8MzMo5yQGxEon/dfOUPZvWI", "oXIHu");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[88]] = Joc.llllIIIlll("q2/Ix2F5fqw=", "DCTwM");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[89]] = Joc.llllIIIlII("HOiI18ztbYk=", "lEpvU");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[90]] = Joc.llllIIIlII("AJBIiONXTCNugOXv1+olrfwjgYj7aTml", "VsXsW");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[91]] = Joc.llllIIIlII("Uon/WzX99O4=", "zBzGR");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[92]] = Joc.llllIIIlll("k7F0j2y9M4U=", "BAOrf");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[93]] = Joc.llllIIIlll("qMXoNkpDWPg=", "wEjUh");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[94]] = Joc.llllIIIlll("JB0Uacn+vVY=", "EopGS");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[95]] = Joc.llllIIIlIl("Lz8RdWc=", "ePrOG");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[96]] = Joc.llllIIIlII("y/gsXwKF09bg/5EF6JXzKw==", "ftsPl");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[97]] = Joc.llllIIIlII("ABXTRhRjCmuAbkANLC0tqA==", "bzfBJ");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[98]] = Joc.llllIIIlIl("fQ==", "XkGas");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[100]] = Joc.llllIIIlll("ZFJ7ep+Xeb1U9qXNzjk8Jw==", "uCmNF");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[101]] = Joc.llllIIIlII("y88lAb9oSUc3sT3pHxZINA==", "GBkhe");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[102]] = Joc.llllIIIlII("oRcEpOhtcgZsHwL5h4SWSA==", "TBZQO");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[103]] = Joc.llllIIIlIl("BAsnKg4sFWI5BDEeYigHNk0oOAwkCS0/GGUJJyFLKQIgLxJlDGIhCmUdIz8fLAkjbQomGTcsBw==", "EmBMk");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[104]] = Joc.llllIIIlII("T89dHgsMvgpSZ9Fl46qlV2+3QMOfLakLXRC0ke7L+DW1iAsgAVYcfZ8xLHGAdut0IIM6il0oR9s=", "gNfhD");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[105]] = Joc.llllIIIlll("YNzdIVr88F52PxWA+8P8NOdHdU9h/BO21tkLT/2n4Y2W13CNojWMaJkvDhs1kowoJdkuULxE74I=", "KfnAX");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[106]] = Joc.llllIIIlII("4LdrwpBBqZc=", "UyIEa");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[46]] = Joc.llllIIIlll("8KVqcCCLiJ51jad/69H5Fw==", "gfKaf");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[107]] = Joc.llllIIIlIl("FTYYMxBrcwc4FzMq", "QSkWu");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[108]] = Joc.llllIIIlIl("AxUQJQwwUwEtETE=", "BsuBe");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[109]] = Joc.llllIIIlII("mJZSUly3+mTL1MyNTb/hP5TduD3A2VgkEHHTKtpytR1yeYKPgoVySBRe08E26TZZJAJKftmmHa8=", "NHIKb");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[110]] = Joc.llllIIIlll("rLus0vJkoCUEYsTDU3PX9X5RlNeorQL43C4TOBboruQRmUva4F7O7osurvSVV3hqvv3rDQEVTmIc+/pPBtinsw==", "FJniy");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[111]] = Joc.llllIIIlIl("Ay5aLjghIx4rP2YqG2QgJzACJTlmJh8obSotGCY0ZiMYJSM1Yh4hbTUnCGQ5Iy4fMD8nLAk0IjQ2GzBtbg==", "FBzDM");
        Joc.lIIIIIIll[Joc.lIlIlIlIl[112]] = Joc.llllIIIlll("NSkgrKJ3I8M=", "aZvks");
    }

    protected void onPlayerMoveDistributed(PlayerMoveEvent lIIlllllIlllII, Player lIIlllllIllllI) {
        Joc lIIllllllIIIII;
        super.onPlayerMoveDistributed(lIIlllllIlllII, lIIlllllIllllI);
        if (Joc.lIIllIlllll((int)Utils.Possibilitat(lIlIlIlIl[46])) && Joc.lIIllIlllll((int)lIIllllllIIIII.getShowPlayerHealthBar().booleanValue()) && Joc.lIIllIlllll((int)lIIllllllIIIII.getDisplayHealthBar())) {
            lIIllllllIIIII.updateHealthSuffix(lIIlllllIllllI);
        }
    }

    private Long establirTempsInicial() {
        Joc lIlIIlIIlIllIl;
        lIlIIlIIlIllIl.startTimeMillis = System.currentTimeMillis();
        return lIlIIlIIlIllIl.startTimeMillis;
    }

    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIIllllIIIIlll, Player lIIllllIIIIllI, Player lIIllllIIIIIIl) {
        Joc lIIllllIIIlIII;
        super.onPlayerDeathByPlayer(lIIllllIIIIlll, lIIllllIIIIllI, lIIllllIIIIIIl);
        if (Joc.lIIlllIlIII((Object)lIIllllIIIIllI, (Object)lIIllllIIIIIIl)) {
            lIIllllIIIIlll.setDeathMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIllllIIIIllI.getName()).append((Object)ChatColor.RED).append(lIIIIIIll[lIlIlIlIl[53]])));
        }
        if (Joc.lIIllIlllll((int)lIIllllIIIlIII.giveSnowLauncherOnKill())) {
            ItemStack[] arritemStack = new ItemStack[lIlIlIlIl[1]];
            arritemStack[Joc.lIlIlIlIl[0]] = lIIllllIIIlIII.getSnowLauncher(lIlIlIlIl[1]);
            "".length();
            lIIllllIIIIIIl.getInventory().addItem(arritemStack);
        }
    }

    protected void updateHealthSuffix(Player lIIllllllIIlll) {
        String lIIllllllIIllI = String.valueOf(new StringBuilder().append(lIIIIIIll[lIlIlIlIl[45]]).append(Joc.getHealthProgressBar(lIIllllllIIlll)));
        Com.setSuffix(lIIllllllIIlll, lIIllllllIIllI);
    }

    public void iniciarCommand(Player lIIllIlllIllII) {
        Joc lIIllIlllIllIl;
        if (Joc.lIIllIlllll((int)lIIllIlllIllIl.canBeStartedBy(lIIllIlllIllII, lIlIlIlIl[1]))) {
            lIIllIlllIllIl.JocIniciat();
        }
    }

    public void JocIniciat() {
        Joc lIlIIlIlIIIIlI;
        if (Joc.lIIllIlllll((int)lIlIIlIlIIIIlI.JocIniciat.booleanValue())) {
            "".length();
            Bukkit.broadcastMessage((String)lIIIIIIll[lIlIlIlIl[11]]);
            return;
        }
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIIlIlIIIIlI.getGameDisplayName()).append(lIIIIIIll[lIlIlIlIl[12]])));
        lIlIIlIlIIIIlI.JocIniciat = lIlIlIlIl[1];
        lIlIIlIlIIIIlI.customJocIniciat();
        lIlIIlIlIIIIlI.world.setPVP(lIlIlIlIl[1]);
        lIlIIlIlIIIIlI.donarItemsInicials();
        lIlIIlIlIIIIlI.teletransportarTothom();
        "".length();
        lIlIIlIlIIIIlI.establirTempsInicial();
        lIlIIlIlIIIIlI.resetHeartbeat();
        lIlIIlIlIIIIlI.matchData = MatchData.registerStart(lIlIIlIlIIIIlI);
        Iterator<Player> lIlIIlIlIIIIIl = lIlIIlIlIIIIlI.getPlayers().iterator();
        while (Joc.lIIllIlllll((int)lIlIIlIlIIIIIl.hasNext())) {
            Player lIlIIlIlIIIlII = lIlIIlIlIIIIIl.next();
            "".length();
            lIlIIlIlIIIIlI.getPlayerInfo(lIlIIlIlIIIlII);
            "".length();
            if (-"  ".length() <= 0) continue;
            return;
        }
        lIlIIlIlIIIIlI.updateScoreBoards();
        lIlIIlIlIIIIlI.sendGameInfo();
    }

    protected void updateScoreBoard(Player lIIlllIIIIllll) {
    }

    protected abstract void customJocIniciat();

    public void setDefaultGameRules() {
        Joc lIlIIlIlIIlIIl;
        "".length();
        lIlIIlIlIIlIIl.world.setGameRuleValue(lIIIIIIll[lIlIlIlIl[0]], lIIIIIIll[lIlIlIlIl[1]]);
        "".length();
        lIlIIlIlIIlIIl.world.setGameRuleValue(lIIIIIIll[lIlIlIlIl[3]], lIIIIIIll[lIlIlIlIl[4]]);
        "".length();
        lIlIIlIlIIlIIl.world.setGameRuleValue(lIIIIIIll[lIlIlIlIl[5]], lIIIIIIll[lIlIlIlIl[6]]);
        "".length();
        lIlIIlIlIIlIIl.world.setGameRuleValue(lIIIIIIll[lIlIlIlIl[7]], lIIIIIIll[lIlIlIlIl[8]]);
        if (Joc.lIIllIllllI((int)lIlIIlIlIIlIIl.getResetPlayerOnRespawn())) {
            "".length();
            lIlIIlIlIIlIIl.world.setGameRuleValue(lIIIIIIll[lIlIlIlIl[9]], lIIIIIIll[lIlIlIlIl[10]]);
        }
        lIlIIlIlIIlIIl.setCustomGameRules();
    }

    protected void onBlockBreak(BlockBreakEvent lIIllIlIIllIII, Block lIIllIlIIllIlI) {
        Joc lIIllIlIIlllII;
        super.onBlockBreak(lIIllIlIIllIII, lIIllIlIIllIlI);
        if (Joc.lIIlllIIlll((Object)lIIllIlIIllIII.getPlayer())) {
            Player lIIllIlIIlllIl = lIIllIlIIllIII.getPlayer();
            if (Joc.lIIllIllllI((int)lIIllIlIIlllII.blockBreakPlace.booleanValue())) {
                lIIllIlIIllIII.setCancelled(lIlIlIlIl[1]);
            }
            if (Joc.lIIlllIlIII((Object)lIIllIlIIlllIl.getGameMode(), (Object)GameMode.CREATIVE)) {
                lIIllIlIIllIII.setCancelled(lIlIlIlIl[0]);
            }
            if (Joc.lIIllIllllI((int)lIIllIlIIllIII.isCancelled())) {
                PlayerInfo lIIllIlIIllllI = lIIllIlIIlllII.getPlayerInfo(lIIllIlIIlllIl);
                lIIllIlIIllllI.setBlocksBroken(lIIllIlIIllllI.getBlocksBroken() + lIlIlIlIl[1]);
            }
        }
    }

    public boolean getResetPlayerOnRespawn() {
        return lIlIlIlIl[1];
    }

    public Long getHeartbeatCount() {
        Joc lIIllIIIIllllI;
        return lIIllIIIIllllI.heartbeatCount;
    }

    protected void onPlayerDamage(EntityDamageEvent lIIlllllIIllll, Player lIIlllllIIlllI) {
        Joc lIIlllllIlIIII;
        super.onPlayerDamage(lIIlllllIIllll, lIIlllllIIlllI);
        PlayerInfo lIIlllllIlIIlI = lIIlllllIlIIII.getPlayerInfo(lIIlllllIIlllI);
        if (Joc.lIIllIlllll((int)lIIlllllIlIIlI.isImmune())) {
            lIIlllllIIllll.setCancelled(lIlIlIlIl[1]);
            lIIlllllIlIIII.getWorld().playEffect(lIIlllllIIlllI.getEyeLocation(), Effect.FIREWORKS_SPARK, (int)DyeColor.BLUE.getDyeData());
        }
        double lIIlllllIlIIIl = 1.0;
        lIIlllllIlIIIl = lIlIlIlIl[47] / (CBUtils.getPing(lIIlllllIIlllI) + lIlIlIlIl[1]);
        if (Joc.lIIlllIIlIl(Joc.lIIlllIlIll(lIIlllllIlIIIl, 1.0))) {
            lIIlllllIlIIIl = 1.0;
        }
        if (Joc.lIIlllIlIlI(Joc.lIIlllIllII(lIIlllllIlIIIl, 0.5))) {
            lIIlllllIlIIIl = 0.5;
        }
        lIIlllllIIllll.setDamage(lIIlllllIIllll.getDamage() * Math.sqrt(lIIlllllIlIIIl));
    }

    protected void updateEloOrdered(ArrayList<Player> lIlIIIlllIlIII) {
        Joc lIlIIIlllIlIIl;
        if (Joc.lIIllIllllI((int)lIlIIIlllIlIIl.canBeRanked())) {
            lIlIIIlllIlIIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIIIIIIll[lIlIlIlIl[22]])));
            return;
        }
        ArrayList lIlIIIlllIIlll = new ArrayList();
        lIlIIIlllIlIII.forEach(lIIlIlIlIIIlII -> {
            "".length();
            lIlIIIlllIIlll.add(new PlayerData(lIIlIlIlIIIlII.getName()).getElo());
        });
        ArrayList lIlIIIlllIIllI = EloUtils.calculateEloGroupChange(lIlIIIlllIIlll, (double)lIlIIIlllIlIIl.getEloK(), (boolean)lIlIlIlIl[0]);
        lIlIIIlllIIllI.forEach(lIIlIlIlIIlllI -> {
            Joc lIIlIlIlIIllIl;
            lIIlIlIlIIllIl.registerEloChange((Player)lIlIIIlllIlIII.get(lIlIIIlllIIllI.indexOf(lIIlIlIlIIlllI)), lIIlIlIlIIlllI);
        });
    }

    public Joc() {
        Joc lIlIIlIlIlIIIl;
        lIlIIlIlIlIIIl.JocIniciat = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.JocFinalitzat = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.Espectadors = new ArrayList<E>();
        lIlIIlIlIlIIIl.InfoStorage = new ArrayList<E>();
        lIlIIlIlIlIIIl.s = new SkillPool();
        lIlIIlIlIlIIIl.won = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.unfairFlag = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.blockBreakPlace = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.giveStartingItemsRespawn = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.showPlayerHealthBar = lIlIlIlIl[1];
        lIlIIlIlIlIIIl.isSnowLauncherEnabled = lIlIlIlIl[0];
        lIlIIlIlIlIIIl.startTimeMillis = 0L;
        lIlIIlIlIlIIIl.heartbeatCount = 0L;
        lIlIIlIlIlIIIl.ultraHeartbeatCount = 0L;
        lIlIIlIlIlIIIl.heartbeatId = lIlIlIlIl[2];
        lIlIIlIlIlIIIl.announceCount = 0L;
        lIlIIlIlIlIIIl.handledBukkitSchedulerTasks = new ArrayList<E>();
        lIlIIlIlIlIIIl.lastProgressETA = 0.0;
    }

    public void clearExternals() {
        Joc lIlIIlIIlllIlI;
        lIlIIlIIlllIlI.cancelAllTasks();
        lIlIIlIIlllIlI.s.clear();
    }

    private static boolean lIIlllIIIll(int n, int n2) {
        return n > n2;
    }

    public boolean getAllowSpectators() {
        return lIlIlIlIl[1];
    }

    protected abstract void setCustomGameRules();

    private static boolean lIIllIlllll(int n) {
        return n != 0;
    }

    public Boolean getGiveStartingItemsRespawn() {
        Joc lIIllIllIIIIlI;
        return lIIllIllIIIIlI.giveStartingItemsRespawn;
    }

    public void heartbeat() {
        Joc lIIllIIIlIIlII;
        Object lIIllIIIlIIIlI = lIIllIIIlIIlII.heartbeatCount;
        Long lIIllIIIlIIIIl = lIIllIIIlIIlII.heartbeatCount = Long.valueOf(lIIllIIIlIIlII.heartbeatCount + 1L);
        "".length();
        lIIllIIIlIIIlI = lIIllIIIlIIlII.InfoStorage.iterator();
        while (Joc.lIIllIlllll((int)lIIllIIIlIIIlI.hasNext())) {
            PlayerInfo lIIllIIIlIIlIl = (PlayerInfo)lIIllIIIlIIIlI.next();
            lIIllIIIlIIlIl.tick();
            "".length();
            if (-" ".length() <= (52 ^ 48)) continue;
            return;
        }
        lIIllIIIlIIlII.lobbyProgressAnoouncerTick();
        lIIllIIIlIIlII.registerTimestamps(lIlIlIlIl[0]);
    }

    protected abstract void customJocFinalitzat();

    private void cancelAllTasks() {
        Joc lIlIIlIIllIlIl;
        lIlIIlIIllIlIl.handledBukkitSchedulerTasks.forEach(lIIlIlIIIlIlII -> Bukkit.getScheduler().cancelTask(lIIlIlIIIlIlII.intValue()));
    }

    public void giveFixedPlaceItems(Player lIlIIIIlIllIlI) {
    }

    protected void onPlayerMove(PlayerMoveEvent lIIllIIllllIll, Player lIIllIIlllllll) {
        Joc lIIllIIlllllII;
        super.onPlayerMove(lIIllIIllllIll, lIIllIIlllllll);
        PlayerInfo lIIllIIllllllI = lIIllIIlllllII.getPlayerInfo(lIIllIIlllllll);
        lIIllIIllllllI.lastMoveEvent = ZonedDateTime.now();
        Vector lIIllIIlllllIl = Utils.CrearVector(lIIllIIllllIll.getFrom(), lIIllIIllllIll.getTo());
        if (!Joc.lIIllIllllI(Joc.lIlIIlIIIlI(lIIllIIlllllIl.getX(), 0.0)) || !Joc.lIIllIllllI(Joc.lIlIIlIIIlI(lIIllIIlllllIl.getZ(), 0.0)) || Joc.lIIllIlllll(Joc.lIlIIlIIIll(lIIllIIllllIll.getFrom().getYaw(), lIIllIIllllIll.getTo().getYaw()))) {
            lIIllIIllllllI.setImmune(lIlIlIlIl[0]);
        }
    }

    public class PlayerInfo {
        /* synthetic */ String name;
        /* synthetic */ int spree;
        private static final /* synthetic */ int[] lIlIIlllI;
        /* synthetic */ int blocksPlaced;
        /* synthetic */ int kills;
        /* synthetic */ AuraRendererStatusEffect auraRenderer;
        /* synthetic */ ArrayList<StatusEffect> effects;
        /* synthetic */ ZonedDateTime lastMoveEvent;
        /* synthetic */ int additionalSkills;
        /* synthetic */ int deaths;
        /* synthetic */ double speedModifier;
        /* synthetic */ int value;
        private /* synthetic */ ArrayList<AuraInfo> auras;
        private static final /* synthetic */ String[] lIlIIlIll;
        /* synthetic */ double damageDealt;
        /* synthetic */ int objectivesCompleted;
        /* synthetic */ int blocksBroken;
        /* synthetic */ ZonedDateTime lastRespawnEvent;
        /* synthetic */ Player lastDamager;
        /* synthetic */ boolean immune;
        /* synthetic */ boolean isAlive;

        public boolean hasStatusEffect(Class<? extends StatusEffect> lIllIllIIlIlII) {
            PlayerInfo lIllIllIIlIlIl;
            Iterator<StatusEffect> lIllIllIIlIIll = lIllIllIIlIlIl.effects.iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIllIIlIIll.hasNext())) {
                String lIllIllIIllIIl;
                StatusEffect lIllIllIIllIII = lIllIllIIlIIll.next();
                String lIllIllIIllIlI = ((Object)((Object)lIllIllIIllIII)).getClass().getName();
                if (PlayerInfo.lIIlIllllll((int)lIllIllIIllIlI.equals(lIllIllIIllIIl = lIllIllIIlIlII.getName()))) {
                    return lIlIIlllI[1];
                }
                "".length();
                if ("   ".length() > ((30 ^ 19) & ~(130 ^ 143))) continue;
                return (boolean)((35 ^ 127) & ~(82 ^ 14));
            }
            return lIlIIlllI[0];
        }

        public void setAlive(boolean lIlllIIlIIlIll) {
            lIlllIIlIIllII.isAlive = lIlllIIlIIlIll;
        }

        public void removeAura(String lIllIllllIlllI) {
            PlayerInfo lIllIllllIllll;
            "".length();
            lIllIllllIllll.auras.removeIf(lIllIlIIlllIIl -> lIllIlIIlllIIl.getName().equalsIgnoreCase(lIllIllllIlllI));
        }

        public int getKills() {
            PlayerInfo lIlllIIllIIIIl;
            return lIlllIIllIIIIl.kills;
        }

        private static boolean lIIlIllllll(int n) {
            return n != 0;
        }

        public String getName() {
            PlayerInfo lIlllIlIIIIIII;
            return lIlllIlIIIIIII.name;
        }

        public void setName(String lIlllIIllllIll) {
            lIlllIIlllllII.name = lIlllIIllllIll;
        }

        public int getBlocksPlaced() {
            PlayerInfo lIlllIIIlIllII;
            return lIlllIIIlIllII.blocksPlaced;
        }

        public void removeExpiredEffects() {
            PlayerInfo lIllIllIllIlIl;
            ArrayList<StatusEffect> lIllIllIllIllI = new ArrayList<StatusEffect>();
            Iterator<StatusEffect> lIllIllIllIIll = lIllIllIllIlIl.getStatusEffects().iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIllIllIIll.hasNext())) {
                StatusEffect lIllIllIlllIII = lIllIllIllIIll.next();
                if (PlayerInfo.lIIlIllllll((int)lIllIllIlllIII.hasExpired())) {
                    lIllIllIlllIII.clearExternals();
                    "".length();
                    lIllIllIllIllI.add(lIllIllIlllIII);
                }
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
            lIllIllIllIlIl.effects.removeAll(lIllIllIllIllI);
        }

        public void updateSpeedSlowPotionEffects() {
            PlayerInfo lIllIlllIlIllI;
            int lIllIlllIllIIl = (int)Math.round(lIllIlllIlIllI.getSpeedModifier());
            if (PlayerInfo.lIIlIlllllI(lIllIlllIllIIl)) {
                return;
            }
            int lIllIlllIllIII = lIlIIlllI[0];
            int lIllIlllIlIlll = lIlIIlllI[0];
            if (PlayerInfo.lIIllIIIIIl(lIllIlllIllIIl)) {
                lIllIlllIllIII = (int)((double)lIllIlllIllIII + Math.floor((double)lIllIlllIllIIl / 20.0));
                double lIllIlllIlllII = (double)lIllIlllIllIIl / 20.0 - (double)lIllIlllIllIII;
                lIllIlllIllIII = (int)((double)lIllIlllIllIII + lIllIlllIlllII * 4.0);
                lIllIlllIlIlll = (int)((double)lIllIlllIlIlll + lIllIlllIlllII * 4.0);
                "".length();
                if ("  ".length() <= 0) {
                    return;
                }
            } else {
                lIllIlllIlIlll = (int)((double)lIllIlllIlIlll + Math.floor((double)(lIllIlllIllIIl *= lIlIIlllI[3]) / 15.0));
                double lIllIlllIllIll = (double)lIllIlllIllIIl / 15.0 - (double)lIllIlllIlIlll;
                lIllIlllIlIlll = (int)((long)lIllIlllIlIlll + Math.round(lIllIlllIllIll * 3.0 * 3.0));
                lIllIlllIllIII = (int)((long)lIllIlllIllIII + Math.round(lIllIlllIllIll * 3.0 * 2.0));
            }
            if (PlayerInfo.lIIllIIIIIl(lIllIlllIllIII)) {
                "".length();
                lIllIlllIlIllI.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIlIIlllI[1], lIllIlllIllIII - lIlIIlllI[1], lIlIIlllI[1], lIlIIlllI[1]));
                "".length();
                if (((107 + 90 - 163 + 192 ^ 59 + 98 - 40 + 58) & (177 + 193 - 309 + 150 ^ 17 + 141 - 13 + 13 ^ -" ".length())) != 0) {
                    return;
                }
            } else {
                lIllIlllIlIllI.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            }
            if (PlayerInfo.lIIllIIIIIl(lIllIlllIlIlll)) {
                "".length();
                lIllIlllIlIllI.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIlIIlllI[1], lIllIlllIlIlll - lIlIIlllI[1], lIlIIlllI[1], lIlIIlllI[1]));
                "".length();
                if ((84 ^ 80) == 0) {
                    return;
                }
            } else {
                lIllIlllIlIllI.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            }
        }

        private static boolean lIIllIIIIll(int n) {
            return n < 0;
        }

        private static void lIIlIlllIII() {
            lIlIIlIll = new String[lIlIIlllI[8]];
            PlayerInfo.lIlIIlIll[PlayerInfo.lIlIIlllI[0]] = PlayerInfo.lIIlIllIlII("nL95cL46x4k=", "dLlSS");
            PlayerInfo.lIlIIlIll[PlayerInfo.lIlIIlllI[1]] = PlayerInfo.lIIlIllIlII("AnfCVlsIIxo=", "nmtOR");
            PlayerInfo.lIlIIlIll[PlayerInfo.lIlIIlllI[4]] = PlayerInfo.lIIlIllIlII("tM7fgkypcvU=", "FsQPG");
            PlayerInfo.lIlIIlIll[PlayerInfo.lIlIIlllI[5]] = PlayerInfo.lIIlIllIlII("1wsklujDsoo=", "pecWL");
            PlayerInfo.lIlIIlIll[PlayerInfo.lIlIIlllI[6]] = PlayerInfo.lIIlIllIlII("ynjMDa2zur3m0A63r5vX1asYnmR7sGxE802+8IV+eWJ33TP+hZS+ww==", "PqZVq");
        }

        private static boolean lIIlIllllIl(Object object) {
            return object == null;
        }

        public void setObjectivesCompleted(int lIlllIIIIllllI) {
            lIlllIIIIlllll.objectivesCompleted = lIlllIIIIllllI;
        }

        public int getTotalSkills() {
            PlayerInfo lIllIlIlIIllll;
            return lIllIlIlIIllll.Joc.this.getBaseSkillUnlockerAmount() + lIllIlIlIIllll.getAdditionalSkills();
        }

        public boolean isImmune() {
            boolean bl;
            PlayerInfo lIlllIIIIllIlI;
            if (!PlayerInfo.lIIlIlllllI((int)lIlllIIIIllIlI.immune) || PlayerInfo.lIIlIllllll((int)lIlllIIIIllIlI.isAFK())) {
                bl = lIlIIlllI[1];
                "".length();
                if (" ".length() == (96 ^ 100)) {
                    return (boolean)((64 ^ 75) & ~(183 ^ 188));
                }
            } else {
                bl = lIlIIlllI[0];
            }
            return bl;
        }

        public void clearAuras() {
            PlayerInfo lIllIllllllIII;
            lIllIllllllIII.auras.clear();
        }

        public int getBlocksBroken() {
            PlayerInfo lIlllIIIllIlIl;
            return lIlllIIIllIlIl.blocksBroken;
        }

        public ArrayList<AuraInfo> getAuras() {
            PlayerInfo lIlllIIIIIlIII;
            ArrayList<AuraInfo> lIlllIIIIIlIIl = new ArrayList<AuraInfo>();
            "".length();
            lIlllIIIIIlIIl.addAll(lIlllIIIIIlIII.auras);
            "".length();
            lIlllIIIIIlIIl.addAll(lIlllIIIIIlIII.getRealtimeAuras());
            return lIlllIIIIIlIIl;
        }

        public Block getBlockWherePlayerStands() {
            PlayerInfo lIllIlIlIIIIIl;
            BlockIterator lIllIlIlIIIIlI = new BlockIterator(lIllIlIlIIIIIl.Joc.this.getWorld(), lIllIlIlIIIIIl.getPlayer().getLocation().toVector(), new Vector(lIlIIlllI[0], lIlIIlllI[3], lIlIIlllI[0]), 1.0, lIlIIlllI[7]);
            while (PlayerInfo.lIIlIllllll((int)lIllIlIlIIIIlI.hasNext())) {
                Block lIllIlIlIIIlII = lIllIlIlIIIIlI.next();
                if (PlayerInfo.lIIlIlllllI((int)lIllIlIlIIIlII.isEmpty())) {
                    return lIllIlIlIIIlII;
                }
                "".length();
                if (-" ".length() <= "  ".length()) continue;
                return null;
            }
            return null;
        }

        public void removeStatusEffect(StatusEffect lIllIlllIIIlIl) {
            PlayerInfo lIllIlllIIIlII;
            "".length();
            lIllIlllIIIlII.effects.remove((Object)lIllIlllIIIlIl);
            lIllIlllIIIlII.Joc.this.sendGlobalMessage(lIllIlllIIIlIl.getName());
        }

        public <T extends StatusEffect> T getStatusEffect(Class<T> lIllIlIlllIlll) {
            PlayerInfo lIllIlIlllIllI;
            Iterator<StatusEffect> lIllIlIlllIlII = lIllIlIlllIllI.effects.iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIlIlllIlII.hasNext())) {
                String lIllIlIllllIlI;
                StatusEffect lIllIlIllllIIl = lIllIlIlllIlII.next();
                String lIllIlIllllIll = ((Object)((Object)lIllIlIllllIIl)).getClass().getName();
                if (PlayerInfo.lIIlIllllll((int)lIllIlIllllIll.equals(lIllIlIllllIlI = lIllIlIlllIlll.getName()))) {
                    return (T)((Object)lIllIlIllllIIl);
                }
                "".length();
                if (-"  ".length() < 0) continue;
                return null;
            }
            return null;
        }

        public int getAdditionalSkills() {
            PlayerInfo lIllIlIlIllllI;
            return lIllIlIlIllllI.additionalSkills;
        }

        public void setSpeedModifier(double lIlllIIllIIllI) {
            lIlllIIllIIlIl.speedModifier = lIlllIIllIIllI;
        }

        public void setAdditionalSkills(int lIllIlIlIllIIl) {
            lIllIlIlIllIlI.additionalSkills = lIllIlIlIllIIl;
        }

        public void ultraTick() {
            PlayerInfo lIllIllllIIllI;
            lIllIllllIIllI.updateSpeedSlowPotionEffects();
            Iterator<StatusEffect> lIllIllllIIlIl = lIllIllllIIllI.effects.iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIllllIIlIl.hasNext())) {
                StatusEffect lIllIllllIlIII = lIllIllllIIlIl.next();
                if (PlayerInfo.lIIlIllllll((int)lIllIllllIlIII.isValid())) {
                    lIllIllllIlIII.tick();
                }
                "".length();
                if ("  ".length() == "  ".length()) continue;
                return;
            }
            lIllIllllIIllI.removeExpiredEffects();
            lIllIllllIIllI.updatePlayerActionBar();
        }

        public void tick() {
        }

        public int getSpree() {
            PlayerInfo lIlllIlIIIlIIl;
            return lIlllIlIIIlIIl.spree;
        }

        public void setAuras(ArrayList<AuraInfo> lIllIllllllIll) {
            lIllIlllllllII.auras = lIllIllllllIll;
        }

        public void setValue(int lIlllIIllIllIl) {
            lIlllIIllIlllI.value = lIlllIIllIllIl;
        }

        public boolean isAlive() {
            PlayerInfo lIlllIIlIlIIII;
            return lIlllIIlIlIIII.isAlive;
        }

        public boolean isAFK() {
            PlayerInfo lIlllIIIIIllIl;
            boolean bl;
            if (PlayerInfo.lIIllIIIIIl(PlayerInfo.lIIllIIIIII(lIlllIIIIIllIl.getIdleTime().getSeconds(), 10L))) {
                bl = lIlIIlllI[1];
                "".length();
                if (((99 ^ 3 ^ (16 ^ 67)) & (55 ^ 83 ^ (69 ^ 18) ^ -" ".length())) != (("  ".length() ^ (122 ^ 47)) & (172 + 102 - 117 + 44 ^ 34 + 89 - 74 + 109 ^ -" ".length()))) {
                    return (boolean)((113 + 11 - -58 + 18 ^ 127 + 114 - 197 + 95) & (86 ^ 92 ^ (104 ^ 33) ^ -" ".length()));
                }
            } else {
                bl = lIlIIlllI[0];
            }
            return bl;
        }

        public void setKills(int lIlllIIlIllIll) {
            lIlllIIlIllllI.kills = lIlllIIlIllIll;
        }

        public Duration getIdleTime() {
            PlayerInfo lIlllIIIIlIIII;
            return Duration.between(lIlllIIIIlIIII.lastMoveEvent, ZonedDateTime.now());
        }

        protected Player getPlayer() {
            PlayerInfo lIlllIIlllIllI;
            if (PlayerInfo.lIIlIllllIl(lIlllIIlllIllI.name)) {
                return null;
            }
            return Bukkit.getPlayer((String)lIlllIIlllIllI.name);
        }

        public void renderAuras() {
        }

        static {
            PlayerInfo.lIIlIllllII();
            PlayerInfo.lIIlIlllIII();
        }

        public PlayerInfo() {
            PlayerInfo lIlllIlIIIlllI;
            lIlllIlIIIlllI.additionalSkills = lIlIIlllI[0];
            lIlllIlIIIlllI.speedModifier = 0.0;
            lIlllIlIIIlllI.immune = lIlIIlllI[1];
            lIlllIlIIIlllI.lastDamager = null;
            lIlllIlIIIlllI.lastMoveEvent = ZonedDateTime.now();
            lIlllIlIIIlllI.lastRespawnEvent = ZonedDateTime.now();
            lIlllIlIIIlllI.kills = lIlIIlllI[0];
            lIlllIlIIIlllI.deaths = lIlIIlllI[0];
            lIlllIlIIIlllI.isAlive = lIlIIlllI[1];
            lIlllIlIIIlllI.damageDealt = 0.0;
            lIlllIlIIIlllI.blocksBroken = lIlIIlllI[0];
            lIlllIlIIIlllI.blocksPlaced = lIlIIlllI[0];
            lIlllIlIIIlllI.objectivesCompleted = lIlIIlllI[0];
            lIlllIlIIIlllI.effects = new ArrayList();
            lIlllIlIIIlllI.auras = new ArrayList();
            lIlllIlIIIlllI.auraRenderer = null;
        }

        private static int lIIllIIIIII(long l, long l2) {
            return (int)(l LCMP l2);
        }

        public ArrayList<AuraInfo> getRealtimeAuras() {
            PlayerInfo lIlllIIIIIIlII;
            ArrayList<AuraInfo> lIlllIIIIIIIll = new ArrayList<AuraInfo>();
            if (PlayerInfo.lIIlIllllll((int)lIlllIIIIIIlII.isImmune())) {
                "".length();
                lIlllIIIIIIIll.add(new AuraInfo(lIlIIlIll[lIlIIlllI[0]], lIlIIlllI[2], 6.0, new ItemStack(Material.BARRIER, lIlIIlllI[1])));
            }
            return lIlllIIIIIIIll;
        }

        public void setBlocksBroken(int lIlllIIIllIIII) {
            lIlllIIIlIllll.blocksBroken = lIlllIIIllIIII;
        }

        public boolean hasStatusEffect(String lIllIllIIIIlll) {
            PlayerInfo lIllIllIIIlIII;
            Iterator<StatusEffect> lIllIllIIIIlII = lIllIllIIIlIII.effects.iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIllIIIIlII.hasNext())) {
                StatusEffect lIllIllIIIlIIl = lIllIllIIIIlII.next();
                String lIllIllIIIlIlI = lIllIllIIIlIIl.getName();
                if (PlayerInfo.lIIlIllllll((int)lIllIllIIIIlll.equals(lIllIllIIIlIlI))) {
                    return lIlIIlllI[1];
                }
                "".length();
                if ((56 ^ 99 ^ (255 ^ 161)) > 0) continue;
                return (boolean)("  ".length() & ("  ".length() ^ -" ".length()));
            }
            return lIlIIlllI[0];
        }

        public int getDeaths() {
            PlayerInfo lIlllIIlIllIII;
            return lIlllIIlIllIII.deaths;
        }

        public void setDamageDealt(double lIlllIIlIIIIlI) {
            lIlllIIlIIIIll.damageDealt = lIlllIIlIIIIlI;
        }

        private static boolean lIIllIIIIlI(Object object) {
            return object != null;
        }

        public <T extends StatusEffect> T getStatusEffect(String lIllIlIllIIllI) {
            PlayerInfo lIllIlIllIlIIl;
            Iterator<StatusEffect> lIllIlIllIIlIl = lIllIlIllIlIIl.effects.iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIlIllIIlIl.hasNext())) {
                StatusEffect lIllIlIllIlIlI = lIllIlIllIIlIl.next();
                String lIllIlIllIlIll = lIllIlIllIlIlI.getName();
                if (PlayerInfo.lIIlIllllll((int)lIllIlIllIIllI.equals(lIllIlIllIlIll))) {
                    return (T)((Object)lIllIlIllIlIlI);
                }
                "".length();
                if (null == null) continue;
                return null;
            }
            return null;
        }

        public void addStatusEffect(StatusEffect lIllIlllIIlIIl) {
            PlayerInfo lIllIlllIIlIlI;
            if (PlayerInfo.lIIlIlllllI((int)lIllIlllIIlIlI.hasStatusEffect(((Object)((Object)lIllIlllIIlIIl)).getClass()))) {
                "".length();
                lIllIlllIIlIlI.effects.add(lIllIlllIIlIIl);
            }
        }

        public double getDamageDealt() {
            PlayerInfo lIlllIIlIIIlll;
            return lIlllIIlIIIlll.damageDealt;
        }

        public String getStatusEffectsText() {
            PlayerInfo lIllIllIlIlIII;
            String lIllIllIlIIlll = lIlIIlIll[lIlIIlllI[1]];
            Iterator<StatusEffect> lIllIllIlIIlII = lIllIllIlIlIII.effects.iterator();
            while (PlayerInfo.lIIlIllllll((int)lIllIllIlIIlII.hasNext())) {
                StatusEffect lIllIllIlIlIIl = lIllIllIlIIlII.next();
                String lIllIllIlIlIlI = lIllIllIlIlIIl.getDisplayText();
                if (PlayerInfo.lIIllIIIIlI(lIllIllIlIlIlI)) {
                    String lIllIllIlIlIll = lIlIIlIll[lIlIIlllI[4]];
                    if (PlayerInfo.lIIlIlllllI(lIllIllIlIlIII.effects.indexOf((Object)lIllIllIlIlIIl))) {
                        lIllIllIlIlIll = lIlIIlIll[lIlIIlllI[5]];
                    }
                    lIllIllIlIIlll = String.valueOf(new StringBuilder().append(lIllIllIlIIlll).append(lIllIllIlIlIll).append(lIllIllIlIlIlI));
                }
                "".length();
                if ("  ".length() < "   ".length()) continue;
                return null;
            }
            return lIllIllIlIIlll;
        }

        private static void lIIlIllllII() {
            lIlIIlllI = new int[9];
            PlayerInfo.lIlIIlllI[0] = (123 ^ 36 ^ (118 ^ 55)) & (226 ^ 189 ^ (246 ^ 183) ^ -" ".length());
            PlayerInfo.lIlIIlllI[1] = " ".length();
            PlayerInfo.lIlIIlllI[2] = 113 ^ 60 ^ (52 ^ 113);
            PlayerInfo.lIlIIlllI[3] = -" ".length();
            PlayerInfo.lIlIIlllI[4] = "  ".length();
            PlayerInfo.lIlIIlllI[5] = "   ".length();
            PlayerInfo.lIlIIlllI[6] = 86 + 115 - 129 + 63 ^ 90 + 34 - 44 + 51;
            PlayerInfo.lIlIIlllI[7] = 151 ^ 137;
            PlayerInfo.lIlIIlllI[8] = 28 ^ 25;
        }

        private static String lIIlIllIlII(String lIllIlIIlIllll, String lIllIlIIllIIII) {
            try {
                SecretKeySpec lIllIlIIllIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlIIllIIII.getBytes(StandardCharsets.UTF_8)), lIlIIlllI[2]), "DES");
                Cipher lIllIlIIllIIll = Cipher.getInstance("DES");
                lIllIlIIllIIll.init(lIlIIlllI[4], lIllIlIIllIlII);
                return new String(lIllIlIIllIIll.doFinal(Base64.getDecoder().decode(lIllIlIIlIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIllIlIIllIIlI) {
                lIllIlIIllIIlI.printStackTrace();
                return null;
            }
        }

        public Player getLastDamager() {
            PlayerInfo lIlllIIIlllllI;
            return lIlllIIIlllllI.lastDamager;
        }

        public void addAura(AuraInfo lIllIlllllIlII) {
            PlayerInfo lIllIlllllIIll;
            "".length();
            lIllIlllllIIll.auras.add(lIllIlllllIlII);
        }

        private static boolean lIIlIlllllI(int n) {
            return n == 0;
        }

        public void setLastDamager(Player lIlllIIIllIlll) {
            lIlllIIIlllIII.lastDamager = lIlllIIIllIlll;
        }

        public void setBlocksPlaced(int lIlllIIIlIIlIl) {
            lIlllIIIlIIllI.blocksPlaced = lIlllIIIlIIlIl;
        }

        public void setSpree(int lIlllIlIIIIlII) {
            lIlllIlIIIIIll.spree = lIlllIlIIIIlII;
        }

        public int getObjectivesCompleted() {
            PlayerInfo lIlllIIIlIIIlI;
            return lIlllIIIlIIIlI.objectivesCompleted;
        }

        public int getUnselectedSkillAmount() {
            PlayerInfo lIllIlIlIIlIIl;
            int n;
            int lIllIlIlIIlIlI = lIllIlIlIIlIIl.getTotalSkills() - lIllIlIlIIlIIl.Joc.this.s.getSkillsForPlayer(lIllIlIlIIlIIl.getPlayer()).size();
            if (PlayerInfo.lIIllIIIIll(lIllIlIlIIlIlI)) {
                n = lIlIIlllI[0];
                "".length();
                if (null != null) {
                    return (92 + 139 - 13 + 28 ^ 67 + 145 - 174 + 124) & (211 ^ 176 ^ (188 ^ 139) ^ -" ".length());
                }
            } else {
                n = lIllIlIlIIlIlI;
            }
            return n;
        }

        public ArrayList<StatusEffect> getStatusEffects() {
            PlayerInfo lIllIlllIIllll;
            return lIllIlllIIllll.effects;
        }

        public int getValue() {
            PlayerInfo lIlllIIlllIIll;
            return lIlllIIlllIIll.value;
        }

        public double getSpeedModifier() {
            PlayerInfo lIlllIIllIlIlI;
            return lIlllIIllIlIlI.speedModifier;
        }

        public void updatePlayerActionBar() {
            PlayerInfo lIllIlIllIIIII;
            if (PlayerInfo.lIIlIllllIl((Object)lIllIlIllIIIII.getPlayer())) {
                return;
            }
        }

        public void setDeaths(int lIlllIIlIlIIlI) {
            lIlllIIlIlIlIl.deaths = lIlllIIlIlIIlI;
        }

        public void removeStatusEffect(Class<? extends StatusEffect> lIllIllIllllll) {
            PlayerInfo lIllIllIlllllI;
            if (PlayerInfo.lIIlIllllll((int)lIllIllIlllllI.hasStatusEffect(lIllIllIllllll))) {
                "".length();
                lIllIllIlllllI.effects.remove((Object)lIllIllIlllllI.getStatusEffect(lIllIllIllllll));
            }
        }

        public void setImmune(boolean lIlllIIIIlIIll) {
            lIlllIIIIlIllI.immune = lIlllIIIIlIIll;
        }

        private static boolean lIIllIIIIIl(int n) {
            return n > 0;
        }

        public void addAdditionalSkill() {
            PlayerInfo lIllIlIlIlIlII;
            lIllIlIlIlIlII.setAdditionalSkills(lIllIlIlIlIlII.getAdditionalSkills() + lIlIIlllI[1]);
            Player lIllIlIlIlIIll = lIllIlIlIlIlII.getPlayer();
            if (PlayerInfo.lIIllIIIIlI((Object)lIllIlIlIlIIll)) {
                lIllIlIlIlIlII.Joc.this.giveRemainingUnlockers(lIllIlIlIlIIll);
            }
            lIllIlIlIlIlII.Joc.this.sendPlayerMessage(lIllIlIlIlIIll, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIlIIlIll[lIlIIlllI[6]])));
        }
    }

    public static final class GameState
    extends Enum<GameState> {
        public static final /* synthetic */ /* enum */ GameState Editant;
        private static final /* synthetic */ String[] llIIIlII;
        public static final /* synthetic */ /* enum */ GameState WaitingForPlayers;
        public static final /* synthetic */ /* enum */ GameState Preparing;
        public static final /* synthetic */ /* enum */ GameState Resetejant;
        public static final /* synthetic */ /* enum */ GameState InGame;
        private static final /* synthetic */ int[] llIllIlI;
        private static final /* synthetic */ GameState[] $VALUES;
        public static final /* synthetic */ /* enum */ GameState Complete;

        private static String llIIIIIIlI(String lIIIlIlIlIIlllI, String lIIIlIlIlIIllIl) {
            try {
                SecretKeySpec lIIIlIlIlIlIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlIlIlIIllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIlIlIlIlIIlI = Cipher.getInstance("Blowfish");
                lIIIlIlIlIlIIlI.init(llIllIlI[2], lIIIlIlIlIlIIll);
                return new String(lIIIlIlIlIlIIlI.doFinal(Base64.getDecoder().decode(lIIIlIlIlIIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlIlIlIlIIIl) {
                lIIIlIlIlIlIIIl.printStackTrace();
                return null;
            }
        }

        public static GameState valueOf(String lIIIlIllIIIIlII) {
            return Enum.valueOf(GameState.class, lIIIlIllIIIIlII);
        }

        private static boolean llIlIIIIll(int n, int n2) {
            return n < n2;
        }

        private static void llIlIIIIlI() {
            llIllIlI = new int[8];
            GameState.llIllIlI[0] = (123 ^ 93) & ~(143 ^ 169);
            GameState.llIllIlI[1] = " ".length();
            GameState.llIllIlI[2] = "  ".length();
            GameState.llIllIlI[3] = "   ".length();
            GameState.llIllIlI[4] = 74 ^ 78;
            GameState.llIllIlI[5] = 49 ^ 90 ^ (111 ^ 1);
            GameState.llIllIlI[6] = 88 ^ 125 ^ (17 ^ 50);
            GameState.llIllIlI[7] = 70 + 132 - 100 + 55 ^ 130 + 97 - 192 + 114;
        }

        private GameState() {
            GameState lIIIlIllIIIIIII;
        }

        static {
            GameState.llIlIIIIlI();
            GameState.llIIIIIlIl();
            InGame = new GameState();
            Preparing = new GameState();
            WaitingForPlayers = new GameState();
            Complete = new GameState();
            Resetejant = new GameState();
            Editant = new GameState();
            GameState[] arrgameState = new GameState[llIllIlI[6]];
            arrgameState[GameState.llIllIlI[0]] = InGame;
            arrgameState[GameState.llIllIlI[1]] = Preparing;
            arrgameState[GameState.llIllIlI[2]] = WaitingForPlayers;
            arrgameState[GameState.llIllIlI[3]] = Complete;
            arrgameState[GameState.llIllIlI[4]] = Resetejant;
            arrgameState[GameState.llIllIlI[5]] = Editant;
            $VALUES = arrgameState;
        }

        private static String llIIIIIIll(String lIIIlIlIlIlllIl, String lIIIlIlIlIlllII) {
            try {
                SecretKeySpec lIIIlIlIllIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIlIlIlIlllII.getBytes(StandardCharsets.UTF_8)), llIllIlI[7]), "DES");
                Cipher lIIIlIlIlIlllll = Cipher.getInstance("DES");
                lIIIlIlIlIlllll.init(llIllIlI[2], lIIIlIlIllIIIII);
                return new String(lIIIlIlIlIlllll.doFinal(Base64.getDecoder().decode(lIIIlIlIlIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlIlIlIllllI) {
                lIIIlIlIlIllllI.printStackTrace();
                return null;
            }
        }

        private static void llIIIIIlIl() {
            llIIIlII = new String[llIllIlI[6]];
            GameState.llIIIlII[GameState.llIllIlI[0]] = GameState.llIIIIIIlI("RIsociv6DpE=", "CRBcx");
            GameState.llIIIlII[GameState.llIllIlI[1]] = GameState.llIIIIIIlI("GG1+1lMHTZEMXm6LCy/mAA==", "LRlwp");
            GameState.llIIIlII[GameState.llIllIlI[2]] = GameState.llIIIIIIlI("eiq0Z+nAwoXNeguTCGIrs6hFRV+UnGmn", "mMCpX");
            GameState.llIIIlII[GameState.llIllIlI[3]] = GameState.llIIIIIIlI("IHtx6ld9uaLgMtkoyzuycQ==", "UPbAR");
            GameState.llIIIlII[GameState.llIllIlI[4]] = GameState.llIIIIIIll("VTRDEZpQYDXXeqyYFxrq2A==", "fhwrT");
            GameState.llIIIlII[GameState.llIllIlI[5]] = GameState.llIIIIIlII("IzALBQkIIA==", "fTbqh");
        }

        private static String llIIIIIlII(String lIIIlIlIllIllIl, String lIIIlIlIlllIIIl) {
            lIIIlIlIllIllIl = new String(Base64.getDecoder().decode(lIIIlIlIllIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIIlIlIlllIIII = new StringBuilder();
            char[] lIIIlIlIllIllll = lIIIlIlIlllIIIl.toCharArray();
            int lIIIlIlIllIlllI = llIllIlI[0];
            char[] lIIIlIlIllIlIII = lIIIlIlIllIllIl.toCharArray();
            int lIIIlIlIllIIlll = lIIIlIlIllIlIII.length;
            int lIIIlIlIllIIllI = llIllIlI[0];
            while (GameState.llIlIIIIll(lIIIlIlIllIIllI, lIIIlIlIllIIlll)) {
                char lIIIlIlIlllIIll = lIIIlIlIllIlIII[lIIIlIlIllIIllI];
                "".length();
                lIIIlIlIlllIIII.append((char)(lIIIlIlIlllIIll ^ lIIIlIlIllIllll[lIIIlIlIllIlllI % lIIIlIlIllIllll.length]));
                ++lIIIlIlIllIlllI;
                ++lIIIlIlIllIIllI;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.valueOf(lIIIlIlIlllIIII);
        }

        public static GameState[] values() {
            return (GameState[])$VALUES.clone();
        }
    }

}

