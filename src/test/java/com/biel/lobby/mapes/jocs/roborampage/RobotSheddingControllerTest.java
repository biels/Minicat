package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RobotSheddingControllerTest {
    @BeforeAll
    static void registries() throws Exception {
        MobilityAndCutterControllerTest.registries();
        // Extend the existing fixture for the living entity's maximum-health attribute.
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        sun.misc.Unsafe allocator = (sun.misc.Unsafe) field.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = stub(io.papermc.paper.registry.RegistryAccess.class, (method, args) ->
                method.equals("getRegistry") ? stub(Registry.class, (operation, values) -> {
                    if (!operation.equals("get") && !operation.equals("getOrThrow")) return null;
                    String registry = args[0].toString().toLowerCase(Locale.ROOT);
                    if (registry.contains("sound")) return stub(Sound.class, (m, a) -> null);
                    if (registry.contains("attribute")) return stub(Attribute.class, (m, a) -> null);
                    return stub(BlockType.class, (m, a) -> m.equals("isAir") && values[0].toString().equals("minecraft:air"));
                }) : null);
        allocator.putObject(allocator.staticFieldBase(providerField), allocator.staticFieldOffset(providerField), Optional.of(provider));
        Field serverField = Bukkit.class.getDeclaredField("server");
        serverField.setAccessible(true);
        serverField.set(null, stub(Server.class, (method, args) -> method.equals("createBlockData")
                ? stub(BlockData.class, (operation, values) -> null) : null));
    }

    @Test
    void crossingHalfHealthWaitsForRealDamageThenStripsExactlyOnePieceAndEmitsOnce() {
        Fixture fixture = new Fixture();
        fixture.health = 12;
        fixture.controller.onDamage(fixture.damage(2));
        assertEquals(1, fixture.deferred.size());
        assertEquals(4, fixture.armorCount());
        assertTrue(fixture.emissions.isEmpty(), "MONITOR must not reward projected damage before health changes");
        fixture.health = 10;
        fixture.flush();
        assertEquals(1, fixture.emissions.size());
        assertEquals(3, fixture.armorCount());
        assertSame(fixture.world, fixture.emissions.getFirst().origin().getWorld());
        assertTrue(Double.isFinite(fixture.emissions.getFirst().velocity().length()));
        assertSame(fixture.weapon, fixture.equipment.getItemInMainHand(), "shedding only touches armor");
    }

    @Test
    void anyRealDamageAtHalfHealthGuaranteesOneShedRatherThanARandomChance() {
        EnumSet<EquipmentSlot> strippedSlots = EnumSet.noneOf(EquipmentSlot.class);
        for (int seed = 0; seed < 12; seed++) {
            Fixture fixture = new Fixture(seed);
            fixture.health = 10;
            fixture.controller.onDamage(fixture.damage(1));
            fixture.health = 9;
            fixture.flush();
            assertEquals(1, fixture.emissions.size());
            assertEquals(3, fixture.armorCount());
            fixture.armor.forEach((slot, item) -> { if (item == null) strippedSlots.add(slot); });
        }
        assertTrue(strippedSlots.size() > 1, "ordinary shedding chooses among equipped armor slots");
    }

    @Test
    void repeatedHitsAndDuplicateDamageDeliveryCannotFarmArmorOrScrap() {
        Fixture fixture = new Fixture();
        fixture.health = 12;
        EntityDamageEvent first = fixture.damage(2);
        fixture.controller.onDamage(first);
        fixture.controller.onDamage(first);
        fixture.health = 10;
        fixture.flush();
        for (int remainingHealth = 9; remainingHealth >= 5; remainingHealth--) {
            fixture.controller.onDamage(fixture.damage(1));
            fixture.health = remainingHealth;
            fixture.flush();
        }
        assertEquals(1, fixture.emissions.size());
        assertEquals(3, fixture.armorCount());
    }

    @Test
    void lethalCancelledZeroDamageAboveHalfAndUntrackedDamageNeverQueueShedding() {
        for (int invalid = 0; invalid < 5; invalid++) {
            Fixture fixture = new Fixture();
            fixture.health = 12;
            EntityDamageEvent damage = fixture.damage(2);
            switch (invalid) {
                case 0 -> damage = fixture.damage(12);
                case 1 -> damage.setCancelled(true);
                case 2 -> damage = fixture.damage(0);
                case 3 -> fixture.health = 20;
                case 4 -> fixture.tracked = false;
            }
            fixture.controller.onDamage(damage);
            assertTrue(fixture.deferred.isEmpty());
            fixture.flush();
            assertTrue(fixture.emissions.isEmpty());
            assertEquals(4, fixture.armorCount());
        }
    }

    @Test
    void queueFailureKeepsBothTheArmorAndTheOneSuccessfulShedBudgetForARetry() {
        Fixture fixture = new Fixture();
        fixture.health = 12;
        fixture.acceptEmission = false;
        fixture.controller.onDamage(fixture.damage(2));
        fixture.health = 10;
        fixture.flush();
        assertEquals(1, fixture.emissionAttempts);
        assertTrue(fixture.emissions.isEmpty());
        assertEquals(4, fixture.armorCount());
        fixture.acceptEmission = true;
        fixture.controller.onDamage(fixture.damage(1));
        fixture.health = 9;
        fixture.flush();
        assertEquals(2, fixture.emissionAttempts);
        assertEquals(1, fixture.emissions.size());
        assertEquals(3, fixture.armorCount());
    }

    @Test
    void deferredRewardRequiresObservedHealthLossAndSurvivingTrackedEntity() {
        for (int invalid = 0; invalid < 7; invalid++) {
            Fixture fixture = new Fixture();
            fixture.health = 12;
            EntityDamageEvent event = fixture.damage(2);
            fixture.controller.onDamage(event);
            assertEquals(1, fixture.deferred.size());
            fixture.health = 10;
            switch (invalid) {
                case 0 -> fixture.health = 12;
                case 1 -> event.setCancelled(true);
                case 2 -> { fixture.dead = true; fixture.health = 0; }
                case 3 -> { fixture.tracked = false; fixture.controller.forget(fixture.robotId); }
                case 4 -> fixture.valid = false;
                case 5 -> fixture.health = 11;
                case 6 -> event.setDamage(0);
            }
            fixture.flush();
            assertTrue(fixture.emissions.isEmpty());
            assertEquals(4, fixture.armorCount());
        }
    }

    @Test
    void closingMatchPreventsQueuedCallbacksAndFutureDamageFromRewardingScrap() {
        Fixture fixture = new Fixture();
        fixture.health = 12;
        fixture.controller.onDamage(fixture.damage(2));
        fixture.health = 10;
        fixture.controller.close();
        fixture.controller.close();
        fixture.flush();
        fixture.controller.onDamage(fixture.damage(1));
        fixture.flush();
        assertTrue(fixture.emissions.isEmpty());
        assertEquals(4, fixture.armorCount());
    }

    @Test
    void aNonlethalDirectPlayerCriticalKnocksOffTheIronHeadAboveHalfHealthOnce() {
        Fixture fixture = new Fixture();
        fixture.armor.put(EquipmentSlot.HEAD, new TestItem(Material.IRON_BLOCK));
        fixture.health = 20;
        fixture.controller.onDamage(fixture.critical(1));
        fixture.health = 19;
        fixture.flush();
        assertEquals(1, fixture.emissions.size());
        assertNull(fixture.equipment.getHelmet());
        assertEquals(3, fixture.armorCount());
        fixture.controller.onDamage(fixture.critical(1));
        fixture.health = 18;
        fixture.flush();
        assertEquals(1, fixture.emissions.size());
    }

    private record Emission(Location origin, Vector velocity) {}

    private static final class TestItem extends ItemStack {
        private final Material material;
        TestItem(Material material) { super(); this.material = material; }
        @Override public Material getType() { return material; }
        @Override public int getAmount() { return 1; }
        @Override public boolean isEmpty() { return material == Material.AIR; }
    }

    private static final class Fixture {
        final UUID robotId = UUID.randomUUID();
        final List<Runnable> deferred = new ArrayList<>();
        final List<Emission> emissions = new ArrayList<>();
        final Map<EquipmentSlot, ItemStack> armor = new EnumMap<>(EquipmentSlot.class);
        final ItemStack weapon = new TestItem(Material.IRON_SWORD);
        final World world = stub(World.class, (m, a) -> null);
        final Location robotLocation = new Location(world, 2.5, 1, 0.5);
        double health = 20;
        boolean tracked = true, valid = true, dead, acceptEmission = true;
        int emissionAttempts;
        final AttributeInstance maximumHealth = stub(AttributeInstance.class, (m, a) ->
                m.equals("getValue") || m.equals("getBaseValue") ? 20.0 : null);
        final EntityEquipment equipment = stub(EntityEquipment.class, (m, a) -> switch (m) {
            case "getItem" -> armor.get(a[0]);
            case "setItem" -> { armor.put((EquipmentSlot) a[0], (ItemStack) a[1]); yield null; }
            case "getHelmet" -> armor.get(EquipmentSlot.HEAD);
            case "getChestplate" -> armor.get(EquipmentSlot.CHEST);
            case "getLeggings" -> armor.get(EquipmentSlot.LEGS);
            case "getBoots" -> armor.get(EquipmentSlot.FEET);
            case "setHelmet" -> { armor.put(EquipmentSlot.HEAD, (ItemStack) a[0]); yield null; }
            case "setChestplate" -> { armor.put(EquipmentSlot.CHEST, (ItemStack) a[0]); yield null; }
            case "setLeggings" -> { armor.put(EquipmentSlot.LEGS, (ItemStack) a[0]); yield null; }
            case "setBoots" -> { armor.put(EquipmentSlot.FEET, (ItemStack) a[0]); yield null; }
            case "getArmorContents" -> new ItemStack[]{armor.get(EquipmentSlot.FEET), armor.get(EquipmentSlot.LEGS),
                    armor.get(EquipmentSlot.CHEST), armor.get(EquipmentSlot.HEAD)};
            case "getItemInMainHand", "getItemInHand" -> weapon;
            default -> null;
        });
        final Mob robot = stub(Mob.class, (m, a) -> switch (m) {
            case "getUniqueId" -> robotId;
            case "getWorld" -> world;
            case "getLocation" -> robotLocation.clone();
            case "getHeight" -> 2.0;
            case "isValid" -> valid;
            case "isDead" -> dead;
            case "getHealth" -> health;
            case "getMaxHealth" -> 20.0;
            case "getAttribute" -> maximumHealth;
            case "getEquipment" -> equipment;
            default -> null;
        });
        final Player attacker = stub(Player.class, (m, a) -> m.equals("getLocation")
                ? new Location(world, 0.5, 1, 0.5) : m.equals("getWorld") ? world : null);
        final DamageSource damageSource = stub(DamageSource.class, (m, a) -> null);
        final RobotSheddingController controller;
        Fixture() { this(123); }
        Fixture(int seed) {
            armor.put(EquipmentSlot.HEAD, new TestItem(Material.IRON_HELMET));
            armor.put(EquipmentSlot.CHEST, new TestItem(Material.IRON_CHESTPLATE));
            armor.put(EquipmentSlot.LEGS, new TestItem(Material.IRON_LEGGINGS));
            armor.put(EquipmentSlot.FEET, new TestItem(Material.IRON_BOOTS));
            controller = new RobotSheddingController(world, id -> tracked && id.equals(robotId),
                    new Random(seed), deferred::add, (origin, velocity) -> {
                        emissionAttempts++;
                        if (!acceptEmission) return false;
                        emissions.add(new Emission(origin.clone(), velocity.clone()));
                        return true;
                    });
        }
        EntityDamageEvent damage(double amount) {
            return new EntityDamageEvent(robot, DamageCause.MAGIC, damageSource, amount);
        }
        EntityDamageEvent critical(double amount) {
            return new EntityDamageByEntityEvent(attacker, robot, DamageCause.ENTITY_ATTACK, damageSource, amount) {
                @Override public boolean isCritical() { return true; }
            };
        }
        int armorCount() { return (int) armor.values().stream().filter(item -> item != null && !item.isEmpty()).count(); }
        void flush() {
            List<Runnable> pending = new ArrayList<>(deferred);
            deferred.clear();
            pending.forEach(Runnable::run);
        }
    }

    @FunctionalInterface private interface Answer { Object call(String method, Object[] args); }
    private static <T> T stub(Class<T> type, Answer answer) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("equals")) return proxy == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
            Object value = answer.call(method.getName(), args);
            if (value != null) return value;
            if (method.getReturnType() == boolean.class) return false;
            if (method.getReturnType() == int.class) return 0;
            if (method.getReturnType() == double.class) return 0.0;
            if (method.getReturnType() == float.class) return 0.0F;
            return null;
        }));
    }
}
