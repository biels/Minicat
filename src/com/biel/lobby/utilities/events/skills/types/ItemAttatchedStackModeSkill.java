package com.biel.lobby.utilities.events.skills.types;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		int maxStacks;

		public StackSkillMode(String name, String description, ChatColor chatColor, int maxStacks) {
			super(name, description, chatColor);
			this.maxStacks = maxStacks;
		}

		public StackSkillMode(String name, String description, int maxStacks) {
			super(name, description);
			this.maxStacks = maxStacks;
		}

		public int getMaxStacks() {
			return maxStacks;
		}

		public void setMaxStacks(int maxStacks) {
			this.maxStacks = maxStacks;
		}

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
		public double getMaxValue() {
			// TODO Auto-generated method stub
			return getSelectedMode().getMaxStacks();
		}
	}
}
