package algorithm.cuttingCondition;

import java.util.HashSet;
import java.util.List;

import algorithm.chromosome.Chromosome;

/*
 * if tolerance percent of the population or more remains the same from
 * generation to generation, isCutting will return true
 * */
public class StructureCuttingCondition implements CuttingCondition {

	private double tolerance;
	private boolean structureRemainsTheSame;
	private HashSet<Chromosome> previousGeneration;

	public StructureCuttingCondition(double tolerance) {
		this.tolerance = tolerance;
		previousGeneration = new HashSet<>();
	}

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		double remainedTheSame = changePercentage(generation);
		if (remainedTheSame > tolerance) {
			structureRemainsTheSame = true;
		} else {
			previousGeneration = new HashSet<>(generation);
		}
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
	}

	@Override
	public boolean isCutting() {
		return structureRemainsTheSame;
	}

	private double changePercentage(List<Chromosome> currentGeneration) {
		int occurrences = 0;
		for (Chromosome chromosome : currentGeneration) {
			if (previousGeneration.contains(chromosome))
				occurrences++;
		}
		return occurrences / ((double) currentGeneration.size());
	}
}
