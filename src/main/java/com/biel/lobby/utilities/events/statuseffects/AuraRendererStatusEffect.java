/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.utilities.events.statuseffects;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class AuraRendererStatusEffect
extends StatusEffect {
    private static final /* synthetic */ String[] lIIlllIlI;
    private static final /* synthetic */ int[] lIIllllIl;
    /* synthetic */ ArrayList<Item> hItems;

    public static ArrayList<Location> getLocationsCircle(Location llIIllIlllIIIl, double llIIllIllIlIII, int llIIllIllIIlll, double llIIllIllIllII) {
        ArrayList<Location> llIIllIllIlIll = new ArrayList<Location>();
        World llIIllIllIlIlI = llIIllIlllIIIl.getWorld();
        int llIIllIlllIIll = lIIllllIl[0];
        while (AuraRendererStatusEffect.lIIIllllIII(llIIllIlllIIll, llIIllIllIIlll)) {
            double llIIllIlllIllI = 6.283185307179586 / (double)llIIllIllIIlll * (double)llIIllIlllIIll + llIIllIllIllII;
            Location llIIllIlllIlII = llIIllIlllIIIl.clone().add(new Location(llIIllIllIlIlI, Math.cos(llIIllIlllIllI) * llIIllIllIlIII, 0.0, Math.sin(llIIllIlllIllI) * llIIllIllIlIII));
            "".length();
            llIIllIllIlIll.add(llIIllIlllIlII);
            ++llIIllIlllIIll;
            "".length();
            if ("  ".length() != 0) continue;
            return null;
        }
        return llIIllIllIlIll;
    }

    private void reallocateItems(boolean llIIllllIlIlll) {
        if (AuraRendererStatusEffect.lIIIlllIlII((int)llIIllllIlIlll)) {
            AuraRendererStatusEffect llIIllllIllIIl;
            llIIllllIllIIl.despawnItems();
            Iterator<AuraInfo> llIIllllIlIIIl = llIIllllIllIIl.getAurasa().iterator();
            while (AuraRendererStatusEffect.lIIIlllIlII((int)llIIllllIlIIIl.hasNext())) {
                AuraInfo llIIllllIllIlI = llIIllllIlIIIl.next();
                llIIllllIllIIl.spawnItems(llIIllllIllIlI);
                "".length();
                if ("  ".length() > -" ".length()) continue;
                return;
            }
        }
    }

    static {
        AuraRendererStatusEffect.lIIIlllIIll();
        AuraRendererStatusEffect.lIIIlllIIII();
    }

    private void spawnItems(AuraInfo llIIllllllIlIl) {
        AuraRendererStatusEffect llIIllllllIllI;
        Iterator<AuraInfo> llIIllllllIlII = llIIllllllIllI.getAurasa().iterator();
        while (AuraRendererStatusEffect.lIIIlllIlII((int)llIIllllllIlII.hasNext())) {
            AuraInfo llIIlllllllIIl = llIIllllllIlII.next();
            llIIllllllIllI.getLocations(llIIlllllllIIl).stream().forEach(llIIllIIllIlIl -> {
                AuraRendererStatusEffect llIIllIIllIlll;
                Item llIIllIIllIlII = llIIllIIllIlll.getWorld().dropItem(llIIllIIllIlIl, llIIllllllIlIl.getItemStack());
                llIIllIIllIlII.setPickupDelay(lIIllllIl[2]);
                "".length();
                llIIllIIllIlll.hItems.add(llIIllIIllIlII);
            });
            "".length();
            if (-"   ".length() < 0) continue;
            return;
        }
    }

    private static void lIIIlllIIII() {
        lIIlllIlI = new String[lIIllllIl[1]];
        AuraRendererStatusEffect.lIIlllIlI[AuraRendererStatusEffect.lIIllllIl[0]] = AuraRendererStatusEffect.lIIIllIlIII("I8XX7jRiSaXZz8JVzy/09A==", "ShsJr");
    }

    protected void onPlayerMove(PlayerMoveEvent llIIllIlIlIIlI, Player llIIllIlIlIIIl) {
        AuraRendererStatusEffect llIIllIlIlIIll;
        super.onPlayerMove(llIIllIlIlIIlI, llIIllIlIlIIIl);
    }

    private void despawnItems() {
        AuraRendererStatusEffect llIIlllllIIlll;
        llIIlllllIIlll.hItems.stream().forEach(Entity::remove);
        llIIlllllIIlll.hItems.clear();
    }

    private double getDefaultRadius(AuraInfo llIIllllIIIlII) {
        AuraRendererStatusEffect llIIllllIIIlIl;
        int llIIllllIIIIll = llIIllllIIIlIl.getAurasa().indexOf(llIIllllIIIlII);
        int llIIllllIIIIlI = llIIllllIIIlIl.getAurasa().size();
        double llIIllllIIIIIl = 0.2;
        double llIIllllIIIIII = 1.2;
        double llIIlllIllllll = 0.5;
        double llIIlllIllllIl = 0.1;
        return llIIllllIIIIII;
    }

    private static void lIIIlllIIll() {
        lIIllllIl = new int[4];
        AuraRendererStatusEffect.lIIllllIl[0] = (34 ^ 71 ^ (31 ^ 73)) & (108 ^ 121 ^ (164 ^ 130) ^ -" ".length());
        AuraRendererStatusEffect.lIIllllIl[1] = " ".length();
        AuraRendererStatusEffect.lIIllllIl[2] = -1 & Integer.MAX_VALUE;
        AuraRendererStatusEffect.lIIllllIl[3] = "  ".length();
    }

    private static boolean lIIIlllIlll(int n) {
        return n > 0;
    }

    private static boolean lIIIllllIII(int n, int n2) {
        return n < n2;
    }

    @Override
    public String getName() {
        return lIIlllIlI[lIIllllIl[0]];
    }

    private static String lIIIllIlIII(String llIIllIIlIlIII, String llIIllIIlIIlll) {
        try {
            SecretKeySpec llIIllIIlIlIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIllIIlIIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIllIIlIlIlI = Cipher.getInstance("Blowfish");
            llIIllIIlIlIlI.init(lIIllllIl[3], llIIllIIlIlIll);
            return new String(llIIllIIlIlIlI.doFinal(Base64.getDecoder().decode(llIIllIIlIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllIIlIlIIl) {
            llIIllIIlIlIIl.printStackTrace();
            return null;
        }
    }

    private ArrayList<AuraInfo> getAurasa() {
        AuraRendererStatusEffect llIIlllIlIIIII;
        return llIIlllIlIIIII.getPlayerInfo().getAuras();
    }

    private ArrayList<Location> getLocations(AuraInfo llIIlllIIlIlIl) {
        AuraRendererStatusEffect llIIlllIIlIllI;
        double d;
        if (AuraRendererStatusEffect.lIIIlllIlll(AuraRendererStatusEffect.lIIIlllIllI(llIIlllIIlIlIl.getRadius(), 0.0))) {
            d = llIIlllIIlIlIl.getRadius();
            "".length();
            if ("   ".length() <= "  ".length()) {
                return null;
            }
        } else {
            d = llIIlllIIlIllI.getDefaultRadius(llIIlllIIlIlIl);
        }
        return AuraRendererStatusEffect.getLocationsCircle(llIIlllIIlIllI.getPlayer().getLocation(), d, llIIlllIIlIlIl.getN(), (double)llIIlllIIlIllI.getTicksLived() * 3.141592653589793 * 2.0 * (llIIlllIIlIlIl.getSpeed() / 7200.0));
    }

    private static int lIIIlllIllI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public void tick() {
        AuraRendererStatusEffect llIIllIlIllIII;
        super.tick();
        llIIllIlIllIII.reallocateItems(lIIllllIl[1]);
    }

    protected void onPlayerTeleport(PlayerTeleportEvent llIIllIlIIIllI, Player llIIllIIllllll, Location llIIllIIlllllI, Location llIIllIIllllIl, PlayerTeleportEvent.TeleportCause llIIllIlIIIIlI) {
        AuraRendererStatusEffect llIIllIlIIIlll;
        super.onPlayerTeleport(llIIllIlIIIllI, llIIllIIllllll, llIIllIIlllllI, llIIllIIllllIl, llIIllIlIIIIlI);
    }

    @Override
    public String getDescription() {
        return null;
    }

    public AuraRendererStatusEffect(Player llIlIIIIIIllll) {
        AuraRendererStatusEffect llIlIIIIIlIIII;
        super(llIlIIIIIIllll);
        llIlIIIIIlIIII.hItems = new ArrayList();
    }

    @Override
    public void clearExternals() {
        AuraRendererStatusEffect llIIllIlIllIlI;
        super.clearExternals();
        llIIllIlIllIlI.despawnItems();
    }

    private static boolean lIIIlllIlII(int n) {
        return n != 0;
    }
}

