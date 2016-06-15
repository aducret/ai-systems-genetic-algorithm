package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithm.model.Chromosome;
import algorithm.model.Gene;

public interface GeneticAlgorithmProblem {
	
	List<Chromosome> initialPopulation();
	
	Map<Integer, ArrayList<Gene>> geneMap();
	
	int getK();
}
