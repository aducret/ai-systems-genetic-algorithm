package algorithm.crossover;

import util.RandomUtils;
import model.Pair;
import algorithm.model.Chromosome;
import algorithm.util.ChromosomeUtils;

public class AnularCrossOver implements CrossOverAlgorithm {
	@Override
	public Pair<Chromosome, Chromosome> crossOver(
			Pair<Chromosome, Chromosome> pair) {
		Chromosome firstChild = pair.first.clone();
		Chromosome secondChild = pair.second.clone();
		
		int amountOfGenes = firstChild.geneAmount();
		int r1 = RandomUtils.randomBetween(0, amountOfGenes);
		int l = RandomUtils.randomBetween(0, amountOfGenes - 1);

		for (int i = r1; i <= (r1 + l); i++) {
			ChromosomeUtils
					.exchange(firstChild, secondChild, i % amountOfGenes);
		}

		return new Pair<>(firstChild, secondChild);
	}
}
