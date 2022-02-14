package com.daagng.test.common.exception;

import org.springframework.http.HttpStatus;

import com.daagng.test.api.response.BankingHttp.BankingErrorResponse;

import lombok.Getter;

@Getter
public class BankingException extends IllegalArgumentException{
	private final BankingErrorResponse bankingErrorResponse;
	private final HttpStatus httpStatus;

	public BankingException(BankingErrorResponse body, HttpStatus httpStatus) {
		this.bankingErrorResponse = body;
		this.httpStatus = httpStatus;
	}
}
