package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ElectricNetworkTest {
    private static final ElectricNetwork.Point SOURCE = new ElectricNetwork.Point(0, 0, 0);
    private static final UUID NEAR = new UUID(0, 1);
    private static final UUID BRANCH = new UUID(0, 2);
    private static final UUID FAR = new UUID(0, 3);

    @Test
    void closestConnectedNodeCreatesABranchingNetwork() {
        var targets = List.of(
                target(NEAR, 4, 0, 0),
                target(BRANCH, 4, 0, 4),
                target(FAR, 9, 0, 4));

        var edges = ElectricNetwork.build(SOURCE, targets, 3, 5, 6, (from, to) -> true);

        assertEquals(List.of(
                new ElectricNetwork.Edge(Optional.empty(), NEAR),
                new ElectricNetwork.Edge(Optional.of(NEAR), BRANCH),
                new ElectricNetwork.Edge(Optional.of(BRANCH), FAR)), edges);
    }

    @Test
    void rangeVisibilityAndTargetLimitAreAllHardBounds() {
        var targets = List.of(
                target(NEAR, 3, 0, 0),
                target(BRANCH, 5, 0, 0),
                target(FAR, 30, 0, 0));

        var edges = ElectricNetwork.build(
                SOURCE, targets, 1, 10, 6,
                (from, to) -> !to.equals(targets.get(1).position()));

        assertEquals(List.of(new ElectricNetwork.Edge(Optional.empty(), NEAR)), edges);
    }

    @Test
    void duplicateTargetIdsCannotReceiveTwoNodes() {
        var edges = ElectricNetwork.build(SOURCE, List.of(
                target(NEAR, 2, 0, 0), target(NEAR, 3, 0, 0)), 5, 10, 6, (from, to) -> true);

        assertEquals(1, edges.size());
    }

    @Test
    void directRangeCanBeExtendedForOneTargetWithoutExtendingChainJumps() {
        var ordinary = target(NEAR, 22, 0, 0);
        var elevatedTarget = target(FAR, 15, 0, 0);

        var edges = ElectricNetwork.build(
                SOURCE,
                List.of(ordinary, elevatedTarget),
                2,
                target -> target.id().equals(FAR) ? 16 : 12,
                6,
                (from, to) -> true);

        assertEquals(List.of(new ElectricNetwork.Edge(Optional.empty(), FAR)), edges);
    }

    private static ElectricNetwork.Target target(UUID id, double x, double y, double z) {
        return new ElectricNetwork.Target(id, new ElectricNetwork.Point(x, y, z));
    }
}
