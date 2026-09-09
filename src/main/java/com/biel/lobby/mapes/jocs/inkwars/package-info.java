/**
 * Ink Wars: teams paint the map with ink and the larger share of it wins.
 * <p>
 * {@link com.biel.lobby.mapes.jocs.inkwars.InkWars} is the game: the kit with its hose, ink balls
 * and roller, the squid form that swims over its own ink on floors, walls and ceilings, the
 * teams and the score. The rest is the ink and the squid as calculations, without a server:
 * <ul>
 * <li>{@link com.biel.lobby.mapes.jocs.inkwars.InkSplash}: a burst of ink cast as rays from an impact.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.inkwars.InkStream}: the hose jet, parcels of ink flying their own arcs.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.inkwars.InkSurfaceFlow}: where wet ink spreads, runs and drips from the face it lies on.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.inkwars.WetInk}: the ink still wet on one block, who laid it and how much is left.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.inkwars.SquidMotion}: the geometry of surface transitions and free flight.</li>
 * </ul>
 */
package com.biel.lobby.mapes.jocs.inkwars;
