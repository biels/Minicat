package com.biel.lobby.mapes.jocs.roborampage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;

/** Owns inert helper Guardians used only to expose the native client laser visual. */
final class GuardianBeamRenderer implements AutoCloseable {
    private static final String HELPER_TAG = "minicat_robo_rampage_taser_beam";
    private static final int SAFE_LASER_TICKS = 0;

    private final World world;
    private final Map<BeamKey, Guardian> helpers = new HashMap<>();
    private final Set<BeamKey> visibleThisFrame = new HashSet<>();

    GuardianBeamRenderer(World world) {
        this.world = world;
        world.getEntitiesByClass(Guardian.class).stream()
                .filter(guardian -> guardian.getScoreboardTags().contains(HELPER_TAG))
                .forEach(Guardian::remove);
    }

    void beginFrame() {
        visibleThisFrame.clear();
    }

    void show(
            UUID operatorId,
            UUID sourceEntityId,
            UUID targetEntityId,
            Location source,
            LivingEntity target) {
        BeamKey beamKey = new BeamKey(operatorId, sourceEntityId, targetEntityId);
        visibleThisFrame.add(beamKey);
        Guardian helper = helpers.get(beamKey);
        if (helper == null || !helper.isValid() || helper.isDead() || helper.getWorld() != world) {
            if (helper != null) helper.remove();
            helper = spawnHelper(source);
            helpers.put(beamKey, helper);
        } else {
            helper.teleport(source);
        }
        helper.setTarget(target);
        helper.setLaser(true);
        helper.setLaserTicks(SAFE_LASER_TICKS);
    }

    void finishFrame() {
        Iterator<Map.Entry<BeamKey, Guardian>> iterator = helpers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BeamKey, Guardian> entry = iterator.next();
            if (visibleThisFrame.contains(entry.getKey()) && entry.getValue().isValid()) continue;
            entry.getValue().remove();
            iterator.remove();
        }
    }

    boolean isHelper(UUID entityId) {
        return helpers.values().stream().anyMatch(helper -> helper.getUniqueId().equals(entityId));
    }

    static boolean isHelper(Entity entity) {
        return entity.getScoreboardTags().contains(HELPER_TAG);
    }

    private Guardian spawnHelper(Location source) {
        return world.spawn(source, Guardian.class, guardian -> {
            guardian.addScoreboardTag(HELPER_TAG);
            guardian.setAI(false);
            guardian.setAware(false);
            guardian.setSilent(true);
            guardian.setInvisible(true);
            guardian.setInvulnerable(true);
            guardian.setGravity(false);
            guardian.setNoPhysics(true);
            guardian.setCollidable(false);
            guardian.setCanPickupItems(false);
            guardian.setPersistent(false);
            guardian.setRemoveWhenFarAway(false);
            guardian.setLootTable(null);
        });
    }

    @Override
    public void close() {
        helpers.values().forEach(Guardian::remove);
        helpers.clear();
        visibleThisFrame.clear();
    }

    private record BeamKey(UUID operatorId, UUID sourceEntityId, UUID targetEntityId) {}
}
