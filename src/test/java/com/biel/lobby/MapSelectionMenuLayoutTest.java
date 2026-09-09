package com.biel.lobby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MapSelectionMenuLayoutTest {
	@Test
	void keepsRankingLastAndAddsOnlyRowsNeededForBothActions() {
		assertLayout(0, 9);
		assertLayout(25, 27);
		assertLayout(26, 36);
		assertLayout(27, 36);
		assertLayout(52, 54);
		assertThrows(IllegalArgumentException.class, () -> MapSelectionMenuLayout.sizeForGames(53));
	}

	private static void assertLayout(int games, int expectedSize) {
		int size = MapSelectionMenuLayout.sizeForGames(games);
		assertEquals(expectedSize, size);
		assertEquals(size - 2, MapSelectionMenuLayout.languageSlot(size));
		assertEquals(size - 1, MapSelectionMenuLayout.rankingSlot(size));
	}
}
