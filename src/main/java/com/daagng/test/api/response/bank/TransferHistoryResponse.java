package com.daagng.test.api.response.bank;

import java.util.List;

import com.daagng.test.api.response.BaseResponse;

import lombok.Getter;

@Getter
public class TransferHistoryResponse extends BaseResponse {
	List<TransferHistoryDto> historyList;

	public TransferHistoryResponse(String msg,
		List<TransferHistoryDto> historyList) {
		super(msg);
		this.historyList = historyList;
	}
}
