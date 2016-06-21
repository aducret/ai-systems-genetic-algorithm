package algorithm.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import plot.ErrorBarsPlotter;
import plot.FunctionPlotter;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;

public class PlotterListener implements GeneticAlgorithmListener {
	
	private List<Double> generations = new ArrayList<Double>();
	private List<Double> fitness = new ArrayList<Double>();
	private List<Double> avgFitness = new ArrayList<Double>();
	private List<Double> errorAvgFitness = new ArrayList<Double>();
	private List<List<Double>> chromosomesFitness;
	
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		Collections.sort(generation, new ChromosomeComparator(true));
		List<Double> generationFitness = new ArrayList<Double>();
		for(Chromosome chromosome: generation) {
			generationFitness.add(chromosome.fitness());
		}

		generations.add((double) newGeneration);
		fitness.add(bestChromosome.fitness());

		double mean = getMean(generationFitness);
		avgFitness.add(mean);
		errorAvgFitness.add(getStd(generationFitness, mean));
		
		if (chromosomesFitness == null) {
			chromosomesFitness = new ArrayList<List<Double>>(generation.size());
			for (int i = 0; i < generation.size(); i++) {
				chromosomesFitness.add(new ArrayList<Double>());
			}
		}
		for (int c = 0; c < generation.size(); c++) {
			chromosomesFitness.get(c).add(generation.get(c).fitness());
		}
	}

	private Double getMean(List<Double> values) {
		double result = 0;
		for (Double value: values) {
			result += value;
		}
		return result / values.size();
	}
	
	private Double getStd(List<Double> values, double mean) {
		double result = 0;
		for (double value: values) {
			result += Math.pow((value - mean), 2);
		}
		result /= (values.size() - 1);
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
		
		List<Double> chromosomesNumber = new ArrayList<Double>();
		List<Double> avgGenerations = new ArrayList<Double>(chromosomesFitness.get(0).size());
		List<Double> errorAvgGeneartions = new ArrayList<Double>(chromosomesFitness.get(0).size());
		for (int c = 0; c < chromosomesFitness.size(); c++) {
			double mean = getMean(chromosomesFitness.get(c));
			double std = getStd(chromosomesFitness.get(c), mean);
			avgGenerations.add(mean);
			errorAvgGeneartions.add(std);
			chromosomesNumber.add((double)c);
			if (c == chromosomesFitness.size() -1) {
				System.out.println(mean);
				System.out.println(std);
			}
		}
		System.out.println(chromosomesFitness.get(chromosomesFitness.size()-1));
		errorBarsPlotter.plot(chromosomesNumber, avgGenerations, errorAvgGeneartions);
	}
}
