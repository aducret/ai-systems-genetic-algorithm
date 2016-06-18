package character;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.Configuration;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.gene.Gene;
import model.Multipliers;
import model.chromosome.CharacterListChromosome;
import parser.ConfigurationParser;
import parser.GeneParser;
import parser.HeightParser;
import parser.ItemParser;
import parser.MultipliersParser;

public class CharacterGeneticAlgorithmProblem implements GeneticAlgorithmProblem {
	
	private static final String multipliersPath = "doc/data/multipliers.txt";
	private static final String configurationPath = "doc/data/configuration.txt";
	private static final String weaponsPath = "doc/data/armas.tsv";
	private static final String bootsPath = "doc/data/botas.tsv";
	private static final String helmetsPath = "doc/data/cascos.tsv";
	private static final String glovesPath = "doc/data/guantes.tsv";
	private static final String shirtsPath = "doc/data/pecheras.tsv";
	private static final String heightsPath = "doc/data/heights.tsv";

	private static final int LOCUS_WEAPONS = 0;
	private static final int LOCUS_BOOTS = 1;
	private static final int LOCUS_HELMETS = 2;
	private static final int LOCUS_GLOVES = 3;
	private static final int LOCUS_SHIRTS = 4;
	private static final int LOCUS_HEIGHTS = 5;
	
	private Multipliers multipliers;
	private Configuration configuration;
	private Map<Integer, ArrayList<Gene>> geneMap;
	
	public CharacterGeneticAlgorithmProblem() throws FileNotFoundException {
		geneMap = new HashMap<>();
		loadMultipliers();
		loadConfigurationFile();
		loadItems();
		loadHeights();
	}
	
	private void loadMultipliers() throws FileNotFoundException {
		multipliers = MultipliersParser.parse(multipliersPath);
	}
	
	private void loadConfigurationFile() throws FileNotFoundException {
		configuration = ConfigurationParser.parse(configurationPath);
	}
	
	private void loadItems() throws FileNotFoundException {
		GeneParser parser = new ItemParser();
		geneMap.put(LOCUS_WEAPONS, parser.parse(weaponsPath));
		geneMap.put(LOCUS_BOOTS, parser.parse(bootsPath));
		geneMap.put(LOCUS_HELMETS, parser.parse(helmetsPath));
		geneMap.put(LOCUS_GLOVES, parser.parse(glovesPath));
		geneMap.put(LOCUS_SHIRTS, parser.parse(shirtsPath));
	}
	
	private void loadHeights() throws FileNotFoundException {
		GeneParser parser = new HeightParser();
		geneMap.put(LOCUS_HEIGHTS, parser.parse(heightsPath));
	}

	@Override
	public Map<Integer, ArrayList<Gene>> geneMap() {
		return geneMap;
	}
	
	@Override
	public Configuration configuration() {
		return configuration;
	}

	@Override
	public Chromosome createChromosome(List<Gene> genes) {
		return new CharacterListChromosome(genes, multipliers);
	}
}
