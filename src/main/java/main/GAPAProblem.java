package main;

import java.util.ArrayList;
import java.util.List;

import algorithm.Configuration;
import algorithm.GeneticAlgorithmProblem;
import algorithm.crossover.GAPACrossOver;
import algorithm.cuttingCondition.ContentCuttingCondition;
import algorithm.cuttingCondition.GoalReachedCuttingCondition;
import algorithm.cuttingCondition.MaxGenerationsCuttingCondition;
import algorithm.cuttingCondition.StructureCuttingCondition;
import algorithm.mutation.GAPAMutationAlgorithm;
import algorithm.pairing.RandomPairingAlgorithm;
import algorithm.replace.ReplaceMethod2;
import algorithm.selector.CompoundSelector;
import algorithm.selector.EliteSelector;
import algorithm.selector.RouletteSelector;
import model.Person;
import model.chromosome.Chromosome;
import model.chromosome.GAPAChromosome;
import structures.Node;
import structures.Pair;
import util.RandomPopper;

public class GAPAProblem implements GeneticAlgorithmProblem {

	private List<Person> people;
	private List<Node> seats;
	private List<Pair<Integer, Integer>> restrictions;
	
	/**
	 * TODO: move parameters to a configuration file and receive file name as parameter
	 * @param people list of employees ids
	 * @param seats should be a call to NodeUtils.leafs(root)
	 * @param restrictions look at {@link GAPAChromosome}
	 */
	public GAPAProblem(List<Person> people, List<Node> seats, List<Pair<Integer, Integer>> restrictions) {
		if  (seats.size() < people.size())
			throw new IllegalArgumentException("can't have fewer seats than people");
		this.people = people;
		this.seats = seats;
		this.restrictions = restrictions;
	}
	
	@Override
	public Configuration configuration() {
		
		int limit = 500;
		int goal = 1000000000;
		double structureTolerance = 0.95;
		int contentTolerance = 50;
		
		return new Configuration.Builder()
				.withN(20)
				.withK(5)
				.withSeed(2)
				.withCrossOverSelector(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.05))
				.withPairingAlgorithm(new RandomPairingAlgorithm())
				.withCrossOverAlgorithm(new GAPACrossOver())
				.withMutationAlgorithm(new GAPAMutationAlgorithm())
				.withReplaceMethod(new ReplaceMethod2(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.05)))
				.addCuttingCondition(new MaxGenerationsCuttingCondition(limit))
				.addCuttingCondition(new GoalReachedCuttingCondition(goal))
				.addCuttingCondition(new StructureCuttingCondition(structureTolerance))
				.addCuttingCondition(new ContentCuttingCondition(contentTolerance))
				.build();
	}

	@Override
	public Chromosome createRandom() {
		RandomPopper<Node> seatsRandomPopper = new RandomPopper<>(seats);
		List<Person> chromosomePeople = new ArrayList<>();
		
		for (int i = 0; i < people.size(); i++) {
			Person person = people.get(i).clone();
			Node seat = seatsRandomPopper.randomPop();
			person.seat = seat;
			chromosomePeople.add(person);
		}
		
		return new GAPAChromosome(chromosomePeople, restrictions, seats);
	}
}
