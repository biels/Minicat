# Minicat copy and localization

English is the source language and fallback. English (`en_US`) and Catalan
(`ca_ES`) are the only active languages in this slice. Spanish can be added once
this system is accepted. Global players must not need Catalan to navigate or play.
Keep Catalan identity in the Catalan copy, community presence, proper names and
optional aliases. Admin/editor copy may remain English or Catalan.

## Authoring

- `languages.json` defines active languages, short codes, client locale mappings
  and fallback. Triton owns stored preferences: saved choice, otherwise supported
  client locale on first arrival, otherwise English. No IP inference.
- `translations/<feature>.json` is a native Triton collection. Keep both language
  versions adjacent under one stable semantic key. English first.
- `contracts/<feature>.json` describes each key's purpose, generated Java constant,
  ordered named arguments (`TEXT` or `NUMBER`), intended surfaces and template
  character budget. Budgets describe templates; long runtime values still require
  layout checks on their destination surface.
- `gradle/localization.gradle` validates coverage, duplicate keys/constants,
  argument positions and budgets, then generates `MessageKey` and resource indexes.
  Do not edit generated output or deployed collections as source copy. The tests
  also check that each generated key has a production Java reference; remove
  unused copy rather than keeping speculative messages installed.

Write complete messages. Do not concatenate translated fragments, hand-roll
plural rules or interpolate player input into MiniMessage. Use neutral count
labels such as “Players: %1”; introduce a proven plural formatter if a future
feature needs grammatical inflection. Keep labels short, buttons action-oriented
and errors specific about what the player can do next. Use the same term for the
same action across menus, chat and books. Preserve names and player chat verbatim.

Use `[minimsg]` with balanced MiniMessage tags. Common styling: red for blocked
operations, gray for neutral feedback, white for explanations, green/aqua for
navigation and gold for rankings. Avoid unnecessary emphasis and punctuation in
button labels. Keep book pages short enough to wrap at Minecraft's narrow width;
packet tests cannot certify visual pagination.

## Java boundary

```java
Messages.send(player, MessageKey.INSTANCE_FAILED,
    MessageArgument.text("game", gameName));
```

Triton remains the only translation and preference implementation. Adventure
components carry literal arguments; argument names, order and types are checked
against generated contracts. Use `Messages.legacy` only at an existing API that
requires legacy strings. Never format user input as markup or packet markers.

Chat resolves directly for each recipient. Triton chat and signed-chat interception
stay disabled so literal text is not interpreted a second time. Broadcasts must
loop over their existing audience and resolve separately for each player.

Shared inventory items retain canonical native markers and existing PDC identity.
No-argument text uses `sharedItemMarker`; dynamic shared markers accept **numbers
only** through `sharedNumberItemMarker`. Never store one viewer's translated item
as the authoritative shared item. Recipient-specific menu values use direct
rendering. Map/instance/active-game menus rebuild on language changes only when
the exact tracked inventory is still open. Inventory contents are never cleared
merely to change language. The map compass remembers its originating menu and
returns there after a language choice, including choosing the current language.
Escaping the selector cancels that intent; standalone `/lang` does not open a
map menu afterward. Replacing the UI, leaving the world or disconnecting also
cancels the pending return.

Titles/actionbars resolve when sent; a repeated localized actionbar resolves each
repeat. Help-book pages use native item translation; its fixed author/title are
Minicat, while the display name is localized. Ranking books resolve when opened
and must be reopened after changing language. Invitation-menu titles resolve on
next open. Shared hologram and scoreboard markers are qualified by the local
integration fixture. Per-game scoreboard content is a separate migration.

## Sign bindings

Signs use Triton's location-bound `sign` entries, not nested `[lang]` text.
A binding contains `id`, `locations` and up to eight `lines` of argument-free copy
keys (null means empty). The installer generates native sign translations from
those keys, so there is no second authored copy. Keep bindings with the relevant
world deployment; an optional first `--sign-bindings` file is saved to Triton's
`minicat-sign-bindings.json` and reused on subsequent installs.

## Commands and installation

Use meaningful English primary commands or established universal shortcuts.
Minicat owns `/lang [code]`, `/language`, `/llengua` and `/idioma`. `/lang` opens
Triton's selector; `/lang en` and `/lang ca` save a preference. Codes and completion
come from `languages.json`. Show the same primary command in every translation.
Triton's administration remains `/triton help`. Shared command descriptions and
essential MOTD information use English.

Build with `./gradlew test jar`. With the target server stopped, install the jar's
copy using `python3 scripts/install-localization.py <jar> <Triton-directory>
--server-stopped` (requires PyYAML). Install that same jar as Minicat, then restart.
The installer backs up configuration and owned catalogs, removes only previously
owned collections, and leaves player JSON and external collections intact. Keep
configuration backups private: they may contain unrelated configuration secrets.
Startup rejects missing/stale copy, mismatched active locales, conflicting command
aliases and incompatible channel settings. A failed installation should be fixed
or restored from its backup before starting the server.

To add Spanish later: add its definition/client mappings, add `es_ES` beside every
message, translate shared selector/help copy as appropriate, build, install and
run the locale/packet checks. Game Java code does not gain language switches.

## Adoption boundary

This slice covers shared navigation, lobby tools/help, instance menus and creation
feedback, join/start errors, invitations, reconnect feedback and ranking explanations.
Shared `JocEquips` team selection, preparation controls, assignment/lock/reset
feedback and its pre-match team board are also localized in `common-teams.json`.
Games inherit these translations when they use the base implementation. Standard
color-team names use `TeamNames`; custom proper names and legacy team identifiers
remain unchanged. Use `Equip.getLocalizedName(viewer)` for recipient-facing names.
Team menus reuse the tracked inventory lifecycle and rebuild on language changes.

Score-holder strings are identifiers: Triton does not translate them. The shared
team board uses `ScoreBoardUpdater.setTranslatedScoreBoard` with invisible holders
and translatable team prefixes, allowing language refresh without changing scores.
Keep this distinction when migrating game boards.

It does not translate each game mode's instructions, match announcements, combat
UI, per-game scoreboard overrides or editor tools. Migrate those by feature
using this system; do not bulk rewrite unrelated legacy text.
