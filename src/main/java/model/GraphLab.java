package model;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import algorithm.chromosome.GAPAChromosome;
import parser.OrganizationParser;
import parser.WorkingPlaceParser;
import util.GraphUtils;
import util.NodeUtils;

public class GraphLab {

	public static void main(String[] args) throws FileNotFoundException {

		final String orgPath = "./doc/gapa/wolox";
		final String empPath = "./doc/gapa/employees";

		Node root = WorkingPlaceParser.generate(orgPath);
		Node rootCopy = WorkingPlaceParser.generate(orgPath);
		
		OrganizationParser op = new OrganizationParser();
		Map<String, HashSet<String>> projects = op.parseProjects(empPath);
		Map<String, HashSet<String>> copyProjects = op.parseProjects(empPath);
		Map<String, HashSet<String>> people = buildPeople(projects);
		Map<Integer, String> projectMappings = buildProjectMappings(projects);
		
		int[] values = buildValues(projects);
		int[][] weights = buildWeights(projects);

		 int[] order = pargmaxOrder(weights, values);
		 System.out.println(Arrays.toString(order));
		 Person[] personArray = new Person[people.size()];
		 int k = 0;
		 for (int projectId: order) {
			 String project = projectMappings.get(projectId);
			 HashSet<String> peopleToSit = projects.get(project);
			 Node bestRoot = bestRoot(peopleToSit.size(), root, null);
			 if (bestRoot == null) throw new IllegalStateException("no space for these people!");
			 LinkedList<Node> leafs = NodeUtils.leafs(bestRoot);
			 for (String personToSit: peopleToSit) {
				 Node leaf = leafs.removeFirst();
				 personArray[k] = new Person(personToSit);
				 personArray[k].projects2 = copyProjects;
				 personArray[k++].setWorkingSpace(leaf);
//				 leaf.used = true;
				 leaf.use();
			 }
			 removeProject(project, projects, people);
		 }
		 
		 GraphUtils.graph(rootCopy, new GAPAChromosome(personArray, null, null));
	}
	
	public static Node bestRoot(int size, Node node, Node best) {
		if (node.capacity < size) return best;
		
		Node currentBest = node;
		for (Node child: node.childs) {
			Node childBest = bestRoot(size, child, currentBest);
			if (childBest.height < currentBest.height) {
				currentBest = childBest;
			}
		}
		return currentBest;
	}

	public static void removeProject(String project, Map<String, HashSet<String>> projects,
			Map<String, HashSet<String>> people) {
		if (!projects.containsKey(project))
			return;

		for (String person : projects.get(project)) {

			for (String otherProject : people.get(person)) {
				if (project.equals(otherProject))
					continue;
				projects.get(otherProject).remove(person);
			}

			people.remove(person);
		}
		projects.remove(project);
	}

	public static Map<Integer, String> buildProjectMappings(Map<String, HashSet<String>> projects) {
		Map<Integer, String> mappings = new TreeMap<>();

		int i = 0;
		for (String project : projects.keySet()) {
			mappings.put(i, project);
			i++;
		}

		return mappings;
	}

	public static Map<String, HashSet<String>> buildPeople(Map<String, HashSet<String>> projects) {
		Map<String, HashSet<String>> people = new TreeMap<>();

		for (String project : projects.keySet()) {
			for (String person : projects.get(project)) {
				if (!people.containsKey(person))
					people.put(person, new HashSet<>());
				people.get(person).add(project);
			}
		}

		return people;
	}

	public static int[][] buildWeights(Map<String, HashSet<String>> projects) {
		int[][] weights = new int[projects.size()][projects.size()];
		int i = 0;
		for (String p1 : projects.keySet()) {
			int j = 0;
			for (String p2 : projects.keySet()) {
				if (p1.equals(p2))
					continue;
				int common = 0;
				for (String person : projects.get(p1)) {
					if (projects.get(p2).contains(person))
						common++;
				}
				weights[i][j] = common;
				weights[j][i] = common;

				j++;
			}
			i++;
		}
		return weights;
	}

	public static int[] buildValues(Map<String, HashSet<String>> projects) {
		int[] values = new int[projects.size()];
		int i = 0;
		for (String p1 : projects.keySet()) {
			values[i] = projects.get(p1).size();
			i++;
		}
		return values;
	}

