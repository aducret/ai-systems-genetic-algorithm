package algorithm;

import algorithm.listener.GeneticAlgorithmListener;

public abstract class CuttingCondition implements GeneticAlgorithmListener {
	
	public abstract boolean isCutting();
	
}
