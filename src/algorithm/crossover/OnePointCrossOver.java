package algorithm.crossover;

import util.RandomUtils;
import model.Pair;
import algorithm.model.Chromosome;
import algorithm.model.Gene;

public class OnePointCrossOver implements CrossOverAlgorithm {

	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		Chromosome child1 = pair.first.clone();
		Chromosome child2 = pair.second.clone();
		int amountOfGenes = child1.geneAmount();
		int locus = RandomUtils.randomBetween(1, amountOfGenes);
		int index = locus - 1;

		for(int i = index; i < amountOfGenes; i++) {
			Gene gene1 = child1.geneAt(i);
			Gene gene2 = child2.geneAt(i);
			child1.setGene(i, gene2);
			child2.setGene(i, gene1);
		}
		
		return new Pair<Chromosome, Chromosome>(child1, child2);
	}
	
}
