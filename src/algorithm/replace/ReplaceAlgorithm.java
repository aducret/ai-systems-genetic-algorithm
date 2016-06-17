package algorithm.replace;

import java.util.List;

import algorithm.model.Chromosome;

public interface ReplaceAlgorithm {
	
	/*
	 * returns the new generation
	 * */
	List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> selectedParents, List<Chromosome> childs);
}
