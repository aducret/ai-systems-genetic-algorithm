package algorithm.model;

import java.util.List;

public interface Chromosome {
	
	double fitness();
	
	int geneAmount();
	
	Chromosome clone();
		
	List<Gene> genes();
	
	Gene geneAt(int index);
	
	void setGene(int index, Gene gene);
}
