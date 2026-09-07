package com.biel.lobby.guide;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes.ContenidorJoc;
import com.biel.lobby.Mapa;
import com.biel.lobby.utilities.PaperMessages;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;

/**
 * A game's words, written once: {@code guides/<game>.md} in the jar (or the same path
 * under the plugin's data folder, which wins, so a server can be corrected without a
 * build). The file is sections, {@code ## kind key}: {@code inici} the lines said in chat
 * at the start, {@code objecte NAME} an item's tooltip, {@code pista key} a one-shot hint,
 * {@code pàgina Title} a page of the guide book. Line sections are bullets ({@code - }),
 * pages are paragraphs; {@code {NOM}} is a value the game injects (a price, a number of
 * seconds), so the text never goes stale; a page line {@code {{objecte NAME}}} pulls a
 * section in whole, so the book repeats no tooltip by hand. Biel, 2026-09-08: "let's
 * deduplicate it with the messages in the chat, store it in an md file instead of
 * repeating strings again and again."
 */
public final class GameGuide {
	private static final Map<String, GameGuide> LOADED = new HashMap<>();
	private static final Pattern PLACEHOLDER = Pattern.compile("\\{([A-Za-z0-9_ÀÈÉÍÒÓÚÇàèéíòóúç]+)}");
	private static final Pattern INCLUDE = Pattern.compile("^\\{\\{(.+)}}$");
	/** A book page shows about this many rows of this many characters before the text runs off it. */
	private static final int ROWS_PER_PAGE = 14;
	private static final int CHARS_PER_ROW = 19;

	private final String gameName;
	private final Map<String, List<String>> sections = new LinkedHashMap<>();
	private final Map<String, String> values = new HashMap<>();

	private GameGuide(String gameName) {
		this.gameName = gameName;
	}

	/** The guide of a game by its registered name, loaded once; empty (see {@link #exists()}) when there is no file. */
	public static synchronized GameGuide of(String gameName) {
		return LOADED.computeIfAbsent(slug(gameName), slug -> load(gameName, slug));
	}

	/** Forgets every loaded guide, so the next {@link #of} reads the files again. */
	public static synchronized void reload() {
		LOADED.clear();
	}

