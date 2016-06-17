package algorithm.crossover;

import util.RandomUtils;
import model.Pair;
import algorithm.model.Chromosome;
import algorithm.util.ChromosomeUtils;

public class OnePointCrossOver implements CrossOverAlgorithm {
	
	private double pc;
	
	public OnePointCrossOver(double pc) {
		this.pc = pc;
	}
	
	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		Pair<Chromosome, Chromosome> childs = new Pair<Chromosome, Chromosome>(pair.first.clone(), pair.second.clone());
		if (!RandomUtils.should(pc)) { return childs; }
		
		int amountOfGenes = pair.first.geneAmount();
		int locus = RandomUtils.randomBetween(1, amountOfGenes);
		int from = locus - 1;
		int to = childs.first.geneAmount()-1;
		ChromosomeUtils.crossOver(childs.first, childs.second, from, to);
		
		return childs;
	}
	
}
