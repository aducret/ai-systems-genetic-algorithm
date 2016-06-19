package algorithm.replace;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.selector.Selector;

public class ReplaceMethod3 implements ReplaceMethod {

	private Selector selector;
	
	public ReplaceMethod3(Selector selector) {
		this.selector = selector;
	}
	
	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs) {
		int N = currentGeneration.size();
		int k = childs.size();
		List<Chromosome> newGeneration = selector.select(currentGeneration, N-k);
		List<Chromosome> currentGenerationAndChilds = new ArrayList<Chromosome>();
		currentGenerationAndChilds.addAll(currentGeneration);
		currentGenerationAndChilds.addAll(childs);
		newGeneration.addAll(selector.select(currentGenerationAndChilds, k));
		return newGeneration;
	}
}
