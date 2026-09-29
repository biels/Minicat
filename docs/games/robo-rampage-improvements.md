# Robo Rampage: easier combat, mobility and finite lives

Implemented design for Biel's 2026-09-29 request. The goal is a forgiving cooperative
climb with readable threats, useful escape tools and a real failure condition.

## Combat and pacing

- Ordinary ground robots have 12 HP (previously 20); iron-head heavies have 22
  (previously 36). Redstone heads have 10 and lapis heads 14. Movement is slower;
  zombie chassis are always adults. Keep the visible iron armor and existing sword
  scaling. A fully charged diamond-sword hit should kill an ordinary robot, while
  a heavy should take two; verify actual Paper armor/damage events on dev.
- Start players with a chainmail chestplate and boots. Taser pulses deal 3 damage
  rather than 2; recharge begins after one second and restores base charge in
  twenty seconds. Upgrades retain their capacity, chaining and recharge benefit.
- Boss health is 140 solo, plus 75 per additional player, capped at 365. Armored
  damage intake rises from 35% to 55%; recovery remains 150%. Preserve the full
  warning and interruption windows so lowering difficulty also teaches counterplay.
- A director opportunity occurs every three seconds rather than two. Normal wave
  quotas are `min(22, 4 + wave + 2 * (players - 1))`; population limits are smaller,
  with one Cutter and one Ghast at most. The supply pause lasts 18 seconds.
- Introduce chassis separately: Worker on wave 1, sword Guard on 2, Springer on 3,
  Cutter on 4, Compactor on 5, Gold Drone on 6 and Iron Artillery on 7. Bosses still
  replace each fifth wave. Guarantee the first spawn of a newly introduced chassis.
  Block-head variants enter only from wave 8. Guarantee a missing ranged kit in
  wave-five supplies, before flying enemies arrive.

## Tools and spring robots

- The rechargeable Jetpack replaces the player piston-jump control. Its feather
  item toggles thrust with right-click; switching items stops it. It carries three
  seconds of fuel, recharges on supported ground in six seconds after a one-second
  delay, and preserves fuel on respawn. It uses bounded velocity, not creative
  flight. Its landing shield is single-use and expires five seconds after thrust.
  Reaching height in the air still cannot satisfy the settled-scrap win condition.
- Springers wear a piston head and golden boots. They compress for one second,
  then make a bounded hop toward a nearby participant, followed by five seconds
  of recovery. The Taser interrupts compression. Invalid targets and cleanup
  release their held AI. They use ordinary melee damage, with no additional slam.
- TNT serves as the second major weapon. Blast power increases from 3 to 4 and
  robot blast damage gains a 1.5 multiplier. No player takes TNT damage. Exposed
  active players and tracked robots receive a bounded upward impulse after native
  explosion processing; players receive single-use landing protection for up to
  eight seconds. Walls, original terrain and settled scrap remain protected;
  scaffolds collapse and nearby tracked TNT chains with the original attribution.

## Lives and outcome

- Each starting participant has three total lives. A death consumes one, preserves
  their inventory, armor and Taser upgrades, and respawns them on supported terrain.
  Duplicate death delivery cannot consume another life before respawn.
- At zero, the player becomes a spectator. The remaining team continues. When all
  roster slots have zero lives or have been deliberately abandoned, end the match,
  clean up all gameplay state and return viewers to the lobby after ten seconds.
- Disconnect/reconnect grace keeps the same lives; reconnect and respawn cannot
  refill them. A dropped participant with lives still has the existing two-minute
  grace. Grace expiry retires their slot. Late arrivals spectate and cannot create
  a fresh pool of lives in an existing match.
- Keep the mode unrated and reuse the existing non-winner persistence path; adding
  first-class cooperative success/failure storage is outside this gameplay change.

## Teaching and feedback

- Opening chat contains only the height goal, scrap rule and lives rule.
- On the first actual appearance of each chassis or block-head variant, show its
  name and one sentence of counterplay beneath it, and retain the sentence in chat.
  Encounter captions take priority over queued tool tips. Space lessons seven
  seconds apart and never repeat a lesson within the match.
- Introduce Taser and Jetpack controls gradually. Explain TNT and scaffolding when
  the first supply pause gives players time to use them; explain corrective junk
  when it actually arrives. Author English and Catalan together.
- The scoreboard includes personal lives and robots remaining, switching the latter
  to a seconds countdown during supplies.

## Acceptance

Run the full plugin suite and localization validation. Exercise actual jetpack
ascent, landing and recharge; TNT robot kills plus player lift and health; three
real deaths with equipment retained; elimination while a teammate continues; a
full wipe; Springer windup/interruption; captions; reconnect; and complete world
cleanup in disposable dev instances. Unit checks do not certify client motion or
actual encounter feel. Measure a natural solo and co-op run before further tuning
height or adding more weapons.
