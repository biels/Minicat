package com.biel.lobby;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.biel.lobby.GestorMapes.ContenidorJoc;
import com.biel.lobby.GestorMapes.DevelopmentState;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable.MapMode;
import com.biel.lobby.utilities.PaperMessages;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;

/**
 * The written book that tells players what each game plays for. It is the one
 * place the rating weights are explained; the teleporter only lists games.
 */
public final class RankingBook {
	/** Entries per page, leaving room for names that wrap and for per-map notes. */
	private static final int GAMES_PER_PAGE = 8;
	private static final String INK = ChatColor.BLACK.toString();
	private static final String FAINT = ChatColor.DARK_GRAY.toString();
	private static final String WEIGHT = ChatColor.DARK_GREEN.toString();

	private RankingBook() {
	}

	/**
	 * Shows the book. The menu the player clicked is closed first and the book opens
	 * on the next tick, because a container close arriving after the book would
	 * shut the book instead.
	 */
	public static void open(Player player) {
		Book book = build();
		player.closeInventory();
		Bukkit.getScheduler().runTask(Com.getPlugin(), () -> {
			if (player.isOnline()) player.openBook(book);
		});
	}

	static Book build() {
		List<Component> pages = new ArrayList<>();
		pages.add(page(introduction()));
		pages.add(page(rules()));
		List<GameWeight> weights = gameWeights();
		for (int from = 0; from < weights.size(); from += GAMES_PER_PAGE) {
			pages.add(page(gamesPage(weights.subList(from, Math.min(weights.size(), from + GAMES_PER_PAGE)), from == 0)));
		}
		return Book.book(Component.text("Rànquing Minicat"), Component.text("Minicat"), pages);
	}

	private static List<String> introduction() {
		List<String> lines = new ArrayList<>();
		lines.add(ChatColor.BOLD + "" + INK + "Rànquing Minicat" + ChatColor.RESET);
		lines.add(INK + "Cada partida mou l'elo amb un pes " + WEIGHT + "K" + INK + ". Com més acabat és el joc, més pes té:");
		lines.add(ChatColor.DARK_GREEN + "Release" + INK + " K " + DevelopmentState.Release.getDefaultEloK());
		lines.add(ChatColor.GOLD + "Beta" + INK + " K " + DevelopmentState.Beta.getDefaultEloK());
		lines.add(ChatColor.DARK_RED + "Alpha" + INK + " K " + DevelopmentState.Alpha.getDefaultEloK());
		lines.add(ChatColor.RED + "Pre-Alpha" + INK + " K " + DevelopmentState.PreAlpha.getDefaultEloK());
		lines.add(ChatColor.RED + "Errors coneguts" + INK + " K " + DevelopmentState.KnownIssues.getDefaultEloK());
		lines.add(INK + "Un mapa pot fixar el seu propi K.");
		if (!Com.getPlugin().isInRankedMode()) {
			lines.add(ChatColor.DARK_RED + "Ara mateix el mode rànquing està desactivat.");
		}
		return lines;
	}

	private static List<String> rules() {
		List<String> lines = new ArrayList<>();
		lines.add(ChatColor.BOLD + "" + INK + "Quan puntua" + ChatColor.RESET);
		lines.add(INK + "Una partida només puntua si:");
		lines.add(INK + "- dura més de 20 s");
		lines.add(INK + "- els equips van igualats");
		lines.add(INK + "- el mode rànquing és actiu");
		lines.add(INK + "- ningú ha estat penalitzat");
		lines.add(INK + "Abandonar una partida avançada resta elo, tret d'errors de xarxa.");
		lines.add(FAINT + "/elo  ·  /top");
		return lines;
	}

	private static List<String> gamesPage(List<GameWeight> weights, boolean first) {
		List<String> lines = new ArrayList<>();
		if (first) lines.add(ChatColor.BOLD + "" + INK + "Jocs i pes" + ChatColor.RESET);
		for (GameWeight weight : weights) {
			String ink = weight.k() > 0 ? INK : FAINT;
			lines.add(ink + weight.name() + " " + (weight.k() > 0 ? WEIGHT : FAINT) + "K " + weight.k());
			for (String note : weight.mapNotes()) {
				lines.add("  " + FAINT + note);
			}
		}
		return lines;
	}

	/** Every registered game with the weight it plays for, and the maps that differ from it. */
	private static List<GameWeight> gameWeights() {
		List<GameWeight> weights = new ArrayList<>();
		for (ContenidorJoc container : Com.getGest().getGameContainers()) {
			int defaultK = container.getDevelopmentState().getDefaultEloK();
			Joc template = container.getTempInstance();
			List<String> mapNotes = new ArrayList<>();
			double k = defaultK;
			if (template != null && template.getMapMode() == MapMode.MULTIPLE) {
				for (String mapName : template.getMultiWorldList()) {
					double mapK = template.getTemplateEloBaseK(mapName);
					if (mapK != defaultK) mapNotes.add(mapName + " K " + Math.round(mapK));
				}
			} else if (template != null) {
				k = template.getTemplateEloBaseK(null);
			}
			weights.add(new GameWeight(container.getNom(), (int) Math.round(k), mapNotes));
		}
		weights.sort(Comparator.comparingInt(GameWeight::k).reversed().thenComparing(GameWeight::name));
		return weights;
	}

	private static Component page(List<String> lines) {
		return PaperMessages.legacy(String.join("\n", lines));
	}

	private record GameWeight(String name, int k, List<String> mapNotes) {
	}
}
