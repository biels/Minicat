/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_12_R1.EntityPlayer
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CBUtils {
    public static int getPing(Player lIIIIllIIlllIIl) {
        return ((CraftPlayer)lIIIIllIIlllIIl).getHandle().ping;
    }

    public CBUtils() {
        CBUtils lIIIIllIIllllIl;
    }
}

