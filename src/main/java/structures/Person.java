package structures;

import java.util.HashMap;
import java.util.Map;

public class Person {
	public final String id;
	public Node workingSpace;
	public Map<String, Integer> projectSizes = new HashMap<>(); 
	
	public Person(String id) {
		this.id = id;
	}
	
	public Person clone() {
		Person clone = new Person(id);
		clone.workingSpace = this.workingSpace;
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
		return id.toString() + "(" + projectSizes.toString() + ")";
	}
}
