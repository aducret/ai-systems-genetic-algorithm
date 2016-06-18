package algorithm.mutation;

import java.util.ArrayList;
import java.util.Map;

import algorithm.chromosome.Chromosome;
import algorithm.gene.Gene;
import algorithm.util.ChromosomeUtils;
import util.RandomUtils;

public class ClassicMutation implements MutationAlgorithm {

	private double pm;
	
	public ClassicMutation(double p) {
		this.pm = p;
	}
	
	@Override
	public void mutate(Chromosome chromosome,
			Map<Integer, ArrayList<Gene>> geneMap) {
		for (int i = 0; i < chromosome.geneAmount(); i++) {
			if (RandomUtils.should(pm)) {
				ChromosomeUtils.mutate(chromosome, geneMap, i);
			}
		}
	}
}
