package model.gene;

import algorithm.gene.Gene;
import model.stats.Stats;

public class CharacterGene implements Gene {
	private Stats deltaStats;
	
	public CharacterGene(Stats deltaStats) {
		this.deltaStats = deltaStats;
	}
	
	public Stats getDeltaStats() {
		return deltaStats;
	}
}
