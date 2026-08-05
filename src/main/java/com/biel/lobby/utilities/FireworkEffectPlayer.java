package com.biel.lobby.utilities;

import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.Location;
import org.bukkit.World;
 
/**
 * FireworkEffectPlayer v1.0
 *
 * FireworkEffectPlayer provides a thread-safe and (reasonably) version independant way to instantly explode a FireworkEffect at a given location.
 * You are welcome to use, redistribute, modify and destroy your own copies of this source with the following conditions:
 *
 * 1. No warranty is given or implied.
 * 2. All damage is your own responsibility.
 * 3. You provide credit publicly to the original source should you release the plugin.
 *
 * @author codename_B
 */
public class FireworkEffectPlayer {
   
    /*
     * Example use:
     *
     * public class FireWorkPlugin implements Listener {
     *
     * FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
     *
     * @EventHandler
     * public void onPlayerLogin(PlayerLoginEvent event) {
     *   fplayer.playFirework(event.getPlayer().getWorld(), event.getPlayer.getLocation(), Util.getRandomFireworkEffect());
     * }
     *
     * }
     */
    /**
     * Play a pretty firework at the location with the FireworkEffect when called
     * @param world
     * @param loc
     * @param fe
     * @throws Exception
     */
    public void playFirework(World world, Location loc, FireworkEffect fe) {
        Firework fw = world.spawn(loc, Firework.class);
        FireworkMeta data = fw.getFireworkMeta();
        data.clearEffects();
        data.setPower(1);
        data.addEffect(fe);
        fw.setFireworkMeta(data);
        fw.detonate();
    }
 
}
