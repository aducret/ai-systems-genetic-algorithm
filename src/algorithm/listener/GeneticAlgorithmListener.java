package algorithm.listener;

import java.util.List;

import algorithm.model.Chromosome;

public interface GeneticAlgorithmListener {
	
	void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome);
	
	void onBestChromosomeUpdated(Chromosome bestChromosome);
}
