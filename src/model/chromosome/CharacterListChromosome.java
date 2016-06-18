package model.chromosome;

import java.util.ArrayList;
import java.util.List;

import algorithm.chromosome.Chromosome;
import algorithm.chromosome.ListChromosome;
import algorithm.gene.Gene;
import model.Multipliers;
import model.gene.CharacterGene;
import model.stats.Stats;

public class CharacterListChromosome extends ListChromosome {

	private Multipliers multipliers;
	private Double fitness = null;
	
	public CharacterListChromosome(List<Gene> genes, Multipliers multipliers) {
		super(genes);
		this.multipliers = multipliers;
	}

	@Override
	public Chromosome cloneChromosome() {
		return new CharacterListChromosome(new ArrayList<Gene>(genes()), multipliers);
	}

	@Override
	public double fitness() {
		if (fitness != null) { return fitness; }
		
		Stats totalStats = new Stats(0, 0, 0, 0, 0, 0);
		for (Gene gene : genes()) {
			CharacterGene characterGene = (CharacterGene) gene;
			totalStats.add(characterGene.getDeltaStats());
		}
		polishStats(totalStats);
		fitness = multipliers.attackMultiplier * attack(totalStats) + multipliers.defenseMultiplier * defense(totalStats);
		return fitness;
	}

	private void polishStats(Stats stats) {
		stats.strength = 100 * Math.tanh(0.01 * stats.strength * multipliers.statsMultiplier.strength);
		stats.agility = Math.tanh(0.01 * stats.agility * multipliers.statsMultiplier.agility);
		stats.expertise = 0.6 * Math.tanh(0.01 * stats.expertise * multipliers.statsMultiplier.expertise);
		stats.resistance = Math.tanh(0.01 * stats.resistance * multipliers.statsMultiplier.resistance);
		stats.health = 100 * Math.tanh(0.01 * stats.health * multipliers.statsMultiplier.health);
	}

	private double attack(Stats totalStats) {
		double h = totalStats.height;
		double atm = 0.5 - Math.pow(3 * h - 5, 4) + Math.pow(3 * h - 5, 2) + h / 2.0;
		return (totalStats.agility + totalStats.expertise) * totalStats.strength * atm;
	}

	private double defense(Stats totalStats) {
		double h = totalStats.height;
		double dem = 2 + Math.pow(3 * h - 5, 4) - Math.pow(3 * h - 5, 2) - h / 2.0;
		return (totalStats.resistance + totalStats.expertise) * totalStats.health * dem;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("--CHROMOSOME--\n");
		builder.append("fitness: " + fitness() + "\n");
		for (Gene gene: genes()) {
			builder.append(gene.toString());
			builder.append("\n");
		}
		builder.append(multipliers);
		builder.append("\n");
		return builder.toString();
	}
}
