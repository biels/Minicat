package com.biel.lobby;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.biel.lobby.localization.*;
import com.biel.lobby.GestorMapes.ContenidorJoc;
import com.biel.lobby.GestorMapes.DevelopmentState;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable.MapMode;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;

/**
 * The written book that tells players what each game plays for. It is the one
 * place the rating weights are explained; the teleporter only lists games.
 */
public final class RankingBook {

	private RankingBook() {
	}

	/**
	 * Shows the book. The menu the player clicked is closed first and the book opens
	 * on the next tick, because a container close arriving after the book would
	 * shut the book instead.
	 */
    public static void open(Player player) {
        player.closeInventory();
        Bukkit.getScheduler().runTask(Com.getPlugin(), () -> {
            if (player.isOnline()) player.openBook(build(player));
        });
    }

    static Book build(Player player) {
        List<Component> pages = new ArrayList<>();
        pages.add(Messages.component(player, MessageKey.RANK_BOOK_INTRO,
            MessageArgument.number("release", DevelopmentState.Release.getDefaultEloK()),
            MessageArgument.number("beta", DevelopmentState.Beta.getDefaultEloK()),
            MessageArgument.number("alpha", DevelopmentState.Alpha.getDefaultEloK()),
            MessageArgument.number("preAlpha", DevelopmentState.PreAlpha.getDefaultEloK()),
            MessageArgument.number("issues", DevelopmentState.KnownIssues.getDefaultEloK())));
        pages.add(Messages.component(player, MessageKey.RANK_BOOK_RULES));
        pages.add(Messages.component(player, MessageKey.RANK_BOOK_LEAVING)
            .append(Component.newline()).append(Component.newline())
            .append(Messages.component(player, Com.getPlugin().isInRankedMode() ? MessageKey.RANKED_ON : MessageKey.RANKED_OFF)));
        for (GameWeight weight : gameWeights()) {
            Component page = Messages.component(player, MessageKey.RANK_BOOK_GAMES).append(Component.newline()).append(Component.newline())
                .append(Messages.component(player, MessageKey.RANK_BOOK_ROW, MessageArgument.text("game", weight.name()), MessageArgument.number("weight", weight.k())));
            pages.add(page);
            // Map overrides get their own page so the list cannot push the game weight off-screen.
            for (MapWeight note : weight.mapNotes()) pages.add(Messages.component(player, MessageKey.RANK_BOOK_MAP,
                MessageArgument.text("map", note.name()), MessageArgument.number("weight", note.k())));
        }
        return Book.book(Messages.component(player, MessageKey.RANK_BOOK_TITLE), Component.text("Minicat"), pages);
    }

	/** Every registered game with the weight it plays for, and the maps that differ from it. */
	private static List<GameWeight> gameWeights() {
		List<GameWeight> weights = new ArrayList<>();
		for (ContenidorJoc container : Com.getGest().getGameContainers()) {
			int defaultK = container.getDevelopmentState().getDefaultEloK();
			Joc template = container.getTempInstance();
			List<MapWeight> mapNotes = new ArrayList<>();
			double k = defaultK;
			if (template != null && template.getMapMode() == MapMode.MULTIPLE) {
				for (String mapName : template.getMultiWorldList()) {
					double mapK = template.getTemplateEloBaseK(mapName);
					if (mapK != defaultK) mapNotes.add(new MapWeight(mapName, Math.round(mapK)));
				}
			} else if (template != null) {
				k = template.getTemplateEloBaseK(null);
			}
			weights.add(new GameWeight(container.getNom(), (int) Math.round(k), mapNotes));
		}
		weights.sort(Comparator.comparingInt(GameWeight::k).reversed().thenComparing(GameWeight::name));
		return weights;
	}


	private record MapWeight(String name, long k) {}
	private record GameWeight(String name, int k, List<MapWeight> mapNotes) {
	}
}
