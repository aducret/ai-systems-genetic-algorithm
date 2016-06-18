package algorithm.crossover;

import util.RandomUtils;
import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;
import algorithm.util.ChromosomeUtils;

public class AnularCrossOver implements CrossOverAlgorithm {
	private double pc;
	
	public AnularCrossOver(double pc) {
		this.pc = pc;
	}
	
	@Override
	public Pair<Chromosome, Chromosome> crossOver(
			Pair<Chromosome, Chromosome> pair) {
		Chromosome firstChild = pair.first.cloneChromosome();
		Chromosome secondChild = pair.second.cloneChromosome();
	
		if (!RandomUtils.should(pc))
			return new Pair<>(firstChild, secondChild);
		
		int amountOfGenes = firstChild.geneAmount();
		int r1 = RandomUtils.randomBetween(0, amountOfGenes);
		int l = RandomUtils.randomBetween(0, amountOfGenes - 1);

		for (int i = r1; i <= (r1 + l); i++) {
			ChromosomeUtils
					.crossOver(firstChild, secondChild, i % amountOfGenes);
		}

		return new Pair<>(firstChild, secondChild);
	}
}
