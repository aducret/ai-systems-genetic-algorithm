package algorithm.selector;

import java.util.ArrayList;
import java.util.List;

import algorithm.model.Chromosome;

/*
 * se considera selector1 en un porcentaje de percentage, el resto de selector2
 * */
public class CompoundSelector implements Selector {

	private Selector selector1;
	private Selector selector2;
	private double percentage;

	public CompoundSelector(Selector selector1, Selector selector2,
			double percentage) {
		this.selector1 = selector1;
		this.selector2 = selector2;
		this.percentage = percentage;
	}

	@Override
	public List<Chromosome> select(List<Chromosome> chromosomes, int k) {
		int k1 = (int) Math.floor(k * percentage);
		int k2 = k - k1;
		List<Chromosome> ans = new ArrayList<>();
		ans.addAll(selector1.select(chromosomes, k1));
		ans.addAll(selector2.select(chromosomes, k2));
		return ans;
	}
}
