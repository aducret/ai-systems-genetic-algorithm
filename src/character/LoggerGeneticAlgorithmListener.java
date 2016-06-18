package character;

import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.listener.GeneticAlgorithmListener;

public class LoggerGeneticAlgorithmListener implements GeneticAlgorithmListener {

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		System.out.println("generation: " + newGeneration + ", currentBest: " + bestChromosome.fitness());
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		System.out.println("best updated: " + bestChromosome.fitness());
	}
}
