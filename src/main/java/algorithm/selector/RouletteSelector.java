package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import util.ChromosomeUtils;
import util.RandomUtils;

public class RouletteSelector implements Selector {

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selected = new ArrayList<>();
		double[] relativeFitnesses = ChromosomeUtils.relativeFitnesses(chromosomes);
		double[] cumulativeRelativeFitnesses = ChromosomeUtils.cumulativeProbabilities(relativeFitnesses);
		for (int i = 0; i < k; i++) {
			double number = RandomUtils.random();
			int winnerIndex = ChromosomeUtils.getWinner(cumulativeRelativeFitnesses, number);
			selected.add(chromosomes.get(winnerIndex));
		}
 		return selected;
	}
}
