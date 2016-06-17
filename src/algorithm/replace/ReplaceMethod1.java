package algorithm.replace;

import java.util.List;

import algorithm.model.Chromosome;

public class ReplaceMethod1 implements ReplaceAlgorithm {

	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs) {
		if (currentGeneration.size() != childs.size())
			throw new IllegalArgumentException("should make N childs");
		return childs;
	}

}
