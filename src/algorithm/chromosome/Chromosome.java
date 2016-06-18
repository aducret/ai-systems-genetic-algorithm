package algorithm.chromosome;

import java.util.List;

import algorithm.gene.Gene;

public interface Chromosome {
	
	double fitness();
	
	int geneAmount();
	
	Chromosome cloneChromosome();
		
	List<Gene> genes();
	
	Gene geneAt(int index);
	
	void setGene(int index, Gene gene);
}
