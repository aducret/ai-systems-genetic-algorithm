package algorithm.selector;

import java.util.List;

import algorithm.chromosome.Chromosome;

public interface Selector {
	
	List<Chromosome> select(List<Chromosome> chromosomes, int k);
}