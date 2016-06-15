package algorithm.crossover;

import model.Pair;
import algorithm.model.Chromosome;

public interface CrossOverAlgorithm {
	Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair);
}
