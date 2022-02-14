package com.daagng.test.common.exception;

import org.springframework.http.HttpStatus;

import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;

import lombok.Getter;

@Getter
public class BankingSystemException extends IllegalArgumentException{
	private final BankingSystemErrorResponse bankingSystemErrorResponse;
	private final HttpStatus httpStatus;

	public BankingSystemException(BankingSystemErrorResponse body, HttpStatus httpStatus) {
		this.bankingSystemErrorResponse = body;
		this.httpStatus = httpStatus;
	}
}
