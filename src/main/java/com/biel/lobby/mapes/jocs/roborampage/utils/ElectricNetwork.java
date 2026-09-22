package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.ToDoubleFunction;

/** Builds a deterministic, bounded electrical tree through visible nearby targets. */
public final class ElectricNetwork {
    private ElectricNetwork() {}

    public record Point(double x, double y, double z) {
        public double distanceSquared(Point other) {
            double deltaX = x - other.x;
            double deltaY = y - other.y;
            double deltaZ = z - other.z;
            return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
        }
    }

    public record Target(UUID id, Point position) {}

    /** Empty parent means the edge begins at the player rather than another robot. */
    public record Edge(Optional<UUID> parentTargetId, UUID targetId) {}

    public static List<Edge> build(
            Point source,
            List<Target> candidates,
            int maximumTargets,
            double sourceRange,
            double jumpRange,
            BiPredicate<Point, Point> hasLineOfSight) {
        return build(
                source,
                candidates,
                maximumTargets,
                ignored -> sourceRange,
                jumpRange,
                hasLineOfSight);
    }

    public static List<Edge> build(
            Point source,
            List<Target> candidates,
            int maximumTargets,
            ToDoubleFunction<Target> sourceRange,
            double jumpRange,
            BiPredicate<Point, Point> hasLineOfSight) {
        if (maximumTargets <= 0 || candidates.isEmpty()) return List.of();

        List<Target> orderedCandidates = candidates.stream()
                .filter(candidate -> candidate != null && candidate.id() != null && candidate.position() != null)
                .collect(java.util.stream.Collectors.toMap(
                        Target::id, candidate -> candidate, (first, ignored) -> first))
                .values().stream()
                .sorted(Comparator.comparing(target -> target.id().toString()))
                .toList();
        List<ConnectedNode> connectedNodes = new ArrayList<>();
        connectedNodes.add(new ConnectedNode(Optional.empty(), source));
        Set<UUID> connectedTargetIds = new HashSet<>();
        List<Edge> edges = new ArrayList<>();

        while (edges.size() < maximumTargets) {
            CandidateEdge shortestEdge = null;
            for (ConnectedNode parent : connectedNodes) {
                for (Target target : orderedCandidates) {
                    if (connectedTargetIds.contains(target.id())) continue;
                    double range = parent.targetId().isEmpty()
                            ? Math.max(0, sourceRange.applyAsDouble(target))
                            : jumpRange;
                    double rangeSquared = range * range;
                    double distanceSquared = parent.position().distanceSquared(target.position());
                    if (distanceSquared > rangeSquared || !hasLineOfSight.test(parent.position(), target.position())) {
                        continue;
                    }
                    CandidateEdge candidateEdge = new CandidateEdge(parent, target, distanceSquared);
                    if (shortestEdge == null || CandidateEdge.ORDER.compare(candidateEdge, shortestEdge) < 0) {
                        shortestEdge = candidateEdge;
                    }
                }
            }
            if (shortestEdge == null) break;
            UUID targetId = shortestEdge.target().id();
            edges.add(new Edge(shortestEdge.parent().targetId(), targetId));
            connectedTargetIds.add(targetId);
            connectedNodes.add(new ConnectedNode(Optional.of(targetId), shortestEdge.target().position()));
        }
        return List.copyOf(edges);
    }

    private record ConnectedNode(Optional<UUID> targetId, Point position) {}

    private record CandidateEdge(ConnectedNode parent, Target target, double distanceSquared) {
        private static final Comparator<CandidateEdge> ORDER = Comparator
                .comparingDouble(CandidateEdge::distanceSquared)
                .thenComparing(edge -> edge.target().id().toString())
                .thenComparing(edge -> edge.parent().targetId().map(UUID::toString).orElse(""));
    }
}
