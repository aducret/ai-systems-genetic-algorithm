package algorithm.chromosome;

import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.gene.Gene;
import model.GraphLab;
import model.Node;
import model.Person;
import util.NodeUtils;
import util.VectorUtils;

public class GAPAChromosome2 implements Chromosome {
	
	public static String orgPath;
	public static String empPath;

	private static final double ALPHA = 0.25;

	public int[] order;
	public int[][] affinities;

	//Change to Map<String, Set<String>> to stop assuming people's position in the array.
	private Map<String, Set<String>> restrictions;

	/**
	 * Used in GAPAMutationAlgorithm
	 */

	private List<Node> totalSeats;

	private Double fitness;
	private Double tmiFitness;

	/**
	 * 
	 * @param people
	 *            are the people from the company
	 * @param restrictions
	 *            is an Nx2 matrix containing N restrictions where each restriction
	 *            consists of 2 indexes: i1 & i2 meaning "the person at index i1
	 *            must be close to the person at index i2"
	 */
	public GAPAChromosome2(int[] order, int[][] affinities, Map<String, Set<String>> restrictions,
			List<Node> totalSeats) {
		this.order = order;
		this.affinities = affinities;
		this.restrictions = restrictions;
		this.totalSeats = totalSeats;
	}
	
	public Person[] getPeople() {
		return GraphLab.seatPeopleWithOrderAndAffinities(orgPath, empPath, order, affinities);
	}

	@Override
	public double fitness() {
		if (fitness != null) return fitness;
		Person[] people = getPeople();
		fitness = ALPHA * distanceFitness(people) + (1 - ALPHA) * tmiFitness(people);
		return fitness;
	}

	public double tmiFitness(Person[] people) {
		if (tmiFitness != null)
			return tmiFitness;
		double acum = 0;
		for (Person person : people) {
			acum += tmiFitness(person);
		}

		tmiFitness = acum / people.length;
		return tmiFitness;
	}

	public double tmiFitness(Person person) {
		double tmi = person.tmi();
		Node node = person.getWorkingSpace().parent;
		double acum = 1;
		while (node != null) {
			acum *= (Math.min(tmi, node.capacity) / tmi);
			node = node.parent;
		}
		return acum;
	}

	private double distanceFitness(Person[] people) {
		double acum = 0;
		for (int i = 0; i < people.length; i++) {
			acum += distanceFitness(people, i);
		}

		return acum / people.length;
	}

	private double distanceFitness(Person[] people, int i) {
		if (restrictions == null || !restrictions.containsKey(people[i].id))
			return 1.0;

		Set<String> A = restrictions.get(people[i].id);
		double sum = A.stream().map(personId -> {
			int D = NodeUtils.distanceBetween(people[i].getWorkingSpace(), personWithId(people, personId).getWorkingSpace());
			return Math.log10(D) / Math.log10(2);
		}).mapToDouble(f -> f.doubleValue()).sum();
		double df = A.size() / sum;

		return df;
	}
	
	private Person personWithId(Person[] people, String id) {
		for (int i = 0; i < people.length; i++) {
			if (people[i].id.equals(id)) return people[i];
		}
		return null;
	}

	@Override
	public Chromosome cloneChromosome() {
		return new GAPAChromosome2(order.clone(), VectorUtils.deepCopy(affinities), restrictions, totalSeats);
	}

	public List<Node> getTotalSeats() {
		return totalSeats;
	}

	@Override
	public int geneAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Gene> genes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gene geneAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGene(int index, Gene gene) {
		// TODO Auto-generated method stub

	}
}
