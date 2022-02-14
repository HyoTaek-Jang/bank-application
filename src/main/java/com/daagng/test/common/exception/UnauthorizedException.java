package com.daagng.test.common.exception;

public class UnauthorizedException extends IllegalArgumentException{
	public UnauthorizedException() {}
	public UnauthorizedException(String message) {
		super(message);
	}
}
