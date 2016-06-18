package algorithm.crossover;

import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;

public interface CrossOverAlgorithm {
	Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair);
}
