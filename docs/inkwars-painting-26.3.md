# InkWars painting on 26.3

## Scope and starting point

Add painting for all 16 colors of wool slabs, wool stairs, concrete slabs and
concrete stairs: 64 new blocks. These are the four new complete block families in
the [official 26.3 release notes](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-3),
confirmed against Paper API `26.3.build.200-stable` and the runtime's four block tags.
Cushions are entities; partial-color poplar leaves and older unsupported block-entity
families are outside this change.

Start from Minicat `6c99971` / `origin/master`, which already pins 26.3.
The original `/Users/biel/git/Minicat` checkout is one commit behind and has unrelated
untracked files. Implementation uses `/Users/biel/git/worktrees/minicat-inkwars-26.3`;
runtime qualification uses `/Users/biel/git/worktrees/minicat-ops-inkwars-26.3` from
operations `e96e684`. Preserve the completed upgrade and concurrent browser polish.

## Existing implementation and correction

Donor: `src/main/java/com/biel/lobby/mapes/jocs/inkwars/InkWars.java`:
`paint`, `rewet`, drying in `tickWetInk`, `getTeamOwningBlock`, and the family mapping.
Historical donor `becb19b` introduced the eight-family mapping. Current source/history
have no complete 26.3 implementation; the web reference checkout has no InkWars source.

1. Extend the explicit supported-family mapping with `_WOOL_SLAB`, `_WOOL_STAIRS`,
   `_CONCRETE_SLAB`, `_CONCRETE_STAIRS`. Do not discover arbitrary colored materials.
2. Paint and identify ownership on these blocks themselves. Only unsupported shapes
   retain the existing rule of borrowing the block underneath. Preserve `isShape`'s
   geometric meaning for squid movement: it also controls step-versus-wall behavior.
3. Use one recoloring operation in paint, drying and rewet: create the target material's
   BlockData, copy compatible source properties using the pinned API, then set it with
   physics disabled. Preserve slab type/waterlogging and stair facing/half/shape/waterlogging,
   plus compatible existing glazed-terracotta and pane properties.
4. Retain existing wet-ink cancellation, drying colors and one-location scoring.
   Same-team repaint never earns another point; enemy takeover transfers one point.
   Keep existing clear glass/pane/bars and generic solid fallback behavior.
5. Reset remains the existing `MapaResetejable` template/instance lifecycle. No new
   snapshot system, game rules, commands, map edits or client rendering changes.

## Verification and execution

One GPT-6.1 Sol worker owns implementation and focused unit tests; a second owns a
real Paper fixture in the operations repository. Use medium reasoning for bounded work,
high only for difficult diagnosis. Review the combined change once.

Unit coverage: all four families and 16 source/target colors; substrate-independent
paint and ownership; same-team repaint, enemy capture and wet-ink cancellation;
drying/rewet; old-family and unsupported-shape regressions. Run existing InkWars
movement/lifecycle suites and the normal plugin build with Java 25 and the qualified
private dependency cache. Do not silently resolve the stale shared 26.2 dependency.

Real Paper coverage: execute actual InkWars calls against all new families and the
slab/stair property matrix; assert resulting material, BlockData, wet state and score.
Check connected stairs and nearby blocks, and template/fresh-instance reset using
the existing generated-world lifecycle donor. Reuse the 26.3 fixture and existing
drivers; use a new disposable loopback runtime and database, not live maps. Record
the candidate source/artifact identity and runtime assertions. Visual evidence is
required before claiming the new painted shapes look correct in the browser.

Serialize builds and fixture/browser runs through
`/Users/biel/git/minecraft-web/.build/heavy-lock.sh run <owner> ...` (never nested).
Keep raw logs under `.build/lane/inkwars-26.3` in the web checkout and small worker
results plus coordinator state under `.build/coordinator/inkwars-26.3` there.

The coordinator wakes every 25 minutes, inspects one compact snapshot, reviews new
results and assigns the next bounded step. No idle polling or repeated passing tests.
The existing inventory-polish timer remains independent.

Verified small changes may be integrated and pushed under the repository workflow.
The public Minicat backend is production, not a development server: prepare a concrete
qualified artifact and rollback/deployment proposal before requesting production
rollout authorization. Do not deploy it under the web-development authorization.

## Status

2026-10-18: plan grounded in current code, pinned API/runtime and official release
notes. Implementation and disposable-server qualification are the next steps.

2026-10-18 checkpoint: implementation and focused InkWars tests pass; full build has
236 tests, zero failures/errors and seven database skips. Product diff reviewed;
frozen candidate SHA256 `5ae3514035d025e958df9c8c5de9619591b8f3ec19faa050dff3186ea99715f1`.
Real Paper qualification and private browser gallery evidence remain in progress.
No integration or deployment yet.

2026-10-18 Paper checkpoint: 4,202/4,202 assertions and 1,199 matrix cases pass on
the frozen candidate, including full slab/stair properties through color transitions,
score transfer and fresh-map reset. Owned services stopped and temporary live folders
removed. Browser gallery acceptance and production rollout proposal are active.

2026-10-18 acceptance: private protocol-777 browser join/Stop passed and six frames
show actual painted shapes beside white controls. Root inspected concrete stair
orientation/corners and wool waterlogged/top/double slab pixels. The full palette
overview is partly cropped; exhaustive colors/properties are established by server
assertions, not a claim of per-color pixel comparison. Browser used the accepted
`7cd1a6c9` / `fbdb271d` client. All owned services stopped. Product is qualified for
integration; the paired jar/profile installer is being prepared before production
rollout approval. No production deployment has occurred.
