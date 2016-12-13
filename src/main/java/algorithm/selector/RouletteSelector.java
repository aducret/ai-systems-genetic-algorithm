package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import model.chromosome.Chromosome;
import util.ChromosomeUtils;
import util.RandomUtils;

public class RouletteSelector implements Selector {

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> chromosomesCloned = new ArrayList<>(chromosomes);
		List<Chromosome> selected = new ArrayList<>();
		double[] relativeFitnesses = ChromosomeUtils.relativeFitnesses(chromosomesCloned);
		double[] cumulativeRelativeFitnesses = ChromosomeUtils.cumulativeProbabilities(relativeFitnesses);
		for (int i = 0; i < k; i++) {
			double number = RandomUtils.random();
			int winnerIndex = ChromosomeUtils.getWinner(cumulativeRelativeFitnesses, number);
			selected.add(chromosomesCloned.get(winnerIndex));
		}
 		return selected;
	}
}
