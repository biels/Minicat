package com.biel.lobby.mapes.jocs.inkwars.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

/**
 * A splash of ink cast as a fan of rays from the impact point.
 * Each ray walks the block grid and lays its ink on the first solid block it meets, so ink never passes a wall or turns a corner,
 * a splash stretches along the direction the shot came from, climbs the wall it hits, and a share of the rays bounce once for the satellite spots.
 * The rays' weights and lengths are modulated by value noise, so the outline is a splotch and never a circle. Pure geometry: it decides nothing about colour or score.
 */
public final class InkSplash {
	private static final double GOLDEN_ANGLE = Math.PI * (3 - Math.sqrt(5));

	/** Ink for one block, summed over every ray that reached it. */
	public record Deposit(Block block, double amount) {}

	/**
	 * @param radius how far the rays reach, the outline wobbles between 0.6 and 1.4 of it
	 * @param ink amount a typical ray lays on the block it hits; the centre, reached by many rays, gets several times that
	 * @param rays how many rays the fan casts
	 * @param forwardBias how much ink favours the direction the shot was travelling along the surface
	 * @param surfaceBias how much ink favours the surface that was hit over flying off it
	 * @param bounceShare share of rays that bounce once for a small satellite spot
	 */
	public record Shape(double radius, double ink, int rays, double forwardBias, double surfaceBias, double bounceShare) {
		public static Shape of(double radius, double ink) {
			int rays = (int) Math.max(32, Math.min(192, Math.round(20 * radius * radius)));
			return new Shape(radius, ink, rays, 1.5, 2.0, 0.15);
		}
	}

	private InkSplash() {}

	/**
	 * @param impact where the ink arrives, a little off the surface on the open side
	 * @param incoming the velocity the ink arrived with; straight down for a body hitting the ground
	 * @param surfaceNormal the outward normal of the surface hit, up for a floor
	 * @param stopsInk which blocks stop a ray and take the ink
	 */
	public static List<Deposit> cast(Location impact, Vector incoming, Vector surfaceNormal, Shape shape, Predicate<Block> stopsInk) {
		World world = impact.getWorld();
		Vector origin = impact.toVector();
		Vector normal = surfaceNormal.lengthSquared() < 1e-6 ? new Vector(0, 1, 0) : surfaceNormal.clone().normalize();
		Vector velocity = incoming.lengthSquared() < 1e-4 ? normal.clone().multiply(-1) : incoming.clone();
		Vector forward = velocity.clone().subtract(normal.clone().multiply(velocity.dot(normal)));
		boolean elongated = forward.lengthSquared() > 1e-4;
		if (elongated) forward.normalize();

		Random random = new Random(Double.doubleToLongBits(origin.getX() * 31 + origin.getY() * 17 + origin.getZ()) ^ System.nanoTime());
		ValueNoise noise = new ValueNoise(random.nextLong());
		double spin = random.nextDouble() * 2 * Math.PI;

		List<Vector> directions = new ArrayList<>(shape.rays());
		List<Double> weights = new ArrayList<>(shape.rays());
		double weightSum = 0;
		for (int i = 0; i < shape.rays(); i++) {
			double y = 1 - 2 * (i + 0.5) / shape.rays();
			double ring = Math.sqrt(Math.max(0, 1 - y * y));
			double angle = i * GOLDEN_ANGLE + spin;
			Vector direction = new Vector(ring * Math.cos(angle), y, ring * Math.sin(angle));
			direction.add(new Vector(random.nextGaussian(), random.nextGaussian(), random.nextGaussian()).multiply(0.15)).normalize();

			double towardSurface = -direction.dot(normal);
			double surfaceWeight = towardSurface >= 0 ? 0.25 + shape.surfaceBias() * towardSurface : 0.25 * (1 + towardSurface);
			double forwardWeight = 1;
			if (elongated) {
				double along = direction.dot(forward);
				forwardWeight = along >= 0 ? 1 + shape.forwardBias() * along : 1 - 0.5 * -along;
			}
			double noiseWeight = 0.5 + noise.at(direction.getX() * 2.5, direction.getY() * 2.5, direction.getZ() * 2.5);
			double weight = surfaceWeight * forwardWeight * noiseWeight;
			directions.add(direction);
			weights.add(weight);
			weightSum += weight;
		}
		double meanWeight = weightSum / shape.rays();

		Map<Block, Double> deposits = new LinkedHashMap<>();
		for (int i = 0; i < directions.size(); i++) {
			Vector direction = directions.get(i);
			double reach = shape.radius() * (0.6 + 0.8 * noise.at(direction.getX() * 1.7 + 7, direction.getY() * 1.7 + 7, direction.getZ() * 1.7 + 7));
			double rayInk = shape.ink() * weights.get(i) / meanWeight;
			Hit hit = walk(world, origin, direction, reach, stopsInk);
			if (hit == null) continue;
			double travelled = Math.min(reach, hit.distance);
			double thinning = Math.max(0.15, 1 - 0.7 * travelled / reach);
			double incidence = 0.4 + 0.6 * Math.abs(direction.dot(hit.faceNormal));
			deposits.merge(hit.block, rayInk * thinning * incidence, Double::sum);

			if (random.nextDouble() < shape.bounceShare()) {
				Vector bounce = direction.clone().subtract(hit.faceNormal.clone().multiply(2 * direction.dot(hit.faceNormal)));
				bounce.add(new Vector(random.nextGaussian(), random.nextGaussian(), random.nextGaussian()).multiply(0.5)).normalize();
				Hit satellite = walk(world, hit.entry, bounce, reach * 0.6, stopsInk);
				if (satellite != null) deposits.merge(satellite.block, rayInk * 0.3, Double::sum);
			}
		}
		List<Deposit> result = new ArrayList<>(deposits.size());
		for (Map.Entry<Block, Double> entry : deposits.entrySet()) result.add(new Deposit(entry.getKey(), entry.getValue()));
		return result;
	}

