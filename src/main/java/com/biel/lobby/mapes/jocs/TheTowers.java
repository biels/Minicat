package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;

import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.localization.TeamNames;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.minions.Minion;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;

/** Quijx's classic arena, with match ownership and rules supplied by Minicat. */
public class TheTowers extends JocTeamScoreRace implements Listener {
    List<BoundingBox> scoringPits = List.of();
    List<BoundingBox> protectedAreas = List.of();
    BoundingBox arenaBounds;
    private final List<Location> teamSpawns = new ArrayList<>();
    private List<Location> ironGenerators = List.of(), lapisGenerators = List.of();
    private int scoreToWin = 10, generatorIntervalSeconds = 1, generatorItemLifetimeTicks = 100;
    private static final int BRIDGE_LANES_PER_TEAM = 6, MAX_BRIDGE_STEPS = 128, MAX_BUILDER_CREDITS = 12;
    private List<List<BridgeLane>> bridgeLanes = List.of();
    private final int[] builderCredits = new int[2], nextBuilderLane = new int[2];
    private boolean builderOnKill = true;
    private int builderStartSeconds = 300, builderIntervalSeconds = 60, builderMaxActivePerTeam = 2,
            builderLifetimeSeconds = 120, nextPeriodicBuilderSecond = 300;

    @Override public String getGameName() { return "The Towers"; }

    @Override public void initialize() {
        super.initialize();
        scoreToWin = positiveProperty("ScoreToWin", 10);
        generatorIntervalSeconds = positiveProperty("GeneratorIntervalSeconds", 1);
        generatorItemLifetimeTicks = positiveProperty("GeneratorItemLifetimeTicks", 100);
        if (generatorItemLifetimeTicks > 6000) throw new IllegalArgumentException("Generator item lifetime must not exceed 6000 ticks");
        for (int team = 0; team < 2; team++) {
            Location spawn = requiredLocation("base" + team).add(0.5, 0, 0.5);
            spawn.setYaw(pMapaActual().ObtenirPropietatInt("SpawnYaw" + team));
            teamSpawns.add(spawn);
        }
        scoringPits = List.of(readBox("Pool0Min", "Pool0Max"), readBox("Pool1Min", "Pool1Max"));
        arenaBounds = readBox("ArenaMin", "ArenaMax");
        List<BoundingBox> protectedBoxes = new ArrayList<>();
        for (int index = 0; pMapaActual().ExisteixPropietat("ProtectedMin_" + index); index++) {
            protectedBoxes.add(readBox("ProtectedMin_" + index, "ProtectedMax_" + index));
        }
        if (protectedBoxes.isEmpty()) throw new IllegalArgumentException("The Towers requires protected infrastructure regions");
        protectedAreas = List.copyOf(protectedBoxes);
        ironGenerators = pMapaActual().ObtenirLocations("IronGenerator", world);
        lapisGenerators = pMapaActual().ObtenirLocations("LapisGenerator", world);
        if (ironGenerators.isEmpty() || lapisGenerators.isEmpty()) {
            throw new IllegalArgumentException("The Towers requires iron and lapis generator coordinates");
        }
        configureBridges();
        // These native events are not forwarded by the shared world event bus.
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private int positiveProperty(String name, int fallback) {
        int value = pMapaActual().ExisteixPropietat(name) ? pMapaActual().ObtenirPropietatInt(name) : fallback;
        if (value < 1) throw new IllegalArgumentException(name + " must be positive");
        return value;
    }

    private void configureBridges() {
        builderStartSeconds = pMapaActual().ExisteixPropietat("BuilderStartSeconds")
                ? pMapaActual().ObtenirPropietatInt("BuilderStartSeconds") : 300;
        if (builderStartSeconds < 0) throw new IllegalArgumentException("BuilderStartSeconds must not be negative");
        builderIntervalSeconds = positiveProperty("BuilderIntervalSeconds", 60);
        builderMaxActivePerTeam = positiveProperty("BuilderMaxActivePerTeam", 2);
        builderLifetimeSeconds = positiveProperty("BuilderLifetimeSeconds", 120);
        if (builderMaxActivePerTeam > 6 || builderLifetimeSeconds > 600) {
            throw new IllegalArgumentException("The Towers builders require at most 6 active per team and a lifetime of at most 600 seconds");
        }
        if (pMapaActual().ExisteixPropietat("BuilderOnKill")) {
            String value = pMapaActual().ObtenirPropietat("BuilderOnKill");
            if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                throw new IllegalArgumentException("BuilderOnKill must be true or false");
            }
            builderOnKill = Boolean.parseBoolean(value);
        }
        List<String> properties = pMapaActual().ObtenirPropietats();
        if (properties.stream().noneMatch(name -> name.startsWith("BridgeLane"))) return;
        List<List<BridgeLane>> teams = new ArrayList<>();
        for (int team = 0; team < 2; team++) {
            List<BridgeLane> lanes = new ArrayList<>();
            for (int lane = 0; lane < BRIDGE_LANES_PER_TEAM; lane++) {
                String prefix = "BridgeLane" + team + "_" + lane;
                List<Location> waypoints = pMapaActual().ObtenirLocations(prefix, world);
                if (waypoints.size() < 2) throw new IllegalArgumentException("Missing The Towers bridge route: " + prefix);
                List<Location> floors = expandBridgeRoute(waypoints, arenaBounds);
                for (int waypoint = 0; waypoint < waypoints.size(); waypoint++) properties.remove(prefix + "_" + waypoint);
                lanes.add(new BridgeLane(floors));
            }
            teams.add(List.copyOf(lanes));
        }
        if (properties.stream().anyMatch(name -> name.startsWith("BridgeLane"))) {
            throw new IllegalArgumentException("The Towers bridge routes must use contiguous waypoints and exactly six lanes per team");
        }
        bridgeLanes = List.copyOf(teams);
    }

