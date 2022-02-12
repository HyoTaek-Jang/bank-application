package com.daagng.test.api.response;

import lombok.Getter;

@Getter
public class BaseResponse {
	String msg = null;

	public BaseResponse(String msg) {
		this.msg = msg;
	}
}
