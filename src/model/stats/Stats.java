package model.stats;

import java.util.List;

public class Stats {
	public double strength;
	public double agility;
	public double expertise;
	public double resistance;
	public double health;
	public double height;
	
	public Stats(double strength, double agility, double expertise, double resistance, double health, double height) {
		this.strength = strength;
		this.agility = agility;
		this.expertise = expertise;
		this.resistance = resistance;
		this.health = health;
		this.height = height;
	}

	public Stats add(Stats stats) {
		this.strength += stats.strength;
		this.agility += stats.agility;
		this.expertise += stats.expertise;
		this.resistance += stats.resistance;
		this.health += stats.health;
		this.height += height;
		return this;
	}
	
	public static Stats sumUp(List<Stats> stats) {
		Stats first = stats.get(0);
		for (int i = 1; i < stats.size(); i++) {
			first.add(stats.get(i));
		}
		return first;
	}
}
