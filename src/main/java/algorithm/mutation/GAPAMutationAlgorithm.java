package algorithm.mutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import algorithm.chromosome.Chromosome;
import algorithm.util.RandomPopper;
import algorithm.util.RandomUtils;
import model.chromosome.GAPAChromosome;
import structures.Node;
import structures.Person;

public class GAPAMutationAlgorithm implements MutationAlgorithm {

	@Override
	public void mutate(Chromosome chromosome) {
		if (!(chromosome instanceof GAPAChromosome))
			throw new IllegalStateException("GAPAMutationAlgorithm can only be used with GAPAChromosomes!");
		GAPAChromosome gc = (GAPAChromosome) chromosome;
		Set<Node> totalSeats = new HashSet<>(gc.getTotalSeats());
		List<Node> usedSeats = new ArrayList<>();
		for (Person person: gc.getPeople()) {
			usedSeats.add(person.workingSpace);
		}
		Set<Node> remainingSeats = new HashSet<>();
		Sets.difference(totalSeats, new HashSet<>(usedSeats)).copyInto(remainingSeats);
		
		//remainingSeats are all the seats that are not being used by this chromosome
		
		//choose a person randomly to mutate (i.e: assign a new seat)
		int i = RandomUtils.randomBetween(0, gc.getPeople().length - 1);
		
		//add his current seat to the seat, because it should be possible to receive the same seat again
		remainingSeats.add(gc.getPeople()[i].workingSpace);
		
		//create random popper with remainingSeats and poll one. This method can easily be extended
		//to support K mutations
		RandomPopper<Node> rp = new RandomPopper<>(new ArrayList<>(remainingSeats));
		gc.getPeople()[i].workingSpace = rp.randomPop();
	}
}
