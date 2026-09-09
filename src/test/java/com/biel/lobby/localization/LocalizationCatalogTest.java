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
}
