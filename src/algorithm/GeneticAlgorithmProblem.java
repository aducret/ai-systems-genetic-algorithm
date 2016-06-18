package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithm.chromosome.Chromosome;
import algorithm.gene.Gene;

public interface GeneticAlgorithmProblem {
	
	Map<Integer, ArrayList<Gene>> geneMap();
	
	Configuration configuration();
	
	Chromosome createChromosome(List<Gene> genes);
}
