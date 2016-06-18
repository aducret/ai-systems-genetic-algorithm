package character;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmProblem;
import algorithm.crossover.UniformCrossOver;
import algorithm.cuttingCondition.ContentCuttingCondition;
import algorithm.cuttingCondition.GoalReachedCuttingCondition;
import algorithm.cuttingCondition.MaxGenerationsCuttingCondition;
import algorithm.cuttingCondition.StructureCuttingCondition;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.mutation.ClassicMutation;
import algorithm.pairing.AlphaPairingAlgorithm;
import algorithm.replace.ReplaceMethod1;
import algorithm.selector.CompoundSelector;
import algorithm.selector.EliteSelector;
import algorithm.selector.RouletteSelector;

public class Main {

	public static void main(String[] args) {
		
		String weaponsPath = "doc/data/armas.tsv";
		String bootsPath = "doc/data/botas.tsv";
		String helmetsPath = "doc/data/cascos.tsv";
		String glovesPath = "doc/data/guantes.tsv";
		String shirtsPath = "doc/data/pecheras.tsv";
		String heightsPath = "doc/data/heights.txt";
		
		GeneticAlgorithmProblem problem = new CharacterGeneticAlgorithmProblem();

		GeneticAlgorithm algorithm = new GeneticAlgorithm.Builder()
				.withProblem(problem)
				.withCuttingCondition(new MaxGenerationsCuttingCondition(500))
				.withCuttingCondition(new GoalReachedCuttingCondition(80))
				.withCuttingCondition(new StructureCuttingCondition(0.8))
				.withCuttingCondition(new ContentCuttingCondition(15))
				.withMutationAlgorithm(new ClassicMutation(0.01))
				.withCrossOverSelector(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.15))
				.withCrossOverAlgorithm(new UniformCrossOver(0.75, 0.4))
				.withReplaceAlgorithm(new ReplaceMethod1())
				.withPairingAlgorithm(new AlphaPairingAlgorithm())
				.build();

		// Setear listeners
		GeneticAlgorithmListener loggerListener = new LoggerGeneticAlgorithmListener();
		algorithm.addListener(loggerListener);

		algorithm.start();
	}

}
