package com.daagng.test.common.constants.bank;

public class RegisterConstant {
	public static final int CODE_SIZE = 4;
	public static final int ACCOUNT_NUMBER_SIZE = 10;
	public static final int ACCOUNT_ID_SIZE = 8;

	public static final int MIN_NUMBER = 0;
	public static final String ACCOUNT_ID_MAX_NUMBER = "99999999";
	public static final String ACCOUNT_NUMBER_MAX_NUMBER = "9999999999";

	public static final String CODE_SIZE_MSG = "은행코드는 " + CODE_SIZE + "자리입니다.";
	public static final String ACCOUNT_NUMBER_SIZE_MSG = "계좌번호 길이는 " + ACCOUNT_NUMBER_SIZE + "자리 숫자입니다.";
	public static final String ACCOUNT_ID_SIZE_MSG = "등록 계좌의 길이는 " + ACCOUNT_ID_SIZE + "자리 숫자입니다.";
	public static final String EXIST_ACCOUNT_NUMBER = "이미 등록된 계좌번호입니다.";
	public static final String NOT_EXIST_CODE = "존재하지 않는 은행코드입니다.";

	public static final String SUCCESS_REGISTER = "계좌 등록을 완료했습니다.";
}
