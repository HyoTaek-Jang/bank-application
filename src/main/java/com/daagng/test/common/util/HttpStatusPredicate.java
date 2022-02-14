package com.daagng.test.common.util;

import org.springframework.http.HttpStatus;

public class HttpStatusPredicate {
	public static boolean test(HttpStatus status, Integer targetCode) {
		return status == HttpStatus.valueOf(targetCode);
	}
}
