package algorithm.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.chromosome.Chromosome;
import model.chromosome.ChromosomeComparator;

public class EliteSelector implements Selector {

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> clonedChromosomes = new ArrayList<>(chromosomes);
		Collections.sort(clonedChromosomes, new ChromosomeComparator(false));
		List<Chromosome> ans = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			ans.add(clonedChromosomes.get(i % chromosomes.size()));
		}
		return ans;
	}

}
