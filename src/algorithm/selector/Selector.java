package algorithm.selector;

import java.util.List;

import javafx.util.Pair;
import algorithm.model.Chromosome;

public interface Selector {
	
	List<Chromosome> select(List<Chromosome> chromosomes, int k);
}
