package algorithm.crossover;

import model.chromosome.Chromosome;
import structures.Pair;

public interface CrossOverAlgorithm {
	Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair);
}
