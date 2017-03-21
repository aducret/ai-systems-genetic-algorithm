package main;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.listener.GraphListener;
import algorithm.listener.PlotterListener;
import model.Node;
import model.Person;
import model.Triple;
import parser.OrganizationParser;
import parser.WorkingPlaceParser;
import util.GAPAUtils;
import util.NodeUtils;

public class Main {
	
	public static Node root;
	
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
		GraphListener graphListener = new GraphListener(root);
		algorithm.addListener(loggerListener);
		algorithm.addListener(graphListener);
		algorithm.addListener(new GeneticAlgorithmListener() {
			
			@Override
			public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
			}
			
			@Override
			public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {
				try {
					GAPAUtils.writeSolution("./doc/gapa/solution.txt", WorkingPlaceParser.generate("./doc/gapa/ej1_org"), (GAPAChromosome) bestChromosome);
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
		Triple<List<Person>, Node, Map<Integer, Set<Integer>>> result = op.parse("./doc/gapa/ej1_org", "./doc/gapa/ej1_emp");
		root = result.second;
		return new GAPAProblem(result.first, NodeUtils.leafs(result.second), result.third);
	}
}
