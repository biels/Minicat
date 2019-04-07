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

import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class AuraStatusEffect
extends StatusEffect {
    /* synthetic */ ArrayList<Item> hItems;
    private static final /* synthetic */ int[] lIlIII;
    private /* synthetic */ AuraInfo info;

    @Override
    public String getDescription() {
        return null;
    }

    public AuraInfo getInfo() {
        AuraStatusEffect llllIlIIIllIlll;
        return llllIlIIIllIlll.info;
    }

    private static void lllIIIl() {
        lIlIII = new int[3];
        AuraStatusEffect.lIlIII[0] = (16 + 20 - -118 + 11 ^ 28 + 84 - 60 + 116) & (36 + 106 - 46 + 45 ^ 7 + 66 - 35 + 90 ^ -" ".length());
        AuraStatusEffect.lIlIII[1] = " ".length();
        AuraStatusEffect.lIlIII[2] = -" ".length() & (-1 & Integer.MAX_VALUE);
    }

    private ArrayList<Location> getLocations() {
        AuraStatusEffect llllIlIIIIlllII;
        return AuraStatusEffect.getLocationsCircle(llllIlIIIIlllII.getPlayer().getLocation(), llllIlIIIIlllII.info.getRadius(), llllIlIIIIlllII.info.getN(), (double)llllIlIIIIlllII.getTicksLived() * 3.141592653589793 * 2.0 * llllIlIIIIlllII.info.getSpeed());
    }

    private void despawnItems() {
        AuraStatusEffect llllIlIIIlIlIll;
        llllIlIIIlIlIll.hItems.stream().forEach(Entity::remove);
        llllIlIIIlIlIll.hItems.clear();
    }

    public void setInfo(AuraInfo llllIlIIIllIIll) {
        llllIlIIIllIIlI.info = llllIlIIIllIIll;
    }

    private static boolean lllIIlI(int n) {
        return n != 0;
    }

    public static ArrayList<Location> getLocationsCircle(Location llllIlIIIIIlIIl, double llllIlIIIIIlIII, int llllIlIIIIIllIl, double llllIlIIIIIIllI) {
        ArrayList<Location> llllIlIIIIIlIll = new ArrayList<Location>();
        World llllIlIIIIIlIlI = llllIlIIIIIlIIl.getWorld();
        int llllIlIIIIlIIII = lIlIII[0];
        while (AuraStatusEffect.lllIlIl(llllIlIIIIlIIII, llllIlIIIIIllIl)) {
            double llllIlIIIIlIIlI = 6.283185307179586 / (double)llllIlIIIIIllIl * (double)llllIlIIIIlIIII + llllIlIIIIIIllI;
            Location llllIlIIIIlIIIl = llllIlIIIIIlIIl.clone().add(new Location(llllIlIIIIIlIlI, Math.cos(llllIlIIIIlIIlI) * llllIlIIIIIlIII, 0.0, Math.sin(llllIlIIIIlIIlI) * llllIlIIIIIlIII));
            "".length();
            llllIlIIIIIlIll.add(llllIlIIIIlIIIl);
            ++llllIlIIIIlIIII;
            "".length();
            if ("   ".length() == "   ".length()) continue;
            return null;
        }
        return llllIlIIIIIlIll;
    }

    public AuraStatusEffect(Player llllIlIIlIIIIII, AuraInfo llllIlIIIllllII) {
        AuraStatusEffect llllIlIIIlllllI;
        super(llllIlIIlIIIIII);
        llllIlIIIlllllI.hItems = new ArrayList();
        llllIlIIIlllllI.info = llllIlIIIllllII;
    }

    protected void onPlayerMove(PlayerMoveEvent llllIIlllllIllI, Player llllIIlllllIIlI) {
        AuraStatusEffect llllIIlllllIlII;
        super.onPlayerMove(llllIIlllllIllI, llllIIlllllIIlI);
        llllIIlllllIlII.reallocateItems(lIlIII[1]);
    }

    private void reallocateItems(boolean llllIlIIIlIIIIl) {
        AuraStatusEffect llllIlIIIlIIlII;
        if (AuraStatusEffect.lllIIlI((int)llllIlIIIlIIIIl)) {
            llllIlIIIlIIlII.despawnItems();
            llllIlIIIlIIlII.spawnItems();
            "".length();
            if (null != null) {
                return;
            }
        } else {
            if (AuraStatusEffect.lllIIll(llllIlIIIlIIlII.hItems.size(), llllIlIIIlIIlII.info.getN())) {
                llllIlIIIlIIlII.spawnItems();
                return;
            }
            ArrayList<Location> llllIlIIIlIIlIl = llllIlIIIlIIlII.getLocations();
            int llllIlIIIlIIllI = lIlIII[0];
            while (AuraStatusEffect.lllIlIl(llllIlIIIlIIllI, llllIlIIIlIIlII.info.getN())) {
                "".length();
                llllIlIIIlIIlII.hItems.get(llllIlIIIlIIllI).teleport(llllIlIIIlIIlIl.get(llllIlIIIlIIllI));
                ++llllIlIIIlIIllI;
                "".length();
                if (-(179 ^ 183) <= 0) continue;
                return;
            }
        }
    }

    @Override
    public void clearExternals() {
        AuraStatusEffect llllIIlllllllll;
        super.clearExternals();
        llllIIlllllllll.despawnItems();
    }

    static {
        AuraStatusEffect.lllIIIl();
    }

    private void spawnItems() {
        AuraStatusEffect llllIlIIIlIlllI;
        llllIlIIIlIlllI.getLocations().stream().forEach(llllIIlllIllIII -> {
            AuraStatusEffect llllIIlllIllIIl;
            Item llllIIlllIllIlI = llllIIlllIllIIl.getWorld().dropItem(llllIIlllIllIII, llllIIlllIllIIl.info.getItemStack());
            llllIIlllIllIlI.setPickupDelay(lIlIII[2]);
            "".length();
            llllIIlllIllIIl.hItems.add(llllIIlllIllIlI);
        });
    }

    @Override
    public void tick() {
        AuraStatusEffect llllIIlllllllII;
        super.tick();
        llllIIlllllllII.reallocateItems(lIlIII[0]);
    }

    private static boolean lllIIll(int n, int n2) {
        return n != n2;
    }

    private static boolean lllIlIl(int n, int n2) {
        return n < n2;
    }

    @Override
    public String getName() {
        return null;
    }

    protected void onPlayerTeleport(PlayerTeleportEvent llllIIllllIIlII, Player llllIIllllIIIll, Location llllIIllllIIIlI, Location llllIIllllIIlll, PlayerTeleportEvent.TeleportCause llllIIllllIIllI) {
        AuraStatusEffect llllIIllllIlIll;
        super.onPlayerTeleport(llllIIllllIIlII, llllIIllllIIIll, llllIIllllIIIlI, llllIIllllIIlll, llllIIllllIIllI);
        llllIIllllIlIll.reallocateItems(lIlIII[1]);
    }
}

