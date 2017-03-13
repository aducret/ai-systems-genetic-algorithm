package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;
import algorithm.gene.Gene;

public class ChromosomeUtils {
	public static void crossOver(Chromosome c1, Chromosome c2, int r1, int r2) {
		for (int r = r1; r <= r2; r++) {
			crossOver(c1, c2, r);
		}
	}

	public static void crossOver(Chromosome c1, Chromosome c2, int r) {
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

	private static double totalFitness(List<Chromosome> chromosomes) {
		double totalFitness = 0;
		for (Chromosome chromosome : chromosomes) {
			totalFitness += chromosome.fitness();
		}
		return totalFitness;

	}

	public static double[] cumulativeProbabilities(double[] probabilities) {
		double[] cumulativeProbabilities = new double[probabilities.length];
		cumulativeProbabilities[0] = probabilities[0];
		for (int i = 1; i < cumulativeProbabilities.length; i++) {
			cumulativeProbabilities[i] = cumulativeProbabilities[i - 1]
					+ probabilities[i];
		}
		return cumulativeProbabilities;
	}

	public static int getWinner(double[] cumulativeProbabilities, double number) {
		for (int i = 0; i < cumulativeProbabilities.length; i++) {
			if (number < cumulativeProbabilities[i])
				return i;
		}
		return -1;
	}

	public static void mutate(Chromosome chromosome,
			Map<Integer, ArrayList<Gene>> geneMap, int locus) {
		List<Gene> alleles = geneMap.get(locus);
		int randomGeneIndex = RandomUtils.randomBetween(0, alleles.size() - 1);
		Gene randomGene = alleles.get(randomGeneIndex);
		chromosome.setGene(locus, randomGene);
	}
	
	public static Chromosome best(Chromosome c1, Chromosome c2) {
		return new ChromosomeComparator(false).compare(c1, c2) >= 0 ? c1 : c2; 
	}
	
	public static Chromosome worst(Chromosome c1, Chromosome c2) {
		return new ChromosomeComparator(false).compare(c1, c2) < 0 ? c1 : c2;
	}
}