package com.biel.lobby.localization;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class LocalizationCatalogTest {
 @Test void bundledCatalogMatchesGeneratedContracts() {
  LocalizationCatalog.validate();
  assertEquals(MessageKey.values().length, LocalizationCatalog.bundledItems().size());
  assertEquals(java.util.List.of("en", "ca"), LanguageCatalog.entries().stream().map(LanguageCatalog.Entry::code).toList());
 }
 @Test void rejectsMissingPlaceholder() {
  var items = LocalizationCatalog.bundledItems();
  items.get(MessageKey.INFO_UNKNOWN_GAME.key()).getAsJsonObject("languages").addProperty("ca_ES", "[minimsg]Missing argument");
  assertThrows(IllegalStateException.class, () -> LocalizationCatalog.validate(items));
 }
 @Test void rejectsMissingLocale() {
  var items = LocalizationCatalog.bundledItems();
  items.get(MessageKey.MAPS_TITLE.key()).getAsJsonObject("languages").remove("ca_ES");
  assertThrows(IllegalStateException.class, () -> LocalizationCatalog.validate(items));
 }
 @Test void languageCodesAndIdsResolveWithoutHardcodedCommandSwitches() {
  assertEquals("ca_ES", LanguageCatalog.find("CA").orElseThrow().id());
  assertEquals("en", LanguageCatalog.find("en_US").orElseThrow().code());
  assertTrue(LanguageCatalog.find("es").isEmpty());
 }
 @Test void rejectsWrongArgumentOrderAndType() {
  assertThrows(IllegalArgumentException.class, () -> Messages.validateArguments(MessageKey.RANK_VALUE, new MessageArgument[]{MessageArgument.number("rank", 1), MessageArgument.number("elo", 1200)}));
  assertThrows(IllegalArgumentException.class, () -> Messages.validateArguments(MessageKey.RANK_VALUE, new MessageArgument[]{MessageArgument.text("elo", "1200"), MessageArgument.number("rank", 1)}));
 }
 @Test void allTemplatesHaveValidMiniMessageNesting() {
  var parser = net.kyori.adventure.text.minimessage.MiniMessage.builder().strict(true).build();
  LocalizationCatalog.bundledItems().values().forEach(item -> item.getAsJsonObject("languages").entrySet().forEach(text -> assertDoesNotThrow(() -> parser.deserialize(text.getValue().getAsString().substring("[minimsg]".length())))));
 }
 @Test void everyKeyHasAProductionJavaReference() throws java.io.IOException {
  StringBuilder source = new StringBuilder();
  try (var files = java.nio.file.Files.walk(java.nio.file.Path.of("src/main/java"))) {
   for (var path : files.filter(p -> p.toString().endsWith(".java")).toList()) source.append(java.nio.file.Files.readString(path)).append("\n");
  }
  var used = new java.util.HashSet<String>();
  var matcher = java.util.regex.Pattern.compile("MessageKey\\.([A-Z][A-Z0-9_]*)").matcher(source);
  while (matcher.find()) used.add(matcher.group(1));
  var unused = java.util.Arrays.stream(MessageKey.values()).map(Enum::name).filter(name -> !used.contains(name)).toList();
  assertTrue(unused.isEmpty(), "Unused catalog keys: " + unused);
 }
}
