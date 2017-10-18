package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import algorithm.chromosome.GAPAChromosome;
import model.Node;
import model.Person;

public class GraphUtils {
	
	private static final String DEFAULT_STYLE = "fontColor=black;fontSize=15;spacingTop=4;fontStyle=1";

	public static void graph(Node root, GAPAChromosome bestChromosome) {
		mxGraph g = new mxGraph();
		Object parent = g.getDefaultParent();
		Map<String, Object> nodes = new HashMap<>(); 
		
        addNodesAndEdges(g, root, bestChromosome, nodes);
        
        mxGraphComponent component = new mxGraphComponent(g);
        mxCompactTreeLayout l = new mxCompactTreeLayout(g, false);
		l.execute(parent);
		g.setCellsMovable(false);
		g.setCellsLocked(true);
		createFrame(component);
	}
	
	private static JFrame createFrame(mxGraphComponent component) {
		JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        double width = screenSize.getWidth();
        frame.add(component);
        frame.setPreferredSize(new Dimension((int) width, (int) height - 50));
        frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		return frame;
	}
	
	private static boolean checkMultipleProjects(GAPAChromosome bestChromosome) {
		for (Person person: bestChromosome.getPeople()) {
			if (person.getAssignedProjects().size() > 1) {
				return true;
			}
		}
		return false;
	}
	
	private static void addNodesAndEdges(mxGraph g, Node root, GAPAChromosome bestChromosome, Map<String, Object> nodes) {
		g.getModel().beginUpdate();
		try {
			nodes.put(root.getFullId(), g.insertVertex(g.getDefaultParent(), root.getFullId(), root.toString(), 0, 0, 0, 0, DEFAULT_STYLE));
			Person[] people = bestChromosome.getPeople();
			Map<String, String> colorDic = null;
			if (!checkMultipleProjects(bestChromosome)) {
		        colorDic = createColorDic(bestChromosome);
	        }
			addNodes(g, root, people, colorDic, nodes);
			addEdges(g, root, people, nodes);
		resizeNodes(g, nodes);
		} finally {
			g.getModel().endUpdate();
		}
	}

	private static void resizeNodes(mxGraph g, Map<String, Object> nodes) {
		g.getModel().beginUpdate();
		g.setAutoSizeCells(true);
		g.setAutoOrigin(true);
		try {
			for (Object node: nodes.values()) {
				g.updateCellSize(node);
			}
		} finally {
			g.getModel().endUpdate();
		}
	}

	private static void addNodes(mxGraph g, Node node, Person[] people, Map<String, String> colors, Map<String, Object> nodes) {
		for (Node child: node.childs) {
			if (child.isLeaf()) {
				Person person = getPersonForNode(child, people);
				if (person != null) {
					String style = getStyle(person, colors);
					nodes.put(person.getFullId(), g.insertVertex(g.getDefaultParent(), person.getFullId(), person.toString(), 0, 0, 0, 0, style));
				} else {
					nodes.put(child.getFullId(), g.insertVertex(g.getDefaultParent(), child.getFullId(), child.toString(), 0, 0, 0, 0, DEFAULT_STYLE));
				}
			} else {
				nodes.put(child.getFullId(), g.insertVertex(g.getDefaultParent(), child.getFullId(), child.toString(), 0, 0, 0, 0, DEFAULT_STYLE));
			}
			addNodes(g, child, people, colors, nodes);
		}
	}
	
	// Para mas informacion sobre estilo
	// https://jgraph.github.io/mxgraph/docs/js-api/files/util/mxConstants-js.html#mxConstants.FONT_BOLD
	private static String getStyle(Person person, Map<String, String> colors) {
		String key = person.getAssignedProjects().get(0);
		if (colors != null && colors.containsKey(key)) {
			String color = colors.get(key);
			return "strokeWidth=5;strokeColor=" + color + ";fontColor=black;fontSize=15;spacingTop=4;fontStyle=1";
		}
		return DEFAULT_STYLE;
	}
	
	private static Person getPersonForNode(Node node, Person[] people) {
		for(Person person: people) {
			if (person.getWorkingSpace().equals(node)) {
				return person;
			}
		}
		return null;
	}
	
	private static void addEdges(mxGraph g, Node node, Person[] people, Map<String, Object> nodes) {
		for (Node child: node.childs) {
			if (child.isLeaf()) {
				Person person = getPersonForNode(child, people);
				if (person != null) {
					g.insertEdge(g.getDefaultParent(), null, "", nodes.get(node.getFullId()), nodes.get(person.getFullId()));
				} else {
					g.insertEdge(g.getDefaultParent(), null, "", nodes.get(node.getFullId()), nodes.get(child.getFullId()));
				}
			} else {
				g.insertEdge(g.getDefaultParent(), null, "", nodes.get(node.getFullId()), nodes.get(child.getFullId()));
				addEdges(g, child, people, nodes);
			}
		}
	}
	
	private static Map<String, String> createColorDic(GAPAChromosome bestChromosome) {
		List<String> colors = new ArrayList<>(Arrays.asList((new String[] { 
				"black",
				"red",
				"blue",
				"green",
				"yellow",
				"#FF00FF",
				"#a9a9a9",
				"#FFA500",
				"#00FFFF"
		})));
		RandomPopper<String> colorPopper = new RandomPopper<>(colors);
		Set<String> projects = getProjects(bestChromosome);
		Map<String, String> dic = new HashMap<>();
		if (projects.size() > colors.size()) {
			System.err.println("There aren't enough colors.");
			return null;
		}
		for (String project: projects) {
			String color = colorPopper.randomPop();
			dic.put(project, color);
		}
		
        return dic;
	}
	
	private static Set<String> getProjects(GAPAChromosome bestChromosome) {
		Set<String> s = new HashSet<>();
		
		for (Person p: bestChromosome.getPeople()) {
			s.addAll(p.projects2.keySet());
		}
		
		return s;
	}
}
