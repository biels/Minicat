# Ink Wars movement checks

The squid owns its position and velocity; the display entity carries the camera.
Surface contacts use collision shapes in world coordinates. Movement is swept as
a body and subdivided at 0.15 blocks, with acceleration, drag and input edges
applied once per server tick. Painting remains sampled separately at half-block
intervals.

Run `./gradlew check --offline` for collision, momentum and controller checks.

Steering responds on the next server tick, including reversals. Quarter turns
retain 80% forward speed plus a short sideways slip; reversing retains 60% and
discards opposing motion. Releasing input coasts. Turbo redirects total momentum
toward the crosshair, projecting onto the attached surface unless aimed outward.
It has one tick of launch priority before steering resumes. Blocked launches do
not spend ink. Jump presses buffer for three ticks; floor edges allow two ticks
of late input. The carrier remains upright with fixed rotation.

Turbo sheds paint for 12 ticks, sampled every 0.25 blocks along resolved movement
segments with a maximum of eight parcels per tick. Parcels share hose gravity,
drag, impact splashes and painting, but deal no damage. They keep flying after
conversion to human form. Ordinary jumps do not generate this extra paint.

Squid audio uses quiet liquid cues for actual form changes, jumps and new surface
contacts. Form changes share a 180ms retrigger guard; contacts and jumps use 150ms.
Movement bubbles require resolved travel on a surface and occur at most every
16 ticks. Landing volume scales with incoming downward speed and caps at 0.5.
Turbo keeps the stronger squirt/pressure release and a watery tail three ticks
later. Reserve cues are private and rate-limited. Weapon/combat sounds are unchanged.

## Playtest course

Use a disposable Ink Wars instance, not a map template. Build the following
stations from paintable blocks and start each approach on friendly ink:

| Station | Geometry | Acceptance |
| --- | --- | --- |
| Runway | 20-block flat lane | Acceleration and boost stay consistent; sideways controls agree with human form. |
| Turns | Full-speed quarter turns and reversals | Immediate response, brief sideways slip, no backward braking phase. |
| Aimed turbo | Boost opposite travel, upward from floor, away from wall | First displacement follows aim even with opposite keys held; control returns the next tick. |
| Paint arc | Boost over a neutral landing area | Team-coloured droplets mark the flight and paint where they land, without damage. |
| Jump timing | Press just before landing or just after a ledge | One buffered/late jump, no repeat or unlimited air jumps. |
| Steps | Bottom slab, full block, alternating stair trim | Body follows actual shape heights without clipping or becoming stuck. |
| Climb | Six-block wall, approached head-on and obliquely | Intentional contact climbs from rest; a brush slides along the wall. |
| Corners | Inside and outside 90-degree bends | Held input and speed continue around the bend. |
| Window | Small seam followed by a two-block opening | Seam gets contact forgiveness; a real gap preserves the departure trajectory. |
| Overhang | Wall connected to a three-block ceiling | Climb bends onto the underside; jump detaches once. |
| Roof | Wall ending in a flat roof | Body clears the lip and held forward carries onto the roof. |
| Wall jump | Lateral run and upward climb | Departure keeps tangent velocity and adds outward/upward impulse; no immediate recapture. |
| Conversion | Floor, wall, ceiling and mid-jump | Aim and momentum survive; human body appears in clear space. |
| Clearance | One-block tunnel and blocked roof exit | Stand-up waits for room; zero reserve can retreat to the last clear exit instead of embedding. |

Repeat the route at rest, normal swimming speed and boosted speed. Check it in
first person as well as from another player's view. The camera must remain
upright and out of geometry. Listen for one contact/departure cue per transition,
not a repeated cue on every tick.

Ink capacity, refill/drain rates, weapon damage and human tools are outside this
movement pass. Evaluate camera comfort and timing in a real client; passing the
automated geometry checks alone does not establish those.
