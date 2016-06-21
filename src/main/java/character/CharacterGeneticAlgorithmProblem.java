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
	
	private static final String multipliersPath = "multipliers.txt";
	private static final String configurationPath = "configuration.txt";
	private static final String weaponsPath = "armas.tsv";
	private static final String bootsPath = "botas.tsv";
	private static final String helmetsPath = "cascos.tsv";
	private static final String glovesPath = "guantes.tsv";
	private static final String shirtsPath = "pecheras.tsv";
	private static final String heightsPath = "heights.tsv";

	private int genotypesAmount = 0;
	private String dirFilepath = "/doc/data/";
	private Multipliers multipliers;
	private Configuration configuration;
	private Map<Integer, ArrayList<Gene>> geneMap;
	
	public CharacterGeneticAlgorithmProblem(String dirFilepath) throws FileNotFoundException {
		this.dirFilepath = dirFilepath;
		this.geneMap = new HashMap<>();
		loadMultipliers();
		loadConfigurationFile();
		loadItems();
		loadHeights();
	}
	
	private void loadMultipliers() throws FileNotFoundException {
		multipliers = MultipliersParser.parse(dirFilepath + multipliersPath);
	}
	
	private void loadConfigurationFile() throws FileNotFoundException {
		configuration = ConfigurationParser.parse(dirFilepath, dirFilepath + configurationPath);
	}
	
	private void loadItems() throws FileNotFoundException {
		GeneParser parser = new ItemParser();
		geneMap.put(genotypesAmount++, parser.parse(dirFilepath + weaponsPath));
		geneMap.put(genotypesAmount++, parser.parse(dirFilepath + bootsPath));
		geneMap.put(genotypesAmount++, parser.parse(dirFilepath + helmetsPath));
		geneMap.put(genotypesAmount++, parser.parse(dirFilepath + glovesPath));
		geneMap.put(genotypesAmount++, parser.parse(dirFilepath + shirtsPath));
	}
	
	private void loadHeights() throws FileNotFoundException {
		GeneParser parser = new HeightParser();
		geneMap.put(genotypesAmount++, parser.parse(dirFilepath + heightsPath));
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
