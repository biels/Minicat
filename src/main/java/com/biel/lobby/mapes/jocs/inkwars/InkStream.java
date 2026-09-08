package com.biel.lobby.mapes.jocs.inkwars;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

/**
 * The jet of a hose: parcels of ink thrown from the nozzle, each flying its own arc under gravity and drag until it lands on a block or a body.
 * The nozzle scatters them a little, so the jet is tight at the hand and a spray at the end of its reach, and it reaches where it reaches:
 * aimed up it carries, aimed level it drops to the floor a few blocks out. Pure motion: it decides nothing about colour, score or damage.
 */
public final class InkStream {
	/** Blocks per tick squared; a hair above the players' gravity so the arc bends visibly. */
	public static final double GRAVITY = 0.06;
	/** Share of the velocity kept per tick in the air. */
	public static final double DRAG = 0.985;
	/** Gaussian scatter of the nozzle direction, per axis, in blocks per tick per block of speed. */
	public static final double SCATTER = 0.035;
	/** Ticks a parcel flies before it is lost as mist. */
	public static final int MAX_AGE_TICKS = 40;
	/** How far off its centre a parcel still counts as hitting a body. */
	public static final double PARCEL_RADIUS = 0.3;

	/** Where a parcel came down: the point, the velocity it arrived with, the outward normal of what it hit, and the block or the body. */
	public record Landing(Vector where, Vector velocity, Vector surfaceNormal, Block block, Entity body, double ink) {}

	private static final class Parcel {
		final Vector position;
		final Vector velocity;
		final double ink;
		int age = 0;

		Parcel(Vector position, Vector velocity, double ink) {
			this.position = position;
			this.velocity = velocity;
			this.ink = ink;
		}
	}

	private final List<Parcel> parcels = new ArrayList<>();
	private final Random random = new Random();

	/** Throws parcels from the nozzle along a direction: each gets the speed give or take a few percent and its own small scatter. */
	public void emit(Location nozzle, Vector direction, double speed, double ink, int count) {
		Vector aim = direction.clone().normalize();
		for (int i = 0; i < count; i++) {
			Vector velocity = aim.clone().add(new Vector(random.nextGaussian(), random.nextGaussian(), random.nextGaussian()).multiply(SCATTER)).normalize();
			velocity.multiply(speed * (1 + 0.08 * random.nextGaussian()));
			parcels.add(new Parcel(nozzle.toVector().add(velocity.clone().multiply(random.nextDouble())), velocity, ink));
		}
	}

	public boolean isEmpty() {
		return parcels.isEmpty();
	}

	/** Where every parcel is right now, for drawing the jet. */
	public List<Vector> positions() {
		List<Vector> positions = new ArrayList<>(parcels.size());
		for (Parcel parcel : parcels) positions.add(parcel.position.clone());
		return positions;
	}

	public void clear() {
		parcels.clear();
	}

	/**
	 * One tick of flight for every parcel: gravity, drag, then the step, which ends early on the first solid block or on a body the filter accepts.
	 * Returns what came down this tick; parcels that flew too long or fell out of the world are dropped.
	 */
	public List<Landing> advance(World world, Predicate<Entity> bodies) {
		List<Landing> landings = new ArrayList<>();
		Iterator<Parcel> iterator = parcels.iterator();
		while (iterator.hasNext()) {
			Parcel parcel = iterator.next();
			parcel.velocity.setY(parcel.velocity.getY() - GRAVITY).multiply(DRAG);
			double length = parcel.velocity.length();
			if (length > 1e-6) {
				Location from = parcel.position.toLocation(world);
				RayTraceResult hit = world.rayTrace(from, parcel.velocity, length, FluidCollisionMode.NEVER, true, PARCEL_RADIUS, bodies);
				if (hit != null) {
					Vector normal = hit.getHitBlockFace() != null ? hit.getHitBlockFace().getDirection() : parcel.velocity.clone().normalize().multiply(-1);
					landings.add(new Landing(hit.getHitPosition(), parcel.velocity.clone(), normal, hit.getHitBlock(), hit.getHitEntity(), parcel.ink));
					iterator.remove();
					continue;
				}
				parcel.position.add(parcel.velocity);
			}
			parcel.age++;
			if (parcel.age > MAX_AGE_TICKS || parcel.position.getY() < world.getMinHeight()) iterator.remove();
		}
		return landings;
	}
}
