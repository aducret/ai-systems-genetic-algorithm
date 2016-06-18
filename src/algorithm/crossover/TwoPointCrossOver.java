package algorithm.crossover;

import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;
import algorithm.util.ChromosomeUtils;
import util.RandomUtils;

public class TwoPointCrossOver implements CrossOverAlgorithm {
	
	private double pc;
	
	public TwoPointCrossOver(double pc) {
		this.pc = pc;
	}

	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		Pair<Chromosome, Chromosome> childs = new Pair<>(pair.first.cloneChromosome(), pair.second.cloneChromosome());
		if (!RandomUtils.should(pc)) return childs;
		
		int geneAmount = pair.first.geneAmount();
		int a = RandomUtils.randomBetween(0, geneAmount - 1);
		int b = RandomUtils.randomBetween(0, geneAmount - 1);;
		int r1 = Math.min(a, b);
		int r2 = Math.max(a, b);
		
		ChromosomeUtils.crossOver(childs.first, childs.second, r1, r2);
		return childs;
	}
}
