package model;

import model.stats.Stats;

public class Multipliers {
	public double attackMultiplier;
	public double defenseMultiplier;
	public Stats statsMultiplier;
	
	public Multipliers(double attackMultiplier, double defenseMultiplier, Stats statsMultiplier) {
		this.attackMultiplier = attackMultiplier;
		this.defenseMultiplier = defenseMultiplier;
		this.statsMultiplier = statsMultiplier;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MULTIPLIERS - ");
		builder.append("attackMultiplier: " + attackMultiplier);
		builder.append(", defenseMultiplier: " + defenseMultiplier);
		builder.append(", statsMultiplier: " + statsMultiplier.toString());
		return builder.toString();
	}
}
