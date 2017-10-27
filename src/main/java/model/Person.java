package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import parser.WorkingPlaceParser;

public class Person {
	public final String id;
	private Node workingSpace;
	public Map<String, List<String>> projects;
	public Map<String, HashSet<String>> projects2;
	private Integer tmi;
	private List<Person> neighbors;
	private String fullId = null;
	
	public Person(String id) {
		this.id = id;
	}
	
	public Person clone() {
		Person clone = new Person(id);
		clone.workingSpace = this.workingSpace;
		clone.projects = projects;
		clone.projects2 = projects2;
		clone.tmi = tmi;
		return clone;
	}
	
	public int tmi() {
		if (tmi != null) return tmi;
		syncProjects();
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

	public List<String> getAssignedProjects() {
		syncProjects();
		List<String> assignedProjects = new ArrayList<>();
		for (Entry<String, HashSet<String>> entry: projects2.entrySet()) {
			if (entry.getValue().contains(id)) {
				assignedProjects.add(entry.getKey());
			}
		}
		return assignedProjects;
	}
	
	public void syncProjects () {
		if (projects != null && projects2 != null) return;
		if (projects == null && projects2 == null) return;
		if (projects != null) {
			projects2 = new HashMap<String, HashSet<String>>();
			for (String project: projects.keySet()) {
				projects2.put(project, new HashSet<>(projects.get(project)));
			}
		}
		if (projects2 != null) {
			projects = new HashMap<String, List<String>>();
			for (String project: projects.keySet()) {
				projects.put(project, new ArrayList<>(projects2.get(project)));
			}
		}
	}
	
	public void setWorkingSpace(Node workingSpace) {
		updateFullId();
		this.workingSpace = workingSpace;
	}
	
	public Node getWorkingSpace() {
		return workingSpace;
	}
	
	private void updateFullId() {
		fullId = WorkingPlaceParser.fullId(workingSpace) + id;
	}
	
	public String getFullId() {
		if (fullId != null) return fullId;
		
		fullId = WorkingPlaceParser.fullId(workingSpace) + id;
		return fullId;
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
		if (getFullId() == null) {
			if (other.getFullId() != null)
				return false;
		} else if (!getFullId().equals(other.getFullId()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return id.toString();
//		return id.toString() +  " " + getAssignedProjects();
	}
}
