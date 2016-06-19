package algorithm.pairing;

import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;
import algorithm.util.RandomPopper;

public class RandomPairingAlgorithm implements PairingAlgorithm {

	@Override
	public void makePairs(List<Chromosome> chromosomes, int k, Callback cb) {
		RandomPopper<Chromosome> randomPopper = new RandomPopper<>(chromosomes);
		for (int i = 0; i < k; i++) {
			cb.onPairAvailable(new Pair<>(randomPopper.randomPop(), randomPopper.randomPop()));
		}
	}
}
