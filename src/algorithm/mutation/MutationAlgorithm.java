package algorithm.mutation;

import java.util.ArrayList;
import java.util.Map;

import algorithm.model.Chromosome;
import algorithm.model.Gene;

public interface MutationAlgorithm {
	
	void mutate(Chromosome chromosome, Map<Integer, ArrayList<Gene>> geneMap);
}
