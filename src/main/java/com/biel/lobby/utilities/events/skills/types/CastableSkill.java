/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.ItemButton
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEvent
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEventHandler
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.utilities.events.skills.types;

import com.biel.BielAPI.Utils.ItemButton;
import com.biel.lobby.utilities.events.skills.types.CooldownSkill;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CastableSkill
extends CooldownSkill {
    public ItemButton getItemButton(Player llllIlIllIIlIll) {
        CastableSkill llllIlIllIIllll;
        ItemButton llllIlIllIIllIl = new ItemButton(llllIlIllIIllll.getItemStack(), llllIlIllIIlIll, llllIlIllIIlIIl -> {});
        return llllIlIllIIllIl;
    }

    public void cast() {
    }

    public CastableSkill(Player llllIlIllIlIlII) {
        CastableSkill llllIlIllIlIlll;
        super(llllIlIllIlIlII);
    }
}

