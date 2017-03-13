package algorithm.crossover;

import algorithm.chromosome.Chromosome;
import model.Pair;

public interface CrossOverAlgorithm {
	Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair);
}
