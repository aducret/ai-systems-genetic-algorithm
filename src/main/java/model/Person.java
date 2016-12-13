package model;

import structures.Node;
import util.WorkingPlaceParser;

public class Person {
	
	public final String id;
	public Node seat;
	public String[] projects; 
			
	public Person(String id, String[] projects) {
		this.id = id;
		this.projects = projects;
	}
	
	public Person clone() {
		Person clone = new Person(id, projects);
		return clone;
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
		return id.toString() + "{" + WorkingPlaceParser.fullId(seat) + "}";
	}
	
}
