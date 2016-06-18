package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.util.ChromosomeUtils;

public class UniversalSelector implements Selector {
	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selected = new ArrayList<>();
		double[] relativeFitnesses = ChromosomeUtils.relativeFitnesses(chromosomes);
		double[] cumulativeRelativeFitnesses = ChromosomeUtils.cumulativeProbabilities(relativeFitnesses);
		
		for (int j = 1; j <= k; j++) {
			double number = (Math.random() + j - 1) / k;
			int winnerIndex = ChromosomeUtils.getWinner(cumulativeRelativeFitnesses, number);
			selected.add(chromosomes.get(winnerIndex));
		}
		
		return selected;
	}
}
