# Obsidian Defenders interactions

Approved on 2026-09-08 using the interior layer in the operations repository's
`docs/games/obsidian-defenders/lanes-map.html`.

## Shop shortcuts

| Team | Entrance center | Shop arrival center | Arrival yaw |
| --- | --- | --- | --- |
| Red (0) | 616.5, 41, -1422.5 | 613.5, 41, -1371.5 | 180 |
| Blue (1) | 712.5, 41, -1379.5 | 713.5, 41, -1428.5 | 0 |

Entrances are behind the respawn positions, approximately 3.5 and 4.5 blocks
away. Each has a team-colored Shop hologram and a wooden pressure plate.
Activation uses a two-block horizontal radius and less than one block of
vertical separation, checked every two ticks. Only living participants of the
owning team can enter. Respawning does not trigger a portal. Successful travel
clears velocity and fall distance and starts a two-second server-tick cooldown.

Optional map properties: `ShopPortal0`, `ShopPortal1`, `ShopArrival0`,
`ShopArrival1`, `ShopArrivalYaw0`, `ShopArrivalYaw1`. Location properties use
block coordinates with feet Y; the code centers X/Z. Invalid or obstructed
locations are skipped with a warning, and an obstructed arrival disables travel.
Match cleanup removes holograms and restores entrance blocks.

## Jungle loot

Player-initiated chest close claims all remaining stacks by clearing the
inventory before creating item entities. Metadata and stack sizes are retained.
The items are immediately pickable by the collector and move toward them for
up to one second. Actual collection uses native pickup and its sound. A full
inventory leaves real drops at the player's feet; disconnect, death, world
change and match cleanup release flying drops with normal gravity.
An empty chest returns to leaves on the following tick.

## Pickaxe arrival

The existing spawn schedule is unchanged. Arrival creates an aqua ball firework
with no trail, flicker or damage. Living participants within four horizontal
blocks and two vertical blocks receive a radial push, limited to 0.4 horizontal
and 0.2 vertical velocity. Nearby obstructions or missing ground reduce or
suppress the push. This is a brief arrival pulse, not a persistent safe zone.
Pickup is delayed ten ticks so a player standing on the spawn cannot collect
the pickaxe on the same tick it appears.

## Canopy Lookout

The shared `Punt de Guaita` oak sign stands at `(662,74,-1392)`,
on leaf block `(662,73,-1392)`, facing east/west with glowing text on both sides.
The sign is waxed to prevent editing. The current template's highest leaves are at
Y=73; this broad crown offers more standing room than the narrow northern peak.
Only air is replaced, and the original block is restored on match cleanup.
The optional `Lookout` map property specifies the supporting block, not the
sign's position; the sign stands one block above it.

The four lines are the title, `Or total`, `<red> vs <blue>` gold values, and
`Kills: <red> vs <blue>`. Gold score is currency currently held by the team plus
gold already spent on successful shop purchases and enchantments. It is displayed
in thousands of nuggets with two decimals (`1250` nuggets becomes `1.25k`). Exact
integer amounts are retained internally; display rounding is to the nearest ten
nuggets. A single Q drop changes the exact score even when rounded text stays put.

Spending preserves score. Dropping gold, depositing it in a chest, destruction,
inventory overflow or any other loss removes that gold until collected again.
The collecting player's team gets the recovered value, including enemy pickups;
repeated drops, pickups or transfers cannot inflate the combined team score.
Cursor stacks count as held, and ingots are worth ten nuggets, as in the shops.
Only actually paid purchases are retained as spending; failed payments add none.

Death with retained inventory and temporary disconnects retain the wallet balance.
Leaving or exhausting reconnect grace removes carried gold but keeps historical
purchases. True spectators do not contribute. Inventory changes are sampled after
clicks, drags, drops and pickups, with a once-per-second reconciliation for other
losses. No gold metadata or loot-generation markers are needed.
Kills reuse the match's existing team counters, including credited minion kills.

The display refreshes once per second, rewrites only changed text and is removed
on match cleanup. Both teams and spectators can read either side of the sign.

## Watchtower Launchers

The former sewer button sells the first base upgrade for 50 gold: both of that
team's towers receive three rear iron plates, an upper stone button and two
lower buttons. The other base buys independently. This does not consume bridge
charge. Installation is validated and rolled back before any failed payment;
successful purchases use the same held-plus-spent gold accounting as shops.

Tower rear rows are X=617 (red) / 709 (blue), Y=52, centered on Z=-1409/-1391.
Upper buttons are two blocks forward, lower buttons two blocks backward at
Y=42 and two blocks either side of the middle Z. Purchase buttons are
`611,42,-1369` and `715,42,-1431`. Exact layouts live in `ObsidianWatchtowers`.

Any living participant can operate an unlocked launcher, regardless of team.
One standing plate occupant launches per press: the activator if eligible,
otherwise the longest waiting player. Each tower has its own 100-tick reload;
empty presses do not spend it. The piston sound is sent to nearby players only.

A short lift is followed by a single natural airborne impulse selected from
the player's actual position. The existing swept-body collision helper checks
the whole route using actual block shapes and Y/X/Z collision order. The target
is lower leaves 30-39 blocks forward, with nine support samples at landing.
No clear route means no forward impulse. Camera direction is preserved; active
steering or combat can change the natural flight. Teleports and interruptions
cancel pending launches. The launch's first fall is protected, not later jumps
or other damage. All tasks, registrations and installed blocks follow match
cleanup, and the purchase resets between matches.

Offline calibration checked 39 starting positions per tower against a runtime
world survey, then independently replayed all 156 selected impulses through
Prismarine physics: all landed on lower leaves without a side collision.
`calibrateObsidianLaunchers` accepts `-PlauncherSurvey=...` (NBT export with
Prismarine collision shapes) and `-PlauncherReport=...` for reproducibility.

## Verification

`./gradlew build` includes `ObsidianInteractionsTest` (approved coordinates,
spawn exclusion, radius and floor boundaries, finite radial push and loot
velocity) and `verifyGameGuide` (19 authored pages, wrapping, includes and long
text). Existing movement regressions also run unchanged.
`ObsidianGoldScoreTest` checks retained spending, single/stack drops, partial and
enemy pickups, deposits/losses, transfers, reconnect/abandonment and match reset.

After deployment, run the operations repository's bot check:

```sh
cd bot-runtime
node scripts/obsidian-defenders-live-check.js --fresh --only teams,kit,portals
```

Check the current server log for both registered portal coordinates and no
exceptions. A visual gameplay pass is still needed to judge the pickup animation
and how the arrival pulse feels against opponents.
