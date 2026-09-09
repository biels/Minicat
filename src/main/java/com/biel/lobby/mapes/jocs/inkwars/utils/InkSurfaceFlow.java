package com.biel.lobby.mapes.jocs.inkwars.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Where the wet ink on a block can go. Ink lives on the face it was laid on: on a floor it spreads to the neighbours and steps or falls off the edge,
 * on a wall it runs down and drips off the bottom onto the floor at the foot, under a ceiling it drips straight down.
 * Pure geometry: the game decides how much moves and who owns it.
 */
public final class InkSurfaceFlow {
	private static final BlockFace[] SIDES = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
	private static final int DROP_SEARCH = 12;

	/** The face the ink sits on and how much ink that face holds before the excess starts to move. */
	public enum Face {
		FLOOR(1.2), WALL(0.5), CEILING(0.3), BURIED(Double.POSITIVE_INFINITY);

		public final double holds;

		Face(double holds) {
			this.holds = holds;
		}
	}

	/** A block the ink may move to; the heavier the weight, the more gravity favours it. */
	public record Outlet(Block target, double weight) {}

	public record Surface(Face face, List<Outlet> outlets) {}

	private InkSurfaceFlow() {}

	public static Surface of(Block wet, Predicate<Block> solid) {
		Block above = wet.getRelative(BlockFace.UP);
		if (!solid.test(above)) return new Surface(Face.FLOOR, floorOutlets(wet, solid));
		List<BlockFace> openSides = new ArrayList<>(4);
		for (BlockFace side : SIDES) if (!solid.test(wet.getRelative(side))) openSides.add(side);
		if (!openSides.isEmpty()) return new Surface(Face.WALL, wallOutlets(wet, openSides, solid));
		Block below = wet.getRelative(BlockFace.DOWN);
		if (!solid.test(below)) {
			List<Outlet> outlets = new ArrayList<>(1);
			Block landing = firstSolidBelow(below, solid);
			if (landing != null) outlets.add(new Outlet(landing, 3));
			return new Surface(Face.CEILING, outlets);
		}
		return new Surface(Face.BURIED, List.of());
	}

	/** Along the floor to a neighbour at the same level, down a step, or over an edge onto whatever is below. */
	private static List<Outlet> floorOutlets(Block wet, Predicate<Block> solid) {
		List<Outlet> outlets = new ArrayList<>(4);
		for (BlockFace side : SIDES) {
			Block neighbour = wet.getRelative(side);
			if (solid.test(neighbour)) {
				if (!solid.test(neighbour.getRelative(BlockFace.UP))) outlets.add(new Outlet(neighbour, 1));
				else outlets.add(new Outlet(neighbour, 0.2)); // the foot of a rising wall takes a little
				continue;
			}
			Block step = neighbour.getRelative(BlockFace.DOWN);
			if (solid.test(step)) {
				outlets.add(new Outlet(step, 1.5));
				continue;
			}
			Block landing = firstSolidBelow(step, solid);
			if (landing != null) outlets.add(new Outlet(landing, 2.5));
		}
		return outlets;
	}

	/** Down the wall first, off its bottom onto the floor at the foot, a little sideways. */
	private static List<Outlet> wallOutlets(Block wet, List<BlockFace> openSides, Predicate<Block> solid) {
		List<Outlet> outlets = new ArrayList<>(4);
		Block below = wet.getRelative(BlockFace.DOWN);
		for (BlockFace open : openSides) {
			Block inFront = wet.getRelative(open);
			Block belowInFront = inFront.getRelative(BlockFace.DOWN);
			if (solid.test(below) && !solid.test(belowInFront)) {
				outlets.add(new Outlet(below, 4)); // the wall continues down
			} else if (solid.test(belowInFront)) {
				outlets.add(new Outlet(belowInFront, 3)); // the floor at the foot of the wall
			} else {
				Block landing = firstSolidBelow(belowInFront, solid);
				if (landing != null) outlets.add(new Outlet(landing, 3)); // drips off the bottom edge
			}
			for (BlockFace side : SIDES) {
				if (side == open || side == open.getOppositeFace()) continue;
				Block beside = wet.getRelative(side);
				if (solid.test(beside) && !solid.test(beside.getRelative(open))) outlets.add(new Outlet(beside, 0.5));
			}
		}
		return outlets;
	}

	private static Block firstSolidBelow(Block air, Predicate<Block> solid) {
		Block probe = air;
		for (int i = 0; i < DROP_SEARCH; i++) {
			probe = probe.getRelative(BlockFace.DOWN);
			if (solid.test(probe)) return probe;
		}
		return null;
	}
}
