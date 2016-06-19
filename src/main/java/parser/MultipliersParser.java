package parser;

import java.io.FileNotFoundException;
import java.util.Map;

import model.Multipliers;
import model.stats.Stats;

public class MultipliersParser {
	
	// multipliers
	private static final String M_ATTACK = "m_ataque";
	private static final String M_DEFENSE = "m_defensa";
	private static final String M_STRENGTH = "m_fuerza";
	private static final String M_AGILITY = "m_agilidad";
	private static final String M_EXPERTISE = "m_pericia";
	private static final String M_RESISTANCE = "m_resistencia";
	private static final String M_HEALTH = "m_vida";
	
	public static Multipliers parse(String path) throws FileNotFoundException {
		return handleMultipliers(MapParser.parseMap(path));
	}
	
	private static Multipliers handleMultipliers(Map<String, String> configurationMap) {
		Double attack = Double.valueOf(configurationMap.get(M_ATTACK));
		Double defense = Double.valueOf(configurationMap.get(M_DEFENSE));
		Double strength = Double.valueOf(configurationMap.get(M_STRENGTH));
		Double agility = Double.valueOf(configurationMap.get(M_AGILITY));
		Double expertise = Double.valueOf(configurationMap.get(M_EXPERTISE));
		Double resistance = Double.valueOf(configurationMap.get(M_RESISTANCE));
		Double health = Double.valueOf(configurationMap.get(M_HEALTH));
		
		Stats statsMultiplier = new Stats(strength, agility, expertise, resistance, health, 1);
		Multipliers multipliers = new Multipliers(attack, defense, statsMultiplier);
		return multipliers;
	}
}
