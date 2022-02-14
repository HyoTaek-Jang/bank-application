package com.daagng.test.common.util;

import java.util.Random;

public class NumberUtil {
	public static boolean isNumberic(String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	public static String makeRandomNumbers(int size) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}
