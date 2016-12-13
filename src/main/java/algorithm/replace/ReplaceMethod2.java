package algorithm.replace;

import java.util.List;

import algorithm.selector.Selector;
import model.chromosome.Chromosome;

public class ReplaceMethod2 implements ReplaceMethod {
	
	private Selector selector;
	
	public ReplaceMethod2(Selector selector) {
		this.selector = selector;
	}
	
	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs) {
		int N = currentGeneration.size();
		int k = childs.size();
		List<Chromosome> newGeneration = selector.select(currentGeneration, N-k);
		newGeneration.addAll(childs);
		return newGeneration;
	}
}
