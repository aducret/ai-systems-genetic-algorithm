package algorithm.selector;

import java.util.List;

import model.chromosome.Chromosome;

public interface Selector {
	
	List<Chromosome> select(List<Chromosome> chromosomes, int k);
}
