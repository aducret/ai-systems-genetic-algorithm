package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import algorithm.Configuration;
import algorithm.crossover.AnularCrossOver;
import algorithm.crossover.CrossOverAlgorithm;
import algorithm.crossover.OnePointCrossOver;
import algorithm.crossover.TwoPointCrossOver;
import algorithm.crossover.UniformCrossOver;
import algorithm.cuttingCondition.ContentCuttingCondition;
import algorithm.cuttingCondition.GoalReachedCuttingCondition;
import algorithm.cuttingCondition.MaxGenerationsCuttingCondition;
import algorithm.cuttingCondition.StructureCuttingCondition;
import algorithm.mutation.ClassicMutation;
import algorithm.mutation.MutationAlgorithm;
import algorithm.mutation.NonUniformMutation;
import algorithm.pairing.AlphaPairingAlgorithm;
import algorithm.pairing.PairingAlgorithm;
import algorithm.replace.ReplaceMethod;
import algorithm.replace.ReplaceMethod1;
import algorithm.replace.ReplaceMethod2;
import algorithm.replace.ReplaceMethod3;
import algorithm.selector.BoltzmannSelector;
import algorithm.selector.CompoundSelector;
import algorithm.selector.DeterministicTournamentSelector;
import algorithm.selector.EliteSelector;
import algorithm.selector.ProbabilisticTournamentSelector;
import algorithm.selector.RandomSelector;
import algorithm.selector.RankingSelector;
import algorithm.selector.RouletteSelector;
import algorithm.selector.Selector;
import algorithm.selector.UniversalSelector;
import model.Multipliers;
import model.stats.Stats;

public class ConfigurationParser {

	private static final String DEFAULT_CONFIGURATION_PATH = "doc/data/defaultConfiguration.txt";
	private static Map<String, String> defaultConfigurationMap;

