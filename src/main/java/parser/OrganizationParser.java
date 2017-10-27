package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import model.Node;
import model.Pair;
import model.Person;
import model.Triple;

public class OrganizationParser {

	public Triple<List<Person>, Node, Map<String, Set<String>>> parse(String organizationPath, String employeesPath) throws FileNotFoundException {
		Node root = WorkingPlaceParser.generate(organizationPath);
		Pair<List<Person>, Map<String, Set<String>> > employeesWithRestrictions = parseEmployees(employeesPath);
		return new Triple<>(employeesWithRestrictions.first, root, employeesWithRestrictions.second); 
	}
	
//	private Node parseOrganization(String organizationPath) throws FileNotFoundException {
//		InputStream inputStream = new FileInputStream(organizationPath);
//		Scanner scanner = new Scanner(inputStream);
//		scanner.useLocale(Locale.US);
//		
//		Node root = new Node("Root", null);
//		Node lastBuilding = null;
//		Node lastFloor = null;
//		Node lastRoom = null;
//		Node lastTable = null;
//		while(scanner.hasNext()) {
//			String line = scanner.nextLine();
//			int matches = StringUtils.countMatches(line, '\t');
//			String entity = line.substring(line.lastIndexOf('\t') + 1);
//			
//			switch (matches) {
//			case 0: // Building
//				if (root == null)
//					System.err.println("There was a problem with the root");
//				lastBuilding = new Node(entity, root);
//				break;
//			case 1: // Floor
//				if (lastBuilding == null)
//					System.err.println("There was a problem with the last building");
//				lastFloor = new Node(entity, lastBuilding);
//				break;
//			case 2: // Room
//				if (lastFloor == null)
//					System.err.println("There was a problem with the last floor");
//				lastRoom = new Node(entity, lastFloor);
//				break;
//			case 3: // Table
//				if (lastRoom == null)
//					System.err.println("There was a problem with the last room");
//				lastTable = new Node(entity, lastRoom);
//				break;
//			case 4: // Workstation
//				if (lastTable == null)
//					System.out.println("There was a problem with the last table");
//				new Node(entity, lastTable);
//				break;
//			default: 
//				System.err.println("Unrecognized case");
//			}
//		}
//		
//		scanner.close();
//		return root;
//	}
	
	public TreeMap<String, HashSet<String>> parseProjects(String employeesPath) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(employeesPath);
		Scanner scanner = new Scanner(inputStream);
		scanner.useLocale(Locale.US);
		
		List<Person> employees = new ArrayList<>();
		TreeMap<String, HashSet<String>> projects = new TreeMap<>();
//		Map<String, HashSet<String>> projects2 = new TreeMap<>();
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] components = line.trim().split(" ");
			Person employee = new Person(components[0]);
			employees.add(employee);
			String[] tags = Arrays.copyOfRange(components, 1, components.length);
			
			for(String projectName: tags) {
				if (projects.containsKey(projectName)) {
					projects.get(projectName).add(employee.id);
				} else {
//					List<Person> newProject = new ArrayList<>();
//					newProject.add(employee);
					projects.put(projectName, new HashSet<>());
					projects.get(projectName).add(employee.id);
				}
			}
		}
		
		scanner.close();
		return projects;
	}
	
	public Pair<List<Person>, Map<String, Set<String> > > parseEmployees(String employeesPath) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(employeesPath);
		Scanner scanner = new Scanner(inputStream);
		scanner.useLocale(Locale.US);
		
		List<Person> employees = new ArrayList<>();
		Map<String, List<Person>> projects = new TreeMap<>();
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] components = line.trim().split(" ");
			Person employee = new Person(components[0]);
			employees.add(employee);
			String[] tags = Arrays.copyOfRange(components, 1, components.length);
			
			for(String projectName: tags) {
				if (projects.containsKey(projectName)) {
					projects.get(projectName).add(employee);
				} else {
					List<Person> newProject = new ArrayList<>();
					newProject.add(employee);
					projects.put(projectName, newProject);
				}
//				employee.projectSizes.put(projectName, 0);
			}
		}
		System.out.println(projects);
		Map<String, Set<String>> restrictions = new HashMap<>();
		for(List<Person> project: projects.values()) {
			for(int i = 0; i < project.size(); i++) {
				int indexOfI = employees.indexOf(project.get(i));
				for (int j = 0; j < project.size(); j++) {
					if (i == j) continue;
					if (!restrictions.containsKey(project.get(i).id)) {
						restrictions.put(project.get(i).id, new HashSet<String>());
					}
					restrictions.get(project.get(i).id).add(project.get(j).id);
//					Pair<Integer, Integer> restriction = new Pair<>(employees.indexOf(project.get(i)), employees.indexOf(project.get(j)));
//					restrictions.add(restriction);
				}
			}
		}
		Map<String, List<String>> projectsWithNames = projects.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
						.map(p->p.id)
						.collect(Collectors.toList())));
		for(Person employee: employees) {
			employee.projects = projectsWithNames;
//			for (String project: employee.projectSizes.keySet()) {
//				employee.projectSizes.put(project, projects.get(project).size());
//			}
		}
		
		scanner.close();
		return new Pair<List<Person>, Map<String, Set<String>> >(employees, restrictions);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		OrganizationParser op = new OrganizationParser();
		Triple<List<Person>, Node, Map<String, Set<String>>> result = op.parse("./doc/gapa/ej1_org", "./doc/gapa/ej1_emp");
		System.out.println("Employees: ");
		System.out.println(result.first);
		System.out.println("Restrictions: ");
		System.out.println(result.third);
	}
	
}	
