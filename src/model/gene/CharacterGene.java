package model.gene;

import algorithm.gene.Gene;
import model.stats.Stats;

public class CharacterGene implements Gene {
	private Stats deltaStats;
	private int id;
	
	public CharacterGene(int id, Stats deltaStats) {
		this.deltaStats = deltaStats;
	}
	
	public Stats getDeltaStats() {
		return deltaStats;
	}
	
	public int getId() {
		return id;
	}
}
