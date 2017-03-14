package algorithm.listener;

import java.awt.Color;
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

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import algorithm.util.RandomPopper;
import model.Node;
import model.Person;

public class GraphListener implements GeneticAlgorithmListener {

	private Node root;
	private Map<Integer, Either> eithers = new HashMap<>();
	
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
        Graph<Either, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        
        addNodesAndEdges(g, root, bestChromosome);
        
        JGraph jg = new JGraph(new JGraphModelAdapter<Either, DefaultEdge>(g));
        JGraphHierarchicalLayout hl = new JGraphHierarchicalLayout();
        JGraphFacade gf = new JGraphFacade(jg);
        hl.run(gf);
        Map nm = gf.createNestedMap(true, true);
        jg.getGraphLayoutCache().edit(nm);
        removeEdgeLabels(jg);
        if (!checkMultipleProjects(bestChromosome)) {
	        Map<String, Color> colorDic = createColorDic(bestChromosome);
	        if (colorDic != null ) {
	        	setColors(jg, colorDic, bestChromosome.getPeople());
	        }
        }
        
        JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        double width = screenSize.getWidth();
        frame.setSize((int) width, (int) height);
        JScrollPane scroller = new JScrollPane(jg);
        frame.add(scroller);
        frame.pack();
        // Por alguna razon el pack no funciona bien y corta 20 pixeles aprox.
        frame.setSize(frame.getWidth(), frame.getHeight() + 25);
        frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private boolean checkMultipleProjects(GAPAChromosome bestChromosome) {
		for (Person person: bestChromosome.getPeople()) {
			if (person.getAssignedProjects().size() > 1) {
				return true;
			}
		}
		return false;
	}
	
	private void addNodesAndEdges(Graph<Either, DefaultEdge> g, Node root, GAPAChromosome bestChromosome) {
		Either e = new Either(root);
		eithers.put(root.number, e);
		g.addVertex(e);
		Person[] people = bestChromosome.getPeople();
		addNodes(g, root, people);
		addEdges(g, root, people);
	}
	
	private void addNodes(Graph<Either, DefaultEdge> g, Node node, Person[] people) {
		for (Node child: node.childs) {
			if (child.isLeaf()) {
				Person person = getPersonForNode(child, people);
				if (person != null) {
					Either e = new Either(person);
					System.out.println(person.number + "/"+ e);
					eithers.put(person.number, e);
					g.addVertex(e);
				} else {
					Either e = new Either(child);
					System.out.println(child.number + "/"+ e);
					eithers.put(child.number, e);
					g.addVertex(e);
				}
			} else {
				Either e = new Either(child);
				System.out.println(child.number + "/"+ e);
				eithers.put(child.number, e);
				g.addVertex(e);
			}
			addNodes(g, child, people);
		}
	}
	
	private Person getPersonForNode(Node node, Person[] people) {
		for(Person person: people) {
			if (person.getWorkingSpace().equals(node)) {
				return person;
			}
		}
		return null;
	}
	
	private void addEdges(Graph<Either, DefaultEdge> g, Node node, Person[] people) {
		for (Node child: node.childs) {
			if (child.isLeaf()) {
				Person person = getPersonForNode(child, people);
				if (person != null) {
					System.out.println(eithers.get(node.number) + " - " + eithers.get(person.number));
					g.addEdge(eithers.get(node.number), eithers.get(person.number));
				} else {
					System.out.println(eithers.get(node.number) + " - " + eithers.get(child.number));
					g.addEdge(eithers.get(node.number), eithers.get(child.number));
				}
			} else {
				System.out.println(eithers.get(node.number) + " - " + eithers.get(child.number));
				g.addEdge(eithers.get(node.number), eithers.get(child.number));
				addEdges(g, child, people);
			}
		}
	}
	
	private Map<String, Color> createColorDic(GAPAChromosome bestChromosome) {
		List<Color> colors = new ArrayList<>(Arrays.asList((new Color[] { 
				Color.BLACK,
				Color.RED,
				Color.BLUE,
				Color.MAGENTA,
				Color.DARK_GRAY,
				Color.ORANGE,
				Color.GREEN,
				Color.cyan
		})));
		RandomPopper<Color> colorPopper = new RandomPopper<>(colors);
		Set<String> projects = getProjects(bestChromosome);
		Map<String, Color> dic = new HashMap<>();
		if (projects.size() > colors.size()) {
			System.out.println("There aren't enough colors.");
			return null;
		}
		for (String project: projects) {
			Color color = colorPopper.randomPop();
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
	
	private void setColors(JGraph jg, Map<String, Color> dic, Person[] people) {
		GraphLayoutCache cache = jg.getGraphLayoutCache();
		for (Object item : jg.getRoots()) {
			GraphCell cell = (GraphCell) item;
			CellView view = cache.getMapping(cell, true);
			AttributeMap map = view.getAttributes();
			String id = item.toString();
			Person person = getPerson(id, people);
			String key = person == null ? item.toString() : person.getAssignedProjects().get(0);
			if (dic.containsKey(key)) {
				Color color = dic.get(key);
				map.applyValue(GraphConstants.BACKGROUND, color);
			}
		}
		cache.reload();
		jg.repaint();
	}
	
	private Person getPerson(String id, Person[] people) {
		for(Person person: people) {
			if (person.id == id) {
				return person;
			}
		}
		return null;
	}
	
	private void removeEdgeLabels(JGraph jg) {
		GraphLayoutCache cache = jg.getGraphLayoutCache();
		CellView[] cells = cache.getCellViews();
		for (CellView cell : cells) {
			if (cell instanceof EdgeView) {
				EdgeView ev = (EdgeView) cell;
				org.jgraph.graph.DefaultEdge eval = (org.jgraph.graph.DefaultEdge) ev.getCell();
				eval.setUserObject("");
			}
		}
		cache.reload();
		jg.repaint();
	}
	
	public class Either {
		private Person person = null;
		private Node node = null;
		
		public Either(Person person) {
			this.person = person;
		}
		
		public Either(Node node) {
			this.node = node;
		}
		
		public Node getNode() {
			return node;
		}
		
		public Person getPerson() {
			return person;
		}
		
		@Override
		public String toString() {
			return person == null ? node.id : person.id;
		}
	}
	
}
