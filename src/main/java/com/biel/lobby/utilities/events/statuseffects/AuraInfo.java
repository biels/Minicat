/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.utilities.events.statuseffects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AuraInfo {
    private /* synthetic */ ItemStack itemStack;
    private /* synthetic */ double speed;
    private /* synthetic */ int n;
    private static final /* synthetic */ int[] llllII;
    private /* synthetic */ String name;
    private /* synthetic */ double radius;

    public int hashCode() {
        AuraInfo lllIlIlIIIIIlll;
        return lllIlIlIIIIIlll.name.hashCode();
    }

    public ItemStack getItemStack() {
        AuraInfo lllIlIlIIlIlIIl;
        return lllIlIlIIlIlIIl.itemStack;
    }

    public AuraInfo(String lllIlIlIlllIllI, double lllIlIlIlllIlII, int lllIlIlIllllIll, double lllIlIlIlllIIIl, ItemStack lllIlIlIllIllll) {
        AuraInfo lllIlIlIlllIlll;
        lllIlIlIlllIlll.radius = 0.0;
        lllIlIlIlllIlll.n = llllII[0];
        lllIlIlIlllIlll.speed = 30.0;
        lllIlIlIlllIlll.itemStack = new ItemStack(Material.BARRIER, llllII[1]);
        lllIlIlIlllIlll.name = lllIlIlIlllIllI;
        lllIlIlIlllIlll.radius = lllIlIlIlllIlII;
        lllIlIlIlllIlll.n = lllIlIlIllllIll;
        lllIlIlIlllIlll.speed = lllIlIlIlllIIIl;
        lllIlIlIlllIlll.itemStack = lllIlIlIllIllll;
    }

    public Material getMaterial() {
        AuraInfo lllIlIlIIIlllIl;
        return lllIlIlIIIlllIl.itemStack.getType();
    }

    public boolean equals(Object lllIlIlIIIIlIlI) {
        AuraInfo lllIlIlIIIIlIll;
        return lllIlIlIIIIlIll.name.equalsIgnoreCase(lllIlIlIIIIlIlI.toString());
    }

    static {
        AuraInfo.llIIIlIl();
    }

    public void setName(String lllIlIlIlIllIIl) {
        lllIlIlIlIlllIl.name = lllIlIlIlIllIIl;
    }

    public void setSpeed(double lllIlIlIIlIlIll) {
        lllIlIlIIlIlllI.speed = lllIlIlIIlIlIll;
    }

    public AuraInfo(String lllIlIllIIIlllI, int lllIlIllIIlIIlI, double lllIlIllIIIllII, ItemStack lllIlIllIIIlIll) {
        AuraInfo lllIlIllIIIllll;
        lllIlIllIIIllll.radius = 0.0;
        lllIlIllIIIllll.n = llllII[0];
        lllIlIllIIIllll.speed = 30.0;
        lllIlIllIIIllll.itemStack = new ItemStack(Material.BARRIER, llllII[1]);
        lllIlIllIIIllll.name = lllIlIllIIIlllI;
        lllIlIllIIIllll.n = lllIlIllIIlIIlI;
        lllIlIllIIIllll.speed = lllIlIllIIIllII;
        lllIlIllIIIllll.itemStack = lllIlIllIIIlIll;
    }

    public double getSpeed() {
        AuraInfo lllIlIlIIlllIll;
        return lllIlIlIIlllIll.speed;
    }

    public String getName() {
        AuraInfo lllIlIlIllIIIlI;
        return lllIlIlIllIIIlI.name;
    }

    public AuraInfo(String lllIlIllIlIIIlI, int lllIlIllIlIIlIl, ItemStack lllIlIllIlIIIII) {
        AuraInfo lllIlIllIlIIlll;
        lllIlIllIlIIlll.radius = 0.0;
        lllIlIllIlIIlll.n = llllII[0];
        lllIlIllIlIIlll.speed = 30.0;
        lllIlIllIlIIlll.itemStack = new ItemStack(Material.BARRIER, llllII[1]);
        lllIlIllIlIIlll.name = lllIlIllIlIIIlI;
        lllIlIllIlIIlll.n = lllIlIllIlIIlIl;
        lllIlIllIlIIlll.itemStack = lllIlIllIlIIIII;
    }

    public void setN(int lllIlIlIlIIIIll) {
        lllIlIlIlIIIlII.n = lllIlIlIlIIIIll;
    }

    public void setRadius(double lllIlIlIlIIlIlI) {
        lllIlIlIlIIllIl.radius = lllIlIlIlIIlIlI;
    }

    public double getRadius() {
        AuraInfo lllIlIlIlIlIlll;
        return lllIlIlIlIlIlll.radius;
    }

    public void setItemStack(ItemStack lllIlIlIIlIIIlI) {
        lllIlIlIIlIIIll.itemStack = lllIlIlIIlIIIlI;
    }

    public int getN() {
        AuraInfo lllIlIlIlIIlIII;
        return lllIlIlIlIIlIII.n;
    }

    private static void llIIIlIl() {
        llllII = new int[2];
        AuraInfo.llllII[0] = 171 + 92 - 258 + 184 ^ 98 + 31 - 79 + 134;
        AuraInfo.llllII[1] = " ".length();
    }
}

