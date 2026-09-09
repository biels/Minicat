package com.biel.lobby.mapes.jocs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Input;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.util.VoxelShape;

import com.biel.lobby.mapes.jocs.InkWars.InkWarsPlayerInfo.Squid;
import com.biel.lobby.mapes.jocs.InkWars.InkWarsPlayerInfo.Squid.Keys;
import com.biel.lobby.mapes.jocs.InkWars.InkWarsPlayerInfo.Squid.SwimSound;
import com.biel.lobby.utilities.SweptBoxCollision;
import com.biel.lobby.mapes.jocs.inkwars.SquidMotion;
import com.biel.lobby.mapes.jocs.inkwars.InkStream;

/** Runs the real movement controller against deterministic collision shapes without a server. */
public final class InkWarsMovementTest {
    @org.junit.jupiter.api.Test
    void movementAndSound() throws Exception {
        installSoundRegistry();
        slabLanding();
        wallClimbAndCrest();
        wallJumpMomentum();
        wallRecaptureSuppression();
        standingClearance();
        floorStep();
        stairStep();
        insideWallCorner();
        outsideWallCorner();
        ceilingAndRoof();
        wallGap();
        ceilingControlFrame();
        carrierDoesNotRotateView();
        cameraFollowsResolvedMovement();
        responsiveSteering();
        aimedTurbo();
        jumpForgiveness();
        trailCoversSubsteps();
        directCameraSteering();
        squidSoundDesign();
        poweredJet();
        System.out.println("InkWars movement controller checks passed");
    }

