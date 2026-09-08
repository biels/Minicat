package com.biel.lobby.mapes.jocs.inkwars;

import java.lang.reflect.Proxy;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

/** Standalone checks for turbo trails sharing the hose's parcel physics. */
public final class InkStreamTest {
    public static void main(String[] args) {
        InkStream.Load load = new InkStream.Load(0.25, 0.4, 0, 0.05);
        InkStream stream = new InkStream();
        Location start = new Location(null, 2, 4, 6);
        Vector velocity = new Vector(1, 0, 0);
        stream.emitTrail(start, start.clone(), velocity, load);
        require(stream.isEmpty(), "stationary squid emits no paint");

        stream.emitTrail(start, start.clone().add(1, 0, 0), velocity, load);
        List<InkStream.Flight> flights = stream.flights();
        require(flights.size() == 3, "one-block segment gets three parcels");
        for (int index = 0; index < flights.size(); index++) {
            InkStream.Flight flight = flights.get(index);
            close(2 + (index + 0.5) / 3, flight.position().getX(), "deterministic path sample");
            close(4, flight.position().getY(), "no vertical spawn offset");
            close(6, flight.position().getZ(), "no lateral spawn offset");
            require(Double.isFinite(flight.velocity().lengthSquared()), "finite parcel motion");
            if (index > 0) require(flight.position().distance(flights.get(index - 1).position()) <= 0.35,
                    "normal travel samples at most 0.35 blocks apart");
        }
        close(1, velocity.getX(), "input velocity preserved");
        close(2, start.getX(), "input location preserved");

        Block floor = (Block) Proxy.newProxyInstance(Block.class.getClassLoader(), new Class<?>[]{Block.class},
                (proxy, method, arguments) -> { throw new AssertionError("Unexpected block call " + method.getName()); });
        World world = (World) Proxy.newProxyInstance(World.class.getClassLoader(), new Class<?>[]{World.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("rayTrace")) {
                        Location origin = (Location) arguments[0];
                        Vector step = ((Vector) arguments[1]).clone();
                        close((0.08) * InkStream.DRAG, step.getX(), "inherited and backwards spill motion");
                        close((-0.035 - load.gravity()) * InkStream.DRAG, step.getY(), "normal gravity and drag");
                        return new RayTraceResult(origin.toVector().add(step), floor, BlockFace.UP);
                    }
                    throw new AssertionError("Unexpected world call " + method.getName());
                });
        List<InkStream.Landing> landings = stream.advance(world, entity -> false);
        require(landings.size() == 3 && stream.isEmpty(), "landed trail parcels leave flight queue");
        for (InkStream.Landing landing : landings) {
            require(landing.load() == load, "landing keeps supplied ink, splash, sting, and gravity");
            require(landing.block() == floor, "normal block landing used");
        }

        stream.emitTrail(start, start.clone().add(12, 0, 0), new Vector(12, 0, 0), load);
        flights = stream.flights();
        require(flights.size() == 8, "fast travel emission is bounded");
        require(flights.get(0).position().getX() < 3 && flights.get(7).position().getX() > 13,
                "capped samples still cover full fast movement segment");
        stream.clear();
        stream.emitTrail(start, start.clone().add(0, 1, 0), new Vector(), load);
        for (InkStream.Flight flight : stream.flights()) {
            require(Double.isFinite(flight.velocity().lengthSquared()), "zero body velocity remains finite");
        }
        System.out.println("InkStream trail checks passed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void close(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 1e-8) throw new AssertionError(message + ": " + actual);
    }
}
