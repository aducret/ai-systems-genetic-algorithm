package algorithm.mutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import model.Node;
import model.Person;
import util.RandomPopper;
import util.RandomUtils;

public class GAPAMutationAlgorithm implements MutationAlgorithm {

	@Override
	public void mutate(Chromosome chromosome, double p) {
		if (!(chromosome instanceof GAPAChromosome))
			throw new IllegalStateException("GAPAMutationAlgorithm can only be used with GAPAChromosomes!");
		
		if (p < RandomUtils.random()) { return; }
		
		GAPAChromosome gc = (GAPAChromosome) chromosome;
		Set<Node> totalSeats = new HashSet<>(gc.getTotalSeats());
		List<Node> usedSeats = new ArrayList<>();
		for (Person person: gc.getPeople()) {
			usedSeats.add(person.workingSpace);
		}
		Set<Node> remainingSeats = new HashSet<>();
		Sets.difference(totalSeats, new HashSet<>(usedSeats)).copyInto(remainingSeats);
		
		//remainingSeats are all the seats that are not being used by this chromosome
		
		if (remainingSeats.size() == 0) {
			return;
		}
		
		//choose a person randomly to mutate (i.e: assign a new seat)
		int i = RandomUtils.randomBetween(0, gc.getPeople().length - 1);
		
		//create random popper with remainingSeats and poll one. This method can easily be extended
		//to support K mutations
		RandomPopper<Node> rp = new RandomPopper<>(new ArrayList<>(remainingSeats));
		gc.getPeople()[i].workingSpace = rp.randomPop();
	}
}
