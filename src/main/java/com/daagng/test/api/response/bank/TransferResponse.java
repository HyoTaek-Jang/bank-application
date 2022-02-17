package com.daagng.test.api.response.bank;

import com.daagng.test.api.response.BaseResponse;

import lombok.Getter;

@Getter
public class TransferResponse extends BaseResponse {
	private final String txId;
	private final String bankTxId;
	private final String result;

	public TransferResponse(String msg, String txId, String bankTxId, String result) {
		super(msg);
		this.txId = txId;
		this.bankTxId = bankTxId;
		this.result = result;
	}
}
