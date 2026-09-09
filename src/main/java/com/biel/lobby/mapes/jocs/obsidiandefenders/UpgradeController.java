package com.biel.lobby.mapes.jocs.obsidiandefenders;

import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.TeamUpgrades;
import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.Watchtowers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import com.biel.lobby.utilities.PaperMessages;

final class UpgradeController {
    private final World world;
    private final TeamUpgrades upgrades;
    private final LauncherController launchers;
    private final Predicate<Player> participant;
    private final ToIntFunction<Player> teamOf;
    private final BiPredicate<Player, Integer> pay;
    private final Consumer<Player> refreshGold;
    private final Map<Block, Sign> originals = new HashMap<>();
    private final Map<Integer, String[]> displayed = new HashMap<>();
    private long tick;

    UpgradeController(World world, TeamUpgrades upgrades, LauncherController launchers,
            Predicate<Player> participant, ToIntFunction<Player> teamOf, BiPredicate<Player, Integer> pay,
            Consumer<Player> refreshGold) {
        this.world = world; this.upgrades = upgrades; this.launchers = launchers;
        this.participant = participant; this.teamOf = teamOf; this.pay = pay; this.refreshGold = refreshGold;
        updateSigns();
    }

    private Block button(int team) {
        var position = Watchtowers.purchaseButton(team);
        return world.getBlockAt(position.x(), position.y(), position.z());
    }

    boolean interact(Player player, Block clicked) {
        for (int team = 0; team < 2; team++) {
            if (!button(team).equals(clicked)) continue;
            if (participant.test(player) && !player.isDead()) {
                try {
                    buy(player, team);
                } catch (RuntimeException exception) {
                    org.bukkit.Bukkit.getLogger().log(java.util.logging.Level.WARNING, "Obsidian upgrade purchase failed", exception);
                    message(player, "No s'ha pogut activar la millora");
                }
            }
            return true;
        }
        return false;
    }

    private void buy(Player player, int team) {
        var next = upgrades.next(team);
        var result = upgrades.purchase(team, teamOf.applyAsInt(player), tick,
                () -> next == TeamUpgrades.Upgrade.LAUNCHERS
                        ? launchers.installAndPay(team, () -> pay.test(player, next.price))
                        : pay.test(player, next.price));
        switch (result) {
            case BOUGHT -> {
                updateSigns();
                refreshGold.accept(player);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                String explanation = switch (next) {
                    case LAUNCHERS -> "Llançadors activats! Les dues torres ja us poden impulsar cap a la jungla.";
                    case ARCHERS -> "Reforços ossis! Cada kill crea un arquer a la teva base que t'ajuda a la batalla.";
                    case ARMOR -> "Herència de guerra! Els nous arquers porten la teva armadura, sense encantaments.";
                };
                for (Player teammate : world.getPlayers()) if (teamOf.applyAsInt(teammate) == team)
                    teammate.sendMessage(ChatColor.GOLD + explanation);
            }
            case ENEMY -> message(player, "Aquesta millora és de l'equip enemic");
            case LOADING -> message(player, "La següent millora encara no està preparada");
            case COMPLETE -> message(player, "Totes les millores activades");
            case FAILED -> message(player, "Calen " + next.price + " d'or");
        }
    }

    static String[] lines(TeamUpgrades upgrades, int team, long tick) {
        int level = upgrades.level(team);
        if (upgrades.ready(team, tick)) {
            String[] title = switch (upgrades.next(team)) {
                case LAUNCHERS -> new String[]{"Llançadors", "de les torres"};
                case ARCHERS -> new String[]{"Reforços", "ossis"};
                case ARMOR -> new String[]{"Herència", "de guerra"};
            };
            return new String[]{title[0], title[1], "Millora " + level + "/3", upgrades.next(team).price + "g"};
        }
        String[] title = switch (level) {
            case 1 -> new String[]{"Llançadors", "activats"};
            case 2 -> new String[]{"Arquers", "activats"};
            default -> new String[]{"Armadures", "activades"};
        };
        return new String[]{title[0], title[1], "Millora " + level + "/3",
                ChatColor.GRAY + "\u25a0 ".repeat(upgrades.remainingSquares(team, tick)).stripTrailing()};
    }

    private void updateSigns() {
        for (int team = 0; team < 2; team++) {
            Block block = button(team).getRelative(BlockFace.DOWN);
            if (!(block.getState() instanceof Sign sign)) continue;
            String[] lines = lines(upgrades, team, tick);
            if (Arrays.equals(lines, displayed.get(team))) continue;
            originals.putIfAbsent(block, (Sign) block.getState());
            for (Side side : Side.values()) for (int line = 0; line < 4; line++)
                sign.getSide(side).line(line, PaperMessages.legacy((line == 2 ? ChatColor.GOLD : ChatColor.WHITE) + lines[line]));
            sign.setWaxed(true);
            sign.update(false, false);
            displayed.put(team, lines);
        }
    }

    void tick() {
        tick++;
        if (tick % 20 != 0) return;
        updateSigns();
        for (int team = 0; team < 2; team++) if (upgrades.ready(team, tick)) {
            Block sign = button(team).getRelative(BlockFace.DOWN);
            if (sign.getState() instanceof Sign) world.spawnParticle(Particle.HAPPY_VILLAGER,
                    sign.getLocation().add(0.5, 1.1, 0.5), 3, 0.25, 0.08, 0.25, 0);
        }
    }

    private static void message(Player player, String text) {
        PaperMessages.sendActionBar(player, ChatColor.GOLD + text, 30);
    }

    void close() {
        originals.values().forEach(sign -> sign.update(false, false));
        originals.clear();
        displayed.clear();
    }
}
