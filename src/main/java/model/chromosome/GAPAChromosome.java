package model.chromosome;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ListChromosome;
import algorithm.gene.Gene;
import model.gene.GAPAGene;
import structures.Node;
import structures.NodeUtils;
import structures.Person;
import util.BitUtils;

public class GAPAChromosome extends ListChromosome {

	private Person[] people;
	private int[][] restrictions;
	
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
	public GAPAChromosome(Person[] people, int[][] restrictions, List<Node> totalSeats) {
		super(createGenes(people));
		this.people = people;
		this.restrictions = restrictions;
		this.totalSeats = totalSeats;
	}

	@Override
	public double fitness() {
		
		if (restrictions == null) return 1.0;
		
		int acum = 0;
		
		for (int i = 0; i < restrictions.length; i++) {
			int index1 = restrictions[i][0];
			int index2 = restrictions[i][1];
			Person p1 = people[index1];
			Person p2 = people[index2];
			acum += NodeUtils.distanceBetween(p1.workingSpace, p2.workingSpace);
		}
		
		return 1.0/BitUtils.biggestBitIndex(acum);
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
			
			sb.append(person.id + " seats in " + person.workingSpace.id);
			
			sb.append("}");
			separator = ", ";
		}
		sb.append("}");
		return sb.toString();
	}
}
