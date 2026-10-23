package com.biel.lobby.utilities.data;

import com.destroystokyo.paper.profile.ProfileProperty;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ForwardedAccountTest {
    @Test void readsStablePlayerIdAndExplicitOperatorFlag() {
        assertEquals(new ForwardedAccount(42, false), ForwardedAccount.fromProperties(List.of(
                new ProfileProperty("minicat:player_id", "42"), new ProfileProperty("minicat:operator", "false"))));
        assertTrue(ForwardedAccount.fromProperties(List.of(new ProfileProperty("minicat:player_id", "42"),
                new ProfileProperty("minicat:operator", "true"))).operator());
    }

    @Test void missingMalformedOrDuplicatePropertiesCannotGrantAnIdentity() {
        assertThrows(IllegalArgumentException.class, () -> ForwardedAccount.fromProperties(List.of()));
        for (String id : List.of("0", "-1", "1.0", "2147483648", "42 ")) {
            assertThrows(IllegalArgumentException.class, () -> ForwardedAccount.fromProperties(List.of(
                    new ProfileProperty("minicat:player_id", id), new ProfileProperty("minicat:operator", "false"))));
        }
        assertThrows(IllegalArgumentException.class, () -> ForwardedAccount.fromProperties(List.of(
                new ProfileProperty("minicat:player_id", "42"), new ProfileProperty("minicat:operator", "yes"))));
        assertThrows(IllegalArgumentException.class, () -> ForwardedAccount.fromProperties(List.of(
                new ProfileProperty("minicat:player_id", "42"), new ProfileProperty("minicat:player_id", "43"),
                new ProfileProperty("minicat:operator", "false"))));
    }
}
