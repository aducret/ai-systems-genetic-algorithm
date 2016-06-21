package algorithm.replace;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.selector.Selector;

public class GAPReplaceMethod implements ReplaceMethod {
	private double G;
	private Selector selector;
	
	public GAPReplaceMethod(double G, Selector selector) {
		this.G = G;
		this.selector = selector;
	}
	
	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs) {
		int N = currentGeneration.size();
		int amountOfParents = (int) Math.floor((1 - G) * N);
		int amountOfChilds = (int) Math.ceil(G * N);
		
		List<Chromosome> newGeneration = new ArrayList<Chromosome>(N); 
		newGeneration.addAll(selector.select(currentGeneration, amountOfParents));
		newGeneration.addAll(selector.select(childs, amountOfChilds));
		
		return newGeneration;
	}
	
}
