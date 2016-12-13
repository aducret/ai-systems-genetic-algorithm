package algorithm.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.chromosome.Chromosome;
import model.chromosome.ChromosomeComparator;
import util.ChromosomeUtils;
import util.RandomUtils;

public class RankingSelector implements Selector {
	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		List<Chromosome> selected = new ArrayList<>();
		Collections.sort(chromosomes, new ChromosomeComparator(true));
		double[] relativeRankings = relativeRankings(chromosomes);
		double[] cummulativeProbabilities = ChromosomeUtils
				.cumulativeProbabilities(relativeRankings);

		for (int j = 0; j < k; j++) {
			double number = RandomUtils.random();
			int winnerIndex = ChromosomeUtils.getWinner(
					cummulativeProbabilities, number);
			selected.add(chromosomes.get(winnerIndex));
		}
		return selected;
	}

	private double[] relativeRankings(List<Chromosome> chromosomes) {
		int chromosomesSize = chromosomes.size();
		double[] relativeRankings = new double[chromosomesSize];
		double totalRanking = (chromosomesSize * (chromosomesSize + 1)) / 2;
		for (int i = 0; i < chromosomesSize; i++) {
			relativeRankings[i] = (i + 1) / totalRanking;
		}
		return relativeRankings;
	}
}
