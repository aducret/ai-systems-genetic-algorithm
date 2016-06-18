package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Pair;
import algorithm.crossover.CrossOverAlgorithm;
import algorithm.cuttingCondition.CuttingCondition;
import algorithm.listener.DispatcherGeneticAlgorithmListener;
import algorithm.listener.GeneticAlgorithmListener;
import algorithm.listener.GeneticAlgorithmLogAdapter;
import algorithm.listener.GeneticAlgorithmLogListener;
import algorithm.model.Chromosome;
import algorithm.mutation.MutationAlgorithm;
import algorithm.pairing.Callback;
import algorithm.pairing.PairingAlgorithm;
import algorithm.replace.ReplaceAlgorithm;
import algorithm.selector.EliteSelector;
import algorithm.selector.Selector;

public class GeneticAlgorithm {

	private GeneticAlgorithmProblem problem;
	private List<CuttingCondition> cuttingConditions;
	
	private PairingAlgorithm pairingAlgorithm;
	private CrossOverAlgorithm crossOverAlgorithm;
	private MutationAlgorithm mutationAlgorithm;
	private ReplaceAlgorithm replaceAlgorithm;
	
	private Selector crossOverSelector;

	private GeneticAlgorithmListener dispatcher;
	private List<GeneticAlgorithmListener> listeners;
	private GeneticAlgorithmLogListener logListener;

	private GeneticAlgorithm() {
		this.listeners = new ArrayList<>();
		this.cuttingConditions = new ArrayList<>();
		this.dispatcher = new DispatcherGeneticAlgorithmListener(listeners);
		this.crossOverSelector = new EliteSelector();
		this.logListener = new GeneticAlgorithmLogAdapter();
	}

	private void subscribeCuttingConditions() {
		for (CuttingCondition cuttingCondition : cuttingConditions) {
			addListener(cuttingCondition);
		}
	}

	public void start() {
		List<Chromosome> currentGeneration = problem.initialPopulation();
		int generation = 1;
		Chromosome bestChromosome = getBestChromosome(currentGeneration);
		dispatcher.onBestChromosomeUpdated(bestChromosome);
		dispatcher.onNewGenerationReached(generation, currentGeneration, bestChromosome);
		while (!isCutting()) {
			
			// seleccionar individuos
			List<Chromosome> parentsSelected = crossOverSelector.select(
					currentGeneration, problem.getK());
			List<Chromosome> childs = new ArrayList<>();

			// aparejar padres para armar K hijos
			pairingAlgorithm.makePairs(parentsSelected, (int) Math.ceil(problem.getK()/2.0), new Callback() {
				@Override
				public void onPairAvailable(Pair<Chromosome, Chromosome> parents) {
					Pair<Chromosome, Chromosome> childsPair = crossOverAlgorithm.crossOver(parents);
					
					//muto y agrego a la lista de hijos
					mutationAlgorithm.mutate(childsPair.first, problem.geneMap());
					mutationAlgorithm.mutate(childsPair.second, problem.geneMap());
					
					childs.add(childsPair.first);
					childs.add(childsPair.second);
				}
			});
			
			// si K es impar, me deshago de un hijo
			if (childs.size() > problem.getK()) {
				childs.remove(childs.size() - 1);
			}
			
			// generar una nueva poblacion
			currentGeneration = replaceAlgorithm.replace(currentGeneration, childs);
			
			generation++;
			
			Chromosome currentBestChromosome = getBestChromosome(currentGeneration);
			if (currentBestChromosome.fitness() > bestChromosome.fitness()) {
				bestChromosome = currentBestChromosome;
				dispatcher.onBestChromosomeUpdated(bestChromosome);
			}
			dispatcher.onNewGenerationReached(generation, currentGeneration, currentBestChromosome);
		}
	}
	
	private Chromosome getBestChromosome(List<Chromosome> chromosomes) {
		return Collections.max(chromosomes, new ChromosomeComparator(true));
	}

	private boolean isCutting() {
		for (CuttingCondition cuttingCondition : cuttingConditions) {
			if (cuttingCondition.isCutting())
				return true;
		}
		return false;
	}

	public void addListener(GeneticAlgorithmListener listener) {
		listeners.add(listener);
	}

	public static class Builder {
		private GeneticAlgorithm geneticAlgorithm;

		public Builder() {
			geneticAlgorithm = new GeneticAlgorithm();
		}

		public Builder withProblem(GeneticAlgorithmProblem problem) {
			geneticAlgorithm.problem = problem;
			return this;
		}

		public Builder withCuttingCondition(CuttingCondition cuttingCondition) {
			geneticAlgorithm.cuttingConditions.add(cuttingCondition);
			return this;
		}

		public Builder withCrossOverSelector(Selector selector) {
			geneticAlgorithm.crossOverSelector = selector;
			return this;
		}

		public Builder withPairingAlgorithm(PairingAlgorithm pairingAlgorithm) {
			geneticAlgorithm.pairingAlgorithm = pairingAlgorithm;
			return this;
		}

		public Builder withCrossOverAlgorithm(
				CrossOverAlgorithm crossOverAlgorithm) {
			geneticAlgorithm.crossOverAlgorithm = crossOverAlgorithm;
			return this;
		}

		public Builder withMutationAlgorithm(MutationAlgorithm mutationAlgorithm) {
			geneticAlgorithm.mutationAlgorithm = mutationAlgorithm;
			return this;
		}
		
		public Builder withReplaceAlgorithm(ReplaceAlgorithm replaceAlgorithm) {
			geneticAlgorithm.replaceAlgorithm = replaceAlgorithm;
			return this;
		}
		
		public Builder withLogListener(GeneticAlgorithmLogListener logListener) {
			geneticAlgorithm.logListener = logListener;
			return this;
		}

		public GeneticAlgorithm build() {
			geneticAlgorithm.subscribeCuttingConditions();
			return geneticAlgorithm;
		}
	}

}
