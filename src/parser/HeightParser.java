package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.gene.CharacterGene;
import model.stats.Stats;
import algorithm.gene.Gene;

public class HeightParser implements GeneParser {
	@Override
	public List<Gene> parse(String path) throws FileNotFoundException {
		List<Gene> genes = new ArrayList<>();

		InputStream inputStream = new FileInputStream(path);
		Scanner scanner = new Scanner(inputStream);
		scanner.useLocale(Locale.US);

		while (scanner.hasNext()) {
			Gene gene = new CharacterGene(scanner.nextInt(), new Stats(0, 0, 0,
					0, 0, scanner.nextDouble()));
			genes.add(gene);
		}
		scanner.close();

		return genes;
	}
}
