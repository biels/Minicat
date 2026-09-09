/**
 * What Obsidian Defenders' rules compute and remember, with no listener, no scheduler and no
 * knowledge of the game class; every type here is checked without a server.
 * <ul>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.utils.GoldScore}: each team's owned gold plus completed purchases, the number the lookout shows.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.utils.TeamUpgrades}: what a team has bought this match (launchers, archers, armor), in server ticks.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.utils.Watchtowers}: launcher layout calculations and each launcher's reload.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.utils.LauncherTrajectory}: the one airborne impulse a launcher gives, chosen so the landing is safe.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.utils.Interactions}: sign text and loot movement.</li>
 * </ul>
 */
package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;
