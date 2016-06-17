package util;

public class RandomUtils {
	
	public static double randomBetween(double a, double b) {
		return Math.random() * (b - a) + a;
	}
	
	public static int randomBetween(int a, int b) {
		return (int)(Math.random() * (b - a + 1) + a);
	}
	
	public static boolean should(double p) {
		return Math.random() < p;
	}
}
