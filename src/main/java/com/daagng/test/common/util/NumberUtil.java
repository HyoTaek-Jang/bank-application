package com.daagng.test.common.util;

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
}
