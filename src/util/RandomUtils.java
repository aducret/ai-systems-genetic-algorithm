package util;

public class RandomUtils {
	
	public static double randomBetween(double a, double b) {
		return Math.random() * (b - a) + a;
	}
	
	/*
	 * devuelve entre a y b inclusive
	 * */
	public static int randomBetween(int a, int b) {
		return (int)(Math.random() * (b - a + 1) + a);
	}
	
	/*
	 * devuelve true con probabilidad p
	 * */
	public static boolean should(double p) {
		return Math.random() < p;
	}
}
