package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Person {
	public final String id;
	public Node workingSpace;
	public Map<String, List<String>> projects;
	private Integer tmi;
	private List<Person> neighbors;
	
	public Person(String id) {
		this.id = id;
	}
	
	public Person clone() {
		Person clone = new Person(id);
		clone.workingSpace = this.workingSpace;
		clone.projects = projects;
		clone.tmi = tmi;
		return clone;
	}
	
	public int tmi() {
		if (tmi != null) return tmi;
		List<Integer> projectSizes = projects.values().stream()
				.filter(l->l.contains(id))
				.map(l -> l.size())
				.collect(Collectors.toList());
		int acum = projectSizes.stream().mapToInt(i->i.intValue()).sum();
		acum -= projectSizes.size();
		acum++;
		this.tmi = acum;
		return acum;
	}
	
	public List<Person> neighbors(Person[] people) {
		if (neighbors != null) return neighbors;
		List<Person> ans = new ArrayList<>();
		
		final Map<String,Person> map = new HashMap<>();
		for (Person person: people) {
			map.put(person.id, person);
		}
		
		for (List<String> l: projects.values()) {
			if (!l.contains(id)) continue;
			ans.addAll(l.stream()
					.map(s->map.get(s))
					.filter(p->!id.equals(p.id))
					.collect(Collectors.toList()));
		}
		
		this.neighbors = ans;
		return ans;
	}
	
	public List<String> personalProjects() {
		List<String> ans = new ArrayList<>();
		for (String projectName: projects.keySet()) {
			if (projects.get(projectName).contains(id)) {
				ans.add(projectName);
			}
		}
		return ans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return id.toString() + " " + personalProjects().toString();
	}
}
