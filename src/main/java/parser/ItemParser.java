package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import model.gene.CharacterGene;
import model.stats.Stats;
import algorithm.gene.Gene;

public class ItemParser implements GeneParser {
	@Override
	public ArrayList<Gene> parse(String path) throws FileNotFoundException {
		ArrayList<Gene> genes = new ArrayList<>();

		InputStream inputStream = new FileInputStream(path);
		Scanner scanner = new Scanner(inputStream);
		scanner.useLocale(Locale.US);

		// Reads the first row of headers
		for (int i = 0; i < 6; i++)
			scanner.next();

		while (scanner.hasNext()) {
			Gene gene = new CharacterGene(scanner.nextInt(), new Stats(
					scanner.nextDouble(), scanner.nextDouble(),
					scanner.nextDouble(), scanner.nextDouble(),
					scanner.nextDouble(), 0));
			genes.add(gene);
		}
		scanner.close();

		return genes;
	}
}
