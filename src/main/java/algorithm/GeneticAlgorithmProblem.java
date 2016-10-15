package algorithm;

import algorithm.chromosome.Chromosome;

public interface GeneticAlgorithmProblem {
	
	Configuration configuration();
	
	Chromosome createRandom();
}
