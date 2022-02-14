package com.daagng.test.common.constants.bank;

public class BankingSystemConstant {
	public static final String BANKING_SYSTEM_HOST = "https://banking-api.sample.com";

	public static final Integer BANK_USER_ERR = 400;
	public static final Integer BANK_ACCOUNT_ERR = 422;
	public static final Integer BANK_SERVER_ERR = 500;

	// 단위는 '초'이다.
	public static final Integer BANK_TIMEOUT = 5;

	public static final String LATE_RESPONSE_MSG = "뱅킹 서비스의 요청이 지연되고 있습니다.";

	public static final String REGISTER_PATH = "/register";
}
