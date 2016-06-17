package algorithm.replace;

import java.util.List;

import algorithm.model.Chromosome;

public class ReplaceMethod1 implements ReplaceAlgorithm {

	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> selectedParents,
			List<Chromosome> childs) {
		if (currentGeneration.size() != selectedParents.size()) {
			System.err.println("should have selected N parents!");
			return null;
		}
		if (currentGeneration.size() != childs.size()) {
			System.err.println("replace method 1 needs N childs!");
			return null;
		}
		return childs;
	}

}
