package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.util.ChromosomeUtils;
import algorithm.util.RandomUtils;

public class BoltzmannSelector implements Selector {

	private double temperature;

	public BoltzmannSelector(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selectedChromosomes = new ArrayList<Chromosome>();
		double[] expectedValues = initializeExpectedValues(chromosomes);
		double[] cumulativeProbabilities = ChromosomeUtils.cumulativeProbabilities(expectedValues);

		for (int i = 0; i < k; i++) {
			int winnerIndex = ChromosomeUtils.getWinner(cumulativeProbabilities, RandomUtils.random());
			selectedChromosomes.add(chromosomes.get(winnerIndex));
		}
		updateTemperature();
		return selectedChromosomes;
	}

	private void updateTemperature() {
		if (temperature > 1) {
			temperature--;
		}
	}
	
	private double[] initializeExpectedValues(List<Chromosome> chromosomes) {
		double[] expectedValues = new double[chromosomes.size()];
		double denominator = calculateDenominator(chromosomes);
		for (int i = 0; i < chromosomes.size(); i++) {
			expectedValues[i] = getExpectedValue(chromosomes.get(i), denominator);
		}
		return expectedValues;
	}

	private double getExpectedValue(Chromosome chromosome, double denominator) {
		double numerator = Math.exp(chromosome.fitness() / temperature);
		return numerator / denominator;
	}

	private double calculateDenominator(List<Chromosome> chromosomes) {
		double denominator = 0;
		for (Chromosome chromosome : chromosomes) {
			denominator += Math.exp(chromosome.fitness() / temperature);
		}

		return denominator;
	}

}
