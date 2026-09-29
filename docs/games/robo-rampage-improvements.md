# Robo Rampage: combat pressure, mobility and finite lives

Implemented design for Biel's 2026-09-29 request. The goal is a forgiving cooperative
climb with readable threats, useful escape tools and a real failure condition.

## Combat and pacing

2026-09-30 playtest correction: restore group pressure rather than long gaps and
an all-purpose Taser. Ordinary robots retain 12 HP and charged sword kills;
chainmail starters, safe TNT, the Jetpack and three lives remain.

- Director groups arrive every second: two robots solo, three with teammates,
  always limited by the remaining quota and current chassis/population caps.
  Quota is `min(42, 10 + 2 * wave + 3 * (players - 1))`; active population ranges
  from 10 solo to 20 with a full team. Supply pauses last ten seconds.
- Workers start on wave one, Guards on two, Springers on three. Wave four
  guarantees both a Cutter and Gold Drone; wave six introduces Iron Artillery.
  Compactors retain every fifth wave. Ranged gear is guaranteed after wave three
  when missing, before flying enemies arrive. Block-head variants start on eight.
- Taser pulses return to two damage. A starting weapon supports two linked robots,
  growing to four at level five. Each energized robot consumes charge every tick,
  so chains spend the same finite damage budget faster. Capacity rises from 100
  to 140. Recovery starts after three seconds, at one charge per five ticks;
  upgrades do not accelerate it. Restart requires twenty charge, and toggling or
  switching weapons cannot bypass pulse cooldown.
- Upgrade cores arrive every second cleared wave, preserve actual remaining
  charge, belong to the intended player and can upgrade once per earned wave.
  Repeated pickup callbacks, another player's core or regenerated same-wave cores
  cannot grant additional levels.
- Boss health and the readable slam/throw warnings remain as in the previous
  release. The boss bar now shows only its localized name and health, with a
  fixed color; phase explanations stay out of that bar. Its arrival announcement
  names the boss without the obsolete instruction to wait for a green bar.

## Tools and spring robots

- The rechargeable Jetpack replaces the player piston-jump control. Its feather
  item toggles thrust with right-click; switching items stops it. It carries three
  seconds of fuel, recharges on supported ground in six seconds after a one-second
  delay, and preserves fuel on respawn. It uses bounded velocity, not creative
  flight. Its landing shield is single-use and expires five seconds after thrust.
  Reaching height in the air still cannot satisfy the settled-scrap win condition.
- Springers wear a piston head and golden boots. Ground hops retain a one-second
  compression warning and five-second recovery, interruptible with the Taser.
  Elevated players provoke pursuit toward owned scaffold steps: a robot approaches
  a supported launch point, checks a clear body route and landing, then makes a
  committed physical jump up to four blocks upward. Higher towers require valid
  intermediate steps. AI resumes for flight; a temporary movement goal prevents
  ordinary chasing from steering the jump. Changed targets, broken platforms,
  interruptions and cleanup release held AI and movement goals.
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
- Each chassis or head variant gets one short localized action-bar caption on
  first appearance. No encounter titles/subtitles or duplicate chat captions.
  Captions last four seconds, refresh during that interval, and briefly reserve
  the status slot so Taser/Jetpack charge messages cannot overwrite them. A new
  enemy takes priority over a control tip; lessons have a one-second gap.
- Taser/Jetpack controls and supply tips use the same brief status slot. English
  and Catalan are authored together.
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
