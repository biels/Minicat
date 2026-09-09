package com.biel.lobby.localization;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** Active languages are data; Triton owns each player's stored selection. */
public final class LanguageCatalog {
    public record Entry(String id, String code, String displayName, List<String> minecraftCodes) {}
    private record Definition(@com.google.gson.annotations.SerializedName("default") String fallback, List<Entry> languages) {}
    private static final Definition DEFINITION = load();
    private LanguageCatalog() {}
    private static Definition load() {
        try (var input = LanguageCatalog.class.getResourceAsStream("/i18n/languages.json")) {
            if (input == null) throw new IllegalStateException("Missing i18n/languages.json");
            return new Gson().fromJson(new InputStreamReader(input, StandardCharsets.UTF_8), Definition.class);
        } catch (java.io.IOException e) { throw new IllegalStateException(e); }
    }
    public static List<Entry> entries() { return List.copyOf(DEFINITION.languages()); }
    public static String fallback() { return DEFINITION.fallback(); }
    public static Optional<Entry> find(String value) {
        return entries().stream().filter(e -> e.id().equalsIgnoreCase(value) || e.code().equalsIgnoreCase(value)).findFirst();
    }
    public static List<String> suggestions(String prefix) {
        String normalized = prefix.toLowerCase(Locale.ROOT);
        return entries().stream().map(Entry::code).filter(code -> code.startsWith(normalized)).toList();
    }
}
