package algorithm;

import java.util.Comparator;

import algorithm.model.Chromosome;

/*
 * Ordena los cromosomas de mayor a menor
 * */
public class ChromosomeComparator implements Comparator<Chromosome> {

	private static final double EPSILON = 1e-4;
	private int modifier;

	public ChromosomeComparator(boolean asc) {
		modifier = asc ? 1 : -1;
	}

	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		double fitness1 = o1.fitness();
		double fitness2 = o2.fitness();
		if (Math.abs(fitness1 - fitness2) < EPSILON) {
			return 0;
		} else if (fitness1 > fitness2)
			return 1 * modifier;
		else
			return (-1) * modifier;
	}

}
