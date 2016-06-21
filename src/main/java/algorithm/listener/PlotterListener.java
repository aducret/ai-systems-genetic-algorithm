package algorithm.listener;

import java.util.ArrayList;
import java.util.List;

import plot.ErrorBarsPlotter;
import plot.FunctionPlotter;
import algorithm.chromosome.Chromosome;

public class PlotterListener implements GeneticAlgorithmListener {
	
	private List<Double> generations = new ArrayList<Double>();
	private List<Double> fitness = new ArrayList<Double>();
	private List<Double> avgFitness = new ArrayList<Double>();
	private List<Double> errorAvgFitness = new ArrayList<Double>();
	
	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		generations.add((double) newGeneration);
		fitness.add(bestChromosome.fitness());
		
		double mean = getAvgFitness(generation);
		avgFitness.add(mean);
		errorAvgFitness.add(getErrorAvgFitness(generation, mean));
	}

	private Double getAvgFitness(List<Chromosome> generation) {
		double result = 0;
		for (Chromosome chromosome: generation) {
			result += chromosome.fitness();
		}
		return result / generation.size();
	}
	
	private Double getErrorAvgFitness(List<Chromosome> generation, double mean) {
		double result = 0;
		for (Chromosome chromosome: generation) {
			result += Math.pow((chromosome.fitness() - mean), 2);
		}
		result /= generation.size();
		return Math.sqrt(result);
	}
	
	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) { }

	@Override
	public void onGeneticAlgorithmFinished(Chromosome currentbestChromosome, Chromosome bestChromosome) { 
		FunctionPlotter plotter = new FunctionPlotter();
		plotter.plot(generations, fitness);
		
		ErrorBarsPlotter errorBarsPlotter = new ErrorBarsPlotter();
		errorBarsPlotter.plot(generations, avgFitness, errorAvgFitness);
	}
}
