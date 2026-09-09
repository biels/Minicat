/**
 * Obsidian Defenders: two teams, an obsidian-clad TNT base each, and the match ends when a
 * base's TNT goes off. Gold nuggets are the economy. The first game the server had (2013).
 * <p>
 * {@link com.biel.lobby.mapes.jocs.obsidiandefenders.ObsidianDefenders} is the game and composes
 * the rest in {@code initialize()}:
 * <ul>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.LauncherController}: the watchtower launchers as a listener: who may use one, when it fires.</li>
 * <li>{@link com.biel.lobby.mapes.jocs.obsidiandefenders.UpgradeController}: the upgrade signs and the purchase flow behind them.</li>
 * <li>{@code utils}: the calculations and match-state models these lean on, checked without a server.</li>
 * </ul>
 */
package com.biel.lobby.mapes.jocs.obsidiandefenders;
