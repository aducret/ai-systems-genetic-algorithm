package algorithm.pairing;

import model.chromosome.Chromosome;
import structures.Pair;

public interface Callback {
	void onPairAvailable(Pair<Chromosome, Chromosome> parents);
}
