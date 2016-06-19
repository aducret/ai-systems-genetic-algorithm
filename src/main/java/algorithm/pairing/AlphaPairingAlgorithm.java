package algorithm.pairing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;
import algorithm.model.Pair;
import algorithm.util.RandomPopper;

public class AlphaPairingAlgorithm implements PairingAlgorithm {

	@Override
	public void makePairs(List<Chromosome> chromosomes, int k, Callback cb) {
		List<Chromosome> auxChromosomes = new ArrayList<>(chromosomes);
		Chromosome alphaChromosome = Collections.max(auxChromosomes, new ChromosomeComparator(true));
		auxChromosomes.remove(alphaChromosome);
		RandomPopper<Chromosome> randomPopper = new RandomPopper<>(auxChromosomes);

		for (int i = 0; i < k; i++) {
			cb.onPairAvailable(new Pair<>(alphaChromosome, randomPopper.randomPop()));
		}
	}
}