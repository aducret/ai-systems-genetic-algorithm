package parser;

import java.io.FileNotFoundException;
import java.util.List;

import algorithm.gene.Gene;

public interface GeneParser {
	public List<Gene> parse(String path) throws FileNotFoundException;
}
