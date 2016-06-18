package character;

import java.io.FileNotFoundException;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.listener.GeneticAlgorithmListener;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		GeneticAlgorithmProblem problem = new CharacterGeneticAlgorithmProblem();
		GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);		

		// Setear listeners
		GeneticAlgorithmListener loggerListener = new LoggerGeneticAlgorithmListener();
		algorithm.addListener(loggerListener);

		algorithm.start();
	}

}
