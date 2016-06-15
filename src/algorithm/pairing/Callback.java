package algorithm.pairing;

import model.Pair;
import algorithm.model.Chromosome;

public interface Callback {
	void onPairAvailable(Pair<Chromosome, Chromosome> parents);
}
