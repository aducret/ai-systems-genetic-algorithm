package algorithm.cuttingCondition;

import algorithm.listener.GeneticAlgorithmListener;

public interface CuttingCondition extends GeneticAlgorithmListener {
	
	boolean isCutting();
	
}
