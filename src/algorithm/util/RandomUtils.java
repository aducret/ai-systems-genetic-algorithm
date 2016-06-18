package algorithm.util;

import java.util.Random;

public class RandomUtils {
	public static void setSeed(long seed) {
		RandomGenerator.getInstance().setSeed(seed);
	}
	
	public static double random() {
		return RandomGenerator.getInstance().random();
	}
	
	public static double randomBetween(double a, double b) {
		return random() * (b - a) + a;
	}

	/*
	 * devuelve entre a y b inclusive
	 */
	public static int randomBetween(int a, int b) {
		return (int) (random() * (b - a + 1) + a);
	}

	/*
	 * devuelve true con probabilidad p
	 */
	public static boolean should(double p) {
		return random() < p;
	}

	private static class RandomGenerator {
		private static RandomGenerator instance = null;
		private Random generator;

		private RandomGenerator() {
			generator = new Random();
		}
		
		public void setSeed(long seed) {
			generator.setSeed(seed);
		}
		
		public double random() {
			return generator.nextDouble();
		}
		
		public static RandomGenerator getInstance() {
			if (instance == null) {
				instance = new RandomGenerator();
			}
			return instance;
		}
	}
}
