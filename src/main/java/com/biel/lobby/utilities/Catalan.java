package com.biel.lobby.utilities;

/**
 * Catalan spelling for words that change with the word after them. The preposition
 * "de" contracts to "d'" before a vowel sound: "d'Obsidian Defenders", "d'Ink Wars",
 * "d'Albert", and, as Catalan speakers say it, "d'Spleef", since a leading s before a
 * consonant is read with an e in front; "de TNT Run", "de Biel" stay whole. Colour codes
 * in front of the name are skipped when deciding and kept in the result.
 */
public final class Catalan {
	private static final String VOWELS = "aeiouàèéíïòóúüAEIOUÀÈÉÍÏÒÓÚÜ";

	private Catalan() {
	}

	/** "de " + name, or "d'" + name when the name starts with a vowel sound. */
	public static String de(String name) {
		return (startsWithVowelSound(name) ? "d'" : "de ") + name;
	}

	private static boolean startsWithVowelSound(String name) {
		if (name == null) return false;
		int i = 0;
		while (i + 1 < name.length() && name.charAt(i) == '§') i += 2;
		while (i < name.length() && name.charAt(i) == ' ') i++;
		if (i >= name.length()) return false;
		char first = name.charAt(i);
		if (VOWELS.indexOf(first) >= 0) return true;
		char second = i + 1 < name.length() ? name.charAt(i + 1) : ' ';
		if ((first == 'h' || first == 'H') && VOWELS.indexOf(second) >= 0) return true;
		return (first == 's' || first == 'S') && Character.isLetter(second) && VOWELS.indexOf(second) < 0;
	}
}
