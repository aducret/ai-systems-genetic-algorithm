package main;

import java.util.List;

import algorithm.Configuration;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.crossover.GAPACrossOver;
import algorithm.crossover.NoCrossOver;
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
import algorithm.util.RandomPopper;
import model.chromosome.GAPAChromosome;
import structures.Node;
import structures.Person;

public class GAPAProblem implements GeneticAlgorithmProblem {

	private List<String> employees;
	private List<Node> seats;
	private int[][] restrictions;
	
	/**
	 * TODO: move parameters to a configuration file and receive file name as parameter
	 * @param employees list of employees ids
	 * @param seats should be a call to NodeUtils.leafs(root)
	 * @param restrictions look at {@link GAPAChromosome}
	 */
	public GAPAProblem(List<String> employees, List<Node> seats, int[][] restrictions) {
		if  (seats.size() < employees.size())
			throw new IllegalArgumentException("can't have fewer seats than people");
		this.employees = employees;
		this.seats = seats;
		this.restrictions = restrictions;
	}
	
	@Override
	public Configuration configuration() {
		
		int limit = 500;
		int goal = 80;
		double structureTolerance = 0.95;
		int contentTolerance = 50;
		
		return new Configuration.Builder()
				.withN(10)
				.withK(4)
				.withSeed(2)
				.withCrossOverSelector(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.15))
				.withPairingAlgorithm(new RandomPairingAlgorithm())
				.withCrossOverAlgorithm(new GAPACrossOver())
				.withMutationAlgorithm(new GAPAMutationAlgorithm())
				.withReplaceMethod(new ReplaceMethod2(new RouletteSelector()))
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
			people[i] = new Person(employees.get(i));
			people[i].workingSpace = rp.randomPop();
		}
		return new GAPAChromosome(people, restrictions, seats);
	}
}
