package algorithm;

import model.chromosome.Chromosome;

public interface GeneticAlgorithmProblem {
	
	Configuration configuration();
	
	Chromosome createRandom();
}
