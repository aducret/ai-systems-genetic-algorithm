package algorithm.listener;

import java.util.List;

import model.chromosome.Chromosome;

public class LoggerGeneticAlgorithmListener implements GeneticAlgorithmListener {

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		System.out.println("generation: " + newGeneration + ", currentBest: " + bestChromosome.fitness());
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		System.out.println("best updated: " + bestChromosome.fitness());
	}

	@Override
	public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {
		System.out.println();
		System.out.println("Algorithm finished, best chromosome for last generation: ");
		System.out.println(currentBestChromosome);
		
		System.out.println("best chromosome:");
		System.out.println(bestChromosome);
	}
}
