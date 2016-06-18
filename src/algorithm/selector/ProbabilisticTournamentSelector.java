package algorithm.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;
import algorithm.util.RandomUtils;

public class ProbabilisticTournamentSelector implements Selector {
	private double probability; // Usually 0.75

	public ProbabilisticTournamentSelector(double probability) {
		this.probability = probability;
	}

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selected = new ArrayList<>(k);

		for (int i = 0; i < k; i++) {
			List<Chromosome> randomChromosomes = new RandomSelector().select(chromosomes, 2);
			if (RandomUtils.should(probability)) {
				selected.add(Collections.max(randomChromosomes, new ChromosomeComparator(true)));
			} else {
				selected.add(Collections.min(randomChromosomes, new ChromosomeComparator(true)));
			}
		}

		return selected;
	}
}
