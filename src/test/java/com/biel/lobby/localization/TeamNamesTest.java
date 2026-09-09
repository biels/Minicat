package com.biel.lobby.localization;

import org.bukkit.DyeColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamNamesTest {
    @Test void recognizesLegacyAndEnglishColorNamesWithoutChangingIdentity() {
        assertTrue(TeamNames.isColorName(DyeColor.RED, "vermell"));
        assertTrue(TeamNames.isColorName(DyeColor.RED, " RED "));
        assertTrue(TeamNames.isColorName(DyeColor.BLUE, "blau"));
        assertTrue(TeamNames.isColorName(DyeColor.LIGHT_GRAY, "silver"));
    }
    @Test void customNamesAndNamesForAnotherColorAreNotReinterpreted() {
        assertFalse(TeamNames.isColorName(DyeColor.RED, "Phoenix"));
        assertFalse(TeamNames.isColorName(DyeColor.RED, "blau"));
        assertFalse(TeamNames.isColorName(DyeColor.BLUE, "<red>Blue</red>"));
    }
}
