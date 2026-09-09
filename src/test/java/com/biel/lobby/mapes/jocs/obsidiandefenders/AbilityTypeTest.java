package com.biel.lobby.mapes.jocs.obsidiandefenders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class AbilityTypeTest {
    @Test
    void loadsEnglishAndLegacyCatalanNames() {
        assertEquals(ObsidianDefenders.Ability.AbilityType.SWORDSMAN,
                ObsidianDefenders.Ability.AbilityType.fromStoredName("SWORDSMAN"));

        Map.ofEntries(
                Map.entry("RESISTENCIA", ObsidianDefenders.Ability.AbilityType.RESISTANCE),
                Map.entry("COMANDANT", ObsidianDefenders.Ability.AbilityType.COMMANDER),
                Map.entry("ESPADATXI", ObsidianDefenders.Ability.AbilityType.SWORDSMAN),
                Map.entry("REGENERACIO_AUGMENTADA", ObsidianDefenders.Ability.AbilityType.ENHANCED_REGENERATION),
                Map.entry("ASSALT", ObsidianDefenders.Ability.AbilityType.ASSAULT),
                Map.entry("ARQUER_PERFECTE", ObsidianDefenders.Ability.AbilityType.PERFECT_ARCHER),
                Map.entry("ARQUER_DE_GEL", ObsidianDefenders.Ability.AbilityType.FROST_ARCHER),
                Map.entry("PROTECCIÓ_IMPACTE", ObsidianDefenders.Ability.AbilityType.IMPACT_PROTECTION),
                Map.entry("PIROTÈCNIC", ObsidianDefenders.Ability.AbilityType.PYROTECHNIC),
                Map.entry("ESQUELET_FORT", ObsidianDefenders.Ability.AbilityType.STRONG_SKELETON),
                Map.entry("CONTROL_GRAVETAT", ObsidianDefenders.Ability.AbilityType.GRAVITY_CONTROL),
                Map.entry("DESTRUCTOR", ObsidianDefenders.Ability.AbilityType.DESTROYER))
                .forEach((storedName, expected) -> assertEquals(expected,
                        ObsidianDefenders.Ability.AbilityType.fromStoredName(storedName)));
    }
}
