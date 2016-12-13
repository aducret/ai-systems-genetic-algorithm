package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Person;
import structures.Node;

public class NodeUtils {
	
	/**
	 * 
	 * @param node1 hoja del arbol
	 * @param node2 hoja del arbol
	 * @return distancia entre las hojas del arbol
	 */
	public static int distanceBetween(Node node1, Node node2) {
		if (node1 == node2) return 0;
		if (node1.height > node2.height) return 1 + distanceBetween(node1.parent, node2);
		if (node2.height > node1.height) return 1 + distanceBetween(node1, node2.parent);
		return 2 + distanceBetween(node1.parent, node2.parent);
	}
	
	public static List<Node> leafs(Node root) {
		List<Node> leafs = new LinkedList<>();
		leafs(root, leafs);
		return leafs;
	}
	
	// Para la persona dada busca sus compañeros de proyecto que estan en su mesa.
	// Se incluye a si mismo.
	public static List<Person> findPeopleInSamePlace(Person target, List<Person> people) {
		List<Node> seats = target.seat.parent.childs;
		List<Person> ans = new ArrayList<>();
		ans.add(target);
		
		for (Person person: people) {
			if (seats.contains(person.seat)) {
				ans.add(person);
			}
		}
		return ans;
	}
	
	public static Person findPerson(Node seat, List<Person> people) {
		for (Person person: people) {
			if (person.seat.equals(seat)) {
				return person;
			}
		}
		
		return null;
	}
	
	// Retorna la cantidad de personas que perteneces al proyecto dado
	public static int getProjectSize(String targetProject, List<Person> people) {
		int acum = 0;
		for (Person person: people) {
			for (String project: person.projects) {
				if (project.equals(targetProject)) {
					acum++;
					break;
				}
			}
		}
		return acum;
	}
	
	private static void leafs(Node node, List<Node> leafs) {
		if (node.isLeaf()) {
			leafs.add(node);
			return;
		}
		for (Node child: node.childs) {
			leafs(child, leafs);
		}
	}
	
}
