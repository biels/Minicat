package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;

public class ExternalCombustionEngine extends InherentSkill {
	private int stacks = 0;
	public ExternalCombustionEngine(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
		resetCooldown();
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 5;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.BLAZE_POWDER;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Combustió externa";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getDmgMultiplier() * 100 + ChatColor.WHITE;
		String modifier3 = ChatColor.GREEN + "" + getDmgMultiplierBlk() * 100 + ChatColor.WHITE;
		String modifier2 = ChatColor.GREEN + "" + 20 + ChatColor.WHITE;
		return "Inflingeix mal als enemics propers. L'efecte escala amb el temps i es reinicialitza al morir. L'efecte no s'aplica immediatament al reaparèixer.";
	}

	private int getStacks(){
		return stacks;
	}
	private void setStacks(int value){
		stacks = value;
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		PlayerInfo i = getPlayerInfo();
		
		if(!i.hasStatusEffect(ExternalCombustionStatusEffect.class)){	
			if(tryUseCD()){
				i.addStatusEffect(new ExternalCombustionStatusEffect(getPlayer()));
			}
		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		if(killed == getPlayer()){
			resetCooldown();
			getPlayerInfo().removeStatusEffect(ExternalCombustionStatusEffect.class);
		}
	}
	public double getDmgMultiplier() {
		return 1.2;
	}
	public double getDmgMultiplierBlk() {
		return 2.35;
	}

	public class ExternalCombustionStatusEffect extends StatusEffect{
		public ExternalCombustionStatusEffect(Player ply) {
			super(ply);
			setType(StatusEffectType.SKILL_TRAY);
			setRemainingTicks(-1);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Combustió externa";
		}
		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "Inflingeix mal als enemics propers";
		}
		@Override
		public void tick() {
			// TODO Auto-generated method stub
			super.tick();
			if(!isNthTick(20))return;
			setValue(Math.max(0, Math.pow(getTicksLived(), 0.6) / (25 * 3) - getCDSeconds()));
			Player ply = getPlayer();
			Location c = ply.getEyeLocation();
			ArrayList<Player> nearbyPlayers = GUtils.getNearbyPlayers(c, 4.5);
			if(isNthTick(15))getWorld().playEffect(ply.getLocation().add(0, -0.12, 0), Effect.MOBSPAWNER_FLAMES, 8);
			//evt.getDrops().add(new ItemStack(Material.))
			if(getValue() > 90 && getGame().getBlockBreakPlace() && isNthTick(20)){
				Block block = getPlayer().getLocation().getBlock();
				if(block.getType() != Material.AIR && GUtils.Possibilitat(getValue() - 80.0, 100.0));
			}
			if(getPlayer().getLocation().getBlock().getType() == Material.WATER){
				expire();
			}
			Vector savedVelocity = ply.getVelocity();
			Function<Player, Double> distFact = p -> 1 / c.distance(p.getEyeLocation());
			Consumer<? super Player> dmgAction = p -> {if(p.getHealth() > 2) {
				double dmg = (getValue() / 10);// * (1 + distFact.apply(p));
				p.damage(dmg, ply);
				p.setVelocity(savedVelocity);
			} setModalRemainingTicks(5);};
			Predicate<? super Player> enemyCheck = p -> getGame().areEnemies(ply, p);
			if(isNthTick(10))nearbyPlayers.stream().filter(enemyCheck).forEach(dmgAction
					//.andThen(p -> ((Entity) p).setFireTicks((getValue() > 50 ? 4 : 0)))
					// TODO: 1.13 Update effect name
					// .andThen(p -> getWorld().playEffect(((LivingEntity) p).getEyeLocation().subtract(Utils.NombreEntre(-0.1, 0.1), Utils.NombreEntre(-1.8, 0.1), Utils.NombreEntre(-0.1, 0.1)), Effect.LARGE_SMOKE, 0))
					.andThen(p -> getWorld().playSound(((LivingEntity) p).getEyeLocation(), Sound.BLOCK_FIRE_AMBIENT,(float) (0.5 * distFact.apply((Player) p)), 1.05F))
					);
		}
	}
}
