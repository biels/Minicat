package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        // These native events are not forwarded by the shared world event bus.
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private int positiveProperty(String name, int fallback) {
        int value = pMapaActual().ExisteixPropietat(name) ? pMapaActual().ObtenirPropietatInt(name) : fallback;
        if (value < 1) throw new IllegalArgumentException(name + " must be positive");
        return value;
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
        super.customJocIniciat();
        setBlockBreakPlace(true);
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
        if (JocEnMarxa() && killed != killer && areEnemies(killed, killer) && !isSpectator(killer)) killer.giveExpLevels(4);
    }

    @Override protected void onPlayerMove(PlayerMoveEvent event, Player player) {
        super.onPlayerMove(event, player);
        if (!event.isCancelled() && JocEnMarxa() && !player.isDead() && player.getWorld() == world
                && player.getGameMode() == GameMode.SURVIVAL && getPlayers().contains(player) && !isSpectator(player)
                && event.getTo().getY() < getMinimumHeight()) player.setHealth(0);
    }

    @Override public void ultraHeartbeat() {
        super.ultraHeartbeat();
        if (JocEnMarxa()) for (Player player : getPlayers()) checkScoring(player);
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
        if (!JocEnMarxa() || getHeartbeatCount() % generatorIntervalSeconds != 0) return;
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
        return new ArrayList<>(List.of(Messages.legacy(player, MessageKey.TOWERS_INFO_GOAL, MessageArgument.number("score", getFinishScore())),
                Messages.legacy(player, MessageKey.TOWERS_INFO_SUPPLIES), Messages.legacy(player, MessageKey.TOWERS_INFO_RULES)));
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
