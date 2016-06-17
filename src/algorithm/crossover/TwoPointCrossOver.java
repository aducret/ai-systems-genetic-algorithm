package algorithm.crossover;

import algorithm.model.Chromosome;
import algorithm.util.ChromosomeUtils;
import model.Pair;
import util.RandomUtils;

public class TwoPointCrossOver implements CrossOverAlgorithm {

	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		int geneAmount = pair.first.geneAmount();
		int a = RandomUtils.randomBetween(0, geneAmount - 1);
		int b = a;
		while (b == a) {
			b = RandomUtils.randomBetween(0, geneAmount - 1);
		}
		int r1 = Math.min(a, b);
		int r2 = Math.max(a, b);
		
		Pair<Chromosome, Chromosome> childs = new Pair<>(pair.first.clone(), pair.second.clone());
		ChromosomeUtils.exchange(childs.first, childs.second, r1, r2);
		return childs;
	}
}
