/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.IllegalClassException
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.events.skills.types;

import com.biel.lobby.utilities.events.skills.types.ItemAttatchedModeSkill;
import org.apache.commons.lang.IllegalClassException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class ItemAttatchedStackModeSkill
extends ItemAttatchedModeSkill {
    @Override
    public void onModeSwitch(ItemAttatchedModeSkill.SkillMode llIlIIllIlIIIl) {
        ItemAttatchedStackModeSkill llIlIIllIlIIlI;
        super.onModeSwitch(llIlIIllIlIIIl);
        StackSkillMode llIlIIllIlIIll = (StackSkillMode)llIlIIllIlIIIl;
        llIlIIllIlIIlI.getTrayEffect().setMaxValue(llIlIIllIlIIll.getMaxStacks());
    }

    @Override
    public StackSkillMode getSelectedMode() {
        ItemAttatchedStackModeSkill llIlIIllIIlIll;
        return (StackSkillMode)super.getSelectedMode();
    }

    public void registerMode(StackSkillMode llIlIIllIllIIl) {
        ItemAttatchedStackModeSkill llIlIIllIllIlI;
        super.registerMode(llIlIIllIllIIl);
    }

    public ItemAttatchedStackModeSkill(Player llIlIIlllIIlII) {
        ItemAttatchedStackModeSkill llIlIIlllIIlIl;
        super(llIlIIlllIIlII);
    }

    @Override
    protected ItemAttatchedStackModeSkillTrayEffect getTrayEffect() {
        ItemAttatchedStackModeSkill llIlIIllIIllIl;
        return (ItemAttatchedStackModeSkillTrayEffect)super.getTrayEffect();
    }

    @Deprecated
    @Override
    public void registerMode(ItemAttatchedModeSkill.SkillMode llIlIIllIlllll) {
        throw new IllegalClassException(StackSkillMode.class, ItemAttatchedModeSkill.SkillMode.class);
    }

    @Override
    protected Class<? extends ItemAttatchedModeSkill.ItemAttatchedModeSkillTrayEffect> getTrayEffectClass() {
        return ItemAttatchedStackModeSkillTrayEffect.class;
    }

    public class ItemAttatchedStackModeSkillTrayEffect
    extends ItemAttatchedModeSkill.ItemAttatchedModeSkillTrayEffect {
        private static final /* synthetic */ int[] llllllIl;

        static {
            ItemAttatchedStackModeSkillTrayEffect.lllIllIlIl();
        }

        public ItemAttatchedStackModeSkillTrayEffect(Player llllllIlIllIlI) {
            ItemAttatchedStackModeSkillTrayEffect llllllIlIllIIl;
            super(llllllIlIllIlI);
        }

        @Override
        public void onMaxLose() {
            ItemAttatchedStackModeSkillTrayEffect llllllIlIlIIIl;
            llllllIlIlIIIl.ItemAttatchedStackModeSkill.this.getTrayEffect().setModalRemainingTicks(llllllIl[1]);
        }

        @Override
        public void onMaxUp() {
            ItemAttatchedStackModeSkillTrayEffect llllllIlIlIlIl;
            llllllIlIlIlIl.ItemAttatchedStackModeSkill.this.getTrayEffect().setModalRemainingTicks(llllllIl[0]);
        }

        private static void lllIllIlIl() {
            llllllIl = new int[2];
            ItemAttatchedStackModeSkillTrayEffect.llllllIl[0] = -" ".length() & (-1 & Integer.MAX_VALUE);
            ItemAttatchedStackModeSkillTrayEffect.llllllIl[1] = (85 ^ 11 ^ (61 ^ 118)) & (173 ^ 146 ^ (154 ^ 176) ^ -" ".length());
        }
    }

    public class StackSkillMode
    extends ItemAttatchedModeSkill.SkillMode {
        private /* synthetic */ int maxStacks;

        public int getMaxStacks() {
            StackSkillMode lllllIIlIIIlIlI;
            return lllllIIlIIIlIlI.maxStacks;
        }

        public StackSkillMode(int lllllIIlIIlIlIl, String lllllIIlIIlIlII, String lllllIIlIIlIIll, int lllllIIlIIlIIlI) {
            StackSkillMode lllllIIlIIlIlll;
            super(lllllIIlIIlIlIl, lllllIIlIIlIlII, lllllIIlIIlIIll);
            lllllIIlIIlIlll.maxStacks = lllllIIlIIlIIlI;
        }

        public void setMaxStacks(int lllllIIlIIIIIll) {
            lllllIIlIIIIlII.maxStacks = lllllIIlIIIIIll;
        }

        public StackSkillMode(int lllllIIlIlIlIIl, String lllllIIlIlIlIII, String lllllIIlIlIIlll, ChatColor lllllIIlIIlllll, int lllllIIlIIllllI) {
            StackSkillMode lllllIIlIlIlIll;
            super(lllllIIlIlIlIIl, lllllIIlIlIlIII, lllllIIlIlIIlll, lllllIIlIIlllll);
            lllllIIlIlIlIll.maxStacks = lllllIIlIIllllI;
        }
    }

}

