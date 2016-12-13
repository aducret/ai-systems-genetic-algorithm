package algorithm.pairing;

import java.util.List;

import model.chromosome.Chromosome;

public interface PairingAlgorithm {
	
	void makePairs(List<Chromosome> chromosomes, int k, Callback cb);
}
