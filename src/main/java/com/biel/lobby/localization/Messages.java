package com.biel.lobby.localization;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import com.rexcantor64.triton.api.Triton;
import com.rexcantor64.triton.api.TritonAPI;
import com.rexcantor64.triton.api.config.FeatureSyntax;
import com.rexcantor64.triton.api.language.Language;
import com.rexcantor64.triton.api.players.LanguagePlayer;

import net.kyori.adventure.text.Component;

/** The single Minicat boundary to Triton's catalog, preferences and native selector. */
public final class Messages {
	private static Messages instance;

	private static boolean privateCatalan;
	private final Triton triton;
	private final String guiMarker;
	private final String itemMarker;

	private Messages(Triton triton) {
		this.triton = triton;
		this.guiMarker = markerName(triton.getConfig().getGuiSyntax(), "GUI");
		this.itemMarker = markerName(triton.getConfig().getItemsSyntax(), "item");
	}

	public static void initialize(JavaPlugin owner) {
		LocalizationCatalog.validate();

		Plugin plugin = Bukkit.getPluginManager().getPlugin("Triton");
		if (plugin == null || !plugin.isEnabled()) throw new IllegalStateException("Triton 4.1.0 must be loaded before Minicat");
		Triton api = TritonAPI.getInstance();
		if (api == null) throw new IllegalStateException("Triton's public API is unavailable");
		privateCatalan = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml")).getBoolean("minicat.private-catalan", false);
		validateConfiguration(plugin, api);
		validateRuntimeCatalog(api);
		instance = new Messages(api);
		owner.getLogger().info("Validated Minicat Triton catalog: " + MessageKey.values().length + " keys; languages " + LanguageCatalog.entries());
	}

	private static void validateConfiguration(Plugin plugin, Triton api) {
		if (!"4.1.0".equals(plugin.getPluginMeta().getVersion())) {
			throw new IllegalStateException("Minicat requires Triton 4.1.0; found " + plugin.getPluginMeta().getVersion());
		}
		for (String alias : api.getConfig().getCommandAliases()) {
			if (java.util.Set.of("lang", "language", "llengua", "idioma").contains(alias.toLowerCase(Locale.ROOT))) {
				throw new IllegalStateException("Remove " + alias + " from Triton command-aliases: Minicat owns language selection; use /triton for Triton tools");
			}
		}
		if (!"local".equalsIgnoreCase(api.getConfig().getStorageType())) {
			throw new IllegalStateException("Triton storage.type must be local for Minicat language preferences");
		}
		if (api.getConfig().isAlwaysCheckClientLocale()) {
			throw new IllegalStateException("Triton force-client-locale-on-join must be false so saved preferences win");
		}
		if (api.getConfig().isChat()) {
			throw new IllegalStateException("Triton language-creation.chat.enabled must be false for direct Minicat chat");
		}
		if (!api.getConfig().isGuis() || !api.getConfig().isItems() || !api.getConfig().isInventoryItems()) {
			throw new IllegalStateException("Triton GUI and inventory-item translation must be enabled for the map menu");
		}
		YamlConfiguration diskConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        var configuredLanguages = diskConfig.getConfigurationSection("languages");
        var expectedLanguages = new java.util.HashSet<>(activeLanguages().stream().map(LanguageCatalog.Entry::id).toList());
        if (configuredLanguages == null || !configuredLanguages.getKeys(false).equals(expectedLanguages)) {
            throw new IllegalStateException("Triton active languages differ from i18n/languages.json; run the localization installer");
        }
        for (var entry : activeLanguages()) {
            if (!diskConfig.getStringList("languages." + entry.id() + ".minecraft-code").equals(entry.minecraftCodes())) {
                throw new IllegalStateException("Triton client locale mappings differ for " + entry.id());
            }
        }
        for (String surface : java.util.List.of("guis", "items", "actionbars", "titles", "signs", "scoreboards", "holograms")) {
            String path = "language-creation." + surface;
            if (!"lang".equals(diskConfig.getString(path + ".syntax-lang")) || !"arg".equals(diskConfig.getString(path + ".syntax-arg"))) {
                throw new IllegalStateException("Minicat requires matching lang/arg marker syntax for " + surface);
            }
            if (!surface.equals("holograms") && !diskConfig.getBoolean(path + ".enabled")) throw new IllegalStateException("Triton channel disabled: " + surface);
        }
        if (!diskConfig.getBoolean("language-creation.items.books")) throw new IllegalStateException("Triton book translation must be enabled");
        validateInstalledCollections(plugin);
		if (diskConfig.getBoolean("language-creation.chat.signed-enabled", true)) {
			throw new IllegalStateException("Triton language-creation.chat.signed-enabled must be false for direct Minicat chat");
		}
		if (!"adventure".equalsIgnoreCase(diskConfig.getString("message-parser", ""))) {
			throw new IllegalStateException("Triton message-parser must be adventure");
		}
	}

