package parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import algorithm.gene.Gene;

public interface GeneParser {
	public ArrayList<Gene> parse(String path) throws FileNotFoundException;
}
