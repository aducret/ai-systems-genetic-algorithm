package algorithm;

import algorithm.listener.GeneticAlgorithmListener;
import character.CharacterGeneticAlgorithmProblem;
import character.LoggerGeneticAlgorithmListener;

public class Main {

	public static void main(String[] args) {
		String configurationFilePath = null;
		GeneticAlgorithmProblem problem = new CharacterGeneticAlgorithmProblem(
				configurationFilePath);
		
		//TODO: add cutting conditions
		GeneticAlgorithm algorithm = new GeneticAlgorithm.Builder()
										.withProblem(problem)
										.build();

		// Setear listeners
		GeneticAlgorithmListener loggerListener = new LoggerGeneticAlgorithmListener();
		algorithm.addListener(loggerListener);
		
		algorithm.start();
	}

}
