package algorithm.listener;

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
import javax.swing.JScrollPane;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import algorithm.util.RandomPopper;
import model.Node;
import model.Person;

public class GraphListener implements GeneticAlgorithmListener {

	private Node root;
	private Map<String, Object> nodes = new HashMap<>(); 
			
	private final mxGraph g = new mxGraph();
	private Object parent = g.getDefaultParent();
	
	public GraphListener(Node root) {
		this.root = root;
	}
	
	@Override
	public void onNewGenerationReached(int newGeneration, List<Chromosome> generation, Chromosome bestChromosome) {
	}

	@Override
	public void onBestChromosomeUpdated(Chromosome bestChromosome) {
	}

	@Override
	public void onGeneticAlgorithmFinished(Chromosome currentBestChromosome, Chromosome bestChromosome) {
		if (root == null) {
			System.err.println("The root node wasn't setted");
			return;
		}
		
		if (!(bestChromosome instanceof GAPAChromosome)) {
			System.err.println("The bestChromosome isn't a GAPAChromosome.");
			return;
		}
		
		graph(root, (GAPAChromosome)bestChromosome);
	}

	private void graph(Node root, GAPAChromosome bestChromosome) {
        addNodesAndEdges(g, root, bestChromosome);
        
        mxGraphComponent component = new mxGraphComponent(g);
        createFrame(component);
        mxHierarchicalLayout l = new mxHierarchicalLayout(g);
		l.execute(parent);
	}
	
	private JFrame createFrame(mxGraphComponent component) {
		JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        double width = screenSize.getWidth();
        frame.setSize((int) width, (int) height - 50);
        JScrollPane scroller = new JScrollPane(component);
        frame.add(scroller);
        frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	
	private boolean checkMultipleProjects(GAPAChromosome bestChromosome) {
		for (Person person: bestChromosome.getPeople()) {
			if (person.getAssignedProjects().size() > 1) {
				return true;
			}
		}
		return false;
	}
	
	private void addNodesAndEdges(mxGraph g, Node root, GAPAChromosome bestChromosome) {
		g.getModel().beginUpdate();
		try {
			nodes.put(root.getFullId(), g.insertVertex(parent, null, root.toString(), 0, 0, 0, 0, defaultStyle));
			Person[] people = bestChromosome.getPeople();
			Map<String, String> colorDic = null;
			if (!checkMultipleProjects(bestChromosome)) {
		        colorDic = createColorDic(bestChromosome);
	        }
			addNodes(g, root, people, colorDic);
			addEdges(g, root, people);
		resizeNodes();
		} finally {
			g.getModel().endUpdate();
		}
	}

	private void resizeNodes() {
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

	private void addNodes(mxGraph g, Node node, Person[] people, Map<String, String> colors) {
		for (Node child: node.childs) {
			if (child.isLeaf()) {
				Person person = getPersonForNode(child, people);
				if (person != null) {
					String style = getStyle(person, colors);
					nodes.put(person.getFullId(), g.insertVertex(parent, null, person.toString(), 0, 0, 0, 0, style));
				} else {
					nodes.put(child.getFullId(), g.insertVertex(parent, null, child.toString(), 0, 0, 0, 0, defaultStyle));
				}
			} else {
				nodes.put(child.getFullId(), g.insertVertex(parent, null, child.toString(), 0, 0, 0, 0, defaultStyle));
			}
			addNodes(g, child, people, colors);
		}
	}
	
	// Para mas informacion sobre estilo
	// https://jgraph.github.io/mxgraph/docs/js-api/files/util/mxConstants-js.html#mxConstants.FONT_BOLD
	private String getStyle(Person person, Map<String, String> colors) {
		String key = person.getAssignedProjects().get(0);
		if (colors != null && colors.containsKey(key)) {
			String color = colors.get(key);
			return "strokeWidth=5;strokeColor=" + color + ";fontColor=black;fontSize=15;spacingTop=4;fontStyle=1";
		}
		return "";
	}
	
	private String defaultStyle = "fontColor=black;fontSize=15;spacingTop=4;fontStyle=1";
	
	private Person getPersonForNode(Node node, Person[] people) {
		for(Person person: people) {
			if (person.getWorkingSpace().equals(node)) {
				return person;
			}
		}
		return null;
	}
	
	private void addEdges(mxGraph g, Node node, Person[] people) {
		for (Node child: node.childs) {
			if (child.isLeaf()) {
				Person person = getPersonForNode(child, people);
				if (person != null) {
					g.insertEdge(parent, null, "", nodes.get(node.getFullId()), nodes.get(person.getFullId()));
				} else {
					g.insertEdge(parent, null, "", nodes.get(node.getFullId()), nodes.get(child.getFullId()));
				}
			} else {
				g.insertEdge(parent, null, "", nodes.get(node.getFullId()), nodes.get(child.getFullId()));
				addEdges(g, child, people);
			}
		}
	}
	
	private Map<String, String> createColorDic(GAPAChromosome bestChromosome) {
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
	
	private Set<String> getProjects(GAPAChromosome bestChromosome) {
		Set<String> s = new HashSet<>();
		
		for (Person p: bestChromosome.getPeople()) {
			s.addAll(p.projects.keySet());
		}
		
		return s;
	}
	
}
