package character;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.replace.ReplaceMethod1;

public class Main {

	public static void main(String[] args) {
		
		String weaponsPath = "doc/data/armas.tsv";
		String bootsPath = "doc/data/botas.tsv";
		String helmetsPath = "doc/data/cascos.tsv";
		String glovesPath = "doc/data/guantes.tsv";
		String shirtsPath = "doc/data/pecheras.tsv";
		String heightsPath = "doc/data/heights.txt";
		
		GeneticAlgorithmProblem problem = new CharacterGeneticAlgorithmProblem();

		// TODO: add cutting conditions
		GeneticAlgorithm algorithm = new GeneticAlgorithm.Builder()
				.withProblem(problem)
				.withReplaceAlgorithm(new ReplaceMethod1())
				.withPairingAlgorithm(null)
				.build();

		// Setear listeners
		GeneticAlgorithmListener loggerListener = new LoggerGeneticAlgorithmListener();
		algorithm.addListener(loggerListener);

		algorithm.start();
	}

}
