package main;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.listener.PlotterListener;
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
		PlotterListener plotterListener = new PlotterListener();
		//algorithm.addListener(plotterListener);
		
		algorithm.start();
	}
	
	private static boolean isRunningInEclipse() {
		return !Main.class.getResource("Main.class").toString().contains("jar:");
	}
	
	
	private static GAPAProblem createGAPA() {
		Node cuarto = new Node("cuarto", null);
		
		Node mesa1 = new Node("mesa1", cuarto);
		Node mesa2 = new Node("mesa2", cuarto);
		
		Node silla1 = new Node("silla1", mesa1);
		Node silla2 = new Node("silla2", mesa1);
		Node silla3 = new Node("silla3", mesa2);
		Node silla4 = new Node("silla4", mesa2);
		
		List<String> people = Arrays.asList(new String[] {
				"nacho",
				"prudi",
				"tom",
				"gus"
		});
		
		int[][] restrictions = new int[][] {
			{0, 2},
			{1, 3}
		};
		
		List<Node> seats = NodeUtils.leafs(cuarto);
		
		return new GAPAProblem(people, seats, restrictions);
	}
}
