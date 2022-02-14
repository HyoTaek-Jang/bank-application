package com.daagng.test.api.response.bank;

import com.daagng.test.api.response.BaseResponse;

import lombok.Getter;

@Getter
public class RegisterAccountResponse extends BaseResponse {
	private String accountId;

	public RegisterAccountResponse(String accountId, String msg) {
		super(msg);
		this.accountId = accountId;
	}
}
