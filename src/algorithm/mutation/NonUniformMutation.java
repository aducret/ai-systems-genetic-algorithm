package algorithm.mutation;

import java.util.ArrayList;
import java.util.Map;

import util.RandomUtils;
import algorithm.chromosome.Chromosome;
import algorithm.gene.Gene;
import algorithm.util.ChromosomeUtils;

public class NonUniformMutation implements MutationAlgorithm {

	private double pm;

	public NonUniformMutation(double p) {
		this.pm = p;
	}

	@Override
	public void mutate(Chromosome chromosome, Map<Integer, ArrayList<Gene>> geneMap) {
		int index = RandomUtils.randomBetween(0, chromosome.geneAmount() - 1);
		if (RandomUtils.should(pm)) {
			ChromosomeUtils.mutate(chromosome, geneMap, index);
		}
	}

}
