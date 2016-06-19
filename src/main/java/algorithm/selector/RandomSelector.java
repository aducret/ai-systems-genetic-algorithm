package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.util.RandomUtils;

public class RandomSelector implements Selector {

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selectedChromosome = new ArrayList<Chromosome>(k);
		while (selectedChromosome.size() < k) {
			int index = RandomUtils.randomBetween(0, chromosomes.size()-1);
			Chromosome chromosome = chromosomes.get(index);
			selectedChromosome.add(chromosome);
		}
		return selectedChromosome;
	}

}
