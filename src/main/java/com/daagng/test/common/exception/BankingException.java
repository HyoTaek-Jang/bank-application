package com.daagng.test.common.exception;

import org.springframework.http.HttpStatus;

import com.daagng.test.api.response.BankingSystem.BankingSystemErrorResponse;

import lombok.Getter;

@Getter
public class BankingException extends IllegalArgumentException{
	private final BankingSystemErrorResponse bankingSystemErrorResponse;
	private final HttpStatus httpStatus;

	public BankingException(BankingSystemErrorResponse body, HttpStatus httpStatus) {
		this.bankingSystemErrorResponse = body;
		this.httpStatus = httpStatus;
	}
}
