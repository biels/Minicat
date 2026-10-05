# In-game feedback through Feedbackr

Status: proposal, 2026-10-06. Nothing here is built yet.

Players should be able to tell us what is wrong or what they want without leaving the game,
and the report should arrive with the game context a screenshot cannot carry: which minigame,
map, round and team, where they stood, what the server was doing. Reports go to a `minicat`
project in Feedbackr, the same inbox and CLI (`feedbackr feedback list`) the browser client's
crash reports already use in its own `minecraft-web` project.

The browser client (`../minecraft-web`) already sends automatic crash and performance reports
to `minecraft-web`. That project is for defects of the web client;
this one is for the game and the server. Section 7 routes between the two.

## 1. What Feedbackr gives us to build on

- **Ingest** `POST {endpoint}/api/ingest/feedback` with the project's ingest key: a `kind`
  (`bug`, `feature`, `friction`, `idea`), `intentText`, a `reporter` (an `externalId` makes the
  reporter identified), an `environment` object, typed `anchors` and `artifacts` (`screenshot`,
  `log`, `state-dump`, `voice`, `video`). Plain JSON over HTTPS, so Java's `HttpClient` is
  enough; no SDK is needed on the server.
- **Anchors** are an open vocabulary: `world-3d`, `app-entity` and `custom` fit a minigame.
- **Drafts** are private and resumable. The creator chooses the draft's id and token, then
  others holding them can upload attachments and submit. This is what lets the server and a
  web client add to one report (section 5).
- **Receipts** come back with a private `reporterLink` where the reporter can follow the report.

## 2. Ways in, from cheapest to richest

1. **Chat commands.** `/bug <text>`, `/feature <text>`, `/idea <text>`, `/friction <text>`,
   and `/feedback` for the open kind (Feedbackr's own command convention). One line, sent at once, with full
   context attached. Works on every client.
2. **A native dialog.** `/feedback` with no text opens a Minecraft dialog: a kind selector
   (`SingleOptionDialogInput`), a multiline text box (`TextDialogInput`), a checkbox "Attach my
   position and game state", Send and Cancel. Paper 26.2's dialog API ships all of this
   (`io.papermc.paper.dialog`, confirmed in `paper-api-26.2.build.92-stable`). Dialogs are
   vanilla, so they work on Java clients and on the web client alike, with no resource pack.
3. **Moments that ask.** After a round, one clickable chat line: "How was this round?
   [Fun] [Unfair] [Broken] [Tell us more]". A click is a `friction`/`idea` report with the
   round's full context and almost no effort from the player. Ask at most once per player per
   few rounds, and never mid-game.
4. **In the lobby.** A feedback book or an NPC beside the map compass, opening the same dialog.

All player-facing text goes through the copy contracts (`src/main/resources/i18n/COPY.md`):
English and Catalan keys, no player input interpolated into MiniMessage, short buttons.

## 3. What the server attaches

This is where in-game feedback beats any web form:

| Context | Source | Shape |
| --- | --- | --- |
| Who | player UUID and name | `reporter.externalId` = UUID, `reporter.name` |
| Where | world, x/y/z, yaw/pitch | `world-3d` anchor |
| Which game | minigame class, map, instance id, phase/round, team, score, time into round | `app-entity` anchors (`minigame`, `map`, `instance`) and `environment.game` |
| What the game saw | the player's agent snapshot (`AgentSnapshotHttpServer.PublishedSnapshot`), which already describes what a player perceives | `state-dump` artifact |
| What just happened | the instance's recent event timeline (joins, deaths, kills, phase changes) and the reporter's own last chat lines | `log` artifact |
| Server health | TPS and MSPT over 1/5/15 minutes, online players, uptime, plugin build commit | `environment.server` |
| Connection | `Player.getPing()`, `getClientBrandName()`, protocol version, client locale (Triton) | `environment.client` |

Other players' chat is not attached.

## 4. A picture without a screenshot

A native client cannot send its screen. The server can still draw one:

- **A server-rendered map.** The 128×128 blocks around the player, coloured with the map-item
  palette (what a vanilla map shows), with the player's position and facing marked, and the
  arena's outline when a game is running. It is attached as the `screenshot` artifact. It is
  cheap, needs nothing from the client and often explains "stuck here" better than a
  first-person image.
- **An overview of the minigame**, the same renderer applied to the whole arena: where every
  team and objective was at the moment of the report.

## 5. Screenshots from the web client: a draft as the meeting point

The browser client can capture its own frame: `diagnostics()` already returns the game's last
presented picture. For a player on the web client:

1. The server creates a Feedbackr **draft** with the text and server context, choosing a random
   draft id and token.
2. It sends `{draftId, draftToken}` to that player's client on a plugin-message channel such as
   `minicat:feedback`.
3. A Minicat extension inside the web client passes the request up through the embedding API.
   The site, which already owns Feedbackr there, uploads the frame and the client diagnostics
   to that draft. The server submits once the upload arrives or a timeout passes.

The report then holds the first-person picture, the client's log and GPU, and the server's
game context in one item, with no account or login. The server learns a player uses the web
client from the gateway, or from the client brand once the client reports one.

## 6. Closing the loop with the player

- Keep the `fb_N` id and the receipt link per player (PostgreSQL). Reply in chat at once:
  "Thanks! Your report is fb_42 [open]".
- When the report becomes `shipped`, tell the player on their next join: "Your report fb_42
  was fixed in today's update." Get this by polling Feedbackr's API for the project's shipped
  items, or with a webhook if Feedbackr gains one. Telling people their report mattered is
  what keeps them reporting.
- Staff get `/feedback recent` to list the last reports with their game and map.

## 7. Routing: game or browser client

The dialog's kind selector gets one more choice for web-client players: "The game looks or
runs wrong in my browser". That choice goes to `minecraft-web` with the draft rendezvous above,
so it lands beside the automatic crash reports for the same release. Everything else goes to
`minicat`.

## 8. Automatic server reports (the server side of the client's crash reports)

- **Exceptions in game logic**: uncaught exceptions in event handlers and scheduler tasks,
  through a logging handler on the plugin logger. Each becomes a `bug` with the stack, the
  instance's state and timeline, deduplicated by exception class and top frames, at most one
  per signature per hour.
- **TPS collapse**: under 15 TPS for 30 seconds becomes one report per hour, with the busiest
  instances and their player counts, and a profiler link if spark is installed.

## 9. Limits

- One report per player per minute and 20 per day. Text up to 1,000 characters.
- Staff can mute a player's feedback.
- The dialog says what is attached; the context checkbox is on by default and can be cleared.
- The ingest key and endpoint live in server configuration, not in the repository.

## 10. Order of work

1. `/bug`, `/feature`, `/idea`, `/friction`, `/feedback` with server context, posted to a new `minicat`
   project (`feedbackr project init` in this repository).
2. The dialog and the post-round prompt.
3. Automatic server reports.
4. The server-rendered map image.
5. The web-client draft rendezvous: a plugin channel, a client extension and an embedding event.
6. Shipped notifications.

## Open questions

- Whether the post-round prompt belongs in every game, or only in new or changed ones.
- Whether Feedbackr should let the reporter add evidence from the receipt page. That would
  give native-client players a way to attach a real screenshot (F2) after the fact.
- Whether player reports should be visible to other players: a public "known issues" board in
  the lobby, fed by the project's open items.
