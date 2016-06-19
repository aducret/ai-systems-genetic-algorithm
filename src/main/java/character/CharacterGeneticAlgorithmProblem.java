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

	private int genotypesAmount = 0;
	
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
		geneMap.put(genotypesAmount++, parser.parse(weaponsPath));
		geneMap.put(genotypesAmount++, parser.parse(bootsPath));
		geneMap.put(genotypesAmount++, parser.parse(helmetsPath));
		geneMap.put(genotypesAmount++, parser.parse(glovesPath));
		geneMap.put(genotypesAmount++, parser.parse(shirtsPath));
	}
	
	private void loadHeights() throws FileNotFoundException {
		GeneParser parser = new HeightParser();
		geneMap.put(genotypesAmount++, parser.parse(heightsPath));
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
