package algorithm.crossover;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import algorithm.chromosome.GAPAChromosome2;
import model.Pair;
import util.RandomUtils;

public class GAPACrossOver2 implements CrossOverAlgorithm {

	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		Chromosome firstChild = pair.first.cloneChromosome();
		Chromosome secondChild = pair.second.cloneChromosome();
		
		if (!(firstChild instanceof GAPAChromosome2) || !(secondChild instanceof GAPAChromosome2))
			throw new IllegalStateException("GAPACrossOver2 can only be used with GAPAChromosome2's!");
		
		//TODO: remove to avoid dead code
//		if (true) return new Pair<>(firstChild, secondChild);
		
		GAPAChromosome2 gc1 = (GAPAChromosome2) firstChild;
		GAPAChromosome2 gc2 = (GAPAChromosome2) secondChild;
		int i = RandomUtils.randomBetween(0, gc1.order.length - 1);
		cross(gc1.order, gc2.order, i);
		
		for (int k = 0; k < gc1.affinities.length; k++) {
//			i = RandomUtils.randomBetween(0, gc1.order.length - 1);
			cross(gc1.affinities[k], gc2.affinities[k], i);
		}
		
		return new Pair<>(firstChild, secondChild);
	}
	
	private void cross(int[] arr1, int[] arr2, int i) {
		int argB = arg(arr2, arr1[i]);
		shift(arr1, i, argB);
		
		int argA = arg(arr1, arr2[i]);
		shift(arr2, i, argA);
	}
	
	private void shift(int[] arr, int i, int arg) {
		if (i == arg) return;
		if (i < arg) swap(arr, i, i+1);
		else swap(arr, i, i-1);
	}
	
	private void swap(int[] arr, int a, int b) {
		int aux = arr[a];
		arr[a] = arr[b];
		arr[b] = aux;
	}
	
	private int arg(int[] arr, int val) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == val) return i;
		}
		return -1;
	}
}
