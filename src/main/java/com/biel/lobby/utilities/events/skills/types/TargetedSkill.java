/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.events.skills.types;

import com.biel.lobby.utilities.events.skills.types.CastableSkill;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class TargetedSkill
extends CastableSkill {
    public TargetedSkill(Player lllllIIlIllIlII) {
        TargetedSkill lllllIIlIllIlll;
        super(lllllIIlIllIlII);
    }

    protected LivingEntity getTargets() {
        return null;
    }

    public static final class TargetingMode
    extends Enum<TargetingMode> {
        private static final /* synthetic */ TargetingMode[] $VALUES;
        private static final /* synthetic */ int[] lIIIIIlll;

        private static void llllIlIIII() {
            lIIIIIlll = new int[1];
            TargetingMode.lIIIIIlll[0] = (157 ^ 140 ^ (137 ^ 193)) & (66 + 19 - -62 + 58 ^ 30 + 54 - 66 + 130 ^ -" ".length());
        }

        public static TargetingMode[] values() {
            return (TargetingMode[])$VALUES.clone();
        }

        static {
            TargetingMode.llllIlIIII();
            $VALUES = new TargetingMode[lIIIIIlll[0]];
        }

        private TargetingMode() {
            TargetingMode llllIllIllIlll;
        }

        public static TargetingMode valueOf(String llllIllIllllII) {
            return Enum.valueOf(TargetingMode.class, llllIllIllllII);
        }
    }

}