    /** Floor coordinates: cardinal x segments, with at most one stair rise per block. */
    static List<Location> expandBridgeRoute(List<Location> waypoints, BoundingBox bounds) {
        if (waypoints.size() < 2 || waypoints.size() > 16) throw new IllegalArgumentException("A bridge requires 2 to 16 waypoints");
        List<Location> floors = new ArrayList<>();
        for (int waypoint = 0; waypoint < waypoints.size(); waypoint++) {
            Location point = waypoints.get(waypoint);
            if (point.getWorld() == null || point.getWorld() != waypoints.getFirst().getWorld()
                    || point.getX() != point.getBlockX() || point.getY() != point.getBlockY() || point.getZ() != point.getBlockZ()) {
                throw new IllegalArgumentException("Bridge waypoints must be integer floor coordinates in the arena world");
            }
        }
        floors.add(waypoints.getFirst().clone());
        for (int waypoint = 1; waypoint < waypoints.size(); waypoint++) {
            Location from = waypoints.get(waypoint - 1), to = waypoints.get(waypoint);
            int dx = to.getBlockX() - from.getBlockX(), dy = to.getBlockY() - from.getBlockY();
            if (dx == 0 || to.getBlockZ() != from.getBlockZ() || Math.abs(dy) > Math.abs(dx)) {
                throw new IllegalArgumentException("Bridge segments must run along x with walkable one-block stairs");
            }
            if (floors.size() + Math.abs(dx) > MAX_BRIDGE_STEPS) throw new IllegalArgumentException("Bridge route exceeds 128 blocks");
            for (int step = 1; step <= Math.abs(dx); step++) {
                floors.add(from.clone().add(Integer.signum(dx) * step,
                        Integer.signum(dy) * Math.min(step, Math.abs(dy)), 0));
            }
        }
        for (Location floor : floors) {
            // Keep the eventual three-block bridge and the golem's two-block body inside the playable arena.
            for (int side = -1; side <= 1; side++) for (int height = 0; height <= 2; height++) {
                if (!bounds.contains(floor.clone().add(0, height, side).toVector())) {
                    throw new IllegalArgumentException("Bridge route leaves the arena bounds");
                }
            }
        }
        return List.copyOf(floors);
    }

    private Location requiredLocation(String name) {
        if (!pMapaActual().ExisteixPropietat(name)) throw new IllegalArgumentException("Missing The Towers coordinate: " + name);
        return pMapaActual().ObtenirLocation(name, world);
    }

