package algorithm.mutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import model.Person;
import model.chromosome.Chromosome;
import model.chromosome.GAPAChromosome;
import structures.Node;
import util.RandomPopper;
import util.RandomUtils;

public class GAPAMutationAlgorithm implements MutationAlgorithm {

	@Override
	public void mutate(Chromosome chromosome) {
		if (!(chromosome instanceof GAPAChromosome))
			throw new IllegalStateException("GAPAMutationAlgorithm can only be used with GAPAChromosomes!");
		GAPAChromosome gc = (GAPAChromosome) chromosome;
		Set<Node> totalSeats = new HashSet<>(gc.seats);
		List<Node> usedSeats = new ArrayList<>();
		for (Person person: gc.people) {
			usedSeats.add(person.seat);
		}
		Set<Node> remainingSeats = new HashSet<>();
		Sets.difference(totalSeats, new HashSet<>(usedSeats)).copyInto(remainingSeats);
		
		// RemainingSeats are all the seats that are not being used by this chromosome
		
		// Choose a person randomly to mutate (i.e: assign a new seat)
		int i = RandomUtils.randomBetween(0, gc.people.size() - 1);
		
		// Add his current seat to the seat, because it should be possible to receive the same seat again
		remainingSeats.add(gc.people.get(i).seat);
		
		// Create random popper with remainingSeats and poll one. This method can easily be extended
		// to support K mutations
		RandomPopper<Node> rp = new RandomPopper<>(new ArrayList<>(remainingSeats));
		Person personI = gc.people.get(i);
		Person newPerson = new Person(personI.id, personI.projects);
		newPerson.seat = rp.randomPop();
		gc.people.set(i, newPerson);
	}
	
}