	public static String slug(String gameName) {
		String plain = Normalizer.normalize(gameName, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
		return plain.replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
	}

	private static GameGuide load(String gameName, String slug) {
		GameGuide guide = new GameGuide(gameName);
		String path = "guides/" + slug + ".md";
		try {
			File override = new File(Com.getPlugin().getDataFolder(), path);
			if (override.isFile()) {
				guide.parse(Files.readAllLines(override.toPath(), StandardCharsets.UTF_8));
				return guide;
			}
			InputStream resource = Com.getPlugin().getResource(path);
			if (resource == null) return guide;
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))) {
				List<String> lines = new ArrayList<>();
				for (String line = reader.readLine(); line != null; line = reader.readLine()) lines.add(line);
				guide.parse(lines);
			}
		} catch (IOException e) {
			Com.getPlugin().getLogger().warning("Guide " + path + " could not be read: " + e.getMessage());
		}
		return guide;
	}

	private void parse(List<String> lines) {
		String current = null;
		for (String raw : lines) {
			if (raw.startsWith("## ")) {
				current = raw.substring(3).trim();
				sections.put(current, new ArrayList<>());
				continue;
			}
			if (current == null || raw.startsWith("# ")) continue;
			List<String> section = sections.get(current);
			if (current.startsWith("pàgina ")) {
				section.add(raw.stripTrailing());
			} else if (raw.startsWith("- ")) {
				section.add(raw.substring(2).trim());
			}
		}
		// Pages keep their inner blank lines as paragraph breaks; the edges are trimmed.
		for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
			if (!entry.getKey().startsWith("pàgina ")) continue;
			List<String> page = entry.getValue();
			while (!page.isEmpty() && page.get(0).isBlank()) page.remove(0);
			while (!page.isEmpty() && page.get(page.size() - 1).isBlank()) page.remove(page.size() - 1);
		}
	}

	public boolean exists() {
		return !sections.isEmpty();
	}

	public String gameName() {
		return gameName;
	}

	/** The values the placeholders stand for; the game sets them once from its constants. */
	public GameGuide withValues(Map<String, String> injected) {
		values.putAll(injected);
		return this;
	}

	/** The lines of a section with the values filled in; empty when the section is not there. */
	public List<String> lines(String section) {
		List<String> raw = sections.get(section);
		if (raw == null) return List.of();
		List<String> filled = new ArrayList<>(raw.size());
		for (String line : raw) filled.add(fill(line));
		return filled;
	}

	/** The first line of a section, or the section's name itself when it is missing, so a missing text is visible in game rather than silent. */
	public String line(String section) {
		List<String> lines = lines(section);
		return lines.isEmpty() ? "[" + section + "]" : lines.get(0);
	}

	private String fill(String line) {
		Matcher m = PLACEHOLDER.matcher(line);
		StringBuilder out = new StringBuilder();
		while (m.find()) m.appendReplacement(out, Matcher.quoteReplacement(values.getOrDefault(m.group(1), m.group(0))));
		m.appendTail(out);
		return out.toString();
	}

	/** The book: one or more pages per {@code pàgina} section, in file order, split where a page would run off the paper. */
	public Book book() {
		List<Component> pages = new ArrayList<>();
		for (String section : sections.keySet()) {
			if (!section.startsWith("pàgina ")) continue;
			String title = section.substring("pàgina ".length());
			List<String> body = new ArrayList<>();
			for (String line : lines(section)) {
				Matcher include = INCLUDE.matcher(line.trim());
				if (include.matches()) body.addAll(lines(include.group(1)));
				else body.add(line);
			}
			paginate(title, body, pages);
		}
		return Book.book(Component.text(gameName), Component.text("Minicat"), pages);
	}

	private static void paginate(String title, List<String> body, List<Component> pages) {
		List<String> page = new ArrayList<>();
		int rows = 0;
		boolean first = true;
		for (String line : body) {
			int needed = Math.max(1, (line.length() + CHARS_PER_ROW - 1) / CHARS_PER_ROW);
			int headerRows = page.isEmpty() ? 2 : 0;
			if (rows + headerRows + needed > ROWS_PER_PAGE && !page.isEmpty()) {
				pages.add(render(title, first, page));
				first = false;
				page = new ArrayList<>();
				rows = 0;
				if (line.isBlank()) continue;
			}
			page.add(line);
			rows += needed;
		}
		if (!page.isEmpty()) pages.add(render(title, first, page));
	}

	private static Component render(String title, boolean first, List<String> body) {
		StringBuilder text = new StringBuilder();
		text.append(ChatColor.BOLD).append(ChatColor.DARK_BLUE).append(title).append(first ? "" : " (cont.)").append(ChatColor.RESET).append("\n\n");
		text.append(String.join("\n", body));
		return PaperMessages.legacy(text.toString());
	}

	public void open(Player player) {
		if (!exists()) {
			player.sendMessage(ChatColor.GRAY + "Encara no hi ha guia " + com.biel.lobby.utilities.Catalan.de(gameName) + ".");
			return;
		}
		Book book = book();
		player.closeInventory();
		Bukkit.getScheduler().runTask(Com.getPlugin(), () -> {
			if (player.isOnline()) player.openBook(book);
		});
	}

	/**
	 * {@code /guia} and the lobby signs: the guide of the game the player is in, or of the
	 * registered game whose name contains the words given.
	 */
	public static void open(Player player, String gameNameOrNull) {
		if (gameNameOrNull == null || gameNameOrNull.isBlank()) {
			Mapa map = Com.getGest().getMapWherePlayerIs(player);
			if (map == null) {
				player.sendMessage(ChatColor.GRAY + "Escriu /guia <joc>, o fes clic al rètol de la guia d'un joc.");
				return;
			}
			of(map.getGameName()).open(player);
			return;
		}
		ContenidorJoc game = findGame(gameNameOrNull);
		if (game == null) {
			player.sendMessage(ChatColor.GRAY + "No conec cap joc que es digui \"" + gameNameOrNull + "\".");
			return;
		}
		of(game.getNom()).open(player);
	}

	/** The registered game whose name contains every word given, ignoring case and accents. */
	public static ContenidorJoc findGame(String words) {
		String wanted = slug(words);
		if (wanted.isEmpty()) return null;
		for (ContenidorJoc game : Com.getGest().getGameContainers()) {
			String name = slug(game.getNom());
			boolean all = true;
			for (String word : wanted.split("-")) if (!name.contains(word)) { all = false; break; }
			if (all) return game;
		}
		return null;
	}
}