	static {
		try {
			defaultConfigurationMap = configurationMap(DEFAULT_CONFIGURATION_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// selector names
	private static final String SELECTOR_ELITE = "elite";
	private static final String SELECTOR_ROULETTE = "roulette";
	private static final String SELECTOR_RANKING = "ranking";
	private static final String SELECTOR_UNIVERSAL = "universal";
	private static final String SELECTOR_PROBABILISTIC_TOURNAMENT = "torneo_probabilistico";
	private static final String SELECTOR_DETERMINISTIC_TOURNAMENT = "torneo_deterministico";
	private static final String SELECTOR_BOLTZMANN = "boltzmann";
	private static final String SELECTOR_RANDOM = "selector_random";

	// pairing names
	private static final String PAIRING_ALPHA = "alpha";

	// cross over names
	private static final String CROSS_OVER_ONE_POINT = "un_punto";
	private static final String CROSS_OVER_TWO_POINT = "dos_puntos";
	private static final String CROSS_OVER_UNIFORM = "uniforme";
	private static final String CROSS_OVER_ANULAR = "anular";

	// mutation algorithm names
	private static final String MUTATION_CLASSIC = "classic";
	private static final String MUTATION_NON_UNIFORM = "no_uniforme";

	// replace algorithm names
	private static final String REPLACE_METHOD_1 = "metodo1";
	private static final String REPLACE_METHOD_2 = "metodo2";
	private static final String REPLACE_METHOD_3 = "metodo3";

	// global values
	private static final String N = "N";
	private static final String K = "k";
	private static final String SEED = "seed";

	// cutting conditions
	private static final String CC_LIMIT = "cc_limite_generaciones";
	private static final String CC_GOAL = "cc_objetivo";
	private static final String CC_STRUCTURE = "cc_estructura";
	private static final String CC_CONTENT = "cc_contenido";

	// cross over selector
	private static final String SC_COMPOUND = "sc_compuesto";
	private static final String SC_FIRST = "sc_primero";
	private static final String SC_SECOND = "sc_segundo";
	private static final String SC_PERCENTAGE = "sc_porcentaje";
	private static final String SC_DETERMINISTIC_TOURNAMENT_M = "sc_torneo_deterministico_m";
	private static final String SC_BOLTZMANN_TEMPERATURE = "sc_boltzmann_temperatura_inicial";

	// pairing algorithm
	private static final String AA_ALGORITHM = "aa_algoritmo";

	// cross over method
	private static final String TC_TYPE = "tc_tipo";
	private static final String TC_PC = "tc_pc";
	private static final String TC_UNIFORM_P = "tc_uniform_p";

	// mutation algorithm
	private static final String AM_ALGORITHM = "am_algoritmo";
	private static final String AM_PM = "am_pm";

	// replace selector
	private static final String SR_COMPOUND = "sr_compuesto";
	private static final String SR_FIRST = "sr_primero";
	private static final String SR_SECOND = "sr_segundo";
	private static final String SR_PERCENTAGE = "sr_porcentaje";
	private static final String SR_DETERMINISTIC_TOURNAMENT_M = "sr_torneo_deterministico_m";
	private static final String SR_BOLTZMANN_TEMPERATURE = "sr_boltzmann_temperatura_inicial";

	// replace method
	private static final String MR_METHOD = "mr_metodo";

	public static Configuration parse(String path) throws FileNotFoundException {
		return handleConfigurationMap(MapParser.parseMap(path));
	}

	private static Configuration handleConfigurationMap(Map<String, String> configurationMap) {
		Configuration.Builder builder = new Configuration.Builder();
		handleGlobalValues(configurationMap, builder);
		handleCuttingConditions(configurationMap, builder);
		handleCrossOverSelector(configurationMap, builder);
		handlePairingAlgorithm(configurationMap, builder);
		handleCrossOverAlgorithm(configurationMap, builder);
		handleMutationAlgorithm(configurationMap, builder);
		handleReplaceMethod(configurationMap, builder);
		return builder.build();
	}

	private static String getValue(Map<String, String> configurationMap, String key) {
		if (configurationMap.containsKey(key))
			return configurationMap.get(key);
		return defaultConfigurationMap.get(key);
	}

	private static void handleGlobalValues(Map<String, String> configurationMap, Configuration.Builder builder) {
		Integer n = Integer.valueOf(getValue(configurationMap, N));
		Integer k = Integer.valueOf(getValue(configurationMap, K));
		Long seed = Long.valueOf(getValue(configurationMap, SEED));
		builder.withN(n).withK(k).withSeed(seed);
	}

	private static void handleCuttingConditions(Map<String, String> configurationMap, Configuration.Builder builder) {
		Integer limit = Integer.valueOf(getValue(configurationMap, CC_LIMIT));
		Double goal = Double.valueOf(getValue(configurationMap, CC_GOAL));
		Double structureTolerance = Double.valueOf(getValue(configurationMap, CC_STRUCTURE));
		Integer contentTolerance = Integer.valueOf(getValue(configurationMap, CC_CONTENT));
		builder.addCuttingCondition(new MaxGenerationsCuttingCondition(limit))
				.addCuttingCondition(new GoalReachedCuttingCondition(goal))
				.addCuttingCondition(new StructureCuttingCondition(structureTolerance))
				.addCuttingCondition(new ContentCuttingCondition(contentTolerance));
	}

	private static void handleCrossOverSelector(Map<String, String> configurationMap, Configuration.Builder builder) {
		Boolean compound = Boolean.valueOf(getValue(configurationMap, SC_COMPOUND));
		String first = getValue(configurationMap, SC_FIRST);
		if (compound) {
			String second = getValue(configurationMap, SC_SECOND);
			Double percentage = Double.valueOf(getValue(configurationMap, SC_PERCENTAGE));
			builder.withCrossOverSelector(new CompoundSelector(createSelector(first, configurationMap),
					createSelector(second, configurationMap), percentage));
		} else {
			builder.withCrossOverSelector(createSelector(first, configurationMap));
		}
	}

	private static void handlePairingAlgorithm(Map<String, String> configurationMap, Configuration.Builder builder) {
		builder.withPairingAlgorithm(createPairingAlgorithm(getValue(configurationMap, AA_ALGORITHM)));
	}

	private static void handleCrossOverAlgorithm(Map<String, String> configurationMap,
			Configuration.Builder builder) {
		String type = getValue(configurationMap, TC_TYPE);
		builder.withCrossOverAlgorithm(createCrossOverAlgorithm(type, configurationMap));
	}

	private static void handleMutationAlgorithm(Map<String, String> configurationMap,
			Configuration.Builder builder) {
		String name = getValue(configurationMap, AM_ALGORITHM);
		builder.withMutationAlgorithm(createMutationAlgorithm(name, configurationMap));
	}

	private static Selector getReplaceSelector(Map<String, String> configurationMap) {
		Selector ans = null;
		Boolean compound = Boolean.valueOf(getValue(configurationMap, SR_COMPOUND));
		String first = getValue(configurationMap, SR_FIRST);
		if (compound) {
			String second = getValue(configurationMap, SR_SECOND);
			Double percentage = Double.valueOf(getValue(configurationMap, SR_PERCENTAGE));
			ans = new CompoundSelector(createSelector(first, configurationMap),
					createSelector(second, configurationMap), percentage);
		} else {
			ans = createSelector(first, configurationMap);
		}
		return ans;
	}

	private static void handleReplaceMethod(Map<String, String> configurationMap,
			Configuration.Builder builder) {
		String name = getValue(configurationMap, MR_METHOD);
		builder.withReplaceMethod(createReplaceMethod(name, getReplaceSelector(configurationMap)));
	}

	public static void main(String[] args) throws FileNotFoundException {
		parse("doc/data/configuration.txt");
	}

	private static Selector createSelector(String selectorName, Map<String, String> configurationMap) {
		switch (selectorName) {
		case SELECTOR_ELITE:
			return new EliteSelector();
		case SELECTOR_ROULETTE:
			return new RouletteSelector();
		case SELECTOR_RANKING:
			return new RankingSelector();
		case SELECTOR_UNIVERSAL:
			return new UniversalSelector();
		case SELECTOR_PROBABILISTIC_TOURNAMENT:
			return new ProbabilisticTournamentSelector();
		case SELECTOR_DETERMINISTIC_TOURNAMENT:
			return new DeterministicTournamentSelector(
					Integer.valueOf(getValue(configurationMap, SC_DETERMINISTIC_TOURNAMENT_M)));
		case SELECTOR_BOLTZMANN:
			return new BoltzmannSelector(Double.valueOf(getValue(configurationMap, SC_BOLTZMANN_TEMPERATURE)));
		case SELECTOR_RANDOM:
			return new RandomSelector();
		default:
			return null;
		}
	}

	private static PairingAlgorithm createPairingAlgorithm(String pairingAlgorithmName) {
		switch (pairingAlgorithmName) {
		case PAIRING_ALPHA:
			return new AlphaPairingAlgorithm();
		default:
			return null;
		}
	}

	private static CrossOverAlgorithm createCrossOverAlgorithm(String crossOverAlgorithmName,
			Map<String, String> configurationMap) {
		double pc = Double.valueOf(getValue(configurationMap, TC_PC));
		switch (crossOverAlgorithmName) {
		case CROSS_OVER_ONE_POINT:
			return new OnePointCrossOver(pc);
		case CROSS_OVER_TWO_POINT:
			return new TwoPointCrossOver(pc);
		case CROSS_OVER_UNIFORM:
			return new UniformCrossOver(pc, Double.valueOf(getValue(configurationMap, TC_UNIFORM_P)));
		case CROSS_OVER_ANULAR:
			return new AnularCrossOver(pc);
		default:
			return null;
		}
	}

	private static MutationAlgorithm createMutationAlgorithm(String mutationAlgorithmName,
			Map<String, String> configurationMap) {
		double pm = Double.valueOf(getValue(configurationMap, AM_PM));
		switch (mutationAlgorithmName) {
		case MUTATION_CLASSIC:
			return new ClassicMutation(pm);
		case MUTATION_NON_UNIFORM:
			return new NonUniformMutation(pm);
		default:
			return null;
		}
	}

	private static ReplaceMethod createReplaceMethod(String replaceMethodName, Selector selector) {
		switch (replaceMethodName) {
		case REPLACE_METHOD_1:
			return new ReplaceMethod1();
		case REPLACE_METHOD_2:
			return new ReplaceMethod2(selector);
		case REPLACE_METHOD_3:
			return new ReplaceMethod3(selector);
		default:
			return null;
		}
	}
}
