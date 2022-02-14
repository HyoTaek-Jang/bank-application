package com.daagng.test.api.response;

import lombok.Getter;

@Getter
public class BaseResponse {
	String message;

	public BaseResponse(String msg) {
		this.message = msg;
	}
}
