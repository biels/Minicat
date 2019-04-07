/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  com.biel.BielAPI.Utils.Pair
 *  com.biel.BielAPI.events.PlayerWorldEventBus
 *  com.connorlinfoot.titleapi.TitleAPI
 *  com.gmail.filoghost.holographicdisplays.api.Hologram
 *  com.gmail.filoghost.holographicdisplays.api.HologramsAPI
 *  com.gmail.filoghost.holographicdisplays.api.line.TextLine
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.Pair;
import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocScoreCombo;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import com.connorlinfoot.titleapi.TitleAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Parkour
extends JocScoreCombo {
    private static final /* synthetic */ String[] lIllllII;
    /* synthetic */ int playerCount;
    /* synthetic */ ParkourProvider provider;
    private static final /* synthetic */ int[] lIllllll;
    /* synthetic */ ArrayList<ParkourStream> streams;
    /* synthetic */ int mapLength;

    public Vector getLeft() {
        Parkour lIIlIlIIIlIlIlI;
        return lIIlIlIIIlIlIlI.getRight().multiply(lIllllll[3]);
    }

    protected void updateStartingPlatforms() {
        Parkour lIIlIlIIIIIIlll;
        ArrayList<Player> lIIlIlIIIIIIllI = lIIlIlIIIIIIlll.getPlayers();
        if (Parkour.lIlllllIIl(lIIlIlIIIIIIlll.playerCount, lIIlIlIIIIIIllI.size())) {
            return;
        }
        Vector lIIlIlIIIIIIlIl = new Vector(lIllllll[0], lIllllll[2], lIllllll[0]).crossProduct(lIIlIlIIIIIIlll.getForward());
        int lIIlIlIIIIIIlII = lIIlIlIIIIIIllI.size() - lIllllll[2];
        "".length();
        lIIlIlIIIIIIlIl.multiply(lIIlIlIIIIIIlII * lIllllll[5]);
        Location lIIlIlIIIIIIIll = lIIlIlIIIIIIlll.world.getSpawnLocation().add(lIIlIlIIIIIIlll.getForward().clone().multiply(lIllllll[6]));
        Cuboid lIIlIlIIIIIIIlI = new Cuboid(lIIlIlIIIIIIIll.clone().add(lIIlIlIIIIIIlIl).add(lIIlIlIIIIIIlll.getForward().clone().multiply(lIllllll[3])), lIIlIlIIIIIIIll.clone().add(lIIlIlIIIIIIlIl.multiply(lIllllll[3])).add(lIIlIlIIIIIIlll.getForward().clone().multiply(lIllllll[2])).add(0.0, -2.0, 0.0));
        lIIlIlIIIIIIIlI.getBlocks().forEach(lIIlIIllIllIIlI -> {
            if (Parkour.lIlllllllI((int)lIIlIIllIllIIlI.isEmpty())) {
                "".length();
                lIIlIIllIllIIlI.breakNaturally();
            }
        });
        Iterator<Player> lIIlIIllllllIll = lIIlIlIIIIIIllI.iterator();
        while (Parkour.lIllllllII((int)lIIlIIllllllIll.hasNext())) {
            int n;
            Player lIIlIlIIIIIlIII = lIIlIIllllllIll.next();
            int lIIlIlIIIIIlIll = lIIlIlIIIIIIllI.indexOf((Object)lIIlIlIIIIIlIII);
            Vector lIIlIlIIIIIlIlI = lIIlIlIIIIIIlIl.clone();
            if (Parkour.lIlllllllI(lIIlIlIIIIIIlII)) {
                n = lIllllll[2];
                "".length();
                if ((113 ^ 117) <= -" ".length()) {
                    return;
                }
            } else {
                n = lIIlIlIIIIIIlII;
            }
            Location lIIlIlIIIIIlIIl = lIIlIlIIIIIIIll.clone().add(lIIlIlIIIIIlIlI.clone().multiply((double)lIIlIlIIIIIlIll / (double)n)).add(lIIlIlIIIIIlIlI.clone().multiply(-0.5));
            lIIlIlIIIIIlIIl.getBlock().getRelative(BlockFace.DOWN).setType(Material.COAL_BLOCK);
            "".length();
            lIIlIlIIIIIlIII.teleport(lIIlIlIIIIIlIIl);
            "".length();
            if (-"  ".length() < 0) continue;
            return;
        }
        lIIlIlIIIIIIlll.playerCount = lIIlIlIIIIIIllI.size();
    }

    @Override
    protected void customJoin(Player lIIlIlIIIIlllIl) {
        Parkour lIIlIlIIIIllllI;
        super.customJoin(lIIlIlIIIIlllIl);
    }

    public void comprovarFinish() {
        Parkour lIIlIIllllIllII;
        int lIIlIIllllIllIl = lIIlIIllllIllII.getPlayers().stream().map(lIIlIIllIllIlIl -> {
            Parkour lIIlIIllIllIllI;
            return ((JocScoreCombo.JocScoreComboPlayerInfo)lIIlIIllIllIllI.getPlayerInfo((Player)lIIlIIllIllIlIl)).isInGame();
        }).allMatch(lIIlIIllIllllII -> {
            boolean bl;
            if (Parkour.lIlllllllI((int)lIIlIIllIllllII.booleanValue())) {
                bl = lIllllll[2];
                "".length();
                if (null != null) {
                    return (boolean)((146 + 137 - 153 + 30 ^ 118 + 151 - 205 + 123) & (118 + 47 - 65 + 118 ^ 140 + 100 - 89 + 42 ^ -" ".length()));
                }
            } else {
                bl = lIllllll[0];
            }
            return bl;
        }) ? 1 : 0;
        if (Parkour.lIllllllII(lIIlIIllllIllIl)) {
            lIIlIIllllIllII.comprovarGuanyador();
        }
    }

    private static boolean lIlllllllI(int n) {
        return n == 0;
    }

    static {
        Parkour.lIllllIlll();
        Parkour.lIlllIlIII();
    }

    private static void lIlllIlIII() {
        lIllllII = new String[lIllllll[7]];
        Parkour.lIllllII[Parkour.lIllllll[0]] = Parkour.lIlllIIllI("IYv4wqENEiI=", "vKuUQ");
        Parkour.lIllllII[Parkour.lIllllll[2]] = Parkour.lIlllIIlll("K1qLUGP5VIyoJvBNVMc9ag==", "BIKsR");
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIIlIlIIIlIIIll) {
        return null;
    }

    public void generateStreams() {
        Parkour lIIlIlIIIIlIlll;
        lIIlIlIIIIlIlll.getPlayers().forEach(lIIlIIllIlIlllI -> {
            Parkour lIIlIIllIlIllll;
            "".length();
            lIIlIIllIlIllll.streams.add(lIIlIIllIlIllll.new ParkourStream((Player)lIIlIIllIlIlllI, lIIlIIllIlIlllI.getLocation().getBlock().getLocation().add(0.0, -1.0, 0.0)));
        });
    }

    @Override
    public String getGameName() {
        return lIllllII[lIllllll[0]];
    }

    public Parkour() {
        Parkour lIIlIlIIIlllIIl;
        lIIlIlIIIlllIIl.streams = new ArrayList();
        lIIlIlIIIlllIIl.provider = lIIlIlIIIlllIIl.new ParkourProvider();
        lIIlIlIIIlllIIl.playerCount = lIllllll[0];
        lIIlIlIIIlllIIl.mapLength = lIllllll[1];
    }

    protected void teleportToEndingSpawn(Player lIIlIIlllllIIIl) {
        Parkour lIIlIIlllllIIlI;
        "".length();
        lIIlIIlllllIIIl.teleport(lIIlIIlllllIIlI.pMapaActual().ObtenirLocation(lIllllII[lIllllll[2]], lIIlIIlllllIIlI.world));
    }

    private static boolean lIlllllIIl(int n, int n2) {
        return n == n2;
    }

    public Vector getCenterize() {
        return new Vector(0.5, 0.5, 0.5);
    }

    public Vector getForward() {
        return new Vector(lIllllll[2], lIllllll[0], lIllllll[0]);
    }

    private static String lIlllIIllI(String lIIlIIlIllllllI, String lIIlIIlIlllllll) {
        try {
            SecretKeySpec lIIlIIllIIIIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlIIlIlllllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIlIIllIIIIIlI = Cipher.getInstance("Blowfish");
            lIIlIIllIIIIIlI.init(lIllllll[7], lIIlIIllIIIIIll);
            return new String(lIIlIIllIIIIIlI.doFinal(Base64.getDecoder().decode(lIIlIIlIllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIIllIIIIIIl) {
            lIIlIIllIIIIIIl.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent lIIlIIlllIllIll, Player lIIlIIlllIllIlI) {
        Parkour lIIlIIlllIlllII;
        super.onPlayerMove(lIIlIIlllIllIll, lIIlIIlllIllIlI);
        if (Parkour.lIlllllllI((int)lIIlIIlllIlllII.JocIniciat.booleanValue()) && Parkour.lIlllllllI((int)lIIlIIlllIllIll.getFrom().getBlock().equals((Object)lIIlIIlllIllIll.getTo().getBlock()))) {
            lIIlIIlllIllIll.setCancelled(lIllllll[2]);
        }
    }

    private static void lIllllIlll() {
        lIllllll = new int[8];
        Parkour.lIllllll[0] = (14 ^ 46) & ~(28 ^ 60);
        Parkour.lIllllll[1] = 237 ^ 197;
        Parkour.lIllllll[2] = " ".length();
        Parkour.lIllllll[3] = -" ".length();
        Parkour.lIllllll[4] = "   ".length();
        Parkour.lIllllll[5] = 180 ^ 140 ^ (155 ^ 171);
        Parkour.lIllllll[6] = 181 ^ 178;
        Parkour.lIllllll[7] = "  ".length();
    }

    @Override
    protected void customJocFinalitzat() {
    }

    @Override
    protected void teletransportarTothom() {
        Parkour lIIlIlIIIIllIlI;
        lIIlIlIIIIllIlI.generateStreams();
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    public void ultraHeartbeat() {
        Parkour lIIlIIllllIIllI;
        super.ultraHeartbeat();
        "".length();
        lIIlIIllllIIllI.streams.removeIf(lIIlIIllIllllll -> {
            boolean bl;
            if (Parkour.lIlllllllI((int)lIIlIIllIllllll.isValid())) {
                bl = lIllllll[2];
                "".length();
                if (((161 ^ 135 ^ (75 ^ 76)) & (5 + 133 - 118 + 167 ^ 84 + 63 - 89 + 96 ^ -" ".length())) != 0) {
                    return (boolean)("   ".length() & ("   ".length() ^ -" ".length()));
                }
            } else {
                bl = lIllllll[0];
            }
            return bl;
        });
        lIIlIIllllIIllI.streams.forEach(ParkourStream::ultraTick);
    }

    @Override
    protected void customJocIniciat() {
    }

    private static boolean lIllllllII(int n) {
        return n != 0;
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return lIllllll[0];
    }

    @Override
    public void heartbeat() {
        Parkour lIIlIIllllIlIII;
        super.heartbeat();
        if (Parkour.lIlllllllI((int)lIIlIIllllIlIII.JocIniciat.booleanValue())) {
            lIIlIIllllIlIII.updateStartingPlatforms();
        }
        if (Parkour.lIllllllII((int)lIIlIIllllIlIII.JocIniciat.booleanValue())) {
            lIIlIIllllIlIII.streams.forEach(ParkourStream::tick);
            lIIlIIllllIlIII.comprovarFinish();
        }
    }

    public Vector getUp() {
        return new Vector(lIllllll[0], lIllllll[2], lIllllll[0]);
    }

    public Vector getZero() {
        return new Vector(lIllllll[0], lIllllll[0], lIllllll[0]);
    }

    public Vector getRight() {
        Parkour lIIlIlIIIlIllIl;
        return lIIlIlIIIlIllIl.getForward().crossProduct(lIIlIlIIIlIllIl.getUp());
    }

    private static String lIlllIIlll(String lIIlIIlIlllIIIl, String lIIlIIlIlllIIlI) {
        try {
            SecretKeySpec lIIlIIlIlllIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIIlIlllIIlI.getBytes(StandardCharsets.UTF_8)), lIllllll[5]), "DES");
            Cipher lIIlIIlIlllIlIl = Cipher.getInstance("DES");
            lIIlIIlIlllIlIl.init(lIllllll[7], lIIlIIlIlllIllI);
            return new String(lIIlIIlIlllIlIl.doFinal(Base64.getDecoder().decode(lIIlIIlIlllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIIlIlllIlII) {
            lIIlIIlIlllIlII.printStackTrace();
            return null;
        }
    }

    @Override
    public ParkourPlayerInfo getPlayerInfo(Player lIIlIIlllIIllIl) {
        Parkour lIIlIIlllIIllII;
        return lIIlIIlllIIllII.getPlayerInfo(lIIlIIlllIIllIl, ParkourPlayerInfo.class);
    }

    @Override
    protected void onPlayerDamage(EntityDamageEvent lIIlIIlllIlIlIl, Player lIIlIIlllIlIlII) {
        Parkour lIIlIIlllIlIIll;
        super.onPlayerDamage(lIIlIIlllIlIlIl, lIIlIIlllIlIlII);
        lIIlIIlllIlIlIl.setCancelled(lIllllll[2]);
    }

    public Vector getBackward() {
        Parkour lIIlIlIIIllIIlI;
        return lIIlIlIIIllIIlI.getForward().multiply(lIllllll[3]);
    }

    public Vector getBackRightLeftRandom() {
        Parkour lIIlIlIIIlIIlIl;
        switch (Utils.NombreEntre(lIllllll[2], lIllllll[4])) {
            case 1: {
                return lIIlIlIIIlIIlIl.getBackward();
            }
            case 2: {
                return lIIlIlIIIlIIlIl.getRight();
            }
            case 3: {
                return lIIlIlIIIlIIlIl.getLeft();
            }
        }
        return lIIlIlIIIlIIlIl.getBackward();
    }

    public class ParkourStream
    extends PlayerWorldEventBus {
        private /* synthetic */ Location startLocation;
        /* synthetic */ int playerPosition;
        private static final /* synthetic */ int[] lIlllllI;
        /* synthetic */ int targetBubbleIndex;
        /* synthetic */ ArrayList<BubbleHandler> handlers;

        protected void onPlayerMove(PlayerMoveEvent lIIllIIIIIIIIIl, Player lIIllIIIIIIIIll) {
            ParkourStream lIIllIIIIIIIlIl;
            super.onPlayerMove(lIIllIIIIIIIIIl, lIIllIIIIIIIIll);
            lIIllIIIIIIIlIl.checkBufferBuildStreaming();
        }

        static {
            ParkourStream.lIlllIlIIl();
        }

        private static boolean lIlllIllII(int n, int n2) {
            return n < n2;
        }

        public BubbleHandler getTargetBubbleHandler() {
            ParkourStream lIIllIIIIIlIIII;
            return lIIllIIIIIlIIII.handlers.get(lIIllIIIIIlIIII.targetBubbleIndex);
        }

        private static void lIlllIlIIl() {
            lIlllllI = new int[3];
            ParkourStream.lIlllllI[0] = (94 ^ 8) & ~(77 ^ 27);
            ParkourStream.lIlllllI[1] = " ".length();
            ParkourStream.lIlllllI[2] = "   ".length();
        }

        public ParkourStream(Player lIIllIIIIlIllII, Location lIIllIIIIlIlIll) {
            ParkourStream lIIllIIIIlIlIlI;
            super(lIIllIIIIlIllII);
            lIIllIIIIlIlIlI.handlers = new ArrayList();
            lIIllIIIIlIlIlI.targetBubbleIndex = lIlllllI[0];
            lIIllIIIIlIlIlI.playerPosition = lIlllllI[0];
            lIIllIIIIlIlIlI.startLocation = lIIllIIIIlIlIll;
            lIIllIIIIlIlIlI.checkBufferBuildStreaming();
        }

        protected void tick() {
            ParkourStream lIIllIIIIIIllIl;
            lIIllIIIIIIllIl.getPlayer().setExp((float)lIIllIIIIIIllIl.targetBubbleIndex / (float)lIIllIIIIIIllIl.Parkour.this.mapLength);
        }

        public Player getPlayer() {
            ParkourStream lIIllIIIIlIIlII;
            return super.getPlayer();
        }

        protected void ultraTick() {
            ParkourStream lIIllIIIIIIlIlI;
            lIIllIIIIIIlIlI.getTargetBubbleHandler().handleTick();
        }

        public boolean isValid() {
            ParkourStream lIIllIIIIlIIIlI;
            int n;
            if (ParkourStream.lIlllIlIlI((Object)lIIllIIIIlIIIlI.getPlayer()) && ParkourStream.lIlllIlIll((int)lIIllIIIIlIIIlI.Parkour.this.getPlayers().contains((Object)lIIllIIIIlIIIlI.getPlayer()))) {
                n = lIlllllI[1];
                "".length();
                if ("  ".length() != "  ".length()) {
                    return (boolean)((80 + 77 - 149 + 132 ^ 62 + 174 - 137 + 88) & (49 + 81 - 18 + 37 ^ 1 + 101 - 41 + 101 ^ -" ".length()));
                }
            } else {
                n = lIlllllI[0];
            }
            return (boolean)n;
        }

        public void checkBufferBuildStreaming() {
            int lIIllIIIIIlIlll = lIlllllI[0];
            while (ParkourStream.lIlllIllII(lIIllIIIIIlIlll, lIlllllI[2])) {
                ParkourStream lIIllIIIIIlIllI;
                int lIIllIIIIIllIIl = lIIllIIIIIlIllI.targetBubbleIndex + lIIllIIIIIlIlll;
                if (ParkourStream.lIlllIllII(lIIllIIIIIllIIl, lIIllIIIIIlIllI.handlers.size())) {
                    "".length();
                    if ("  ".length() <= 0) {
                        return;
                    }
                } else {
                    BubbleHandler lIIllIIIIIllIII = lIIllIIIIIlIllI.new BubbleHandler(lIIllIIIIIllIIl);
                    lIIllIIIIIllIII.build();
                    "".length();
                    lIIllIIIIIlIllI.handlers.add(lIIllIIIIIllIII);
                }
                ++lIIllIIIIIlIlll;
                "".length();
                if (((82 ^ 9 ^ (170 ^ 175)) & (31 ^ 21 ^ (111 ^ 59) ^ -" ".length())) == 0) continue;
                return;
            }
        }

        private static boolean lIlllIlIll(int n) {
            return n != 0;
        }

        public int getTargetBubbleIndex() {
            ParkourStream lIIllIIIIIllllI;
            return lIIllIIIIIllllI.targetBubbleIndex;
        }

        private static boolean lIlllIlIlI(Object object) {
            return object != null;
        }

        public class BubbleHandler {
            private static final /* synthetic */ int[] lIIIlIIIl;
            /* synthetic */ ZonedDateTime firstContactTime;
            private static final /* synthetic */ String[] lIIIIIlII;
            /* synthetic */ ArrayList<CheckpointHandler> checkpointHandlers;
            /* synthetic */ int providerBubbleIndex;
            /* synthetic */ JocScoreCombo.Score score;
            /* synthetic */ ZonedDateTime leaveTime;
            /* synthetic */ ArrayList<Vector> positions;
            /* synthetic */ Hologram h;

            private static int llllllIlll(double d, double d2) {
                return (int)(d DCMPG d2);
            }

            private static boolean llllllIlIl(int n, int n2) {
                return n > n2;
            }

            static {
                BubbleHandler.lllllIllIl();
                BubbleHandler.llllIIlIlI();
            }

            private static String llllIIlIIl(String lllIlIIIllllll, String lllIlIIIlllllI) {
                lllIlIIIllllll = new String(Base64.getDecoder().decode(lllIlIIIllllll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                StringBuilder lllIlIIlIIIIlI = new StringBuilder();
                char[] lllIlIIlIIIIIl = lllIlIIIlllllI.toCharArray();
                int lllIlIIlIIIIII = lIIIlIIIl[0];
                char[] lllIlIIIlllIlI = lllIlIIIllllll.toCharArray();
                int lllIlIIIlllIIl = lllIlIIIlllIlI.length;
                int lllIlIIIlllIII = lIIIlIIIl[0];
                while (BubbleHandler.lllllllllI(lllIlIIIlllIII, lllIlIIIlllIIl)) {
                    char lllIlIIlIIIlIl = lllIlIIIlllIlI[lllIlIIIlllIII];
                    "".length();
                    lllIlIIlIIIIlI.append((char)(lllIlIIlIIIlIl ^ lllIlIIlIIIIIl[lllIlIIlIIIIII % lllIlIIlIIIIIl.length]));
                    ++lllIlIIlIIIIII;
                    ++lllIlIIIlllIII;
                    "".length();
                    if (-" ".length() <= 0) continue;
                    return null;
                }
                return String.valueOf(lllIlIIlIIIIlI);
            }

            public void advance(JocScoreCombo.Score lllIllIIIlIlll) {
                BubbleHandler lllIllIIIllIII;
                Joc.PlayerInfo lllIllIIIlIllI = lllIllIIIllIII.Parkour.this.getPlayerInfo(lllIllIIIllIII.ParkourStream.this.getPlayer());
                if (BubbleHandler.llllllIlII((int)((JocScoreCombo.JocScoreComboPlayerInfo)lllIllIIIlIllI).isInGame())) {
                    lllIllIIIllIII.Parkour.this.teleportToEndingSpawn(lllIllIIIllIII.ParkourStream.this.getPlayer());
                    return;
                }
                if (BubbleHandler.llllllIlIl(lllIllIIIllIII.ParkourStream.this.targetBubbleIndex, lllIllIIIllIII.Parkour.this.mapLength) && BubbleHandler.lllllIllll((int)((JocScoreCombo.JocScoreComboPlayerInfo)lllIllIIIlIllI).isInGame())) {
                    lllIllIIIllIII.Parkour.this.sendGlobalMessage(String.valueOf(new StringBuilder().append(lllIllIIIllIII.ParkourStream.this.getPlayer().getName()).append(lIIIIIlII[lIIIlIIIl[4]])));
                    lllIllIIIllIII.ParkourStream.this.getPlayer().setGameMode(GameMode.SPECTATOR);
                    ((JocScoreCombo.JocScoreComboPlayerInfo)lllIllIIIlIllI).setInGame(lIIIlIIIl[0]);
                    return;
                }
                if (BubbleHandler.lllllIllll((int)lllIllIIIllIII.isCompleted())) {
                    lllIllIIIllIII.Parkour.this.sendGlobalMessage(lIIIIIlII[lIIIlIIIl[2]]);
                    return;
                }
                lllIllIIIllIII.score = lllIllIIIlIlll;
                lllIllIIIllIII.Parkour.this.incrementScore(lllIllIIIllIII.ParkourStream.this.getPlayer(), lllIllIIIlIlll);
                lllIllIIIllIII.showScore(lllIllIIIlIlll);
                lllIllIIIllIII.updateHologram();
                lllIllIIIllIII.ParkourStream.this.targetBubbleIndex += lIIIlIIIl[1];
                lllIllIIIllIII.ParkourStream.this.checkBufferBuildStreaming();
            }

            private static boolean llllllIIIl(int n, int n2) {
                return n == n2;
            }

            private static boolean llllllIIlI(Object object) {
                return object != null;
            }

            public void updateHologram() {
                BubbleHandler lllIllIIllIIll;
                String string;
                if (BubbleHandler.llllllIIll((Object)lllIllIIllIIll.h)) {
                    return;
                }
                lllIllIIllIIll.h.clearLines();
                if (BubbleHandler.llllllIIll((Object)lllIllIIllIIll.score)) {
                    ChatColor chatColor;
                    if (BubbleHandler.lllllIllll((int)lllIllIIllIIll.isTargeted())) {
                        chatColor = ChatColor.GREEN;
                        "".length();
                        if ("   ".length() < 0) {
                            return;
                        }
                    } else {
                        chatColor = ChatColor.YELLOW;
                    }
                    string = String.valueOf(new StringBuilder().append((Object)chatColor).append(lIIIIIlII[lIIIlIIIl[0]]).append((Object)ChatColor.BOLD).append(lIIIIIlII[lIIIlIIIl[1]]));
                    "".length();
                    if ("   ".length() <= 0) {
                        return;
                    }
                } else {
                    string = lllIllIIllIIll.score.getFormattedString();
                }
                "".length();
                lllIllIIllIIll.h.appendTextLine(string);
            }

            public void handleLocationCheckIn(Location lllIlIlIIlIllI) {
                Duration lllIlIlIIlllII;
                long lllIlIlIIllIll;
                BubbleHandler lllIlIlIIllIlI;
                Player lllIlIlIIllIII = lllIlIlIIllIlI.ParkourStream.this.getPlayer();
                if (BubbleHandler.llllllIlII((int)lllIlIlIIllIlI.Parkour.this.getPlayers().contains((Object)lllIlIlIIllIII))) {
                    return;
                }
                lllIlIlIIllIlI.checkpointHandlers.forEach(lllIlIIlllIIlI -> lllIlIIlllIIlI.handleCpLocationCheckIn(lllIlIlIIlIllI));
                if (BubbleHandler.lllllllIIl(BubbleHandler.llllllIlll(lllIlIlIIlIllI.getY(), lllIlIlIIllIlI.getBubble().getLowestSurfaceY(lllIlIlIIllIlI.ParkourStream.this.startLocation) - lIIIlIIIl[1]))) {
                    lllIlIlIIllIlI.registerFail(lllIlIlIIllIII);
                    return;
                }
                if (BubbleHandler.llllllIIlI(lllIlIlIIllIlI.firstContactTime) && BubbleHandler.lllllllIll(BubbleHandler.lllllllIII(lllIlIlIIllIll = (lllIlIlIIlllII = Duration.between(lllIlIlIIllIlI.firstContactTime, ZonedDateTime.now())).toMillis(), 3000.0 * lllIlIlIIllIlI.getBubble().getMultiplier()))) {
                    lllIlIlIIllIlI.registerFail(lllIlIlIIllIII);
                    lllIlIlIIllIlI.setLeaveTime();
                }
            }

            public boolean isCompleted() {
                BubbleHandler lllIllIIllllII;
                boolean bl;
                if (BubbleHandler.llllllIIlI((Object)lllIllIIllllII.score)) {
                    bl = lIIIlIIIl[1];
                    "".length();
                    if (" ".length() == -" ".length()) {
                        return (boolean)((114 + 53 - 77 + 62 ^ 69 + 93 - 143 + 149) & (1 ^ 26 ^ (66 ^ 105) ^ -" ".length()));
                    }
                } else {
                    bl = lIIIlIIIl[0];
                }
                return bl;
            }

            private static int llllllIllI(long l, long l2) {
                return (int)(l LCMP l2);
            }

            private void setFirstContactTime() {
                lllIlIIlllllll.firstContactTime = ZonedDateTime.now();
            }

            private static int lllllllIII(double d, double d2) {
                return (int)(d DCMPL d2);
            }

            private static void lllllIllIl() {
                lIIIlIIIl = new int[7];
                BubbleHandler.lIIIlIIIl[0] = (21 + 91 - -108 + 27 ^ 110 + 4 - 102 + 170) & (90 ^ 117 ^ (226 ^ 140) ^ -" ".length());
                BubbleHandler.lIIIlIIIl[1] = " ".length();
                BubbleHandler.lIIIlIIIl[2] = 215 ^ 129 ^ (29 ^ 79);
                BubbleHandler.lIIIlIIIl[3] = "  ".length();
                BubbleHandler.lIIIlIIIl[4] = "   ".length();
                BubbleHandler.lIIIlIIIl[5] = 182 ^ 179;
                BubbleHandler.lIIIlIIIl[6] = 100 ^ 108;
            }

            public void registerFail(Player lllIlIIllllIlI) {
                BubbleHandler lllIlIIllllIll;
                "".length();
                lllIlIIllllIlI.teleport(lllIlIIllllIll.getBubble().getFailTeleportPoint(lllIlIIllllIll.ParkourStream.this.startLocation));
                lllIlIIllllIll.ParkourStream.this.getPlayer().playSound(lllIlIIllllIll.ParkourStream.this.getPlayer().getEyeLocation(), Sound.ENTITY_HORSE_ARMOR, 1.0f, 1.1f);
                lllIlIIllllIll.advance(JocScoreCombo.Score.FAIL);
            }

            private static boolean llllllIlII(int n) {
                return n == 0;
            }

            protected void advanceBasedOnTime() {
                double lllIlIlIIIlIIl;
                BubbleHandler lllIlIlIIIllIl;
                if (BubbleHandler.llllllIIll(lllIlIlIIIllIl.firstContactTime)) {
                    lllIlIlIIIllIl.advance(JocScoreCombo.Score.N100);
                    return;
                }
                if (BubbleHandler.llllllIIll(lllIlIlIIIllIl.leaveTime)) {
                    lllIlIlIIIllIl.setLeaveTime();
                }
                Duration lllIlIlIIIllII = Duration.between(lllIlIlIIIllIl.firstContactTime, lllIlIlIIIllIl.leaveTime);
                JocScoreCombo.Score lllIlIlIIIlIll = JocScoreCombo.Score.N50;
                long lllIlIlIIIlIlI = lllIlIlIIIllII.toMillis();
                if (BubbleHandler.lllllllIIl(BubbleHandler.llllllllII(lllIlIlIIIlIlI, 1200.0 * (lllIlIlIIIlIIl = lllIlIlIIIllIl.getBubble().getMultiplier())))) {
                    lllIlIlIIIlIll = JocScoreCombo.Score.N100;
                }
                if (BubbleHandler.lllllllIIl(BubbleHandler.llllllllII(lllIlIlIIIlIlI, 800.0 * lllIlIlIIIlIIl))) {
                    lllIlIlIIIlIll = JocScoreCombo.Score.N200;
                }
                if (BubbleHandler.lllllllIIl(BubbleHandler.llllllllII(lllIlIlIIIlIlI, 400.0 * lllIlIlIIIlIIl))) {
                    lllIlIlIIIlIll = JocScoreCombo.Score.N300;
                }
                if (BubbleHandler.lllllllIIl(BubbleHandler.llllllllII(lllIlIlIIIlIlI, 250.0 * lllIlIlIIIlIIl))) {
                    lllIlIlIIIlIll = JocScoreCombo.Score.C300;
                }
                lllIlIlIIIllIl.advance(lllIlIlIIIlIll);
            }

            public BubbleHandler(int lllIllIllIlIlI) {
                BubbleHandler lllIllIllIlIIl;
                lllIllIllIlIIl.positions = new ArrayList();
                lllIllIllIlIIl.checkpointHandlers = new ArrayList();
                lllIllIllIlIIl.providerBubbleIndex = lllIllIllIlIlI;
                lllIllIllIlIIl.fetchCheckpoints();
            }

            private static boolean lllllllIIl(int n) {
                return n < 0;
            }

            public void showScore(JocScoreCombo.Score lllIllIIlIlIlI) {
                BubbleHandler lllIllIIlIllIl;
                TitleAPI.sendTitle((Player)lllIllIIlIllIl.ParkourStream.this.getPlayer(), (Integer)lIIIlIIIl[1], (Integer)lIIIlIIIl[2], (Integer)lIIIlIIIl[3], (String)lllIllIIlIlIlI.getFormattedString(), (String)String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_AQUA).append(lIIIIIlII[lIIIlIIIl[3]]).append(lllIllIIlIllIl.Parkour.this.getCombo(lllIllIIlIllIl.ParkourStream.this.getPlayer()))));
            }

            private static void llllIIlIlI() {
                lIIIIIlII = new String[lIIIlIIIl[5]];
                BubbleHandler.lIIIIIlII[BubbleHandler.lIIIlIIIl[0]] = BubbleHandler.llllIIIllI("QZfzN4H4o2g=", "urXRc");
                BubbleHandler.lIIIIIlII[BubbleHandler.lIIIlIIIl[1]] = BubbleHandler.llllIIlIII("hIU/BL7YFFY=", "gxAyO");
                BubbleHandler.lIIIIIlII[BubbleHandler.lIIIlIIIl[3]] = BubbleHandler.llllIIlIII("MMujob4v0LM=", "iPcmc");
                BubbleHandler.lIIIIIlII[BubbleHandler.lIIIlIIIl[4]] = BubbleHandler.llllIIlIIl("Tj83VA8cJT8WDxp3N1QCD3c7ERoPdg==", "nWVtn");
                BubbleHandler.lIIIIIlII[BubbleHandler.lIIIlIIIl[2]] = BubbleHandler.llllIIlIII("3apNaqsGLPUrYR5U4TBgV3GVp76v5+1XcrrrpEsAOGTcujWX79UrNUpvkbppCr0DoRKVf2GK1ic+iJ1MfrVtPA==", "NyXpG");
            }

            private static int llllllllII(double d, double d2) {
                return (int)(d DCMPG d2);
            }

            public ParkourProvider.ParkourBubble getBubble() {
                BubbleHandler lllIllIllIIlIl;
                return lllIllIllIIlIl.Parkour.this.provider.getBubble(lllIllIllIIlIl.providerBubbleIndex);
            }

            private static boolean lllllllllI(int n, int n2) {
                return n < n2;
            }

            private static boolean lllllllIll(int n) {
                return n > 0;
            }

            private static boolean lllllIllll(int n) {
                return n != 0;
            }

            private Location getHologramLocation() {
                BubbleHandler lllIllIIlllIIl;
                return lllIllIIlllIIl.getBubble().getFailTeleportPoint(lllIllIIlllIIl.ParkourStream.this.startLocation).add(0.0, 1.6, 0.0);
            }

            private void setLeaveTime() {
                lllIlIlIIIIIIl.leaveTime = ZonedDateTime.now();
            }

            private static boolean llllllIIll(Object object) {
                return object == null;
            }

            public void handleTick() {
                BubbleHandler lllIlIllIlIllI;
                Location lllIlIllIlIlll = lllIlIllIlIllI.ParkourStream.this.getPlayer().getLocation();
                "".length();
                lllIlIllIlIllI.positions.add(lllIlIllIlIlll.toVector());
                lllIlIllIlIllI.handleLocationCheckIn(lllIlIllIlIllI.getAvgPosition().toLocation(lllIlIllIlIllI.ParkourStream.this.getWorld()));
            }

            public void createHolgram() {
                BubbleHandler lllIllIIllIlll;
                if (BubbleHandler.llllllIIlI((Object)lllIllIIllIlll.h)) {
                    return;
                }
                lllIllIIllIlll.h = HologramsAPI.createHologram((Plugin)Com.getPlugin(), (Location)lllIllIIllIlll.getHologramLocation());
            }

            public void build() {
                BubbleHandler lllIllIIllIIIl;
                lllIllIIllIIIl.getBubble().buildAt(lllIllIIllIIIl.ParkourStream.this.startLocation);
                lllIllIIllIIIl.createHolgram();
            }

            public boolean isTargeted() {
                boolean bl;
                BubbleHandler lllIllIIllllll;
                if (BubbleHandler.llllllIIIl(lllIllIIllllll.ParkourStream.this.handlers.indexOf(lllIllIIllllll), lllIllIIllllll.ParkourStream.this.targetBubbleIndex)) {
                    bl = lIIIlIIIl[1];
                    "".length();
                    if ("   ".length() <= 0) {
                        return (boolean)((48 ^ 46) & ~(3 ^ 29));
                    }
                } else {
                    bl = lIIIlIIIl[0];
                }
                return bl;
            }

            private static String llllIIlIII(String lllIlIIlIlIIlI, String lllIlIIlIlIIll) {
                try {
                    SecretKeySpec lllIlIIlIlIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIlIIlIlIIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                    Cipher lllIlIIlIlIllI = Cipher.getInstance("Blowfish");
                    lllIlIIlIlIllI.init(lIIIlIIIl[3], lllIlIIlIlIlll);
                    return new String(lllIlIIlIlIllI.doFinal(Base64.getDecoder().decode(lllIlIIlIlIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception lllIlIIlIlIlIl) {
                    lllIlIIlIlIlIl.printStackTrace();
                    return null;
                }
            }

            private static String llllIIIllI(String lllIlIIllIIIIl, String lllIlIIlIllllI) {
                try {
                    SecretKeySpec lllIlIIllIIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIlIIlIllllI.getBytes(StandardCharsets.UTF_8)), lIIIlIIIl[6]), "DES");
                    Cipher lllIlIIllIIIll = Cipher.getInstance("DES");
                    lllIlIIllIIIll.init(lIIIlIIIl[3], lllIlIIllIIlII);
                    return new String(lllIlIIllIIIll.doFinal(Base64.getDecoder().decode(lllIlIIllIIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception lllIlIIllIIIlI) {
                    lllIlIIllIIIlI.printStackTrace();
                    return null;
                }
            }

            public Vector getAvgPosition() {
                BubbleHandler lllIllIIIIIlIl;
                Supplier<Stream> lllIllIIIIIlII = () -> {
                    BubbleHandler lllIlIIlllIIII;
                    return lllIlIIlllIIII.positions.stream().skip(Math.max(lIIIlIIIl[0], lllIlIIlllIIII.positions.size() - lIIIlIIIl[4]));
                };
                if (BubbleHandler.llllllIlII(BubbleHandler.llllllIllI(lllIllIIIIIlII.get().count(), 0L))) {
                    return lllIllIIIIIlIl.ParkourStream.this.getPlayer().getLocation().toVector();
                }
                return new Vector(lllIllIIIIIlII.get().mapToDouble(Vector::getX).average().getAsDouble(), lllIllIIIIIlII.get().mapToDouble(Vector::getY).average().getAsDouble(), lllIllIIIIIlII.get().mapToDouble(Vector::getZ).average().getAsDouble());
            }

            public void fetchCheckpoints() {
                BubbleHandler lllIllIlIlIlII;
                int lllIllIlIlIlIl = lIIIlIIIl[0];
                Iterator<ParkourProvider.ParkourBubble.Checkpoint> lllIllIlIlIIII = lllIllIlIlIlII.getBubble().getCheckpoints().iterator();
                while (BubbleHandler.lllllIllll((int)lllIllIlIlIIII.hasNext())) {
                    ParkourProvider.ParkourBubble.Checkpoint lllIllIlIllIII = lllIllIlIlIIII.next();
                    int n = lllIllIlIlIlIl++;
                    "".length();
                    lllIllIlIlIlII.checkpointHandlers.add(lllIllIlIlIlII.new CheckpointHandler(n));
                    "".length();
                    if ("  ".length() > 0) continue;
                    return;
                }
            }

            public void handlePlayerMoveEvent(PlayerMoveEvent lllIllIIIIllII, Player lllIllIIIIlIll) {
                BubbleHandler lllIllIIIIlllI;
                lllIllIIIIlllI.updateHologram();
            }

            public class CheckpointHandler {
                /* synthetic */ Hologram ho;
                /* synthetic */ boolean completed;
                private static final /* synthetic */ int[] lIIlIl;
                /* synthetic */ int checkpointIndex;
                /* synthetic */ boolean wasPlayerInsideRange;
                private static final /* synthetic */ String[] llllI;

                private static void lIllllI() {
                    lIIlIl = new int[14];
                    CheckpointHandler.lIIlIl[0] = (255 ^ 163) & ~(15 ^ 83);
                    CheckpointHandler.lIIlIl[1] = " ".length();
                    CheckpointHandler.lIIlIl[2] = "   ".length();
                    CheckpointHandler.lIIlIl[3] = "  ".length();
                    CheckpointHandler.lIIlIl[4] = 164 + 128 - 213 + 115 ^ 83 + 137 - 118 + 96;
                    CheckpointHandler.lIIlIl[5] = 175 ^ 136 ^ (120 ^ 90);
                    CheckpointHandler.lIIlIl[6] = 220 ^ 177 ^ (209 ^ 186);
                    CheckpointHandler.lIIlIl[7] = -(-2325 & 19893) & (-4185 & 32767);
                    CheckpointHandler.lIIlIl[8] = 60 ^ 59;
                    CheckpointHandler.lIIlIl[9] = -193 & 11206;
                    CheckpointHandler.lIIlIl[10] = 69 ^ 36 ^ (118 ^ 31);
                    CheckpointHandler.lIIlIl[11] = 117 ^ 95 ^ (136 ^ 171);
                    CheckpointHandler.lIIlIl[12] = 73 ^ 67;
                    CheckpointHandler.lIIlIl[13] = 18 + 58 - 21 + 75 ^ 86 + 87 - 60 + 24;
                }

                private static boolean llIllII(Object object) {
                    return object == null;
                }

                private static boolean llIlIlI(Object object) {
                    return object != null;
                }

                private static boolean llIIlll(int n) {
                    return n <= 0;
                }

                ParkourProvider.ParkourBubble.Checkpoint getCheckpoint() {
                    CheckpointHandler llllIlIlIlllllI;
                    return llllIlIlIlllllI.BubbleHandler.this.getBubble().getCheckpoints().get(llllIlIlIlllllI.checkpointIndex);
                }

                void complete() {
                    CheckpointHandler llllIlIlIlIIIlI;
                    llllIlIlIlIIIlI.BubbleHandler.this.setLeaveTime();
                    llllIlIlIlIIIlI.completed = lIIlIl[1];
                    llllIlIlIlIIIlI.updateHologram();
                    llllIlIlIlIIIlI.Parkour.this.incrementCombo(llllIlIlIlIIIlI.ParkourStream.this.getPlayer(), 0.1);
                    llllIlIlIlIIIlI.Parkour.this.updateScoreBoard(llllIlIlIlIIIlI.ParkourStream.this.getPlayer());
                    llllIlIlIlIIIlI.playCompletionSound();
                }

                private static boolean lIlllll(int n) {
                    return n == 0;
                }

                boolean isLast() {
                    boolean bl;
                    CheckpointHandler llllIlIlIllIlII;
                    if (CheckpointHandler.llIIIIl(llllIlIlIllIlII.checkpointIndex, llllIlIlIllIlII.BubbleHandler.this.getBubble().getCheckpoints().size() - lIIlIl[1])) {
                        bl = lIIlIl[1];
                        "".length();
                        if (-" ".length() >= " ".length()) {
                            return (boolean)((8 ^ 113 ^ (163 ^ 194)) & (127 + 11 - 4 + 30 ^ 76 + 147 - 39 + 4 ^ -" ".length()));
                        }
                    } else {
                        bl = lIIlIl[0];
                    }
                    return bl;
                }

                void playCompletionSound() {
                    CheckpointHandler llllIlIlIIlllll;
                    llllIlIlIIlllll.ParkourStream.this.getPlayer().playSound(llllIlIlIIlllll.ParkourStream.this.getPlayer().getEyeLocation(), Sound.BLOCK_WOOD_BREAK, 1.0f, (float)(0.5 + 1.5 * llllIlIlIIlllll.getRelativePositionRatio()));
                }

                public Vector getAbsoluteCheckpointPosition() {
                    CheckpointHandler llllIlIlIlllIll;
                    return llllIlIlIlllIll.BubbleHandler.this.getBubble().getEntryPoint().add(llllIlIlIlllIll.ParkourStream.this.startLocation.toVector()).add(llllIlIlIlllIll.getCheckpoint().position);
                }

                double getRelativePositionRatio() {
                    CheckpointHandler llllIlIlIlIlllI;
                    return (double)llllIlIlIlIlllI.checkpointIndex / (double)(llllIlIlIlIlllI.BubbleHandler.this.getBubble().getCheckpoints().size() - lIIlIl[1]);
                }

                private static boolean llIIIIl(int n, int n2) {
                    return n == n2;
                }

                static {
                    CheckpointHandler.lIllllI();
                    CheckpointHandler.lIlllIl();
                }

                void onLeave() {
                    CheckpointHandler llllIlIlIlIlIIl;
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIlIlIIl.isAlone())) {
                        llllIlIlIlIlIIl.BubbleHandler.this.advanceBasedOnTime();
                    }
                    llllIlIlIlIlIIl.tryComplete();
                }

                private Location getHologramLocation() {
                    CheckpointHandler llllIlIlIIlIIll;
                    return llllIlIlIIlIIll.getAbsoluteCheckpointPosition().toLocation(llllIlIlIIlIIll.ParkourStream.this.getWorld()).add(0.5, 1.8, 0.5);
                }

                private static String lIlIlll(String llllIlIIllllllI, String llllIlIIlllllIl) {
                    llllIlIIllllllI = new String(Base64.getDecoder().decode(llllIlIIllllllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    StringBuilder llllIlIIlllllII = new StringBuilder();
                    char[] llllIlIIllllIll = llllIlIIlllllIl.toCharArray();
                    int llllIlIIllllIlI = lIIlIl[0];
                    char[] llllIlIIlllIlII = llllIlIIllllllI.toCharArray();
                    int llllIlIIlllIIll = llllIlIIlllIlII.length;
                    int llllIlIIlllIIlI = lIIlIl[0];
                    while (CheckpointHandler.llIlllI(llllIlIIlllIIlI, llllIlIIlllIIll)) {
                        char llllIlIIlllllll = llllIlIIlllIlII[llllIlIIlllIIlI];
                        "".length();
                        llllIlIIlllllII.append((char)(llllIlIIlllllll ^ llllIlIIllllIll[llllIlIIllllIlI % llllIlIIllllIll.length]));
                        ++llllIlIIllllIlI;
                        ++llllIlIIlllIIlI;
                        "".length();
                        if (null == null) continue;
                        return null;
                    }
                    return String.valueOf(llllIlIIlllllII);
                }

                boolean isAlone() {
                    int n;
                    CheckpointHandler llllIlIlIllIIIl;
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIllIIIl.isFirst()) && CheckpointHandler.llIIIlI((int)llllIlIlIllIIIl.isLast())) {
                        n = lIIlIl[1];
                        "".length();
                        if (((61 ^ 42 ^ (146 ^ 162)) & (191 + 76 - 188 + 146 ^ 127 + 103 - 102 + 70 ^ -" ".length())) != 0) {
                            return (boolean)((84 ^ 51 ^ (124 ^ 95)) & (204 + 174 - 216 + 57 ^ 105 + 41 - -7 + 6 ^ -" ".length()));
                        }
                    } else {
                        n = lIIlIl[0];
                    }
                    return (boolean)n;
                }

                void tryComplete() {
                    CheckpointHandler llllIlIlIlIIlIl;
                    if (CheckpointHandler.lIlllll((int)llllIlIlIlIIlIl.completed)) {
                        llllIlIlIlIIlIl.complete();
                    }
                }

                private static int llIIllI(double d, double d2) {
                    return (int)(d DCMPG d2);
                }

                public CheckpointHandler(int llllIlIllIIIIll) {
                    CheckpointHandler llllIlIllIIIlIl;
                    llllIlIllIIIlIl.wasPlayerInsideRange = lIIlIl[0];
                    llllIlIllIIIlIl.checkpointIndex = llllIlIllIIIIll;
                    llllIlIllIIIlIl.updateHologram();
                }

                private static String lIllIlI(String llllIlIIllIlIIl, String llllIlIIllIlIII) {
                    try {
                        SecretKeySpec llllIlIIllIllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlIIllIlIII.getBytes(StandardCharsets.UTF_8)), lIIlIl[10]), "DES");
                        Cipher llllIlIIllIlIll = Cipher.getInstance("DES");
                        llllIlIIllIlIll.init(lIIlIl[3], llllIlIIllIllII);
                        return new String(llllIlIIllIlIll.doFinal(Base64.getDecoder().decode(llllIlIIllIlIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                    }
                    catch (Exception llllIlIIllIlIlI) {
                        llllIlIIllIlIlI.printStackTrace();
                        return null;
                    }
                }

                private static boolean llIlllI(int n, int n2) {
                    return n < n2;
                }

                void onEnter() {
                    CheckpointHandler llllIlIlIlIllII;
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIlIllII.isFirst())) {
                        llllIlIlIlIllII.BubbleHandler.this.setFirstContactTime();
                    }
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIlIllII.isLast()) && CheckpointHandler.lIlllll((int)llllIlIlIlIllII.isAlone())) {
                        llllIlIlIlIllII.BubbleHandler.this.advanceBasedOnTime();
                    }
                    if (CheckpointHandler.lIlllll((int)llllIlIlIlIllII.isAlone())) {
                        llllIlIlIlIllII.tryComplete();
                    }
                }

                private static String lIlIIII(String llllIlIIlIllIlI, String llllIlIIlIllIIl) {
                    try {
                        SecretKeySpec llllIlIIlIlllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIlIIlIllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                        Cipher llllIlIIlIllllI = Cipher.getInstance("Blowfish");
                        llllIlIIlIllllI.init(lIIlIl[3], llllIlIIlIlllll);
                        return new String(llllIlIIlIllllI.doFinal(Base64.getDecoder().decode(llllIlIIlIllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                    }
                    catch (Exception llllIlIIlIlllIl) {
                        llllIlIIlIlllIl.printStackTrace();
                        return null;
                    }
                }

                public void handleCpLocationCheckIn(Location llllIlIlIIllIlI) {
                    CheckpointHandler llllIlIlIIllIll;
                    int n;
                    int llllIlIlIIllIII;
                    Player llllIlIlIIllIIl = llllIlIlIIllIll.ParkourStream.this.getPlayer();
                    if (CheckpointHandler.lIlllll((int)llllIlIlIIllIll.Parkour.this.getPlayers().contains((Object)llllIlIlIIllIIl))) {
                        return;
                    }
                    if (CheckpointHandler.llIIlll(CheckpointHandler.llIIllI(llllIlIlIIllIll.getAbsoluteCheckpointPosition().add(new Vector(0.5, 1.2, 0.5)).distance(llllIlIlIIllIIl.getLocation().toVector()), llllIlIlIIllIll.getCheckpoint().radius))) {
                        n = lIIlIl[1];
                        "".length();
                        if (-" ".length() != -" ".length()) {
                            return;
                        }
                    } else {
                        n = llllIlIlIIllIII = lIIlIl[0];
                    }
                    if (CheckpointHandler.lIlllll((int)llllIlIlIIllIll.wasPlayerInsideRange) && CheckpointHandler.llIIIlI(llllIlIlIIllIII)) {
                        llllIlIlIIllIll.onEnter();
                    }
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIllIll.wasPlayerInsideRange) && CheckpointHandler.lIlllll(llllIlIlIIllIII)) {
                        llllIlIlIIllIll.onLeave();
                    }
                    llllIlIlIIllIll.wasPlayerInsideRange = llllIlIlIIllIII;
                }

                public void createHolgram() {
                    CheckpointHandler llllIlIlIIlIIII;
                    if (CheckpointHandler.llIlIlI((Object)llllIlIlIIlIIII.ho)) {
                        return;
                    }
                    llllIlIlIIlIIII.ho = HologramsAPI.createHologram((Plugin)Com.getPlugin(), (Location)llllIlIlIIlIIII.getHologramLocation());
                }

                private static boolean llIIIlI(int n) {
                    return n != 0;
                }

                boolean isFirst() {
                    CheckpointHandler llllIlIlIllIlll;
                    boolean bl;
                    if (CheckpointHandler.lIlllll(llllIlIlIllIlll.checkpointIndex)) {
                        bl = lIIlIl[1];
                        "".length();
                        if (" ".length() != " ".length()) {
                            return (boolean)((98 + 31 - 67 + 70 ^ 97 + 145 - 97 + 12) & (86 ^ 9 ^ (53 ^ 115) ^ -" ".length()));
                        }
                    } else {
                        bl = lIIlIl[0];
                    }
                    return bl;
                }

                String getHologramDisplayText() {
                    CheckpointHandler llllIlIlIIIlIlI;
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIIlIlI.completed)) {
                        return String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(llllI[lIIlIl[5]]).append(llllIlIlIIIlIlI.Parkour.this.getCombo(llllIlIlIIIlIlI.ParkourStream.this.getPlayer())));
                    }
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIIlIlI.isAlone())) {
                        return String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_GREEN).append(llllI[lIIlIl[6]]).append(lIIlIl[7]).append((Object)ChatColor.DARK_RED).append(llllI[lIIlIl[8]]).append(lIIlIl[9]));
                    }
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIIlIlI.isFirst())) {
                        return String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_GREEN).append(llllI[lIIlIl[10]]).append(lIIlIl[7]));
                    }
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIIlIlI.isLast())) {
                        return String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_RED).append(llllI[lIIlIl[11]]).append(lIIlIl[9]));
                    }
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llllI[lIIlIl[12]]));
                }

                public void updateHologram() {
                    CheckpointHandler llllIlIlIIIllII;
                    String string;
                    Object object;
                    if (CheckpointHandler.llIllII((Object)llllIlIlIIIllII.ho)) {
                        llllIlIlIIIllII.createHolgram();
                    }
                    llllIlIlIIIllII.ho.clearLines();
                    Object[] arrobject = new Object[lIIlIl[2]];
                    arrobject[CheckpointHandler.lIIlIl[0]] = llllIlIlIIIllII.getHologramDisplayText();
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIIllII.BubbleHandler.this.isTargeted())) {
                        object = llllI[lIIlIl[1]];
                        "".length();
                        if (-" ".length() >= 0) {
                            return;
                        }
                    } else {
                        object = arrobject[CheckpointHandler.lIIlIl[1]] = llllI[lIIlIl[3]];
                    }
                    if (CheckpointHandler.llIIIlI((int)llllIlIlIIIllII.BubbleHandler.this.isTargeted())) {
                        string = llllI[lIIlIl[2]];
                        "".length();
                        if ("  ".length() < 0) {
                            return;
                        }
                    } else {
                        string = llllI[lIIlIl[4]];
                    }
                    arrobject[CheckpointHandler.lIIlIl[3]] = string;
                    "".length();
                    llllIlIlIIIllII.ho.appendTextLine(MessageFormat.format(llllI[lIIlIl[0]], arrobject));
                }

                private static void lIlllIl() {
                    llllI = new String[lIIlIl[13]];
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[0]] = CheckpointHandler.lIlIIII("Pce2uAUjhqo=", "vISOM");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[1]] = CheckpointHandler.lIlIlll("Fg==", "Mfdyq");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[3]] = CheckpointHandler.lIlIIII("LbAngzGZzbk=", "vgRMv");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[2]] = CheckpointHandler.lIlIIII("NlSkzCKP1Cs=", "OookT");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[4]] = CheckpointHandler.lIlIlll("", "IxdVf");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[5]] = CheckpointHandler.lIllIlI("1ZMYxHRmJYw=", "GpLsL");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[6]] = CheckpointHandler.lIlIIII("aTWmqsNUGwM=", "ZDttd");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[8]] = CheckpointHandler.lIlIIII("I5HTz3i5NzU=", "YyLOn");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[10]] = CheckpointHandler.lIllIlI("QXYWtZUZgog=", "rkHIp");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[11]] = CheckpointHandler.lIllIlI("2Gz8h18n3Jo=", "iAvhS");
                    CheckpointHandler.llllI[CheckpointHandler.lIIlIl[12]] = CheckpointHandler.lIllIlI("MwqlJ76SpyM=", "kKIfh");
                }
            }

        }

    }

    public class ParkourPlayerInfo
    extends JocScoreCombo.JocScoreComboPlayerInfo {
        public ParkourPlayerInfo() {
            ParkourPlayerInfo lIlllIIlIlIllII;
        }

        public ParkourStream getStream() {
            ParkourPlayerInfo lIlllIIlIlIIllI;
            return lIlllIIlIlIIllI.Parkour.this.streams.stream().filter(lIlllIIlIlIIIII -> {
                ParkourPlayerInfo lIlllIIlIlIIIIl;
                return lIlllIIlIlIIIII.getPlayer().equals((Object)lIlllIIlIlIIIIl.getPlayer());
            }).findFirst().get();
        }
    }

    public class ParkourProvider {
        /* synthetic */ ArrayList<ParkourBubble> bubbles;
        private static final /* synthetic */ int[] lIlIlIlII;

        public ParkourProvider() {
            ParkourProvider lIllIIllllIlIl;
            lIllIIllllIlIl.bubbles = new ArrayList();
        }

        private static boolean lIIllIIlIlI(int n) {
            return n > 0;
        }

        List<Pair<Class<? extends ParkourBubble>, Double>> getRegisteredBubbleTypes() {
            ArrayList<Pair<Class<? extends ParkourBubble>, Double>> lIllIIllIlIIII = new ArrayList<Pair<Class<? extends ParkourBubble>, Double>>();
            "".length();
            lIllIIllIlIIII.add(new Pair(SingleBlockBubble.class, (Object)60.0));
            "".length();
            lIllIIllIlIIII.add(new Pair(ZigZagBubble.class, (Object)8.0));
            "".length();
            lIllIIllIlIIII.add(new Pair(CrossBlockTowerBubble.class, (Object)10.0));
            "".length();
            lIllIIllIlIIII.add(new Pair(SingleBlockLineBubble.class, (Object)10.0));
            "".length();
            lIllIIllIlIIII.add(new Pair(SlimeJumpBubble.class, (Object)10.0));
            "".length();
            lIllIIllIlIIII.add(new Pair(GlassPaneLineBubble.class, (Object)10.0));
            "".length();
            lIllIIllIlIIII.add(new Pair(SineWaveBubble.class, (Object)8.0));
            return lIllIIllIlIIII;
        }

        private static void lIIllIIIlll() {
            lIlIlIlII = new int[4];
            ParkourProvider.lIlIlIlII[0] = " ".length();
            ParkourProvider.lIlIlIlII[1] = (174 ^ 164 ^ (119 ^ 92)) & (143 ^ 190 ^ (77 ^ 93) ^ -" ".length());
            ParkourProvider.lIlIlIlII[2] = 195 ^ 145 ^ (48 ^ 102);
            ParkourProvider.lIlIlIlII[3] = -14546 & 14845;
        }

        Class<? extends ParkourBubble> getRandomBubbleType() {
            ParkourProvider lIllIIllIllIII;
            List<Pair<Class<? extends ParkourBubble>, Double>> lIllIIllIllIll = lIllIIllIllIII.getRegisteredBubbleTypes();
            Collections.shuffle(lIllIIllIllIll);
            double lIllIIllIllIlI = lIllIIllIllIll.stream().mapToDouble(Pair::getSecond).sum();
            int lIllIIllIllIIl = lIlIlIlII[1];
            do {
                Iterator<Pair<Class<? extends ParkourBubble>, Double>> lIllIIllIlIlII = lIllIIllIllIll.iterator();
                while (ParkourProvider.lIIllIIllII((int)lIllIIllIlIlII.hasNext())) {
                    Pair<Class<? extends ParkourBubble>, Double> lIllIIllIlllIl = lIllIIllIlIlII.next();
                    if (ParkourProvider.lIIllIIllII((int)Utils.Possibilitat((Double)lIllIIllIlllIl.getSecond(), lIllIIllIllIlI))) {
                        return (Class)lIllIIllIlllIl.getFirst();
                    }
                    "".length();
                    if (" ".length() == " ".length()) continue;
                    return null;
                }
            } while (!ParkourProvider.lIIllIIlIII(++lIllIIllIllIIl, lIlIlIlII[3]));
            "".length();
            if (-"  ".length() >= 0) {
                return null;
            }
            return (Class)lIllIIllIllIll.get(lIlIlIlII[1]).getFirst();
        }

        private static boolean lIIllIIlIII(int n, int n2) {
            return n > n2;
        }

        private static boolean lIIllIIlIIl(int n, int n2) {
            return n <= n2;
        }

        static {
            ParkourProvider.lIIllIIIlll();
        }

        private static boolean lIIllIIllII(int n) {
            return n != 0;
        }

        public void generateNextBubble() {
            try {
                ParkourProvider lIllIIlllIIlll;
                Class[] arrclass = new Class[lIlIlIlII[0]];
                arrclass[ParkourProvider.lIlIlIlII[1]] = ParkourProvider.class;
                Object[] arrobject = new Object[lIlIlIlII[0]];
                arrobject[ParkourProvider.lIlIlIlII[1]] = lIllIIlllIIlll;
                ParkourBubble lIllIIlllIlIIl = lIllIIlllIIlll.getRandomBubbleType().getConstructor(arrclass).newInstance(arrobject);
                lIllIIlllIlIIl.generate();
                if (ParkourProvider.lIIllIIlIlI(lIllIIlllIIlll.bubbles.size())) {
                    Vector lIllIIlllIlIlI = lIllIIlllIIlll.bubbles.get(lIllIIlllIIlll.bubbles.size() - lIlIlIlII[0]).getAbsoluteExitPoint().add(lIllIIlllIlIIl.getRandomBubbleSpacing());
                    lIllIIlllIlIIl.setEntryPoint(lIllIIlllIlIlI);
                    "".length();
                    if (" ".length() != " ".length()) {
                        return;
                    }
                } else {
                    lIllIIlllIlIIl.setEntryPoint(lIllIIlllIIlll.Parkour.this.getForward().multiply(lIlIlIlII[2]));
                }
                "".length();
                lIllIIlllIIlll.bubbles.add(lIllIIlllIlIIl);
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException lIllIIlllIlIII) {
                lIllIIlllIlIII.printStackTrace();
            }
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        }

        public ParkourBubble getBubble(int lIllIIlllIlllI) {
            ParkourProvider lIllIIlllIllll;
            if (ParkourProvider.lIIllIIlIII(lIllIIlllIllll.bubbles.size(), lIllIIlllIlllI)) {
                return lIllIIlllIllll.bubbles.get(lIllIIlllIlllI);
            }
            while (ParkourProvider.lIIllIIlIIl(lIllIIlllIllll.bubbles.size(), lIllIIlllIlllI)) {
                lIllIIlllIllll.generateNextBubble();
                "".length();
                if ((96 + 69 - 128 + 93 ^ 47 + 94 - 17 + 11) != 0) continue;
                return null;
            }
            return lIllIIlllIllll.getBubble(lIllIIlllIlllI);
        }

        public abstract class ParkourBubble {
            private static final /* synthetic */ int[] llIIlIIl;
            /* synthetic */ ArrayList<Checkpoint> checkpoints;
            /* synthetic */ ArrayList<Material> materials;
            /* synthetic */ Function<? super Vector, Material> materialGetter;
            /* synthetic */ ArrayList<Vector> blocks;
            private /* synthetic */ Vector entryPoint;

            public abstract double getMultiplier();

            public void setEntryPoint(Vector lIIIlllIIIlIIIl) {
                lIIIlllIIIlIIII.entryPoint = lIIIlllIIIlIIIl;
            }

            public ArrayList<Checkpoint> getCheckpoints() {
                ParkourBubble lIIIlllIIIIlIIl;
                return lIIIlllIIIIlIIl.checkpoints;
            }

            public Vector getRandomBubbleSpacing() {
                ParkourBubble lIIIlllIIIlllll;
                Vector lIIIlllIIIllllI = new Vector(llIIlIIl[0], llIIlIIl[0], llIIlIIl[0]);
                if (ParkourBubble.llIIlIIIlI((int)Utils.Possibilitat(llIIlIIl[1]))) {
                    "".length();
                    lIIIlllIIIllllI.setY(llIIlIIl[2]);
                }
                if (ParkourBubble.llIIlIIIlI((int)Utils.Possibilitat(llIIlIIl[3]))) {
                    "".length();
                    lIIIlllIIIllllI.setY(llIIlIIl[4]);
                }
                if (ParkourBubble.llIIlIIIlI((int)Utils.Possibilitat(llIIlIIl[5]))) {
                    "".length();
                    lIIIlllIIIllllI.setY(llIIlIIl[6]);
                }
                Vector lIIIlllIIIlllIl = new Vector(llIIlIIl[0], llIIlIIl[6], llIIlIIl[0]).crossProduct(lIIIlllIIIlllll.Parkour.this.getForward()).normalize();
                if (ParkourBubble.llIIlIIIlI((int)Utils.Possibilitat(llIIlIIl[7]))) {
                    "".length();
                    lIIIlllIIIlllIl.multiply(llIIlIIl[2]);
                }
                if (ParkourBubble.llIIlIIIlI((int)Utils.Possibilitat(llIIlIIl[1]))) {
                    "".length();
                    lIIIlllIIIlllIl.multiply(llIIlIIl[8]);
                }
                if (ParkourBubble.llIIlIIIlI((int)Utils.Possibilitat(llIIlIIl[9]))) {
                    "".length();
                    lIIIlllIIIlllIl.multiply(llIIlIIl[0]);
                }
                Vector lIIIlllIIIlllII = lIIIlllIIIlllll.Parkour.this.getForward().multiply(Utils.NombreEntre(llIIlIIl[10], llIIlIIl[11]));
                return lIIIlllIIIllllI.add(lIIIlllIIIlllIl).add(lIIIlllIIIlllII);
            }

            private static boolean llIIlIIlIl(int n) {
                return n == 0;
            }

            private static void llIIlIIIIl() {
                llIIlIIl = new int[12];
                ParkourBubble.llIIlIIl[0] = (66 ^ 85) & ~(214 ^ 193);
                ParkourBubble.llIIlIIl[1] = 71 ^ 77;
                ParkourBubble.llIIlIIl[2] = -" ".length();
                ParkourBubble.llIIlIIl[3] = 12 ^ 4;
                ParkourBubble.llIIlIIl[4] = -"  ".length();
                ParkourBubble.llIIlIIl[5] = 68 ^ 40 ^ (118 ^ 8);
                ParkourBubble.llIIlIIl[6] = " ".length();
                ParkourBubble.llIIlIIl[7] = 63 + 143 - 171 + 156 ^ 60 + 49 - -3 + 29;
                ParkourBubble.llIIlIIl[8] = "  ".length();
                ParkourBubble.llIIlIIl[9] = 163 ^ 199 ^ (78 ^ 108);
                ParkourBubble.llIIlIIl[10] = "   ".length();
                ParkourBubble.llIIlIIl[11] = 67 ^ 86 ^ (33 ^ 48);
            }

            public void buildAt(Location lIIIllIllllllll) {
                ParkourBubble lIIIlllIIIIIIII;
                Predicate<Vector> lIIIlllIIIIIIlI = lIIIllIlIlllIlI -> {
                    ParkourBubble lIIIllIlIlllIIl;
                    return lIIIllIlIlllIIl.materialGetter.apply((Vector)lIIIllIlIlllIlI).isSolid();
                };
                Consumer<Vector> lIIIlllIIIIIIIl = lIIIllIllIIIIIl -> {
                    ParkourBubble lIIIllIllIIIIll;
                    lIIIllIllllllll.clone().add(lIIIllIllIIIIll.getEntryPoint()).add(lIIIllIllIIIIIl).getBlock().setType(lIIIllIllIIIIll.materialGetter.apply((Vector)lIIIllIllIIIIIl));
                };
                lIIIlllIIIIIIII.blocks.stream().filter(lIIIlllIIIIIIlI).forEach(lIIIlllIIIIIIIl);
                lIIIlllIIIIIIII.blocks.stream().filter(lIIIlllIIIIIIlI.negate()).forEach(lIIIlllIIIIIIIl);
            }

            public Location getFailTeleportPoint(Location lIIIllIlllllIII) {
                ParkourBubble lIIIllIllllIllI;
                Location lIIIllIllllIlll = lIIIllIlllllIII.clone().add(lIIIllIllllIllI.getAbsoluteExitPoint()).add(0.5, 1.0, 0.5);
                lIIIllIllllIlll.setPitch(0.0f);
                lIIIllIllllIlll.setYaw(270.0f);
                return lIIIllIllllIlll;
            }

            static {
                ParkourBubble.llIIlIIIIl();
            }

            public Vector getAbsoluteExitPoint() {
                ParkourBubble lIIIlllIIIIllII;
                return lIIIlllIIIIllII.getEntryPoint().add(lIIIlllIIIIllII.checkpoints.get((int)(lIIIlllIIIIllII.checkpoints.size() - ParkourBubble.llIIlIIl[6])).position);
            }

            public List<Block> getBlockList(Location lIIIllIlllIlllI) {
                ParkourBubble lIIIllIlllIllll;
                return lIIIllIlllIllll.blocks.stream().map(lIIIllIllIIIlll -> {
                    ParkourBubble lIIIllIllIIllII;
                    return lIIIllIlllIlllI.clone().add(lIIIllIllIIllII.getEntryPoint()).add(lIIIllIllIIIlll).getBlock();
                }).collect(Collectors.toList());
            }

            public List<Block> getSurfaceBlockList(Location lIIIllIlllIIllI) {
                ParkourBubble lIIIllIlllIlIlI;
                List<Block> lIIIllIlllIlIII = lIIIllIlllIlIlI.getBlockList(lIIIllIlllIIllI);
                return lIIIllIlllIlIII.stream().map(lIIIllIllIlIIII -> lIIIllIllIlIIII.getRelative(BlockFace.UP)).filter(lIIIllIllIlIIll -> {
                    boolean bl;
                    if (ParkourBubble.llIIlIIlIl((int)lIIIllIlllIlIII.contains((Object)lIIIllIllIlIIll))) {
                        bl = llIIlIIl[6];
                        "".length();
                        if (null != null) {
                            return (boolean)((36 + 48 - 75 + 141 ^ 80 + 20 - 28 + 66) & (251 ^ 193 ^ (10 ^ 44) ^ -" ".length()));
                        }
                    } else {
                        bl = llIIlIIl[0];
                    }
                    return bl;
                }).collect(Collectors.toList());
            }

            public int getLowestSurfaceY(Location lIIIllIlllIIIII) {
                ParkourBubble lIIIllIlllIIIIl;
                List<Block> lIIIllIllIlllll = lIIIllIlllIIIIl.getSurfaceBlockList(lIIIllIlllIIIII);
                return lIIIllIllIlllll.stream().map(Block::getY).mapToInt(lIIIllIllIllIlI -> lIIIllIllIllIlI).min().getAsInt();
            }

            public ParkourBubble() {
                ParkourBubble lIIIlllIIlIlIll;
                lIIIlllIIlIlIll.checkpoints = new ArrayList<E>();
                lIIIlllIIlIlIll.blocks = new ArrayList<E>();
                lIIIlllIIlIlIll.materials = new ArrayList<E>();
                lIIIlllIIlIlIll.materialGetter = lIIIllIlIllIIlI -> {
                    ParkourBubble lIIIllIlIllIIll;
                    return lIIIllIlIllIIll.materials.get(lIIIllIlIllIIll.blocks.indexOf((Object)lIIIllIlIllIIlI));
                };
            }

            public Vector getEntryPoint() {
                ParkourBubble lIIIlllIIIlIllI;
                return lIIIlllIIIlIllI.entryPoint.clone();
            }

            private static boolean llIIlIIIlI(int n) {
                return n != 0;
            }

            public abstract void generate();

            public class Checkpoint {
                /* synthetic */ Vector position;
                /* synthetic */ double radius;

                public Checkpoint(Vector lIllIIIllIIIll) {
                    Checkpoint lIllIIIllIIlIl;
                    lIllIIIllIIlIl.position = lIllIIIllIIIll;
                    lIllIIIllIIlIl.radius = 1.2;
                }

                public Checkpoint(Vector lIllIIIllIllIl, double lIllIIIllIllII) {
                    Checkpoint lIllIIIlllIIll;
                    lIllIIIlllIIll.position = lIllIIIllIllIl;
                    lIllIIIlllIIll.radius = lIllIIIllIllII;
                }
            }

        }

        public class SingleBlockLineBubble
        extends ParkourBubble {
            private static final /* synthetic */ int[] lIIIllI;
            /* synthetic */ int n;

            private static boolean lllIlllI(int n) {
                return n == 0;
            }

            @Override
            public double getMultiplier() {
                SingleBlockLineBubble lllIIIlIlIIIllI;
                return (double)lllIIIlIlIIIllI.n * 1.05 + 0.5;
            }

            private static void lllIllII() {
                lIIIllI = new int[4];
                SingleBlockLineBubble.lIIIllI[0] = "  ".length();
                SingleBlockLineBubble.lIIIllI[1] = 207 ^ 129 ^ (18 ^ 88);
                SingleBlockLineBubble.lIIIllI[2] = (72 ^ 14) & ~(246 ^ 176);
                SingleBlockLineBubble.lIIIllI[3] = " ".length();
            }

            private static boolean lllIllIl(int n, int n2) {
                return n < n2;
            }

            static {
                SingleBlockLineBubble.lllIllII();
            }

            @Override
            public void generate() {
                SingleBlockLineBubble lllIIIlIlIIlIll;
                int lllIIIlIlIIllIl = lIIIllI[2];
                while (SingleBlockLineBubble.lllIllIl(lllIIIlIlIIllIl, lllIIIlIlIIlIll.n)) {
                    Vector lllIIIlIlIIlllI = lllIIIlIlIIlIll.Parkour.this.getForward().multiply(lIIIllI[0] * lllIIIlIlIIllIl);
                    "".length();
                    lllIIIlIlIIlIll.blocks.add(lllIIIlIlIIlllI);
                    "".length();
                    lllIIIlIlIIlIll.materials.add(Material.QUARTZ_BLOCK);
                    if (SingleBlockLineBubble.lllIlllI(lllIIIlIlIIllIl % lIIIllI[3])) {
                        "".length();
                        lllIIIlIlIIlIll.checkpoints.add(lllIIIlIlIIlIll.new ParkourBubble.Checkpoint(lllIIIlIlIIlllI));
                    }
                    ++lllIIIlIlIIllIl;
                    "".length();
                    if ((100 ^ 96) >= 0) continue;
                    return;
                }
            }

            public SingleBlockLineBubble() {
                SingleBlockLineBubble lllIIIlIlIlIIll;
                lllIIIlIlIlIIll.n = Utils.NombreEntre(lIIIllI[0], lIIIllI[1]) * lIIIllI[0];
            }
        }

        public class SingleBlockBubble
        extends ParkourBubble {
            private static final /* synthetic */ int[] lIIlllIIl;

            static {
                SingleBlockBubble.lIIIllIIlll();
            }

            @Override
            public double getMultiplier() {
                return 1.1;
            }

            @Override
            public void generate() {
                SingleBlockBubble llIlIIlIlllIll;
                "".length();
                llIlIIlIlllIll.blocks.add(new Vector(lIIlllIIl[0], lIIlllIIl[0], lIIlllIIl[0]));
                "".length();
                llIlIIlIlllIll.materials.add(Material.QUARTZ_BLOCK);
                "".length();
                llIlIIlIlllIll.checkpoints.add(llIlIIlIlllIll.new ParkourBubble.Checkpoint(new Vector(lIIlllIIl[0], lIIlllIIl[0], lIIlllIIl[0])));
            }

            private static void lIIIllIIlll() {
                lIIlllIIl = new int[1];
                SingleBlockBubble.lIIlllIIl[0] = (14 ^ 96 ^ (102 ^ 60)) & (122 + 123 - 64 + 9 ^ 49 + 132 - 133 + 90 ^ -" ".length());
            }

            public SingleBlockBubble() {
                SingleBlockBubble llIlIIllIIIIIl;
            }
        }

        public class GlassPaneLineBubble
        extends ParkourBubble {
            private static final /* synthetic */ int[] lIIllIIll;

            private static boolean lIIIllIIIIl(int n) {
                return n == 0;
            }

            private static boolean lIIIllIIIII(int n) {
                return n != 0;
            }

            public GlassPaneLineBubble() {
                GlassPaneLineBubble llIlIlIllIlllI;
            }

            private static void lIIIlIllllI() {
                lIIllIIll = new int[4];
                GlassPaneLineBubble.lIIllIIll[0] = " ".length();
                GlassPaneLineBubble.lIIllIIll[1] = 138 ^ 133;
                GlassPaneLineBubble.lIIllIIll[2] = 146 ^ 194;
                GlassPaneLineBubble.lIIllIIll[3] = "   ".length();
            }

            @Override
            public double getMultiplier() {
                return 5.5;
            }

            private static boolean lIIIlIlllll(int n, int n2) {
                return n < n2;
            }

            static {
                GlassPaneLineBubble.lIIIlIllllI();
            }

            @Override
            public void generate() {
                GlassPaneLineBubble llIlIlIllIIlIl;
                "".length();
                llIlIlIllIIlIl.blocks.add(llIlIlIllIIlIl.Parkour.this.getZero());
                "".length();
                llIlIlIllIIlIl.materials.add(Material.QUARTZ_BLOCK);
                "".length();
                llIlIlIllIIlIl.checkpoints.add(llIlIlIllIIlIl.new ParkourBubble.Checkpoint(llIlIlIllIIlIl.Parkour.this.getZero()));
                int llIlIlIllIlIII = lIIllIIll[0];
                while (GlassPaneLineBubble.lIIIlIlllll(llIlIlIllIlIII, lIIllIIll[1])) {
                    if (GlassPaneLineBubble.lIIIllIIIII((int)Utils.Possibilitat(lIIllIIll[2]))) {
                        "".length();
                        llIlIlIllIIlIl.blocks.add(llIlIlIllIIlIl.Parkour.this.getForward().multiply(llIlIlIllIlIII));
                        "".length();
                        llIlIlIllIIlIl.materials.add(Material.STAINED_GLASS_PANE);
                        if (GlassPaneLineBubble.lIIIllIIIIl(llIlIlIllIlIII % lIIllIIll[3])) {
                            "".length();
                            llIlIlIllIIlIl.checkpoints.add(llIlIlIllIIlIl.new ParkourBubble.Checkpoint(llIlIlIllIIlIl.Parkour.this.getForward().multiply(llIlIlIllIlIII)));
                        }
                    }
                    ++llIlIlIllIlIII;
                    "".length();
                    if ("  ".length() != -" ".length()) continue;
                    return;
                }
                Vector llIlIlIllIIllI = llIlIlIllIIlIl.Parkour.this.getForward().multiply(lIIllIIll[1]);
                "".length();
                llIlIlIllIIlIl.blocks.add(llIlIlIllIIllI);
                "".length();
                llIlIlIllIIlIl.materials.add(Material.QUARTZ_BLOCK);
                "".length();
                llIlIlIllIIlIl.checkpoints.add(llIlIlIllIIlIl.new ParkourBubble.Checkpoint(llIlIlIllIIllI));
            }
        }

        public class CrossBlockTowerBubble
        extends ParkourBubble {
            private static final /* synthetic */ int[] lIlIIII;
            /* synthetic */ int n;

            private static boolean lIIlIlllI(int n) {
                return n != 0;
            }

            public CrossBlockTowerBubble() {
                CrossBlockTowerBubble llIlIIlIIIIllll;
                llIlIIlIIIIllll.n = Utils.NombreEntre(lIlIIII[0], lIlIIII[1]) * lIlIIII[2] + lIlIIII[3];
            }

            private static boolean lIIlIlIll(int n) {
                return n == 0;
            }

            private static boolean lIIlIllII(int n, int n2) {
                return n < n2;
            }

            private static void lIIlIlIlI() {
                lIlIIII = new int[9];
                CrossBlockTowerBubble.lIlIIII[0] = (90 ^ 72) & ~(79 ^ 93);
                CrossBlockTowerBubble.lIlIIII[1] = "  ".length();
                CrossBlockTowerBubble.lIlIIII[2] = 153 ^ 157;
                CrossBlockTowerBubble.lIlIIII[3] = "   ".length();
                CrossBlockTowerBubble.lIlIIII[4] = " ".length();
                CrossBlockTowerBubble.lIlIIII[5] = -"  ".length();
                CrossBlockTowerBubble.lIlIIII[6] = 78 ^ 80;
                CrossBlockTowerBubble.lIlIIII[7] = 119 + 135 - 231 + 232 ^ 121 + 171 - 124 + 27;
                CrossBlockTowerBubble.lIlIIII[8] = -" ".length();
            }

            private static boolean lIIlIllIl(int n, int n2) {
                return n == n2;
            }

            @Override
            public void generate() {
                CrossBlockTowerBubble llIlIIIllllIlIl;
                Vector llIlIIIlllllIIl = llIlIIIllllIlIl.Parkour.this.getForward();
                Vector llIlIIIlllllIII = llIlIIIllllIlIl.Parkour.this.getBackRightLeftRandom();
                if (CrossBlockTowerBubble.lIIlIlIll((int)llIlIIIlllllIII.equals((Object)llIlIIIllllIlIl.Parkour.this.getBackward()))) {
                    llIlIIIllllIlIl.n -= lIlIIII[4];
                }
                int llIlIIIllllIlll = GUtils.NombreEntre((int)lIlIIII[5], (int)lIlIIII[1]);
                int llIlIIIllllIllI = llIlIIIlllllIII.equals((Object)llIlIIIllllIlIl.Parkour.this.getRight());
                int llIlIIIlllllIll = lIlIIII[0];
                while (CrossBlockTowerBubble.lIIlIllII(llIlIIIlllllIll, llIlIIIllllIlIl.n)) {
                    Material material;
                    int n;
                    Vector llIlIIIlllllllI = llIlIIIlllllIIl.clone().add(llIlIIIllllIlIl.Parkour.this.getUp().multiply(llIlIIIlllllIll));
                    "".length();
                    llIlIIIllllIlIl.blocks.add(llIlIIIlllllllI);
                    if (CrossBlockTowerBubble.lIIlIllIl(llIlIIIllllIlll, lIlIIII[4])) {
                        material = Material.PACKED_ICE;
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else {
                        material = Material.QUARTZ_BLOCK;
                    }
                    "".length();
                    llIlIIIllllIlIl.materials.add(material);
                    Vector llIlIIIllllllIl = llIlIIIlllllllI.clone().add(llIlIIIlllllIII);
                    Material llIlIIIllllllII = Material.QUARTZ_BLOCK;
                    if (CrossBlockTowerBubble.lIIlIllIl(llIlIIIllllIlll, lIlIIII[4]) && CrossBlockTowerBubble.lIIlIlllI((int)GUtils.Possibilitat((int)lIlIIII[6]))) {
                        llIlIIIllllllII = Material.IRON_FENCE;
                    }
                    if (CrossBlockTowerBubble.lIIlIllIl(llIlIIIllllIlll, lIlIIII[1]) && CrossBlockTowerBubble.lIIlIlllI((int)GUtils.Possibilitat((int)lIlIIII[7]))) {
                        llIlIIIllllllII = Material.PACKED_ICE;
                    }
                    "".length();
                    llIlIIIllllIlIl.blocks.add(llIlIIIllllllIl);
                    "".length();
                    llIlIIIllllIlIl.materials.add(llIlIIIllllllII);
                    "".length();
                    llIlIIIllllIlIl.checkpoints.add(llIlIIIllllIlIl.new ParkourBubble.Checkpoint(llIlIIIllllllIl));
                    if (CrossBlockTowerBubble.lIIlIlllI(llIlIIIllllIllI)) {
                        n = lIlIIII[4];
                        "".length();
                        if ((53 ^ 50 ^ "   ".length()) <= ((99 + 165 - 132 + 71 ^ 54 + 146 - 160 + 109) & (45 ^ 48 ^ (100 ^ 39) ^ -" ".length()))) {
                            return;
                        }
                    } else {
                        n = lIlIIII[8];
                    }
                    llIlIIIlllllIII = GUtils.rotateVector((Vector)llIlIIIlllllIII, (Vector)llIlIIIllllIlIl.Parkour.this.getUp(), (double)(1.5707963267948966 * (double)n));
                    ++llIlIIIlllllIll;
                    "".length();
                    if (null == null) continue;
                    return;
                }
            }

            @Override
            public Vector getRandomBubbleSpacing() {
                CrossBlockTowerBubble llIlIIlIIIIlIIl;
                Vector llIlIIlIIIIlIlI = super.getRandomBubbleSpacing();
                "".length();
                llIlIIlIIIIlIlI.setY(lIlIIII[0]);
                return llIlIIlIIIIlIlI;
            }

            @Override
            public double getMultiplier() {
                CrossBlockTowerBubble llIlIIIlllIlIll;
                return 1.7 * (double)llIlIIIlllIlIll.n + 0.5;
            }

            static {
                CrossBlockTowerBubble.lIIlIlIlI();
            }
        }

        public class ZigZagBubble
        extends ParkourBubble {
            private static final /* synthetic */ int[] lIIIllIll;
            /* synthetic */ int n;

            private static boolean lIIIIIlIIlI(int n, int n2) {
                return n < n2;
            }

            @Override
            public void generate() {
                ZigZagBubble lllIIlIlIIIlIl;
                int lllIIlIlIIIllI = lIIIllIll[2];
                while (ZigZagBubble.lIIIIIlIIlI(lllIIlIlIIIllI, lllIIlIlIIIlIl.n)) {
                    "".length();
                    lllIIlIlIIIlIl.blocks.add(lllIIlIlIIIlIl.Parkour.this.getForward().multiply(lIIIllIll[3] * lllIIlIlIIIllI));
                    "".length();
                    lllIIlIlIIIlIl.materials.add(Material.QUARTZ_BLOCK);
                    "".length();
                    lllIIlIlIIIlIl.blocks.add(lllIIlIlIIIlIl.Parkour.this.getForward().multiply(lIIIllIll[3] * lllIIlIlIIIllI + lIIIllIll[4]).add(lllIIlIlIIIlIl.Parkour.this.getRight().multiply(lIIIllIll[3])));
                    "".length();
                    lllIIlIlIIIlIl.materials.add(Material.QUARTZ_BLOCK);
                    if (ZigZagBubble.lIIIIIlIIlI(lllIIlIlIIIllI, lllIIlIlIIIlIl.n - lIIIllIll[4])) {
                        "".length();
                        lllIIlIlIIIlIl.blocks.add(lllIIlIlIIIlIl.Parkour.this.getForward().multiply(lIIIllIll[3] * lllIIlIlIIIllI + lIIIllIll[4]).add(lllIIlIlIIIlIl.Parkour.this.getUp()));
                        "".length();
                        lllIIlIlIIIlIl.materials.add(Material.FENCE);
                        "".length();
                        lllIIlIlIIIlIl.blocks.add(lllIIlIlIIIlIl.Parkour.this.getForward().multiply(lIIIllIll[3] * lllIIlIlIIIllI + lIIIllIll[3]).add(lllIIlIlIIIlIl.Parkour.this.getRight().multiply(lIIIllIll[3])).add(lllIIlIlIIIlIl.Parkour.this.getUp()));
                        "".length();
                        lllIIlIlIIIlIl.materials.add(Material.FENCE);
                    }
                    ++lllIIlIlIIIllI;
                    "".length();
                    if (" ".length() != 0) continue;
                    return;
                }
                lllIIlIlIIIlIl.blocks.stream().filter(lllIIlIIIllIII -> {
                    boolean bl;
                    ZigZagBubble lllIIlIIIllIIl;
                    if (ZigZagBubble.lIIIIIlIlII(lllIIlIIIllIIl.materialGetter.apply(lllIIlIIIllIII), (Object)Material.QUARTZ_BLOCK)) {
                        bl = lIIIllIll[4];
                        "".length();
                        if (null != null) {
                            return (boolean)((101 + 122 - 203 + 116 ^ 95 + 144 - 69 + 1) & (220 ^ 129 ^ (40 ^ 86) ^ -" ".length()));
                        }
                    } else {
                        bl = lIIIllIll[2];
                    }
                    return bl;
                }).forEach(lllIIlIIlIIIlI -> {
                    ZigZagBubble lllIIlIIlIIlIl;
                    "".length();
                    lllIIlIIlIIlIl.checkpoints.add(lllIIlIIlIIlIl.new ParkourBubble.Checkpoint((Vector)lllIIlIIlIIIlI, 0.9));
                });
            }

            public ZigZagBubble() {
                ZigZagBubble lllIIlIlIllIll;
                lllIIlIlIllIll.n = Utils.NombreEntre(lIIIllIll[0], lIIIllIll[1]);
            }

            @Override
            public double getMultiplier() {
                ZigZagBubble lllIIlIIlllIlI;
                return lIIIllIll[0] * lllIIlIIlllIlI.n + lIIIllIll[4];
            }

            static {
                ZigZagBubble.lIIIIIlIIIl();
            }

            private static void lIIIIIlIIIl() {
                lIIIllIll = new int[5];
                ZigZagBubble.lIIIllIll[0] = "   ".length();
                ZigZagBubble.lIIIllIll[1] = 95 ^ 81 ^ (167 ^ 172);
                ZigZagBubble.lIIIllIll[2] = (87 ^ 111) & ~(19 ^ 43);
                ZigZagBubble.lIIIllIll[3] = "  ".length();
                ZigZagBubble.lIIIllIll[4] = " ".length();
            }

            private static boolean lIIIIIlIlII(Object object, Object object2) {
                return object == object2;
            }
        }

        public class SlimeJumpBubble
        extends ParkourBubble {
            private static final /* synthetic */ int[] lllIIIII;

            static {
                SlimeJumpBubble.llIlIlIlll();
            }

            private static boolean llIlIllIII(int n, int n2) {
                return n < n2;
            }

            @Override
            public double getMultiplier() {
                return 1.5;
            }

            private static void llIlIlIlll() {
                lllIIIII = new int[7];
                SlimeJumpBubble.lllIIIII[0] = "  ".length();
                SlimeJumpBubble.lllIIIII[1] = (90 ^ 40 ^ (162 ^ 155)) & (57 + 163 - 104 + 81 ^ 31 + 63 - -12 + 36 ^ -" ".length());
                SlimeJumpBubble.lllIIIII[2] = -(0 + 156 - 20 + 25 ^ 125 + 49 - 63 + 62);
                SlimeJumpBubble.lllIIIII[3] = "   ".length();
                SlimeJumpBubble.lllIIIII[4] = " ".length();
                SlimeJumpBubble.lllIIIII[5] = 138 ^ 143;
                SlimeJumpBubble.lllIIIII[6] = 94 + 34 - -4 + 4 ^ 77 + 52 - 29 + 40;
            }

            @Override
            public Vector getRandomBubbleSpacing() {
                SlimeJumpBubble lIIIlIlIIIllIll;
                return lIIIlIlIIIllIll.Parkour.this.getForward().multiply(lllIIIII[0]).add(new Vector(lllIIIII[1], lllIIIII[2], lllIIIII[1]));
            }

            @Override
            public void generate() {
                SlimeJumpBubble lIIIlIlIIIlIIIl;
                int lIIIlIlIIIlIlII = lllIIIII[1];
                while (SlimeJumpBubble.llIlIllIII(lIIIlIlIIIlIlII, lllIIIII[3])) {
                    int lIIIlIlIIIlIlIl = lllIIIII[1];
                    while (SlimeJumpBubble.llIlIllIII(lIIIlIlIIIlIlIl, lllIIIII[3])) {
                        "".length();
                        lIIIlIlIIIlIIIl.blocks.add(lIIIlIlIIIlIIIl.Parkour.this.getRight().multiply(lIIIlIlIIIlIlII - lllIIIII[4]).add(lIIIlIlIIIlIIIl.Parkour.this.getForward().multiply(lIIIlIlIIIlIlIl)));
                        "".length();
                        lIIIlIlIIIlIIIl.materials.add(Material.SLIME_BLOCK);
                        ++lIIIlIlIIIlIlIl;
                        "".length();
                        if ("  ".length() == "  ".length()) continue;
                        return;
                    }
                    ++lIIIlIlIIIlIlII;
                    "".length();
                    if ("  ".length() == "  ".length()) continue;
                    return;
                }
                "".length();
                lIIIlIlIIIlIIIl.checkpoints.add(lIIIlIlIIIlIIIl.new ParkourBubble.Checkpoint(lIIIlIlIIIlIIIl.Parkour.this.getForward(), 2.12));
                Vector lIIIlIlIIIlIIII = lIIIlIlIIIlIIIl.Parkour.this.getForward().multiply(lllIIIII[5]).add(lIIIlIlIIIlIIIl.Parkour.this.getUp().multiply(lllIIIII[6]));
                int lIIIlIlIIIlIIlI = lllIIIII[1];
                while (SlimeJumpBubble.llIlIllIII(lIIIlIlIIIlIIlI, lllIIIII[3])) {
                    int lIIIlIlIIIlIIll = lllIIIII[1];
                    while (SlimeJumpBubble.llIlIllIII(lIIIlIlIIIlIIll, lllIIIII[0])) {
                        "".length();
                        lIIIlIlIIIlIIIl.blocks.add(lIIIlIlIIIlIIIl.Parkour.this.getRight().multiply(lIIIlIlIIIlIIlI - lllIIIII[4]).add(lIIIlIlIIIlIIIl.Parkour.this.getForward().multiply(lIIIlIlIIIlIIll)).add(lIIIlIlIIIlIIII));
                        "".length();
                        lIIIlIlIIIlIIIl.materials.add(Material.QUARTZ_BLOCK);
                        ++lIIIlIlIIIlIIll;
                        "".length();
                        if (((143 ^ 183) & ~(88 ^ 96)) <= ((15 ^ 1) & ~(111 ^ 97))) continue;
                        return;
                    }
                    ++lIIIlIlIIIlIIlI;
                    "".length();
                    if (-" ".length() <= 0) continue;
                    return;
                }
                "".length();
                lIIIlIlIIIlIIIl.checkpoints.add(lIIIlIlIIIlIIIl.new ParkourBubble.Checkpoint(lIIIlIlIIIlIIII.clone().add(lIIIlIlIIIlIIIl.Parkour.this.getForward()), 1.8));
            }

            public SlimeJumpBubble() {
                SlimeJumpBubble lIIIlIlIIIllllI;
            }
        }

        public class SineWaveBubble
        extends ParkourBubble {
            /* synthetic */ int n;
            /* synthetic */ int r;
            private static final /* synthetic */ int[] lIIIlllIl;

            private static void lIIIIlIIlIl() {
                lIIIlllIl = new int[5];
                SineWaveBubble.lIIIlllIl[0] = " ".length();
                SineWaveBubble.lIIIlllIl[1] = 87 ^ 83;
                SineWaveBubble.lIIIlllIl[2] = "   ".length();
                SineWaveBubble.lIIIlllIl[3] = 45 ^ 36;
                SineWaveBubble.lIIIlllIl[4] = "  ".length();
            }

            public SineWaveBubble() {
                SineWaveBubble lllIIlIIIIIIll;
                lllIIlIIIIIIll.n = Utils.NombreEntre(lIIIlllIl[0], lIIIlllIl[1]);
                lllIIlIIIIIIll.r = Utils.NombreEntre(lIIIlllIl[2], lIIIlllIl[3]);
            }

            @Override
            public void generate() {
                SineWaveBubble lllIIIllllIllI;
                double lllIIIllllIlll = 0.0;
                while (SineWaveBubble.lIIIIlIlIIl(SineWaveBubble.lIIIIlIIllI(lllIIIllllIlll, lIIIlllIl[4] * lllIIIllllIllI.r * lllIIIllllIllI.n))) {
                    double lllIIIlllllIlI = lllIIIllllIlll;
                    double lllIIIlllllIIl = Math.sin(lllIIIllllIlll * 3.141592653589793 / (double)(lIIIlllIl[4] * lllIIIllllIllI.r)) * (double)lllIIIllllIllI.r;
                    Vector lllIIIlllllIII = lllIIIllllIllI.Parkour.this.getForward().multiply(lllIIIlllllIlI).add(lllIIIllllIllI.Parkour.this.getLeft().multiply(lllIIIlllllIIl));
                    "".length();
                    lllIIIllllIllI.blocks.add(lllIIIlllllIII);
                    "".length();
                    lllIIIllllIllI.materials.add(Material.QUARTZ_BLOCK);
                    if (SineWaveBubble.lIIIIlIlIlI(SineWaveBubble.lIIIIlIIlll(lllIIIllllIlll % (double)lllIIIllllIllI.r, 0.0))) {
                        "".length();
                        lllIIIllllIllI.checkpoints.add(lllIIIllllIllI.new ParkourBubble.Checkpoint(lllIIIlllllIII));
                    }
                    lllIIIllllIlll += 1.0;
                    "".length();
                    if (null == null) continue;
                    return;
                }
            }

            private static boolean lIIIIlIlIlI(int n) {
                return n == 0;
            }

            static {
                SineWaveBubble.lIIIIlIIlIl();
            }

            private static boolean lIIIIlIlIIl(int n) {
                return n <= 0;
            }

            @Override
            public double getMultiplier() {
                SineWaveBubble lllIIIlllIllll;
                return 3.141592653589793 * (double)lllIIIlllIllll.r * (double)lllIIIlllIllll.n / 2.0;
            }

            private static int lIIIIlIIlll(double d, double d2) {
                return (int)(d DCMPL d2);
            }

            private static int lIIIIlIIllI(double d, double d2) {
                return (int)(d DCMPG d2);
            }
        }

    }

}

