package algorithm.replace;

import java.util.List;

import model.chromosome.Chromosome;

public interface ReplaceMethod {
	
	/*
	 * returns the new generation
	 * */
	List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs);
}
