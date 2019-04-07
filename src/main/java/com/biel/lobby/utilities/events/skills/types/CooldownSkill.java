/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.events.skills.types;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.Skill;
import com.biel.lobby.utilities.events.statuseffects.CDStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import org.bukkit.entity.Player;

public abstract class CooldownSkill
extends Skill {
    private /* synthetic */ int cdRemainingTicks;
    private static final /* synthetic */ int[] lIlIIIIlI;

    private static boolean lIIlIIIlIIl(int n) {
        return n > 0;
    }

    public boolean usingAssociatedCDEffect() {
        return lIlIIIIlI[0];
    }

    protected void applyAssociatedCDEffect() {
        CooldownSkill llIIlIlllIlIIl;
        Joc.PlayerInfo llIIlIlllIlIlI = llIIlIlllIlIIl.getPlayerInfo(llIIlIlllIlIIl.getPlayer());
        if (CooldownSkill.lIIlIIIIlll((int)llIIlIlllIlIlI.hasStatusEffect(llIIlIlllIlIIl.getName()))) {
            llIIlIlllIlIlI.addStatusEffect(new CDStatusEffect(llIIlIlllIlIIl.getPlayer(), llIIlIlllIlIIl));
        }
    }

    private static boolean lIIlIIIlIll(int n) {
        return n < 0;
    }

    public void onCDUse() {
    }

    static {
        CooldownSkill.lIIlIIIIIll();
    }

    private static boolean lIIlIIIIlll(int n) {
        return n == 0;
    }

    public void resetCooldown() {
        CooldownSkill llIIllIIIlIIIl;
        llIIllIIIlIIIl.setCdRemainingTicks((int)Math.round(llIIllIIIlIIIl.getCDSeconds() * 20.0));
        if (CooldownSkill.lIIlIIIIllI((int)llIIllIIIlIIIl.usingAssociatedCDEffect())) {
            "".length();
            llIIllIIIlIIIl.getAssociatedCDEffect();
        }
        llIIllIIIlIIIl.onCDUse();
    }

    public int getCDRemainigTicks() {
        CooldownSkill llIIllIIIIIlIl;
        return llIIllIIIIIlIl.cdRemainingTicks;
    }

    public abstract double getCDSeconds();

    private static boolean lIIlIIIIllI(int n) {
        return n != 0;
    }

    public boolean isCDAvaliable() {
        boolean bl;
        CooldownSkill llIIllIIIIIIII;
        if (CooldownSkill.lIIlIIIIlll(llIIllIIIIIIII.cdRemainingTicks)) {
            bl = lIlIIIIlI[1];
            "".length();
            if (((83 ^ 26) & ~(247 ^ 190)) >= (40 ^ 44)) {
                return (boolean)((45 ^ 53) & ~(3 ^ 27));
            }
        } else {
            bl = lIlIIIIlI[0];
        }
        return bl;
    }

    protected void setCdRemainingTicks(int llIIlIlllllIIl) {
        if (CooldownSkill.lIIlIIIIlll(llIIlIlllllIIl)) {
            CooldownSkill llIIlIllllllII;
            llIIlIllllllII.onCDAvaliable();
        }
        llIIlIllllllII.cdRemainingTicks = llIIlIlllllIIl;
    }

    @Override
    public void tick() {
        CooldownSkill llIIlIllIlllIl;
        super.tick();
        llIIlIllIlllIl.doCDTick();
    }

    protected boolean tryUseCD() {
        CooldownSkill llIIlIllllIIIl;
        boolean llIIlIllllIIlI = llIIlIllllIIIl.isCDAvaliable();
        if (CooldownSkill.lIIlIIIIllI((int)llIIlIllllIIlI)) {
            llIIlIllllIIIl.resetCooldown();
        }
        return llIIlIllllIIlI;
    }

    private static boolean lIIlIIIIlII(Object object) {
        return object != null;
    }

    private void doCDTick() {
        CooldownSkill llIIlIllllIlll;
        if (CooldownSkill.lIIlIIIIlll(llIIlIllllIlll.cdRemainingTicks)) {
            return;
        }
        if (CooldownSkill.lIIlIIIlIIl(llIIlIllllIlll.cdRemainingTicks)) {
            llIIlIllllIlll.setCdRemainingTicks(llIIlIllllIlll.cdRemainingTicks - lIlIIIIlI[1]);
        }
        if (CooldownSkill.lIIlIIIlIll(llIIlIllllIlll.cdRemainingTicks)) {
            llIIlIllllIlll.setCdRemainingTicks(lIlIIIIlI[0]);
        }
    }

    protected CDStatusEffect getAssociatedCDEffect() {
        CooldownSkill llIIlIlllIIIlI;
        if (CooldownSkill.lIIlIIIllII((Object)llIIlIlllIIIlI.getPlayer())) {
            return null;
        }
        Joc.PlayerInfo llIIlIlllIIIll = llIIlIlllIIIlI.getPlayerInfo(llIIlIlllIIIlI.getPlayer());
        llIIlIlllIIIlI.applyAssociatedCDEffect();
        return (CDStatusEffect)((Object)llIIlIlllIIIll.getStatusEffect(llIIlIlllIIIlI.getName()));
    }

    public double getCDRemainigSeconds() {
        CooldownSkill llIIllIIIIIIlI;
        return (double)llIIllIIIIIIlI.cdRemainingTicks / (double)llIIllIIIIIIlI.getTickSpacing();
    }

    public CooldownSkill(Player llIIllIIIlIlll) {
        CooldownSkill llIIllIIIlIllI;
        super(llIIllIIIlIlll);
        llIIllIIIlIllI.cdRemainingTicks = lIlIIIIlI[0];
        if (CooldownSkill.lIIlIIIIlII((Object)llIIllIIIlIlll)) {
            llIIllIIIlIllI.resetCooldown();
        }
    }

    private static boolean lIIlIIIllII(Object object) {
        return object == null;
    }

    private static void lIIlIIIIIll() {
        lIlIIIIlI = new int[2];
        CooldownSkill.lIlIIIIlI[0] = (133 + 151 - 124 + 31 ^ 16 + 51 - -102 + 5) & (110 ^ 98 ^ (176 ^ 173) ^ -" ".length());
        CooldownSkill.lIlIIIIlI[1] = " ".length();
    }

    public void skipCooldown(double llIIllIIIIlIII) {
        CooldownSkill llIIllIIIIlIll;
        llIIllIIIIlIll.setCdRemainingTicks((int)((double)llIIllIIIIlIll.cdRemainingTicks * llIIllIIIIlIII));
    }

    public void skipCooldown() {
        CooldownSkill llIIllIIIIlllI;
        llIIllIIIIlllI.setCdRemainingTicks(lIlIIIIlI[0]);
    }

    public void onCDAvaliable() {
    }
}

