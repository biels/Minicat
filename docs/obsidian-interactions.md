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

The shared `Punt de Guaita` hologram is centered at `(662.5,77,-1391.5)`,
above leaf block `(662,73,-1392)`. The current template's highest leaves are at
Y=73; this broad crown offers more standing room than the narrow northern peak.
Air above the crown is clear through Y=78, and no chest or block is replaced.
The optional `Lookout` map property specifies the supporting block, not the
hologram's position; the display is centered four blocks above it.

The title is followed by exactly two rows: `Or total: <red> vs <blue>` and
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
on match cleanup. Its visibility is shared by both teams and spectators through
the existing hologram system.

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
