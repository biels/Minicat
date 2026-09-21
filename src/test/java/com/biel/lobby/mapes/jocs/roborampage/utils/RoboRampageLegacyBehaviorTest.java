package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/** Golden contract for the observable rules in the October 2015 implementation. */
class RoboRampageLegacyBehaviorTest {
    private static final Path GOLDEN = Path.of(
            "src/test/resources/roborampage/legacy-behavior.txt");

    @Test
    void recoveredBehaviorMatchesGoldenSnapshot() throws Exception {
        String actual = snapshot();
        if ("1".equals(System.getenv("UPDATE_GOLDEN"))) {
            Files.createDirectories(GOLDEN.getParent());
            Files.writeString(GOLDEN, actual, StandardCharsets.UTF_8);
        }
        assertEquals(Files.readString(GOLDEN, StandardCharsets.UTF_8), actual,
                "A golden diff is a semantic Robo Rampage change and must be explained");
    }

    private static String snapshot() {
        List<String> lines = new ArrayList<>();
        lines.add("starting-kit=DIAMOND_SWORD:1");
        lines.add("player-damage-multiplier=" + RoboRampageRules.PLAYER_DAMAGE_MULTIPLIER);
        lines.add("robot-damage-multiplier=" + RoboRampageRules.ROBOT_DAMAGE_MULTIPLIER);
        lines.add("power-up=ticks:" + RoboRampageRules.POWER_UP_TICKS
                + ",amplifier:" + RoboRampageRules.POWER_UP_AMPLIFIER);
        for (int height : List.of(0, 1, 2, 3, 8, 32)) {
            var limits = RoboRampageRules.legacySpawnLimits(height);
            lines.add("height=" + height + ",zombies=" + limits.zombies()
                    + ",skeletons=" + limits.skeletons() + ",blazes=" + limits.blazes()
                    + ",total=" + limits.total());
        }
        for (boolean iron : List.of(false, true)) {
            for (boolean redstone : List.of(false, true)) {
                lines.add("zombie-helmet=" + iron + "," + redstone + ":"
                        + RoboRampageRules.zombieHelmet(iron, redstone));
            }
        }
        for (boolean iron : List.of(false, true)) {
            for (boolean redstone : List.of(false, true)) {
                for (boolean lapis : List.of(false, true)) {
                    lines.add("skeleton-helmet=" + iron + "," + redstone + "," + lapis + ":"
                            + RoboRampageRules.skeletonHelmet(iron, redstone, lapis));
                }
            }
        }
        for (var helmet : RoboRampageRules.HelmetVariant.values()) {
            lines.add("ground-reward=" + helmet + ":" + RoboRampageRules.groundRobotReward(helmet));
        }
        lines.add("blaze-reward=" + RoboRampageRules.blazeReward());
        return String.join("\n", lines) + "\n";
    }
}
