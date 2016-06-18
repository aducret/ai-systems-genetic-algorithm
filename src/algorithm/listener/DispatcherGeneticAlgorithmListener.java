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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		// TODO Auto-generated method stub
		
	}
}
