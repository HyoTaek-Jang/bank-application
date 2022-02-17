package com.daagng.test.common.constants.bank;

public class TransferConstant {
	public static final Integer TRANSFER_SUCCESS = 0;
	public static final Integer TRANSFER_FAIL = 1;
	public static final Integer TRANSFER_WAITING = 2;

	public static final Integer TX_ID_SIZE = 8;
	public static final Integer MAX_TX_ID_NUMBER = 9999999;

	public static final String SUCCESS_SIGNATURE = "SUCCESS";
	public static final String FAIL_SIGNATURE = "FAIL";

	public static final String EXIST_WAITING_TRANSFER = "현재 진행 중인 계좌이체가 존재합니다.";
	public static final String FULL_TX_ID = "가능한 은행 거래 ID가 없습니다.";
	public static final String NOT_MATCHING_USER = "등록된 계좌의 사용자가 아닙니다.";

	public static final String FINISHED_REQUEST = "요청을 성공적으로 수행했습니다.";

}
