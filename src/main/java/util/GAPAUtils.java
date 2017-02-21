package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import model.chromosome.GAPAChromosome;
import structures.Node;
import structures.Person;

public class GAPAUtils {
	public static void writeSolution(String path, Node workingPlace, GAPAChromosome chromosome) throws FileNotFoundException {
	
		PrintWriter writer = new PrintWriter(path);
		
		HashMap<String, String> map = new HashMap<>();
		for (Person person: chromosome.getPeople()) {
			map.put(WorkingPlaceParser.fullId(person.workingSpace), person.id);
		}
		
		writeSolutionRec(writer, workingPlace, map, 0);
		
		writer.close();
	}
	
	private static void writeSolutionRec(PrintWriter writer, Node node, HashMap<String, String> map, int level) {
		String spaces = new String(new char[4*level]).replace("\0", " ");
		
		if (node.childs.isEmpty()) {
//			this is a leaf
			writer.println(String.format("%s%s (%s)", spaces, node.id, map.get(WorkingPlaceParser.fullId(node))));
			return;
		}
		
		writer.println(String.format("%s%s", spaces, node.id));
		for (Node child: node.childs) {
			writeSolutionRec(writer, child, map, level + 1);
		}
	}
}