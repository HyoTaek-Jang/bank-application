package com.daagng.test.api.response.BankingHttp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankingSystemErrorResponse {
	private String code;
	private String message;
}
