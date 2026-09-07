package com.biel.lobby.minions;

import java.util.List;

import org.bukkit.Location;

/**
 * A team's lane: the waypoints from its base to the enemy's, in order. Minions do not
 * start at the first waypoint; they join the lane where they are and walk the rest.
 *
 * <p>Joining is by leg, not by waypoint: the nearest point of the polyline (measured flat,
 * the heights of a lane are only the ground under it) decides which leg the minion is on,
 * and the minion heads for that leg's far end. So one thrown mid-leg continues forward,
 * one beside a corner goes to the corner, one past the end holds the last waypoint, and
 * none ever turns back toward a waypoint behind it, which joining at the nearest waypoint
 * did whenever that waypoint was the one just passed.
 */
public final class Lane {
	private final List<Location> waypoints;

	private Lane(List<Location> waypoints) {
		this.waypoints = List.copyOf(waypoints);
	}

	/** {@code waypoints}: at least one, in marching order. */
	public static Lane of(List<Location> waypoints) {
		if (waypoints.isEmpty()) throw new IllegalArgumentException("a lane needs at least one waypoint");
		return new Lane(waypoints);
	}

	public List<Location> waypoints() {
		return waypoints;
	}

	/** The waypoints still ahead of {@code here}, starting with the far end of the leg it is nearest to. */
	public List<Location> ahead(Location here) {
		return waypoints.subList(joinIndex(here), waypoints.size());
	}

	/** Index of the first waypoint to walk to from {@code here}: the far end of the nearest leg. */
	int joinIndex(Location here) {
		if (waypoints.size() == 1) return 0;
		int join = 1;
		double nearest = Double.MAX_VALUE;
		for (int leg = 0; leg < waypoints.size() - 1; leg++) {
			double distance = flatDistanceSquaredToLeg(here, waypoints.get(leg), waypoints.get(leg + 1));
			// Strictly nearer only: at a shared corner the earlier leg wins, so the corner itself is the target.
			if (distance < nearest) {
				nearest = distance;
				join = leg + 1;
			}
		}
		return join;
	}

	private static double flatDistanceSquaredToLeg(Location p, Location a, Location b) {
		double abX = b.getX() - a.getX(), abZ = b.getZ() - a.getZ();
		double apX = p.getX() - a.getX(), apZ = p.getZ() - a.getZ();
		double lengthSquared = abX * abX + abZ * abZ;
		double t = lengthSquared == 0 ? 0 : Math.max(0, Math.min(1, (apX * abX + apZ * abZ) / lengthSquared));
		double dX = apX - t * abX, dZ = apZ - t * abZ;
		return dX * dX + dZ * dZ;
	}
}
