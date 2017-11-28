package main;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome2;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.listener.GraphListener;
import algorithm.listener.PlotterListener;
import model.GraphLab;
import model.Node;
import parser.OrganizationParser;
import parser.WorkingPlaceParser;
import util.GAPAUtils;

public class Main {

	public static Node root;

	public static void main(String[] args) throws FileNotFoundException {
		String dirFilepath = isRunningInEclipse() ? "doc/gapa/" : "./";
		String org = dirFilepath  + "org.txt";
		String emp = dirFilepath + "emp.txt";

		if (args.length >= 1) {
			org = args[0];
		}

		if (args.length >= 2) {
			emp = args[1];
		}

		// Paths
		final String solutionPath = dirFilepath + "solution.txt";
//		final String orgPath = dirFilepath  + "ej1_org";
		final String empPath = emp;
		final String orgPath = org;
//		final String empPath = dirFilepath + "emp.txt";

		// Setear listeners
		GeneticAlgorithmListener loggerListener = new LoggerGeneticAlgorithmListener();
		
		GeneticAlgorithmListener writeSolutionListener = new GeneticAlgorithmListener() {

			@Override
			public void onNewGenerationReached(int newGeneration, List<Chromosome> generation,
					Chromosome bestChromosome) {
			}

			@Override
			public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {
				try {
					OrganizationParser op = new OrganizationParser();
					TreeMap<String, HashSet<String>> projects = op.parseProjects(empPath);
					Map<String, HashSet<String>> people = GraphLab.buildPeople(projects);
					GAPAUtils.writeSolution(solutionPath, WorkingPlaceParser.generate(orgPath), people,
							(GAPAChromosome2) bestChromosome);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBestChromosomeUpdated(Chromosome bestChromosome) {
			}
		};
		
//		GeneticAlgorithmProblem problem = createGAPA(orgPath, empPath);
		GeneticAlgorithmProblem problem = new GAPAProblem(dirFilepath, orgPath, empPath);
		
		GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
		algorithm.addListener(writeSolutionListener);
		algorithm.addListener(loggerListener);
		GraphListener graphListener = new GraphListener(root);
		algorithm.addListener(graphListener);
		PlotterListener plotterListener = new PlotterListener();
		algorithm.addListener(plotterListener);
		algorithm.start();
		
//		int k = 3;
//		double bestFitness = 0;
//		Chromosome bestChromosome = null;
//		for (int i = 0; i < k; i++) {
//			
//			GeneticAlgorithmProblem problem = createGAPA(orgPath, empPath);
//			GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
////			algorithm.addListener(writeSolutionListener);
////			algorithm.addListener(loggerListener);
////			algorithm.addListener(graphListener);
////			PlotterListener plotterListener = new PlotterListener();
////			algorithm.addListener(plotterListener);
//			algorithm.start();
//			
//			
//			Chromosome chromosome = algorithm.getBestChromosome();
//			if (chromosome.fitness() > bestFitness) {
//				bestFitness = chromosome.fitness();
//				bestChromosome = chromosome;
//			}
//		}
//		
//		System.out.println(bestFitness);

		// int k = 250;
		// double acum = 0;
		// double perfectTimes = 0;
		// for (int i = 0; i < k; i++) {
		// GeneticAlgorithmProblem problem = createGAPA();
		// GeneticAlgorithm algorithm = new GeneticAlgorithm(problem);
		// algorithm.start();
		// acum += algorithm.getBestChromosome().fitness();
		// if (algorithm.getBestChromosome().fitness() == 1.0) perfectTimes++;
		// }
		//
		// System.out.println("avg best fitness found: " + acum/k + ", perfect ratio: "
		// + perfectTimes/k);
	}

	private static boolean isRunningInEclipse() {
		return !Main.class.getResource("Main.class").toString().contains("jar:");
	}

//	private static GAPAProblem createGAPA(String orgPath, String empPath) throws FileNotFoundException {
//		OrganizationParser op = new OrganizationParser();
//		Triple<List<Person>, Node, Map<Integer, Set<Integer>>> result = op.parse(orgPath, empPath);
//		root = result.second;
//		return new GAPAProblem(result.first, NodeUtils.leafs(result.second), result.third);
//	}
}
