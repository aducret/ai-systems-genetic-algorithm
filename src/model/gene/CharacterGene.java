package model.gene;

import algorithm.gene.Gene;
import model.stats.Stats;

public class CharacterGene implements Gene {
	private Stats deltaStats;
	private int id;
	
	public CharacterGene(int id, Stats deltaStats) {
		this.id = id;
		this.deltaStats = deltaStats;
	}
	
	public Stats getDeltaStats() {
		return deltaStats;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deltaStats == null) ? 0 : deltaStats.hashCode());
		result = prime * result + id;
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
		CharacterGene other = (CharacterGene) obj;
		if (deltaStats == null) {
			if (other.deltaStats != null)
				return false;
		} else if (!deltaStats.equals(other.deltaStats))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
