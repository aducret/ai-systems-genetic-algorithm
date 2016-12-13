package algorithm.crossover;

import java.util.List;

import model.Person;
import model.chromosome.Chromosome;
import model.chromosome.GAPAChromosome;
import structures.Node;
import structures.Pair;
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
		int i = RandomUtils.randomBetween(0, gc1.people.size() - 1);
		List<Person> A = gc1.clonePeople();
		List<Person> B = gc2.clonePeople();
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
		
		gc1.people = A;
		gc2.people = B;
		
//		System.out.println(i);
//		System.out.println(pair.first);
//		System.out.println(pair.second);
//		System.out.println(firstChild);
//		System.out.println(secondChild);
//		System.out.println("------------");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return new Pair<>(firstChild, secondChild);
	}
	
	private void II(List<Person> A, List<Person> B, int i) {
		int a = indexOf(B.get(i).seat, A);
		int b = indexOf(A.get(i).seat, B);
		swapWorkingPlaces(A, i, a);
		swapWorkingPlaces(B, i, b);
	}
	
	private void IN(List<Person> A, List<Person> B, int i) {
		int b = indexOf(A.get(i).seat, B);
		Person personA = A.get(i);
		Person personB = B.get(i);
		
		Person newPersonA = personA.clone();
		newPersonA.seat = personB.seat;
		
		A.set(i, newPersonA);
		swapWorkingPlaces(B, i, b);
	}
	
	private void NN(List<Person> A, List<Person> B, int i) {
		crossOverWorkingPlace(A, B, i);
	}
	
	private int indexOf(Node seat, List<Person> people) {
		for (int i = 0; i < people.size(); i++) {
			if (people.get(i).seat.equals(seat))
				return i;
		}
		return -1;
	}
	
	private static void swapWorkingPlaces(List<Person> people, int a, int b) {
		Person personA = people.get(a);
		Person personB = people.get(b);
		
		Person newPersonA = personA.clone();
		newPersonA.seat = personB.seat;
		
		Person newPersonB = personB.clone();
		newPersonB.seat = personA.seat;
		
		people.set(a, newPersonA);
		people.set(b, newPersonB);
	}
	
	private static void crossOverWorkingPlace(List<Person> A, List<Person> B, int i) {
		Person personA = A.get(i);
		Person personB = B.get(i);
		
		A.set(i, personA);
		B.set(i, personB);
	}
	
}
