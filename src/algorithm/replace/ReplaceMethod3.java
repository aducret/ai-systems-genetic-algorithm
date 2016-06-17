package algorithm.replace;

import java.util.ArrayList;
import java.util.List;

import algorithm.model.Chromosome;
import algorithm.selector.Selector;

public class ReplaceMethod3 implements ReplaceAlgorithm {

	private Selector selector;
	
	public ReplaceMethod3(Selector selector) {
		this.selector = selector;
	}
	
	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> selectedParents, List<Chromosome> childs) {
		if (selectedParents.size() != childs.size()) {
			System.err.println("Selected parents and childs sizes don't match!");
			return null;
		}
		
		int N = currentGeneration.size();
		int k = selectedParents.size();
		List<Chromosome> newGeneration = selector.select(currentGeneration, N-k);
		List<Chromosome> currentGenerationAndChilds = new ArrayList<Chromosome>();
		currentGenerationAndChilds.addAll(currentGeneration);
		currentGenerationAndChilds.addAll(childs);
		newGeneration.addAll(selector.select(currentGenerationAndChilds, k));
		
		if (newGeneration.size() != N) {
			System.err.println("The new generation has to have the same size of the old generation!");
			return null;
		}
		
		return newGeneration;
	}
}
