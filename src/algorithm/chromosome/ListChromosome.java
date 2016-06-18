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
}
