package structures;

import java.util.LinkedList;
import java.util.List;

public class NodeUtils {
	
	/**
	 * 
	 * @param node1 hoja del arbol
	 * @param node2 hoja del arbol
	 * @return distancia entre las hojas del arbol
	 */
	public static int distanceBetween(Node node1, Node node2) {
		if (node1 == node2) return 0;
		if (node1.height > node2.height) return 1 + distanceBetween(node1.parent, node2);
		if (node2.height > node1.height) return 1 + distanceBetween(node1, node2.parent);
		return 2 + distanceBetween(node1.parent, node2.parent);
	}
	
	public static List<Node> leafs(Node root) {
		List<Node> leafs = new LinkedList<>();
		leafs(root, leafs);
		return leafs;
	}
	
	private static void leafs(Node node, List<Node> leafs) {
		if (node.isLeaf()) {
			leafs.add(node);
			return;
		}
		for (Node child: node.childs) {
			leafs(child, leafs);
		}
	}
}