    private static void squidSoundDesign() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.FLOOR);
        require(squid.allowSound(SwimSound.DIVE, 0), "first dive cue plays");
        require(!squid.allowSound(SwimSound.SURFACE, 100_000_000), "rapid form toggles share a cooldown");
        require(!squid.allowSound(SwimSound.SURFACE, 999_999_999), "form chatter stays suppressed for a second");
        require(squid.allowSound(SwimSound.SURFACE, 1_000_000_000), "form cue recovers at cooldown boundary");
        require(squid.allowSound(SwimSound.TURBO, 50_000_000), "contact cooldown does not suppress turbo");
        squid.lastSoundNanos.clear();
        squid.sound(SwimSound.DIVE, fixture.playerLocation);
        require(fixture.heardSounds.size() == 1, "dive is a single soft bubble");
        require(fixture.heardSounds.getFirst().sound() == Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP,
                "dive keeps the gentle bubble cue");
        fixture.heardSounds.clear();
        squid.leapOff(new Vector(-1, 0, 0));
        squid.tickAir(keys(new Vector(1, 0, 0), false));
        require(fixture.heardSounds.isEmpty(), "routine movement and thrust layers stay silent");
        squid.lastSoundNanos.clear();
        for (SwimSound cue : new SwimSound[]{SwimSound.DIVE, SwimSound.TURBO, SwimSound.READY, SwimSound.EMPTY}) {
            squid.sound(cue, fixture.playerLocation);
        }
        require(fixture.heardSounds.size() == 4, "retained cues each use one sound");
        for (HeardSound sound : fixture.heardSounds) {
            require(sound.volume() <= 0.12F, "retained movement cues stay subtle");
            require(sound.privateCue(), "movement feedback is private to the player");
        }
        squid.lastSoundNanos.clear();
        require(squid.allowSound(SwimSound.EMPTY, 0), "first empty cue plays");
        require(!squid.allowSound(SwimSound.EMPTY, 1_999_999_999), "empty presses do not chatter");
        require(squid.allowSound(SwimSound.EMPTY, 2_000_000_000L), "empty cue recovers after two seconds");
    }

    private record HeardSound(Sound sound, float volume, float pitch, boolean privateCue) {}

    private static void responsiveSteering() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.FLOOR);
        squid.speed = 0.65;
        squid.steer(new Vector(-1, 0, 0), 1, true);
        require(squid.velocity().getX() < -0.35, "reverse must move in the requested direction immediately");
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.65;
        squid.steer(new Vector(0, 0, 1), 1, true);
        require(squid.velocity().getX() > 0 && squid.velocity().getZ() > 0.5, "quarter turn retains brief sideways drift");
        for (int tick = 0; tick < 3; tick++) squid.steer(new Vector(0, 0, 1), 1, true);
        require(Math.abs(squid.velocity().getX()) < 0.005, "sideways drift settles within 200ms");
        double coastingSpeed = squid.speed;
        squid.steer(new Vector(), 1, true);
        close(coastingSpeed * InkWars.SQUID_COAST, squid.speed, "release preserves coasting");
    }

    private static void poweredJet() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.FLOOR);
        squid.speed = 0.65;
        squid.reserve = 1;
        require(squid.tryThrust(new Vector(1, 0, 0)), "powered jet starts");
        double previousSpeed = squid.speed;
        double distance = 0;
        double oldSpeed = 1.15;
        double oldDistance = 0;
        for (int tick = 0; tick < 40; tick++) {
            squid.steer(new Vector(1, 0, 0), 1, true);
            if (tick < SquidMotion.JET_PUSH_TICKS) require(squid.speed > previousSpeed, "jet keeps accelerating through push phase");
            if (tick == SquidMotion.JET_DURATION_TICKS - 1) require(squid.speed < previousSpeed, "release fades into drag");
            require(squid.speed <= InkWars.SQUID_THRUST_CEILING, "jet respects existing speed ceiling");
            previousSpeed = squid.speed;
            distance += squid.speed;
            if (tick > 0) oldSpeed = Math.max(0.65, oldSpeed * 0.97);
            oldDistance += oldSpeed;
        }
        require(Math.abs(distance / oldDistance - 1) < 0.1, "two-second runway range stays within 10% of impulse turbo");
        require(squid.turboJetTick == SquidMotion.JET_DURATION_TICKS, "jet expires without extending itself");
        close(1 - InkWars.SQUID_THRUST_COST, squid.reserve, "entire burst charges once");
        close(0, squid.turboJetStrength, "thrust ends after release");

        Squid turn = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.AIR);
        turn.speed = 0.65;
        turn.reserve = 1;
        turn.tryThrust(new Vector(1, 0.5, 0));
        turn.steer(new Vector(1, 0, 0), 0.3, false);
        turn.steer(new Vector(-1, 0, 0), 0.3, false);
        require(turn.velocity().getX() < 0, "active jet follows reversal rather than fighting input");
        require(turn.verticalSpeed > 0, "steering does not discard aimed upward propulsion");

        Fixture blockedFixture = new Fixture();
        Squid blocked = blockedFixture.squid(new Vector(0.698, 1.5, 0.5), InkWars.Surface.AIR);
        blocked.reserve = 1;
        blocked.tryThrust(new Vector(1, 0, 0));
        blockedFixture.add(1, 1, 0, Material.STONE, new BoundingBox(0, 0, 0, 1, 1, 1));
        blocked.steer(new Vector(1, 0, 0), 0.3, false);
        require(blocked.turboJetTick == SquidMotion.JET_DURATION_TICKS, "blocked jet cannot build hidden acceleration");
        close(0, blocked.turboJetStrength, "blocked jet stops powered particles");

        Squid flight = fixture.squid(new Vector(0, 20, 0), InkWars.Surface.AIR);
        flight.speed = 0.65;
        flight.reserve = 1;
        flight.tryThrust(new Vector(1, 1, 0));
        double oldHorizontal = 1.15 / Math.sqrt(2);
        double oldFlightDistance = 0;
        for (int tick = 0; tick < 20; tick++) {
            flight.tickAir(new Keys(new Vector(1, 0, 0), false, 1, 0));
            if (tick > 0) oldHorizontal = oldHorizontal > 0.65 ? Math.max(0.65, oldHorizontal * 0.97) : Math.min(0.65, oldHorizontal + 0.006);
            oldHorizontal *= 0.98;
            oldFlightDistance += oldHorizontal;
        }
        require(Math.abs(flight.centre.getX() / oldFlightDistance - 1) < 0.15, "one-second aerial range stays near old turbo");
        double beforeGravity = flight.verticalSpeed;
        flight.tickAir(new Keys(new Vector(), false, 0, 0));
        close((beforeGravity - InkWars.SQUID_GRAVITY) * 0.98, flight.verticalSpeed, "normal gravity resumes with no lingering thrust");

        Vector slow = new Vector(0.5, 0, 0);
        Vector fast = new Vector(1.2, 0, 0);
        double slowGain = SquidMotion.jetStep(slow, new Vector(1, 0, 0), 3, 1.4).getX() - slow.getX();
        double fastGain = SquidMotion.jetStep(fast, new Vector(1, 0, 0), 3, 1.4).getX() - fast.getX();
        require(slowGain > fastGain, "drag increases with speed");
        close(0.5, slow.getX(), "jet math does not mutate input velocity");
        require(SquidMotion.jetEnvelope(8) > SquidMotion.jetEnvelope(9)
                && SquidMotion.jetEnvelope(9) > SquidMotion.jetEnvelope(10), "release envelope fades smoothly");
        System.out.println("Turbo runway distance: " + distance + " blocks; previous impulse: " + oldDistance);
    }

    private static void directCameraSteering() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.FLOOR);
        squid.readKeys();
        fixture.playerLocation.setYaw(90);
        Keys reversed = squid.readKeys();
        require(squid.movementKeys(reversed).getX() < -0.99, "mouse half-turn updates floor controls immediately");
        squid.cornerTicks = 10;
        fixture.playerLocation.setYaw(0);
        Keys afterCorner = squid.readKeys();
        require(squid.movementKeys(afterCorner).getZ() > 0.99, "deliberate aim change overrides corner continuity");
    }

    private static void aimedTurbo() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.FLOOR);
        squid.speed = 0.65;
        squid.reserve = 1;
        require(squid.tryThrust(new Vector(-1, 0, 0)), "opposite-facing turbo succeeds");
        close(-0.77, squid.velocity().getX(), "turbo redirects momentum with a small immediate kick");
        close(1 - InkWars.SQUID_THRUST_COST, squid.reserve, "successful turbo spends ink once");
        require(squid.turboTrailTicks == InkWars.SQUID_TURBO_TRAIL_TICKS, "turbo starts bounded paint trail");
        squid.steer(new Vector(1, 0, 0), 1, true);
        require(squid.velocity().getX() < -0.77, "held opposite input cannot cancel the first boost tick");
        squid.steer(new Vector(1, 0, 0), 1, true);
        require(squid.velocity().getX() > 0, "steering returns immediately after launch tick");
        squid.reserve = 1;
        Vector aim = new Vector(1, 1, 0).normalize();
        require(squid.tryThrust(aim), "upward turbo succeeds");
        require(squid.surface == InkWars.Surface.AIR, "upward floor turbo detaches");
        close(1, squid.velocity().normalize().dot(aim), "launch follows crosshair pitch and yaw");
        require(squid.floorGraceTicks == 0, "boost cannot also claim an edge jump");

        Fixture wall = new Fixture();
        wall.add(1, 1, 0, Material.STONE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid wallSquid = wall.wallSquid(new Vector(0.698, 1.5, 0.5), new Vector(0, 1, 0));
        wallSquid.reserve = 1;
        require(wallSquid.tryThrust(new Vector(1, 0, 0)), "head-on wall aim maps to climbing");
        require(wallSquid.surface == InkWars.Surface.WALL && wallSquid.velocity().getY() > 0.4, "wall boost remains tangent");
        wallSquid.reserve = 1;
        require(wallSquid.tryThrust(new Vector(-1, 0.4, 0)), "aiming away from wall launches");
        require(wallSquid.surface == InkWars.Surface.AIR && wallSquid.velocity().getX() < 0,
                "wall turbo departs outward instead of continuing climb");
        require(wallSquid.detachTicks > 0, "wall boost has recapture protection");

        Squid blocked = wall.squid(new Vector(0.698, 1.5, 0.5), InkWars.Surface.AIR);
        blocked.reserve = 1;
        require(!blocked.tryThrust(new Vector(1, 0, 0)), "blocked turbo rejected");
        close(1, blocked.reserve, "blocked turbo spends no ink");
        require(blocked.turboTrailTicks == 0, "blocked turbo emits no paint");
    }

    private static void trailCoversSubsteps() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.AIR);
        squid.turboTrailTicks = InkWars.SQUID_TURBO_TRAIL_TICKS;
        InkStream stream = new InkStream();
        for (int step = 0; step < 14; step++) {
            squid.emitTurboTrail(stream, new Vector(step * 0.1, 2, 0), new Vector((step + 1) * 0.1, 2, 0));
        }
        require(stream.flights().size() == 5, "spacing carries across tiny collision substeps");
        require(stream.flights().getLast().position().getX() > 1.2, "fast tick trail reaches the final path section");
        for (int step = 14; step < 50; step++) {
            squid.emitTurboTrail(stream, new Vector(step * 0.1, 2, 0), new Vector((step + 1) * 0.1, 2, 0));
        }
        require(stream.flights().size() == 8, "trail has a hard per-tick parcel budget");
        squid.turboTrailTicks = 0;
        squid.trailParcelsThisTick = 0;
        squid.emitTurboTrail(stream, new Vector(5, 2, 0), new Vector(6, 2, 0));
        require(stream.flights().size() == 8, "expired turbo emits no more paint");
        close(0, InkWars.SQUID_TRAIL_LOAD.sting(), "turbo paint is not a new damage mechanic");
    }

    private static void jumpForgiveness() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0, 2, 0), InkWars.Surface.AIR);
        Keys press = new Keys(new Vector(), true, 0, 0);
        Keys release = new Keys(new Vector(), false, 0, 0);
        squid.prepareJump(press);
        squid.surface = InkWars.Surface.FLOOR;
        squid.prepareJump(release);
        squid.tickFloor(release);
        close(InkWars.SQUID_HOP, squid.verticalSpeed, "pre-landing jump is buffered");
        require(squid.floorGraceTicks == 0 && squid.jumpBufferedTicks == 0, "jump consumes buffer and grace");
        squid.surface = InkWars.Surface.FLOOR;
        squid.prepareJump(release);
        squid.surface = InkWars.Surface.AIR;
        squid.prepareJump(press);
        squid.tickAir(press);
        require(squid.verticalSpeed > 0.35, "just-off-edge jump still works");
        squid.verticalSpeed = -0.1;
        squid.floorGraceTicks = 0;
        squid.prepareJump(press);
        squid.tickAir(press);
        require(squid.verticalSpeed < 0, "no unlimited mid-air jumps");
    }

    private static void carrierDoesNotRotateView() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        squid.carrierYaw = -90;
        float previousCarrierYaw = squid.carrierYaw;
        for (float lookYaw : new float[]{-75, -30, 45, 179, -179, -90}) {
            fixture.playerLocation.setYaw(lookYaw);
            float targetCarrierYaw = squid.seatLocation().getYaw();
            // Paper applies the vehicle's rotation delta to each passenger on teleport.
            double nextPlayerYaw = targetCarrierYaw + lookYaw - previousCarrierYaw;
            close(lookYaw, nextPlayerYaw, "carrier movement must not amplify mouse movement");
            close(0, squid.seatLocation().getPitch(), "carrier pitch remains fixed");
            previousCarrierYaw = targetCarrierYaw;
        }
    }

    private static void cameraFollowsResolvedMovement() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0.5, 2, 0.5), InkWars.Surface.WALL);
        squid.cameraCentre = squid.centre.clone();
        squid.heading = new Vector(0, 1, 0);
        squid.speed = 0.65;
        squid.pushed = new Vector();
        for (int tick = 0; tick < 10; tick++) {
            squid.updateCamera();
            close(squid.centre.getY(), squid.cameraCentre.getY(), "blocked climb must not move camera");
        }
        for (int tick = 0; tick < 10; tick++) {
            squid.pushed = new Vector(0, 0.2, 0);
            squid.centre.add(squid.pushed);
            squid.updateCamera();
            close(squid.centre.getY(), squid.cameraCentre.getY(), "constant climb follows actual displacement");
        }
        squid.surface = InkWars.Surface.FLOOR;
        squid.centre.setY(squid.centre.getY() + 0.5);
        double previousCameraHeight = squid.cameraCentre.getY();
        for (int tick = 0; tick < 12; tick++) {
            squid.updateCamera();
            require(squid.cameraCentre.getY() >= previousCameraHeight, "step camera converges monotonically");
            require(squid.cameraCentre.getY() <= squid.centre.getY(), "step camera does not overshoot");
            previousCameraHeight = squid.cameraCentre.getY();
        }
        require(squid.centre.getY() - previousCameraHeight < 0.001, "step camera settles");
    }

    private static void installSoundRegistry() throws Exception {
        // Paper resolves Sound constants through a server service; the fixture only needs opaque sound values.
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Object allocator = unsafeField.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = proxy(io.papermc.paper.registry.RegistryAccess.class, (object, method, args) -> {
            if (method.getName().equals("getRegistry")) {
                return proxy(Registry.class, (registry, operation, values) -> {
                    if (operation.getName().equals("get") || operation.getName().equals("getOrThrow")) {
                        return proxy(Sound.class, InkWarsMovementTest::defaultValue);
                    }
                    return defaultValue(registry, operation, values);
                });
            }
            return defaultValue(object, method, args);
        });
        Object base = unsafeClass.getMethod("staticFieldBase", Field.class).invoke(allocator, providerField);
        long offset = (long) unsafeClass.getMethod("staticFieldOffset", Field.class).invoke(allocator, providerField);
        unsafeClass.getMethod("putObject", Object.class, long.class, Object.class)
                .invoke(allocator, base, offset, Optional.of(provider));
    }

    private static void slabLanding() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(0, 0, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        Squid squid = fixture.squid(new Vector(0.5, 2, 0.5), InkWars.Surface.AIR);
        squid.verticalSpeed = -2;
        squid.tickAir(keys(new Vector(), false));
        require(squid.surface == InkWars.Surface.FLOOR, "falling squid must land on slab");
        close(0.5 + InkWars.SQUID_RIDE_HEIGHT, squid.centre.getY(), "landing follows slab top");
        close(0.5, squid.centre.getX(), "landing preserves horizontal position");
    }

    private static void floorStep() throws Exception {
        Fixture fixture = new Fixture();
        for (int x = -1; x <= 3; x++) fixture.add(x, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(1, 1, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        fixture.add(2, 1, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.4;
        for (int tick = 0; tick < 4; tick++) fixture.move(squid, keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.FLOOR, "slab step remains floor movement");
        require(squid.centre.getX() > 1.3, "squid moves onto slab without stalling");
        close(2, squid.centre.getY(), "step follows slab elevation");
    }

    private static void ceilingControlFrame() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(0, 3, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.5, 2.5, 0.5), InkWars.Surface.AIR);
        squid.controlForward = new Vector(0, 0, 1);
        squid.controlRight = new Vector(-1, 0, 0);
        squid.heading = new Vector(0, 0, 1);
        squid.speed = 0.3;
        Block obstacle = fixture.block(0, 3, 0);
        squid.attachToCeiling(new Squid.Contact(obstacle, BlockFace.DOWN, 0, new Vector(0.5, 3, 0.5), obstacle.getBoundingBox()), new Vector(0, 1, 0));
        squid.attachToWall(new Squid.Contact(obstacle, BlockFace.NORTH, 0, new Vector(0.5, 3.5, 0), obstacle.getBoundingBox()), new Vector(0, -1, 0));
        squid.landOnFloor(new Vector(0, 0, -1), obstacle, 4);
        Vector expectedRight = squid.controlForward.clone().crossProduct(new Vector(0, 1, 0)).normalize();
        require(expectedRight.dot(squid.controlRight) > 0.999, "ceiling route preserves floor control handedness");
    }

    private static void stairStep() throws Exception {
        Fixture fixture = new Fixture();
        for (int x = -1; x <= 3; x++) fixture.add(x, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(1, 1, 0, Material.STONE_STAIRS, new BoundingBox(0, 0, 0, 1, 0.5, 1), new BoundingBox(0.5, 0.5, 0, 1, 1, 1));
        fixture.add(2, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.3;
        for (int tick = 0; tick < 6; tick++) fixture.move(squid, keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.FLOOR, "stair traversal stays on floor");
        require(squid.centre.getX() > 2, "squid traverses both stair treads");
        close(2.5, squid.centre.getY(), "stair reaches upper floor");
    }

    private static void insideWallCorner() throws Exception {
        Fixture fixture = new Fixture();
        for (int z = 0; z <= 2; z++) fixture.add(1, 1, z, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(0, 1, 2, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(-1, 1, 2, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 0, 1));
        for (int tick = 0; tick < 7; tick++) fixture.move(squid, keys(new Vector(0, 0, 1), false));
        require(squid.surface == InkWars.Surface.WALL && squid.wallSide == BlockFace.SOUTH, "inside corner changes to connected wall");
        require(squid.heading.getX() < -0.9, "inside corner bends momentum away from old wall");
    }

    private static void outsideWallCorner() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 0, 1));
        for (int tick = 0; tick < 4; tick++) fixture.move(squid, keys(new Vector(0, 0, 1), false));
        require(squid.surface == InkWars.Surface.WALL && squid.wallSide == BlockFace.NORTH, "outside corner wraps onto far face");
        require(squid.heading.getX() > 0.9, "outside corner carries momentum around edge");
    }

    private static void ceilingAndRoof() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(1, 2, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        for (int x = -2; x <= 1; x++) fixture.add(x, 3, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 1, 0));
        boolean ceilingSeen = false;
        boolean edgeWallSeen = false;
        for (int tick = 0; tick < 45; tick++) {
            fixture.move(squid, keys(new Vector(1, 0, 0), false));
            ceilingSeen |= squid.surface == InkWars.Surface.CEILING;
            edgeWallSeen |= ceilingSeen && squid.surface == InkWars.Surface.WALL;
            if (edgeWallSeen && squid.surface == InkWars.Surface.FLOOR) break;
        }
        require(ceilingSeen, "wall climb bends onto overhang underside");
        require(edgeWallSeen, "ceiling edge wraps onto outer wall");
        require(squid.surface == InkWars.Surface.FLOOR, "outer wall crests overhang roof");
        close(4.5, squid.centre.getY(), "overhang roof height");
    }

    private static void wallGap() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 0.6, 0.8));
        fixture.solids.clear();
        fixture.blocks.clear();
        // Stale contact stays below a distant top, modeling a missing wall section rather than a roof.
        squid.gripContact = new Squid.Contact(squid.gripped, BlockFace.WEST, 0, new Vector(1, 1.5, 0.5), new BoundingBox(1, 1, 0, 2, 10, 1));
        for (int tick = 0; tick < 8 && squid.surface == InkWars.Surface.WALL; tick++) fixture.move(squid, keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.AIR, "missing wall releases after contact grace");
        require(squid.verticalSpeed > 0, "leaving wall gap preserves upward velocity");
        require(squid.speed > 0, "leaving wall gap preserves horizontal velocity");
    }

    private static void wallClimbAndCrest() throws Exception {
        Fixture fixture = new Fixture();
        for (int x = -2; x <= 3; x++) fixture.add(x, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        for (int y = 1; y <= 3; y++) fixture.add(1, y, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.55, 1 + InkWars.SQUID_RIDE_HEIGHT, 0.5), InkWars.Surface.FLOOR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.3;
        Keys forward = keys(new Vector(1, 0, 0), false);
        squid.tickFloor(forward);
        require(squid.surface == InkWars.Surface.WALL, "moving into wall must attach");
        double attachmentHeight = squid.centre.getY();
        for (int tick = 0; tick < 30 && squid.surface == InkWars.Surface.WALL; tick++) squid.tickWall(forward);
        require(squid.centre.getY() > attachmentHeight, "holding forward must climb");
        require(squid.surface == InkWars.Surface.FLOOR, "continuous wall must crest onto roof");
        close(4 + InkWars.SQUID_RIDE_HEIGHT, squid.centre.getY(), "crest reaches roof height");
        require(squid.centre.getX() >= 1, "crest places roof under body");
        require(squid.heading.getX() > 0.9, "climb momentum bends forward across roof");
    }

    private static void wallJumpMomentum() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0.69, 2, 0.5), InkWars.Surface.WALL);
        squid.wallSide = BlockFace.EAST;
        squid.heading = new Vector(0, 0.6, 0.8);
        squid.speed = 0.5;
        squid.tickWall(keys(new Vector(), true));
        require(squid.surface == InkWars.Surface.AIR, "wall jump detaches");
        close(0.4, squid.heading.getZ() * squid.speed, "wall jump retains sideways momentum");
        require(squid.heading.getX() * squid.speed < -0.39, "wall jump pushes away");
        require(squid.verticalSpeed > 0.3, "wall jump preserves upward movement and adds lift");
        require(squid.detachTicks > 0 && squid.detachedFace == BlockFace.WEST, "jump protects against immediate recapture");
    }

    private static void standingClearance() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(0, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(0, 2, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        require(!squid.standingClear(squid.standingSpot()), "low ceiling prevents human expansion");
        require(squid.standingClear(new Location(fixture.world, 2.5, 1, 0.5)), "open location permits standing");
        squid.surface = InkWars.Surface.CEILING;
        squid.centre = new Vector(0.5, 1.69, 0.5);
        require(squid.standingSpot().getY() + 1.8 <= squid.centre.getY() + 1e-8, "ceiling conversion expands downward");
        squid.surface = InkWars.Surface.WALL;
        squid.wallSide = BlockFace.EAST;
        double centerX = squid.centre.getX();
        require(squid.standingSpot().getX() < centerX, "wall conversion separates outward");
    }

    private static void wallRecaptureSuppression() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.6, 1.5, 0.5), InkWars.Surface.AIR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.3;
        squid.detachTicks = 4;
        squid.detachedFace = BlockFace.WEST;
        squid.tickAir(keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.AIR, "recent wall jump must not reattach despite inward input");
        require(squid.centre.getX() <= 1 - InkWars.SQUID_RADIUS, "detach protection must still collide with wall");

        Squid glancing = fixture.squid(new Vector(0.6, 1.5, 0.5), InkWars.Surface.AIR);
        glancing.heading = new Vector(1, 0, 0);
        glancing.speed = 0.3;
        glancing.tickAir(keys(new Vector(), false));
        require(glancing.surface == InkWars.Surface.AIR, "air contact without inward input must not capture");
    }

    private static Keys keys(Vector direction, boolean jump) {
        return new Keys(direction, jump, direction.lengthSquared() > 0 ? 1 : 0, 0);
    }

    private static final class TestGame extends InkWars {
        private World fixtureWorld;
        private Player fixturePlayer;

        @Override public World getWorld() { return fixtureWorld; }
        @Override public boolean isPaintable(Block block) { return !block.isPassable(); }

        class Info extends InkWarsPlayerInfo {
            @Override protected Player getPlayer() { return fixturePlayer; }
        }
    }

    private static final class Fixture {
        private final Map<String, Block> blocks = new HashMap<>();
        private final List<Block> solids = new ArrayList<>();
        private final UUID worldId = UUID.randomUUID();
        private final World world;
        private final TestGame game;
        private final Location playerLocation;
        private final List<HeardSound> heardSounds = new ArrayList<>();

        Fixture() throws Exception {
            world = proxy(World.class, (object, method, args) -> switch (method.getName()) {
                case "getBlockAt" -> args[0] instanceof Location location
                        ? block(location.getBlockX(), location.getBlockY(), location.getBlockZ())
                        : block((int) args[0], (int) args[1], (int) args[2]);
                case "getMinHeight" -> -64;
                case "getMaxHeight" -> 320;
                case "getUID" -> worldId;
                case "getName" -> "movement-fixture";
                case "rayTraceBlocks" -> rayTrace((Location) args[0], (Vector) args[1], (double) args[2]);
                case "playSound" -> {
                    heardSounds.add(new HeardSound((Sound) args[1], (float) args[2], (float) args[3], false));
                    yield null;
                }
                default -> defaultValue(object, method, args);
            });
            // The normal constructor registers a live plugin event bus; this fixture only exercises geometry.
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Object allocator = unsafeField.get(null);
            game = (TestGame) unsafeClass.getMethod("allocateInstance", Class.class).invoke(allocator, TestGame.class);
            game.fixtureWorld = world;
            playerLocation = new Location(world, 0, 2, 0, -90, 0);
            game.fixturePlayer = proxy(Player.class, (object, method, args) -> switch (method.getName()) {
                case "getLocation" -> playerLocation.clone();
                case "getCurrentInput" -> proxy(Input.class, (inputObject, inputMethod, inputArgs) ->
                        inputMethod.getName().equals("isForward") ? true : defaultValue(inputObject, inputMethod, inputArgs));
                case "getWorld" -> world;
                case "playSound" -> {
                    heardSounds.add(new HeardSound((Sound) args[1], (float) args[2], (float) args[3], true));
                    yield null;
                }
                default -> defaultValue(object, method, args);
            });
        }

        Squid squid(Vector center, InkWars.Surface surface) {
            Squid squid = game.new Info().new Squid();
            squid.centre = center;
            squid.surface = surface;
            squid.controlForward = new Vector(1, 0, 0);
            squid.controlRight = new Vector(0, 0, 1);
            return squid;
        }

        Squid wallSquid(Vector center, Vector heading) {
            Squid squid = squid(center, InkWars.Surface.WALL);
            squid.wallSide = BlockFace.EAST;
            squid.heading = heading.clone();
            squid.controlForward = heading.clone();
            squid.speed = 0.3;
            squid.gripped = block(1, 1, 0);
            squid.gripContact = new Squid.Contact(squid.gripped, BlockFace.WEST, 0, new Vector(1, center.getY(), center.getZ()), squid.gripped.getBoundingBox());
            return squid;
        }

        void move(Squid squid, Keys keys) {
            int steps = Math.max(1, (int) Math.ceil((squid.speed + (squid.surface == InkWars.Surface.AIR ? Math.abs(squid.verticalSpeed) : 0) + InkWars.SQUID_ACCELERATION) / InkWars.SQUID_MOVEMENT_STEP));
            squid.movementFraction = 1D / steps;
            for (int step = 0; step < steps; step++) {
                squid.firstMovementStep = step == 0;
                switch (squid.surface) {
                    case FLOOR -> squid.tickFloor(keys);
                    case WALL -> squid.tickWall(keys);
                    case CEILING -> squid.tickCeiling(keys);
                    case AIR -> squid.tickAir(keys);
                }
                for (Block block : solids) for (BoundingBox local : block.getCollisionShape().getBoundingBoxes()) {
                    require(!SweptBoxCollision.overlaps(squid.centre, new Vector(InkWars.SQUID_RADIUS, InkWars.SQUID_RADIUS, InkWars.SQUID_RADIUS), local.clone().shift(block.getLocation())),
                            "body overlaps " + block.getLocation() + " at " + squid.centre + " on " + squid.surface);
                }
            }
        }

        void add(int x, int y, int z, Material material, BoundingBox... localShapes) {
            Block block = makeBlock(x, y, z, material, localShapes);
            blocks.put(key(x, y, z), block);
            solids.add(block);
        }

        Block block(int x, int y, int z) {
            return blocks.computeIfAbsent(key(x, y, z), ignored -> makeBlock(x, y, z, Material.AIR));
        }

        Block makeBlock(int x, int y, int z, Material material, BoundingBox... localShapes) {
            BoundingBox aggregate = localShapes.length == 0 ? new BoundingBox() : localShapes[0].clone();
            for (BoundingBox localShape : localShapes) aggregate.union(localShape);
            VoxelShape shape = proxy(VoxelShape.class, (object, method, args) -> switch (method.getName()) {
                case "getBoundingBoxes" -> List.of(localShapes);
                case "overlaps" -> java.util.Arrays.stream(localShapes).anyMatch(box -> box.overlaps((BoundingBox) args[0]));
                default -> defaultValue(object, method, args);
            });
            return proxy(Block.class, (object, method, args) -> switch (method.getName()) {
                case "getX" -> x;
                case "getY" -> y;
                case "getZ" -> z;
                case "getWorld" -> world;
                case "getLocation" -> new Location(world, x, y, z);
                case "getType" -> material;
                case "isPassable", "isEmpty" -> localShapes.length == 0;
                case "getCollisionShape" -> shape;
                case "getBoundingBox" -> aggregate.clone().shift(x, y, z);
                case "getRelative" -> {
                    BlockFace face = (BlockFace) args[0];
                    int distance = args.length == 2 ? (int) args[1] : 1;
                    yield block(x + face.getModX() * distance, y + face.getModY() * distance, z + face.getModZ() * distance);
                }
                default -> defaultValue(object, method, args);
            });
        }

        RayTraceResult rayTrace(Location origin, Vector direction, double distance) {
            RayTraceResult nearest = null;
            double nearestDistance = Double.POSITIVE_INFINITY;
            for (Block block : solids) for (BoundingBox local : block.getCollisionShape().getBoundingBoxes()) {
                RayTraceResult hit = local.clone().shift(block.getLocation()).rayTrace(origin.toVector(), direction, distance);
                if (hit != null && hit.getHitPosition().distanceSquared(origin.toVector()) < nearestDistance) {
                    nearestDistance = hit.getHitPosition().distanceSquared(origin.toVector());
                    nearest = new RayTraceResult(hit.getHitPosition(), block, hit.getHitBlockFace());
                }
            }
            return nearest;
        }
    }

    private static String key(int x, int y, int z) { return x + ":" + y + ":" + z; }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }

    private static Object defaultValue(Object object, Method method, Object[] args) {
        return switch (method.getName()) {
            case "equals" -> object == args[0];
            case "hashCode" -> System.identityHashCode(object);
            case "toString" -> "movement fixture " + object.getClass().getInterfaces()[0].getSimpleName();
            default -> {
                if (method.getReturnType() == boolean.class) yield false;
                if (method.getReturnType() == int.class) yield 0;
                if (method.getReturnType() == long.class) yield 0L;
                if (method.getReturnType() == double.class) yield 0D;
                if (method.getReturnType() == float.class) yield 0F;
                yield null;
            }
        };
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void close(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 1e-6) throw new AssertionError(message + ": " + actual);
    }
}
