package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;

import com.biel.lobby.utilities.SweptBoxCollision;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/** Chooses one natural airborne impulse; it never steers a player after launch. */
public final class LauncherTrajectory {
    // Airborne recurrence and Y/X/Z collision order used by prismarine-physics.
    public static final double HORIZONTAL_DRAG = 0.91;
    public static final double VERTICAL_DRAG = (double) (float) 0.98;
    public static final double GRAVITY = 0.08;
    private static final Vector HALF_BODY = new Vector(0.3, 0.9, 0.3);

    public interface Terrain {
        List<BoundingBox> obstacles(Vector feet, Vector movement);
        boolean leaves(Vector feet);
    }

    public static Vector find(Watchtowers.Tower tower, Vector origin, Terrain terrain) {
        Vector heading = new Vector(tower.rearX() + 0.5 + tower.direction() * 14 - origin.getX(),
                0, tower.middleZ() + 0.5 - origin.getZ()).normalize();
        for (int up = 8; up <= 18; up++) {
            for (int speed = 61; speed <= 77; speed++) {
                Vector impulse = heading.clone().multiply(speed * 0.05).setY(up * 0.1);
                if (safeLanding(tower, origin, impulse, terrain)) return impulse;
            }
        }
        return null;
    }

    public static boolean safeLanding(Watchtowers.Tower tower, Vector origin, Vector impulse, Terrain terrain) {
        Vector feet = origin.clone(), velocity = impulse.clone();
        for (int step = 0; step < 65; step++) {
            boolean landed = false;
            for (Vector movement : List.of(new Vector(0, velocity.getY(), 0),
                    new Vector(velocity.getX(), 0, 0), new Vector(0, 0, velocity.getZ()))) {
                var hit = SweptBoxCollision.sweep(feet.clone().add(new Vector(0, 0.9, 0)), HALF_BODY,
                        movement, terrain.obstacles(feet, movement));
                if (hit == null) feet.add(movement);
                else {
                    if (hit.startedInside || hit.normal.getY() < 0.5 || velocity.getY() >= 0) return false;
                    feet = hit.position.clone().subtract(new Vector(0, 0.9, 0));
                    landed = true;
                }
            }
            if (landed) {
                double distance = tower.direction() * (feet.getX() - (tower.rearX() + 0.5));
                if (distance < 30 || distance > 39 || feet.getY() < 44 || feet.getY() > 55
                        || Math.abs(feet.getZ() - (tower.middleZ() + 0.5)) > 4) return false;
                for (double x : new double[]{-0.2, 0, 0.2}) for (double z : new double[]{-0.2, 0, 0.2})
                    if (!terrain.leaves(feet.clone().add(new Vector(x, 0, z)))) return false;
                return true;
            }
            velocity.setX(velocity.getX() * HORIZONTAL_DRAG);
            velocity.setZ(velocity.getZ() * HORIZONTAL_DRAG);
            velocity.setY((velocity.getY() - GRAVITY) * VERTICAL_DRAG);
            if (feet.getY() < 40 || feet.getY() > 78) return false;
        }
        return false;
    }

    public static Terrain terrain(World world) {
        Map<Watchtowers.Position, List<BoundingBox>> cache = new HashMap<>();
        return new Terrain() {
            public List<BoundingBox> obstacles(Vector feet, Vector movement) {
                List<BoundingBox> result = new ArrayList<>();
                Vector end = feet.clone().add(movement);
                int minX = (int) Math.floor(Math.min(feet.getX(), end.getX()) - 0.3);
                int maxX = (int) Math.floor(Math.max(feet.getX(), end.getX()) + 0.3);
                int minY = (int) Math.floor(Math.min(feet.getY(), end.getY()));
                int maxY = (int) Math.floor(Math.max(feet.getY(), end.getY()) + 1.8);
                int minZ = (int) Math.floor(Math.min(feet.getZ(), end.getZ()) - 0.3);
                int maxZ = (int) Math.floor(Math.max(feet.getZ(), end.getZ()) + 0.3);
                for (int x = minX; x <= maxX; x++) for (int y = minY; y <= maxY; y++) for (int z = minZ; z <= maxZ; z++) {
                    var position = new Watchtowers.Position(x, y, z);
                    result.addAll(cache.computeIfAbsent(position, at -> {
                        Block block = world.getBlockAt(at.x(), at.y(), at.z());
                        return block.getCollisionShape().getBoundingBoxes().stream()
                                .map(box -> box.clone().shift(at.x(), at.y(), at.z())).toList();
                    }));
                }
                return result;
            }
            public boolean leaves(Vector feet) {
                return Tag.LEAVES.isTagged(world.getBlockAt((int) Math.floor(feet.getX()),
                        (int) Math.floor(feet.getY() - 0.05), (int) Math.floor(feet.getZ())).getType());
            }
        };
    }
}
