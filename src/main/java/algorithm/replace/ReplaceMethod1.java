package algorithm.replace;

import java.util.List;

import algorithm.chromosome.Chromosome;

public class ReplaceMethod1 implements ReplaceMethod {

	@Override
	public List<Chromosome> replace(List<Chromosome> currentGeneration, List<Chromosome> childs) {
		if (currentGeneration.size() != childs.size())
			throw new IllegalArgumentException("should make N childs");
		return childs;
	}

}
