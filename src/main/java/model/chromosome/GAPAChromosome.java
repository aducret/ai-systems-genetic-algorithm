package model.chromosome;

import java.util.ArrayList;
import java.util.List;
import model.Person;
import model.gene.GAPAGene;
import model.gene.Gene;
import structures.Node;
import structures.Pair;
import util.NodeUtils;
import util.WorkingPlaceParser;

public class GAPAChromosome extends ListChromosome {

	public List<Person> people;
	public List<Node> seats;
	public List<Pair<Integer, Integer>> restrictions;
	
	public GAPAChromosome(List<Person> people, List<Pair<Integer, Integer>> restrictions, List<Node> totalSeats) {
		super(createGenes(people));
		this.people = people;
		this.restrictions = restrictions;
		this.seats = totalSeats;
	}

	@Override
	public double fitness() {
		
		if (restrictions == null) return 1.0;
		
		int acum = 0;
		for (int i = 0; i < restrictions.size(); i++) {
			int index1 = restrictions.get(i).first;
			int index2 = restrictions.get(i).second;
			Person p1 = people.get(index1);
			Person p2 = people.get(index2);
			acum += NodeUtils.distanceBetween(p1.seat, p2.seat);
		}
		
		double factorB = 0; 
		for (Person person: people) {
			// Por ahora deberia dar un solo projecto
			for (String project: person.projects) {
				// Cantidad de lugares de trabajo de la mesa en la cual esta la persona
				int space = person.seat.parent.childs.size();
				int employeesOfSameProject = NodeUtils.findPeopleInSamePlace(person, people).size();
				// Chequeo si el equipo entra en la mesa justo (0.25) si entra sobrado (0.1) o si no entra (0)
				double mult = 0;
				int projectSize = NodeUtils.getProjectSize(project, people);
				if (space == projectSize) {
					mult = 0.25f;
				} else if (space > projectSize) {
					mult = 0.1f;
				}
				factorB += mult * employeesOfSameProject;
			}
		}
		
		return (1.0 / acum) + factorB;
	}

	public List<Person> clonePeople() {
		List<Person> clone = new ArrayList<>();
		for (Person person: people) {
			Person personClone = person.clone();
			personClone.seat = person.seat;
			clone.add(personClone);
		}
		
		return clone;
	}
	
	private static List<Gene> createGenes(List<Person> people) {
		List<Gene> genes = new ArrayList<>();
		for (Person person: people) {
			genes.add(new GAPAGene(person));
		}
		return genes;
	}
	
	@Override
	public Chromosome cloneChromosome() {
		List<Person> clone = new ArrayList<>();
		for (int i = 0; i < people.size(); i++) {
			clone.add(people.get(i));
		}
		return new GAPAChromosome(clone, restrictions, seats);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		String separator = "";
		for (Person person: people) {
			sb.append(separator);
			sb.append("{");
			
			sb.append(person.id + " seats in " + WorkingPlaceParser.fullId(person.seat));
			
			sb.append("}");
			separator = ", ";
		}
		sb.append("}");
		return sb.toString();
	}
	
}
