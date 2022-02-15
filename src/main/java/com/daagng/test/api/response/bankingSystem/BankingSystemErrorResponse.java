package com.daagng.test.api.response.bankingSystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankingSystemErrorResponse {
	private String code;
	private String message;
}
