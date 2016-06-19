package algorithm.pairing;

import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;

public interface Callback {
	void onPairAvailable(Pair<Chromosome, Chromosome> parents);
}