	public static int[] pargmaxOrder(int[][] weights, int[] values) {
		int[] order = new int[values.length];
		int k = 0;
		int pargmax;
		while ((pargmax = removePargmax(weights, values)) >= 0) {
			order[k++] = pargmax;
		}
		return order;
	}

	public static int removePargmax(int[][] weights, int[] values) {
		int pargmax = pargmax(weights, values);

		if (pargmax >= 0) {
			values[pargmax] = 0;
			for (int c = 0; c < weights.length; c++) {
				if (pargmax == c || weights[pargmax][c] == 0)
					continue;
				weights[pargmax][c] = 0;
				weights[c][pargmax] = 0;
			}
		}

		return pargmax;
	}

	public static int pargmax(int[][] weights, int[] values) {
		int max = 0;
		int pargmax = -1;
		for (int v = 0; v < values.length; v++) {

			int acum = values[v];
			for (int c = 0; c < weights.length; c++) {
				if (v == c || weights[v][c] == 0)
					continue;
				acum += weights[v][c];
			}

			// System.out.println(v + ": " + acum);
			if (acum > max) {
				max = acum;
				pargmax = v;
			}
		}
		return pargmax;
	}

	public static int argmax(int[] values) {
		int max = values[0];
		int argmax = 0;
		for (int i = 1; i < values.length; i++) {
			if (values[i] > max) {
				max = values[i];
				argmax = i;
			}
		}

		return argmax;
	}

	public static void print(int[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	// private HashMap<I, Vertex<I, V, W>> nodes;

	// public Graph() {
	// this.nodes = new HashMap<>();
	// }
	//
	// public void node(I id, V value) {
	// if (nodes.containsKey(id)) throw new IllegalArgumentException("can't add same
	// node twice");
	// nodes.put(id, new Vertex<I, V, W>(id, value));
	// }
	//
	// public void edge(I i1, I i2, W weight) {
	// if (!nodes.containsKey(i1)) node(i1, null);
	// if (!nodes.containsKey(i2)) node(i2, null);
	// nodes.get(i1).add(nodes.get(i2), weight);
	// }
	//
	// public void print() {
	// for (Vertex<I, V, W> node: nodes.values()) {
	// System.out.println(node.id + ": " + node.value);
	// for (Arc<I, V, W> arc: node.neighbors) {
	// System.out.println(node.id + " -> " + arc.neighbor.id + " (" + arc.weight +
	// ")");
	// }
	// }
	// }
	//
	// public I maxValue() {
	// unmark();
	//
	// I bestId = null;
	// V best = null;
	// for (Vertex<I, V, W> node: nodes.values()) {
	// I aux = maxValue(node);
	// if (bestId == null || nodes.get(aux).value.compareTo(best) > 0) {
	// bestId = aux;
	// best = nodes.get(aux).value;
	// }
	// }
	// return bestId;
	// }
	//
	// private I maxValue(Vertex<I, V, W> from) {
	// if (from.marked) return from.id;
	// from.marked = true;
	//
	// I bestId = from.id;
	// V best = from.value;
	//
	// for (Arc<I, V, W> arc: from.neighbors) {
	// I id = maxValue(arc.neighbor);
	// if (nodes.get(id).value.compareTo(best) > 0) {
	// bestId = id;
	// best = nodes.get(id).value;
	// }
	// }
	// return bestId;
	// }
	//
	// public void unmark() {
	// for (Vertex<I, V, W> node: nodes.values()) {
	// node.marked = false;
	// }
	// }
	//
	// public static class Vertex<I,V,W> {
	// public final I id;
	// public V value;
	// public final List<Arc<I, V, W>> neighbors;
	// public boolean marked;
	//
	// public Vertex(I id, V value) {
	// this.id = id;
	// this.value = value;
	// this.neighbors = new ArrayList<>();
	// }
	//
	// public void add(Vertex<I, V, W> neighbor, W weight) {
	// this.neighbors.add(new Arc<I, V, W>(weight, this, neighbor));
	// neighbor.neighbors.add(new Arc<I, V, W>(weight, neighbor, this));
	// }
	// }
	//
	// public static class Arc<I,V,W> {
	// public W weight;
	// public final Vertex<I, V, W> self;
	// public final Vertex<I, V, W> neighbor;
	//
	// public Arc(W weight, Vertex<I, V, W> self, Vertex<I, V, W> neighbor) {
	// this.weight = weight;
	// this.self = self;
	// this.neighbor = neighbor;
	// }
	// }
}
