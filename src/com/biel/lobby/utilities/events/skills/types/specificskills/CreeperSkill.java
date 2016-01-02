package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class CreeperSkill extends InherentSkill {

	public CreeperSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.SKULL_ITEM;
	}
	@Override
	public Byte getData() {
		// TODO Auto-generated method stub
		return 4; //Creeper skull
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Creeper";
	}
	public double getModifier(){
		return 2.25D;
	}
	public double getProtectionRatio(){
		return 0.12D;
	}
	@Override
	public String getDescription() {
		return "Explotes al morir, inflingint mal als enemics";
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		if(killed != getPlayer())return;
		if(getPlayer().getHealth() > 0)return;
		int dist = 10;
		Location c = killed.getEyeLocation();
		ArrayList<Player> nearbyPlayers = GUtils.getNearbyPlayers(c, dist);
		getWorld().playEffect(c, Effect.EXPLOSION_HUGE, 8);
		getWorld().playSound(c, Sound.EXPLODE, 0.8F, 1.0F);
		getWorld().playSound(c, Sound.CREEPER_HISS, 0.8F, 1.0F);
		//evt.getDrops().add(new ItemStack(Material.))
		Consumer<? super Player> action = p -> p.damage(2 + 32 / (c.distance(p.getEyeLocation())), killed);
		Predicate<? super Player> predicate = p -> getGame().areEnemies(killed, p);
		nearbyPlayers.stream().filter(predicate).forEach(action.andThen(p -> ((Entity) p).setFireTicks(40)));
		
		
	}
	public void playEffect(Player p) {
		for (int i = 0; i < 12; i++) {
			getWorld().playEffect(p.getEyeLocation().subtract(0, 0.9, 0),
					Effect.SNOWBALL_BREAK, Utils.NombreEntre(0, 7));
		}
	}

}
