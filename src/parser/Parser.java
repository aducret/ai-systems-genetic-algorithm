package parser;

import java.util.List;

import algorithm.gene.Gene;

public interface Parser {
	public List<Gene> parse(String path);
}
