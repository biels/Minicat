package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/** Offline calibration against a read-only NBT survey with Prismarine block shapes. */
public final class LauncherCalibration {
    record Palette(String Name) {}
    record Survey(int[] bounds, List<Palette> palette, int[] indices, double[][][] shapes) {}
    record Trial(int tower, double[] origin, double[] impulse) {}

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        Survey survey = gson.fromJson(Files.readString(Path.of(args[0])), Survey.class);
        List<Watchtowers.Tower> towers = configuredTowers(Path.of(args[2]));
        int[] bounds = survey.bounds;
        Map<String, List<BoundingBox>> cache = new HashMap<>();
        var terrain = new LauncherTrajectory.Terrain() {
            private int index(int x, int y, int z) {
                if (x < bounds[0] || y < bounds[1] || z < bounds[2] || x >= bounds[3] || y >= bounds[4] || z >= bounds[5])
                    throw new IllegalArgumentException("Outside surveyed terrain: " + x + "," + y + "," + z);
                return survey.indices[((x - bounds[0]) * (bounds[4] - bounds[1]) + y - bounds[1])
                        * (bounds[5] - bounds[2]) + z - bounds[2]];
            }
            public List<BoundingBox> obstacles(Vector feet, Vector movement) {
                Vector end = feet.clone().add(movement);
                List<BoundingBox> result = new ArrayList<>();
                for (int x = (int) Math.floor(Math.min(feet.getX(), end.getX()) - 0.3); x <= Math.floor(Math.max(feet.getX(), end.getX()) + 0.3); x++)
                    for (int y = (int) Math.floor(Math.min(feet.getY(), end.getY())); y <= Math.floor(Math.max(feet.getY(), end.getY()) + 1.8); y++)
                        for (int z = (int) Math.floor(Math.min(feet.getZ(), end.getZ()) - 0.3); z <= Math.floor(Math.max(feet.getZ(), end.getZ()) + 0.3); z++) {
                            String key = x + "," + y + "," + z;
                            List<BoundingBox> boxes = cache.get(key);
                            if (boxes == null) {
                                boxes = new ArrayList<>();
                                for (double[] shape : survey.shapes[index(x, y, z)]) boxes.add(new BoundingBox(
                                        x + shape[0], y + shape[1], z + shape[2], x + shape[3], y + shape[4], z + shape[5]));
                                cache.put(key, boxes);
                            }
                            result.addAll(boxes);
                        }
                return result;
            }
            public boolean leaves(Vector feet) {
                return survey.palette.get(index((int) Math.floor(feet.getX()), (int) Math.floor(feet.getY() - 0.05),
                        (int) Math.floor(feet.getZ()))).Name.endsWith("_leaves");
            }
        };
        List<Trial> trials = new ArrayList<>();
        int failed = 0;
        long started = System.nanoTime();
        for (var tower : towers) {
            int accepted = 0;
            for (double x : new double[]{-0.19, 0, 0.19}) for (int z = -6; z <= 6; z++) {
                Vector origin = new Vector(tower.rearX() + 0.5 + x,
                        tower.plateY() + 0.5625, tower.middleZ() + 0.5 + z * 0.19);
                Vector impulse = LauncherTrajectory.find(tower, origin, terrain);
                trials.add(new Trial(tower.id(), array(origin), impulse == null ? null : array(impulse)));
                if (impulse == null) failed++; else accepted++;
            }
            System.out.println("Tower " + tower.id() + ": " + accepted + "/39 collision-checked launch positions");
        }
        Files.writeString(Path.of(args[1]), gson.toJson(trials));
        System.out.printf("%d trials, %.1f ms per trial%n", trials.size(), (System.nanoTime() - started) / 1e6 / trials.size());
        if (failed > 0) throw new AssertionError(failed + " launch positions have no safe impulse");
    }

    private static double[] array(Vector vector) { return new double[]{vector.getX(), vector.getY(), vector.getZ()}; }

    private static List<Watchtowers.Tower> configuredTowers(Path propertiesPath) throws Exception {
        Properties properties = new Properties();
        try (var reader = Files.newBufferedReader(propertiesPath, StandardCharsets.ISO_8859_1)) {
            properties.load(reader);
        }
        List<Watchtowers.Tower> towers = new ArrayList<>();
        for (int team = 0; team < 2; team++) {
            int direction = Integer.parseInt(properties.getProperty("LauncherDirection" + team));
            int lowerButtonY = Integer.parseInt(properties.getProperty("LauncherLowerButtonY" + team));
            for (int index = 0; ; index++) {
                String value = properties.getProperty("LauncherTower" + team + "_" + index);
                if (value == null) break;
                String[] coordinates = value.split(",");
                towers.add(new Watchtowers.Tower(towers.size(), team, Integer.parseInt(coordinates[0]),
                        Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]), direction, lowerButtonY));
            }
        }
        if (towers.isEmpty()) throw new IllegalArgumentException("No launcher towers in " + propertiesPath);
        return towers;
    }
}
