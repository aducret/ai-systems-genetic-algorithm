package algorithm;

import java.util.ArrayList;
import java.util.List;

import algorithm.crossover.CrossOverAlgorithm;
import algorithm.cuttingCondition.CuttingCondition;
import algorithm.mutation.MutationAlgorithm;
import algorithm.pairing.PairingAlgorithm;
import algorithm.replace.ReplaceMethod;
import algorithm.selector.Selector;

public class Configuration {
	public int N;
	public int k;
	public Long seed = null;
	public List<CuttingCondition> cuttingConditions;
	public Selector crossOverSelector;
	public PairingAlgorithm pairingAlgorithm;
	public CrossOverAlgorithm crossOverAlgorithm;
	public MutationAlgorithm mutationAlgorithm;
	public ReplaceMethod replaceMethod;
	public double pm;
	
	public Configuration() {
		cuttingConditions = new ArrayList<>();
		initializeDefaults();
	}
	
	private void initializeDefaults() {
		
	}

	public static class Builder {
		Configuration configuration;

		public Builder() {
			configuration = new Configuration();
		}
		
		public Builder withN(int N) {
			configuration.N = N;
			return this;
		}
		
		public Builder withK(int k) {
			configuration.k = k;
			return this;
		}
		
		public Builder withSeed(long seed) {
			configuration.seed = seed;
			return this;
		}
		
		public Builder addCuttingCondition(CuttingCondition cuttingCondition) {
			configuration.cuttingConditions.add(cuttingCondition);
			return this;
		}
		
		public Builder withCrossOverSelector(Selector selector) {
			configuration.crossOverSelector = selector;
			return this;
		}
		
		public Builder withCrossOverAlgorithm(CrossOverAlgorithm crossOverAlgorithm) {
			configuration.crossOverAlgorithm = crossOverAlgorithm;
			return this;
		}
		
		public Builder withPairingAlgorithm(PairingAlgorithm pairingAlgorithm) {
			configuration.pairingAlgorithm = pairingAlgorithm;
			return this;
		}
		
		public Builder withMutationAlgorithm(MutationAlgorithm mutationAlgorithm, double pm) {
			configuration.mutationAlgorithm = mutationAlgorithm;
			configuration.pm = pm;
			return this;
		}
		
		public Builder withReplaceMethod(ReplaceMethod replaceMethod) {
			configuration.replaceMethod = replaceMethod;
			return this;
		}	
		
		public Configuration build() {
			return configuration;
		}
	}
}
