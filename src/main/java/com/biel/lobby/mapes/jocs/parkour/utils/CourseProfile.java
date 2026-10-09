package com.biel.lobby.mapes.jocs.parkour.utils;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** Validated map semantics, independent of Bukkit and the map's command runtime. */
public record CourseProfile(int schemaVersion, String engine, Start start, Sphere finish,
                            Failure failure, List<Checkpoint> checkpoints) {
    public static final String FILE_NAME = "parkour-course.json";

    public CourseProfile {
        if (schemaVersion != 1) throw invalid("Unsupported schemaVersion: " + schemaVersion);
        if (!"spiral3".equals(engine)) throw invalid("Unsupported course engine: " + engine);
        if (start == null || finish == null || failure == null) throw invalid("Missing course rules");
        checkpoints = List.copyOf(checkpoints);
        Set<String> checkpointIds = new HashSet<>();
        Set<BlockPosition> triggers = new HashSet<>();
        triggers.add(start.trigger());
        for (Checkpoint checkpoint : checkpoints) {
            if (!checkpointIds.add(checkpoint.id())) throw invalid("Duplicate checkpoint ID: " + checkpoint.id());
            if (!triggers.add(checkpoint.trigger())) throw invalid("Duplicate course trigger: " + checkpoint.trigger());
        }
    }

    public static CourseProfile load(Path path) {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return parse(reader);
        } catch (IOException | RuntimeException exception) {
            throw new IllegalArgumentException("Invalid course profile " + path + ": " + exception.getMessage(), exception);
        }
    }

    public static CourseProfile parse(Reader reader) {
        JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
        JsonObject start = object(root, "start");
        JsonObject finish = object(root, "finish");
        JsonObject failure = object(root, "failure");
        JsonArray checkpointValues = array(root, "checkpoints");
        List<Checkpoint> checkpoints = checkpointValues.asList().stream().map(value -> {
            JsonObject checkpoint = value.getAsJsonObject();
            return new Checkpoint(string(checkpoint, "id"), block(object(checkpoint, "trigger")),
                    position(object(checkpoint, "safePosition")), optionalPositive(checkpoint, "maxDrop"));
        }).toList();
        List<Volume> volumes = failure.has("volumes") ? array(failure, "volumes").asList().stream()
                .map(value -> {
                    JsonObject volume = value.getAsJsonObject();
                    return new Volume(point(object(volume, "min")), point(object(volume, "max")));
                }).toList() : List.of();
        List<Surface> surfaces = failure.has("surfaces") ? array(failure, "surfaces").asList().stream()
                .map(value -> {
                    JsonObject surface = value.getAsJsonObject();
                    return new Surface(string(surface, "material"), integer(surface, "minY"), integer(surface, "maxY"));
                }).toList() : List.of();
        return new CourseProfile(integer(root, "schemaVersion"), string(root, "engine"),
                new Start(position(object(start, "entry")), block(object(start, "trigger"))),
                new Sphere(point(object(finish, "center")), number(finish, "radius")),
                new Failure(number(failure, "minY"), strings(failure, "hazardMaterials"),
                        strings(failure, "damageCauses"), optionalPositive(failure, "defaultMaxDrop"), volumes, surfaces), checkpoints);
    }

    public record Point(double x, double y, double z) {
        public Point { finite(x, "x"); finite(y, "y"); finite(z, "z"); }
    }

    public record Position(double x, double y, double z, float yaw, float pitch) {
        public Position {
            finite(x, "x"); finite(y, "y"); finite(z, "z"); finite(yaw, "yaw"); finite(pitch, "pitch");
            if (pitch < -90 || pitch > 90) throw invalid("Pitch must be between -90 and 90");
        }
    }

    public record BlockPosition(int x, int y, int z) {
        public Position safePosition(float yaw, float pitch) {
            return new Position(x + .5, y + .0625, z + .5, yaw, pitch);
        }

        /** Matches the footprint touching a plate, rather than nearby players above it. */
        public boolean touches(double feetX, double feetY, double feetZ) {
            return feetY >= y - .02 && feetY <= y + .22
                    && feetX + .3 > x && feetX - .3 < x + 1
                    && feetZ + .3 > z && feetZ - .3 < z + 1;
        }
    }

    public record Start(Position entry, BlockPosition trigger) {
        public Start { if (entry == null || trigger == null) throw invalid("Missing start position/trigger"); }
    }

    public record Checkpoint(String id, BlockPosition trigger, Position safePosition, Double maxDrop) {
        public Checkpoint {
            if (id == null || id.isBlank() || trigger == null || safePosition == null) throw invalid("Incomplete checkpoint");
            if (maxDrop != null) positive(maxDrop, "maxDrop");
        }
    }

    public record Sphere(Point center, double radius) {
        public Sphere { if (center == null) throw invalid("Missing finish center"); positive(radius, "radius"); }
        public boolean contains(double x, double y, double z) {
            double dx = x - center.x(), dy = y - center.y(), dz = z - center.z();
            return dx * dx + dy * dy + dz * dz <= radius * radius;
        }
    }

    public record Volume(Point min, Point max) {
        public Volume {
            if (min == null || max == null || min.x() > max.x() || min.y() > max.y() || min.z() > max.z())
                throw invalid("Invalid failure volume");
        }
        public boolean contains(double x, double y, double z) {
            return x >= min.x() && x <= max.x() && y >= min.y() && y <= max.y() && z >= min.z() && z <= max.z();
        }
    }

    /** A declared contact block layer, independent of the player's elevation. */
    public record Surface(String material, int minY, int maxY) {
        public Surface {
            if (material == null || material.isBlank()) throw invalid("Failure surface requires a material");
            material = material.toUpperCase(Locale.ROOT);
            if (!material.matches("[A-Z][A-Z0-9_]*")) throw invalid("Invalid failure surface material: " + material);
            if (minY > maxY) throw invalid("Failure surface minY must not exceed maxY");
        }
        public boolean matches(String blockMaterial, int blockY) {
            return material.equals(blockMaterial) && blockY >= minY && blockY <= maxY;
        }
    }

    public record Failure(double minY, Set<String> hazardMaterials, Set<String> damageCauses,
                          Double defaultMaxDrop, List<Volume> volumes, List<Surface> surfaces) {
        public Failure(double minY, Set<String> hazardMaterials, Set<String> damageCauses,
                       Double defaultMaxDrop, List<Volume> volumes) {
            this(minY, hazardMaterials, damageCauses, defaultMaxDrop, volumes, List.of());
        }
        public Failure {
            finite(minY, "minY");
            hazardMaterials = Set.copyOf(hazardMaterials);
            damageCauses = Set.copyOf(damageCauses);
            volumes = List.copyOf(volumes);
            surfaces = List.copyOf(surfaces);
            if (defaultMaxDrop != null) positive(defaultMaxDrop, "defaultMaxDrop");
        }
        public boolean contains(double x, double y, double z) {
            return y < minY || volumes.stream().anyMatch(volume -> volume.contains(x, y, z));
        }
        public boolean contacts(String blockMaterial, int blockY) {
            return surfaces.stream().anyMatch(surface -> surface.matches(blockMaterial, blockY));
        }
        public boolean belowCheckpoint(double y, Position anchor, Double stageMaxDrop) {
            Double maxDrop = stageMaxDrop == null ? defaultMaxDrop : stageMaxDrop;
            return maxDrop != null && y < anchor.y() - maxDrop;
        }
    }

    private static Point point(JsonObject object) {
        return new Point(number(object, "x"), number(object, "y"), number(object, "z"));
    }
    private static Position position(JsonObject object) {
        return new Position(number(object, "x"), number(object, "y"), number(object, "z"),
                (float) optionalNumber(object, "yaw", 0), (float) optionalNumber(object, "pitch", 0));
    }
    private static BlockPosition block(JsonObject object) {
        return new BlockPosition(integer(object, "x"), integer(object, "y"), integer(object, "z"));
    }
    private static Set<String> strings(JsonObject object, String name) {
        Set<String> result = new HashSet<>();
        for (JsonElement value : array(object, name)) result.add(value.getAsString().toUpperCase(Locale.ROOT));
        return result;
    }
    private static JsonObject object(JsonObject object, String name) { return required(object, name).getAsJsonObject(); }
    private static JsonArray array(JsonObject object, String name) { return required(object, name).getAsJsonArray(); }
    private static String string(JsonObject object, String name) {
        JsonElement value = required(object, name);
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) throw invalid(name + " must be a string");
        return value.getAsString();
    }
    private static int integer(JsonObject object, String name) {
        double value = number(object, name);
        if (value != Math.rint(value) || value < Integer.MIN_VALUE || value > Integer.MAX_VALUE)
            throw invalid(name + " must be an integer");
        return (int) value;
    }
    private static double number(JsonObject object, String name) {
        JsonElement element = required(object, name);
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) throw invalid(name + " must be a number");
        double value = element.getAsDouble();
        finite(value, name);
        return value;
    }
    private static double optionalNumber(JsonObject object, String name, double fallback) {
        return object.has(name) ? number(object, name) : fallback;
    }
    private static Double optionalPositive(JsonObject object, String name) {
        if (!object.has(name) || object.get(name).isJsonNull()) return null;
        double value = number(object, name);
        positive(value, name);
        return value;
    }
    private static JsonElement required(JsonObject object, String name) {
        JsonElement value = object.get(name);
        if (value == null || value.isJsonNull()) throw invalid("Missing " + name);
        return value;
    }
    private static void finite(double value, String name) {
        if (!Double.isFinite(value)) throw invalid(name + " must be finite");
    }
    private static void positive(double value, String name) {
        finite(value, name);
        if (value <= 0) throw invalid(name + " must be positive");
    }
    private static IllegalArgumentException invalid(String message) { return new IllegalArgumentException(message); }
}
