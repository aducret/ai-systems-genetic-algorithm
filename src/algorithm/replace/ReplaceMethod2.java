package algorithm.replace;

import java.util.List;

import algorithm.model.Chromosome;
import algorithm.selector.Selector;

public class ReplaceMethod2 implements ReplaceAlgorithm {
	
	private Selector selector;
	
	public ReplaceMethod2(Selector selector) {
		this.selector = selector;
	}
	
	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> selectedParents, List<Chromosome> childs) {
		if (currentGeneration.size() != selectedParents.size()) {
			System.err.println("Should have selected N parents!");
			return null;
		}
		
		int N = currentGeneration.size();
		int k = selectedParents.size();
		List<Chromosome> newGeneration = selector.select(currentGeneration, N-k);
		newGeneration.addAll(childs);
		
		if (newGeneration.size() != N) {
			System.err.println("The new generation has to have the same size of the old generation!");
			return null;
		}
		
		return newGeneration;
	}
	
}
