package com.biel.lobby.minions;

import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.entity.Mob;

/**
 * What a {@link LaneMinion} is: its body and its numbers. Kinds differ only in these, so
 * a game declares a kind as one value and enlists as many as it likes (design:
 * minicat-repo docs/games/obsidian-defenders/snowman-turrets-design.md, "The Torres roster").
 *
 * @param label player-facing name, "Esquelet wither"
 * @param maxHealth hit points
 * @param attackDamage the ATTACK_DAMAGE attribute the melee goal's hit reads
 * @param reach blocks within which it swings
 * @param meleeCooldownTicks ticks between swings
 * @param acquireRadius blocks within which it picks a target and pursues it
 * @param speed pathfinder speed multiplier for the march and the pursuit
 * @param body spawns and dresses the mob at a spot; goals and attributes are set afterwards
 */
public record LaneMinionKind(String label, double maxHealth, double attackDamage, double reach, int meleeCooldownTicks, double acquireRadius, double speed, Function<Location, Mob> body) {
}
