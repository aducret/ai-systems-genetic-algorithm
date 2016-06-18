package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.util.ChromosomeUtils;

public class RouletteSelector implements Selector {

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selected = new ArrayList<>();
		double[] relativeFitnesses = ChromosomeUtils.relativeFitnesses(chromosomes);
		double[] cumulativeRelativeFitnesses = ChromosomeUtils.cumulativeProbabilities(relativeFitnesses);
		for (int i = 0; i < k; i++) {
			double number = Math.random();
			int winnerIndex = ChromosomeUtils.getWinner(cumulativeRelativeFitnesses, number);
			selected.add(chromosomes.get(winnerIndex));
		}
 		return selected;
	}
}
