package gapa;

import java.util.List;

import algorithm.model.Pair;
import structures.Node;
import structures.Person;

public interface GapaService {
	List<Person> getAllPeople();
	List<Node> getSeats();
	List<Pair<Integer, Integer>> getRestrictions();
	List<Person> getPeopleInProject(String projectName);
	List<String> getProjectsOf(String personName);
}