	private record Hit(Block block, Vector faceNormal, Vector entry, double distance) {}

	/** Walks the grid from origin along direction until a block stops the ink, or the reach runs out. */
	private static Hit walk(World world, Vector origin, Vector direction, double reach, Predicate<Block> stopsInk) {
		if (!(direction.lengthSquared() > 1e-9) || !Double.isFinite(origin.getX() + origin.getY() + origin.getZ())) return null;
		int steps = (int) Math.ceil(reach) + 1;
		BlockIterator iterator;
		try {
			iterator = new BlockIterator(world, origin, direction, 0, steps);
		} catch (IllegalStateException startBlockMissed) { // BlockIterator refuses some starts on a block boundary; that ray is lost
			return null;
		}
		Block previous = null;
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (stopsInk.test(block)) {
				Vector centre = block.getLocation().toVector().add(new Vector(0.5, 0.5, 0.5));
				double distance = Math.max(0, centre.clone().subtract(origin).dot(direction) - 0.5);
				if (distance > reach) return null;
				Vector faceNormal;
				Vector entry;
				if (previous == null) {
					faceNormal = direction.clone().multiply(-1);
					entry = origin.clone();
				} else {
					faceNormal = previous.getLocation().toVector().subtract(block.getLocation().toVector());
					if (faceNormal.lengthSquared() < 1e-6) faceNormal = direction.clone().multiply(-1);
					faceNormal.normalize();
					entry = previous.getLocation().toVector().add(new Vector(0.5, 0.5, 0.5));
				}
				return new Hit(block, faceNormal, entry, distance);
			}
			previous = block;
		}
		return null;
	}

	/** Three-dimensional value noise in [0, 1]: hashed lattice values, smoothly interpolated. */
	static final class ValueNoise {
		private final long seed;

		ValueNoise(long seed) {
			this.seed = seed;
		}

		double at(double x, double y, double z) {
			int x0 = (int) Math.floor(x), y0 = (int) Math.floor(y), z0 = (int) Math.floor(z);
			double fx = smooth(x - x0), fy = smooth(y - y0), fz = smooth(z - z0);
			double c000 = lattice(x0, y0, z0), c100 = lattice(x0 + 1, y0, z0);
			double c010 = lattice(x0, y0 + 1, z0), c110 = lattice(x0 + 1, y0 + 1, z0);
			double c001 = lattice(x0, y0, z0 + 1), c101 = lattice(x0 + 1, y0, z0 + 1);
			double c011 = lattice(x0, y0 + 1, z0 + 1), c111 = lattice(x0 + 1, y0 + 1, z0 + 1);
			double x00 = c000 + (c100 - c000) * fx, x10 = c010 + (c110 - c010) * fx;
			double x01 = c001 + (c101 - c001) * fx, x11 = c011 + (c111 - c011) * fx;
			double y0v = x00 + (x10 - x00) * fy, y1v = x01 + (x11 - x01) * fy;
			return y0v + (y1v - y0v) * fz;
		}

		private double lattice(int x, int y, int z) {
			long h = seed ^ (x * 0x9E3779B97F4A7C15L) ^ (y * 0xC2B2AE3D27D4EB4FL) ^ (z * 0x165667B19E3779F9L);
			h ^= h >>> 31;
			h *= 0x7FB5D329728EA185L;
			h ^= h >>> 27;
			h *= 0x81DADEF4BC2DD44DL;
			h ^= h >>> 33;
			return (h >>> 11) * 0x1.0p-53;
		}

		private static double smooth(double t) {
			return t * t * (3 - 2 * t);
		}
	}
}
