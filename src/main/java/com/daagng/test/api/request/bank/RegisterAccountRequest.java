package com.daagng.test.api.request.bank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class RegisterAccountRequest {
	@NotNull
	@Size(max = 4, min = 4, message = "은행코드는 4자리입니다.")
	private String code;

	@NotNull
	@Size(max = 10, min = 10, message = "계좌번호 길이는 10자리입니다.")
	private String accountNumber;
}
