package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.ChromosomeComparator;
import algorithm.model.Chromosome;

public class EliteSelector implements Selector {

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> clonedChromosomes = new ArrayList<>(chromosomes);
		clonedChromosomes.sort(new ChromosomeComparator(false));
		List<Chromosome> ans = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			ans.add(clonedChromosomes.get(i));
		}
		return ans;
	}

}
