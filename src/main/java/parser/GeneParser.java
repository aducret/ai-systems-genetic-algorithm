package parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.gene.Gene;

public interface GeneParser {
	public ArrayList<Gene> parse(String path) throws FileNotFoundException;
}
