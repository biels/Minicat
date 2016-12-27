package com.biel.lobby.utilities.events.skills.types;

import org.apache.commons.lang.IllegalClassException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class ItemAttatchedStackModeSkill extends ItemAttatchedModeSkill {

	public ItemAttatchedStackModeSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Class<? extends ItemAttatchedModeSkillTrayEffect> getTrayEffectClass() {
		// TODO Auto-generated method stub
		return ItemAttatchedStackModeSkillTrayEffect.class;
	}
	public class StackSkillMode extends SkillMode{
		private int maxStacks;

		public StackSkillMode(int id, String name, String description, ChatColor chatColor, int maxStacks) {
			super(id, name, description, chatColor);
			this.maxStacks = maxStacks;
		}

		public StackSkillMode(int id, String name, String description, int maxStacks) {
			super(id, name, description);
			this.maxStacks = maxStacks;
		}

		public int getMaxStacks() {
			return maxStacks;
		}

		public void setMaxStacks(int maxStacks) {
			this.maxStacks = maxStacks;
		}

	}
	@Override @Deprecated
	public void registerMode(SkillMode mode) {
		throw new IllegalClassException(StackSkillMode.class, SkillMode.class);
	}
	public void registerMode(StackSkillMode mode) {
		// TODO Auto-generated method stub
		super.registerMode(mode);
	}
	@Override
	public void onModeSwitch(SkillMode newMode) {
		// TODO Auto-generated method stub
		super.onModeSwitch(newMode);
		StackSkillMode m = (StackSkillMode) newMode;
		getTrayEffect().setMaxValue(m.getMaxStacks());
	}
	@Override
	protected ItemAttatchedStackModeSkillTrayEffect getTrayEffect() {
		// TODO Auto-generated method stub
		return (ItemAttatchedStackModeSkillTrayEffect) super.getTrayEffect();
	}
	@Override
	public StackSkillMode getSelectedMode() {
		// TODO Auto-generated method stub
		return (StackSkillMode) super.getSelectedMode();
	}
	public class ItemAttatchedStackModeSkillTrayEffect extends ItemAttatchedModeSkillTrayEffect{

		public ItemAttatchedStackModeSkillTrayEffect(Player ply) {
			super(ply);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onMaxUp() {
			// TODO Auto-generated method stub
			getTrayEffect().setModalRemainingTicks(Integer.MAX_VALUE);
		}
		@Override
		public void onMaxLose() {
			// TODO Auto-generated method stub
			getTrayEffect().setModalRemainingTicks(0);
		}
	}
}
