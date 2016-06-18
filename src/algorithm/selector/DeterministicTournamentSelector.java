package algorithm.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.ChromosomeComparator;
import algorithm.model.Chromosome;

public class DeterministicTournamentSelector implements Selector {
	
	private int m;
	
	public DeterministicTournamentSelector(int m) {
		this.m = m;
	}
	
	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		RandomSelector randomSelector = new RandomSelector();
		List<Chromosome> selectedChromosome = new ArrayList<Chromosome>(k);
		while (selectedChromosome.size() < k) {
			List<Chromosome> randomChromosomes = randomSelector.select(chromosomes, m);
			Collections.max(randomChromosomes, new ChromosomeComparator(true));
			selectedChromosome.add(randomChromosomes.get(0));
		}
		return selectedChromosome;
	}

}
