package algorithm.pairing;

import java.util.List;

import algorithm.model.Chromosome;

public interface PairingAlgorithm {
	
	void makePairs(List<Chromosome> chromosomes, int k, Callback cb);
}
