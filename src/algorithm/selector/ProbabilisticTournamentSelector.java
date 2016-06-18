package algorithm.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.RandomUtils;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;

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
			Collections.max(randomChromosomes, new ChromosomeComparator(true));
			
			if (RandomUtils.should(probability)) {
				selected.add(randomChromosomes.get(0));
			} else {
				selected.add(randomChromosomes.get(1));
			}
		}

		return selected;
	}
}
