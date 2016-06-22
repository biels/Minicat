package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.ColorConverter;
import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.Com;
import com.biel.lobby.utilities.events.skills.types.ItemAttatchedModeSkill;
import com.biel.lobby.utilities.events.skills.types.ItemAttatchedStackModeSkill;
import com.biel.lobby.utilities.events.statuseffects.LifeDrainStatusEffect;

public class MagicArcherSkill extends ItemAttatchedStackModeSkill {
	public boolean flyingEffect = false;
	public MagicArcherSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}
	public enum Mode {ACCELERATOR, ENCHANCER, REPULSOR, DRAINER};
	@Override
	public void registerModes() {
		registerMode(new StackSkillMode(Mode.ACCELERATOR.ordinal(), "Accelerador", "Et dona l'efecte de velocitat", ChatColor.DARK_BLUE, 3));
		registerMode(new StackSkillMode(Mode.ENCHANCER.ordinal(), "Potenciador", "Inflingeix mal addicional", ChatColor.DARK_RED, 3));
		registerMode(new StackSkillMode(Mode.REPULSOR.ordinal(), "Repulsor", "Empenta els enemics", ChatColor.YELLOW, 4));
		//registerMode(new StackSkillMode(Mode.DRAINER.ordinal(), "Drenador", "Aplica l'efecte de wither a l'enemic i drena vida", ChatColor.BLACK, 5));
	}

	@Override
	public boolean matchesItem(ItemStack i) {
		// TODO Auto-generated method stub
		return i != null && i.getType() == Material.BOW;
	}

	@Override
	public double getCDSeconds() { // UNUSED
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Arquer màgic";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Aquesta habilitat s'aplica als arcs. Utilitza shift + m_wheel per canviar entre modes amb l'arc a la ma. Els modes: " + getModeList();
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.FIREWORK;
	}
	protected Color getColor(ChatColor c){
		return ColorConverter.hexToColor(ColorConverter.chatToHex(c));
	}
	//Class overriding, important to keep uniquely identified to the SkillTrayEffect
	@Override
	protected Class<? extends ItemAttatchedModeSkillTrayEffect> getTrayEffectClass() {
		// TODO Auto-generated method stub
		return MagicArcherTrayEffect.class;
	}
	public class MagicArcherTrayEffect extends ItemAttatchedStackModeSkillTrayEffect{

		public MagicArcherTrayEffect(Player ply) {
			super(ply);
			// TODO Auto-generated constructor stub
			setMaxValue(getSelectedMode().getMaxStacks());
			setValue(0);
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
				boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager == getPlayer() && ranged && getGame().areEnemies(damager, damaged)){
				int increment = getValue() == 0 ? 2 : 2;
				setValue(getValue() + increment);
				if(flyingEffect){
					StackSkillMode m = getSelectedMode();
					if(m.getId() == Mode.ACCELERATOR.ordinal()){
						damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60, 1));
						//Effect
						Builder b = FireworkEffect.builder();
						b.withColor(getColor(getSelectedMode().getChatColor()));
						b.with(FireworkEffect.Type.BALL_LARGE);
						FireworkEffect eff = b.build();
						//CustomEntityFirework.spawn(damaged.getEyeLocation(), eff, getAllPlayersArray());
						
					}
					if(m.getId() == Mode.ENCHANCER.ordinal()){
						Consumer<? super Player> action = (p -> {
								p.damage(10, damager);
								//Effect
								Builder b = FireworkEffect.builder();
								b.withColor(getColor(getSelectedMode().getChatColor()));
								b.with(FireworkEffect.Type.BALL);
								FireworkEffect eff = b.build();
								//if(p != damaged)CustomEntityFirework.spawn(p.getEyeLocation(), eff, getAllPlayersArray());
							}); //TODO Add kill based modifier
						Predicate<? super Player> predicate = p -> getGame().areEnemies(damager, p);
						ArrayList<Player> nearbyPlayers = GUtils.getNearbyPlayers(damaged.getLocation(), 12);
						nearbyPlayers.stream().filter(predicate).forEach(action);
						//Effect
						Builder b = FireworkEffect.builder();
						b.withColor(getColor(getSelectedMode().getChatColor()));
						b.with(FireworkEffect.Type.BALL_LARGE);
						FireworkEffect eff = b.build();
						//CustomEntityFirework.spawn(damaged.getEyeLocation(), eff, getAllPlayersArray());
						
					}
					if(m.getId() == Mode.DRAINER.ordinal()){
						Consumer<? super Player> action = (p -> {
							//wither
								LifeDrainStatusEffect wEff = new LifeDrainStatusEffect(p);
								wEff.setRemainingTicks(20 * 20);
								wEff.setOwnerPlayer(getPlayer());
								getPlayerInfo(p).addStatusEffect(wEff);
								//Effect
								Builder b = FireworkEffect.builder();
								b.withColor(getColor(getSelectedMode().getChatColor()));
								b.with(FireworkEffect.Type.BALL);
								FireworkEffect eff = b.build();
								//if(p != damaged)CustomEntityFirework.spawn(p.getEyeLocation(), eff, getAllPlayersArray());
							}); //TODO Add kill based modifier
						Predicate<? super Player> predicate = p -> getGame().areEnemies(damager, p);
						ArrayList<Player> nearbyPlayers = GUtils.getNearbyPlayers(damaged.getLocation(), 16);
						nearbyPlayers.stream().filter(predicate).forEach(action);
						//Effect
						Builder b = FireworkEffect.builder();
						b.withColor(getColor(getSelectedMode().getChatColor()));
						b.with(FireworkEffect.Type.BALL_LARGE);
						FireworkEffect eff = b.build();
//						CustomEntityFirework.spawn(damaged.getEyeLocation(), eff, getAllPlayersArray());
						
					}
					flyingEffect = false;
				}
			}
		}
		//Shoot bow
		@Override
		protected void onEntityShootBow(EntityShootBowEvent evt, Entity e, ItemStack bow, Projectile proj,
				float power) {
			// TODO Auto-generated method stub
			super.onEntityShootBow(evt, e, bow, proj, power);
			System.out.println("Shootbow: " + ((CraftPlayer) e).getName() + ", " + getPlayer().getName());
			if(((CraftPlayer) e).getName().equalsIgnoreCase(getPlayer().getName())){
				if(isMaxed()){
					//Shoot with effect
					Builder b = FireworkEffect.builder();
					b.withColor(getColor(getSelectedMode().getChatColor()));
					b.with(FireworkEffect.Type.BURST);
					FireworkEffect eff = b.build();
					//CustomEntityFirework.spawn(proj.getLocation(), eff, getAllPlayersArray());
					
					setValue(0);
					flyingEffect = true;
				}else{
					setValue(getValue() - 1);
				}
			}
		}
		@Override
		protected void onPlayerShootBow(EntityShootBowEvent evt, Player p, ItemStack bow, Projectile proj,
				float power) {
			super.onPlayerShootBow(evt, p, bow, proj, power);
			
		}
		private Player[] getAllPlayersArray() {
			return getGame().getPlayers().toArray(new Player[getGame().getPlayers().size()]);
		}
		
		@Override
		protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
			// TODO Auto-generated method stub
			super.onProjectileHit(evt, proj);
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					if(flyingEffect){
						StackSkillMode m = getSelectedMode();
						Location focus = proj.getLocation();
						
						if(m.getId() == Mode.REPULSOR.ordinal()){
							Consumer<? super Player> action = (p -> {
								//wither
								Vector v = GUtils.CrearVector(focus, p.getEyeLocation()).normalize();
								v.setY(0.1 + v.getY() / 3);
								v.normalize().multiply(2.8);
								p.setVelocity(v);
								//Effect
								Builder b = FireworkEffect.builder();
								b.withColor(getColor(getSelectedMode().getChatColor()));
								b.with(FireworkEffect.Type.BALL);
								FireworkEffect eff = b.build();
								//CustomEntityFirework.spawn(p.getEyeLocation().add(0, -1, 0).add(v), eff, getAllPlayersArray());
							}); //TODO Add kill based modifier
							Predicate<? super Player> predicate = p -> getGame().areEnemies(getPlayer(), p);
							ArrayList<Player> nearbyPlayers = GUtils.getNearbyPlayers(focus, 16);
							nearbyPlayers.stream().filter(predicate).forEach(action);
							//Effect
							Builder b = FireworkEffect.builder();
							b.withColor(getColor(getSelectedMode().getChatColor()));
							b.with(FireworkEffect.Type.BALL_LARGE);
							FireworkEffect eff = b.build();
							//CustomEntityFirework.spawn(focus, eff, getAllPlayersArray());
							
						}
						flyingEffect = false;
					}
				}
			};
			Bukkit.getScheduler().runTaskLater(Com.getPlugin(), runnable, 2);
			
		}
	}
	
}
