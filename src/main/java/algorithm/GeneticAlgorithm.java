package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ChromosomeComparator;
import algorithm.cuttingCondition.CuttingCondition;
import algorithm.listener.DispatcherGeneticAlgorithmListener;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.model.Pair;
import algorithm.pairing.Callback;
import algorithm.util.RandomUtils;

public class GeneticAlgorithm {

	private GeneticAlgorithmProblem problem;
	private Configuration configuration;

	private GeneticAlgorithmListener dispatcher;
	private List<GeneticAlgorithmListener> listeners;
	
	private Chromosome bestChromosome;

	public GeneticAlgorithm(GeneticAlgorithmProblem problem) {
		this.problem = problem;
		this.configuration = problem.configuration();
		this.listeners = new ArrayList<>();
		this.dispatcher = new DispatcherGeneticAlgorithmListener(listeners);

		if (configuration.seed != null) {
			RandomUtils.setSeed(configuration.seed);
		}
		
		subscribeCuttingConditions();
	}

	private void subscribeCuttingConditions() {
		for (CuttingCondition cuttingCondition : configuration.cuttingConditions) {
			addListener(cuttingCondition);
		}
	}

	public void start() {
		List<Chromosome> currentGeneration = generateRandomePopulation(configuration.N);
		int generation = 1;
		bestChromosome = getBestChromosome(currentGeneration);
		Chromosome currentBestChromosome = getBestChromosome(currentGeneration);
		dispatcher.onBestChromosomeUpdated(bestChromosome);
		dispatcher.onNewGenerationReached(generation, currentGeneration, bestChromosome);
		while (!isCutting()) {

			// seleccionar individuos
			List<Chromosome> parentsSelected = configuration.crossOverSelector.select(currentGeneration,
					configuration.k + 1);
			List<Chromosome> childs = new ArrayList<>();

			// aparejar padres para armar K hijos
			configuration.pairingAlgorithm.makePairs(parentsSelected, (int) Math.ceil(configuration.k / 2.0),
					new Callback() {
						@Override
						public void onPairAvailable(Pair<Chromosome, Chromosome> parents) {
							Pair<Chromosome, Chromosome> childsPair = configuration.crossOverAlgorithm
									.crossOver(parents);

							// muto y agrego a la lista de hijos
							configuration.mutationAlgorithm.mutate(childsPair.first);
							configuration.mutationAlgorithm.mutate(childsPair.second);

							childs.add(childsPair.first);
							childs.add(childsPair.second);
						}
					});

			// si K es impar, me deshago de un hijo
			if (childs.size() > configuration.k) {
				childs.remove(childs.size() - 1);
			}

			// generar una nueva poblacion
			currentGeneration = configuration.replaceMethod.replace(currentGeneration, childs);

			generation++;
			currentBestChromosome = getBestChromosome(currentGeneration);
			if (currentBestChromosome.fitness() > bestChromosome.fitness()) {
				bestChromosome = currentBestChromosome;
				dispatcher.onBestChromosomeUpdated(bestChromosome);
			}
			dispatcher.onNewGenerationReached(generation, currentGeneration, currentBestChromosome);
		}
		dispatcher.onGeneticAlgorithmFinished(currentBestChromosome, bestChromosome);
	}
	
	public Chromosome getBestChromosome() {
		return bestChromosome;
	}

	private Chromosome getBestChromosome(List<Chromosome> chromosomes) {
		return Collections.max(chromosomes, new ChromosomeComparator(true));
	}

	private boolean isCutting() {
		for (CuttingCondition cuttingCondition : configuration.cuttingConditions) {
			if (cuttingCondition.isCutting()) {
				System.out.println("cutting description:");
				System.out.println(cuttingCondition.description());
				return true;
			}
		}
		return false;
	}

	public void addListener(GeneticAlgorithmListener listener) {
		listeners.add(listener);
	}
	
	private List<Chromosome> generateRandomePopulation(int N) {
		List<Chromosome> chromosomes = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			Chromosome randomChromosome = problem.createRandom();
			chromosomes.add(randomChromosome);
		}
//		for (Chromosome chromosome : chromosomes) {
//			System.out.println(chromosome);
//		}
		return chromosomes;
	}
}
