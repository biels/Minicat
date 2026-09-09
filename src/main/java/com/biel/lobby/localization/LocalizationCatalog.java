package com.biel.lobby.localization;

import com.google.gson.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/** Validates the same artifacts generated and checked during the build. */
public final class LocalizationCatalog {
    private LocalizationCatalog() {}
    public static JsonElement read(String resource) {
        try (var input = LocalizationCatalog.class.getResourceAsStream("/" + resource)) {
            if (input == null) throw new IllegalStateException("Missing " + resource);
            return JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) { throw new IllegalStateException(e); }
    }
    public static List<String> resources() {
        return read("i18n/catalog-index.json").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
    }
    public static Map<String, JsonObject> bundledItems() {
        Map<String, JsonObject> result = new LinkedHashMap<>();
        for (String path : resources()) for (var item : read(path).getAsJsonObject().getAsJsonArray("items")) {
            var object = item.getAsJsonObject();
            if (result.put(object.get("key").getAsString(), object) != null) throw new IllegalStateException("Duplicate message key");
        }
        return result;
    }
    public static void validate() { validate(bundledItems()); }
    public static void validate(Map<String, JsonObject> items) {
        Set<String> required = new HashSet<>();
        Set<String> languages = new HashSet<>(LanguageCatalog.entries().stream().map(LanguageCatalog.Entry::id).toList());
        var pattern = Pattern.compile("%(\\d+)");
        for (MessageKey key : MessageKey.values()) {
            required.add(key.key());
            var item = items.get(key.key());
            if (item == null) throw new IllegalStateException("Missing " + key.key());
            var texts = item.getAsJsonObject("languages");
            if (!texts.keySet().equals(languages)) throw new IllegalStateException("Language coverage differs for " + key.key());
            for (var value : texts.entrySet()) {
                String text = value.getValue().getAsString();
                if (!text.startsWith("[minimsg]")) throw new IllegalStateException("Missing format for " + key.key());
                Set<Integer> found = new HashSet<>(); var matcher = pattern.matcher(text);
                while (matcher.find()) found.add(Integer.parseInt(matcher.group(1)));
                Set<Integer> expected = new HashSet<>();
                for (int i = 1; i <= key.arguments().size(); i++) expected.add(i);
                if (!found.equals(expected)) throw new IllegalStateException("Placeholder mismatch for " + key.key());
            }
        }
        if (!items.keySet().equals(required)) throw new IllegalStateException("Unexpected catalog keys");
    }
}
