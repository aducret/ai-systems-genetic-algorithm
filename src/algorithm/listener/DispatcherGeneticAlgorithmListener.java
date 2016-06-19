package algorithm.listener;

import java.util.List;

import algorithm.chromosome.Chromosome;

/*
 * Esta clase implementa GeneticAlgorithmListener y en cada metodo
 * delega la llamada a todos los listeners
 * */
public class DispatcherGeneticAlgorithmListener implements GeneticAlgorithmListener {
	
	private List<GeneticAlgorithmListener> listeners;
	
	public DispatcherGeneticAlgorithmListener(List<GeneticAlgorithmListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	public void onNewGenerationReached(int newGeneration,
			List<Chromosome> generation, Chromosome bestChromosome) {
		for (GeneticAlgorithmListener listener: listeners) {
			listener.onNewGenerationReached(newGeneration, generation, bestChromosome);
		}
 	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		for (GeneticAlgorithmListener listener: listeners) {
			listener.onBestChromosomeUpdated(bestChromosome);
		}
	}

	@Override
	public void onGeneticAlgorithmFinished(Chromosome chromosome) {
		for (GeneticAlgorithmListener listener: listeners) {
			listener.onGeneticAlgorithmFinished(chromosome);
		}		
	}
}
