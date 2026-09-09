/**
 * Obsidian Defenders: two teams, an obsidian-clad TNT base each, and the match ends when a
 * base's TNT goes off. Gold nuggets are the economy. The first game the server had (2013).
 * <p>
 * {@link com.biel.lobby.mapes.jocs.obsidiandefenders.ObsidianDefenders} is the game and composes
 * the rest in {@code initialize()}:
 * <ul>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.GoldScore}: each team's owned gold plus completed purchases, the number the lookout shows.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.TeamUpgrades}: what a team has bought this match (archers, armor, launchers), in server ticks.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.UpgradeController}: the upgrade signs and the purchase flow behind them.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.Watchtowers}: the four surveyed watchtower layouts and their match-local state.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.LauncherController}: the watchtower launchers as a listener: who may use one, when it reloads.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.LauncherTrajectory}: the one airborne impulse a launcher gives, chosen so the landing is safe.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.Interactions}: the map's fixed positions, sign text and loot movement.</li>
 * </ul>
 */
package com.biel.lobby.mapes.jocs.obsidiandefenders;
