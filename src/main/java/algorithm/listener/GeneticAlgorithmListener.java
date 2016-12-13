package algorithm.listener;

import java.util.List;

import model.chromosome.Chromosome;

public interface GeneticAlgorithmListener {
	
	void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome);
	
	void onBestChromosomeUpdated(Chromosome bestChromosome);
	
	void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome);
}
