package util;

import java.util.LinkedList;
import java.util.List;

import model.Node;

public class NodeUtils {
	
	/**
	 * 
	 * @param node1 hoja del arbol
	 * @param node2 hoja del arbol
	 * @return distancia entre las hojas del arbol
	 */
	public static int distanceBetween(Node node1, Node node2) {
//		System.out.println(node1 + ", " + node2);
//		System.out.println(node1.level + ", " + node2.level);
//		System.out.println();
		if (node1 == node2) return 0;
		if (node1.level > node2.level) return 1 + distanceBetween(node1.parent, node2);
		if (node2.level > node1.level) return 1 + distanceBetween(node1, node2.parent);
		return 2 + distanceBetween(node1.parent, node2.parent);
	}
	
	public static LinkedList<Node> leafs(Node root) {
		LinkedList<Node> leafs = new LinkedList<>();
		leafs(root, leafs);
		return leafs;
	}
	
	private static void leafs(Node node, List<Node> leafs) {
		if (node.isLeaf()) {
			if (!node.used) leafs.add(node);
			return;
		}
		for (Node child: node.childs) {
			leafs(child, leafs);
		}
	}
	
	public static void calculateCapacities(Node node) {
		if (node.childs.isEmpty()) {
			node.capacity = 1;
			node.height = 0;
			return;
		}
		int capacityAcum = 0;
		int maxHeight = 0;
		for (Node child: node.childs) {
			calculateCapacities(child);
			capacityAcum += child.capacity;
			maxHeight = Math.max(maxHeight, child.height+1);
		}
		node.capacity = capacityAcum;
		node.height = maxHeight;
	}
}
