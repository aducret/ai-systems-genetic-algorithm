package algorithm.pairing;

import java.util.List;

import algorithm.chromosome.Chromosome;

public interface PairingAlgorithm {
	
	void makePairs(List<Chromosome> chromosomes, int k, Callback cb);
}
