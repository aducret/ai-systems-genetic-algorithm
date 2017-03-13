package algorithm.pairing;

import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.selector.Selector;
import model.Pair;

public class SelectorPairingAlgorithm implements PairingAlgorithm {

	private Selector selector;

	public SelectorPairingAlgorithm(Selector selector) {
		this.selector = selector;
	}

	@Override
	public void makePairs(List<Chromosome> chromosomes, int k, Callback cb) {
		for (int i = 0; i < k; i++) {
			List<Chromosome> selected = selector.select(chromosomes, 2);
			cb.onPairAvailable(new Pair<>(selected.get(0), selected.get(1)));
		}
	}

}
