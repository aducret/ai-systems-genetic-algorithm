package main;

import java.awt.Color;
import java.awt.Font;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

import algorithm.chromosome.Chromosome;
import algorithm.listener.GeneticAlgorithmListener;

public class LoggerGeneticAlgorithmListener implements GeneticAlgorithmListener {

	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
		System.out.println("generation: " + newGeneration + ", currentBest: " + bestChromosome.fitness());
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
		System.out.println("best updated: " + bestChromosome.fitness());
	}

	@Override
	public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {
		System.out.println();
		System.out.println("Algorithm finished, best chromosome for last generation: ");
		System.out.println(currentBestChromosome);
		
		System.out.println("best chromosome:");
		System.out.println(bestChromosome);
	}
	
}
