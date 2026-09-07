package com.biel.lobby.guide;

import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes.ContenidorJoc;

/**
 * The lobby's "Wiki <game>" signs, from the 2013 wiki that no longer answers: a right
 * click opens that game's {@link GameGuide} book instead. A sign counts when a line says
 * wiki or guia and the rest names a registered game.
 */
public final class GuideSigns implements Listener {
	@EventHandler
	public void onSignClick(PlayerInteractEvent evt) {
		if (evt.getAction() != Action.RIGHT_CLICK_BLOCK || evt.getHand() != EquipmentSlot.HAND) return;
		if (evt.getClickedBlock() == null || evt.getClickedBlock().getWorld() != Com.getLobbyWorld()) return;
		if (!(evt.getClickedBlock().getState() instanceof Sign sign)) return;
		String text = String.join(" ", sign.getSide(Side.FRONT).getLines());
		String lower = text.toLowerCase();
		if (!lower.contains("wiki") && !lower.contains("guia")) return;
		ContenidorJoc game = GameGuide.findGame(text.replaceAll("(?i)wiki|guia", " "));
		if (game == null) return;
		evt.setCancelled(true);
		GameGuide.of(game.getNom()).open(evt.getPlayer());
	}
}
