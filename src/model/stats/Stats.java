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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(agility);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(expertise);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(health);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(resistance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(strength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (Double.doubleToLongBits(agility) != Double.doubleToLongBits(other.agility))
			return false;
		if (Double.doubleToLongBits(expertise) != Double.doubleToLongBits(other.expertise))
			return false;
		if (Double.doubleToLongBits(health) != Double.doubleToLongBits(other.health))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (Double.doubleToLongBits(resistance) != Double.doubleToLongBits(other.resistance))
			return false;
		if (Double.doubleToLongBits(strength) != Double.doubleToLongBits(other.strength))
			return false;
		return true;
	}
	
	
}
