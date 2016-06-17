package algorithm.mutation;

import java.util.ArrayList;
import java.util.Map;

import algorithm.model.Chromosome;
import algorithm.model.Gene;
import algorithm.util.ChromosomeUtils;
import util.RandomUtils;

public class ClassicMutationAlgorithm implements MutationAlgorithm {

	private double p;
	
	public ClassicMutationAlgorithm(double p) {
		this.p = p;
	}
	
	@Override
	public void mutate(Chromosome chromosome,
			Map<Integer, ArrayList<Gene>> geneMap) {
		for (int i = 0; i < chromosome.geneAmount(); i++) {
			if (RandomUtils.should(p)) {
				ChromosomeUtils.mutate(chromosome, geneMap, i);
			}
		}
	}
}
