package main;

import java.io.FileNotFoundException;
import java.util.List;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.model.Pair;
import algorithm.model.Triple;
import parser.OrganizationParser;
import structures.Node;
import structures.NodeUtils;
import structures.Person;

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
//		PlotterListener plotterListener = new PlotterListener();
//		algorithm.addListener(plotterListener);
		
		algorithm.start();
	}
	
	private static boolean isRunningInEclipse() {
		return !Main.class.getResource("Main.class").toString().contains("jar:");
	}
	
	
	private static GAPAProblem createGAPA() throws FileNotFoundException {
		OrganizationParser op = new OrganizationParser();
		Triple<List<Person>, Node, List<Pair<Integer, Integer>>> result = op.parse("./doc/gapa/ej1_org", "./doc/gapa/ej1_emp");
		
		return new GAPAProblem(result.first, NodeUtils.leafs(result.second), result.third);
	}
}
