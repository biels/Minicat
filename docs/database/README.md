# PostgreSQL persistence

Minicat uses PostgreSQL exclusively. Gameplay SQL stays in `DataAPI`; schema SQL
lives in `src/main/resources/db/migration`. The runtime checks the migration ledger
at startup and does not apply DDL with application credentials.

Provision an empty Minicat database, apply `V001__postgresql_baseline.sql` through
the operations migration tool, and install a restricted server-local
`plugins/lobby/database.properties` (see `database.properties.example`). The file
can instead be selected by `minicat.database.config` or `MINICAT_DATABASE_CONFIG`.
Do not commit the populated file. Startup fails explicitly if configuration,
PostgreSQL or the required schema version is unavailable.

The connection has bounded connect/socket/statement/lock timeouts. Main gameplay
operations serialize ownership of one JDBC connection; telemetry has an independent
bounded writer and drains on shutdown. A broken connection is replaced on a later
operation. Writes report actual execution success, and Elo increments are atomic.
This does not add a new match-rating ledger or move every read off the game thread.

The baseline preserves IDs, floating-point values, legacy rosters/winner meaning,
numeric item IDs and existing historical anomalies. Names use case/accent-insensitive
ICU comparisons with trailing-space-insensitive lookup/uniqueness. The game/map
names remain globally unique; conflicting map/game assignments fail explicitly.
Timestamp instants are stored with timezone; source imports use explicit UTC.

Run ordinary regressions with `./gradlew test`. Run real database integration tests
with `MINICAT_TEST_DATABASE_CONFIG=/path/to/properties ./gradlew postgresTest`.
The configured database must be named `minicat_test`; the test role must own it.
Tests create/drop a uniquely named schema there and never clear a shared schema.
The suite covers generated map IDs, naming, fractional money, reporting, telemetry
drain, atomic concurrent rating changes, failed-write acknowledgement and reconnect.

Operational export/import/equivalence/restore and cutover procedures live in
`minicat-repo/ops/database/postgresql`; do not run the retired MySQL dev container
as the new application backend.
