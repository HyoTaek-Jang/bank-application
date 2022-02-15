package com.daagng.test.common.exception;

import javax.validation.ValidationException;

import lombok.Getter;

@Getter
public class CustomValidException extends ValidationException {
	private final Integer status;
	public final String message;

	public CustomValidException(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
}
