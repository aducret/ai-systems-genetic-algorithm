package main;

import java.io.FileNotFoundException;
import java.util.List;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome2;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.listener.GraphListener;
import algorithm.listener.PlotterListener;
import model.Node;
import parser.WorkingPlaceParser;
import util.GAPAUtils;

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

		// Paths
		final String solutionPath = "./doc/gapa/solution.txt";
//		final String orgPath = "./doc/gapa/ej1_org";
		final String empPath = "./doc/gapa/ej3_emp";
		final String orgPath = "./doc/gapa/wolox";
//		final String empPath = "./doc/gapa/employees";

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
					GAPAUtils.writeSolution(solutionPath, WorkingPlaceParser.generate(orgPath),
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
		GeneticAlgorithmProblem problem = new GAPAProblem(orgPath, empPath);
		
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
