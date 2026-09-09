package com.biel.lobby;

/** Stable reserved slots for the map menu's actions. */
public final class MapSelectionMenuLayout {
	private MapSelectionMenuLayout() {
	}

	public static int sizeForGames(int gameCount) {
		if (gameCount < 0) throw new IllegalArgumentException("gameCount must not be negative");
		int rows = (gameCount + 10) / 9;
		if (rows > 6) throw new IllegalArgumentException("Map menu exceeds six inventory rows");
		return rows * 9;
	}

	public static int languageSlot(int menuSize) {
		return menuSize - 2;
	}

	public static int rankingSlot(int menuSize) {
		return menuSize - 1;
	}
}
