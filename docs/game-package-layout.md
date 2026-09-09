# Game package layout

The law for `src/main/java/com/biel/lobby/mapes/jocs`, the folder that holds every game
mode. Design agreed 2026-09-09; the moves it prescribes are listed at the end with
their status.

## The rule

A game has exactly one of two shapes, and the tree alone tells you which:

| Shape | Where | When |
| --- | --- | --- |
| **Single class** | `jocs/<Game>.java` | The game is one top-level type. |
| **Package** | `jocs/<game>/` with `<Game>.java` inside | The game has a second top-level type. |

- `<Game>` is the class registered in `GestorMapes`, the `Joc` subclass. Its simple name
  never changes on promotion: the registry, `createGameInstance`, the bot harness and
  the map menus resolve a game by simple name, and the template folder comes from
  `getGameName()`.
- `<game>` is `<Game>` lowercased, nothing else: `obsidiandefenders`, `inkwars`,
  `rainbowclay`, `torres`. No abbreviation, no judgment call.
- **Everything the game owns lives in its package.** No `Obsidian*.java` siblings beside
  `ObsidianDefenders.java`; no `InkWars.java` beside `inkwars/`. The test tree mirrors the
  same package, because tests use package-private access.
- **What more than one game can use is not a game's.** `Turret`, `minions`, the swept-box
  collision: shared substrate lives outside `jocs`, even while only one game uses it yet.
- **Promotion is triggered by the second top-level type**, never by line count. A long
  single class is allowed; a prefixed sibling is not. Demotion is never required.
- **Only the entry class is public.** Every other type in a game package is
  package-private. A game never imports another game's package; what two games share is
  lifted to a shared home first (see `minions`, and the sweep below).
- **Inner classes stay inner.** Team classes (`EquipInkWars`, `EquipCaça`), player-info
  classes and the enums a game's rules read are part of the entry class, not the trigger
  for a package.

## Why this shape

The flat folder was "one file per game", and it silently stopped being that. On
2026-09-09 it held 36 files for 28 games: Obsidian Defenders had seven `Obsidian*`
siblings, Rainbow Clay one, and Ink Wars kept its entry class outside its own `inkwars/`
subpackage. Every one of those siblings was package-private in `jocs`, which made
Obsidian Defenders' internals visible to all 28 games. A game package turns
package-private into what it should mean: the game's internals, hidden from the other
games.

The package is a container, not a splitting program. A 3700-line entry class inside its
package is the intended state; it gets divided only when someone chooses to, feature by
feature.

A package is the cheap boundary here, not the expensive one: nothing outside a game
imports its collaborators, the registry keeps one import per game, and `git mv`
preserves history. What we do not do is split games into many packages by feature; a
game is one package, and its features are files inside it.

## Anatomy of a game package

```
jocs/obsidiandefenders/
  package-info.java        one paragraph: the game in a sentence, then each collaborator's role
  ObsidianDefenders.java   public; the Joc subclass; composes the collaborators in initialize()
  GoldScore.java           package-private collaborators, named without the game prefix
  TeamUpgrades.java        (the package already says "obsidian")
  Watchtowers.java
  LauncherController.java
  LauncherTrajectory.java
  UpgradeController.java
  ...
```

- `package-info.java` is required. The tree is the map; the paragraph is what a cold
  reader gets before opening 3700 lines.
- Collaborators drop the game prefix. `ObsidianGoldScore` in `jocs` was a namespace
  workaround; `obsidiandefenders.GoldScore` says the same thing once.
- **Collaborators are composed, not looked up.** The entry class constructs them in
  `initialize()` and hands them the world, the plugin and closures (`LauncherController`
  takes `player -> team id` lambdas). None of the Obsidian collaborators imports
  `ObsidianDefenders`, and that is why five of them have server-free unit tests. A
  collaborator that genuinely needs the game takes it as a constructor argument
  (`Turret(…, Torres joc, …)`, today), never through a static lookup.
- Server-independent calculation (the `inkwars` physics, `GoldScore`, `LauncherTrajectory`)
  and Bukkit-bound controllers (`LauncherController implements Listener`) live side by
  side in the same package. The test says which is which; a second package level is not
  needed.

## Promotion procedure

1. `git mv jocs/<Game>.java jocs/<game>/<Game>.java`, and the same for every sibling and
   for each of their tests under `src/test`. One commit, explicit paths.
2. Fix the `package` line in every moved file; add `import com.biel.lobby.mapes.jocs.<game>.<Game>;`
   to `GestorMapes` (its `jocs.*` wildcard does not descend). Fix any fully qualified
   name outside Java: today that is the `calibrateObsidianLaunchers` task in `build.gradle`.
3. Search for what the moved files import from other games. Anything found is lifted to a
   shared home in a commit of its own, before the move.
4. Second commit: drop the game prefix from the collaborators and their tests, and add
   `package-info.java`.
5. `./gradlew build`. The game's name in the menu, the template folder, the guide slug
   and the bot scripts are unchanged by construction; a `git grep` for the old FQN must
   come back empty.

## The moves, 2026-09-09

In dependency order. Each line is one or two commits.

| Step | Change | Status |
| --- | --- | --- |
| 1 | This document; the layout paragraph in `README.md` points here. | written, uncommitted |
| 2 | Lift `inkwars.SquidCollision` to `utilities.SweptBoxCollision` (+ its test). It is a generic swept-box sweep against world collision boxes, used by Ink Wars and by Obsidian Defenders' launcher trajectory; `utilities` is where `Vec`, `Matrix` and `Cuboid` already live. Removes the only game-to-game import. | pending |
| 3 | Ink Wars: move `InkWars.java`, `InkWarsLifecycleTest`, `InkWarsMovementTest` into `inkwars/`. Package exists; nothing to rename. | pending |
| 4 | Obsidian Defenders: `ObsidianDefenders` + 7 siblings + 6 tests into `obsidiandefenders/`; `build.gradle` main class; then the prefix-drop commit. `ObsidianInteractions` (portal geometry + loot velocity + lookout sign text) is a grab-bag name; splitting it is rename debt, not part of the move. | pending |
| 5 | Rainbow Clay: `RainbowClay` + `RainbowClaySnapshotPublisher` → `rainbowclay/SnapshotPublisher`. `AgentSnapshotHttpServer` is generic and stays in `agent`. `lobby.java` keeps its public call. | pending |

Torres stays a single class. `Turret` is shareable across game modes and stays in
`utilities`; that it still takes a `Torres` in its constructor (for the turret list, the
balancing multiplier and the gameplay scheduler) is the debt that stops a second game from
using it, and it is untangled on its own, not by this layout.

Not moved: `minions` (shared by Obsidian Defenders and Torres, already outside `jocs`),
the 25 single-class games, and the `mapes` base classes.

## Later, and deliberately separate

- **Dividing Obsidian Defenders itself**, if ever. The entry class has 19 `//----------`
  regions (bridge, control points, jungle chests, Guardian, infernal star, sudden death,
  snowmen, scoreboard, …); each would be a package-private collaborator in the shape
  above. Nothing in this layout asks for it.
- **The Catalan identifiers** (`jocs`, `mapes`, `Joc`, `JocEquips`, `Equip`, `Parada`,
  `Mercaderia`) are kid-era rename debt. This layout is written against the names as they
  are and survives that rename untouched: `jocs/` → `games/` moves every game package the
  same way.
