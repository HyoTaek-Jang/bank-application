package com.daagng.test.api.response;

import lombok.Getter;

@Getter
public class BaseResponse {
	String msg;

	public BaseResponse(String msg) {
		this.msg = msg;
	}
}
