package algorithm.mutation;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome2;
import util.RandomUtils;

public class GAPAMutationAlgorithm2 implements MutationAlgorithm {

	@Override
	public void mutate(Chromosome chromosome, double p) {
		if (!(chromosome instanceof GAPAChromosome2))
			throw new IllegalStateException("GAPAMutationAlgorithm can only be used with GAPAChromosomes!");
		
		if (p < RandomUtils.random()) { return; }
//		if (true) return;
		
		GAPAChromosome2 gc = (GAPAChromosome2) chromosome;
		int i = RandomUtils.randomBetween(1, gc.order.length - 1);
		swap(gc.order, i, i-1);
		
		for (int k = 0; k < gc.affinities.length; k++) {
//			i = RandomUtils.randomBetween(1, gc.order.length - 1);
			swap(gc.affinities[k], i, i-1);
		}
	}
	
	private void swap(int[] arr, int a, int b) {
		int aux = arr[a];
		arr[a] = arr[b];
		arr[b] = aux;
	}
}
