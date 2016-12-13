package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import model.Person;
import structures.Node;
import structures.Pair;
import structures.Triple;
import util.WorkingPlaceParser;

public class OrganizationParser {

	public Triple<List<Person>, Node, List<Pair<Integer, Integer>>> parse(String organizationPath, String employeesPath) throws FileNotFoundException {
		Node root = WorkingPlaceParser.generate(organizationPath);
		Pair<List<Person>, List<Pair<Integer, Integer>>> employeesWithRestrictions = parseEmployees(employeesPath);
		return new Triple<>(employeesWithRestrictions.first, root, employeesWithRestrictions.second); 
	}
	
	private Pair<List<Person>, List<Pair<Integer, Integer>>> parseEmployees(String employeesPath) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(employeesPath);
		Scanner scanner = new Scanner(inputStream);
		scanner.useLocale(Locale.US);
		
		List<Person> employees = new ArrayList<>();
		Map<String, List<Person>> projects = new TreeMap<>();
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] components = line.trim().split(" ");
			String[] tags = Arrays.copyOfRange(components, 1, components.length);
			
			Person employee = new Person(components[0], tags);
			employees.add(employee);
			
			for(String projectName: tags) {
				if (projects.containsKey(projectName)) {
					projects.get(projectName).add(employee);
				} else {
					List<Person> newProject = new ArrayList<>();
					newProject.add(employee);
					projects.put(projectName, newProject);
				}
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
