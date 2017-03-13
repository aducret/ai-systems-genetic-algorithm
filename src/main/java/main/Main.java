package main;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.listener.PlotterListener;
import algorithm.model.Triple;
import model.chromosome.GAPAChromosome;
import parser.OrganizationParser;
import structures.Node;
import structures.NodeUtils;
import structures.Person;
import util.GAPAUtils;
import util.WorkingPlaceParser;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String dirFilepath = isRunningInEclipse() ? "doc/data/" : "./";
		if (args.length != 0) {
			dirFilepath = args[0];
			if (dirFilepath.lastIndexOf('/') != dirFilepath.length()) {
				dirFilepath += "/";
			}
		}
        
//		GeneticAlgorithmProblem problem = new CharacterGeneticAlgorithmProblem(dirFilepath);
		GeneticAlgorithmProblem problem = createGAPA();
		GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);		

		// Setear listeners
		GeneticAlgorithmListener loggerListener = new LoggerGeneticAlgorithmListener();
		algorithm.addListener(loggerListener);
		algorithm.addListener(new GeneticAlgorithmListener() {
			
			@Override
			public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
			}
			
			@Override
			public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {
				try {
					GAPAUtils.writeSolution("doc/gapa/solution.txt", WorkingPlaceParser.generate("doc/gapa/ej2_org"), (GAPAChromosome) bestChromosome);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onBestChromosomeUpdated(Chromosome bestChromosome) {
			}
		});
		PlotterListener plotterListener = new PlotterListener();
		algorithm.addListener(plotterListener);
		
		algorithm.start();
		
//		int k = 250;
//		double acum = 0;
//		double perfectTimes = 0;
//		for (int i = 0; i < k; i++) {
//			GeneticAlgorithmProblem problem = createGAPA();
//			GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
//			algorithm.start();
//			acum += algorithm.getBestChromosome().fitness();
//			if (algorithm.getBestChromosome().fitness() == 1.0) perfectTimes++;
//		}
//		
//		System.out.println("avg best fitness found: " + acum/k + ", perfect ratio: " + perfectTimes/k);
	}
	
	private static boolean isRunningInEclipse() {
		return !Main.class.getResource("Main.class").toString().contains("jar:");
	}
	
	
	private static GAPAProblem createGAPA() throws FileNotFoundException {
		OrganizationParser op = new OrganizationParser();
		Triple<List<Person>, Node, Map<Integer, Set<Integer>>> result = op.parse("./doc/gapa/ej2_org", "./doc/gapa/ej2_emp");
		
		return new GAPAProblem(result.first, NodeUtils.leafs(result.second), result.third);
	}
}
