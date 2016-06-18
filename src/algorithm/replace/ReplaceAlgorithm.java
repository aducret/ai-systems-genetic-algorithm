package algorithm.replace;

import java.util.List;

import algorithm.chromosome.Chromosome;

public interface ReplaceAlgorithm {
	
	/*
	 * returns the new generation
	 * */
	List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs);
}
