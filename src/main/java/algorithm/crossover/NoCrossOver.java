package algorithm.crossover;

import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;

public class NoCrossOver implements CrossOverAlgorithm {

	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		return new Pair<>(pair.first.cloneChromosome(), pair.second.cloneChromosome());
	}
}
