package model.gene;

import algorithm.gene.Gene;
import structures.Person;

public class GAPAGene implements Gene {

	public final Person person;
	
	public GAPAGene(Person person) {
		this.person = person;
	}
}
