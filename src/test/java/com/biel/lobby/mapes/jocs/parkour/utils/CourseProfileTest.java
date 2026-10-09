package com.biel.lobby.mapes.jocs.parkour.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CourseProfileTest {
    private static final String PROFILE = """
            {
              "schemaVersion": 1, "engine": "spiral3",
              "start": {"entry": {"x":1.5,"y":-61,"z":60.5,"yaw":0,"pitch":10},
                        "trigger": {"x":1,"y":-61,"z":63}},
              "finish": {"center":{"x":0,"y":310,"z":100},"radius":30},
              "failure": {"minY":-64,"hazardMaterials":["LAVA","FIRE"],
                          "damageCauses":["FALL","VOID"],"defaultMaxDrop":null},
              "checkpoints": [{"id":"plate-3-144-62","trigger":{"x":3,"y":144,"z":62},
                               "safePosition":{"x":3.5,"y":144.0625,"z":62.5},"maxDrop":null}]
            }
            """;

    @Test void parsesCompleteSemanticsWithoutInferringPlateRoles() {
        CourseProfile profile = CourseProfile.parse(new StringReader(PROFILE));
        assertEquals("spiral3", profile.engine());
        assertEquals(10, profile.start().entry().pitch());
        assertEquals(1, profile.checkpoints().size());
        assertEquals(144.0625, profile.checkpoints().getFirst().safePosition().y());
        assertEquals(Set.of("LAVA", "FIRE"), profile.failure().hazardMaterials());
        assertThrows(UnsupportedOperationException.class, () -> profile.checkpoints().clear());
    }

    @Test void rejectsUnknownVersionsEnginesAndIncompleteProfiles() {
        assertThrows(IllegalArgumentException.class, () -> parse(PROFILE.replace("\"schemaVersion\": 1", "\"schemaVersion\": 2")));
        assertThrows(IllegalArgumentException.class, () -> parse(PROFILE.replace("spiral3", "mystery")));
        assertThrows(IllegalArgumentException.class, () -> parse(PROFILE.replace("\"minY\":-64,", "")));
        assertThrows(IllegalArgumentException.class, () -> parse(PROFILE.replace("\"radius\":30", "\"radius\":0")));
        assertThrows(IllegalArgumentException.class, () -> parse(PROFILE.replace("\"y\":144,", "\"y\":144.5,")));
    }

    @Test void duplicateIdsOrTriggersCannotShadowEachOther() {
        CourseProfile profile = parse(PROFILE);
        CourseProfile.Checkpoint checkpoint = profile.checkpoints().getFirst();
        assertThrows(IllegalArgumentException.class, () -> new CourseProfile(1, "spiral3", profile.start(), profile.finish(),
                profile.failure(), List.of(checkpoint, checkpoint)));
        assertThrows(IllegalArgumentException.class, () -> new CourseProfile(1, "spiral3", profile.start(), profile.finish(),
                profile.failure(), List.of(new CourseProfile.Checkpoint("start-shadow", profile.start().trigger(),
                profile.start().entry(), null))));
    }

    @Test void triggerRequiresFootContactAndFinishKeepsTheOriginalSphere() {
        CourseProfile profile = parse(PROFILE);
        CourseProfile.BlockPosition plate = profile.checkpoints().getFirst().trigger();
        assertTrue(plate.touches(3.5, 144.0625, 62.5));
        assertTrue(plate.touches(2.9, 144.03125, 62.5), "player footprint can overlap the plate edge");
        assertFalse(plate.touches(3.5, 145, 62.5), "jumping above a plate cannot claim it");
        assertFalse(plate.touches(2.69, 144.0625, 62.5));
        assertTrue(profile.finish().contains(0, 280, 100));
        assertFalse(profile.finish().contains(0, 279.9, 100));
    }

    @Test void intentionalDescentsHaveNoGlobalCheckpointHeightFailure() {
        CourseProfile profile = parse(PROFILE);
        CourseProfile.Position anchor = profile.checkpoints().getFirst().safePosition();
        assertFalse(profile.failure().belowCheckpoint(-61, anchor, null));
        assertFalse(profile.failure().contains(0, -64, 0));
        assertTrue(profile.failure().contains(0, -64.01, 0));
        assertTrue(profile.failure().belowCheckpoint(130, anchor, 10.0));
        assertFalse(profile.failure().belowCheckpoint(140, anchor, 10.0));
    }

    @Test void declaredFailureSurfacesMatchOnlyTheInclusiveMaterialLayer() {
        CourseProfile profile = parse(PROFILE.replace("\"defaultMaxDrop\":null",
                "\"defaultMaxDrop\":null,\"surfaces\":[{\"material\":\"bedrock\",\"minY\":-64,\"maxY\":-63}]"));
        assertEquals(List.of(new CourseProfile.Surface("BEDROCK", -64, -63)), profile.failure().surfaces());
        assertTrue(profile.failure().contacts("BEDROCK", -64));
        assertTrue(profile.failure().contacts("BEDROCK", -63));
        assertFalse(profile.failure().contacts("BEDROCK", -62));
        assertFalse(profile.failure().contacts("BEDROCK", -65));
        assertFalse(profile.failure().contacts("WATER", -64));
        assertFalse(profile.failure().contacts("AIR", -64));
        assertThrows(UnsupportedOperationException.class, () -> profile.failure().surfaces().clear());
        assertTrue(parse(PROFILE).failure().surfaces().isEmpty(), "legacy profiles keep no inferred failure floor");
    }

    @Test void malformedSurfaceBoundsAndMaterialsAreRejected() {
        String surface = "\"defaultMaxDrop\":null,\"surfaces\":[{\"material\":\"BEDROCK\",\"minY\":-64,\"maxY\":-64}]";
        String json = PROFILE.replace("\"defaultMaxDrop\":null", surface);
        assertThrows(IllegalArgumentException.class, () -> parse(json.replace("\"maxY\":-64", "\"maxY\":-65")));
        assertThrows(IllegalArgumentException.class, () -> parse(json.replace("\"minY\":-64", "\"minY\":-64.2")));
        assertThrows(IllegalArgumentException.class, () -> parse(json.replace("\"BEDROCK\"", "\"\"")));
        assertThrows(IllegalArgumentException.class, () -> parse(json.replace("\"BEDROCK\"", "\"minecraft:bedrock\"")));
    }

    private static CourseProfile parse(String json) { return CourseProfile.parse(new StringReader(json)); }
}
