package algorithm.crossover;

import util.RandomUtils;
import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;
import algorithm.util.ChromosomeUtils;

public class UniformCrossOver implements CrossOverAlgorithm {
	private double pc;
	private double probability;

	public UniformCrossOver(double pc, double probability) {
		this.pc = pc;
		this.probability = probability;
	}

	@Override
	public Pair<Chromosome, Chromosome> crossOver(
			Pair<Chromosome, Chromosome> pair) {
		Chromosome firstChild = pair.first.cloneChromosome();
		Chromosome secondChild = pair.second.cloneChromosome();
		
		if (!RandomUtils.should(pc))
			return new Pair<>(firstChild, secondChild);

		for (int i = 0; i < firstChild.geneAmount(); i++) {
			if (RandomUtils.should(probability))
				ChromosomeUtils.crossOver(firstChild, secondChild, i);
		}

		return new Pair<>(firstChild, secondChild);
	}
}
