package algorithm.cuttingCondition;

import java.util.List;

import algorithm.model.Chromosome;

public class MaxGenerationsCuttingCondition implements CuttingCondition {

	private boolean maxGenerationsReached;
	private int maxGenerations;

	public MaxGenerationsCuttingCondition(int maxGenerations) {
		this.maxGenerations = maxGenerations;
	}

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		if (newGeneration > maxGenerations)
			maxGenerationsReached = true;
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCutting() {
		return maxGenerationsReached;
	}

}
