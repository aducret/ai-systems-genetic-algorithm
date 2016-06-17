package algorithm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithm.model.Chromosome;
import algorithm.model.Gene;
import util.RandomUtils;

public class ChromosomeUtils {
	public static void exchange(Chromosome c1, Chromosome c2, int r1, int r2) {
		for (int r = r1; r <= r2; r++) {
			exchange(c1, c2, r);
		}
	}
	
	public static void exchange(Chromosome c1, Chromosome c2, int r) {
		Gene gene = c1.geneAt(r);
		c1.setGene(r, c2.geneAt(r));
		c2.setGene(r, gene);
	}
	
	public static double[] relativeFitnesses(List<Chromosome> chromosomes) {
		double totalFitness = totalFitness(chromosomes);
		double[] relativeFitnesses = new double[chromosomes.size()];
		for (int i = 0; i < relativeFitnesses.length; i++) {
			relativeFitnesses[i] = chromosomes.get(i).fitness() / totalFitness;
		}
		return relativeFitnesses;
	}

	public static double[] cumulativeRelativeFitnesses(double[] relativeFitnesses) {
		double[] cumulativeRelativeFitnesses = new double[relativeFitnesses.length];
		cumulativeRelativeFitnesses[0] = relativeFitnesses[0];
		for (int i = 1; i < cumulativeRelativeFitnesses.length; i++) {
			cumulativeRelativeFitnesses[i] = cumulativeRelativeFitnesses[i - 1] + relativeFitnesses[i];
		}
		return cumulativeRelativeFitnesses;
	}

	public static double totalFitness(List<Chromosome> chromosomes) {
		double totalFitness = 0;
		for (Chromosome chromosome : chromosomes) {
			totalFitness += chromosome.fitness();
		}
		return totalFitness;

	}
	
	public static int getWinner(double[] cumulativeRelativeFitnesses, double number) {
		for (int i = 0; i < cumulativeRelativeFitnesses.length; i++) {
			if (cumulativeRelativeFitnesses[i] < number)
				return i;
		}
		return -1;
	}
	
	public static void mutate(Chromosome chromosome, Map<Integer, ArrayList<Gene>> geneMap, int locus) {
		List<Gene> alleles = geneMap.get(locus);
		int randomGeneIndex = RandomUtils.randomBetween(0, alleles.size() - 1);
		Gene randomGene = alleles.get(randomGeneIndex);
		chromosome.setGene(locus, randomGene);
	}
}
