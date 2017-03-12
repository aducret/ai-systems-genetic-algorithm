package model.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ListChromosome;
import algorithm.gene.Gene;
import algorithm.model.Pair;
import model.gene.GAPAGene;
import structures.Node;
import structures.NodeUtils;
import structures.Person;
import util.WorkingPlaceParser;

public class GAPAChromosome extends ListChromosome {

	private Person[] people;
	private Map<Integer, Set<Integer>> restrictions;
	
	/**
	 * Used in GAPAMutationAlgorithm
	 */
	private List<Node> totalSeats;
	
	/**
	 * 
	 * @param people are the people from the company
	 * @param restrictions is an Nx2 matrix containing N restrictions where each
	 * restriction consists of 2 indexes: i1 & i2 meaning "the person at index i1 must be
	 * close to the person at index i2"
	 */
	public GAPAChromosome(Person[] people, Map<Integer, Set<Integer>> restrictions, List<Node> totalSeats) {
		super(createGenes(people));
		this.people = people;
		this.restrictions = restrictions;
		this.totalSeats = totalSeats;
	}

	@Override
	public double fitness() {
		return 0.5*distanceFitness() + 0.5*tmiFitness();
	}
	
	private double tmiFitness() {
		double acum = 0;
		for (Person person: people) {
			acum += tmiFitness(person);
		}
		
		return acum/people.length;
	}
	
	private double tmiFitness(Person person) {
		double tmi = person.tmi();
		Node node = person.workingSpace.parent;
		double acum = 1;
		while(node != null) {
			acum *= (Math.min(tmi, node.capacity)/tmi);
			node = node.parent;
		}
		return acum;
	}
	
	private double distanceFitness() {
		double acum = 0;
		for (int i = 0; i < people.length; i++) {
			acum += distanceFitness(i);
		}
		
		return acum/people.length;
	}
	
	private double distanceFitness(int i) {
		if (restrictions == null) return 1.0;
		
		Set<Integer> A = restrictions.get(i);
		double sum = A.stream().map(j -> {
			int D = NodeUtils.distanceBetween(people[i].workingSpace, people[j].workingSpace);
			return Math.log10(D)/Math.log10(2);
		}).mapToDouble(f -> f.doubleValue()).sum();
		double df = A.size()/sum;
		
		return df;
	}

	@Override
	public Chromosome cloneChromosome() {
		Person[] clone = new Person[people.length];
		for (int i = 0; i < clone.length; i++) {
			clone[i] = people[i].clone();
		}
		return new GAPAChromosome(clone, restrictions, totalSeats);
	}
	
	private static List<Gene> createGenes(Person[] people) {
		List<Gene> genes = new ArrayList<>();
		for (Person person: people) {
			genes.add(new GAPAGene(person));
		}
		return genes;
	}
	
	public Person[] getPeople() {
		return people;
	}
	
	public List<Node> getTotalSeats() {
		return totalSeats;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		String separator = "";
		for (Person person: people) {
			sb.append(separator);
			sb.append("{");
			
			sb.append(person.id + " seats in " + WorkingPlaceParser.fullId(person.workingSpace));
			
			sb.append("}");
			separator = ", ";
		}
		sb.append("}");
		return sb.toString();
	}
}
