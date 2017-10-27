package util;

import java.util.Arrays;
import java.util.Collections;

public class Test {
	
	public static void main(String[] args) {
		
		Integer[] nums = new Integer[10];
		Arrays.setAll(nums, i -> 15-i);
		Collections.sort(Arrays.asList(nums));
		
		System.out.println(Arrays.asList(nums));
	}
}
