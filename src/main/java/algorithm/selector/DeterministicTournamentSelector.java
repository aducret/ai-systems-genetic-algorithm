package algorithm.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;

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
			selectedChromosome.add(Collections.max(randomChromosomes, new ChromosomeComparator(true)));
		}
		return selectedChromosome;
	}

}
