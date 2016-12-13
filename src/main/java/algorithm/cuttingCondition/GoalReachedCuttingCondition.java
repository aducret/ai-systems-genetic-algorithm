package algorithm.cuttingCondition;

import java.util.List;

import model.chromosome.Chromosome;

public class GoalReachedCuttingCondition implements CuttingCondition {

	private double goal;
	private boolean goalReached;

	public GoalReachedCuttingCondition(double goal) {
		this.goal = goal;
	}

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		if (bestChromosome.fitness() >= goal)
			goalReached = true;
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {

	}

	@Override
	public boolean isCutting() {
		return goalReached;
	}

	@Override
	public String description() {
		return "reached goal fitness: " + goal;
	}

	@Override
	public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) { }
}
