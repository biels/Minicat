package com.biel.lobby.mapes.jocs.inkwars;

/** Ink still wet on a block: who laid it and how much is left. It dries by one per second and flows while it is above what the surface holds. */
public final class WetInk {
	public final String painterName;
	public double amount;

	public WetInk(String painterName, double amount) {
		this.painterName = painterName;
		this.amount = amount;
	}
}
