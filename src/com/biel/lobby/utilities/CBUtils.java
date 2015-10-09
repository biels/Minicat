package com.biel.lobby.utilities;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CBUtils {
	// CraftBukkit needed //
	public static int getPing(Player player) {
		return ((CraftPlayer) player).getHandle().ping;
	}
}


