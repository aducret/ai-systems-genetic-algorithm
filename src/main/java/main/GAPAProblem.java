package main;

import algorithm.Configuration;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
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

public class GAPAProblem implements GeneticAlgorithmProblem {

	@Override
	public Configuration configuration() {
		
		int limit = 500;
		int goal = 80;
		double structureTolerance = 0.95;
		int contentTolerance = 50;
		
		return new Configuration.Builder()
				.withN(10)
				.withK(4)
				.withSeed(1)
				.withCrossOverSelector(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.15))
				.withPairingAlgorithm(new RandomPairingAlgorithm())
				.withCrossOverAlgorithm(new NoCrossOver())
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
//		TODO: create random configuration
		return null;
	}
}
