package main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.Configuration;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
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
import model.Node;
import model.Person;
import util.RandomPopper;

public class GAPAProblem implements GeneticAlgorithmProblem {

	private List<Person> employees;
	private List<Node> seats;
	private Map<Integer, Set<Integer>> restrictions;
	
	/**
	 * TODO: move parameters to a configuration file and receive file name as parameter
	 * @param employees list of employees ids
	 * @param seats should be a call to NodeUtils.leafs(root)
	 * @param restrictions look at {@link GAPAChromosome}
	 */
	public GAPAProblem(List<Person> employees, List<Node> seats, Map<Integer, Set<Integer>> restrictions) {
		if  (seats.size() < employees.size()) {
			for (Person person: employees) {
				System.out.println(person.id);
			}
			throw new IllegalArgumentException(String.format("can't have fewer seats than people. has %d seats and %d people", seats.size(), employees.size()));
		}
		this.employees = employees;
		this.seats = seats;
		this.restrictions = restrictions;
	}
	
	@Override
	public Configuration configuration() {
		
		int limit = 500;
		double goal = 1.0;
		double structureTolerance = 0.95;
		int contentTolerance = 50;
		
		return new Configuration.Builder()
				.withN(750)
				.withK(325)
//				.withSeed(2)
				.withCrossOverSelector(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.05))
				.withPairingAlgorithm(new RandomPairingAlgorithm())
				.withCrossOverAlgorithm(new GAPACrossOver())
				.withMutationAlgorithm(new GAPAMutationAlgorithm())
				.withReplaceMethod(new ReplaceMethod2(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.1)))
				.addCuttingCondition(new MaxGenerationsCuttingCondition(limit))
				.addCuttingCondition(new GoalReachedCuttingCondition(goal))
				.addCuttingCondition(new StructureCuttingCondition(structureTolerance))
				.addCuttingCondition(new ContentCuttingCondition(contentTolerance))
				.build();
	}

	@Override
	public Chromosome createRandom() {
		RandomPopper<Node> rp = new RandomPopper<>(seats);
		Person[] people = new Person[employees.size()];
		for (int i = 0; i < employees.size(); i++) {
			people[i] = employees.get(i).clone();
			people[i].workingSpace = rp.randomPop();
		}
		return new GAPAChromosome(people, restrictions, seats);
	}
}
