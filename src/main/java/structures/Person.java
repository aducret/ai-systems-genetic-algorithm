package structures;

public class Person {
	public final String id;
	public Node workingSpace;
	
	public Person(String id) {
		this.id = id;
	}
	
	public Person clone() {
		Person clone = new Person(id);
		clone.workingSpace = this.workingSpace;
		return clone;
	}
}