    private BoundingBox readBox(String minName, String maxName) {
        Location min = requiredLocation(minName), max = requiredLocation(maxName);
        // Properties are inclusive block corners; Bukkit boxes have an exclusive upper edge.
        return new BoundingBox(Math.min(min.getX(), max.getX()), Math.min(min.getY(), max.getY()), Math.min(min.getZ(), max.getZ()),
                Math.max(min.getX(), max.getX()) + 1, Math.max(min.getY(), max.getY()) + 1, Math.max(min.getZ(), max.getZ()) + 1);
    }

    @Override protected ArrayList<Equip> getDesiredTeams() {
        ArrayList<Equip> teams = new ArrayList<>();
        for (DyeColor color : List.of(DyeColor.RED, DyeColor.BLUE)) {
            teams.add(new EquipScoreRace(color, color.name().toLowerCase(java.util.Locale.ROOT)) {
                @Override public Location getTeamSpawnLocation() { return teamSpawns.get(getId()).clone(); }
            });
        }
        return teams;
    }

    @Override protected boolean canStartGame() {
        if (!super.canStartGame()) return false;
        if (Equips.stream().anyMatch(team -> team.getPlayers().stream().noneMatch(getPlayers()::contains))) {
            sendGlobalMessage(MessageKey.TOWERS_NEED_OPPONENT);
            return false;
        }
        return true;
    }

    @Override protected int getFinishScore() { return scoreToWin; }

    @Override protected void setCustomGameRules() {
        world.setGameRule(GameRules.KEEP_INVENTORY, false);
        world.setGameRule(GameRules.ADVANCE_WEATHER, false);
        world.setStorm(false);
        world.setTime(6000);
    }

    @Override protected void customJocIniciat() {
        // Both teams are populated before start; two active players means a 1v1.
        // Select once, before the shared start hook publishes the scoreboard.
        if (getPlayers().size() == 2) scoreToWin = 5;
        super.customJocIniciat();
        setBlockBreakPlace(true);
        nextPeriodicBuilderSecond = builderStartSeconds;
    }

    @Override protected void customJocFinalitzat() {
        setBlockBreakPlace(false);
        super.customJocFinalitzat();
    }

