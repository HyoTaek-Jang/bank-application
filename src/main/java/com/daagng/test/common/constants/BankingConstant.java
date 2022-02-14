package com.daagng.test.common.constants;

public class BankingConstant {
	public static String BANKING_SYSTEM_HOST = "https://banking-api.sample.com";
	public static String TEST_HOST = "https://jsonplaceholder.typicode.com";

	public static Integer BANK_USER_ERR = 400;
	public static Integer BANK_ACCOUNT_ERR = 422;
	public static Integer BANK_SERVER_ERR = 500;

	// 단위는 '초'이다.
	public static Integer BANK_TIMEOUT = 3;

	public static Integer TRANSFER_SUCCESS = 0;
	public static Integer TRANSFER_FAIL = 1;
	public static Integer TRANSFER_WAITING = 2;
}
