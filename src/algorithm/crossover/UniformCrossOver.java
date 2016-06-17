package algorithm.crossover;

import model.Pair;
import util.RandomUtils;
import algorithm.model.Chromosome;
import algorithm.util.ChromosomeUtils;

public class UniformCrossOver implements CrossOverAlgorithm {
	private double probability;

	public UniformCrossOver(double probability) {
		this.probability = probability;
	}

	@Override
	public Pair<Chromosome, Chromosome> crossOver(
			Pair<Chromosome, Chromosome> pair) {
		Chromosome firstChild = pair.first.clone();
		Chromosome secondChild = pair.second.clone();

		for (int i = 0; i < firstChild.geneAmount(); i++) {
			if (RandomUtils.should(probability))
				ChromosomeUtils.exchange(firstChild, secondChild, i);
		}

		return new Pair<>(firstChild, secondChild);
	}
}
