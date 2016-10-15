package structures;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public String id;
	public Node parent;
	public List<Node> childs;
	public int height;
	
	public Node(String id, Node parent) {
		this.childs = new ArrayList<>();
		this.id = id;
		this.parent = parent;
		if (parent != null) {
			parent.childs.add(this);
			this.height = parent.height + 1;
		}
	}
	
	public boolean isLeaf() {
		return childs.isEmpty();
	}

	public String toString() {
		return id;
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
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
