package algorithm.crossover;

import java.util.Arrays;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import model.Node;
import model.Pair;
import model.Person;
import util.ChromosomeUtils;
import util.RandomUtils;

public class GAPACrossOver implements CrossOverAlgorithm {

	@Override
	public Pair<Chromosome, Chromosome> crossOver(Pair<Chromosome, Chromosome> pair) {
		Chromosome firstChild = pair.first.cloneChromosome();
		Chromosome secondChild = pair.second.cloneChromosome();
		
		if (!(firstChild instanceof GAPAChromosome) || !(secondChild instanceof GAPAChromosome))
			throw new IllegalStateException("GAPACrossOver can only be used with GAPAChromosomes!");
		
		GAPAChromosome gc1 = (GAPAChromosome) firstChild;
		GAPAChromosome gc2 = (GAPAChromosome) secondChild;
		int i = RandomUtils.randomBetween(0, gc1.getPeople().length - 1);
//		int i = tmiRouletteIndex(gc1, gc2);
		
		List<Person> A = Arrays.asList(gc1.getPeople());
		List<Person> B = Arrays.asList(gc2.getPeople());
		
		if (contains(B, A.get(i).workingSpace)) {
			if (contains(A, B.get(i).workingSpace)) {
				II(A, B, i);
			} else {
				IN(A, B, i);
			}
		} else {
			if (contains(A, B.get(i).workingSpace)) {
				IN(B, A, i);
			} else {
				NN(A, B, i);
			}
		}
		
		return new Pair<>(firstChild, secondChild);
	}
	
	private int tmiRouletteIndex(final GAPAChromosome c1, final GAPAChromosome c2) {
		List<Person> A1 = Arrays.asList(c1.getPeople());
		List<Person> A2 = Arrays.asList(c2.getPeople());
//		final double totalTmi = A.stream()
//				.map(p -> 2 - c.tmiFitness(p))
//				.mapToDouble(v -> v.doubleValue())
//				.sum();
		double totalTmi = 0;
		double[] relativeTmis = new double[A1.size()];
		for (int i = 0; i < A1.size(); i++) {
			relativeTmis[i] = (2 - c1.tmiFitness(A1.get(i)));
			relativeTmis[i] += (2 - c2.tmiFitness(A2.get(i)));
			totalTmi += relativeTmis[i];
		}
		for (int i = 0; i < A1.size(); i++) {
			relativeTmis[i] /= totalTmi;
		}
		
		double[] cumulativeTmis = new double[relativeTmis.length];
		cumulativeTmis[0] = relativeTmis[0];
		for (int i = 1; i < cumulativeTmis.length; i++) {
			cumulativeTmis[i] = cumulativeTmis[i - 1] + relativeTmis[i];
		}
		
		double number = RandomUtils.random();
		int winnerIndex = ChromosomeUtils.getWinner(cumulativeTmis, number);
		return winnerIndex;
	}
	
	private void II(List<Person> A, List<Person> B, int i) {
		int a = indexOf(A, B.get(i).workingSpace);
		int b = indexOf(B, A.get(i).workingSpace);
//		System.out.println("A: (i=" + i + "j=" + a + ") " + B.get(i).workingSpace);
//		System.out.println("B: (i=" + i + "j=" + b + ") " + A.get(i).workingSpace);
//		System.out.println(A);
//		System.out.println(B);
		swapWorkingPlaces(A, i, a);
		swapWorkingPlaces(B, i, b);
//		System.out.println(A);
//		System.out.println(B);
//		System.out.println("----------------------");
//		try {
//			 Thread.sleep(5000);
//		 } catch (InterruptedException e) {
//			 e.printStackTrace();
//		 }
	}
	
	private void IN(List<Person> A, List<Person> B, int i) {
		A.get(i).workingSpace = B.get(i).workingSpace;
		swapWorkingPlaces(B, i, indexOf(B, A.get(i).workingSpace));
	}
	
	private void NN(List<Person> A, List<Person> B, int i) {
		crossOverWorkingPlace(A, B, i);
	}
	
	private int indexOf(List<Person> people, Node workingSpace) {
		// Aca me hace ruido que da 4 veces el mismo resultado. 
		// Estaria bueno debuguear un poco y ver si se puede optimizar algo.
//		System.out.println(workingSpace);
		for (int i = 0; i < people.size(); i++) {
//			System.out.println(people.get(i));
			if (people.get(i).workingSpace.equals(workingSpace)) {
//				System.out.println(i);
//				System.out.println("----------------------------");
				return i;
			}
		}
		return -1;
	}
	
	private boolean contains(List<Person> people, Node workingSpace) {
		return indexOf(people, workingSpace) != -1;
	}
	
	private static void swapWorkingPlaces(List<Person> people, int a, int b) {
		Node aux = people.get(a).workingSpace;
		people.get(a).workingSpace = people.get(b).workingSpace;
		people.get(b).workingSpace = aux;
	}
	
	private static void crossOverWorkingPlace(List<Person> A, List<Person> B, int i) {
		Node aux = A.get(i).workingSpace;
		A.get(i).workingSpace = B.get(i).workingSpace;
		B.get(i).workingSpace = aux;
	}
}
