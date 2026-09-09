# Minicat
Public minigame minecraft server

> IP: bcn.protocolapp.net:12346

> Telegram: [https://telegram.me/servidorminicat](https://telegram.me/servidorminicat)

Please, use the issues tab to report bugs or to suggest enchancements.

## Development

Use Java 25. Build the paired `../SpigotBielAPI` checkout with `mvn clean install`
first, then run `./gradlew clean build` here. JUnit discovers tests under
`src/test/java`; `./gradlew test --tests '*InkWarsLifecycleTest'` runs one suite.

Game rules and server integration live in `src/main/java/com/biel/lobby/mapes/jocs`:
a game is one class, or one package once it has a second type, per
[the game package layout](docs/game-package-layout.md). Shared minion behavior
lives in `minions`.

Deploy the paired plugins using the guarded procedure in
[`../minicat-repo/docs/operations/paper-26.2-operations.md`](../minicat-repo/docs/operations/paper-26.2-operations.md).
That repository owns deployment scripts and operational evidence; this one owns
the plugin and its tests. Do not copy artifacts into the legacy server tree.

Persistence now uses PostgreSQL only; see [database setup and verification](docs/database/README.md).
