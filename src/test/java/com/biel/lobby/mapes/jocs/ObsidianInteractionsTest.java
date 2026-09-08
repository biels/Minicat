package com.biel.lobby.mapes.jocs;

import org.bukkit.util.Vector;

public final class ObsidianInteractionsTest {
    public static void main(String[] args) {
        approvedPortals();
        portalBoundaries();
        pickaxePush();
        lootFlight();
        System.out.println("Obsidian interaction geometry checks passed");
    }

    private static void approvedPortals() {
        Vector[] spawns = {new Vector(616, 41, -1419), new Vector(712, 41, -1384)};
        Vector[] facing = {new Vector(0, 0, 1), new Vector(0, 0, -1)};
        Vector[] arrivals = {new Vector(613.5, 41, -1371.5), new Vector(713.5, 41, -1428.5)};
        for (int team = 0; team < 2; team++) {
            var portal = ObsidianInteractions.portal(team);
            require(!ObsidianInteractions.inPortal(spawns[team], portal.entrance()), "respawn must not trigger portal");
            require(portal.entrance().clone().subtract(spawns[team]).dot(facing[team]) < 0, "entrance is behind spawn");
            require(portal.arrival().equals(arrivals[team]), "arrival matches approved map marker");
            require(portal.arrivalYaw() == (team == 0 ? 180 : 0), "arrival faces down the shop corridor");
        }
    }

    private static void portalBoundaries() {
        Vector entrance = ObsidianInteractions.portal(0).entrance();
        require(ObsidianInteractions.inPortal(entrance, entrance), "center activates");
        require(ObsidianInteractions.inPortal(entrance.clone().add(new Vector(2, 0, 0)), entrance), "two-block edge activates");
        require(!ObsidianInteractions.inPortal(entrance.clone().add(new Vector(2.01, 0, 0)), entrance), "outside edge does not activate");
        require(!ObsidianInteractions.inPortal(entrance.clone().add(new Vector(1.5, 0, 1.5)), entrance), "radius is circular, not square");
        require(!ObsidianInteractions.inPortal(entrance.clone().add(new Vector(0, 1, 0)), entrance), "floor above excluded");
        require(!ObsidianInteractions.inPortal(entrance.clone().add(new Vector(0, -1, 0)), entrance), "floor below excluded");
    }

    private static void pickaxePush() {
        Vector center = new Vector(664.5, 42, -1401.5);
        for (Vector offset : new Vector[]{new Vector(1, -1, 0), new Vector(-1, -1, 0), new Vector(0, 0, 4)}) {
            Vector player = center.clone().add(offset);
            Vector push = ObsidianInteractions.outwardPush(player, center, new Vector());
            require(push.dot(offset.clone().setY(0)) > 0, "push must be radial and outward");
            close(0.4, push.clone().setY(0).length(), "horizontal force is bounded");
            close(0.2, push.getY(), "vertical force is bounded");
            require(player.equals(center.clone().add(offset)), "input vectors are not mutated");
        }
        require(ObsidianInteractions.outwardPush(center.clone().add(new Vector(4.01, 0, 0)), center, new Vector()).isZero(), "outside radius excluded");
        require(ObsidianInteractions.outwardPush(center.clone().add(new Vector(0, 2.01, 0)), center, new Vector()).isZero(), "other level excluded");
        Vector centered = ObsidianInteractions.outwardPush(center, center, new Vector(0, 1, 0));
        centered.checkFinite();
        require(centered.lengthSquared() > 0, "exact-center player still receives a finite push");
        require(ObsidianInteractions.PICKAXE_PICKUP_DELAY == 10, "half-second pickup window");
    }

    private static void lootFlight() {
        Vector origin = new Vector(1, 2, 3);
        require(ObsidianInteractions.lootVelocity(origin, origin).isZero(), "arrived loot does not normalize zero");
        Vector nearby = new Vector(1.1, 2, 3);
        close(0.1, ObsidianInteractions.lootVelocity(origin, nearby).length(), "nearby loot does not overshoot");
        Vector far = new Vector(5, 4, -2);
        Vector velocity = ObsidianInteractions.lootVelocity(origin, far);
        close(0.4, velocity.length(), "flight speed is bounded");
        require(velocity.dot(far.clone().subtract(origin)) > 0, "loot flies toward collector");
        require(origin.equals(new Vector(1, 2, 3)) && far.equals(new Vector(5, 4, -2)), "flight preserves input vectors");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void close(double expected, double actual, String message) {
        require(Math.abs(expected - actual) < 1e-6, message + ": " + actual);
    }
}
