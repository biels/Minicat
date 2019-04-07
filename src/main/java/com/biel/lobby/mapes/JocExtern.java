/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes;

import com.biel.lobby.mapes.Joc;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class JocExtern
extends Joc {
    @Override
    protected void teletransportarTothom() {
    }

    @Override
    protected void customJocFinalitzat() {
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIIlllIIIlIIllI) {
        return new ArrayList<ItemStack>();
    }

    @Override
    protected void customJocIniciat() {
    }

    @Override
    protected void setCustomGameRules() {
    }

    public JocExtern() {
        JocExtern lIIlllIIIlIlIII;
        lIIlllIIIlIlIII.JocIniciat();
    }
}

