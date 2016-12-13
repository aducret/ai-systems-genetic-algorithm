package model.chromosome;

import java.util.List;

import model.gene.Gene;

public interface Chromosome {

	double fitness();

	int geneAmount();

	Chromosome cloneChromosome();

	List<Gene> genes();

	Gene geneAt(int index);

	void setGene(int index, Gene gene);

}
