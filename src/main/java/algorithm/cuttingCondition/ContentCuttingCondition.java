package algorithm.cuttingCondition;

import java.util.List;

import algorithm.chromosome.Chromosome;

/*
 * if generationsTolerance generations pass without the max fitness being updated,
 * isCutting will return true
 * */
public class ContentCuttingCondition implements CuttingCondition {

	private int generationsTolerance;
	private boolean toleranceLevelReached;
	private int generationsWithoutUpdate;

	public ContentCuttingCondition(int generationsTolerance) {
		this.generationsTolerance = generationsTolerance;
		generationsTolerance = 0;
	}

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		generationsWithoutUpdate++;
		if (generationsWithoutUpdate >= generationsTolerance)
			toleranceLevelReached = true;
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		generationsWithoutUpdate = 0;
	}

	@Override
	public boolean isCutting() {
		return toleranceLevelReached;
	}

	@Override
	public String description() {
		return "reached " + generationsTolerance + " generations without best chromosome update";
	}

	@Override
	public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {	}
}
