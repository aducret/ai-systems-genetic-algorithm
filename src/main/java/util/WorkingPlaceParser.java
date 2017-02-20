package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

import structures.Node;

public class WorkingPlaceParser {
	public static Node generate(String filePath) {
		return generate(new File(filePath));
	}
	
	public static Node generate(File file) {
		try {
			return generate(new Scanner(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Node generate(Scanner sc) {
		Deque<Node> stack = new LinkedList<>();
		Node root = new Node(sc.nextLine().trim(), null);
		root.height = 0;
		int previousLevel = 0;
		stack.push(root);
		while (sc.hasNext() && !stack.isEmpty()) {
			String line = sc.nextLine();
			String trimmed = line.trim();
			int level = line.indexOf(trimmed);
			
			if (level <= previousLevel) {
				while (!stack.isEmpty() && level <= stack.peek().height) stack.pop();
			}
			
			Node nextNode = new Node(trimmed, stack.peek());
			nextNode.height = level;
			stack.push(nextNode);
			previousLevel = level;
		}
		sc.close();
		return root;
	}
	
	public static String fullId(Node node) {
		if (node == null) return "";
		return fullId(node.parent) + "-" + node.id;
	}
	
}
