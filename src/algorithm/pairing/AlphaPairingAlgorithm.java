package algorithm.pairing;

import java.util.Collections;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;
import algorithm.model.Pair;
import algorithm.util.RandomPopper;

public class AlphaPairingAlgorithm implements PairingAlgorithm {

	@Override
	public void makePairs(List<Chromosome> chromosomes, int k, Callback cb) {
		RandomPopper<Chromosome> randomPopper = new RandomPopper<>(chromosomes);
		Chromosome alphaChromosome = Collections.max(chromosomes, new ChromosomeComparator(true));
		for (int i = 0; i < k; i++) {
			cb.onPairAvailable(new Pair<>(alphaChromosome, randomPopper.randomPeek()));
		}
	}
}