package algorithm.util;

import algorithm.model.Chromosome;
import algorithm.model.Gene;

public class ChromosomeUtils {
	public static void exchange(Chromosome c1, Chromosome c2, int r1, int r2) {
		for (int r = r1; r <= r2; r++) {
			exchange(c1, c2, r);
		}
	}
	
	public static void exchange(Chromosome c1, Chromosome c2, int r) {
		Gene gene = c1.geneAt(r);
		c1.setGene(r, c2.geneAt(r));
		c2.setGene(r, gene);
	}
}
