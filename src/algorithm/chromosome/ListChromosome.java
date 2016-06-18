package algorithm.chromosome;

import java.util.ArrayList;
import java.util.List;

import algorithm.gene.Gene;

public abstract class ListChromosome implements Chromosome {

	private List<Gene> genes;

	public ListChromosome(List<Gene> genes) {
		this.genes = new ArrayList<>(genes);
	}

	@Override
	public int geneAmount() {
		return genes.size();
	}

	@Override
	public List<Gene> genes() {
		return genes;
	}

	@Override
	public Gene geneAt(int index) {
		return genes.get(index);
	}

	@Override
	public void setGene(int index, Gene gene) {
		genes.set(index, gene);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
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
		ListChromosome other = (ListChromosome) obj;
		if (genes == null || other.genes == null)
			return false;
		for (int i = 0; i < genes.size(); i++) {
			if (!genes.get(i).equals(other.genes.get(i)))
				return false;
		}
		return true;
	}
}
