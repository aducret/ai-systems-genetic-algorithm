package main;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithm.Configuration;
import algorithm.GeneticAlgorithmProblem;
import algorithm.chromosome.Chromosome;
import algorithm.chromosome.GAPAChromosome;
import algorithm.chromosome.GAPAChromosome2;
import algorithm.crossover.GAPACrossOver2;
import algorithm.cuttingCondition.ContentCuttingCondition;
import algorithm.cuttingCondition.GoalReachedCuttingCondition;
import algorithm.cuttingCondition.MaxGenerationsCuttingCondition;
import algorithm.cuttingCondition.StructureCuttingCondition;
import algorithm.mutation.GAPAMutationAlgorithm2;
import algorithm.pairing.RandomPairingAlgorithm;
import algorithm.replace.ReplaceMethod2;
import algorithm.selector.CompoundSelector;
import algorithm.selector.EliteSelector;
import algorithm.selector.RouletteSelector;
import model.Node;
import model.Person;
import model.Triple;
import parser.OrganizationParser;
import util.NodeUtils;
import util.VectorUtils;
import parser.ConfigurationParser;
import util.RandomPopper;

public class GAPAProblem implements GeneticAlgorithmProblem {

	private List<Person> employees;
	private List<Node> seats;

	private Map<String, Set<String>> restrictions;
	private Map<String, HashSet<String>> projects;
	
	
	//second version of problem state
	private String orgPath;
	private String empPath;

	private Configuration configuration;
	private String dirFilepath = "doc/";
	private String configurationPath = "configuration.txt";

	/**
	 * TODO: move parameters to a configuration file and receive file name as parameter
	 * @param employees list of emp.txt ids
	 * @param seats should be a call to NodeUtils.leafs(root)
	 * @param restrictions look at {@link GAPAChromosome}
	 */
	public GAPAProblem(List<Person> employees, List<Node> seats, Map<Integer, Set<Integer>> restrictions) throws FileNotFoundException {
		if  (seats.size() < employees.size()) {
			for (Person person: employees) {
				System.out.println(person.id);
			}
			throw new IllegalArgumentException(String.format("can't have fewer seats than people. has %d seats and %d people", seats.size(), employees.size()));
		}
		this.employees = employees;
		this.seats = seats;
//		this.restrictions = restrictions;
	}
	
	public GAPAProblem(String dirFilepath, String orgPath, String empPath) throws FileNotFoundException {
		this.dirFilepath = dirFilepath;
		this.orgPath = orgPath;
		this.empPath = empPath;
		
		//TODO: change this, add parameters at GAPAChromosome2 constructor
		GAPAChromosome2.orgPath = orgPath;
		GAPAChromosome2.empPath = empPath;
		
		OrganizationParser op = new OrganizationParser();
		Triple<List<Person>, Node, Map<String, Set<String>>> info = op.parse(orgPath, empPath);
		
		this.employees = info.first;
		this.seats = NodeUtils.leafs(info.second);
		this.restrictions = info.third;
		this.projects = op.parseProjects(empPath);
		
		Main.root = info.second;
		this.configuration = ConfigurationParser.parse(dirFilepath, dirFilepath + configurationPath);
	}
	
	@Override
	public Configuration configuration() {
//		int limit = 100;
//		double goal = 1.0;
//		double structureTolerance = 0.95;
//		int contentTolerance = 30;
//
//		return new Configuration.Builder()
//				.withN(400)
//				.withK(200)
//				.withSeed(4)
//				.withCrossOverSelector(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.15))
//				.withPairingAlgorithm(new RandomPairingAlgorithm())
//				.withCrossOverAlgorithm(new GAPACrossOver2())
//				.withMutationAlgorithm(new GAPAMutationAlgorithm2(), 0.4)
//				.withReplaceMethod(new ReplaceMethod2(new CompoundSelector(new EliteSelector(), new RouletteSelector(), 0.5)))
//				.addCuttingCondition(new MaxGenerationsCuttingCondition(limit))
//				.addCuttingCondition(new GoalReachedCuttingCondition(goal))
//				.addCuttingCondition(new StructureCuttingCondition(structureTolerance))
//				.addCuttingCondition(new ContentCuttingCondition(contentTolerance))
//				.build();
		return configuration;
	}

	@Override
	public Chromosome createRandom() {
//		RandomPopper<Node> rp = new RandomPopper<>(seats);
//		Person[] people = new Person[emp.txt.size()];
//		for (int i = 0; i < emp.txt.size(); i++) {
//			people[i] = emp.txt.get(i).clone();
//			people[i].setWorkingSpace(rp.randomPop());
//		}
//		return new GAPAChromosome(people, restrictions, seats);
		
//		Map<String, Node> bestSeats = null;
//		try {
//			bestSeats = GraphLab.seats(orgPath, empPath);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Person[] people = new Person[emp.txt.size()];
//		for (int i = 0; i < emp.txt.size(); i++) {
//			people[i] = emp.txt.get(i).clone();
////			people[i].setWorkingSpace(rp.randomPop());
//			
//			Node bestSeat = bestSeats.get(people[i].id);
////			System.out.println(bestSeat.getFullId());
//			for (Node seat: seats) {
//				if (seat.getFullId().equals(bestSeat.getFullId())) {
//					people[i].setWorkingSpace(seat);
//					break;
//				}
//			}
////			people[i].setWorkingSpace();
//		}
		//TODO: add restrictions and seats
		int[] order = new int[projects.size()];
		Arrays.setAll(order, i -> i);
		VectorUtils.shuffleArray(order);
		
		int[][] affinities = new int[projects.size()][];
		for (int i = 0; i < affinities.length; i++) {
			int[] row = new int[projects.size()];
			Arrays.setAll(row, k -> k);
			VectorUtils.shuffleArray(row);
			affinities[i] = row;
		}
		
		return new GAPAChromosome2(order, affinities, restrictions, seats);
	}
}