    private static void validateInstalledCollections(Plugin plugin) {
        var found = new java.util.LinkedHashMap<String, com.google.gson.JsonObject>();
        File[] files = new File(plugin.getDataFolder(), "translations").listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) throw new IllegalStateException("Missing Triton translation directory");
        for (File file : files) {
            try (var reader = java.nio.file.Files.newBufferedReader(file.toPath())) {
                var items = com.google.gson.JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("items");
                for (var value : items) {
                    var item = value.getAsJsonObject();
                    String key = item.get("key").getAsString();
                    if (!key.startsWith("minicat.")) continue;
                    if (found.put(key, item) != null) throw new IllegalStateException("Duplicate installed Minicat key " + key);
                }
            } catch (java.io.IOException error) { throw new IllegalStateException("Cannot read " + file.getName(), error); }
        }
        var bundled = LocalizationCatalog.bundledItems();
        for (var entry : bundled.entrySet()) {
            var installed = found.get(entry.getKey());
            if (installed == null || !entry.getValue().get("languages").equals(installed.get("languages"))) {
                throw new IllegalStateException("Installed Triton copy differs from this Minicat build: " + entry.getKey());
            }
        }
    }

	private static void validateRuntimeCatalog(Triton api) {
		for (var entry : activeLanguages()) {
            String languageName = entry.id();
			Language language = api.getLanguageManager().getLanguageByName(languageName)
					.orElseThrow(() -> new IllegalStateException("Triton language is not configured: " + languageName));
			for (MessageKey key : MessageKey.values()) {
				Component[] arguments = key.argumentNames().stream().map(name -> Component.text("{" + name + "}")).toArray(Component[]::new);
				if (api.getTranslationManager().getTextComponent(language, key.key(), arguments).isEmpty()) {
					throw new IllegalStateException("Triton runtime catalog is missing " + key.key() + " in " + languageName);
				}
			}
		}
		if (!(privateCatalan ? "ca_ES" : LanguageCatalog.fallback()).equals(api.getLanguageManager().getMainLanguage().getName())) {
			throw new IllegalStateException("Triton main-language differs from the deployment language mode");
		}
	}

    public static boolean languageSelectionEnabled() { return !privateCatalan; }

    private static java.util.List<LanguageCatalog.Entry> activeLanguages() {
        return LanguageCatalog.entries().stream().filter(entry -> !privateCatalan || entry.id().equals("ca_ES")).toList();
    }

	private static String markerName(FeatureSyntax syntax, String surface) {
		if (syntax == null || syntax.getLang() == null || syntax.getLang().isBlank()) {
			throw new IllegalStateException("Triton " + surface + " marker syntax is unavailable");
		}
		return syntax.getLang();
	}

	private static Messages get() {
		if (instance == null) throw new IllegalStateException("Messages has not been initialized");
		return instance;
	}

	public static Component component(Player player, MessageKey key, MessageArgument... arguments) {
		validateArguments(key, arguments);
		LanguagePlayer languagePlayer = get().languagePlayer(player);
		Component[] values = Arrays.stream(arguments).map(MessageArgument::value).toArray(Component[]::new);
		return get().triton.getTranslationManager().getTextComponent(languagePlayer, key.key(), values)
				.orElseThrow(() -> new IllegalStateException("Missing runtime translation " + key.key() + " for " + languagePlayer.getLang().getName()));
	}

	public static void send(Player player, MessageKey key, MessageArgument... arguments) {
		player.sendMessage(component(player, key, arguments));
	}

	public static String menuTitleMarker(MessageKey key) {
		return marker(get().guiMarker, key);
	}

	public static String sharedItemMarker(MessageKey key) {
		return marker(get().itemMarker, key);
	}

	public static Component sharedItemMarkerComponent(MessageKey key) {
		return Component.text(sharedItemMarker(key));
	}

    public static String legacy(Player player, MessageKey key, MessageArgument... arguments) {
        return LegacyComponentSerializer.legacySection().serialize(component(player, key, arguments));
    }

    public static String sharedNumberItemMarker(MessageKey key, MessageArgument... arguments) {
        validateArguments(key, arguments);
        String argTag = get().triton.getConfig().getItemsSyntax().getArg();
        StringBuilder result = new StringBuilder("[" + get().itemMarker + "]" + key.key());
        for (var argument : arguments) {
            if (argument.type() != MessageArgument.Type.NUMBER) throw new IllegalArgumentException("Shared marker arguments must be numeric");
            String value = net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText().serialize(argument.value());
            if (!value.matches("-?[0-9]+(?:\\.[0-9]+)?")) throw new IllegalArgumentException("Invalid numeric marker argument");
            result.append("[").append(argTag).append("]").append(value).append("[/").append(argTag).append("]");
        }
        return result.append("[/").append(get().itemMarker).append("]").toString();
    }

    private static String marker(String markerName, MessageKey key) {
		if (!key.argumentNames().isEmpty()) throw new IllegalArgumentException("Packet marker arguments are not implemented for " + key.key());
		return "[" + markerName + "]" + key.key() + "[/" + markerName + "]";
	}

	public static void openLanguageSelector(Player player) {
		if (languageSelectionEnabled()) get().triton.openLanguagesSelectionGUI(get().languagePlayer(player));
	}

	public static boolean setLanguage(Player player, String requestedLanguage) {
        if (!languageSelectionEnabled()) return false;
        var entry = LanguageCatalog.find(requestedLanguage).orElse(null);
        if (entry == null) return false;
        Language language = get().triton.getLanguageManager().getLanguageByName(entry.id())
                .orElseThrow(() -> new IllegalStateException("Configured language disappeared: " + entry.id()));
        get().languagePlayer(player).setLang(language);
        if (!get().languagePlayer(player).getLang().getName().equals(entry.id())) return false;
        String displayName = entry.displayName();
		send(player, MessageKey.LANGUAGE_CHANGED, MessageArgument.text("language", displayName));
		return true;
	}

	private LanguagePlayer languagePlayer(Player player) {
		LanguagePlayer languagePlayer = triton.getPlayerManager().get(player.getUniqueId());
		if (languagePlayer == null) throw new IllegalStateException("Triton has no language player for " + player.getUniqueId());
		return languagePlayer;
	}

	static void validateArguments(MessageKey key, MessageArgument[] arguments) {
		if (arguments.length != key.argumentNames().size()) throw new IllegalArgumentException("Expected arguments " + key.argumentNames() + " for " + key.key());
		for (int index = 0; index < arguments.length; index++) {
			if (!key.argumentNames().get(index).equals(arguments[index].name())
                    || key.arguments().get(index).type() != arguments[index].type()) {
				throw new IllegalArgumentException("Argument " + (index + 1) + " for " + key.key() + " must be " + key.argumentNames().get(index));
			}
		}
	}
}
