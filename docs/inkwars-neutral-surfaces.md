# InkWars neutral surface painting

## Translation rules

An ink hit converts a neutral block when the supported palette has a matching
shape. Every neutral stair and slab uses the concrete family; an already dyed
wool stair or slab stays wool. Conversion happens on the hit block, including
ownership and scoring, rather than on its support.

| Source | Painted family |
| --- | --- |
| Any undyed stair, including quartz, stone, brick, wood and copper | Colored concrete stairs |
| Any undyed slab, including top and double slabs | Colored concrete slabs |
| Clear or tinted glass | Stained glass |
| Clear glass pane or iron bars | Stained glass pane |
| Moss carpet | Colored carpet |
| Existing supported dyed families | Same family, team color |
| Other full occluding blocks | Colored terracotta, as before |

The existing `recolor` operation copies compatible BlockData before replacing the
block with physics disabled. Stair facing, half, corner shape and waterlogging,
slab type and waterlogging, and pane connections survive painting, drying,
rewetting and capture. Tinted glass takes the stained-glass appearance and light
behavior. Shapes with no matching colored geometry, such as fences, walls,
trapdoors and leaves, retain the existing support-painting rule. Barriers remain
protected. Templates are unchanged; a fresh game instance restores the originals.

Donor: `InkWars.java` at `cacc64d`, specifically `paint`, `getTeamOwningBlock`,
`getPaintMaterial`, `isPaintableUnsafely` and `recolor`. The correction shares a
neutral-family translation between eligibility and material selection, and uses
the same support-borrowing condition for painting and ownership. The web reference
checkout, including its `work/` sources, contains no InkWars implementation.

## Current maps inspected on 2026-10-20

Read-only copies came from the active 26.3 backend's
`mapes/inkwars` directory. NBT was decoded with the existing operations
`ops/map/survey-legacy-map.py` parser. All stored chunks matched their region
slots, with no duplicate sector pointers. Counts describe stored template blocks,
including buried blocks, rather than exposed or reachable scoring surfaces.

| Map | Undyed stairs | Undyed slabs, including doubles |
| --- | ---: | ---: |
| Canal | 1,394 | 5,625 |
| Castell | 0 | 0 |
| Districte 5 | 58 | 220 |
| Estratègia | 0 | 0 |
| Fàbrica | 38 | 0 |
| InkWars12 | 123 | 8 |
| Pilota_NRG | 772 | 2 |
| [Jan] L inkwars.net | 0 | 0 |
| [Jan] Nou mapa 2 | 441 | 256 |

The actual stair palette contains cobblestone, brick, stone brick, quartz, oak,
dark oak and sandstone. In particular, Fàbrica's 38 stairs and InkWars12's 123
stairs are quartz. Slabs include stone, sandstone, the old wood-textured stone
slab (petrified oak), cobblestone, brick, stone brick, nether brick, spruce, acacia
and dark oak, with upper and double variants. The empty `[Jan] L inkwars.net`
template was observed but not repaired as part of painting.

## Verification and release

2026-10-20: full Java 25 build passed: 238 tests, zero failures/errors, seven
database skips. Unit checks cover every target color for all 134 neutral stair
and slab materials, direct ownership, support isolation and paint/dry/rewet/capture.
The real Paper 26.3 fixture passed 8,071 assertions across 1,798 matrix cases,
including all 134 materials, full geometry/property combinations for map stair
families, slabs, glass, bars, carpet, unsupported-shape regressions, barriers and
fresh-instance restoration from an actual quartz-stair template.

The private browser join/disconnect/Stop check passed with the accepted
`7cd1a6c9` / `fbdb271d` client. Two inspected frames show actual painted quartz,
stone-brick, oak, smooth-stone and polished-andesite shape/state pairs beside
untouched originals. All 16 gallery samples retain exact compatible properties.
Later pause-covered frames are excluded from pixel evidence. All owned fixture
services stopped; the source runtime and public backend were untouched.

Candidate SHA256: `f8f74df37ddbfc3135dc953266ea0fd342be978f4072a3a69a4d506f2f246841`.
Raw palette and runtime results are under the web checkout's ignored
`.build/lane/inkwars-26.3/neutral-surfaces/`.

2026-10-20: after Biel's explicit approval, source commit `ed4534c` was deployed
with a zero-player graceful restart and a verified jar/profile rollback backup.
The running jar matches the candidate above; normal startup, all eight plugins,
database/HTTP health and an ordinary public protocol-777 login/logout passed.
Task policy, admission and configuration values were preserved. No full live
InkWars match is claimed. The operations repository's
`docs/operations/inkwars-painting-26.3.md` records the exact backup and receipts.
