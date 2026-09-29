package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SupplyUpgradeClaimsTest {
    @Test void teammateCannotStealCoreAndDuplicateNativePickupCannotUpgradeTwice() {
        SupplyUpgradeClaims claims = new SupplyUpgradeClaims();
        UUID item = UUID.randomUUID(), owner = UUID.randomUUID(), teammate = UUID.randomUUID();
        claims.register(item, owner, 2);
        assertFalse(claims.claim(item, teammate));
        assertTrue(claims.claim(item, owner));
        assertFalse(claims.claim(item, owner));
        assertFalse(claims.claim(item, teammate));
    }

    @Test void regeneratedOrOlderCoresCannotAwardTheSameWaveTwiceButNextEarnedWaveCan() {
        SupplyUpgradeClaims claims = new SupplyUpgradeClaims();
        UUID owner = UUID.randomUUID(), original = UUID.randomUUID(), duplicate = UUID.randomUUID();
        claims.register(original, owner, 4);
        assertTrue(claims.claim(original, owner));
        claims.discardUnclaimed();
        claims.register(duplicate, owner, 4);
        assertFalse(claims.claim(duplicate, owner));
        claims.register(duplicate, owner, 2);
        assertFalse(claims.claim(duplicate, owner));
        claims.register(duplicate, owner, 6);
        assertTrue(claims.claim(duplicate, owner));
    }

    @Test void expiresUnclaimedItemsAndKeepsEachParticipantsEarnedWaveIndependent() {
        SupplyUpgradeClaims claims = new SupplyUpgradeClaims();
        UUID expired = UUID.randomUUID(), owner = UUID.randomUUID(), teammate = UUID.randomUUID();
        claims.register(expired, owner, 2);
        claims.discardUnclaimed();
        assertFalse(claims.claim(expired, owner));
        UUID first = UUID.randomUUID(), second = UUID.randomUUID();
        claims.register(first, owner, 2); claims.register(second, teammate, 2);
        assertTrue(claims.claim(first, owner)); assertTrue(claims.claim(second, teammate));
        claims.close();
        assertFalse(claims.claim(first, owner));
    }
}
