package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import algorithm.model.Pair;
import algorithm.model.Triple;
import structures.Node;
import structures.Person;
import util.WorkingPlaceParser;

public class OrganizationParser {

	public Triple<List<Person>, Node, List<Pair<Integer, Integer>>> parse(String organizationPath, String employeesPath) throws FileNotFoundException {
		Node root = WorkingPlaceParser.generate(organizationPath);
		Pair<List<Person>, List<Pair<Integer, Integer>>> employeesWithRestrictions = parseEmployees(employeesPath);
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
	
	private Pair<List<Person>, List<Pair<Integer, Integer>>> parseEmployees(String employeesPath) throws FileNotFoundException {
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
				employee.projectSizes.put(projectName, 0);
			}
		}
		
		List<Pair<Integer, Integer>> restrictions = new ArrayList<>();
		for(List<Person> project: projects.values()) {
			for(int i = 0; i < project.size(); i++) {
				for (int j = i + 1; j < project.size(); j++) {
					Pair<Integer, Integer> restriction = new Pair<>(employees.indexOf(project.get(i)), employees.indexOf(project.get(j)));
					restrictions.add(restriction);
				}
			}
		}
		
		for(Person employee: employees) {
			for (String project: employee.projectSizes.keySet()) {
				employee.projectSizes.put(project, projects.get(project).size());
			}
		}
		
		scanner.close();
		return new Pair<List<Person>, List<Pair<Integer, Integer>>>(employees, restrictions);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		OrganizationParser op = new OrganizationParser();
		Triple<List<Person>, Node, List<Pair<Integer, Integer>>> result = op.parse("./doc/gapa/ej1_org", "./doc/gapa/ej1_emp");
		System.out.println("Employees: ");
		System.out.println(result.first);
		System.out.println("Restrictions: ");
		System.out.println(result.third);
	}
	
}	
