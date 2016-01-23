package com.biel.lobby.utilities.events.statuseffects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AuraInfo {
	private String name;
	private double radius = 0;
	private int n = 5;
	private double speed = 30;
	private ItemStack itemStack = new ItemStack(Material.BARRIER, 1);
	
	
	public AuraInfo(String name, int n, ItemStack itemStack) {
		super();
		this.name = name;
		this.n = n;
		this.itemStack = itemStack;
	}

	public AuraInfo(String name, int n, double speed, ItemStack itemStack) {
		super();
		this.name = name;
		this.n = n;
		this.speed = speed;
		this.itemStack = itemStack;
	}

	public AuraInfo(String name, double radius, int n, double speed, ItemStack itemStack) {
		super();
		this.name = name;
		this.radius = radius;
		this.n = n;
		this.speed = speed;
		this.itemStack = itemStack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	/**
	 * @return The speed in degrees per second
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * @param speed Speed in degrees per second
	 */
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
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return name.equalsIgnoreCase(obj.toString());
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}
}
