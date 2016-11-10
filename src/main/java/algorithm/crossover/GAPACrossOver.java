package algorithm.crossover;

import java.util.Arrays;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.model.Pair;
import algorithm.util.RandomUtils;
import model.chromosome.GAPAChromosome;
import structures.Node;
import structures.Person;

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
		
		List<Person> A = Arrays.asList(gc1.getPeople());
		List<Person> B = Arrays.asList(gc2.getPeople());
		
		if (B.contains(A.get(i))) {
			if (A.contains(B.get(i))) {
				//II
				II(A, B, i);
			} else {
				//IN
				IN(A, B, i);
			}
		} else {
			if (A.contains(B.get(i))) {
				//NI
				IN(B, A, i);
			} else {
				//NN
				NN(A, B, i);
			}
		}
		
		return new Pair<>(firstChild, secondChild);
	}
	
	private void II(List<Person> A, List<Person> B, int i) {
		swapWorkingPlaces(A, i, A.indexOf(B.get(i)));
		swapWorkingPlaces(B, i, B.indexOf(A.get(i)));
	}
	
	private void IN(List<Person> A, List<Person> B, int i) {
		A.get(i).workingSpace = B.get(i).workingSpace;
		swapWorkingPlaces(B, i, B.indexOf(A.get(i)));
	}
	
	private void NN(List<Person> A, List<Person> B, int i) {
		crossOverWorkingPlace(A, B, i);
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
