package algorithm.mutation;

import java.util.ArrayList;
import java.util.Map;

import algorithm.chromosome.Chromosome;
import algorithm.gene.Gene;

public interface MutationAlgorithm {
	
	void mutate(Chromosome chromosome, Map<Integer, ArrayList<Gene>> geneMap);
}
