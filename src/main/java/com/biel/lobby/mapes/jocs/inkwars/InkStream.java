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
	/** Share of the velocity kept per tick in the air. */
	public static final double DRAG = 0.985;
	/** Ticks a parcel flies before it is lost as mist. */
	public static final int MAX_AGE_TICKS = 40;
	/** How far off its centre a parcel still counts as hitting a body. */
	public static final double PARCEL_RADIUS = 0.3;

	/** What one parcel carries: ink for the block it lands on, the radius of the splash it makes, the sting it gives a body, and the gravity that bends its arc. Set at the nozzle. */
	public record Load(double ink, double splashRadius, double sting, double gravity) {}

	/** Where a parcel came down: the point, the velocity it arrived with, the outward normal of what it hit, the block or the body, and what it carried. */
	public record Landing(Vector where, Vector velocity, Vector surfaceNormal, Block block, Entity body, Load load) {}

	private static final class Parcel {
		final Vector position;
		final Vector velocity;
		final Load load;
		int age = 0;

		Parcel(Vector position, Vector velocity, Load load) {
			this.position = position;
			this.velocity = velocity;
			this.load = load;
		}
	}

	private final List<Parcel> parcels = new ArrayList<>();
	private final Random random = new Random();

	/**
	 * Throws parcels from the nozzle along a direction: each gets the speed give or take a few percent and its own scatter,
	 * a gaussian of the given width per axis on the unit direction, so a wide nozzle sprays and a pinched one shoots a line.
	 */
	public void emit(Location nozzle, Vector direction, double speed, double scatter, Load load, int count) {
		Vector aim = direction.clone().normalize();
		for (int i = 0; i < count; i++) {
			Vector velocity = aim.clone().add(new Vector(random.nextGaussian(), random.nextGaussian(), random.nextGaussian()).multiply(scatter)).normalize();
			velocity.multiply(speed * (1 + 0.08 * random.nextGaussian()));
			parcels.add(new Parcel(nozzle.toVector().add(velocity.clone().multiply(0.3 * random.nextDouble())), velocity, load));
		}
	}

	/** Sheds ink along a resolved movement segment, using the same flight and landing as hose ink. */
	public void emitTrail(Location from, Location to, Vector bodyVelocity, Load load) {
		emitTrail(from, to, bodyVelocity, load, 0);
	}

	public void emitTrail(Location from, Location to, Vector bodyVelocity, Load load, double thrust) {
		Vector displacement = to.toVector().subtract(from.toVector());
		double distance = displacement.length();
		if (!Double.isFinite(distance) || !Double.isFinite(bodyVelocity.lengthSquared())) {
			throw new IllegalArgumentException("Trail movement must be finite");
		}
		if (distance < 1e-6) return;
		int count = Math.min(8, (int) Math.ceil(distance / 0.35));
		Vector inheritedVelocity = bodyVelocity.clone().multiply(0.18);
		if (inheritedVelocity.lengthSquared() > 0.45 * 0.45) inheritedVelocity.normalize().multiply(0.45);
		Vector spillVelocity = inheritedVelocity.subtract(displacement.clone().multiply(0.10 / distance));
		if(thrust > 0)spillVelocity.subtract(displacement.clone().multiply(0.3 * thrust / distance));
		spillVelocity.setY(spillVelocity.getY() - 0.035);
		// Midpoint samples cover the entire path without putting a nozzle offset through nearby walls.
		for (int sample = 0; sample < count; sample++) {
			Vector position = from.toVector().add(displacement.clone().multiply((sample + 0.5) / count));
			Vector dropletVelocity = spillVelocity.clone();
			if(thrust > 0){
				double scatter = 0.015 + 0.035 * (1 - Math.min(1, thrust));
				dropletVelocity.add(new Vector(random.nextGaussian(), random.nextGaussian(), random.nextGaussian()).multiply(scatter));
			}
			parcels.add(new Parcel(position, dropletVelocity, load));
		}
	}

	public boolean isEmpty() {
		return parcels.isEmpty();
	}

	/** A parcel in flight: where it is and where it is going, for drawing the jet. */
	public record Flight(Vector position, Vector velocity) {}

	/** Every parcel in flight right now. */
	public List<Flight> flights() {
		List<Flight> flights = new ArrayList<>(parcels.size());
		for (Parcel parcel : parcels) flights.add(new Flight(parcel.position.clone(), parcel.velocity.clone()));
		return flights;
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
			parcel.velocity.setY(parcel.velocity.getY() - parcel.load.gravity()).multiply(DRAG);
			double length = parcel.velocity.length();
			if (length > 1e-6) {
				Location from = parcel.position.toLocation(world);
				RayTraceResult hit = world.rayTrace(from, parcel.velocity, length, FluidCollisionMode.NEVER, true, PARCEL_RADIUS, bodies);
				if (hit != null) {
					Vector normal = hit.getHitBlockFace() != null ? hit.getHitBlockFace().getDirection() : parcel.velocity.clone().normalize().multiply(-1);
					landings.add(new Landing(hit.getHitPosition(), parcel.velocity.clone(), normal, hit.getHitBlock(), hit.getHitEntity(), parcel.load));
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
