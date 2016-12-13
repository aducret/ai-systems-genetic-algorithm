package algorithm.pairing;

import java.util.ArrayList;
import java.util.List;

import model.chromosome.Chromosome;
import structures.Pair;
import util.RandomPopper;

public class RandomPairingAlgorithm implements PairingAlgorithm {

	@Override
	public void makePairs(List<Chromosome> chromosomes, int k, Callback cb) {
		List<Chromosome> chromosomesCloned = new ArrayList<>(chromosomes);
		RandomPopper<Chromosome> randomPopper = new RandomPopper<>(chromosomesCloned);
		for (int i = 0; i < k; i++) {
			Chromosome c1 = randomPopper.randomPop();
			Chromosome c2 = randomPopper.randomPop();
			cb.onPairAvailable(new Pair<>(c1, c2));
		}
	}
}
