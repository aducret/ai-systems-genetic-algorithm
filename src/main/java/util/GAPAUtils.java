package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import algorithm.chromosome.GAPAChromosome2;
import model.Node;
import model.Person;
import parser.WorkingPlaceParser;

public class GAPAUtils {
	public static void writeSolution(String path, Node workingPlace, Map<String, HashSet<String>> people, GAPAChromosome2 chromosome) throws FileNotFoundException {
	
		PrintWriter writer = new PrintWriter(path);
		
		HashMap<String, Person> map = new HashMap<>();
		for (Person person: chromosome.getPeople()) {
			map.put(WorkingPlaceParser.fullId(person.getWorkingSpace()), person);
		}
		
		writeSolutionRec(writer, workingPlace, people, map, 0);
		
		writer.close();
	}
	
	private static void writeSolutionRec(PrintWriter writer, Node node, Map<String, HashSet<String>> people, HashMap<String, Person> map, int level) {
		String spaces = new String(new char[4*level]).replace("\0", " ");
		if (node == null) throw new IllegalStateException("node can't be null");
		if (node.childs.isEmpty()) {
//			this is a leaf
			Person person = map.get(WorkingPlaceParser.fullId(node));
			if (person == null) {
				writer.println(String.format("%s%s (EMPTY)", spaces, node.id));
				return;
			}
			writer.println(String.format("%s%s (%s) %s", spaces, node.id, person.id, people.get(person.id).toString()));
			return;
		}
		
		writer.println(String.format("%s%s", spaces, node.id));
		for (Node child: node.childs) {
			writeSolutionRec(writer, child, people, map, level + 1);
		}
	}
}
