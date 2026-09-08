# Ink Wars movement checks

The squid owns its position and velocity; the display entity carries the camera.
Surface contacts use collision shapes in world coordinates. Movement is swept as
a body and subdivided at 0.15 blocks, with acceleration, drag and input edges
applied once per server tick. Painting remains sampled separately at half-block
intervals.

Run `./gradlew check --offline` for collision, momentum and controller checks.

## Playtest course

Use a disposable Ink Wars instance, not a map template. Build the following
stations from paintable blocks and start each approach on friendly ink:

| Station | Geometry | Acceptance |
| --- | --- | --- |
| Runway | 20-block flat lane | Acceleration and boost stay consistent; sideways controls agree with human form. |
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
