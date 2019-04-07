/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.events.statuseffects;

import com.biel.lobby.utilities.events.skills.types.CooldownSkill;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import org.bukkit.entity.Player;

public class CDStatusEffect
extends StatusEffect {
    private static final /* synthetic */ int[] lIllIIIIl;
    /* synthetic */ CooldownSkill s;

    private static boolean lIIllIllIlI(int n) {
        return n != 0;
    }

    private static void lIIllIllIIl() {
        lIllIIIIl = new int[1];
        CDStatusEffect.lIllIIIIl[0] = -" ".length();
    }

    @Override
    public String getDescription() {
        CDStatusEffect lIllIIIlIlIIlI;
        return lIllIIIlIlIIlI.s.getDescription();
    }

    static {
        CDStatusEffect.lIIllIllIIl();
    }

    @Override
    public String getName() {
        CDStatusEffect lIllIIIlIlIlIl;
        return lIllIIIlIlIlIl.s.getName();
    }

    public CDStatusEffect(Player lIllIIIlIlllII, CooldownSkill lIllIIIlIllIII) {
        CDStatusEffect lIllIIIlIllIlI;
        super(lIllIIIlIlllII);
        lIllIIIlIllIlI.s = lIllIIIlIllIII;
    }

    @Override
    public void tick() {
        CDStatusEffect lIllIIIlIIllll;
        super.tick();
        if (CDStatusEffect.lIIllIllIlI((int)lIllIIIlIIllll.s.isCDAvaliable())) {
            lIllIIIlIIllll.setRemainingTicks(lIllIIIIl[0]);
            lIllIIIlIIllll.setType(StatusEffect.StatusEffectType.BUFF);
            "".length();
            if (" ".length() <= -" ".length()) {
                return;
            }
        } else {
            lIllIIIlIIllll.setType(StatusEffect.StatusEffectType.SKILL_TRAY);
            lIllIIIlIIllll.setRemainingTicks(lIllIIIlIIllll.s.getCDRemainigTicks());
        }
    }
}