    @Override protected ArrayList<ItemStack> getStartingItems(Player player) {
        Equip team = obtenirEquip(player);
        ArrayList<ItemStack> items = new ArrayList<>();
        for (Material material : List.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
                Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS)) {
            ItemStack armor = Utils.createColoredTeamArmor(material, team);
            ItemMeta metadata = armor.getItemMeta();
            metadata.setUnbreakable(true);
            armor.setItemMeta(metadata);
            if (material == Material.LEATHER_LEGGINGS) armor.addEnchantment(Enchantment.PROJECTILE_PROTECTION, 2);
            items.add(armor);
        }
        items.add(new ItemStack(Material.valueOf(team.getColor().name() + "_STAINED_GLASS"), 16));
        items.add(new ItemStack(Material.BAKED_POTATO, 8));
        return items;
    }

    @Override protected void donarEfectesInicials(Player player) {
        super.donarEfectesInicials(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0));
    }

    @Override protected void onPlayerDeathByPlayer(PlayerDeathEvent event, Player killed, Player killer) {
        super.onPlayerDeathByPlayer(event, killed, killer);
        if (JocEnMarxa() && killed != killer && areEnemies(killed, killer) && !isSpectator(killer)) {
            killer.giveExpLevels(4);
            if (builderOnKill && getPlayers().contains(killed) && getPlayers().contains(killer)
                    && killer.getWorld() == world && killed.getWorld() == world
                    && killed.getGameMode() == GameMode.SURVIVAL && killer.getGameMode() == GameMode.SURVIVAL
                    && !isSpectator(killed)) requestBuilder(obtenirEquip(killer));
        }
    }

    @Override protected void onPlayerMove(PlayerMoveEvent event, Player player) {
        super.onPlayerMove(event, player);
        if (!event.isCancelled() && JocEnMarxa() && !player.isDead() && player.getWorld() == world
                && player.getGameMode() == GameMode.SURVIVAL && getPlayers().contains(player) && !isSpectator(player)
                && event.getTo().getY() < getMinimumHeight()) player.setHealth(0);
    }

    @Override public void ultraHeartbeat() {
        super.ultraHeartbeat();
        if (!JocEnMarxa()) return;
        for (Player player : getPlayers()) checkScoring(player);
        if (!JocEnMarxa() || getUltraHeartbeatCount() % 5 != 0) return;
        for (List<BridgeLane> lanes : bridgeLanes) for (BridgeLane lane : lanes) {
            // Another plugin can remove an entity without a death event; that must also free the corridor.
            if (lane.builder != null && !lane.builder.isAlive()) lane.builder.release();
        }
        for (Minion minion : new ArrayList<>(minions())) if (minion instanceof BridgeBuilder builder) builder.buildAndWalk();
        for (int team = 0; team < bridgeLanes.size(); team++) spendBuilderCredits(team);
    }

    void checkScoring(Player player) {
        if (!JocEnMarxa() || player.isDead() || player.getWorld() != world
                || player.getGameMode() != GameMode.SURVIVAL || !getPlayers().contains(player) || isSpectator(player)) return;
        EquipScoreRace team = obtenirEquip(player, EquipScoreRace.class);
        if (team == null) return;
        int enemy = 1 - team.getId();
        if (!scoringPits.get(enemy).contains(player.getLocation().toVector())) return;
        // Successful return leaves the pit before scoring, so another tick cannot count the same entry.
        if (!player.teleport(team.getTeamSpawnLocation())) return;
        for (Player viewer : getViewers()) Messages.send(viewer, MessageKey.TOWERS_SCORED,
                MessageArgument.text("player", player.getName()), new MessageArgument("team", team.getLocalizedName(viewer)),
                MessageArgument.number("score", team.getScore() + 1), MessageArgument.number("target", getFinishScore()));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2F);
        getPlayerInfo(player).setObjectivesCompleted(getPlayerInfo(player).getObjectivesCompleted() + 1);
        team.incrementScore();
    }

    @Override public void comprovarGuanyador() {
        if (!JocEnMarxa()) return;
        EquipScoreRace winner = getOrderedWinnerList().getFirst();
        if (winner.getScore() < getFinishScore()) return;
        for (Player viewer : getViewers()) Messages.send(viewer, MessageKey.TOWERS_WINNER,
                new MessageArgument("team", winner.getLocalizedName(viewer)));
        winGame(winner);
    }

    @Override public void heartbeat() {
        super.heartbeat();
        if (!JocEnMarxa()) return;
        if (!bridgeLanes.isEmpty() && periodicBuilderDue(segonsTranscorreguts())) {
            for (Equip team : Equips) requestBuilder(team);
            // Resume at the current time after lag, rather than creating a catch-up swarm.
            nextPeriodicBuilderSecond = segonsTranscorreguts() + builderIntervalSeconds;
        }
        if (getHeartbeatCount() % generatorIntervalSeconds != 0) return;
        for (Location generator : ironGenerators) generateResource(generator, Material.IRON_INGOT);
        for (Location generator : lapisGenerators) generateResource(generator, Material.LAPIS_LAZULI);
    }

    private void generateResource(Location generator, Material material) {
        Location drop = generator.clone().add(0.5, 0.5, 0.5);
        int waiting = world.getNearbyEntities(drop, 2, 2, 2).stream()
                .filter(entity -> entity instanceof Item item && item.getItemStack().getType() == material)
                .mapToInt(entity -> ((Item) entity).getItemStack().getAmount()).sum();
        if (waiting >= 16) return;
        Item item = world.dropItem(drop, new ItemStack(material));
        item.setVelocity(new org.bukkit.util.Vector());
        item.setTicksLived(6000 - generatorItemLifetimeTicks); // Preserve the original short-lived resource drops.
    }

    boolean periodicBuilderDue(int elapsedSeconds) {
        return JocEnMarxa() && !bridgeLanes.isEmpty() && elapsedSeconds >= nextPeriodicBuilderSecond;
    }

    void requestBuilder(Equip team) {
        if (!JocEnMarxa() || bridgeLanes.isEmpty() || team == null || team.getId() < 0 || team.getId() > 1) return;
        int side = team.getId();
        builderCredits[side] = Math.min(MAX_BUILDER_CREDITS, builderCredits[side] + 1);
        spendBuilderCredits(side);
    }

    private void spendBuilderCredits(int side) {
        if (!JocEnMarxa() || builderCredits[side] == 0 || bridgeLanes.isEmpty()) return;
        List<BridgeLane> lanes = bridgeLanes.get(side);
        while (builderCredits[side] > 0 && lanes.stream().filter(lane -> lane.active).count() < builderMaxActivePerTeam) {
            BridgeLane selected = chooseBridgeLane(side);
            if (selected == null) {
                // Credits can wait for a living builder, but never keep a finished map's queue alive.
                if (lanes.stream().noneMatch(lane -> lane.active)) builderCredits[side] = 0;
                return;
            }
            int width = selected.nextWidth();
            if (!prepareBridgeFloor(selected.floors.getFirst(), width)) return;
            BridgeBuilder builder = new BridgeBuilder(Equips.get(side), selected, width);
            selected.active = true;
            selected.builder = builder;
            builderCredits[side]--;
            try {
                enlist(builder, bridgeFeet(selected.floors.getFirst()));
            } catch (RuntimeException failure) {
                builder.remove();
                throw failure;
            }
            for (Player viewer : getViewers()) Messages.send(viewer, MessageKey.TOWERS_BUILDER_SPAWNED,
                    new MessageArgument("team", builder.team().getLocalizedName(viewer)), MessageArgument.number("width", width));
        }
    }

    BridgeLane chooseBridgeLane(int side) {
        List<BridgeLane> lanes = bridgeLanes.get(side);
        // A second builder reinforces an arrived narrow bridge before another balcony starts.
        for (int pass = 0; pass < 2; pass++) for (int offset = 0; offset < lanes.size(); offset++) {
            int index = (nextBuilderLane[side] + offset) % lanes.size();
            BridgeLane lane = lanes.get(index);
            if (lane.active || (pass == 0) != (lane.completedWidth == 1)) continue;
            if (lanes.stream().anyMatch(other -> other.active && lane.overlaps(other))) continue;
            Location start = lane.floors.getFirst();
            if (!bridgeSpaceClear(start) || (!safeBridgeSupport(start.getBlock().getType()) && !canPlaceBridge(start.getBlock()))
                    || !bridgeNeedsWork(lane, lane.nextWidth())) continue;
            nextBuilderLane[side] = (index + 1) % lanes.size();
            return lane;
        }
        return null;
    }

    boolean bridgeNeedsWork(BridgeLane lane, int width) {
        int radius = width / 2;
        for (Location floor : lane.floors) for (int side = -radius; side <= radius; side++) {
            Block block = floor.clone().add(0, 0, side).getBlock();
            if (canPlaceBridge(block)) return true;
        }
        return false;
    }

    boolean canPlaceBridge(Block block) {
        return block.getWorld() == world && arenaBounds.contains(block.getLocation().toVector())
                && !isProtected(block) && (block.getType().isAir() || block.getType() == Material.SNOW);
    }

    boolean prepareBridgeFloor(Location floor, int width) {
        for (int side = -(width / 2); side <= width / 2; side++) {
            Block block = floor.clone().add(0, 0, side).getBlock();
            if (canPlaceBridge(block)) block.setType(Material.QUARTZ_BLOCK, false);
        }
        return safeBridgeSupport(floor.getBlock().getType()) && bridgeSpaceClear(floor);
    }

    private static boolean safeBridgeSupport(Material support) {
        return support.isSolid() && support != Material.MAGMA_BLOCK && support != Material.CACTUS
                && support != Material.CAMPFIRE && support != Material.SOUL_CAMPFIRE;
    }

    private boolean bridgeSpaceClear(Location floor) {
        for (int height = 1; height <= 2; height++) {
            Block space = floor.clone().add(0, height, 0).getBlock();
            if (!space.isPassable() || space.isLiquid() || space.getType() == Material.FIRE || space.getType() == Material.SOUL_FIRE) return false;
        }
        return true;
    }

    private static Location bridgeFeet(Location floor) { return floor.clone().add(0.5, 1, 0.5); }

    static final class BridgeLane {
        final List<Location> floors;
        int completedWidth;
        boolean active;
        BridgeBuilder builder;

        BridgeLane(List<Location> floors) { this.floors = floors; }
        int nextWidth() { return completedWidth == 0 ? 1 : 3; }
        void release(boolean arrived, int width) {
            active = false;
            builder = null;
            if (arrived) completedWidth = Math.max(completedWidth, width);
        }
        boolean overlaps(BridgeLane other) {
            // The three balconies in each row share a central corridor, including their widening strips.
            return Math.abs(floors.getFirst().getBlockZ() - other.floors.getFirst().getBlockZ()) <= 2;
        }
    }

    final class BridgeBuilder extends Minion {
        private final BridgeLane lane;
        private final int width;
        private int floorIndex, lastProgressSecond;
        private boolean released;

        BridgeBuilder(Equip team, BridgeLane lane, int width) {
            super(TheTowers.this, team, null);
            this.lane = lane;
            this.width = width;
            lastProgressSecond = segonsTranscorreguts();
        }

        @Override protected Mob spawnBody(Location at) {
            Snowman snowman = at.getWorld().spawn(at, Snowman.class);
            snowman.setCustomName(team().getChatColor() + "Bridge builder");
            snowman.setCustomNameVisible(true);
            return snowman;
        }

        @Override protected void installGoals(Mob mob) { Bukkit.getMobGoals().removeAllGoals(mob); }
        @Override public boolean mayTarget(LivingEntity candidate) { return false; }
        @Override public void onHit(EntityDamageByEntityEvent event, LivingEntity victim) { event.setCancelled(true); }

        void buildAndWalk() {
            Mob body = mob();
            int now = segonsTranscorreguts();
            if (!JocEnMarxa() || body == null || now - bornAtSecond() >= builderLifetimeSeconds
                    || now - lastProgressSecond >= 10 || body.getWorld() != world
                    || body.getLocation().getY() < lane.floors.get(floorIndex).getY() - 1) {
                discharge(this);
                return;
            }
            Location floor = lane.floors.get(floorIndex), feet = bridgeFeet(floor);
            if (!prepareBridgeFloor(floor, width)) { body.getPathfinder().stopPathfinding(); return; }
            Location position = body.getLocation();
            boolean arrived = Math.abs(position.getX() - feet.getX()) <= 0.45
                    && Math.abs(position.getZ() - feet.getZ()) <= 0.45 && Math.abs(position.getY() - feet.getY()) <= 0.65;
            if (arrived) {
                lastProgressSecond = now;
                if (floorIndex == lane.floors.size() - 1) {
                    lane.release(true, width);
                    released = true;
                    discharge(this);
                    return;
                }
                floor = lane.floors.get(++floorIndex);
                feet = bridgeFeet(floor);
                if (!prepareBridgeFloor(floor, width)) { body.getPathfinder().stopPathfinding(); return; }
            }
            // Native movement preserves collisions, stairs, enemy knockback and falling.
            var path = body.getPathfinder().findPath(feet, 0);
            if (path != null && path.canReachFinalPoint()) body.getPathfinder().moveTo(path, 0.9);
            else body.getPathfinder().stopPathfinding();
        }

        @Override public void onMinionDeath(EntityDeathEvent event, Player killer) { release(); }
        private void release() {
            if (!released) { lane.release(false, width); released = true; }
        }
        @Override public void remove() { release(); super.remove(); }
    }

    @Override protected void onProjectileLaunch(ProjectileLaunchEvent event, Projectile projectile) {
        if (attackingMinion(projectile) instanceof BridgeBuilder) event.setCancelled(true);
        super.onProjectileLaunch(event, projectile);
    }

    @Override protected void onEntityDamage(EntityDamageEvent event, Entity entity) {
        super.onEntityDamage(event, entity);
        if (minionOf(entity) instanceof BridgeBuilder && (event.getCause() == EntityDamageEvent.DamageCause.MELTING
                || event.getCause() == EntityDamageEvent.DamageCause.DROWNING)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBuilderSnowTrail(EntityBlockFormEvent event) {
        if (event.getEntity().getWorld() == world && minionOf(event.getEntity()) instanceof BridgeBuilder) event.setCancelled(true);
    }

    @Override public void clearExternals() {
        java.util.Arrays.fill(builderCredits, 0);
        super.clearExternals();
        for (List<BridgeLane> lanes : bridgeLanes) for (BridgeLane lane : lanes) lane.active = false;
    }

    boolean isProtected(Block block) {
        return protectedAreas.stream().anyMatch(area -> area.contains(block.getLocation().toVector()))
                || switch (block.getType()) {
                    case CHEST, TRAPPED_CHEST, ENCHANTING_TABLE, BEDROCK -> true;
                    default -> false;
                };
    }

    private boolean canBuild(Player player, Block block) {
        return JocEnMarxa() && getPlayers().contains(player) && !isSpectator(player)
                && player.getGameMode() == GameMode.SURVIVAL && arenaBounds.contains(block.getLocation().toVector()) && !isProtected(block);
    }

    @Override protected void onBlockBreak(BlockBreakEvent event, Block block) {
        super.onBlockBreak(event, block);
        if (!getEditMode() && !canBuild(event.getPlayer(), block)) event.setCancelled(true);
    }

    @Override protected void onBlockPlace(BlockPlaceEvent event, Block block) {
        super.onBlockPlace(event, block);
        if (!getEditMode() && !canBuild(event.getPlayer(), block)) event.setCancelled(true);
    }

    @Override protected void onEntityExplode(EntityExplodeEvent event, Entity entity) {
        super.onEntityExplode(event, entity);
        if (!JocEnMarxa()) event.setCancelled(true);
        else event.blockList().removeIf(block -> isProtected(block) || !arenaBounds.contains(block.getLocation().toVector()));
    }

    @Override protected void onBlockFromTo(BlockFromToEvent event, Block block) {
        super.onBlockFromTo(event, block);
        if (!JocEnMarxa() || isProtected(event.getToBlock()) || !arenaBounds.contains(event.getToBlock().getLocation().toVector())) event.setCancelled(true);
    }

    @Override protected void onPlayerBucketEmpty(PlayerBucketEmptyEvent event, Player player) {
        super.onPlayerBucketEmpty(event, player);
        if (!canBuild(player, event.getBlock())) event.setCancelled(true);
    }

    @Override protected void onEntityChangeBlock(EntityChangeBlockEvent event, Entity entity) {
        super.onEntityChangeBlock(event, entity);
        if (!JocEnMarxa() || isProtected(event.getBlock())) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (event.getBlock().getWorld() != world || getEditMode()) return;
        if (!JocEnMarxa() || isProtected(event.getBlock().getRelative(event.getDirection()))
                || event.getBlocks().stream().anyMatch(block -> isProtected(block) || isProtected(block.getRelative(event.getDirection())))) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (event.getBlock().getWorld() != world || getEditMode()) return;
        if (!JocEnMarxa() || event.getBlocks().stream().anyMatch(block -> isProtected(block)
                || isProtected(block.getRelative(event.getDirection())) || isProtected(block.getRelative(event.getDirection().getOppositeFace())))) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockExplosion(BlockExplodeEvent event) {
        if (event.getBlock().getWorld() != world || getEditMode()) return;
        if (!JocEnMarxa()) event.setCancelled(true);
        else event.blockList().removeIf(block -> isProtected(block) || !arenaBounds.contains(block.getLocation().toVector()));
    }

    @Override public void destroyEventBus() {
        HandlerList.unregisterAll(this);
        super.destroyEventBus();
    }

    @Override protected ArrayList<String> getGameInfo(Player player) {
        ArrayList<String> info = new ArrayList<>(List.of(Messages.legacy(player, MessageKey.TOWERS_INFO_GOAL, MessageArgument.number("score", getFinishScore())),
                Messages.legacy(player, MessageKey.TOWERS_INFO_SUPPLIES), Messages.legacy(player, MessageKey.TOWERS_INFO_RULES)));
        if (!bridgeLanes.isEmpty()) info.add(Messages.legacy(player, MessageKey.TOWERS_INFO_BUILDERS));
        return info;
    }

    @Override protected void updateScoreBoard(Player player) {
        if (!JocIniciat) { super.updateScoreBoard(player); return; }
        ScoreBoardUpdater.setTranslatedScoreBoard(player, getGameName(), List.of(
                TeamNames.marker(DyeColor.RED, "red"), TeamNames.marker(DyeColor.BLUE, "blue"),
                Messages.scoreboardMarker(MessageKey.TOWERS_SCORE_TARGET)),
                List.of(getSpecificTeams().get(0).getScore(), getSpecificTeams().get(1).getScore(), getFinishScore()));
    }

    @Override public double getGameProgressETA() {
        return Math.min(1, getOrderedWinnerList().getFirst().getScore() / (double) getFinishScore());
    }
}
