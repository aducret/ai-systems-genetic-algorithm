package model;

import java.util.ArrayList;
import java.util.List;

import parser.WorkingPlaceParser;

public class Node {
	public String id;
	public Node parent;
	public List<Node> childs;
	public int height;
	public int level;
	public int capacity;
	private String fullId = null;
	public boolean used;
	
	public Node(String id, Node parent) {
		this.childs = new ArrayList<>();
		this.id = id;
		this.parent = parent;
		if (parent != null) {
			parent.childs.add(this);
			this.height = parent.height + 1;
		}
	}
	
	public void use() {
		used = true;
		decNode();
	}
	
	public void decNode() {
		capacity--;
		//recalculate height
		int max = 0;
		for (Node child: childs) {
			if (child.isLeaf() && child.used) continue;
			max = Math.max(max, child.height);
		}
		height = max + 1;
		if (parent != null) {
			parent.decNode();
		}
	}
	
	public boolean isLeaf() {
		return childs.isEmpty();
	}

	public void print() {
		System.out.println(getFullId() + ", height: " + height);
		for (Node child: childs) {
			child.print();
		}
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

	public String getFullId() {
		if (fullId != null) return fullId;
		
		fullId = WorkingPlaceParser.fullId(this);
		return fullId;
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
		if (getFullId() == null) {
			if (other.getFullId() != null)
				return false;
		} else if (!getFullId().equals(other.getFullId()))
			return false;
		return true;
	}
}
