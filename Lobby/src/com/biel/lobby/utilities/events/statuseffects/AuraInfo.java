package com.biel.lobby.utilities.events.statuseffects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AuraInfo {
	private double radius = 4;
	private int n = 5;
	private double speed = 0.1;
	private ItemStack itemStack = new ItemStack(Material.BARRIER, 1);
	
	public AuraInfo(double radius, int n, double speed, ItemStack itemStack) {
		super();
		this.radius = radius;
		this.n = n;
		this.speed = speed;
		this.itemStack = itemStack;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public ItemStack getItemStack() {
		return itemStack;
	}
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	public Material getMaterial() {
		return itemStack.getType();
	}
	
}
